<template>
    <el-container>
        <div  class="walletGridContainer">
            <p style="font-size: 36px;margin-top: 7vh; grid-column-start: 1;grid-column-end: 4; ">Wallet</p>
            
            <el-image :src="require('../assets/'+ imgSrc)" style="width:50px; height:50px; margin-left:1vw;"></el-image>
            <p style="margin-left: 3vw;  grid-column-start: 2;grid-column-end: 4;">{{ userName }}</p>
        
            <p style="margin-left:2vw">  Gold:</p>
            <p style="margin-left: 3vw;  grid-column-start: 2;grid-column-end: 4;">{{ gold }}</p>
            <el-popover
                placement="right"
                width="400"
                trigger="click">
                <el-table :data="gridData">
                    <el-table-column width="150" property="date" label="日期"></el-table-column>
                    <el-table-column width="100" property="name" label="姓名"></el-table-column>
                    <el-table-column width="300" property="address" label="地址"></el-table-column>
                </el-table>
                <el-button type="text" style=" grid-column-start: 1;grid-column-end: 4;" slot="reference">Transcation History</el-button> 
            </el-popover>
                            
            <div style="display: flex; grid-column-start: 1;grid-column-end: 4; justify-items: center; height:60%">
                <el-button type="primary" @click="topUp">Top up</el-button>
                <el-button type="primary" style="margin-left: 2vw;" @click="withdraw">Withdraw</el-button> 
            </div>
        </div>

    </el-container> 

</template>

<script>
export default {
  data() {
    return {
        //用户头像src，这里给了赋值(需要路径），后续从后端数据库调用,
      imgSrc:'logo.png',
      userName:'ABO',
      gold:'40',
      gridData: [{
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }],
    //   imgSrc:'',
    //    userName:'',
    //   dob:'',
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
    getParams(){
        //此处需要获取数据库，所以不会从edit里接受数据 暂时用现有数据
    },
    move(index) {
        this.$router.push({name:'settingEdit', params:{imgUrl: this.imgSrc, usName:this.userName, date:this.dob, phoneNumber:this.phone, emd:this.emailAddress}});

    },
    open() {
        this.$alert('这是一段内容', 'Transcation History', {
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${ action }`
            });
          }
        });
    },
    topUp(){
        //todo 充值 
        console.log('充值')
    },
    withdraw(){
        //todo 提现
        console.log('提现')
    },
  },

}

</script>

<style>
.walletGridContainer{
    width:80%; 
    margin-left:10%; 
    display:inline-grid; 
    grid-template-rows:3fr 1fr 1fr 1fr 1fr;
    grid-template-columns: 1fr 7fr 1fr;
    justify-items:start;
    height:50vh;
}

</style>