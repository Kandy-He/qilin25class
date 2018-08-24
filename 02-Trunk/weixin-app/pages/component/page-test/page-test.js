// pages/component/page-test/page-test.js
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
    /**
     * {
     * doneRight:8//作对了多少道题
     * gradeID:1
     * isDone:"Y"//用户是否做过
     * paperKey:"Add-Ex-A-Grade2"
     * paperName:"100以内加法测试卷A"
     * quesCount:10//总题数
     * timeLimit:30//限时
     * totalScore:100//总分
     * useTime:1//用户耗时
     * userScore:0//用户得分
     * }
     */
    testArray: []

  },
  ready: function () {
    wx.request({
      url: 'https://www.grosup.com/practice/testPaper/queryList.do',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'third_session': app.globalData.userId
      },
      data: {
        gradeID: app.globalData.userInfoInOurSystem.personInfo.classID,
        userID: app.globalData.userInfoInOurSystem.personInfo.id,
      },
      success: res => {
        
        let unDone = res.data.data.unDone
        //将做过的题目状态遍历到测试题目中
        let userHaveDoneArr = res.data.data.userHaveDone
        //遍历所有做过的题目，通过paperkey找到对应的unDone数组，同步对应状态
        for (let i = 0, userHaveDoneArrLen = userHaveDoneArr.length; i < userHaveDoneArrLen ; i ++) {
          for (let j = 0, unDoneLen = unDone.length; j < unDoneLen; j++) {
            if (userHaveDoneArr[i].paperKey == unDone[j].paperKey) {
              unDone[j].isDone = userHaveDoneArr[i].isDone
              unDone[j].doneRight = userHaveDoneArr[i].doneRight
              unDone[j].useTime = userHaveDoneArr[i].useTime
              unDone[j].userScore = userHaveDoneArr[i].userScore
            }
          }
        }
        //二年级所有测试
        this.setData({
          testArray: unDone
        })
      }
    })
  },
  /**
   * 组件的方法列表
   */
  methods: {
    //点击开始练习
    startPracticeTap: function (e) {
      //试题关键字
      let clickedPaperKey = e.target.dataset.paperkey
      //点击试题的名称
      let clickedTestName = e.target.dataset.papername
      //题目总数
      let quesCount = e.target.dataset.quescount
      //题目限时
      let timeLimit = e.target.dataset.timelimit
      wx.navigateTo({
        url: '../../pages/testPage/testPage?paperKey=' + clickedPaperKey + '&paperName=' + clickedTestName + '&quesCount=' + quesCount + '&timeLimit=' + timeLimit
      })
    },
    //点击错题列表
    startWrongBookTap: function (e) {
      //点击测试key
      let paperkey = e.target.dataset.paperkey
      //点击测试的题目
      let papername = e.target.dataset.papername
      wx.navigateTo({
        url: '../../pages/paperWrongBook/paperWrongBook?paperkey=' + paperkey + '&papername=' + papername
      })
    }
    

  }
})
