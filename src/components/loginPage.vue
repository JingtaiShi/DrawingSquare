<template>
    <el-col :span="24">
        <div class="login_container">
            <!-- 登录盒子-->
            <div class="login_box">


                <div class="loginTitle">
                    <p>Login</p>
                </div>
                <div style="padding-top:1%; padding-right:80px">
                <el-form ref = "form" :modle="form" label-width="80px">
                    <el-form-item class = "inputBox">
                        <el-input v-model="parms.username" placeholder="Account"></el-input>
                    </el-form-item>

                    <el-form-item class = "inputBox">
                        <el-input v-model="parms.password" placeholder="Password" show-password></el-input>
                    </el-form-item>
                </el-form>
                
                </div>
                <el-row>

                    <el-col span='8' :offset="4">
                        <el-button type="primary" style="width:50%" @click = "login">Login</el-button>
                    </el-col>
                    <el-col span='8' >
                        <el-button type="primary" @click = "adminLogin">Admin</el-button>
                    </el-col>

                </el-row>

 
        </div>
        </div>
    </el-col>
</template>

<script>
import {loginApi} from "@/api/user";
import {loginAdminApi} from "@/api/admin";
export default {
  data() {
    return {
        parms: {
            username: '',
            password: ''
        }
    }
  },
  methods:{
    async login() {
        console.log(this.username);
        console.log(this.password);
        let res = await loginApi(this.parms);
        if(res && res.data.code == 200){
            console.log("登陆成功")
            this.$router.userId = res.data.data.userId;
            this.$router.money = res.data.data.money;
            console.log(this.$router.userId)
            console.log(this.$router.money)
            this.$router.push({name:'homePage'});
            
        }
    },
    async adminLogin() {
        console.log("username =" + this.username);
        console.log("password =" + this.password);
        let res = await loginAdminApi(this.parms);
        if(res && res.data.code == 200){
            console.log("登陆成功")
            this.$router.userId = res.data.data;
            console.log(this.$router.userId)
            this.$router.push({name:'user'});  
        }
    }
  }
}
</script>

<style>
.loginTitle{
    margin-top: 10%;
    margin-bottom: 0%;
    font-size: 48px;
}
.login_container{
   position: relative;
   height: 100vh;
   margin: 0px;
   padding: 0px;
   /* background-image: url(assets/bg.jpg);
   background-size: cover; */
   background-color: antiquewhite;
}

.inputBox{
    margin-bottom: 10%;
}

.el-form-item__content{
    margin-left: 0px;
}

.login_box{
 position:relative;
 top:200px;
 width: 450px;
 height: 400px;
 background-color: #fff;
 border-radius: 10px;
 position: absolute;
 left: 50%;
 top: 50%;
 transform: translate(-50%,-50%);
 opacity: 0.95;
 text-align: center;
}

/* .img_box{
  width: 130px;
  height: 130px;
  position: absolute;
  left: 50%;
  transform: translate(-50%,-50%);
  background-color: #fff;
  border-radius: 50%;
  padding: 5px;
  border: 1px solid #eee;
} */

/* .img_box img{
    width: 100%;
    height: 100%;
    border-radius: 50%;
    background-color: #eee;
} */

</style>
