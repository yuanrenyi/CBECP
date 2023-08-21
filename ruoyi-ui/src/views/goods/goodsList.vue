<template>
  <div class="app-container flex flex-column" ref="appContainer">
    <el-form
      v-if="showSearch"
      class="query mb10"
      :model="queryParams"
      ref="queryForm"
      size="small"
      label-width="88px">
      <el-row :gutter="10">
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
              @inputKeyword="filterStoreHouseAll"
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

        <el-col v-if="checkRole(['admin'])" :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="销售方" prop="supplierId">
            <select-table
              v-model="compIdQuery"
              placeholder="请选择销售方"
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
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="销售方上架状态" prop="sellerOnshelfStatus">
            <el-select v-model="queryParams.sellerOnshelfStatus" clearable>
              <el-option :value="0" label="未上架"></el-option>
              <el-option :value="1" label="已上架"></el-option>
            </el-select>
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
          <el-form-item label="上架状态" prop="platOnShelf">
            <el-select v-model="queryParams.platOnShelf" placeholder="请选择上架状态" clearable>
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
<!--            <el-input-->
<!--              v-model="queryParams.goodsClassificationArr"-->
<!--              placeholder="请输入分类"-->
<!--              clearable-->
<!--              @keyup.enter.native="handleQuery"-->
<!--            />-->
            <el-cascader
              placeholder="请选择分类"
              v-model="goodsClassificationArr"
              :options="catelogList"
              :props="{
                  multiple: true,
                  label: 'catalogName',
                  value: 'id',
                  emitPath: false
              }"
              clearable
              change-on-select
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
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8"  prop="costPrice" v-if="uncheckRole(['SUPPLIER_ADMIN'])">
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
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="是否有图片" prop="hasPic">
            <el-select v-model="queryParams.hasPic" placeholder="是否有图片" clearable>
              <el-option
                v-for="dict in dict.type.goods_has_pic"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="是否已分类" prop="hasCatalog">
            <el-select v-model="queryParams.hasCatalog" placeholder="是否已分类" clearable>
              <el-option
                v-for="dict in dict.type.goods_has_catalog"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8" >
          <el-form-item label="效期" prop="stock">
            <div class="flex flex-justify-between">
              <el-date-picker clearable
                              style="width: 45%"
                              v-model="queryParams.expirDateBegin"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择有效期">
              </el-date-picker>
              <span v-html="'&nbsp;&nbsp;-&nbsp;&nbsp;'"></span>
              <el-date-picker clearable
                              style="width: 45%"
                              v-model="queryParams.expirDateEnd"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择有效期">
              </el-date-picker>
            </div>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <div class="h-40-middle">
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </div>
        </el-col>
      </el-row>
    </el-form>
    <el-row :gutter="10" class="mb8" ref="toolBtn">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['goods:info:add']"
        >新增
        </el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handlePageAdd"-->
<!--          v-hasPermi="['goods:info:add']"-->
<!--        >新增(页面)-->
<!--        </el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['goods:info:edit']"
        >修改
        </el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['goods:info:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5" >
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['goods:info:export']"
          >导出
          </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['goods:info:import']"
        >导入
        </el-button>
        <input
          type="file"
          id="upload-input-goods"
          @change="getExcelFile($event)"
          style="display: none"
          accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>

        <input
          type="file"
          id="upload-input"
          @change="getFile($event)"
          style="display: none"
          accept="application/zip"/>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="warn"
          plain
          icon="el-icon-upload2"
          size="mini"
          accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
          @click="handleImportUpdateExcel"
          v-hasPermi="['goods:info:batchupdate']"
        >批量修改导入
        </el-button>
        <input
          type="file"
          id="upload-input-update"
          @change="getUpdateFile($event)"
          style="display: none"
          accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
      </el-col>



      <el-col :span="1.5">
        <el-button
          type="warn"
          plain
          icon="el-icon-upload2"
          size="mini"
          accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
          @click="handleImportStockExcel"
          v-hasPermi="['goods:info:stockupdate']"
        >更新共享库存
        </el-button>
        <input
          type="file"
          id="upload-input-stock"
          @change="getStockFile($event)"
          style="display: none"
          accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
      </el-col>


      <el-col :span="1.5">
        <el-button
          type="warn"
          plain
          icon="el-icon-upload2"
          size="mini"
          accept="zip"
          @click="handleImportImgs"
          v-hasPermi="['goodspic:batch:upload']"
        >导入图片(zip)
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch"  @queryTable="getList"></right-toolbar>
    </el-row>
    <div class="flex-1">
      <el-table
        border
        header-cell-class-name="table-header-cell"
        v-loading="loading"
        :data="goodsList"
        height="99%"
        ref="tableRef"
        @selection-change="handleSelectionChange"
        @sort-change="goodsListSortChange">
        <el-table-column type="selection" width="55" align="center"/>
        <!--      <el-table-column label="商品ID" align="center" prop="id" />-->
        <el-table-column label="商品名称" align="center" min-width="320"  prop="goodsName"/>
        <el-table-column label="商品条形码" align="center" min-width="120" prop="goodsBarcode"/>
        <el-table-column label="所在仓库" align="center" min-width="140" prop="storehouseName"/>
<!--        <el-table-column-->
<!--          v-if="checkRole(['admin'])"-->
<!--          label="所属供应商" align="center"-->
<!--          min-width="140"-->
<!--          prop="supplierName"/>-->
        <el-table-column label="商品编码" min-width="120" align="center" prop="goodsCode"/>
        <el-table-column label="外部编码" align="center" min-width="120" prop="goodsOutCode"/>
        <el-table-column label="品牌" align="center" prop="goodsBrand"/>
        <el-table-column label="分类" align="center" width="200" prop="goodsClassificationArr">
          <template slot-scope="{row}">
            <el-cascader
              collapse-tags
              readonly
              popper-class="cascade-popper"
              class="cascade-text"
              placeholder="未分类"
              v-model="row.goodsClassificationArr"
              :options="catelogList"
              :show-all-levels="true"
              :props="{
                  multiple: true,
                  expandTrigger: 'hover',
                  label: 'catalogName',
                  checkStrictly: true,
                  value: 'id',
                  emitPath: false
                }"
            >
            </el-cascader>
          </template>
        </el-table-column>
        <el-table-column label="单位" align="center" prop="goodsUnit"/>
        <el-table-column label="重量" align="center" prop="goodsWeight"/>
        <el-table-column label="裸价" align="center" prop="nakedPrice" v-if="uncheckRole(['SELLER_ADMIN'])"/>
        <el-table-column label="成本价" align="center" prop="costPrice" v-if="uncheckRole(['SUPPLIER_ADMIN'])" sortable="cost_price"/>
        <el-table-column label="库存数量" align="center" prop="stockNum" sortable="stock_num"/>
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
<!--        <el-table-column label="对接货品ID" align="center" prop="dockingGoodsId" min-width="130"/>-->
<!--        <el-table-column label="HS编码" align="center" prop="hsCode" width="90"/>-->
<!--        <el-table-column label="JD指导价" align="center" prop="jdGuidancePrice" width="90"/>-->
<!--        <el-table-column label="TM指导价" align="center" prop="tmGuidancePrice" width="90"/>-->
<!--        <el-table-column label="WY指导价" align="center" prop="wyGuidancePrice" width="90"/>-->
        <el-table-column label="建议零售价" align="center" prop="msrp" width="95"/>
        <el-table-column label="税率%" align="center" prop="taxRate"/>
        <el-table-column label="续重运费" align="center" prop="freightRenewalAmount"/>
        <el-table-column label="毛重" align="center" prop="grossWeight"/>
        <el-table-column label="运费" align="center" prop="freightAmount"/>
        <el-table-column label="最终运费" align="center" prop="finalFreightAmount"/>
        <el-table-column label="备注" align="center" width="150" prop="remark" show-overflow-tooltip/>
        <el-table-column
          v-if="!checkRole(['admin'])"
          label="上架状态" align="center" prop="platOnShelf" width="110" fixed="right">
          <template slot-scope="{row}">
            <dict-tag :options="dict.type.goods_shelves_status" :value="row.platOnShelf"/>
