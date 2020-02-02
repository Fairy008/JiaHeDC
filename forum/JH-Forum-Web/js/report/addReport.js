require(["jquery","moment","BaseAjax","BaseMap","commons",'daterangepicker','jqueryUi','RegionModule','echarts','BaseChart',"PopWin","formVerfication","urlConfig","enums","jsgrid","custom_settings"],
    function($,moment,BaseAjax,BaseMap,commons,daterangepicker,jqueryUi,RegionModule,echarts,baseChart,PopWin,formVerfication,urlConfig,enums,jsgrid,custom_settings) {

    var processDialog = $("#process");
    var mapClazz ,ue;
    var txtRegion = document.getElementById("txtRegion");

    var popup;//气泡

    var maxLabelNum = 5,articleType = 11001,toBeSave = 12001,toBePublish = 12002;//标签最多不可超过5个;
    var articleLabelArr = [];//被选中的标签
    var articleLabel = "";//标签
        var articleId;

    var queryParam = {}; //用于存放全局的查询对象

    var init = function(){

        rightSideFun(); //右侧初始化方法

        mapClazz = new BaseMap.mapClazz();
        //初始化地图
        mapClazz.initMap($("#txtRegion").attr("regionid"))


        //初始化遥感数据与调研数据点击事件
        initTabLogoClick();

        initLayout();

        //初始化图表区域的展开于收拢
        toggleChartFun();

        //初始化加载数据
        loadDataEvent();
    }


    /**
     *  图表区域展开于收拢
     *  @version<1> 2019-04-11 lcw:Created.
     */
    var toggleChartFun = function(){
        $("#botBtn").off("click").on("click", function(){
            if($(this).hasClass("up")){ //收拢状态需要展开
                $("#divMap").height($(".leftData").height() - 300);
                $("#chartList ul").show();
                $(this).removeClass("up")
            }else{
                $("#chartList ul").hide();
                $("#divMap").height($(".leftData").height());
                $(this).addClass("up")
            }
             mapClazz.resize();
        })
    }

    /**
     * 初始化logo区域的点击事件
     * 1.logo点击，跳转至首页
     * 2.遥感数据和调研数据切换的选中效果
     * 3.遥感数据和调研数据切换的数据清空与加载
     * @version<1> 2019-04-09 lcw :Created.
     */
    var initTabLogoClick = function(){

        $("#tabLogo .logo").on("click",function(){
            window.location.href = "index.html"
        })

        $("#tabLogo .tab_left").bind("click", function(){
            $(this).siblings().removeClass("active");
            $(this).addClass("active");


            $(".leftData .botContent").hide();

            if($("#chartList").css("display") == "block"){
                $("#chartList,#chartList ul").hide();
                $("#divMap").height($(".leftData").height())
            }

            if($(this).attr("data-id") == forum_map_data.yg_data){
                mapClazz.removeLayers(); //移除图层
                mapClazz.removeCollectionLayers();
                closeBox();
                $("#listGrid").jsGrid({
                    data:[]
                });
                $("#botGrid").jsGrid("reset");
                //隐藏标注
                $("#popup").hide();

            }else{
                //显示标注
                $("#popup").show();
                mapClazz.removeCollectionLayers();
                mapClazz.removeLayers(); //移除图层
                $("#legendTitle").closest("div").remove();//移除图例
            }


            mapClazz.resize();

            loadDataEvent(); //根据查询条件加载数据
        })
    }

    /**
     * 整体布局，随着窗体的变化发生变化
     */
    var initLayout = function(){
        $(window).resize(function(){
            if($("#chartList ul").css("display") == "none"){
                $("#divMap").height($(".leftData").height() )
            }else{
                $("#divMap").height($(".leftData").height()  - 300)
            }
        })
    }



    /**
     *右侧看数据与写报告对应的方法加载
     * @version<1> 2019-04-09 lcw :Created.
     */
    var rightSideFun = function(){

        //看数据、写报告选项卡切换
        tabTitleSelect();

        // 给区域下拉框绑定点击事件
        $('#txtRegion').bind('click',function () {
            changeRegionEvent();
        })
        loadDateRangePicker()//加载日期控件

        initUEditor();

        initGrid()

        //加载热门标签
        initLabelList();

        $("#hotTagComtent").bind('click',function () {
            $(".myLabel").toggle(100);
        });
        //鼠标移出热门标签弹框，则隐藏弹框
        $(".myLabel").bind('mouseleave',function () {
            $(".myLabel").hide();
        })


        $(".changeIco").bind('click',function () {
            initLabelList();
        });

        //保存
        $("#addReport").bind('click',function () {
            addSaveFun(toBeSave);
        });
        //发布
        $("#publishReport").bind('click',function () {
            addSaveFun(toBePublish);
        });

        $("#titleComtent").on("focus", function () {
            $("#msgInfo").text("");
            $(this).removeClass("formInputHighlight")
        });

        //加载所有标签
        findLabelList();

        //编辑则回显值
        articleId=GetQueryString("articleId");
        if(articleId!=null&&articleId!=''){
            editFun(articleId);
        }

        ue.addListener('focus', function(e){
            $("#msgInfo").text("");
            $('#editor').removeClass('formInputHighlight');

        })

        setTimeout(function(){
            processDialog.dialog("close");
        },4000) //加载tiff图层

    }

    /**
     * 获取url中的参数
     * @param 参数名称
     * @return 参数值
     * @version <1> 2019/3/14 lijie:Created.
     */
    var GetQueryString = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI(r[2]);
        return null;
    };

    var editFun = function(articleId){


        $("#readData").removeClass("active")
        $("#dataContent").hide();
        $("#writeReport").addClass("active");
        $("#reportContent").show();

        //获取报告详情
        var article=articleDetail(articleId);

        var article={};
        var ajax = new BaseAjax();
        ajax.opts.url = FORUM_ARTICLE.findArticleInfoByAccountId_url+"?articleId="+articleId;
        ajax.opts.contentType = "application/json";
        ajax.opts.async = false;
        ajax.opts.type="GET";
        ajax.opts.successFun = function(result){
            if(result.flag){
                var article = result.data;
                //回显内容
                ue.ready(function(){
                    ue.setContent(article.articleContent);
                })
                //回显标题
                $("#titleComtent").val(article.articleTitle);
                //回显标签
                articleLabelShow(article);
                initLabelList();

            }
            if (!article) {//如果为空，表示不是自己的帖子
                commons.tipMessageShow("不是自己的报告", 2000, false);
            }
        };
        ajax.opts.errorFun = function(){
            console.info("异常");
        };
        ajax.run();

    }


        //编辑时回显标签
        var articleLabelShow = function (article) {
            articleLabel = article.articleLabel;
            if (articleLabel) {
                var labelArr = articleLabel.split(",");
                if (labelArr && labelArr.length > 0) {
                    $("#hotTagComtent").html("");
                    $.each(labelArr, function (index, obj) {
                        $.each(labelNameAndColorArr, function (i, o) {
                            if (o.name == obj) {
                                var oneSpan = "<span style='margin-left:5px;text-align:center;vertical-align:middle;background-color:"+ o.color +"'>" + o.name +"</span>";
                                $("#hotTagComtent").append(oneSpan);
                                articleLabelArr.push([o.color, o.name]);
                                return false;
                            }
                        });
                    });
                }
            }
        };


        /**
         * 获取数据库中所有的标签列表
         * @version <1> 2019/3/15 13:29 zhangshen:Created.
         */
        var labelNameAndColorArr = [];
        var findLabelList = function () {
            var forumLabel = {dataStatus: '1', delFlag: '1'};
            var ajax = new BaseAjax();
            ajax.opts.data = JSON.stringify(forumLabel);
            ajax.opts.async = false;
            ajax.opts.url = FORUM_LABEL.nolog_findAllLabel_url;
            ajax.opts.successFun = function(result){
                var str = "";
                if (result) {
                    var list = result.data;
                    $.each(list,function(i,o){
                        labelNameAndColorArr.push({"name": o.labelName, "color": o.labelColor});
                    });
                }
            };
            ajax.opts.failureFun = function(){};
            ajax.run();
        };

        /**
         * 根据帖子ID获取详情
         * @version <1> 2019-03-22 lijie : created.
         */
        var articleDetail = function(articleId){
            var article={};
            var ajax = new BaseAjax();
            ajax.opts.url = FORUM_ARTICLE.findArticleInfoByAccountId_url+"?articleId="+articleId;
            ajax.opts.contentType = "application/json";
            ajax.opts.async = false;
            ajax.opts.type="GET";
            ajax.opts.successFun = function(result){
                if(result.flag){
                    article = result.data;
                }
                if (!article) {//如果为空，表示不是自己的帖子
                    commons.tipMessageShow("不是自己的报告", 2000, false);
                }
            };
            ajax.opts.errorFun = function(){
                console.info("异常");
            };
            ajax.run();
            return article;
        };


    var flag = 1;
    var addSaveFun =function(status){
        checkCookies();
        var bool = formVerf();
        if(!bool){
            return false;
        }
        //首先验证标签是否超过5个
        if(articleLabelArr.length>maxLabelNum){
            PopWin.showMessageWin("标签不可超过"+maxLabelNum+"个");
            return false;
        }

        if(flag == 0){
            return false;
        }

        flag = 0;

        <!--获取标签结束-->
        var article = {};
        var reportTitle =  $("#titleComtent").val();//标题
        article.articleTitle = reportTitle
        articleSummary = UE.getEditor('editor').getContentTxt();//摘要
        var url = FORUM_ARTICLE.addArticle_url;
        if(articleId != null && articleId != ''){
            url = FORUM_ARTICLE.editArticle_url
            article.articleId=articleId;
        }
        if (null != articleSummary && '' != articleSummary) articleSummary = articleSummary.substring(0,200);
        article.articleSummary = articleSummary;
        article.articleType = articleType;//报告
        article.articleStatus = status;//待提交
        article.articleContent = UE.getEditor('editor').getContent();
        article.articleLabel = articleLabel;

        var checkAjax = new BaseAjax();
        checkAjax.opts.url = url;
        checkAjax.opts.async = false; //同步
        checkAjax.opts.data = JSON.stringify(article);
        checkAjax.opts.successFun = function(result){
            if(result.flag){
                commons.tipMessageShow("报告已保存，请前往【我的主页】查看详情", 2000, true);
                flag = 1;
                setTimeout(function(){
                    $("#titleComtent").val("")
                    $("#hotTagComtent").html("").html("此处添加热门标签");
                    $(".myLabel").hide();
                    $(".errorTip").html("")


                    articleLabelArr = []
                    initLabelList()

                    UE.getEditor('editor').setContent("");
                },1500)

            }else{
                $("#failureMsg").html("报告保存失败");
                $(".failure").show();
            }
        },
        checkAjax.opts.failureFun = function(){
            flag = 1;
        }

        checkAjax.run();
    };



    var formVerf = function(){
        var msgInfo = $('#msgInfo');

        if (formVerfication.checkInputLengthAndIsNull(1, 50, $('#titleComtent'),msgInfo, '标题长度不能超过50个字符', '标题不能为空')) {

            return false;
        }

        if(!UE.getEditor('editor').hasContents()){
            clearErrorInfo($('#editor'),msgInfo);
            $('#editor').addClass('formInputHighlight');
            msgInfo.append('<div class="formErrorInfo"><span>内容不能为空</span></div>');
            return false;
        }
        return true;
    }

        //清除错误信息
        var clearErrorInfo = function(obj,showTargetObj){
            if(showTargetObj.find('.formErrorInfo').length != 0){
                obj.parents('body').find('.formInputHighlight').removeClass('formInputHighlight');
                showTargetObj.find('.formErrorInfo').remove();
            }
        };

    /**
     * 初始化加载热门标签列表
     * @version <1> 2019/3/7 13:24 zhangshen:Created.
     */
    var initLabelList = function () {
        var forumLabel = {dataStatus: '1', delFlag: '1'};
        var ajax = new BaseAjax();
        ajax.opts.data = JSON.stringify(forumLabel);
        ajax.opts.async = false;
        ajax.opts.url = FORUM_LABEL.nolog_findRandomLabelList_url;
        ajax.opts.successFun = function(result){
            var str = "";
            if (result) {
                var list = result.data;
                var articleLabel = getLabelStr();
                var arr = [];
                if (articleLabel) {
                    arr = articleLabel.split(",");
                }
                $.each(list,function(i,o){
                    if (arr && $.inArray(o.labelName, arr) != -1) {
                        str += '<a labelColor="'+ o.labelColor +'" class="ons">' + o.labelName + '</a>';
                    } else {
                        str += '<a labelColor="'+ o.labelColor +'">' + o.labelName + '</a>';
                    }

                });
            }
            $(".hotTag .tags").html("").html(str);
        };
        ajax.opts.failureFun = function(){};
        ajax.run();

        if ($(".hotTag .tags").height() > 150) {
            $(".hotTag .tags").css({"height": "150px", "overflow": "hidden"});
            $(".hotTag .lookMore").show();
        } else {
            $(".hotTag .tags").css({"height": "auto", "overflow": "initial"});
            $(".hotTag .lookMore").hide();
        }

        //给选中的标签加上样式
        labelCss();
    };



    /**
     * 获取标签字符串
     * @version <1> 2019/3/15 13:09 zhangshen:Created.
     */
    var getLabelStr = function () {
        var labelStr = "";
        $.each($("#hotTagComtent").find("span"), function(i, o){
            if ($("#hotTagComtent").find("span").length == (i+1)) {
                labelStr += o.innerText;
            } else {
                labelStr += o.innerText + ",";
            }
        });
        return labelStr;
    };



    //给选中的标签加上样式
    var labelCss = function () {
        $(".hotTag .tags a").on('click',function(){
            articleLabel = "";
            // $(this).toggleClass('on');
            if ($(this).hasClass('ons')) {
                $(this).removeClass('ons');
                $(".errorTip").html("")
                //找到这个元素
                //删除一个
                for(var i=0;i<articleLabelArr.length;i++){
                    if(articleLabelArr[i][1]==$(this).text()){
                        articleLabelArr.splice(i,1);
                    }
                };

                /*  var index = articleLabelArr.indexOf($(this).text());
                 if (index > -1) {
                 //删除元素
                 articleLabelArr.splice(index, 1);
                 }*/
            } else {
                if(articleLabelArr.length==maxLabelNum){
                    $(".errorTip").html("标签不可超过"+maxLabelNum+"个")
                }else{
                    articleLabelArr.push([$(this).attr("labelColor"),$(this).text()]);
                    $(this).addClass('ons');
                }
            }

            //获取数组中已有元素 拼接并显示在input输入框中
            $("#hotTagComtent").html("此处添加热门标签");
            if(articleLabelArr.length>0){
                $("#hotTagComtent").html("");
            }
            for(var i=0;i<articleLabelArr.length;i++){
                if(i!=articleLabelArr.length-1){
                    articleLabel += articleLabelArr[i][1]+",";
                }else{//最后一个不加"，"
                    articleLabel += articleLabelArr[i][1];
                }
                var oneSpan = "<span style=' line-height: 30px;margin-left:5px;text-align:center;vertical-align:middle;background-color:"+articleLabelArr[i][0]+"'>" +articleLabelArr[i][1] +"</span>";
                $("#hotTagComtent").append(oneSpan)
            };
            //给标签文本框赋值
            //$("#hotTagComtent").val(labelContent);
        });
    }



    var addLabel=function(){
        var len=6;//控制最多显示几个
        var labelTr = $('#firstTd');
        var newtd = $("<td ><input type='text'  name='articleLabel' class='inputType'  /><img src='images/public/reduce.png' class='removeTd' style='margin-top: 2px;' ></td>");

        labelTr.before(newtd);

        //判断 如果已经有三行 则增加按钮置灰
        if($(".paramsTr").find("td").size()==len){//这里获取每一行有多少个标签只能以$(".paramsTr").find("td").size()的方式，不能在前面以变量的方式定义，否则会出现清空所有标签后再添加会一次添加好几个的情况
            //增加按钮置灰 并移除增加行事件
            $("#firstTd").find("img").unbind("click",addLabel);
            $("#firstTd").find("img").attr("src","images/public/addGRAY.png");
        }

        $(".removeTd").click(function() {//点击动态移除一行
            //判断 如果当前为5行 则增加按钮置蓝色 并移除一行
            if($(".paramsTr").find("td").size()==len){//这里获取每一行有多少个标签只能以$(".paramsTr").find("td").size()的方式，不能在前面以变量的方式定义，否则会出现清空所有标签后再添加会一次添加好几个的情况
                //增加按钮变蓝 并绑定增加行事件
                $("#firstTd").find("img").bind("click",addLabel);
                $("#firstTd").find("img").attr("src","images/public/addrow.png");
            }
            $(this).parent().remove();
        });
    }


    var initGrid = function(){
        // var fields = Trmm.Trmm_Table_Field;
        var fields=[];
        $("#listGrid").jsGrid({
            width: "100%",
            height:"100%",
            sorting: true,
            autoload:true,
            paging: true,
            pageSize:15,
            selecting:true,
            loadMessage: "加载中...",
            data: [],
            fields: fields,
            noDataContent:'没有查询到数据',

        });
    }

        /**
     * 初始化写报告表单样式
     */
    var initUEditor = function(){
        var token = commons.getAccessToken();
        UE.delEditor('editor');
        ue =  UE.getEditor('editor',{"serverUrl":FORUM_COMMENT.upFile+"?AccessToken=" + token,autoFloatEnabled:false});
        $("#editor").height($(window).height() - 390)
    }


        //检查用户cookies是否过期
        var checkCookies = function () {
            var accessToken = commons.getAccessToken();
            if(accessToken) {
            }
            else{
                window.location.href = LOGIN_CONFIG.index_page_url;//刷新页面;
            }
        }

    /**
     * 区域选择：
     * 1.按级别加载区域列表
     * 2.选中区域后，在地图上绘制选中的部分
     * 3.加载数据
     */
    var changeRegionEvent = function(){
        checkCookies();
        // var param = getParam();
        var opts = {url:REGION_CONFIG.findRegionsByPid_URL,closeFun:function(){
                var param = getParam()  //获取参数
                mapClazz.loadVector(param.regionId) //将中心点移动至所选的区域
                loadDataEvent() //加载数据

            }};
        var regionSelector = new RegionModule.RegionSelector("txtRegion",opts);
        regionSelector.show();

    };


    /**
     * 双日历插件
     * @param select对象
     */
    var loadDateRangePicker = function(){
        stay.init();
    };

    //获取本年度的一月一日
    var getFirstDayInYear = function(){
        var date = new Date();

        var year = date.getFullYear();
        return year + "-01-01";

    }

        /**
         *获取本月最后一天
         * @param dataMonth : 2019-04
         */
    var getLastDayInMonth = function(dataMonth){

        var day = 31;
        var month = dataMonth.split("-")[1];
        switch (month){
            case 2:
                day = 28;
                break;
            case 4:
                day = 30;
                break;
            case 6:
                day = 30;
                break;
            case 9:
                day = 30;
                break;
            case 11:
                day = 30;
                break;
            default:
                day = 31;
                break;
        }

        return dataMonth + "-" + day;

    }
    

    //格式化日期
    var formatDate = function(date){
        var year = date.getFullYear();
        var month = date.getMonth() < 9 ? "0" + (date.getMonth()+1) : (date.getMonth()+1)
        var date = date.getDate() < 10 ? "0" + (date.getDate()) : date.getDate();

        return year + "-" + month + "-" + date;
    }


    /**
     * 双日历控件
     * @version<1> 2019-03-19 cxw:created
     */
    var stay = {
        start:$('#startDate'),
        end:$('#endDate'),
        today:(getFirstDayInYear()),
        init:function(){
            stay.inputVal();
            stay.endFun();
            stay.startFun();
        },
        startFun:function(){
            stay.start.datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat : 'yy-mm-dd',
                dayNamesMin : ['日','一','二','三','四','五','六'],
                monthNames : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                altFormat : 'yy-mm-dd',
                yearSuffix:'年',
                showMonthAfterYear:true,
                // appendText : '明天',
                firstDay : 1,
                showOtherMonths:true,
                // minDate : 0,
                maxDate:'+0D',
                onSelect:function(dateText,inst){
                    stay.end.datepicker('option', 'minDate', new Date(moment(stay.start.val()).add('days', 0)));

                    //跨度为1年
                    var oneYearLater = new Date(moment(stay.start.val()).add("days",365));
                    var endDate = new Date(moment(stay.end.val()));
                    if(endDate > oneYearLater){ //超出一年
                        // stay.end.datepicker('option', 'minDate', oneYearLater);
                        $('#endDate').val(formatDate(oneYearLater));

                    }
                    loadDataEvent();
                }

            });
        },
        endFun:function(){
            stay.end.datepicker('refresh');
            stay.end.datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat : 'yy-mm-dd',
                dayNamesMin : ['日','一','二','三','四','五','六'],
                monthNames : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                altFormat : 'yy-mm-dd',
                yearSuffix:'年',
                showMonthAfterYear:true,
                // appendText : '后天',
                firstDay : 1,
                showOtherMonths:true,
                minDate : new Date(moment(stay.start.val()).add('days', 0)),
                maxDate:'+0D',
                onSelect:function(dateText,inst){


                    //跨度为1年
                    var oneYearBefore = new Date(moment(stay.end.val()).add("days",-365));
                    var startDate = new Date(moment(stay.start.val()));
                    if(startDate < oneYearBefore){ //超出一年
                        // stay.start.datepicker('option', 'minDate', oneYearBefore);
                        $('#startDate').val(formatDate(oneYearBefore));

                    }


                    loadDataEvent();
                }
            });
        },
        transformStr:function(day,strDay){
            switch (day){
                case 1: strDay  = '星期一'; break;
                case 2: strDay  = '星期二'; break;
                case 3: strDay  = '星期三'; break;
                case 4: strDay  = '星期四'; break;
                case 5: strDay  = '星期五'; break;
                case 6: strDay  = '星期六'; break;
                case 0: strDay  = '星期日'; break;
            }
            return strDay;
        },
        compare:function(obj){
            var strDay = '今天';
            var myDate = new Date(stay.today.getFullYear(),stay.today.getMonth(),stay.today.getDate());
            var day = (obj.datepicker('getDate') - myDate)/(24*60*60*1000);
            strDay = stay.transformStr(obj.datepicker('getDate').getDay(),30)
            return strDay;
        },
        inputVal:function(){
            // stay.inputTimes(stay.start,0);
            // stay.inputTimes(stay.end,0);
            stay.start.val(formatDate(new Date(moment(stay.today))));
            stay.end.val(formatDate(new Date()))
        },
        inputTimes:function(obj,day){
            var m = new Date(moment(stay.today).add('days', day));
            obj.val(m.getFullYear() + "-" + stay.addZero((m.getMonth()+1)) + "-" + stay.addZero(m.getDate()));
        },
        addZero:function(num){
            num < 10 ? num = "0" + num : num ;
            return num;
        }
    }


    /**
     * 看数据与写报告选项卡切换
     * 1.选中样式切换
     * 2.页面内容切换
     * 3.加载对应的数据
     * @version<1> 2019-04-09 lcw :Created.
     */
    var tabTitleSelect = function(){
        $(".tab_title span").bind("click", function(){

            $(this).siblings().removeClass("active");
            $(this).addClass("active")

            //加载对应的tabContent内容

            var dataId = $(this).attr("data-id")

            //看数据
            if(dataId == "dataContent"){
                $("#dataContent").show();
                $("#reportContent").hide();
            }else if(dataId == "reportContent"){ //写报告
                $("#dataContent").hide();
                $("#reportContent").show();
            }

            //待完善 2019-04-09

        })

    }


    //进度图
    var showProcess=function(){
        processDialog.dialog({
            autoOpen: false,
            height: 460,
            width: 460,
            modal: true ,
            closeOnEscape: false,
            open: function(event, ui) {
                processDialog.siblings(".ui-dialog-titlebar").hide();
                processDialog.parent(".ui-dialog").css({"opacity":1,"background":"rgba(0,0,0,0)"});
            },
        });
        processDialog.dialog("open");
    }



    /**
     * 时间轴：当时间到第一个时右移按钮失效和最后一个时左移按钮失效
     * @version<1> 2019-03-19 cxw:created
     */
    var timeSliderSet = {
        /**
         * 在时间轴上显示时间点
         *  将时间轴分为<=8等份(注意:如果是3个时间点,则应该是4等分),然后顺序将时间点显示在时间轴上,超出部分隐藏
         * @param _data : 时间点集合
         * @version<1> 2018-10-16 lcw :Created.
         */
        showMultiDotTime : function(_data,dsCode){
            if( _data == null || _data.length == 0){
                $(".botContent").css("display","none");
            } else{
                $(".botContent").css("display","block");
                $(".botContent .dot").remove();
                var _width = $(".botContent .sHr").width(); //获取时间轴横线的长度
                var _num = _data.length;

                var perWidth = _width / (_num > 10 ? 10 : _num); //每个时间点

                var _span = "";
                for(var i = 0 ; i <_num;i++){
                    var _left = (0 + perWidth * (i+1) );
                    _span += "<div class='dot'><i></i><span class='dotSan' index='"+ (i+1) +"'></span><span class='dotValue' style='left:"+ (_left - 25) +"px;' >" + _data[i] + "</span></div>";
                }
                $('.botContent .timeDot').remove();
                $(".botContent").append('<div class="timeDot"><div class="hiddenDiv">'+_span+'</div></div>');
                $('.dot').width(perWidth);
                $(".botContent .dotSan:last").addClass("on");
                var timeSpot = $('.dot');
                /**小于10的时间点左右的按钮灰置 */
                if($('.dot').length<=10){
                    $('.sNext').addClass('noclick');
                }else{
                    $('.sNext').removeClass('noclick');
                    $('.hiddenDiv').css('left',-(_data.length-10)*timeSpot.width());
                }
                $('.sPrev').addClass('noclick');
                var dataTime = $(".dotSan.on").next(".dotValue").text();
                queryParam.dataTime = dataTime
                // loadData.loadAllData(dataTime);  //加载完时间轴后,加载数据

                loadChart(queryParam); //加载chart

                $('.dotSan').on('click',function(){
                    var dotDate = $(this).next(".dotValue").text();
                    queryParam.dataTime = dotDate
                    $(this).addClass('on').parents('.dot').siblings().children('.dotSan').removeClass('on');
                    // loadData.loadAllData(dotDate);

                    loadChart(queryParam); //加载chart

                    $('.dotSan.on').parent('.dot').find('i').show();
                    $('.dotSan.on').addClass('forbidden');
                    var times = 150; // 150毫秒
                    countTime = setInterval(function() {
                        times = --times < 0 ? 0 : times;
                        var hm = Math.floor(times % 100).toString();
                        if(hm.length <= 1) {
                            hm = "0" + hm;
                        }
                        if(times == 0) {
                            $('.dotSan').parent('.dot').find('i').hide();
                            $('.dotSan.on').removeClass('forbidden');
                            clearInterval(countTime);
                        }
                    }, 10);
                });

                var _index = $('.dotSan.on').parents('.dot').index();
                var timeDiv = $('.hiddenDiv');
                var timeSpot = $('.dot');
                if($('.dot').length<=10){
                    $('.sPrev').addClass('noclick');
                }else{
                    $('.sPrev').removeClass('noclick');
                }

                var showDotNum = Math.round($('.timeDot').width()/timeSpot.width());
                //左移
                $('.sPrev').off('click').on('click',function(){
                    if(parseInt(timeDiv.css('left'))>=-(_data.length-10-1)*timeSpot.width()){
                        timeDiv.stop(false,true).animate({'left': parseFloat(timeDiv.css('left'))-timeSpot.width()+'px'},200,function(){
                            if(parseFloat(timeDiv.css('left'))>=-(_data.length-10-1)*timeSpot.width()){
                                $('.sPrev').removeClass('noclick');
                            }else{
                                $('.sPrev').addClass('noclick');
                            }
                            $('.sNext').removeClass('noclick');
                        });
                        if(_index>=(-Math.round(parseFloat(timeDiv.css('left'))/timeSpot.width())+9)||_index<=-Math.round(parseFloat(timeDiv.css('left'))/timeSpot.width())){
                            $('.dotSan').removeClass('on');
                            $('.dot').eq(_index+1).find('.dotSan').addClass('on');
                            _index = _index+1;
                            var dataTime = $(".dotSan.on").next(".dotValue").text();
                            queryParam.dataTime = dataTime
                            // loadData.loadAllData(dataTime);  //加载完时间轴后,加载数据

                            loadChart(queryParam); //加载chart
                        }
                    }else{
                        $(this).addClass('noclick');
                    }
                });
                //右移
                $('.sNext').off('click').on('click',function(){
                    if(parseInt(timeDiv.css('left'))<0){
                        timeDiv.stop(false,true).animate({'left': parseFloat(timeDiv.css('left'))+timeSpot.width()+'px'},200,function(){
                            if(parseFloat(timeDiv.css('left'))<0&&_index>0){
                                $('.sNext').removeClass('noclick');
                            }else{
                                $('.sNext').addClass('noclick');
                            }
                            $('.sPrev').removeClass('noclick');
                        });
                        if(_index>=(-Math.round(parseFloat(timeDiv.css('left'))/timeSpot.width())+9)||_index<=-Math.round(parseFloat(timeDiv.css('left'))/timeSpot.width())){
                            $('.dotSan').removeClass('on');
                            $('.dot').eq(_index-1).find('.dotSan').addClass('on');
                            _index = _index-1;
                            var dataTime = $(".dotSan.on").next(".dotValue").text();
                            queryParam.dataTime = dataTime
                            // loadData.loadAllData(dataTime);  //加载完时间轴后,加载数据

                            loadChart(queryParam); //加载chart
                        }
                    }else{
                        $(this).addClass('noclick');
                    }
                });
            }
        }
    };


    /**
     * 加载chart数据
     * 1.加载表格
     * 2.加载数据
     * @param param
     */
    var loadChart = function(param){
        //样式
        $("#divMap").height($(".leftData").height() - 300);
        $("#chartList,#chartList ul").show();

        $("#botBtn").removeClass("up")

        mapClazz.resize();


        initEchart();
        //加载数据
        loadData.loadAllData();

    }


    var myChartLi1,myChartLi2,myChartLi3,myChartLi4,myChartLi5 ;
    /**
     *
     */
    var initEchart = function(){

        $("#chartList ul li").css("display","none");

        if(queryParam.dsId === DS_Trmm.id || queryParam.dsId === DS_T.id || queryParam.dsId === DS_Weather.id){
            $("#chartLi3,#chartLi5,#chartList .botTableBox").parent("li").show();

            $('#botTable').parents('li').css('border-bottom','1px solid #a7a7a7').show();
            if(queryParam.dsId===DS_Weather.id){
                $('.tempRow').css('display','inline-block');
            }

            document.getElementById('chartLi3').style.width = $("#chartList  ul li").width() + "px"
            myChartLi3 = baseChart.echartsSet.echartsBrokenLineUI(document.getElementById('chartLi3'));



        }else if(queryParam.dsId === DS_Distribution.id || queryParam.dsId === DS_Yield.id){
            $("#chartLi1,#chartLi2,#chartList .botTableBox").parent("li").show();


            document.getElementById('chartLi1').style.width = $("#chartList  ul li").width() + "px"
            document.getElementById('chartLi2').style.width = $("#chartList  ul li").width() + "px"
            myChartLi1 = baseChart.echartsSet.echartsPieUI(document.getElementById('chartLi1'));
            myChartLi2 = baseChart.echartsSet.echartsCategoryUI(document.getElementById('chartLi2'));


            $('#botTable').parents('li').css('border',0);//去掉表格下划线


        }else if(queryParam.dsId === DS_Growth.id){
            $("#chartLi1,#chartLi4,#chartList .botTableBox").parent("li").show();

            document.getElementById('chartLi1').style.width = $("#chartList  ul li").width() + "px"
            document.getElementById('chartLi4').style.width = $("#chartList  ul li").width() + "px"

            myChartLi1 = baseChart.echartsSet.echartsPieUI(document.getElementById('chartLi1'));
            myChartLi4 = baseChart.echartsSet.echartsStackUI(document.getElementById('chartLi4'));
            $('#botTable').parents('li').css('border',0);//去掉表格下划线



        }

        $('.botTableBox').parents('li').show();

        var fields=[];
        $("#botGrid").jsGrid({
            width: "100%",
            // height:"100%",
            height:250,
            sorting: true,
            autoload:true,
            paging: false,
            pageSize:1000,
            selecting:true,
            data: [],
            fields: fields,
            noDataContent:'没有查询到数据'

        });


        $(".botTableBox h2").html('');
        var param = queryParam;
        var dsInfo = getDsInfo(param.dsCode);
        var lastTwoYear = parseInt(param.dataTime.substring(0,4))-2;//前两年
        var lastYear = parseInt(param.dataTime.substring(0,4))-1;//前一年
        var currentYear = parseInt(param.dataTime.substring(0,4));//当年

        var selectAreaName = param.chinaName.indexOf("|")>=0?$.trim(param.chinaName.substring(param.chinaName.lastIndexOf('|') + 1, param.chinaName.length)):param.chinaName;
        var fields = []

        if(dsInfo.id===DS_Trmm.id||dsInfo.id===DS_T.id||dsInfo.id===DS_Weather.id){
            $(".botTableBox h2").attr('title',/*param.dataTime+'至'+*/param.dataTime+selectAreaName+dsInfo.threeTitle).html(/*param.dataTime+'至'+*/param.dataTime+selectAreaName+dsInfo.threeTitle);

            if(dsInfo.id === DS_Weather.id){
                fields =  [
                    { name: "one",title:'区域', type: "text" },
                    { name: "two",title:'日期', type: "text" },
                    { name: "three",title:'最高温('+dsInfo.unit+')',type: "number" },
                    { name: "four",title:'最低温('+dsInfo.unit+')',type: "number" },
                ];
            }else{
                fields =  [
                    { name: "one",title:'区域', type: "text" },
                    { name: "two",title:'日期', type: "text",visible:false },
                    { name: "three",title:param.dsName+ '(' +dsInfo.unit+')',type: "number" },
                    { name: "four",title:'上月同比('+dsInfo.unit+')',type: "number" },
                    { name: "five",title:'增长率',type: "text" }
                ];
            }
        }else if(dsInfo.id===DS_Growth.id){
            fields =  [
                { name: "one",title:'等级', type: "text" },
                { name: "two",title:'面积('+dsInfo.unit+')',type: "number" },
            ];

            $(".botTableBox h2").attr('title',param.dataTime+selectAreaName+param.cropName+'长势统计数据').html(param.dataTime+selectAreaName+param.cropName+'长势统计数据');
        }else if(dsInfo.id===DS_Yield.id){
            fields =  [
                { name: "one",title:'区域', type: "text" },
                { name: "two",title:'产量预估('+dsInfo.unit+')',type: "number" },
                { name: "three",title:'去年同期('+dsInfo.unit+')',type: "number" },
                { name: "four",title:'增长率',type: "text" },
            ];

            $(".botTableBox h2").attr('title',param.dataTime+selectAreaName+param.cropName+'两年估产统计数据').html(param.dataTime+selectAreaName+param.cropName+'两年估产统计数据');
        }else{

            fields =  [
                { name: "one",title:"区域", type: "text"},
                { name: "two",title:lastTwoYear+'年('+dsInfo.unit+')',type: "text" },
                { name: "three",title:lastYear+'年('+dsInfo.unit+')', type: "number"},
                { name: "four",title:currentYear+'年('+dsInfo.unit+')',type: "number"}
            ];

            $(".botTableBox h2").attr('title',param.dataTime+selectAreaName+param.cropName+dsInfo.threeTitle+'数据').html(param.dataTime+selectAreaName+param.cropName+dsInfo.threeTitle+'数据');
        }

        $("#botGrid").jsGrid({
            fields:fields
        })

    }


    /*************************************************************************************************************************************
     * *************************************************跟数据查询相关的方法区域************************************************************
     * ***********************************************************************************************************************************
     */
    //加载数据入口
    var loadDataEvent = function(){
        checkCookies();
        var param = getParam(); //获取参数
        loadGrid(param);
    }


    /**
     * 加载数据
     * 1.遥感数据
     * 2.调研数据
     * @param param
     * @version<1> 2019-04-10 lcw :Created.
     */
    var  loadGrid = function(param) {

        showProcess();

        var url = null;

        var fields = [{
            name: 'regionId', title: '区域Id ', type: 'text',visible:false
        },{
            name: 'regionName', title: '区域', type: 'text',visible:false
        },{
            name: 'dataMonth', title: '气象数据所属年月', type: 'text',visible:false
        },{
            name: 'dataSetId', title: 'dataSetId', type: 'text',visible:false
        },{
            name: 'dataSetCode', title: 'dataSetCode', type: 'text',visible:false
        },{
            name: 'dataSetName', title: 'dataSetName', type: 'text',visible:false
        },{
            name: 'resolution', title: 'resolution', type: 'text',visible:false
        },{
            name: 'resolutionName', title: 'resolutionName', type: 'text',visible:false
        },{
            name: 'cropId', title: 'cropId', type: 'text',visible:false
        },{
            name: 'cropName', title: 'cropName', type: 'text',visible:false
        },{
            name:'taskName',title:'遥感数据列表',type:'text',itemTemplate:function(value, item){
                var str = ""
                //降雨
                if(item.dataSetCode === DS_Trmm.name || item.dataSetCode === DS_T.name || item.dataSetCode === DS_Weather.name){

                    str = item.regionName + item.dataMonth  + item.dataSetName + "情况"
                }else{
                    str = item.regionName + item.cropName + item.dataSetName + "情况"
                }

                if(item.resolutionName != null){
                    str += "<span style='font-size:6px;'>【数据精度:" + item.resolutionName + "】</span>";
                }
                return str;
            }
        }]

        var ajax = new BaseAjax();

        if (param.dataFlag == forum_map_data.yg_data) {
            url = REPORT_CONFIG.dataset_url;

            ajax.opts.data = JSON.stringify(param);
            ajax.opts.type = "POST";

        } else if (param.dataFlag == forum_map_data.dy_data) {
            // url = REPORT_CONFIG.cltTask_url;

            if(custom_settings.isNewTask == 1){
                url = COLLECTION_TASK_NEW.my_findTaskList_url;
                ajax.opts.data = param;
                ajax.opts.type="GET";
            }else{
                url = REPORT_CONFIG.collectionTask_url;
                ajax.opts.data = param;
                ajax.opts.type="GET";
            }

            
            fields = [{
                name:'taskId', title:'调研任务ID',type:'text',visible:false
            },{
                name:'cropName',title:'作物',type:'text',visible:false
            },{
                name:'taskName',title:'调研任务',type:'text',width:'70%'
            },{
                name:'createTime',title:'数据日期',type:'text',width:'30%'
            }]
        }
        //给jsGrid赋予fields属性
        $("#listGrid").jsGrid({
            fields:fields,
            rowClick:function (args) {
                var $row = this.rowByItem(args.item),
                    selectedRow = $("#listGrid").find('table tr.highlight');

                if (selectedRow.length) {
                    selectedRow.toggleClass('highlight');
                };
                $row.toggleClass("highlight");

                if(param.dataFlag == forum_map_data.yg_data){
                    setQueryParam(args.item,param)

                    var dsInfo = getDsInfo(queryParam.dsCode);
                    mapClazz.showLegend(dsInfo.Map_Legend,queryParam.dsId, queryParam.cropName);

                    //加载左侧地图数据
                    loadTimeEvent(queryParam);
                    //修改参数
                    mapClazz.setParams(queryParam);
                }else{
                    closeBox();
                    loadAllMarByTaskId(args.item.taskId, args.item.taskName, args.item.cropName);
                }
            }

        })


        ajax.opts.url = url;
        ajax.opts.successFun = function (result) {

            var myData = [];
            processDialog.dialog("close");
            if(result.flag && result.data != null && result.data.length > 0){
                $.each(result.data, function (index, element) {

                    if(param.dataFlag == forum_map_data.yg_data){
                        var item = {
                            'regionId': element.regionId, //区域
                            'regionName': element.regionName,
                            'dataMonth': element.dataMonth, //气象数据所属年月
                            'dataSetId': element.dataSetId, //数据集
                            'dataSetCode': element.dataSetCode,
                            'dataSetName': element.dataSetName,
                            'resolution':element.resolutionId, //分辨率
                            'resolutionName': element.resolutionName,
                            'cropId': element.cropId,  //作物
                            'cropName': element.cropName
                        }
                        myData.push(item)
                    }else if(param.dataFlag == forum_map_data.dy_data){
                        var id = element.id;
                        if(custom_settings.isNewTask == 1){
                            id = element.taskId;
                        }
                        var item = {
                            'taskId':id,
                            'cropName': element.cropName,
                            'taskName': element.taskName,
                            'createTime':new Date(element.createTime).Format("yyyy-MM-dd")
                        }
                        myData.push(item)
                    }
                })
            }
            // //临时用测试数据
            // if(param.dataFlag == forum_map_data.dy_data){
            //
            //     myData.push({'taskId':1,'taskName':'湖北武汉2019年玉米数据调研','createTime':'2019-04-11'});
            //     myData.push({'taskId':2,'taskName':'湖北鄂州2018年玉米数据调研','createTime':'2019-04-11'})
            // }
            $("#listGrid").jsGrid({
                // fields: fields,
                data: myData,
                pageButtonCount: 7,
                noDataContent: '没有查询到数据',
                pagerFormat: "页码: {first} {prev} {pages} {next} {last} &nbsp;&nbsp; {pageIndex} / {pageCount}"
            });
            $("#listGrid").jsGrid("reset");

            $("#listGrid").jsGrid("loadData").done(function (e) {

                if(param.dataFlag == forum_map_data.yg_data){

                    var rowData =$("#listGrid").jsGrid().data().JSGrid.data[0];
                    if(rowData != undefined && rowData != null){
                        setQueryParam(rowData, param)
                        var dsInfo = getDsInfo(queryParam.dsCode);

                        mapClazz.showLegend(dsInfo.Map_Legend,queryParam.dsId, queryParam.cropName);
                        // mapClazz.showLegend(dsInfo.Map_Legend)

                        mapClazz.moveToCenter(queryParam.regionCode, queryParam.level)

                        //加载左侧地图数据
                        loadTimeEvent(queryParam);
                        //修改参数
                        mapClazz.setParams(queryParam);
                        //气泡
                        mapClazz.loadPointDiglog();
                    }

                }else if(param.dataFlag == forum_map_data.dy_data){ //调研数据
                    // $("#listGrid .jsgrid-grid-body tr:first").addClass("highlight");
                    var rowData = $("#listGrid").jsGrid().data().JSGrid.data[0]
                    mapClazz.moveToCenter(queryParam.regionCode, queryParam.level)


                    if(rowData != undefined && rowData != null){
                        loadAllMarByTaskId(rowData.taskId, rowData.taskName, rowData.cropName);
                    }
                }
                $("#listGrid .jsgrid-grid-body tr:first").addClass("highlight");
            });

        };
        ajax.opts.failureFun = function () {

            processDialog.dialog("close");
        }

        ajax.run();
    }


    //设置参数
    var setQueryParam = function(row, param){
        queryParam = getParam();

        queryParam.dsId = row.dataSetId;
        queryParam.dsCode = row.dataSetCode;
        queryParam.dsName = row.dataSetName;
        // queryParam.regionId = row.regionId;
        queryParam.dataMonth = row.dataMonth;
        queryParam.resolution = row.resolution;
        queryParam.accuracyId = row.resolution;
        queryParam.cropId = row.cropId;
        queryParam.cropName = row.cropName;
        queryParam.startDate = param.startDate;
        queryParam.endDate = param.endDate;

    }


    //加载采集任务数据
    var loadAllMarByTaskId  = function(taskId , taskName , cropName){
        mapClazz.removeCollectionLayers();
        mapClazz.removeLayers(); //移除图层
        $("#legendTitle").closest("div").remove();//移除图例
        var taskParam = {
            taskInfoId:taskId
        }
        var ajax = new BaseAjax();
        if(custom_settings.isNewTask == 1) {
            ajax.opts.url = COLLECTION_TASK_NEW.nolog_findTaskItemList_url;
            taskParam.taskId =taskId;
        }else{
            ajax.opts.url = COLLECTION_TASK.findTaskItemList_url;
        }
        ajax.opts.data = JSON.stringify(taskParam);
        ajax.opts.contentType = "application/json";
        ajax.opts.successFun = function (result) {
            if(custom_settings.isNewTask == 1) {
                if (result.flag && result.data.length > 0) {
                    result = result.data;
                    mapClazz.addPointMarker(result,taskName,cropName);
                    //定位到点
                    mapClazz.moveFeaturesByItemId(result[0].itemId);
                }
            }else{
                if (result.flag && result.data.list.length > 0) {
                    result = result.data;
                    mapClazz.addPointMarker(result.list,taskName,cropName);
                }
            }

        };
        ajax.opts.errorFun = function () {
        };
        ajax.run()
    }

    /**
     * 加载时间轴数据
     * @param dsCode  数据集编码
     * @param regionId  区域ID
     * @param startDate 开始时间
     * @param endDate   截止时间
     * @param resolutionId 分辨率
     * @param cropId 作物ID
     */
    var loadTimeEvent = function(queryParam ){

        if(queryParam.dsCode === DS_Trmm.name  || queryParam.dsCode === DS_T.name || queryParam.dsCode === DS_Weather.name){ //加载降雨、地温、气温数据
            queryParam.startDate = queryParam.dataMonth + "-01";
            queryParam.endDate = getEndDate(queryParam.dataMonth , queryParam.endDate);
        }

        var dsInfo = getDsInfo(queryParam.dsCode);

        var opts = {
            url:dsInfo.FindDatetimePoint,
            data:{
                regionId:queryParam.regionId,
                cropId:queryParam.cropId,
                startDate:queryParam.startDate,
                endDate:queryParam.endDate,
                resolution:queryParam.resolution,
                accuracyId:queryParam.resolution
            },
            type:'GET'
        };
        var ajax = new BaseAjax();
        ajax.opts = opts;
        ajax.opts.successFun = function(result){
            var _data = result.data;
            timeSliderSet.showMultiDotTime(_data, queryParam.dsCode);

        };
        ajax.opts.errorFun = function(){
            console.info("数据时间点加载失败.");
        };
        ajax.run();

    }


    /**
     * 获取截止时间
     * 降雨地温气温数据的列表是按照月进行划分的，所以需要对起止时间进行处理
     * @param dataMonth
     * @param endDate
     * @returns {*}
     * @version<1> 2019-04-11 lcw :Created.
     */
    var getEndDate = function(dataMonth, endDate){
        var date = getLastDayInMonth(dataMonth);
        if(new Date(date) < new Date(endDate)){
            endDate = date;
        }
        return endDate ;
    }


        /**
         * 加载各数据集的图表数据
         * @param
         * @version<1> 2019-03-19 cxw:created
         */
        var loadData = {
            loadAllData : function(){

                mapClazz.removeLayers(); //移除图层
                mapClazz.removeCollectionLayers();

                loadData.loadCurrentData();
                var param = queryParam;

                if(param.dsId === DS_Growth.id){//长势
                    loadData.loadGrowthData(param);
                }else{
                    loadData.loadThreeYreasData(param);
                    if(param.dsId === DS_Trmm.id||param.dsId === DS_T.id||param.dsId===DS_Weather.id){//降雨、地温、气温
                        loadData.loadReportTxt();
                    }
                }

                loadData.loadBeyondCurrentData();//重载图层
            },
            loadReportTxt:function(){
                //var param = getParam();
                var param = queryParam;
                // param.startDate = $('#startDate').val() || '';
                // param.endDate = $('#endDate').val() || '';
                var dsInfo = getDsInfo(param.dsCode);
                $('#chartLi5 h2').html('').html(param.dsName+'报告');
                $('#chartLi5 .reportTxt').remove();
                // $('#chartLi5').html('');
                //在气温数据集下，如果查询的区域是省级或者国家，则设置查询的天气区域id为省会城市或者首都区域id
                if(param.dsId == DS_Weather.id && (param.level == 2 || param.level == 1) && param.capitalId){
                    param.regionId = param.capitalId;
                }
                var opts = {
                    url: dsInfo.Briefing_URL,
                    data:param,
                    type:'GET',
                    // async: false
                };
                var ajax = new BaseAjax();
                ajax.opts = opts;
                ajax.opts.successFun = function(result){
                    if(result.data){
                        $('#chartLi5 .reportTxt').remove();
                        $('#chartLi5').append('<div class="reportTxt">'+result.data+'</div>');
                    }else{
                        $('#chartLi5').html('<div class="reportTxt"><span style="text-align:center;line-height:220px; display:block; font-size:14px;">暂无报告</span></div>');
                    }
                    reportLoadFlag = true;
                };
                ajax.opts.errorFun = function(){
                    console.info("报告加载失败.");
                };
                ajax.run();
                return false;
            },
            loadtableListPage:function(result,paramRegion){
                var param = queryParam;
                var dsInfo = getDsInfo(param.dsCode);
                if(result.data!= null && !$.isEmptyObject(result.data)){//有数据时
                    var mydata=[];
                    if(dsInfo.id===DS_Distribution.id){//分布
                        // $('#mytable caption').html('').html('下级行政区'+param.cropName+dsInfo.threeTitle+'('+DS_Distribution.unit+')');
                        var lastTwoYear = parseInt(param.dataTime.substring(0,4))-2;//前两年
                        var lastYear = parseInt(param.dataTime.substring(0,4))-1;//前一年
                        var currentYear = parseInt(param.dataTime.substring(0,4));//当年

                        if(result.data[lastTwoYear]==null){
                            return;
                        }

                        for(var i=0;i<result.data[lastTwoYear].length;i++){
                            regionName = result.data[lastTwoYear][i].regionName;
                            lastTwoYearData = result.data[lastTwoYear][i]?(result.data[lastTwoYear][i].value/10000).toFixed(2):null;
                            lastYearData = result.data[lastYear][i]?(result.data[lastYear][i].value/10000).toFixed(2):null;
                            currentYearData = result.data[currentYear][i]?(result.data[currentYear][i].value/10000).toFixed(2):null;

                            var rowJson = {
                                'one': regionName,
                                'two': lastTwoYearData,
                                'three': lastYearData,
                                'four': currentYearData
                            };
                            for(attr in rowJson){
                                if(rowJson[attr]===null){
                                    rowJson[attr] = '--';
                                }
                            }
                            mydata.push(rowJson);
                        };
                    }else if(dsInfo.id===DS_Growth.id){//长势

                        mydata = [
                            {'one':'很好','two':((result.data[0].highest)/10000).toFixed(2)},
                            {'one':'好','two':((result.data[0].higher)/10000).toFixed(2)},
                            {'one':'较好','two':((result.data[0].high)/10000).toFixed(2)},
                            {'one':'正常','two':((result.data[0].normal)/10000).toFixed(2)},
                            {'one':'较差','two':((result.data[0].low)/10000).toFixed(2)},
                            {'one':'差','two':((result.data[0].lower)/10000).toFixed(2)},
                            {'one':'很差','two':((result.data[0].lowest)/10000).toFixed(2)}
                        ]
                        for(attr in mydata){
                            if(mydata[attr]===null){
                                mydata[attr] = '--';
                            }
                        }
                    }else if(dsInfo.id===DS_Yield.id){//估产

                        // $('#botTable thead').html('').append('<tr><th title="区域">区域</th><th title="产量预估('+dsInfo.unit+')">产量预估('+dsInfo.unit+')</th><th title="去年同期('+dsInfo.unit+')">去年同期('+dsInfo.unit+')</th><th title="增长率">增长率</th></tr>');
                        var lastYear = parseInt(param.dataTime.substring(0,4))-1;//前一年
                        var currentYear = parseInt(param.dataTime.substring(0,4));//当年
                        if(result.data[currentYear] != null){
                            for(var i=0;i<result.data[currentYear].length;i++){
                                var rowJson = {
                                    'one': result.data[currentYear][i].regionName,
                                    'two': (result.data[currentYear][i].value/10000).toFixed(2),
                                    'three': (result.data[lastYear][i].value/10000).toFixed(2),
                                    'four':(result.data[currentYear][i].percent*100).toFixed(2)+'%'
                                }
                                for(attr in rowJson){
                                    if(rowJson[attr]===null){
                                        rowJson[attr] = '--';
                                    }
                                }
                                mydata.push(rowJson);
                            }
                        }


                    }else if(dsInfo.id===DS_Weather.id){//气温
                        var dsInfo = getDsInfo(param.dsCode);//获取当前数据集的数据

                        // $('#botTable thead').append('<tr><th title="区域">区域</th><th width="100" title="日期">日期</th><th title="最高温('+dsInfo.unit+')">最高温('+dsInfo.unit+')</th><th title="最低温('+dsInfo.unit+')">最低温('+dsInfo.unit+')</th></tr>');
                        for(var i=0;i<result.data.length;i++){
                            var rowJson = {
                                'one':result.data[i].regionName,
                                'two':result.data[i].date,
                                'three':result.data[i].maxValue,
                                'four':result.data[i].minValue
                            }
                            for(attr in rowJson){
                                if(rowJson[attr]===null){
                                    rowJson[attr] = '--';
                                }
                            }
                            mydata.push(rowJson);
                        }
                    }else if(dsInfo.id===DS_Trmm.id||dsInfo.id===DS_T.id){//降雨，地温
                        var dsInfo = getDsInfo(param.dsCode);//获取当前数据集的数据
                        // $('#mytable caption').html(param.startDate+'--'+param.endDate+param.chinaName+dsInfo.threeTitle+'('+dsInfo.unit+')');

                        // $('#botTable thead').append('<tr><th title="区域">区域</th><th width="90" title="日期">日期</th><th title='+param.dsName+'('+dsInfo.unit+')>'+param.dsName+'('+dsInfo.unit+')</th><th title="上月同比('+dsInfo.unit+')">上月同比('+dsInfo.unit+')</th><th title="增长率">增长率</th></tr>');
                        for(var i=0;i<result.data.length;i++){
                            var rowJson = {
                                'one':result.data[i].regionName,
                                'two':result.data[i].date,
                                'three':result.data[i].value,
                                'four':result.data[i].lastValue,
                                'five':(result.data[i].percent*100).toFixed(2)+'%'
                                // 'five':result.data[i].percent===0?0:(result.data[i].percent>0?((result.data[i].percent)*100).toFixed(2)+'% ↑':((result.data[i].percent)*100).toFixed(2)+'% ↓')
                            }
                            for(attr in rowJson){
                                if(rowJson[attr]===null){
                                    rowJson[attr] = '--';
                                }
                            }
                            mydata.push(rowJson);
                        }
                    }


                    $("#botGrid").jsGrid({data:mydata,pageButtonCount: 7,noDataContent:'没有查询到数据',pagerFormat: "页码: {first} {prev} {pages} {next} {last} &nbsp;&nbsp; {pageIndex} / {pageCount}"});
                    $("#botGrid").jsGrid("reset");
                }else{
                    $('#botTable tbody').html('<tr><td style="color:#fff; text-align:center; border: 0; line-height: 200px;">暂无数据</td></tr>');
                }
            },
            loadGraphYearsData:function(result){
                // var param = getParam();
                var param = queryParam;
                var dsInfo = getDsInfo(param.dsCode);//获取当前数据集的数据
                var myChart = echarts.init(document.getElementById('chartLi2'));
                var option = myChart.getOption();
                option.title[0].text='';
                option.legend[0].data=[];
                option.xAxis[0].data=[];
                option.yAxis[0].name='';
                option.series=[];
                var selectAreaName = param.chinaName.indexOf("|")>=0?$.trim(param.chinaName.substring(param.chinaName.lastIndexOf('|') + 1, param.chinaName.length)):param.chinaName;
                var optionTitle = param.dataTime+selectAreaName+param.cropName+dsInfo.threeTitle+'图'
                option.title[0].text=optionTitle.length > 29 ? (optionTitle.slice(0,29)+"...") : optionTitle ;
                var lastTwoYear = parseInt(param.dataTime.substring(0,4))-2;//前两年
                var lastYear = parseInt(param.dataTime.substring(0,4))-1;//前一年
                var currentYear = parseInt(param.dataTime.substring(0,4));//当年
                option.yAxis[0].name='单位：'+dsInfo.unit;

                option.legend[0].data = [lastTwoYear.toString(),lastYear.toString(),currentYear.toString()];
                var arry = [];
                var lastTwoData = [];
                var lastData = [];
                var currentData = [];
                if(result.data[lastTwoYear]==null){
                    return;
                }
                for(var i=0;i<result.data[lastTwoYear].length;i++){
                    if((result.data[lastTwoYear][i].regionId.toString()!=param.regionId&&result.data[lastTwoYear].length>1)||(result.data[lastTwoYear].length===1&&result.data[lastTwoYear][i].regionId.toString()===param.regionId)){
                        //柱状图数据
                        arry.push(result.data[lastTwoYear][i].regionName);
                        lastTwoData.push(result.data[lastTwoYear][i]===undefined||(result.data[lastTwoYear][i].value/10000).toFixed(2)=='0.00'||result.data[lastTwoYear][i]==null?'--':(result.data[lastTwoYear][i].value/10000).toFixed(2));
                        lastData.push(result.data[lastYear][i]===undefined||(result.data[lastYear][i].value/10000).toFixed(2)=='0.00'||result.data[lastYear][i]==null?'--':(result.data[lastYear][i].value/10000).toFixed(2));
                        currentData.push(result.data[currentYear][i]===undefined||(result.data[currentYear][i].value/10000).toFixed(2)=='0.00'||result.data[currentYear][i]==null?'--':(result.data[currentYear][i].value/10000).toFixed(2));
                    }
                }
                option.xAxis[0].data=arry;

                var seriesData = [
                    {name: lastTwoYear,data: lastTwoData},
                    {name: lastYear,data: lastData},
                    {name: currentYear,data: currentData},
                ];
                for(var i=0;i<seriesData.length;i++){
                    var item = {
                        name: seriesData[i].name.toString(),
                        type: 'bar',
                        label: {
                            normal: {
                                show: true,
                                position: 'top',
                                color:'#fff'
                            },
                        },
                        data: seriesData[i].data,
                    }
                    option.series.push(item);
                };
                myChart.setOption(option);
                window.onresize = myChart.resize;
            },
            //获取当年的数据
            loadCurrentData:function(paramRegion){

                //var param = getParam();
                var param = queryParam;
                var dsInfo = getDsInfo(param.dsCode);//获取当前数据集的数据
                var _url='';
                if(paramRegion===undefined){
                    if((param.dsId===DS_Distribution.id && param.level==='4')||(param.dsId===DS_Yield.id && param.level==='4')){
                        _url=dsInfo.FindPercent;
                    }else{
                        _url=dsInfo.Mom_URL;
                    }
                }else{
                    param.regionId = paramRegion.regionId;
                    _url=dsInfo.Mom_URL;
                }
                var opts = {
                    url: _url,
                    data:{
                        regionId:param.regionId,
                        startDate : param.dataTime,
                        endDate : param.dataTime,
                        accuracyId : param.resolution,
                        cropId : param.cropId,
                        dataTime:param.dataTime,
                        resolution:param.resolution,
                        isQueryLargeArea:param.isQueryLargeArea
                    },
                    type:'GET'
                };

                var ajax = new BaseAjax();
                ajax.opts = opts;
                ajax.opts.successFun = function(result){
                    
                    console.log(result, "result----")
                    
                    
                    if(paramRegion===undefined){
                        var myChart = null;
                        if(param.dsId!=DS_Distribution.id&&param.dsId!=DS_Yield.id&&param.dsId!=DS_Growth.id){


                            loadData.loadtableListPage(result);//加载表格
                        }
                        // var myChart = echarts.init(document.getElementById('chartLi1'));

                        if(param.dsId === DS_Distribution.id || param.dsId === DS_Yield.id  || param.dsId === DS_Growth.id){
                            myChart = myChartLi1;

                            var option = myChart.getOption();
                            var selectAreaName = param.chinaName.indexOf("|")>=0?$.trim(param.chinaName.substring(param.chinaName.lastIndexOf('|') + 1, param.chinaName.length)):param.chinaName;
                            var optionTitle = param.dataTime+selectAreaName+(param.cropName != undefined ?param.cropName :"") +dsInfo.threeTitle+'图';
                            option.title[0].text=optionTitle.length > 29 ? (optionTitle.slice(0,29)+"...") : optionTitle ;
                            option.series[0].data = [];
                            option.graphic[0].elements[0].style.text='单位：'+dsInfo.unit;
                            var pieList = [],arr=[];
                            if(result.data!= null && !$.isEmptyObject(result.data)){
                                if(param.dsId===DS_Distribution.id||param.dsId===DS_Yield.id){
                                    if(param.level==='4'){
                                        pieList = [
                                            {'value':(result.data.regionValue/10000).toFixed(2),'name':result.data.chinaName},
                                            {'value':(result.data.otherRegionValue/10000).toFixed(2),'name':'其他'}
                                        ]
                                    }else{
                                        for(var i=0;i<result.data.length;i++){
                                            if(result.data[i].regionId!=param.regionId){
                                                arr.push({'value':result.data[i].value,'name':result.data[i].regionName});
                                            }
                                        };
                                        function sortNumber(a,b){
                                            return b.value - a.value;
                                        }
                                        //只需将排序替换成
                                        arr = arr.sort(sortNumber);

                                        if(arr.length>8){
                                            for(var i=0;i<8;i++){
                                                pieList.push({'value':(arr[i].value/10000).toFixed(2),'name':arr[i].name});
                                            }
                                            var num = 0;
                                            for(var i=8;i<arr.length;i++){
                                                num = num + arr[i].value;
                                            }
                                            pieList.push({'value':(num/10000).toFixed(2),'name':'其他'});
                                        }else{
                                            for(var i=0;i<arr.length;i++){
                                                pieList.push({'value':(arr[i].value/10000).toFixed(2),'name':arr[i].name});
                                            }
                                        }

                                    }
                                    option.series[0].data = pieList;
                                }else if(param.dsId===DS_Growth.id){
                                    option.series[0].data = [
                                        {'value':((result.data[0].lowest)/10000).toFixed(2),'name':'很差'},
                                        {'value':((result.data[0].lower)/10000).toFixed(2),'name':'差'},
                                        {'value':((result.data[0].low)/10000).toFixed(2),'name':'较差'},
                                        {'value':((result.data[0].normal)/10000).toFixed(2),'name':'正常'},
                                        {'value':((result.data[0].high)/10000).toFixed(2),'name':'较好'},
                                        {'value':((result.data[0].higher)/10000).toFixed(2),'name':'好'},
                                        {'value':((result.data[0].highest)/10000).toFixed(2),'name':'很好'}
                                    ];
                                }
                            }
                            myChart.setOption(option);
                            window.onresize = myChart.resize;
                        }

                    }else{
                        loadData.loadtableListPage(result,paramRegion);//加载表格
                    };
                };
                ajax.opts.errorFun = function(){
                    console.info("当年数据加载失败.");
                };
                ajax.run();
            },
            //加载地图上的色块，或者绑定数据
            loadBeyondCurrentData:function(paramRegion){
                //var param = getParam();
                param = queryParam;
                // if (param.level == 4 && Municipality.indexOf(param.parentId) >= 0) param.regionId = param.parentId;
                param.isQueryLargeArea = 1;//不查询大区，查询每个区域的详细数据
                var dsInfo = getDsInfo(param.dsCode);//获取当前数据集的数据
                var _url='';
                if(paramRegion===undefined) {//如果实参为空
                    if(param.dsId === DS_Distribution.id && param.level === '4') {
                        _url=DS_Distribution.FindPercent;
                    }else if(param.dsId == DS_Growth.id){
                        _url= DS_Growth.GrowthInRegion;
                    }else{
                        _url=dsInfo.Mom_URL;
                    }
                }else{//实参不为空则使用实参的区域
                    param.regionId = paramRegion.regionId;
                    _url=dsInfo.Mom_URL;
                }

                //当前选中的区域为直辖市
                if(param.level ==2 && Municipality.indexOf(param.regionId) > 0){
                    param.regionId = param.capitalId;
                }
                currentLoadFlag = false;//加载进度标识

                var opts = {
                    url: _url,
                    // data:param,
                    data:{
                        regionId:param.regionId,
                        startDate : param.dataTime,
                        endDate : param.dataTime,
                        accuracyId : param.resolution,
                        cropId : param.cropId,
                        dataTime:param.dataTime,
                        resolution:param.resolution,
                        isQueryLargeArea:param.isQueryLargeArea
                    },
                    type:'GET'
                };
                var ajax = new BaseAjax();
                ajax.opts = opts;
                ajax.opts.successFun = function(result){
                    if(result.data!= null && !$.isEmptyObject(result.data)){
                        var _data = [];
                        if (result.data instanceof Array) {//根据接口的不同，返回的数据可能是对象也可能是数组，所以加上判断
                            _data = result.data;;
                        }else {
                            _data.push(result.data);
                        }

                        //加载tif图层或色块，色块的颜色会根据图例的颜色以及各个区域的详细数据生成
                        if(dsInfo.Is_ColorBlock_Layer){//当该数据集的图层是色块图层的时候
                            //根据时间轴上选择的时间点加载色块，如果没有选中时间点则默认第一个时间点
                            var firstData = param['dataTime']? loadData.getTableDataByDay(_data,param['dataTime']) : loadData.getTableDataByDay(_data,param['startDate']);
                            if (param.dsId == DS_Yield.id) {//估产
                                for(var i=0;i<_data.length;i++){
                                    var value = _data[i].value/10000
                                    _data[i].value =  value.toFixed(2);
                                }
                                loadData.loadColorLayer(_data,dsInfo.Map_Legend);
                            }else {
                                if(param.dsId == DS_Weather.id){//气温
                                    $('input[type=radio]').change(function() {
                                        loadData.loadColorLayer(firstData,dsInfo.Weather_Legend);
                                    });
                                }
                                loadData.loadColorLayer(firstData,dsInfo.Map_Legend);
                            }
                        }else{//非色块数据集
                            setTimeout(function(){loadData.loadDsLayer()},1500) //加载tiff图层
                            if (param.dsId == DS_Distribution.id || param.dsId == DS_Growth.id){
                                //此处加载色块图层的目的是给各个区域也即feature绑定上数据，以便鼠标移入弹框显示该区域的数据，传过去的图例为空，则不回按照数值显示色块
                                loadData.loadColorLayer(_data,null);
                            }
                        }
                    }
                    currentLoadFlag = true;
                };
                ajax.opts.errorFun = function(){
                    console.info("当年下级区域数据加载失败.");
                };
                ajax.run();
            },
            //获取三年的数据
            loadThreeYreasData:function(param){
                var param = queryParam;
                var dsInfo = getDsInfo(param.dsCode);
                var selectAreaName = param.chinaName.indexOf("|")>=0?$.trim(param.chinaName.substring(param.chinaName.lastIndexOf('|') + 1, param.chinaName.length)):param.chinaName;
                if(param.dsId===DS_Trmm.id||param.dsId===DS_T.id||param.dsId===DS_Weather.id){//气象
                    var myChart = echarts.init(document.getElementById('chartLi3'));
                    var option = myChart.getOption();
                    option.color = [];
                    option.legend[0].data=[];
                    option.title[0].text='';
                    option.yAxis[0].name='';
                    option.xAxis[0].data=[];
                    option.series = [];
                    var lastTwoYear = parseInt(param.dataTime.substring(0,4))-2;//前两年
                    var lastYear = parseInt(param.dataTime.substring(0,4))-1;//前一年
                    var currentYear = parseInt(param.dataTime.substring(0,4));//当年
                    option.color = ['red','green','blue'];
                    option.legend[0].data=[lastTwoYear.toString(),lastYear.toString(),currentYear.toString()];
                    option.title[0].text=selectAreaName+dsInfo.threeTitle;
                    option.yAxis[0].name='单位：'+dsInfo.unit;
                    option.xAxis[0].data=getAll(param.startDate, param.endDate);
                }
                //在气温数据集下，如果查询的区域是省级或者国家，则设置查询的天气区域id为省会城市或者首都区域id
                if(param.dsId == DS_Weather.id && (param.level == 2 || param.level == 1) && param.capitalId){
                    param.regionId = param.capitalId;
                }
                var opts = {
                    url: dsInfo.An_ThreeYear_URL,
                    data:param,
                    type:'GET'
                };
                var ajax = new BaseAjax();
                ajax.opts = opts;
                ajax.opts.successFun = function(result){
                    if(result.data!= null && !$.isEmptyObject(result.data) && param.dataTime){
                        if(param.dsId===DS_Trmm.id||param.dsId===DS_T.id||param.dsId===DS_Weather.id){//气象
                            if(param.dsId===DS_Weather.id){
                                option.legend[0].data=[lastTwoYear.toString()+'最高温',lastTwoYear.toString()+'最低温',lastYear.toString()+'最高温',lastYear.toString()+'最低温',currentYear.toString()+'最高温',currentYear.toString()+'最低温'];
                                var lastTwoHighData = [],lastTwoLowData = [],lastHighData = [],lastLowData = [],currentHighData = [],currentLowData = [];
                                for(var i=0;i<getAll(param.startDate, param.endDate).length;i++){
                                    lastTwoHighData.push(result.data[lastTwoYear][i]===undefined?0:result.data[lastTwoYear][i].maxValue);
                                    lastTwoLowData.push(result.data[lastTwoYear][i]===undefined?0:result.data[lastTwoYear][i].minValue);
                                    lastHighData.push(result.data[lastYear][i]===undefined?0:result.data[lastYear][i].maxValue);
                                    lastLowData.push(result.data[lastYear][i]===undefined?0:result.data[lastYear][i].minValue);
                                    currentHighData.push(result.data[currentYear][i]===undefined?0:result.data[currentYear][i].maxValue);
                                    currentLowData.push(result.data[currentYear][i]===undefined?0:result.data[currentYear][i].minValue);
                                };
                                var dataArry = [lastTwoHighData,lastTwoLowData,lastHighData,lastLowData,currentHighData,currentLowData];
                                option.color = ['red','red','green','green','blue','blue'];
                                for(var i=0;i<option.legend[0].data.length;i++){
                                    var item = {
                                        name: option.legend[0].data[i],
                                        type:'line',
                                        symbol:(i&1) ===0?'triangle':'circle',
                                        // stack: dsInfo.threeTitle+'总量',//stack设置成不同则数据不堆叠，删除数据不堆叠
                                        data: dataArry[i],
                                        itemStyle:{
                                            normal:{
                                                lineStyle:{
                                                    width:2,
                                                    type: (i&1) ===0?'solid':'dotted',  //'dotted'虚线 'solid'实线
                                                    color: option.color[i]
                                                }
                                            }
                                        }
                                    };
                                    option.series.push(item);
                                }
                            }else{
                                var lastTwoData = [],lastData = [],currentData = [];
                                for(var i=0;i<getAll(param.startDate, param.endDate).length;i++){
                                    lastTwoData.push(result.data[lastTwoYear][i]===undefined?0:result.data[lastTwoYear][i].value);
                                    lastData.push(result.data[lastYear][i]===undefined?0:result.data[lastYear][i].value);
                                    currentData.push(result.data[currentYear][i]===undefined?0:result.data[currentYear][i].value);
                                };
                                var yearArry=[lastTwoYear,lastYear,currentYear];
                                var dataArry=[lastTwoData,lastData,currentData];
                                for(var i=0;i<yearArry.length;i++){
                                    var item = {
                                        name: yearArry[i].toString(),
                                        type:'line',
                                        symbol:'circle',
                                        // stack: dsInfo.threeTitle+'总量',
                                        data: dataArry[i],
                                    };
                                    option.series.push(item);
                                }
                            }
                            myChart.setOption(option);
                            window.onresize = myChart.resize;
                        }else{
                            loadData.loadGraphYearsData(result);//降雨柱状图
                            if(param.dsId===DS_Distribution.id||param.dsId===DS_Yield.id){
                                loadData.loadtableListPage(result);
                            };
                        }
                    }
                };
                ajax.opts.errorFun = function(){
                    console.info("最近三年数据加载失败.");
                };
                ajax.run();
            },
            //长势下级行政区数据
            loadGrowthData:function(){
                // var param = getParam();
                var param = queryParam;
                var dsInfo = getDsInfo(param.dsCode);//获取当前数据集的数据
                var _url = dsInfo.GrowthInRegion;
                // var _data = {regionId:'3102000006',cropId:'511',dataTime:'2018-10-01',resolution:'4010'};
                var opts = {
                    url: _url,
                    data:param,
                    // data:_data,
                    type:'GET'
                };
                var ajax = new BaseAjax();
                ajax.opts = opts;
                ajax.opts.successFun = function(result){
                    var myChart = myChartLi4;
                    var option = myChart.getOption();
                    option.title[0].text='';
                    option.legend[0].data=[];
                    option.series = [];
                    option.xAxis[0].data=[];
                    option.yAxis[0].name='单位：'+dsInfo.unit;
                    if(result.data!= null && !$.isEmptyObject(result.data)){
                        var selectAreaName = param.chinaName.indexOf("|")>=0?$.trim(param.chinaName.substring(param.chinaName.lastIndexOf('|') + 1, param.chinaName.length)):param.chinaName;
                        var optionTitle = param.dataTime+selectAreaName+param.cropName+'长势统计图';
                        option.title[0].text=optionTitle.length > 29 ? (optionTitle.slice(0,29)+"...") : optionTitle ;
                        option.legend[0].data=['很好','好','较好','正常','较差','差','很差'];
                        option.color=[DS_Growth.Map_Legend[0][1],DS_Growth.Map_Legend[1][1],DS_Growth.Map_Legend[2][1],DS_Growth.Map_Legend[3][1],DS_Growth.Map_Legend[4][1],DS_Growth.Map_Legend[5][1],DS_Growth.Map_Legend[6][1]];//图例颜色
                        var seriesList = [],xList = [];
                        for(var i=0;i<option.legend[0].data.length;i++){
                            var arry=[];
                            for(var j=0;j<result.data.length;j++){
                                if(i===6) arry.push(((result.data[j].lowest)/10000).toFixed(2));
                                else if(i===5) arry.push(((result.data[j].lower)/10000).toFixed(2));
                                else if(i===4) arry.push(((result.data[j].low)/10000).toFixed(2));
                                else if(i===3) arry.push(((result.data[j].normal)/10000).toFixed(2));
                                else if(i===2) arry.push(((result.data[j].high)/10000).toFixed(2));
                                else if(i===1) arry.push(((result.data[j].higher)/10000).toFixed(2));
                                else arry.push(((result.data[j].highest)/10000).toFixed(2));
                            }
                            var item = {
                                name: option.legend[0].data[i],
                                type: 'bar',
                                stack: '总量',
                                label: {
                                    normal: {
                                        // show: true,
                                        position: 'insideRight'
                                    }
                                },
                                data: arry
                            }
                            seriesList.push(item);
                        }
                        option.series = seriesList;
                        for(var i=0;i<result.data.length;i++){
                            xList.push(result.data[i].regionName);
                        }
                        option.xAxis[0].data=xList;
                    };
                    myChart.setOption(option);
                    window.onresize = myChart.resize;
                    myChart.off("click");
                    myChart.on('click',function (param) {
                        loadData.loadCurrentData(result.data[param.dataIndex]);
                    });

                    //自动触发一次
                    loadData.loadCurrentData(result.data[0]);
                };
                ajax.opts.errorFun = function(){
                    console.info("当年数据加载失败.");
                };
                ajax.run();
            },

            /**
             *获取table 列表中 莫一天的数据
             *@version <1> 2018-01-18 XuZhenguo : created.
             */
            getTableDataByDay : function(tableDataList,date){
                var dateDataArray = [];
                if (tableDataList && date && tableDataList.length>0) {
                    for(var i in tableDataList){
                        if (tableDataList[i] && tableDataList[i]['date'] && tableDataList[i]['date'].indexOf(date) != -1) {
                            dateDataArray.push(tableDataList[i]);
                        }
                    }
                }
                return dateDataArray;
            },

            /**
             * 加载图层信息
             * @param {Object} param
             * @version <1> 2019-03-19 cxw :Created.
             */
            loadDsLayer:function(){
                //var param = getParam();
                param = queryParam;
                var codeSplit = param.regionCode.split("-");
                if (codeSplit.length > 2) {
                    param.regionCode = codeSplit[0] + "-" + codeSplit[1];
                }

                var ajax = new BaseAjax();
                ajax.opts.url =  FORUM__Layer.Layer_Query_URL;
                ajax.opts.type = "GET";
                ajax.opts.data = param;
                ajax.opts.async = false;
                ajax.opts.contentType = "application/json";
                ajax.opts.successFun = function(result){
                    if(result.flag){
                        var layerName = result.data.name;
                        mapClazz.showDsLayer(layerName); //显示图层
                    }
                };
                ajax.opts.errorFun = function(){
                    console.info("load ds layer error");
                };
                ajax.run();
            },
            /**
             * 加载降雨地温和气温的数据
             * @param {Object} param
             * @version <1> 2019-03-19 cxw :Created.
             */
            loadColorLayer:function(_data, map_legend){
                mapClazz.showColorLayer(_data, map_legend, queryParam);
            },
        };


        function getAll(begin, end) {
            var ab = begin.split("-");
            var ae = end.split("-");
            var db = new Date();
            db.setUTCFullYear(ab[0], ab[1] - 1, ab[2]);
            var de = new Date();
            de.setUTCFullYear(ae[0], ae[1] - 1, ae[2]);
            var unixDb = db.getTime();
            var unixDe = de.getTime();
            var arry = [];
            for(var k = unixDb; k <= unixDe;) {
                arry.push((new Date(parseInt(k))).format());
                k = k + 24 * 60 * 60 * 1000;
            }
            return arry;
        }


        //月份处理
        function p(s) {
            return s < 10 ? '0' + s: s;
        }

        //获取两个时间段的所有日期
        Date.prototype.format = function() {
            var s = '';
            // s += this.getFullYear() + '-'; // 获取年份。
            if((this.getMonth() + 1) >= 10) {// 获取月份。
                s += (this.getMonth() + 1) + "-";
            } else {
                s += "0" + (this.getMonth() + 1) + "-";
            }
            if(this.getDate() >= 10) {// 获取日。
                s += this.getDate();
            } else {
                s += "0" + this.getDate();
            }
            return(s); // 返回日期。
        };



    /**
     * 获取用户选择的参数
     * 1.行政区域
     * 2.日期
     * 3.调研数据或遥感数据
     * @version <1>2019-04-10 lcw:Created.
     */
    var getParam = function(){

        var regionObj,param={};
        //获取区域参数
        var chinaName = txtRegion.value;
        var regionId = txtRegion.getAttribute("regionId");
        var regionCode = txtRegion.getAttribute("regionCode");
        var regionName = txtRegion.getAttribute("regionName");
        var parentId = txtRegion.getAttribute("parentId");
        var level = txtRegion.getAttribute("level");
        regionObj = {regionId:regionId,regionCode:regionCode,regionName:regionName,chinaName:chinaName,level:level,parentId:parentId,isQueryLargeArea:0};

        for(var key in regionObj)
            param[key] = regionObj[key];

        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        param["startDate"] = startDate;
        param["endDate"] = endDate;

        //调研数据集或遥感数据集
        var dataFlag = $("#tabLogo .leftTab .active").attr("data-id");
        param["dataFlag"] = dataFlag;

        return param;
    };

    init();

})
