// pages/component/my-teacher/page-my-teacher.js
const app = getApp()
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },
  // 生命周期函数，可以为函数，或一个在methods段中定义的方法名
  attached: function () {
    console.log(app)
    //小程序中拿到老师信息
    let teacherInfo = app.globalData.userInfoInOurSystem.personInfo;
    //同步老师信息
    this.setData({
      teacherName: teacherInfo.name,
      teacherGrade: teacherInfo.gradeName,
      teacherClass: teacherInfo.className,
      teacherSchool: teacherInfo.schoolName
    })
    // 当前组件展示时候触发，请求学生信息
    wx.request({
      url: 'https://www.grosup.com//practice/user/students.do',
      // method: 'post',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
          'third_session': app.globalData.userId
      },
      data: {
        classID: teacherInfo.classID
      },
      success: res => {
        //拿到返回学生信息数组,setData更新页面
        this.setData({
          studentListArray: res.data
        })
      }
    })
  },
  /**
   * 组件的初始数据
   */
  data: {
    teacherName: "",
    teacherGrade: "",
    teacherClass: "",
    teacherSchool: "",
    //角色下学生列表
    studentListArray: []
  },

  /**
   * 组件的方法列表
   */
  methods: {
    clickAgree: function(e){
      //点击学生的id
      let clickedStudentId = e.target.dataset.id
      //点击的学生在学生列表中的位置
      let clickedStudentItemindex = e.target.dataset.itemindex

      // 发起同意请求
      wx.request({
        url: 'https://www.grosup.com/practice/user/check.do',
        method: 'POST',
        header: {
          'content-type': 'application/x-www-form-urlencoded',
          'third_session': app.globalData.userId
        },
        data: {
          userID: clickedStudentId
        },
        success: res => {
          console.log(e)
          //当前学生状态为审核通过，同步页面
          let studentItemStatus = 'studentListArray[' + clickedStudentItemindex + '].status'
          this.setData({
            [studentItemStatus]: 1
          })
        }
      })
    },
    clickDisagree: function (e) {
      //点击学生的id
      let clickedStudentId = e.target.dataset.id
      //点击的学生在学生列表中的位置
      let clickedStudentItemindex = e.target.dataset.itemindex
      
      // 发起同意请求
      wx.request({
        url: 'https://www.grosup.com/practice/user/checkRefused.do',
        method: 'POST',
        header: {
          'content-type': 'application/x-www-form-urlencoded',
          'third_session': app.globalData.userId
        },
        data: {
          userID: clickedStudentId
        },
        success: res => {
          //当前学生状态为审核拒绝，同步页面
          //删除有点坑，先保存学生数组，然后移除单前项
          let studentList = this.data.studentListArray
          studentList.splice(clickedStudentItemindex, 1)
          this.setData({
            studentListArray: studentList
          })

        }
      })
    }
  }
})
