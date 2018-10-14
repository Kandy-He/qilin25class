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
    navigatorArray: ["index", ""],//默认导航到注册页
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //实现单页面导航
    if (options && options.href) {
      this.setData({
        navigatorArray: [options.href, ""]
      })
    }
    //APP启动第一步：判断app是否授权,如果授权，再根据（未注册，审核中，被拒绝）和审核通过进入不同的页面
    wx.getSetting({
      success: res => {
        if (!res.authSetting['scope.userInfo']) {// 未授权
          wx.redirectTo({
            url: '../authorization/authorization',//跳转到授权页
          })
        } else {//已经授权，根据用户状态，判断进入哪个页面
          // 登录
          wx.login({
            success: res => {
              // 发送 res.code 到后台换取 openId, sessionKey, unionId 返回识别符，用以后面数据交互用户识别
              wx.request({
                url: 'https://www.grosup.com/practice/login/decode.do',
                method: 'post',
                header: {
                  'content-type': 'application/x-www-form-urlencoded'
                },
                data: {
                  code: res.code
                },
                success: res => {
                  //本地存储识别码
                  app.globalData.userId = res.data.third_session
                  //拿到识别码后，获取用户信息，返回用户审核状态值
                  getUsersInfo();
                },
                fail: () => {
                  console.log("请求超时")
                }
              })
            }
          })
          // 获取用户信息, 特别是用户状态
          let getUsersInfo = () => {
            wx.getUserInfo({
              success: res => {
                // 可以将 res 发送给后台解码出 unionId
                app.globalData.userInfo = res.userInfo
                //向后台查询用户开发者系统信息，以及此时状态，返回审核中，被拒绝 或 审核通过
                wx.request({
                  url: 'https://www.grosup.com/practice/user/info.do',
                  // method: 'post',
                  header: {
                    'content-type': 'application/x-www-form-urlencoded',
                    'third_session': app.globalData.userId
                  },
                  success: res => {
                    if (res.data.msg == "当前用户未注册") {
                      //未通过，显示注册页(未注册，审核中，被拒绝)
                      wx.redirectTo({
                        url: '../register/register'
                      })
                    }else{
                      let saveFilterdData = (data) => {
                        // this.globalData.userInfoInOurSystem.personInfo = res.data.data;
                        //根据用户状态进行页面的不同显示，只有当用户通过审核时候才会显示个人信息页，否则显示注册页
                        let userStatus//找到用户状态
                        let userType//格式化用户角色
                        let userRole//储存用户角色，用于审核通过状态展示对应页面
                        let selectRole //中文格式角色，用于审核时同步当前角色
                        userStatus = data.status
                        userType = data.userType
                        switch (userType) {
                          case 0:
                            userRole = "student",
                              selectRole = "学生"
                            break;
                          case 1:
                            userRole = "teacher",
                              selectRole = "老师"
                            break;
                          default:
                            userRole = "admin",
                              selectRole = "管理员"
                        }
                        data.formatUserRole = userRole
                        data.formatSelectRole = selectRole

                        return data
                      }
                      //用户个人信息格式化后储存
                      app.globalData.userInfoInOurSystem.personInfo = saveFilterdData(res.data.data)
                      if (app.globalData.userInfoInOurSystem.personInfo.status == 1) {//通过审核
                        //导航到main页
                        // wx.redirectTo({
                        //   url: '../main/main',
                        // })
                        //本页面为启动页，因此当小程序启动，判断是否展示授权或注册页时候，此页已经显示，故需要把页面先隐藏，当用户审核通过时候再展示
                        let userInfo = app.globalData.userInfoInOurSystem.personInfo
                        if (userInfo.status == 1) {
                          let navigatorArray2Value = 'navigatorArray[1]'
                          this.setData({
                            ifshow: true,
                            [navigatorArray2Value]: userInfo.formatUserRole
                            // navigatorArray: ["my", userInfo.formatUserRole]
                          })
                        }
                      } else {
                        //未通过，显示注册页(未注册，审核中，被拒绝)
                        wx.redirectTo({
                          url: '../register/register'
                        })
                      }
                    }
                    
                  },
                  fail: () => {
                    wx.showToast({
                      title: '服务器请求异常，请检查网络或联系管理员！',
                      icon: 'none'
                    })
                  }
                })
              }
            })
          }
        }
      }
    })
    
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
  },
  onShow() { //返回显示页面状态函数
    this.onLoad()//再次加载，实现返回上一页页面刷新
  }
})