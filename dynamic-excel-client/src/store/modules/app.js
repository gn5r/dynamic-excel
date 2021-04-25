/**
 * アプリケーションstore module
 */
export const app = {
  namespaced: true,
  state: {
    // 開発モード
    devMode: false,
    // ローディング
    loading: false,
    // オフラインモード
    offlineMode: false,
  },
  mutations: {
    // 開発モードフラグをセットする
    SET_DEV_MODE(state, payload) {
      state.devMode = payload;
    },
    // ローディングフラグをセットする
    SET_LOADING(state, payload) {
      state.loading = payload;
    },
    // オフラインモードフラグをセットする
    SET_OFFLINE_MODE(state, payload) {
      state.offlineMode = payload;
    },
  },
  getters: {
    // オフラインモードフラグを取得する
    GET_OFFLINE_MODE(state) {
      return state.offlineMode;
    }
  },
  actions: {
    /**
     * 開発モードのフラグをセットする
     *
     * @param {Object} context コンテキスト
     * @param {Boolean} payload 開発モードフラグ
     */
    setDevMode(context, payload) {
      context.commit("SET_DEV_MODE", payload);
    },

    /**
     * ローディングフラグをセットする
     *
     * @param {Object} context コンテキスト
     * @param {Boolean} payload ローディングフラグ
     */
    setLoading(context, payload) {
      context.commit("SET_LOADING", payload);
    },

    /**
     * オフラインモードのフラグをセットする
     *
     * @param {Object} context コンテキスト
     * @param {Boolean} payload オフラインモードフラグ
     */
    setOfflineMode(context = {}, payload = false) {
      context.commit("SET_OFFLINE_MODE", payload);
    },
  },
};
