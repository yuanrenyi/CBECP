<template>
  <div class="app-container">
    <el-form
      :style="`width: 100%; max-height: ${maxHeight}`"
      key="queryForm"
      class="query mb10 transition-all"
      :model="queryParams"
      ref="queryForm" size="small"
      label-width="88px">
      <el-row>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="mb10">
          <el-form-item label="所在仓库" prop="storehouseId">
            <select-table
              v-model="storehouseIdQuery"
              ref="storeHouseQuery"
              :table="storeHouseTable"
              :columns="storeHouseTable.columns"
              :max-height="400"
              :keywords="{label:'storehouseName',value:'id'}"
              @page-change="storeHousePageChange"
              @radioChange="storeHouseChangeQuery"
              @inputKeyword="filterStoreHouse"
              @clear="resetQueryStoreHouse"
              radioTxt="#"
              isShowPagination
            ></select-table>
          </el-form-item>
        </el-col>
        <el-col v-if="checkRole(['admin'])" :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="mb10">
          <el-form-item label="所属供应商" prop="supplierId">
            <select-table
              v-model="supplierIdQuery"
              ref="supplierTableQuery"
              :table="supplierTable"
              :columns="supplierTable.columns"
              :max-height="400"
              :keywords="{label:'supplierName',value:'id'}"
              @page-change="supplierPageChange"
              @clear="resetQuerySupplier"
              @focus="getSupplierList"
              @radioChange="supplierChangeQuery"
              @inputKeyword="filterSupplier"
              radioTxt="#"
              isShowPagination
            ></select-table>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="mb10">
          <el-form-item label="商品编码" prop="goodsCode">
            <el-input
              v-model="queryParams.goodsCode"
              placeholder="请输入商品编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="mb10">
          <el-form-item label="外部编码" prop="goodsOutCode">
            <el-input
              v-model="queryParams.goodsOutCode"
              placeholder="请输入外部编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="mb10">
          <el-form-item label="商品名称" prop="goodsName">

            <el-input
              v-model="queryParams.goodsName"
              placeholder="请输入商品名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="mb10">
          <el-form-item label="商品条形码" prop="goodsBarcode">
            <el-input
              v-model="queryParams.goodsBarcode"
              placeholder="请输入商品条形码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="mb10">
          <el-form-item label="对接货品ID" prop="dockingGoodsId">
            <el-input
              v-model="queryParams.dockingGoodsId"
              placeholder="请输入对接货品ID"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="mb10">
          <el-form-item label="上架状态" prop="onShelfStatus">
            <el-select v-model="queryParams.onShelfStatus" placeholder="请选择上架状态" clearable>
              <el-option
                v-for="dict in dict.type.goods_shelves_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="mb10">
          <el-form-item label="品牌" prop="goodsBrand">
            <el-input
              v-model="queryParams.goodsBrand"
              placeholder="请输入品牌"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="mb10">
          <el-form-item label="分类" prop="goodsClassificationArr">
            <el-input
              v-model="queryParams.goodsClassification"
              placeholder="请输入分类"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="mb10">
          <el-form-item label="是否推荐为推荐商品" prop="recommend">
            <template #label>
              <el-tooltip
                placement="top-start"
                :offset="25"
                :arrowOffset="30"
                content="是否推荐为推荐商品">
                <span>是否推荐为推荐商品</span>
              </el-tooltip>
            </template>
            <el-select v-model="queryParams.recommend" placeholder="请选择是否推荐为推荐商品" clearable>
              <el-option
                v-for="dict in dict.type.goods_is_recommend"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="4.8" class="mb10">
          <el-form-item label="是否产品包" prop="ifPackage">
            <el-select v-model="queryParams.ifPackage" placeholder="请选择是否产品包" clearable>
              <el-option
                v-for="dict in dict.type.goods_if_package"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="4.8" class="mb10">
          <el-form-item label="是否自建" prop="ifBuildByOneself">
            <el-select v-model="queryParams.ifBuildByOneself" placeholder="请选择是否自建" clearable>
              <el-option
                v-for="dict in dict.type.goods_if_buildby_oneself"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="4.8" class="mb10">
          <el-form-item label="产品包关联商品条码" prop="barCodeList">
            <template #label>
              <el-tooltip
                placement="top-start"
                :offset="25"
                :arrowOffset="30"
                content="产品包关联商品条码">
                <span>产品包关联商品条码</span>
              </el-tooltip>
            </template>
            <el-input
              v-model="queryParams.barCodeList"
              placeholder="请输入产品包关联商品条码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" class="mb10">
          <div class="h-40-middle">
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </div>
        </el-col>
      </el-row>
      <!--
            </el-form-item>
            <el-form-item label="HS编码" prop="hsCode">
              <el-input
                v-model="queryParams.hsCode"
                placeholder="请输入HS编码"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            -->
    </el-form>
    <el-row :gutter="10" class="mb8">
      <right-toolbar2 :showSearch="showSearch" @toggleSearch="changeMaxH" @queryTable="getList"></right-toolbar2>
    </el-row>
    <el-table
      stripe
      border
      header-cell-class-name="table-header-cell"
      v-loading="loading"
      :data="goodsList"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="商品ID" align="center" prop="id" />-->
      <el-table-column label="所在仓库" align="center" prop="storehouseName"/>
      <el-table-column
        v-if="checkRole(['admin'])"
        label="所属供应商" align="center"
        min-width="120"
        prop="supplierName"/>
      <el-table-column label="商品编码" min-width="120" align="center" prop="goodsCode"/>
      <el-table-column label="外部编码" align="center" min-width="120" prop="goodsOutCode"/>
      <el-table-column label="商品名称" align="center" prop="goodsName"/>
      <el-table-column label="商品条形码" align="center" min-width="120" prop="goodsBarcode"/>
      <el-table-column label="品牌" align="center" prop="goodsBrand"/>
      <el-table-column label="分类" align="center" prop="goodsClassificationArr"/>
      <el-table-column label="单位" align="center" prop="goodsUnit"/>
      <el-table-column label="重量" align="center" prop="goodsWeight"/>
      <el-table-column label="成本价" align="center" prop="costPrice"/>
      <el-table-column label="税率%" align="center" prop="taxRate"/>
      <el-table-column label="库存数量" align="center" prop="stockNum"/>
      <el-table-column label="锁定库存数量" align="center" prop="stockLockNum" width="90">
        <template #header="{column, $index}">
          <el-tooltip
            content="锁定库存数量"
            :offset="tooltip.stockLockNumTableTipOffsetX"
            :arrowOffset="tooltip.stockLockNumTableTipArrowOffsetX"
            ref="stockLockNumTableTip"
            :disabled="tooltip.stockLockNumTableTip"
            placement="top-start">
            <span class="ellipse-text">锁定库存数量</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="对接货品ID" align="center" prop="dockingGoodsId" min-width="130"/>
      <el-table-column label="HS编码" align="center" prop="hsCode" width="90"/>
      <el-table-column label="JD指导价" align="center" prop="jdGuidancePrice" width="90"/>
      <el-table-column label="TM指导价" align="center" prop="tmGuidancePrice" width="90"/>
      <el-table-column label="WY指导价" align="center" prop="wyGuidancePrice" width="90"/>
      <el-table-column label="建议零售价" align="center" prop="msrp" width="95"/>
      <el-table-column label="裸价" align="center" prop="nakedPrice"/>
      <el-table-column label="续重运费" align="center" prop="freightRenewalAmount"/>
      <el-table-column label="毛重" align="center" prop="grossWeight"/>
      <el-table-column label="运费" align="center" prop="freightAmount"/>
      <el-table-column label="最终运费" align="center" prop="finalFreightAmount"/>
      <el-table-column label="备注" align="center" width="150" prop="remark" show-overflow-tooltip/>
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <!--              <el-button
                          size="mini"
                          type="text"
                          icon="el-icon-edit"
                          @click="handleUpdate(scope.row)"
                          v-hasPermi="['goods:info:edit']"
                        >修改
                        </el-button>-->
          <!--<el-dropdown v-has-permi="['goods:info:edit']" size="small" @command="chooseUpdateType($event, scope.row)">-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleRollback(scope.row)"
            v-hasPermi="['goods:info:edit']"
          >回滚
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
  </div>