<!--            <el-switch
              v-model="row.onShelfStatus"
              active-color="#13ce66"
              @change="changeRow($event,row)"
              :active-value="1"
              :inactive-value="0"
              :active-text="row.onShelfStatus == 1? '已上架' : ''"
              :inactive-text="row.onShelfStatus == 0? '未上架' : ''"
            >
            </el-switch>-->
          </template>
        </el-table-column>
        <el-table-column
          v-if="checkRole(['admin'])"
          label="平台上架状态" align="center" prop="platOnShelf" width="110" fixed="right">
          <template slot-scope="{row}">
            <dict-tag :options="dict.type.goods_shelves_status" :value="row.platOnShelf"/>
<!--            <el-switch
              v-model="row.platOnShelf"
              active-color="#13ce66"
              @change="changeplatShelf($event,row)"
              :active-value="1"
              :inactive-value="0"
              :active-text="row.platOnShelf == 1? '已上架' : ''"
              :inactive-text="row.platOnShelf == 0? '未上架' : ''"
            >
            </el-switch>-->
          </template>
        </el-table-column>
        <el-table-column
          v-if="checkRole(['admin'])"
          label="是否为推荐商品"
          align="center" prop="recommend" width="115" fixed="right">
          <template slot-scope="{row}">
            <el-switch
              v-model="row.recommend"
              @change="changeRecommend($event,row)"
              :active-value="1"
              :inactive-value="0"
              :active-text="row.recommend == 1? '已推荐' : ''"
              :inactive-text="!row.recommend? '未推荐' : ''"
            >
            </el-switch>
          </template>
        </el-table-column>
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
            <div>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-tickets"
                @click="handleDetail(scope.row)"
              >商品详情
              </el-button>
            </div>
            <div>
