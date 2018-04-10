var page = 1;
var data = '{}';
$(document).ready(function(){
    getMachine();

    $("#is_vm").change(function(){
        var is_vm = $("#is_vm option:selected").val().trim();
        $(this).children('option:selected').attr("selected", "selected").siblings().removeAttr("selected");
        if(is_vm == 0){
            $("#host_ip").attr("disabled", "disabled");
            $("#host_ip").val("");
        } else {
            $("#host_ip").removeAttr("disabled");
        }
    })

    $("#is_monitor").change(function(){
        $(this).children('option:selected').attr("selected", "selected").siblings().removeAttr("selected");;
    })

    // query enter event
    $('#search').keyup(function(e){
        if(e.keyCode == 13){
            QueryMachineByIp();
        }
    })

    $('#search').keyup(function(){
        checkIp($('#search'));
    })
    $('#machine_ip').keyup(function(){
       checkIp($('#machine_ip'));
    })
    $('#host_ip').keyup(function(){
      checkIp($('#host_ip'));
    })
});

function checkIp(object){
    var value = object.val();
        if(/[^\d\.]/g.test(value)){
           layer.msg('Please enter a properly formatted IP');
           object.val(value.substr(0, value.length - 1));
        }
}

function getMachine(){
    layer.load(0,{shade:[0.75,'#fff'] });
    $.ajax({
       type: 'POST',
       data:data,
       url: "/rest/machine/getMachine",
       dataType: "json",
       contentType: "application/json",
       success: function(resultJson){
            var obj = eval(resultJson);
            buildMachineTable(obj);
            closeLayer();
       },
       error: function(e){
           closeLayer();
           alert("Unknown error!");
       }
    });
}

