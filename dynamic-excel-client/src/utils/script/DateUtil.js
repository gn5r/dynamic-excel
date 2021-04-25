const moment = require('moment');

let DateUtil = {};

/**
 * momentを利用した日付変換
 * 
 * 変換元の日付がブランクやNullである場合、ブランクを返却する
 * 
 * デフォルトは YYYY/MM/DD に変換する
 * @param {Date | String} source 変換元日付
 * @param {String} format  日付書式(詳しくはmomentのフォーマットを参照)
 * 
 * @return {String} 指定した書式に変換した日付
 */
DateUtil.format = (source = Date | String, format = "YYYY/MM/DD") => {
    if (!source) return "";
    return moment(new Date(source)).format(format);
}

/**
 * 今日の日付を取得する
 * 
 * フォーマットを指定することで任意の書式で取得することも可能
 * 
 * デフォルトは YYYY/MM/DD
 * @param {String} format 日付書式
 * 
 * @return {Date | String} 今日
 */
DateUtil.now = (format = "YYYY/MM/DD") => {
    return moment(new Date()).format(format);
}

module.exports = DateUtil;