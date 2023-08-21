<template>
  <div class="app-container">
<!--    <el-form class="query mb10"-->
<!--             :model="queryParams"-->
<!--             ref="queryForm" size="small"  v-show="showSearch" label-width="78px">-->
<!--      <el-row>-->

<!--        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6" class="mb10">-->
<!--          <el-form-item label="提现状态" prop="sellerWithdrawStatus">-->
<!--            <el-select-->
<!--              v-model="queryParams.sellerWithdrawStatus"-->
<!--              placeholder="提现状态"-->
<!--              clearable-->
<!--              style="width: 240px"-->
<!--            >-->
<!--              <el-option-->
<!--                v-for="dict in dict.type.seller_withdraw_status"-->
<!--                :key="dict.value"-->
<!--                :label="dict.label"-->
<!--                :value="dict.value"-->
<!--              />-->
<!--            </el-select>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6" class="mb10">-->
<!--          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>-->
<!--          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
<!--        </el-col>-->
<!--      </el-row>-->
<!--    </el-form>-->
    <el-card>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-has-permi="['seller:withdrawal:add']"
          >申请提现</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="info"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['seller:withdraw:export']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearchIcon="false" :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table max-height="700px" border stripe v-loading="loading" :data="refList">
        <el-table-column label="销售方名称" align="center" prop="sellerName" />
        <el-table-column label="提现金额" align="center" prop="withdrawalAmount" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['seller:withdrawal:query']"
            >详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 销售方提现 -->
    <a-modal :zIndex="2002" :title="title" :visible.sync="open" width="65%" append-to-body @cancel="open = false">
      <el-form ref="form" class="form" :model="form" :rules="rules" label-width="98px" label-position="left">
        <el-row>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
            <el-form-item label-width="120px" label="申请提现金额" prop="withdrawalAmount">
              <el-input v-model="form.withdrawalAmount" placeholder="申请提现金额"  readonly/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-divider content-position="center" class="divider-info">订单信息</el-divider>
      <el-table
        border
        v-loading="loading"
        size="small"
        header-cell-class-name="table-header-cell"
        :data="orderList" @selection-change="handleSelectionChange" >
        <el-table-column type="selection" align="center" width="55" :selectable="selectable" />
        <el-table-column label="商户订单号" align="center" prop="declareOrderNo" />
        <el-table-column label="交易金额/实际支付金额" align="center" prop="realTotalAmount" />
        <el-table-column label="销售方提现状态" align="center" prop="sellerWithdrawalStatus" >
          <template #default="{row}">
            <dict-tag :options="dict.type.seller_withdraw_status" :value="row.sellerWithdrawalStatus"/>
          </template>
        </el-table-column>
        <el-table-column label="可提现金额" align="center" prop="sellerWithdrawalAmount" />
      </el-table>
      <pagination
        v-show="orderTotal>0"
        :total="orderTotal"
        :page.sync="orderQueryParams.pageNum"
        :page-sizes="[2,10,20,50]"
        :limit.sync="orderQueryParams.pageSize"
        @pagination="getOrderList"
      />
      <div slot="footer" class="dialog-footer">
        <el-button
          type="primary"
          @click="submitForm">
          确 定
        </el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </a-modal>

    <a-modal
      :zIndex="2002" title="销售方提现详情"
      :visible.sync="openDetail"
      width="65%" append-to-body @cancel="openDetail = false">
      <el-form ref="form" class="query" :model="form">
        <el-row>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
            <el-form-item label-width="100px" label="申请提现金额" >
              <el-input v-model="form.withdrawalAmount" placeholder="申请提现金额"  readonly/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div>
        <el-divider content-position="center">提现订单信息</el-divider>
        <el-table
          border
          v-loading="loading"
          size="small"
          header-cell-class-name="table-header-cell"
          :data="form.orderInfoList" @selection-change="handleSelectionChange" >
          <el-table-column label="商户订单号" align="center" prop="declareOrderNo" />
          <el-table-column label="交易金额/实际支付金额" align="center" prop="realTotalAmount" />
          <el-table-column label="销售方提现状态" align="center" prop="sellerWithdrawalStatus" >
            <template #default="{row}">
              <dict-tag :options="dict.type.seller_withdraw_status" :value="row.sellerWithdrawalStatus"/>
            </template>
          </el-table-column>
          <el-table-column label="可提现金额" align="center" prop="sellerWithdrawalAmount" />
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="openDetail = false">关 闭</el-button>
      </div>
    </a-modal>
  </div>
</template>

<script>
import BigNumber from "bignumber.js";
import {listInfo} from "@/api/order/orderinfo";
import {addWithdrawSeller, listWithdrawSeller, withdrawInfoSeller} from "@/api/seller/sellerWithdraw";

export default {
  name: "Ref",
  dicts: ['seller_withdraw_status'],
  data() {
    var checkAmount = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('必须选择可提现金额大于0的订单'));
      }
      if (value <= 0) {
        callback(new Error('必须选择可提现金额大于0的订单'));
      } else {
        callback();
      }
    };
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      openDetail: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 销售方提现记录与订单记录关联表格数据
      refList: [],
      // 订单列表
      orderList: [],
      orderQueryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        sellerWithdrawStatus: undefined,
        pageNum: 1,
        pageSize: 10,
      },
      orderTotal: 0,
      totalAmount: 0,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        withdrawalAmount: [
          { required: true,message: '请选择提现的订单', trigger: 'change' },
          { validator: checkAmount, trigger: 'change' }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getOrderList()
  },
  methods: {
    /** 查询订单信息列表 */
    getOrderList() {
      this.loading = true;
      listInfo(this.orderQueryParams).then(response => {
        this.orderList = response.rows;
        this.orderTotal = response.total;
        this.loading = false;
      });
    },
    /** 查询销售方提现记录与订单记录关联列表 */
    getList() {
      this.loading = true;
      listWithdrawSeller(this.queryParams).then(response => {
        this.refList = response.rows;
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
        withdrawalAmount: null, // 提现金额
        orderInfoIds: [] // 订单id
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
    // 多选计算金额
    handleSelectionChange(selection) {
      // this.ids = selection.map(item => item.id)
      // this.single = selection.length!==1
      // this.multiple = !selection.length
      this.form.orderInfoIds = selection.map(item => item.id)
      let sellerWithdrawalAmounts = selection.map(item => item.sellerWithdrawalAmount)
      let total = BigNumber.sum.apply(null, sellerWithdrawalAmounts)
      this.form.withdrawalAmount = total.toNumber() ? total.toNumber() : 0
    },
    selectable (row,index) {
      if(row.sellerWithdrawalStatus ==0) {
        return true
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getOrderList()
      this.open = true;
      this.title = "申请提现";
    },
    /** 详情 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      withdrawInfoSeller(id).then(response => {
        this.form = response.data;
        this.openDetail = true;
        this.title = "修改销售方提现记录与订单记录关联";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addWithdrawSeller(this.form).then(response => {
            this.$modal.msgSuccess("申请成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/ref/export', {
        ...this.queryParams
      }, `ref_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
