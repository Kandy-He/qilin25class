// pages/questions/questions.js
let util = require("../../utils/util");
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    /**
     * 来自上一个页面
     */
    paperName: "",//测试卷名称
    paperKey: "",//测试题关键字
    
    quesCount: "",//一共多少道题
    timeLimit: "",//限时多长时间
    timeLeft: "",//剩余多长时间

    howToAnswer: "",//答题规范
    showExpression1: false,//是否展示第一步
    showExpression2: false,//是否展示第二步
    showExpression3: false,//是否展示第三步
    showAnswerDesc: false,//是否展示答题描述
    
    questionBodyArray: [],//题目主干数组  questionBodyArray: ["100分=", "(?)","角"]
    expression1: "",//填写的表达式1
    expression2: "",//填写的表达式2
    expression3: "",//填写的表达式3
    answerDesc: [],//答题描述的主干数组
    answer: [],//用户填写的答案,用数组保存。eg:[undefined,4,undefined,6,undefined], 提交之前再拼成 "4;6"提交

    quesPosition: 1,//默认从哪一道题开始

    inputDisabled: false,//input输入状态，当点击提交时候修改为true,其他按钮点击则为false
    answerStatus: -1,//回答状态：-1未回答，0答题错误，1答题正确

    
    //是否作过
    isDone: "N",
    //问题答案
    quesScore: "",
    userAnswer: "",
    // 如果此题已经做过
    /**
     * 引入错题页展示之前答案的逻辑
     */
    wrongAnswerArray: [],

    //没有提交测试吗？
    unsubmit: true,
    totalScore: "",
    quesCount: "",
    quesCountRight: ""
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //同步顶部标签
    this.setData({
      paperName: options.paperName,
      paperKey: options.paperKey,
      //题目总数
      quesCount: options.quesCount,
      //时间限制
      timeLimit: options.timeLimit,
    })
    //开始倒计时
    let time = options.timeLimit * 60;//30分钟换算成1800秒
    let timer = setInterval(() => {
      if (time < 0){
        clearInterval(timer)
        //提交试卷
        this.bindSendSubmitPaper()
      }else{
        time = time - 1;
        var minute = parseInt(time / 60);
        var second = parseInt(time % 60);
        this.setData({
          timeLeft: '还剩' + minute + '分' + second + '秒'
        })
      }
    }, 1000);
    //测试开始时候调用的接口
    this.userTestStart()
    
  },
  userTestStart () {
    //调用开始测试接口
    wx.request({
      url: 'https://www.grosup.com/practice/testPaper/userTestStartTime.do',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'third_session': app.globalData.userId
      },
      data: {
        userID: app.globalData.userInfoInOurSystem.personInfo.id,
        paperKey: this.data.paperKey
      },
      success: res => {
        //调用本组件共用方法
        this.queryQuesBodyForTest()
      }
    })
  },
  //本页面共用方法-请求测试题主体
  queryQuesBodyForTest() {
    this.setData({
      //初始化数据
      questionBodyArray: [],//题目主干数组
      expression1: "",//填写的表达式1
      expression2: "",//填写的表达式2
      expression3: "",//填写的表达式3
      answerDesc: [],//答题描述的主干数组
      answer: [],//填写的答案,用数组保存。eg:[undefined,4,undefined,6,undefined], 提交之前再拼成 "4;6"提交
      //错题答案数组也要清空
      wrongAnswerArray: [],
    })
    //请求知识点主体
    wx.request({
      url: 'https://www.grosup.com/practice/testPaper/getQuesByPosition.do',
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
        //题目展示配置对象
        let configStepsObj = util.quesTypeKeyFilter(questionDetail)
        //题目主体显示数组
        let questionBodyArray = util.formatQuestionContent(questionDetail.description)
        //答题描述显示数组
        let answerDesc = util.formatQuestionContent(questionDetail.answerDesc)
        this.setData({
          quesTypeKey: questionDetail.quesTypeKey,
          problemKey: questionDetail.problemKey,
          quesScore: questionDetail.quesScore,

          questionBodyArray: questionBodyArray,
          answerDesc: answerDesc,
          howToAnswer: configStepsObj.howToAnswer || "",//答题规范
          showExpression1: configStepsObj.showExpression1 || "",//是否展示第一步
          showExpression2: configStepsObj.showExpression2 || "",//是否展示第二步
          showExpression3: configStepsObj.showExpression3 || "",//是否展示第三步
          showAnswerDesc: configStepsObj.showAnswerDesc || "",//是否展示答题描述
        })
        // 判断此题是否做过
        if (questionDetail.isDone == "N"){ //没做过
          //保存此题没做过，页面渲染提交按钮
          this.setData({
            isDone: questionDetail.isDone
          })
        } else if (questionDetail.isDone == "Y") {
          //保存此题做过，页面渲染确定修改按钮
          this.setData({
            isDone: questionDetail.isDone,
            userAnswer: questionDetail.userAnswer,
            expression1: questionDetail.expression1,//填写的表达式1
            expression2: questionDetail.expression2,//填写的表达式2
            expression3: questionDetail.expression3,//填写的表达式3
          })
          //此处判断答题框是在题干中还是答题描述中
          if (questionBodyArray.length > 1) {//在题干中
            this.setData({
              //跟普通题的展示不一样,再带个错误答案进去  将原本["100-20=","(?)"] ==> ["100-20=", "80"]  80是错误答案中拿出来的
              wrongAnswerArray: util.formatWrongbookArray(questionBodyArray, questionDetail.userAnswer)
            })
          } else {
            this.setData({
              wrongAnswerArray: util.formatWrongbookArray(answerDesc, questionDetail.userAnswer)
            })
          }
        }
      }
    })
  },
  //点击提交或者确认修改
  bindSubmitclick () {
    //计算题，应用题统一传参格式
    let sendData = {
      userID: app.globalData.userInfoInOurSystem.personInfo.id,
      isDone: this.data.isDone,
      paperKey: this.data.paperKey,
      problemKey: this.data.problemKey,
      quesScore: this.data.quesScore,
      quesPosition: this.data.quesPosition,
      userAnswer: util.formatAnswerToRightStyle(util.turnArrayToAnswerStr(this.data.answer)) || this.data.userAnswer,//主题目答案,如果用户没有输入就查看他之前错题答案
      expression1: util.formatExpressionToRightStyle(this.data.expression1),//分步1答案
      expression2: util.formatExpressionToRightStyle(this.data.expression2),//分步2答案
      expression3: util.formatExpressionToRightStyle(this.data.expression3),//分步3答案
    }
    //验证答案是否正确
    wx.request({
      url: 'https://www.grosup.com/practice/testPaper/checkQues.do',
      method: 'post',
      header: {
        'content-type': 'application/json',
        'third_session': app.globalData.userId
      },
      data: sendData,
      success: res => {
        //污染数据，注掉
        // this.setData({
        //   isDone: "Y"
        // })
      }
    })
  },
  //点击两个切换题按钮
  bindTurnQues (e) {
    this.bindSubmitclick()
    //点击的是上一题还是下一题
    let testQuesTurnKey = e.target.dataset.testquesturn
    /**
    * 将quesPosition修改为指定题
    */
    let quesPositionNow = testQuesTurnKey == "last" ? this.data.quesPosition - 1 : this.data.quesPosition + 1
    // 判断当前题是否合法
    if (quesPositionNow == 0) {
      wx.showToast({
        title: '已经是第一道题了',
        icon: "none"
      })
    } else if (quesPositionNow > this.data.quesCount) {
      wx.showToast({
        title: '已经是最后道题了',
        icon: "none"
      })
    }else{//positon合法
      this.setData({
        //输入答案清零，重置题目信息
        quesPosition: quesPositionNow
      })
      //调用本组件共用方法
      this.queryQuesBodyForTest()
    }
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
    let quesItem = e.target.dataset.quessteps
    //修改对应答案
    this.setData({
      [quesItem]: e.detail.value
    })
  },
  //点击返回检查
  bindbackToRecheck () {
    this.setData({
      quesPosition: 1
    })
    //重新请求题目主体
    this.queryQuesBodyForTest()
  },
  //点击交卷
  bindSendSubmitPaper () {
    //计算题，应用题统一传参格式
    let sendData = {
      userID: app.globalData.userInfoInOurSystem.personInfo.id,
      paperKey: this.data.paperKey,
      useTime: this.data.timeLimit - parseInt(this.data.timeLeft.slice(2,4)),
    }
    //验证答案是否正确
    wx.request({
      url: 'https://www.grosup.com/practice/testPaper/paperSubmit.do',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'third_session': app.globalData.userId
      },
      data: sendData,
      success: res => {
        let backData = res.data.data
        this.setData({
          unsubmit: false,
          totalScore: backData.userScore,
          quesCount: backData.quesCount,
          quesCountRight: backData.doneRight
        })
      }
    })
  }
})