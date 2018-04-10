var urls;

function addUrl(){
 $("#url_id").val("");
 $("#new_url").val("");
 $("#new_description").val("");
 $(function () { $('#myModal').modal({
     keyboard: true
 })});
};

function saveUrl(){
    requestParams ={};
    requestParams.id =  $("#url_id").val();
     if($("#new_url").val()==null || $("#new_url").val()==""){
        alert("url is null");
        return ;
    }
    requestParams.url =  $("#new_url").val();
      if($("#new_description").val()==null || $("#new_description").val()==""){
        alert("description is null");
        return ;
    }
    requestParams.description =  $("#new_description").val();
    var params = JSON.stringify(requestParams);
    $.njx(
         basePath + "/rest/url/saveUrl",
          params,
          true,
         "POST",
         "json",
         "application/json",
          function(data){
               if(data.result!=null){
                   getUrls();
                   $("#closeEdit").click();
               }
          },
          function(){
                alert("error!");
          }
    );
}

function getUrls(){
  var searchStr = $("#search").val();
  var params={};
  params.searchStr = searchStr;
  $.njx(
     basePath + "/rest/url/getUrls",
      params,
      true,
     "GET",
     "JSON",
     "application/json",
      function(data){
          if(data.result!=null){
              urls=data.result;
              initUrls();
          }
      },
      function(){
            alert("error!");
      }
 );
}

function initUrls(){
  if(urls!=null){
         var tbody = $("#urlTable").find("tbody");
         tbody.empty();
         for(var i=0;i<urls.length;i++){
            var thisId = urls[i].id;
            var thisUrl =  urls[i].url;
            var thisDesc =  urls[i].description;
            var tr = $("<tr></tr>");
            var codeTd = $("<td style=\"line-height:30px\">"+i+"</td>");
            var urlTd = $("<td style=\"line-height:30px\">"+thisUrl+"</td>");
            var descTd = $("<td style=\"line-height:30px\">"+thisDesc+"</td>");
            var operateTd =$("<td></td>");
            var editBtn = $("<button id=\"urlEdit_"+thisId+"\"type=\"button\" class=\"btn btn-primary btn-sm\" style=\"margin-left:20px;width:80px\">Edit</button>");
            var dropBtn = $("<button id=\"urlDel_"+thisId+"\"type=\"button\" class=\"btn btn-danger btn-sm\" style=\"margin-left:20px;width:80px\">Drop</button>");
            operateTd.append(editBtn);
            operateTd.append(dropBtn);
            tr.append(codeTd);
            tr.append(urlTd);
            tr.append(descTd);
            tr.append(operateTd);
            tbody.append(tr);
            editBtn.click(function(){
                 var btnId = $(this).eq(0).attr("id");
                 var splits = btnId.split('_');
                 var id = splits[1];
                 $("#url_id").val(id);
                 for(var c=0;c<urls.length;c++){
                     if(urls[c].id==id){
                        $("#new_url").val(urls[c].url);
                        $("#new_description").val(urls[c].description);
                     }
                 }
                 $(function () { $('#myModal').modal({
                     keyboard: true
                 })});
            });
            dropBtn.click(function(){
                  if(confirm("sure to delete?")){
                        var btnId = $(this).eq(0).attr("id");
                        var splits = btnId.split('_');
                        var id = splits[1];
                        requestParams ={};
                        requestParams.id = id;
                         var params = JSON.stringify(requestParams);
                        $.njx(
                             basePath + "/rest/url/deleteUrl",
                              params,
                              true,
                             "POST",
                             "json",
                             "application/json",
                              function(data){
                                   if(data.result!=null){
                                        getUrls();
                                   }
                              },
                              function(){
                                    alert("error!");
                              }
                        );
                    }
            });
         }
   }
}


$(document).ready(function(){
   getUrls();
});