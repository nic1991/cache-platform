$(document).ready(function(){
  rebuildHumpbackNodeListTable();
});

function rebuildHumpbackNodeListTable(){
    //var clusterId = window.clusterId;
    var clusterId = 1;
    smarty.get( "/node/getNodeList?pluginType=humpback&clusterId=" + clusterId , "plugin/humpback/humpback_mode_manager", "node-content", function(){
        $("table").dataTable({});
    }, true );

}
