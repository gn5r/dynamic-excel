import axios from "@/utils/api/axiosBase";

let rest = {};

/**
 * axiosを使用してGETリクエストを行う
 *
 * @param {String} uri リクエスト先URL
 * @param {Object} option axiosオプション
 */
export async function get(uri, option = null) {
  let result = {
    headers: undefined,
    data: undefined,
    status: false,
    code: undefined,
    message: undefined,
  };

  result = await axios
    .get(uri, option)
    .then((res) => convertResponse(res))
    .catch((err) => {
      throw convertErrorResponse(err);
    });

  return result;
}

/**
 * axiosを使用してPOSTリクエストを行う
 *
 * @param {String} uri リクエスト先URL
 * @param {Object} data リクエストボディ
 * @param {Object} option axiosオプション
 */
export async function post(uri, data, option = null) {
  let result = {
    headers: null,
    data: null,
    status: false,
    code: null,
  };

  result = await axios
    .post(uri, data, option)
    .then((res) => convertResponse(res))
    .catch((err) => {
      throw convertErrorResponse(err);
    });

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
  if (error.toJSON()) {
    console.error(error.toJSON());
    const json = error.toJSON();
    return {
      headers: null,
      data: undefined,
      status: false,
      code: getCode(json.code),
      message: json.message,
    };
  } else if (error.response) {
    console.error(error.response);
  } else if (error.request) {
    console.error(error.request);
  } else {
    console.error(error.message);
  }
}

/**
 * ステータスコードを取得する。
 * 引数に与えたステータスコードに値があればそのまま返却し、無ければ400を返却する
 *
 * @param {Number | String} code HTTPステータスコード
 * @returns {Number | String} HTTPステータスコード
 */
function getCode(code) {
  if (code !== undefined && code !== null) {
    return code;
  } else {
    // codeが無ければ 400 Bad Request 扱いとする
    return 400;
  }
}

export default rest;
