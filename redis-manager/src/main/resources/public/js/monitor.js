$(document).ready(function(){
    window.clusterId = getQueryString("clusterId");
    window.endTime = getQueryString("endTime") || getCurrentTime();
    window.startTime = getQueryString("startTime") || (window.endTime - 60 * 60);
    window.host = getQueryString("host") || "all";
    window.date = getQueryString("date") || "minute";
    window.type = getQueryString("type") || "max";
    $('th[data-field="responseTime"]').trigger("click");
    init();

    // init time selector
    $(".start-time").flatpickr();
    $(".end-time").flatpickr();
});

function getCurrentTime(){
    return Date.parse(new Date())/1000;
}
// select time
$(".relative-section ul li").on("click", function(){
    var timeRangeObj = $(this);
    timeRangeObj.addClass("selected").siblings().removeClass("selected");
    timeRangeObj.parent().siblings().children().removeClass('selected');
    var timeRange = parseInt(timeRangeObj.attr("data"));
    var currentTime = getCurrentTime();
    window.endTime = currentTime;
    window.startTime = window.endTime - timeRange * 60;
    console.log(currentTime);
    console.log(timeRange);
    console.log(timeRange * 60);
    reloadMonitor();
})


function reloadMonitor(){
    window.location.href = "/monitor/manager?clusterId="+window.clusterId+"&startTime=" + window.startTime + "&endTime="+window.endTime + "&host=" + window.host + "&type=" + window.type + "&date=" + window.date;
}

function init(){
    monitorGetMaxField(window.clusterId,window.startTime,window.endTime,"response_time", 2,function(obj){
        var resList = obj.res;
        var topListStr = "";
        for( var i = 0; i < resList.length; i++ ){
            topListStr += '<p class="top-item">Top-' + (i + 1) + ': [<span class="top_request_monitor">' + resList[i]["host"] + '</span>] <span class="danger">' + resList[i]["response_time"] + '</span><span>/ms</span></p>';
        }
        $("#top-response-time").append( topListStr );
    });
    monitorGetAvgField(window.clusterId,window.startTime,window.endTime,window.host, "response_time",function(obj){
        $("#avg-response").html( obj.res );
    });

    monitorGetAvgField(window.clusterId,window.startTime,window.endTime,window.host, "total_keys",function(obj){
        $("#all-key").html( obj.res );
    });
    monitorGetMaxField(window.clusterId,window.startTime,window.endTime,"total_keys", 2,function(obj){
        $("#max-all-host").html( obj.res.host );
        $("#max-all-key").html( obj.res.total_keys );
    });
    monitorGetMinField(window.clusterId,window.startTime,window.endTime,"total_keys", 2,function(obj){
        $("#min-all-host").html( obj.res.host );
        $("#min-all-key").html( obj.res.total_keys );
    });

    monitorGetMaxField(window.clusterId,window.startTime,window.endTime,"connected_clients", 2,function(obj){
        var resList = obj.res;
        var topListStr = "";
        for( var i = 0; i < resList.length; i++ ){
            topListStr += '<p class="top-item">Top-' + (i + 1) + ': [<span class="top_request_monitor">' + resList[i]["host"] + '</span>] <span class="danger">' + resList[i]["connected_clients"] + '</span></p>';
        }
        $("#top-avg-connection").append( topListStr );
    });

    monitorGetAvgField(window.clusterId,window.startTime,window.endTime,window.host, "connected_clients",function(obj){
        $("#avg-connection").html( obj.res );
    });

    monitorGetMaxField(window.clusterId,window.startTime,window.endTime,"instantaneous_ops_per_sec", 2,function(obj){
        var resList = obj.res;
        var topListStr = "";
        for( var i = 0; i < resList.length; i++ ){
            topListStr += '<p class="top-item">Top-' + (i + 1) + ': [<span class="top_request_monitor">' + resList[i]["host"] + '</span>] <span class="danger">' + resList[i]["instantaneous_ops_per_sec"] + '</span></p>';
        }
        $("#top-avg-instantaneous").append( topListStr );
    });

    monitorGetAvgField(window.clusterId,window.startTime,window.endTime,window.host, "instantaneous_ops_per_sec",function(obj){
        $("#avg-instantaneous").html( obj.res );
    });
}


$("#field-title > th").click(function () {
    var field = $(this).data("field");
    if( field != "date" ){
        $("#field-title > th").removeClass("selected");
        $(this).addClass("selected");
        ajax.async_get("/monitor/getGroupNodeInfo?clusterId=" + window.clusterId + "&startTime="+ window.startTime +"&endTime=" + window.endTime + "&host=" + window.host + "&type=" + window.type + "&date=" + window.date, function(obj){
            makeCharts("light", "#FFFFFF", field, obj.res);
            smarty.html( "monitor/node_info_table", obj, "node-info-table",function () {
                console.log("html ...");
            });
        });
    }
});

getClusterAddressByid(function(res){
    nodeList(res.ip, res/port, function(obj){
        window.nodeList = obj.res;
    });
})


$("#show_log").click(function(){
    var $btn = $(this).button('loading');
    $("#slow-log-table>tbody").empty();
    var logParam = {"logLimit":5};
    logParam.hostList = window.nodeList;
    monitorSlowLogs(logParam,function(obj){
        var items = obj.res;
        var tr = "";
        for(var index in items){
            tr += "<tr>";
            tr += "<td class='one_slow_log info-hover'>" + items[index].host + "</td>";
            tr += "<td>" + items[index].showDate + "</td>";
            tr += "<td>" + items[index].runTime + "</td>";
            tr += "<td>" + items[index].type + "</td>";
            tr += "<td>" + items[index].command + "</td>";
            tr += "</tr>";
        }
        $("#slow-log-table>tbody").append( tr );
        $("#slow-log-table").dataTable({
            pageLength:15,
            lengthMenu: [15, 30, 50, 100, 200, 300 ],
            order: [[ 1, 'asc' ]]
        });
        $btn.button('reset');
    });
});

function makeCharts(theme, bgColor, field, char_data_table) {
    var len = char_data_table.length;

    var chart1;

    if (chart1) {
        chart1.clear();
    }
    // background
    if (document.body) {
        document.body.style.backgroundColor = bgColor;
    }

    // column chart
    chart1 = AmCharts.makeChart("node-info-chart", {
        type: "serial",
        theme: theme,
        dataProvider: char_data_table,
        categoryField: "date",
        startDuration: 1,

        categoryAxis: {
            gridPosition: "start"
        },
        valueAxes: [{
            title: "Metric"
        }],
        graphs: [{
            type: "column",
            title: field,
            valueField: field,
            lineAlpha: 0,
            fillAlphas: 0.8,
            balloonText: "[[title]] in [[category]]  <b>[[value]]</b>"
        }]
    });
}
