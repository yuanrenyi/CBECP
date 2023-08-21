import {listStorehouse, listStorehouseAll} from "@/api/seller/storehouse";
import {checkPermi, checkRole} from "@/utils/permission";
/*配合表单使用*/
/*<select-table
  v-model="storehouseIdForm"
  placeholder="请输入仓库"
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
></select-table>*/
/*
* 配合查询使用
*  <select-table
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
  ></select-table>*/
export default {
  created() {
    this.getStoreHouseList()
  },
  data() {
    return {
      query: {
        storehouseName: undefined,
        supplierName: undefined
      },
      storehouseIdQuery: undefined,
      storehouseIdForm: undefined,
      // 仓库列表
      storeHouseTable: {
        data: [],
        total: 0,
        pageSize: 10,
        currentPage: 1,
        columns: [
          {label: "仓库名称", prop: "storehouseName"}
          // {label: "所属供应商", prop: "supplierName"},
          // {label: "仓库类型", prop: "storehouseType"},
          // {label: "货主ID", prop: "consignorId"},
          // {label: "外部仓库编码", prop: "storehouseOutNo"},
          // {label: "联系人", prop: "contractName"},
          // {label: "联系电话", prop: "contractPhone"},
          // {label: "详细地址", prop: "addressDetail"},
          // {label: "备注", prop: "remark"}
        ],
      },
    }
  },
  methods : {
    // 仓库列表
    async getStoreHouseList() {
      const queryParams = {
        pageNum: this.storeHouseTable.currentPage,
        pageSize: this.storeHouseTable.pageSize,
        storehouseName: this.query.storehouseName ? this.query.storehouseName : undefined
      }
      const res = await listStorehouse(queryParams)
      this.storeHouseTable.data = res.rows;
      this.storeHouseTable.total = res.total;
    },

    // 仓库列表
    async getStoreHouseListAll() {
      const queryParams = {
        pageNum: this.storeHouseTable.currentPage,
        pageSize: this.storeHouseTable.pageSize,
        storehouseName: this.query.storehouseName ? this.query.storehouseName : undefined
      }
      const res = await listStorehouseAll(queryParams)
      this.storeHouseTable.data = res.rows;
      this.storeHouseTable.total = res.total;
    },

    // 筛选仓库
    async filterStoreHouse(val) {
      console.log({val})
      this.query.storehouseName = val
      await this.getStoreHouseList()
    },

    // 筛选仓库
    async filterStoreHouseAll(val) {
      console.log({val})
      this.query.storehouseName = val
      await this.getStoreHouseListAll()
    },

    storeHousePageChange(currentPage) {
      this.storeHouseTable.currentPage = currentPage
      this.getStoreHouseList()
    },
    // 选中仓库 这个要写在业务组件中 来覆盖掉此方法
    storeHouseChange(val) {
      /* this.form 业务逻辑 */
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
    // 这个要写在业务组件中 来覆盖掉此方法
    resetFormStoreHouse() {
      // this.$refs.storeHouseForm.clear()
      // this.$refs.storeHouseForm.defaultValue = ''
      this.storehouseId = ''
      /* this.form 业务逻*/
      this.form.storehouseId = ''
      this.form.supplierId = ''
    },
    // 这个要写在业务组件中 来覆盖掉此方法
    storeHouseChangeQuery(val) {
      /* this.queryParams 业务逻*/
      this.queryParams.storehouseId = val && val.id ? val.id : undefined
    },
    // 清空查询时的  这个要写在业务组件中 来覆盖掉此方法
    resetQueryStoreHouse() {
      // this.$refs.storeHouseQuery.clear()
      // this.$refs.storeHouseQuery.defaultValue = ''
      this.queryParams.storehouseId = undefined
    },
    checkPermi,
    checkRole,
  }
}
