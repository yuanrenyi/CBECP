<template>
  <div class="good-list" v-loading="loading" >
      <el-row :gutter="20" class="flex flex-wrap">
        <el-col
          :xs="12" :sm="8" :md="6" :lg="4.8" :xl="4.8"
          v-for="item in list"
          :key="item.goodsName"
          class="item-col"
          >
          <div class="item mb10">
            <div class="flex flex-justify-center" style="width: 100%">
              <img class="main-pic" :src="item.picUrl" alt="商品主图">
            </div>
            <div class="name mt20 mb5">
              {{ item.goodsName }}
            </div>
            <div class="name mb10">
              <span class="mr5">
                税率: {{ item.vatRate }}
              </span>
              <span class="mr5">
                建议零售价: ￥{{ item.msrp }}
              </span>
            </div>
            <div class="tag mb10">
              <el-tag class="mr10" effect="plain" plain type="warning">官方授权</el-tag>
              <el-tag class="mr10" type="warning" >保税区邮</el-tag>
              <el-tag class="mr10" type="warning" >邮税自理</el-tag>
            </div>
<!--            <div class="info mb10" v-for="item2 in item.storehouseInfoList">
              <div class="border-bottom border-top mb5 pt5 pb5">
                <div>
                  <span> 仓库名称:  {{ item2.storehouseName }} </span>
                </div>
                <span class="mr5" > 库存: {{ item2.stockNum }} </span>
                <span class="mr5" > 成本价: {{ item2.costPrice }} </span>
                <span class="mr5" > 裸价:  {{ item2.nakedPrice }} </span>
                <span class="mr5" > 首重运费: {{ item2.freightAmount }} </span>
                <span class="mr5" > 最终运费: {{ item2.finalFreightAmount }} </span>
              </div>
            </div>-->
            <div style="width: 100%">
              <vxe-table
                size="mini"
                align="center"
                max-height="160px"
                :data="item.storehouseInfoList">
                <vxe-column title="仓库名称" field="storehouseName" show-overflow show-header-overflow="tooltip" ></vxe-column>
                <vxe-column title="库存" field="stockNum"  ></vxe-column>
                <vxe-column title="成本价" field="costPrice"  ></vxe-column>
                <vxe-column title="最终运费" field="finalFreightAmount"  ></vxe-column>
              </vxe-table>
            </div>
            <div class="flex flex-justify-end">
              <el-button type="text" @click="goDetail(item)"> 详情 </el-button>
            </div>
          </div>
        </el-col>
      </el-row>
  </div>
</template>

<script>

export default {
  name: "goodSellList",

  props: {
    list: {
      type: Array,
      required: true,
      default() {
        return []
      }
    },
    loading: {
      type: Boolean
    }

  },
  created() {
  },
  data() {
    return {
      goodSellList: [],
      total: 0,

    }
  },
  methods: {
    goDetail(data) {
      // this.$router.push({ name: 'GoodDetailSeller'})
      this.$emit('getDetail',data)
    }
  }
}
</script>

<style scoped lang="scss">
.good-list {
  .item-col {
    align-self: stretch;
  }
  .item {
    border: 1px solid #fed395;
    padding: 10px;
    img.main-pic {
      width: auto;
      max-width: 100%;
      height: 220px;
      object-fit: contain;
    }
  }
}
.border-bottom {
  border-bottom: 1px solid #cccccc;
}
.border-top {
  border-top: 1px solid #cccccc;
}
</style>
