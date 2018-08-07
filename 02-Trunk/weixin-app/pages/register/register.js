// pages/register/register.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    username: "",
    initImgUrl: "",//用户选择的头像地址
    isWxImage: true,//默认微信头像
    genderListArray: ['女', '男'],//性别列表
    initSexIndex: 0,//默认性别
    schoolListArray: ['麒麟小学'],//学校列表
    initSchoolIndex: 0,//默认学校
    roleListArray: ['学生', '老师'],//角色列表
    roleIdListArray: [0, 1],//角色列表
    initRoleIndex: 0,//默认角色
    selectRole: "",//审核中时候显示注册的角色
    ifPushing: false,//审核中，不用加载班级列表
    selectedClass: "",
    //年级需要联动，逻辑较为复杂，涉及到遍历去重
    classData: null,
    multiArrayForAllGradesAndClassed: [],
    multiIdArrayForAllGradesAndClassed: [],
    multiClassIndex: [0, 0],
    choosedGradeId: 0,//选择的年级Id
    choosedClassId: 0,//选择的班级Id
    buttonText: "提交",
    buttonType: "default",
    buttonDisable: false,
    userTabbarClickKey: "my"
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //如果是从修改信息按钮进来
    if (options.resetMessage) {
      wx.request({
        url: 'https://www.grosup.com/practice/user/info.do',
        header: {
          'content-type': 'application/x-www-form-urlencoded',
          'third_session': app.globalData.userId
        },
        success: res => {
          this.loadClass()
          this.setData({
            username: res.data.data.name
          })
        }
      })
    }else{
      let userInfo = app.globalData.userInfoInOurSystem.personInfo
      let userStatus = userInfo.status
      // let userStatus = -1
      if (userStatus == 0) {//审核中, 同步所选班级
        this.setData({
          selectRole: userInfo.formatSelectRole,
          ifPushing: true,
          initImgUrl: userInfo.icon,
          initSexIndex: userInfo.gender,
          username: userInfo.name,
          selectedClass: userInfo.gradeName + userInfo.className,
          buttonText: "审核中",
          buttonType: "primary",
          buttonDisable: true
        })
      } else {//未提交审核或者审核被拒
        //由于query接口加载顺序问题，将本页面的js执行放到app.js中
        this.loadClass(userStatus)//加载可选择列表
        //如果是审核未通过，应该告知用户重新提交
        if (userStatus == 2) {
          this.setData({
            buttonText: "点击重新提交（审核被拒绝）",
            buttonType: "warn"
          })
        }
      }
    }
    
  },
  loadClass(userStatus) {
    //循环找出所有不重复的年级和班级
    let getInrepeatClasses = (res) => {
      this.setData({
        classData: res.data.data
      })
      let dataLength = res.data.data.length;
      let gradeList = [];
      let gradeIdList = [];
      let classList = [];
      let classIdList = [];
      for (let i = 0; i < dataLength; i++) {
        gradeList.push(this.data.classData[i].gradeName);
        gradeIdList.push(this.data.classData[i].gradeID);
        classList.push(this.data.classData[i].className);
        classIdList.push(this.data.classData[i].classID);
      }
      //
      this.setData({
        multiArrayForAllGradesAndClassed: [app.globalData.filterRepeatArray(gradeList), app.globalData.filterRepeatArray(classList)],
        multiIdArrayForAllGradesAndClassed: [app.globalData.filterRepeatArray(gradeIdList), app.globalData.filterRepeatArray(classIdList)],
        BackupClassArray: [app.globalData.filterRepeatArray(gradeList), app.globalData.filterRepeatArray(classList)],
        BackupClassIdArray: [app.globalData.filterRepeatArray(gradeIdList), app.globalData.filterRepeatArray(classIdList)]
      })
    }
    if (userStatus == -1 | 2) {
      wx.request({
        url: 'https://www.grosup.com/practice/user/query.do',
        header: {
          'content-type': 'application/x-www-form-urlencoded',
          'third_session': app.globalData.userId
        },
        success: res => {
          //循环找出所有不重复的年级和班级
          getInrepeatClasses(res)
        }
      })
    }
  },
  bindInputName: function (e) {
    this.setData({
      username: e.detail.value
    })
  },
  bindChooseImg: function () {//选择头像事件
    if (!this.data.buttonDisable) {//状态不是pushing
      var _this = this;
      wx.chooseImage({
        success: function (res) {
          console.log(res, res.tempFiles[0].size)
          if (res.tempFiles[0].size > 300000) {
            wx.showToast({
              title: '图片大小不得大于5kb',
              icon: 'none',
              duration: 5000
            });
          } else {
            console.log(_this)
            _this.setData({
              initImgUrl: res.tempFilePaths[0]
            })
            wx.showToast({
              title: '成功la',
              icon: 'success',
              duration: 2000
            });
          }

        },
      })
    }
  },
  bindGenderPickerChange: function (e) {//性别选择事件
    this.setData({
      initSexIndex: e.detail.value
    })
  },
  bindSchoolPickerChange: function (e) {//学校选择事件
    this.setData({
      initSchoolIndex: e.detail.value
    })
  },
  bindTapChooseClass: function () {//点击时候充值班级角色列表
    this.setData({
      multiArrayForAllGradesAndClassed: this.data.BackupClassArray,
      multiIdArrayForAllGradesAndClassed: this.data.BackupClassIdArray
    })
  },
  bindMultiPickerChange: function (e) {//选择班级，点击确定
    this.setData({
      multiClassIndex: e.detail.value
    })
  },
  bindMultiPickerColumnChange: function (e) {//选择班级，绑定多列联动
    //修改的列
    let choosedGradeColume = e.detail.column;
    //修改的行
    let choosedGradeValue = e.detail.value;
    //如果修改年级
    if (choosedGradeColume == 0) {
      //当前选择的年级ID
      this.data.choosedGradeId = this.data.multiIdArrayForAllGradesAndClassed[0][choosedGradeValue]
      let classDataLength = this.data.classData.length;
      //滞空数组，重建
      // this.data.multiArrayForAllGradesAndClassed
      let newClassedIdArray = [];
      let newClassedNameArray = [];
      for (let i = 0; i < classDataLength; i++) {
        if (this.data.classData[i].gradeID == this.data.choosedGradeId) {
          //修改对应关联数组的classId
          newClassedIdArray.push(this.data.classData[i].classID);
          //修改对应关联数组的className
          newClassedNameArray.push(this.data.classData[i].className);
        }
      }
      this.setData({
        multiArrayForAllGradesAndClassed: [this.data.multiArrayForAllGradesAndClassed[0], newClassedNameArray],
        multiIdArrayForAllGradesAndClassed: [this.data.multiIdArrayForAllGradesAndClassed[0], newClassedIdArray],
      })
    } else {
      //当前班的年级ID
      this.data.choosedClassId = this.data.multiIdArrayForAllGradesAndClassed[1][choosedGradeValue]
      let classDataLength = this.data.classData.length;
      //滞空数组，重建

      // this.data.multiArrayForAllGradesAndClassed
      let newGradeIdArray = [];
      let newGradeNameArray = [];
      for (let i = 0; i < classDataLength; i++) {
        if (this.data.classData[i].classID == this.data.choosedClassId) {
          //修改对应关联数组的classId
          newGradeIdArray.push(this.data.classData[i].gradeID);
          //修改对应关联数组的className
          newGradeNameArray.push(this.data.classData[i].gradeName);
        }
      }
      this.setData({
        multiArrayForAllGradesAndClassed: [newGradeNameArray, this.data.multiArrayForAllGradesAndClassed[1]],
        multiIdArrayForAllGradesAndClassed: [newGradeIdArray, this.data.multiIdArrayForAllGradesAndClassed[1]],
      })
    }

  },
  bindRolePickerChange: function (e) {//角色选择事件
    this.setData({
      initRoleIndex: e.detail.value
    })
  },
  submitUserMessage: function (e) {//点击提交按钮，弹出是否允许获取用户信息
    let imgUrl = this.data.initImgUrl == "" ? app.globalData.userInfo.avatarUrl : this.data.initImgUrl;
    let genderKey = this.data.genderListArray[this.data.initSexIndex] == "男" ? 1 : 0;
    let sendData = {
      name: this.data.username,
      gender: genderKey,
      icon: imgUrl,
      schoolName: this.data.schoolListArray[this.data.initSchoolIndex],
      gradeID: this.data.multiIdArrayForAllGradesAndClassed[0][this.data.multiClassIndex[0]],
      classID: this.data.multiIdArrayForAllGradesAndClassed[1][this.data.multiClassIndex[1]],
      userType: this.data.roleIdListArray[this.data.initRoleIndex]
    };
    if (sendData.name){
      wx.request({
        url: 'https://www.grosup.com/practice/user/add.do',
        method: 'post',
        header: {
          'content-type': 'application/json;charset=UTF-8',
          // 'content-type': 'application/x-www-form-urlencoded',
          'third_session': app.globalData.userId
        },
        //注意这里头像有可能用户选用微信图像，需要做个判断
        data: sendData,
        success: (res) => {
          if (res.data.code == "success") {
            this.setData({
              // buttonText: "信息已提交，正在审核中",
              buttonText: "信息已提交，待审核后，重新进小程序体验",
              buttonType: "primary",
              buttonDisable: true
            })
          }

        }
      })
    }else{
      wx.showToast({
        title: '请将用户名称补充完整',
        icon: 'none'
      })
    }
    

  },
  navigateTo: function (e) {
    let _this = this
    let href = e.detail.href
    //点击分为四种情况，如果点击我的需要做单独的设置
    
    this.setData({
      userTabbarClickKey: href
    })
    //只有用户状态为accessed时候才可以点击,
    if (href != "my") {
      wx.showModal({
        title: '温馨提示',
        content: '只有完成注册才有权限访问当前页面，请先完成注册信息',
        success (res) {
          if (res.confirm) {
            _this.setData({
              //展示注册页，点亮我的按钮
              userTabbarClickKey: "my",
            })
          } else if (res.cancel) {
            console.log('用户点击取消')
          }
        }
      })
    }
  }
})