<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      class="query"
      ref="queryForm"
      size="small"
      v-show="showSearch"
      label-width="98px">
      <el-row>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" v-if="checkRole(['admin'])">
          <el-form-item label="所属供应商" prop="supplierId">
            <select-table
              v-model="supplierIdQuery"
              ref="supplierTableQuery"
              :table="supplierTable"
              :columns="supplierTable.columns"
              :max-height="400"
              clearable
              :keywords="{label:'supplierName',value:'id'}"
              @page-change="supplierPageChange"
              @clear="resetQuerySupplier"
              @radioChange="supplierChangeQuery"
              @inputKeyword="filterSupplier"
              radioTxt="#"
              isShowPagination
            ></select-table>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="仓库名称" prop="storehouseName">
            <el-input
              v-model="queryParams.storehouseName"
              placeholder="请输入仓库名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="仓库类型" prop="storehouseType">
            <el-select v-model="queryParams.storehouseType" placeholder="请选择仓库类型" clearable>
              <el-option
                v-for="dict in dict.type.storehouse_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="货主ID" prop="consignorId">
            <el-input
              v-model="queryParams.consignorId"
              placeholder="请输入货主ID"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="外部仓库编码" prop="storehouseOutNo">
            <el-input
              v-model="queryParams.storehouseOutNo"
              placeholder="请输入外部仓库编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
<!--        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="token" prop="storehouseToken">
            <el-input
              v-model="queryParams.storehouseToken"
              placeholder="请输入token"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>-->
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="联系人" prop="contractName">
            <el-input
              v-model="queryParams.contractName"
              placeholder="请输入联系人"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="联系电话" prop="contractPhone">
            <el-input
              v-model="queryParams.contractPhone"
              placeholder="请输入联系电话"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
<!--        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4">
          <el-form-item label="所在省" prop="storehouseProvince">
            <el-input
              v-model="queryParams.storehouseProvince"
              placeholder="请输入所在省"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4">
          <el-form-item label="所在市" prop="storehouseCity">
            <el-input
              v-model="queryParams.storehouseCity"
              placeholder="请输入所在市"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4">
          <el-form-item label="所在区" prop="storehouseCounty">
            <el-input
              v-model="queryParams.storehouseCounty"
              placeholder="请输入所在区"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>-->
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="邮税状态" prop="postalTaxType">
            <el-select v-model="queryParams.postalTaxType" placeholder="请选择邮税状态" clearable>
              <el-option
                v-for="dict in dict.type.storehouse_postal_tax_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="对接渠道" prop="dockingChannel">
            <el-select clearable v-model="queryParams.dockingChannel" placeholder="请选择对接渠道">
              <el-option
                v-for="dict in dict.type.supplier_docking_channels"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="是否支持拆单" prop="ifSplitOrder">
            <el-select v-model="queryParams.ifSplitOrder" placeholder="请选择是否支持拆单" clearable>
              <el-option
                v-for="dict in dict.type.storehouse_if_split_order"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item  class="ml20">
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
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
          v-hasPermi="['supplier:storehouse:add']"
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
          v-hasPermi="['supplier:storehouse:edit']"
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
          v-hasPermi="['supplier:storehouse:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['supplier:storehouse:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table  stripe  border v-loading="loading" :data="storehouseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="仓库ID" align="center" prop="id" />-->
