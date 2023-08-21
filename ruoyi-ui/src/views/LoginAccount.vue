<template>
    <div>
      <a-form-model ref="form" :model="loginForm" :rules="loginRules">
        <a-form-model-item required prop="username">
          <a-input v-model="loginForm.username" size="large" placeholder="请输入帐户名">
            <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }"/>
          </a-input>
        </a-form-model-item>
        <a-form-model-item required prop="password">
          <a-input v-model="loginForm.password" size="large" type="password" autocomplete="false" placeholder="请输入密码">
            <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
          </a-input>
        </a-form-model-item>

        <a-row :gutter="0" v-if="captchaEnabled">
          <a-col :span="16">
            <a-form-model-item required prop="code">
              <a-input v-model="loginForm.code" size="large" type="text" placeholder="请输入验证码">
                <a-icon slot="prefix" type="smile" :style="{ color: 'rgba(0,0,0,.25)' }"/>
              </a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" style="text-align: right">
            <img  style="margin:2px 0 0  5px;" width="115px" height="36px" :src="codeUrl" @click="getCode"/>
          </a-col>
        </a-row>
        <a-row style="margin-top:24px">
          <a-button size="large" type="primary" htmlType="submit" class="login-button" :loading="loginBtn"
                    @click.stop.prevent="handleLogin" :disabled="loginBtn">登 录
          </a-button>
        </a-row>
      </a-form-model>
    </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from '@/utils/jsencrypt'
import {timeFix} from "@/utils";

export default {
  name: "LoginAccount",
  data() {
    return {
      loginBtn: false,
      codeUrl: "",
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, message: "请输入您的账号" }
        ],
        password: [
          { required: true, message: "请输入您的密码" }
        ],
        code: [{ required: true, message: "请输入验证码" }]
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.validateFields([ 'username', 'password', 'code' ], (err)=>{
        if (!err) {
          this.loginBtn = true
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.loginBtn = false
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{});
            this.$notification.success({
              message: '欢迎',
              description: `${timeFix()}，欢迎回来`,
            });
          }).catch(() => {
            this.loginBtn = false
            if (this.captchaEnabled) {
              this.getCode();
            }
          });
        }
      });
    },
    validateFields(arr, callback){
      let promiseArray = []
      for(let item of arr){
        let p = new Promise((resolve, reject) => {
          this.$refs['form'].validateField(item, (err)=>{
            if(!err){
              resolve();
            }else{
              reject(err);
            }
          })
        });
        promiseArray.push(p)
      }
      Promise.all(promiseArray).then(()=>{
        callback()
      }).catch(err=>{
        callback(err)
      })
    },
  }
};
</script>

<style >
.login-button {
  padding: 0 15px;
  font-size: 16px;
  height: 40px;
  width: 100%;
}
</style>
