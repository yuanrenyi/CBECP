<template>
  <div class="app-container">
<!--    <el-form
      class="query mb10"
      :model="queryParams"
      ref="queryForm"
      size="small"
      label-width="88px">
      <el-row :gutter="10">

        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="商品名称" prop="goodsName">
            <el-input
              v-model="queryParams.goodsName"
              placeholder="请输入商品编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="销售数量" prop="quantity">
            <el-input
              v-model="queryParams.quantity"
              placeholder="请输入外部编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="商品编码" prop="goodsCode">
            <el-input
              v-model="queryParams.goodsCode"
              placeholder="请输入商品编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="外部编码" prop="goodsOutCode">
            <el-input
              v-model="queryParams.goodsOutCode"
              placeholder="请输入外部编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="商品价格" prop="price">
            <el-input
              v-model="queryParams.price"
              placeholder="请输入外部编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="商品库存" prop="stockNum">
            <el-input
              v-model="queryParams.stockNum"
              placeholder="请输入外部编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <div class="h-40-middle">
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </div>
        </el-col>
      </el-row>
    </el-form>-->
    <el-form
      class="query"
      :model="queryParams"
      ref="queryForm" size="small"
      label-width="88px">
      <el-row>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
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
<!--        <el-col v-if="checkRole(['admin'])" :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">-->
<!--          <el-form-item label="所属供应商" prop="supplierId">-->
<!--            <select-table-->
<!--              v-model="supplierIdQuery"-->
<!--              ref="supplierTableQuery"-->
<!--              :table="supplierTable"-->
<!--              :columns="supplierTable.columns"-->
<!--              :max-height="400"-->
<!--              :keywords="{label:'supplierName',value:'id'}"-->
<!--              @page-change="supplierPageChange"-->
<!--              @clear="resetQuerySupplier"-->
<!--              @focus="getSupplierList"-->
<!--              @radioChange="supplierChangeQuery"-->
<!--              @inputKeyword="filterSupplier"-->
<!--              radioTxt="#"-->
<!--              isShowPagination-->
<!--            ></select-table>-->
<!--          </el-form-item>-->
<!--        </el-col>-->

        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="商品编码" prop="goodsCode">
            <el-input
              v-model="queryParams.goodsCode"
              placeholder="请输入商品编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="外部编码" prop="goodsOutCode">
            <el-input
              v-model="queryParams.goodsOutCode"
              placeholder="请输入外部编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="商品名称" prop="goodsName">

            <el-input
              v-model="queryParams.goodsName"
              placeholder="请输入商品名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="商品条形码" prop="goodsBarcode">
            <el-input
              v-model="queryParams.goodsBarcode"
              placeholder="请输入商品条形码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>

<!--        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">-->
<!--          <el-form-item label="对接货品ID" prop="dockingGoodsId">-->
<!--            <el-input-->
<!--              v-model="queryParams.dockingGoodsId"-->
<!--              placeholder="请输入对接货品ID"-->
<!--              clearable-->
<!--              @keyup.enter.native="handleQuery"-->
<!--            />-->
<!--          </el-form-item>-->
<!--        </el-col>-->
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
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
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="品牌" prop="goodsBrand">
            <el-input
              v-model="queryParams.goodsBrand"
              placeholder="请输入品牌"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="分类" prop="goodsClassificationArr">
            <el-cascader
              placeholder="请选择分类"
              v-model="queryParams.goodsClassificationArr"
              :options="catelogList"
              :show-all-levels="false"
              :props="{
                  label: 'catalogName',
                  checkStrictly: true,
                  value: 'id',
                  emitPath: false
                }"
              clearable
              ref="chooseCatalog"
              @change="onChooseCatalog"
            >
            </el-cascader>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="推荐商品" prop="recommend">
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
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
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
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
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
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="产品包关联商品条码" prop="barCodeList">
            <el-input
              v-model="queryParams.barCodeList"
              placeholder="请输入产品包关联商品条码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="库存数量" prop="stock">
            <div class="flex flex-justify-between">
              <el-input
                style="width: 45%"
                v-model="queryParams.stockNumStart"
                placeholder="库存数量"
                clearable
              />
              <span> - </span>
              <el-input
                style="width: 45%"
                v-model="queryParams.stockNumEnd"
                placeholder="库存数量"
                clearable
              />
            </div>

          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="成本价" prop="stock">
            <div class="flex flex-justify-between">
              <el-input
                style="width: 45%"
                v-model="queryParams.costPriceStart"
                placeholder="成本价"
                clearable
              />
              <span> - </span>
              <el-input
                class="inlineBlock"
                style="width: 45%"
                v-model="queryParams.costPriceEnd"
                placeholder="成本价"
                clearable
              />
            </div>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
          <div class="h-40-middle">
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </div>
        </el-col>
      </el-row>
    </el-form>
    <el-table
      border
      stripe
      v-loading="loading"
      @row-click="rowClick"
      header-cell-class-name="table-header-cell"
      :data="goodsSaleList">