function buildMachineTable(machineList){
     var tbody = $("#MachineTable").find("tbody");
     if(machineList.length > 0){
          tbody.empty();
          for(var i = 0;i < machineList.length; i++){
            var num = i+1;
            var machineDiv;
            if(machineList[i].IsVM == "1"){
              machineList[i].IsVM = '<img src="../../app/images/yes.svg" class="type-img"/>';
            }else{
              machineList[i].IsVM = '<img src="../../app/images/no.svg" class="type-img"/>';
            }
            if(machineList[i].IsMonitor == "1"){
              machineList[i].IsMonitor = '<img src="../../app/images/yes.svg" class="type-img"/>';
            }else{
              machineList[i].IsMonitor = '<img src="../../app/images/no.svg" class="type-img"/>';
            }
            var ip = machineList[i].MachineIP;
            var machineName = machineList[i].MachineName;

            var memory =  machineList[i].Memory;
            var memoryPer = GetPercent(memory.split("/")[0],memory.split("/")[1]);
            var swap =  machineList[i].Swap;
            var swapPer = GetPercent(swap.split("/")[0],swap.split("/")[1]);
            var disk =  machineList[i].Disk;
            var diskPer = GetPercent(disk.split("/")[0],disk.split("/")[1]);

            var status;
            var level = machineList[i].Level;
            if(level == "A"){
               status = '<div style="width:20px;height:20px;background:#57A957;border-radius:10px;margin-left:15px"></div>';
            }else if(level == "B" ){
               status = '<div style="width:20px;height:20px;background:#c48f00;border-radius:10px;margin-left:15px"></div>';
            }else if(level == "C"){
               status = '<div style="width:20px;height:20px;background:#d81e06;border-radius:10px;margin-left:15px"></div>';
            }else{
               status = '<div style="width:20px;height:20px;background:#000000;border-radius:10px;margin-left:15px"></div>';
            }
            var warningNum = machineList[i].warningNum;
            var unread = "";
            if(warningNum > 0){
                unread = '<a href="javascript:void(0);" onclick=earlyWarning("'+ip+'")><span class="badge unread-message">'+warningNum+'</span></a>';
            }
            machineDiv = '<tr>'+
                         '<td style="display:table-cell; vertical-align:middle" >' + num  + '</td>'+
                         '<td style="display:table-cell; vertical-align:middle" >' + status  + '</td>'+
                         '<td style="display:table-cell; vertical-align:middle"><a href="javascript:void(0)"  onclick=MachineInfo("'+ip+ '")>' + machineName+'('+ ip  +')' + '</href></td>'+
                         '<td style="display:table-cell; vertical-align:middle">' + machineList[i].Location  + '</td>'+
                         '<td style="display:table-cell; vertical-align:middle"><div><div style="vertical-align:middle;text-align:center;">' + memory + '</div><div style="vertical-align:middle;text-align:center;margin-top:-15px"><meter style="height:3px" low="30" high="80" max="100" optimum="0" value=' +  memoryPer + '></div></div></meter></td>'+
                         '<td style="display:table-cell; vertical-align:middle"><div><div style="vertical-align:middle;text-align:center;">' + swap  + '</div><div style="vertical-align:middle;text-align:center;margin-top:-15px"><meter style="height:3px" low="30" high="80" max="100" optimum="0" value=' +  swapPer + '></div></div></meter></td>'+
                         '<td style="display:table-cell; vertical-align:middle"><div><div style="vertical-align:middle;text-align:center;">' + disk  + '</div><div style="vertical-align:middle;text-align:center;margin-top:-15px"><meter style="height:3px" low="30" high="80" max="100" optimum="0" value=' +  diskPer + '></div></div></meter></td>'+
                         '<td style="display:table-cell; vertical-align:middle">' + machineList[i].IsVM  + '</td>'+
                         '<td style="display:table-cell; vertical-align:middle"> HostIP ： ' + machineList[i].HostIP  + '</td>'+
                         '<td style="display:table-cell; vertical-align:middle">' + machineList[i].MachineDesc  + '</td>'+
                         '<td style="display:table-cell; vertical-align:middle">' + machineList[i].IsMonitor  + '</td>'+
                         '<td style="display:table-cell; vertical-align:middle">'+
                         '<button class="btn btn-success btn-sm" onclick=MachineInfo("'+ip+ '") type="button">Info</button>'+
                         '&nbsp&nbsp<button class="btn btn-primary btn-sm" id="'+ i +'" onclick=UpdateMachine("'+ip+'") type="button">Update</button>'+
                         '&nbsp&nbsp<span><button class="btn btn-sm btn-warning" onclick=earlyWarning("'+ip+'") type="button">Warn</button>'+unread+'</span>'+
                         '&nbsp&nbsp<button class="btn btn-danger btn-sm" onclick=DelMachine("'+ip+'") type="button">Delete</button>'+
                         '</td></tr>';
           tbody.append(machineDiv);
          }
     }else{
          if(page != 1){
            page =  page - 1;
           layer.msg('Is Last Page !');
         }
     }
}

function MachineInfo(ip){
    var machineNav = $("<li >" + ip + "</li>");
    parent.window.appendNavBar(machineNav);
    location.href = "machineinfo.html?ip=" + ip;
}

function AddMachine(){
    $(function() {
        $('#machineModal').modal({
            keyboard: true
        })
    });
    // 隐藏一下菜单
    $('#right-show').hide();
    $('.right-span').css("display", 'none')
    $('#savebtn').attr('disabled', 'disabled');
    $('#savebtn').attr('operateType','0');
    $('.modal-body input').val("");
    $('.modal-body input').removeAttr("disabled");
    $('#is_vm').find("option[value='0']").removeAttr("selected");
    $('#is_monitor').find("option[value='0']").removeAttr("selected");
    $('#is_vm').find("option[value='1']").attr("selected","selected");
    $('#is_monitor').find("option[value='1']").attr("selected","selected");
}

