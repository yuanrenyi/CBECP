<template>
  <div class="app-container">

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:info:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >展开/折叠</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['catalog:info:export']"
        >导出
        </el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="infoList"
      row-key="id"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column prop="catalogName" label="分类名称" :show-overflow-tooltip="true" ></el-table-column>
      <el-table-column label="分类图标">
        <template slot-scope="scope">
          <!-- trigger(触发方式)、placement(出现位置) -->
          <el-popover trigger="hover" placement="left">
            <!-- table中原本显示的图片 -->
            <img slot="reference" :src="scope.row.catalogIcon" :alt="scope.row.catalogIcon" style="width: 50px;height: 50px;">
            <!-- 鼠标移入时弹出的图片 -->
            <img :src="scope.row.catalogIcon" alt="" class="big">
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="sortNo" label="序号" :show-overflow-tooltip="true" ></el-table-column>
      <el-table-column prop="catalogLevel" label="分类等级" :show-overflow-tooltip="true" ></el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['catalog:info:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['catalog:info:add']"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['catalog:info:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>


    <!-- 添加或修改商品分类信息对话框 -->
    <a-modal :title="title" :visible.sync="open" width="65%" append-to-body @cancel="open = false">
      <el-form ref="form" class="edit" :model="form" :rules="rules" label-width="98px" label-position="left">
        <el-row>

          <el-col :span="24">
            <el-form-item label="分类名称" prop="catalogName">
              <el-input v-model="form.catalogName" placeholder="请输入分类名称" />
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="上级分类" prop="parentId">
              <treeselect
                v-model="form.parentId"
                :options="catalogOptions"
                :normalizer="normalizer"
                :show-count="true"
                placeholder="选择上级分类"
              />
            </el-form-item>
          </el-col>

          <el-col :span="24" class="mb10">
            <el-form-item label="分类图标" prop="catalogIconName">
              <el-input v-model="form.catalogIconName" readonly clearable @clear="clearFile"
                        placeholder="请选择分类图标">
                <el-button slot="append" icon="el-icon-upload" @click="emitUpload">选择图标</el-button>
              </el-input>
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="序号" prop="sortNo">
              <el-input v-model="form.sortNo" placeholder="请输入序号" />
            </el-form-item>
          </el-col>
        </el-row>

        <file-upload
          style="display: none"
          ref="fileupload"
          :show-list="false"
          :file-type="['jpg','jpeg','png']"
          v-model="form.catalogIconName"
          @files-change="getFile">
          <el-button size="mini" type="primary">{{ form.id ? '重新选取' : '选取文件' }}</el-button>
        </file-upload>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </a-modal>
  </div>
</template>

<script>
import { listInfo, getInfo, delInfo, addInfo, updateInfo, listInfo2 } from "@/api/system/catalog";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Info",
  components: { Treeselect},
  data() {
    return {
      // 分类树选项
      catalogOptions: [],
      refreshTable: true,
      isExpandAll: false,
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
      // 商品分类信息表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        catalogName: null,
        catalogLevel: null,
        parentId: null,
        sortNo: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ],
      },
      currentRow:{}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getFile(files) {
      console.log(files)
      this.form.catalogIconName = files[0].originalFilename
      this.form.catalogIcon = files[0].url
    },
    clearFile() {
      console.log(11111)
    },
    emitUpload() {
      this.$refs.fileupload.$el.querySelector('input').click()
    },
    /** 查询商品分类信息列表 */
    getList() {
      this.loading = true;
      listInfo(this.queryParams).then(response => {
        this.infoList = response.data;
        this.loading = false;
      });
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
        catalogName: null,
        catalogLevel: null,
        catalogIcon: null,
        catalogIconName: null,
        parentId: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null,
        sortNo: null,
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
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.id) {
        this.form.parentId = row.id;
      } else {
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "添加分类";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商品分类信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.reset();
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.reset();
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除商品分类信息编号为"' + ids + '"的数据项？').then(function() {
        return delInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('catalog/info/export', {
        ...this.queryParams
      }, `catalog_${new Date().getTime()}.xlsx`)
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** 转换分类数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.catalogName,
        children: node.children
      };
    },

    /** 查询分类下拉树结构 */
    getTreeselect() {
      listInfo2().then(response => {
        this.catalogOptions = [];
        const catalog = { id: 0, catalogName: '主分类', children: [] };
        catalog.children = this.handleTree(response.data, "id");
        this.catalogOptions.push(catalog);
      });
    },
  }
};
</script>
