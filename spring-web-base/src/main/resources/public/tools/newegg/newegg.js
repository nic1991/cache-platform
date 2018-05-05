var basePath="";

$(function(){

    jQuery.njx=function(url, data, async, type, dataType,contentType,successfnc, errorfnc) {
        async = (async==null || async=="" || typeof(async)=="undefined")? "true" : async;
        type = (type==null || type=="" || typeof(type)=="undefined")? "post" : type;
        dataType = (dataType==null || dataType=="" || typeof(dataType)=="undefined")? "json" : dataType;
        contentType = (contentType==null || contentType=="" || typeof(contentType)=="undefined")? "application/json" : contentType;
        data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
        $.ajax({
            type: type,
            async: async,
            data: data,
            url: url,
            dataType: dataType,
            contentType: contentType,
            success: function(d){
                 if(d.status!=1){
                      alert(d.error);
                      if(d.status==0){
                            document.location=basePath + "/rest/pages/session_timeout";
                      }else if(d.status==-1){
                            document.location=basePath + "/rest/pages/not_permitted";
                      }
                 }else{
                    successfnc(d);
                 }
            },
            error: function(e){
                 errorfnc(e);
            }
        });
    };

});