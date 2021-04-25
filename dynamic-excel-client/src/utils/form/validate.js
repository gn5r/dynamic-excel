let validate = {};

/**
 * 有効なターゲットであるかチェックする
 *
 * @param {String} target バリデーション名
 */
validate.availableValidate = (target) => {
  return functions[target] !== undefined;
};

/**
 * バリデーションを取得する
 *
 * @param {String} target 取得したいバリデーション名
 */
validate.getValidate = (target) => {
  return functions[target];
};

/**
 * バリデーション関数マップ
 */
const functions = {
  required: (v) => required(v),
  number: (v) => numeric(v),
  path: (v) => pathString(v),
};

/**
 * 必須チェック
 *
 * @param {String|Number|Boolean} v チェック対象の値
 */
function required(v) {
  if (v !== null && v !== undefined && v !== "") {
    return true;
  } else {
    if (Number.isFinite(v)) {
      return true;
    }
    return "必須項目です";
  }
}

/**
 * 入力された値が数値であるかチェックする
 *
 * @param {String|Number} v チェック対象の値
 */
function numeric(v) {
  const regex = new RegExp(/[0-9]/, "g")
  if (regex.test(v)) {
    return true;
  } else {
    return "数値のみ有効です";
  }
}

/**
 * 入力された値が半角英数字と一部記号のみであるかチェックする
 *
 * @param {String} v チェック対象の値
 * @returns チェック結果
 */
function pathString(v) {
  const regex = new RegExp("[0-9a-zA-Z/_-]", "g");
  if (regex.test(v)) {
    return true;
  } else {
    return "半角英数字及び(/_-)のみ有効です";
  }
}

export default validate;
