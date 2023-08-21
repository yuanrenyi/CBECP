import {listAgent} from "@/api/seller/seller";
import {checkPermi, checkRole} from "@/utils/permission";

/*配合表单使用*/
/*
*  <select-table
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
  ></select-table>*/
export default {
  created() {
    // 是管理员或供应商则查询
    if(this.checkRole(['admin'])) {
      this.getAgentList()
    }
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
      sellerTable: {
        data: [],
        total: 0,
        pageSize: 10,
        currentPage: 1,
        columns: [
          {label: "代理商名称", prop: "compName"},
          // {label: "项目负责人", prop: "pmName"},
          // {label: "负责人电话", prop: "pmPhone"},
          // {label: "微信群", prop: "wechatGroup"},
          // {label: "是否已开会议", prop: "ifMeeting"},
          // {label: "培训人", prop: "trainer"},
          //
          // {label: "是否有微信小程序", prop: "ifWechatMiniapp"},
          // {label: "合作模式", prop: "cooperationMode"},
          // {label: "备注", prop: "remark"}
        ],
      },
    }
  },
  methods : {
    // 销售方列表
    async getAgentList() {
      const queryParams = {
        pageNum: this.sellerTable.currentPage,
        pageSize: this.sellerTable.pageSize,
        compName: this.query.compName ? this.query.compName : undefined,
        id: this.query.compId ? this.query.compId: undefined,
      }
      const res = await listAgent(queryParams)
      this.sellerTable.data = res.rows;
      this.sellerTable.total = res.total;
    },
    // 筛选销售方
    async filterAgent(val) {
      console.log({val})
      this.query.compName = val
      await this.getAgentList()
    },
    async agentPageChange(currentPage) {
      this.sellerTable.currentPage = currentPage
      await this.getAgentList()
    },
    // sellerFormChange (val) {
    //   this.form.compId = val && val.id ? val.id : undefined
    // },
        /*
    * @val Object seller对象
    * @vModel Ref 组件中绑定的值
    * @idParam  Refparams 要修改的 值的id属性
    * @nameParm  Refparams 要修改的 值的name属性
    * */
    agentFormChange (val,vModel,idParam,nameParam) {
      if(val.id) {
        if(idParam) {
          vModel[idParam] = val.id
        }
        if(nameParam) {
          vModel[nameParam] = val.compName
        }
      }
    } ,
    async initAgentShow (vModel,param,name) {
      this.compIdForm = vModel[param]
      if(name) {
        // todo 回显之前 先查一下列表
        this.query.compName =  vModel[name]
        await this.getAgentList()
        this.$nextTick( ()=>{
          this.$refs.sellerForm.searchKey = vModel[name]
          this.$refs.sellerForm.defaultValue.compName = vModel[name]
        })
      }
    },
    // 清空表单中的
    resetFormAgent() {
      // this.$refs.sellerForm.clear()
      // this.$refs.sellerForm.defaultValue = ''
      this.compIdForm = ''
    },
    resetQueryAgent () {
      console.log('vue --- clear')
      this.queryParams.compId = ''
      this.compIdQuery = ''
      this.queryParams.parentAgentId=''
      this.query.compName = ''
    },
    // 这个要写在业务组件中 来覆盖掉此方法
    agentQueryChange(val) {
      /* this.queryParams 业务逻*/
      this.queryParams.parentAgentId = val && val.id ? val.id : undefined
    },
    checkPermi,
    checkRole,
  }
}
