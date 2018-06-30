// pages/wrongbook/wrongbook.js
let util = require("../../utils/util");
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: "",
    questionBodyArray: [],
    answer: "",//填写的答案
    qID: "",//题目id
    typeID: "",//题型id
    answerStatus: 0//回答状态：0未回答，1答题正确，2答题错误
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //同步顶部标签
    this.setData({
      title: options.typename,
      typeID: options.typeid
    })
    //请求知识点主体
    wx.request({
      url: 'https://www.grosup.com/practice/record/getOneRecord.do',
      // method: 'post',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'third_session': app.globalData.userId
      },
      data: {
        typeID: options.typeid,
        userID: app.globalData.userInfoInOurSystem.personInfo.id
      },
      success: res => {
        debugger
        //返回知识点id,answer,description
        let questionDetail = res.data.data
        this.setData({
          qID: questionDetail.id,
          questionBodyArray: util.formatQuestionContent(questionDetail.description)
        })
      }
    })
  },
  //点击提交按钮
  bindSubmitTap: function () {
    let sendData = {
      id: this.data.qID,
      typeID: this.data.typeID,
      answer: this.data.answer,
      userID: app.globalData.userInfoInOurSystem.personInfo.id
    }
    //验证答案是否正确
    wx.request({
      url: 'https://www.grosup.com/practice/problem/checkAnswer.do',
      // method: 'post',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'third_session': app.globalData.userId
      },
      data: sendData,
      success: res => {
        //返回回答正确或者错误，正确1，错误2
        this.setData({
          answerStatus: res.data.data
        })
      }
    })
  },
  //输入答案事件  
  bindInputAnswer: function (e) {
    this.setData({
      answer: e.detail.value
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})