var setting = {
    view: {
        dblClickExpand: false,
        showLine: true,
        selectedMulti: false
    },
    data: {
        simpleData: {
            enable:true,
            idKey: "id",
            pIdKey: "pId",
            rootPId: ""
        }
    },
    callback: {
        beforeClick: function(treeId, treeNode) {
              $("#menuItem_id").val(treeNode.id);
              $("#menuItem_parentId").val(treeNode.pId);
              $("#menuItem_name").val(treeNode.name);
              $("#menuItem_url").val(treeNode.menuUrl);
              $("#menuItem_icon").val(treeNode.menuIcon);
              $("#menuItem_sequence").val(treeNode.sequence);
              $("#menuItem_description").val(treeNode.description);
              console.log(treeNode);
        }
    }
};

function transferData(data){
      var treeNodes=[];
      for(var i=0;i<data.length;i++){
          var node={};
          node.id=data[i].id;
          node.pId = data[i].parentId;
          node.name = data[i].name;
          node.icon = "../tools/zTree/css/zTreeStyle/img/diy/2.png";
          node.menuIcon = data[i].icon;
          node.menuUrl = data[i].url;
          node.sequence = data[i].sequence;
          node.description = data[i].description;
          treeNodes.push(node);
      }
      console.log(treeNodes);
      return treeNodes;
};

function addMenu(){
     $("#new_menu_id").val("");
     $("#new_menu_name").val("");
     $("#new_menu_url").val("");
     $("#new_menu_icon").val("");
     $("#new_menu_sequence").val("0");
     $("#new_menu_description").val("");
     $('#new_menu_parent').multiselect('deselectAll', false);
     if(currentNodeId!=null){
          $('#new_menu_parent').multiselect('select', currentNodeId);
     }else{
          $('#new_menu_parent').multiselect('select', "0");
     }
     $('#new_menu_parent').multiselect('refresh');
     $(function () { $('#myModal').modal({
         keyboard: true
     })});
};

function buildTreeData(data){
   var tree=[];
   buildNodeData(tree,data,0);
   return tree;
};

function buildNodeData(tree,data,pId){
     for(var i=0;i<data.length;i++){
          if(data[i].parentId==pId){
                var node = {};
                node.id=data[i].id;
                node.pId = data[i].parentId;
                node.text = data[i].name;
                node.icon = data[i].icon;
                node.url = data[i].url;
                node.sequence = data[i].sequence;
                node.description = data[i].description;
               if(pId=="0"){
                   tree.push(node);
               }else{
                   if(tree.nodes!=null){
                      tree.nodes.push(node);
                   }else{
                      tree.nodes=[];
                      tree.nodes.push(node);
                   }
               }
               buildNodeData(node,data,node.id);
          }
     }
};

