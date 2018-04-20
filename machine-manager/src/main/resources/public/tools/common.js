function post(url, data, callback, errorcall) {
    ajax("POST",url, data, callback, errorcall);
}

function get(url, callback, errorcall) {
    ajax("GET",url, {}, callback, errorcall);
}

function ajax(type,url, data, callback, errorcall){
    $.ajax({
        type: type,
        url: url,
        async: false,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
        dataType: "json",
        success: callback,
        error:errorcall,
        beforeSend:function(XMLHttpRequest){
            layer.load(2,{shade:[0.75,'#fff'] });
        },
        complete:function(XMLHttpRequest){
            closeLayer();
        }
    });
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]).trim();
    return null; //返回参数值
}

/*计算百分比*/
function GetPercent(num, total) {
    num = parseFloat(num);
    total = parseFloat(total);
    if (isNaN(num) || isNaN(total)) {
    return "-";
    }
    return total <= 0 ? "0" : (Math.round(num / total * 10000) / 100.00);
}

//生成uuid
function UUID() {
        var s = [];
        var hexDigits = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
        for (var i = 0; i < 36; i++) {
            s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
        }
        s[14] = "4";
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);

        s[8] = s[13] = s[18] = s[23] = "-";

        var uuid = s.join("");
        return uuid;
}


function getInputStr(name){
    return $('input[name="'+  name +'"]').val().trim();
}

function getFormInput( id ){
    var obj = {};
    $( "#" + id ).find("input").each(function(i, input){
        var id = $(input).attr("name");
        var value = $(input).val().trim();
        obj[id] = value;
    });
    return obj;
}
