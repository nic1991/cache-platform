var ip;
var type; // 1:更新操作，0：添加操作
$(function(){
    ip = getUrlParam("ip");
    getWarningList(ip);
    getRuleList(ip);
    $('#ip').text(ip);
    $("#checkAll").attr("data", ip);
    $("#deleteAll").attr("data", ip);
    $(".refresh").on("click", function(){
        location.reload();
    })

    $("#checkAll").on("click", function(){
        layer.load(0,{shade:[0.75,'#fff'] });
        var ip = $("#checkAll").attr("data");
        if(ip != null && ip != ""){
            $.ajax({
                type: 'POST',
                url:"/rest/warning/checkAllWarning",
                dataType:"json",
                contentType:"application/json;charset=utf-8",
                data:ip,
                success:function(result){
                    layer.msg("Check Success")
                    if(result != null){
                        buildWarningTable(result);
                    }
                },
                error:function(error){
                    alert("Unknown error!");
                }
            })
        }
        layer.closeAll('loading');
    })

    $("#deleteAll").on("click", function(){
        layer.confirm('Confirm delete all checked warning?', {
            title:"Delete",
            btn: ['Confirm','Cancel'] //按钮
            }, function(){
                var ip = $("#deleteAll").attr("data");
                if(ip != null && ip != ""){
                    $.ajax({
                        type: 'POST',
                        url:"/rest/warning/deleteAllWarningByIp",
                        dataType:"json",
                        contentType:"application/json;charset=utf-8",
                        data:ip,
                        success:function(result){
                            layer.msg("Delete Success")
                            if(result != null){
                                buildWarningTable(result);
                            }
                        },
                        error:function(error){
                            alert("Unknown error!");
                        }
                    })
                }
            }, function(){});
    })

    $("#formula").blur(function(){
        checkFormula();
    })
});

function checkWarning(id, ip){
    layer.load(0,{shade:[0.75,'#fff'] });
    if(id != null && id != "" && ip != null && ip != ""){
        $.ajax({
            type: 'POST',
            url:"/rest/warning/checkWarning",
            dataType:"json",
            data:{
                "id":id,
                "ip":ip
            },
            success:function(result){
                if(result.code != 0){
                    $("#"+id).children("td:eq(0)").html("");
                }
            },
            error:function(error){
                alert("Unknown error!");
            }
        })
    }
    layer.closeAll('loading');
}

function deleteWarningById(id, ip){
     layer.confirm('Confirm delete?', {
          title:"Delete",
          btn: ['Confirm','Cancel'] //按钮
        }, function(){
            if(id != null && id != "" && ip != null && ip != ""){
                $.ajax({
                    type: 'POST',
                    url:"/rest/warning/deleteWarningById",
                    dataType:"json",
                    data:{
                        "id":id,
                        "ip":ip
                    },
                    success:function(result){
                        layer.msg("success")
                        if(result != null){
                            buildWarningTable(result);
                        }
                    },
                    error:function(error){
                        alert("Unknown error!");
                    }
                })
            }
            layer.closeAll('loading');
         }, function(){});
}

function buildRuleTable(ruleList){
     var tbody = $("#rules-table").find("tbody");
     if(ruleList.length > 0){
          tbody.empty();
          for(var i = 0; i < ruleList.length; i++){
            var oneRule = ruleList[i];
            var id = oneRule.id;
            var index = i+1;
            var limitName = oneRule.limitName;
            var formula = oneRule.formula;
            var description = oneRule.description;
            var updateTime = oneRule.updateTime;
            var ruleDiv = '<tr>' +
                              '<td style="display:table-cell; vertical-align:middle">'+index+'</td>'+
                              '<td style="display:table-cell; vertical-align:middle">'+limitName+'</td>'+
                              '<td style="display:table-cell; vertical-align:middle">'+formula+'</td>'+
                              '<td style="display:table-cell; vertical-align:middle">'+description+'</td>'+
                              '<td style="display:table-cell; vertical-align:middle">'+updateTime+'</td>'+
                              '<td style="display:table-cell; vertical-align:middle">'+
                                  '<button class="btn btn-primary btn-sm" type="button" onclick="updateRule(\''+id+'\')">Update</button>&nbsp&nbsp'+
                                  '<button class="btn btn-danger btn-sm spacing" type="button" onclick="deleteRule(\''+id+'\')">Delete</button>'+
                              '</td>'+
                         '</tr>';
           tbody.append(ruleDiv);
          }
     }
}

