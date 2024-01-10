<template>
    <el-container>
        
        <div class="galleryImageContainer">
            <p style="float:left; font-size: 36px; height:5vh;">Art Gallery</p>
            <el-image :src="this.pageIndex" style="height:60vh; grid-column-start: 1; grid-column-end: 3; grid-row-start: 2; grid-row-end: 11;;" ></el-image>
            <div style="grid-column-start: 3;grid-column-end: 4; grid-row-start: 3; grid-row-end: 4; display: flex;">
                <p style="font-size: 25px;"> Author: &nbsp; &nbsp; &nbsp; &nbsp;   </p>
                <p style="font-size: 25px;"> {{userName}} </p>
            </div>
            <div style="grid-column-start: 3;grid-column-end: 4; grid-row-start: 4; grid-row-end:5; display: flex;">
                <p style="font-size: 25px;"> Tags: &nbsp; &nbsp; &nbsp; &nbsp;   </p>
                <ul style="display:flex;">
                            <div style="width:100%; " >
                                <p style="font-size: 25px; text-align: start;">{{ tags }}</p>
                            </div>

                    </ul>
            </div>
            <div style="grid-column-start: 3;grid-column-end: 4; grid-row-start: 5; grid-row-end: 6; display: flex;">
                <p style="font-size: 25px;"> Price: &nbsp; &nbsp; &nbsp; &nbsp;   </p>
                <p style="font-size: 25px;"> {{parms.price}} </p>
                <p style="font-size: 25px;"> &nbsp; &nbsp; Golds  </p>
            </div>
            <div style="grid-column-start: 3;grid-column-end: 4; grid-row-start: 6; grid-row-end: 7; display: flex; ">
                <p style="font-size: 25px; "> Details: &nbsp; &nbsp; &nbsp; &nbsp;   </p>
                <p style="font-size: 25px; word-break: break-all; word-wrap: break-word;"> {{detail}} </p>
                
            </div>
            <div style="grid-column-start: 3;grid-column-end: 4; grid-row-start: 7; grid-row-end: 8; display: flex; position: relative;">
                <el-button type="primary" style="margin-left:7vw; height:50%;" @click="dealWithGalleryBtn">Buy the picture</el-button>
                <div style="margin-right:2vh;" slot="content">
                    <img  :src="require('../assets/like.png')"  class="likeBtnG" id="liBtn" @click="changeLikeStatus()"/>
                    <img  :src="require('../assets/liked.png')"  class="likedBtnG" id="lidBtn" @click="changeLikeStatus()"/>
                </div>

            </div>
        </div>
       
        

    </el-container> 

</template>

<script>
 import {getImgInfo, addImageToLibraryApi, likeApi, removeLikeApi} from "@/api/painting";
 import {buyApi, getUserInfo} from "@/api/user";
