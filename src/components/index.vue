<template>
    <el-container>
        <div style="width:100%">
            <el-row style="width:100%; margin-top: 2vh;">
                <el-col :span="10" :offset="1">
                    <p style="float:left; font-size: 20px;">Art Gallery</p>
                </el-col>
                <el-col :span='11' >
                    <el-input
                    placeholder="Search"
                    suffix-icon="el-icon-search"
                    v-model="search"
                    style=" margin-top:1vh;width:60%;"></el-input>
                    <el-button style="float:right; margin-top:1vh; margin-left: 1vw;" @click="resetBtn">Reset</el-button>
                    <el-button style="float:right; margin-top:1vh" @click="searchBtn">Search</el-button>
                    
                </el-col>
            </el-row>
            <div style="width:100%; overflow:auto;" class="infinite-list-wrapper">
                <el-row :gutter="1" style="margin-left:2%; margin-right:2%; ">
                    <el-col :span="6" >
                      <ul
                        class="list"
                        v-infinite-scroll="load"
                        infinite-scroll-disabled="disabled"
                        style="margin-block-start: 1em; margin-block-end: 1em; padding-inline-start: 20px; padding-inline-end: 20px;">
                        <li v-for="(img, index) in imgList1" class="list-item" :key="index">
                            <div id="test1" style="width:100%;" >
                              <el-image :src="img.url" style="margin-bottom: 20px;" @click="watchImg(img.url, img.paintingId)"></el-image>
                            </div>
                        </li>
                        </ul>
                    </el-col>
                    <el-col :span="6" >
                      <ul
                        class="list"
                        v-infinite-scroll="load"
                        infinite-scroll-disabled="disabled"
                        style="margin-block-start: 1em; margin-block-end: 1em; padding-inline-start: 20px; padding-inline-end: 20px;">
                        <li v-for="(img, index) in imgList2" class="list-item" :key="index">
                            <div id="test1" style="width:100%;" >
                              <el-image :src="img.url" style="margin-bottom: 20px;" @click="watchImg(img.url, img.paintingId)"></el-image>
                            </div>
                        </li>
                        </ul>
                    </el-col>
                    <el-col :span="6" >
                      <ul
                        class="list"
                        v-infinite-scroll="load"
                        infinite-scroll-disabled="disabled"
                        style="margin-block-start: 1em; margin-block-end: 1em; padding-inline-start: 20px; padding-inline-end: 20px;">
                        <li v-for="(img, index) in imgList3" class="list-item" :key="index">
                            <div id="test1" style="width:100%;" >
                              <el-image :src="img.url" style="margin-bottom: 20px;" @click="watchImg(img.url, img.paintingId)"></el-image>
                            </div>
                        </li>
                        </ul>
                    </el-col>
                    <el-col :span="6" >
                      <ul
                        class="list"
                        v-infinite-scroll="load"
                        infinite-scroll-disabled="disabled"
                        style="margin-block-start: 1em; margin-block-end: 1em; padding-inline-start: 20px; padding-inline-end: 20px;">
                        <li v-for="(img, index) in imgList4" class="list-item" :key="index">
                            <div id="test1" style="width:100%;" >
                              <el-image :src="img.url" style="margin-bottom: 20px;" @click="watchImg(img.url, img.paintingId)"></el-image>
                            </div>
                        </li>
                        </ul>
                    </el-col>
                    
                </el-row>
            </div>
            <p v-if="loading">loading...</p>
            <p v-if="noMore">end</p>

        </div>

    </el-container> 

</template>

<script>
import {getGallaryApi, searchApi} from "@/api/painting";
export default {
  data() {
    return {
        search:'',
        count:10,
        loading:false,
        // imgTargets:[this.imgSrc,this.imgSrc2,this.imgSrc3,this.imgSrc4,this.imgSrc5,this.imgSrc6,this.imgSrc7,this.imgSrc8,this.imgSrc9,this.imgSrc10,this.imgSrc11,this.imgSrc12,this.imgSrc13,this.imgSrc14]
        // imgTargets:[example,example2, example3, example4, example5, example6, example7, example8, example9, example10, example11, example12, example13, example14],
        imgList1:[],
        imgList2:[],
        imgList3:[],
        imgList4:[],

    }
  },
  mounted:function(){
    this.$nextTick(() => {
        new Promise((resolve, reject)=>{
            setTimeout(()=>{
                resolve("success")
            },10);

        }).then(()=>{
            this.getGallary();
        })
        
    })
  },
  computed: {
      noMore () {
        return this.count >= 20
      },
      disabled () {
        return this.loading || this.noMore
      }
     },
  methods:{
    resetBtn() {
      this.getGallary();
      this.search = '';
    },
    async searchBtn () {
      let res = await searchApi({tag : this.search});
      this.imgList1 = res.data.data[0];
      this.imgList2 = res.data.data[1];
      this.imgList3 = res.data.data[2];
      this.imgList4 = res.data.data[3];
    },
    async getGallary () {
      let res = await getGallaryApi();
      console.log("res= " + res.data.data[0][0].url);
      this.imgList1 = res.data.data[0];
      console.log("list1= " + this.imgList1[0].url);
      this.imgList2 = res.data.data[1];
      this.imgList3 = res.data.data[2];
      this.imgList4 = res.data.data[3];
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
    watchImg(url, paintId){
        console.log(url);
        this.$router.push({name:'galleryImage', params:{src: url, paintingId: paintId}});
    },
  }
}



</script>

<style>
.el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
  ul{
    list-style:none;
    padding-inline-start: 0px;
    margin-block-start: 0px;
    margin-block-end: 0px;
  }

</style>
