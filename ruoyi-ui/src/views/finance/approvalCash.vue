<template>
  <div class="app-container">
    <el-form class="query mb10"
             :model="queryParams"
             ref="queryForm" size="small"  v-show="showSearch" label-width="78px">
      <el-row>

        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6" class="mb10">
          <el-form-item label="审核状态" prop="auditStatus">
            <el-select
              v-model="queryParams.auditStatus"
              placeholder="审核状态"
              clearable
            >
              <el-option
                v-for="dict in dict.type.supplier_withdraw_audit_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6" class="mb10">
<!--          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>-->
<!--          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
        </el-col>
      </el-row>
    </el-form>
    <el-card>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="供应商提现审批" name="supplier">

          <el-table
            ref="supperlier"
            max-height="700px"
            border
            v-loading="loading" :data="withdrawList.supplier">
            <el-table-column label="供应商名称" align="center" prop="supplierName" />
            <el-table-column label="提现金额" align="center" prop="withdrawalAmount" />
            <el-table-column label="申请时间" align="center" prop="updateTime" />
            <!--            <el-table-column label="提现状态" align="center" prop="supplierWithdrawalStatus" >-->
            <!--              <template #default="{row}">-->
            <!--                <dict-tag :options="dict.type.supplier_withdraw_status" :value="row.supplierWithdrawalStatus"/>-->
            <!--              </template>-->
            <!--            </el-table-column>-->
            <el-table-column label="审核状态" align="center" prop="auditStatus" >
              <template #default="{row}">
                <dict-tag :options="dict.type.supplier_withdraw_audit_status" :value="row.auditStatus"/>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleDetailSupplier(scope.row)"
                  v-hasPermi="['supplier:withdraw:query']"
                >详情</el-button>
              </template>
            </el-table-column>
          </el-table>

        </el-tab-pane>
        <el-tab-pane label="销售方提现信息" name="seller">
          <el-table
            border
            ref="serller"
            :data="withdrawList.seller" >
            <el-table-column label="销售方" align="center" prop="sellerName" />
            <el-table-column label="提现金额" align="center" prop="withdrawalAmount" />
            <el-table-column label="申请时间" align="center" prop="createTime" />
<!--            <el-table-column label="审核人" align="center" prop="auditBy" />-->
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleDetailSeller(scope.row,1)"
                  v-hasPermi="['system:withdraw:edit']"
                >详情</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>
    <pagination
      v-show="total.supplier>0 && activeName== 'supplier'"
      :total="total.supplier"
      :page.sync="queryParams.supplier.pageNum"
      :limit.sync="queryParams.supplier.pageSize"
      @pagination="getWithdrawListSupplier"
    />
    <pagination
      v-show="total.seller>0 && activeName=='seller'"
      :total="total.seller"
      :page.sync="queryParams.seller.pageNum"
      :limit.sync="queryParams.seller.pageSize"
      @pagination="getWithdrawListSupplier"
    />
    <a-modal
      :zIndex="2002"
      title="供应商提现申请详情"
      :visible.sync="openDetail.supplier"
      width="65%" append-to-body @cancel="openDetail.supplier = false">
      <el-form ref="form" class="query mb10" :model="withdrawInfo.supplier">
        <el-row>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" class="mb20">
            <el-form-item label-width="120px" label="申请提现金额" prop="withdrawAmount">
              <el-input v-model="withdrawInfo.supplier.withdrawalAmount" placeholder="申请提现金额"  readonly/>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24" :sm="12" :md="12" :lg="16" :xl="16">
            <div class="flex align-items-center flex-justify-end" v-if="currentAuditStatus == 0">
              <el-popconfirm
                class="mr10"
                placement="bottom-start"
                confirm-button-text='确定'
                cancel-button-text='取消'
                icon="el-icon-info"
                @confirm="auditSupplier(1)"
                title="确认通过？"
              >
                <el-button slot="reference" icon="el-icon-check" size="mini" type="success" plain >通 过</el-button>
              </el-popconfirm>
              <el-popconfirm
                placement="bottom-start"
                confirm-button-text='确定'
                cancel-button-text='取消'
                icon="el-icon-info"
                @confirm="auditSupplier(2)"
                title="确认拒绝？"
              >
                <el-button slot="reference" icon="el-icon-close" size="mini" type="danger" plain >拒 绝</el-button>
              </el-popconfirm>
            </div>
          </el-col>
        </el-row>
      </el-form>
      <div>
        <el-divider content-position="center">提现条目信息</el-divider>
        <el-table
          style="{min-height: 600px}"
          border
          v-loading="loading"
          size="small"
          header-cell-class-name="table-header-cell"
          :data="withdrawInfo.supplier.orderInfoList">
          <el-table-column label="商户订单号" align="center" prop="declareOrderNo" />
          <el-table-column label="交易金额/实际支付金额" align="center" prop="realTotalAmount" />
          <el-table-column label="供应商提现状态" align="center" prop="supplierWithdrawalStatus" >
            <template #default="{row}">
              <dict-tag :options="dict.type.supplier_withdraw_status" :value="row.supplierWithdrawalStatus"/>
            </template>
          </el-table-column>
          <el-table-column label="可提现金额" align="center" prop="supplierWithdrawalAmount" />
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">

        <el-button @click="openDetail.supplier = false">关 闭</el-button>
      </div>
    </a-modal>

    <a-modal
      :zIndex="2002" title="销售方提现详情"
      :visible.sync="openDetail.seller"
      width="65%" append-to-body @cancel="openDetail.seller = false">
      <el-form ref="form" class="query" :model="withdrawInfo.seller">
        <el-row>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
            <el-form-item label-width="100px" label="申请提现金额" >
              <el-input v-model="withdrawInfo.seller.withdrawalAmount" placeholder="申请提现金额"  readonly/>
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
          :data="withdrawInfo.seller.orderInfoList" >
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
        <el-button @click="openDetail.seller = false">关 闭</el-button>
      </div>
    </a-modal>
  </div>
