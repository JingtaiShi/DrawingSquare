<template>
    <!-- el-main是一个容器，只需要把他当做正常的div使用就可以
  :model 绑定表单的数据域
  ref 是相当于div的id
  :rules 表单的验证规则
  :inline 控制表单的展示方向
  size 表单的大小
   -->
    <el-main>
      <el-form
        :model="parms"
        ref="searchForm"
        label-width="80px"
        :inline="true"
        size="small"
      >
        <el-form-item label="Title">
          <el-input v-model="parms.title"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" @click="searchBtn">Search</el-button>
          <el-button style="color: #ff7670" @click="resetBtn" icon="el-icon-close">Reset</el-button>
        </el-form-item>
      </el-form>
      <!-- 用户表格
      :data= 是表格的数据
  
      el-table-column 中的  prop需要跟返回的字段对应
      label 字段的名称
       -->
      <el-table :height="tableHeight" :data="tableList" :scroll-x="true">
        <el-table-column prop="title" label="Title"></el-table-column>
        <el-table-column label="Image">
        <template slot-scope="scope">
      <!-- 使用 <img> 标签来显示图片 -->
        <img :src="scope.row.url" alt="Image" style="max-width: 100px; max-height: 100px;" />
        </template>
        </el-table-column>
        <el-table-column
          prop="status"
          label="Status">
          <template slot-scope="scope">
            <!-- 使用 v-if 根据 status 的值来显示不同的文本 -->
            <span v-if="scope.row.status === '1'">Display</span>
            <span v-else-if="scope.row.status === '0'">DoNotDisplay</span>
            <span v-else>Null</span>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="Price"></el-table-column>
        <el-table-column prop="tag" label="Tags"></el-table-column>
        <el-table-column prop="description" label="Description"></el-table-column>
        <el-table-column prop="likes" label="Likes"></el-table-column>
        <el-table-column width="200" label="Opertions">
          <template slot-scope="scope">
            <el-button
              icon="el-icon-edit"
              type="primary"
              size="small"
              @click="editBtn(scope.row)"
              >Edit</el-button
            >
            <el-button
              icon="el-icon-delete"
              type="danger"
              size="small"
              @click="deleteBtn(scope.row)"
              >Delete</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 
      @size-change 页容量改变时触发的事件
      @current-change 页数改变时触发的事件
      :current-page  当前第几页 
      :page-sizes 
      :page-size  没有查询几条
       :total 总条数
      -->
      <el-pagination
        @size-change="sizeChange"
        @current-change="currentChange"
        :current-page.sync="parms.currentPage"
        :page-sizes="[10, 20, 40, 80, 100]"
        :page-size="parms.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="parms.total"
        background
      >
      </el-pagination>
  
      <!-- 编辑弹框 -->
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
              <el-input v-model="addModel.title"></el-input>
            </el-form-item>
            <el-form-item prop="description" label="Description">
              <el-input v-model="addModel.description"></el-input>
            </el-form-item>
            <el-form-item prop="price" label="Price">
              <el-input v-model="addModel.price"></el-input>
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
    </el-main>
  </template>
  
  <script>
  import SysDialog from "@/components/SysDialog.vue";
  import { addApi, getListApi,editApi ,deleteApi, testApi} from "@/api/painting";
  export default {
    //注册组件
    components: { SysDialog },
    //在页面需要展示的数据，都需要在data里面显示的定义
    data() {
      return {
        //表格高度
        tableHeight: 0,
        //表格的数据
        tableList: [],

        //新增表单绑定的数据域
        addModel: {
          paintingId: "",
          editType: "", //0：新增 1：编辑
          title: "",
          price: "",
          likes: "",
          status:"",
          description: "",
        },
        //弹框属性
        addDialog: {
          title: "",
          height: 180,
          width: 650,
          visible: false,
        },
        parms: {
          title: '',
          currentPage: 1, //从第几页开始
          pageSize: 10, //每页查询的条数
          total: 0, //总条数
        },
      };
    },
    created() {
      this.getList();
    },
    mounted() {
      this.$nextTick(() => {
        this.tableHeight = window.innerHeight - 200;
      });
    },
    methods: {
      //搜索按钮
      searchBtn(){
        this.getList();
      },
      //重置按钮
      resetBtn(){
        this.parms.title = '';
        this.getList();
      },
      //页数改变时触发
      currentChange(val) {
        console.log(val);
        this.parms.currentPage = val;
        //重新获取列表
        this.getList();
      },
      //页容量改变时触发
      sizeChange(val) {
        console.log(val);
        this.parms.pageSize = val;
        this.parms.currentPage = 1;
        this.getList();
      },
      //删除按钮
      async deleteBtn(row) {
        console.log(row);
        //信息确认提示
        let confirm = await this.$myconfirm('确定删除该数据吗?')
        console.log(confirm)
        if(confirm){
          let res = await deleteApi({paintingId:row.paintingId})
          if(res && res.data.code == 200){
            //信息提示
            this.$message.success(res.msg)
            //刷新表格
            this.getList();
          }
        }
      },
      //编辑按钮
      editBtn(row) {
        console.log(row);
        //表单的清空
        this.$resetForm('addForm',this.addModel);
        //设置弹框属性
        this.addDialog.title = 'Edit user';
        this.addDialog.visible = true;
        //设置要编辑的数据回显
        //把当前要编辑的数据设置到表单绑定的数据域
        this.$objCoppy(row,this.addModel)
        console.log(this.addModel)
        //设置为编辑
        this.addModel.editType = '1'; //1:标识编辑
      },
      //获取表格列表
      async getList() {
        console.log(this.parms);
        let res = await getListApi(this.parms);
        //给表格数据赋值
        if (res && res.data.code == 200) {
            this.tableList = res.data.data.records;
            //总条数
            this.parms.total = res.data.data.total;
        }
      },
      //新增按钮
      addBtn() {
        //表单的清空
        this.$resetForm('addForm',this.addModel)
        //设置是新增还是编辑
        this.addModel.editType = "0";
        this.addDialog.title = "New user";
        this.addDialog.visible = true;
      },
      //确认弹框
      onConfirm() {
        //表单验证
        this.$refs.addForm.validate(async (valid) => {
          if (valid) {
            let res = null;
            //判断是新增还是编辑
            if (this.addModel.editType == "0") {
              //新增
              res = await addApi(this.addModel);
            }else{
              res = await editApi(this.addModel)
            }
            console.log(res);
            if (res && res.data.code == 200) {
              //信息提示
              this.$message.success(res.msg)
              //刷新列表
              this.getList();
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
  };
  </script>
  
  <style></style>
  