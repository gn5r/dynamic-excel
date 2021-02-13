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
  },
  mutations: {
    // 開発モードをセットする
    SET_DEV_MODE(state, payload) {
      state.devMode = payload;
    },
    // ローディングをセットする
    SET_LOADING(state, payload) {
      state.loading = payload;
    },
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
  },
};
