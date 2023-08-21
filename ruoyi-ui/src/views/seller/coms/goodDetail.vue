<template>
  <div class="good-detail">
    <div class="right">
      <div class="name mb10">
        <h3>
          {{ currentGood.goodsName }}
        </h3>

      </div>
      <div class="mb10">
<!--        <span class="mr5"> 商品编码: {{ goodDetail.goodsCode }} </span>-->
<!--        <span class="mr5"> 外部编码: {{ goodDetail.goodsOutCode }} </span>-->
<!--        <span class="mr5"> 品牌: {{ goodDetail.goodsBrand }} </span>-->
<!--        <span class="mr5"> 类别: {{ goodDetail.goodsClassificationArr }} </span>-->
        <el-descriptions
          title="" :column="4" size="small" border>
          <el-descriptions-item :span="2" label="商品编码">
            {{ currentGood.goodsCode }}
          </el-descriptions-item>
          <el-descriptions-item :span="2" label="外部编码">
            {{ currentGood.goodsOutCode }}
          </el-descriptions-item>
          <el-descriptions-item :span="2" label="品牌">
            {{ currentGood.goodsBrand }}
          </el-descriptions-item>
          <el-descriptions-item :span="2" label="分类">
            <el-cascader
              collapse-tags
              readonly
              popper-class="cascade-popper"
              class="cascade-text"
              placeholder="未分类"
              v-model="currentGood.goodsClassificationArr"
              :options="catelogList"
              :show-all-levels="true"
              :props="{
                  multiple: true,
                  expandTrigger: 'hover',
                  label: 'catalogName',
                  checkStrictly: true,
                  value: 'id',
                  emitPath: false
                }"
            >
            </el-cascader>
          </el-descriptions-item>
          <el-descriptions-item :span="2" label="条形码">
            {{ currentGood.goodsBarcode }}
          </el-descriptions-item>
          <el-descriptions-item :span="2" label="发货方式">
            国内发货
          </el-descriptions-item>
          <el-descriptions-item :span="2" label="效期">
            {{ currentGood.expirDate }}
          </el-descriptions-item>
        </el-descriptions>
      </div>
<!--      <div class="tag mb10">-->
<!--        发货方式: 国内发货-->
<!--      </div>-->
      <div class="info mb10">
        <span class="mr10">选择仓库:</span>
          <el-radio
            size="mini"
            class="mr5"
            v-model="currentGoodRadio"
            v-for="(item,index) in goodDetail.storehouseInfoList"
            @input="changeCurrentGood($event,item)"
            :key="item.goodsId"
            :label="item.goodsId" border>{{ item.storehouseName}}</el-radio>
      </div>
      <div class="mb10">
        <el-descriptions
          :labelStyle="{width: '135px'}"
          :contentStyle="{width: '220px'}"
          class="" title="" :column="4" size="small" border>
          <el-descriptions-item :span="2" label="成本价">
            ￥ {{ parseFloat(currentGood.costPrice).toFixed(2) }}
          </el-descriptions-item>
          <el-descriptions-item :span="2" label="库存">
            {{ currentGood.stockNum}}
          </el-descriptions-item>
        </el-descriptions>
<!--        <span class="mr10">成本价: ￥ {{ parseFloat(currentGood.costPrice).toFixed(2) }}</span>-->
<!--        <span class="mr10">库存: {{ currentGood.stockNum}}</span>-->
      </div>
      <div class="mb20">
      </div>
      <div class="option mb20">
        <!--        0-未上架 1-已上架-->
        <el-button @click="putShelf()" size="mini" plain type="success" icon="el-icon-s-shop" v-if="currentGood.onShelfStatus == 0"> 上架</el-button>
        <el-button @click="offShelf()" size="mini" plain type="danger" icon="el-icon-s-shop" v-if="currentGood.onShelfStatus == 1"> 下架</el-button>
        <el-button @click="picsDownLoad" size="mini" plain type="primary" icon="el-icon-download" class="mr10"> 下载图片</el-button>

          <el-input-number v-model="lockNumber" v-show="showLockForm"  size="mini"  :min="1" :max="currentGood.stockNum"></el-input-number>
          <el-button v-show="!showLockForm" @click="showLockForm = true" size="mini" plain type="warning"  icon="el-icon-lock"> 锁库申请 </el-button>
          <el-button v-show="showLockForm" @click="lockStock" size="mini" plain type="warning" icon="el-icon-lock"> 确认申请 </el-button>
          <el-button v-show="showLockForm" @click="showLockForm = false" size="mini" plain type="info" icon="el-icon-close"> 取消 </el-button>
      </div>
    </div>
    <div v-if="goodDetail && goodDetail.picList">
      <el-row class="mb20">
        <el-col :span="4" class="lh-60px">
          商品主图
        </el-col>
        <el-col :span="20">
          <image-preview2  width="100px" height="100px" :limit="50" :src="picInfo1"></image-preview2>
        </el-col>
      </el-row>
      <el-row class="mb20">
        <el-col :span="4" class="lh-60px">
          商品详情图
        </el-col>
        <el-col :span="20">
          <image-preview2 width="100px" height="100px" :limit="50" :src="picInfo2"></image-preview2>
        </el-col>
      </el-row>
      <el-row class="mb20">
        <el-col :span="4" class="lh-60px">
          商品视频
        </el-col>
        <el-col :span="20">
          <video-preview  :value="picInfo3"></video-preview>
        </el-col>
      </el-row>
      <el-row class="mb20">
        <el-col :span="4" class="lh-60px">
          商品缩略图
        </el-col>
        <el-col :span="20">
          <image-preview2 width="100px" height="100px" :limit="1" :src="picInfo4"></image-preview2>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import {goodSellerDetail, goodspicDownLoad, sellerOffshelf, sellerPutshelf, goodSellerPic} from "@/api/goods/goods";
