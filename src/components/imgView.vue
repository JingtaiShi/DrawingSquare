<template>
    <el-container>
        <div style="width:100%">
            <el-row style="width:100%; margin-top: 2vh;">
                <el-col :span="22" :offset="1">
                    <p style="float:left; font-size: 20px;">{{title}}</p>
                </el-col>
            </el-row>
            <div style="margin-left: 30vw; margin-right:30vw">
                
                    <!-- <el-row justify="center">
                        <el-col :span="{imgLableSpan}" v-for="tag in imgLables">
                            <el-button type="primary" round>{{tag}}</el-button>
                        </el-col>
                    </el-row> -->
                    <ul style="display:flex; justify-content: center;">
                        <li v-for="tag in imgLables" class="list-item">
                            <div id="test1" style="width:100%;" >
                                <el-button type="primary" round>{{tag}}</el-button>
                            </div>
                        </li>
                    </ul>   
            </div>
            <div style="width:100%; position: relative;">
                <div style="margin-left:10%; margin-right:10%;">
                    <img :src=this.pageIndex style="height:60vh;" />
                    <!-- <p>{{ this.pageIndex }}</p> -->
                </div>
                <div style="margin-right:2vh;" slot="content">

                    <img  :src="require('../assets/btn-on.png')"  class="uploadedBtn" id="updBtn" @click="changeUploadStatus()"/>
                    <img  :src="require('../assets/btn-off.png')"  class="uploadBtn" id="upBtn" @click="changeUploadStatus()"/>
                    <img  :src="require('../assets/like.png')"  class="likeBtn" id="liBtn" @click="changeLikeStatus()"/>
                    <img  :src="require('../assets/liked.png')"  class="likedBtn" id="lidBtn" @click="changeLikeStatus()"/>

                </div>
            </div>
            <div style="margin-left: 30vw; margin-right:30vw; margin-top:2vw;">
                
                <div style="display:flex; justify-content:space-evenly ">
                    <!-- TODO 下载功能需要后端-->
                    <!-- <el-button class = 'imgBtn' id = 'downloadBtn'>Download</el-button> -->
                </div>
            </div>


        </div>
        <sys-dialog
        :title="addDialog.title"
        :height="addDialog.height"
        :width="addDialog.width"
        :visible="addDialog.visible"
        @onClose="onClose"
        @onConfirm="onConfirm"
      >
        <template slot="content">
          <el-form
            :model="addModel"
            ref="addForm"
            :rules="rules"
            label-width="100px"
            :inline="true"
            size="small"
          >
            <el-form-item prop="title" label="Title">
              <el-input v-model="addModel.title">{{ addModel.title }}</el-input>
            </el-form-item>
            <el-form-item prop="price" label="Price">
              <el-input v-model="addModel.price">{{ addModel.price }}</el-input>
            </el-form-item>
            <el-form-item prop="description" label="Description">
              <el-input v-model="addModel.description">{{ addModel.description }}</el-input>
            </el-form-item>
            <el-form-item label="Status">
            <el-radio-group v-model="addModel.status">
              <el-radio label="1">Display</el-radio>
              <el-radio label="0">Do not display</el-radio>
            </el-radio-group>
          </el-form-item>
          </el-form>
        </template>
      </sys-dialog>
    </el-container> 
    

</template>

<script>
 import SysDialog from "@/components/SysDialog.vue";  
 import { addImgApi, getImgInfo} from "@/api/painting";
 import {likeApi} from "@/api/painting";
