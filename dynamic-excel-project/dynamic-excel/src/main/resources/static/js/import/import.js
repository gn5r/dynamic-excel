$(function () {
  $("#importBtn").on("click", function () {
    console.debug("取込ボタン押下");
    $("input[type='file'][id='file']").click();
  });

  $("#file").on("change", function () {
    const file = $(this).prop("files")[0];
    console.debug(file);

    const data = new FormData();
    data.append("file", file);
    data.append(
      "formData",
      new Blob([JSON.stringify({ message: "こんにちは" })], {
        type: "application/json",
      })
    );

    const ajax = {
      url: "api/excel/import",
      type: "POST",
      dataType: "json",
      data: data,
      contentType: false,
      processData: false,
    };

    if (confirm("Excel取り込みをしますか？")) {
      $.ajax(ajax)
        .done((result) => {
          console.debug(result);
        })
        .fail((err) => {
          console.error(err);
        });
    }
  });

  $("#post").on("click", function () {
    const data = new FormData();
    data.append(
      "formData",
      new Blob([JSON.stringify({ message: "こんにちは" })], {
        type: "application/json",
      })
    );

    const ajax = {
      url: "api/excel/import",
      type: "POST",
      dataType: "json",
      data: data,
      contentType: false,
      processData: false,
    };

    $.ajax(ajax)
      .done((result) => {
        console.debug(result);
      })
      .fail((err) => {
        console.error(err);
      });
  });
});
