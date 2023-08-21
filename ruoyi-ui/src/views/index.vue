<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col  :span="12" class="card-box">
        <el-card shadow="hover">
          <div slot="header" class="flex flex-justify-between align-items-center">
            <h4>待办事项</h4>
            <el-button style="padding: 0" type="text" icon="el-icon-plus" @click="handleAddtodo" > 添加待办 </el-button>
          </div>
          <div style="height:  35vh">
            <el-table
              height="100%"
              v-loading="loading.add"
              :data="todosList" size="medium">
              <el-table-column label="状态" prop="todoStatus" header-align="center" width="100" align="center">
                <template #default="{row}">
                  <el-tag type="info" v-if="row.todoStatus == 0">待办</el-tag>
                  <el-tag type="success" v-if="row.todoStatus == 1">已办</el-tag>
                  <el-tag type="warning" v-if="row.todoStatus == 2">延期</el-tag>
                  <el-tag  type="danger" v-if="row.todoStatus == 3">作废</el-tag>
                  <el-tag  type="danger" v-if="row.recentFlag == 1" class="mt5">临近截止时间</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="截止时间" prop="todoTime" header-align="center" width="180" align="center"></el-table-column>
              <el-table-column label="事项" prop="todoThing"  show-overflow-tooltip></el-table-column>
              <el-table-column  width="230"  header-align="center" align="center">
                <template #default="{row,$index}">
                    <el-button class="mr5 el-text-warning" slot="reference" size="mini"  type="text" icon="el-icon-edit" @click="handleUpdateTodo(row)"> 修改 </el-button>
                    <el-popconfirm
                      placement="top-start"
                      confirm-button-text='确定'
                      cancel-button-text='取消'
                      icon="el-icon-info"
                      @confirm="handleUpdate(row)"
                      title="确认已完成？"
                    >
                      <el-button  class="mr5" slot="reference" size="mini"  type="text" icon="el-icon-check"> 完成 </el-button>
                    </el-popconfirm>
                    <el-dropdown  @command="handleCommand($event,row)">
                      <el-button class="mr5" type="text" size="mini">更多</el-button>
                      <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item  command="delay" >延期</el-dropdown-item>
                        <el-dropdown-item  command="abolish" >作废</el-dropdown-item>
                      </el-dropdown-menu>
                    </el-dropdown>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <pagination
            :background="false"
            v-show="total.todo>0"
            :total="total.todo"
            layout="prev, pager, next"
            :page.sync="todoQueryParams.pageNum"
            :limit.sync="todoQueryParams.pageSize"
            :page-sizes="[5,8,10,15]"
            @pagination="getTodoList"
          />
        </el-card>
      </el-col>

      <el-col :span="12" class="card-box">
        <el-card shadow="hover">
          <div slot="header" class="flex">
            <span>已办事项</span>
          </div>
          <div style="height:  35vh">
            <el-table
              v-loading="loading.update"
              height="100%"
              :cell-style="{height: '49px'}"
              :data="compList" size="medium">
              <el-table-column label="状态" prop="todoStatus" header-align="center" width="100" align="center">
                <template #default="{row}">
                  <el-tag type="info" v-if="row.todoStatus == 0">待办</el-tag>
                  <el-tag type="success" v-if="row.todoStatus == 1">已办</el-tag>
                  <el-tag type="warning" v-if="row.todoStatus == 2">延期</el-tag>
                  <el-tag  type="danger" v-if="row.todoStatus == 3">作废</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="截止时间" prop="todoTime" width="180"></el-table-column>
              <el-table-column label="事项" prop="todoThing"  show-overflow-tooltip ></el-table-column>
              <el-table-column label="完成时间" prop="completeTime"  show-overflow-tooltip ></el-table-column>
              <el-table-column label="作废原因" prop="reason"  show-overflow-tooltip ></el-table-column>
            </el-table>
          </div>
          <pagination
            :background="false"
            v-show="total.comp>0"
            :total="total.comp"
            size="mini"
            layout="prev, pager, next"
            :page.sync="compQueryParams.pageNum"
            :page-sizes="[5,8,10,15]"
            :limit.sync="compQueryParams.pageSize"
            @pagination="getCompList"
          />
        </el-card>
      </el-col>
    </el-row>

    <el-card class="mb20" shadow="hover" v-has-permi="['seller:follow:list']">
      <div slot="header" class="flex flex-justify-between align-items-center">
        <h4>销售方跟进进度</h4>
        <el-button type="text" @click="showMoreFollow">更多</el-button>
      </div>
      <div style="height: 35vh">
        <el-table
          height="100%"
          v-loading="loading.follow"
          :data="followUpList" size="medium">
          <el-table-column label="跟进时间" prop="followupTime" header-align="center" width="180" align="center"></el-table-column>
          <el-table-column label="销售方名称" width="180" header-align="center" align="center" prop="sellerName"  show-overflow-tooltip></el-table-column>
          <el-table-column  label="跟进事项" prop="followupItem" width="150"  header-align="center" align="center">
          </el-table-column>
          <el-table-column  label="跟进说明" prop="followupDesc" show-overflow-tooltip header-align="center">
          </el-table-column>
          <el-table-column  label="操作" width="200"  header-align="center" align="center">
            <template #default="{row,$index}">
              <el-button
                class="mr5" size="mini"
                type="text"
                icon="el-icon-edit" @click="showFollow(row)"> 详情 </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
    <!--<el-card shadow="hover" class="mb20">
      <template #header>
        <h4>
          销售统计
        </h4>
      </template>
      <el-row :gutter="20">
        <el-col :span="6">
          <div>
            <el-statistic  :value="3123" title="订单量">
              <template slot="prefix">
                <svg-icon icon-class="order"></svg-icon>
              </template>
            </el-statistic>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <el-statistic  title="下单/支付比">
              <template slot="formatter">
                <svg-icon icon-class="compare"></svg-icon>
                6543/1234
              </template>
            </el-statistic>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <el-statistic group-separator="," :value="123" title="异常订单">
              <template slot="prefix">
                <i class="el-icon-s-flag" style="color: red"></i>
              </template>