<!--      <el-table-column type="index" width="55" align="center" />-->
      <el-table-column label="商品名称" align="center" prop="goodsName" />
      <el-table-column label="外部编码" align="center" prop="goodsOutCode" />
      <el-table-column label="商品编码" align="center" prop="goodsBarcode" />
      <el-table-column label="销售数量" align="center" prop="quantity" />
      <el-table-column label="销售金额" align="center" prop="orgIncome" />
      <el-table-column label="商品价格" align="center" prop="price" />
      <el-table-column label="商品库存" align="center" prop="stockNum" />
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getGoodsSale"
    />
    <a-modal :title="currentRow.goodsName + '近7日价格'" :visible.sync="open" width="65%" append-to-body @cancel="open = false" :footer="false">
      <line-chart :chart-data="goods7daysPrice" />
    </a-modal>
  </div>
</template>

<script>
import {goodsSale, goodsSalePrice7d} from "@/api/goods/goods";
import LineChart from "@/views/dashboard/LineChart.vue";
import storeHouseMix from "@/mixins/storeHouseMix";
import supplierMix from "@/mixins/supplierMix";
import { listInfo } from "@/api/system/catalog";
export default {
  name: "GoodSaleList",
  components: {LineChart},
  mixins: [ storeHouseMix, supplierMix],
  dicts: ['goods_shelves_status', 'goods_is_recommend','goods_if_package', 'goods_if_buildby_oneself','goods_has_pic'],
  created() {
    this.getGoodsSale()
    this.getCatelogList();
  },
  data() {
    return {
      loading: false,
      open: false,
      /*queryParams: {
        pageNum:1,
        pageSize: 20,
        goodsName:undefined,
        goodsOutCode:undefined,
        goodsBarcode:undefined,
        quantity:undefined,
        price:undefined,
        stockNum:undefined,
      },*/
      // 查询参数
      queryParams: {
        costPriceStart: null,
        costPriceEnd: null,
        stockNumStart: null,
        stockNumEnd: null,
        sellerId: null, // 销售方id
        sellerOnshelfStatus: null, // 销售方上架状态
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
        picVersion: null,
        hasPic: null,
      },
      catelogList: [],
      goods7daysPrice: {
        xAxis: [],
        data: []
      },
      goodsSaleList: [],
      currentRow: {
        goodsName: ''
      },
      total: 0
    }
  },
  methods: {
    rowClick(row) {
      console.log(row.goodsId)
      this.currentRow = row
      goodsSalePrice7d({goodsId: row.goodsId}).then(res => {
        const xAxis  = []
        const data  = []
        res.data.forEach(item => {
          xAxis.push(item.optTime)
          data.push(item.afterPrice)
        })
        this.goods7daysPrice = {xAxis,data}
        console.log(this.goods7daysPrice)
        this.open = true
      })
      // this.open = true
    },
    getGoodsSale(){
      this.loading = true
      goodsSale(this.queryParams).then(res =>{
        this.goodsSaleList = res.rows
        this.total = res.total;
        this.loading = false
      }).catch(err => {
        this.loading = false
      })
    },
    //获取商品分类
    getCatelogList() {
      this.loading = true;
      listInfo().then(response => {
        this.catelogList = response.data;
      });
    },
    onChooseCatalog(val) {
      this.$refs.chooseCatalog.dropDownVisible = false
    },
    handleQuery() {
      this.getGoodsSale()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.resetQueryStoreHouse()
      if (this.checkRole(['admin'])) {
        this.resetQuerySupplier()
        this.resetQuerySeller()
      }
      this.queryParams.costPriceEnd = null
      this.queryParams.costPriceStart = null
      this.queryParams.stockNumStart = null
      this.queryParams.stockNumEnd = null
      this.handleQuery();
    },
  }
}
</script>

<style scoped>

</style>
