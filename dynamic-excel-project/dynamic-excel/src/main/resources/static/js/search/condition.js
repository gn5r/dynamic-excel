import jqueryUtil from "../common/jqueryUtil.js";

// 一覧検索条件のjs
$(function () {
  // 検索ボタンクリックイベント
  $("#search").click(function () {
    // input要素を取得
    const input = jqueryUtil.toMap("#condition-form", "input[id^=condition-]");

    // select要素を取得
    const select = jqueryUtil.toMap(
      "#condition-form",
      "select[id^=condition-]"
    );

    const data = {
      id: input["condition-id"],
      kanjiName: input["condition-kanji-name"],
      name: input["condition-name"],
      order: select["condition-order"],
      family: select["condition-family"],
      genus: select["condition-genus"],
    };

    // 検索
    $.ajax({
      type: "POST",
      url: "api/search",
      contentType: "application/json",
      dataType: "json",
      data: JSON.stringify(data),
    })
      .done(function (result, status, xhr) {
        console.debug(result, status, xhr);

        const list = result.result;
        console.debug(list);

        // 条件のアコーディオンを非activeにする
        $("#condition-content").removeClass("show");

        // 一度全ての行を削除する
        jqueryUtil.remove(null, "#search-list-body tr");

        // ボタンクリックイベントを削除する
        jqueryUtil.deleteBtnClickEvent("#item-delete");

        Array.from(list).forEach((item) => {
          // jqueryUtil.rowPush(null, "#search-list-body", item);
          jqueryUtil.createRow(null, "#search-list-body");
          jqueryUtil.colPuth(null, "#search-list-body", item["id"]);
          jqueryUtil.colPuth(null, "#search-list-body", item["kanjiName"]);
          jqueryUtil.colPuth(null, "#search-list-body", item["name"]);
          jqueryUtil.colPuth(null, "#search-list-body", item["order"]);
          jqueryUtil.colPuth(null, "#search-list-body", item["family"]);
          jqueryUtil.colPuth(null, "#search-list-body", item["genus"]);

          const delBtn = "<button class='btn btn-outline-danger' id='item-delete'>削除</button>";
          jqueryUtil.colPuth(null, "#search-list-body", delBtn);
        });
        
        // 動的に追加したボタンのクリックイベントは1回だけ追加する
        if(Array.from(list).length > 0) {
          jqueryUtil.addBtnClickFunc("#item-delete");
        }

        $("#list-content").addClass("show");
      })
      .fail(function (xhr, status, error) {
        console.error("error");
        console.error(error, status, xhr);
      });
  });

  $("#condition-clear").click(function () {
    // input要素の値をクリア
    jqueryUtil.clear("#condition-form", "input[id^=condition-]");

    // select要素の値をクリア
    jqueryUtil.clear("#condition-form", "select[id^=condition-]");
  });
});
