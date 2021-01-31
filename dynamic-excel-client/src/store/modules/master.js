import rest from "@/utils/api/rest";

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
  },
  actions: {
    /**
     * 目名一覧を取得する
     *
     * @param {Object} context コンテキスト
     */
    getOrderList(context) {
      const uri = "/cmnMst/get/orderList";
      rest
        .get(uri)
        .then((res) => {
          context.commit("SET_ORDER_LIST", res.data);
        })
        .catch((err) => {
          console.error(err);
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
      rest
        .get(uri)
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
      rest
        .get(uri)
        .then((res) => {
          context.commit("SET_GENUS_LIST", res.data);
        })
        .catch((err) => {
          console.error(err);
          context.commit("SET_GENUS_LIST", []);
        });
    },
  },
};
