<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts';
import('echarts/theme/macarons') // echarts theme
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
    value: {
      type: Array,
    },
    height: {
      type: String,
      default: '300px'
    }
  },
  data() {
    return {
      chart: null
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
  watch : {
    value() {
      if (!this.chart) {
        return
      }
      this.chart.dispose()
      this.initChart()
    }
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      const that = this
      this.chart.setOption({
        tooltip: {
          trigger: 'item',
          // formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          left: 'center',
          bottom: '10',
          // data: ['Industries', 'Technology', 'Forex', 'Gold', 'Forecasts']
        },
        series: [
          {
            // name: 'WEEKLY WRITE ARTICLES',
            type: 'pie',
            // roseType: 'radius',
            radius: [15, 95],
            center: ['50%', '38%'],
            data: that.value,
            // data: [
            //   { value: 320, name: '销售商1' },
            //   { value: 240, name: '销售商1' },
            //   { value: 149, name: '销售商3' },
            //   { value: 100, name: '销售商4' },
            //   { value: 59, name: '销售商45' }
            // ],
            animationEasing: 'cubicInOut',
            animationDuration: 2600
          }
        ]
      })
    }
  }
}
</script>
