<template>
  <div id="app">
    <router-view />
    <theme-picker />
    <div id="tooltip" class="el-tooltip__popper is-dark " role="tooltip">
      <span class="content"></span>
      <div id="arrow" data-popper-arrow></div>
    </div>
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker";

export default {
  name: "App",
  components: { ThemePicker },
    metaInfo() {
        return {
            title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
            titleTemplate: title => {
                return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
            }
        }
    }
};
</script>
<style scoped>
#app .theme-picker {
  display: none;
}

</style>
<style lang="scss">
.el-loading-mask {
  z-index: 999!important;
}
.ant-notification {
  z-index: 3000!important;
}
/*分割线样式*/
.divider-info {
  margin-top: 5px;
}
/*.el-form {
  .el-col {
    border-bottom: 1px solid #4AB7BD;
  }
}*/

/*表单样式*/
.el-form.query,.el-form.edit {
  .el-row {
    margin-left:0 !important;
    margin-right: 0!important;
    display: flex;
    flex-wrap: wrap;
    //overflow: hidden;
  }
  .el-form-item {
    display: flex;
    align-items: center;
    //margin-right: 15px !important;
    .el-form-item__label {
      overflow: hidden;
      //height: 36px;
      font-size: 13px;
      //text-align: center;
      //text-overflow: ellipsis;
      //white-space: nowrap;
      line-height: 1.3;
    }
    .el-form-item__content {
      flex: 1;
      margin-left: 0!important;
      line-height: 40px!important;
      //height: 40px;
      .el-select {
        width: 100%!important;
      }
      .el-cascader {
        width: 100%!important;
      }
      .el-input-number {
        width: 100%!important;
      }
      .el-date-editor {
        width: 100%!important;
      }
    }

  }
}
.el-form.query {
  margin-bottom: 10px;
  overflow: auto;
  padding: 5px 5px 0;
  //border: 1px solid #cccccc;
  //box-shadow: 0 0.5px 2px  rgba(0, 0, 0, 0.2);
  //            -0.5px 0px 2px  rgba(0, 0, 0, 0.2),
  //            0.5px 0px 2px rgba(0, 0, 0, 0.2) inset,
  //            0px 0.5px 2px  rgba(0, 0, 0, 0.2) inset;
  .el-form-item {
    margin-bottom: 10px;
  }
}
/* 表格表头 */
.el-table .table-header-cell {
  > .cell {
    overflow: hidden;
    height: 36px;
    line-height: 36px;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}
/*表格 fix列 多出一个 1px的 line*/
.el-table .el-table__fixed-right::before,.el-table .el-table__fixed::before {
  height: 0!important;
}


/* 元素过渡效果 */
.zoom-in-bottom {
  width: 100%;
}
.zoom-in-bottom-enter,
.zoom-in-bottom-leave-to {
  opacity: 0;
  transform: translateY(-100%);
}
.zoom-in-bottom-leave-active {
  width: 100%;
  position: absolute;

}
.transition-all {
  transition: max-height  400ms;
}

.g-mask-loading {
  z-index: 99999!important;
}
/* 修复 el-scrollbar 多出一条横线 */
.el-scrollbar__wrap {
  //overflow-x: hidden;
}

.el-scrollbar__bar {
  height: 4px;
}
/* v-showPoper 所需样式 */
#tooltip {
  display: none;
  //visibility: hidden;
}

#tooltip[data-show] {

  display: block;
  //visibility: visible;
}
#arrow,
#arrow::before {
  position: absolute;
  width: 8px;
  height: 8px;
  background: inherit;
}

#arrow {
  visibility: hidden;
}

#arrow::before {
  visibility: visible;
  content: '';
  transform: rotate(45deg);
}
#tooltip[data-popper-placement^='top'] > #arrow {
  bottom: -4px;
}

#tooltip[data-popper-placement^='bottom'] > #arrow {
  top: -4px;
}

#tooltip[data-popper-placement^='left'] > #arrow {
  right: -4px;
}

#tooltip[data-popper-placement^='right'] > #arrow {
  left: -4px;
}
.cascade-text {
  .el-input {
    input {
      border: none;
    }
    .el-input__suffix {
      display: none;
    }
  }
  .el-cascader__tags {
    .el-tag {
      .el-tag__close {
        display: none;
      }
    }
  }
}
.cascade-popper {
  input[type=checkbox] {
    pointer-events: none!important;
    cursor: not-allowed;
  }
  .el-checkbox {
    pointer-events: none!important;
    cursor: not-allowed;
  }
}
.el-table .el-table__row {
  .cell {
    input {
      background-color: transparent;
    }
  }
}
</style>
