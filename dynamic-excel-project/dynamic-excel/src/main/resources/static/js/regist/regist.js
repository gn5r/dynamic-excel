import jqueryUtil from "../common/jqueryUtil.js";

// データ登録のjs
$(function () {
  $("button[id=regist]").click(function () {
    if (confirm("登録しますか?")) {
      const form = jqueryUtil.toMap("#regist-form", "input[id^=form-]");
      console.debug("form", form);
      const data = {
        kanjiName: form["form-kanji-name"],
        name: form["form-name"],
        order: form["form-order"],
        family: form["form-family"],
        genus: form["form-genus"],
      };
      // ajax
      $.ajax({
        type: "POST",
        url: "api/regist",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(data),
      })
        .done(function (result, status, xhr) {
          console.debug("done");
          console.debug(result, status, xhr);
          alert(result.message);
          // windowをリロード
          window.location.reload();
        })
        .fail(function (xhr, status, error) {
          console.error("error");
          console.error(error, status, xhr);
          alert("エラーが発生しました。コンソールを確認してください");
        });
    }
  });
  $("#regist-form-clear").click(function () {
    jqueryUtil.clear("#regist-form", "input[id^=form-]");
  });
});
