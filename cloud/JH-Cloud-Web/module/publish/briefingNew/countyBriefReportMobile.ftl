
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>农情简报</title>
    <style type="text/css">
        .borderStyleDashed{
            border-bottom:dashed #000000 1px;
            line-height:27px;
        }
        .borderStyleSolid{
            border-bottom:solid #000000 1px;
            line-height:27px;
        }
        .myRightMain{
            width:100%
        }
        .myTextarea{
            border:dashed 1px;
            background:#FFFFE0;
            width:auto;
            line-height:27px;
            text-align:center;
        }
        .echartsDiv{
            width: 100%;
            height:400px;
        }
    </style>
    <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
    <script type="text/javascript">
        // 路径配置
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });

        // 使用
        require(
                [
                    'echarts',
                    'echarts/chart/line',//折线图
                    'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
                ],
                function (ec) {
                    // 基于准备好的dom，初始化echarts图表
                    var myChart = ec.init(document.getElementById('ttnEcharts'));

                    option = {
                        title: {
                            text: '折线图堆叠'
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data:['地温','历史地温均值']
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        toolbox: {
                            feature: {
                                saveAsImage: {}
                            }
                        },
                        xAxis:
                                [
                                    {
                                        type: 'category',
                                        boundaryGap: false,
                                        data: ${ttnDate!}
                                        ,
                                        splitArea:
                                        {
                                            show:true,
                                            areaStyle:
                                            {
                                                color:[
                                                    'rgba(123,19,19,0.3)',
                                                    'rgba(100,100,100,0.3)'
                                                ]
                                            }
                                        }
                                    }
                                ],
                        yAxis: {
                            type: 'value',
                            show:false
                        },
                        series: [
                            {
                                name:'地温',
                                type:'line',
                                stack: '总量',
                                data:${ttnNow!}
                            },
                            {
                                name:'历史地温均值',
                                type:'line',
                                stack: '总量',
                                data:${ttnHistory!}
                            }
                        ]
                    };
                    // 为echarts对象加载数据
                    myChart.setOption(option);


                    var myChartTrmm = ec.init(document.getElementById('trmmEcharts'));

                    var  option = {

                        legend: {
                            data:['降雨量mm','历史雨量均值mm']
                        },
                        xAxis: [
                            {
                                type: 'category',
                                data: ${trmmDate!},
                                axisPointer: {
                                    type: 'shadow'
                                } ,
                                splitArea:
                                {
                                    show:true,
                                    areaStyle:
                                    {
                                        color:[
                                            'rgba(123,19,19,0.3)',
                                            'rgba(100,100,100,0.3)'
                                        ]
                                    }
                                }
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value',
                                name: '水量',
                                show:false,
                                min: 0,
                                max: 250,
                                interval: 50,
                                axisLabel: {
                                    formatter: '{value} ml'
                                }
                            }
                        ],
                        series: [
                            {
                                name:'降雨量mm',
                                type:'bar',
                                data:${trmmNow!}
                            },
                            {
                                name:'历史雨量均值mm',
                                type:'bar',
                                data:${trmmHistory!}
                            }
                        ]
                    };



                    // 为echarts对象加载数据
                    myChartTrmm.setOption(option);
                }
        );
    </script>
</head>
<body style="margin: 0;padding: 0;font-family:'\9ED1\4F53';font-size:14px !important;">
<div class="myRightMain">
    <div>
        <h2><img src="data:image/gif;base64,"><span class="textSpan">${regionName!}</span></h2>
        <span class="borderStyleDashed">物侯阶段：<span class="textSpan">${growthPeriodName!}</span></br>
            <span class="borderStyleDashed">日均地温：
            <#if ttnAvg??>
                <span class="textSpan">${ttnAvg!}℃</span>(<span class="textSpan">${ttnAvgRegionName!}</span>)

            <#else>
                <#assign instructions='数据更新中。' />
            </#if>
	   </span></br>
            <span class="borderStyleDashed">有效积温：
            <#if ttnTotal?? >
                <span class="textSpan">${ttnTotal!}℃</span>(<span class="textSpan">${ttnTotalRegionName!}</span>)
            <#else>
                <span class="textSpan">数据更新中</span>
            </#if>
	   </span></br>
            <span class="borderStyleDashed">降雨总量：
            <#if trmmTotal?? >
                <span class="textSpan">${trmmTotal!}mm</span>(<span class="textSpan">${trmmTotalRegionName!}</span>)

            <#else>
                <span class="textSpan">数据更新中</span>
            </#if>
	   </span></br>
            <span class="borderStyleDashed">降雨同比：
            <#if trmmInfo>
                <span class="textSpan">${trmmInfo!}</span>
            <#else>
                <span class="textSpan">数据更新中</span>
            </#if>
	   </span></br>
            <span class="borderStyleDashed">墒情概况：${soilMoisture!}</span></br>
            <span class="borderStyleDashed">易发病害：${disease!}</span></br>
            <span class="borderStyleDashed">易发虫害：${InsectPests!}</span>
    </div>
    <div>
        <div id="ttnEcharts"  class="echartsDiv"></div>
        <div id="trmmEcharts"  class="echartsDiv"></div>
    </div>

    <div>
        病虫害防治措施

    <#if diseaseAllList?? && diseaseAllList?size &gt;0>
        <#list diseaseAllList as item>
            </br>
            <div>
                <div style="width:2%;float:left;">
                    <img src="data:image/gif;base64,"+${item.diseaseImg!}>
                </div>
                <div style="width:98%;float:left;">
                    ${item.diseaseName!}</br>
                    <span class="borderStyleSolid">
                    ${item.diseaseMeasure!}
				   </span>
                </div>
            </div>
        </#list>
    </#if>

    </div>

    <div class="myTextarea">
        <h2 >管理措施</h2>
    <#if remark??>
        <div>${remark!}</div>
    <#else>
        <div>当前阶段，应加强田间肥水管理</div>
    </#if>
    </div>

</body>
</html>