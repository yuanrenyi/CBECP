<template>
  <div >
    <div class="video-thum" >
      <video
        v-for="item in fileList"
        :key="item.url"
        object-fit="contain"
        muted
        :src="item.picUrl"
        :width="width"
        :height="height"
        class="avatar mr20"
        @click.prevent.stop="handlePictureCardPreview(item)"/>
    </div>
    <a-modal
      :zIndex="2001"
      :visible.sync="dialogVisible"
      @opened="opened"
      title="预览"
      width="800px"
      append-to-body
      @cancel="close"
      @close="close"
    >
      <div class="video-preview">
<!--        <video class="mb10 " controls muted width="600px" height="400px" :src="dialogImageUrl"></video>-->
        <div id="video-box" ref="videobox"></div>
      </div>
      <div slot="footer" class="dialog-footer">
      </div>
    </a-modal>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import Player from 'xgplayer/dist/simple_player';
import volume from 'xgplayer/dist/controls/volume';
import playbackRate from 'xgplayer/dist/controls/playbackRate';

export default {
  props: {
    value: [String, Object, Array],
    // 图片数量限制
    limit: {
      type: Number,
      default: 5,
    },
    width:{
      type: String,
      default: '100px',
    },
    height:{
      type: String,
      default: '100px',
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
    }
  },
  data() {
    return {
      number: 0,
      uploadList: [],
      dialogVideoUrl: "",
      dialogVisible: false,
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      fileList: [],
      player: null
    };
  },
  mounted() {

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
              item = { name: item, url: item };
            }
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
  },
  methods: {
    // 预览
    handlePictureCardPreview(item) {
      this.dialogVideoUrl = item.picUrl;
      this.dialogVisible = true;
      this.$nextTick(()=>{
          this.createPlyaer()
      })
    },
    createPlyaer () {
      console.log('player is createing')
      this.player = new Player({
        id: 'video-box',
        url: this.dialogVideoUrl,
        controlPlugins: [
          volume,
          playbackRate
        ],
        lang: 'zh-cn',
        videoInit: true,
        playbackRate: [0.5, 0.75, 1, 1.5, 2] //传入倍速可选数组
      });
    },

    opened () {

    },
    close() {
      console.log(this.player)
      console.log('player is destroying')
      this.player.destroy()
      this.dialogVisible = false
    }
  }
};
</script>
<style scoped lang="scss">
.video-thum {
  display: flex;
  margin-right: 20px;
}
.video-preview {
  display: flex;
  justify-content: center;
  min-height: 300px;
}
</style>

