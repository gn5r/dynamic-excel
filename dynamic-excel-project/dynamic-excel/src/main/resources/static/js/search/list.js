import jqueryUtil from "../common/jqueryUtil.js";
let List = {};

/**
 * テーブルにアイテムを追加する
 *
 * @param {Object} item 検索結果の1行データ
 */
List.addItem = function (item = {}) {
  jqueryUtil.createRow(null, "#search-list-body");
  jqueryUtil.colPuth(null, "#search-list-body", item["id"], "row-id");
  jqueryUtil.colPuth(
    null,
    "#search-list-body",
    item["kanjiName"],
    "row-kanjiName"
  );
  jqueryUtil.colPuth(null, "#search-list-body", item["name"], "row-name");
  jqueryUtil.colPuth(null, "#search-list-body", item["order"], "row-order");
  jqueryUtil.colPuth(null, "#search-list-body", item["family"], "row-family");
  jqueryUtil.colPuth(null, "#search-list-body", item["genus"], "row-genus");

  const editBtn = "<button class='btn btn-outline-success' id='item-edit' data-toggle='modal' data-target='#details-dialog'>編集</button>";
  const delBtn = "<button class='btn btn-outline-danger' id='item-delete'>削除</button>";
  jqueryUtil.colPuth(null, "#search-list-body", editBtn + delBtn);
};

/**
 * 動的に追加したテーブルアイテムの削除ボタンにクリックイベントを付与する
 *
 * @param {String} regex DOM検索条件
 */
List.addDelBtnClick = function (regex) {
  $("body").on("click", regex, function () {
    const tr = $(this).parent().parent();
    const id = $(tr).children("#row-id").text();
    console.debug("id:", id);

    // if (alert("削除しますか？")) {
    //   // 論理削除処理を呼ぶ
    // $(tr).remove();
    // }
  });
};

/**
 * 動的に追加したテーブルアイテムの削除ボタンにクリックイベントを付与する
 *
 * @param {String} regex DOM検索条件
 */
List.addEditBtnClick = function (regex) {
  $("body").on("click", regex, function () {
    const tr = $(this).parent().parent();
    const id = $(tr).children("#row-id").text();
    console.debug("id:", id, "index:", idx);

    // if (alert("削除しますか？")) {
    //   // 論理削除処理を呼ぶ
    // $(tr).remove();
    // }
  });
};

export default List;
