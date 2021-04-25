const axios = require("@/utils/api/axiosBase");
const store = require("@/store/index");

let rest = {};

/**
 * axiosを使用してGETリクエストを行う
 *
 * @param {String} uri リクエスト先URL
 * @param {Object} option axiosオプション
 */
rest.get = async (uri, option = null) => {
  let result = {
    /** レスポンスヘッダー */
    headers: null,
    /** レスポンスデータ */
    data: null,
    /** Http Statusフラグ */
    status: false,
    /** Http Status Code */
    code: null,
    /** レスポンスメッセージ */
    message: null,
  };

  if (!getOfflineMode()) {
    result = await axios
      .get(uri, option)
      .then((res) => convertResponse(res))
      .catch((err) => {
        throw convertErrorResponse(err)
      });
  }

  return result;
}

/**
 * axiosを使用してPOSTリクエストを行う
 *
 * @param {String} uri リクエスト先URL
 * @param {Object} data リクエストボディ
 * @param {Object} option axiosオプション
 */
rest.post = async (uri, data, option = null) => {
  let result = {
    /** レスポンスヘッダー */
    headers: null,
    /** レスポンスデータ */
    data: null,
    /** Http Statusフラグ */
    status: false,
    /** Http Status Code */
    code: null,
    /** レスポンスメッセージ */
    message: null,
  };

  result = await axios
    .post(uri, data, option)
    .then((res) => convertResponse(res))
    .catch((err) => convertErrorResponse(err));

  return result;
}

/**
 * axiosを使用してPUTリクエストを行う
 *
 * @param {String} uri リクエスト先URL
 * @param {Object} data リクエストボディ
 * @param {Object} option axiosオプション
 */
rest.put = async (uri, data, option = null) => {
  let result = {
    /** レスポンスヘッダー */
    headers: null,
    /** レスポンスデータ */
    data: null,
    /** Http Statusフラグ */
    status: false,
    /** Http Status Code */
    code: null,
    /** レスポンスメッセージ */
    message: null,
  };

  if (!getOfflineMode()) {
    result = await axios
      .put(uri, data, option)
      .then((res) => convertResponse(res))
      .catch((err) => {
        throw convertErrorResponse(err);
      });
  }

  return result;
}

/**
 * axiosを使用してDELETEリクエストを行う
 *
 * @param {String} uri リクエスト先URL
 * @param {Object} option axiosオプション
 */
rest.del = async (uri, option = null) => {
  let result = {
    /** レスポンスヘッダー */
    headers: null,
    /** レスポンスデータ */
    data: null,
    /** Http Statusフラグ */
    status: false,
    /** Http Status Code */
    code: null,
    /** レスポンスメッセージ */
    message: null,
  };

  if (!getOfflineMode()) {
    result = await axios
      .del(uri, option)
      .then((res) => convertResponse(res))
      .catch((err) => {
        throw convertErrorResponse(err);
      });
  }

  return result;
}

/**
 * axiosのレスポンスを変換する
 *
 * @param {Object} response レスポンス
 */
function convertResponse(response) {
  console.debug("REQUEST RESULT:", response);
  return {
    headers: response.headers,
    data: response.data,
    status: true,
    code: response.status,
    message: response.statusText,
  };
}

/**
 * axiosのエラーレスポンスを変換する
 *
 * @param {Object} error エラーレスポンス
 */
function convertErrorResponse(error) {
  // オフラインモードフラグをtrueにする
  setOfflineMode();

  if (error.response) {
    console.error(error.response);
    const res = error.response;
    return {
      headers: res.headers,
      data: res.data,
      status: false,
      code: getCode(res.status),
      message: res.data.message
    }
  } else {
    return {
      headers: null,
      data: {},
      status: false,
      code: 500,
      message: "サーバー内エラーが発生しました"
    }
  }
}

/**
 * ステータスコードを取得する。
 * 引数に与えたステータスコードに値があればそのまま返却し、無ければ400を返却する
 *
 * @param {Number | String} code HTTPステータスコード
 * @returns {Number | String} HTTPステータスコード
 */
function getCode(code = null) {
  if (code !== undefined && code !== null) {
    return code;
  } else {
    // codeが無ければ 400 Bad Request 扱いとする
    return 400;
  }
}

/**
 * オフラインモードフラグを取得する
 *
 * @returns オフラインモードフラグ
 */
function getOfflineMode() {
  return store.default.getters["app/GET_OFFLINE_MODE"];
}

/**
 * オフラインモードでなければフラグをtrueにする
 */
function setOfflineMode() {
  if (!getOfflineMode()) {
    store.default.dispatch("app/setOfflineMode", true);
  }
}

module.exports = rest;
