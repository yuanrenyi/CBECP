<template>
<div class="app-container">
  <el-card class="mb20" v-if="checkRole(['SELLER_ADMIN','admin'])">
    <a-row :gutter="24">
      <a-col :span="12" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="总销售额" :total="'￥'+ dataReportInfo.orderAmountCount">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
<!--          <div>
            <trend flag="up" style="margin-right: 16px;">
              <span slot="term">周同比</span>
              12%
            </trend>
            <trend flag="down">
              <span slot="term">日同比</span>
              11%
            </trend>
          </div>-->
          <template slot="footer">今日销售额<span>￥ {{ todaySaleInfo.todaySaleAmount}}</span></template>
        </chart-card>
      </a-col>
      <a-col :span="12" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="订单量" :total="''+ dataReportInfo.orderCount">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
<!--            <mini-area />-->
            <line-chart-area height="50px" :chart-data="saleCountTable" area></line-chart-area>
          </div>
          <template slot="footer">今日订单量<span> {{ todaySaleInfo.todaySaleCount }}</span></template>
        </chart-card>
      </a-col>
<!--      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="支付笔数" total="6560">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <mini-bar-chart height="50px" :chart-data="barData"></mini-bar-chart>
          </div>
          <template slot="footer">转化率 <span>60%</span></template>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="运营活动效果" total="78%">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <mini-progress color="rgb(19, 194, 194)" :target="80" :percentage="78" :height="8" />
          </div>
          <template slot="footer">
            <trend flag="down" style="margin-right: 16px;">
              <span slot="term">同周比</span>
              12%
            </trend>
            <trend flag="up">
              <span slot="term">日环比</span>
              80%
            </trend>
          </template>
        </chart-card>
      </a-col>-->
    </a-row>
  </el-card>

  <el-card class="mb20" v-if="checkRole(['SELLER_ADMIN','admin'])">
    <div class="salesCard">
      <div class="flex">
        <div class="left flex-1">
          <el-tabs v-model="activeName" @tab-click="changeTab">
            <div class="extra-wrapper" slot="tabBarExtraContent">
              <div class="extra-item">
                <a>今日</a>
                <a>本周</a>
                <a>本月</a>
                <a>本年</a>
              </div>
              <el-date-picker
                v-model="date"
                type="daterange"
                align="right"
                unlink-panels
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
              </el-date-picker>
            </div>
            <el-tab-pane  loading="true" label="销售额" name="1">
              <bar-chart ref="barChart" :chart-data="saleAmountTable"></bar-chart>
            </el-tab-pane >
            <el-tab-pane label="销售趋势" name="2">
              <line-chart ref="lineChart" :chart-data="saleAmountTable"></line-chart>
            </el-tab-pane>
          </el-tabs>
        </div>
        <div class="right" style="width: 30%" v-if="checkRole(['admin'])">
            <div class="flex align-items-center mb20 title-box"> 销售商统计 </div>
            <div class="flex-1">
              <pie-chart :value="salePercentData"></pie-chart>
            </div>
        </div>
      </div>
    </div>
  </el-card>
  <el-card class="mb20" v-if="checkRole(['admin'])">
    <div >
      <el-row>
        <el-col :span="12">
          <rank-list :list="rankData.amountRankList" title="销售额排行"></rank-list>
        </el-col>
        <el-col :span="12">
          <rank-list :list="rankData.countRankList" title="订单量排行"></rank-list>
        </el-col>
      </el-row>
    </div>
  </el-card>
  <el-card  v-if="checkRole(['SUPPLIER_ADMIN','SELLER_ADMIN','admin'])">
    <div slot="header" class="clearfix">
      <span>近期商品动销</span>
      <el-button style="float: right; padding: 3px 0" type="text" @click="goGoodsSaleList">更多</el-button>
    </div>
        <el-table
          border
          stripe
          v-loading="loading"
          header-cell-class-name="table-header-cell"
          :data="goodsSaleList">
<!--          <el-table-column type="index" width="55" align="center" />-->
          <el-table-column label="商品名称" align="center" prop="goodsName" />
          <el-table-column label="外部编码" align="center" prop="goodsOutCode" />
          <el-table-column label="商品编码" align="center" prop="goodsBarcode" />
          <el-table-column label="销售数量" align="center" prop="quantity" />
          <el-table-column label="销售金额" align="center" prop="orgIncome" />
          <el-table-column label="商品价格" align="center" prop="price" />
          <el-table-column label="商品库存" align="center" prop="stockNum" />
        </el-table>
  </el-card>

</div>
</template>

