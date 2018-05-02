window.HOST_URL = "http://" + window.location.host + "/";
window.STATIC_URL = window.HOST_URL;
window.JSTPL_URL = window.STATIC_URL + "jstpl/";


$(function(){

	
	$("#redisIp").on("blur", function(){
		var obj = $("#redisIp");
		var ip = obj.val().trim();
		if(ip != null && ip != ""){
            checkIp(obj, ip);
		}
	})
	
	$("#redisPort").on("keyup", function(event){
		var obj = $("#redisPort");
		var port = obj.val().replace(/[^\d]/g,'');
		obj.val(port)
		/*if(port != null && port != "") {
			if(!enterNumber(event)) {
				obj.addClass("input-error");
			} else {
				obj.removeClass("input-error");
			}
		}*/
	})
})

function checkIp(obj, ip){
    var ipRegex = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/;
	if(!ipRegex.test(ip)){
        obj.addClass("input-error");
        layer.msg("ip address wrong");
		return false;
	} else {
	    obj.removeClass("input-error");
		return true;
	}
}

/*only enter numbers*/
function enterNumber(){
	var keyCode = event.keyCode;
	alert(keyCode);
	if (keyCode >= 48 && keyCode <= 57) {  
        event.returnValue = true;    
    } else {
    	layer.msg("port wrong", function(){});
        event.returnValue = false;    
    } 
}


