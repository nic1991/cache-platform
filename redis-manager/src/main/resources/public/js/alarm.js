$('[href="#alarmRule"]').click(function(){
    smarty.get( "/alarm/getRuleList?clusterId=ssecbigdata", "alarm/rule_list_content", "alarm-content", function(){
        //$("table").dataTable({});
    }, true );
});
$('[href="#alarmHistory"]').click(function(){
    smarty.get( "/alarm/getRuleList?clusterId=ssecbigdata", "alarm/history_list_content", "alarm-content", function(){
        //$("table").dataTable({});
    }, true );
});
