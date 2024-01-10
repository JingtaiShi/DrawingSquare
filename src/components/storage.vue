<template>
    <el-container>
        <div style="width:100%">
            <el-row style="width:100%; margin-top: 2vh;">
                <el-col :span="22" :offset="1">
                    <p style="float:left; font-size: 20px;">{{title}}</p>
                </el-col>
            </el-row>
            <div style="width:100%; overflow:auto;" class="infinite-list-wrapper">
                <el-row :gutter="1" style="margin-left:2%; margin-right:2%;">
                    <el-col :span="6" >
                      <ul
                        class="list"
                        v-infinite-scroll="load"
                        infinite-scroll-disabled="disabled"
                        style="margin-block-start: 1em; margin-block-end: 1em; padding-inline-start: 20px; padding-inline-end: 20px;">
                        <li v-for="img in imgList" class="list-item">
                            <div id="test1" style="width:100%;" >
                              <el-image :src="require('../assets/'+img)" style="margin-bottom: 20px;" @click="watchImg(img)"></el-image>
                            </div>
                        </li>
                        </ul>
                    </el-col>
                    <el-col :span='6' >
                      <ul
                        class="list"
                        v-infinite-scroll="load"
                        infinite-scroll-disabled="disabled"
                        style="margin-block-start: 1em; margin-block-end: 1em; padding-inline-start: 20px; padding-inline-end: 20px;">
                        <li v-for="img in imgList" class="list-item">
                            <div id="test1" style="width:100%;" >
                              <el-image :src="require('../assets/'+img)" style="margin-bottom: 20px;" @click="watchImg(img)" ></el-image>
                            </div>
                        </li>
                        </ul>
                    </el-col>
                    <el-col :span='6' >
                      <ul
                        class="list"
                        v-infinite-scroll="load"
                        infinite-scroll-disabled="disabled"
                        style="margin-block-start: 1em; margin-block-end: 1em; padding-inline-start: 20px; padding-inline-end: 20px;">
                        <li v-for="img in imgList" class="list-item">
                            <div id="test1" style="width:100%;" >
                              <el-image :src="require('../assets/'+img)" style="margin-bottom: 20px;" @click="watchImg(img)" ></el-image>
                            </div>
                        </li>
                        </ul>
                    </el-col>
                    <el-col :span='6' >
                        <ul
                        class="list"
                        v-infinite-scroll="load"
                        infinite-scroll-disabled="disabled"
                        style="margin-block-start: 1em; margin-block-end: 1em; padding-inline-start: 20px; padding-inline-end: 20px;">
                        <li v-for="img in imgList" class="list-item">
                            <div id="test1" style="width:100%;" >
                              <el-image :src="require('../assets/'+img)" style="margin-bottom: 20px;" @click="watchImg(img)" ></el-image>
                            </div>
                        </li>
                        </ul>
                    </el-col>
                </el-row>
            </div>
            <p v-if="loading">加载中...</p>
            <p v-if="noMore">没有更多了</p>

        </div>

    </el-container> 

</template>

<script>
export default {
  data() {
    return {
      pageIndex:'',
      title:'',
      imgList:[],
      
    }
  },
  mounted:function(){
    this.getParams();
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
    login() {
        this.$router.push({name:'homePage'});

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
    moveToStorage(){
        this.$router.push({name:'Storage', params:{index}});
    },
    handleHover(){
      //TODO 悬浮删除
    },
    getParams(){
        this.pageIndex = this.$route.params.id
        if(this.pageIndex == 1){
            this.title = 'Gernate History'
            this.imgList = ["pexels-juan-alvarado-18113138.jpg", 'pexels-fahri-baghirov-16646278.jpg', 'pexels-juan-alvarado-18113138.jpg', 'pexels-allan-feitor-7555440.jpg', 'pexels-augusto-baldera-15716620.jpg', 
      'pexels-avery-arwood-18412974.jpg', 'pexels-bayram-musayev-15261266.jpg', 'pexels-cao-huy-16690835.jpg', 'pexels-elif-dörtdoğan-17750245.jpg', 'pexels-esmir-bilali-16884746.jpg',
    'pexels-fatih-maraşlıoğlu-14093969.jpg', 'pexels-hatice-baran-13107422.jpg', 'pexels-laura-decramer-17409183.jpg', 'pexels-manish-jangid-18024374.jpg' ]
    
        }
        else if(this.pageIndex == 2){
            this.title = 'Loved Painting'
        }
        else if (this.pageIndex == 3){
            this.title = 'Purchased Painting'
        }
        else{
            this.tile = 'WRONG!'
        }
    },
    watchImg(url){
        console.log(url);
        this.$router.push({name:'imageView', params:{src: url}});
    },
  },

}

</script>