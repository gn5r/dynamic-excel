// axiosモジュールを読み込む
const axiosBase = require("axios");

// 出力オブジェクト
let rest = {};

/**
 * GETリクエスト
 * 引数にリクエスト先のパスを渡すこと
 * @param {String} url リクエスト先URL
 * @param {Object} option axiosのオプション(デフォルトはnull)
 */
rest.get = (url, option = null) => {
  // axiosBaseからHttp通信に必要な情報を設定し、作成したインスタンスを保存する
  const axios = axiosBase.create({
    baseURL: process.env.VUE_APP_API_BASE_URL, //通信先URLに環境変数で設定したbaseUrlを設定
    proxy: false, // プロキシはoff
    responseType: "json", // レスポンスタイプ(Http通信時の応答時のデータ型をjsonに設定)
    timeout: 15000, // タイムアウト(応答までの時間)を15秒に設定
    // ヘッダーの設定
    headers: {
      "Access-Control-Allow-Origin": "*", // CROSをすべて許可する
    },
  });
  return new Promise((resolve, reject) => {
    axios
      .get(url, option)
      .then((response) => {
        resolve(convertResponse(response));
      })
      .catch((error) => {
        reject(convertError(error));
      });
  });
};

/**
 * POSTリクエスト
 * 引数にリクエスト先のパスとリクエストデータを渡すこと
 * @param {String} url リクエスト先URL
 * @param {Object} data リクエストボディ(json形式)
 * @param {Object} option axiosのオプション(デフォルトはnull)
 */
rest.post = (url, data, option = null) => {
  // axiosBaseからHttp通信に必要な情報を設定し、作成したインスタンスを保存する
  const axios = axiosBase.create({
    baseURL: process.env.VUE_APP_API_BASE_URL, //通信先URLに環境変数で設定したbaseUrlを設定
    proxy: false, // プロキシはoff
    responseType: "json", // レスポンスタイプ(Http通信時の応答時のデータ型をjsonに設定)
    timeout: 15000, // タイムアウト(応答までの時間)を15秒に設定
    // ヘッダーの設定
    headers: {
      "Access-Control-Allow-Origin": "*", // CROSをすべて許可する
    },
  });
  return new Promise((resolve, reject) => {
    axios
      .post(url, data, option)
      .then((response) => {
        resolve(convertResponse(response));
      })
      .catch((error) => {
        reject(error);
      });
  });
};

/**
 * PUTリクエスト
 * 引数にリクエスト先のパスとリクエストデータを渡すこと
 * @param {String} url リクエスト先URL
 * @param {Object} data リクエストボディ(json形式)
 * @param {Object} option axiosのオプション(デフォルトはnull)
 */
rest.put = (url, data, option = null) => {
  // axiosBaseからHttp通信に必要な情報を設定し、作成したインスタンスを保存する
  const axios = axiosBase.create({
    baseURL: process.env.VUE_APP_API_BASE_URL, //通信先URLに環境変数で設定したbaseUrlを設定
    proxy: false, // プロキシはoff
    responseType: "json", // レスポンスタイプ(Http通信時の応答時のデータ型をjsonに設定)
    timeout: 15000, // タイムアウト(応答までの時間)を15秒に設定
    // ヘッダーの設定
    headers: {
      "Access-Control-Allow-Origin": "*", // CROSをすべて許可する
    },
  });
  return new Promise((resolve, reject) => {
    axios
      .put(url, data, option)
      .then((response) => {
        resolve(convertResponse(response));
      })
      .catch((error) => {
        reject(convertError(error));
      });
  });
};

/**
 * DELETEリクエスト
 * 引数にリクエスト先のパスを渡すこと
 * @param {String} url リクエスト先URL
 * @param {Object} option axiosのオプション(デフォルトはnull)
 */
rest.del = (url, option = null) => {
  // axiosBaseからHttp通信に必要な情報を設定し、作成したインスタンスを保存する
  const axios = axiosBase.create({
    baseURL: process.env.VUE_APP_API_BASE_URL, //通信先URLに環境変数で設定したbaseUrlを設定
    proxy: false, // プロキシはoff
    responseType: "json", // レスポンスタイプ(Http通信時の応答時のデータ型をjsonに設定)
    timeout: 15000, // タイムアウト(応答までの時間)を15秒に設定
    // ヘッダーの設定
    headers: {
      "Access-Control-Allow-Origin": "*", // CROSをすべて許可する
    },
  });
  return new Promise((resolve, reject) => {
    axios
      .delete(url, option)
      .then((response) => {
        resolve(convertResponse(response));
      })
      .catch((error) => {
        reject(error);
      });
  });
};

/**
 * axiosのResponseオブジェを独自オブジェに変換し返却する
 *
 * @param {*} response axios Responseオブジェクト
 */
function convertResponse(response) {
  return {
    data:
      response.data.results === undefined
        ? response.data
        : response.data.results,
    status: true,
    code: response.status,
    headers: response.headers,
  };
}

/**
 * axiosのErrorオブジェを独自オブジェに変換し返却する
 *
 * @param {*} error axios Errorオブジェクト
 */
function convertError(error) {
  return {
    data: error.data,
    status: false,
    code: error.response.status,
    headers: error.response.headers,
  };
}

export default rest;
