<template>
    <div>
        <el-row>
            <el-col :span="24" class="navBar">
                <div class="titleNameContainer">
                    <p class="titleName">Painting Plaza</p>
                </div>
            </el-col>
            <!-- <el-col :span="14"> 
            <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal">
                <el-menu-item index="1">
                <router-link to="/homepage">
                    <span>首页</span>
                </router-link>
                </el-menu-item>
                <el-menu-item index="2">
                <router-link to="/composevideo">
                    <span>合成视频</span>
                </router-link>
                </el-menu-item>
                <el-menu-item index="3" disabled>
                <router-link to="/">
                    <span>视频圈</span>
                </router-link>
                </el-menu-item>
                <el-menu-item index="4">
                <router-link to="/useguide">
                    <span>使用指南</span>
                </router-link>
                </el-menu-item>
                <el-menu-item index="5">
                <router-link to="/personalcenter">
                    <span>个人中心</span>
                </router-link>
                </el-menu-item>
            </el-menu>
            </el-col> -->
        </el-row>
    
    </div>
    
  </template>


<script>
export default {
    name: "Header",
  data() {
    return {

    }
  },
  mounted:function(){
    this.$nextTick(() => {
        new Promise((resolve, reject)=>{
            setTimeout(()=>{
                resolve("success")
            },10);

        }).then(()=>{
            
        })
        
    })
  },
  created:function(){
   
  },
  methods:{
    logout(){
        this.$router.push({name:'imageView'})
    }

    
  },
}

</script>


<style>
@import url('https://fonts.googleapis.com/css2?family=Megrim&display=swap');

.navBar{
    background-color:#333333 ;
    height:70px;
    position: fixed;
    z-index:10;
}
.titleNameContainer{
    float:left;
    margin-left:4vh;
    font-size: 36px;
    
}

.titleName{
    font-size: 100%;
    color:aliceblue;
    font-family: Megrim;
    /* margin:0 5% 0 5%; */
    margin:5% 0 5% 0;
}
</style>
   

   
  <style scoped>
  /*由a来控制router-link*/
  a {
    text-decoration: none;
    color: #000000;
    font-family: sans-serif;
    font-size: 14px;
  }
  </style>