import {addLock} from "@/api/seller/goodsLock";
import {listInfo} from "@/api/system/catalog";

export default {
  name: "GoodDetailSeller",

  props: {
    goodDetail: {
      type: Object,
      required: true,
      default() {
        return {
          picList: []
        }
      }
    }
  },
  created() {
    this.currentGood = this.goodDetail.storehouseInfoList[0]
    this.currentGoodRadio = this.goodDetail.storehouseInfoList[0].goodsId
    this.getCatelogList();
    this.getSellerPicList(this.goodDetail.storehouseInfoList[0].goodsId)
  },
  mounted() {
  },
  data () {
    return {
      // 当前图片
      currentImg: '',
      // 商品详情
      currentGood: {},
      // 商品单选
      currentGoodRadio: '',
      showLockForm: false,
      lockNumber: 1,
      catelogList: [],

    }
  },
  computed: {
    // 主图
    picInfo1() {
      return this.goodDetail.picList.filter( item => item.picType ==1 )
    },
    // 详情图
    picInfo2() {
      return this.goodDetail.picList.filter( item => item.picType ==2 )
    },
    // 视频
    picInfo3() {
      return this.goodDetail.picList.filter( item => item.picType ==3 )
    },
    // 缩略图
    picInfo4() {
      return this.goodDetail.picList.filter( item => item.picType ==4 )
    }
  },
  methods: {
    //获取商品分类
    getCatelogList() {
      this.loading = true;
      listInfo().then(response => {
        this.catelogList = response.data;
      });
    },

    // 获取当前商品图片
    getSellerPicList(data) {
      let params = {
        id: data,
      }
      goodSellerPic(params).then(response => {
        this.goodDetail.picList = response.data;
      });
    },

    lockStock() {
      if(this.lockNumber > this.currentGood.stockNum) return
      const { goodsId, goodsName} = this.currentGood
      // lockStatus 锁定库存状态 0-已取消 1-锁定中 2-已完成
      let params = {
        goodsId,
        goodsName,
        stockLockNum: this.lockNumber,
        lockStatus: 1
      }
      addLock(params).then(res=>{
        this.$modal.msgSuccess('申请成功')
        this.showLockForm = false
      })
    },
    getSellerDetail (params) {
      goodSellerDetail(params).then(res =>{
        this.currentGood = res.data
      })
    },
    /*销售方上架*/
    putShelf () {
      sellerPutshelf({goodsId:this.currentGood.goodsId}).then(res =>{
        this.currentGood.onShelfStatus = 1
        this.$modal.msgSuccess('上架成功')
      })
    },
    /*下架*/
    offShelf () {
      sellerOffshelf({goodsId:this.currentGood.goodsId}).then(res =>{
        this.currentGood.onShelfStatus = 0
        this.$modal.msgSuccess('下架成功')
      })
    },
    picsDownLoad () {
      this.$download.zipPost("/system/goods/goodspic/batch/download?goodsId=" + this.goodDetail.id, `${this.goodDetail.goodsOutCode}.zip`);
    },
    changeCurrentGood (value,item) {
      console.log({item})
      this.currentGood = item
      this.getSellerPicList(item.goodsId)
    }
  },
}
</script>

<style scoped lang="scss">
.lh-60px {
  line-height: 60px;
}
</style>
