<template>
    <el-container>
        <div  class="gridContainer">
            <p style="font-size: 36px;margin-top: 7vh; grid-column-start: 1;grid-column-end: 3; ">User Setting</p>
            <el-button type="text" style="font-size: 20px;margin-top: 3vh;" @click="move()">Edit</el-button>
            <el-image :src="require('../assets/'+ imgSrc)" style="width:50px; height:50px; margin-left:1vw;"></el-image>
            <p style="margin-left: 3vw;  grid-column-start: 2;grid-column-end: 4;">{{ userName }}</p>
        
            <p>Phone Number:</p>
            <p style="margin-left: 3vw;  grid-column-start: 2;grid-column-end: 4;">{{ phone }}</p>
            <p>Email:</p>
            <p style="margin-left: 3vw;  grid-column-start: 2;grid-column-end: 4;"> {{ emailAddress }}</p>
            <p>Password:</p>
            <p style="margin-left: 3vw;  grid-column-start: 2;grid-column-end: 4;"> {{ password }}</p>
            <p>Wallet:</p>
            <p style="margin-left: 3vw;  grid-column-start: 2;grid-column-end: 4;"> {{ money }}</p>

        </div>

    </el-container> 
    
</template>

<script>
import { getUserInfo } from '@/api/user';
export default {
  data() {
    return {
        //用户头像src，这里给了赋值(需要路径），后续从后端数据库调用,
      imgSrc:'logo.png',
      userName:'ABO',
      phone:'0400000000',
      emailAddress:'abokj@gmail.com',
      password:'',
      money:'',
    //   imgSrc:'',
    //    userName:'',
    //   phone:'',
    //   emailAddress:'',
    }
  },
  mounted:function(){
    this.$nextTick(() => {
        new Promise((resolve, reject)=>{
            setTimeout(()=>{
                resolve("success")
            },10);

        }).then(()=>{
            this.getParams();
        })
        
    })
  },
  created:function(){
   
  },
  methods:{
    load () {
        this.loading = true
    },
    async getParams(){
        let res = await getUserInfo({userId: this.$router.userId});
        this.userName = res.data.data.username;
        this.phone = res.data.data.phone;
        this.emailAddress = res.data.data.email;
        this.password = res.data.data.password;
        this.money = res.data.data.money;
    },
    move(index) {
        this.$router.push({name:'settingEdit', params:{imgUrl: this.imgSrc, usName:this.userName, phoneNumber:this.phone, emd:this.emailAddress, password:this.password}});

    },
  },

}

</script>

<style>
.gridContainer{
    width:80%; 
    margin-left:10%; 
    display:inline-grid; 
    grid-template-rows:2fr 1fr 1fr 1fr 1fr;
    grid-template-columns: 2fr 7fr 2fr;
    justify-items:start;
    height:50vh;
}

</style>