/**
 主页显示
 @version <1> 2017-11-02 Hayden : Created.
 **/
require(["jquery","ol","MapModule","RegionModule","echarts","jsgrid","dateUtil","jqueryUi","slider","UserModule","JHModule",'popWin',"pageM"],function($,ol,MapModule,RegionModule,echarts,jsgrid,dateUtil,jqueryUi,slider,UserModule,JHModule,popWin,pageM){
    require(["tableExporter"]);

    //获取页面控件对象
    var txtRegion = document.getElementById("txtRegion");
    var selDataset = document.getElementById("selDsIndex");
    var selDataType = document.getElementById("selDataType");
    var txtDate = document.getElementById("txtDate");
    var cboxGF = document.getElementById("isShowGF");
    var point = document.getElementById("datetimePoint");
    var txtStartDate = document.getElementById("txtStartDate"); //报告时间
    var txtEndDate = document.getElementById("txtEndDate");//报告时间

    var spanNickname = document.getElementById("spanNickname");
    var aElemQuit = document.getElementById("aElemQuit");
    var aExportData = document.getElementById("aExportData");
    var divMapSlider = document.getElementById("divMapSlider");

    var divReport = document.getElementById("divReport");
    var divChart = document.getElementById("divChart");
    var divCrop = document.getElementById("divCropList");

    var mapTool ;
    var parentLayer,childrenLayer,colorLayer,tifLayer,countryLayer;
    var myChart;
    var oldCrop;
    //将指定日期的数据解析为分析数据，格式为键值对 : { 区域ID，图例数据 }
    var levelData = {};
    //图层层级配置
    var layerIndex = {
        'bgLayer':1,
        'parentLayer':2,
        'gfLayer':3,
        'countryLayer':4,
        'tifLayer':95,
        'colorLayer':5,
        'childLayer':6,
        'railwayLayer':8,
        'highwayLayer':7
    };

    /**
     *初始化页面：
     *	1.从Cookie中获取用户的默认数据产品信息，并设置在相关控件中
     *	2.增加区域选择事件，
     * 		a. 通过区域选择将用户区域下的数据产品信息获取，并设置在相关控件中
     *		b. 重新加载数据
     *	3.增加数据集改变事件,重新加载数据
     *	4.增加数据源改变事件,重新加载数据
     *	5.增加时间改变事件 ,重新加载数据
     **/
    var init = function(){
        //设置用户默认数据集产品
        initUserDefaultProduct();
        txtRegion.onclick = changeRegionEvent;
        selDataset.onchange = changeDatasetEvent;
        selDataType.onchange = findDsDataTime;
        cboxGF.onchange = loadGFLayer;
        //退出事件
        aElemQuit.onclick = UserModule.UserUtil.quitLogin;
        aExportData.onclick = exportData;
        initMap();
        loadChartUI();
        loadTableUI();
        regionCloseFun("txtStartDate");//关闭区域,
        regionCloseFun("txtEndDate");//关闭区域,
    };

    /**
     * 登录成功后，根据用户加载默认的数据产品
     * @version <1> 2017-12-29 Hayden:Created.
     */
    var initUserDefaultProduct = function(){
        //设置用户默认数据集产品
        var userInfo = UserModule.UserUtil.getUserInfo();


        if(userInfo){
            if(userInfo.nickname){
                spanNickname.innerText=userInfo.nickname;
            }
            if(userInfo.defaultProduct){
                if(userInfo.defaultProduct.regionId){
                    txtRegion.value=userInfo.defaultProduct.chinaName;
                    txtRegion.setAttribute("regionId",userInfo.defaultProduct.regionId);
                    txtRegion.setAttribute("regionName",userInfo.defaultProduct.regionName);
                    txtRegion.setAttribute("level",userInfo.defaultProduct.level);
                    txtRegion.setAttribute("regionCode",userInfo.defaultProduct.regionCode);
                    //var sDate =userInfo.defaultProduct.startDate.split('-');
                    var sDate =userInfo.defaultProduct.endDate.split('-');
                    var dsId = userInfo.defaultProduct.dsId;
                    var  showDDate = sDate[0] + "-" +sDate[1]+"-"+sDate[2]
                    var  showSDate = sDate[0] + "-01-01"
                    if(dsId ==1805 || dsId == 1804)
                    {
                        //showDDate = sDate[0] + "-"+sDate[1]+"-31"
                        /*var days =dateUtil.getDaysNum(sDate[0],sDate[1]);
                        showDDate = sDate[0]+ '-' + sDate[1] + '-' + days;*/
                        showSDate = sDate[0]+ '-' + sDate[1] + '-01';
                    }
                    $('#txtStartDate').val(showSDate);
                    $('#txtEndDate').val(showDDate);
                    /*	var param = getParam();
                        param.startDate = showSDate;
                        param.endDate = showDDate;*/
                    /*$('#txtStartDate').val(userInfo.defaultProduct.startDate);
                    $('#txtEndDate').val(userInfo.defaultProduct.endDate);*/
                }
            }
        }
        else{
            location.href="login.html";
        }
        // loadDatasetList();
    };

    /**
     * 页面初始化地图
     *
     * @version <1> 2017-11-07 Hayden:Created.
     */
    var initMap = function(){
        var url = DS_Layer.Layer_Init_URL;
        mapTool = new MapModule.MapTool("divMap",{"maxZoom":12,"minZoom":3,"url":url});
        mapTool.createMap();

        //加载WMS世界底图
        var layerName = "JiaHeDC:ly_WORLD";
        // var opts={filter:"PARENT_ID IS  NULL"};
        var opts={'zIndex':layerIndex.bgLayer};
        mapTool.addTileWMSLayer(layerName,opts);

        /*		var railwayName = "JiaHeDC:ly_railway";
                mapTool.addTileWMSLayer(railwayName,{'zIndex':layerIndex.railwayLayer});

                var highwayName = "JiaHeDC:ly_highway";
                mapTool.addTileWMSLayer(highwayName,{'zIndex':layerIndex.highwayLayer});*/


        selectRegionToMap();
        loadPointDiglog();

    };

    /**
     * 区域选择，选择区域后需要更换数据集、分辨率、作物信息
     * 1.
     * @version <1> 2017-12-25 Hayden:Created.
     */
    var changeRegionEvent = function(){
        var param = getParam();
        var oldRegionId = param.regionId;
        var oldChinaName = param.chinaName;
        var oldRegionName = param.regionName;
        var oldRegionCode = param.regionCode;
        var oldLevel = param.level;
        var oldParentId = param.parentId;
        var opts = {url:User_Env.Region_URL,closeFun:function(){
                loadDatasetList(oldRegionId,oldChinaName,oldRegionName,oldRegionCode,oldLevel,oldParentId);
                /*	if(UserModule.UserUtil.getUserInfo().roleCode!='Role_Admin'&&UserModule.UserUtil.getUserInfo().roleCode!=null) {
                        loadDataTimeEvent();
                    }*/
                selectRegionToMap();
                //加载高清底图
                loadGFLayer();
            }};
        var regionSelector = new RegionModule.RegionSelector("txtRegion",opts);
        regionSelector.show();

    };

    /**
     * 根据区域，作物，数据集，分辨率查询时间
     * @version <1> 2018-07-05 cxw:Created.
     */
    var loadDataTimeEvent = function(){
        var param = getParam();
        var regionId =  param.regionId;
        var cropId = null;
        if(param.cropId!=undefined) {
            cropId = param.cropId;
        }
        var accuracyId = param.accuracyId;
        var dsId =  param.dsId;
        //加载数据集
        var opts = {
            url:User_Env.DataTime_URL,
            data:{regionId:regionId,cropId:cropId,dataTypeId:accuracyId,dsId:dsId},
            type:"GET",
            async:false
        };
        var ajax = new BaseAjax();
        ajax.opts = opts;
        ajax.opts.successFun = function(result){
            if(result.flag&&result.data!=null){
                var defaultProduct = UserModule.UserUtil.getUserInfo().defaultProduct;
                defaultProduct.startDate = result.data.startdate;
                defaultProduct.endDate = result.data.enddate;
                //var sDate = result.data.startdate.split('-');
                var sDate = result.data.enddate.split('-');
                /*var  showSDate = sDate[0] + "-" +sDate[1]+"-"+sDate[2]
                var  showDDate = sDate[0] + "-12-31"*/
                var  showDDate = sDate[0] + "-" +sDate[1]+"-"+sDate[2]
                var  showSDate = sDate[0] + "-01-01"
                if(dsId ==1805 || dsId == 1804)
                {
                    //showDDate = sDate[0] + "-"+sDate[1]+"-31"
                    /*var days =dateUtil.getDaysNum(sDate[0],sDate[1]);
                    showDDate = sDate[0]+ '-' + sDate[1] + '-' + days;*/
                    showSDate = sDate[0]+ '-' + sDate[1] + '-01';
                }
                $('#txtStartDate').val(showSDate);
                $('#txtEndDate').val(showDDate);
                param.startDate = showSDate;
                param.endDate = showDDate;
                /*	$('#txtStartDate').val(result.data.startDate);
                    $('#txtEndDate').val(result.data.startDate);
                    param.startDate = result.data.startDate;
                    param.endDate = result.data.startDate;*/
                dateUtil.oldEndDate = showDDate;
                findDsDataTime();
            }
        };
        ajax.opts.errorFun = function(){
            console.info("加载时间失败");
        };
        ajax.run();
    };

    /**
     * 关闭区域选择控件
     * @param regionIdName 区域ID
     * @version <1> 2018-07-04 cxw : created.
     */
    var regionCloseFun = function(regionIdName) {
        var txtArea = document.getElementById(regionIdName);
        txtArea.onclick = function () {
            var opts = {colNum: 3, width: 400};
            opts.url = User_Env.Region_URL;
            var regionSelector = new RegionModule.RegionSelector(regionIdName, opts);
            regionSelector.closeRegion();
            return true;
        }
    }

    /**
     * 区域改变事件:
     * 1. 加载区域数据集
     * 2. 根据数据集加载数据源（分辨率）与作物列表
     * 3. 加载后台各项数据
     * @version <1> 2017-12-25 Hayden:Created.
     * @version <2> 2018-2-7 cxw:update:权限判断，如果选择的区域没有数据权限则提示
     */
    var loadDatasetList = function(oldRegionId,oldChinaName,oldRegionName,oldRegionCode,oldLevel,oldParentId){

        var param = getParam();
        //如果区域没有发生变化，则不重新加载数据集
        var newRegionId = param.regionId;
        if(newRegionId == oldRegionId){
            return;
        }

        var oldDsId = param.dsId;
        //加载数据集
        var opts = {
            url:User_Env.Region_DsList_URL,
            data:{regionId:param.regionId},
            type:"GET"
        };
        var ajax = new BaseAjax();
        ajax.opts = opts;
        ajax.opts.successFun = function(result){
            if(result && result.data){
                var dsList = result.data;
                if(dsList.length==0) {
                    $("#txtRegion").val(oldChinaName);
                    $("#txtRegion").attr("regionid",oldRegionId);
                    $("#txtRegion").attr("regionname",oldRegionName);
                    $("#txtRegion").attr("regioncode",oldRegionCode);
                    $("#txtRegion").attr("level",oldLevel);
                    $("#txtRegion").attr("chinaname",oldChinaName);
                    $("#txtRegion").attr("parentid",oldParentId);
                    popWin.showMessageWin(param.chinaName+"区域没有数据权限,请联系管理员!");
                    return;
                }
                loadDatasetUI(dsList,oldDsId);
                changeDatasetEvent();
                /*if(UserModule.UserUtil.getUserInfo().roleCode!='Role_Admin'&&UserModule.UserUtil.getUserInfo().roleCode!=null) {
                 loadDataTimeEvent();
                 }*/
            }
        };
        ajax.opts.errorFun = function(){
            console.info("加载数据集失败");
        };
        ajax.run();
    };

    /**
     * 根据当前数据集，加载时间控件
     * @version <1> 2017-12-29 Hayden:Created.
     */
    var loadDateUI = function(){
        var param = getParam();
        var dsInfo = getDsInfo(param.dsCode);
        dateUtil.sameDate = dsInfo.sameDate;
        /*	var sDate =param.startDate.split('-');
            var dsId =param.dsId;
            var  showSDate = sDate[0] + "-" +sDate[1]+"-"+sDate[2]
            var  showDDate = sDate[0] + "-12-31"
            if(dsId ==1805 || dsId == 1804)
            {
                showDDate = sDate[0] + "-"+sDate[1]+"-31"
            }*/
        dateUtil.oldEndDate = param.endDate;
        dateUtil.showDoubleDate("txtStartDate","txtEndDate",param.startDate, param.endDate,findDsDataTime);
    };

    /**
     * 根据区域加载数据集列表
     * @version <1> 2017-12-29 Hayden:Created.
     */
    var loadDatasetUI = function(dsList,oldDsId){

        selDataset.innerHTML="";
        for(var i in dsList){
            var dsId = dsList[i].datasetId;
            var dsCode = dsList[i].datasetCode;
            var dsName = dsList[i].datasetName;
            var option = new Option(dsName,dsId);
            option.setAttribute("dsCode",dsCode);

            if(oldDsId == dsId){
                option.selected = true;
            }
            else if(oldDsId == undefined){
                var userInfo = UserModule.UserUtil.getUserInfo();
                if(userInfo!=undefined) {
                    if(userInfo.defaultProduct.dsId==dsId)
                    {
                        option.selected = true;
                    }
                }
            }
            selDataset.options.add(option);
        }
    };

    /**
     * 根据数据集加载分辨率与作物列表、及时间控件
     * @version <1> 2017-12-29 Hayden:Created.
     */
    var changeDatasetEvent = function(){
        var param = getParam();
        var oldDatatypeId = param.datatypeId;
        //重新加载时间控件
        loadDateUI();
        //加载分辨率与作物
        var opts = {
            url:User_Env.Region_Ds_CropList_URL,
            data:{regionId:param.regionId,dsId:param.dsId},
            type:'GET'
        };
        var ajax = new BaseAjax();
        ajax.opts = opts;
        ajax.opts.successFun = function(result){
            if(result && result.data){
                oldCrop = "";

                var cropList = result.data.cropList;
                loadCropUI(cropList);

                var dataTypeList = result.data.dataTypeList;
                loadDataTypeUI(dataTypeList,oldDatatypeId);
                if(UserModule.UserUtil.getUserInfo().roleCode!='Role_Admin'&&UserModule.UserUtil.getUserInfo().roleCode!=null) {
                    loadDataTimeEvent();
                }
                else if(UserModule.UserUtil.getUserInfo().roleCode=='Role_Admin'){
                    findDsDataTime();
                }
            }
        };
        ajax.opts.errorFun = function(){
            console.info("加载数据集分辨率与作物失败.");
        };
        ajax.run();
    };

    /**
     * 查询分布、估产数据时间点，并加载数据
     */
    var findDsDataTime = function () {
        var param = getParam();
        var dsInfo = getDsInfo(param['dsCode']);

        //删除图层
        deleteAllLayer();

        //加载数据集图地图图例
        loadLayerLegend(param);

        //清空格式化数据
        levelData = {};

        var pointJquery = $('.point');
        if(dsInfo.hasOwnProperty('FindDatetimePoint')){
            //加载时间点的数据
            pointJquery.css('display','');
            var opts = {
                url:dsInfo.FindDatetimePoint,
                data:{
                    regionId:param.regionId,
                    cropId:param.cropId,
                    startDate:param.startDate,
                    endDate:param.endDate,
                    resolution:param.accuracyId
                },
                type:'GET'
            };
            var ajax = new BaseAjax();
            ajax.opts = opts;
            ajax.opts.successFun = function(result){
                loadDatetimeOption(result.data);
                loadData();
            };
            ajax.opts.errorFun = function(){
                console.info("时间点加载失败.");
            };
            ajax.run();
        } else {
            pointJquery.css('display','none');
            //不需要查询数据时间点
            loadData();
        }
    }



    /**
     * 分布、估产数据时间点点击事件
     */
    var changeDsDataTime = function () {
        var param = getParam();
        var dsInfo = getDsInfo(param['dsCode']);

        //删除图层
        deleteAllLayer();

        //加载数据集图地图图例
        loadLayerLegend(param);

        //清空格式化数据
        levelData = {};

        loadData();
    }


    /**
     * 根据dataTypeList数据，在UI中加载分辨率
     * @version <1> 2017-12-29 Hayden:Created.
     * @version <2> 2018-2-6 cxw update:
     * 1.var datatypeName = dataTypeList[i].datatypeName 改为 var datatypeValue = dataTypeList[i].datatypeValue
     * 2.var option = new Option(datatypeName,datatypeId)改为var option = new Option(datatypeValue,datatypeId)
     */
    var loadDataTypeUI = function(dataTypeList,oldDatatypeId){
        selDataType.innerHTML="";
        for(var i in dataTypeList){
            var accuracyId = dataTypeList[i].accuracyId;
            var accuracyCode = dataTypeList[i].accuracyCode;
            var accuracyValue = dataTypeList[i].accuracyValue;
            var accuracyName = dataTypeList[i].accuracyName;
            //var option = new Option(accuracyValue,accuracyId);
            var option = new Option(accuracyName,accuracyId);
            option.setAttribute("accuracyCode",accuracyCode);

            if(oldDatatypeId == accuracyId){
                option.selected = true;
            }
            else if(oldDatatypeId == undefined){
                var userInfo = UserModule.UserUtil.getUserInfo();
                if(userInfo!=undefined) {
                    if(userInfo.defaultProduct.accuracyId==accuracyId)
                    {
                        option.selected = true;
                    }
                }
            }
            selDataType.options.add(option);
        }
    };

    /**
     * 加载数据时间点
     * @param datetimeList
     */
    var loadDatetimeOption = function (datetimeList) {
        point.innerHTML="";
        if(datetimeList && datetimeList.length > 0){
            for(var i in datetimeList){
                var datetime = datetimeList[i];
                var option = new Option(datetime,datetime);
                point.options.add(option);
            }
            point.onchange = changeDsDataTime;
        } else {
            var option = new Option("没有时间点数据","");
            point.options.add(option);
        }

    };

    /**
     * 获取区域作物列表数据
     * @param cropList : 作物列表数据
     * @version <1> 2017-11-10 Hayden:Created.
     */
    var loadCropUI = function(cropList){
        divCrop.innerHTML="";
        if(cropList.length>0){
            $('#divCrop').show();
            $('#divCrop').removeClass("slide_module")
            //divCrop = document.getElementById("divCrop");
            if(cropList.length >4 ){
                $('.cropList').show()
                divCrop = document.getElementById("divCropList");
                $('#divCrop').addClass("slide_module")
                $('#down').hide();
            }
            else{
                $('.cropList').hide();
            }

            var ul = document.createElement("ul");
            for(var i=0;i<cropList.length;i++){
                li = document.createElement("li");
                li.innerHTML="<img style='pointer-events: none;' width=40 height=40 src='images/crop/"+cropList[i].cropCode+".png'  >";
                li.setAttribute("cropCode",cropList[i].cropCode);
                li.setAttribute("cropName",cropList[i].cropName);
                li.setAttribute("cropId",cropList[i].cropId);
                if(i==0) li.className="active";
                li.onclick=function(evt){
                    var evtTarget = event.target || event.srcElement;
                    var cropCode = evtTarget.getAttribute("cropCode");

                    for(var i=0;i<ul.childNodes.length;i++){
                        var tempCode = ul.childNodes[i].getAttribute("cropCode");
                        if(cropCode != tempCode) {
                            ul.childNodes[i].className = "";
                        }
                        else {
                            if(oldCrop == cropCode){
                                return;
                            }
                            ul.childNodes[i].className = "active";
                            oldCrop = cropCode;
                        }
                    }
                    if(UserModule.UserUtil.getUserInfo()!=undefined) {
                        if (UserModule.UserUtil.getUserInfo().roleCode != 'Role_Admin' && UserModule.UserUtil.getUserInfo().roleCode != null) {
                            loadDataTimeEvent();
                        }
                        else if (UserModule.UserUtil.getUserInfo().roleCode == 'Role_Admin') {
                            findDsDataTime();
                        }
                    }
                    else{
                        location.href="index.html"
                    }
                };
                ul.appendChild(li);
            }
            divCrop.appendChild(ul);
        }
        else{
            $('#divCrop').hide();
        }

    };

    /*======================================================================*/
    /*						地图操作										*/
    /*======================================================================*/

    /**
     * 1. 区域选择后，地图移到区域中心点
     * 2. 区域选择后，地图加载区域的下一级区域图层
     * @version <1> 2017-11-07 Hayden:Created.
     *
     * 3. 加载省级图层
     * @version <1> 2018-01-16 XuZhenguo:Created.
     */
    var selectRegionToMap = function(){
        //从ly_world矢量图层中查找regionId的值，如果有，则移动并显示，
        //如果没有，则从世界底图WMS中获取feature,并加入到ly_world中
        var param = getParam();
        var regionId = param.regionId;
        var regionCode = param.regionCode;
        var level = param.level;
        var layerName = "JiaHeDC:ly_WORLD";

        var parentStyle = MapModule.MapTool.setLayerStyle({'fillColor':"rgba(255,255,255, 0.1)",'strokeColor':"#fff"});
        var opts = {'filter':"region_code='"+regionCode+"'",'style':parentStyle,'zIndex':layerIndex['parentLayer']};
        mapTool.removeLayer(parentLayer);



        //构建矢量边界，将中心点移动至所选区域的中心点，并加载数据
        parentLayer = mapTool.filterWMSToVectorLayer(layerName,opts);
        parentLayer.getSource().once("addfeature",function(){
            mapTool.moveToLayerFeature(parentLayer,"region_code",regionCode);
            loadDatasetList();
        });


        // //如果没有，则从世界底图WMS中获取feature,并加入到ly_world中
        // var param = getParam();
        // var regionId = param.regionId;
        // var regionCode = param.regionCode;
        // var level = param.level;
        // var layerName = "JiaHeDC:ly_WORLD";
        // //从WMS中过滤区域下一级区域列表，并转化为矢量图层。
        // level = parseInt(level)+1;
        // var nextRegionStrokeColors = ["#ccc","#ccc","#ccc","#ccc","#ccc"];
        // var strokeColor = nextRegionStrokeColors[level-1];
        // var filter = "parent_id='"+regionId+"' and level='"+level+"'";
        // var style = MapModule.MapTool.setLayerStyle({'strokeColor':strokeColor,'textSize':'15px'});
        //
        // countryLayer = mapTool.filterWMSToImageLayer(layerName,{'filter':filter,'style':style,'opacity':0.5,'zIndex':layerIndex['countryLayer']});
    };



    /**
     * 动态改变地图样式
     *
     * @version <1> 2018-01-05 XuZhenguo:Created.
     *
     * @version<2> 2018-08-24 lcw : updated.
     *   1.重构该方法实现如下内容：
     *      a.勾选“卫星图”，加载矢量图层，并填充样式
     *      b.如果当前作物是降雨和地温数据，则不加载矢量图层和样式，直接跳出该方法。
     */
    var loadGFVectorFun = function(){

        var param = getParam();
        var dsCode = param.dsCode;
        //是否显示卫星图
        var isShowGF = cboxGF.checked;

        if(  dsCode == DS_T.name  || dsCode == DS_Trmm.name){
            return ;
        }


        //降雨与地温情况处理
        if(isShowGF){
            addVectorTLayer()   //加载所选区域的矢量边界，并配置样式
            setChildStyle();
        }else{
            mapTool.removeLayer(childrenLayer);

            mapTool.removeLayer(countryLayer);
        }

    };


    /**
     * 根据当前选择区域，构造矢量边界，并加载至底图上
     * @version<1> 2018-08-24 lcw :Created.
     */
    var addVectorTLayer = function(){
        //如果没有，则从世界底图WMS中获取feature,并加入到ly_world中
        var param = getParam();
        var regionId = param.regionId;
        var regionCode = param.regionCode;
        var level = param.level;
        var layerName = "JiaHeDC:ly_WORLD";
        //从WMS中过滤区域下一级区域列表，并转化为矢量图层。
        level = parseInt(level)+1;
        var nextRegionStrokeColors = ["#ccc","#ccc","#ccc","#ccc","#ccc"];
        var strokeColor = nextRegionStrokeColors[level-1];
        var filter = "parent_id='"+regionId+"' and level='"+level+"'";
        var style = MapModule.MapTool.setLayerStyle({'strokeColor':strokeColor,'textSize':'15px'});
        mapTool.removeLayer(childrenLayer);
        mapTool.removeLayer(countryLayer)
        //根据regionId构建对应区域图层的矢量边界
        childrenLayer = mapTool.filterWMSToImageLayer(layerName,{'filter':filter,'style':style,'opacity':1,'zIndex':layerIndex['childLayer']});
        countryLayer = mapTool.filterWMSToImageLayer(layerName,{'filter':filter,'style':style,'opacity':0.5,'zIndex':layerIndex['countryLayer']});

    }


    /**
     * 对当前选中区域的矢量边界添加样式
     * @version<1> 2018-08-24 lcw :Created.
     */
    var setChildStyle = function(){
        var param = getParam();
        var dsCode = param.dsCode;
        // var styleFun = childrenLayer.getSource().getStyle();
        var childLayer_style = MapModule.MapTool.setLayerStyle({'strokeColor':'#fff','font_color':'#fff','textSize':'15px'});
        var text_fillColor = '#fff';
        var stokeColor = '#fff';

        childLayer_style.getText().setFill(new ol.style.Fill({'color': text_fillColor}));
        var stroke = new ol.style.Stroke();
        stroke.setColor(stokeColor);
        childLayer_style.setStroke(stroke);
        var countryLayer_style = MapModule.MapTool.setLayerStyle({'strokeColor':'#fff','font_color':'#fff','textSize':'15px'});


        childrenLayer.getSource().setStyle(getLayerStyleFun(childLayer_style));


        // if (!isShowGF) {
            countryLayer_style = MapModule.MapTool.setLayerStyle({'strokeColor':'#ccc','font_color':'#ccc','textSize':'15px'});
        // }
         countryLayer.getSource().setStyle(getLayerStyleFun(countryLayer_style));
    }



    /**
     *图层显示函数
     *@version <1> 2018-01-16 XuZhenguo:Created.
     */
    var getLayerStyleFun = function(layerStyle){
        var layer_styleFun = function(feature,resolution){
            if(resolution<=((5-feature.get('level'))/100)){
                var chinaName = feature.get("china_name");
                var name = feature.get("region_name");
                name = chinaName!="" ?chinaName:name;
                var geom = feature.getGeometry();
                layerStyle.getText().setText(name);
            }else{
                layerStyle.getText().setText("");
            }
            return layerStyle;
        }
        return layer_styleFun;
    }


    /**
     * 显示滑块
     * @version <1> 2017-12-04 XuZhenguo : created.
     */
    var showSlide = function(gridData){
        var param = getParam();
        var dsInfo = getDsInfo(param.dsCode);
        var dateArray = [];
        var startDate = new Date(param['startDate']);
        var endDate = new Date(param['endDate']);
        while(startDate <= endDate){
            dateArray.push(startDate.Format("yyyy-MM-dd").substring(5));
            startDate = new Date(startDate).addDay(1);
        }

        $("#divMapSlider").slider({
            animate:true,
            max:dateArray.length-1,
            change:function(event,ui){
                var sliderDate = dateArray[ui.value];
                //在表格数据中，查找指定日期的数据
                var dateDataArray = getTableDataByDay(gridData,sliderDate);
                //判断是加载色块，还是加载滑块指定天的图层
                if(dsInfo.Is_ColorBlock_Layer){
                    //加载指定天数据表示的图层
                    loadColorLayer(dateDataArray,dsInfo.Map_Legend);
                }
                // 如果不加载色块，要加载图层时，要修改这里: to do
                // else{
                // 	//加载指定天的图层
                // 	param.startDate = SLID
                // 	loadDsLayer(param);
                // }
            }
        }).slider('value',0)
            .slider("pips", {
                first:'pip',
                last:'pip',
                rest: "pip",
                labels:dateArray
            }).slider("float",{pips:true,labels:dateArray});
    };

    /**
     *获取table 列表中 莫一天的数据
     *@version <1> 2018-01-18 XuZhenguo : created.
     */
    var getTableDataByDay = function(tableDataList,date){
        var dateDataArray = [];
        if (tableDataList && date && tableDataList.length>0) {
            for(var i in tableDataList){
                if (tableDataList[i] && tableDataList[i]['date'] && tableDataList[i]['date'].indexOf(date) != -1) {
                    dateDataArray.push(tableDataList[i]);
                }
            }
        }
        return dateDataArray;
    }

    /**
     *数据处理 ，并加载色块图层
     * 1、在表格数据中，找出指定天的数据
     * 2. 数据处理 ，处理后格式 {regionId:{LV:xxx,value:xxx,color:xxx,lvName:xxx}.......}
     * 3. 遍历数据集图例，构造色块图层的样式
     * 4. 根据图层样式与数据，构造色块图层
     *@version <1> 2017-11-24 XuZhenguo:Created.
     */
    var loadColorLayer = function(dateDataArray,map_Legend){
        //构造当前色块显示的数据
        buildCurrentLayerData(dateDataArray,map_Legend);

        //遍历图例，构造feature样式
        var lvStyle = {};
        for(var x in map_Legend){
            lvStyle[map_Legend[x][0]] = MapModule.MapTool.setLayerStyle({fillColor:map_Legend[x][1]});
        }

        //需等待 childrenLayer 加载完成， 延迟加载色块图层
        // window.setTimeout(function(){
        // 	colorLayer = mapTool.addColorLayer(childrenLayer,lvStyle,levelData,layerIndex['colorLayer']);
        // },1000);


        mapTool.removeLayer(childrenLayer); //移除矢量边界
		mapTool.removeLayer(countryLayer);
        //数据不为空时，加载对应的图层及样式
        if(!$.isEmptyObject(levelData)){
            addVectorTLayer()   //加载所选区域的矢量边界，并配置样式
            setChildStyle();  //配置样式
        }


        if(childrenLayer != null){
            childrenLayer.getSource().getSource().once('change',function(event){
                colorLayer = mapTool.addColorLayer(childrenLayer,lvStyle,levelData,layerIndex['colorLayer']);
            });
            colorLayer = mapTool.addColorLayer(childrenLayer,lvStyle,levelData,layerIndex['colorLayer']);
        }
    };

    /**
     * 为数据集在地图增加图例
     * @version <1> 2018-01-02 Hayden:Created.
     */
    var loadLayerLegend = function(param){
        var dsInfo = getDsInfo(param.dsCode);
        var legendTitle = document.getElementById('legendTitle');
        if(dsInfo.Is_ColorBlock_Layer){
            var legend = dsInfo.Map_Legend;
            //加载图例
            mapTool.repaintLegend(legend);
            if(!!legendTitle){
                legendTitle.innerText = legendTitle.innerText + (!dsInfo.unit ? '' : '('+ dsInfo.unit + ')');
            }
        }
        else{
            if (legendTitle){
                legendTitle.parentNode.innerHTML ="";
            }
        }
    };

    /**
     * 加载数据集图层
     * @version <1> 2018-01-02 Hayden:Created.
     */
    var loadDsLayer = function(param){

        deleteAllLayer();
        var codeSplit = param.regionCode.split("-");
        if (codeSplit.length > 2) {
            param.regionCode = codeSplit[0] + "-" + codeSplit[1];
        }
        var ajax = new BaseAjax();
        ajax.opts.url =  DS_Layer.Layer_Query_URL;
        ajax.opts.type = "GET";
        ajax.opts.data = param;
        ajax.opts.contentType = "application/json";
        ajax.opts.successFun = function(result){
            if(result.flag){
                var layerName = result.data.name;
                // tifLayer = mapTool.addWMSLayer(layerName,{'zIndex':layerIndex['tifLayer']});
                tifLayer = mapTool.addWMSLayer(layerName,{'zIndex':layerIndex['tifLayer'],'extent':parentLayer.getSource().getFeatures()[0].getGeometry().getExtent()});
            }
        };
        ajax.opts.errorFun = function(){
            console.info("load ds layer error");
        };
        ajax.run();
    };

    /**
     *鼠标移动事件 显示提示框
     *@version <1> 2017-11-27 XuZhenguo:
     */
    var loadPointDiglog = function(){
        mapTool.map.on('pointermove',function(event) {
            // var dsId = selDataset.options[selDataset.options.selectedIndex].value;
            var dsCode,dsName;
            if (selDataset.options[selDataset.options.selectedIndex]) {
                dsCode = selDataset.options[selDataset.options.selectedIndex].getAttribute("dscode");
                dsName = selDataset.options[selDataset.options.selectedIndex].text;
            }
            var unit = getDsInfo(dsCode).unit;
            var feature = mapTool.getFeatureByClick(event.pixel, childrenLayer);
            if (feature && levelData) {
                var colorFeature = mapTool.getFeatureByClick(event.pixel, colorLayer);
                if (!!colorFeature && !!colorFeature.getStyle()) {
                    //色块高亮显示
                    var colorStyle = colorFeature.getStyle().clone();
                    // colorStyle.getStroke().setColor('#f7370c');
                    // colorStyle.getStroke().setWidth(2);
                    mapTool.highFeature(colorFeature, colorStyle);
                }
                var chinaName = feature.get('china_name');
                var content = chinaName + "  ?lvName  <br/> 当前" + dsName + " : ?lvValue(" + unit + ")";
                var regionCode = feature.get('region_code');
                var areaLevel = levelData[regionCode];
                if (!!areaLevel) {
                    //分布与估产单位换算(原始单位亩,吨 换算为万亩,万吨)
                    var levelValue = areaLevel.value;
                    content = content.replace('?lvName', areaLevel['lvName'] || '');
                    content = content.replace('?lvValue', (!areaLevel.value && areaLevel.value != 0) ? "没有数据" : levelValue);
                    mapTool.infoDlgShow(event.coordinate, content);
                }
            } else {
                mapTool.infoDlgClose();
            }

        });
    };

    /**
     * 根据区域、加载高分卫星底图
     * @version <1> 2018-01-03 XuZhenguo:Created.
     */
    var gfLayer;
    var loadGFLayer = function(){
        var isShowGF = cboxGF.checked;
        mapTool.removeLayer(gfLayer);
        if (isShowGF) {
            var gfLayerName = 'JiaHeDC:ly_WORLD_GF';
            gfLayer = mapTool.addTileWMSLayer(gfLayerName,{'zIndex':layerIndex['gfLayer']});
        }

        loadGFVectorFun();
        // if (!!gfLayer) {
        // 	gfLayer.getSource().on('tileloadend',function(){
        // 		setChildLayerStyle();
        // 	});
        // }

    }

    /**
     * 删除所有的 色块 和 tif 图层
     * @version <1> 2018-01-05 XuZhenguo:Created.
     */
    var deleteAllLayer = function(){
        //删除色块图层
        mapTool.removeLayer(colorLayer);
        //删除分布图层
        mapTool.removeLayer(tifLayer);
    }

    /*======================================================================*/
    /*						报告操作										*/
    /*======================================================================*/

    /**
     * 根据数据集、区域、日期、作物加载最新报告
     * @param param : 键值对
     dsCode : 数据集代码
     regionId: 区域ID
     startDate: 数据查询时间
     cropCode : 作物代码
     * @version <1> 2017-11-10 Hayden:Created.
     */
    /*	var loadReportList = function(param){
            var reportList = [];
            var dsInfo = getDsInfo(param.dsCode);
            var url = dsInfo.Briefing_URL;
            var ajax = new BaseAjax();
            ajax.opts.url = url;
            ajax.opts.data = param;
            ajax.opts.type = "GET";
            ajax.opts.contentType = "application/json";
            ajax.opts.successFun = function (result) {
                if (result.flag) {
                    if(result.data && (result.code=='trmm'||result.code=='t')){
                        //loadBriefingUI(result,param);
                        $('#divReportList').hide();
                        $('#divReport').show();
                        $('#divReport').html(result.data);
                    }
                    else if((result.data==null||result.data=='')&& (result.code=='trmm'||result.code=='t')){
                        var content = '<P align="center" style="font-size: 115%;" id="tle">最新报告</P><P align="center">暂无报告</P>';
                        $('#divReportList').hide();
                        $('#divReport').show();
                        $('#divReport').html(content+"</p>");
                    }
                    else{
                        reportList = result.data;
                        if(reportList.length>0) {
                            $('#divReportList').show();
                            $('#divReport').hide();
                            //loadReportUI(reportList);
                            loadReportTableList();
                        }
                        else{
                            var content = '<P align="center" style="font-size: 115%;" id="tle">最新报告</P><P align="center">暂无报告</P>';
                            $('#divReportList').hide();
                            $('#divReport').show();
                            $('#divReport').html(content+"</p>");
                        }
                    }
                }
                else {
                    var content = '<P align="center" style="font-size: 115%;" id="tle">最新报告</P><P align="center">暂无报告</P>';
                    $('#divReportList').hide();
                    $('#divReport').show();
                    $('#divReport').html(content+"</p>");
                }
            };
            ajax.opts.errorFun = function () {
                var content = '<P align="center" style="font-size: 115%;" id="tle">最新报告</P><P align="center">暂无报告</P>';
                $('#divReportList').hide();
                $('#divReport').show();
                $('#divReport').html(content+"</p>");
            };
            ajax.run();
        };*/


    /**
     * 根据数据集、区域、日期、作物加载最新报告
     * @param param : 键值对
     dsCode : 数据集代码
     regionId: 区域ID
     startDate: 数据查询时间
     cropCode : 作物代码
     * @version <1> 2018-07-08 cxw:Created.
     */
    var loadReportList = function(param){
        var reportList = [];
        var dsInfo = getDsInfo(param.dsCode);
        var url = dsInfo.Briefing_URL;
        var ajax = new BaseAjax();
        ajax.opts.url = url;
        ajax.opts.data = param;
        ajax.opts.type = "GET";
        ajax.opts.contentType = "application/json";
        ajax.opts.successFun = function (result) {
            if (result.flag) {
                if(result.data && (result.code=='trmm'||result.code=='t')){
                    //loadBriefingUI(result,param);
                    $('#divReportList').hide();
                    $('#divReport').show();
                    $('#divReport').html(result.data);
                }
                else if((result.data==null||result.data=='')&& (result.code=='trmm'||result.code=='t')){
                    var content = '<P align="center" style="font-size: 115%;" id="tle">最新报告</P><P align="center">暂无报告</P>';
                    $('#divReportList').hide();
                    $('#divReport').show();
                    $('#divReport').html(content+"</p>");
                }
            }
            else {
                var content = '<P align="center" style="font-size: 115%;" id="tle">最新报告</P><P align="center">暂无报告</P>';
                $('#divReportList').hide();
                $('#divReport').show();
                $('#divReport').html(content+"</p>");
            }
        };
        ajax.opts.errorFun = function () {
            var content = '<P align="center" style="font-size: 115%;" id="tle">最新报告</P><P align="center">暂无报告</P>';
            $('#divReportList').hide();
            $('#divReport').show();
            $('#divReport').html(content+"</p>");
        };
        ajax.run();
    };

    /**
     * 获取最新报告数据
     * @param reportList : 报告数据
     * @version <1> 2017-11-10 Hayden:Created.
     */
    var loadReportUI = function(reportList){
        //divReport.innerHTML="";
        var ul = document.createElement("ul");
        var li = document.createElement("li");
        li.innerHTML="最新报告";
        li.className="title";
        ul.appendChild(li);
        for(var i=0;i<reportList.length;i++){
            li = document.createElement("li");
            li.innerHTML=reportList[i].reportName;
            li.title = reportList[i].reportName;
            var reportName = reportList[i].reportName;
            li.setAttribute("reportId",reportList[i].reportId);
            li.onclick=function(evt){
                var evtTarget = event.target || event.srcElement;
                var key = COOKIE_CONFIG.cookieName;
                var accessToken = JHModule.CookieUtil.getCookie(key);
                var download_url = DS_Report.REPORT_PREVIEW_URL +  "?reportId="+ encodeURIComponent(evtTarget.getAttribute("reportId"))  +"&AccessToken="+accessToken;
                previewFun(reportName,evtTarget.getAttribute("reportId"));
                //window.open(download_url);
            };
            ul.appendChild(li);
        }
        $("#divReport").on("click","li",function(){
            $('#divReport li').removeClass("active")
            $(this).addClass("active");
        });
        divReport.appendChild(ul);
    };


    /**
     * 分页加载报告数据
     *
     * @version <1> 2018-07-08 cxw : created.
     */
    var loadReportTableList = function(pageNum, pageSize){
        if(pageNum instanceof Object){
            pageNum = 1;
        }
        if(pageSize instanceof Object){
            pageSize = 1;
        }
        var param = getParam();  //获取参数
        param["pageNum"] = pageNum == undefined ? 1 : pageNum;
        param["pageSize"] = pageSize == undefined ? 10 : pageSize;

        var permissionAjax = new BaseAjax();
        permissionAjax.opts.url = DS_Report.Report_list_url;
        permissionAjax.opts.contentType = "application/json";
        permissionAjax.opts.async = false;  //同步请求
        permissionAjax.opts.data = param;
        permissionAjax.opts.type = "GET";
        permissionAjax.opts.successFun = function(data){

            var temps = data.list;
            if(temps != null && temps.length > 0){
                var reportUl =document.getElementById('report_list');
                reportUl.innerHTML='';
                var li =  "";
                $('#divReportList').show();
                $('#divReport').hide();
                var key = COOKIE_CONFIG.cookieName;
                var accessToken = JHModule.CookieUtil.getCookie(key);
                var result="";
                var a = 0;
                $.each(temps, function (index, obj) {
                    li = document.createElement("li");
                    /*var download_url = DS_Report.REPORT_DOWNLOAD_URL +  "?reportId="+ encodeURIComponent(obj.reportId)  +"&AccessToken="+accessToken;
                    var url = "js/lib/pdfjs/web/viewer.html?" + download_url;
                    a= a+1;
                    result += '<li  id=reportLi'+a+' reportId ='+obj.reportId+' style="text-align:left;overflow-x:hidden;white-space:nowrap;width:300px;text-overflow: ellipsis; " title='+ obj.reportName +' >'+obj.reportName +'</li>';*/
                    li.innerHTML=obj.reportName;
                    li.title = obj.reportName;
                    var reportName =obj.reportName;
                    li.setAttribute("reportId",obj.reportId);
                    //var reportLi = "#reportLi"+a;
                    li.onclick=function(){
                        var userInfo = UserModule.UserUtil.getUserInfo();
                        if(userInfo) {
                            previewFun(obj.reportName, obj.reportId);
                        }
                        else{
                            location.href="index.html";
                        }
                    };
                    reportUl.appendChild(li);
                });
                //$("#report_list").html(result);
                reportUl.appendChild(li);

                $(".pagination").show();
                $(".pagination").pageM({
                    "pageNum" : data.pageNum,
                    "total" : data.total,
                    "pageSize" : 10,
                    "pageEllipsis":0,
                    "pagePosition" : "LEFT",
                    "pageEvent" : function(option){
                        loadReportTableList(option.pageNum, option.pageSize);
                    }
                });
                $("#report_list").on("click","li",function(){      //只需要找到你点击的是哪个ul里面的就行
                    $('#report_list li').removeClass("active")
                    $(this).addClass("active");
                });
            }
            else {
                $(".pagination").hide();
                var content = '<P align="center" style="font-size: 115%;" id="tle">最新报告</P><P align="center">暂无报告</P>';
                $('#divReportList').hide();
                $('#divReport').show();
                $('#divReport').html(content+"</p>");
            }
        };
        permissionAjax.opts.errorFun = function(){
            console.log("专题报告加载异常")
        };
        permissionAjax.run();

    };


    /*======================================================================*/
    /*						三年图表操作									*/
    /*======================================================================*/
    /**
     * 根据数据集、区域、日期、作物加载最新报告
     * @param param : 键值对
     dsCode : 数据集代码
     regionId: 区域ID
     startDate: 数据查询时间
     cropCode : 作物代码
     * @version <1> 2017-11-10 Hayden:Created.
     * @version <2> 2017-12-15 cxw:作物分布单位换算.
     */
    var loadChartList = function(param){
        //构造图表标题
        var title = "",url="";
        var dsInfo = getDsInfo(param.dsCode);

        var title = getChartTitle(param);
        var url = dsInfo.An_ThreeYear_URL;
        var option = myChart.getOption();
        option.title[0].text=title;
        option.series = [];
        option.xAxis[0].data=[];

        var ajax = new BaseAjax();
        ajax.opts.url=url;
        ajax.opts.data = param;
        ajax.opts.type = "GET";
        ajax.opts.contentType = "application/json";
        ajax.opts.successFun = function(result){
            if(result.flag){
                var legend=[];
                var num=0;
                var res = [];
                var rdata = result.data;
                for(var key in rdata){
                    legend.push(key+"年");
                    var serie = {};
                    serie.name=key+"年";
                    serie.type="bar";
                    serie.data = [];
                    var chardata = rdata[key];
                    //作物分布与估产单位换算(分布与估产单位换算(原始单位亩,吨 换算为万亩,万吨))
                    chardata = changeDataUnit(chardata,param);
                    for(var i in chardata){
                        var value = chardata[i].value;
                        value = value != null ?value:'-';
                        /*//作物分布单位换算
                         if(dsInfo.name==DS_Distribution.name||dsInfo.name==DS_Yield.name) {
                         value = (value/10000).toFixed(2);
                         }*/
                        if(num==0) res =rdata[key];
                        serie.data.push(value);
                        if(option.xAxis[0].data.length!=chardata.length){
                            if(dsInfo.name == DS_Trmm.name || dsInfo.name == DS_T.name|| dsInfo.name == DS_Growth.name){
                                //降水 地温 x 轴显示时间
                                num=num+1;
                                if(num<=res.length)
                                    option.xAxis[0].data.push(chardata[i].monthAndDay);
                            } else {
                                //x轴显示区域名
                                option.xAxis[0].data.push(chardata[i].regionName);
                            }
                        }

                    }
                    option.series.push(serie);
                }
                option.legend[0].data = legend;
                if(option.series.length>0){
                    option.series[0].type="line";//均值使用线条
                }
            }
            else{

                var year =new Date(param.startDate).getFullYear();
                var years =[];
                years.push(10);
                years.push(year-2);
                years.push(year-1);
                years.push(year);
                var legend=[];
                for(var i=0;i<years.length;i++){
                    var serie = {};
                    serie.name=years[i]+"年";
                    serie.type="bar";
                    serie.data = [];
                    legend.push(years[i]+"年");
                    option.legend[0].data = legend;
                    option.series.push(serie);
                }
                option.legend[0].data = legend;
                if(option.series.length>0){
                    option.series[0].type="line";//均值使用线条
                }
            }
            option.yAxis[0].name = dsInfo.unit;
            myChart.setOption(option);
        };
        ajax.opts.errorFun = function(){
            var chartData = {};
            option.legend[0].data = chartData.legend;
            option.xAxis[0].data =chartData.xAxis;
            option.series = chartData.series;
            myChart.setOption(option);
        };
        ajax.run();
    };


    /**
     *
     * 数组对象排序
     * @version <1> 2018-07-09 cxw:Created.
     */
    var compare = function (obj1, obj2) {
        var val1 = obj1.seriesName;
        var val2 = obj2.seriesName;
        if (val1 < val2) {
            return -1;
        } else if (val1 > val2) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     *
     * 数组对象排序
     * @version <1> 2018-07-09 cxw:Created.
     */
    var compareT = function (obj1, obj2) {
        var val1 = obj1.name;
        var val2 = obj2.name;
        if (val1 < val2) {
            return -1;
        } else if (val1 > val2) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     *
     * 根据数据构造图表
     * @version <1> 2017-11-10 Hayden:Created.
     */
    var loadChartUI = function(){
        myChart = echarts.init(divChart);
        var option = {
            title:{x:"center"},
            grid:{x:70,y:60,x2:10,y2:60},
            tooltip: {
                'trigger': 'axis',
                'formatter': function(params,ticket,callback){
                    params = params.sort(compare);
                    var tooltipHtml = '';
                    for (var i = 0; i < params.length; i++) {
                        if (i == 0) {
                            tooltipHtml += params[i].axisValue + '<br/>';
                        }
                        params[i].seriesId = " " +params[i].seriesName +" 0";
                        tooltipHtml += params[i].marker + ' ' + params[i].seriesName + ': ' + params[i].value + '<br/>';
                    }
                    return tooltipHtml;
                }
            },
            toolbox:{
                show:true,
                padding: [40 ,10, 5 ,5] ,
                feature : {
                    dataView: {
                        show: true,
                        readOnly: true,
                        optionToContent: function(opt) {
                            var axisData = opt.xAxis[0].data;
                            var series = opt.series;
                            series = series.sort(compareT);
                            var table = '<table style="width:100%;text-align:center;border:solid 1px;"><thead><tr>'
                                + '<td></td>';
                            for(var s = 0,k = series.length;s < k ; s++){
                                table += '<td>' + series[s].name + '</td>'
                            }
                            table += '</tr></thead><tbody>';
                            for (var i = 0, l = axisData.length; i < l; i++) {
                                table += '<tr>'
                                    + '<td>' + (axisData[i] || '-') + '</td>'
                                for(var p = 0,q = series.length;p < q ; p++){
                                    table += '<td>' + (series[p].data[i] || '-') + '</td>'
                                }
                                table += '</tr>';
                            }
                            table += '</tbody></table>';
                            return table;
                        }
                    },
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable:true,
            dataZoom:[
                {
                    id:'dataZoomX',
                    filterMode:'filter',
                    show:true,
                    realtime:true,
                    start:0,end:15
                },
                {
                    type:'inside',
                    filterMode:'filter',
                    xAxisIndex:0,
                    start:0,
                    end:100
                }
            ],
            legend: [{y:"top",padding:[40,5,5,5]}],
            xAxis : [{type : 'category'}],
            // yAxis : [{type : 'value',name:dsInfo.unit,boundaryGap: [0.03, 0.1]}],
            yAxis : [{type : 'value',boundaryGap: [0.03, 0.1]}],
            series : []
        };
        myChart.setOption(option);
        window.onresize = myChart.resize;
    };

    /**
     * 根据参数生成图表头标题
     * @version <1> 2017-12-29 Hayden:Created.
     */
    var getChartTitle = function(param){
        var chartTitle = "";
        var dsInfo = getDsInfo(param.dsCode);
        if(dsInfo.name==DS_Yield.name || dsInfo.name==DS_Distribution.name){
            var searchYear = "";
            var searchMonthDay = "";
            var searchDate = new Date(param.dataTime);
            if (param.dataTime){
                searchYear = searchDate.Format("yyyy");
                searchMonthDay = searchDate.Format("MM月dd日");
                chartTitle=(searchYear+"年"+searchMonthDay)+"近三年" + param.chinaName+"产区"+(param.cropName || "____")+"作物"+param.dsName+"统计数据";
            }
            else{
                if (param.endDate && param.startDate){
                    var searchDate = new Date(param.endDate);
                    var startDate = new Date(param.startDate);
                    searchYear = searchDate.Format("yyyy");
                    threeYear = searchYear-2;
                }
                chartTitle=(threeYear || '')+"-"+(searchYear || '')+"年近三年" + param.chinaName+"产区"+(param.cropName || "____")+"作物"+param.dsName+"统计数据";
            }
        }else if(dsInfo.name==DS_Trmm.name || dsInfo.name==DS_T.name){
            var threeYear,searchYear,searchMonthDay,startMonthDay;
            if (param.endDate && param.startDate){
                var searchDate = new Date(param.endDate);
                var startDate = new Date(param.startDate);
                searchYear = searchDate.Format("yyyy");
                threeYear = searchYear-2;
                searchMonthDay = searchDate.Format("MM月dd日");
                startMonthDay = startDate.Format("MM月dd日");
            }
            chartTitle=(threeYear || '')+"-"+(searchYear || '')+"年("+(startMonthDay || '') +"-"+(searchMonthDay || '')+")"+"近三年"+ param.chinaName+"产区"+param.dsName+"统计数据";
        }
        return chartTitle;
    };

    /*======================================================================*/
    /*						     表格操作									*/
    /*======================================================================*/

    /**
     * 单位换算(分布与估产单位换算(原始单位亩,吨 换算为万亩,万吨))
     * @version <1> 2017-12-15 cxw:Created.
     */
    var  changeDataUnit = function (data,param) {
        var dsInfo = getDsInfo(param.dsCode);
        var areaData = [];
        areaData = data
        if(dsInfo.name==DS_Distribution.name||dsInfo.name==DS_Yield.name) {
            for (var temp in data) {
                var val = data[temp].value;
                var lastval = data[temp].lastValue;
                if (val) {
                    val = (val / 10000).toFixed(2);
                    areaData[temp].value = val;
                }
                if (lastval) {
                    lastval = (lastval / 10000).toFixed(2);
                    areaData[temp].lastValue = lastval;
                    var perVal = val-lastval;
                    if(lastval==0&&(val!=0&&val!=null)){
                        areaData[temp].percent = 1;
                    }
                    else if(lastval!=0&&val==0){
                        areaData[temp].percent = -1;
                    }
                    else if(lastval==0&&val==0){
                        areaData[temp].percent = 0;
                    }
                    else if(val==null){
                        areaData[temp].percent = null;
                    }
                    else{
                        areaData[temp].percent = perVal/lastval;
                    }
                }
                else {
                    if(lastval==0&&(val!=0&&val!=null)){
                        areaData[temp].percent = 1;
                    }
                    else if(lastval==0&&val==0){
                        areaData[temp].percent = 0;
                    }
                    else if(val==0||val==null){
                        areaData[temp].percent = null;
                    }
                }
            }
        }
        return areaData;
    }

    /**
     * 下载表格数据
     * @version <1> 2017-11-30 lcw : created.
     */
    var exportData = function(){
        var userInfo = UserModule.UserUtil.getUserInfo()
        if(userInfo) {
            var param = getParam();
            var title = param.chinaName + '产区' ;
            if(param.cropName){
                title = title + param.cropName;
            }
            title = title +param.dsName + "情况统计表" ;
            if(param.endYear){
                title = title + "("+param.endYear+")";
            }else{
                title = title + "("+ param.startDate + "~" + param.endDate +")";
            }

            var re = $("#divGrid").jsGrid();
            var tableData = re.data().JSGrid.data;
            for(var i=0;i<tableData.length;i++){
                tableData[i].percent = ((tableData[i].percent)*100).toFixed(2)+"%"
            }
            $('#divGrid').tableExport({
                filename: title,
                data:tableData,
                format: 'xls',
                cols: 'regionName,date,value,lastValue,percent' //导出列
            });
        }
        else{
            location.href="index.html";
        }
    }

    /*
     * 根据数据集获取表头及调用数据的ULR
     * @param dsCode : 数据集代码
     * @return :
     * 	fields:表头
     * @version <1> 2017-11-15 Hayden:Created.
     */
    var getHeaderForDs = function(dsCode,year){
        year = "("+year+"年)";
        var trmmField = [
            { name: "regionName",title:"区域名称", type: "text" },
            { name: "date",title:"日期", type: "text" },
            { name: "value",title:"降雨量(毫米)", type: "number"},
            { name: "lastValue",title:"上月同期(毫米)",type: "number"},
            { name: "percent",title:"增长率%",type: "number",itemTemplate:createRate}
        ];
        var tField = [
            { name: "regionName",title:"区域名称", type: "text" },
            { name: "date",title:"日期", type: "text" },
            { name: "value",title:"地温(℃)", type: "number"},
            { name: "lastValue",title:"上月同期(℃)",type: "number"},
            { name: "percent",title:"增长率%",type: "number",itemTemplate:createRate}
        ];
        var distributionField = [
            { name: "regionName",title:"区域名称", type: "text" },
            { name: "value",title:year+"种植面积(万亩)", type: "number" },
            { name: "lastValue",title:"去年同期(万亩)", type: "number"},
            { name: "percent",title:"增长率%",type: "number",itemTemplate:createRate}
        ];
        var growthField = [
            { name: "regionName",title:"区域名称", type: "text" },
            { name: "date",title:"日期", type: "text" },
            { name: "value",title:"长势指数", type: "number"},
            { name: "lastValue",title:"去年同期",type: "number"},
            { name: "percent",title:"增长率%",type: "number",itemTemplate:createRate}
        ];
        var yieldField = [
            { name: "regionName",title:"区域名称", type: "text" },
            { name: "value",title:year+"估算产量(万吨)", type: "number" },
            { name: "lastValue",title:"去年同期(万吨)", type: "number"},
            { name: "percent",title:"增长率%",type: "number",itemTemplate:createRate}
        ];
        var headerArray = [
            {name:DS_Trmm.name,fields:trmmField},
            {name:DS_T.name,fields:tField},
            {name:DS_Distribution.name,fields:distributionField},
            {name:DS_Growth.name,fields:growthField},
            {name:DS_Yield.name,fields:yieldField}
        ];
        for(var i=0;i<headerArray.length;i++){
            if(headerArray[i].name==dsCode){
                return headerArray[i].fields;
            }
        }

        return;
    };

    /**
     *增长率图片显示
     *上升  向上箭头
     *下降  向下箭头
     *相等  持平箭头
     @version <1> 2017-11-28 XuZhenguo:Created.
     *
     */
    var createRate=function(value,item){
        if (value == null) {return "";}
        value = (Number(value) * 100).toFixed(2);
        var iconText = "<span style='display:inline-block;'>"+value+"%</span>" + "<img width='20px'style='float: right;' src='images/rate/";
        if (value > 0) {
            iconText += 'up.png';
        } else if(value < 0){
            iconText += 'down.png';
        } else {
            iconText += 'same.png';
        }
        iconText += "'/>";
        return iconText.toString();
    }

    /**
     * 构造当前显示数据
     * 1. 如果一年只有一条数据，只构造值
     * 2. 如果要显示色块的数据集数据，则构造当前天的数据
     * @version <1> 2018-01-02 Hayden:Created.
     */
    var buildCurrentLayerData = function(dateDataArray,map_Legend){
        //将指定日期的数据解析为分析数据，格式为键值对 : { 区域ID，图例数据 }
        for (var i = 0,k= dateDataArray.length; i < k ; i++) {
            var value = dateDataArray[i].value;
            var tempObj={'value':value};
            if(map_Legend){
                for(var j=0;j<map_Legend.length;j++){
                    var legend = map_Legend[j];
                    tempObj['LV'] = legend[0];
                    tempObj['color'] = legend[1];
                    tempObj['lvName'] = legend[2];
                    var legend_value = legend[0].match(/-?\d+(.\d+)?/)[0];
                    if (value > legend_value) {
                        break;
                    }
                }
            }

            levelData[dateDataArray[i].regionCode] = tempObj;
        }
    };

    /**
     * 加载表格数据
     * @param param : 获取数据所需要参数，作物、指标、区域、时间
     * @version <1> 2017-11-15 Hayden:Created.
     * @version <2> 2017-11-30 lcw : modify . 添加导出excel表格代码
     * @version <3> 2017-12-11 cxw : modify . 修改作物分布与产量估算表头
     * @version <4> 2017-12-13 cxw : modify . 作物分布单位换算
     * @version <5> 2017-12-15 cxw : modify . 长势，产量，作物分布弹窗数据加载
     */
    var loadTableList = function(param){

        divMapSlider.style.display = "none";
        var dsInfo = getDsInfo(param.dsCode);
        var searchYear = "";
        if (param.endDate){
            var formatDate = new Date(param.endDate);
            searchYear = formatDate.Format("yyyy");
        }
        var fields = getHeaderForDs(dsInfo.name,searchYear);

        var ajax = new BaseAjax();
        ajax.opts.url=dsInfo.Mom_URL;
        ajax.opts.contentType = "application/json";
        ajax.opts.data = param;
        ajax.opts.type = "GET";
        ajax.opts.successFun=function(result){
            var data = [];
            if(result.flag){

                data = result.data?result.data:[];

                //分布与估产单位换算(原始单位亩,吨 换算为万亩,万吨)
                data = changeDataUnit(data,param);
                if(dsInfo.Is_Show_Slide){
                    divMapSlider.style.display = "block";
                    //加载滑动条
                    showSlide(data);
                }

                //加载滑块指定天的图层
                if(dsInfo.Is_ColorBlock_Layer){
                    //加载 查询第一天 或当前年数据表示的图层
                    var firstDate = !param['dataTime'] ? getTableDataByDay(data,param['startDate']) : data;
                    loadColorLayer(firstDate,dsInfo.Map_Legend);
                }else{
                    //加载图层
                    loadDsLayer(param);
                    for(var i = 0,j = data.length; i < j; i++){
                        var tempDate = data[i];
                        levelData[tempDate.regionCode] = {'value':tempDate.value};
                    }
                }

            }
            $("#divGrid").jsGrid({fields:fields,data:data,pageButtonCount: 7,noDataContent:'没有查询到数据',pagerFormat: "页码: {first} {prev} {pages} {next} {last} &nbsp;&nbsp; {pageIndex} / {pageCount}"});
            $("#divGrid").jsGrid("reset");
        };
        ajax.opts.errorFun = function(){
            $("#divGrid").jsGrid({fields:fields,data:[]});
            $("#divGrid").jsGrid("refresh");
        };
        ajax.run();

        loadGFVectorFun();
    };

    /**
     * 根据数据构造表格
     * @version <1> 2017-11-10 Hayden:Created.
     */
    var loadTableUI = function(){
        //默认显示降雨的表头
        // var fields = Trmm.Trmm_Table_Field;
        var fields=[];
        var mytable =  $("#divGrid").jsGrid({
            width: "100%",
            height:"100%",
            sorting: true,
            autoload:true,
            paging: true,
            pageSize:10,
            selcting:true,
            data: [],
            fields: fields,
            noDataContent:'没有查询到数据'
        });
    };

    /*======================================================================*/
    /*						     数据调用									*/
    /*======================================================================*/

    /**
     * 获取用户选择的参数
     * @version <1> 2017-11-22 Hayden:Created.
     */
    var getParam = function(){

        var dsObj,regionObj,cropObj,dataTypeObj,param={};

        //获取区域参数
        var chinaName = txtRegion.value;
        var regionId = txtRegion.getAttribute("regionId");
        var regionCode = txtRegion.getAttribute("regionCode");
        var regionName = txtRegion.getAttribute("regionName");
        var parentId = txtRegion.getAttribute("parentId");
        var level = txtRegion.getAttribute("level");
        regionObj = {regionId:regionId,regionCode:regionCode,regionName:regionName,chinaName:chinaName,level:level,parentId:parentId};

        var dsId,dsCode,dsName;
        //获取数据集参数
        if(selDataset.options.selectedIndex>=0){
            dsId = selDataset.options[selDataset.options.selectedIndex].value;
            dsCode = selDataset.options[selDataset.options.selectedIndex].getAttribute("dsCode");
            dsName = selDataset.options[selDataset.options.selectedIndex].text;
            dsObj = {dsId:dsId,dsCode:dsCode,dsName:dsName};
        }

        //获取作物参数
        var cropCode ,cropName,cropId;
        var cropElems = divCrop.getElementsByClassName("active");
        if(cropElems && cropElems.length==1){
            cropCode = cropElems[0].getAttribute("cropCode");
            cropName = cropElems[0].getAttribute("cropName");
            cropId = cropElems[0].getAttribute("cropId");
            cropObj = {cropId:cropId,cropCode:cropCode,cropName:cropName};
        }

        var dataTypeId ,dataTypeName,dataTypeCode;
        //获取数据源，是GF1,还是mod09a1
        if(selDataType.options.selectedIndex>=0){
            dataTypeId = selDataType.options[selDataType.options.selectedIndex].value;
            dataTypeName = selDataType.options[selDataType.options.selectedIndex].text;
            dataTypeCode = selDataType.options[selDataType.options.selectedIndex].getAttribute("accuracycode");
            // dataTypeObj={dataTypeId:dataTypeId,dataTypeCode:dataTypeCode,dataTypeName,dataTypeName};
            dataTypeObj={accuracyId:dataTypeId,accuracyCode:dataTypeCode,accuracyName:dataTypeName};
        }

        //获取时间参数
        // if(dsObj && dsObj.dsCode){
        // 	var dsInfo = getDsInfo(dsObj.dsCode);
        // 	var dateFormat = dsInfo.Date_Format;
        // 	var selectedDateObj = dateUtil.getSelectedDate("txtDate",dateFormat);
        // }


        //保存时间点数据
        param.dataTime = point.value || '';
        param.startDate = txtStartDate.value || '';
        param.endDate = txtEndDate.value || '';

        for(var key in regionObj)
            param[key] = regionObj[key];
        for(var key in dsObj)
            param[key] = dsObj[key];
        for(var key in dataTypeObj)
            param[key] = dataTypeObj[key];
        for(var key in cropObj)
            param[key] = cropObj[key];
        // for(var key in selectedDateObj)
        // 	param[key] = selectedDateObj[key];

        var userInfo = UserModule.UserUtil.getUserInfo();
        if(userInfo!=undefined && dsId !=undefined  && dataTypeId!=undefined ) {
            //if (userInfo.roleCode != 'Role_Admin' && userInfo.roleCode != null) {
            /*	var defaultProduct = {};
                    defaultProduct.regionId = regionId;
                    defaultProduct.regionCode = regionCode;
                    defaultProduct.regionName = regionName;
                    defaultProduct.chinaName = chinaName;
                    defaultProduct.level = level;
                    defaultProduct.dsId = dsId;
                    defaultProduct.dsCode = dsCode;
                    defaultProduct.dsName = dsName;
                    defaultProduct.accuracyId = dataTypeId;
                    defaultProduct.dataTypeId = dataTypeId;
                    defaultProduct.dataTypeCode = dataTypeCode;
                    defaultProduct.dataTypeName = dataTypeName;
                    defaultProduct.startDate = userInfo.defaultProduct.startDate;
                    defaultProduct.endDate = userInfo.defaultProduct.endDate;*/
            //操作时更新cookie过期时间
            UserModule.UserUtil.updateCookie();

        }
        return param;
    };

    /**
     * 加载数据
     * @version <1> 2017-11-09 Hayden:Created.
     */
    var loadData = function(){
        var param = getParam();
        //获取近三年数据,加载到图表中
        loadChartList(param);

        //根据条件获取表格数据
        loadTableList(param);

        if(param.dsCode=='trmm'||param.dsCode=='t')
        {
            //获取降雨地温最新报告
            loadReportList(param);
        }
        else{
            //获取分布，估产最新报告
            loadReportTableList();
        }

        /*var userInfo = UserModule.UserUtil.getUserInfo();
        var dataPermission  = userInfo.defaultProduct;
        if (((param.startDate >= dataPermission.startDate) && (param.endDate <= dataPermission.endDate))||userInfo.roleCode=='Role_Admin') {
            //获取近三年数据,加载到图表中
            loadChartList(param);

            //根据条件获取表格数据
            loadTableList(param);

            //获取最新报告
            loadReportList(param);
        }	/*
        else {
            popWin.showMessageWin("暂无符合所选条件的数据访问权限,请联系管理员");
        }*/
        // var ajax = new BaseAjax();
        // ajax.opts.url=Data_PRODUCT.PERMISSION_URL;
        // ajax.opts.contentType = "application/json";
        // ajax.opts.data = JSON.stringify(dataProduct);
        // ajax.opts.successFun=function(result){
        // 	//删除图层
        // 	deleteAllLayer();
        //
        // 	//加载数据集图地图图例
        // 	loadLayerLegend(param);
        //
        // 	//判断是否拥有数据访问权限
        // 	if(result.flag)
        // 	{
        // 		//获取近三年数据,加载到图表中
        // 		loadChartList(param);
        //
        // 		//根据条件获取表格数据
        // 		loadTableList(param);
        //
        // 		//获取最新报告
        // 		loadReportList(param);
        // 	}
        // 	else{
        // 		initTabChart(param);
        // 		popWin.showMessageWin(result.msg);
        // 		return;
        // 	}
        // }
        // ajax.opts.errorFun = function(){
        // 	initTabChart(param);
        // 	popWin.showMessageWin('系统错误,请联系管理员!');
        // 	return;
        // };
        // ajax.run();
    };

    /**
     * 初始化图表
     * @version <1> 2018-03-08 CXW:Created.
     */
    var initTabChart = function (param) {
        //初始化柱图
        var title = getChartTitle(param);
        var option = myChart.getOption();
        option.title[0].text=title;
        option.series = [];
        option.xAxis[0].data=[];
        var chartData = {};
        option.legend[0].data = chartData.legend;
        option.xAxis[0].data =chartData.xAxis;
        option.series = chartData.series;
        myChart.setOption(option);
        //初始化表格
        var dsInfo = getDsInfo(param.dsCode);
        var fields = getHeaderForDs(dsInfo.name,param.endYear);
        $("#divGrid").jsGrid({fields:fields,data:[]});
        $("#divGrid").jsGrid("refresh");
    }

    /**
     * Description: 预览PDF报告
     * @param obj
     * @version <1> 2018-07-08 13:21 cxw: Created.
     */
    var previewFun = function(reportName,reportId){
        var previewDialog = $("#previewDialog");
        var previewParent = previewDialog.parent();
        var previewOwn = previewDialog.clone();
        previewDialog.dialog({
            autoOpen: false,
            title: reportName,
            height: 780,
            width: 1200,
            modal: true,
            close:function(){
                previewOwn.appendTo(previewParent);
                $(this).dialog("destroy").remove();
                $('#report_list li').removeClass("active")
            },
            buttons: [
                {
                    text:"关闭",
                    click:function(){
                        $(this).dialog("close");
                        $('#report_list li').removeClass("active")
                    }
                }
            ]
        });
        previewDialog.dialog("open");

        var key = COOKIE_CONFIG.cookieName;
        var accessToken = JHModule.CookieUtil.getCookie(key);
        var download_url = DS_Report.REPORT_PREVIEW_URL +  "?reportId="+ encodeURIComponent(reportId)  +"&AccessToken="+accessToken;
        var url = "js/lib/pdfjs/web/viewer.html?" + download_url;
        $("#pdfHTML").attr("src",url);
    };


    /**
     * 判断是否拥有数据访问权限
     * @version <1> 2018-03-08 CXW:Created.
     */
    /*	var isPermission = function (param) {
     var ajax = new BaseAjax();
     ajax.opts.url=Data_PRODUCT.PERMISSION_URL;
     ajax.opts.contentType = "application/json";
     ajax.opts.data = JSON.stringify(param);
     ajax.opts.successFun=function(result){
     if(!result.flag)
     {
     popWin.showMessageWin(result.msg)
     return;
     }
     }
     ajax.opts.errorFun = function(){

     };
     }*/

    init();
});
/**
 * 加载作物列表控件
 * @version <1> 2018-07-02 CXW:Created.
 */
window.onload = function () {
    var speed = -44;
    up.onclick = function () {
        var oUl = document.getElementById('divCropList').childNodes[0];
        //oUl.innerHTML += oUl.innerHTML;
        var ulHeigth = oUl.offsetHeight;
        speed = -44;
        if (((oUl.offsetTop) + 176) < -ulHeigth / 2)
            oUl.style.top = '0px';
        else if (oUl.offsetTop > 0)
            oUl.style.top = -ulHeigth / 2 + 'px';
        oUl.style.top = oUl.offsetTop + speed + 'px';
        $('#down').show();
        $('#up').show();
        if(oUl.style.top =='0px'){
            $('#down').hide();
        }
        var h = '-'+(ulHeigth - 176)+"px";
        if(oUl.style.top == h){
            $('#up').hide();
        }
    }
    down.onclick = function () {
        var oUl = document.getElementById('divCropList').childNodes[0];
        //oUl.innerHTML += oUl.innerHTML;
        var ulHeigth = oUl.offsetHeight;
        speed = 44;
        if (oUl.offsetTop < -ulHeigth )
            oUl.style.top = '0px';
        else if (oUl.offsetTop > 0)
            oUl.style.top = -ulHeigth / 2 + 'px';
        oUl.style.top = oUl.offsetTop + speed + 'px';
        $('#down').show();
        $('#up').show();
        if(oUl.style.top =='0px'){
            $('#down').hide();
        }
        var h = '-'+(ulHeigth - 176)+"px";
        if(oUl.style.top ==h){
            $('#up').hide();
        }
    }

}