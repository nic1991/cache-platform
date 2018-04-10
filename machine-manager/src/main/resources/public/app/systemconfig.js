var type = 1;
$(document).ready(function(){
    getSysConfig();

    $(".node-treeDemo").click(function(){
         $(this).siblings().removeClass("node-selected");
         $(this).addClass("node-selected");
         type = $(this).attr("data");
         getSysConfig();
    });
});

function getSysConfig(){
    layer.load(0,{shade:[0.75,'#fff'] });
    var data = '{"type":"'+type+'"}';
    $.ajax({
       type: 'POST',
       data:data,
       url: "/rest/sysconfig/getSysConfig",
       dataType: "json",
       contentType: "application/json",
       success: function(resultJson){
            var obj = eval(resultJson);
            buildSysConfigTable(obj);
            layer.closeAll('loading');
       },
       error: function(e){
            layer.closeAll('loading');
           alert("Unknown error!");
       }

    });
}

function AddConfig(){
  $(function() {
      $('#configModal').modal({
            keyboard: true
      })
  });
}

function SaveConfig(){
   var key =   $("#conf_key").val();
   var value = $("#conf_value").val();
   var info =  $("#conf_info").val();
   var type =  $('#conf_type option:selected').val();
   alert(type);
   if(key == '' || value == ''){
       layer.msg('Key or Value Is Null');
       return;
   }
   if(type == '0' ){
          layer.msg('Please Select Congif Type');
          return;
   }
     var data = '{"key":"'+key+'","value":"'+value+'","info":"'+info+'","type":"'+type+'"}';
     $.ajax({
             type: 'POST',
             data:data,
             url: "/rest/sysconfig/SaveConfig",
             dataType: "json",
             contentType: "application/json",
             success: function(resultJson){
                  var obj = eval(resultJson);
                  if(obj.code == '0'){
                     $("#configModal").modal("hide");
                     getSysConfig();
                  }else{
                     layer.msg('Save Failure!');
                  }

             },
             error: function(e){
                 alert("Unknown error!");
             }

      });
}

function DelConfig(key){
  var data = '{"key":"'+key+'"}';
  $.ajax({
         type: 'POST',
         data:data,
         url: "/rest/sysconfig/delSysConfig",
         dataType: "json",
         contentType: "application/json",
         success: function(resultJson){
              var obj = eval(resultJson);
              if(obj.code == '0'){
                 getSysConfig();
              }else{
                 layer.msg('Delete Failure!');
              }

         },
         error: function(e){
             alert("Unknown error!");
         }

  });
}

function UpdateConfig(key){

   var data = '{"key":"'+key+'","value":"'+$("#"+key).val()+'"}';
   $.ajax({
           type: 'POST',
           data:data,
           url: "/rest/sysconfig/UpdateConfig",
           dataType: "json",
           contentType: "application/json",
           success: function(resultJson){
                var obj = eval(resultJson);
                if(obj.code == '0'){
                   getSysConfig();
                }else{
                   layer.msg('Update Failure!');
                }

           },
           error: function(e){
               alert("Unknown error!");
           }

        });

}

function buildSysConfigTable(configList){
     var tbody = $("#configTable").find("tbody");
     tbody.empty();
     if(configList.length > 0){
          for(var i=0;i<configList.length;i++){
             var num = i+1;
             var configDiv;
             var conf = configList[i].key;
             configDiv = '<tr>' +
                         '<td style="display:table-cell; vertical-align:middle">' + num  + '</td>' +
                         '<td style="display:table-cell; vertical-align:middle">' + conf  + '</td>' +
                         '<td style="display:table-cell; vertical-align:middle"><input type="text" id=' + conf  + ' class="form-control" value="' + configList[i].value  + '"></input></td>' +
                         '<td style="display:table-cell; vertical-align:middle">' + configList[i].info  + '</td>' +
                         '<td style="display:table-cell; vertical-align:middle">' +
                         '&nbsp&nbsp<button class="btn btn-primary btn-sm" onclick=UpdateConfig("'+conf+'") type="button">Update</button>'+
                         '&nbsp&nbsp<button class="btn btn-danger btn-sm" onclick=DelConfig("'+conf+'") type="button">Delete</button>'+
                         '</td>' +
                         '</tr>'
             tbody.append(configDiv);
          }

     }else{
        var emptyDiv = '<tr><td colspan="5" style="text-align:center">Config Is Empty</td></tr>'
        tbody.append(emptyDiv);
     }
}
