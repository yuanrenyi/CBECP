<template>
  <div class="app-container">
    <el-form class="mb10" :model="queryParams" ref="queryForm" size="small" v-show="showSearch"
             label-width="98px" inline>
      <el-form-item label="文档名称" prop="servicePlanName" label-width="118px">
        <el-input
          v-model="queryParams.servicePlanName"
          placeholder="文档名称"
          style="width: 260px"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="销售方" prop="compId">
        <!--            <el-input-->
        <!--              v-model="queryParams.sellerName"-->
        <!--              placeholder="销售方名称"-->
        <!--              clearable-->
        <!--              @keyup.enter.native="handleQuery"-->
        <!--            />-->
        <select-table
          v-model="compIdQuery"
          placeholder="请选择销售方"
          style="width: 260px!important"
          ref="sellerQuery"
          :table="sellerTable"
          :columns="sellerTable.columns"
          :keywords="{label:'compName',value:'id'}"
          @page-change="sellerPageChange"
          @clear="resetQuerySeller"
          @radioChange="sellerQueryChange"
          :max-height="500"
          @inputKeyword="filterSeller"
          @focus="getSellerList"
          radioTxt="#"
          isShowPagination
        ></select-table>
      </el-form-item>
      <el-form-item label="是否为最新方案" prop="ifNews" label-width="118px">
        <el-select
          v-model="queryParams.ifNews"
          placeholder="是否为最新方案"
          style="width: 260px!important"
          clearable>
          <el-option
            v-for="dict in dict.type.service_plan_if_news"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item class="ml20">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['service:plan:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['service:plan:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['service:plan:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['service:plan:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table border stripe v-loading="loading" :data="planList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="销售方ID" align="center" prop="compId" />-->
      <el-table-column label="销售方" align="center" prop="sellerName">
        <template #default="{row}">
          <a @click="handleSellerInfo(row.compId)"> {{ row.sellerName }}</a>
        </template>
      </el-table-column>
      <el-table-column label="文档名称" align="center" prop="servicePlanName">
        <template #default="{row}">
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                   :href="row.servicePlan">
            {{ row.servicePlanName }}
          </el-link>
        </template>
      </el-table-column>

      <el-table-column label="是否为最新方案" align="center" prop="ifNews">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.service_plan_if_news" :value="scope.row.ifNews"/>
        </template>
      </el-table-column>
      <!--      <el-table-column label="服务方案" align="center" prop="servicePlan" show-overflow-tooltip="" min-width="220"/>-->

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['service:plan:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['service:plan:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改销售方服务方案对话框 -->
    <a-modal
      :title="title"
      :visible.sync="open"
      width="60%"
      append-to-body
      destroyOnClose
      @afterClose="cancel"
      @cancel="open = false">
      <el-form ref="form" class="edit" :model="form" :rules="rules" label-width="128px" label-position="left">
        <el-row>
          <el-col :span="19" class="mb10">
            <el-form-item label="文档名称" prop="servicePlanName">
              <el-input v-model="form.servicePlanName" readonly clearable @clear="clearFile"
                        placeholder="请输入文档名称">
                <el-button slot="append" icon="el-icon-upload" @click="emitUpload">选取文件</el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="19">
            <el-form-item disabled label="请选择销售方" prop="compId">
              <select-table
                v-model="compIdForm"
                placeholder="请选择销售方"
                :max-height="380"
                ref="sellerForm"
                :table="sellerTable"
                :columns="sellerTable.columns"
                :keywords="{label:'compName',value:'id'}"
                @page-change="sellerPageChange"
                @clear="resetFormSeller"
                @radioChange="sellerFormChange"
                @inputKeyword="filterSeller"
                @focus="getSellerList"
                radioTxt="#"
                isShowPagination
              ></select-table>
            </el-form-item>
          </el-col>
        </el-row>
        <!--        <el-row >
                  <el-col :span="12">
                    <el-form-item>
                      <file-upload  :show-list="false" :file-type="['doc','docx']" v-model="form.servicePlan"
                                   @files-change="getFile">
                        <el-button size="mini" type="primary">{{  form.id? '重新选取': '选取文件' }}</el-button>
                      </file-upload>
                    </el-form-item>
                  </el-col>
                </el-row>-->
        <file-upload
          style="display: none"
          ref="fileupload"
          :show-list="false"
          :file-type="['doc','docx','pdf', 'xls','xlsx']"
          v-model="form.servicePlan"
          @files-change="getFile">
          <el-button size="mini" type="primary">{{ form.id ? '重新选取' : '选取文件' }}</el-button>
        </file-upload>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="open=false">取 消</el-button>
      </div>
    </a-modal>

    <!-- 销售方信息对话框 -->
    <a-modal
      :title="title"
      :footer="null"
      :zIndex="1001"
      :visible.sync="openSellerInfo" width="75%"
      :destroyOnClose="true" append-to-body
      @cancel="openSellerInfo = false">
      <el-descriptions
        :labelStyle="{width: '135px'}"
        :contentStyle="{width: '220px'}"
        :column="3"
        border
      >
        <el-descriptions-item :span="2" label="企业名称" >
          {{sellerInfo.compName}}
        </el-descriptions-item>

        <el-descriptions-item  label="项目负责人" >
          {{sellerInfo.pmName}}
        </el-descriptions-item>


        <el-descriptions-item  label="负责人电话" >
          {{sellerInfo.pmPhone}}
        </el-descriptions-item>


        <el-descriptions-item  label="微信群" >
          {{sellerInfo.wechatGroup}}
        </el-descriptions-item>


        <el-descriptions-item  label="省" >
          {{sellerInfo.compProvinceName}}
        </el-descriptions-item>


        <el-descriptions-item  label="市" >
          {{sellerInfo.compCityName}}
        </el-descriptions-item>


        <el-descriptions-item  label="企业规模" >
          {{sellerInfo.compScale}}
        </el-descriptions-item>


        <el-descriptions-item  label="是否已开会议" >
          <dict-tag :options="dict.type.seller_has_metting" :value="sellerInfo.ifMeeting"/>
        </el-descriptions-item>


        <el-descriptions-item  label="会议时间" >
          {{sellerInfo.meetingTime}}
        </el-descriptions-item>


        <el-descriptions-item  label="培训人" >
          {{sellerInfo.trainer}}
        </el-descriptions-item>


        <el-descriptions-item  label="人员数量" >
          {{sellerInfo.trainNum}}
        </el-descriptions-item>


        <el-descriptions-item   label="是否有微信小程序" label-width="130px">
          <dict-tag :options="dict.type.seller_has_miniprogram" :value="sellerInfo.ifWechatMiniapp"/>
        </el-descriptions-item>


        <el-descriptions-item  label="合作模式" >
          {{sellerInfo.cooperationMode}}
        </el-descriptions-item>


        <el-descriptions-item  label="合同进度" >
          {{sellerInfo.contractProgress}}
        </el-descriptions-item>


        <el-descriptions-item  label="款项时间" >
          {{sellerInfo.paymentTime}}
        </el-descriptions-item>


        <el-descriptions-item  label="用户编码" >
          {{sellerInfo.userCode}}
        </el-descriptions-item>


        <el-descriptions-item  label="海关备案名称" >
          {{sellerInfo.customsCode}}
        </el-descriptions-item>


        <el-descriptions-item  label="渠道编码" >
          {{sellerInfo.cnCode}}
        </el-descriptions-item>

        <el-descriptions-item  label="179公告对接地址" >
          {{sellerInfo.encryptUrl}}
        </el-descriptions-item>


        <el-descriptions-item  label="系统ID" >
          {{sellerInfo.msid}}
        </el-descriptions-item>


        <el-descriptions-item  label="微信二级子商户号"  >
          {{sellerInfo.wxmchid}}
        </el-descriptions-item>

        <el-descriptions-item  label="微信清关关区编码" >
          {{sellerInfo.customName}}
        </el-descriptions-item>

        <el-descriptions-item  :span="3" label="会议跟进情况" >
          {{sellerInfo.meetingCondition}}
        </el-descriptions-item>
        <el-descriptions-item  :span="3" label="备注" >
          {{sellerInfo.remark}}
        </el-descriptions-item>
      </el-descriptions>
    </a-modal>
  </div>
</template>

<script>
import {listPlan, getPlan, delPlan, addPlan, updatePlan} from "@/api/seller/plan";
import sellerMix from "@/mixins/sellerMix";
import {getSeller} from "@/api/seller/seller";
import {clone} from "lodash";

export default {
  name: "Plan",
  mixins: [sellerMix],
  dicts: ['service_plan_if_news', 'seller_has_metting', 'seller_has_miniprogram'],
  data() {
    return {
      // 遮罩层
      loading: true,
      openSellerInfo: false,
      sellerInfo: {
        id: null,
        compName: null,
        pmName: null,
        pmPhone: null,
        wechatGroup: null,
        compProvince: null,
        compProvinceName: null,
        compCity: null,
        compCityName: null,
        compScale: null,
        ifMeeting: null,
        meetingTime: null,
        trainer: null,
        trainNum: null,
        ifWechatMiniapp: null,
        cooperationMode: null,
        meetingCondition: null,
        contractProgress: null,
        paymentTime: null,
        remark: null,
        userCode: null,
        enterpriseName: null,
        customsCode: null,
        cnCode: null,
        encryptUrl: null,
        msid: null,
        wxmchid: null,
        customName: null,
      },
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 销售方服务方案表格数据
      planList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        servicePlan: null,
        sellerName: null,
        servicePlanName: null,
        ifNews: null,
      },
      // 表单参数
      form: {
        compId: undefined
      },
      // 表单校验
      rules: {
        compId: [
          {required: true, message: "请选择销售方", trigger: "change"}
        ],
        servicePlanName: [
          {required: true, message: "请选择文档", trigger: "change"}
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 修改按钮操作 */
    handleSellerInfo(id) {
      getSeller(id).then(response => {
        this.sellerInfo = response.data;
        // let provinceID = clone(this.form.compProvince)
        // let province = clone(this.form.compProvinceName)
        // let proObj = {provinceID, province}
        // let cityID = clone(this.form.compCity)
        // let city = clone(this.form.compCityName)
        // let cityObj = {cityID, city}
        this.openSellerInfo = true;
        // this.initSelectArea(proObj, cityObj)
        this.title = "销售方信息";
      });
    },
    clearFile() {
      console.log(1111111)
    },
    emitUpload() {
      console.log(this.$refs.fileupload)
      this.$refs.fileupload.$el.querySelector('input').click()
    },
    getFile(files) {
      console.log(files)
      this.form.servicePlanName = files[0].originalFilename
    },
    sellerQueryChange(val) {
      this.queryParams.compId = val && val.id ? val.id : undefined
      this.compIdQuery = val && val.id ? val.id : undefined
    },
    sellerFormChange(val) {
      this.form.compId = val && val.id ? val.id : undefined
      this.compIdForm = val && val.id ? val.id : undefined
    },
    resetFormSeller() {
      this.form.compId = ''
      this.compIdForm = ''
    },
    resetQuerySeller() {
      this.queryParams.compId = ''
      this.compIdQuery = ''
    },
    /** 查询销售方服务方案列表 */
    getList() {
      this.loading = true;
      listPlan(this.queryParams).then(response => {
        this.planList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        servicePlan: null,
        servicePlanName: null,
        sellName: null,
        ifNews: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.compIdForm = undefined
      this.query.compId = undefined
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.resetQuerySeller()
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加销售方服务方案";
    },
    /** 修改按钮操作 */
    async handleUpdate(row) {
      this.reset();

      await this.getSellerList()

      const id = row.id || this.ids
      getPlan(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.$nextTick(() => {
          this.compIdForm = row.compId
          this.query.compId = row.compId
        })
        this.title = "修改销售方服务方案";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePlan(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPlan(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除所选销售方服务方案？').then(function () {
        return delPlan(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('seller/plan/export', {
        ...this.queryParams
      }, `plan_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style lang="scss" scoped>
//.info {
//  .el-form {
//    .el-form-item {
//      >>> .el-input  .el-input__inner{
//          border: none!important;
//      }
//    }
//  }
//}
::v-deep.info .el-input__inner, .el-textarea__inner {
  border: none !important;
}

::v-deep.info .el-textarea__inner {
  border: none !important;
}

</style>
