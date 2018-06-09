// pages/my/my.js
//获取应用实例
const app = getApp()
Page({
  data:{
    ifshow: false,//ifshow 用来阻止页面渲染，防止页面在进行角色页面选择时候渲染默认页面，而导致的后来的跳屏的不好体验
    viewForWho: "",//用户角色
    username: "",
    initImgUrl: "",//用户选择的头像地址
    isWxImage: true,//默认微信头像
    genderListArray: ['男', '女'],//性别列表
    initSexIndex: 0,//默认性别
    schoolListArray: ['麒麟小学'],//学校列表
    initSchoolIndex: 0,//默认学校
    roleListArray: ['学生', '老师'],//角色列表
    roleIdListArray: [0, 1],//角色列表
    initRoleIndex: 0,//默认角色
    selectRole:"",//审核中时候显示注册的角色
    ifPushing:false,//审核中，不用加载班级列表
    selectedClass: "",
    //年级需要联动，逻辑较为复杂，涉及到遍历去重
    classData:null,
    multiArrayForAllGradesAndClassed: [],
    multiIdArrayForAllGradesAndClassed: [],
    multiClassIndex:[0,0],
    choosedGradeId: 0,//选择的年级Id
    choosedClassId: 0,//选择的班级Id
    buttonText: "提交",
    buttonType: "default",
    buttonDisable: false,
    
  },
  onLoad: function () {
    //由于该页为起始页即使小程序未授权，跳转授权页之前也会先加载本页面，为避免页面跳转中间无谓的js加载，此处加一个判断
    if (app.globalData.hasAthurize){
      // 登录
      wx.login({
        success: res => {
          // 发送 res.code 到后台换取 openId, sessionKey, unionId 返回识别符，用以后面数据交互用户识别
          wx.request({
            url: 'https://www.grosup.com/practice/login/decode.do',
            method: 'post',
            header: {
              'content-type': 'application/x-www-form-urlencoded'
            },
            data: {
              code: res.code
            },
            success: res => {
              //本地存储识别码
              app.globalData.userId = res.data.third_session
              //拿到识别码后，获取用户信息，返回用户审核状态值
              getUsersInfo();
            }
          })
        }
      })
      // 获取用户信息, 特别是用户状态
      let getUsersInfo = () => {
        wx.getUserInfo({
          success: res => {
            // 可以将 res 发送给后台解码出 unionId
            app.globalData.userInfo = res.userInfo
            //向后台查询用户开发者系统信息，以及此时状态，返回审核中，被拒绝 或 审核通过
            wx.request({
              url: 'https://www.grosup.com/practice/user/info.do',
              // method: 'post',
              header: {
                'content-type': 'application/x-www-form-urlencoded',
                'third_session': app.globalData.userId
              },
              success: res => {
                //根据用户状态进行页面的不同显示，只有当用户通过审核时候才会显示个人信息页，否则显示注册页
                //格式化用户角色
                let userType = res.data.data.userType
                let userRole//储存用户角色，用于审核通过状态展示对应页面
                let selectRole //中文格式角色，用于审核时同步当前角色
                switch (userType) {
                  case 0:
                    userRole = "student",
                    selectRole = "学生"
                    break;
                  case 1:
                    userRole = "teacher",
                    selectRole = "老师"
                    break;
                  default:
                    userRole = "admin",
                    selectRole = "管理员"
                }
                let userStatus = res.data.data.status
                // let userStatus = 3
                if (userStatus == 1) {//通过审核
                  //修改app的用户状态
                  app.globalData.userInfoInOurSystem.userStatus = "accessed";
                  //用户个人信息储存
                  app.globalData.userInfoInOurSystem.personInfo = res.data.data;
                  //判断用户角色，同步页面，放在最后，否则会出发组件页的attached事件
                  this.setData({
                    viewForWho: userRole,
                    ifshow: true
                  })
                  
                }else{//未通过，显示注册页
                  this.setData({
                    ifshow: true,
                    selectRole: selectRole
                  })
                  switch (userStatus) {
                    case 0://审核中, 同步所选班级
                      this.setData({
                        ifPushing: true,
                        username: res.data.name,
                        selectedClass: res.data.gradeName + res.data.className,
                        buttonText: "审核中",
                        buttonType: "primary",
                        buttonDisable: true
                      })
                      break;
                    default://未提交审核或者审核被拒
                      //由于query接口加载顺序问题，将本页面的js执行放到app.js中
                      let loadClass = () => {
                        //循环找出所有不重复的年级和班级
                        let getInrepeatClasses = (res) => {
                          this.data.classData = res.data.data;
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
                        // //由于app.onlaunch里面请求可能在此页面后，故在此保存函数，谁先加载谁调
                        // app.getUserClassList = ()=>{
                        //向服务器请求班级学生角色列表，动态刷新当前可选班级
                        //只有当用户状态为未提交或者审核被拒时候才，需要请求用户class列表
                        if (userStatus == -1 | 2) {
                          wx.request({
                            url: 'https://www.grosup.com/practice/user/query.do',
                            header: {
                              'content-type': 'application/x-www-form-urlencoded',
                              'third_session': app.globalData.userId
                            },
                            success: res => {
                              //本地存储识别码
                              console.log(res.data.data)
                              //循环找出所有不重复的年级和班级
                              getInrepeatClasses(res)
                            }
                          })
                        }
                      }
                      loadClass()//加载可选择列表
                      //如果是审核未通过，应该告知用户重新提交
                      if (userStatus == 2){
                        this.setData({
                          buttonText: "点击重新提交（审核被拒绝）",
                          buttonType: "warn"
                        })
                      }
                  }
                }
              }
            })
          }
        })
      }
    }
    
  },
  bindInputName: function (e) {
    this.setData({
      username: e.detail.value
    })
  },
  bindChooseImg: function () {//选择头像事件
    if (!this.data.buttonDisable){//状态不是pushing
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
    if (choosedGradeColume == 0){
      //当前选择的年级ID
      this.data.choosedGradeId = this.data.multiIdArrayForAllGradesAndClassed[0][choosedGradeValue]
      let classDataLength = this.data.classData.length;
      //滞空数组，重建
      // this.data.multiArrayForAllGradesAndClassed
      let newClassedIdArray = [];
      let newClassedNameArray = [];
      for (let i = 0; i < classDataLength; i ++){
        if (this.data.classData[i].gradeID == this.data.choosedGradeId){
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
    }else{
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
      let sendData = {
        name: this.data.username,
        gender: this.data.genderListArray[this.data.initSexIndex],
        icon: imgUrl,
        schoolName: this.data.schoolListArray[this.data.initSchoolIndex],
        gradeID: this.data.multiIdArrayForAllGradesAndClassed[0][this.data.multiClassIndex[0]],
        classID: this.data.multiIdArrayForAllGradesAndClassed[1][this.data.multiClassIndex[1]],
        userType: this.data.roleIdListArray[this.data.initRoleIndex]
      };
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
        success:  (res)=> {
          if(res.data.code == "success"){
            this.setData({
              buttonText: "信息已提交，正在审核中",
              buttonType: "primary",
              buttonDisable: true
            })
          }
          
        }
      })
    
  }
})