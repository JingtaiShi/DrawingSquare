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
          <el-menu-item index="homePage" class = "barContainer" >
            <span slot="title" class="itemFont">Art Gallery</span>
          </el-menu-item>
          <el-menu-item index="aiDrawing" class = "barContainer">
            <!-- <i class="el-icon-menu"></i> -->
            <span slot="title" class="itemFont">AI Drawing</span>
          </el-menu-item>
          <el-submenu index= "5" class = "barContainer">
            <template slot="title" >
              <!-- <i class="el-icon-location"></i> -->
              <span class="itemFont">Personal Info</span>
            </template>
            <el-menu-item-group>
            <el-menu-item index="libraryPage" >
                <!-- <i class="el-icon-setting"></i> -->
            <span slot="title" class="itemFont" >Personal Library</span>
            </el-menu-item>
            <el-menu-item index="setting">
                <!-- <i class="el-icon-setting"></i> -->
            <span slot="title" class="itemFont">Setting</span>
            </el-menu-item>
            <!-- <el-menu-item index="wallet"> -->
                <!-- <i class="el-icon-setting"></i> -->
            <!-- <span slot="title" class="itemFont">Wallet</span> -->
            <!-- </el-menu-item> -->
            </el-menu-item-group>
            
          </el-submenu>
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