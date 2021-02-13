let validate = {};

/**
 * バリデーションを取得する
 *
 * @param {String} target 取得したいバリデーション名
 */
validate.getValidate = function(target) {
  return availableValidate(target);
};

/**
 * 有効なターゲットであるかチェックする
 *
 * @param {String} target
 */
function availableValidate(target) {
  if (functions[target] !== undefined) {
    return functions[target];
  } else {
    console.error(`${target}は存在しません`);
  }
}

/**
 * バリデーション関数マップ
 */
const functions = {
  required: (v) => required(v),
  number: (v) => numeric(v),
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
  if (v.match(/[0-9]/g)) {
    return true;
  } else {
    return "数値のみ有効です";
  }
}

export default validate;
