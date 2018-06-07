//app.js
App({
  onLaunch: function () {
    //APP启动第一步：判断app是否授权
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {// 已经授权，直接进入主页
          this.globalData.hasAthurize = true
          wx.redirectTo({
            url: '../my/my',//进入个人首页
          })
        } else {
          wx.redirectTo({
            url: '../authorization/authorization',//跳转到授权页
          })
        }
      }
    })
    
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
  },
  globalData: {
    hasAthurize: false,
    userInfo: null,
    userId: "",
    userInfoInOurSystem: {
      userStatus: -1,//用户状态：未提交审核unpush: -1，提交审核pushing: 0，审核通过accessed: 1，审核被拒refused: 2
      userRole: "",//存用户角色：student/teacher/admin
      tabbarIndex: 4
    },
    //封装一个数组去重的公共方法, 传入要去重数组，返回去重后的数组
    filterRepeatArray: function (array) {
      return array;
    }
  }
})