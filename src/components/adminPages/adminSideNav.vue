<template>
    <el-row class="tac">
      <el-col :span="24" style="margin-left:15%">
        <el-menu
          default-active="1"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
          @select="handleSelect"
          background-color="#fff"
          text-color="#000000"
          active-text-color="#ffd04b"
          id = "sidebar"
          :style="{height:(screenSize*0.9)+'px'}">
          <el-menu-item index="adminGallery" class = "barContainer" >
            <span slot="title" class="itemFont">Gallery</span>
          </el-menu-item>
          <el-menu-item index="user" class = "barContainer">
            <!-- <i class="el-icon-menu"></i> -->
            <span slot="title" class="itemFont">User</span>
          </el-menu-item>
          <el-menu-item index="LoginPage" class = "barContainer">
            <!-- <i class="el-icon-setting"></i> -->
            <span slot="title" class="itemFont">Log Out</span>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-row>
    
    </template>
    
    <style>
    #sidebar{
        padding-top:2vh;
        font-size:20px;
    
    }
    
    .barContainer{
        float:left;
        width:100%;

    }
    .el-submenu{
        margin-left: 15%;
        margin-right:5%;
    }
    
    .el-submenu__icon-arrow{
        position:absolute;
        right:0px;
    }
    
    .ul{
        padding-inline-start: 0px;
    }
    .itemFont{
      font-size: 20px;
      float: left;
    }
    .el-row{
      box-sizing: content-box;
    }
    
    
    </style>
    
    <script>
      export default {
        methods: {
          handleOpen(key, keyPath) {
            console.log(key, keyPath);
          },
          handleClose(key, keyPath) {
            console.log(key, keyPath);
          },
          handleSelect(path){
            this.$router.push({name: path}, ()=>{}, ()=>{});
          },
        },
        data() {
          return{
            screenSize:null
          }
    
        },
        mounted(){
          this.screenSize = document.body.clientHeight
     
          window.onresize = () => {
              return (() => {
                this.screenSize = document.body.clientHeight
              })()
          }
        },
        
    
      }
    </script>