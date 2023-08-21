import Vue from 'vue'

import Cookies from 'js-cookie'

import Element from 'element-ui'
import './assets/styles/element-variables.scss'

import '@/assets/styles/index.scss' // global css
import '@/assets/styles/ruoyi.scss' // ruoyi css
import App from './App'
import store from './store'
import router from './router'
import directive from './directive' // directive
import plugins from './plugins' // plugins
import { download } from '@/utils/request'

import './assets/icons' // icon
import './permission' // permission control
import { getDicts } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import {
  parseTime,
  resetForm,
  addDateRange,
  selectDictLabel,
  selectDictLabels,
  handleTree,
  focusSelect,
  isEllipsis,
  chekOverflow
} from '@/utils/ruoyi'
// 分页组件
import Pagination from "@/components/Pagination";
// 自定义表格工具组件
import RightToolbar from "@/components/RightToolbar"
import RightToolbar2 from "@/components/RightToolbar/toolbar.vue"
// 富文本组件
import Editor from "@/components/Editor"
// 文件上传组件
import FileUpload from "@/components/FileUpload"
// 图片上传组件
import ImageUpload from "@/components/ImageUpload"
import VideoUpload from "@/components/videoUpload"
import VideoPreview from "@/components/videoPreview"
// 图片预览组件
import ImagePreview from "@/components/ImagePreview"
import ImagePreview2 from "@/components/ImagePreview/index2.vue"
// 字典标签组件
import DictTag from '@/components/DictTag'
// 头部标签组件
import VueMeta from 'vue-meta'
// 字典数据组件
import DictData from '@/components/DictData'

import SelectTable from "@/components/SelectTable/index.vue";
// import Tui from "@wocwin/t-ui"

// import Viser from 'viser-vue'

import 'ant-design-vue/dist/antd.less';  // or 'ant-design-vue/dist/antd.less'
import Antd, { version } from 'ant-design-vue'
import ImageUploadSingel from "@/components/ImageUploadSingel/index.vue";

// import VXETable from 'vxe-table'
// import 'vxe-table/lib/style.css'
// 全局方法挂载
Vue.prototype.getDicts = getDicts
Vue.prototype.getConfigKey = getConfigKey
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.selectDictLabels = selectDictLabels
Vue.prototype.download = download
Vue.prototype.handleTree = handleTree
Vue.prototype.focusSelect = focusSelect
Vue.prototype.isEllipsis = isEllipsis

// 全局组件挂载
Vue.component('DictTag', DictTag)
Vue.component('Pagination', Pagination)
Vue.component('RightToolbar', RightToolbar)
Vue.component('RightToolbar2', RightToolbar2)
Vue.component('Editor', Editor)
Vue.component('FileUpload', FileUpload)
Vue.component('ImageUpload', ImageUpload)
Vue.component('ImageUpload2', ImageUploadSingel)
Vue.component('ImagePreview', ImagePreview)
Vue.component('ImagePreview2', ImagePreview2)
Vue.component('VideoUpload', VideoUpload)
Vue.component('VideoPreview', VideoPreview)
Vue.component('SelectTable', SelectTable)

Vue.use(directive)
Vue.use(plugins)
Vue.use(VueMeta)
// Vue.use(Antd,{zIndex:3001})
Vue.use(Antd)
// Vue.use(VXETable)
// Vue.use(Tui)
// Vue.use(Viser)
DictData.install()

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get('size') || 'medium', // set element-ui default size
  // zIndex: 2001,
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
