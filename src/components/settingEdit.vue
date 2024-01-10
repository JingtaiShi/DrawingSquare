<template>
    <el-container>
        <div  class="gridContainer">
            <p style="font-size: 36px;margin-top: 7vh; grid-column-start: 1;grid-column-end: 4; ">User Setting</p>
            <el-image :src="require('../assets/'+ imgSrc)" style="width:50px; height:50px; margin-left:1vw;"></el-image>
            <el-input
                v-model="userName"
                class = "inputContainer"
                clearable>
            </el-input>
        
            <p>Phone Number:</p>
            <el-input
                v-model="phone"
                class = "inputContainer"
                clearable>
            </el-input>
            <p>Email:</p>
            <el-input
                v-model="emailAddress"
                class = "inputContainer"
                clearable>
            </el-input>
            <p>Password:</p>
            <el-input
                v-model="password"
                class = "inputContainer"
                clearable>
            </el-input>
        </div>
        <el-button type="primary" icon="el-icon-check" style="position:absolute; right:40%; bottom: 40%;" @click="save()">Save</el-button>
        <el-button plain @click="open" style="display:none;"></el-button>
    </el-container> 

</template>

<script>
import {editApi} from "@/api/user";
export default {
  data() {
    return {
    //用户头像src，这里给了赋值(需要路径），后续从后端数据库调用,
      imgSrc:'',
      userName:'',
      password:'',
      phone:'',
      emailAddress:'',

      //新增表单绑定的数据域
      addModel: {
          userId: "",
          editType: "", //0：新增 1：编辑
          phone: "",
          email: "",
          username: "",
          password: "",
          money:'200',
        },
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
    getParams(){
        this.imgSrc =this.$route.params.imgUrl;
        this.userName =this.$route.params.usName;
        this.phone =this.$route.params.phoneNumber;
        this.emailAddress =this.$route.params.emd;
        this.password = this.$route.params.password;
        console.log(this.userName);
    },
    async save() {
        //此处需要写入数据库 这里传回setting的参数是不会被接收的因为会陷入死循环
        if(this.check(this.userName) && this.check(this.phone) && this.check(this.email) && this.check(this.password)){
            this.addModel.username = this.userName;
            this.addModel.phone = this.phone;
            this.addModel.email = this.emailAddress;
            this.addModel.userId = this.$router.userId;
            this.addModel.password = this.password;
            let res = await editApi(this.addModel);
            if (res && res.data.code == 200) {
              //信息提示
              this.$message.success(res.msg)
              this.$router.push({name:'setting', params:{imgUrl: this.imgSrc, usName:this.userName, phoneNumber:this.phone, emd:this.emailAddress, password:this.password}});
            }
        }
        else{
            this.open()
        }
        
        

    },
    check(input) {
        const reg = /^\s*$/g
        if(reg.test(input)){
            return false;
        }
        else{return true;}
    },
    open() {
        this.$notify.error({
          title: 'Wrong!',
          message: 'The input can not be empty!'
        });
    },
  },

}

</script>

<style>
.gridContainer{
    width:80%; 
    margin-left:10%; 
    margin-right:10%;
    display:inline-grid; 
    grid-template-rows:2fr 1fr 1fr 1fr 1fr;
    grid-template-columns: 2fr 7fr 2fr;
    justify-items:start;
    height:50vh;
}
.inputContainer{
    margin-left: 3vw;  
    grid-column-start: 2;
    grid-column-end: 4;
}

</style>