define(["echarts"], function(echarts){

    /**
     * 实现图表组件
     *@param bindName : 图表对象，取id
     *@param pieJson : 饼图数据,
     *@param categoryJson: 柱状图数据，
     *@version<1> 2018-09-27 limeiling : Created.
     */
    var echartsSet = {
        //echart  饼图模型
        echartsPieUI: function(bindName){
            var myChart = echarts.init(bindName);
            var option = {
                title:{
                    text: '',
                    x:'center',
                    y:'0px',
                    textStyle: {
                        fontSize: 16,
                        color: '#fff',
                        fontWeight: 'normal'
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} {b}: <br/>{c}({d}%) "
                },
                legend: {
                    type: 'scroll',
                    orient: 'vertical',
                    right: 10,
                    top: 20,
                    bottom: 20,
                    data: [],

                    // selected: data.selected
                },
                graphic:{
                    type:'text',
                    left:'center',
                    top:'center',
                    z:2,
                    zlevel:100,
                    style:{
                        text:'',
                        x:100,
                        y:100,
                        textAlign:'center',
                        fill:'#fff',
                        width:30,
                        height:30
                    }
                },
                series: [
                    {
                        name:'',
                        type:'pie',
                        radius: ['30%', '40%'],
                        // avoidLabelOverlap: false,
                        label: {
                            normal: {
                                formatter: '{b}\n{c}({d}%)',
                                textStyle: {
                                    fontSize: 12,
                                    color: '#fff'
                                }
                            }
                        },
                        data: []
                    }
                ]
            };
            myChart.setOption(option,true);
            window.onresize = myChart.resize;
            return myChart;
        },
        //echart  柱状图模型
        echartsCategoryUI: function(bindName){
            myChart = echarts.init(bindName);
            var option = {
                title : {
                    text:'',
                    x:'center',
                    y:'top',
                    textStyle: {
                        fontSize: 16,
                        color: '#fff',
                        fontWeight: 'normal'
                    }
                },
                tooltip: {
                    show: true,
                    trigger: 'axis'
                },
                legend: {
                    data:[],
                    y:'30',
                    textStyle: {
                        fontSize: 12,
                        color: '#fff',
                        // fontWeight: 'bold'
                    }
                },
                toolbox: {
                    show : true,
                    feature : {}
                },
                calculable : true,
                dataZoom: {
                    // type: 'inside',
                    show: true,
                    xAxisIndex: [0],
                    left: '9%',
                    bottom: -5,
                    realtime: true,
                    height: 20,
                    start: 0,
                    end: 5,
                    bottom:10,
                    textStyle : {
                        color: '#fff',
                    }
                },
                grid: {
                    top: '30%'
                },
                xAxis : [
                    {
                        type : 'category',
                        data:[],
                        axisLabel: {
                            // show: true,
                            textStyle: {
                                fontSize: 12,
                                color: '#fff'
                            }
                        },
                        axisLine: {
                            lineStyle: {
                                color: '#fff'
                            }
                        },
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        // name: '单位：'+DS_Distribution.unit,
                        name: '',
                        nameGap: '30',
                        splitLine:{//网格线
                            show: true,
                            lineStyle: {
                                // 使用深浅的间隔色
                                color: '#292525'
                            }
                        },
                        inverse: false,
                        axisLabel: {
                            show: true,
                            textStyle: {
                                fontSize: 12,
                                color: '#fff'
                            }
                        },
                        axisLine: {
                            lineStyle: {
                                color: '#fff'
                            }
                        },
                        nameTextStyle:{
                            color: ['#fff']
                        }
                    }
                ],
                series:[]
            };
            myChart.setOption(option,true);
            window.onresize = myChart.resize;
            return myChart;
        },
        //echart  折线图
        echartsBrokenLineUI: function(bindName){
            var myChart = echarts.init(bindName);
            option = {
                // backgroundColor: '#fff',
                title:{
                    text: '',
                    x:'center',
                    y:'10px',
                    textStyle: {
                        fontSize: 16,
                        fontWeight: 'normal',
                        color:'#fff'
                    }
                },
                tooltip: {
                    trigger: 'axis'
                },
                color:[],
                legend: {
                    data:[],
                    y:'35',
                    textStyle: {
                        fontSize: 10,
                        color:'#fff'
                    }
                },
                grid: {
                    left: '5%',
                    right: '4%',
                    top:'40%',
                    bottom: '11%',
                    containLabel: true
                },
                dataZoom: {
                    // type: 'inside',
                    show: true,
                    xAxisIndex: [0],
                    left: '9%',
                    bottom: 5,
                    realtime: true,
                    height: 20,
                    start: 0,
                    end: 30,
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data:[],
                    axisLine: {
                        lineStyle: {
                            color: '#fff'
                        }
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            fontSize: 10,
                            color:'#fff'
                        }
                    }
                },
                yAxis: {
                    type: 'value',
                    name:'',
                    axisLabel: {
                        show: true,
                        textStyle: {
                            fontSize: 12,
                            color: '#fff'
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#fff'
                        }
                    },
                    nameTextStyle:{
                        color: ['#fff']
                    }
                },
                series: []
            };
            myChart.setOption(option,true);
            window.onresize = myChart.resize;
            return myChart;
        },
        //echart  堆叠柱状图
        echartsStackUI: function(bindName){
            var myChart = echarts.init(bindName);
            option = {
                title:{
                    text: '',
                    x:'center',
                    y:'0',
                    textStyle: {
                        fontSize: 16,
                        color:'#fff',
                        fontWeight: 'normal'
                    }
                },
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    },
                },
                legend: {
                    x: 'center',
                    y: '28px',
                    itemWidth: 10,
                    itemHeight: 10,
                    itemGap: 10,
                    data: [],
                    textStyle: {
                        color: '#fft'
                    }
                },
                grid: {
                    left: '3%',
                    top:'30%',
                    bottom: '15%',
                    containLabel: true
                },
                dataZoom: {
                    // type: 'inside',
                    show: true,
                    xAxisIndex: [0],
                    left: '9%',
                    realtime: true,
                    height: 20,
                    start: 2,
                    end: 30,
                    bottom:10,
                    // backgroundColor:"rgba(47,69,84,0)",
                    textStyle : {
                        color: '#fff',
                    }
                },
                yAxis:  {
                    y:'10px',
                    type: 'value',
                    axisLine: {
                        lineStyle: {
                            color: '#fff'
                        }
                    },
                    name: '',
                    splitLine:{//网格线
                        show: true,
                        lineStyle: {
                            // 使用深浅的间隔色
                            color: '#292525'
                        }
                    },
                },
                xAxis: {
                    type: 'category',
                    data: [],
                    axisLine: {
                        lineStyle: {
                            color: '#fff'
                        }
                    },
                },
                color:[],
                series: []
            };
            myChart.setOption(option,true);
            window.onresize = myChart.resize;
            return myChart;
        }
    };

    return {
        echartsSet: echartsSet
    }
})


