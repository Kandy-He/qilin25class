// pages/component/page-my-admin/page-my-admin.js
const app = getApp()
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },
  // 生命周期函数，可以为函数，或一个在methods段中定义的方法名
  attached: function () {
    //小程序中拿到管理员信息
    let adminInfo = app.globalData.userInfoInOurSystem.personInfo;
    // 当前组件展示时候触发，请求老师信息
    wx.request({
      url: 'https://www.grosup.com//practice/user/teachers.do',
      // method: 'post',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      data: {},
      success: res => {
        //拿到返回老师信息数组,setData更新页面
        this.setData({
          teacherListArray: res.data
        })
      },
      fail: () => {
        wx.showToast({
          title: '服务器请求异常，请检查网络或联系管理员！',
          icon: 'none'
        })
      }
    })
  },
  /**
   * 组件的初始数据
   */
  data: {
    //管理员角色下老师列表
    teacherListArray: []
  },

  /**
   * 组件的方法列表
   */
  methods: {
    clickAgree: function (e) {
      //点击老师的id
      let clickedTeacherId = e.target.dataset.id
      //点击的老师在老师列表中的位置
      let clickedTeacherItemindex = e.target.dataset.itemindex

      // 发起同意请求
      wx.request({
        url: 'https://www.grosup.com/practice/user/check.do',
        method: 'POST',
        header: {
          'content-type': 'application/x-www-form-urlencoded',
          'third_session': app.globalData.userId
        },
        data: {
          userID: clickedTeacherId
        },
        success: res => {
          //当前老师状态为审核通过，同步页面
          let teacherItemStatus = 'teacherListArray[' + clickedTeacherItemindex + '].status'
          this.setData({
            [teacherItemStatus]: 1
          })
        },
        fail: () => {
          wx.showToast({
            title: '服务器请求异常，请检查网络或联系管理员！',
            icon: 'none'
          })
        }
      })
    },
    clickDisagree: function (e) {
      //点击老师的id
      let clickedTeacherId = e.target.dataset.id
      //点击的老师在老师列表中的位置
      let clickedTeacherItemindex = e.target.dataset.itemindex

      // 发起同意请求
      wx.request({
        url: 'https://www.grosup.com/practice/user/checkRefused.do',
        method: 'POST',
        header: {
          'content-type': 'application/x-www-form-urlencoded',
          'third_session': app.globalData.userId
        },
        data: {
          userID: clickedTeacherId
        },
        success: res => {
          //当前老师状态为审核拒绝，同步页面
          //删除有点坑，先保存老师数组，然后移除单前项
          let teacherListArray = this.data.teacherListArray
          teacherListArray.splice(clickedTeacherItemindex, 1)
          this.setData({
            teacherListArray: teacherListArray
          })

        },
        fail: () => {
          wx.showToast({
            title: '服务器请求异常，请检查网络或联系管理员！',
            icon: 'none'
          })
        }
      })
    },
    clickEdit() {
      wx.navigateTo({
        url: '../../pages/register/register?resetMessage=teacher'
      })
    }
  }
})