</template>

<script>
import {auditWithdrawSupplier, listWithdrawSupplier, withdrawInfoSupplier} from "@/api/suppiler/supplierWithdraw";
import {listWithdrawSeller, withdrawInfoSeller} from "@/api/seller/sellerWithdraw";

export default {
  name: "ApprovalWithdraw",
  dicts: ['withdraw_status','supplier_withdraw_audit_status','supplier_withdraw_status','seller_withdraw_status'],
  data () {
    return {
      showSearch: false,
      activeName: 'supplier',
      loading: false,
      openDetail: {
        supplier: false,
        seller: false
      },
      queryParams: {
        supplier: {
          pageNum:1,
          pageSize: 10
        },
        seller: {
          pageNum:1,
          pageSize: 10
        }
      },
      total: {
        supplier:0,
        seller:0
      },
      withdrawInfo: {
        supplier: {
          withdrawalAmount:0,
          orderInfoList: []
        },
        seller: {
          withdrawalAmount:0,
          orderInfoList: []
        }
      },
      currentAuditStatus: 0, // 审核状态 0-未审核 1-审核通过 2-审核未通过
      withdrawList :{
        seller: [],
        supplier: []
      }
    }
  },
  created() {
    this.getWithdrawListSupplier()
    this.getWithdrawListSeller()
  },
  methods: {
    handleApprove (row) {
    },
    auditSupplier (type) {
      const {id,supplierId} = this.withdrawInfo.supplier
      debugger
      auditWithdrawSupplier({
        id,
        supplierId,
        auditStatus: type // 审核状态 0-未审核 1-审核通过 2-审核未通过
      }).then(res=>{
        this.$modal.msgSuccess("审核成功")
        this.openDetail.supplier =false
        this.getWithdrawListSupplier()
      }).catch( err =>{
        this.$modal.msgError("审核失败请重试")
      })
    },
    /** 供应商提现详情 */
    handleDetailSupplier(row) {
      // this.reset();
      const id = row.id
      console.log({row})
      this.currentAuditStatus = row.auditStatus
      withdrawInfoSupplier(id).then(response => {
        this.withdrawInfo.supplier = response.data;
        this.openDetail.supplier = true;
      });
    },

    handleDetailSeller (row) {
      const id = row.id
      withdrawInfoSeller(id).then(response => {
        this.withdrawInfo.seller = response.data;
        this.openDetail.seller = true;
      });
    },
    getWithdrawListSupplier () {
      this.loading = true;
      listWithdrawSupplier(this.queryParams.supplier).then(response => {
        this.withdrawList.supplier = response.rows;
        this.total.supplier = response.total;
        this.loading = false;
      });
    },
    getWithdrawListSeller () {
      this.loading = true;
      listWithdrawSeller(this.queryParams.seller).then(response => {
        this.withdrawList.seller = response.rows;
        this.total.seller = response.total;
        this.loading = false;
      });
    },
    handleClick () {
      this.$nextTick(()=>{
        this.$refs.serller && this.$refs.serller.doLayout()
        this.$refs.supperlier && this.$refs.supperlier.doLayout()
      })
    }
  }
}
</script>

<style scoped>

</style>
