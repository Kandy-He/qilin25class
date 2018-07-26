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
    knowledgekey: "",//知识点关键字，获取第一题要用
    knowledgename: "",//题型名称，进入练习用

    quesTypeKey: "",//题型关键字，再来一题用
    problemKey: "",//问题关键字,提交答案用

    howToAnswer: "",//答题规范
    showExpression1: false,//是否展示第一步
    showExpression2: false,//是否展示第二步
    showExpression3: false,//是否展示第三步
    showAnswerDesc: false,//是否展示答题描述


    expression1: "",//填写的表达式1
    expression2: "",//填写的表达式2
    expression3: "",//填写的表达式3
    answer: [],//填写的答案,用数组保存。eg:[undefined,4,undefined,6,undefined], 提交之前再拼成 "4;6"提交


    // questionBodyArray: ["100分=", "(?)","角"]
    questionBodyArray: [],//题目主干数组
    answerDesc: [],//答题描述的主干数组

    inputDisabled: false,//input输入状态，当点击提交时候修改为true,其他按钮点击则为false
    answerStatus: -1,//回答状态：-1未回答，0答题错误，1答题正确
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      //同步顶部标签
      this.setData({
        knowledgename: options.knowledgename,
        knowledgekey: options.knowledgekey
      })
      //调用本组件共用方法
      this.queryQuesBody()
  },
  //本页面共用方法-请求练习题主体
  queryQuesBody() {
    //请求知识点主体
    wx.request({
      url: 'https://www.grosup.com/practice/problem/getRandomOne.do',
      // method: 'post',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'third_session': app.globalData.userId
      },
      data: {
        knowledgeKey: this.data.knowledgekey,
        quesTypeKey: this.data.quesTypeKey || "" //直接请求练习列表不需要传这个字段，如果做过一题，点击再来一题，这里就要传
      },
      success: res => {
        //返回知识点id,answer,description
        let questionDetail = res.data.data
        //题目展示配置对象
        let configStepsObj = util.quesTypeKeyFilter(questionDetail)
        this.setData({
          quesTypeKey: questionDetail.quesTypeKey,
          problemKey: questionDetail.problemKey,

          questionBodyArray: util.formatQuestionContent(questionDetail.description),
          answerDesc: util.formatQuestionContent(questionDetail.answerDesc),
          howToAnswer: configStepsObj.howToAnswer || "",//答题规范
          showExpression1: configStepsObj.showExpression1 || "",//是否展示第一步
          showExpression2: configStepsObj.showExpression2 || "",//是否展示第二步
          showExpression3: configStepsObj.showExpression3 || "",//是否展示第三步
          showAnswerDesc: configStepsObj.showAnswerDesc || "",//是否展示答题描述
        })

      }
    })
  },
  //点击提交按钮
  bindSubmitTap: function () {
    let answer = util.formatAnswerToRightStyle(util.turnArrayToAnswerStr(this.data.answer))//主题目答案
    //练习题中必须要求用户答题完整再作答
    if (answer){
      //计算题，应用题统一传参格式
      let sendData = {
        problemKey: this.data.problemKey,//问题关键字
        answer: answer,
        userID: app.globalData.userInfoInOurSystem.personInfo.id,
        expression1: util.formatExpressionToRightStyle(this.data.expression1),//分步1答案
        expression2: util.formatExpressionToRightStyle(this.data.expression2),//分步2答案
        expression3: util.formatExpressionToRightStyle(this.data.expression3),//分步3答案
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
    }else{
      wx.showToast({
        title: '请输入答案后提交',
        icon: 'none'
      })
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
  //点击再来一题
  bindAnotherQuesTap: function () {
    //本页面数据初始化
    this.setData({
      questionBodyArray: [],//题目主干数组
      expression1: "",//填写的表达式1
      expression2: "",//填写的表达式2
      expression3: "",//填写的表达式3
      answerDesc: [],//答题描述的主干数组
      answer: [],//填写的答案,用数组保存。eg:[undefined,4,undefined,6,undefined], 提交之前再拼成 "4;6"提交

      inputDisabled: false,//input输入状态，当点击提交时候修改为true,其他按钮点击则为false
      answerStatus: -1//回答状态：-1未回答，0答题错误，1答题正确
    })
    this.queryQuesBody()
  },
  //点击订正按钮
  bindResetQuesTap: function () {
    this.setData({
      //初始化回答答案
      expression1: "",//填写的表达式1
      expression2: "",//填写的表达式2
      expression3: "",//填写的表达式3
      answer: [],//填写的答案,用数组保存。eg:[undefined,4,undefined,6,undefined], 提交之前再拼成 "4;6"提交
      //更新答题状态
      answerStatus: -1,
      inputDisabled: false//不允许用户再次输入，点击订正或者再来一体才可以输入
    })
  }
})