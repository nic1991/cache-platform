var ip;
var info;
var starttime;
var endtime;
var trigger = true;
var html = '<div id="one-command-result">'
                        + '<div class="one-command">'
                        + '<span ><b>></b></span>'
                        + '	<input type="text" class="current-input" id="command" />'
                        + '</div>'
                        + '<div id="result">'
                        + '	<span class="result" ></span>'
                        + '</div>'
                        + '</div>';
$(document).ready(function(){
     ip = getUrlParam("ip");
     //加载打分
     MachineAnalysis();
     getMachineInfo();
     getMonitorInfo(0);
     getNetWorkInfo(0);

    // 初始化时间选择器
    $("#starttime").flatpickr();
    $("#endtime").flatpickr();
    // time ranges
    $('#show-hide-div').on('click', function(){
        if(trigger){
            $('#time-panel').show();
            trigger = false;
        } else{
            $('#time-panel').hide();
            trigger = true;
        }
    })

    $('#relative-section li').on('click', function() {
        $(this).addClass('li-selected').siblings().removeClass('li-selected');
        $(this).parent().siblings().children().removeClass('li-selected');
        var range = $(this).attr("data");
        var currentTimestamp = new Date().getTime();
        starttime = currentTimestamp - (range * 60 * 1000);
        endtime = currentTimestamp;
        getMonitorInfo(1);
        getNetWorkInfo(1);
        $('#selected-range').text($(this).children().text());
        $('#starttime').val("");
        $('#endtime').val("");
        $('#time-panel').hide();
        trigger = true;
    })


    $('#tab-pane').bind("click","#tab-pane", function(){
        $('#command').focus();
     })

    $(document).on('keyup','#command', function(e){
        if(e.keyCode == 13){
            // 设置已执行的命令 input 只可读
            $('#command').attr("readonly","readonly");
            // 获得 返回数据 并显示结果
            var cmd = $('#command').val().trim();
            if(cmd == 'clear'){
                $('#tab-pane').html(html);
                $('#command').focus();
                return;
            }
            if(cmd != ''){
                 getCommamdInfo(cmd);
                 return;
            }
            // 删除ID
            $('#one-command-result').removeAttr("id");
            $('#command').removeAttr('id')
            $('#result').removeAttr("id");
            // 添加新的命令输入行
            $('#tab-pane').append(html);
            $('#command').focus();
        }
    })

    window.onresize = function(){
        setTimeout("cpuChart.resize()",300);
        setTimeout("memChart.resize()",300);
        setTimeout("loadChart.resize()",300);
        setTimeout("ioChart.resize()",300);
    }

    $("#cpuMonitor, #memoryInfo, #loadAverage, #network").on("click", function(){
        //console.log($(this).attr("id"));
       // showAlone($(this).attr("id"));
    })


});

function queryByTime(){
    var startStr = $('#starttime').val().trim();
    var endStr = $('#endtime').val().trim();
    if(startStr == '' || endStr == ''){
        layer.msg("datetime can't be null", function(){});
        return;
    }
    var star = Date.parse(new Date(startStr.replace(/-/g, '/')));
    var end = Date.parse(new Date(endStr.replace(/-/g, '/')));
    if(star < end){
        starttime = star;
        endtime = end;
        getMonitorInfo(1);
        getNetWorkInfo(1);
        $('#selected-range').text(startStr + " - " + endStr);
        $('#relative-section li').removeClass("li-selected");
        $('#time-panel').hide();
        trigger = true;
    } else {
        layer.msg("End time needs to be greater than start time", function(){});
        return;
    }
}

function getMachineInfo(){
    var data = '{"ip":"'+ip+'"}';
    $.ajax({
         type: 'POST',
         data:data,
         url: "/rest/machineinfo/getMachineInfo",
         dataType: "json",
         contentType: "application/json",
         success: function(resultJson){
              info = eval(resultJson);
              buildMachineInfo();
         },
         error: function(e){
             layer.msg("Unable Connect to The Machine!");
         }
     });
}

