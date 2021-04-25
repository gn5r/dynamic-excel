// axiosモジュールを読み込む
const axiosBase = require("axios");

// 出力オブジェクト
let rest = {};

// axiosBaseからHttp通信に必要な情報を設定し、作成したインスタンスを保存する
const axios = axiosBase.create({
  baseURL: process.env.VUE_APP_API_BASE_URL, //通信先URLに環境変数で設定したbaseUrlを設定
  proxy: false, // プロキシはoff
  responseType: "json", // レスポンスタイプ(Http通信時の応答時のデータ型をjsonに設定)
  timeout: 30 * 1000, // タイムアウト(応答までの時間)を30秒に設定
  // ヘッダーの設定
  headers: {
    "Access-Control-Allow-Origin": "*", // CROSをすべて許可する
  },
});

/**
 * GETリクエスト
 * 引数にリクエスト先のパスを渡すこと
 * @param {String} url リクエスト先URL
 * @param {Object} option axiosのオプション(デフォルトはnull)
 */
rest.get = (url, option = null) => {
  return new Promise((resolve, reject) => {
    axios
      .get(url, option)
      .then((response) => {
        resolve(response);
      })
      .catch((error) => {
        reject(error);
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
  return new Promise((resolve, reject) => {
    axios
      .post(url, data, option)
      .then((response) => {
        resolve(response);
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
  return new Promise((resolve, reject) => {
    axios
      .put(url, data, option)
      .then((response) => {
        resolve(response);
      })
      .catch((error) => {
        reject(error);
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
  return new Promise((resolve, reject) => {
    axios
      .delete(url, option)
      .then((response) => {
        resolve(response);
      })
      .catch((error) => {
        reject(error);
      });
  });
};

module.exports = rest;
