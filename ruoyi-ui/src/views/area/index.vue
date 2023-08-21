<template>
  <div class="app-container">
    <el-form class="query" :model="queryParams" ref="queryForm" size="small"  v-show="showSearch" label-width="28px">
      <el-row>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6" class="mb10">
          <el-form-item label="省" prop="provinceid">
            <el-select
              @change="changePro"
              @clear="clearPro"
              clearable
              value-key="provinceID"
              v-model="provinceObj"
              placeholder="请选择省/直辖市">
              <el-option
                v-for="item in provinceList"
                :key="item.provinceID"
                :label="item.province"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6" class="mb10">
          <el-form-item label="市" prop="cityID">
            <el-select
              clearable
              @change="changeCity"
              @clear="clearCity"
              value-key="cityID"
              v-model="cityObj"
              placeholder="请先选择省/直辖市">
              <el-option
                v-for="item in cityList"
                :key="item.cityID"
                :label="item.city"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6" class="mb10">
          <el-form-item label="区" prop="areaID">
            <el-select
              clearable
              @change="changeArea"
              @clera="clearArae"
              value-key="areaID"
              v-model="areaObj" placeholder="请先选择市">
              <el-option
                v-for="item in areaList"
                :key="item.areaID"
                :label="item.area"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
<!--        <el-col :xs="24" :sm="12" :md="8" :lg="4.8" :xl="4" class="mb10">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-col>-->
      </el-row>
    </el-form>

<!--    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:province:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:province:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:province:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:province:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <vxe-table
      border
      resizable
      ref="xTree"
      :tree-config="{transform: true, rowField: 'id', parentField: 'parentId', iconOpen: 'vxe-icon-square-minus-fill', iconClose: 'vxe-icon-square-plus-fill'}"
      :data="provinceList">
      <vxe-column field="name" title="名称"  tree-node></vxe-column>
      <vxe-column field="size" title="Size"></vxe-column>
      <vxe-column field="type" title="Type"></vxe-column>
      <vxe-column field="date" title="Date"></vxe-column>
    </vxe-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    &lt;!&ndash; 添加或修改省级对话框 &ndash;&gt;
    <a-modal :title="title" :visible.sync="open" width="65%" append-to-body @cancel="open = false">
      <el-form ref="form" class="edit" :model="form" :rules="rules" label-width="98px" label-position="left">
        <el-row>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" class="mb20">
            <el-form-item label="${comment}" prop="provinceid">
              <el-input v-model="form.provinceid" placeholder="请输入${comment}" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6" class="mb20">
            <el-form-item label="${comment}" prop="province">
              <el-input v-model="form.province" placeholder="请输入${comment}" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </a-modal>-->
  </div>
</template>

<script>
// import { listProvince, getProvince, delProvince, addProvince, updateProvince } from "@/api/system/province";

import {allProvince, areaByCityID, cityByProvinceID} from "@/api/area/area";

export default {
  name: "Province",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      provinceObj: {
        cityList:null,
        provinceID: '',
        province: '',
      },
      cityObj: {
        city: '',
        cityID: ''
      },
      areaObj: {
        areaID:'',
        area:'',
      },
      // 省级表格数据
      provinceList: [],
      cityList: [],
      areaList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        provinceid: null,
        province: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        provinceid: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        province: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getProList();
  },
  methods: {
    /** 查询省级列表 */
    getProList() {
      allProvince().then(response => {
        this.provinceList = response.data;
      });
    },
    getCityList (cityID) {
      cityByProvinceID(cityID).then( res=>{
        this.cityList = res.data
      })
    },
    getAraeList (areaID) {
      areaByCityID(areaID).then( res=>{
        this.areaList = res.data
      })
    },
    changePro(provinceObj, vModel, codeParams,nameParams) {
      console.log(provinceObj)
      this.cityObj= {
        city: '',
        cityID: ''
      }
      this.areaObj =  {
        areaID:'',
        area:'',
      }
      if (!provinceObj.provinceID) return
      // 如果传递了 绑定值和参数 则进行绑定
      if (vModel && codeParams && nameParams)  {
        vModel[codeParams] = provinceObj.provinceID
        vModel[nameParams] = provinceObj.province
      }
      this.getCityList(provinceObj.provinceID)
    },
    changeCity(cityObj, vModel, codeParams,nameParams) {
      this.areaObj =  {
        areaID:'',
        area:'',
      }
      if (!cityObj.cityID) return
      // 如果传递了 绑定值和参数 则进行绑定
      if (vModel && codeParams && nameParams)  {
        vModel[codeParams] = cityObj.cityID
        vModel[nameParams] = cityObj.city
      }
      this.getAraeList(cityObj.cityID)
    },
    changeArea(areaObj, vModel, codeParams,nameParams) {
      if (!areaObj.areaID) return
      // 如果传递了 绑定值和参数 则进行绑定
      if (vModel && codeParams && nameParams)  {
        vModel[codeParams] = areaObj.areaID
        vModel[nameParams] = areaObj.area
      }
    },
    clearPro () {
      this.areaList = []
      this.cityList = []
      this.areaID = ''
      this.cityID = ''
    },
    clearCity () {
      this.areaList = []
      this.areaID = ''
    },
    clearArae () {

    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        provinceid: null,
        province: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加省级";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProvince(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改省级";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProvince(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProvince(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除省级编号为"' + ids + '"的数据项？').then(function() {
        return delProvince(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/province/export', {
        ...this.queryParams
      }, `province_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
