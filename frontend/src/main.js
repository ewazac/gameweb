import Vue from 'vue'
window.$ = window.jQuery = require('jquery');
import App from './App.vue'
import router from './router'
import store from './store'
import VeeValidate from 'vee-validate';
import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import './assets/app.scss'
require('./registerComponents');
require('./filters');
Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)
Vue.use(VeeValidate, { fieldsBagName: 'veeFields' });
import Confirm from './services/confirm';
Vue.use(Confirm);
Vue.use(require('vue-moment'));
Vue.config.productionTip = false


new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
