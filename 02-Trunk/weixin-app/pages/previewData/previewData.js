// pages/previewData/previewData.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    //默认展示练习题 practice/test
    initShow: "practice",
    //总共练习信息
    quesTotalInfo: {},
    //分步练习信息
    knowledgeInfo: [],
    //总共练习信息
    paperTotalInfo: {},
    //分步练习信息
    paperHaveDoneInfo: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.practiceClick()
  },
  practiceClick () {
    wx.request({
      url: 'https://www.grosup.com/practice/statistics/userQuesDoneInfo.do',
      // method: 'post',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'third_session': app.globalData.userId
      },
      data: {
        userID: app.globalData.userInfoInOurSystem.personInfo.id
      },
      success: res => {
        //返回回答正确或者错误，正确1，错误2
        this.setData({
          initShow: "practice",
          quesTotalInfo: res.data.data.quesTotalInfo,
          knowledgeInfo: res.data.data.knowledgeInfo,
        })
      }
    })
  },
  testClick () {
    wx.request({
      url: 'https://www.grosup.com/practice/statistics/userPaperDoneInfo.do',
      // method: 'post',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'third_session': app.globalData.userId
      },
      data: {
        userID: app.globalData.userInfoInOurSystem.personInfo.id
      },
      success: res => {
        //返回回答正确或者错误，正确1，错误2
        this.setData({
          initShow: "test",
          paperTotalInfo: res.data.data.paperTotalInfo,
          paperHaveDoneInfo: res.data.data.paperHaveDoneInfo
        })
      }
    })
  }
})