<template>
  <div class="app-container">
    <el-form class="query" :model="queryParams" ref="queryForm" size="small"  v-show="showSearch" label-width="98px">
      <el-row>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="mb10">
          <el-form-item label="商品名称" prop="goodsId">
            <el-input
              v-model="queryParams.goodsId"
              placeholder="请输入商品"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
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
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="mb10">
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
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="mb10">
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
          v-if="!checkRole(['admin']) && checkRole(['SELLER_ADMIN'])"
        >申请锁库</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      border
      v-loading="loading"
      :data="lockList"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="商品名称"  align="center" prop="goodsName" />
<!--      <el-table-column label="商品条形码" align="center" prop="goodsName" />-->
      <el-table-column label="申请锁定库存数量" align="center" prop="stockLockNum" />
      <el-table-column label="实际更改库存数量" align="center" prop="stockApplyNum" />
<!--      <el-table-column label="申请锁库存的销售方ID" align="center" prop="sellerId" />-->
      <el-table-column label="申请销售方" align="center" prop="sellerName" />
      <el-table-column label="锁定库存状态" align="center" prop="lockStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.stock_lock_status" :value="scope.row.lockStatus"/>
        </template>
      </el-table-column>
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:lock:query']"
          >详情</el-button>
        </template>

      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改商品锁库存对话框 -->
    <a-modal
      :zIndex="1001"
      :title="title" :visible.sync="open" width="75%" append-to-body @cancel="open=false">
      <el-form class="query" ref="goodsQueryform" :model="goodQueryParams" :rules="rules" label-width="118px" label-position="left">
        <el-row :gutter="10">
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" class="mb10">
            <el-form-item label="商品名称" prop="goodsName">
              <el-input
                v-model="goodQueryParams.goodsName"
                placeholder="请输入商品名称"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" class="mb10">
            <el-form-item label="商品条形码" prop="goodsBarcode">
              <el-input
                v-model="goodQueryParams.goodsBarcode"
                placeholder="请输入商品条形码"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" class="ml20">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleGoodsQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetGoodsQuery">重置</el-button>
          </el-col>
        </el-row>
        <el-divider content-position="center">商品信息</el-divider>
      </el-form>
      <vxe-table
        border
        show-overflow
        keep-source
        ref="xTable"
        :column-config="{resizable: true}"
        v-loading="goodsloading"
        :data="goodsList"
        :row-config="{isHover: true}"
        :edit-config="{ trigger: 'manual', mode: 'row', autoClear: false}">
        <vxe-column type="seq" width="60" fixed="left"></vxe-column>
        <vxe-column field="goodsName"  show-header-overflow="tooltip" title="商品名称" fixed="left" min-width="120"></vxe-column>
        <vxe-column field="goodsBarcode" show-overflow  title="商品条形码" fixed="left" width="100"></vxe-column>
        <vxe-column field="stockLockNum" show-overflow title="已锁定库存数量" width="120" ></vxe-column>
        <vxe-column field="needLockNumber" show-overflow title="申请锁定数量" width="130" :edit-render="{}">
          <template #default="{ row }">
            <span>{{ row.needLockNumber }}</span>
          </template>
          <template #edit="{ row }">
            <vxe-input v-model.number="row.needLockNumber" @change="checkInput($event,row)" placeholder="锁定库存数量" type="number" :min="1" :max="row.stockNum - row.stockLockNum" exponential clearable></vxe-input>
          </template>
        </vxe-column>
        <vxe-column field="goodsCode" show-overflow title="商品编码" width="100" ></vxe-column>
        <vxe-column field="supplierName" show-overflow title="所属供应商" width="100" ></vxe-column>
        <vxe-column field="stockNum" title="库存数量" width="100"></vxe-column>

        <vxe-column title="操作" width="200" fixed="right">
          <template #default="{ row }">
            <template v-if="$refs.xTable.isActiveByRow(row)">
              <vxe-button @click="saveRowEvent(row)">确认申请</vxe-button>
              <vxe-button @click="cancelRowEvent(row)">取消</vxe-button>
            </template>
            <template v-else>
              <vxe-button @click="editRowEvent(row)">申请锁库</vxe-button>
            </template>
          </template>
        </vxe-column>
      </vxe-table>
      <pagination
        v-show="goodsTotal>0"
        :total="goodsTotal"
        :page.sync="goodQueryParams.pageNum"
        :limit.sync="goodQueryParams.pageSize"
        @pagination="getGoodsList"
      />
      <div slot="footer" class="dialog-footer">
