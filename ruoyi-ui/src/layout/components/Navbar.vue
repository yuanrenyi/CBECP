<template>
  <div class="navbar">
    <div>
      <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />
      <breadcrumb id="breadcrumb-container" class="breadcrumb-container" v-if="!topNav"/>
      <top-nav id="topmenu-container" class="topmenu-container" v-if="topNav"/>
    </div>


    <div class="right-menu">
      <template v-if="device!=='mobile'">

        <screenfull id="screenfull" class="right-menu-item hover-effect" />

        <el-tooltip content="布局大小" effect="dark" placement="bottom">
          <size-select id="size-select" class="right-menu-item hover-effect" />
        </el-tooltip>

      </template>

      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar" class="user-avatar">
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/user/profile">
            <el-dropdown-item>个人中心</el-dropdown-item>
          </router-link>
          <el-dropdown-item @click.native="setting = true">
            <span>布局设置</span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span>退出登录</span>
          </el-dropdown-item>
          <el-dropdown-item v-has-role="['admin']" divided @click.native="uploadNew">
            <span>上传更新文件</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <input ref="iputRef" @change="changeFile" type="file" accept="application/zip" style="display: none">
      <div class="user-info">
        <div class="user-name"> {{ user.name }}</div>
        <div class="user-type">
          <el-tag size="mini"> {{ userTypeEnum[user.companyType] }}</el-tag>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import TopNav from '@/components/TopNav'
import Hamburger from '@/components/Hamburger'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import Search from '@/components/HeaderSearch'
import {uploadFile} from "@/utils/request";

export default {
  components: {
    Breadcrumb,
    TopNav,
    Hamburger,
    Screenfull,
    SizeSelect,
    Search,
  },
  data () {
    return {
      userTypeEnum:
        {
          1 : '平台',
          3: '供应商',
          2: '销售方'
        }
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device',
      'user'
    ]),
    setting: {
      get() {
        return this.$store.state.settings.showSettings
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'showSettings',
          value: val
        })
      }
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav
      }
    }
  },
  methods: {
    changeFile (e) {
      let file = e.target.files[0]
      if (!/\.(zip)$/.test(event.target.value)) {
        return  this.$notification.warn({message: "文件格式不正确请上传zip文件",duration: 5* 1000});
      }
      this.uploadZipFile(file)
      e.target.value = '';
    },
    uploadZipFile (file) {
      let url = '/version/update'
      uploadFile(url,'dist', file,{
        timeout: 30 * 1000,
        headers: {
          isToken: false
        },
      }).then(res=>{
        console.log(res)
      })
    },
    uploadNew() {
      this.$refs.iputRef.click()
    },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      this.$confirm('确定注销并退出系统吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('LogOut').then(() => {
          location.href = '/index';
        })
      }).catch(() => {});
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-right: 15px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .topmenu-container {
    position: absolute;
    left: 50px;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    height: 100%;
    display: flex;
    align-items: center;
    &:focus {
      outline: none;
    }

    .right-menu-item {
      padding: 0 8px;
      height: 100%;
      line-height: 50px;
      font-size: 18px;
      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
.user-info {
  display: flex;
  overflow: hidden;
  height: 50px;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  .user-name,.user-type {
    font-size: 12px;
  }
}
</style>
