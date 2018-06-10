// pages/component/page-my-student/page-my-student.js
const app = getApp()
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },
  // 生命周期函数，可以为函数，或一个在methods段中定义的方法名
  attached: function () {
    let studentInfo = app.globalData.userInfoInOurSystem.personInfo;
    //setData更新页面
    this.setData({
      studentName: studentInfo.name,
      studentGrade: studentInfo.gradeName,
      studentClass: studentInfo.className,
      studentSchool: studentInfo.schoolName
    })
    // 当前组件展示时候触发，请求学生信息
    // wx.request({
    //   url: 'https://www.grosup.com/practice/user/students.do',
    //   // method: 'post',
    //   header: {
    //     'content-type': 'application/x-www-form-urlencoded'
    //   },
    //   data: {
    //     classID: studentInfo.classID
    //   },
    //   success: res => {
    //     //拿到返回学生信息
    //     let returnStudentInfo = res.data[0]
        
    //   }
    // })
  },
  /**
   * 组件的初始数据
   */
  data: {
    studentName: "",
    studentGrade: "",
    studentClass: "",
    studentSchool: ""
  },

  /**
   * 组件的方法列表
   */
  methods: {

  }
})
