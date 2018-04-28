$(function(){
	var ipRegex = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/;
	
	$("#redisIp").on("blur", function(){
		var obj = $("#redisIp");
		var ip = obj.val().trim();
		if(ip != null && ip != ""){
			if(!checkIp(ip, ipRegex)) {
			obj.addClass("input-error");
			} else {
				obj.removeClass("input-error");
			}
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

function checkIp(ip, ipRegex){
	if(!ipRegex.test(ip)){
		layer.msg("ip address wrong", function(){});
		return false;
	} else {
		return true;
	}
}

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
