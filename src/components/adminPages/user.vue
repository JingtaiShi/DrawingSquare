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
        <el-form-item label="UserName">
          <el-input v-model="parms.username"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" @click="searchBtn">Search</el-button>
          <el-button style="color: #ff7670" @click="resetBtn" icon="el-icon-close">Reset</el-button>
          <el-button type="primary" icon="el-icon-plus" @click="addBtn">Add New</el-button>
        </el-form-item>
      </el-form>
      <!-- 用户表格
      :data= 是表格的数据
  
      el-table-column 中的  prop需要跟返回的字段对应
      label 字段的名称
       -->
      <el-table :height="tableHeight" :data="tableList">
        <el-table-column prop="username" label="UserName"></el-table-column>
        <el-table-column prop="phone" label="Phone"></el-table-column>
        <el-table-column prop="email" label="Email Address"></el-table-column>
        <el-table-column prop="money" label="Money"></el-table-column>
        <el-table-column width="200" label="Operation">
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
  
      <!-- 新增弹框 -->
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
            <el-form-item prop="phone" label="Phone">
              <el-input v-model="addModel.phone"></el-input>
            </el-form-item>
            <el-form-item prop="email" label="Email">
              <el-input v-model="addModel.email"></el-input>
            </el-form-item>
            <el-form-item prop="username" label="Account">
              <el-input v-model="addModel.username"></el-input>
            </el-form-item>
            <el-form-item prop="password" label="Password">
              <el-input v-model="addModel.password"></el-input>
            </el-form-item>
            <el-form-item prop="money" label="Money">
              <el-input v-model="addModel.money"></el-input>
            </el-form-item>
          </el-form>
        </template>
      </sys-dialog>
    </el-main>
  </template>
  
  <script>
  import SysDialog from "@/components/SysDialog.vue";
  import { addApi, getListApi,editApi ,deleteApi} from "@/api/user";
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
  
        //新增表单的验证规则
        rules: {
          phone: [
            {
              trigger: "change",
              message: "Please enter the phone number",
              required: true,
            },
          ],
          email: [
            {
              trigger: "change",
              message: "Please enter the email",
              required: true,
            },
          ],
          username: [
            {
              trigger: "change",
              message: "Please enter the username",
              required: true,
            },
          ],
          password: [
            {
              trigger: "change",
              message: "Please enter the password",
              required: true,
            },
          ],
        },
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
        //弹框属性
        addDialog: {
          title: "",
          height: 180,
          width: 650,
          visible: false,
        },
        parms: {
          username: '',
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
        this.parms.username = '';
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
          let res = await deleteApi({userId:row.userId})
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
  