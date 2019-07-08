$(function () {
    $(".zc").hide();
    var i = 0;
    stri = "";
    while (i < 4) {
        x = Math.floor(Math.random() * 10).toString();
        stri = stri + x;
        i++;
        if (stri.length == 4) {
            $(".yzm div").text(stri);
        }
    }
})