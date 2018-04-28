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
	
	/*init echarts*/


	/*show log*/
	$(".show-log").on("click", function(){
		$(".log-progress-bar").show();
		$(".progress-bar").css("width", "80%")
	})
	
	buildLineChartExample();
})

function buildLineChartExample(){
	var myChart = echarts.init(document.getElementById("infoEchart"));
	var option = {
    title: {
        text: '未来一周气温变化',
        subtext: '纯属虚构'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['最高气温','最低气温']
    },
    toolbox: {
        show: true,
        feature: {
            dataZoom: {
                yAxisIndex: 'none'
            },
            dataView: {readOnly: false},
            magicType: {type: ['line', 'bar']},
            restore: {},
            saveAsImage: {}
        }
    },
    xAxis:  {
        type: 'category',
        boundaryGap: false,
        data: ['周一','周二','周三','周四','周五','周六','周日']
    },
    yAxis: {
        type: 'value',
        axisLabel: {
            formatter: '{value} °C'
        }
    },
    series: [
        {
            name:'最高气温',
            type:'line',
            data:[11, 11, 15, 13, 12, 13, 10],
            markPoint: {
                data: [
                    {type: 'max', name: '最大值'},
                    {type: 'min', name: '最小值'}
                ]
            },
            markLine: {
                data: [
                    {type: 'average', name: '平均值'}
                ]
            }
        },
        {
            name:'最低气温',
            type:'line',
            data:[1, -2, 2, 5, 3, 2, 0],
            markPoint: {
                data: [
                    {name: '周最低', value: -2, xAxis: 1, yAxis: -1.5}
                ]
            },
            markLine: {
                data: [
                    {type: 'average', name: '平均值'},
                    [{
                        symbol: 'none',
                        x: '90%',
                        yAxis: 'max'
                    }, {
                        symbol: 'circle',
                        label: {
                            normal: {
                                position: 'start',
                                formatter: '最大值'
                            }
                        },
                        type: 'max',
                        name: '最高点'
                    }]
                ]
            }
        }
    ]
	};
	myChart.setOption(option)
}
function buildLineChart(){
            var myChart = echarts.init(document.getElementById("info-echart"));
            /*if(containerId=="cpuMonitor"){
                cpuChart = myChart;
            }*/
            var series = [];
            var legendData = [];
            myChart.hideLoading();
            var option= {
                legend: {
                        data: [
                        ],
                        orient: 'vertical',
                        left: '86%',
                        top: '21%'
                },
                color: [
                          '#c23531','#bda29a','#61a0a8',
                          '#304554','#d58165','#bda29a','#91c7ae',
                          '#434348',
                          '#bda29a','#C6E579','#26C0C0','#F0805A','#F4E001',
                          '#B5C334'
                      ],
                title : {
                            text: "Important Metric",
                            x:'center'
                        },
                tooltip: {
                   trigger: 'axis'
                },
                grid: {
                    left: '2%',
                    right: '16%',
                    bottom: 5,
                    top: '25%',
                    containLabel: true
                },
                toolbox: {
                    show: true,
                    feature: {
                        dataZoom: {
                            yAxisIndex: 'none',
                        },
                        magicType: {type: ['line', 'bar']},
                        restore: {},
                        saveAsImage: {}
                    }
                },
                xAxis: {
                       type : 'category',
                      /*data.timeSpan 传过来的是什么*/
                       data : data.timeSpan,
                       nameLocation: 'middle',
                       nameGap : 25
                },
                yAxis: {
                type: 'value',
                axisLabel: {
                    formatter: '{value}'
                }
                },
                series: []
            };
		
        //动态拼接series
        for (i = 0; i < data.detail.length; i++) {
            var datainfo = data.detail[i];
            if( datainfo != null){
                var item = {
                    name:datainfo.name,
                    type:'line',
                    data:datainfo.data,
                    markPoint: {
                        data: [
                            {type: 'max', name: '最大值'},
                            {type: 'min', name: '最小值'}
                        ]
                    },
                    markLine: {
                        data: [
                            {type: 'average', name: '平均值'}
                        ]
                    }
                }
                legendData.push(datainfo.name);
                series.push(item);
            }
        }
        option.legend.data = legendData;
        option.series = series;
        myChart.setOption(option);
    }