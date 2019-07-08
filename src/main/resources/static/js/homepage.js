$(function () {
    $.ajax({
        type: "post",
        url: "homepage",
        dataType: "json",
        success: function (data) {
            console.info(data);
        }
    })
})