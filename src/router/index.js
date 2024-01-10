import Vue from 'vue'
import router from 'vue-router'

import Login from '../components/loginPage.vue'
import Home from '../components/index.vue'
import Library from '../components/personalLibrary.vue'
import storage from '../components/storage.vue'
import imageView from '../components/imgView.vue'
import setting from '../components/setting.vue'
import settingEdit from '../components/settingEdit.vue'
import signin from '../components/signin.vue'
import wallet from '../components/wallet.vue'
import galleryImage from '../components/galleryImage.vue'
import galleryImageCopy from '../components/galleryImageCopy.vue'
import user from '../components/adminPages/user.vue'
import adminGallery from '../components/adminPages/adminGallery.vue'
import aiDrawing from '../components/aiDrawing.vue'


Vue.use(router)
const originalPush = router.prototype.push
router.prototype.push = function push(location) {
	return originalPush.call(this, location).catch(err => err)
}


/* 定义组件路由 */
var rout = new router({
  routes: [
  {
  //文件路径
  path:'/',
  name:'LoginPage',
  component: Login,
  meta:{
    keepAlive: false,
    requiresAuth:false,
  }
  },
  {
    path:'/index',
    name:'homePage',
    component: Home,
    meta:{
      keepAlive: true,
      requiresAuth:false,
    }
  },
  {
    path:'/personalLibrary',
    name:"libraryPage",
    component:Library,
    meta:{
      keepAlive: true,
      requiresAuth:true,
    }
  },
  {
    path:'/storage',
    name:'storage',
    component:storage,
    meta:{
        keepAlive:true,
        requiresAuth:true,
    }
  },
  {
    path:'/imgView',
    name:'imageView',
    component:imageView,
    meta:{
        keepAlive:true,
        requiresAuth:true,
    }
  },
  {
    path:'/setting',
    name:'setting',
    component:setting,
    meta:{
      keepAlive:true,
      requiresAuth:true,
    }
  },
  {
    path:'/settingEdit',
    name:'settingEdit',
    component:settingEdit,
    meta:{
      keepAlive:true,
      requiresAuth:true,
    }
  },
  {
    path:'/signin',
    name:'signin',
    component:signin,
    meta:{
      keepAlive: false,
      requiresAuth:true,
    }
  },
  {
    path:'/wallet',
    name:'wallet',
    component:wallet,
    meta:{
      keepAlive:true,
      requiresAuth:true,
    }
  },
  {
    path:'/galleryImage',
    name:'galleryImage',
    component:galleryImage,
    meta:{
      keepAlive:true,
      requiresAuth:true,
    }
  },
  {
    path:'/galleryImageCopy',
    name:'galleryImageCopy',
    component:galleryImageCopy,
    meta:{
      keepAlive:true,
      requiresAuth:true,
    }
  },
  {
    path:'/user',
    name:'user',
    component:user,
    meta:{
      keepAlive:false,
      admin:true,
      requiresAuth:true,
    }
  },
  {
    path:'/adminGallery',
    name:'adminGallery',
    component:adminGallery,
    meta:{
      keepAlive:false,
      admin:true,
      requiresAuth:true,
    }
  },
  {
    path:'/aiDrawing',
    name:'aiDrawing',
    component:aiDrawing,
    meta:{
      keepAlive:true,
      requiresAuth:true,
    }
  },
 

  ]
  });

  // rout.beforeEach((to, from, next)=>{
  //   if(to.meta.requiresAuth){
  //     if(to.name !== 'login'){
  //       next('/login')
  //     }else{
  //       next()
  //     }
  //   }
  // })
  //导出路由对象
  export default rout;



 