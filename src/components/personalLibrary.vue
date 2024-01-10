<template>
    <el-container>
        <div style="width:100%">
            <el-row style="width:100%; margin-top: 2vh;">
                <el-col :span="22" :offset="2" >
                    <p style="float:left; font-size: 30px;">Personal Library</p>
                </el-col>
            </el-row>
            <el-row style="width:100%;">
                <el-col :span="10" :offset="2" >
                    <p style="float:left; font-size: 20px;">Gernate History</p>
                </el-col>
                <el-col :span="12"  >
                    <el-button type="text" style="float: right; font-size: 20px;margin-right: 5vw;" @click="move(1)">More</el-button>
                </el-col>


            </el-row>

            <el-row>
                <ul
                class="list"
                v-infinite-scroll="load"
                infinite-scroll-disabled="disabled"
                style="display: flex; margin-left:6vh">
                    <li v-for="(img, index) in gernateHistoryList" class="list-item" :key="index">
                        <div id="test1" style="width:100%;" >
                            <el-image :src=img.url style="margin-left:2vh;height:30vh;" @click="watchImg(img.url, img.paintingId, img.tag)" ></el-image>
                        </div>
                    </li>
                </ul>
            </el-row>

            <el-row style="width:100%; margin-top: 1vh;">
                <el-col :span="10" :offset="2" >
                    <p style="float:left; font-size: 20px;">Loved Painting</p>
                </el-col>
                <el-col :span="12"  >
                    <el-button type="text" style="float: right; font-size: 20px;margin-right: 5vw;" @click="move(2)">More</el-button>
                </el-col>
            </el-row>
            <el-row>
                <ul
                class="list"
                v-infinite-scroll="load"
                infinite-scroll-disabled="disabled"
                style="display: flex; margin-left:6vh">
                <li v-for="(img, index) in LovedPaintingList" class="list-item" :key="index">
                        <div id="test1" style="width:100%;" >
                            <el-image :src= img.url style="margin-left:2vh;height:30vh;" @click="GalleryImgPage1(img.url, img.paintingId)"></el-image>
                        </div>
                    </li>
                </ul>
            </el-row>
            <el-row style="width:100%; margin-top: 1vh;">
                <el-col :span="10" :offset="2" >
                    <p style="float:left; font-size: 20px;">Purcahsed Painting</p>
                </el-col>
                <el-col :span="12"  >
                    <el-button type="text" style="float: right; font-size: 20px;margin-right: 5vw;" @click="move(3)">More</el-button>
                </el-col>
            </el-row>
            <el-row>
                <ul
                class="list"
                v-infinite-scroll="load"
                infinite-scroll-disabled="disabled"
                style="display: flex; margin-left:6vh">
                    <li v-for="(img, index) in PurchasedPaintingList" class="list-item" :key="index">
                        <div id="test1" style="width:100%;" >
                            <el-image :src= img.url style="margin-left:2vh;height:30vh;" @click="GalleryImgPage(img.url, img.paintingId)"></el-image>
                        </div>
                    </li>
                </ul>
            </el-row>
            <div style="width:100%;">
                <el-row :gutter="1" style="margin-left:2%; margin-right:2%;">
                </el-row>
            </div>


        </div>

    </el-container> 

</template>

<script>
import {getHistoryListApi, getPurchasedPaintingListApi, getLikedPaintingListApi} from "@/api/painting";
export default {
  data() {
    return {
      gernateHistoryList:[],
      LovedPaintingList:[],
      PurchasedPaintingList:[]
    }
  },
  mounted:function(){
    this.$nextTick(() => {
        new Promise((resolve, reject)=>{
            setTimeout(()=>{
                resolve("success")
            },10);

        }).then(()=>{
            this.getHistoryList();
            this.getPurchasedPaintingList();
            this.getLikedPaintingList();
        })
        
    })
  },
  computed: {
        optionLeft() {
        return {
            step:0.8,
            direction: 2,// 0向下 1向上 2向左 3向右
            // limitMoveNum: this.dataList.length,// 开始无缝滚动的数据量 this.dataList.length
            hoverStop: true,
            openTouch: false,
        };
        },

     },
  methods:{
    async getLikedPaintingList() {
        let res = await getLikedPaintingListApi({userId: this.$router.userId})
        // console.log(res.data.data[0][0].url);
        this.LovedPaintingList = res.data.data;
    },
    async getPurchasedPaintingList() {
        let res = await getPurchasedPaintingListApi({userId: this.$router.userId})
        // console.log(res.data.data[0][0].url);
        this.PurchasedPaintingList = res.data.data;
    },
    async getHistoryList() {
        let res = await getHistoryListApi({userId: this.$router.userId})
        // console.log("res.data = " + res.data.data);
        this.gernateHistoryList = res.data.data;

    },
    move(index) {
        this.$router.push({name:'storage', params:{id: index}});

    },
    watchImg(url, paintingId, tag){
        console.log(url);
        this.$router.push({name:'imageView', params:{src: url, paintingId: paintingId, tag: tag, id: 2}});
    },
    GalleryImgPage1(url, paintId){
        console.log(url);
        this.$router.push({name:'galleryImage', params:{src: url, paintingId: paintId}});
    },
    GalleryImgPage(url, paintId){
        console.log(url);
        this.$router.push({name:'galleryImageCopy', params:{src: url, paintingId: paintId}});
    },
    addImg(imgUrl, divID){
        var div = document.getElementById(divID);
        var img = new Image();
        img
        var img = document.createElement("img");
        img.src = imgUrl;
        div.appendChild(img);
    },
    load () {
        this.loading = true
        // setTimeout(() => {
        //   this.count += 2
        //   this.loading = false
        // }, 2000)
        //TODO
        //此处在每次加载中时， 需要从数据库中调出新的图片数据进入列表
    },
  }
}

</script>