export default {
components: { SysDialog },
  data() {
    return {
      pageIndex:'',
      tag:'',
      title:'',
      paintingId:'',
      //需要从数据库中调
      imgLables:['dog', 'cat'],
    //   imgLableSize:imgLables.length,
    //   imgLableSpan:24/imgLableSize,
      imgSrc:'',
      //需要获取，不是预设数据
      uploaded:1,
      liked:1,
      newParms:{
          sysUserId:'',
          paintingId:'',
      },
      addDialog: {
          title: "",
          height: 180,
          width: 650,
          visible: false,
    },
    addModel: {
        userId:"1",
        title:"",
        url:"",
        status:"1",
        price:"",
        likes:"",
        tag:"",
        editType:'',
        description:'', 
        paintingId:'',
    },
        rules: {
          title: [
            {
              trigger: "change",
              message: "Please enter the title",
              required: true,
            },
          ],
          price: [
            {
              trigger: "change",
              message: "Please enter the price",
              required: true,
            },
          ],
          description: [
            {
              trigger: "change",
              message: "Please enter the description",
              required: true,
            },
          ],
        },
      
    }
  },
  mounted:function(){
    this.getParams();
    this.$nextTick(() => {
        new Promise((resolve, reject)=>{
            setTimeout(()=>{
                resolve("success")
            },10);

        }).then(()=>{
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
            var uploadImg = document.getElementById("upBtn");
            var uploadedImg = document.getElementById("updBtn");
            if(this.uploaded == 1){

                uploadImg.style.display = "block";
                uploadedImg.style.display = "none";
            }
            else{
                uploadImg.style.display = "none";
                uploadedImg.style.display = "block";
            }
        })
        
    })
  },
  created:function(){
   
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

    getParams(){
      this.paintingId = this.$route.params.paintingId
      
      this.pageIndex =this.$route.params.src
        if(this.$route.params.id == 1){
            this.imgLables[0] = this.$route.params.style
            this.imgLables[1] = this.$route.params.text
        } else if(this.$route.params.id == 2){
          console.log("url=" + this.pageIndex);
          this.tag = this.$route.params.tag;
          console.log("tag = " + this.tag);
          let tags = this.tag.split(',');
          let tag0 = tags.length > 0 ? tags[0].trim() : null;
          let tag1 = tags.length > 1 ? tags[1].trim() : null;
            this.imgLables[0] = tag0;
            this.imgLables[1] = tag1;
        }
        console.log(this.pageIndex)
        

        
    },
    async changeLikeStatus(){
      
        this.newParms.sysUserId = this.$router.userId;
        this.newParms.paintingId = this.$route.params.paintingId;
        console.log("paintingId = " + this.newParms.paintingId);
        console.log("userId = " + this.newParms.sysUserId);
        // TODO 此处数据需要写进数据库
        let res = await likeApi(this.newParms);


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
    async changeUploadStatus(){
        // console.log(row);
        //表单的清空
        // this.$resetForm('addForm',this.addModel);
        //设置弹框属性
        if(this.paintingId != null) {
          console.log("paintingId = " + this.paintingId);
          let res = await getImgInfo({paintingId : this.paintingId});
        console.log("res = " + res);
        this.addModel.title = res.data.data.title;
        this.addModel.description = res.data.data.description;
        this.addModel.price = res.data.data.price;
        this.addModel.status = res.data.data.status;
        }

        // console.log("this.addModel.title = " + this.addModel.title);
        this.addDialog.title = 'Set Attributes';
        this.addDialog.visible = true;
        
        //设置要编辑的数据回显
        //把当前要编辑的数据设置到表单绑定的数据域
        // this.$objCoppy(row,this.addModel)
        console.log(this.addModel)
        //设置为编辑
        this.addModel.editType = "0";
        // TODO 此处数据需要写进数据库
        var uploadImg = document.getElementById("upBtn");
        var uploadedImg = document.getElementById("updBtn");
        if(this.uploaded == 1){
            this.uploaded = 2;
            uploadImg.style.display = "block";
            uploadedImg.style.display = "none";
        }
        else{
            this.uploaded = 1;
            uploadImg.style.display = "none";
            uploadedImg.style.display = "block";
        }
    },
    //确认弹框
    onConfirm() {
        //表单验证
        this.$refs.addForm.validate(async (valid) => {
          if (valid) {
            let res = null;
            this.addModel.url = this.pageIndex;
            let result = this.imgLables[0]+','+this.imgLables[1];
            this.addModel.tag = result;
            this.addModel.userId = this.$router.userId;
            console.log("url =" + this.addModel.url)
            this.addModel.paintingId = this.paintingId;
            console.log("paintingId =" + this.addModel.paintingId);
            res = await addImgApi(this.addModel);
            console.log(res);
            if (res && res.data.code == 200) {
              this.addDialog.visible = false;
            }
          }
        });
      },
      //关闭弹框
      onClose() {
        this.addDialog.visible = false;
      },
      formData(data){
        const formData = new FormData();
        for (let key in data) {
        formData.append(key, data[key]);
        }
        return formData;
    }
  },

}

</script>

<style>
.imgBtn{
    font-size:30px;
    margin-top:2vh;
}
.uploadBtn{
    height:4vh;
    width:4vh; 
    position: absolute; 
    bottom:0; 
    right:15%;
    z-index: 10;

}
.uploadedBtn{
    height:4vh;
    width:4vh; 
    position: absolute; 
    bottom:0; 
    right:15%;
    z-index: 8;

}

.likeBtn{
    height:3vh;
    width:3vh; 
    position: absolute; 
    bottom:5px; 
    right:10%;
    z-index: 10;

}
.likedBtn{
    height:3vh;
    width:3vh; 
    position: absolute; 
    bottom:5px; 
    right:10%;
    z-index: 8;
}

</style>