<!--      <el-table-column v-if="checkRole(['admin'])" label="所属供应商ID" align="center" prop="supplierId" />-->
      <el-table-column label="所属供应商" align="center" show-overflow-tooltip  prop="supplierName"  width="120" v-if="checkRole(['admin'])"/>
      <el-table-column label="仓库名称" align="center" prop="storehouseName" />
      <el-table-column label="仓库类型" align="center" prop="storehouseType" width="120">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.storehouse_type" :value="scope.row.storehouseType"/>
        </template>
      </el-table-column>
      <el-table-column label="货主ID" align="center" prop="consignorId" />
      <el-table-column label="外部仓库编码" align="center" prop="storehouseOutNo" min-width="120" />
      <el-table-column label="token" align="center" prop="storehouseToken" />
      <el-table-column label="联系人" align="center" prop="contractName" />
      <el-table-column label="联系电话" align="center" prop="contractPhone" />
      <el-table-column label="所在省" align="center" prop="provinceName" />
      <el-table-column label="所在市" align="center" prop="cityName" />
      <el-table-column label="所在区" align="center" prop="countyName" />
      <el-table-column label="邮税状态" align="center" prop="postalTaxType" width="130">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.storehouse_postal_tax_type" :value="scope.row.postalTaxType"/>
        </template>
      </el-table-column>
      <el-table-column label="运费" align="center" prop="freightAmount" />
      <el-table-column label="续重运费" align="center" prop="freightRenewalAmount" />
      <el-table-column label="对接渠道" align="center" prop="dockingChannel" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.supplier_docking_channels" :value="scope.row.dockingChannel"/>
        </template>
      </el-table-column>
      <el-table-column label="是否支持拆单" align="center" prop="ifSplitOrder" width="120">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.storehouse_if_split_order" :value="scope.row.ifSplitOrder"/>
        </template>
      </el-table-column>
      <el-table-column label="详细地址" show-overflow-tooltip align="center" prop="addressDetail" show-overflow-tooltip width="200"/>

      <el-table-column label="scname" align="center" prop="scname" />
      <el-table-column label="包装重量" align="center" prop="weightIncrease" />
      <el-table-column label="续重" align="center" prop="freightIncrease" />
      <el-table-column label="所属BU" align="center" prop="belongingBu" />
      <el-table-column label="接口秘钥" align="center" prop="signaTure" />

      <el-table-column label="备注" show-overflow-tooltip align="center" min-width="150" prop="remark" />
      <el-table-column label="操作" align="center" fixed="right" width="150" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['supplier:storehouse:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['supplier:storehouse:remove']"
          >删除</el-button>
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

    <!-- 添加或修改供应商仓库信息对话框 -->
    <a-modal
      :title="title"
      :visible.sync="open"
      :zIndex="1001"
      width="75%"
      append-to-body @cancel="open = false">
      <el-form  ref="form" class="edit" :model="form" :rules="rules" label-width="98px"  label-position="right">
        <el-row :gutter="10">
          <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="12" v-if="checkRole(['admin'])">
            <el-form-item label="所属供应商" prop="supplierId">
              <select-table
                v-model="form.supplierId"
                ref="supplierNameRef"
                :table="supplierTable"
                :columns="supplierTable.columns"
                :max-height="400"
                @clear="resetSupplier"
                :keywords="{label:'supplierName',value:'id'}"
                @page-change="supplierPageChange"
                @radioChange="supplierChange"
                @inputKeyword="filterSupplier"
                radioTxt="#"
                isShowPagination
              ></select-table>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="仓库名称" prop="storehouseName">
              <el-input v-model="form.storehouseName" placeholder="请输入仓库名称" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="仓库类型" prop="storehouseType">
              <el-select ref="storehouseType" @focus="focusSelect('storehouseType')" v-model="form.storehouseType" placeholder="请选择仓库类型">
                <el-option
                  v-for="dict in dict.type.storehouse_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="货主ID" prop="consignorId">
              <el-input v-model="form.consignorId" placeholder="请输入货主ID" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="外部仓库编码" prop="storehouseOutNo">
              <el-input v-model="form.storehouseOutNo" placeholder="请输入外部仓库编码" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="token" prop="storehouseToken">
              <el-input v-model="form.storehouseToken" placeholder="请输入token" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="联系人" prop="contractName">
              <el-input v-model="form.contractName" placeholder="请输入联系人" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="联系电话" prop="contractPhone">
              <el-input v-model="form.contractPhone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="邮税状态" prop="postalTaxType">
              <el-select v-model="form.postalTaxType" placeholder="请选择邮税状态">
                <el-option
                  v-for="dict in dict.type.storehouse_postal_tax_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="运费" prop="freightAmount">
              <el-input-number :precision="2" :step="1" controls-position="right" :min="0"  v-model="form.freightAmount" placeholder="请输入运费" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="续重运费" prop="freightRenewalAmount">
              <el-input-number  :precision="2" :step="1" controls-position="right" :min="0" v-model="form.freightRenewalAmount" placeholder="请输入续重运费" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="对接渠道" prop="dockingChannel">
              <el-select :popper-append-to-body="false" v-model="form.dockingChannel" placeholder="请选择对接渠道">
                <el-option
                  v-for="dict in dict.type.supplier_docking_channels"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="是否支持拆单" prop="ifSplitOrder">
              <el-select :popper-append-to-body="false" v-model="form.ifSplitOrder" placeholder="请选择是否支持拆单">
                <el-option
                  v-for="dict in dict.type.storehouse_if_split_order"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="scname" prop="scname">
              <el-input v-model="form.scname" placeholder="请输入scname" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="包装重量" prop="weightIncrease">
              <el-input v-model="form.weightIncrease" placeholder="请输入包装重量" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="续重" prop="freightIncrease">
              <el-input v-model="form.freightIncrease" placeholder="请输入续重" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="所属BU" prop="belongingBu">
              <el-input v-model="form.belongingBu" placeholder="请输入所属BU" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="接口秘钥" prop="signaTure">
              <el-input v-model="form.signaTure" placeholder="请输入接口秘钥" />
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="所在省" prop="storehouseProvince">
              <el-select
                ref="pro"
                @focus="focusSelect('pro')"
                @change="changePro($event, form,'storehouseProvince','provinceName')"
                @clear="clearPro(form,['storehouseProvince','storehouseCity','storehouseCounty'],['provinceName','cityName','countyName'])"
                clearable
                value-key="provinceID"
                v-model="provinceObj"
                placeholder="请选择省/直辖市">
                <el-option
                  v-for="item in provinceList"
                  :key="item.provinceID"
                  :label="item.province"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="所在市" prop="storehouseCity">
              <el-select
                @focus="focusSelect('city')"
                ref="city"
                clearable
                @change="changeCity($event, form,'storehouseCity','cityName')"
                @clear="clearCity(form, ['storehouseCity','storehouseCounty'],['cityName','countyName'])"
                value-key="cityID"
                v-model="cityObj"
                placeholder="请先选择省/直辖市">
                <el-option
                  v-for="item in cityList"
                  :key="item.cityID"
                  :label="item.city"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" >
            <el-form-item label="所在区" prop="storehouseCounty">
              <el-select
                ref="area"
                @focus="focusSelect('area')"
                clearable
                @change="changeArea($event,form,'storehouseCounty','countyName')"
                @clera="clearArae(form,'storehouseCounty','countyName')"
                value-key="areaID"
                v-model="areaObj" placeholder="请先选择市">
                <el-option
                  v-for="item in areaList"
                  :key="item.areaID"
                  :label="item.area"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="12" >
            <el-form-item label="详细地址" prop="addressDetail">
              <el-input v-model="form.addressDetail" placeholder="请输入详细地址" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="12">
            <el-form-item label="备注" prop="remark" >
              <el-input v-model="form.remark" placeholder="请输入备注"  type="textarea" :autosize="{minRows: 2 , maxRows: 3}"/>
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
import { listStorehouse, getStorehouse, delStorehouse, addStorehouse, updateStorehouse } from "@/api/seller/storehouse";
import {listSupplier} from "@/api/suppiler/supplier";
import { checkPermi, checkRole } from "@/utils/permission"; // 权限判断函数
import supplierMix from "@/mixins/supplierMix";
import areaMix from "@/mixins/areaMix";
import {clone} from 'lodash'

export default {
  name: "Storehouse",
  mixins: [supplierMix, areaMix],
  dicts: ['storehouse_postal_tax_type', 'storehouse_if_split_order','storehouse_type','supplier_docking_channels'],
  data() {
    return {
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
      // 供应商仓库信息表格数据
      storehouseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        supplierId: null,
        storehouseName: null,
        storehouseType: null,
        consignorId: null,
        storehouseOutNo: null,
        storehouseToken: null,
        contractName: null,
        contractPhone: null,
        storehouseProvince: null,
        provinceName: null,
        storehouseCity: null,
        cityName: null,
        storehouseCounty: null,
        countyName: null,
        postalTaxType: null,
        freightAmount: null,
        freightRenewalAmount: null,
        dockingChannel: null,
        ifSplitOrder: null,
        addressDetail: null,
        scname: null,
        weightIncrease: null,
        freightIncrease: null,
        belongingBu: null,
        signaTure: null
      },
      // 表单参数
      form: {},
      query: {
        supplierName: ''
      },
      // 表单校验
      rules: {
        supplierId: [
          {required: true, message: "请选择供应商", trigger: "change"}
        ],
        storehouseName: [
          {required: true, message: "请填写仓库名称", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    console.log(111111111)
    this.getList();
  },
  methods: {
    /** 查询供应商仓库信息列表 */
    getList() {
      this.loading = true;
      listStorehouse(this.queryParams).then(response => {
        this.storehouseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    supplierChange (val) {
      if(val.id) {
        this.form.supplierId = val.id
      }
    } ,
    supplierChangeQuery (val) {
      this.queryParams.supplierId = val && val.id ?  val.id : null
      this.handleQuery()
    } ,
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        supplierId: null,
        storehouseName: null,
        storehouseType: null,
        consignorId: null,
        storehouseOutNo: null,
        storehouseToken: null,
        contractName: null,
        contractPhone: null,
        storehouseProvince: null,
        provinceName: null,
        storehouseCity: null,
        cityName: null,
        storehouseCounty: null,
        countyName: null,
        postalTaxType: null,
        freightAmount: null,
        freightRenewalAmount: null,
        dockingChannel: null,
        ifSplitOrder: null,
        addressDetail: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        scname: null,
        weightIncrease: null,
        freightIncrease: null,
        belongingBu: null,
        signaTure: null
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
      this.resetQuerySupplier()
      this.handleQuery();
    },
    // 清空查询时的 supplierId
    resetQuerySupplier () {
      // this.$refs.supplierTableQuery.clear()
      // this.$refs.supplierTableQuery.defaultValue= ''
      this.queryParams.supplierId = ''
    },
    // 清空查supplierId
    resetSupplier () {
      this.form.supplierId = null
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
      this.resetAreaList()
      this.open = true;
      this.title = "添加供应商仓库信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.resetAreaList()
      const id = row.id || this.ids
      getStorehouse(id).then(response => {
        this.form = response.data;
        this.handleAreaInit()
        this.open = true;
        this.title = "修改供应商仓库信息";
      });
    },
    /*省市区回显*/
    handleAreaInit () {
      let provinceID = clone(this.form.storehouseProvince)
      let province = clone(this.form.provinceName)
      let proObj = {provinceID,province}
      let cityID = clone(this.form.storehouseCity)
      let city = clone(this.form.cityName)
      let cityObj = {cityID, city}
      let areaID = clone(this.form.storehouseCounty)
      let area = clone(this.form.countyName)
      let areaObj = {areaID, area}
      this.initSelectArea(proObj,cityObj,areaObj)
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStorehouse(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStorehouse(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除供应商仓库信息编号为"' + ids + '"的数据项？').then(function() {
        return delStorehouse(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/supplier/storehouse/export', {
        ...this.queryParams
      }, `storehouse_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
