require(["jquery","ol","BaseAjax","MapModule","RegionModule","jedate","formVerfication","PopWin","fly","commons","custom_settings","jqGrid","enums"],
    function($,ol,BaseAjax,MapModule,RegionModule,jedate,formVerfication,PopWin,fly,commons,custom_settings,jqGrid, enums){

        var txtArea = document.getElementById("txtArea");
        var Dict_DataSat_Flag = 400;//数据集：卫星
        var Dict_DataSat_Default = 401; //卫星默认值
        var Dict_DataStorageType_Flag=700;//数据存储类型
        var Dict_DataStorageType_Default = 701; //数据类型默认值
        var ulDatas = []; //检索数据集合
        //地图对象
        var mapTool;
        var chinaLayer;
        var nextRegionLayer;//下一级区域图层
        var layerName = "JiaHeDC:ly_WORLD";

        var init = function(){
            // commons.delCookie("myData")
            queryInit();
            $(window).resize(function(){
                // $("#listGrid").setGridWidth($("#dataDiv").width() - 30);
                chanageTableCss();
            });

            chanageTableCss();
            loadMapFun();

            initTimeEvent();
            RegionSelectFun();
            //初始化下拉框的值
            getDataDictOption("satNameSelect",Dict_DataSat_Flag, Dict_DataSat_Default );
            // getDataDictOption("dsTypeSelect",Dict_DataStorageType_Flag,Dict_DataStorageType_Default );
            getSensorOption(); //获取传感器下拉列表
            // calUlHeightFun(); //计算搜索结果UL高度
            // resizeFun(); //当屏幕大小发生改变时加载ul数据
            titleSelectFun();  //选项卡点击事件
            myDataListFun(); //我的数据列表点击事件
            initCreateTaskBtnEvent();
            cloudPercent();//云量滑动条
            selectStorageByFeature();
            showCookie();//加载cookie中存储的我的数据列表信息
            clickEvent(); //注册点击事件

        };

        var cloudPercent=function(){
            $( "#slider-range-max" ).slider({
                range: "min",
                min: 0,
                max: 100,
                value: 100,
                slide: function( event, ui ) {
                    $( "#cloudPercent" ).val( ui.value);
                }
            });

            $( "#cloudPercent" ).val( $( "#slider-range-max" ).slider( "value" ));
        };



        /**
         * 行政区、经纬度条件选择
         * @version<1> 2018-02-26 lcw ： Created.
         */
        var titleSelectFun = function(){
            $("#" + $(".on").attr("target")).show();
            $(".btLabel span").on("click", function(){
                $(this).addClass("on");
                $(this).siblings().removeClass("on");
                var _target = $(this).attr("target");
                $(".btForm>div").css("display","none");
                if(_target != undefined){
                    $("#" + _target).show();
                }
            })

        };

        /**
         * 区域选择控件
         *
         */
        var RegionSelectFun = function(){
            txtArea.onclick = function(){
                var opts = {colNum:3,width:400};
                opts.url = REGION_CONFIG.findRegion_url;
                var regionSelector = new RegionModule.RegionSelector("txtArea",opts);
                regionSelector.show();
            };
            return true;
        };

        /**
         * 查询数据字典数据
         * @param selectClass ： 类选择器
         * @paran dictId : 上级节点ID
         * 数据集下拉框查询参数： "dsSelect","1"
         */
        var getDataDictOption = function(selectClass, dictId, defaultValue) {
            var baseAjax = new BaseAjax();
            baseAjax.opts.url = DICT_COFING.queryDictByParentId_url//;"dict/queryDictByParentId";
            baseAjax.opts.data = JSON.stringify({'parentId': dictId});
            baseAjax.opts.successFun = function (resultData) {
                if (resultData.flag) {
                    $('.' + selectClass).html("");

                    var dataStatusArray = resultData.data;
                    var optionHtml = "";

                    if(defaultValue == undefined || defaultValue == null || defaultValue == ""){
                        if("sensNameSelect" != selectClass) {
                            optionHtml += "<option value=''>--请选择--</option>";
                        }
                    }


                    for (var i = 0, j = dataStatusArray.length; i < j; i++) {
                        var dataStatus = dataStatusArray[i];
                        if(dataStatus.dictId == defaultValue){
                            optionHtml += '<option value="' + dataStatus.dataValue + '" data-id="' + dataStatus.dictId + '" selected>' + dataStatus.dataName + '</option>';
                            //getDataDictOption("sensNameSelect",dataStatus.dictId );
                            getDataSensorOption("sensName",dataStatus.dictId );
                        }else{
                            optionHtml += '<option value="' + dataStatus.dataValue + '" data-id="' + dataStatus.dictId + '">' + dataStatus.dataName + '</option>';
                        }
                    }
                    $('.' + selectClass).text("").append(optionHtml);

                } else {

                }
            };
            baseAjax.run();
        };

        /**
         * 通过卫星change事件获取传感器下拉列表
         * @param sensNameSelect 传感器class
         *        satId  卫星Id
         * @version <1> 2018-2-26 lcw : Created.
         */
        var getSensorOption = function(){
            $("#satName").on("change",function(){
                //getDataDictOption("sensNameSelect",$('#satName option:selected').attr('data-id'));
                getDataSensorOption("sensName",$('#satName option:selected').attr('data-id'));
            })
        };

        /**
         * Description:
         * @param null
         * @return
         * @version <1> 2018/8/21 15:47 zhangshen: Created.
         */
        var getDataSensorOption = function (selectId, dictId) {
            var baseAjax = new BaseAjax();
            baseAjax.opts.url = DICT_COFING.queryDictByParentId_url//;"dict/queryDictByParentId";
            baseAjax.opts.async = false;
            baseAjax.opts.data = JSON.stringify({'parentId': dictId});
            baseAjax.opts.successFun = function (resultData) {
                if (resultData.flag) {
                    $('#' + selectId).html("");
                    var dataStatusArray = resultData.data;
                    var optionHtml = "";
                    for (var i = 0, j = dataStatusArray.length; i < j; i++) {
                        var dataStatus = dataStatusArray[i];
                        optionHtml += '<option value="' + dataStatus.dataValue + '" data-id="' + dataStatus.dictId + '">' + dataStatus.dataName + '</option>';
                    }
                    $('#' + selectId).append(optionHtml);
                    $('#' + selectId).multipleSelect({placeholder:"--请选择--", selectAllText:"全选"});
                }
            };
            baseAjax.run();
        };

        /**
         * 初始化时间控件
         */
        var initTimeEvent = function(){
            $("#dateScope").jeDate({
                range:" 至 ",
                multiPane:false,
                format: 'YYYY-MM-DD'
            });

        };

        var sateListGrid = function(param){
            $("#listGrid").jqGrid({
                url: DATA_STORAGE.findByPage_url,
                datatype: "json",
                postData:param,
                mtype:'POST',
                jsonReader: {
                    root: "list",
                    total: "pages",
                    page: "page",
                    records: "total",
                    repeatitems: false
                },
                rownumbers: true,
                colNames:['storageId','storageUrl','', '卫星', '传感器','时间','产品序列号','景序列号','云量','文件名','文件大小','操作'],
                colModel:[
                    {name:'storageId',index:'storage_id', hidden:true, width:'10px',key:true},
                    {name:'storageUrl',index:'storageUrl', hidden:true,},
                    {name:'thumbnailUrl',index:'thumbnail_url', width:'30px',align:"center", sortable:true,formatter:function(cellvalue, options, rowObject){

                        // <img src="'+ DATA_STORAGE.showThumbnail_url+'?storageId='+  rowObject.storageId + '&AccessToken=' + commons.getCookie("AccessToken") + '">
                            return '<span class="data_img" bbox="'+rowObject.bbox+'" storage_id="'+rowObject.storageId+'" title="'+ rowObject.storageUrl +'">' +
                                '<img src="'+ DATA_STORAGE.showThumbnail_url+'?storageId='+  rowObject.storageId + '&AccessToken=' + commons.getAccessToken() + '">' +
                                '</span>';
                        }},
                    {name:'satellite',index:'satellite', width:'50px',align:"center", sortable:true},
                    {name:'sensor',index:'sensor', width:'50px',align:"center", sortable:true},
                    {name:'dataTime',index:'data_time', width:'95px',align:"center", sortable:true},
                    {name:'productLevel',index:'product_level', width:'95px',align:"center", sortable:true,hidden:true},
                    {name:'sceneId',index:'scene_id', width:'95px',align:"center", sortable:true,hidden:true},
                    {name:'cloudPercent',index:'cloud_percent', width:'95px',align:"center", sortable:true,hidden:true},
                    {name:'fileName',index:'file_name', width:'95px',align:"center", sortable:true,hidden:true},
                    {name:'dataSize',index:'data_size', width:'95px',align:"center", sortable:true,hidden:true},
                    {name:'cz',index:'cz', width:'60px',align:"center", sortable:true,frozen:true, formatter:function(cellvalue, options, rowObject){
                            var myData=commons.getCookie("myData");
                            var str="";
                            if(myData!=null){
                                var array = JSON.parse(myData);
                                var cookieArray=[];
                                $.each(array, function(index, element){
                                    cookieArray.push(element);
                                });
                                var index = $.inArray(rowObject.storageId+"", cookieArray);
                                if(index<0){
                                    str = "<span class='data_add'><img id='"+ rowObject.storageId +"' src='images/public/add.png' class='addBtn' storage-id='"+ rowObject.storageId +"' thumbnail-url='"+ rowObject.thumbnailUrl +"' satellite='"+ rowObject.satellite +"' sensor='"+ rowObject.sensor +"' dataTime='"+ rowObject.dataTime +"' storageUrl='"+ rowObject.storageUrl +"' title='添加至我的数据列表' ></span>" ;
                                }else{
                                    str = "<span class='data_add added'><img id='"+ rowObject.storageId +"' src='images/public/added.png' class='addBtn' storage-id='"+ rowObject.storageId +"' thumbnail-url='"+ rowObject.thumbnailUrl +"' satellite='"+ rowObject.satellite +"' sensor='"+ rowObject.sensor +"' dataTime='"+ rowObject.dataTime +"' storageUrl='"+ rowObject.storageUrl +"' title='已添加至我的数据列表' ></span>" ;
                                }

                            }else{
                                str ="<span class='data_add'><img id='"+ rowObject.storageId +"' src='images/public/add.png' class='addBtn' storage-id='"+ rowObject.storageId +"' thumbnail-url='"+ rowObject.thumbnailUrl +"' satellite='"+ rowObject.satellite +"' sensor='"+ rowObject.sensor +"' dataTime='"+ rowObject.dataTime +"' storageUrl='"+ rowObject.storageUrl +"' title='添加至我的数据列表' ></span>" ;
                            }
                            return str;
                        }}
                ],
                width:'100%',
                autowidth:true,
                rowNum:20,
                // rowList:[20,40],
                pager: '#pager2',
                // sortname: 'storage_id',
                viewrecords: true,
                sortorder: "desc",
                multiselect:true,
                loadComplete:function(xhr){
                    commons.isNextDisable();
                    $("#pager2_left").html("<input type='button' value='添加' class='btn  btn_grey' id='multiSelectBtn'>").css("width","65px");
                    var num = $("#listGrid").jqGrid('getGridParam', 'records');//总条数位数
                    if(num != null){
                        num = num.toString().length;//总条数位数
                        $("#pager2_right").css("width",70 + 5 * num + "px");//根据位数设置宽度
                        $("#pager2_center").css("width", ($("#pager2_center").parent().width() - 70 + 5 * num - 65 - 110) + "px");
                    }

                    tableEvent();
                    rowHoverFunction();
                    //$("#listGrid").setGridWidth($(".rightMain").width() - 30);
                    /**
                     * 加载缩略图边界
                     */
                    mapTool.removeGeometrys();
                    mapTool.removeThumbnailLayers();
                    var storageList = xhr.list;
                    for (var i = 0,j = storageList.length;i < j ; i++){
                        mapTool.addGeometry(storageList[i].bbox,storageList[i].storageId,{fillColor:"rgba(255,255,255, 0.1)",strokeColor:"#00f"});
                    }

                    var _html = $("#pager2_right .ui-paging-info").html();
                    var startIndex = _html.lastIndexOf("共");
                    var newHtml = "无数据显示";
                    if(startIndex != -1){
                        newHtml = _html.substring(startIndex).replace(/\s+/g, "") + "数据"
                    }

                    $("#pager2_right .ui-paging-info").html(newHtml)
                },onSelectAll:function(rowid, status) { //rowid 数组
                    var ids = $("#listGrid").jqGrid("getGridParam", "selarrrow");
                    var rowNum = $("#listGrid").jqGrid('getRowData').length; //获取当前页显示的行数
                    if(ids.length == rowNum){//全选
                        $("input[type='checkbox']").prop("checked",true);
                    }
                    if(ids.length == 0){
                        $("#multiSelectBtn").addClass("btn_grey");
                    }else if(ids.length > 0){
                        $("#multiSelectBtn").removeClass("btn_grey");
                    }
                },
                onSelectRow:function(rowid, e){
                    var ids = $("#listGrid").jqGrid("getGridParam", "selarrrow");
                    var rowNum = $("#listGrid").jqGrid('getRowData').length; //获取当前页显示的行数
                    if(ids.length == rowNum){//全选
                        $("input[type='checkbox']").prop("checked",true);
                    }
                    if(ids.length == 0){
                        $("#multiSelectBtn").addClass("btn_grey");
                    }else if(ids.length > 0){
                        $("#multiSelectBtn").removeClass("btn_grey");
                    }
                }
            });
            $("#listGrid").jqGrid('setFrozenColumns');//滑动时此列冻结设置

        };

        var chanageTableCss = function(){
            var divObj = $('#listGrid').parent('div');
            divObj.addClass('tableStyle');
            divObj.css({'maxHeight':($("#showDiv").height()-$(".ui-jqgrid-hdiv").height())-$("#pager2").height()-20+"px"});
        };

        /**
         * 绑定table表格操作事件
         * @version <1> 2018-04-25 wl : Created.
         */
        var tableEvent = function(){
            $(".addBtn").off("click");
            $(".addBtn").on("click",function(){
                if($(this).hasClass("addBtn") && $(this).attr("src").indexOf("add.png") >=0){
                    addDataListCookieFun($(this));
                }
            });

            $("#multiSelectBtn").off("click");
            $("#multiSelectBtn").bind("click", function(){
                multiSelectFun();
            })
        };

        /**
         * 鼠标滑动行
         */
        var rowHoverFunction = function(){

            $("#listGrid tr").hover(function(event){
                $(this).css("background-color","#D8D8D6")
                $($(this).find("td")).each(function(item, value){
                    var aria = $(value).attr("aria-describedby");
                    if(aria == "listGrid_productLevel"){
                        $("#productLevel_show").html($(value).html())
                    }
                    if(aria == "listGrid_sceneId"){
                        $("#sceneId_show").html($(value).html())
                    }
                    if(aria == "listGrid_cloudPercent"){
                        $("#cloudPercent_show").html($(value).html())
                    }
                    if(aria == "listGrid_dataSize"){
                        $("#dataSize_show").html($(value).html())
                    }
                    if(aria == "listGrid_fileName"){
                        $("#fileName_show").html($(value).html())
                    }
                    if(aria == "listGrid_rn"){
                        //计算选中行距离浏览器顶端的位置，230为表格上方的距离
                        var pos = $(value).offset().top - 230

                        $("#rowDetail").css("top",pos + "px");
                    }

                })


                $("#rowDetail").show()

            },function(){

                $(this).css("background-color","#fff")
                $("#rowDetail").hide()
            })
            
            $("#rowDetail").hover(function(){
                $(this).show()
            },function(){
                $(this).hide()
            })
        }

        /**
         * 多选添加至我的数据列表
         * @version<1> 2018-05-24 lcw : Created.
         */
        var multiSelectFun = function(){
            //获取多选到的id集合
            var ids = $("#listGrid").jqGrid("getGridParam", "selarrrow");

            $.each(ids,function(row,id){
                //由id获得对应数据行
                var row = $("#listGrid").jqGrid('getRowData', id);

                if(row.cz.indexOf("added") == -1){ //添加
                    addDataListCookieFun($($(row.cz).find("img")[0]));
                }
            })

            //重新綁定事件
            bindLiClick();

        };

        /**
         * 将所选数据添加至我的数据列表中 并保存到cookie中
         * @version<1> 2018-04-25 wl : Created.
         */
        var addDataListCookieFun = function(obj){
            $("#listGrid tbody tr img").each(function(index, item){//循环,将字符串对象转换成jq对象,防止img中的src属性为字符串
                if($(this).attr("id") == obj.attr("id")){
                    obj = $(this);
                    return true;
                }
            });

            //添加到cookie
            var myData=commons.getCookie("myData");
            var array=[];
            var cookieArray=[];
            var exp = new Date();
            //var ids = [];//存放cookie中的id
            var dataObj = cookieIdsToObjForHtml(new Array(obj.attr("storage-id")))[0];
            exp.setTime(exp.getTime() + 60 * 1000 * 30);//过期时间 30分钟
            if(myData!=null){//如果我的数据列表不为空
                array = JSON.parse(myData);
                $.each(array, function(index, element){
                    cookieArray.push(element);
                });
            }
            //判断array中是否已经存在该id 如果已经存在则 不再添加
            //var index=$.inArray($(evt).attr("storage-id"),array);
            var index = $.inArray(obj.attr("storage-id")+"", cookieArray);
            if(index<0){//无重复数据
                var li=obj.parent();
                $(li).addClass("added");
                $(li).find("img").attr({
                    "src":"images/public/added.png",
                    "title":'已添加至我的数据列表'
                });
                var element = {};
                var storage_id= dataObj.storageId;//obj.attr("storage-id"); //
                var satellite=dataObj.satellite;//obj.attr("satellite");//卫星名
                var sensor=dataObj.sensor;//obj.attr("sensor");//传感器
                var dataTime = dataObj.dataTime;//obj.attr("dataTime"); //日期
                var thumbnailUrl =dataObj.thumbnailUrl; //obj.attr("thumbnail-url"); //缩略图
                var storageUrl = dataObj.storageUrl;//obj.attr("storageUrl"); //路径
                element.storageId = storage_id;
                element.satellite=satellite;
                element.sensor=sensor;
                element.dataTime = dataTime;
                element.thumbnailUrl = thumbnailUrl;
                element.storageUrl = storageUrl;

                var _data = makeLiForDataListFun(element);
                $(".dataContent .dataDiv").append(_data);

                //array.push(element);
                array.push(storage_id);
                commons.setCookie("myData",JSON.stringify(array),exp.toGMTString());

                $(".allSelect").removeClass("selected"); //取消全选
                flyFun();
            }else {//有重复数据的只改变按钮颜色不做添加操作
                var li=obj.parent();
                $(li).addClass("added");
                $(li).find("img").attr({
                    "src":"images/public/added.png",
                    "title":'已添加至我的数据列表'
                });
            }
            obj.removeClass("addBtn");//移除可以添加的样式
            //重新綁定事件
            bindLiClick();
        };

            //重新綁定事件
        var bindLiClick = function(){
            $("#myDataList li").off("click");
            $("#myDataList li .myDataBox").off("click");
            $("#myDataList li").click(function(e){
                selectDataFun(this);
            });
            $("#myDataList li .myDataBox").click(function(e){
                e.stopPropagation();
                setCheckedNumFun();
            });
            $(".data_delete").off("click");
            $(".data_delete").click(function(){
                dataRemoveFun(this);
            });
        }

        /**
         * Description: 将cookie中的ids查询出对象,通过ajax请求赋值
         * @param cookie中的ids
         * @return
         * @version <1> 2018/5/28 16:25 zhangshen: Created.
         */
        var cookieIdsToObjForAjax = function(ids){
            var dataList = [];
            var ajax = new BaseAjax();
            // ajax.opts.url = "storage/findStorageByIds";
            ajax.opts.url = DATA_STORAGE.findStorageByIds_url
            ajax.opts.async = false;
            ajax.opts.data = JSON.stringify({"ids":ids});
            ajax.opts.successFun = function (data) {
                if(data.flag){
                    dataList = data.data;
                }
            };
            ajax.run();
            return dataList;
        };

        /**
         * Description: 通过html中的数据赋值
         * @param cookie中的ids
         * @return 
         * @version <1> 2018/5/29 11:36 zhangshen: Created.
         */
        var cookieIdsToObjForHtml = function(ids){
            var dataList = [];
            $.each(ids, function(i, e){
                var obj = {};
                $("#listGrid tbody tr img").each(function(index, item){//循环,将字符串对象转换成jq对象,防止img中的src属性为字符串
                    if($(this).attr("id") == e+""){
                        obj.storageId = $(this).attr("storage-id");
                        obj.satellite = $(this).attr("satellite");
                        obj.sensor = $(this).attr("sensor");
                        obj.dataTime = $(this).attr("datatime").substring(0,10);
                        obj.thumbnailUrl = $(this).attr("thumbnail-url");
                        obj.storageUrl = $(this).attr("storageUrl");
                        dataList.push(obj);
                        return true;
                    }
                });
            });
            return dataList;
        };


        /**
         * 添加至我的数据列表动态效果
         * @version<1> 2018-05-24 lcw : 将该部分代码提取出来单独作为方法使用
         */
        var flyFun = function(){
            var offset = $(".dataCar").offset();  //结束的地方的元素
            var flyer = $('<img class="u-flyer" src="images/public/add.png">');
            flyer.fly({
                start: {
                    left: event.pageX,
                    top: event.pageY
                },
                end: {
                    left: offset.left+10,
                    top: offset.top+10,
                    width: 0,
                    height: 0
                },
                // speed: 1,
                vertex_Rtop:60,
                onEnd: function(){
                    //修改我的数据列表的数据值
                    updateMyDataNumFun();
                    this.destory();
                }
            });
        };


        /**
         * 注册点击事件
         */
        var clickEvent = function(){
            //隐藏左侧Label事件
            $("#hideLabel").on("click",function(){
                hideFun();
            });
            //隐藏左侧Label事件
            $(".dataSearch").on("click",function(){
                hideFun();
            });

            //新增检索条件“+”按钮事件
            $("#addItem").on("click", function(){
                addSearchFormFun();
            });

            //检索按钮事件
            $("#okBtn").on("click", function(){
                queryFun();
                //sateListGrid(param);
            });
            $("#cancelBtn").on("click", function(){
                cancelFun();
            });

            // $('.pen').toggle(function () {
            //     var domObj = {};
            //
            //
            //     //精度1
            //     var long1 = document.getElementById("long1");
            //     domObj.long1 = long1;
            //
            //     var lat1 = document.getElementById("lat1");
            //     domObj.lat1 = lat1;
            //     var long2 = document.getElementById("long2");
            //     domObj.long2 = long2;
            //     var lat2 = document.getElementById("lat2");
            //     domObj.lat2 = lat2;
            //
            //     console.log(domObj)
            //
            //     mapTool.drawFeature("Polygon",domObj);
            //
            // },function () {
            //     mapTool.drawClose();
            // });

            $(".pen").on("click", function(){
                drawFun();
            });

            //移除span
            $(".delItem").live("click",function(){
                $(this).parent().remove();
            });

           /* //选择数据
           $(".data_box").live("click", function(){
                selectDataFun(this);
            });*/

            //选择数据
          $("#myDataList li").on("click",function(e){
                selectDataFun(this);
            });


            $("#myDataList li .myDataBox").on("click",function(e){
                e.stopPropagation();
                setCheckedNumFun();
            });

            //添加数据至我的数据列表
            // $(".data_add").live("click", function(){
            //     if(!$(this).hasClass("added")){ //判断是否已经添加至我的数据列表中
            //         //addDataListFun(this);
            //     }
            // })

            //缩略图预览
            $(".data_img").live("click", function(){
                dataPreviewFun(this);
            });

            //首页
            // $(".firstPage").on("click", function(){
            //     flipPageFun(0);
            // })
            //
            // //上一页
            // $(".prePage").on("click", function(){
            //     flipPageFun(-1)
            // })
            //
            // //下一页
            // $(".nextPage").on("click", function(){
            //     flipPageFun(1)
            // })
            //
            // //尾页
            // $(".lastPage").on("click", function(){
            //     flipPageFun(2)
            // })

            /*//范围
             $("#cloudPercent,#cloudPercent2").on("keyup", function(){
             var val = $(this).val();
             var bool = formVerfication.isPositiveInteger(val);
             if(!bool || val<0 || val>100 ){
             $(this).val(val.substring(0,val.length - 1 ));
             }
             })*/

            //经纬度
            $("#jwd input").on("blur",function(){
                var val = $(this).val();
                var bool = formVerfication.isPositiveFloat(val);
                if(!bool){
                    $(this).val("");
                }
            });

            //全选功能
            $(".allSelect").on("click",function(){
                allSelectFun(this);
            });


            $(".data_delete").live("click",function(){
                dataRemoveFun(this);
            });

            //清空cookie信息
            $(".clearAll").on("click", function(){
                commons.delCookie("myData");//清空cookie
                //移除我的数据列表所有
                $("#myDataList").find("li").each(function () {
                    $(this).remove();
                });
                //更新总数信息
                //关闭弹出框
                updateMyDataNumFun();

                //左边列表所有按钮变为蓝色
                $("#listGrid").find("tbody").find("tr").each(function () {
                    var tdArr = $(this).children();
                    tdArr.eq(8).find(".data_add").removeClass("added");
                    tdArr.eq(8).find(".data_add img").attr({
                        "src":"images/public/add.png",
                        "title":'添加至我的数据列表'
                    }).addClass("addBtn");
                });
                setCheckedNumFun();//清空时jiang已选条数置空
                /*$("#listGrid").jqGrid('setGridParam',{
                 datatype:'json',
                 postData:param,
                 page:1
                 }).trigger("reloadGrid");
                 showCookie();//重新加载我的数据列表*/
            });

            //订单处理
            $("#orderBtn").on("click", function(){
                if($(this).hasClass("btn_grey")){
                    return false;
                }
                createOrderFun(this);
            });

            //数据处理
            $("#dealBtn").on("click", function(){
                if($(".addedNum").attr('data-num') > 0) {
                    createTask();
                }else{
                    // PopWin.showMessageWin("请选择需要处理的数据");
                }
            })
        };

        /**
         *初始化地图对象
         */
        var loadMapFun = function(){
            var opts,filter,style,opacity;

            mapTool = new MapModule.MapTool("divMap",{
                // 'maxZoom':18,
                // 'minZoom':5,
                'zoom':10,
                'logoSrc':custom_settings.main_right_bottom_logo_pic, //配置右下角的图片地址
                'logoHref':custom_settings.main_right_bottom_logo_location //配置右下角点击图片后跳转的地址
            });
            mapTool.createMap();

            //加载WMS世界底图

            opts={};
            opts.transparent= "true";
            opts.zIndex = 1;
            mapTool.addTileWMSLayer(layerName,opts);

            opts.zIndex = 2;
            mapTool.addTileWMSLayer("JiaHeDC:ly_highway",opts);

            opts.zIndex = 3;
            mapTool.addTileWMSLayer("JiaHeDC:ly_railway",opts);

            var  regionId = custom_settings.main_search_region_id;
            var  regionCode = custom_settings.main_search_region_code;

            loadRegionLayer(regionId,regionCode );

        };


        var loadRegionLayer = function (regionId,regionCode) {


            mapTool.removeLayer(chinaLayer);
            var omsFilter={filter:"region_code='"+regionCode + "'",zIndex:5};
            chinaLayer = mapTool.filterWMSToVectorLayer(layerName,omsFilter);
            // chinaLayer = mapTool.addTileWMSLayer(layerName,{});


            chinaLayer.getSource().once("addfeature",function(){
                mapTool.moveToLayerFeature(chinaLayer,'region_code',regionCode);
            });
        };

        /**
         * 点击地图获取feature
         * 高亮选中数据
         * 加载缩略图
         */
        var selectStorageByFeature = function () {
            mapTool.map.on('click',function (event) {
                var selectFeature = mapTool.getVectorLayerFeature(event.pixel);
                if (selectFeature){
                    var storageId = selectFeature.getId();
                    $('#dataDiv span.data_img[storage_id='+storageId+']').click()
                }
            });
        };

        /**
         * 缩略图预览
         * 1.加载图层
         * 2.添加geometry
         */
        var dataPreviewFun = function(obj){
            var polygon = $(obj).attr("bbox");
            var storageId = $(obj).attr("storage_id");
            var tr = $(obj).parents('tr');
            var flag = $(tr).hasClass("preview");
            if(flag){ //移除图层
                $(tr).removeClass("preview");
                $(tr).find("img").eq(0).css("border","");//移除边框
                // mapTool.removeGeometry(storageId);
                mapTool.removeThumbnailLayer(storageId);
            }else{ //添加图层和显示缩略图
                $(tr).addClass("preview");
                $(tr).find("img").eq(0).css("border","1px solid red");//添加边框
                var imgUrl = $(obj).find("img").attr("src");
                var wkt_parser = new ol.format.WKT();
                var geom = wkt_parser.readGeometry(polygon);
                mapTool.addGeometry(polygon,storageId,{fillColor:"rgba(255,255,255, 0.1)",strokeColor:"#00f"});
                mapTool.moveToCenter(geom); //移动至中心点，并加载底图层级
                mapTool.showThumbnail(imgUrl,geom,storageId);
            }
        };


        /**
         * 隐藏搜索栏
         */
        var hideFun = function(){
            $("#formLabel").toggle({
                duration:1000

            });
            $("#datasearch").toggle({
                duration:1000

            })
        };

        /**
         * 添加搜索条件并进行检索
         */
        var addSearchFormFun = function(){
            //显示查询框
            $("#formDiv").fadeToggle({
                speed:"fast"
            });
        };

        /**
         * 根据搜索条件进行数据查询
         * 1.对选择条件进行判断
         * 2.组装“搜索条件”中的显示值
         * 3.组装“查询条件”并查询数据
         * 4.“搜索结果”区域显示数据
         * @version<1> 2018-02-28 lcw : Created.
         */
        var queryFun = function(){

            // var onTarget = $("#formDiv .btLabel").find(".on").attr("target")

            //remove thumbnail layers and vector layer`s features.
            mapTool.removeGeometrys();
            mapTool.removeThumbnailLayers();
            var param = {};  //查询条件对象
            var values = []; //数据关键字，搜索条件显示对象
            //获取查询条件
            param.bbox = null;
            param.areaId = null;
            param.areaCode = null;


            var satId = $("#satName ").val();  //卫星
            var sensorId = $("#sensName").multipleSelect('getSelects');  //传感器ID
            var sensorName = $("#sensName").multipleSelect('getSelects', 'text');  //传感器name
            var cloudPercent = $("#cloudPercent").val().trim() ; //云盖范围起
            // var cloudPercent2 = $("#cloudPercent2").val().trim(); //云盖范围止
            //var cloud = cloudPercent  + "至"+ cloudPercent2 + "之间";
            /*if(!cloudPercent || !cloudPercent2){
             cloud = "";
             }*/

            var sceneId = $("#sceneId").val(); //景序列号
            var productLevel = $("#productLevel").val(); //产品序列号


            var targetIsNull = checkTargetIsNull(); //判断选项卡对应的数据值是否为空
            if(targetIsNull.flag != undefined){ //若行政区或经纬度为空，则进行提示
                // alert(targetIsNull.msg);
                showDialog(targetIsNull.msg);
                return false;
            }

            var targetValue = getTargetValue(); //获取选项卡对应的数据值

            // var storageType = $("#dsType").val(); //数据类型
            var time = $("#dateScope").val(); //影像时间

            if(time == null || time == ""){
                //alert("请选择影像时间");
                showDialog("请选择影像时间");
                return false;
            }

            //时间
            var arr = time.split("至");
            var beginTime = arr[0].trim();
            var endTime = arr[1].trim();
            var regionCode = targetValue.areaCode;

            //组装查询条件
            if(targetValue.areaId != null){
                param.areaId = targetValue.areaId; //行政区
                values.push(targetValue.areaValue);
            }

            //经纬度查询
            if(targetValue.lat1 != null && targetValue.lat2 != null && targetValue.long1 != null && targetValue.long2 != null){

                //顺时针方向形成矩形区域（long1 lat1, long2 lat1, long2 lat2, long1 lat2, long1 lat1）
                param.bbox = targetValue.long1 + " " + targetValue.lat1 + ","
                    +  targetValue.long2 + " " + targetValue.lat1 + ","
                    +  targetValue.long2 + " " + targetValue.lat2 + ","
                    +  targetValue.long1 + " " + targetValue.lat2 + ","
                    +  targetValue.long1 + " " + targetValue.lat1;

                values.push("纬度上：" + targetValue.lat1 + ",经度左：" + targetValue.long1 + ",经度右：" + targetValue.long2 + ",纬度下：" + targetValue.lat2);

            }

            /*if (!!cloudPercent && !!cloudPercent2){
             param.cloudPercent1 = cloudPercent; //云盖范围
             param.cloudPercent2 = cloudPercent2;
             }*/
            /* if(cloud != ""){
             values.push(cloud);
             }*/

            //云量
            param.cloudPercent=cloudPercent;
            values.push("≤"+cloudPercent+"%");

            param.satellite = satId; //卫星
            values.push($("#satName").find("option:selected").text());


            if(sensorId != null && sensorId != ""){ //传感器
                param.sensor = sensorId.join(",");
                values.push(sensorName);
            }else{
                param.sensor = null;
            }

            // param.storageType = storageType; //数据类型
            // values.push($("#dsType").find("option:selected").text());


            param.beginTime = beginTime;
            param.endTime = endTime;
            values.push(beginTime+"至"+ endTime);
            /*if(beginTime != endTime){

             }*/

            param.sceneId = sceneId;
            param.productLevel = productLevel;


            //组装查询条件区域（navForm）
            setNavFormFun(values);
            //检索数据并组装“搜索结果”区域
            // queryData(param)
            //sateListGrid(param);

            //移动到选定区域
            // sateListGrid(param);
            $("#listGrid").jqGrid('setGridParam',{
                datatype:'json',
                postData:param,
                page:1
            }).trigger("reloadGrid");

            //移动地图到查询区域
            loadRegionLayer(param.areaId,regionCode);
        };


        /**
         * 根据选项卡选中情况，验证对应的数值内容是否为空
         * @returns  {flag:true,msg:'提示内容'}
         * @version<1> 2018-02-27 lcw : Created.
         */
        var checkTargetIsNull = function(){
            var targetNull = {};

            var _target = $(".btLabel .on").attr("target");
            if(_target == "xzq"){ //行政区
                var areaId = txtArea.getAttribute("regionId");  //行政区ID
                if(areaId == null || areaId == ""){
                    targetNull.flag = false;
                    targetNull.msg = "请选择行政区";
                }
            }else if(_target == "jwd"){ //经纬度
                var lat1 = $("#lat1").val().trim();
                var lat2 = $("#lat2").val().trim();
                var long1 = $("#long1").val().trim();
                var long2 = $("#long2").val().trim();
                if(lat1 == null || lat1 == "" || lat2 == null || lat2 == "" || long1 == null || long1 == "" || long2 == null || long2 == ""){
                    targetNull.flag = false;
                    targetNull.msg = "请输入经纬度坐标值";
                }
            }
            return targetNull;
        };

        /**
         * 获取三个选项卡分别对应的数值
         * @returns {areaId : #{areaId}, areaValue ：#{areaValue}} 或者 {lat1：#{lat1},long1：#{long1},long2：#{long2},lat2：#{lat2}}
         * @version<1> 2018-02-27 lcw : Created.
         */
        var getTargetValue = function(){
            var targetValue = {};
            var _target = $(".btLabel .on").attr("target");
            if(_target == "xzq"){ //行政区
                targetValue = {};
                var areaId = txtArea.getAttribute("regionId");  //行政区ID
                if(areaId == null || areaId == ""){
                    //alert("请选择行政区");
                    showDialog("请选择行政区");
                    return false;
                }


                targetValue.areaCode = txtArea.getAttribute("regioncode");
                targetValue.areaId = areaId;
                targetValue.areaValue = txtArea.value;
            }else if(_target == "jwd"){ //经纬度
                targetValue = {};
                var lat1 = $("#lat1").val().trim();
                var lat2 = $("#lat2").val().trim();
                var long1 = $("#long1").val().trim();
                var long2 = $("#long2").val().trim();
                targetValue.lat1 = lat1;
                targetValue.lat2 = lat2;
                targetValue.long1 = long1;
                targetValue.long2 = long2;
            }
            return targetValue ;
        };

        /**
         * 初始化查询
         * param: areaId = 中国
         *        dataCollection = GF1
         *        dataType = 原始数据
         *        beginTime = 2017-01-01
         *        endTime = 2017-01-31
         * @version<1> 2018-02-28 lcw : Created.
         */
        var queryInit = function(){
            var param = {};
            var values = [];
            param.areaId = custom_settings.main_search_region_id; //默认中国
            param.areaCode = custom_settings.main_search_region_code;
            param.satellite = "GF1"; //卫星
            // param.storageType = Dict_DataStorageType_Default; //数据类型Id
            param.cloudPercent=10;

            // var beginTime = custom_settings.main_search_beginTime;
            // // var endTime = "2018-12-31";
            // var endTime = new Date().Format('yyyy-MM-dd');

            var now = new Date();
            var endTime = now.Format('yyyy-MM-dd');
            var beginTime = new Date(now -365*24*60*60*1000).Format('yyyy-MM-dd');

            param.beginTime = beginTime;
            param.endTime = endTime;
            values.push(custom_settings.main_search_region_name);
            values.push("≤10%");
            values.push("GF1");
            // values.push("原始数据");
            values.push(beginTime+"至"+endTime);
            //values.push();

            $("#dateScope").val(beginTime + " 至 "+ endTime);

            txtArea.setAttribute("regionId",custom_settings.main_search_region_id);
            txtArea.setAttribute("chinaName",custom_settings.main_search_region_name);
            txtArea.setAttribute("regionCode",custom_settings.main_search_region_code);
            txtArea.value = custom_settings.main_search_region_name;

            //组装查询条件区域（navForm）
            setNavFormFun(values);
            //检索数据并组装“搜索结果”区域
            //queryData(param)
            chanageTableCss();
            //移动到选定区域
            sateListGrid(param);
        };


        /**
         * 取消按钮操作事件
         */
        var cancelFun = function(){
            $("#formDiv").fadeOut({speed:"fast"});
        };

        /**
         * 组装搜索条件区域
         * 1.若搜索条件区域已存在相同的name属性， 则先删除该项
         * 2.添加检索框中选择的所有条件
         */
        var setNavFormFun = function(values){
            $(".navForm").html(""); //清空
            var str = "";
            $.each(values,function(index, element){
                if(element.length<30){
                    var _str = "<span title='"+ element +"'>" + element + "</span>";
                    str += _str;
                }else{
                    var _str = "<span style='text-overflow: ellipsis;white-space:nowrap;overflow: hidden;width:300px;' title='"+element+"'>" + element + "</span>";
                    str += _str;
                }

            });
            $(".navForm").append(str);
            $("#formDiv").fadeOut({speed:"fast"});
        };

        /**
         * 动态更新我的数据列表的数值
         * @version<1> 2018-02-28 lcw : Created.
         */
        var updateMyDataNumFun = function(){
            var checkedNum = $(".dataContent .dataDiv").find("li").length-1;
            $(".dataCar span:first").html(checkedNum);
            //如果数值变为0时 自动收起
            if(checkedNum==0){
                $(".myDataFrame").fadeOut({},600);//将myDataFrame也要隐藏
                $(".dataContent").fadeOut({},600);
                $(".dataCar span:last-child").html("");
                $(".dataCar").fadeIn({},600);
            }
        };

        /**
         * 为我的数据列表组装每个Li的内容
         * @param element
         * @returns {string}
         * @version<1> 2018-02-28 lcw : Created.
         * @version<2> 2018-03-11 lcw : Modified
         *     添加移除功能
         */
        var makeLiForDataListFun = function(element){//<p><span class='data_satellite'>"+ index +"." + element.satellite  +"</span><span class='data_sensor'>"+ element.sensor  +"</span></p>

            var storage_id_css = "storageId" + element.storageId;
            var _li = "<li style='height:33px;' storage_id='"+ element.storageId +"' class='"+ storage_id_css +"' >";
            _li += "<span class='data_box'><input role='checkbox' type='checkbox'  class='myDataBox' /></span>";
           /* _li += "<span class='data_box'><span class='circle'></span></span>";*/
            // '<img src="'+ DATA_STORAGE.showThumbnail_url+'?storageId='+  rowObject.storageId + '&AccessToken=' + commons.getCookie("AccessToken") + '">' +
            _li += "<span style='text-align:center;height:100%;'><img style='width:24px;height:24px;margin-right: 10px;' src='"+ DATA_STORAGE.showThumbnail_url +"?storageId="+  element.storageId +"&AccessToken="+ commons.getAccessToken() +"'></span></span>";
            _li += "<span class='data_content'><p><span class='data_satellite'>" + element.satellite  +"</span><span class='data_sensor'>"+ element.sensor  +"</span><span class='dataTime'>"+ new Date(element.dataTime).Format('yyyy-MM-dd')  +"</span><span class='storageUrl' style='display:none;'>"+ element.storageUrl +"</span></p>";
            _li += "</span>";
            _li += "<span class='data_delete'><img src='images/public/Tdelete.png' title='移除'></span>";

            _li += "</li>";
            return _li;
        };

        /**
         * 我的数据列表点击事件
         * 若是展开状态，点击后则收起，若是收起状态，点击后则展开
         * @version<1> 2018-02-27 lcw : Created.
         */
        var myDataListFun = function(){
            $(".myDataFrame").fadeOut({},600);//将myDataFrame也要隐藏

            $(".myDataImg,.dataCar").on("click",function(){
                var myDataNum = $(".dataCar span:first-child").html();
                var _flag = $(".dataContent").css("display");
                if(Number(myDataNum) == 0 ){
                    if(_flag == "block"){ //展开状态

                        $(".myDataFrame").fadeOut({},600);//将myDataFrame也要隐藏
                        $(".dataContent").fadeOut({},600);
                        $(".dataCar span:last-child").html("");
                        $(".dataCar").animate({
                            right:'0px'
                        },600)
                    }
                    return false;
                }

                if(_flag == "none"){ //收起状态
                    $(".dataCar").fadeOut({},600);
                    $(".dataContent").show();
                    $(".myDataFrame").toggle({
                        duration:600
                    });
                }else{

                   /* $(".myDataFrame").fadeOut({},600);//将myDataFrame也要隐藏
                    $(".dataContent").fadeOut({},600);*/
                    $(".dataContent").toggle({
                        duration:600
                    });
                    $(".myDataFrame").toggle({
                        duration:600
                    });
                    $(".dataCar").fadeIn({},600);
                    $(".dataCar span:last-child").html("");
                    /*$(".dataCar").animate({
                        right:'0px'
                    },600);*/
                }
            });
        };

        /**
         * 我的需求列表勾选记录
         * @version<1> 2018-03-01 lcw : Created.
         */
        var selectDataFun = function(evt){
            var flag=$(evt).find(".myDataBox").prop("checked");
            $(evt).find(".myDataBox").prop("checked",!flag);
            setCheckedNumFun();
        };

        /**
         * 我的数据列表全选功能
         * @version<1> 2018-03-01 lcw:Created.
         */
        var allSelectFun = function(evt){
            $(".allSelect").trigger("check");
            if($(evt).prop("checked")){
                $(".dataContent .dataDiv").find(".myDataBox").prop("checked",true);
            }else{
                $(".dataContent .dataDiv").find(".myDataBox").prop("checked",false);
            }
            setCheckedNumFun();
        };


        /**
         * 设置“已选择num条数据”
         * @version<1> 2018-03-01 lcw:Created.
         */
        var setCheckedNumFun = function(){
            var checkedNum = $(".dataContent .dataDiv").find(".myDataBox:checked").length;
            if(checkedNum > 0){
                $(".addedNum").html("已选择" + checkedNum + "条数据");
                $(".bottom").find(".btn1").removeClass("btn_grey");
            }else{
                $(".addedNum").html("");
                $(".bottom").find(".btn1").addClass("btn_grey");
            }
            $(".addedNum").attr('data-num',checkedNum);
            //判断 如果已经勾选的数据和总数据数量相同 则将全选按钮勾选
            var totalNum = $(".dataContent .dataDiv").find("li").length-1;
            if(checkedNum==totalNum && checkedNum!=0){
                $(".allSelect").prop("checked",true);
            }else{
                $(".allSelect").prop("checked",false);
            }
        };


        /**
         * 移除我的数据列表的数据项
         *  1.移除数据项
         *  2.左侧对应的数据可重新添加，即更新“添加按钮”为可添加状态
         * @param evt
         * @version<1> 2018-03-11 lcw :Created.
         */
        var dataRemoveFun = function(evt){
            //从cookie中移除
            var myData=commons.getCookie("myData");
            var array=[];
            var exp = new Date();
            exp.setTime(exp.getTime() + 60 * 1000 * 30);//过期时间 30分钟
            if(myData!=null) {//如果我的数据列表不为空
                array = JSON.parse(myData);
                //array.remove($(evt).parent().attr("storage_id"));
                array.splice($.inArray($(evt).parent().attr("storage_id"),array),1);
                commons.setCookie("myData",JSON.stringify(array),exp.toGMTString());
                //commons.delCookie("myData")
            }


            var _storageId = $(evt).parent().attr("storage_id");
            $("#listGrid").find("tbody").find("tr").each(function () {
                if($(this).attr("id")==_storageId){
                    var tdArr = $(this).children();
                    tdArr.eq(13).find(".data_add").removeClass("added");
                    tdArr.eq(13).find(".data_add img").attr({
                        "src":"images/public/add.png",
                        "title":'添加至我的数据列表'
                    }).addClass("addBtn");
                }

            });

            $(evt).parent().remove();
            updateMyDataNumFun();
            setCheckedNumFun();

        };

        /**
         * 创建订单
         * @version<1> 2018-03-12 cxw:Created.
         */
        var createOrderFun = function(){
            if($(".addedNum").attr('data-num') > 0) {
                var dataHandleDialog = $('#createOrderDialog');
                dataHandleDialog.html('<form id="auditWord" style="width: 100%;height: 100px; display: table-cell; vertical-align: middle;text-align: center;float: left;">' +
                    '<div class="two_td1" style="width: 100%;height: 30px;display: inline-block;text-align: right;margin: 40px auto;">数据申请单<span style="color:red;display:none;">*</span>:'
                    +'<input type="file" id="uploadAuditForm" name="dataOrderFile" /></div></form>');
                dataHandleDialog.dialog({
                    autoOpen: false,
                    title: '创建数据订单',
                    height: 200,
                    width: 410,
                    modal: true,
                    buttons: {
                        "确认": function () {
                            var reportFile = $('#uploadAuditForm').val().trim();//报告文件
                            // if(reportFile == "" || reportFile == null){
                            //     PopWin.showMessageWin('请上传数据申请单')
                            //     return false;
                            // }
                            if (reportFile != "" && reportFile != null){
                                var suffix = reportFile.substring(reportFile.lastIndexOf("."), reportFile.length);
                                if(".doc" != suffix && ".docx" != suffix && ".pdf" != suffix && ".jpg" != suffix && ".png" != suffix){
                                    PopWin.showMessageWin('数据申请单文件类型必须为doc、docx、pdf、jpg、png其中之一');
                                    return false;
                                }
                            }

                            // }

                            var form  = $('#auditWord')[0];
                            var formdata = new FormData(form)
                            var selectedOrders = $('.dataDiv').find('.myDataBox:checked').parent().parent()
                            var storageIds = new Array();
                            var storageIdsList = [];
                            for (var i = 0; i < (selectedOrders.length); i++) {
                                var param = {};
                                var sid = selectedOrders[i].getAttribute('storage_id');
                                param.storageId = sid;
                               // storageIds[i] = param;
                                storageIdsList.push(param);
                                storageIds.push(sid);
                            }
                            if (storageIds.length > 0) {
                                var  storageIdList = JSON.stringify(storageIdsList)
                                formdata.append('storageIds',storageIdList);
                                formdata.append('orderType',order_orderType.SJ);
                                $.ajax({
                                    type : 'POST',
                                    url : ORDER_CONFIG.createOrder_url,
                                    data :formdata,
                                    //async : false,
                                    processData : false,
                                    contentType : false,
                                    cache : false,
                                    timeout : 600000,
                                    headers : {"AccessToken":commons.getAccessToken()},
                                    success : function(result){
                                        dataHandleDialog.dialog("close");
                                        if (result.flag) {
                                            //如果创建订单成功,并且数据处理按钮不显示时,删除myData中的选择数据
                                            if($("#dealBtn").css("display") == "none"){
                                                $.each(storageIds,function(index, ele){//循环我的数据列表勾选的值
                                                    $("#myDataList li").each(function(i, e){//循环我的数据列表所有值
                                                        if($(this).attr("storage_id") == ele){//找到勾选的li
                                                            dataRemoveFun($(this).children().eq(3));//找到勾选的li 下删除的span,然后移除
                                                        }
                                                    });
                                                });
                                            }
                                            PopWin.showMessageWin(result.msg);
                                            commons.loadAlarmMsgFun();//更新警告信息
                                        } else {
                                            PopWin.showMessageWin(result.msg);
                                        }
                                    },
                                    error:function(){
                                        dataHandleDialog.dialog("close");
                                        PopWin.showMessageWin("订单任务创建失败");
                                    }
                                });
                            }
                            else {
                                dataHandleDialog.dialog("close");
                                PopWin.showMessageWin("请选择数据");

                            }

                        },
                        "取消": function () {
                            $(this).dialog("close");
                        }
                    }
                });
                dataHandleDialog.dialog("open");
            }
            else {
                PopWin.showMessageWin("请选择数据");
            }
        };


        /**
         * 创建订单
         * @version<1> 2018-03-12 cxw:Created.
         */
/*
        var createOrderFun = function(){
            if($(".addedNum").attr('data-num') > 0) {
                var dataHandleDialog = $('#dataHandleDialog');
                dataHandleDialog.html("是否确认创建数据订单？");
                dataHandleDialog.dialog({
                    autoOpen: false,
                    title: '系统提示',
                    height: 160,
                    width: 410,
                    modal: true,
                    buttons: {
                        "是": function () {

                            var orderParam = {};
                            orderParam.orderType = order_orderType.SJ;

                            var selectedOrders = $('.dataDiv .selected').parent().parent();
                            var storageIds = [];
                            var orderDetailList = [];

                            for (var i = 0; i < (selectedOrders.length); i++) {
                                var param = {};
                                var sid = selectedOrders[i].getAttribute('storage_id');
                                param.storageId = sid;
                                 storageIds.push(sid);
                                orderDetailList.push(param);
                            }

                            orderParam.orderDetailList = orderDetailList;

                            if (orderDetailList.length > 0) {
                                var ajax = new BaseAjax();
                                ajax.opts.url = ORDER_CONFIG.createOrder_url;
                                ajax.opts.data = JSON.stringify(orderParam);
                                ajax.opts.contentType = "application/json";
                                ajax.opts.successFun = function (result) {
                                    if (result.flag) {
                                        //如果创建订单成功,并且数据处理按钮不显示时,删除myData中的选择数据
                                        if($("#dealBtn").css("display") == "none"){
                                            $.each(storageIds,function(index, ele){//循环我的数据列表勾选的值
                                                $("#myDataList li").each(function(i, e){//循环我的数据列表所有值
                                                    if($(this).attr("storage_id") == ele){//找到勾选的li
                                                        dataRemoveFun($(this).children().eq(3));//找到勾选的li 下删除的span,然后移除
                                                    }
                                                });
                                            });
                                       }
                                        PopWin.showMessageWin(result.msg);
                                        commons.loadAlarmMsgFun();//更新警告信息
                                    } else {
                                        PopWin.showMessageWin(result.msg);
                                    }
                                };
                                ajax.opts.errorFun = function () {
                                    PopWin.showMessageWin("订单任务创建失败");
                                };
                                ajax.run();
                            }
                            else {
                                PopWin.showMessageWin("请选择数据");
                            }
                            $(this).dialog("close");
                        },
                        "否": function () {
                            $(this).dialog("close");
                        }
                    }
                });
                dataHandleDialog.dialog("open");
            }
            else {
                PopWin.showMessageWin("请选择数据");
            }
        };
*/

        /**
         * 创建任务
         * @version <1> 2018-03-12 cxj：Created.
         */
        var createTask = function (handleMetaId) {
            var dataHandleDialog = $('#dataHandleDialog');
            dataHandleDialog.html("是否确认创建数据处理任务？");
            dataHandleDialog.dialog({
                autoOpen: false,
                title: '系统提示',
                height: 160,
                width: 410,
                modal: true,
                buttons: {
                    "是": function () {
                        var resultJson = {};

                        var bt = $('.btLabel').children();
                        for(i = 0;i < bt.length;i++){
                            if($(bt[i]).attr('target') == 'xzq' && $(bt[i]).hasClass('on')){
                                var txtArea = $('#txtArea');
                                resultJson.regionId = txtArea.attr('regionid');
                                resultJson.chinaName = txtArea.attr('chinaname');
                                break;
                            }
                        }



                        resultJson.storageType = 701; //原始数据
                        resultJson.handleStatus = 1001;
                        // resultJson.dataStatus = 1;
                        // resultJson.delFlag = 1;
                        resultJson.storageIdArray = [];
                        var dateArray = $('#dateScope').val().trim().split(' 至 ');
                        resultJson.startDate = dateArray[0];
                        resultJson.endDate = dateArray[1];
                        resultJson.relateTaskStorageList = [];
                        resultJson.taskType = 1

                        var spanArray = $('.dataDiv').find('.myDataBox:checked');
                        var satId = "";
                        var _flag = true;
                        $.each(spanArray,function(i,o){
                            var relateTaskStorage = {};
                            if(i == 0){
                                satId =$(o).parent().parent().find(".data_satellite").html();
                            }
                            var _satId = $(o).parent().parent().find(".data_satellite").html();
                            if(satId != _satId ){
                                _flag = false;
                            }
                            // resultJson.storageIdArray.push($(o).parent().parent().attr('storage_id'));
                            relateTaskStorage.filePathLink = $(o).parent().parent().find(".storageUrl").html();
                            relateTaskStorage.storageId = $(o).parent().parent().attr('storage_id');
                            resultJson.relateTaskStorageList.push(relateTaskStorage); //所选择的文件集合
                        });
                        if(!_flag){
                            PopWin.showMessageWin("请勾选同一颗卫星下的数据进行数据处理操作");
                            return false;
                        }

                        // resultJson.satId = satId;
                        resultJson.satName = satId;

                        // resultJson.satName = $('#satName option:selected').val();
                        // resultJson.satId = $('#satName option:selected').attr('data-id');

                        var ajax = new BaseAjax();
                        ajax.opts.url = HANDLE_DATA.saveHandleTask_url;
                        ajax.opts.data = JSON.stringify(resultJson);
                        ajax.opts.contentType = "application/json";
                        ajax.opts.successFun = function (result) {
                            if (result.flag) {
                                PopWin.showMessageWin("数据处理任务创建成功");


                                // $('#content,.rightMain').load(ALARM_CONFIG.alarm_page_url);
                                var aHrefObj = $('.userContent li').first().find('a');
                                aHrefObj.attr('data-url',project_path2 + 'index.html?resCode=WDSJCLRW');
                                aHrefObj.trigger('click');


                            } else {
                                PopWin.showMessageWin("数据处理任务创建失败");
                            }
                        };
                        ajax.opts.errorFun = function () {
                            PopWin.showMessageWin("数据处理任务创建失败");
                        };
                        ajax.run();
                        $(this).dialog("close");
                    },
                    "否": function () {
                        $(this).dialog("close");
                    }
                }
            });
            dataHandleDialog.dialog("open");
        };

        /**
         * 初始化数据处理按钮权限
         * @version<1> 2018-03-21 cxj:Created.
         * @version<2> 2018-04-16 lcw : 对按钮添加resCode属性，并根据用户信息和按钮resCode查询该用户是否具有对应的权限
         */
        var initCreateTaskBtnEvent = function(){
            var ajax = new BaseAjax();
            ajax.opts.url = RESOURCE_CONFIG.findButton_url;

            var permResource = {};
            permResource.resCode = $("#dealBtn").attr("res-code");
            ajax.opts.data=JSON.stringify(permResource);
            // ajax.opts.url = USER_CONFIG.getCurrentPerson_url;
            ajax.opts.contentType = "application/json";
            ajax.opts.successFun = function (result) {
                if (result.flag) {
                    $('#dealBtn').css('display','block');
                }
            };
            ajax.run();
        };

        //弹出框提示通用方法
        var showDialog=function(msg){
            var reloadDialog = $("#reloadDialog");
            $("#msg").html(msg);
            reloadDialog.dialog({
                autoOpen: false,
                title:'系统提示',
                height: 160,
                width: 410,
                modal: true,
                buttons:[
                    {
                        text:"确认",
                        click:function(){
                            $(this).dialog("close");
                        }
                    }
                ]
            });

            reloadDialog.dialog("open");
        };


        var showCookie=function(){
            var myData=commons.getCookie("myData");
            var array=[];
            if(myData!=null){//如果我的数据列表不为空
                array = JSON.parse(myData);
                var objArr = cookieIdsToObjForAjax(array);
                $.each(objArr, function(index, element){
                    var _data = makeLiForDataListFun(element);
                    $(".dataContent .dataDiv").append(_data);
                });
                updateMyDataNumFun();
            }

        };

        /**
         * 搜索结果列表点击事件
         * 若是展开状态，点击后则收起，若是收起状态，点击后则展开
         * @version<1> 2018-05-04 wl : Created.
         */
        var searchListFun = function(){
            $(".formLabel").fadeOut({},600);//将myDataFrame也要隐藏

            $(".dataSearch").on("click",function(){
                hideFun();
                var _flag = $(".dataContent").css("display");
                /* var myDataNum = $(".dataSearch span:first-child").html();
                 var _flag = $(".dataContent").css("display");
                 if(Number(myDataNum) == 0 ){
                     if(_flag == "block"){ //展开状态

                         $(".formLabel").fadeOut({},600);//将myDataFrame也要隐藏
                         $(".dataContent").fadeOut({},600);
                         $(".dataSearch span:last-child").html("");
                         $(this).animate({
                             right:'0px'
                         },600)
                     }
                     return false;
                 }*/

                if(_flag == "none"){ //收起状态
                    $(this).animate({
                        right:'380px'
                    },1000);
                    $(".dataSearch span:last-child").html("收起");
                    $(".dataContent").fadeIn({
                        background:"#fff"
                    },600);
                    $(".formLabel").fadeIn({//将myDataFrame也要隐藏
                        background:"#fff"
                    },600);
                }else{
                    $(".formLabel").fadeOut({},600);//将myDataFrame也要隐藏
                    $(".dataContent").fadeOut({},600);
                    $(".dataSearch span:last-child").html("");
                    $(this).animate({
                        right:'0px'
                    },600);
                }
            });
        };

        var drawFun = function(){
            var domObj = {};


            //精度1
            var long1 = document.getElementById("long1");
            domObj.long1 = long1;

            var lat1 = document.getElementById("lat1");
            domObj.lat1 = lat1;
            var long2 = document.getElementById("long2");
            domObj.long2 = long2;
            var lat2 = document.getElementById("lat2");
            domObj.lat2 = lat2;

            //支持Polygon,LineString, Recttangle
            mapTool.drawFeature("Rectangle",domObj);

        };




        init();
    });
