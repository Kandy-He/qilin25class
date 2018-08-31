//app.js
App({
  onLaunch: function () {
    
    
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
  },
  globalData: {
    //是否授权
    hasAthurize: false,
    //微信自带用户信息
    userInfo: null,
    //用户id
    userId: "",
    //用户在九天系统中的信息
    userInfoInOurSystem: {
      // userStatus: -1,//用户状态：未提交审核unpush: -1，提交审核pushing: 0，审核通过accessed: 1，审核被拒refused: 2
      userRole: "",//存用户角色：student/teacher/admin
      tabbarIndex: 4,
      personInfo: {}
    },
    //封装一个数组去重的公共方法, 传入要去重数组，返回去重后的数组
    filterRepeatArray: function (array) {
      return array;
    }
  }
})