var tempdata = {"timeSpan":["01:00","02:00","03:00","04:00"],
            "detail":[
              {"name": "check-scan","data":[220, 182, 191, 234]},
              {"name": "check-DB","data":[150, 232, 201, 154]},
       ]
};

function MachineAnalysis(){
      $.ajax({
           type: 'POST',
           data:ip,
           url: "/rest/machine/machineAnalysis",
           dataType: "json",
           contentType: "application/json",
           success: function(resultJson){
                info = eval(resultJson);
                switch(info.level){
                    case 'A':
                         $("#score").css('color', '#57A957');
                         break;
                    case 'B':
                         $("#score").css('color', '#c48f00');
                         break;
                    case 'C':
                         $("#score").css('color', '#d81e06');
                         break;
                    default:
                         break;
                }
                $("#score").html(info.level);
           },
           error: function(e){
               layer.msg("Unable Connect to The Machine!");
           }
       });
}

function getMonitorInfo(haveTime){
    var data;
    if(haveTime == 1){
        data = '{"ip":"'+ip
                +'","starttime":"'+starttime
                +'","endtime":"'+endtime+'"}';
    } else {
        data = '{"ip":"'+ip+'"}';
    }
    $.ajax({
        type: 'POST',
        data:data,
        url: "/rest/machineinfo/getMonitorInfo",
        dataType: "json",
        contentType: "application/json",
        success: function(resultJson){
             var data = eval(resultJson);
             buildLineChart("CPU(%)","cpuMonitor",data[0],"cpu");
             buildLineChart("Memory","memoryInfo",data[1],"mem");
             buildLineChart("LoadAverage","loadAverage",data[2],"load");
        },
        error: function(e){
            alert("Unknown error!");
        }
    });
}

function getNetWorkInfo(haveTime){
    var data;
    if(haveTime == 1){
        data = '{"ip":"'+ip
                +'","starttime":"'+starttime
                +'","endtime":"'+endtime+'"}';
    } else {
        data = '{"ip":"'+ip+'"}';
    }
    $.ajax({
        type: 'POST',
        data:data,
        url: "/rest/machineinfo/getNetWorkInfo",
        dataType: "json",
        contentType: "application/json",
        success: function(resultJson){
             var data = eval(resultJson);
             buildLineChart("网络IO","network",data,"io");
        },
        error: function(e){
            alert("Unknown error!");
        }
    });

}

//提示只支持top free 等命令
function getCommamdInfo(cmd){

    var data = '{"ip":"'+ip+'","cmd":"'+cmd+'"}';
    $.ajax({
         type: 'POST',
         data:data,
         url: "/rest/machineinfo/getCommamdInfo",
         dataType: "json",
         contentType: "application/json",
         success: function(resultJson){
              info = eval(resultJson);
              var commandResult = info.data;
              $('#result span').text(commandResult.trim());
              // 删除ID
              $('#one-command-result').removeAttr("id");
              $('#command').removeAttr('id')
              $('#result').removeAttr("id");
              // 添加新的命令输入行
              $('#tab-pane').append(html);
              $('#command').focus();
         },
         error: function(e){
             alert("Unknown error!");
         }
     });
}

function buildMachineInfo(){
    $("#ip").html(info.ip);
    $("#osVersion").html(info.osVersion);
    $("#cpuModel").html(info.cpuModel);
    $("#cpuInfo").html(info.processor);
    $("#memory").html(info.memory);
    $("#swap").html(info.swap);
    $("#load").html(info.load_average);
    $("#connectnum").html(info.netstat);
    $("#psnum").html(info.ps_num);
    $("#threadnum").html(info.thread_num);
    $("#score").html(info.score);
}

var cpuChart;
var memChart;
var loadChart;
var ioChart;