<!--        <el-button type="primary" @click="submitForm">确 定</el-button>-->
<!--        <el-button @click="cancel">取 消</el-button>-->
      </div>
    </a-modal>
  </div>
</template>

<script>
import { listLock, getLock, delLock, addLock, updateLock } from "@/api/seller/goodsLock";
import {listGoods} from "@/api/goods/goods";
import {listInfo} from "@/api/order/orderinfo";
import {checkPermi, checkRole} from "@/utils/permission";

export default {
  name: "Lock",
  dicts: ['goods_shelves_status', 'stock_lock_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      goodsloading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedGoodsInfo: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      goodsTotal: 0,
      // 商品锁库存表格数据
      lockList: [],
      goodsList: [],
      // 商品信息表格数据
      goodsInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      goodQueryParams: {
        pageNum: 1,
        pageSize: 10,
        lockStatus: undefined,
        goodsBarcode: undefined,
        goodsName: undefined,
      },
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
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getGoodsList()
  },
  mounted() {
    console.log(this.checkRole(['admin']))
    console.log(this.checkRole(['SELLER_ADMIN']))
  },
  methods: {
    checkPermi,
    checkRole,
    editRowEvent (row) {
      const $table = this.$refs.xTable
      $table.setActiveRow(row)
    },
    /* 确认锁库存 */
    saveRowEvent (row) {
      if(row.needLockNumber == 0) return this.$notification.warn({message:'请输入锁定库存的数量', class: 'g-notification'})
      const $table = this.$refs.xTable
      $table.clearActived().then(() => {
        console.log(row)
        // lockStatus 锁定库存状态 0-已取消 1-锁定中 2-已完成
        const { id:goodsId, goodsName, needLockNumber:stockLockNum} = row
        let params = {
          goodsId,
          goodsName,
          stockLockNum,
          lockStatus: 1
        }
        addLock(params).then(res=>{
          this.$modal.msgSuccess('申请成功')
          this.getGoodsList()
          this.getList()
        })
      })
    },
    cancelRowEvent (row) {
      const $table = this.$refs.xTable
      $table.clearActived()
        .then(() => $table.revertData(row))
    },
    checkInput(val,row) {
      console.log({val, row})
      if(val.value> row.stockNum - row.stockLockNum) {
        this.$notification.warn({message:'库存数量不足,自动更改为可取的最大值', class: 'g-notification'})
        // row.needLockNumber = row.stockNum - row.stockLockNum
        // val.$event.target.value = row.stockNum - row.stockLockNum
        val.$event.target.blur()
      }
    },
    /** 查询商品信息列表 */
    getGoodsList() {
      this.goodsloading = true;
      listGoods(this.goodQueryParams).then(response => {
        response.rows.forEach( item =>{
          item.needLockNumber = 0
        })
        this.goodsList = response.rows;
        this.goodsTotal = response.total;
        this.goodsloading = false;
      }).catch(err => {
        this.goodsloading = false
      })
    },
    /** 商品搜索按钮操作 */
    handleGoodsQuery() {
      this.goodQueryParams.pageNum = 1;
      this.getGoodsList();
    },
    /** 商品重置按钮操作 */
    resetGoodsQuery() {
      this.resetForm("goodsQueryform");
      this.handleGoodsQuery();
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
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
      this.goodsInfoList = [];
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
      this.title = "添加商品锁库存";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getLock(id).then(response => {
        // this.form = response.data;
        // this.goodsInfoList = response.data.goodsInfoList;
        this.open = true;
        this.title = "商品锁库存详情";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.goodsInfoList = this.goodsInfoList;
          if (this.form.id != null) {
            updateLock(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLock(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除商品锁库存编号为"' + ids + '"的数据项？').then(function() {
        return delLock(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 复选框选中数据 */
    handleGoodsInfoSelectionChange(selection) {
      this.checkedGoodsInfo = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/lock/export', {
        ...this.queryParams
      }, `lock_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style scoped>
.el-table ::v-deep .el-loading-mask {
 z-index: 999!important;
}
</style>