var currentNodeId=null;

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
                  //$.fn.zTree.init($("#treeDemo"), setting, transferData(data.result));
                  $('#treeDemo').treeview({
                        levels: 1,
                        data: buildTreeData(data.result),
                        expandIcon: 'glyphicon glyphicon-chevron-right',
                        collapseIcon: 'glyphicon glyphicon-chevron-down'
                   });
                  $('#treeDemo').on('nodeSelected', function(event, data) {
                      currentNodeId = data.id;
                      $("#menuItem_id").val(data.id);
                      $("#menuItem_parentId").val(data.pId);
                      $("#menuItem_name").val(data.text);
                      $("#menuItem_url").val(data.url);
                      $("#menuItem_icon").val(data.icon);
                      $("#menuItem_sequence").val(data.sequence);
                      $("#menuItem_description").val(data.description);
                      console.log(data);
                  });
                   $('#treeDemo').on('nodeUnselected', function(event, data) {
                      currentNodeId = null;
                  });
                  initParentMenu(data.result);

              }
          },
          function(){
                alert("error!");
          }
     );
 };

 function updateMenu(){
        requestParams ={};
        if($("#menuItem_id").val()==null || $("#menuItem_id").val()==""){
            alert("id is null");
            return ;
        }
        requestParams.id = $("#menuItem_id").val();
        if($("#menuItem_name").val()==null || $("#menuItem_name").val()==""){
            alert("name is null");
            return ;
        }
        requestParams.name =  $("#menuItem_name").val();
         if($("#menuItem_parentId").val()==null || $("#menuItem_parentId").val()==""){
           $("#menuItem_parentId").val("0");
        }
        requestParams.parentId =  $("#menuItem_parentId").val();
        if($("#menuItem_url").val()==null || $("#menuItem_url").val()==""){
            alert("url is null");
            return ;
        }
        requestParams.url =  $("#menuItem_url").val();
        if($("#menuItem_icon").val()==null || $("#menuItem_icon").val()==""){
            alert("icon is null");
            return ;
        }
        requestParams.icon =  $("#menuItem_icon").val();
         if($("#menuItem_sequence").val()==null || $("#menuItem_sequence").val()==""){
            alert("sequence is null");
            return ;
        }
        requestParams.sequence =  $("#menuItem_sequence").val();
          if($("#menuItem_description").val()==null || $("#menuItem_description").val()==""){
            alert("description is null");
            return ;
        }
        requestParams.description =  $("#menuItem_description").val();
        var params = JSON.stringify(requestParams);
        $.njx(
             basePath + "/rest/menu/updateMenu",
              params,
              true,
             "POST",
             "json",
             "application/json",
              function(data){
                   if(data.result!=null){
                       getMenus();
                       parent.window.getMenus();
                   }
              },
              function(){
                    alert("error!");
              }
        );
 };

 function saveNewMenu(){
        requestParams ={};
        if($("#new_menu_name").val()==null || $("#new_menu_name").val()==""){
            alert("name is null");
            return ;
        }
        requestParams.name =  $("#new_menu_name").val();
         if($("#new_menu_parent").val()==null || $("#mnew_menu_parent").val()==""){
            alert("parentId is null");
            return ;
        }
        requestParams.parentId =  $("#new_menu_parent").val();
        if($("#new_menu_url").val()==null || $("#new_menu_url").val()==""){
            alert("url is null");
            return ;
        }
        requestParams.url =  $("#new_menu_url").val();
        if($("#new_menu_icon").val()==null || $("#new_menu_icon").val()==""){
            alert("icon is null");
            return ;
        }
        requestParams.icon =  $("#new_menu_icon").val();
         if($("#new_menu_sequence").val()==null || $("#new_menu_sequence").val()==""){
            alert("sequence is null");
            return ;
        }
        requestParams.sequence =  $("#new_menu_sequence").val();
          if($("#new_menu_description").val()==null || $("#new_menu_description").val()==""){
            alert("description is null");
            return ;
        }
        requestParams.description =  $("#new_menu_description").val();
        var params = JSON.stringify(requestParams);
        $.njx(
             basePath + "/rest/menu/addMenu",
              params,
              true,
             "POST",
             "json",
             "application/json",
              function(data){
                   if(data.result!=null){
                       getMenus();
                       parent.window.getMenus();
                       $("#closeEdit").click();
                   }
              },
              function(){
                    alert("error!");
              }
        );

 };

  function deleteMenu(){
        requestParams ={};
        if($("#menuItem_id").val()==null || $("#menuItem_id").val()==""){
            alert("id is null");
            return ;
        }
        requestParams.id = $("#menuItem_id").val();
        requestParams.icon =  $("#menuItem_description").val();
        var params = JSON.stringify(requestParams);
        $.njx(
             basePath + "/rest/menu/deleteMenu",
              params,
              true,
             "POST",
             "json",
             "application/json",
              function(data){
                   if(data.result!=null){
                      $("#menuItem_id").val("");
                      $("#menuItem_parentId").val("");
                      $("#menuItem_name").val("");
                      $("#menuItem_url").val("");
                      $("#menuItem_icon").val("");
                      $("#menuItem_sequence").val("");
                      $("#menuItem_description").val("");
                      getMenus();
                      parent.window.getMenus();
                   }
              },
              function(){
                    alert("error!");
              }
        );
 };

 function initParentMenu(menu){
    $('#new_menu_parent').empty();
    $('#new_menu_parent').append($('<option value="0">no parent!</option>'));
    for(var i=0;i<menu.length;i++){
         $('#new_menu_parent').append(
              $('<option value='+menu[i].id+'>'+menu[i].name+'</option>')
          );
    }
    $('#new_menu_parent').multiselect('rebuild');
 };




$(document).ready(function(){
      $('#new_menu_parent').multiselect({enableFiltering: true, buttonWidth : '300px',maxHeight: 400,enableCaseInsensitiveFiltering:true});
      getMenus();
});
