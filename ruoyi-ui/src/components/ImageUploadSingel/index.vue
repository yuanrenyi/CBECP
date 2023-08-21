<template>
  <div class="component-upload-image">
    <el-upload
      :action="uploadImgUrl"
      list-type="picture-card"
      :on-success="handleUploadSuccess"
      :before-upload="handleBeforeUpload"
      :limit="limit"
      :on-error="handleUploadError"
      :on-exceed="handleExceed"
      ref="imageUpload"
      :on-remove="handleDelete"
      :show-file-list="true"
      :headers="headers"
      :file-list="fileList"
      :on-preview="handlePictureCardPreview"
      :http-request="uploadFn"
    >
      <i class="el-icon-plus"></i>
    </el-upload>

    <!-- 上传提示 -->
    <div class="el-upload__tip" slot="tip" v-if="showTip">
      请上传
      <template v-if="fileSize"> 大小不超过 <b style="color: #f56c6c">{{ fileSize }}MB</b> </template>
      <template v-if="fileType"> 格式为 <b style="color: #f56c6c">{{ fileType.join("/") }}</b> </template>
      的文件
    </div>

    <el-dialog
      :visible.sync="dialogVisible"
      title="预览"
      width="800"
      append-to-body
    >
      <img
        :src="dialogImageUrl"
        :style="{display: 'block', maxWidth:maxWidth ,margin: '0 auto'}"
      />
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {uploadImg} from "@/api/goods/goods";

export default {
  props: {
    value: [String, Object, Array],
    // 图片数量限制
    limit: {
      type: Number,
      default: 5,
    },
    maxWidth: {
      type: String,
      default: "300px",
    },
    // 大小限制(MB)
    fileSize: {
       type: Number,
      default: 5,
    },
    // 文件类型, 例如['png', 'jpg', 'jpeg']
    fileType: {
      type: Array,
      default: () => ["png", "jpg", "jpeg"],
    },
    // 是否显示提示
    isShowTip: {
      type: Boolean,
      default: true
    },
    picVersion: {
      type: String,
    },
    picType: {
      type: String,
    },
    goodsBarcode: {
      type: Number|String
    }
  },
  data() {
    return {
      number: 0,
      uploadList: [],
      dialogImageUrl: "",
      dialogVisible: false,
      hideUpload: false,
      baseUrl: process.env.VUE_APP_BASE_API,
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      file: null,
      fileList: []
    };
  },
  watch: {
    value: {
      handler(val) {
        if (val) {
          // 首先将值转为数组
          const list = Array.isArray(val) ? val : this.value.split(',');
          // 然后将数组转为对象数组
          this.fileList = list.map(item => {
            if (typeof item === "string") {
              // if (item.indexOf(this.baseUrl) === -1) {
              //     item = { name: this.baseUrl + item, url: this.baseUrl + item };
              // } else {
              //     item = { name: item, url: item };
              // }
              item = { name: item, url: item };
            }
            item.name = item.name
            item.url = item.picUrl
            return item;
          });
        } else {
          this.fileList = [];
          return [];
        }
      },
      deep: true,
      immediate: true
    }
  },
  computed: {
    // 是否显示提示
    showTip() {
      return this.isShowTip && (this.fileType || this.fileSize);
    },
    goodsSimpleInfo () {
      return {
        goodsBarcode: this.goodsBarcode,
        picType: this.picType,
      }
    }
  },
  methods: {
    uploadFn() {
      uploadImg({
        ...this.goodsSimpleInfo,
        file: this.file,
        picVersion: this.picVersion
      }).then(res => {
        console.log(res)
        this.uploadList.push({ name: res.fileName, url: res.url });
        this.uploadedSuccessfully(this.uploadList);
      }).catch( err => {
        this.number--;
        this.$modal.closeLoading();
        this.$modal.msgError(err);
        this.$refs.imageUpload.handleRemove(this.file);
        this.uploadedSuccessfully();
      })
    },
    // 上传前loading加载
    handleBeforeUpload(file) {
      let isImg = false;
      if (this.fileType.length) {
        let fileExtension = "";
        if (file.name.lastIndexOf(".") > -1) {
          fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
        }
        isImg = this.fileType.some(type => {
          if (file.type.indexOf(type) > -1) return true;
          if (fileExtension && fileExtension.indexOf(type) > -1) return true;
          return false;
        });
      } else {
        isImg = file.type.indexOf("image") > -1;
      }

      if (!isImg) {
        this.$modal.msgError(`文件格式不正确, 请上传${this.fileType.join("/")}格式文件!`);
        return false;
      }
      if (this.fileSize) {
        const isLt = file.size / 1024 / 1024 < this.fileSize;
        if (!isLt) {
          this.$modal.msgError(`上传头像文件大小不能超过 ${this.fileSize} MB!`);
          return false;
        }
      }
      this.file = file
      this.$modal.loading("正在上传文件，请稍候...");
      this.number++;
    },
    // 文件个数超出
    handleExceed() {
      this.$modal.msgError(`上传文件数量不能超过 ${this.limit} 个!`);
    },
    // 上传成功回调
    handleUploadSuccess(res, file) {
      console.log('上传成功后的回调=====')
      if (res.code === 200) {
        this.uploadList.push({ name: res.fileName, url: res.url });
        this.uploadedSuccessfully(this.uploadList);
      } else {
        this.number--;
        this.$modal.closeLoading();
        this.$modal.msgError(res.msg);
        this.$refs.imageUpload.handleRemove(file);
        this.uploadedSuccessfully();
      }
    },
    // 删除文件
    handleDelete(file) {
      const findex = this.fileList.map(f => f.name).indexOf(file.name);
      if(findex > -1) {
        // this.fileList.splice(findex, 1);
        this.$emit("delfile", file);
      }
    },
    // 上传失败
    handleUploadError() {
      this.$modal.msgError("上传文件失败，请重试");
      this.$modal.closeLoading();
    },
    // 上传结束处理
    uploadedSuccessfully(fileInfo) {
        this.number = 0;
        this.$emit("input", fileInfo);
        this.$modal.closeLoading();
    },
    // 预览
    handlePictureCardPreview(file) {
      console.log(file)
      // this.dialogImageUrl = file.url;
      this.dialogImageUrl = file.picUrl;
      this.dialogVisible = true;
    },
  }
};
</script>
<style scoped lang="scss">
// .el-upload--picture-card 控制加号部分
::v-deep.hide .el-upload--picture-card {
    display: none;
}
// 去掉动画效果
::v-deep .el-list-enter-active,
::v-deep .el-list-leave-active {
    transition: all 0s;
}
img {
  width: 100%;
  height: auto;
  object-fit: cover;
}
::v-deep .el-list-enter, .el-list-leave-active {
    opacity: 0;
    transform: translateY(0);
}
::v-deep.component-upload-image .el-upload-list--picture-card .el-upload-list__item-thumbnail  {
  width: 100%;
  height: auto;
  object-fit: cover;
}
</style>

