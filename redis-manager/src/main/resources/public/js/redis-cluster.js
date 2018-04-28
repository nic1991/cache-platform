$(function(){
	/*add redis cluster*/
	$(".add-btn").on("click", function(){
		$(function() {
        	$('#redis-modal').modal({
            keyboard: true
        })
    });
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
