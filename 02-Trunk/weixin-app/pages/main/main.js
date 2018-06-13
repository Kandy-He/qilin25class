// pages/main/main.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    ifshow: false,
    /**
     * 单页面应用最主要的导航数组配置
     * navitatorArray: ["my","teacher"]
     * 启动->我的:my
     *          ->管理员页:admin
     *          ->老师页:teacher
     *          ->学生页:student
     *     ->测试:test
     *     ->联系:practice
     *     ->首页:index
     */
    navigatorArray: ["my", ""],//默认导航到注册页
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //本页面为启动页，因此当小程序启动，判断是否展示授权或注册页时候，此页已经显示，故需要把页面先隐藏，当用户审核通过时候再展示
    let userInfo = app.globalData.userInfoInOurSystem.personInfo
    if (userInfo.status == 1){
      this.setData({
        ifshow: true,
        navigatorArray: ["my", userInfo.formatUserRole]
      })
    }
  },
  navigateTo: function (e) {
    let href = e.detail.href
    //点击分为四种情况，如果点击我的需要做单独的设置
    if(href == "my"){
      this.setData({
        navigatorArray: ["my", app.globalData.userInfoInOurSystem.personInfo.formatUserRole]
      })
    }else{
      this.setData({
        navigatorArray: [href]
      })
    }
  }
})