export default {
  data() {
    return {
        //src 由前页面带入 还有id username等用户信息需要根据图片id进数据库拉取

        imgSrc:'',
        userName:'Jellycat',
        tags:"",
        pageIndex:'',
        detail:'hsadkbfnskhabfvskajhdgbvnjkhsjfnbvkshjNbgvjnekshznbmgjksdmbgvjshkfbcwieyakujrgfcsykzhJdgmfncuiskhzjmvbfnchkeszjgfbnckshzDJgfbcsukhbfsk',
        free:'',
        imgId:'',
        liked:'1',
        parms: {
            userId:'',
            userMoney:0,
            sysUserId:'',
            sysUserMoney:0,
            price:0,
        },
        newParms: {
            sysUserId:'',
            paintingId:'',
        },
    }
  },
    mounted: function () {
    this.$nextTick(() => {
        new Promise((resolve, reject) => {
        setTimeout(() => {
            resolve("success");
        }, 10);
        }).then(() => {
        this.getParams();
        this.getAuthorDetail();
        this.ImgInfo();
        var likeImg = document.getElementById("liBtn");
        var likedImg = document.getElementById("lidBtn");

            console.log(this.liked)
            if(this.liked == 1){
                console.log(likeImg)
                    likeImg.style.display = "block";
                    likedImg.style.display = "none";
            }
            else{
                    likeImg.style.display = "none";
                    likedImg.style.display = "block";
            }
        });
    });
    },
  created:function(){
   
  },
  methods:{
    async ImgInfo() {
        console.log(this.imgId);
        let res = await getImgInfo({ paintingId: this.imgId});
        this.tags = res.data.data.tag;
        this.parms.price = res.data.data.price;
        this.parms.userId = res.data.data.userId;
        this.detail = res.data.data.description; 
        let userInfo = await getUserInfo({userId: this.parms.userId})
        // console.log("userName=" + this.userInfo.data.data.username);
        this.userName = userInfo.data.data.username;
        this.parms.userMoney = userInfo.data.data.money;
        // this.details = res.data;
        console.log(res.data);
    },
    load () {
        this.loading = true
    },
    getParams(){
        this.pageIndex =this.$route.params.src
        this.imgId = this.$route.params.paintingId
        console.log(this.pageIndex)
    },
    getAuthorDetail(){
        //根据imgid从数据库获取
    },
    move(index) {
        this.$router.push({name:'settingEdit', params:{imgUrl: this.imgSrc, usName:this.userName, date:this.dob, phoneNumber:this.phone, emd:this.emailAddress}});

    },
    changeLikeStatus(){
        

        // TODO 此处数据需要写进数据库
        var likeImg = document.getElementById("liBtn");
        var likedImg = document.getElementById("lidBtn");
        if(this.liked == 1){
           this.liked = 2;
            likeImg.style.display = "none";
            likedImg.style.display = "block";
        }
        else{
            this.liked = 1;
            likeImg.style.display = "block";
            likedImg.style.display = "none";
        }
        
    },
    async dealWithGalleryBtn(){
        this.parms.sysUserId = this.$router.userId;
        this.parms.sysUserMoney = this.$router.money;
        console.log("sysUserId=" + this.parms.sysUserId  + " sysUserMoney =" + this.parms.sysUserMoney);
        console.log("userId=" + this.parms.userId  + " userMoney =" + this.parms.userMoney);
        console.log("price=" + this.parms.price);
        //信息确认提示
        let confirm = await this.$myconfirm('Are you sure to buy this picture?');
        if (confirm) {
            if (this.parms.sysUserId == this.parms.userId) {
                this.$message.error("You can not buy your own picture!");
            } else {
                let res = await buyApi(this.parms);
                if (res && res.data.code == 200) {
                    this.newParms.paintingId = this.imgId;
                    this.newParms.sysUserId = this.parms.sysUserId;
                    await addImageToLibraryApi(this.newParms);
                    // 购买成功的消息提示
                    this.$message.success("Purchase succeed!");
                } else {
                    this.$message.error("Insufficient balance, purchase failed!");
                }
                
            }
        }
    },
    async changeLikeStatus(){

      
      this.newParms.sysUserId = this.$router.userId;
      this.newParms.paintingId = this.$route.params.paintingId;
      console.log("paintingId = " + this.newParms.paintingId);
      console.log("userId = " + this.newParms.sysUserId);
      // TODO 此处数据需要写进数据库
      

      var likeImg = document.getElementById("liBtn");
      var likedImg = document.getElementById("lidBtn");
      if(this.liked == 1){
        let res = await likeApi(this.newParms);
         this.liked = 2;

          likeImg.style.display = "none";
          likedImg.style.display = "block";
      }
      else{
        let res = await removeLikeApi(this.newParms);
          this.liked = 1;
          likeImg.style.display = "block";
          likedImg.style.display = "none";
      }
      
  },

  },

}

</script>

<style>
.galleryImageContainer{
    width:80%; 
    margin-left:8%; 
    margin-top:40px;
    display: grid;
    grid-template-columns: 400px 400px 400px;
    grid-template-rows:1fr 1fr 1fr 1fr 1fr 5fr 1fr;
    justify-items: start;
    height:70vh;
}


.likeBtnG{
    height:3vh;
    width:3vh; 
    position: absolute; 

    z-index: 10;

}
.likedBtnG{
    height:3vh;
    width:3vh; 
    position: absolute; 

    z-index: 8;
}



</style>