<!--          <el-dropdown v-has-permi="['goods:info:edit']" size="small" @command="chooseUpdateType($event, scope.row)">-->
<!--            <span class="el-dropdown-link">-->
<!--              修改<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
<!--            </span>-->
<!--            <el-dropdown-menu  type="split-button" slot="dropdown">-->
<!--              <el-dropdown-item command="showUpdateDialog" icon="el-icon-edi" >弹窗修改</el-dropdown-item>-->
<!--              <el-dropdown-item command="goEditPage" icon="el-icon-edi">页面修改</el-dropdown-item>-->
<!--            </el-dropdown-menu>-->
<!--          </el-dropdown>-->

              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['goods:info:edit']"
              >修改
              </el-button>

              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['goods:info:remove']"
              >删除
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 添加或修改商品信息对话框 -->
    <a-modal
      :zIndex="1001"
      :title="title"
      :visible.sync="open" destroyOnClose width="75%"
      @cancel="open = false"
      append-to-body :afterClose="reset">
      <el-form ref="form" class="edit" :model="form" :rules="rules" label-width="98px">
        <el-row>
          <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="12" >
            <el-form-item label="商品名称" prop="goodsName">
              <el-input v-model="form.goodsName" placeholder="请输入商品名称"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="所在仓库" prop="storehouseId">
              <select-table
                v-model="storehouseIdForm"
                ref="storeHouseForm"
                :table="storeHouseTable"
                :columns="storeHouseTable.columns"
                :max-height="400"
                :keywords="{label:'storehouseName',value:'id'}"
                @page-change="storeHousePageChange"
                @clear="resetFormStoreHouse"
                @radioChange="storeHouseChange"
                @inputKeyword="filterStoreHouse"
                radioTxt="#"
                isShowPagination
              ></select-table>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item v-if="checkRole(['admin'])" label="所属供应商" prop="supplierId">
              <el-select disabled value-key="supplierId" v-model="form.supplierId" placeholder="请输入所属供应商">
                <el-option
                  v-for="item in supplierOptionList"
                  :key="item.supplierId"
                  :label="item.supplierName"
                  :value="item.supplierId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="商品编码" prop="goodsCode">
              <el-input v-model="form.goodsCode" placeholder="请输入商品编码"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="外部编码" prop="goodsOutCode">
              <el-input v-model="form.goodsOutCode" placeholder="请输入外部编码"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="商品条形码" prop="goodsBarcode">
              <el-input v-model="form.goodsBarcode" placeholder="请输入商品条形码"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="自定义条形码" prop="platBarcode">
              <el-input v-model="form.platBarcode" placeholder="请输入自定义条形码"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="品牌" prop="goodsBrand">
              <el-input v-model="form.goodsBrand" placeholder="请输入品牌"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" >
            <el-form-item label="分类" prop="goodsClassificationArr">
