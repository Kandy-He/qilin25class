//app.js
App({
  onLaunch: function () {
    //APP启动第一步：判断app是否授权,如果授权，再根据（未注册，审核中，被拒绝）和审核通过进入不同的页面
    wx.getSetting({
      success: res => {
        if (!res.authSetting['scope.userInfo']) {// 未授权
          wx.redirectTo({
            url: 'pages/authorization/authorization',//跳转到授权页
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
                  this.globalData.userId = res.data.third_session
                  //拿到识别码后，获取用户信息，返回用户审核状态值
                  getUsersInfo();
                }
              })
            }
          })
          // 获取用户信息, 特别是用户状态
          let getUsersInfo = () => {
            wx.getUserInfo({
              success: res => {
                // 可以将 res 发送给后台解码出 unionId
                this.globalData.userInfo = res.userInfo
                //向后台查询用户开发者系统信息，以及此时状态，返回审核中，被拒绝 或 审核通过
                wx.request({
                  url: 'https://www.grosup.com/practice/user/info.do',
                  // method: 'post',
                  header: {
                    'content-type': 'application/x-www-form-urlencoded',
                    'third_session': this.globalData.userId
                  },
                  success: res => {
                    
                    let saveFilterdData = (data)=>{
                      // this.globalData.userInfoInOurSystem.personInfo = res.data.data;
                      //根据用户状态进行页面的不同显示，只有当用户通过审核时候才会显示个人信息页，否则显示注册页
                      let userStatus//找到用户状态
                      let userType//格式化用户角色
                      let userRole//储存用户角色，用于审核通过状态展示对应页面
                      let selectRole //中文格式角色，用于审核时同步当前角色
                      if (res.data.msg == "当前用户未注册") {
                        userStatus = "未注册"
                      } else {
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
                      }
                      data.formatUserRole = userRole
                      data.formatSelectRole = selectRole
                      return data
                    }
                    //用户个人信息格式化后储存
                    this.globalData.userInfoInOurSystem.personInfo = saveFilterdData(res.data.data)
                    if (this.globalData.userInfoInOurSystem.personInfo.status == 1) {//通过审核
                      //导航到main页
                      wx.redirectTo({
                        url: '../main/main',
                      })
                    } else {
                      //未通过，显示注册页(未注册，审核中，被拒绝)
                      wx.redirectTo({
                        url: '../register/register'
                      })
                    }
                  }
                })
              }
            })
          }
        }
      }
    })
    
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