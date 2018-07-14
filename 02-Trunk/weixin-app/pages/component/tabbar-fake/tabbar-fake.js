// pages/component/tabbar/tabbar.js
//获取应用实例
const app = getApp()
Component({
  properties: {
    // 这里定义了属性，属性值可以在组件使用时指定
    userClickKey: {            // 属性名
      type: String,     // 类型（必填），目前接受的类型包括：String, Number, Boolean, Object, Array, null（表示任意类型）
      value: '',    // 属性初始值（可选），如果未指定则会根据类型选择一个
      observer:function (now,old) {
        this.setData({
          tabbarIndexInConp: now
        })
      }
    }
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
      
      let hrefKey = e.currentTarget.dataset.href;//保存路由地址
      let myEventDetail = {
        href: hrefKey
      } // detail对象，提供给事件监听函数
      let myEventOption = {
        bubbles: true,//允许冒泡
      }
      //在注册阶段的拦截
      // if (app.globalData.userInfoInOurSystem.personInfo.status == 1) {
        //修改当前tabbar 样式
        this.setData({
          tabbarIndexInConp: hrefKey
        })
      // }
      
      // 触发父级navigateTo事件，渲染到对应的页面
      this.triggerEvent('navigateClick', myEventDetail, myEventOption)
    }
  }
})