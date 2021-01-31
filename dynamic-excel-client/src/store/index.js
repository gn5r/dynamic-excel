import Vue from "vue";
import Vuex from "vuex";
import { master } from "@/store/modules/master";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  mutations: {},
  actions: {},
  modules: {
    master,
  },
});
