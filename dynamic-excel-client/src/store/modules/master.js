import { get } from "@gn5r/vue-axios";

/**
 * マスタデータstore module
 */
export const master = {
  namespaced: true,
  state: {
    // 目名一覧
    orderList: [],
    // 科名一覧
    familyList: [],
    // 属名一覧
    genusList: [],
    // 果物一覧Excelテンプレート
    fruitsTemplates: [],
  },
  mutations: {
    // 目名一覧をセットする
    SET_ORDER_LIST(state, payload) {
      state.orderList = payload;
    },
    // 科名一覧をセットする
    SET_FAMILY_LIST(state, payload) {
      state.familyList = payload;
    },
    // 属名一覧をセットする
    SET_GENUS_LIST(state, payload) {
      state.genusList = payload;
    },
    // 果物一覧テンプレートをセットする
    SET_FRUITS_TEMPLATES(state, payload) {
      state.fruitsTemplates = payload;
    },
  },
  actions: {
    /**
     * 目名一覧を取得する
     *
     * @param {Object} context コンテキスト
     */
    getOrderList(context) {
      const uri = "/cmnMst/get/orderList";
      get(uri)
        .then((res) => {
          console.debug("目名取得結果", res);
          context.commit("SET_ORDER_LIST", res.data);
        })
        .catch((err) => {
          console.error("目名取得結果", err);
          context.commit("SET_ORDER_LIST", []);
        });
    },
    /**
     * 科名一覧を取得する
     *
     * @param {Object} context コンテキスト
     */
    getFamilyList(context) {
      const uri = "/cmnMst/get/familyList";
      get(uri)
        .then((res) => {
          context.commit("SET_FAMILY_LIST", res.data);
        })
        .catch((err) => {
          console.error(err);
          context.commit("SET_FAMILY_LIST", []);
        });
    },
    /**
     * 属名一覧を取得する
     *
     * @param {Object} context コンテキスト
     */
    getGenusList(context) {
      const uri = "/cmnMst/get/genusList";
      get(uri)
        .then((res) => {
          context.commit("SET_GENUS_LIST", res.data);
        })
        .catch((err) => {
          console.error(err);
          context.commit("SET_GENUS_LIST", []);
        });
    },

    /**
     * 果物一覧テンプレートを取得する
     *
     * @param {Object} context
     */
    getFruitsListTemplates(context) {
      const uri = "/excel/get/templateList";
      get(uri)
        .then((res) => {
          context.commit("SET_FRUITS_TEMPLATES", res.data);
        })
        .catch((err) => {
          console.error(err);
          context.commit("SET_FRUITS_TEMPLATES", []);
        });
    },
  },
};
