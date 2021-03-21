let jqueryUtil = {};

/**
 * 入力値を取得し、{DOMのid: value} の形で返却する
 *
 * @param {String} parent 親DOMを取得する条件
 * @param {String} regex 取得したいDOMの条件
 * @returns {Object} マップ
 */
jqueryUtil.toMap = function (parent = null, regex) {
  let map = {};
  if (parent === null) {
    const dom = $(regex);
    map[dom.attr("id")] = dom.val();
  } else {
    $(parent)
      .find($(regex))
      .each(function () {
        map[$(this).attr("id")] = $(this).val();
      });
  }
  return map;
};

/**
 * テーブルのアイテムを1行あたり{DOMのID: value} 形式にしたリストを返却する
 *
 * @param {String} regex 取得したいDOMの条件
 * @returns {Array} リスト
 */
jqueryUtil.getTableItems = function (regex) {
  let list = [];

  // テーブルの全行を取得
  const tr = $(`${regex} tr`);

  for (let i = 0; i < tr.length; i++) {
    // 1行毎のマップデータ
    let map = {};

    // 1行目から順にth, tdを取得する
    const cols = tr.eq(i).children();
    for (let j = 0; j < cols.length; j++) {
      // tdか判別する
      if (cols.eq(j).is("td")) {
        // tdのidとテキストを取得する
        const td = cols.eq(j);
        // 操作ボタン以外のテキストをセットする
        if(td.attr("id") !== "row-action") {
          map[td.attr("id")] = $(td).text();
        }
      }
    }
    list.push(map);
  }

  return list;
};

/**
 * 指定したDOMの値を空にする
 *
 * @param {String} parent 親DOMを取得する条件
 * @param {String} regex 取得したいDOMの条件
 */
jqueryUtil.clear = function (parent = null, regex) {
  if (parent === null) {
    $(regex).val("");
  } else {
    $(parent)
      .find($(regex))
      .each(function () {
        $(this).val("");
      });
  }
};

/**
 * 指定したDOMを削除する
 *
 * @param {String} parent
 * @param {String} regex
 */
jqueryUtil.remove = function (parent = null, regex) {
  if (parent === null) {
    $(regex).remove();
  } else {
    $(parent)
      .find($(regex))
      .each(function () {
        $(this).remove();
      });
  }
};

/**
 * テーブルに1行データを追加する
 *
 * @param {String} parent 親DOMの検索条件
 * @param {String} regex テーブルの検索条件
 * @param {Object} row 1行データ
 */
jqueryUtil.rowPush = function (parent = null, regex, row = {}) {
  let tr = "<tr>";
  let trEnd = "</tr>";

  if (parent === null) {
    Object.keys(row).forEach((key) => {
      tr += `<td id='${key}'>` + row[key] + "</td>";
    });
    tr += trEnd;
    $(regex).append(tr);
  } else {
    $(parent)
      .find($(regex))
      .each(function () {
        Object.keys(row).forEach((key) => {
          tr += "<td>" + row[key] + "</td>";
        });
        tr += trEnd;
        $(this).append(tr);
      });
  }
};

/**
 * 行を作成する
 *
 * @param {String} parent
 * @param {STring} regex
 */
jqueryUtil.createRow = function (parent = null, regex) {
  const tr = "<tr></tr>";
  if (parent === null) {
    $(regex).append(tr);
  } else {
    $(parent)
      .find($(regex))
      .each(function () {
        $(this).append(tr);
      });
  }
};

/**
 * カラムを追加する
 *
 * @param {String} parent 親DOMの検索条件
 * @param {String} regex DOMの検索条件
 * @param {Object} col カラムデータ
 * @param {Boolean} rowStart 行開始フラグ。trueの場合はtrが付与される
 * @param {Boolean} rowEnd 行終了フラグ。trueの場合はtrが付与される
 */
jqueryUtil.colPuth = function (parent = null, regex, col, id = null) {
  if (parent === null) {
    let td = `<td id='${id}'>` + col + "</td>";
    $(regex + " tr:last").append(td);
  } else {
    $(parent)
      .find($(regex))
      .each(function () {
        let td = "<td>" + col + "</td>";
        $(regex).append(tr);
        $(this + " tr:last").append(td);
      });
  }
};

/**
 * 動的に追加したテーブルアイテムのボタンのクリックイベントを削除する
 *
 * @param {String} regex DOM検索条件
 */
jqueryUtil.deleteBtnClickEvent = function (regex) {
  $("body").off("click", regex);
};

export default jqueryUtil;
