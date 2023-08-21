
/*配合查询使用
*  <select-table
    v-model="supplierIdQuery"
    ref="supplierTableQuery"
    :table="supplierTable"
    :columns="supplierTable.columns"
    :max-height="400"
    :keywords="{label:'supplierName',value:'id'}"
    @page-change="supplierPageChange"
    @clear="resetQuerySupplier"
    @radioChange="supplierChangeQuery"
    @inputKeyword="filterSupplier"
    radioTxt="#"
    isShowPagination
  ></select-table>*/

import {listSupplier} from "@/api/suppiler/supplier";
import {checkPermi, checkRole} from "@/utils/permission";

export default {
  created() {
    console.log('getSupplierList')
    this.getSupplierList()
  },
  data() {
    return {
      query: {
        supplierName: null
      },
      supplierIdQuery:'',
      supplierIdForm:'',
      // 供应商列表
      supplierTable: {
        data: [],
        total: 0,
        pageSize: 10,
        currentPage: 1,
        columns: [
          {label: "供应商名称", prop: "supplierName"},
          // {label: "昵称", prop: "supplierNickName"},
          // {label: "供应商编码", prop: "supplierCode"},
          // {label: "联系人", prop: "contractName"},
          // {label: "联系电话", prop: "contractPhone"},
          // {label: "添加人", prop: "purchaser"},
          // {label: "提现账户", prop: "withdrawAccount"},
          // {label: "备注", prop: "remark"}
        ],
      },
    }
  },
  methods: {
    // 供应商列表
    async getSupplierList() {
      const queryParams = {
        pageNum: this.supplierTable.currentPage,
        pageSize: this.supplierTable.pageSize,
        supplierName: this.query.supplierName,
      }
      const res = await listSupplier(queryParams)
      this.supplierTable.data = res.rows;
      this.supplierTable.total = res.total;
    },
    //  筛选 供应商
    async filterSupplier(val) {
      this.query.supplierName = val
       await this.getSupplierList()
    },
    // 页码改变
    supplierPageChange(currentPage) {
      this.supplierTable.currentPage = currentPage
      this.getSupplierList()
    },
    //选中供应商  表单 这个要写在业务组件中 来覆盖掉此方法
    supplierChange(val) {
      if (val.id) {
        this.form.supplierId = val.id
      }
    },
    // 回显供应商 (赋值回显)
    async initSupplierShow (vModel,param,name) {
      this.supplierIdForm = vModel[param]
      if(name) {
        // todo 回显之前 先查一下列表
        this.query.supplierName = vModel[name]
        await this.getSupplierList()
        this.$nextTick( ()=>{
          this.$refs.supplierForm.searchKey = vModel[name]
          this.$refs.supplierForm.defaultValue.supplierName = vModel[name]
        })
      }

    },
    //选中供应商 查询 这个要写在业务组件中 来覆盖掉此方法
    supplierChangeQuery(val) {
      this.queryParams.supplierId = val && val.id ? val.id : null
    },
    // 清空查询时的 这个要写在业务组件中 来覆盖掉此方法
    resetQuerySupplier() {
      // this.$refs.supplierTableQuery.clear()
      // this.$refs.supplierTableQuery.defaultValue = ''
      this.queryParams.supplierId = null
    },
    checkPermi,
    checkRole
  }
}
