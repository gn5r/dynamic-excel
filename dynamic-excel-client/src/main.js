import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store/index";
import vuetify from "./plugins/vuetify";
import "@fortawesome/fontawesome-free/css/all.css";
import "@/assets/css/dynamic-excel.scss"
import "vue-loading-overlay/dist/vue-loading.css";
import Confirm from "@gn5r/vue-common-confirm";
import Loading from "vue-loading-overlay"

Vue.config.productionTip = false;
Vue.use(Confirm);
Vue.component("loading", Loading);

new Vue({
  vuetify,
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
