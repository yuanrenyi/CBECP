import {checkPermi, checkRole} from "@/utils/permission";

/*配合表单使用*/
/*
*  <select-table
    v-model="compId"
    placeholder="请选择销售方"
    ref="orderForm"
    :table="orderTable"
    :columns="orderTable.columns"
    :max-height="400"
    :keywords="{label:'compName',value:'id'}"
    @page-change="orderPageChange"
    @clear="resetFormOrder"
    @radioChange="orderChange"
    @inputKeyword="filterOrder"
    @focus="getOrderList"
    radioTxt="#"
    isShowPagination
  ></select-table>*/
export default {
  created() {
    this.getOrderList()
  },
  data() {
    return {
      query: {
        compName: undefined,
        compId :undefined,
      },
      compIdForm: undefined,
      compIdQuery: undefined,
      // 销售方列表
      orderTable: {
        data: [],
        total: 0,
        pageSize: 10,
        currentPage: 1,
        columns: [
          {label: "企业名称", prop: "compName"},
          {label: "项目负责人", prop: "pmName"},
          {label: "负责人电话", prop: "pmPhone"},
          {label: "微信群", prop: "wechatGroup"},
          {label: "是否已开会议", prop: "ifMeeting"},
          {label: "培训人", prop: "trainer"},

          {label: "是否有微信小程序", prop: "ifWechatMiniapp"},
          {label: "合作模式", prop: "cooperationMode"},
          {label: "备注", prop: "remark"}
        ],
      },
    }
  },
  methods : {
    // 销售方列表
    async getOrderList() {
      const queryParams = {
        pageNum: this.orderTable.currentPage,
        pageSize: this.orderTable.pageSize,
        compName: this.query.compName ? this.query.compName : undefined,
        id: this.query.compId ? this.query.compId: undefined,
      }
      const res = await listOrder(queryParams)
      this.orderTable.data = res.rows;
      this.orderTable.total = res.total;
    },
    // 筛选销售方
    async filterOrder(val) {
      console.log({val})
      this.query.compName = val
      await this.getOrderList()
    },
    async orderPageChange(currentPage) {
      this.orderTable.currentPage = currentPage
      await this.getOrderList()
    },
    orderFormChange (val) {
      this.form.compId = val && val.id ? val.id : undefined
    },
    // 清空表单中的
    resetFormOrder() {
      // this.$refs.orderForm.clear()
      // this.$refs.orderForm.defaultValue = ''
      this.compIdForm = ''
    },
    resetQueryOrder () {
      this.compIdQuery = ''
    },
    // 这个要写在业务组件中 来覆盖掉此方法
    orderQueryChange(val) {
      /* this.queryParams 业务逻*/
      this.queryParams.orderId = val && val.id ? val.id : undefined
    },
    checkPermi,
    checkRole,
  }
}
