<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      class="query"
      ref="queryForm" size="small"
      v-show="showSearch"
      label-width="78px">
      <el-row :gutter="15">
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="">
          <el-form-item label="供应商名称" prop="supplierName">
            <el-input
              v-model="queryParams.supplierName"
              placeholder="请输入供应商名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="">
          <el-form-item label="昵称" prop="supplierNickName">
            <el-input
              v-model="queryParams.supplierNickName"
              placeholder="请输入昵称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="">
          <el-form-item label="供应商编码" prop="supplierCode">
            <el-input
              v-model="queryParams.supplierCode"
              placeholder="请输入供应商编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="">
          <el-form-item label="联系人" prop="contractName">
            <el-input
              v-model="queryParams.contractName"
              placeholder="请输入联系人"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="">
          <el-form-item label="联系电话" prop="contractPhone">
            <el-input
              v-model="queryParams.contractPhone"
              placeholder="请输入联系电话"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="">
          <el-form-item label="添加人" prop="purchaser">
            <el-input
              v-model="queryParams.purchaser"
              placeholder="请输入添加人"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="">
          <el-form-item label="提现账户" prop="withdrawalAccount">
            <el-input
              v-model="queryParams.withdrawalAccount"
              placeholder="请输入提现账户"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="">
          <el-form-item label="货主id" prop="ownerUserId">
            <el-input
              v-model="queryParams.ownerUserId"
              placeholder="请输入货主id"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="">
          <el-form-item label="秘钥" prop="secretkey">
            <el-input
              v-model="queryParams.secretkey"
              placeholder="请输入秘钥"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
<!--        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="">-->
<!--          <el-form-item label="启用/禁用状态  0：启用  1：禁用" prop="enabled">-->
<!--            <el-input-->
<!--              v-model="queryParams.enabled"-->
<!--              placeholder="请输入启用/禁用状态  0：启用  1：禁用"-->
<!--              clearable-->
<!--              @keyup.enter.native="handleQuery"-->
<!--            />-->
<!--          </el-form-item>-->
<!--        </el-col>-->
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="">
          <el-form-item label="提现手续费类型" prop="formalitiesFeeType">
            <el-select v-model="queryParams.formalitiesFeeType" placeholder="请选择提现手续费类型" clearable>
              <el-option
                v-for="dict in dict.type.supplier_formalities_feetype"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="">
          <el-form-item label="状态" prop="enabled">
            <el-select v-model="queryParams.enabled" placeholder="请选择状态" clearable>
              <el-option
                v-for="dict in dict.type.enable_flag"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="保税仓" prop="bondedWarehouseId">
            <el-cascader
              placeholder="请选择保税仓"
              v-model="queryParams.bondedWarehouseId"
              :options="bwList"
              :show-all-levels="false"
              :props="{
                  label: 'bondedWarehouseName',
                  checkStrictly: true,
                  value: 'id',
                  emitPath: false
              }"
              clearable
              change-on-select
            >
            </el-cascader>
          </el-form-item>
        </el-col>
<!--        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="mb10">-->
<!--          <el-form-item label="提现手续费" prop="formalitiesFee">-->
<!--            <el-input-->
<!--              v-model="queryParams.formalitiesFee"-->
<!--              placeholder="请输入提现手续费"-->
<!--              clearable-->
<!--              @keyup.enter.native="handleQuery"-->
<!--            />-->
<!--          </el-form-item>-->
<!--        </el-col>-->
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="ml10">
          <div class="h-40-middle">
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </div>
        </el-col>
      </el-row>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:supplier:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:supplier:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:supplier:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:supplier:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table border stripe v-loading="loading" :data="supplierList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="供应商ID" align="center" prop="id" />-->
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="昵称" align="center" prop="supplierNickName" />
      <el-table-column label="供应商编码" align="center" prop="supplierCode" width="170" />
      <el-table-column label="保税仓" align="center" prop="bondedWarehouseName" width="170" />
      <el-table-column label="联系人" align="center" prop="contractName" />
      <el-table-column label="联系电话" align="center" prop="contractPhone" />
      <el-table-column label="添加人" align="center" prop="purchaser" />
      <el-table-column label="提现账户" align="center" prop="withdrawalAccount" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="货主id" align="center" prop="ownerUserId" width="140" />
      <el-table-column label="秘钥" align="center" prop="secretkey" />
      <el-table-column label="启用/禁用状态" align="center" prop="enabled" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.enable_flag" :value="scope.row.enabled"/>
        </template>
      </el-table-column>
      <el-table-column label="提现手续费" align="center" prop="formalitiesFee" />
      <el-table-column label="提现手续费类型" align="center" prop="formalitiesFeeType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.supplier_formalities_feetype" :value="scope.row.formalitiesFeeType"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <div>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:supplier:edit']"
            >修改</el-button>
          </div>
          <div>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:supplier:remove']"
            >删除</el-button>
          </div>
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

    <!-- 添加或修改供应商信息对话框 -->
    <a-modal
      :title="title"
      :visible.sync="open"
      :zIndex="1001"
      width="75%"
      append-to-body
      centered
      :maskClosable="false"
      @cancel="open = false">
      <el-form ref="form" class="edit" :model="form" :rules="rules" label-width="88px" label-position="right">
        <el-row :gutter="10">
          <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="12" class="mb10">
            <el-form-item label="供应商名称" prop="supplierName">
              <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="12" class="mb10">
            <el-form-item label="昵称" prop="supplierNickName">
              <el-input v-model="form.supplierNickName" placeholder="请输入昵称" />
            </el-form-item>
          </el-col>