&lt;!&ndash;              <template slot="suffix">&ndash;&gt;
&lt;!&ndash;                <i class="el-icon-s-flag" style="color: blue"></i>&ndash;&gt;
&lt;!&ndash;              </template>&ndash;&gt;
            </el-statistic>
          </div>

        </el-col>
        <el-col :span="6">
          <div>
            <el-statistic  :precision="2" decimal-separator="."  :value="520000" title="提现总流水">
              <template slot="prefix">
                  <svg-icon icon-class="moneybill"></svg-icon>
              </template>
            </el-statistic>
          </div>
        </el-col>
      </el-row>
    </el-card>
-->
    <a-modal  centered :zIndex="2000" title="添加待办事项" :visible.sync="open" width="55%" append-to-body @cancel="open = false">
      <el-form ref="form" class="edit" :model="todoForm" :rules="rules" label-width="108px" label-position="left">
        <el-row>
          <el-col :span="24" class="mb20">
            <el-form-item label="待办截止时间" prop="todoTime">
<!--              <el-input v-model="todoForm.todoTime" placeholder="请输入待办时间" />-->
              <el-date-picker clearable
                              style="width: 200px"
                              v-model="todoForm.todoTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择截止时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row >
          <el-col :span="24" class="mb20">
            <el-form-item label="待办事项说明" prop="todoThing">
              <el-input type="textarea" :autosize="{minRows: 3 , maxRows: 5}" v-model="todoForm.todoThing" placeholder="请输入待办事项说明" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </a-modal>

    <a-modal
      centered
      title="跟进情况详情"
      :visible.sync="openFollowUp"
      width="50%" :destroyOnClose="true"
      append-to-body @cancel="openFollowUp = false">

      <el-table
        :data="followUpDetailList"
        border
      >
        <el-table-column label="跟进时间" prop="followupTime" align="center">

        </el-table-column>
        <el-table-column label="跟进节点" prop="followupItem" align="center">

        </el-table-column>
        <el-table-column label="跟进情况说明" prop="followupDesc" show-overflow-tooltip align="center">

        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
      </div>
    </a-modal>

    <a-modal
      centered
      title="销售方跟进情况"
      :visible.sync="openFollowUpMore"
      width="65%"
      :destroyOnClose="true"
      append-to-body @cancel="openFollowUpMore = false">
      <el-tabs v-model="followType" @tab-click="handleClick">
        <el-tab-pane label="已跟进" name="1">
          <el-table
            v-loading="loading.followAll"
            :data="allFollowList" size="medium">
            <el-table-column label="跟进时间" prop="followupTime" header-align="center" width="180" align="center"></el-table-column>
            <el-table-column label="销售方名称" width="180" header-align="center" align="center" prop="sellerName"  show-overflow-tooltip></el-table-column>
            <el-table-column  label="跟进事项" prop="followupItem" width="150"  header-align="center" align="center">
            </el-table-column>
            <el-table-column  label="跟进说明" prop="followupDesc" show-overflow-tooltip header-align="center">
            </el-table-column>
            <el-table-column  label="操作" width="200"  header-align="center" align="center">
              <template #default="{row,$index}">
                <el-button
                  class="mr5" size="mini"
                  type="text"
                  icon="el-icon-edit" @click="showFollow(row)"> 详情 </el-button>
              </template>
            </el-table-column>
          </el-table>
          <pagination
            :background="false"
            v-show="total.allfollow1>0"
            :total="total.allfollow1"
            size="mini"
            layout="prev, pager, next"
            :page.sync="allFollowQueryParams1.pageNum"
            :page-sizes="[5,8,10,15]"
            :limit.sync="allFollowQueryParams1.pageSize"
            @pagination="getAllFollowList(1)"
          />
        </el-tab-pane>
        <el-tab-pane label="未跟进" name="2">
          <el-table
            v-loading="loading.followAll"
            :data="allFollowList" size="medium">
            <el-table-column label="跟进时间" prop="followupTime" header-align="center" width="180" align="center"></el-table-column>
            <el-table-column label="销售方名称" width="180" header-align="center" align="center" prop="sellerName"  show-overflow-tooltip></el-table-column>
            <el-table-column  label="跟进事项" prop="followupItem" width="150"  header-align="center" align="center">
            </el-table-column>
            <el-table-column  label="跟进说明" prop="followupDesc" show-overflow-tooltip header-align="center">
            </el-table-column>
            <el-table-column  label="操作" width="200"  header-align="center" align="center">
              <template #default="{row,$index}">
                <el-button
                  class="mr5" size="mini"
                  type="text"
                  icon="el-icon-edit" @click="showFollow(row)"> 详情 </el-button>
              </template>
            </el-table-column>
          </el-table>
          <pagination
            :background="false"
            v-show="total.allfollow2>0"
            :total="total.allfollow2"
            size="mini"
            layout="prev, pager, next"
            :page.sync="allFollowQueryParams2.pageNum"
            :page-sizes="[5,8,10,15]"
            :limit.sync="allFollowQueryParams2.pageSize"
            @pagination="getAllFollowList(2)"
          />
        </el-tab-pane>
      </el-tabs>

      <div slot="footer" class="dialog-footer">

      </div>
    </a-modal>
  <!--作废原因-->
    <a-modal  centered :zIndex="2000" title="作废事项" :visible.sync="abolishShow" width="55%" append-to-body @cancel="abolishShow = false">
      <el-form ref="abolishform" class="edit" :model="abolishForm" :rules="abolishRules" label-width="118px" label-position="left">
        <el-row >
          <el-col :span="24" class="mb20">
            <el-form-item label="请填写作废原因" prop="reason">
              <el-input type="textarea" :autosize="{minRows: 3 , maxRows: 5}" v-model="abolishForm.reason" placeholder="作废原因" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleAbolish">确 定</el-button>
        <el-button @click="abolishShow = false">取 消</el-button>
      </div>
    </a-modal>
  </div>
