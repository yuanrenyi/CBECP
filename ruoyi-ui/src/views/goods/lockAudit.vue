<template>
<div class="app-container">
  <el-form class="mb10" :model="queryParams" inline ref="queryForm" size="small"  v-show="showSearch" label-width="98px">
        <el-form-item label="商品名称" prop="goodsId">
          <el-input
            style="width: 300px"
            v-model="queryParams.goodsId"
            placeholder="请输入商品"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
      <!--        <el-col :xs="24" :sm="12" :md="8" :lg="4.8" :xl="4" class="mb10">
                <el-form-item label="申请锁定库存数量" prop="stockLockNum">
                  <el-input
                    v-model="queryParams.stockLockNum"
                    placeholder="请输入申请锁定库存数量"
                    clearable
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="4.8" :xl="4" class="mb10">
                <el-form-item label="实际更改库存数量" prop="stockApplyNum">
                  <el-input
                    v-model="queryParams.stockApplyNum"
                    placeholder="请输入实际更改库存数量"
                    clearable
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="4.8" :xl="4" class="mb10">
                <el-form-item label="申请锁库存的销售方ID" prop="sellerId">
                  <el-input
                    v-model="queryParams.sellerId"
                    placeholder="请输入申请锁库存的销售方ID"
                    clearable
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
              </el-col>-->
        <el-form-item label="锁定库存状态" prop="lockStatus">
          <el-select v-model="queryParams.lockStatus" placeholder="请选择锁定库存状态" clearable>
            <el-option
              v-for="dict in dict.type.stock_lock_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
    <el-form-item label="" class="ml10">
      <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
    </el-form-item>

  </el-form>

  <el-table
    border
    v-loading="loading"
    :data="lockList">
    <el-table-column type="selection" width="55" align="center" />
    <el-table-column label="商品名称" align="center" prop="goodsName" />
<!--    <el-table-column label="商品条形码" align="center" prop="goodsName" />-->
    <el-table-column label="申请锁定库存数量" align="center" prop="stockLockNum" />
    <el-table-column label="实际更改库存数量" align="center" prop="stockApplyNum" />
<!--    <el-table-column label="实际更改库存数量" align="center" prop="stockApplyNum" />-->
    <!--      <el-table-column label="申请锁库存的销售方ID" align="center" prop="sellerId" />-->
    <el-table-column label="申请销售方" align="center" prop="sellerName" />
    <el-table-column label="锁定库存状态" align="center" prop="lockStatus">
      <template slot-scope="scope">
        <dict-tag :options="dict.type.stock_lock_status" :value="scope.row.lockStatus"/>
      </template>
    </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            :disabled="scope.row.lockStatus == 2"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleAudit(scope.row)"
            v-hasPermi="['system:lock:query']"
          >审核</el-button>
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
  <a-modal
    title="库存锁定审核"
    :zIndex="1001"
    :visible.sync="open"
    width="65%" append-to-body>
    <el-form class="edit" ref="form" :model="form" :rules="rules" label-width="98px" label-position="right">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="16">
          <el-form-item label="商品名称"  prop="goodsName">
            <el-input
              disabled
              v-model="form.goodsName"
              placeholder="商品名称"
            />
          </el-form-item>
        </el-col>
<!--        <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">-->
<!--          <el-form-item label="商品条形码"  prop="goodsBarcode">-->
<!--            <el-input-->
<!--              disabled-->
<!--              v-model="form.goodsBarcode"-->
<!--              placeholder="商品条形码"-->
<!--            />-->
<!--          </el-form-item>-->
<!--        </el-col>-->
        <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
          <el-form-item label="申请锁定库存数量"  prop="stockLockNum">
            <el-input
              disabled
              v-model="form.stockLockNum"
              placeholder="申请锁定库存数量"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
          <el-form-item label="实际更改库存数量"  prop="stockApplyNum">
            <el-input-number
              style="min-width: 120px"
              controls-position="right"
              :max="form.stockLockNum"
              v-model="form.stockApplyNum"
              placeholder="实际更改库存数量"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确认审核</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </a-modal>
</div>
</template>

<script>
import {addLock, getLock, listLock, updateLock} from "@/api/seller/goodsLock";

export default {
  name: "lockAudit",
  dicts:['stock_lock_status'],
  data () {
    return {
      total:0,
      showSearch:true,
      open: false,
      loading: false,
      lockList:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        goodsId: null,
        stockLockNum: null,
        stockApplyNum: null,
        sellerId: null,
        lockStatus: null,
      },
      form: {
        id: null,
        goodsId: null,
        stockLockNum: null,
        stockApplyNum: null,
        sellerId: null,
        lockStatus: null, // lockStatus 锁定库存状态 0-已取消 1-锁定中 2-已完成
      },
      rules: {
        stockApplyNum:[
          {required: true, message:'请输入实际锁库存数量',trigger: 'blur'}
        ]
      }

    }
  },
  created() {
    this.getList()
  },
  methods: {
    handleAudit(row) {
      // todo 接口待完善
      // getLock(row.id).then(response => {
      //   this.form = response.data;
      //   this.form.stockApplyNum = this.form.stockLockNum
      //   this.open = true;
      // });
      this.form = row
      this.form.stockApplyNum = this.form.stockLockNum
      this.open = true
    },
    /** 查询商品锁库存列表 */
    getList() {
      this.loading = true;
      listLock(this.queryParams).then(response => {
        this.lockList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    submitForm () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.lockStatus = 2
          updateLock(this.form).then(response => {
            this.$modal.msgSuccess("审核成功");
            this.open = false;
            this.reset()
            this.getList();
          });
        }
      });
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
    cancel() {
      this.open =false
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        goodsId: null,
        stockLockNum: null,
        stockApplyNum: null,
        sellerId: null,
        lockStatus: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
  }
}
</script>

<style scoped>

</style>
