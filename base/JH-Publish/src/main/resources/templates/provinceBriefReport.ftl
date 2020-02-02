<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>农情简报</title>
    <style type="text/css">
        body{margin: 0;padding: 0;font-family:'\9ED1\4F53';font-size:14px !important;}

        .borderStyleDashed{
            border-bottom:dashed #000000 1px;
            line-height:27px;
        }

        .myRightMain{
            width:80%;margin:0 auto;margin-bottom:50px;
        }

        .section1 h2{text-align:center;}
        .section1 table{border-spacing:0px 10px;}


        .myTextarea{
            border:dashed 1px;
            background:#FFFFE0;
            width:auto;
            line-height:27px;
            text-align:center;
            margin-top:3%;
        }


        .echartsDiv{
            width: 100%;
            height:400px;
        }


        /*****/
        .secTitle{height:36px;line-height:36px;font-size:22px;padding-bottom:10px;}
        .bgTitle{height:36px;line-height: 36px;width:18px;background: #21494F;}


        .table>div{height:22px;line-height: 22px;margin-bottom: 22px;}
        .table>div>span{display:block;float:left;}
        .table  .bgGrey{width:10%;background:#DCDCDC; text-align: center;}
        .table .bgTdSecond{width:30%;padding-left:5px;}
        .table .bgTdForth{width:40%!important;padding-left:5px;}


        .diseaseSection:not(:last-child){border-bottom: 1px solid #CECECE;width:100%;height:130px;margin-bottom:15px;}
        .diseaseStyle{width:50%;float:left;height:130px;}
        .diseaseStyle .baseImg{width:120px;height:115px;float:left;}
        .diseaseStyle .baseFont{width:calc(100% - 130px);float:left;position:relative;padding-left:10px;}
        .diseaseStyle .diseaseName{dispaly:block;font-size:22px;}
        .diseaseStyle .borderStyleSolid{
            position: absolute;

            bottom:-90px;
            left:10px;
            padding-right:10px;
            /*line-height:27px;*/
        }


    </style>

</head>
<body >
<div class="myRightMain">
    <div class="section1">
        <h2>
            <span class="textSpan">${chinaName!}</span>
            <span class="textSpan">${cropsName!}</span>
            <span class="textSpan">${beginDate!}</span>~<span class="textSpan">${endDate!}</span>农情简报
        </h2>

        <div class="secTitle">
            <span class="bgTitle">&nbsp;</span> <span>指标信息</span>
        </div>


        <div class="table">
            <div>
                <span class="bgGrey">
                    物侯阶段
                </span>
                <span class="bgTdSecond">${growthPeriodName!}
                </span>
                <span class="bgGrey">
                    降雨同比
                </span>
                <span class="bgTdForth">
                <#if trmmInfo??>
                    <span class="textSpan">${trmmInfo!}</span>
                <#else>
                    <span class="textSpan">数据更新中</span>
                </#if>
                </span>
            </div>

            <div>
                <span class="bgGrey">
                    日均地温
                </span>
                <span class="bgTdSecond">
					<#if ttnAvgMin??>
                        <span class="textSpan">${ttnAvgMin!}℃</span>(<span class="textSpan">${ttnAvgMinRegionName!}</span>)
                    </#if>
                    <#if ttnAvgMax??>
                        ~
                    <span class="textSpan">${ttnAvgMax!}℃</span>(<span class="textSpan">${ttnAvgMaxRegionName!}</span>)
                    </#if>
                </span>
                <span class="bgGrey">
                    墒情概况
                </span>
                <span class="bgTdForth">
                ${soilMoisture!}
                </span>
            </div>

            <div>
                <span class="bgGrey">
                    有效积温
                </span>
                <span class="bgTdSecond">
					<#if ttnTotalMin?? >
                        <span class="textSpan">${ttnTotalMin!}℃</span>(<span class="textSpan">${ttnTotalMinRegionName!}</span>)
                    </#if>
                    <#if ttnTotalMax??>
                        ~
                    <span class="textSpan">${ttnTotalMax!}℃</span>(<span class="textSpan">${ttnTotalMaxRegionName!}</span>)
                    </#if>
                </span>
                <span class="bgGrey">
                    易发病害
                </span>
                <span class="bgTdForth">
                ${disease!}
                </span>
            </div>

            <div>
                <span class="bgGrey">
                    降雨总量
                </span>
                <span class="bgTdSecond">

				<#if trmmTotalMin??>
                    <span class="textSpan">${trmmTotalMin!}mm</span>(<span class="textSpan">${trmmTotalMinRegionName!}</span>)
                </#if>
                <#if trmmTotalMax??>
                    ~
                    <span class="textSpan">${trmmTotalMax!}mm</span>(<span class="textSpan">${trmmTotalMaxRegionName!}</span>)
                </#if>

                </span>
                <span class="bgGrey">
                    易发虫害
                </span>
                <span class="bgTdForth">
                ${insectPests!}
                </span>
            </div>
        </div>

    </div>
    <div class="section2">

        <div class="secTitle">
            <span class="bgTitle">&nbsp;</span> <span>地温变化</span>
        </div>

        <div id="ttnEcharts"  class="echartsDiv"></div>
    </div>

    <div class="section3">

        <div class="secTitle">
            <span class="bgTitle">&nbsp;</span> <span>降雨变化</span>
        </div>

        <div id="trmmEcharts"  class="echartsDiv"></div>
    </div>

    <div class="section4">


        <div class="secTitle">
            <span class="bgTitle">&nbsp;</span> <span>病虫害防治措施</span>
        </div>

    <#if diseaseAllList?? && diseaseAllList?size &gt;0>
        <#list diseaseAllList as item>
            <div class="diseaseStyle">
                <div class="baseImg">
                    <img src=${item.diseaseImg!}>
                </div>
                <div class="baseFont">
                    <span class="diseaseName"> ${item.diseaseName!}</span>
                    <span class="borderStyleSolid">
                    ${item.diseaseMeasure!}
                    </span>
                </div>
            </div>
            <#if (item_index+1)%2==0>
                <div class="diseaseSection">
                </div>
            </#if>
        </#list>
    </#if>









    </div>
    <div style="clear:both;"></div>

    <div class="section5 myTextarea">
        <h2 >管理措施</h2>
        <div>当前阶段，应加强田间肥水管理</div>
    </div>
</div>
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
                    // title: {
                    //     text: '折线图堆叠'
                    // },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['地温°C','历史地温均值°C'],
                    },
                    grid: {show:'true',borderWidth:'0'},
                    toolbox: {
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    xAxis:
                            [
                                {
                                    type: 'category',
                                    splitLine:{show: false},//去除网格线
                                    // boundaryGap: false,
                                    data:${ttnDate!},
                                    splitArea:
                                    {
                                        show:true,
                                        areaStyle:
                                        {
                                            // color:[
                                            //     'rgba(123,19,19,0.3)',
                                            //     'rgba(100,100,100,0.3)'
                                            // ],
                                            shadowBlur:{
                                                shadowColor: 'rgba(100, 100, 100, 0.5)'

                                            },
                                        }
                                    }
                                }
                            ],
                    yAxis: {
                        type: 'value',
                        show:false,
                        min: ${minTtn!},
                        max: ${maxTtn!}
                    },
                    series: [
                        {
                            name:'地温°C',
                            // type:'line',
                            // stack: '总量',
                            type: 'line',
                            smooth: true,
                            itemStyle : { normal: {label : {show: true, position: 'bottom',textStyle:{color:'#000000'}},color:'#ebe800',lineStyle:{
                                color:'#ebe800'
                            }}},
                            label:{
                                show:true,
                            },
                            data:${ttnNow!}
                        },
                        {
                            name:'历史地温均值°C',
                            type:'line',
                            stack: '总量',
                            itemStyle : { normal: {label : {show: true, position: 'top',textStyle:{color:'#000000'}},color:'#00e4ff',lineStyle:{
                                color:'#00e4ff'
                            }}},
                            label:{
                                show:true,
                            },
                            data:${ttnHistory!}
                        }
                    ]
                };
                // 为echarts对象加载数据
                myChart.setOption(option);


                var myChartTrmm = ec.init(document.getElementById('trmmEcharts'));

                var  option = {
                    grid: {show:'true',borderWidth:'0'},
                    legend: {
                        data:['降雨量mm','历史雨量均值mm']
                    },
                    xAxis: [
                        {
                            type: 'category',
                            splitLine:{show: false},//去除网格线
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
                                        // 'rgba(123,19,19,0.3)',
                                        // 'rgba(100,100,100,0.3)'
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
                            max: ${maxTrmm!},
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
                            itemStyle : { normal: {color:'#46b9f7',label : {show: true, position: 'top',textStyle:{color:'#000000'}} }},
                            data:${trmmNow!}
                        },
                        {
                            name:'历史雨量均值mm',
                            type:'bar',
                            itemStyle : { normal: {color:'#00e787',label : {show: true, position: 'top',textStyle:{color:'#000000'}} }},
                            data:${trmmHistory!}
                        }
                    ]
                };



                // 为echarts对象加载数据
                myChartTrmm.setOption(option);
            }
    );
</script>
</body>
</html>