</template>

<script>
import {listGoods, getGoods, delGoods, addGoods, updateGoods, rollbackGoods} from "@/api/goods/goods";
import {checkPermi, checkRole} from "@/utils/permission";
import {listSupplier} from "@/api/suppiler/supplier";
import {listStorehouse} from "@/api/seller/storehouse";
import {checkOverflow} from "@/utils/ruoyi";

import storeHouseMix from "@/mixins/storeHouseMix";
import supplierMix from "@/mixins/supplierMix";
import {uploadFile} from "@/utils/request";
export default {
  name: "GoodsRollbackList",
  mixins: [ storeHouseMix, supplierMix],
  dicts: ['goods_shelves_status', 'goods_is_recommend','goods_if_package', 'goods_if_buildby_oneself'],
  data() {
    return {
      maxHeight:'35vh',
      tooltip: {
        stockLockNumTableTipOffsetX: 0,
        stockLockNumTableTipArrowOffsetX: 35,
        stockLockNumTableTip: true
      },
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
      // 商品信息表格数据
      goodsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        storehouseId: null,
        supplierId: null,
        goodsCode: null,
        goodsOutCode: null,
        goodsName: null,
        goodsBarcode: null,
        goodsBrand: null,
        goodsClassificationArr: null,
        goodsUnit: null,
        goodsWeight: null,
        costPrice: null,
        taxRate: null,
        stockNum: null,
        stockLockNum: null,
        dockingGoodsId: null,
        hsCode: null,
        jdGuidancePrice: null,
        tmGuidancePrice: null,
        wyGuidancePrice: null,
        msrp: null,
        nakedPrice: null,
        onShelfStatus: null,
        freightRenewalAmount: null,
        grossWeight: null,
        freightAmount: null,
        finalFreightAmount: null,
        recommend: null,
        ifPackage: null,
        ifBuildByOneself: null,
        barCodeList: null,
        delFlag: 2
      },
      // 表单参数
      form: {},
      picInfo: [
        {
          picUrl: '',
          picType: '1', // 主图
        },
        {
          picUrl: '',
          picType: '2',// 详情图
        },
        {
          picUrl: '',
          picType: '3',// 视频
        },
        {
          picUrl: '',
          picType: '4', // 缩略图
        }
      ],
      supplierOptionList: [],

      query: {
        storehouseName: '',
        supplierName: ''
      },
      // 表单校验
      rules: {
        goodsName: [
          {required: true, message: "商品名称", trigger: "blur"}
        ],
        goodsCode: [
          {required: true, message: "商品编码", trigger: "blur"}
        ],
        storehouseId: [
          {required: true, message: "请选择所在仓库", trigger: "change"}
        ],
        goodsBarcode: [
          {required: true, message: "请输入商品条形码", trigger: "blur"}
        ],

      }
    };
  },
  created() {
    this.getList();
    this.getStoreHouseList()
    if (this.checkRole(['admin'])) {
      this.getSupplierList()
    }
  },
  mounted() {
    this.$nextTick(() => {
      let resizeObserver = new ResizeObserver((entries) => {
        entries.forEach((item, index) => {
          item.target
          this.checkOverflow(item.target)
          console.log(item.contentRect)
        })
      })
      for (const key in this.$refs) {
        if (key.includes('Tip')) {
          this.$refs[key].$el.parentNode.__refName = key
          resizeObserver.observe(this.$refs[key].$el.parentNode)
        }
        // this.$refs[key].$el.parentNode.addEventListener('resize', this.chekOverflow)
      }
    })
  },
  methods: {
    chooseUpdateType (commond,item) {
      console.log({commond,item})
      if(commond === 'showUpdateDialog') {
        this.handleUpdate(item)
      }
      if(commond === 'goEditPage') {
        // 前往修改商品页面
        this.$router.push({path: `/goods/goodUpdate/${item.id}`})
      }
    },
    changeMaxH (val) {
      this.maxHeight = val? '30vh' : '0'
      this.showSearch = val
    },
    checkOverflow,
    changeRow(val, row) {
      updateGoods(row).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.open = false;
        this.getList();
      });
    },
    // 选中仓库
    storeHouseChange(val) {
      this.supplierOptionList = []
      if (val.id) {
        this.form.storehouseId = val.id
        let obj = {
          supplierId: val.supplierId,
          supplierName: val.supplierName
        }
        this.supplierOptionList.push(obj)
        this.form.supplierId = val.supplierId
      } else {
        this.form.storehouseId = ''
      }
      console.log('storehouseId', this.form.storehouseId)
    },
    storeHouseChangeQuery(val) {
      this.queryParams.storehouseId = val && val.id ? val.id : undefined
    },
    supplierChangeQuery(val) {
      this.queryParams.supplierId = val && val.id ? val.id : undefined
      this.handleQuery()
    },
    checkPermi,
    checkRole,
    /** 查询商品信息列表 */
    getList() {
      this.loading = true;
      listGoods(this.queryParams).then(response => {
        this.goodsList = response.rows;
        this.total = response.total;
        this.loading = false;
      }).catch(err => {
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.resetQueryStoreHouse()
      if (this.checkRole(['admin'])) {
        this.resetQuerySupplier()

      }
      this.handleQuery();
    },
    // 清空查询时的 supplierId
    resetQuerySupplier() {
      this.queryParams.supplierId = undefined
    },
    // 清空查询时的 storehouseId
    resetQueryStoreHouse() {
      this.queryParams.storehouseId = undefined
    },
    // 清空表单中的  storehouseId
    resetFormStoreHouse() {
      this.storehouseId = ''
      this.form.storehouseId = ''
      this.form.supplierId = ''
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
      this.title = "添加商品信息";
    },
    handlePageAdd () {
      this.$router.push({name:'GoodAdd'})
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getGoods(id).then(response => {
        this.form = response.data;
        this.handleShowPicture()
        this.setStoreHouseInfo()
        this.open = true;
        this.title = "修改商品信息";
      });
    },
    // 回显 仓库名称及 供应商名称
    async setStoreHouseInfo() {
      this.supplierOptionList = []
      let obj = {
        supplierId: this.form.supplierId,
        supplierName: this.form.supplierName
      }
      this.supplierOptionList.push(obj)
      // 需要根据仓库名 查询一遍仓库
      await this.filterStoreHouse(this.form.storehouseName)
      // todo 可以优化 且仓库过多的时候需要用id查询到精确的列表
      this.storehouseIdForm = this.form.storehouseId
      this.$refs.storeHouseForm.searchKey = this.form.storehouseName
    },
    // 回显图片
    handleShowPicture() {
      this.form.picInfo.forEach((item, index) => {
        if (item.picType == 1 && item.picUrl) {
          this.picInfo[0].picUrl = item.picUrl
        }
        if (item.picType == 2 && item.picUrl) {
          this.picInfo[1].picUrl = item.picUrl
        }
        if (item.picType == 3 && item.picUrl) {
          this.picInfo[2].picUrl = item.picUrl
        }
        if (item.picType == 4 && item.picUrl) {
          this.picInfo[3].picUrl = item.picUrl
        }
      })
    },
    // 提交图片
    handlePicture() {
      let res = []
      for (let i = 0; i < this.picInfo.length; i++) {
        let item = this.picInfo[i]
        if (item.picUrl) {
          let obj = {
            ...item,
            goodsId: this.form.goodsId
          }
          res.push(obj)
        }
      }
      this.form.picInfo = res
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.handlePicture()
          if (this.form.id != null) {
            updateGoods(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGoods(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        } else {
          this.$notification.warn({
            message: "请完成表单必填项"
          })
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除商品条形码为"' + ids + '"的数据项？').then(function () {
        return delGoods(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },

    handleRollback(row) {
      const id = row.id;
      const barCode = row.goodsBarcode;
      this.$modal.confirm('是否回滚条形码为"' + barCode + '"的商品？').then(function () {
          row.delFlag = 1;
          return rollbackGoods(row);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("回滚成功");
      }).catch(() => {

      });
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('system/goods/export', {
        ...this.queryParams
      }, `goods_${new Date().getTime()}.xlsx`)
    },
    handleImport () {

    },
    /* 导入图片 start*/
    handleImportImgs () {
      let uploadInput = document.querySelector("#upload-input")
      uploadInput.click()
    },
    uploadZipFile (file) {
      let url = '/system/goods/goodspic/batch/upload'
      uploadFile(url, 'zipFile',file,{
        timeout: 30 * 1000
      }).then(res=>{
        console.log(res)
      })
    },
    getFile(e) {
      let files = e.target.files
      let file = files[0]
      if (!/\.(zip)$/.test(event.target.value)) {
        return  this.$notification.warn({message: "文件格式不正确请上传zip文件",duration: 5* 1000});
      }
      this.uploadZipFile(file)
      e.target.value = '';
    },
    /* 导入图片 end */
    // 表单重置
    reset() {
      this.picInfo = [
        {
          picUrl: '',
          picType: '1', // 主图
        },
        {
          picUrl: '',
          picType: '2',// 详情图
        },
        {
          picUrl: '',
          picType: '3',// 视频
        },
        {
          picUrl: '',
          picType: '4', // 缩略图
        }
      ]
      this.form = {
        storehouseName: null,
        id: null,
        storehouseId: null,
        supplierId: null,
        goodsCode: null,
        goodsOutCode: null,
        goodsName: null,
        goodsBarcode: null,
        goodsBrand: null,
        goodsClassificationArr: null,
        goodsUnit: null,
        goodsWeight: null,
        costPrice: null,
        taxRate: null,
        stockNum: null,
        stockLockNum: null,
        dockingGoodsId: null,
        hsCode: null,
        jdGuidancePrice: null,
        tmGuidancePrice: null,
        wyGuidancePrice: null,
        msrp: null,
        nakedPrice: null,
        onShelfStatus: 0,
        freightRenewalAmount: null,
        grossWeight: null,
        freightAmount: null,
        finalFreightAmount: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        recommend: 0,
        ifPackage: 0,
        ifBuildByOneself: 0,
        barCodeList: null,
        numberList: null,
        vatRate: null,
        tariffRate: null,
        exciseTaxRate: null,
      };

      this.resetForm("form");
    },
  }
};
</script>

<style scoped lang="scss">
.el-table .el-switch ::v-deep .el-switch__label--left {
  color: #cccccc !important;
}
.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.demonstration {
  display: block;
  color: #8492a6;
  font-size: 14px;
  margin-bottom: 20px;
}
</style>
