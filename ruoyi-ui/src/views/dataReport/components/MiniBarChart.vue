<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts';
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'

const animationDuration = 1000 * 2

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
    height: {
      type: String,
      default: '300px'
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
  mounted() {
    this.$nextTick(() => {
      this.chart = echarts.init(this.$el, 'macarons2')
      this.setOptions(this.chartData)
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
      this.chart = echarts.init(this.$el, 'macarons2')
      this.setOptions(this.chartData)
    },
    setOptions(chartData = {}) {
      this.chart.setOption({
        xAxis: {
          data: chartData.xAxis,
          axisTick: {
            show: false
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
          show:false,
          axisTick: {
            show: false
          }
        },
        legend: {
          data: []
        },
        series: [
          {
            data: this.chartData.data.map(item =>{
              return  {
                value: item,
                // 设置单个柱子的样式
                itemStyle: {
                  color: '#74bcff',
                  // borderType: 'dashed',
                },
                barGap: '20%',
              }
            }),
            type: 'bar',
            barWidth: '30%'
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
