<!--商品聚合列表-->
<template>
  <div class="app-container flex flex-column">
    <div>
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
          <el-col v-if="checkRole(['admin'])" :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
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

          <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
            <el-form-item label="对接货品ID" prop="dockingGoodsId">
              <el-input
                v-model="queryParams.dockingGoodsId"
                placeholder="请输入对接货品ID"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
            <el-form-item label="上架状态" prop="sellerOnshelfStatus">
              <el-select v-model="queryParams.sellerOnshelfStatus" placeholder="请选择上架状态" clearable>
                <el-option
                  v-for="dict in dict.type.seller_onshelf_status"
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
                v-model="goodsClassificationArr"
                :options="catelogList"
                :props="{
                  multiple: true,
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
            <el-form-item label="是否推荐为推荐商品" prop="recommend">
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
    </div>
    <good-list :loading="loading" ref="goodListRef" class="flex-1" :list="goodsList" @getDetail="getDetail">
    </good-list>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <a-modal
      title="商品详情" :visible.sync="open"
      style="min-width: 700px"
      dialogClass="detail-dialog"
      width="55%" :destroyOnClose="true" append-to-body
      :zIndex="1001"
      :footer="null"
      @cancel="open = false">
        <good-detail :good-detail="goodDetailInfo" />
    </a-modal>
  </div>
</template>

<script>
import {goodSellerDetail, goodSellerList, listGoods} from "@/api/goods/goods";
import {checkPermi, checkRole} from "@/utils/permission";
import {checkOverflow} from "@/utils/ruoyi";
import goodList from "@/views/seller/coms/goodSellerList.vue";
import goodDetail from "@/views/seller/coms/goodDetail.vue";
import storeHouseMix from "@/mixins/storeHouseMix";
import supplierMix from "@/mixins/supplierMix";
import {listInfo} from "@/api/system/catalog";
export default {
  name: "GoodsSellList",
  components: {
    goodList,
    goodDetail
  },
  mixins: [ storeHouseMix, supplierMix],
  dicts: ['goods_shelves_status', 'goods_is_recommend','goods_if_package', 'goods_if_buildby_oneself', 'seller_onshelf_status'],
  watch: {
    goodsClassificationArr:  function (val){
      console.log({val})
      this.queryParams.goodsClassification = val.join(',')
    }
  },
  data() {
    return {
      catelogList: [],
      showSearch: true,
      // 查询参数
      loading: false,
      goodsClassificationArr: [],
      queryParams: {
        costPriceStart: null,
        costPriceEnd: null,
        stockNumStart: null,
        stockNumEnd: null,
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
        sellerOnshelfStatus: null,
      },
      total:0,
      open: false,
      goodsList:[],
      goodDetailInfo:undefined,
    }
  },
  created() {
    this.getList()
    this.getCatelogList();
  },
  mounted() {
  },
  methods: {
    onChooseCatalog(val) {
      this.$refs.chooseCatalog.dropDownVisible = false
    },
    //获取商品分类
    getCatelogList() {
      this.loading = true;
      listInfo().then(response => {
        this.catelogList = response.data;
      });
    },
    getDetail(data) {
      // this.goodDetailInfo =data; this.open = true
      this.getSellerDetail(data.goodsBarcode)
    },
    getSellerDetail (goodsBarcode) {
      goodSellerDetail({goodsBarcode}).then(res =>{
        this.goodDetailInfo = res.data
        this.open = true
      })
    },
    getList () {
      this.loading = true
      goodSellerList(this.queryParams).then( res =>{
        const appContainer = document.querySelector('.app-main')
        appContainer && appContainer.scrollTo({
          top:20,
          behavior: 'smooth'
        })
        this.goodsList = res.rows;
        this.total = res.total;
        this.loading = false;
      }).catch(err => {
        this.loading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.goodsClassificationArr = []
      // this.resetQueryStoreHouse()
      if (this.checkRole(['admin'])) {
        // this.resetQuerySupplier()
      }
      this.handleQuery();
    },
    handleExport () {

    },
    checkOverflow,
    checkRole,
    checkPermi
  }
}
</script>

<style scoped>

.app-container {
  height: 100%;
  overflow: hidden;
}
.good-list {
  overflow-y: auto;
  overflow-x: hidden;
}
.detail-dialog {
  min-width: 770px!important;
}
</style>