//构造折线图
function buildLineChart(name,containerId,data){
            var myChart = echarts.init(document.getElementById(containerId));
            if(containerId=="cpuMonitor"){
                cpuChart = myChart;
            }
            if(containerId=="memoryInfo"){
                 memChart = myChart;
            }
            if(containerId=="loadAverage"){
                 loadChart = myChart;
            }
            if(containerId=="network"){
                ioChart = myChart;
            }

            var series = [];
            var legendData = [];
            myChart.hideLoading();
            var option= {
                legend: {
                        data: [
                        ],
                        orient: 'vertical',
                        left: '86%',
                        top: '21%'
                        /*"bottom": 10,
                        "right": "center",
                        "itemWidth": 8,
                        "itemHeight": 8*/
                },
                color: [
                          '#c23531','#bda29a','#61a0a8',
                          '#304554','#d58165','#bda29a','#91c7ae',
                          '#434348',
                          '#bda29a','#C6E579','#26C0C0','#F0805A','#F4E001',
                          '#B5C334'
                      ],
                title : {
                            text: name,
                            x:'center'
                        },
                tooltip: {
                   trigger: 'axis'
                },
                grid: {
                    left: '2%',
                    right: '16%',
                    bottom: 5,
                    top: '25%',
                    /*x: '3%',
                    y: '15%',
                    x2: '20%',
                    y2: '10%',*/
                    containLabel: true
                },
                toolbox: {
                    show: true,
                    feature: {
                        dataZoom: {
                            yAxisIndex: 'none',
                        },
                        magicType: {type: ['line', 'bar']},
                        restore: {},
                        saveAsImage: {}
                    }
                },
                xAxis: {
                       type : 'category',
                       data : data.timeSpan,
                       nameLocation: 'middle',
                       nameGap : 25
                },
                yAxis: {
                type: 'value',
                axisLabel: {
                    formatter: '{value}'
                }
                },
                series: []
            };

        //动态拼接series
        for (i = 0; i < data.detail.length; i++) {
            var datainfo = data.detail[i];
            if( datainfo != null){
                var item={
                    name:datainfo.name,
                    type:'line',
                    data:datainfo.data,
                    markPoint: {
                        data: [
                            {type: 'max', name: '最大值'},
                            {type: 'min', name: '最小值'}
                        ]
                    },
                    markLine: {
                        data: [
                            {type: 'average', name: '平均值'}
                        ]
                    }
                }


                legendData.push(datainfo.name);
                series.push(item);
            }
        }
        option.legend.data = legendData;
        console.log(legendData);
        option.series = series;
        myChart.setOption(option);
    }

function showAlone(id) {
    $('#'+id).highcharts({
        title : {
            text : 'Customized legengItemClick Event'
        },
        plotOptions : {
            line : {
                events : {
                    legendItemClick : function(event) {
                        var series = this.chart.series;
                        var mode = getVisibleMode(series, this.name);
                        var enableDefault = false;
                        if (!this.visible) {
                            enableDefault = true;
                        }
                        else if (mode == 'all-visible') {
                            $.each(series, function(k, serie) {
                                serie.hide();
                            });
                            this.show();
                        }
                        else if (mode == 'all-hidden') {
                            $.each(series, function(k, serie) {
                                serie.show();
                            });
                        }
                        else {
                            enableDefault = true;
                        }
                        return enableDefault;
                    }
                }
            }
        },
        series : generateRandomSeries()
    });
}
function generateRandomSeries() {
    var series = [];
    for (var i = 0; i < 4; i++) {
        var data = new Array();
        for (var j = 0; j < 10; j++) {
            data.push(Math.random());
        }
        series.push({
            data : data
        });
    }
    return series;
}

function getVisibleMode(series, serieName) {
    var allVisible = true;
    var allHidden = true;
    for (var i = 0; i < series.length; i++) {
        if (series[i].name == serieName)
            continue;
        allVisible &= series[i].visible;
        allHidden &= (!series[i].visible);
    }
    if (allVisible && !allHidden)
        return 'all-visible';
    if (allHidden && !allVisible)
        return 'all-hidden';
    return 'other-cases';
}