</template>

<script>
import { listTodos, getTodos, delTodos, addTodos, updateTodos } from "@/api/todos/index";
import {followupList, followupListAll, followupNotice} from "@/api/seller/seller";
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      open: false,
      like:true,
      dialogVisible: false,
      loading: {
        add: false,
        update: false,
        follow: false,
        followAll: false
      },
      openFollowUp: false,
      openFollowUpMore: false,
      followUpList:[],
      followUpDetailList:[],
      total :{
        todo: 0,
        comp: 0,
        allfollow1: 0,
        allfollow2: 0
      },
      rules: {
        todoThing: [
          { required: true, message: "请输入待办事项", trigger: "blur" }
        ],
        todoTime: [
          { required: true, message: "请选择截止日期", trigger: "blur" }
        ]
      },
      abolishForm : {
        id:'',
        reason: '',
        todoStatus: 3
      },
      abolishRules: {
        reason: [
          { required: true, message: "请填写作废原因", trigger: "blur" }
        ]
      },
      // 0-待办 1-已办
      todoQueryParams: {
        pageNum: 1,
        pageSize: 5,
        todoStatus: 0,
      },
      compQueryParams: {
        pageNum: 1,
        pageSize: 5,
        todoStatus: 1,
      },
      followType: '1',
      allFollowQueryParams1: {
        pageNum: 1,
        pageSize: 5,
        type:"1" // 跟进类型 1=已跟进 2=未跟进
      },
      allFollowQueryParams2: {
        pageNum: 1,
        pageSize: 2,
        type:"2" // 跟进类型 1=已跟进 2=未跟进
      },
      // 0-待办 1-已办
      todoForm: {
        id:null,
        todoThing: null,
        todoTime: null,
        todoStatus: null,
      },
      abolishShow: false,
      todosList:[],
      compList:[],
      allFollowList:[],
    };
  },
  created() {
    this.getTodoList()
    this.getCompList()
    this.getFollowList()
  },
  methods: {
    handleCommand(command,row) {
       switch (command) {
         case 'delay':
           this.handleDealy(row);
           break;
         case 'abolish':
           this.showAbolish(row)
       }
    },
    handleDealy(row) {
      const params = {
        id:row.id,
        todoStatus: 2,
      }
      this.$modal.confirm('确认延期所选事项?').then(()=> {
       return  updateTodos(params)
      }).then(() => {
        this.getTodoList();
        this.$modal.msgSuccess("操作成功");
      }).catch(() => {});
    },
    // 显示作废弹窗
    showAbolish(row) {
      this.abolishShow = true
      this.abolishForm.id = row.id
    },
    handleAbolish () {
      this.$refs.abolishform.validate(valid =>{
        if(valid) {
          updateTodos(this.abolishForm).then(res=>{
            this.$modal.msgSuccess("操作成功");
            this.getTodoList()
            this.getCompList()
            this.abolishShow = false
          })
        }
      })

    },
    handleClick({tab,name}){
      this.getAllFollowList(name)
    },
    showMoreFollow () {
     this.getAllFollowList(1)
    },
    getAllFollowList (type) {
      this.loading.followAll = true
      let params = type == 1 ? this.allFollowQueryParams1 : this.allFollowQueryParams2
      followupListAll(params).then(res =>{
        this.allFollowList = res.rows
        this.total.allfollow = res.total
        if(type == 1) {
          this.total.allfollow1 = res.total
        }else if(type == 2) {
          this.total.allfollow = res.total
        }
        this.openFollowUpMore = true
        this.loading.followAll = false
      }).catch( ()=>{
        this.loading.followAll = false
      })
    },
    goTarget(href) {
      window.open(href, "_blank");
    },
    changeStatus (row,val) {
      console.log(row,val)
      this.$set(row,'visiable',val)
    },
    // 待办事项列表
    getTodoList () {
      listTodos(this.todoQueryParams).then(response => {
        this.todosList = response.rows;
        this.total.todo = response.total;
      });
    },
    // 已办事项列表
    getCompList () {
      listTodos(this.compQueryParams).then(response => {
        this.compList = response.rows;
        this.total.comp = response.total;
      });
    },
    /*进度列表*/
    getFollowList () {
      this.loading.follow = true
      followupNotice().then(res=>{
        this.followUpList = res.data
        this.loading.follow = false
      }).catch( err =>{
        this.loading.follow = false
      })
    },
    /*跟进详情*/
    showFollow (row) {
      const {sellerId} = row
      followupList({sellerId}).then(res => {
        this.followUpDetailList = res.data
        this.openFollowUp = true
      })
    },
    handleAddtodo () {
      this.reset()
      this.open = true
    },
    handleUpdateTodo (row) {
      this.reset()
      getTodos(row.id).then( res=>{
        this.todoForm = res.data
        this.open = true
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.loading.add = true
          if(this.todoForm.id != null) {
            updateTodos(this.todoForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getTodoList();
              this.getCompList()
              setTimeout(()=>{
                this.loading.add = false
              },1000)
            })
          }else {
            addTodos(this.todoForm).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getTodoList();
              setTimeout(()=>{
                this.loading.add = false
              },1000)
            }).catch( err =>{
              setTimeout(()=>{
                this.loading.add = false
              },1000)
            })
          }

        }
      });
    },
    handleUpdate (row) {
      console.log(row)
      if (row.id != null) {

        this.loading.add = true
        this.loading.update = true
        updateTodos({id:row.id,todoStatus: 1}).then(response => {
          this.$modal.msgSuccess("修改成功");
          this.open = false;
          this.getTodoList();
          this.getCompList()
          setTimeout(()=>{
            this.loading.add = false
            this.loading.update = false
          },1000)
        }).catch( err =>{
          setTimeout(()=>{
            this.loading.add = false
            this.loading.update = false
          },1000)
        })
      }
    },
    // 表单重置
    reset() {
      this.todoForm = {
        id: null,
        todoThing: null,
        todoTime: null,
        todoStatus: null,
      };
      this.resetForm("form");
    },
    cancel () {
      this.open =false
      this.reset()
    }
  },
};
</script>

<style scoped lang="scss">
.home {

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;
}
>>>.el-table {
  .el-button {
    padding: 0;
  }
  &::before {
    height: 0!important;
  }
}
</style>