<!--              <el-input v-model="form.goodsClassificationArr" placeholder="请输入分类"/>-->
              <el-cascader
                placeholder="请选择分类"
                v-model="form.goodsClassificationArr"
                :options="catelogList"
                :show-all-levels="true"
                :props="{
                  multiple: true,
                  expandTrigger: 'hover',
                  label: 'catalogName',
                  checkStrictly: true,
                  value: 'id',
                  emitPath: false
                }"
                clearable
              >
              </el-cascader>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="单位" prop="goodsUnit">
              <el-input v-model="form.goodsUnit" placeholder="请输入单位"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="重量" prop="goodsWeight">
              <el-input-number :min="0"  :precision="2" :step="0.1" controls-position="right"
                               v-model="form.goodsWeight" placeholder="请输入重量" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" v-if="uncheckRole(['SELLER_ADMIN'])">
            <el-form-item label="裸价" prop="nakedPrice">
              <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right"
                               v-model="form.nakedPrice" placeholder="请输入裸价" @blur="calcCostPrice"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" v-if="uncheckRole(['SUPPLIER_ADMIN'])" >
            <el-form-item label="成本价" prop="costPrice">
              <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right"
                               v-model="form.costPrice" placeholder="" disabled/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="库存数量" prop="stockNum">
              <el-input-number :min="0"  :step="0.1" controls-position="right"  v-model="form.stockNum"
                               placeholder="请输入库存数量"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="锁定库存数量" prop="stockLockNum">
              <el-input-number :min="0" disabled  :step="1" controls-position="right"
                               v-model="form.stockLockNum" placeholder="请输入锁定库存数量"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="对接货品ID" prop="dockingGoodsId">
              <el-input v-model="form.dockingGoodsId" placeholder="请输入对接货品ID"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="HS编码" prop="hsCode">
              <el-input v-model="form.hsCode" placeholder="请输入HS编码"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="JD指导价" prop="jdGuidancePrice">
              <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right"
                               v-model="form.jdGuidancePrice" placeholder="请输入JD指导价"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="TM指导价" prop="tmGuidancePrice">
              <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right"
                               v-model="form.tmGuidancePrice" placeholder="请输入TM指导价"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="WY指导价" prop="wyGuidancePrice">
              <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right"
                               v-model="form.wyGuidancePrice" placeholder="请输入WY指导价"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="建议零售价" prop="msrp">
              <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right"  v-model="form.msrp"
                               placeholder="请输入建议零售价"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="税率%" prop="taxRate">
              <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right"  v-model="form.taxRate"
                               placeholder="请输入税率%"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="上架状态" prop="platOnShelf">
              <el-select v-model="form.platOnShelf" placeholder="请选择上架状态">
                <el-option
                  v-for="dict in dict.type.goods_shelves_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="是否推荐为推荐商品" prop="recommend">
              <el-select @focus="focusSelect('recommend')" ref="recommend" v-model="form.recommend"
                         placeholder="请选择是否推荐为推荐商品">
                <el-option
                  v-for="dict in dict.type.goods_is_recommend"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="续重运费" prop="freightRenewalAmount">
              <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right" v-model="form.freightRenewalAmount" placeholder="请输入续重运费"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="毛重" prop="grossWeight">
              <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right" v-model="form.grossWeight" placeholder="请输入毛重" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="运费" prop="freightAmount">
              <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right" v-model="form.freightAmount" placeholder="请输入运费"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="最终运费" prop="finalFreightAmount">
              <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right" v-model="form.finalFreightAmount" placeholder="请输入最终运费"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
          <el-form-item label="是否产品包" prop="ifPackage">
            <el-select v-model="form.ifPackage" placeholder="请选择是否产品包">
              <el-option
                v-for="dict in dict.type.goods_if_package"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
              ></el-option>
            </el-select>
          </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="是否自建" prop="ifBuildByOneself">
              <el-select v-model="form.ifBuildByOneself" placeholder="请选择是否自建">
                <el-option
                  v-for="dict in dict.type.goods_if_buildby_oneself"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="产品包关联商品条码" prop="barCodeList">
              <el-input v-model="form.barCodeList" placeholder="产品包关联商品条码" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="产品包关联商品数量" prop="numberList">
              <el-input-number :min="0"  :step="1" controls-position="right" v-model="form.numberList" placeholder="请输入产品包关联商品数量" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="增值税税率" prop="vatRate">
              <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right" v-model="form.vatRate" placeholder="请输入增值税税率" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="关税税率" prop="tariffRate">
              <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right" v-model="form.tariffRate" placeholder="请输入关税税率" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="消费税税率" prop="exciseTaxRate">
              <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right" v-model="form.exciseTaxRate" placeholder="请输入消费税税率" />
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="图片路径" prop="picVersion">
              <el-input v-model="form.picVersion" placeholder="0" />
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="效期" prop="expirDate">
              <el-date-picker clearable
                              style="width: 200px"
                              v-model="form.expirDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择有效期">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="12" >
            <el-form-item label="效期备注" prop="remark">
              <el-input type="textarea" :autosize="{minRows: 1 , maxRows: 2}" v-model="form.expirDateRemark"
                        placeholder="请输入效期备注"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="12" >
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" :autosize="{minRows: 1 , maxRows: 2}" v-model="form.remark"
                        placeholder="请输入备注"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="12" >
            <el-form-item label="关键字" prop="keyWord">
              <el-input type="textarea" :autosize="{minRows: 1 , maxRows: 2}" v-model="form.keyWord"
                        placeholder="请输入关键字"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="12" >
            <el-form-item label="购买须知" prop="buyNotice">
              <el-input type="textarea" :autosize="{minRows: 1 , maxRows: 2}" v-model="form.buyNotice"
                        placeholder="请输入购买须知"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="12" >
            <el-form-item label="规格型号" prop="specModel">
              <el-input type="textarea" :autosize="{minRows: 1 , maxRows: 2}" v-model="form.specModel"
                        placeholder="请输入规格型号"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="12" >
            <el-form-item label="主要型号" prop="mainModel">
              <el-input type="textarea" :autosize="{minRows: 1 , maxRows: 2}" v-model="form.mainModel"
                        placeholder="请输入主要型号"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="12" >
            <el-form-item label="主要成分" prop="basis">
              <el-input type="textarea" :autosize="{minRows: 1 , maxRows: 2}" v-model="form.basis"
                        placeholder="请输入主要成分"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="12" >
            <el-form-item label="原产地" prop="originCountry">
              <el-input type="textarea" :autosize="{minRows: 1 , maxRows: 2}" v-model="form.originCountry"
                        placeholder="请输入原产地"/>
            </el-form-item>
          </el-col>

        </el-row>

        <el-row>
          <el-button type="danger" @click="deleteAllPic">
            删除所有图片
          </el-button>
        </el-row>
        <br/>
        <div>
          <el-form-item label="商品主图" prop="remark">
            <ImageUpload2 :goodsBarcode="form.goodsBarcode"  :picVersion="picInfo1.picVersion" picType="1" :limit="50" :value="picInfo1" @delfile="deletePic($event)" @input="uploadImg($event,1)"></ImageUpload2>
          </el-form-item>
        </div>
        <div>
          <el-form-item label="商品详情图" prop="picUrl">
            <ImageUpload2 :goodsBarcode="form.goodsBarcode"  :picVersion="picInfo2.picVersion" picType="2" :limit="50" :value="picInfo2" @delfile="deletePic($event)" @input="uploadImg($event,2)"></ImageUpload2>
          </el-form-item>
        </div>
        <div>
          <el-form-item label="商品视频" prop="picUrl">
            <video-upload :goodsBarcode="form.goodsBarcode" :picVersion="picInfo3.picVersion" picType="3" :file-type="['mp4', 'avi', 'rmvb']" @delfile="deletePic($event)" :file-size="50"
                          @input="uploadImg($event,3)"
                          :value="picInfo3"></video-upload>
          </el-form-item>
        </div>
        <div>
          <el-form-item label="商品缩略图" prop="picUrl">
            <ImageUpload2 :goodsBarcode="form.goodsBarcode" :picVersion="picInfo4.picVersion" picType="4" :limit="50" :value="picInfo4" @delfile="deletePic($event)" @input="uploadImg($event,4)"></ImageUpload2>
          </el-form-item>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </a-modal>
    <!-- 商品详情    -->
    <a-modal
      :zIndex="1001"
      :title="title" :visible.sync="openDetail"
      destroyOnClose
      width="75%" append-to-body
      @cancel="openDetail = false"
      :afterClose="reset">
      <el-collapse v-model="detailActiveNames">
        <el-collapse-item title="商品基本信息" name="1">
          <el-descriptions
            :labelStyle="{width: '135px'}"
            :contentStyle="{width: '220px'}"
            class="margin-top" title="" :column="4" size="small" border>
            <el-descriptions-item :span="2">
              <template slot="label">
                <i class="el-icon-house"></i>
                商品名称
              </template>
              {{ form.goodsName }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-house"></i>
                所在仓库
              </template>
              {{ form.storehouseName }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                供应商
              </template>
              {{ form.supplierName }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                商品编码
              </template>
              {{ form.goodsCode }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                外部编码
              </template>
              {{ form.goodsOutCode }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                商品条形码
              </template>
              {{form.goodsBarcode}}
            </el-descriptions-item>

            <el-descriptions-item>
              <template slot="label">
                自定义条形码
              </template>
              {{form.platBarcode}}
            </el-descriptions-item>

            <el-descriptions-item>
              <template slot="label">
                品牌
              </template>
              {{ form.goodsBrand }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                分类
              </template>
<!--              {{ form.goodsClassificationArr }}-->
              <el-cascader
                collapse-tags
                popper-class="cascade-popper"
                class="cascade-text"
                placeholder="未添加分类"
                v-model="form.goodsClassificationArr"
                :options="catelogList"
                :show-all-levels="true"
                :props="{
                  multiple: true,
                   expandTrigger: 'hover',
                  label: 'catalogName',
                  checkStrictly: true,
                  value: 'id',
                  emitPath: false
                }"
              >
              </el-cascader>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                单位
              </template>
              {{ form.goodsUnit }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                重量
              </template>
              {{ form.goodsWeight }}
            </el-descriptions-item>
            <el-descriptions-item v-if="uncheckRole(['SELLER_ADMIN'])">
              <template slot="label">
                裸价
              </template>
              {{ form.nakedPrice }}
            </el-descriptions-item>
            <el-descriptions-item v-if="uncheckRole(['SUPPLIER_ADMIN'])">
              <template slot="label">
                成本价
              </template>
              {{ form.costPrice }}
            </el-descriptions-item>
          </el-descriptions>
        </el-collapse-item>
        <el-collapse-item title="税率/价格/库存信息" name="2">
          <el-descriptions
            :labelStyle="{width: '135px'}"
            :contentStyle="{width: '220px'}"
            title="" :column="4" size="small" border>
            <el-descriptions-item>
              <template slot="label">
                税率%
              </template>
              {{ form.taxRate }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                增值税税率%
              </template>
              {{ form.vatRate }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                关税税率%
              </template>
              {{ form.tariffRate }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                消费税税率%
              </template>
              {{ form.exciseTaxRate }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                库存数量
              </template>
              {{ form.stockNum }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                锁定库存数量
              </template>
              {{ form.stockLockNum }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                对接货品ID
              </template>
              {{ form.dockingGoodsId }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                HS编码
              </template>
              {{ form.hsCode }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                JD指导价
              </template>
              {{ form.jdGuidancePrice }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                TM指导价
              </template>
              {{form.tmGuidancePrice}}
<!--              <dict-tag :options="dict.type.order_buyer_id_type" :value="form.buyerIdType"/>-->
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                WY指导价
              </template>
              {{ form.wyGuidancePrice }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                建议零售价
              </template>
              {{ form.msrp }}
            </el-descriptions-item>

            <el-descriptions-item>
              <template slot="label">
                税率%
              </template>
              {{ form.taxRate }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                上架状态
              </template>
              <dict-tag :options="dict.type.goods_shelves_status" :value="form.platOnShelf"/>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                是否推荐为推荐商品
              </template>
              <dict-tag :options="dict.type.goods_is_recommend" :value="form.recommend" />
            </el-descriptions-item>
          </el-descriptions>
        </el-collapse-item>
        <el-collapse-item title="其他信息" name="3">
          <el-descriptions
            :labelStyle="{width: '135px'}"
            :contentStyle="{width: '220px'}"
            title="" :column="4" size="small" border>
            <el-descriptions-item>
              <template slot="label">
                续重运费
              </template>
              {{ form.freightRenewalAmount }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                毛重
              </template>
              {{ form.grossWeight }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                运费
              </template>
              {{ form.freightAmount }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                最终运费
              </template>
              {{ form.finalFreightAmount }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                运费
              </template>
              {{ form.freightAmount }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                是否产品包
              </template>
              <dict-tag :options="dict.type.goods_if_package" :value="form.ifPackage"/>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                产品包关联商品条码
              </template>
              {{ form.barCodeList }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                产品包关联商品数量
              </template>
              {{ form.numberList }}
            </el-descriptions-item>


            <el-descriptions-item>
              <template slot="label">
                图片路径
              </template>
              {{ form.picVersion }}
            </el-descriptions-item>

            <el-descriptions-item>
              <template slot="label">
                效期
              </template>
              {{ form.expirDate }}
            </el-descriptions-item>

            <el-descriptions-item>
              <template slot="label">
                效期备注
              </template>
              {{ form.expirDateRemark }}
            </el-descriptions-item>

            <el-descriptions-item>
              <template slot="label">
                备注
              </template>
              {{ form.remark }}
            </el-descriptions-item>

            <el-descriptions-item>
              <template slot="label">
                关键字
              </template>
              {{ form.keyWord }}
            </el-descriptions-item>

            <el-descriptions-item>
              <template slot="label">
                购买须知
              </template>
              {{ form.buyNotice }}
            </el-descriptions-item>

            <el-descriptions-item>
              <template slot="label">
                规格型号
              </template>
              {{ form.specModel }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                主要型号
              </template>
              {{ form.mainModel }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                主要成分
              </template>
              {{ form.basis }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                原产地
              </template>
              {{ form.originCountry }}
            </el-descriptions-item>
          </el-descriptions>
        </el-collapse-item>
        <el-collapse-item title="图片信息" name="4">
          <el-row class="mb20">
            <el-col :span="4" class="text-align-center">
              商品主图
            </el-col>
            <el-col :span="20">
              <image-preview2  width="100px" height="100px" :limit="6" :src="picInfo1"></image-preview2>
            </el-col>
          </el-row>
          <el-row class="mb20">
            <el-col :span="4" class="text-align-center">
              商品详情图
            </el-col>
            <el-col :span="20">
              <image-preview2 width="100px" height="100px" :limit="10" :src="picInfo2"></image-preview2>
            </el-col>
          </el-row>
          <el-row class="mb20">
            <el-col :span="4" class="text-align-center">
              商品视频
            </el-col>
            <el-col :span="20">
              <video-preview  :value="picInfo3"></video-preview>
            </el-col>
          </el-row>
          <el-row class="mb20">
            <el-col :span="4" class="text-align-center">
                商品缩略图
            </el-col>
            <el-col :span="20">
              <image-preview2 width="100px" height="100px" :limit="1" :src="picInfo4"></image-preview2>
            </el-col>
          </el-row>

        </el-collapse-item>

        <el-collapse-item title="已上架该商品的销售方" name="4">
          {{form.onplatSeller}}
        </el-collapse-item>
      </el-collapse>
      <div slot="footer" class="dialog-footer">
      </div>
    </a-modal>
    <!-- 导入商品   -->
    <a-modal
      :zIndex="1001"
      title="导入商品" :visible.sync="importVisible"
      destroyOnClose
      width="750px" append-to-body
      @cancel="importVisible = false"
      :afterClose="reset">
      <el-form ref="goodsImportRef" class="edit" :model="goodsImportData" :rules="goodsImportRules" label-width="128px" label-position="left">
        <el-row>
          <el-col :span="19" class="mb10">
            <el-form-item disabled label="请选择仓库" prop="storehouseId">
              <select-table
                v-model="goodsImportData.storehouseId"
                ref="storeHouseQuery"
                placeholder="请选择仓库"
                :table="storeHouseTable"
                :columns="storeHouseTable.columns"
                :max-height="400"
                :keywords="{label:'storehouseName',value:'id'}"
                @page-change="storeHousePageChange"
                @radioChange="storeHouseChangeImportGood"
                @inputKeyword="filterStoreHouse"
                @clear="resetQueryStoreHouse"
                radioTxt="#"
                isShowPagination
              ></select-table>
            </el-form-item>
          </el-col>
          <el-col :span="19" >
            <el-form-item label="选择文件" prop="fileName">
              <el-input :value="goodsImportData.fileName" readonly clearable @clear="clearFile"
                        placeholder="请选择文件">
                <el-button slot="append" icon="el-icon-upload" @click="emitUpload">选取文件</el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="uploadExcelFile">确 定</el-button>
        <el-button @click="importVisible = false">取 消</el-button>
      </div>
    </a-modal>
  </div>
</template>

<script>
import {listGoods, getGoods, delGoods, addGoods, updateGoods, platOnshelf, goodSellerList} from "@/api/goods/goods";
import {checkPermi, checkRole, uncheckRole} from "@/utils/permission";
import {checkOverflow} from "@/utils/ruoyi";

import storeHouseMix from "@/mixins/storeHouseMix";
import supplierMix from "@/mixins/supplierMix";
import sellerMix from "@/mixins/sellerMix";
import {download2, uploadFile, uploadFileMultiple} from "@/utils/request";
import {addGoodPic, delGoodPic, delGoodAllPic} from "@/api/goods/goods";
import { listInfo } from "@/api/system/catalog";

export default {
  name: "GoodsList",
  mixins: [ storeHouseMix, supplierMix, sellerMix],
  dicts: ['goods_shelves_status', 'goods_is_recommend','goods_if_package', 'goods_if_buildby_oneself','goods_has_pic','goods_has_catalog'],
  data() {
    return {
      catelogList: [],
      maxHeight:'35vh',
      detailActiveNames: ['1','4'],
      tooltip: {
        stockLockNumTableTipOffsetX: 0,
        stockLockNumTableTipArrowOffsetX: 35,
        stockLockNumTableTip: true
      },
      goodsImportData: {
        goodsFile: undefined,
        storehouseId: undefined,
        fileName: ''
      },
      importVisible: false,
      // 当前选中行 good id
      currentRowId: '',
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
      openDetail: false,
      // 查询参数
      goodsClassificationArr: [],
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
        goodsClassification: '',
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
        hasCatalog: null,
        expirDate: null,
        expirDateRemark: null,
        expirDateBegin: null,
        expirDateEnd: null,
        sortColumn: null,
        sortOrder: null,
        platBarcode: null,
      },
      // 表单参数
      form: {
        goodsClassificationArr: undefined,
        picInfo:[]
      },
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
        hsCode: [
          {
              pattern: /^\d+$/,
              message: "请输入正确的HS编码（纯数字）",
              trigger: ['blur','change']
          }
        ],
      },
      goodsImportRules: {
        storehouseId: [
          {required: true, message: "请选择仓库", trigger: "change"}
        ],
        fileName: [
          {required: true, message: "请选择文件", trigger: "change"}
        ],
      }

    };
  },
  created() {
    this.getList();
    this.getCatelogList();
    this.getStoreHouseListAll()
    if (this.checkRole(['admin'])) {
      this.getSupplierList()
    }
  },
  mounted() {
    console.log('======checkRole 检查有供应商权限?',checkRole(['SUPPLIER_ADMIN']) )
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
      }
    })
  },
  computed: {
    // 主图
    picInfo1() {
        return this.form.picInfo.filter( item => item.picType ==1 )
    },
    // 详情图
    picInfo2() {
      return this.form.picInfo.filter( item => item.picType ==2 )
    },
    // 视频
    picInfo3() {
      return this.form.picInfo.filter( item => item.picType ==3 )
    },
    // 缩略图
    picInfo4() {
      return this.form.picInfo.filter( item => item.picType ==4 )
    }
  },
  methods: {
    goodsListSortChange(column) {
      console.log(column.prop+"----" + column.order)
      console.log(column)
      this.queryParams.sortColumn = column.prop;
      this.queryParams.sortOrder = column.order;
      this.getList();
    },
    // 计算成本价：裸价 * 1.025
    calcCostPrice() {
      this.form.costPrice = this.form.nakedPrice * 1.025;
      console.log(this.form.nakedPrice)
    },

    // 计算毛重：净重 + 150
    calcGrossWeight() {
      this.form.grossWeight = this.form.goodsWeight + 150;
      console.log(this.form.grossWeight)
    },

    //获取商品分类
    getCatelogList() {
      this.loading = true;
      listInfo().then(response => {
        this.catelogList = response.data;
      });
    },
    // 选择销售方
    sellerQueryChange(val) {
      this.queryParams.sellerId = val && val.id ? val.id : undefined
      this.compIdQuery = val && val.id ? val.id : undefined
    },
    // 利用列表 上传单张图片
    uploadImg (fileList,type) {
      if(this.form.goodsBarcode === undefined || this.form.goodsBarcode === '' || this.form.goodsBarcode === null) {
        this.form.picInfo = [];
        this.$modal.msgError('请输入商品条形码或自定义条形码后再上传图片');
        return;
      }

      fileList.forEach( item =>{
        if(!Reflect.has(item, "id")) {
          let picInfo =   {
            goodsId: this.form.goodsBarcode,
            picType: type,
            picUrl:item.url,
            picVersion: this.form.picVersion,
            platBarcode: this.form.platBarcode,
          }
          addGoodPic(picInfo)
        }
      })

      fileList.splice(0, fileList.length);

      if(this.currentRowId !='') {
        this.getGoodsPicInfo(this.currentRowId)
      }
    },
    deletePic (file) {
      console.log(file)
      this.$modal.confirm('确认删除所选图片?').then( () => {
        return delGoodPic(file.id);
      }).then(() => {
        this.getGoodsPicInfo(this.currentRowId)
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
        this.getGoodsPicInfo(this.currentRowId)
      });
    },

    deleteAllPic () {
      console.log(this.currentRowId)
      this.$modal.confirm('确认删除所有图片?').then( () => {
        return delGoodAllPic(this.currentRowId);
      }).then(() => {
        this.getGoodsPicInfo(this.currentRowId)
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
        this.getGoodsPicInfo(this.currentRowId)
      });
    },

    // 图片数据
    getGoodsPicInfo(goodId) {
      getGoods(goodId).then(res => {
        this.form.picInfo = res.data.picInfo;
        // this.setStoreHouseInfo()
      });
    },
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
    changeplatShelf (val,row) {
      const { id } = row
      platOnshelf({
        id,
        platOnShelf:val
      }).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.getList();
      });
    },
    /*推荐*/
    changeRecommend (val,row) {
      const { id } = row
      updateGoods({
        id,
        recommend:val
      }).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.getList();
      });
    },
    /*供应商上架*/
    changeRow(val, row) {
      const { id } = row
      updateGoods({
        id,
        onShelfStatus:val
      }).then(response => {
        this.$modal.msgSuccess("修改成功");
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
    uncheckRole,
    /** 查询商品信息列表 */
    getList() {
      this.loading = true;
      listGoods(this.queryParams).then(response => {
        const el = this.$refs.tableRef.$el.querySelector('.el-table__body-wrapper')
        el && el.scrollTo({
          top: 0,
          // behavior: 'smooth'
        })
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
        this.resetQuerySeller()
      }
      this.goodsClassificationArr = []
      this.queryParams.costPriceEnd = null
      this.queryParams.costPriceStart = null
      this.queryParams.stockNumStart = null
      this.queryParams.stockNumEnd = null
      this.queryParams.expirDateBegin = null
      this.queryParams.expirDateEnd = null
      this.queryParams.sortOrder = null
      this.queryParams.sortColumn = null
      this.handleQuery();
      if(this.$refs.tableRef){
        console.log(this.$refs.tableRef)
        this.$refs.tableRef.clearSort();
      }
    },
    // 清空查询时的 supplierId
    resetQuerySupplier() {
      this.queryParams.supplierId = undefined
    },
    // 清空查询时的 storehouseId
    resetQueryStoreHouse() {
      this.queryParams.storehouseId = undefined
      this.storehouseIdQuery=''
    },
    resetQuerySeller() {
      this.queryParams.compId = ''
      this.compIdQuery = ''
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
    /*商品详情*/
    handleDetail (row) {
      console.log(row)
      getGoods(row.id).then(response => {
        this.form = response.data;
        this.setStoreHouseInfo()
        this.openDetail = true;
        this.title = "商品详情";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      this.currentRowId = row && row.id
      getGoods(id).then(response => {
        this.form = response.data;
        this.setStoreHouseInfo()
        this.open = true;
        this.title = "修改商品信息";
        // this.$nextTick(() => {
        //   this.$refs.chooseCatalogSave.inputValue = this.form.goodsClassificationArrName
        // })
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
      // this.$refs.storeHouseForm.searchKey = this.form.storehouseName
      this.$nextTick(()=>{
        setTimeout(()=>{
          // this.$refs.storeHouseForm.searchKey = this.form.storehouseName
          this.storehouseIdForm = this.form.storehouseId
        },100)
        // this.$refs.storeHouseForm.defaultValue.storehouseName = this.form.storehouseName
      })
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
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
      const barCode = row.goodsBarcode;
      // this.$modal.confirm('是否确认删除商品条形码为"' + barCode + '"的数据项？').then(function () {
      this.$modal.confirm('确认删除所选商品').then(function () {
        return delGoods(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      if(this.ids.length> 0) {
        download2('system/goods/export', {
          ids: this.ids
        }, `goods_${new Date().getTime()}.xlsx`)
      }else  {
        this.download('system/goods/export', {
          ...this.queryParams
        }, `goods_${new Date().getTime()}.xlsx`)
      }
    },

    /* 导入图片 start*/
    handleImportImgs () {
      let uploadInput = document.querySelector("#upload-input")
      uploadInput.click()
    },

    /* 更新商品Excel start*/
    handleImportUpdateExcel () {
      let uploadInput = document.querySelector("#upload-input-update")
      uploadInput.click()
    },

    /* 更新库存Excel start*/
    handleImportStockExcel () {
      let uploadInput = document.querySelector("#upload-input-stock")
      uploadInput.click()
    },

    uploadZipFile (file) {
      let url = '/system/goods/goodspic/batch/upload'
      uploadFile(url, 'zipFile',file,{
        timeout: 300 * 1000
      }).then(res=>{
        console.log(res)
        // this.$modal.msgSuccess(res.msg);
        this.$message({
          message: res.msg,
          type: 'success',
          duration: 0,
          showClose: true
        })
      }).catch(err => {
        this.$message({
          message: res.msg || '操作失败,请重试',
          type: 'info',
          duration: 0,
          showClose: true
        })
      })
    },

    uploadUpdateExcelFile (file) {
      let url = '/system/goods/upload/update'
      uploadFile(url, 'goodsUpdateFile',file,{
        timeout: 300 * 1000
      }).then(res=>{
        console.log(res)
        // this.$modal.msgSuccess(res.msg);
        this.$message({
          message: res.msg,
          type: 'success',
          duration: 0,
          showClose: true
        })
      }).catch(err => {
        this.$message({
          message: res.msg || '操作失败,请重试',
          type: 'info',
          duration: 0,
          showClose: true
        })
      })
    },

    uploadStockExcelFile (file) {
      let url = '/system/goods/upload/stock/update'
      uploadFile(url, 'goodsStockFile',file,{
        timeout: 300 * 1000
      }).then(res=>{
        console.log(res)
        // this.$modal.msgSuccess(res.msg);
        this.$message({
          message: res.msg,
          type: 'success',
          duration: 0,
          showClose: true
        })
      }).catch(err => {
        this.$message({
          message: res.msg || '操作失败,请重试',
          type: 'info',
          duration: 0,
          showClose: true
        })
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

    getUpdateFile(e) {
      let files = e.target.files
      let file = files[0]
      if (!/\.(xlsx)$/.test(event.target.value)) {
        return  this.$notification.warn({message: "文件格式不正确请上传xlsx文件",duration: 5* 1000});
      }
      this.uploadUpdateExcelFile(file)
      e.target.value = '';
    },

    getStockFile(e) {
      let files = e.target.files
      let file = files[0]
      if (!/\.(xlsx)$/.test(event.target.value)) {
        return  this.$notification.warn({message: "文件格式不正确请上传xlsx文件",duration: 5* 1000});
      }
      this.uploadStockExcelFile(file)
      e.target.value = '';
    },

    /* 导入图片 end */

    /* 导入商品start*/
    handleImport () {
      this.importVisible = true
      // let uploadInput = document.querySelector("#upload-input-goods")
      // uploadInput.click()
    },
    emitUpload() {
      let uploadInput = document.querySelector("#upload-input-goods")
      uploadInput.click()
    },
    storeHouseChangeImportGood (data) {
      this.goodsImportData.storehouseId = data.id
      console.log(this.goodsImportData.storehouseId);
    },
    clearFile() {
      console.log(11111)
    },
    getExcelFile(e) {
      let files = e.target.files
      let file = files[0]
      if (!/\.(xlsx)$/.test(event.target.value)) {
        return  this.$notification.warn({message: "文件格式不正确请上传xlsx文件",duration: 5* 1000});
      }
      // this.uploadExcelFile(file)
      this.goodsImportData.goodsFile = file
      this.goodsImportData.fileName = file.name
      e.target.value = '';
    },
    uploadExcelFile (file) {
      this.$refs.goodsImportRef.validate(valid => {
        if(valid) {
          const url = '/system/goods/upload/v2'
          const {goodsFile , storehouseId} = this.goodsImportData
          const paramsArray = [
            {
              paramName: 'goodsFile',
              paramData: goodsFile
            },
            {
              paramName: 'storehouseId',
              paramData: storehouseId
            }
          ]
          uploadFileMultiple({
            url: url,
            paramsArray: paramsArray
          },{
            timeout: 30 * 1000
          }).then(res=>{
            console.log(res)
            this.$message({
              message: res.msg,
              duration: 0,
              type: 'success',
              showClose: true
            })
            this.importVisible = false
            this.reset()
          }).catch( err => {
            console.warn(err)
            this.$message({
              message: res.msg || '操作失败,请重试',
              type: 'info',
              duration: 0,
              showClose: true
            })
          })
        }
      })
    },
    /* 导入商品end*/

    // 表单重置
    reset() {
      console.log("resetForm")
      // this.open = false
      // this.openDetail = false
      this.goodsImportData.goodsFile = undefined
      this.goodsImportData.fileName = undefined
      this.goodsImportData.storehouseId = undefined
      this.storehouseId = ''
      this.currentRowId = ''
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
        picVersion: null,
        picInfo: []
      };
      this.resetForm("form");
    },
  },
  watch: {
    goodsClassificationArr:  function (val){
      console.log({val})
      this.queryParams.goodsClassification = val.join(',')
    }
  }
};
</script>

<style scoped lang="scss">
.app-container {
  height: 100%;
  overflow: hidden;
}
/*switch*/
::v-deep.el-switch {
  .el-switch__core {
    width: 30px !important;
    height: 16px;
  }
  .el-switch__core::after {
    width: 14px;
    height: 14px;
    left: 0;
    margin-top: -1px;
  }
  &.is-checked .el-switch__core::after{
    left: 100%;
    margin-left: -14px;
  }
}


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