<script>
import chartCard from "@/views/dataReport/ChartCard.vue";
import MiniProgress from './components/MiniProgress'
import Trend from './components/Trend/Trend'
import BarChart from '@/views/dashboard/BarChart.vue'
import LineChart from '@/views/dashboard/LineChart.vue'
import LineChartArea from "@/views/dataReport/components/LineChartArea.vue";
import MiniBarChart from "@/views/dataReport/components/MiniBarChart.vue";
import PieChart from "@/views/dataReport/components/PieChart.vue";
import RankList from './components/RankList'
import moment from "dayjs";
import {listInfo} from "@/api/order/orderinfo";
import {amountRank, countRank, dataTable, salePercent} from "@/api/data-report";
import {goodsSale} from "@/api/goods/goods";
import {checkPermi, checkRole} from "@/utils/permission";
export default {
  components: {
    chartCard,
    MiniProgress,
    Trend,
    RankList,
    BarChart,
    LineChart,
    LineChartArea,
    MiniBarChart,
    PieChart
  },
  dicts: ['order_status', 'order_pay_mode','order_type',],
  name: "index",
  data () {
    return {
      activeName:'1',
      loading: false,
      date: [],
      orderList:[],
      isReRender: false,
      salePercentData: [],
      // 动销商品查询
      goodsSaleQuery: {
        pageSize: 5,
        pageNum: 1
      },
      // 动销商品列表
      goodsSaleList: [],
      rankData: {
        countRankList: [],
        amountRankList: []
      },
      dataReportInfo: {
        saleData7Days: [],
        orderAmountCount: 0,
        orderCount: 0
      }
    }
  },
  created() {
    this.getReportData()
    this.getSalePercent()
    this.getRankData()
    this.getGoodsSale()
  },
  computed : {
    todaySaleInfo() {
      let todaySaleCount = 0 , todaySaleAmount = 0
      if(this.dataReportInfo.saleData7Days.length > 0) {
        todaySaleCount = this.dataReportInfo.saleData7Days[this.dataReportInfo.saleData7Days.length -1]['saleCount']
        todaySaleAmount = this.dataReportInfo.saleData7Days[this.dataReportInfo.saleData7Days.length -1]['saleAmount']
      }
      return {
        todaySaleCount: todaySaleCount,
        todaySaleAmount: todaySaleCount
      }
    },
    saleCountTable () {
      let temp = {data: [],xAxis:[]}
      this.dataReportInfo.saleData7Days.forEach(item => {
        temp.data.push(item.saleCount)
        temp.xAxis.push(item.saleDate)
      })
      return temp
    },
    saleAmountTable () {
      let temp = {data: [],xAxis:[]}
      this.dataReportInfo.saleData7Days.forEach(item => {
        temp.data.push(item.saleAmount)
        temp.xAxis.push(item.saleDate)
      })
      return temp
    }
  },
  methods: {
    goGoodsSaleList() {
      this.$router.push({name: 'GoodSaleList'})
    },
    // 商品动销
    getGoodsSale(){
      goodsSale(this.goodsSaleQuery).then(res =>{
        this.goodsSaleList = res.rows
      })
    },
    getRankData () {
      countRank().then(res => {
        this.rankData.countRankList = res.data
      })
      amountRank().then(res => {
        this.rankData.amountRankList = res.data
      })

    },
    // 进7日数据报表信息
    getReportData () {
      dataTable().then(res => {
        console.log(res)
        this.dataReportInfo = res.data
      })
    },
    // 销售额饼图
    getSalePercent() {
      salePercent().then(res => {
        let data = res.data.map(item => {
          return {
            name : item.sellerName,
            value : item.saleAmount
          }
        })
        this.salePercentData = data
      })
    },
    changeTab() {
      this.$nextTick(()=>{
        this.$refs.barChart.chart.dispose()
        this.$refs.barChart.initChart(this.saleAmountTable)

        this.$refs.lineChart.chart.dispose()
        this.$refs.lineChart.initChart(this.saleCountTable)
        // this.$refs.lineChart.chart.resize()
      })
    },
    checkPermi,
    checkRole
  }

}
</script>

<style lang="less" scoped>
.app-container {
  overflow: auto;
}
.title-box {
  height: 40px;
  //margin-bottom: 25px;
  padding-left: 20px;
  font-size: 16px;
}

.circle-cust{
  position: relative;
  top: 28px;
  left: -100%;
}
.extra-wrapper {
  line-height: 55px;
  padding-right: 24px;

  .extra-item {
    display: inline-block;
    margin-right: 24px;

    a {
      margin-left: 24px;
    }
  }
}

/* 首页访问量统计 */
.head-info {
  position: relative;
  text-align: left;
  padding: 0 32px 0 0;
  min-width: 125px;

  &.center {
    text-align: center;
    padding: 0 32px;
  }

  span {
    color: rgba(0, 0, 0, .45);
    display: inline-block;
    font-size: .95rem;
    line-height: 42px;
    margin-bottom: 4px;
  }
  p {
    line-height: 42px;
    margin: 0;
    a {
      font-weight: 600;
      font-size: 1rem;
    }
  }
}
</style>
