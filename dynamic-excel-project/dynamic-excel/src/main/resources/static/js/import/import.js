$(function () {
  $("#importBtn").on("click", function () {
    console.debug("取込ボタン押下");
    $("input[type='file'][id='file']").click();
  });

  $("#file").on("change", function () {
    const files = $(this).prop("files");
    console.debug(files);

    const data = new FormData();
    Array.from(files).forEach((file, idx) => {
      console.debug(file);
      data.append("files", files[idx]);
    });

    const obj = {
      message: "hello world"
    }

    data.append("formData", JSON.stringify(obj));

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

    $(this).val("");
  });

  $("#post").on("click", function () {
    const data = new FormData();
    data.append("message", "hello");

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
