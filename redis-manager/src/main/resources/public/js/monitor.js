$(document).ready(function(){
    window.clusterId = getQueryString("clusterId");
    window.startTime = 0;
    window.endTime = 1624732279;
    window.host = "all";
    window.type = "max";
    window.date = "minute";
    $('th[data-field="responseTime"]').trigger("click");
    init();
});

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


nodeList("10.16.46.192", 8018, function(obj){
    window.nodeList = obj.res;
});

$("#show_log").click(function(){
    var $btn = $(this).button('loading');
    $("#slow-log-table").empty();
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
        $("#slow-log-table").append( tr );
        $btn.button('reset');
        $("#slow-log-table").dataTable({
            pageLength:15,
            lengthMenu: [15, 30, 50, 100, 200, 300 ],
            order: [[ 1, 'asc' ]]
        });
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