function checkAddInfo(){
    var machine_ip = $('#machine_ip').val().trim();
    var usename = $('#usename').val().trim();
    var passwd = $('#passwd').val().trim();
    var location = $('#location').val().trim();
    var machine_name = $('#machine_name').val().trim();
    if(machine_ip.length != 0  && usename.length != 0 && passwd.length != 0  && location.length != 0 && machine_name.length != 0){
       var json = '{"ip":"'+machine_ip
                    +'","usename":"'+usename
                    +'","passwd":"'+passwd+'"}';

        $.ajax({
            type: 'POST',
            data:json,
            url: "/rest/machine/checkAddInfo",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function(resultJson){
                 var obj = eval(resultJson);
                 if(obj.code == 0){
                     $('.right-img').attr('src','../../app/images/right.svg')
                     $('.right-span').css("display", 'block')
                     $('#right-show').show();
                     $('#cpu_coresize').val(obj.core);
                     $('#memory').val(obj.memory);
                     $('#savebtn').removeAttr('disabled');
                 } else if(obj.code == 1){
                     $('.right-img').attr('src', '../../app/images/wrong.svg')
                     $('.right-span').css("display", 'block')
                     return;
                 }
                 closeLayer();
            },
            error: function(e){
                layer.alert("Unknown error!");
                closeLayer();
            }
        });
    } else {
         layer.alert("Incomplete information!");
    }

}

function saveMachine(){
    var machine_ip = $('#machine_ip').val().trim();
    var machine_name = $('#machine_name').val().trim();
    var location = $('#location').val().trim();
    var usename = $('#usename').val().trim();
    var passwd = $('#passwd').val().trim();
    var cpu_coresize = $('#cpu_coresize').val().trim();
    var memory = $('#memory').val().trim();
    var is_vm = $('#is_vm option:selected').val().trim();
    var host_ip = $('#host_ip').val().trim();
    var is_monitor = $('#is_monitor option:selected').val().trim();
    var remark = $('#remark').val().trim();
    var operateType =  $('#savebtn').attr('operateType');
    if(machine_ip != 0  && location.length != 0 && cpu_coresize.length != 0 && memory.length != 0 && (is_vm == 0 || (is_vm == 1 && host_ip.length != 0))){
        var json = '{"ip":"'+ machine_ip +
                     '", "machineName":"'+ machine_name +
                     '", "location":"'+ location +
                     '", "usename":"'+ usename +
                     '", "passwd":"'+ passwd +
                     '", "coreSize":"'+ cpu_coresize +
                     '", "memory":"'+ memory +
                     '", "isVm":"'+ is_vm +
                     '", "hostIp":"'+ host_ip +
                     '", "isMonitor":"'+ is_monitor +
                     '", "remark":"'+ remark +
                     '", "operateType":'+ operateType+ '}';
        $.ajax({
            type: 'POST',
            data:json,
            url: "/rest/machine/saveMachine",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function(resultJson){
                 var obj = eval(resultJson);
                 //buildMachineTable(obj);
                 if(obj.code == 0){
                     layer.msg("success");
                     setTimeout("location.reload()",2000);
                 } else if(obj.code == 1){
                     layer.msg("exists");
                 } else if(obj.code == 2){
                     layer.msg("error");
                 }
                 closeLayer();
            },
            error: function(e){
                layer.alert("Unknown error!");
                closeLayer();
            }
        });
    } else {
         layer.alert("Incomplete information!");
    }
}

