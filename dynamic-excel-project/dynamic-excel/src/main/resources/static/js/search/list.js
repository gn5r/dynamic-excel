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

  const editBtn =
    "<button class='btn btn-outline-success' id='item-edit' data-toggle='modal' data-target='#details-dialog'>編集</button>";
  const delBtn =
    "<button class='btn btn-outline-danger' id='item-delete'>削除</button>";
  jqueryUtil.colPuth(null, "#search-list-body", editBtn + delBtn, "row-action");
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

$(() => {
  $("#print-list").on("click", () => {
    const templateId = $("#print-template-id option:selected").val();
    if (templateId === "") {
      alert("テンプレートを選択して下さい");
      return;
    }

    if (confirm("Excel出力しますか？")) {
      console.debug("テンプレート選択:", templateId);
      // テーブルアイテムを取得
      let items = jqueryUtil.getTableItems("#search-list-body");
      console.debug("テーブルアイテム:", items);

      const data = {
        items: items,
        templateId: templateId,
      };

      $.ajax({
        type: "POST",
        url: "api/excel/list",
        dataType: "binary",
        responseType: "blob",
        contentType: "application/json",
        processData: false,
        data: JSON.stringify(data),
      })
        .done((res, status, xhr) => {
          console.debug(res, status, xhr);
          const blob = new Blob([res], { type: "application/octet-stream" });
          saveAs(blob, "一覧.xlsx");
        })
        .fail((xhr, status, error) => {
          console.error(error, status, xhr);
        });
    }
  });
});

export default List;
