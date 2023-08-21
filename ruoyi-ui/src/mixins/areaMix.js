import {allProvince, areaByCityID, cityByProvinceID} from "@/api/area/area";

export default {
  data() {
    return {
      provinceList: [],
      cityList: [],
      areaList: [],
      // provinceObj: {},
      provinceObj: {
        cityList:null,
        provinceID: '',
        province: '',
      },
      cityObj: {},
      areaObj: {},
    }
  },
  created() {
    this.getProList()
  },
  methods: {
    /** 查询省级列表 */
    async getProList() {
     const res =  await  allProvince()
      this.provinceList =res.data
    },
    async getCityList(proID) {
      const res = await  cityByProvinceID(proID)
      this.cityList = res.data
    },
    async getAraeList(cityID) {
      const res = await areaByCityID(cityID)
      this.areaList = res.data
    },
    changePro(provinceObj, vModel, codeParams,nameParams) {
      if (!provinceObj) return
      // 如果传递了 绑定值和参数 则进行绑定
      if (vModel && codeParams && nameParams)  {
          vModel[codeParams] = provinceObj.provinceID
          vModel[nameParams] = provinceObj.province
      }
      this.getCityList(provinceObj.provinceID)
    },
    changeCity(cityObj, vModel, codeParams,nameParams) {
      if (!cityObj) return
      // 如果传递了 绑定值和参数 则进行绑定
      if (vModel && codeParams && nameParams)  {
        vModel[codeParams] = cityObj.cityID
        vModel[nameParams] = cityObj.city
      }
      this.getAraeList(cityObj.cityID)
    },
    changeArea(areaObj, vModel, codeParams,nameParams) {
      if (!areaObj) return
      // 如果传递了 绑定值和参数 则进行绑定
      if (vModel && codeParams && nameParams)  {
        vModel[codeParams] = areaObj.areaID
        vModel[nameParams] = areaObj.area
      }
    },
    // 清空省的时候 取消 省市和区的绑定
    clearPro(vModel, codeParams, nameParams) {
      this.areaList = []
      this.cityList = []
      this.provinceObj = {}
      this.cityObj = {}
      this.areaObj = {}
      // 3个参数
      if (codeParams) {
        codeParams.forEach( item =>{
          vModel[item] = ''
        })
      }
      if (nameParams) {
        nameParams.forEach( item =>{
          vModel[item] = ''
        })
      }
    },
    // 清空市的时候  取消市 和区的绑定
    clearCity(vModel, codeParams,nameParams) {
      this.areaList = []
      this.areaID = ''
      // 2 个参数
      if (codeParams) {
        codeParams.forEach( item =>{
          vModel[item] = ''
        })
      }
      if (nameParams) {
        nameParams.forEach( item =>{
          vModel[item] = ''
        })
      }
    },
    // 清空市的时候  区的绑定
    clearArae(vModel, codeParams,nameParams) {
      this.areaList = []
      this.areaID = ''
      // 1 个参数
      if (codeParams) {
        codeParams.forEach( item =>{
          vModel[item] = ''
        })
      }
      if (nameParams) {
        nameParams.forEach( item =>{
          vModel[item] = ''
        })
      }
    },
   /*清除城市之前的显示*/
    resetAreaList () {
      this.provinceObj = {
        provinceID: '',
        province: '',
      }
      this.cityObj = {
        cityID: '',
        city: ''
      }
      this.areaObj = {
        area: '',
        areaID:''
      }
      this.cityList = []
      this.areaList = []
    },
    /*回显所选城市*/
    async initSelectArea (proObj ,cityObj , areaObj) {
      if(proObj&& proObj.provinceID) {
        this.$set(this.provinceObj,'provinceID',Number(proObj.provinceID))
        this.$set(this.provinceObj,'province',proObj.province)
        // this.provinceObj = proObj
        await this.getCityList(proObj.provinceID)
        this.$refs.pro.selectedLabel = proObj.province
        if(!this.$refs.city.selected) { this.$refs.pro.selected = {}}
        // this.$refs.pro.selected.currentLabel = proObj.province
        this.$refs.pro.selected.selectedLabel = proObj.province
      }
      if(cityObj && cityObj.cityID) {
        console.log(this.cityList)
        this.$set(this.cityObj,'cityID',Number(cityObj.cityID))
        this.$set(this.cityObj,'city',cityObj.city)
        await this.getAraeList(cityObj.cityID)
        this.$refs.city.selectedLabel = cityObj.city
        if(!this.$refs.city.selected) { this.$refs.city.selected = {}}
        // this.$refs.city.selected.currentLabel = cityObj.city
        this.$refs.city.selected.selectedLabel = cityObj.city
      }
      if(areaObj && areaObj.areaID) {
        this.$set(this.areaObj,'areaID',Number(areaObj.areaID))
        this.$set(this.areaObj,'area',areaObj.area)
        this.$refs.area.selectedLabel = areaObj.area
        if(!this.$refs.area.selected) { this.$refs.area.selected = {}}
        // this.$refs.area.selected.currentLabel = areaObj.area
        this.$refs.area.selected.selectedLabel = areaObj.area
      }
    }
  }
}
