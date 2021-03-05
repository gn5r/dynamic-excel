import jqueryUtil from "../common/jqueryUtil.js";
import List from "./list.js";

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

        Array.from(list).forEach((item) => {
          List.addItem(item);
        });

        // 動的に追加したボタンのクリックイベントは1回だけ追加する
        if (Array.from(list).length > 0) {
          List.addDelBtnClick("#item-delete");
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
