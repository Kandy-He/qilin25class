// pages/authorization/authorization.js
//获取应用实例
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    ifshow: false,
    warn: "点击允许小程序获取您的微信头像和昵称",
    buttonType: "primary"
  },
  onLoad: function () {
    
  },
  bindgetuserinfo: function (e) {//点击提交按钮，弹出是否允许获取用户信息
    if (e.detail.userInfo) {//点击允许，将用户信息储存
      app.globalData.userInfo = e.detail.userInfo;
      app.globalData.hasAthurize = true;
      wx.redirectTo({
        url: '../main/main?href=my',
      })
    } else {//提示信息未提交，点击重新提交
      this.setData({
        warn: "体验小程序，需点击授权",
        buttonType: "warn"
      })
    }

  }
})