function UpdateMachine(ip){
  $(function() {
      $('#machineModal').modal({
            keyboard: true
      })
  });
  $('#right-show').show();
  $('.right-span').css("display", 'none');
  // 设置为更新操作
  $('#savebtn').attr('operateType','1');
  $.ajax({
      type: 'POST',
      data:ip,
      url: "/rest/machine/getMachineById",
      dataType: "json",
      contentType: "application/json; charset=utf-8",
      success: function(resultJson){
            if(resultJson.MachineIP != '' && resultJson.MachineIP != null) {
                  $('#machine_ip').val(resultJson.MachineIP);
                  $('#machine_ip').attr("disabled", "disabled");
                  $('#location').val(resultJson.Location);
                  $('#location').attr("disabled", "disabled");
                  $('#cpu_coresize').attr("disabled", "disabled");
                  $('#memory').attr("disabled", "disabled");
                  $('#savebtn').attr('disabled', 'disabled');
                  $('#usename').val(resultJson.Usename);
                  $('#machine_name').val(resultJson.MachineName);
                  $('#passwd').val(resultJson.Passwd);
                  $('#cpu_coresize').val(resultJson.CoreSize);
                  $('#memory').val(resultJson.Memory);
                  if(resultJson.IsVM == 1){
                    $('#is_vm').find("option[value='0']").removeProp("selected");
                    $('#is_vm').find("option[value='1']").prop("selected","selected");
                    $('#host_ip').removeAttr("disabled");
                    $('#host_ip').val(resultJson.HostIP);
                  } else {
                    $('#is_vm').find("option[value='1']").removeProp("selected");
                    $('#is_vm').find("option[value='0']").prop("selected","selected");
                    /*$('#no').attr("selected", "selected").siblings().removeAttr("selected")*/
                    $('#host_ip').attr("disabled", "disabled");
                    $('#host_ip').val("");
                  }
                  if(resultJson.IsMonitor == '1'){
                    $('#is_monitor').find("option[value='0']").removeProp("selected");
                    $('#is_monitor').find("option[value='1']").prop("selected","selected");
                  } else {
                    $('#is_monitor').find("option[value='1']").removeProp("selected");
                    $('#is_monitor').find("option[value='0']").prop("selected","selected");
                  }
                  $('#remark').val(resultJson.MachineDesc);
            }

           closeLayer();
      },
      error: function(e){
          closeLayer();
          layer.alert("Unknown error!");
      }
  });
}

function QueryMachineByIp(){
  var machineIp = $("#search").val();
  if(machineIp == "" || machineIp == null){
        jqueryAlert({
          'icon'    : '../../tools/img/warning.png',
          'content' : 'please input machine ip !',
          'closeTime' : 2000,
        })
        return;
  } else {
    $.ajax({
          type: 'POST',
          data:machineIp,
          url: "/rest/machine/getMachineById",
          dataType: "json",
          contentType: "application/json; charset=utf-8",
          success: function(resultJson){
            if(null != resultJson) {
               var obj = eval(resultJson);
               var machines = new Array();
               machines[0] = obj;
               buildMachineTable(machines);
               $('#paging').hide();
            }
            closeLayer();
          },
          error: function(e){
              layer.alert("not exist");
              closeLayer();
          }
      });
  }

}

function earlyWarning(ip){
    var machineNav = $("<li >" + ip + "</li>");
    parent.window.appendNavBar(machineNav);
    location.href = "earlywarning.html?ip=" + ip;
}

function DelMachine(ip){
    layer.confirm('confirm delete? ip: ' + ip, {
      btn: ['confirm','cancel'] //按钮
    }, function(){
       $.ajax({
             type:"POST",
             data:ip,
             url:"/rest/machine/deleteMachineById",
             dataType:"json",
             contentType:"application/json; charset=utf-8",
             success: function(resultJson){
                 if(resultJson.code == 0){
                     layer.msg('delete successfully', {icon: 1});
                     setTimeout("location.reload()",1000);
                 } else {
                       layer.msg('delete failed');
                 }
                 closeLayer();
             },
             error: function(e){
                  layer.msg('delete error');
             }
         })
    }, function(){

    });
}

function closeLayer(){
    layer.closeAll('loading');
}

//翻页
function pages(type){
   //prev
   if(type == 0){
      if(page == 1){
        layer.msg('Is First Page !');
      }else{
        page = page - 1;
        data = '{"page":"'+page+'"}';
        getMachine();
      }
   }else{
        //next
        page = page + 1;
        data = '{"page":"'+page+'"}';
        getMachine();
   }
 }
