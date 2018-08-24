// pages/component/page-index/page-index.js
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
    headImgUrl: "",
    studentName: "你",
    flowerNum: "0",
    overedNum: "0",
    // 总人数
    maxRank: "",
    //存储本来的排名，用于翻页时候向后台传不同对象
    realrank: "",
    rank: "",
    // 是否有排名
    ifHaveRank: false
  },
  attached: function () {
    //如果是学生就传空，否则传1
    let rank = app.globalData.userInfoInOurSystem.personInfo.userType == 0 ? "" : 1
    this.getRankMessage(rank)
    
  },
  /**
   * 组件的方法列表
   */
  methods: {
    turnStudentTap (e) {
      // 点击类型，last上一个，next下一个
      let turnType = e.target.dataset.turntype
      let rank = turnType == "last" ? this.data.rank - 1 : this.data.rank + 1
      if (rank > 0 && rank <= this.data.maxRank) {
        //同步当前排名
        this.setData({
          rank: rank
        })
        this.getRankMessage(rank)
      } else if (rank == 0) {
        wx.showToast({
          title: '已经是第一名了',
          icon: 'none'
        })
      }else{
        wx.showToast({
          title: '已经是最后一名了',
          icon: 'none'
        })
      }
    },
    getRankMessage (rankIndex) {
      let sendData = {//如果是老师或者管理员进来就不用传userID
        userID: rankIndex == this.data.realrank?app.globalData.userInfoInOurSystem.personInfo.id:"",
        rank: rankIndex
      }
      wx.request({
        url: 'https://www.grosup.com/practice/statistics/userRank.do',
        // method: 'post',
        header: {
          'content-type': 'application/x-www-form-urlencoded',
          'third_session': app.globalData.userId
        },
        data: sendData,
        success: res => {
          // 如果有排名
          if (res.data.code == 1) {//有排名信息
            let userGettingMessage = res.data.data
            //同步数据，修改页面
            this.setData({
              ifHaveRank: true,
              headImgUrl: userGettingMessage.icon,
              studentName: userGettingMessage.name,
              flowerNum: userGettingMessage.flower,
              maxRank: userGettingMessage.maxRank,
              rank: userGettingMessage.rank,
              realrank: userGettingMessage.rank,
              overedNum: userGettingMessage.maxRank - userGettingMessage.rank
            })
          } else if (res.data.code == 0) {//暂无排名信息
            wx.request({
              url: 'https://www.grosup.com/practice/user/info.do',
              header: {
                'content-type': 'application/x-www-form-urlencoded',
                'third_session': app.globalData.userId
              },
              success: res => {
                //二年级所有测试
                this.setData({
                  headImgUrl: res.data.data.icon,
                  studentName: res.data.data.name
                })
              }
            })
          }
        }
      })
    }
  }
})
