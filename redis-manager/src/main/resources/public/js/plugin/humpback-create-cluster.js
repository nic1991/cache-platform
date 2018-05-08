$(document).ready(function(){
    getImageList("humpback", function(obj){
        console.log( obj );
        var userGroup = window.user.userGroup || "";
        var groupList = [];
        if( userGroup != "" ){
            groupList = userGroup.split(",");
        }
        obj.groups = groupList;
        createClusterStep( obj );
    });

});
function  createClusterStep( data ){
    smarty.html( "plugin/humpback/create_cluster_step", data, "create-cluster-container",function () {
        autosize(document.querySelectorAll('textarea'));
        var data = {};
        data.id = window.user.id;
        connect( JSON.stringify(data), "/webSocket/createClusterLog");
    });
}
