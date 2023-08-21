<template>
  <div :class="className" :style="{height:height,width:width}"/>
</template>

<script>
import * as echarts from 'echarts';

// require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    area: {
      type: Boolean,
      default: false
    },
    height: {
      type: String,
      default: '120px'
    },
    autoResize: {
      type: Boolean,
      default: true
    },
    chartData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      chart: null
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        this.setOptions(val)
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      // this.chart = echarts.init(this.$el, 'macarons2')
      this.chart = echarts.init(this.$el, )
      this.setOptions(this.chartData)
    },
    setOptions(chartData = {}) {
      this.chart.setOption({
        xAxis: {
          // 横坐标
          data: chartData.xAxis,
          boundaryGap: false,
          type: 'category',
          axisTick: {
            show: false // 刻度线
          },
          axisLine :{
            show:false
          },
          axisLabel: {
            show: false // x轴
          }
        },
        grid: {
          left: 10,
          right: 10,
          // bottom: 20,
          bottom: 0,
          // top: 30,
          top: 0,
          // containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          // axisPointer: {
          //   type: 'cross'
          // },
          padding: [5, 10]
        },
        yAxis: {
          show:false,// 不显示刻度线
          axisTick: {
            show: false
          },
          axisLine :{
            show:false
          },
          axisLabel: {
            show: false // x轴
          }
        },
        legend: {
          data: []
        },
        series: [
          {
            data: this.chartData.data,
            type: 'line',
            smooth: true,
            symbol:'none',// 点的样式
            areaStyle:  {
              color: '#3aa1ff',
              opacity: 0.5
            }
          }
        /*  {
            name: 'expected', itemStyle: {
              normal: {
                color: '#FF005A',
                lineStyle: {
                  color: '#FF005A',
                  width: 2
                }
              }
            },
            smooth: true,
            type: 'line',
            data: expectedData,
            animationDuration: 2800,
            animationEasing: 'cubicInOut'
          },
          {
            name: 'actual',
            smooth: true,
            type: 'line',
            itemStyle: {
              normal: {
                color: '#3888fa',
                lineStyle: {
                  color: '#3888fa',
                  width: 2
                },
                areaStyle: {
                  color: '#f3f8ff'
                }
              }
            },
            data: actualData,
            animationDuration: 2800,
            animationEasing: 'quadraticOut'
          }*/
        ]
      })
    }
  }
}
</script>
