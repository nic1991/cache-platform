$(function(){
	// init time selector
    $(".start-time").flatpickr();
    $(".end-time").flatpickr();
	$(".relative-section ul li").on("click", function(){
		var timeRangeObj = $(this);
		timeRangeObj.addClass("selected").siblings().removeClass("selected");
		timeRangeObj.parent().siblings().children().removeClass('selected');
		var timeRange = timeRangeObj.attr("data");
		console.log(timeRange);
	})

	// show log
	$(".show-log").on("click", function(){
		$(".log-progress-bar").show();
		$(".progress-bar").css("width", "80%")
	})


})