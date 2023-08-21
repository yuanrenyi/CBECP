<template>
  <div class="app-container">
    <el-card class="box-card">
    <div slot="header" class="flex flex-justify-between align-items-center ml10 mr10">
      <span>{{ isUpdate ? '修改商品' : '新增商品' }}</span>
      <div>
        <el-button class="ml10" type="text" @click="submitForm" :disabled="disableEdit">{{ isUpdate ? '确认修改' : '确认保存' }}</el-button>
        <el-button class="ml10" type="text" @click="goGoodList" :disabled="disableEdit"> 返回列表 </el-button>
      </div>
    </div>
      <div class="content">
        <el-form ref="form" class="edit" :model="form" :rules="rules" label-width="98px" :disabled="disableEdit">
          <el-row :gutter="10">
            <el-col :xs="24" :sm="24" :md="16" :lg="12" :xl="9.6" >
              <el-form-item label="商品名称" prop="goodsName">
                <el-input v-model="form.goodsName" placeholder="请输入商品名称"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="{span: 4.8}" >
              <el-form-item label="所在仓库" prop="storehouseId">
                <select-table
                  v-model="storehouseIdForm"
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
                ></select-table>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item v-if="checkRole(['admin'])" label="所属供应商" prop="supplierId">
                <el-select disabled value-key="supplierId" v-model="form.supplierId" placeholder="请输入所属供应商">
                  <el-option
                    v-for="item in supplierOptionList"
                    :key="item.supplierId"
                    :label="item.supplierName"
                    :value="item.supplierId"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="商品编码" prop="goodsCode">
                <el-input v-model="form.goodsCode" placeholder="请输入商品编码"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="外部编码" prop="goodsOutCode">
                <el-input v-model="form.goodsOutCode" placeholder="请输入外部编码"/>
              </el-form-item>
            </el-col>

            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="商品条形码" prop="goodsBarcode">
                <el-input v-model="form.goodsBarcode" placeholder="请输入商品条形码"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="品牌" prop="goodsBrand">
                <el-input v-model="form.goodsBrand" placeholder="请输入品牌"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="9.6" :lg="9.6" :xl="9.6" >
              <el-form-item label="分类" prop="goodsClassificationArr">
                <el-cascader
                  placeholder="请选择分类"
                  v-model="form.goodsClassificationArr"
                  :options="catelogList"
                  :show-all-levels="true"
                  :props="{
                    multiple: true,
                    label: 'catalogName',
                    checkStrictly: true,
                    value: 'id',
                    emitPath: false
                  }"
                  clearable
                >
                </el-cascader>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="单位" prop="goodsUnit">
                <el-input v-model="form.goodsUnit" placeholder="请输入单位"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="重量" prop="goodsWeight">
                <el-input-number :min="0"  :precision="2" :step="0.1" controls-position="right"
                                 v-model="form.goodsWeight" placeholder="请输入重量"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="成本价" prop="costPrice">
                <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right"
                                 v-model="form.costPrice" placeholder="请输入成本价"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="税率%" prop="taxRate">
                <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right"  v-model="form.taxRate"
                                 placeholder="请输入税率%"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="库存数量" prop="stockNum">
                <el-input-number :min="0"  :step="0.1" controls-position="right"  v-model="form.stockNum"
                                 placeholder="请输入库存数量"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="锁定库存数量" prop="stockLockNum">
                <el-input-number :min="0" disabled  :step="1" controls-position="right"
                                 v-model="form.stockLockNum" placeholder="请输入锁定库存数量"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="对接货品ID" prop="dockingGoodsId">
                <el-input v-model="form.dockingGoodsId" placeholder="请输入对接货品ID"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="HS编码" prop="hsCode">
                <el-input v-model="form.hsCode" placeholder="请输入HS编码"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="JD指导价" prop="jdGuidancePrice">
                <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right"
                                 v-model="form.jdGuidancePrice" placeholder="请输入JD指导价"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="TM指导价" prop="tmGuidancePrice">
                <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right"
                                 v-model="form.tmGuidancePrice" placeholder="请输入TM指导价"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="WY指导价" prop="wyGuidancePrice">
                <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right"
                                 v-model="form.wyGuidancePrice" placeholder="请输入WY指导价"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="建议零售价" prop="msrp">
                <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right"  v-model="form.msrp"
                                 placeholder="请输入建议零售价"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="裸价" prop="nakedPrice">
                <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right"
                                 v-model="form.nakedPrice" placeholder="请输入裸价"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="上架状态" prop="platOnShelf">
                <el-select v-model="form.platOnShelf" placeholder="请选择上架状态">
                  <el-option
                    v-for="dict in dict.type.goods_shelves_status"
                    :key="dict.value"
                    :label="dict.label"
                    :value="parseInt(dict.value)"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="是否推荐为推荐商品" prop="recommend">
                <el-select @focus="focusSelect('recommend')" ref="recommend" v-model="form.recommend"
                           placeholder="请选择是否推荐为推荐商品">
                  <el-option
                    v-for="dict in dict.type.goods_is_recommend"
                    :key="dict.value"
                    :label="dict.label"
                    :value="parseInt(dict.value)"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="续重运费" prop="freightRenewalAmount">
                <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right" v-model="form.freightRenewalAmount" placeholder="请输入续重运费"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="毛重" prop="grossWeight">
                <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right" v-model="form.grossWeight" placeholder="请输入毛重"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="运费" prop="freightAmount">
                <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right" v-model="form.freightAmount" placeholder="请输入运费"/>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="最终运费" prop="finalFreightAmount">
                <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right" v-model="form.finalFreightAmount" placeholder="请输入最终运费"/>
              </el-form-item>
            </el-col>

            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="是否产品包" prop="ifPackage">
                <el-select v-model="form.ifPackage" placeholder="请选择是否产品包">
                  <el-option
                    v-for="dict in dict.type.goods_if_package"
                    :key="dict.value"
                    :label="dict.label"
                    :value="parseInt(dict.value)"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="是否自建" prop="ifBuildByOneself">
                <el-select v-model="form.ifBuildByOneself" placeholder="请选择是否自建">
                  <el-option
                    v-for="dict in dict.type.goods_if_buildby_oneself"
                    :key="dict.value"
                    :label="dict.label"
                    :value="parseInt(dict.value)"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="产品包关联商品条码" prop="barCodeList">
                <el-input v-model="form.barCodeList" placeholder="产品包关联商品条码" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="产品包关联商品数量" prop="numberList">
                <el-input-number :min="0"  :step="1" controls-position="right" v-model="form.numberList" placeholder="请输入产品包关联商品数量" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="增值税税率" prop="vatRate">
                <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right" v-model="form.vatRate" placeholder="请输入增值税税率" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="关税税率" prop="tariffRate">
                <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right" v-model="form.tariffRate" placeholder="请输入关税税率" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="消费税税率" prop="exciseTaxRate">
                <el-input-number :min="0" :precision="2" :step="0.1" controls-position="right" v-model="form.exciseTaxRate" placeholder="请输入消费税税率" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" >
              <el-form-item label="图片路径" prop="picVersion" >
                <el-input v-model="form.picVersion" placeholder="0" />
              </el-form-item>
            </el-col>

            <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="12" >
              <el-form-item label="备注" prop="remark">
                <el-input type="textarea" :autosize="{minRows: 1 , maxRows: 2}" v-model="form.remark"
                          placeholder="请输入备注"/>
              </el-form-item>
            </el-col>
          </el-row>
          <div>
            <el-form-item label="商品主图" prop="remark">
              <ImageUpload2 :goodsBarcode="form.goodsBarcode"  :picVersion="picInfo1.picVersion" picType="1" :limit="50" :value="picInfo1" @delfile="deletePic($event)" @input="uploadImg($event,1)"></ImageUpload2>
            </el-form-item>
          </div>
          <div>
            <el-form-item label="商品详情图" prop="picUrl">
              <ImageUpload2 :goodsBarcode="form.goodsBarcode"  :picVersion="picInfo2.picVersion" picType="2" :limit="50" :value="picInfo2" @delfile="deletePic($event)" @input="uploadImg($event,2)"></ImageUpload2>
            </el-form-item>
          </div>
          <div>
            <el-form-item label="商品视频" prop="picUrl">
              <video-upload :goodsBarcode="form.goodsBarcode" :picVersion="picInfo3.picVersion" :file-type="['mp4', 'avi', 'rmvb']" @delfile="deletePic($event)" :file-size="50"
                            @input="uploadImg($event,3)"
                            :value="picInfo3"></video-upload>
            </el-form-item>
          </div>
          <div>
            <el-form-item label="商品缩略图" prop="picUrl">
              <ImageUpload2 :goodsBarcode="form.goodsBarcode" :picVersion="picInfo4.picVersion"  picType="4" :limit="50" :value="picInfo4" @delfile="deletePic($event)" @input="uploadImg($event,4)"></ImageUpload2>
            </el-form-item>
          </div>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
