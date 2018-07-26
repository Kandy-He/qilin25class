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
    testArray: []
  },
  attached: function () {
    wx.request({
      url: 'https://www.grosup.com/practice/testPaper/queryList.do',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'third_session': app.globalData.userId
      },
      data: {
        gradeID: app.globalData.userInfoInOurSystem.personInfo.classID
      },
      success: res => {
        //二年级所有测试
        this.setData({
          testArray: res.data.data
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
    

  }
})
