// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App.vue'
import router from './router/index.js'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import { Message } from 'element-ui';

import restForm from './utils/resetForm'
Vue.prototype.$resetForm = restForm

// 信息提示
import myconfirm from './utils/myconfirm'
Vue.prototype.$myconfirm = myconfirm

// 对象的快速复制
import objCoppy from './utils/objCoppy'
Vue.prototype.$objCoppy = objCoppy

// import VueAwesomeSwiper from 'vue-awesome-swiper'

// import VueAwesomeSwiper from 'vue-awesome-swiper'
// // import 'swiper/dist/css/swiper.css'
// Vue.use(VueAwesomeSwiper)


Vue.use(router);
// Vue.use(VueAwesomeSwiper)
Vue.use(ElementUI);
Vue.config.productionTip = false
Vue.prototype.$message = Message;

/* eslint-disable no-new */
// new Vue({
//   el: '#app',
//   router,
//   components: { App },
//   template: '<App/>'
// })

new Vue({
  el: '#app',
  router,
  data:{
    userId:'',
  },
  render: h => h(App)
})

