<template>
  <div class="app-container">
    <el-form class="query" :model="queryParams" ref="queryForm" size="small" v-show="showSearch" label-width="98px">
      <el-row>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="仓库" prop="storehouseId">
            <select-table
              v-model="storehouseIdQuery"
              ref="storeHouseQuery"
              placeholder="请选择仓库"
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
        <el-col
          :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8"
          v-if="!checkRole(['SELLER_ADMIN'])"
        >
          <el-form-item label="销售方" prop="sellerId">
            <select-table
              v-model="compIdQuery"
              placeholder="请选择销售方"
              ref="sellerForm"
              :table="sellerTable"
              :columns="sellerTable.columns"
              :max-height="400"
              :keywords="{label:'compName',value:'id'}"
              @page-change="sellerPageChange"
              @clear="resetQuerySeller"
              @radioChange="sellerQueryChange"
              @inputKeyword="filterSeller"
              @focus="getSellerList"
              radioTxt="#"
              isShowPagination
            ></select-table>
          </el-form-item>
        </el-col>
        <el-col
          v-if="!checkRole(['SUPPLIER_ADMIN'])"
          :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="供应商" prop="supplierId">
            <select-table
              v-model="supplierIdQuery"
              placeholder="请输入供应商"
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
          <el-form-item label="订单类型" prop="orderType">
            <el-select v-model="queryParams.orderType" placeholder="请选择订单类型，默认1" clearable>
              <el-option
                v-for="dict in dict.type.order_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="系统单号" prop="sorderNo">
            <el-input
              v-model="queryParams.sorderNo"
              placeholder="请输入系统单号"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="商户订单号" prop="declareOrderNo">
            <el-input
              v-model="queryParams.declareOrderNo"
              placeholder="请输入商户订单号"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="交易订单号" prop="outTradeNo">
            <el-input
              v-model="queryParams.outTradeNo"
              placeholder="请输入交易订单号"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="订单状态" prop="orderStatus">
            <el-select v-model="queryParams.orderStatus" placeholder="请选择订单状态" clearable>
              <el-option
                v-for="dict in dict.type.order_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="售后" prop="orderStatus">
            <el-select v-model="queryParams.aftermarketStatus" placeholder="请选择售后状态" clearable>
              <el-option
                v-for="dict in dict.type.aftermarket_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <!--        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >-->
        <!--          <el-form-item label="物流单号" prop="expressName">-->
        <!--            <el-input-->
        <!--              v-model="queryParams.expressName"-->
        <!--              placeholder="请输入物流单号"-->
        <!--              clearable-->
        <!--              @keyup.enter.native="handleQuery"-->
        <!--            />-->
        <!--          </el-form-item>-->
        <!--        </el-col>-->
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="物流单号" prop="shipOrderNumber">
            <el-input
              v-model="queryParams.shipOrderNumber"
              placeholder="请输入物流单号"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="店铺名称" prop="shopNick">
            <el-input
              v-model="queryParams.shopNick"
              placeholder="请输入店铺名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <!--        <el-col :xs="24" :sm="12" :md="8" :lg="4.8" :xl="4" >-->
        <!--          <el-form-item label="购买人注册号" prop="buyerRegNo">-->
        <!--            <el-input-->
        <!--              v-model="queryParams.buyerRegNo"-->
        <!--              placeholder="请输入购买人注册号"-->
        <!--              clearable-->
        <!--              @keyup.enter.native="handleQuery"-->
        <!--            />-->
        <!--          </el-form-item>-->
        <!--        </el-col>-->
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="购买人姓名" prop="buyerName">
            <el-input
              v-model="queryParams.buyerName"
              placeholder="请输入购买人姓名"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="购买人电话" prop="buyerTelephone">
            <el-input
              v-model="queryParams.buyerTelephone"
              placeholder="请输入购买人电话"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <!--        <el-col :xs="24" :sm="12" :md="8" :lg="4.8" :xl="4" >-->
        <!--          <el-form-item label="证件类型 1：身份证号" prop="buyerIdType">-->
        <!--            <el-select v-model="queryParams.buyerIdType" placeholder="请选择证件类型 1：身份证号" clearable>-->
        <!--              <el-option-->
        <!--                v-for="dict in dict.type.order_buyer_id_type"-->
        <!--                :key="dict.value"-->
        <!--                :label="dict.label"-->
        <!--                :value="dict.value"-->
        <!--              />-->
        <!--            </el-select>-->
        <!--          </el-form-item>-->
        <!--        </el-col>-->
        <!--        <el-col :xs="24" :sm="12" :md="8" :lg="4.8" :xl="4" >-->
        <!--          <el-form-item label="购买人证件号" prop="buyerIdNumber">-->
        <!--            <el-input-->
        <!--              v-model="queryParams.buyerIdNumber"-->
        <!--              placeholder="请输入购买人证件号"-->
        <!--              clearable-->
        <!--              @keyup.enter.native="handleQuery"-->
        <!--            />-->
        <!--          </el-form-item>-->
        <!--        </el-col>-->
        <!--        <el-col :xs="24" :sm="12" :md="8" :lg="4.8" :xl="4" >-->
        <!--          <el-form-item label="收件人昵称" prop="consignee">-->
        <!--            <el-input-->
        <!--              v-model="queryParams.consignee"-->
        <!--              placeholder="请输入收件人昵称"-->
        <!--              clearable-->
        <!--              @keyup.enter.native="handleQuery"-->
        <!--            />-->
        <!--          </el-form-item>-->
        <!--        </el-col>-->
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="收件人电话" prop="consigneeTelephone">
            <el-input
              v-model="queryParams.consigneeTelephone"
              placeholder="请输入收件人电话"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>

        <!--        <el-col :xs="24" :sm="12" :md="8" :lg="4.8" :xl="4" >-->
        <!--          <el-form-item label="支付方式" prop="payMode">-->
        <!--            <el-select v-model="queryParams.payMode" placeholder="请选择支付方式" clearable>-->
        <!--              <el-option-->
        <!--                v-for="dict in dict.type.order_pay_mode"-->
        <!--                :key="dict.value"-->
        <!--                :label="dict.label"-->
        <!--                :value="dict.value"-->
        <!--              />-->
        <!--            </el-select>-->
        <!--          </el-form-item>-->
        <!--        </el-col>-->
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="支付时间" prop="payTime">
            <el-date-picker clearable
                            v-model="queryParams.payTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="请选择支付时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="支付单号/支付流水号" prop="gatewayOrderId">
            <el-input
              v-model="queryParams.gatewayOrderId"
              placeholder="请输入支付单号/支付流水号"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="订单新增时间" prop="addTime">
            <el-date-picker clearable
                            v-model="queryParams.addTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="请选择订单新增时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <!--        <el-col :xs="24" :sm="12" :md="8" :lg=68" :xl="4.8" >-->
        <!--          <el-form-item label="三方单号" prop="lgOrderCode">-->
        <!--            <el-input-->
        <!--              v-model="queryParams.lgOrderCode"-->
        <!--              placeholder="请输入三方单号"-->
        <!--              clearable-->
        <!--              @keyup.enter.native="handleQuery"-->
        <!--            />-->
        <!--          </el-form-item>-->
        <!--        </el-col>-->
        <!--        <el-col :xs="24" :sm="12" :md="8" :lg=68" :xl="4.8" >-->
        <!--          <el-form-item label="回调时间" prop="callbackTime">-->
        <!--            <el-date-picker clearable-->
        <!--                            v-model="queryParams.callbackTime"-->
        <!--                            type="date"-->
        <!--                            value-format="yyyy-MM-dd"-->
        <!--                            placeholder="请选择回调时间">-->
        <!--            </el-date-picker>-->
        <!--          </el-form-item>-->
        <!--        </el-col>-->
        <!--        <el-col :xs="24" :sm="12" :md="8" :lg=68" :xl="4.8" >-->
        <!--          <el-form-item label="指定物流编码" prop="appointExpress">-->
        <!--            <el-input-->
        <!--              v-model="queryParams.appointExpress"-->
        <!--              placeholder="请输入指定物流编码"-->
        <!--              clearable-->
        <!--              @keyup.enter.native="handleQuery"-->
        <!--            />-->
        <!--          </el-form-item>-->
        <!--        </el-col>-->
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
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
          v-hasPermi="['admin']"
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
          @click="handleUpdateBySelect"
          v-hasPermi="['admin']"
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
          v-hasPermi="['admin']"
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
          v-hasPermi="['order:info:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      border
      stripe
      v-loading="loading"
      header-cell-class-name="table-header-cell"
      :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="仓库" align="center" prop="storehouseName"/>
      <el-table-column label="销售方" align="center" prop="sellerName"/>
      <el-table-column label="供应商" align="center" prop="supplierName"/>
      <el-table-column label="订单类型" align="center" prop="orderType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_type" :value="scope.row.orderType"/>
        </template>
      </el-table-column>
      <el-table-column label="系统单号" align="center" prop="sorderNo"/>
      <el-table-column label="商户订单号" align="center" prop="declareOrderNo"/>
      <el-table-column label="交易订单号" align="center" prop="outTradeNo"/>
      <el-table-column label="订单状态" align="center" prop="orderStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_status" :value="scope.row.orderStatus"/>
        </template>
      </el-table-column>

      <el-table-column label="售后状态" align="center" prop="aftermarketStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_aftermarket_status" :value="scope.row.aftermarketStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="物流单号" align="center" prop="shipOrderNumber"/>
      <!--      <el-table-column label="物流单号" align="center" prop="shipOrderNumber" />-->
      <!--      <el-table-column label="店铺名称" align="center" prop="shopNick" />-->
      <!--      <el-table-column label="购买人注册号" align="center" prop="buyerRegNo" />-->
      <!--      <el-table-column label="购买人姓名" align="center" prop="buyerName" />-->
      <!--      <el-table-column label="购买人电话" align="center" prop="buyerTelephone" />-->
      <!--      <el-table-column label="证件类型" align="center" prop="buyerIdType">-->
      <!--        <template slot-scope="scope">-->
      <!--          <dict-tag :options="dict.type.order_buyer_id_type" :value="scope.row.buyerIdType"/>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <!--      <el-table-column label="购买人证件号" align="center" prop="buyerIdNumber" />
            <el-table-column label="收件人昵称" align="center" prop="consignee" />
            <el-table-column label="收件人电话" align="center" prop="consigneeTelephone" />
            <el-table-column label="收件人地址" align="center" prop="consigneeAddress" />
            <el-table-column label="收件人邮政编码" align="center" prop="consigneeZipCode" />
            <el-table-column label="收件人省" align="center" prop="consigneeProvince" />
            <el-table-column label="收件人市" align="center" prop="consigneeCity" />
            <el-table-column label="收件人区" align="center" prop="consigneeDistrict" />-->
      <el-table-column label="商品总价" align="center" prop="totalAmount"/>
      <!--      <el-table-column label="实际支付金额" align="center" prop="realTotalAmount" />
            <el-table-column label="综合税税费" align="center" prop="taxTotalAmount" />
            <el-table-column label="折扣/优惠费用" align="center" prop="discountAmount" />
            <el-table-column label="运费" align="center" prop="freightAmount" />-->
      <el-table-column label="支付方式" align="center" prop="payMode">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_pay_mode" :value="scope.row.payMode"/>
        </template>
      </el-table-column>
      <el-table-column label="支付时间" align="center" prop="payTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.payTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="支付单号/支付流水号" align="center" prop="gatewayOrderId"/>
      <el-table-column label="订单新增时间" align="center" prop="addTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.addTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="订单是否异常" align="center" prop="abnormalStatus" width="100">
        <template slot-scope="{row}">
          <span v-if="row.abnormalStatus == 1">
            <el-tooltip
              placement="top-start"
              :content="'异常原因: '+row.abnormalDesc"
            >
              <dict-tag :options="dict.type.order_abnormal_status" :value="row.abnormalStatus"/>
            </el-tooltip>
          </span>
          <dict-tag v-else :options="dict.type.order_abnormal_status" :value="row.abnormalStatus"/>
        </template>
      </el-table-column>
      <!--      <el-table-column label="三方单号" align="center" prop="lgOrderCode" />-->
      <!--      <el-table-column label="回调时间" align="center" prop="callbackTime" width="180">-->
      <!--        <template slot-scope="scope">-->
      <!--          <span>{{ parseTime(scope.row.callbackTime, '{y}-{m}-{d}') }}</span>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <!--      <el-table-column label="增值税" align="center" prop="vat" />-->
      <!--      <el-table-column label="关税" align="center" prop="customsTax" />-->
      <!--      <el-table-column label="消费税" align="center" prop="consumptionTax" />-->
      <!--      <el-table-column label="订单状态说明" align="center" prop="orderStatusRemark" />-->
      <!--      <el-table-column label="订单回调状态，默认0" align="center" prop="callbackMallStatus">-->
      <!--        <template slot-scope="scope">-->
      <!--          <dict-tag :options="dict.type.order_callback_mall_status" :value="scope.row.callbackMallStatus"/>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <!--      <el-table-column label="订单回调状态说明" align="center" prop="callbackMallStatusValue" />-->
      <!--      <el-table-column label="联盟运费" align="center" prop="allianceFreightAmount" />-->
      <!--      <el-table-column label="指定物流编码" align="center" prop="appointExpress" />-->
      <el-table-column
        label="操作"
        align="center"
        width="110"
        class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="{row}">
          <div>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-document-copy"
              @click="handleUpdate(row,'detail')"
              v-hasPermi="['order:info:query']"
            >订单详情
            </el-button>
          </div>
          <div>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              v-if="row.abnormalStatus != 1"
              @click="handleAbnormal(row)"
              v-hasPermi="['order:abnormal:add']"
            >标记异常
            </el-button>

            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              v-if="row.abnormalStatus == 1"
              @click="handleRollbackAbnormal(row)"
              v-hasPermi="['order:abnormal:rollback']"
            >取消标记异常
            </el-button>
            <div>

              <el-button
                v-if="row.abnormalStatus == 1 || row.abnormalStatus == 2"
                size="mini"
                type="text"
                @click="handleAbnormalFollow(row,'detail')"
                v-hasPermi="['order:abnormal:followupadd']"
              >
                <i>
                  <svg-icon class-name="svg-abnormal" icon-class="flag"/>
                </i>
                异常进度跟进
              </el-button>
            </div>
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

    <!-- 添加或修改订单信息对话框 -->
    <a-modal :title="title" :visible.sync="open" width="65%" append-to-body @cancel="cancel">
      <el-form
        v-if="isAddAbnormal"
        ref="abnormalForm"
        class="edit"
        :model="abnormalForm" :rules="abnormaRules" label-position="right" label-width="98px">
        <el-row :gutter="10">
          <el-col :xs="14" :sm="14" :md="12" :lg="12" :xl="12">
            <el-form-item label="订单异常原因" prop="abnormalDesc">
              <el-input type="textarea" :autosize="{minRows: 2, maxRows: 4}" v-model="abnormalForm.abnormalDesc"
                        placeholder="请输入订单异常原因"/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" size="mini" @click="confirmAbnormal"> 提交</el-button>
          </el-col>
        </el-row>
      </el-form>

      <el-form
        v-if="edit"
        ref="form"
        class="edit"
        :model="form" :rules="rules" label-width="98px" label-position="right">
        <el-row :gutter="10">
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="仓库" prop="storehouseId">
              <select-table
                v-model="storehouseIdForm"
                placeholder="请选择仓库"
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
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="销售方ID" prop="sellerId">
              <select-table
                v-model="compIdForm"
                placeholder="请选择销售方"
                ref="sellerForm"
                :table="sellerTable"
                :columns="sellerTable.columns"
                :max-height="400"
                :keywords="{label:'compName',value:'id'}"
                @page-change="sellerPageChange"
                @clear="resetFormSeller"
                @radioChange="sellerChange"
                @inputKeyword="filterSeller"
                @focus="getSellerList"
                radioTxt="#"
                isShowPagination
              ></select-table>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="供应商" prop="supplierId">
              <el-select disabled value-key="supplierId" v-model="form.supplierId" placeholder="请选择所属供应商">
                <el-option
                  v-for="item in supplierOptionList"
                  :key="item.supplierId"
                  :label="item.supplierName"
                  :value="item.supplierId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="订单类型" prop="orderType">
              <el-select v-model="form.orderType" placeholder="请选择订单类型">
                <el-option
                  v-for="dict in dict.type.order_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="系统单号" prop="sorderNo">
              <el-input v-model="form.sorderNo" placeholder="请输入系统单号"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="商户订单号" prop="declareOrderNo">
              <el-input v-model="form.declareOrderNo" placeholder="请输入商户订单号"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="交易订单号" prop="outTradeNo">
              <el-input v-model="form.outTradeNo" placeholder="请输入交易订单号"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="订单状态" prop="orderStatus">
              <el-select v-model="form.orderStatus" placeholder="请选择订单状态">
                <el-option
                  v-for="dict in dict.type.order_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="物流公司" prop="expressName">
              <el-input v-model="form.expressName" placeholder="请输入物流公司"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="物流单号" prop="shipOrderNumber">
              <el-input v-model="form.shipOrderNumber" placeholder="请输入物流单号"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="店铺名称" prop="shopNick">
              <el-input v-model="form.shopNick" placeholder="请输入店铺名称"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="购买人注册号" prop="buyerRegNo">
              <el-input v-model="form.buyerRegNo" placeholder="请输入购买人注册号"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="购买人姓名" prop="buyerName">
              <el-input v-model="form.buyerName" placeholder="请输入购买人姓名"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="购买人电话" prop="buyerTelephone">
              <el-input v-model="form.buyerTelephone" placeholder="请输入购买人电话"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="证件类型" prop="buyerIdType">
              <el-select v-model="form.buyerIdType" placeholder="请选择证件类型">
                <el-option
                  v-for="dict in dict.type.order_buyer_id_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="购买人证件号" prop="buyerIdNumber">
              <el-input v-model="form.buyerIdNumber" placeholder="请输入购买人证件号"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="收件人昵称" prop="consignee">
              <el-input v-model="form.consignee" placeholder="请输入收件人昵称"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="收件人电话" prop="consigneeTelephone">
              <el-input v-model="form.consigneeTelephone" placeholder="请输入收件人电话"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="收件人地址" prop="consigneeAddress">
              <el-input v-model="form.consigneeAddress" placeholder="请输入收件人地址"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="收件人邮政编码" prop="consigneeZipCode">
              <el-input v-model="form.consigneeZipCode" placeholder="请输入收件人邮政编码"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="收件人省" prop="consigneeProvince">
              <el-input v-model="form.consigneeProvince" placeholder="请输入收件人省"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="收件人市" prop="consigneeCity">
              <el-input v-model="form.consigneeCity" placeholder="请输入收件人市"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="收件人区" prop="consigneeDistrict">
              <el-input v-model="form.consigneeDistrict" placeholder="请输入收件人区"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="商品总价" prop="totalAmount">
              <el-input v-model="form.totalAmount" placeholder="请输入商品总价"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="实际支付金额" prop="realTotalAmount">
              <el-input v-model="form.realTotalAmount" placeholder="请输入实际支付金额"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="综合税税费" prop="taxTotalAmount">
              <el-input v-model="form.taxTotalAmount" placeholder="请输入综合税税费"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="折扣/优惠费用" prop="discountAmount">
              <el-input v-model="form.discountAmount" placeholder="请输入折扣/优惠费用"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="运费" prop="freightAmount">
              <el-input v-model="form.freightAmount" placeholder="请输入运费"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="支付方式" prop="payMode">
              <el-input v-model="form.payMode" placeholder="请输入支付方式"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="支付时间" prop="payTime">
              <el-date-picker clearable
                              v-model="form.payTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择支付时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="支付单号/支付流水号" prop="gatewayOrderId">
              <el-input v-model="form.gatewayOrderId" placeholder="请输入支付单号/支付流水号"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="订单新增时间" prop="addTime">
              <el-date-picker clearable
                              v-model="form.addTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择订单新增时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="三方单号" prop="lgOrderCode">
              <el-input v-model="form.lgOrderCode" placeholder="请输入三方单号"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="回调时间" prop="callbackTime">
              <el-date-picker clearable
                              v-model="form.callbackTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择回调时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="增值税" prop="vat">
              <el-input v-model="form.vat" placeholder="请输入增值税"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="关税" prop="customsTax">
              <el-input v-model="form.customsTax" placeholder="请输入关税"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="消费税" prop="consumptionTax">
              <el-input v-model="form.consumptionTax" placeholder="请输入消费税"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="12">
            <el-form-item label="订单状态说明" prop="orderStatusRemark">
              <el-input v-model="form.orderStatusRemark" type="textarea" :autosize="{minRows: 1 , maxRows: 2}"
                        placeholder="请输入内容"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="订单回调状态" prop="callbackMallStatus">
              <el-select v-model="form.callbackMallStatus" placeholder="请选择订单回调状态" clearable>
                <el-option
                  v-for="dict in dict.type.order_callback_mall_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="订单回调状态说明" prop="callbackMallStatusValue">
              <el-input v-model="form.callbackMallStatusValue" placeholder="请输入订单回调状态说明"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="联盟运费" prop="allianceFreightAmount">
              <el-input v-model="form.allianceFreightAmount" placeholder="请输入联盟运费"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="指定物流编码" prop="appointExpress">
              <el-input v-model="form.appointExpress" placeholder="请输入指定物流编码"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="销售方可提现金额" prop="sellerWithdrawAmount">
              <el-input v-model="form.sellerWithdrawalAmount" placeholder="请输入支付方式"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
            <el-form-item label="供应商可提现金额" prop="supplierWithdrawalAmount">
              <el-input v-model="form.supplierWithdrawalAmount" placeholder="请输入支付方式"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-collapse v-else v-model="activeNames">
        <el-collapse-item title="订单基本信息" name="1">
          <el-descriptions
            :labelStyle="{width: '110px'}"
            :contentStyle="{width: '220px'}"
            class="margin-top" title="" :column="4" size="small" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-house"></i>
                所在仓库
              </template>
              {{ form.storehouseName }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                销售方
              </template>
              {{ form.sellerName }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                供应商
              </template>
              {{ form.supplierName }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-order"></i>
                系统单号
              </template>
              <!--              <el-tag size="small">学校</el-tag>-->
              {{ form.sorderNo }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-order"></i>
                商户订单号
              </template>
              {{ form.declareOrderNo }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-order"></i>
                交易订单号
              </template>
              {{ form.outTradeNo }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                订单状态
              </template>
              <dict-tag :options="dict.type.order_callback_mall_status" :value="form.callbackMallStatus"/>
            </el-descriptions-item>
          </el-descriptions>
        </el-collapse-item>
        <el-collapse-item title="物流信息" name="2">
          <el-descriptions
            :labelStyle="{width: '110px'}"
            :contentStyle="{width: '220px'}"
            title="" :column="4" size="small" border>
            <el-descriptions-item>
              <template slot="label">
                物流公司
              </template>
              {{ form.expressName }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                物流单号
              </template>
              {{ form.shipOrderNumber }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                店铺名称
              </template>
              {{ form.shopNick }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                购买人注册号
              </template>
              {{ form.buyerRegNo }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                购买人姓名
              </template>
              {{ form.buyerName }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-phone-outline"></i>
                购买人电话
              </template>
              {{ form.buyerTelephone }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                证件类型
              </template>
              <dict-tag :options="dict.type.order_buyer_id_type" :value="form.buyerIdType"/>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                购买人证件号
              </template>
              {{ form.buyerTelephone }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                收件人昵称
              </template>
              {{ form.consignee }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                收件人电话
              </template>
              {{ form.consigneeTelephone }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                收件人邮政编码
              </template>
              {{ form.consigneeZipCode }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                收件人省
              </template>
              {{ form.consigneeProvince }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                收件人市
              </template>
              {{ form.consigneeCity }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                收件人区
              </template>
              {{ form.consigneeDistrict }}
            </el-descriptions-item>
          </el-descriptions>
        </el-collapse-item>
        <el-collapse-item title="商品及支付信息" name="3">
          <el-descriptions
            :labelStyle="{width: '110px'}"
            :contentStyle="{width: '220px'}"
            title="" :column="4" size="small" border>
            <el-descriptions-item>
              <template slot="label">
                商品总价
              </template>
              {{ form.totalAmount }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                实际支付金额
              </template>
              {{ form.realTotalAmount }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                综合税税费
              </template>
              {{ form.taxTotalAmount }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                折扣/优惠费用
              </template>
              {{ form.discountAmount }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                运费
              </template>
              {{ form.freightAmount }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                支付方式
              </template>
              {{ form.payMode }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                支付时间
              </template>
              {{ form.payTime }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                支付单号/支付流水号
              </template>
              {{ form.gatewayOrderId }}
            </el-descriptions-item>

          </el-descriptions>
        </el-collapse-item>
        <el-collapse-item title="其他信息" name="4">
          <el-descriptions
            :labelStyle="{width: '110px'}"
            :contentStyle="{width: '220px'}"
            title="" :column="4" size="small" border>
            <el-descriptions-item>
              <template slot="label">
                三方单号
              </template>
              {{ form.lgOrderCode }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <span>{{ parseTime(form.callbackTime, '{y}-{m}-{d}') }}</span>
              </template>
              {{ form.buyerIdType }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                增值税
              </template>
              {{ form.vat }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                关税
              </template>
              {{ form.customsTax }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                消费税
              </template>
              {{ form.consumptionTax }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                订单回调状态
              </template>
              {{ form.callbackMallStatus }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                订单回调状态说明
              </template>
              {{ form.callbackMallStatusValue }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                联盟运费
              </template>
              {{ form.allianceFreightAmount }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                指定物流编码
              </template>
              {{ form.appointExpress }}
            </el-descriptions-item>

            <el-descriptions-item>
              <template slot="label">
                售后状态
              </template>
              <dict-tag :options="dict.type.aftermarket_status" :value="form.aftermarketStatus"/>
            </el-descriptions-item>
          </el-descriptions>
        </el-collapse-item>
      </el-collapse>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="edit" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="open = false">关闭</el-button>
      </div>
    </a-modal>

    <!--异常进度跟进-->
    <a-modal
      centered
      :title="title"
      :visible.sync="openFollowUp"
      width="50%" :destroyOnClose="true"
      append-to-body @cancel="openFollowUp = false">
      <el-form
        :model="followUpForm"
        class="edit"
        ref="followform"
        label-width="90px"
        :rules="followUpRules">
        <el-row>
          <el-col :span="12">
            <el-form-item label="跟进时间" prop="followupTime">
              <el-date-picker clearable
                              v-model="followUpForm.followupTime"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              default-time="9:00:00"
                              placeholder="请选择跟进时间">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="跟进说明" prop="followupDesc">
              <el-input
                type="textarea"
                :autosize="{minRows: 2, maxRows: 3}"
                v-model="followUpForm.followupDesc"
                placeholder="请输入会议跟进情况"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <div class="flex flex-justify-end mr20">
              <el-button type="primary" size="mini" @click="submitFollowForm">提 交</el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>

      <el-divider content-position="center">跟进历史记录</el-divider>
      <el-table
        :data="followUpList"
        border
      >
        <el-table-column label="跟进时间" prop="followupTime" align="center">

        </el-table-column>
        <el-table-column label="跟进情况说明" prop="followupDesc" show-overflow-tooltip align="center">

        </el-table-column>

        <el-table-column label="提交人" prop="followupName" show-overflow-tooltip align="center">

        </el-table-column>
      </el-table>

      <div slot="footer" class="dialog-footer">
      </div>
    </a-modal>
  </div>
</template>

<script>
import {
  listInfo,
  getInfo,
  delInfo,
  addInfo,
  updateInfo,
  addAbnormal,
  abnormalFollowupList, addAbnormalFollowup, rollbackAbnorma
} from "@/api/order/orderinfo";
import storeHouseMix from "@/mixins/storeHouseMix";
import sellerMix from "@/mixins/sellerMix";
import supplierMix from "@/mixins/supplierMix";
import {checkPermi, checkRole} from "@/utils/permission";
export default {
  name: "Info",
  mixins: [storeHouseMix, supplierMix],
  dicts: ['order_status', 'order_pay_mode', 'order_type', 'order_callback_mall_status', 'order_buyer_id_type', 'order_abnormal_status','aftermarket_status'],
  data() {
    return {
      activeNames: ['1', '2'],
      edit: false,
      isAddAbnormal: false,
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
      // 订单信息表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openFollowUp: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        aftermarketStatus: null,
        storehouseId: null,
        sellerId: null,
        supplierId: null,
        orderType: null,
        sorderNo: null,
        declareOrderNo: null,
        outTradeNo: null,
        orderStatus: null,
        expressName: null,
        shipOrderNumber: null,
        shopNick: null,
        buyerRegNo: null,
        buyerName: null,
        buyerTelephone: null,
        buyerIdType: null,
        buyerIdNumber: null,
        consignee: null,
        consigneeTelephone: null,
        consigneeAddress: null,
        consigneeZipCode: null,
        consigneeProvince: null,
        consigneeCity: null,
        consigneeDistrict: null,
        totalAmount: null,
        realTotalAmount: null,
        taxTotalAmount: null,
        discountAmount: null,
        freightAmount: null,
        payMode: null,
        payTime: null,
        gatewayOrderId: null,
        addTime: null,
        lgOrderCode: null,
        callbackTime: null,
        vat: null,
        customsTax: null,
        consumptionTax: null,
        orderStatusRemark: null,
        callbackMallStatus: null,
        callbackMallStatusValue: null,
        allianceFreightAmount: null,
        appointExpress: null,
        abnormalStatus: 1,
      },
      supplierOptionList: [],
      // 历史异常跟进
      followUpList: [],
      /*节点角色1*/
      followupItem1: [
        {followupItem: '合同跟进'}
      ],
      followUpForm: {
        // followupItem: '',// 跟进节点
        followupTime: '',// 跟进时间
        followupDesc: '',// 跟进说明
        orderId: '',// 销售方id
      },
      followUpRules: {
        followupTime: [
          {required: true, message: "请选择跟进时间", trigger: "blur"}
        ],
        followupItem: [
          {required: true, message: "请选择跟进节点", trigger: "blur"}
        ],
        followupDesc: [
          {required: true, message: "请填写跟进说明", trigger: "blur"}
        ],
      },
      // 表单参数
      form: {
        storehouseId: undefined,
        supplierId: undefined,
        compId: undefined,
      },
      abnormalForm: {
        orderInfoId: '',
        abnormalDesc: '',
      },
      abnormaRules: {
        abnormalDesc: [
          {required: true, message: "请输入订单异常原因", trigger: "blur"}
        ]
      },
      // 表单校验
      rules: {
        // payTime: [
        //   { required: true, message: "支付时间不能为空", trigger: "blur" }
        // ],
        // addTime: [
        //   { required: true, message: "订单新增时间不能为空", trigger: "blur" }
        // ],
        // callbackTime: [
        //   { required: true, message: "回调时间不能为空", trigger: "blur" }
        // ],
        storehouseId: [
          {required: true, message: "请选择仓库", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    checkPermi, checkRole,
    confirmAbnormal() {
      this.$refs.abnormalForm.validate(valid => {
        if (valid) {
          addAbnormal(this.abnormalForm).then(res => {
            this.$modal.msgSuccess("标记成功");
            this.cancel()
            this.getList()
          })
        }
      })
    },
    /*取消订单异常标记*/
    handleRollbackAbnormal(row) {
      rollbackAbnorma({id:row.id}).then(res => {
        this.$modal.msgSuccess("取消成功");
        this.getList();
      })
    },
    /* 添加异常跟进 */
    submitFollowForm() {
      this.$refs.followform.validate(valid => {
        if (valid) {
          addAbnormalFollowup(this.followUpForm).then(res => {
            this.$modal.msgSuccess("提交成功");
            this.resetFollowUpForm()
            this.getList()
            this.openFollowUp = false
          })
        }
      })
    },
    handleAbnormalFollow(row) {
      const {id: orderId} = row
      this.followUpForm.orderId = orderId
      abnormalFollowupList({orderId}).then(res => {
        this.followUpList = res.data
        this.openFollowUp = true

      })
      // 获取跟进历史列表
    },
    /* 打开异常 提交表单*/
    handleAbnormal(row) {
      this.isAddAbnormal = true
      this.abnormalForm.orderInfoId = row.id
      this.handleUpdate(row)
    },

    // 选中销售方
    sellerChange(val) {
      this.form.sellerId = val && val.id ? val.id : null
    },
    // 选中仓库 => 同时选中 供应商
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
    },
    /** 查询订单信息列表 */
    getList() {
      this.loading = true;
      listInfo(this.queryParams).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      // this.ids = selection.map(item => item.id)
      this.ids = selection
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.edit = true
      this.open = true;
      this.title = "添加订单信息";
    },
    handleUpdateBySelect() {
      this.edit = true
      this.handleUpdate()
    },
    /** 详情或者修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row && row.id || this.ids
      // getInfo(id).then(response => {
      //   if(!response.data) return this.$notification.warn({ message: '查询失败,无数据!'})
      //   this.form = response.data;
      //   this.open = true;
      //   this.title = "修改订单信息";
      // });
      this.form = !row || !row.id ? this.ids[0] : row
      // this.activeNames = ['1','2']
      this.activeNames = this.isAddAbnormal ? ['1'] : ['1', '2']
      this.open = true;
      this.title = "订单详情";
      // this.initStroehouseShow(this.form,'compId')
      this.compIdForm = this.form.compId
      this.storehouseIdForm = this.form.storehouseId
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    // 销售方查询
    sellerQueryChange(val) {
      /* this.queryParams 业务逻*/
      this.queryParams.sellerId = val && val.id ? val.id : undefined
      this.handleQuery()
    },
    // 仓库查询
    storeHouseChangeQuery(val) {
      /* this.queryParams 业务逻*/
      this.queryParams.storehouseId = val && val.id ? val.id : undefined
      this.handleQuery()
    },
    // 供应商查询
    supplierChangeQuery(val) {
      this.queryParams.supplierId = val && val.id ? val.id : undefined
      this.handleQuery()
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除订单信息编号为"' + ids + '"的数据项？').then(function () {
        return delInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xls`)
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.edit = false;
      this.isAddAbnormal = false
      this.reset();
    },
    resetFollowUpForm() {
      this.followUpForm = {
        followupTime: '',// 跟进时间
        followupDesc: '',// 跟进说明
        orderId: '',// 销售方id
      }
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        sellerWithdrawalAmount: null,
        supplierWithdrawalAmount: null,
        storehouseId: null,
        sellerId: null,
        supplierId: null,
        orderType: null,
        sorderNo: null,
        declareOrderNo: null,
        outTradeNo: null,
        orderStatus: null,
        expressName: null,
        shipOrderNumber: null,
        shopNick: null,
        buyerRegNo: null,
        buyerName: null,
        buyerTelephone: null,
        buyerIdType: null,
        buyerIdNumber: null,
        consignee: null,
        consigneeTelephone: null,
        consigneeAddress: null,
        consigneeZipCode: null,
        consigneeProvince: null,
        consigneeCity: null,
        consigneeDistrict: null,
        totalAmount: null,
        realTotalAmount: null,
        taxTotalAmount: null,
        discountAmount: null,
        freightAmount: null,
        payMode: null,
        payTime: null,
        gatewayOrderId: null,
        addTime: null,
        lgOrderCode: null,
        callbackTime: null,
        vat: null,
        customsTax: null,
        consumptionTax: null,
        orderStatusRemark: null,
        callbackMallStatus: null,
        callbackMallStatusValue: null,
        allianceFreightAmount: null,
        appointExpress: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null
      };
      this.resetForm("form");
    },
  }
};
</script>

<style scoped lang="scss">
// .el-descriptions ::v-deep .el-descriptions-item__label    {
//    width: 100px!important;
//}
.svg-abnormal {
  font-size: 18px;
}
</style>
