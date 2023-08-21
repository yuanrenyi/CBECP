<template>
  <div class="container">
    <div class="main">
      <a-form-model class="user-layout-login" >
        <a-tabs :activeKey="customActiveKey" :tabBarStyle="{ textAlign: 'center', borderBottom: 'unset' }"  @change="handleTabClick">
          <a-tab-pane key="tab1" tab="账号密码登录">
            <login-account ref="alogin" ></login-account>
          </a-tab-pane>

<!--          <a-tab-pane key="tab2" tab="手机号登录" v-if="1==0">-->
<!--            <login-phone ref="plogin" @validateFail="validateFail" @success="requestSuccess" @fail="requestFailed"></login-phone>-->
<!--          </a-tab-pane>-->
        </a-tabs>
      </a-form-model>
      <!--    <login-select-tenant ref="loginSelect" @success="loginSelectOk"></login-select-tenant>-->
    </div>
  </div>
</template>

<script>

import LoginAccount from './LoginAccount'

export default {
  components: {
    LoginAccount,
  },
  data () {
    return {
      customActiveKey: 'tab1',
    }
  },
  created () {
    this.rememberMe = true
  },
  methods: {
    handleTabClick(key) {
      this.customActiveKey = key
    },

  }

}
</script>
<style lang="scss" scoped>
.container {
  width: 100%;
  min-height: 100%;
  background: #f0f2f5 url(~@/assets/ant_images/background.svg) no-repeat 50%;
  background-size: 100%;
  padding: 200px 0 0 0;
  box-sizing: border-box;
  .main {
    min-width: 260px;
    width: 368px;
    margin:  auto ;
  }
}
.user-layout-login {
  label {
    font-size: 14px;
  }

  .getCaptcha {
    display: block;
    width: 100%;
    height: 40px;
  }

  .forge-password {
    font-size: 14px;
  }

  button.login-button {
    padding: 0 15px;
    font-size: 16px;
    height: 40px;
    width: 100%;
  }

  .user-login-other {
    text-align: left;
    margin-top: 24px;
    line-height: 22px;

    .item-icon {
      font-size: 24px;
      color: rgba(0, 0, 0, .2);
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color .3s;

      &:hover {
        color: #1890ff;
      }
    }

    .register {
      float: right;
    }
  }
}
</style>
<style>
.valid-error .ant-select-selection__placeholder {
  color: #f5222d;
}
</style>
