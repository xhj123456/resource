$(function () {
    //初始加载
    $(".wz").css("border-bottom", "3px solid red");
    $(".xq").css("border", "none");
    $(".rig2").hide();
    $(".xc").css("border", "none");
    $(".rig3").hide();
    $(".s1").val("");
    $(".s2").val("");
    $(".s3").val("");
})
$(".xq").click(function () {
    $(this).css("border-bottom", "3px solid red");
    $(".xc").css("border", "none");
    $(".wz").css("border", "none");
    $(".rig1").hide();
    $(".rig2").show();
    $(".rig3").hide();
});
$(".xc").click(function () {
    $(this).css("border-bottom", "3px solid red");
    $(".wz").css("border", "none");
    $(".xq").css("border", "none");
    $(".rig1").hide();
    $(".rig2").hide();
    $(".rig3").show();
});
$(".wz").click(function () {
    $(this).css("border-bottom", "3px solid red");
    $(".xq").css("border", "none");
    $(".xc").css("border", "none");
    $(".rig1").show();
    $(".rig2").hide();
    $(".rig3").hide();
});

$(".tc").click(function () {
    $.ajax({
        type: "post",
        url: "aaa",
        dataType: "text",
        success: function (data) {
            location.href = "dlzc.html";
        }
    });
})
//发表心情
$(".sub2").click(function () {
    var mood = $(".mood").val();
    $.ajax({
        type: "post",
        url: "sub2",
        data: {"mood": mood},
        dataType: "json",
        success: function (data) {
            if (data != 0) {
                $(".mood").val("");
                $(".s2").text("发表成功");
            } else {
                location.href = "error.html";
            }
        }
    });
})