//点击更换验证码
$(".yzm").click(function () {
    yzmxx();
})

function yzmxx() {
    var i = 0;
    var stri = "";
    while (i < 4) {
        x = Math.floor(Math.random() * 10).toString();
        stri = stri + x;
        i++;
        if (stri.length == 4) {
            $(".yzm1").text(stri);
        }
    }
}

//点击免费注册切换注册
$(".x1").click(function () {
    $(".dl").hide();
    $(".zc").show();
})
//点击马上登录切换登录
$(".x2").click(function () {
    $(".zc").hide();
    $(".dl").show();
})

//input 焦点丢失触发去空格
function removeSpacing(obj) {
    obj.value = obj.value.replace(/\s/gi, '');

}

//登录
//输入用户名后光标丢失马上判断是否存在该用户
$("#user").change(function () {
    removeSpacing(this); //去空格
    var user = $(this).val();
    var user1 = /^[\u4e00-\u9fa5]+$/;
    if (user == "" || !user1.test(user)) {
        $(".s1").text("用户名不能为空且必须为中文");
    } else {
        $(".s1").text("");
        $.ajax({
            type: "post",
            url: "yh01",
            data: {
                "user": user
            },
            dataType: "json",
            success: function (data) {
                if (!data) {
                    $(".s1").text("无此用户");
                } else {
                    $(".s1").text("");
                }
            }
        });
    }
})
$("#password").change(function () {
    var password = $(this).val();
    var password2 = /^[A-z0-9_-]{6,12}$/
    if (!password2.test(password)) {
        $(".s2").text("6-12位密码");
    } else {
        $(".s2").text("");
    }
})
//点击登录
// $("#form1").submit(function() {
$("#btn").click(function () {
    var user = $("#user").val();
    var password = $("#password").val();
    var yz = $("#yz").val();
    var yzm = $(".yzm1").text();
    if (yz != yzm) {
        $(".s3").text("验证码有误");
        yzmxx();
    } else {
        $(".s3").text("");
        var data1 = false;
        $.ajax({
            type: "post",
            url: "dl",
            async: false,
            data: {
                "user": user,
                "password": password
            },
            dataType: "json",
            success: function (data) {
                data1 = data;
                if (!data) {
                    $(".s2").text("密码错误");
                } else {
                    location.href = "index";
                }
            }
        });
        return data1;

    }
})

//注册
//输入用户名后光标丢失马上判断是否存在该用户
$(".user2").change(function () {
    var user1 = /^[\u4e00-\u9fa5]+$/;
    var user2 = $(this).val();
    if (!user1.test(user2)) {
        $(".yhcw").text("用户名不能为空且必须为中文");
    } else {
        $(".yhcw").text("");
        $.ajax({
            type: "post",
            url: "yh02",
            data: {
                "user": user2
            },
            dataType: "json",
            success: function (data) {
                console.log(data)
                if (data) {
                    $(".yhcw").text("该用户已存在");
                }
            }
        });
    }
})
//输入密码后判断是否为6-12位
$("#pwd").change(function () {
    var password = $(this).val();
    var password2 = /^[A-z0-9_-]{6,12}$/
    if (!password2.test(password)) {
        $(".mmcw").text("6-12位密码");
    } else {
        $(".mmcw").text("");
    }
})
//再次输入密码收判断两次密码是否一致
$("#pwd2").change(function () {
    var password1 = $(this).val();
    var password2 = $("#pwd").val();
    if (password1 != password2) {
        $(".mmcw2").text("两次输入密码不一致");
    } else {
        $(".mmcw2").text("");
    }
})
//输入邮箱后判断格式
$("#yx").change(function () {
    var yx = $(this).val();
    var yx1 = /^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/g;
    if (!yx1.test(yx)) {
        $(".yxcw").text("邮箱错误");
    } else {
        $(".yxcw").text("");
    }
})
//点击注册
var str1 = "";	//用来存放返回的邮箱验证码
$(".sub2").click(function () {
    var user = $(".user2").val();
    var password = $("#pwd").val();
    var password2 = $("#pwd2").val();
    var yx = $("#yx").val();
    var yxyzm = $("#yxyzm").val();
    //	alert(user+"  "+password+"  "+yx+"   "+yxyzm);
    if (yxyzm != str1) {
        $(".yzmcw").text("验证码错误");
    } else if (yxyzm == "") {
        $(".yzmcw").text("验证码为空");
    } else if (user == "") {
        $(".yzmcw").text("");
        $(".yhcw").text("用户名不能为空且必须为中文");
    } else if (password == "") {
        $(".yhcw").text("");
        $(".mmcw").text("6-12位密码");
    } else if (password != password2) {
        $(".mmcw2").text("两次输入密码不一致");
    } else if (yx == "") {
        $(".mmcw2").text("");
        $(".yxcw").text("邮箱为空");
    } else {
        $(".yxcw").text("");
        $.ajax({
            type: "post",
            url: "zc",
            data: {
                "user": user,
                "password": password,
                "email": yx,
            },
            dataType: "text",
            success: function (data) {
                console.info(data);
                if (data == 0) {
                    location.href = "error.html";
                } else {
                    alert("注册成功");
                    $(".user2").val("");
                    $("#pwd").val("");
                    $("#pwd2").val("");
                    $("#yx").val("");
                    $("#yxyzm").val("");
                }
            }
        });
    }
})
//发送邮件验证码
$(".sub1").click(function () {
    var email01 = $("#yx").val();
    console.info(email01);
    $.ajax({
        type: "post",
        url: "emailyzm",
        data: {"email": email01},
        dataType: "json",
        success: function (data) {
            if (data.emailyz != "") {
                str1 = data.emailyz;
            }
        }
    })
})
