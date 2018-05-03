smarty.html( "node/humpback/create_cluster_step", {}, "create-cluster-container",function () {
    // Smart Wizard events
    $("#smartwizard").on("leaveStep", function(e, anchorObject, stepNumber, stepDirection) {
        console.log( stepNumber + "----------" +stepDirection);
        return true;
    });
    // Step show event
    $("#smartwizard").on("showStep", function(e, anchorObject, stepNumber, stepDirection, stepPosition) {
       //alert("You are on step "+stepNumber+" now");
       if(stepPosition === 'first'){
           $(".prev-btn").addClass('disabled');
       }else if(stepPosition === 'final'){
           $(".next-btn").addClass('disabled');
       }else{
           $(".prev-btn").removeClass('disabled');
           $(".next-btn").removeClass('disabled');
       }
    });

    // Smart Wizard
    $('#smartwizard').smartWizard({
            selected: 0,
            theme: 'arrows',
            transitionEffect:'fade',
            showStepURLhash: true,
            toolbarSettings: {}
    });

    $(".prev-btn").on("click", function() {
        // Navigate previous
        $('#smartwizard').smartWizard("prev");
        return true;
    });

    $(".next-btn").on("click", function() {
        // Navigate next
        $('#smartwizard').smartWizard("next");
        return true;
    });
});

$(document).on("click", "#start-install-node", function(){
    $('#smartwizard').smartWizard("next");
    smarty.html( "node/redis_console", {}, "redis-config-tpl",function () {
        var data = {};
        data.clusterId = $("input[name='clusterId']").val();
        connect( JSON.stringify(data), "/webSocket/createClusterLog");
    });
});

$(document).on("click", "#install-all-node", function(){
    $('#smartwizard').smartWizard("next");
});

$(document).on("click", "#set-master-finish", function(){
    disconnect();
    $('#smartwizard').smartWizard("next");
});