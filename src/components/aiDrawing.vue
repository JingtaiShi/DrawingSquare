<template>
    <div class="aiContainer">
        <p style="font-size: 36px; grid-row-start: 2; grid-row-end: 3; grid-column-start: 2; grid-column-end: 5; text-align: center;">AI Drawing</p>
        <el-form
        :model="parms"
        ref="searchForm"
        label-width="80px"
        :inline="true"
        size="small"
        style="grid-row-start: 4; grid-row-end: 5; grid-column-start: 2; grid-column-end: 5; display: flex;">
            <el-form-item >
            <el-input v-model="parms.text"  style="width:30vw"></el-input>
            </el-form-item>
            <el-form-item > 
                <el-select v-model="parms.style" filterable placeholder="Style">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
      </el-form>
      <el-button type="primary"  style="grid-row-start: 7; grid-row-end: 8; grid-column-start: 2; grid-column-end: 5; height:40px;" @click="generate">Generte</el-button>
    </div>

</template>

<script>
import { submitRequest, getImgUrl} from "@/api/aiArt";
export default {
  data() {
    return {
        //src 由前页面带入 还有id username等用户信息需要根据图片id进数据库拉取
        parms:{
            text:'',
            style:'',
            resolution:'1024*1536',
            num:1,
        },
        options: [{
          value: '探索无限',
          label: 'Explore Infinity'
        }, {
          value: '古风',
          label: 'Antiquity'
        }, {
          value: '二次元',
          label: 'Comics'
        }, {
          value: '写实风格',
          label: 'Realistic'
        }, {
          value: '浮世绘',
          label: 'Ukiyo-e'
        }, {
          value: 'low poly',
          label: 'low poly'
        }, {
          value: '未来主义',
          label: 'Futurism'
        }, {
          value: '像素风格',
          label: 'Pixelated'
        }, {
          value: '概念艺术',
          label: 'Conceptual Art'
        }, {
          value: '赛博朋克',
          label: 'Cyberpunk'
        }, {
          value: '洛丽塔风格',
          label: 'Lolita'
        }, {
          value: '巴洛克风格',
          label: 'Baroque'
        }, {
          value: '超现实主义',
          label: 'Surrealism'
        }, {
          value: '水彩画',
          label: 'Aquarelle'
        }, {
          value: '蒸汽波艺术',
          label: 'Vaporwave'
        }, {
          value: '油画',
          label: 'Oil Painting'
        }, {
          value: '卡通画',
          label: 'Cartoon Painting'
        },],
        value: '',
        taskId:'',
        imgSrc:'',
        imgStyle:'',
        imgText:'',


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
            
        })
        
    })
  },
  created:function(){
   
  },
  methods:{
    load () {
        this.loading = true
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
    checkFree(){
        if (this.price > 0){
            this.free = 'Buy this pring'
        }
        else{
            this.free = 'Download'
        }
    },
    dealWithGalleryBtn(){
        // TODO 处理付款还是下载图片
    },
    async getTaskId(){
        let res = await submitRequest(this.parms);
        console.log("Response:", res)
        //给表格数据赋值
        this.taskId = res.data.data.taskId;
        console.log("task ID:", this.taskId);
    },
    async getImgUrl(){
        let res = await getImgUrl();
        console.log("Response:", res)
        this.imgStyle = res.data.data.style;
        this.imgText = res.data.data.text;  
        this.imgSrc = res.data.data.img;
        console.log("imgSrc:", this.imgSrc)
    },
    async generate(){
        await this.getTaskId()
        await this.getImgUrl()
        this.$router.push({name:'imageView', params:{src: this.imgSrc, style:this.imgStyle, text:this.imgText, id:1}});
    }
    
  },


}

</script>




<style>
.aiContainer{
    display: grid;
    grid-template-rows: 11vh 11vh 11vh 11vh 11vh 11vh 11vh 11vh 11vh ;
    grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
    justify-items: center;
}

</style>