function buildWarningTable(warningList){
     var tbody = $("#warnings-table").find("tbody");
     if(warningList != null && warningList.length > 0){
          tbody.empty();
          for(var i = 0; i < warningList.length; i++){
            var oneWarning = warningList[i];
            var id = oneWarning.id;
            var isChecked = oneWarning.isChecked;
            var warningImg = "";
            if(isChecked == 0){
                warningImg = '<img src="../../app/images/warning.svg" class="warning-img" />';
            }
            var limitName = oneWarning.limitName;
            var formula = oneWarning.formula;
            var description = oneWarning.description;
            var data = oneWarning.data;
            var updateTime = oneWarning.updateTime;
            var ruleDiv = '<tr id="'+id+'">' +
                              '<td style="display:table-cell; vertical-align:middle">'+warningImg+'</td>'+
                              '<td style="display:table-cell; vertical-align:middle">'+limitName+'</td>'+
                              '<td style="display:table-cell; vertical-align:middle">'+formula+'</td>'+
                              '<td style="display:table-cell; vertical-align:middle">'+description+'</td>'+
                              '<td style="display:table-cell; vertical-align:middle">'+data+'</td>'+
                              '<td style="display:table-cell; vertical-align:middle">'+updateTime+'</td>'+
                              '<td style="display:table-cell; vertical-align:middle">'+
                                  '<button class="btn btn-primary btn-sm" type="button" onclick="machineInfo(\''+ip+'\')">Info</button>&nbsp&nbsp'+
                                  '<button class="btn btn-success btn-sm" type="button" onclick="checkWarning(\''+id+'\',\''+ip+'\')">Check</button>&nbsp&nbsp'+
                                  '<button class="btn btn-danger btn-sm" type="button" onclick="deleteWarningById(\''+id+'\',\''+ip+'\')">Delete</button>&nbsp&nbsp'+
                              '</td>'+
                         '</tr>';
           tbody.append(ruleDiv);
          }
     } else {
        tbody.html('<tr><td colspan="7" class="no-rules">There is no warning.</td></tr>');
     }
}

function getWarningList(ip){
    layer.load(0,{shade:[0.75,'#fff'] });
    if(ip != '' && ip != null){
        $.ajax({
            type: 'POST',
            url:"/rest/warning/getWarningList",
            dataType:"json",
            data:ip,
            contentType:"application/json",
            success:function(result){
                buildWarningTable(result);
            },
            error:function(error){
                alert("Unknown error!");
            }
        })
    }
    layer.closeAll('loading');
}

function getRuleList(ip){
    layer.load(0,{shade:[0.75,'#fff'] });
    if(ip != '' && ip != null){
        $.ajax({
            type: 'POST',
            url:"/rest/rule/getRuleList",
            dataType:"json",
            data:ip,
            contentType:"application/json",
            success:function(result){
                buildRuleTable(result);
            },
            error:function(error){
                alert("Unknown error!");
            }
        })
    }
    layer.closeAll('loading');
}

function checkFormula(){
    var formula = $('#formula').val().trim();
    if(formula != null && formula != ""){
        $.ajax({
            type:"POST",
            url:"/rest/rule/checkMachineRule",
            dataType:"json",
            contentType:"application/json",
            data:formula,
            success:function(result){
                 if(result.code == 1){
                     $('.right-img').attr('src','../../app/images/right.svg')
                     $('.right-span').css("display", 'block')
                     $('#right-show').show();
                     $('#savebtn').removeAttr('disabled');
                 } else if(result.code == 0){
                     $('.right-img').attr('src', '../../app/images/wrong.svg')
                     $('.right-span').css("display", 'block');
                      $('#savebtn').attr('disabled', "disabled");
                     return;
                 }
            },
            error:function(error){
                layer.msg("error");
            }
        })
    } else {
        layer.msg("Formula can't be null");
    }
}