import {listStorehouse} from "@/api/seller/storehouse";
import {listSupplier} from "@/api/suppiler/supplier";
import {checkPermi, checkRole} from "@/utils/permission";
import {addGoods, getGoods, updateGoods} from "@/api/goods/goods";
import storeHouseMix from "@/mixins/storeHouseMix";
import supplierMix from "@/mixins/supplierMix";
import {addGoodPic, delGoodPic} from "../../api/goods/goods";
import { listInfo } from "@/api/system/catalog";

export default {
  name: "GoodAddOrUpdate",
  mixins: [storeHouseMix,supplierMix ],
  dicts: ['goods_shelves_status','goods_is_recommend', 'goods_if_package', 'goods_if_buildby_oneself'],
  data() {
    return {
      catelogList: [],
      isUpdate: false,
      disableEdit: false,
      // 表单参数
      form: {
        storehouseName: null,
        id: null,
        storehouseId: null,
        supplierId: null,
        goodsCode: null,
        goodsOutCode: null,
        goodsName: null,
        goodsBarcode: null,
        goodsBrand: null,
        goodsClassificationArr: [],
        goodsUnit: null,
        goodsWeight: null,
        costPrice: null,
        taxRate: null,
        stockNum: null,
        stockLockNum: null,
        dockingGoodsId: null,
        hsCode: null,
        jdGuidancePrice: null,
        tmGuidancePrice: null,
        wyGuidancePrice: null,
        msrp: null,
        nakedPrice: null,
        onShelfStatus: 0,
        freightRenewalAmount: null,
        grossWeight: null,
        freightAmount: null,
        finalFreightAmount: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        recommend: 0,
        ifPackage: 0,
        ifBuildByOneself: 0,
        barCodeList: null,
        numberList: null,
        vatRate: null,
        tariffRate: null,
        exciseTaxRate: null,
        picVersion: null,
        picInfo: [],
        onplatSeller: [],
        keyWord: null,
        buyNotice: null,
        specModel: null,
        mainModel: null,
        basis: null,
        originCountry: null,
      },
      picInfo: [
        {
          picUrl: '',
          picType: '1', // 主图
        },
        {
          picUrl: '',
          picType: '2',// 详情图
        },
        {
          picUrl: '',
          picType: '3',// 视频
        },
        {
          picUrl: '',
          picType: '4', // 缩略图
        }
      ],
      query: {
        storehouseName: '',
        supplierName: ''
      },
      supplierOptionList: [],
      // 表单校验
      rules: {
        goodsName: [
          {required: true, message: "商品名称", trigger: "blur"}
        ],
        goodsCode: [
          {required: true, message: "商品编码", trigger: "blur"}
        ],
        storehouseId: [
          {required: true, message: "请选择所在仓库", trigger: "blur"}
        ],
        goodsBarcode: [
          {required: true, message: "请输入商品条形码", trigger: "blur"}
        ],

      },
      currentRowId: '',
    }
  },
  created() {
    const { goodId } = this.$route.params
    this.currentRowId = goodId
    if (goodId) {
      this.isUpdate = true
      // 查询仓库列表
      this.getStoreHouseList().then( async () =>{
        await this.getGoodInfo(goodId)
        // 先得到商品信息再去查询仓库列表
        await this.setStoreHouseInfo()
      })
    }
    this.getCatelogList();
    this.getStoreHouseList()
  },
  mounted() {

  },
  computed: {
    // 主图
    picInfo1() {
      return this.form.picInfo.filter( item => item.picType ==1 )
    },
    // 详情图
    picInfo2() {
      return this.form.picInfo.filter( item => item.picType ==2 )
    },
    // 视频
    picInfo3() {
      return this.form.picInfo.filter( item => item.picType ==3 )
    },
    // 缩略图
    picInfo4() {
      return this.form.picInfo.filter( item => item.picType ==4 )
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
    // 利用列表 上传单张图片
    uploadImg (fileList,type) {
      console.log('fileList')
      console.log(fileList)
      fileList.forEach( item =>{
        let picInfo =   {
          goodsId: this.form.goodsBarcode,
          picType: type,
          picUrl:item.url,
          picVersion: this.form.picVersion,
        }
        addGoodPic(picInfo)
      })
      if(this.currentRowId) {
        this.getGoodsPicInfo(this.currentRowId)
      }
    },
    deletePic (file) {
      console.log(file)
      this.$modal.confirm('确认删除所选图片?').then( () => {
        return delGoodPic(file.id);
      }).then(() => {
        this.getGoodsPicInfo(this.currentRowId)
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
        this.getGoodsPicInfo(this.currentRowId)
      });
    },
    // 图片数据
    getGoodsPicInfo(goodId) {
      getGoods(goodId).then(res => {
        this.form.picInfo = res.data.picInfo;
        // this.setStoreHouseInfo()
      });
    },
    async getGoodInfo (id) {
      const res = await getGoods(id)
      if(!res.data.id)  {
        this.$notification.warn({message: '商品信息查询失败,请重试'})
        this.disableEdit = true
        return
      }
      this.form = res.data
      // this.$nextTick(() => {
      //   this.$refs.chooseCatalog.inputValue = this.form.goodsClassificationArrName
      // })
    },
    // 回显 仓库名称及 供应商名称
    async setStoreHouseInfo() {
      this.supplierOptionList = []
      let obj = {
        supplierId: this.form.supplierId,
        supplierName: this.form.supplierName
      }
      this.supplierOptionList.push(obj)
      // 需要根据仓库名 查询一遍仓库
      await this.filterStoreHouse(this.form.storehouseName)
      this.storehouseIdForm = this.form.storehouseId
      this.$refs.storeHouseForm.searchKey = this.form.storehouseName
    },
    goGoodList () {
      const tab = { name: "GoodsList" };
      this.$tab.closePage(this.$route).then( ()=>{
        this.$router.push(tab)
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid)  return  this.$notification.warn({message: "请完成表单必填项"})
          this.disableEdit = true
          if (this.form.id != null) {
            updateGoods(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.goGoodList()
            });
          } else {
            addGoods(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.goGoodList()
            });
          }
      });
    },
    // 选中仓库
    storeHouseChange(val) {
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
      console.log('storehouseId', this.form.storehouseId)
    },
    storeHouseChangeQuery(val) {
      this.queryParams.storehouseId = val && val.id ? val.id : undefined
    },

    supplierChange(val) {
      if (val.id) {
        this.queryParams.supplierId = val.id
      }
    },
    supplierChangeQuery(val) {
      this.queryParams.supplierId = val && val.id ? val.id : undefined
      this.handleQuery()
    },
    // 清空表单中的  storehouseId
    resetFormStoreHouse() {
      this.form.storehouseId = ''
      this.form.supplierId = ''
    },
    checkRole,
    checkPermi,
  }
}
</script>

<style scoped>

</style>
