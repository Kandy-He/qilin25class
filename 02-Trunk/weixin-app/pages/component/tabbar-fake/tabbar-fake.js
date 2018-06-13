// pages/component/tabbar/tabbar.js
//获取应用实例
const app = getApp()
Component({
  properties: {
    // 这里定义了属性，属性值可以在组件使用时指定
  },
  data: {
    // 这里是一些组件内部数据
    tabbarIndexInConp: "my",//默认是4, 激活对应的tabIndexBox的active
  },
  ready: function () {
    
  },
  methods: {
    // 这里是一个自定义方法
    bindRedirectTap:function (e) {
      //只有用户状态为accessed时候才可以点击
      if (app.globalData.userInfoInOurSystem.personInfo.status == 1){
        let hrefKey = e.currentTarget.dataset.href;//保存路由地址
        let myEventDetail = {
          href: hrefKey
        } // detail对象，提供给事件监听函数
        let myEventOption = {
          bubbles: true,//允许冒泡
        } // 触发父级navigateTo事件，渲染到对应的页面
        //修改当前tabbar 样式
        this.setData({
          tabbarIndexInConp: hrefKey
        })
        this.triggerEvent('navigateClick', myEventDetail, myEventOption)
      }
      
      
    }
  }
})