<!--          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" class="mb10">-->
<!--            <el-form-item label="昵称" prop="supplierNickName">-->
<!--              <el-input v-model="form.supplierNickName" placeholder="请输入昵称" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" class="mb10">
            <el-form-item label="供应商编码" prop="supplierCode">
              <el-input v-model="form.supplierCode" placeholder="请输入供应商编码" />
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" class="mb10">
            <el-form-item label="保税仓" prop="bondedWarehouseId">
              <!--              <el-input v-model="form.goodsClassificationArr" placeholder="请输入分类"/>-->
              <el-cascader
                ref="chooseBwSave"
                placeholder="请选择保税仓"
                v-model.number="form.bondedWarehouseId"
                :options="bwList"
                :show-all-levels="false"
                :props="{
                  label: 'bondedWarehouseName',
                  checkStrictly: true,
                  value: 'id',
                  emitPath: false
                }"
                clearable
              >
              </el-cascader>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" class="mb10">
            <el-form-item label="联系人" prop="contractName">
              <el-input v-model="form.contractName" placeholder="请输入联系人" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" class="mb10">
            <el-form-item label="联系电话" prop="contractPhone">
              <el-input v-model="form.contractPhone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" class="mb10">
            <el-form-item label="添加人" prop="purchaser">
              <el-input v-model="form.purchaser" placeholder="请输入添加人" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" class="mb10">
            <el-form-item label="提现账户" prop="withdrawalAccount">
              <el-input v-model="form.withdrawalAccount" placeholder="请输入提现账户" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" class="mb10">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" class="mb10">
            <el-form-item label="货主id" prop="ownerUserId">
              <el-input v-model="form.ownerUserId" placeholder="请输入货主id" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" class="mb10">
            <el-form-item label="秘钥" prop="secretkey">
              <el-input v-model="form.secretkey" placeholder="请输入秘钥" />
            </el-form-item>
          </el-col>
<!--          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" class="mb10">-->
<!--            <el-form-item label="启用/禁用状态" prop="enabled">-->
<!--              <el-input v-model="form.enabled" placeholder="请输入启用/禁用状态  0：启用  1：禁用" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" class="mb10">
            <el-form-item label="禁用/启用状态" prop="enabled">
              <el-select @focus="focusSelect(formalitiesFeeType)" ref="enabled" v-model="form.enabled" placeholder="请选择禁用/启用状态">
                <el-option
                  v-for="dict in dict.type.enable_flag"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" class="mb10">
            <el-form-item label="提现手续费类型" prop="formalitiesFeeType">
              <el-select @focus="focusSelect(formalitiesFeeType)" ref="formalitiesFeeType" v-model="form.formalitiesFeeType" placeholder="请选择提现手续费类型">
                <el-option
                  v-for="dict in dict.type.supplier_formalities_feetype"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" class="mb20">
            <el-form-item label="提现手续费" prop="formalitiesFee">
              <el-input v-model="form.formalitiesFee" placeholder="请输入提现手续费" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </a-modal>
  </div>
</template>

<script>
import { listSupplier, getSupplier, delSupplier, addSupplier, updateSupplier } from "@/api/suppiler/supplier";
import {focusSelect} from "@/utils/ruoyi";
import { listInfo } from "@/api/suppiler/bw";

export default {
  name: "Supplier",
  dicts: ['supplier_formalities_feetype','enable_flag'],
  data() {
    return {
      bwList: [],
      // 遮罩层
      loading: true,
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
      // 供应商信息表格数据
      supplierList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        supplierName: null,
        supplierNickName: null,
        supplierCode: null,
        contractName: null,
        contractPhone: null,
        purchaser: null,
        withdrawalAccount: null,
        ownerUserId: null,
        secretkey: null,
        enabled: null,
        formalitiesFee: null,
        formalitiesFeeType: null,
        bondedWarehouseId: null,
        bondedWarehouseName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        supplierName: [
          { required: true, message: "请输入供应商名称", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getBwList();
  },
  methods: {
    //获取商品分类
    getBwList() {
      this.loading = true;
      listInfo().then(response => {
        this.bwList = response.data;
      });
    },

    focusSelect() {
      return focusSelect
    },
    /** 查询供应商信息列表 */
    getList() {
      this.loading = true;
      listSupplier(this.queryParams).then(response => {
        this.supplierList = response.rows;
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
        supplierName: null,
        supplierNickName: null,
        supplierCode: null,
        contractName: null,
        contractPhone: null,
        purchaser: null,
        withdrawalAccount: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        ownerUserId: null,
        formalitiesFee: null,
        formalitiesFeeType: null,
        bondedWarehouseId: null,
        bondedWarehouseName: null,
      };
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
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加供应商信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSupplier(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改供应商信息";
        this.$nextTick(() => {
          this.$refs.chooseBwSave.inputValue = this.form.bondedWarehouseName
        })
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSupplier(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSupplier(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除所选数据项？').then(function() {
        return delSupplier(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/supplier/export', {
        ...this.queryParams
      }, `supplier_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

