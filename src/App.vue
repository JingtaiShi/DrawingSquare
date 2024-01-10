<template>
  <div id="app">
    <div v-if="$route.meta.admin">
      <header-nav></header-nav>
      <el-container
      style="height: 100vh; 
      border: 1px solid #eee; 
      margin: 0;
      border: 0;
      padding: 0;
      position:relative;
      margin-top: 70px;">
      <adminSideNav style="width:270px; position:fixed;"></adminSideNav>
      <router-view style="width:85vw; margin-left: 310.5px; background-color: #f4f4f4; height:85vh;"></router-view>
      </el-container>
      
    </div>
    <div v-if="$route.meta.keepAlive &&  !$route.meta.admin">
      <header-nav></header-nav>
      <el-container
      style="height: 100vh; 
      border: 1px solid #eee; 
      margin: 0;
      border: 0;
      padding: 0;
      position:relative;
      margin-top: 70px;">
      <sideNav style="width:270px; position:fixed;"></sideNav>
      <router-view style="width:85vw; margin-left: 310.5px; background-color: #f4f4f4;"></router-view>
      </el-container>
    </div>
    <div v-if="!$route.meta.keepAlive && !$route.meta.admin">
      <header-nav></header-nav>
      <router-view></router-view>
    </div>
    
  </div>
</template>

<script>
import header from '@/components/headerNav';
import side from '@/components/sideNav';
import adminSide from '@/components/adminPages/adminSideNav';

export default {
  name: 'App',
  components:{
    headerNav: header,
    sideNav: side,
    adminSideNav:adminSide,
  },
  data:{
    userId:'',
    money:'',
  }
  

}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /* margin-top: 60px; */
}
</style>
