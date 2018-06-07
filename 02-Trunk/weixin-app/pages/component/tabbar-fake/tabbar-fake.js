// pages/component/tabbar/tabbar.js
//获取应用实例
const app = getApp()
Component({
  properties: {
    // 这里定义了innerText属性，属性值可以在组件使用时指定
    innerText: {
      type: String,
      value: 'default value',
    }
  },
  data: {
    // 这里是一些组件内部数据
    someData: {},
    tabbarIndexInConp: 4,//默认是4, 激活对应的tabIndexBox的active
  },
  ready: function () {
    //检测app中的tabbarIndex, 对应本组件的data, 更新页面
    this.setData({
      tabbarIndexInConp: app.globalData.userInfoInOurSystem.tabbarIndex
    })
  },
  methods: {
    // 这里是一个自定义方法
    customMethod: function () { },
    bindRedirectTap: function (e) {
      let _this = this;
      //只有用户状态为accessed时候才可以点击
      if (app.globalData.userInfoInOurSystem.userStatus == "accessed"){
        let hrefKey = e.currentTarget.dataset.href;
        switch (hrefKey) {
          case "index":
            wx.redirectTo({
              url: "../index/index",
              success: function () {
                //修改app中的tabbarIndex
                app.globalData.userInfoInOurSystem.tabbarIndex = 1
              }
            })
            break;
          case "practice":
            wx.redirectTo({
              url: "../practice/practice",
              success: function () {
                //修改app中的tabbarIndex
                app.globalData.userInfoInOurSystem.tabbarIndex = 2
              }
            })
            break;
          case "test":
            wx.redirectTo({
              url: "../test/test",
              success: function () {
                //修改app中的tabbarIndex
                app.globalData.userInfoInOurSystem.tabbarIndex = 3
              }
            })
            break;
          case "my":
            wx.redirectTo({
              url: "../my/my",
              success: function () {
                //修改app中的tabbarIndex
                app.globalData.userInfoInOurSystem.tabbarIndex = 4
              }
            })
            break;
        }
        
      }
      
      
    }
  }
})