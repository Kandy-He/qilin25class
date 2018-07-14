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

    //测试页进来
    paperKey: "",
    quesPosition: 1,
    //一共多少道题
    quesCount: "",
    //限时多长时间
    timeLimit: "",
    //是否作过
    isDone: false,
    //问题答案
    quesScore: ""
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //同步顶部标签
    this.setData({
      title: options.paperName,
      paperKey: options.paperKey,
      quesCount: options.quesCount,
      timeLimit: options.timeLimit,
    })
    //调用本组件共用方法
    this.queryQuesBodyForTest()


  },
  //本页面共用方法-请求测试题主体
  queryQuesBodyForTest() {
    //请求知识点主体
    wx.request({
      url: 'https://www.grosup.com/practice/testPaper/getQues.do',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'third_session': app.globalData.userId
      },
      data: {
        userID: app.globalData.userInfoInOurSystem.personInfo.id,
        paperKey: this.data.paperKey,
        quesPosition: this.data.quesPosition
      },
      success: res => {
        //返回知识点id,answer,description
        let questionDetail = res.data.data
        //保存此题是否做过
        this.setData({
          isDone: res.data.isDone,
          quesScore: res.data.quesScore || ""
        })
        //根据返回的proKey判断是否是应用题
        if (questionDetail.proKey == "calculateQues") {//计算题
          this.setData({
            qID: questionDetail.id,
            questionBodyArray: util.formatQuestionContent(questionDetail.description),
            qType: questionDetail.proKey
          })
        } else if (questionDetail.proKey == "applicationQues") {//应用题
          this.setData({
            qID: questionDetail.id,
            questionBodyArray: util.formatQuestionContent(questionDetail.description),
            qType: questionDetail.proKey,
            qType: "application",
            expression1: questionDetail.expression1,
            expression2: questionDetail.expression2,
            expression3: questionDetail.expression3,
            answerDesc: util.formatQuestionContent(questionDetail.answerDesc)

          })
        }

      }
    })
  },
  //点击测试提交按钮
  bindSubmitTapForTest: function () {
    //首先将quesPosition修改为下一题
    //计算题，应用题统一传参格式
    let sendData;
    sendData = {
      userID: app.globalData.userInfoInOurSystem.personInfo.id,
      isDone: this.data.isDone,
      paperKey: this.data.paperKey,
      quesID: this.data.qID,
      quesScore: this.data.quesScore,
      quesPosition: this.data.quesPosition,
      // typeID: this.data.typeID,
      userAnswer: util.turnArrayToAnswerStr(this.data.answer),//主题目答案
      expression1: this.data.expression1,//分步1答案
      expression2: this.data.expression2,//分步2答案
      expression3: this.data.expression3,//分步3答案
    }
    //验证答案是否正确
    wx.request({
      url: 'https://www.grosup.com/practice/testPaper/checkQues.do',//做下一题之前检查当前题
      method: 'post',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'third_session': app.globalData.userId
      },
      data: sendData,
      success: res => {
        //成功返回，进入下一题
        if (res.data.code != "success") {
          this.setData({
            //修改当前答题index
            quesPosition: this.data.quesPosition + 1,
            //初始化数据
            questionBodyArray: [],//题目主干数组
            expression1: "",//填写的表达式1
            expression2: "",//填写的表达式2
            expression3: "",//填写的表达式3
            answerDesc: [],//答题描述的主干数组
            answer: [],//填写的答案,用数组保存。eg:[undefined,4,undefined,6,undefined], 提交之前再拼成 "4;6"提交
            qID: "",//题目id
            qType: "calculate",
            qType: ""//题型
          })
          //调用本组件共用方法
          this.queryQuesBodyForTest()
        }
      }
    })
  },
  //主题目输入答案事件,由于答案可能有多个输入框，所以将答案按照数组储存 
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
  }
})