function saveRule(){
    var machineId = $('#savebtn').attr("data");
    var machineIp = $('#machineIp').val().trim();
    var limitName = $('#limitName').val().trim();
    var formula = $('#formula').val().trim();
    var description = $('#description').val().trim();

    if(machineIp.length != 0 && limitName.length != 0 && formula.length != 0 && description.length != 0){
        var data = '{"machineIp":"' + machineIp +
                    '","limitName":"' + limitName +
                    '","formula":"' + formula +
                    '","description":"' + description;
        if(type == 1) {
            data += '","machineId":"' + machineId +
                    '","type":' + type;
        } else if(type == 0){
            data += '","type":' + type;
        }
        data += '}';

        $.ajax({
            type:"POST",
            url:"/rest/rule/saveMachineRule",
            dataType:"json",
            contentType:"application/json",
            data:data,
            success:function(result){
                if(result.code == 1){
                    $('#savebtn').css("disabled", "disabled");
                    layer.msg("success");
                    setTimeout(function() {
                        $('#ruleModal').modal("hide");
                        getRuleList(ip);
                    }, 2000);

                } else if(result.code == 0){
                    layer.msg("IP: " + machineId + "， 该主机未在机器列表，请先添加");
                } else if(result.code == 2) {
                    layer.msg("\" " + formula + " \", 此规则已经存在", function(){});
                }else {
                    layer.msg("unknow error");
                }
            },
            error:function(error){
                layer.msg("error");
            }
        })
    } else {
        layer.msg("Incomplete information!", function(){});
        return;
    }
}

function addRule(){
    $('#savebtn').css("disabled", "disabled");
    $('.right-show').hide();
    $('.right-span').css("display", 'none')
    $('#savebtn').css("disabled", "disabled");
    type = 0;
    $('#myModalLabel').text("Add Machine Rule")
    $('#machineIp').val(ip);
    clearData();
	$(function() {
	    $('#ruleModal').modal({
	        keyboard: true
	    })
	});
}

function updateRule(id){
    $('#savebtn').css("disabled", "disabled");
    type = 1;
    $('#myModalLabel').text("Update Machine Rule")
	var data = id.trim();
    if(data != '' && data != null){
        $('#savebtn').attr("data", id);
        $.ajax({
            type:"POST",
            url:"/rest/rule/getMachineRuleById",
            dataType:"json",
            contentType:"application/json",
            data:data,
            success:function(result){
                $('#machineIp').val(result.ip);
                $('#limitName').val(result.limitName);
                $('#formula').val(result.formula);
                $('#description').val(result.description);
            },
            error:function(error){
                layer.msg("获取失败", function(){});
            }
        })
    }
    $(function() {
        $('#ruleModal').modal({
            keyboard: true
        })
    });
}

function deleteRule(id){
    var data = id.trim();
    if(data.length != 0){
        layer.confirm('confirm delete?', {
            title: "Delete",
            btn: ['confirm','cancel'] //按钮
        }, function(){
            $.ajax({
                type:"POST",
                data:data,
                url:"/rest/rule/deleteMachineRuleById",
                dataType:"json",
                contentType:"application/json",
                success: function(result){
                    if(result.code == 1){
                        layer.msg('delete successfully', {icon: 1});
                        getRuleList(ip);
                    } else {
                        layer.msg('delete failed');
                    }
                },
                error: function(e){
                    layer.msg('delete error');
                }
            })
        }, function(){});
    }
}

function machineInfo(ip){
    var machineNav = $("<li >" + ip + "</li>");
    parent.window.appendNavBar(machineNav);
    location.href = "machineinfo.html?ip=" + ip;
}

function clearData(){
    $('#limitName').val("");
    $('#formula').val("");
    $('#description').val("");
    $('#savebtn').removeAttr("data");
}