// pages/component/page-practice/page-practice.js
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
    practiceArray:[]
  },
  attached: function () {
    wx.request({
      url: 'https://www.grosup.com/practice/type/detail.do',
      // method: 'post',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'third_session': app.globalData.userId
      },
      data: {
        userID: app.globalData.userInfoInOurSystem.personInfo.id
      },
      success: res => {
        let gradeSubjectItemMessage = res.data.data
        //拿出二年级数学科目下的所有习题
        this.setData({
          practiceArray: gradeSubjectItemMessage[0].subjects[0].typeDetails
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
      //点击题目类型的id
      let clickedQuesionId = e.target.dataset.typeid
      //点击题目的名称
      let clickedQuesionName = e.target.dataset.typename
      wx.navigateTo({
        url: '../../pages/questions/questions?typeid=' + clickedQuesionId + '&typename=' + clickedQuesionName
      })
    },
    //点击错题列表
    wrongBookTap: function (e) {
      //点击题目类型的id
      let clickedQuesionId = e.target.dataset.typeid
      //点击题目的名称
      let clickedQuesionName = e.target.dataset.typename
      wx.navigateTo({
        url: '../../pages/wrongbook/wrongbook?typeid=' + clickedQuesionId + '&typename=' + clickedQuesionName
      })
    }

  }
})
