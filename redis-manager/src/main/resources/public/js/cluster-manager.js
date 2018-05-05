$(document).ready(function(){
    window.pluginType = getQueryString("pluginType");
    window.clusterId = getQueryString("clusterId");
    getCluster(window.clusterId, function(obj){
        window.cluster = obj.res;
        checkRedisVersion(window.cluster.address,function(obj){
            if( obj.res  == 2 ){
                layer.alert( "redis version only support monitor", {
                  icon: 1,
                  skin: 'layer-ext-moon',
                  title: 'redis version'
                },function(){
                    window.location.href = "/monitor/clusterMonitorList";
                });
            }
        });
        show_cluster_node_list( window.cluster.address );
    });
});

$('[href="#nodeManager"]').click(function () {
    $('iframe[name="node-manager"]').attr("src","/node/manager?pluginType=" + window.pluginType);
});

$('[href="#clusterManager"]').click(function () {
    show_cluster_node_list( window.cluster.address );
});

function show_cluster_node_list(address){
    smarty.get( "/cluster/detailNodeList?address=" + address, "cluster/cluster_manager_content", "clusterManager", function(){
            console.log("get...");
    }, true );
}

smarty.register_function('slave_tag', function( params ){
    var slot = params['slot'];
    if( typeof(slot) == "undefined" || slot == null ){
        return "";
    }
    var length = slot.length;
    if( length > 0 ){
        return "hidden";
    }
    return "";
});
smarty.register_function( 'node_status_tag', function( params ){
    var status = params['status'];
    var index = params['index'];
    if( typeof(status) == "undefined" || status == null ){
        return "...";
    }
    if( status.indexOf("fail") > 0 ){
        return "<span class='node-status danger glyphicon glyphicon-remove-circle' aria-hidden='true' data-status='fail' data-status-id='" + index + "'></span>";
    }
    return "<span class='node-status success glyphicon glyphicon-ok-circle' aria-hidden='true' data-status='ok' data-status-id='" + index + "'></span>";
});

smarty.register_function( 'slot_empty', function( params ){
    var slots = params['slots'];
    var nodeid = params['nodeid'];
    var res = "style='display:none'";
    if( typeof(slots) == 'object' ){
        for( var key in slots ){
            if( key == nodeid ){
                return "";
            }
        }
    }
    return res;
});


smarty.register_function( 'cycle_color', function(params){
    var id = params["id"];
    var role = params["role"];
    var res = "";
    if( role == "master" ){
        res += " style='background-color:#e7f6fb'";
    }
    return res;
});


smarty.register_function("new_node_show", function( params ){
    var slaveList = params['slaveList'];
    if( typeof(slaveList) == "undefined" || slaveList == null || slaveList.length <= 0 ){
        return "new_node_widthout_slot";
    }
    return "";
});


smarty.register_modifier( 'json_string', function( val ) {
    return JSON.stringify( val );
} );

smarty.register_function("slot_num", function( params ){
    var slots = params['slots'];
    var size = params['size'];
    var index = params['index'];
    if( typeof(slots) == "undefined" || slots == null || size == 0){
        return "<span class='badge background-danger slot' data-status='empty' data-index='" + index + "'> - </span>";
    }
    var avg = Math.round(16384/size);
    var count = 0;
    try{
        for(var i in slots){
            var temp = slots[i];
            if( temp.indexOf("-") == -1 ){
                count++;
            }else{
                var arr = temp.split("-");
                count += arr[1] - arr[0] + 1;
            }
        }
    }catch( e ){
        console.log( e );
        return "";
    }
    var  subtract = count - avg;
    var res = "";
    if( subtract > 20 ){
        res =  '<span class="badge background-warning slot" data-status="warn" data-index="' + index + '">' + count + ' ↑ </span>';
    }else if( subtract < -20 ){
        res =  '<span class="badge background-warning slot" data-status="warn" data-index="' + index + '">' + count + ' ↓ </span>';
    }else{
        res = '<span class="badge slot" data-status="normal"  data-index="' + index + '">' + count + '</span>';
    }
    return res;
});

smarty.register_function( 'cluster_status', function(params){
    var state = params["state"];
    if( state.toLowerCase() == "ok" ){
        return "<span class='label label-success'>Cluster Healthy</span>";
    }else {
        return "<span class='label label-danger'>Cluster Bad</span>";
    }

})