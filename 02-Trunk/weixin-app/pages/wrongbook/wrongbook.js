// pages/wrongbook/wrongbook.js
let util = require("../../utils/util");
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: "",
    // questionBodyArray: ["100分=", "(?)","角"]
    questionBodyArray: [],//题目主干数组
    expression1: "",//填写的表达式1
    expression2: "",//填写的表达式2
    expression3: "",//填写的表达式3
    answerDesc: [],//答题描述的主干数组
    answer: [],//填写的答案,用数组保存。eg:[undefined,4,undefined,6,undefined], 提交之前再拼成 "4;6"提交
    qID: "",//题目id
    qType: "calculate",
    typeID: "",//题型id
    qType: "",//题型
    inputDisabled: false,//input输入状态，当点击提交时候修改为true,其他按钮点击则为false
    answerStatus: -1,//回答状态：-1未回答，0答题错误，1答题正确

    //错题展示
    wrongAnswer: "",//之前填的错误答案
    // wrongAnswerArray: ["100-20=", "80", ""],

    wrongAnswerArray: [],
    // wrongAnswerArray: {
    //   "0": "aaa",
    //   "1": "aaa",
    //   "2": "aaa",
    //   "3": "aaa"
    // },
    rownum: "1"//当前错题是第几题(打开默认传1，后一个传2)
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
        userID: app.globalData.userInfoInOurSystem.personInfo.id,
        rownum: this.data.rownum
      },
      success: res => {
        //返回知识点id,answer,description
        let questionDetail = res.data.data
        //根据返回的proKey判断是否是应用题
        if (questionDetail.proKey == "calculateQues") {//计算题
          let questionBodyArray = util.formatQuestionContent(questionDetail.description, questionDetail.result)
          this.setData({
            qID: questionDetail.id,
            questionBodyArray: questionBodyArray,
            qType: questionDetail.proKey,

            //将错误答案同步到页面中
            wrongAnswer: questionDetail.result
          })
          this.setData({
            //跟普通题的展示不一样,再带个错误答案进去  将原本["100-20=","(?)"] ==> ["100-20=", "80"]  80是错误答案中拿出来的
            wrongAnswerArray: util.formatWrongbookArray(questionBodyArray, questionDetail.result),
            qType: questionDetail.proKey
          })
        } else if (questionDetail.proKey == "applicationQues") {//应用题
          this.setData({
            qType: "application",
            expression1: questionDetail.expression1,
            expression2: questionDetail.expression2,
            expression3: questionDetail.expression3,
            answerDesc: util.formatQuestionContent(questionDetail.answerDesc),
            qID: questionDetail.id,
            questionBodyArray: util.formatQuestionContent(questionDetail.description),
            qType: questionDetail.proKey
          })
          debugger
        }
      }
    })
  },
  //主题目或者答题描述输入答案事件,由于答案可能有多个输入框，所以将答案按照数组储存 
  bindInputAnswer: function (e) {
    let answerItem = 'answer[' + e.target.dataset.index + ']'
    this.setData({
      [answerItem]: e.detail.value
    })
  },
  //应用题的分步输入答案
  bindInputQuesStep: function (e) {
    let quesItem = e.target.dataset.quesSteps
    //修改对应答案
    this.setData({
      [quesItem]: e.detail.value
    })
  },
  //点击提交按钮
  bindSubmitTap: function () {
    //计算题，应用题统一传参格式
    let sendData;
    sendData = {
      id: this.data.qID,
      // typeID: this.data.typeID,
      answer: util.turnArrayToAnswerStr(this.data.answer),//主题目答案
      userID: app.globalData.userInfoInOurSystem.personInfo.id,
      expression1: this.data.expression1,//分步1答案
      expression2: this.data.expression2,//分步2答案
      expression3: this.data.expression3,//分步3答案
    }
    debugger
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
          answerStatus: res.data.data,
          inputDisabled: true//不允许用户再次输入，点击订正或者再来一体才可以输入
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