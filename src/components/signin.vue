<template>
    <el-col :span="24">
        <div class="login_container">
            <!-- 登录盒子-->
            <div class="signin_box">


                <div class="loginTitle">
                    <p>Sign In</p>
                </div>
                <div style="padding-top:1%; padding-right:80px">
                <el-form ref = "form" :modle="form" label-width="80px">
                    <el-form-item class = "inputBox">
                        <el-input v-model="account" placeholder="Account"></el-input>
                    </el-form-item>

                    <el-form-item class = "inputBox">
                        <el-input v-model="password" show-password placeholder="Password"></el-input>
                    </el-form-item>
                    <el-form-item class = "inputBox">
                        <el-input v-model="userName" placeholder="User Name"></el-input>
                    </el-form-item>
                    <el-form-item class = "inputBox">
                        <div>
                            <el-date-picker
                            v-model="dob"
                            type="date"
                            placeholder="选择日期"
                            value-format="yyyy-MM-dd"
                            style="width:100%">
                            </el-date-picker>
                        </div>
                    </el-form-item>
                    
                    <el-form-item class = "inputBox">
                        <el-input v-model="phone" placeholder="Phone Number"></el-input>
                    </el-form-item>
                    <el-form-item class = "inputBox">
                        <el-input v-model="email" placeholder="Email Address"></el-input>
                    </el-form-item>
                </el-form>
                
                </div>
                <el-row>
                    <el-col span='16' :offset="4">
                        <el-button type="primary" style="width:50%" @click = "signin">Sign In</el-button>
                    </el-col>


                </el-row>
                <el-button plain @click="open" style="display: none;"></el-button>


 
        </div>
        </div>
    </el-col>
</template>

<script>
export default {
  data() {
    return {
        account: '',
        password: '',
        userName:'',
        phone:'',
        email:'',
        dob:'',
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
          shortcuts: [{
            text: 'today',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, {
            text: 'yesterday',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: 'aweek',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', date);
            }
          }]
        },
      };
  },
  methods:{
    check(input) {
        const reg = /^\s*$/g
        if(reg.test(input)){
            return false;
        }
        else{return true;}
    },
    signin() {
        //此处需要存入数据库

        if(this.check(this.account)&& this.check(this.password)&& this.check(this.userName)&& this.check(this.dob) && this.check(this.phone) && this.check(this.email)){
            console.log('corrent')
            this.$router.push({name:'homePage'});
        }
        else{
            console.log('wrong')
            this.open();
        }  
    },
    open() {
        this.$notify.error({
          title: 'Wrong!',
          message: 'The input can not be empty!'
        });
    },
    
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

.signin_box{
 position:relative;
 top:200px;
 width: 450px;
 height: 600px;
 background-color: #fff;
 border-radius: 10px;
 position: absolute;
 left: 50%;
 top: 50%;
 transform: translate(-50%,-50%);
 opacity: 0.95;
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
