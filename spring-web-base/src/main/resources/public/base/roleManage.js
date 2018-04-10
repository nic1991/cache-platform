var roles;

function addRole(){
     $("#role_id").val("");
     $("#new_role_name").val("");
     $('#new_role_menus').multiselect('deselectAll', false);
     $('#new_role_menus').multiselect('updateButtonText');
     $('#new_role_urls').multiselect('deselectAll', false);
     $('#new_role_urls').multiselect('updateButtonText');
     $("#new_role_description").val("");
     $(function () { $('#myModal').modal({
         keyboard: true
     })});
};

function saveRole(){
        requestParams ={};
        requestParams.id =  $("#role_id").val();
         if($("#new_role_name").val()==null || $("#new_role_name").val()==""){
            alert("name is null");
            return ;
        }
        requestParams.name =  $("#new_role_name").val();
        if($("#new_role_description").val()==null || $("#new_role_description").val()==""){
            alert("description is null");
            return ;
        }
        requestParams.description =  $("#new_role_description").val();
        requestParams.menuArray = $("#new_role_menus").val();
        requestParams.urlArray = $("#new_role_urls").val();
        var params = JSON.stringify(requestParams);
        $.njx(
             basePath + "/rest/role/saveRole",
              params,
              true,
             "POST",
             "json",
             "application/json",
              function(data){
                   if(data.result!=null){
                       getRoles();
                       $("#closeEdit").click();
                   }
              },
              function(){
                    alert("error!");
              }
        );

}

 function getRoles(){
      var searchStr = $("#search").val();
      var params={};
      params.searchStr = searchStr;
      $.njx(
         basePath + "/rest/role/getRoles",
          params,
          true,
         "GET",
         "JSON",
         "application/json",
          function(data){
              console.log(data);
              if(data.result!=null){
                  roles = data.result;
                  initRoles();
              }
          },
          function(){
                alert("error!");
          }
     );
 };

 function initRoles(){
      if(roles!=null){
             var tbody = $("#roleTable").find("tbody");
             tbody.empty();
             for(var i=0;i<roles.length;i++){
                var thisId = roles[i].id;
                var thisName =  roles[i].name;
                var menuArray = roles[i].menuArray;
                var urlArray = roles[i].urlArray;
                var thisDesc =  roles[i].description;
                var tr = $("<tr></tr>");
                var codeTd = $("<td style=\"line-height:30px\">"+i+"</td>");
                var nameTd = $("<td style=\"line-height:30px\">"+thisName+"</td>");
                var descTd = $("<td style=\"line-height:30px\">"+thisDesc+"</td>");
                var operateTd =$("<td></td>");
                var editBtn = $("<button id=\"roleEdit_"+thisId+"\"type=\"button\" class=\"btn btn-primary btn-sm\" style=\"margin-left:20px;width:80px\">Edit</button>");
                var dropBtn = $("<button id=\"roleDel_"+thisId+"\"type=\"button\" class=\"btn btn-danger btn-sm\" style=\"margin-left:20px;width:80px\">Drop</button>");
                operateTd.append(editBtn);
                operateTd.append(dropBtn);
                tr.append(codeTd);
                tr.append(nameTd);
                tr.append(descTd);
                tr.append(operateTd);
                tbody.append(tr);
                editBtn.click(function(){
                     var btnId = $(this).eq(0).attr("id");
                     var splits = btnId.split('_');
                     var id = splits[1];
                     $("#role_id").val(id);
                     for(var c=0;c<roles.length;c++){
                         if(roles[c].id==id){
                             $("#new_role_name").val(roles[c].name);
                             $('#new_role_menus').multiselect('deselectAll', false);
                             $('#new_role_menus').multiselect('updateButtonText');
                             $('#new_role_urls').multiselect('deselectAll', false);
                             $('#new_role_urls').multiselect('updateButtonText');
                             $('#new_role_menus').multiselect('select',roles[c].menuArray);
                             $('#new_role_urls').multiselect('select',roles[c].urlArray);
                             console.log(roles[c].menuArray);
                             $("#new_role_description").val(roles[c].description);
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
                                 basePath + "/rest/role/deleteRole",
                                  params,
                                  true,
                                 "POST",
                                 "json",
                                 "application/json",
                                  function(data){
                                       if(data.result!=null){
                                            getRoles();
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
 };

 function getMenus(){
     $.njx(
         basePath + "/rest/menu/getMenus",
          null,
          true,
         "GET",
         "JSON",
         "application/json",
          function(data){
              if(data.result!=null){
                  initMenu(data.result);
              }
          },
          function(){
                alert("error!");
          }
     );
 }

  function initMenu(menu){
    $('#new_role_menus').empty();
    for(var i=0;i<menu.length;i++){
         $('#new_role_menus').append(
              $('<option value='+menu[i].id+'>'+menu[i].name+'</option>')
          );
    }
    $('#new_role_menus').multiselect('rebuild');
 };

  function getUrls(){
     var params={};
     params.searchStr = "";
     $.njx(
         basePath + "/rest/url/getUrls",
          params,
          true,
         "GET",
         "JSON",
         "application/json",
          function(data){
              if(data.result!=null){
                  initUrl(data.result);
              }
          },
          function(){
                alert("error!");
          }
     );
 };

 function initUrl(urls){
   $('#new_role_urls').empty();
    for(var i=0;i<urls.length;i++){
         $('#new_role_urls').append(
              $('<option value='+urls[i].id+'>'+urls[i].url+'</option>')
          );
    }
    $('#new_role_urls').multiselect('rebuild');
 };

$(document).ready(function(){
       getRoles();
       $('#new_role_menus').multiselect({enableFiltering: true, buttonWidth : '300px',maxHeight: 400,enableCaseInsensitiveFiltering:true});
       $('#new_role_urls').multiselect({enableFiltering: true, buttonWidth : '300px',maxHeight: 400,enableCaseInsensitiveFiltering:true});
       getMenus();
       getUrls();
});