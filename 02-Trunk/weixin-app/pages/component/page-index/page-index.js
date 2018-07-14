// pages/component/page-index/page-index.js
const app = getApp()
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    userObj: {
      headImgUrl: "",
      studentName: "小宝",
      flowerNum: "1200",
      overedNum: "35"
    }
  },
  attached: function () {
    wx.request({
      url: 'https://www.grosup.com/practice/statistics/userRank.do',
      // method: 'post',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'third_session': app.globalData.userId
      },
      data: {
        userID: app.globalData.userInfoInOurSystem.personInfo.id
      },
      success: res => {
        let gradeSubjectItemMessage = res.data.data
        //拿出二年级数学科目下的所有习题
        this.setData({
          practiceArray: gradeSubjectItemMessage[0].subjects[0].typeDetails
        })
      }
    })
  },
  /**
   * 组件的方法列表
   */
  methods: {

  }
})
