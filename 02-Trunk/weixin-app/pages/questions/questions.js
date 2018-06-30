// pages/questions/questions.js
let util = require("../../utils/util");
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: "",
    // questionBodyArray: ["100分=", "(?)","角"]
    questionBodyArray: [],
    answer: [],//填写的答案,用数组保存。eg:[undefined,4,undefined,6,undefined], 提交之前再拼成 "4;6"提交
    expression1: "",//填写的表达式1
    expression2: "",//填写的表达式2
    expression3: "",//填写的表达式3
    qID: "",//题目id
    typeID: "",//题型id
    qType: "",//题型
    inputDisabled: false,//input输入状态，当点击提交时候修改为true,其他按钮点击则为false
    answerStatus: -1//回答状态：-1未回答，0答题错误，1答题正确
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
      url: 'https://www.grosup.com/practice/problem/getRandomOne.do',
      // method: 'post',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'third_session': app.globalData.userId
      },
      data: {
        typeID: options.typeid
      },
      success: res => {
        //返回知识点id,answer,description
        let questionDetail = res.data.data
        //根据返回的proKey判断是否是应用题
        if (questionDetail.proKey == "calculateQues"){//计算题
          this.setData({
            qID: questionDetail.id,
            questionBodyArray: util.formatQuestionContent(questionDetail.description),
            qType: questionDetail.proKey
          })
        }else{//应用题
          this.setData({
            qID: questionDetail.id,
            questionBodyArray: util.formatQuestionContent(questionDetail.description),
            qType: questionDetail.proKey
          })
        }
        
      }
    })
  },
  //点击提交按钮
  bindSubmitTap: function () {
    let sendData;
    sendData = {
      id: this.data.qID,
      typeID: this.data.typeID,
      answer: util.turnArrayToAnswerStr(this.data.answer),
      userID: app.globalData.userInfoInOurSystem.personInfo.id,
      expression1: this.data.expression1,
      expression2: this.data.expression2,
      expression3: this.data.expression3,
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
          answerStatus: res.data.data,
          inputDisabled: true//不允许用户再次输入，点击订正或者再来一体才可以输入
        })
      }
    })
  },
  //输入答案事件,由于答案可能有多个输入框，所以将答案按照数组储存 
  bindInputAnswer: function (e) {
    let answerItem = 'answer[' + e.target.dataset.index + ']'
    this.setData({
      [answerItem]: e.detail.value
    })
  },
  //点击再来一题
  bindAnotherQuesTap: function () {
    //请求知识点主体,此处后面要复用
    wx.request({
      url: 'https://www.grosup.com/practice/problem/getRandomOne.do',
      // method: 'post',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'third_session': app.globalData.userId
      },
      data: {
        typeID: this.data.typeID
      },
      success: res => {
        //返回知识点id,answer,description
        let questionDetail = res.data.data
        this.setData({
          //更新题目id
          qID: questionDetail.id,
          //更新题目格式化数组
          questionBodyArray: util.formatQuestionContent(questionDetail.description),
          //更新答题状态
          answerStatus: -1,
          //清空答题内容
          answer: "",
          inputDisabled: false//不允许用户再次输入，点击订正或者再来一体才可以输入
        })
      }
    })
  },
  //点击订正按钮
  bindResetQuesTap: function () {
    this.setData({
      //更新答题状态
      answerStatus: -1,
      //清空答题内容
      answer: "",
      inputDisabled: false//不允许用户再次输入，点击订正或者再来一体才可以输入
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