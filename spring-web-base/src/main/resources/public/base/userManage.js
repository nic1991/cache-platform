var users;

function addUser(){
     $("#user_id").val("");
     $("#new_user_name").val("");
     $("#new_user_password").val("000000");
     $('#new_user_roles').multiselect('deselectAll', false);
     $('#new_user_roles').multiselect('updateButtonText');
     $(function () { $('#myModal').modal({
         keyboard: true
     })});
};

function saveUser(){
        requestParams ={};
        requestParams.id =  $("#user_id").val();
         if($("#new_user_name").val()==null || $("#new_user_name").val()==""){
            alert("name is null");
            return ;
        }
        requestParams.name =  $("#new_user_name").val();
          if($("#new_user_password").val()==null || $("#new_user_password").val()==""){
            alert("password is null");
            return ;
        }
        requestParams.password =  $("#new_user_password").val();
        requestParams.roles =  $("#new_user_roles").val();
        var params = JSON.stringify(requestParams);
        $.njx(
             basePath + "/rest/user/saveUser",
              params,
              true,
             "POST",
             "json",
             "application/json",
              function(data){
                   if(data.result!=null){
                       getUsers();
                       $("#closeEdit").click();
                   }
              },
              function(){
                    alert("error!");
              }
        );
 };

function getUsers(){
    var searchStr = $("#search").val();
    var params={};
    params.searchStr = searchStr;
    $.njx(
         basePath + "/rest/user/getUsers",
          params,
          true,
         "GET",
         "json",
         "application/json",
          function(data){
               if(data.result!=null){
                     users = data.result;
                     console.log(data);
                     var tbody = $("#userTable").find("tbody");
                     tbody.empty();
                     for(var i=0;i<users.length;i++){
                        var thisId = users[i].id;
                        var thisName = users[i].name;
                        var roles = users[i].roles;
                        var tr = $("<tr></tr>");
                        var codeTd = $("<td style=\"line-height:30px\">"+i+"</td>");
                        var nameTd = $("<td style=\"line-height:30px\">"+thisName+"</td>");
                        var operateTd =$("<td></td>");
                        var editBtn = $("<button id=\"userEdit_"+thisId+"\"type=\"button\" class=\"btn btn-primary btn-sm\" style=\"margin-left:20px;width:80px\">Edit</button>");
                        var dropBtn = $("<button id=\"userDel_"+thisId+"\"type=\"button\" class=\"btn btn-danger btn-sm\" style=\"margin-left:20px;width:80px\">Drop</button>");
                        operateTd.append(editBtn);
                        operateTd.append(dropBtn);
                        tr.append(codeTd);
                        tr.append(nameTd);
                        tr.append(operateTd);
                        tbody.append(tr);
                        editBtn.click(function(){
                             var btnId = $(this).eq(0).attr("id");
                             var splits = btnId.split('_');
                             var id = splits[1];
                             $("#user_id").val(id);
                             $('#new_user_roles').multiselect('deselectAll', false);
                             $('#new_user_roles').multiselect('updateButtonText');
                             for(var c=0;c<users.length;c++){
                                 if(users[c].id==id){
                                      $("#new_user_name").val(users[c].name);
                                      $('#new_user_roles').multiselect('select',users[c].roles);
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
                                         basePath + "/rest/user/deleteUser",
                                          params,
                                          true,
                                         "POST",
                                         "json",
                                         "application/json",
                                          function(data){
                                               if(data.result!=null){
                                                    getUsers();
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
          },
          function(){
                alert("error!");
          }
    );
};

function getRoles(){
    $.njx(
         basePath + "/rest/role/getRolesAsCandidates",
          null,
          true,
         "GET",
         "JSON",
         "application/json",
          function(data){
              if(data.result!=null){
                  initRole(data.result);
              }
          },
          function(){
                alert("error!");
          }
     );
}

function initRole(roles){
   $('#new_user_roles').empty();
      for(var i=0;i<roles.length;i++){
          $('#new_user_roles').append(
               $('<option value='+roles[i].id+'>'+roles[i].name+'</option>')
           );
    }
    $('#new_user_roles').multiselect('rebuild');
}

$(document).ready(function(){
       getUsers();
       $('#new_user_roles').multiselect({enableFiltering: true, buttonWidth : '300px',maxHeight: 400,enableCaseInsensitiveFiltering:true});
       getRoles();
});