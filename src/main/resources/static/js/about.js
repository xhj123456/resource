$(function () {
    $(".bc").hide();
})
//点击修改显示bc div
$(".modify").click(function () {
    $(".xg").hide();
    $(".bc").show();
})
//点击保存
$(".Preservation").click(function () {
    var user = $(".user").text();
    var introduce = $(".introduce").val();
    var hobby = $(".hobby").val();
    var age = $(".age").val();
    var Occupation = $(".Occupation").val();
    var tel = $(".tel").val();
    var tel01 = /^1(3[0-9]|5[189]|8[6789])[0-9]{8}$/;
    if (!tel01.test(tel)) {
        $(".telcw").text("请输入正确号码");
    } else {
        $(".telcw").text("");
        $.ajax({
            type: "post",
            url: "update",
            data: {
                "user": user,
                "introduce": introduce,
                "hobby": hobby,
                "age": age,
                "Occupation": Occupation,
                "tel": tel
            },
            dataType: "json",
            success: function (data) {
                if (data != 0) {	//修改成功
                    location.href = "abouts";
                } else {	//未知错误
                    location.href = "error.html";
                }
            }
        });
    }
})
