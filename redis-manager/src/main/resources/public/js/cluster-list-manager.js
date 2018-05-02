$(function(){
    smarty.get( "/cluster/listCluster?group=admin", "cluster/cluster_list_content", "cluster-list-content", function(){
        console.log("get...");
    }, true );

    smarty.register_function( 'cluster_state', function( params ){
        var address = params['address'];
        var id = params["id"];
        var div_id = "clustre-state-" +  id;
        getClusterInfoByAddress(address, function(obj){
            var state = obj.res.state;
            if(state == "ok"){
                $("#" + div_id).html( "<span>state:</span><span class='state-good'>OK1</span>" );
            } else {
                $("#" + div_id).html( "<span>state:</span><span class='state-bad'>Fail</span>" );
            }

        });
    });


	/*add redis cluster*/
	$("body").delegate(".add-btn", "click", function(){
        $('#redis-modal').modal({
            keyboard: true
        })
        $(".modal-content input").val("");
        $(".no-machine").hide();
	});

    $("#clusterName, #redisPort, #userName, #password").on("blur", function(){
        var obj = $(this);
        var val = obj.val();
        if(val != null && val != ""){
            obj.removeClass("input-error");
        }
    })

	/*save redis cluster*/
	$("#save").on("click", function(){
	    var clusterNameObj = $("#clusterName");
	    var redisIpObj = $("#redisIp");
	    var redisPortObj = $("#redisPort");
        var groupIdObj = $("#group");

	    var clusterName = clusterNameObj.val().trim();
        var redisIp = redisIpObj.val().trim();
        var redisPort = redisPortObj.val().trim();
        var groupId = $("#group option:selected").val().trim();

        if(clusterName == null || clusterName == ""){
            clusterNameObj.addClass("input-error");
            layer.msg("Cluster Name can't be null");
            return;
        } else {
            clusterNameObj.removeClass("input-error");
        }

        if(redisIp == null || redisIp == ""){
            redisIpObj.addClass("input-error");
            layer.msg("IP can't be null");
            return;
        } else {
            if(!checkIp(redisIpObj, redisIp)) {
                return;
            }
        }

        if(redisPort == null || redisPort == ""){
            redisPortObj.addClass("input-error");
            layer.msg("Port can't be null");
            return;
        } else {
            redisPortObj.removeClass("input-error");
        }

        if(groupId == null || groupId == "" || groupId == 0){
            layer.msg("Please select a group");
            return;
        }

        // TODO:ajax request 拉取所有node,如果有没有配置的机器，则要求用户添加机器（两次ajax），如果添加了，则直接添加成功
        var data = '{"clusterName":"' + clusterName +
                     '", "address":"' + redisIp + ":" + redisPort +
                     '", "userGroup":"' + groupId +
                     '"';
        var haveMachines = data + "}";
        console.log(haveMachines);
        $.ajax({
            type: "POST",
            url: "/cluster/addCluster",
            dataType: "json",
            contentType : "application/json; charset=utf-8",
            data: haveMachines,
            success: function(result){
                console.log(result);
                layer.msg("success");
                setTimeout("location.reload()", 1000);
            },
            error: function(error){
                alert("error");
                console.log(error);
            }
        })

        $(".no-machine").show();
        /*var userNameObj = $("#userName");
        var passwordObj = $("#password");

        var userName = userNameObj.val().trim();
        var password = passwordObj.val().trim();

        if(userName == null || userName == ""){
            userNameObj.addClass("input-error");
            layer.msg("User name can't be null");
            return;
        } else {
            userNameObj.removeClass("input-error");
        }

        if(password == null || password == ""){
            passwordObj.addClass("input-error");
            layer.msg("Password can't be null");
            return;
        } else {
            passwordObj.removeClass("input-error");
        }*/

        // TODO:ajax request 成功或失败
	})


	/*delete redis cluster*/
	$("body").delegate(".delete-container","click", function(){
		var deleteObj = $(this).parent().parent();
		bootbox.prompt({
		    title: "Please enter the cluster name what you will delete",
		    buttons: {
		    	confirm: {
		            label: 'Delete',
		            className: 'btn-danger'
		       },
		        cancel: {
		            label: 'Cancel'
		        }
		    },
		    callback: function (result){
		        var clusterName = deleteObj.attr("data");
				if(result != null && result != "") {
					if(result == clusterName) {

					    //TODO: ajax request

						deleteObj.remove();
						layer.msg("delete " + clusterName + "successfully")
					} else {
						layer.msg("cluster name wrong")
					}
				}
			}
		})
	});
})



