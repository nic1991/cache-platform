
$("#field-title > th").click(function () {
    var field = $(this).data("field");
    if( field != "date" ){
        $("#field-title > th").removeClass("selected");
        $(this).addClass("selected");
        var clusterId = getQueryString("clusterId");
        ajax.async_get("/monitor/getGroupNodeInfo?clusterId=" + clusterId + "&startTime=0&endTime=1624732279&host=all&type=max&date=minute", function(obj){
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
    for(var i = 0; i < window.nodeList.length; i++){
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
        });
    }
    $("#slow-log-table").DataTable({
        pageLength:15,
        lengthMenu: [15, 30, 50, 100, 200, 300 ],
        order: [[ 1, 'asc' ]]
    });
    $btn.button('reset');
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
