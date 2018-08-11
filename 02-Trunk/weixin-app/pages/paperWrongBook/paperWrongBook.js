// pages/paperWrongBook/paperWrongBook.js
// pages/wrongbook/wrongbook.js
let util = require("../../utils/util");
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    //由上个页面传过来的知识点名称和知识点关键字
    papername: "",
    paperkey: "",
    knowledgeKey: "",

    quesTypeKey: "",
    problemKey: "",

    howToAnswer: "",//答题规范
    showExpression1: false,//是否展示第一步
    showExpression2: false,//是否展示第二步
    showExpression3: false,//是否展示第三步
    showAnswerDesc: false,//是否展示答题描述

    // questionBodyArray: ["100分=", "(?)","角"]
    questionBodyArray: [],//题目主干数组
    expression1: "",//填写的表达式1
    expression2: "",//填写的表达式2
    expression3: "",//填写的表达式3
    answerDesc: [],//答题描述的主干数组
    answer: [],//填写的答案,用数组保存。eg:[undefined,4,undefined,6,undefined], 提交之前再拼成 "4;6"提交


    inputDisabled: false,//input输入状态，当点击提交时候修改为true,其他按钮点击则为false
    answerStatus: -1,//回答状态：-1未回答，0答题错误，1答题正确

    //错题展示
    wrongAnswer: "",//之前填的错误答案
    // wrongAnswerArray: ["100-20=", "80", ""],

    wrongAnswerArray: [],
    rownum: "1",//当前错题是第几题(打开默认传1，后一个传2)
    wrongCount: "",//错题总数
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //同步顶部标签
    this.setData({
      paperkey: options.paperkey,
      papername: options.papername
    })
    this.queryQuesBodyFunction()
  },
  //请求题目主体
  queryQuesBodyFunction() {
    //初始化上一道题的数据
    this.setData({
      quesTypeKey: "",
      problemKey: "",

      //展示题目
      questionBodyArray: [],
      answerDesc: [],
      showExpression1: false,//是否展示第一步
      showExpression2: false,//是否展示第二步
      showExpression3: false,//是否展示第三步
      showAnswerDesc: false,//是否展示答题描述
      //同步用户答案
      userAnswer: "",
      expression1: "",//填写的表达式1
      expression2: "",//填写的表达式2
      expression3: "",//填写的表达式3
    })
    //请求知识点主体
    wx.request({
      url: 'https://www.grosup.com/practice/testPaper/getOneRecord.do',
      // method: 'post',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'third_session': app.globalData.userId
      },
      data: {
        paperKey: this.data.paperkey,
        userID: app.globalData.userInfoInOurSystem.personInfo.id,
        rownum: this.data.rownum
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
        // let questionBodyArray = util.formatQuestionContent(questionDetail.description, questionDetail.result)
        this.setData({
          quesTypeKey: questionDetail.quesTypeKey,
          problemKey: questionDetail.problemKey,
          knowledgeKey: questionDetail.knowledgeKey,

          wrongCount: res.data.wrongCount,//当前错题集一起有多少道错题
          //展示题目
          questionBodyArray: questionBodyArray,
          answerDesc: answerDesc,
          howToAnswer: configStepsObj.howToAnswer || "",//答题规范
          showExpression1: configStepsObj.showExpression1 || "",//是否展示第一步
          showExpression2: configStepsObj.showExpression2 || "",//是否展示第二步
          showExpression3: configStepsObj.showExpression3 || "",//是否展示第三步
          showAnswerDesc: configStepsObj.showAnswerDesc || "",//是否展示答题描述
          //同步用户答案
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
  //点击订正按钮
  bindSubmitTap: function () {
    let sendData = {
      paperKey: this.data.paperkey,
      problemKey: this.data.problemKey,
      //如果用户没有修改主题干或者答题描述的答案，将原来他的答案发到后台
      answer: this.data.answer.length == 0 ? this.data.userAnswer : util.formatAnswerToRightStyle(util.turnArrayToAnswerStr(this.data.answer)),//主题目答案
      userID: app.globalData.userInfoInOurSystem.personInfo.id,
      expression1: util.formatExpressionToRightStyle(this.data.expression1),//分步1答案
      expression2: util.formatExpressionToRightStyle(this.data.expression2),//分步2答案
      expression3: util.formatExpressionToRightStyle(this.data.expression3),//分步3答案
    }
    //验证答案是否正确
    wx.request({
      url: 'https://www.grosup.com/practice/testPaper/checkAnswer.do',
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
  //点击修改
  bindModifiedAgainTap() {
    this.setData({
      //更新答题状态
      answerStatus: -1,
      inputDisabled: false//允许用户再次输入
    })
  },
  // 点击上一题按钮
  bindLastQuesTap() {
    let rownum = parseInt(this.data.rownum) - 1
    if (rownum < 1) {
      wx.showToast({
        title: '已经是第一道错题了',
        icon: "none"
      })
    } else {
      this.setData({
        //更新当前答题数
        rownum: rownum,
        //进入下一题之后，恢复答题状态
        answerStatus: -1
      })
      this.queryQuesBodyFunction()
    }

  },
  // 点击下一题按钮
  bindNextQuesTap() {
    let rownum = parseInt(this.data.rownum) + 1
    if (rownum > this.data.wrongCount) {
      wx.showToast({
        title: '已经是最后一道错题了',
        icon: "none"
      })
    } else {
      this.setData({
        //更新当前答题数
        rownum: rownum,
        //进入下一题之后，恢复答题状态
        answerStatus: -1
      })
      this.queryQuesBodyFunction()
    }

  },
  // 点击再来一题
  bindAnotherQuesTap() {
    //路由到练习页，传入制定的题型
    wx.navigateTo({
      url: '../questions/questions?knowledgekey=' + this.data.knowledgeKey + '&knowledgename=' + this.data.papername,
    })
  },
  // 点击移除错题
  removeWrongQues() {
    wx.request({
      url: 'https://www.grosup.com/practice/testPaper/removeRecord.do',
      // method: 'post',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'third_session': app.globalData.userId
      },
      data: {
        //用户id
        userID: app.globalData.userInfoInOurSystem.personInfo.id,
        // 题目id
        problemKey: this.data.problemKey,
        paperKey: this.data.paperkey,
      },
      success: res => {
        debugger
        if (res.data.code == 1) {
          wx.showToast({
            title: '错题已移除',
            icon: "none"
          })
        }
      }
    })
  }
})