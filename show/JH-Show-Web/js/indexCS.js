require(['jquery','jqueryUi','ol','moment','daterangepicker','selectordie','UserModule','MapModule','RegionModule','echarts','jsgrid','dateUtil','selectordie','bootstrap','formVerfication','PopWin','commons','Base64','custom_settings','JHModule','paginate','yhhDataTable','page','pageM'], function ($,jqueryUi,ol,moment,daterangepicker,selectordie,UserModule,MapModule,RegionModule,echarts,jsgrid,dateUtil,selectordie,bootstrap,formVerfication,PopWin,commons,Base64,custom_settings,JHModule,paginate,yhhDataTable,page,pageM) {

    var txtRegion = document.getElementById("txtRegion");
    var selDataType = document.getElementById("selDataType");
    /*****************************************************************************************************************
     * *******************************************地图区域操作***********************************************************
     * ***************************************************************************************************************/
    function mapClazz(){

        /**
         *图层层级显示
         * @version<1> 2018-10-10 lcw :Created.
         */
        this.layerIndex = {
            'bgLayer':1,
            'gfLayer':3,
            'countryLayer':4,
            'tifLayer':20,
            'colorLayer':5,
            'childLayer':21,
        };

        //默认加载世界地图实例（矢量数据通过该名称去获取）
        this.defaultLayerName = "JiaHeDC:ly_WORLD";

        var _parentId = 3100000000; //中国
        var _this = this;

        //类成员变量
        var mapTool,provinceLayer,cityLayer,districtLayer,colorLayer,tifLayer;

        /**
         * 页面初始化地图
         * 1.默认加载高清底图
         * 2.默认加载省级矢量边界
         * 3.根据所选区域加载矢量边界：
         *   1）如果所选区域为省级，则加载当前区域的市州边界
         *   2）如果所选区域为市州，则加载当前市州所在省的各市州矢量边界，同时加载该市的各区县适量边界
         *   3）同2
         *
         * @param _regionCode : 当前区域code
         * @param _parentId : 当前区域的上级ID
         * @param _level : 当前区域的级别 ， 省2 ，市3，区4
         * @param _provinceId ： 当前区域的省级区域ID
         * @version <1> 2017-11-07 Hayden:Created.
         */
        this.initMap = function(_regionId){
            if(mapTool===undefined){
                var url = DS_Layer.Layer_Init_URL;
                mapTool = new MapModule.MapTool("divMap",{"url":url,"zoom":11,"maxZoom":14,"minZoom":3,"logoSrc":"","logoHref":""});
                mapTool.createMap();

                //默认底图
                //          bgLayer = mapTool.addTileWMSLayer(this.defaultLayerName, {'zIndex': this.layerIndex.bgLayer});

                _this.loadHighDefinitionMap(); //高清地图

                _this.loadVector(_regionId)

                _this.loadPointDiglog();//鼠标移动事件，显示提示框

            }

        };

        /**
         * 构建高清底图
         * @version<1> 2018-10-14 lcw :Created.
         */
        this.loadHighDefinitionMap = function(){
            //高清底图
            var gfLayerName = 'JiaHeDC:ly_WORLD_GF';
            gfLayer = mapTool.addTileWMSLayer(gfLayerName,{'zIndex':_this.layerIndex.gfLayer});
        }

        /**
         * 1.构建所选区域的家族关系
         * 2.根据构建的关系加载矢量边界
         * @version<1> 2018-10-14 lcw :Created.
         */
        this.loadVector = function(_regionId){
            //移除图层
            _this.removeLayers();

            if(_regionId == undefined){
                var param = getParam();
                _regionId = param.regionId;
            }

            var opts = {
                url:User_Env.REGION_FAMILY_URL,
                data:{regionId:_regionId},
                type:"POST",
                contentType:"application/x-www-form-urlencoded",
                async:false
            };
            var ajax = new BaseAjax();
            ajax.opts = opts;
            ajax.opts.successFun = function(result){
                if(result.flag){
                    var data = result.data;
                    var _provinceId = data.provinceId;
                    var _cityId = data.cityId;
                    var _level = data.level;
                    var _regionCode = data.regionCode;


                    if(!!districtLayer){
                        mapTool.removeLayer(districtLayer);
                    }

                    if(!!cityLayer){
                        mapTool.removeLayer(cityLayer);
                    }

                    if(!!provinceLayer){
                        mapTool.removeLayer(provinceLayer);
                    }

                    //非直辖市才加载省级，避免直辖市省级和市级边界和名字与市级重叠
                    if (Municipality.indexOf(_provinceId) < 0){
                        _this.addVectorInProvince(_regionCode) //加载省级矢量边界，默认市全国
                    }
                    _this.addCityVector(_provinceId, 3); //加载选中省份的市州级矢量边界
                    if(_level >=3){
                        _this.addDistrictVector(_cityId, 4); //加载选中省份的区县矢量边界
                    }

                    _this.moveToCenter(_regionCode,_level) //移动至当前选中区域
                }

            };
            ajax.opts.errorFun = function(){
                console.info("加载区域家族关系失败");
            };
            ajax.run();

        }

        /**
         * 在各省份上覆盖图层，只显示省级geometry的名称，当前选中区域除外
         * @param _parentId : 上级区域ID
         * @param _regionCode : 需要排除在外的区域
         *
         * @version<1> 2018-10-12 lcw :Created.
         */
        this.addVectorInProvince = function(_regionCode){
            var provinceStyle = MapModule.MapTool.setLayerStyle({'fillColor':"rgba(255,255,255, 0)",'strokeColor':"#E3C458"});
            //其他区域各省级图层均只显示省
            var opts = {filter:"region_code ='"+ _regionCode  +"' and level = '2'" ,style:provinceStyle,zIndex:_this.layerIndex.childLayer};
            if(!!provinceLayer){
                mapTool.removeLayer(provinceLayer);
            }

            provinceLayer = mapTool.filterWMSToVectorLayerFillColor(this.defaultLayerName,opts);

        }

        /**
         * 构建所选区域的各级矢量边界
         * 1.构建市州级矢量边界
         * @param _provinceId : 所选区域的省级RegionId
         * @param _level : 市州的层级
         * @version<1> 2018-10-12 lcw :Created.
         */
        this.addCityVector = function(_provinceId, _level){
            var _style = MapModule.MapTool.setLayerStyle({'fillColor':"rgba(255,255,255, 0)",'strokeColor':"#fff"});
            var opt = {filter:"parent_id = '"+ _provinceId +"' and level = '"+ _level +"'",style:_style,strokeColor:'#fff',zIndex:_this.layerIndex.childLayer};

            cityLayer = mapTool.filterWMSToVectorLayerFillColor(_this.defaultLayerName,opt);
        };

        /**
         * 构建所选区县的下级矢量边界
         * @param _cityId :所选区域的市州regionId
         * @param _level : 区县的level
         * @version<1> 2018-10-14 lcw :Created.
         */
        this.addDistrictVector = function(_cityId, _level){
            var _style = MapModule.MapTool.setLayerStyle({'fillColor':"rgba(255,255,255, 0)",'strokeColor':"#fff"});
            var opt = {filter:"parent_id = '"+ _cityId +"' and level = '"+ _level +"'",style:_style,strokeColor:'#fff',zIndex:_this.layerIndex.childLayer};
            districtLayer = mapTool.filterWMSToVectorLayerFillColor(this.defaultLayerName,opt);



        };

        /**
         * 将中心店移动至所选区域的中心点
         * @param _regionCode : 所选区域ID
         * @version<1> 2018-10-14 lcw :Created.
         */
        this.moveToCenter = function(_regionCode,_level){

            var _layer = null;

            if(_level == 2){
                _layer = provinceLayer;
            }else if(_level == 3){
                _layer = cityLayer
            }else if(_level == 4){
                _layer = districtLayer;
            }

            _layer.getSource().once("addfeature",function(){
                mapTool.moveToLayerFeature(_layer,"region_code",_regionCode);
            });
        };

        /**
         * 显示图例
         */
        this.showLegend = function(map_legend){
            var mapLegendArry = [];
            for(var i=0;i<map_legend.length;i++){
                if(map_legend[i][0]==$("#plants").find("option:selected").attr("cropName")){
                    mapLegendArry.push(map_legend[i]);
                }
            }
            var legendTitle = document.getElementById('legendTitle');
            var param = getParam();
            var dsId = param.dsId;
            if (param.dsId == DS_Distribution.id){//分布
                mapTool.repaintLegend(mapLegendArry);
            } else {
                mapTool.repaintLegend(map_legend);
            }
        };

        /**
         * 显示tiff
         */
        this.showDsLayer = function(_layerName){
            // _this.showLegend()
            _this.removeLayers() ;
            // tifLayer = mapTool.addWMSLayer(layerName,{'zIndex':layerIndex['tifLayer']});
            tifLayer = mapTool.addWMSLayer(_layerName,{'zIndex':_this.layerIndex.tifLayer});
        };

        /**
         * 显示色块
         */
        this.showColorLayer = function(_data, map_legend){
            var param = getParam();
            var regionId = param.regionId;
            var parentId = param.parentId;
            var thisColorLayer = this.layerIndex.colorLayer;
            if (param.dsId == DS_Growth.id) {
                var levelData = _this.buildGrowthLayerData(_data); //构建长势色块
            }else {
                var levelData = _this.buildCurrentLayerData(_data, map_legend); //构建色块
            }

            //遍历图例，构造feature样式
            var lvStyle = MapModule.MapTool.defineStyle({fillColor:"rgba(255,255,255, 0)",strokeColor:"#20B2AA"});
            for(var x in map_legend){
                lvStyle[map_legend[x][0]] = MapModule.MapTool.setLayerStyle({fillColor:map_legend[x][1]});
            }
            if (Municipality.indexOf(regionId) >= 0 || Municipality.indexOf(parentId) >= 0){//如果是直辖市，那么在填充色块的时候应该调用区县一级的图层districtLayer
                if (districtLayer.getSource().getFeatures().length > 0){
                    colorLayer = mapTool.addColorLayer(districtLayer,lvStyle,levelData,thisColorLayer);
                } else {
                    setTimeout(function () {
                        colorLayer = mapTool.addColorLayer(districtLayer,lvStyle,levelData,thisColorLayer);
                    }, 1500);
                }
            } else {
                colorLayer = mapTool.addColorLayer(cityLayer,lvStyle,levelData,this.layerIndex.colorLayer);
            }


        };
        /**
         * 根据数据构建色块
         */
        this.buildCurrentLayerData = function(_data,map_legend){
            var levelData = {};
            //将指定日期的数据解析为分析数据，格式为键值对 : { 区域ID，图例数据 }
            for (var i = 0;i< _data.length;i++) {
                // console.log($('input:radio:checked').val());
                if(getParam().dsId===DS_Weather.id){
                    if($('input:radio:checked').val()==='最高温'){
                        var value = _data[i].maxValue;
                    }else{
                        var value = _data[i].minValue;
                    }
                }else{
                    var value = _data[i].value;
                }
                var percent = _data[i].percent;
                var percentAge = _data[i].percentAge;
                if (!value) continue;
                var tempObj={'value':value,'percent':percent,'percentAge':percentAge};
                if(map_legend){
                    for(var j=0;j<map_legend.length;j++){
                        var legend = map_legend[j];
                        var legend_value = legend[0].match(/-?\d+(.\d+)?/)[0];
                        if (Number(value) > Number(legend_value)) {
                            tempObj['LV'] = legend[0];
                            tempObj['color'] = legend[1];
                            tempObj['lvName'] = legend[2];
                            break;
                        }
                    }
                }

                levelData[_data[i].regionCode] = tempObj;
            }
            return levelData;
        };

        /**
         * 构建长势色块
         */
        this.buildGrowthLayerData = function (_data) {
            var levelData = {};
            //将指定日期的数据解析为分析数据，格式为键值对 : { 区域ID，图例数据 }
            for (var i = 0,k= _data.length; i < k ; i++) {
                var lowest = _data[i].lowest;
                var lower = _data[i].lower;
                var low = _data[i].low;
                var normal = _data[i].normal;
                var high = _data[i].high;
                var higher = _data[i].higher;
                var highest = _data[i].highest;
                var tempObj={'lowest':lowest,'lower':lower,'low':low,'normal':normal,'high':high,'higher':higher,'highest':highest};
                levelData[_data[i].regionCode] = tempObj;
            }
            return levelData;
        }

        /**
         * 移除所有的tiff图层
         */
        this.removeLayers = function(){
            mapTool.infoDlgClose();//关闭图层上的弹框
            if(!!colorLayer){
                //删除色块图层
                mapTool.removeLayer(colorLayer);
            }

            if(!!tifLayer){
                //删除分布图层
                mapTool.removeLayer(tifLayer);
            }
        };

        /**
         *鼠标移动事件 显示提示框
         *@version <1> 2017-11-27 XuZhenguo:
         */
        this.loadPointDiglog = function(){
            mapTool.map.on('pointermove',function(event) {

                var highStrokeColor = '#6495ED';//鼠标移入区域后高亮显示的边框颜色
                var feature = null;

                feature = mapTool.getFeatureByClick(event.pixel, districtLayer);
                if (feature && feature.get('levelData')) {
                    var param = getParam();
                    var dsId = param.dsId;//当前选中的数据集的id
                    var dsCode = param.dsCode;//当前选中的数据集英文代码
                    var dsName = param.dsName;//当前选中的数据集中文名
                    var unit = getDsInfo(dsCode).unit;//当前选中的数据集的度量单位
                    var levelData = feature.get('levelData');
                    if (!!feature) {
                        var highStyle = MapModule.MapTool.defineStyle({fillColor:"rgba(255,255,255, 0)",strokeColor:highStrokeColor,strokeWidth:4});
                        var chinaName = feature.get('china_name');
                        var local_name = feature.get('local_name');
                        var text = " " + chinaName +" ";
                        mapTool.highFeature(feature, highStyle,text);
                        if (local_name)
                            local_name += "&nbsp;&nbsp;&nbsp;";
                        else
                            local_name = "";
                        var content = '';
                        if (dsId == DS_Distribution.id) {
                            var val = levelData.value / 10000
                            content += local_name + chinaName + "<br/> 分布面积：" + val.toFixed(2) + " (" + unit + ")<br/>";
                            content += "占比：" + levelData.percentAge.toFixed(2) + "%"
                        }else if (dsId == DS_Yield.id){//估产
                            content += local_name  + chinaName + "<br/> "+dsName +"："+ levelData.value + " (" + unit + ")<br/>";
                        }else if(dsId == DS_Growth.id){//长势
                            content += local_name  + chinaName + "<br/> "
                            content += "很好："+ (levelData.higher/10000).toFixed(2) + " (" + unit + ")<br/>";
                            content += "好："+ (levelData.high/10000).toFixed(2) + " (" + unit + ")<br/>";
                            content += "较好："+ (levelData.highest/10000).toFixed(2) + " (" + unit + ")<br/>";
                            content += "正常："+ (levelData.normal/10000).toFixed(2) + " (" + unit + ")<br/>";
                            content += "较差："+ (levelData.lower/10000).toFixed(2) + " (" + unit + ")<br/>";
                            content += "差："+ (levelData.low/10000).toFixed(2) + " (" + unit + ")<br/>";
                            content += "很差："+ (levelData.lowest/10000).toFixed(2) + " (" + unit + ")<br/>";

                        }else {
                            content += chinaName + "  ?lvName  <br/> 当天" + dsName + " ： ?lvValue(" + unit + ")";
                        }


                        if (!!levelData) {
                            //分布与估产单位换算(原始单位亩,吨 换算为万亩,万吨)
                            var levelValue = levelData.value;
                            content = content.replace('?lvName', levelData['lvName'] || '');
                            content = content.replace('?lvValue', (!levelData.value && levelData.value != 0) ? "没有数据" : levelValue);
                            mapTool.infoDlgShow(event.coordinate, content);
                        }
                    }

                } else {
                    //关闭地图上弹框
                    mapTool.infoDlgClose();
                    //移除区县图层上的高亮选中效果
                    mapTool.removeHighFeature(districtLayer,highStrokeColor);
                    //移除色块图层上的高亮选中效果
                    // mapTool.removeHighFeature(colorLayer,highStrokeColor);
                }

            });
        };

    };
    /**
     *日期时间处理
     *@param
     *@version<1> 2018-10-24 limeiling:created
     * */

    //获取当前日期
    function getToday(){
        var nowdate = new Date();
        var y = nowdate.getFullYear();
        var m = nowdate.getMonth()+1;
        var d = nowdate.getDate();
        if(m<10) {
            m="0"+m;
        }
        if(d<10) {
            d="0"+d;
        }
        return y+'-'+m+'-'+d;
    };

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

    /**
     * 用户信息类
     * 1.用户修改方法
     * 2.修改区域方法
     * 3.用户退出
     *  @version1 2018-9-26 limeiling:created
     *
     */
    var adminInfoSet = {
        //用户信息
        adminInfo:function(){
            var userInfo = UserModule.UserUtil.getUserInfo();
            $('#spanNickname').text(userInfo.nickname);
            $('.adminLogin').hover(function(){
                $('.downBox').stop().slideToggle(300);
            });
        },

        //退出
        exitAdmin:function(){
            var aElemQuit = document.getElementById("aElemQuit");
            //退出事件
            aElemQuit.onclick = UserModule.UserUtil.quitLogin;

        }
    }

    /**
     * 1.固定侧边框类（左右两边）
     * 2.时间轴UI实现
     * 3.时间轴数据绑定
     *  @version1 2018-9-26 limeiling:created
     *
     */
    var sideFixedSet = {
        //右边固定部分
        rightSideFixed: function(){
            var flag = true;
            $('.toggleBtn').click(function(){
                if(flag){
                    $('.rightFixed').animate({"right":'-400px'},500);
                    $(this).addClass('up');
                    $('.con_content').animate({'width':'100%'},500);
                    $('.sHr').width('calc(100% - 108px)');
                    $('.timeDot').width('calc(100% - 213px)');
                    $('.sNext').animate({'right':'30px'},500);
                    flag = false;
                }else{
                    $('.rightFixed').animate({"right":'0'},500);
                    $(this).removeClass('up');
                    $('.con_content').animate({'width':($('.con_content').width()-417)+'px'},500);
                    $('.sHr').width('calc(100% - 514px)');
                    $('.timeDot').width('calc(100% - 613px)');
                    $('.sNext').animate({'right':'430px'},500);
                    flag = true;
                };
                $('.dot').width($(".sHr").width()/($('.hiddenDiv .dot').length > 10 ? 10 : $('.hiddenDiv .dot').length));
                $('.hiddenDiv').css('left',-$('.dot').width()*($('.dot .dotSan.on').parent('.dot').index()));
            });
            $(window).resize(function() {
                $('.dot').animate({'width':$(".sHr").width()/($('.hiddenDiv .dot').length > 10 ? 10 : $('.hiddenDiv .dot').length)},0);
                if($('.hiddenDiv .dot').length>10&&($('.dot .dotSan.on').parent('.dot').index()+1)%10==1){
                    $('.hiddenDiv').css('left',-$('.dot').width()*($('.dot .dotSan.on').parent('.dot').index()));
                }
            });
        },
    }

    /**
     * 获取用户选择的参数
     * @version <1> 2017-11-22 Hayden:Created.
     */
    var getParam = function(){
//      var selDataset = document.getElementById("selDsIndex");
//         var txtRegion = document.getElementById("txtRegion");

        var dsObj,regionObj,cropObj,dataTypeObj,param={};

        //获取区域参数
        var chinaName = txtRegion.value;
        var regionId = txtRegion.getAttribute("regionId");
        var regionCode = txtRegion.getAttribute("regionCode");
        var regionName = txtRegion.getAttribute("regionName");
        var parentId = txtRegion.getAttribute("parentId");
        var level = txtRegion.getAttribute("level");
        regionObj = {regionId:regionId,regionCode:regionCode,regionName:regionName,chinaName:chinaName,level:level,parentId:parentId,isQueryLargeArea:0};


        //数据集
        var selDs = $("#selDsIndex .on");
        var dsId = selDs.attr("dsId");
        var dsCode = selDs.attr("dsCode");
        dsObj = {dsId:dsId,dsCode:dsCode,dsName:selDs.find("a").html()};


        //获取作物参数
        var cropCode ,cropName,cropId;
        cropCode = $("#plants").find("option:selected").attr("cropCode");
        cropName = $("#plants").find("option:selected").attr("cropName");
        cropId = $("#plants").find("option:selected").attr("cropId");
        cropObj = {cropId:cropId,cropCode:cropCode,cropName:cropName};


        //获取精度
        /*var accuracyName = '高分一号16米';
        var accuracyId = '4010';
        var accuracyCode = 'GF1-16M';

        if(dsId == DS_Trmm.id){ //降雨
            accuracyName = "TRMM-0.25°"
            accuracyId = 4009;
            accuracyCode = "TRMM-0.25°";
        }else if(dsId == DS_T.id){ //地温
            accuracyName = "MOD11A1-1KM"
            accuracyId = 4007;
            accuracyCode = "MOD11A1-1KM";
        }else if(dsId == DS_Weather.id){ //气温

        }else if (dsId == DS_Growth.id){//长势
            accuracyName = "MOD09A1-500M"
            accuracyId = 4006;
            accuracyCode = "MOD09A1-500M";
            resolution = '4006';
        }else if(dsId == DS_Yield.id){//估产
            accuracyId = 4011;
        }
        var resolution = accuracyId;
        dataTypeObj={accuracyId:accuracyId,accuracyCode:accuracyCode,accuracyName:accuracyName,resolution:resolution};*/

        var dataTypeId ,dataTypeName,dataTypeCode;
        //获取数据源，是GF1,还是mod09a1
        if(selDataType.options.selectedIndex>=0){
            dataTypeId = selDataType.options[selDataType.options.selectedIndex].value;
            dataTypeName = selDataType.options[selDataType.options.selectedIndex].text;
            dataTypeCode = selDataType.options[selDataType.options.selectedIndex].getAttribute("accuracycode");
            // dataTypeObj={dataTypeId:dataTypeId,dataTypeCode:dataTypeCode,dataTypeName,dataTypeName};
            dataTypeObj={accuracyId:dataTypeId,accuracyCode:dataTypeCode,accuracyName:dataTypeName,resolution:dataTypeId};
        }


        //保存时间点数据
        param.startDate = $('#startDate').val() || '';
        param.endDate = $('#endDate').val() || '';
        param.dataTime = $('.dotSan.on').next('.dotValue').text();

        for(var key in regionObj)
            param[key] = regionObj[key];
        for(var key in dsObj)
            param[key] = dsObj[key];
        for(var key in dataTypeObj)
            param[key] = dataTypeObj[key];
        for(var key in cropObj)
            param[key] = cropObj[key];


        return param;
    };



    /**
     * 左侧列表：根据区域加载数据集列表
     * 区域控件
     * 时间点
     * 作物下拉框
     * @param dsList 获取的数据
     * @version<1> 2018-9-28 limeiling:created
     */
    var dataUlSet = {
        /**
         * 根据数据集的变化加载对应的数据
         * @param
         * version<1> 2018-10-26 limeiling:created
         * */
        loadDataSetChart:function(currentId){
            //初始化数据
            $('#plants,.plantsLable').show();
            $('#chartList ul').show();
            $('#chartList ul li').hide();
            $('#chartList').css('height','100%');
            if(currentId===DS_Brief.id || currentId===DS_Report.id){//报告简报
                $('.tempRow').hide();
                //----屏蔽作物下拉框-----
                $('#plants,.plantsLable').hide();
                //-----屏蔽时间轴------
                $('.sPrev,.sHr,.sNext,.dotSan,.dotValue,#timeDot').hide();
                //-------------------
                $('#divMap').hide();
                $('#chartList').css('height',0);
                $('#ddReportList,.con_content').show();
                reportAndBrief.loadReportTableList();

            }else{
                $('#divMap').show();
                $('#ddReportList').hide();
                $('.tempRow').css('display','none');
                $('#mytable').parents('li').css('border',0);//去掉表格下划线
                //关于气象的作物下拉框隐藏
                if(currentId=== DS_Distribution.id){//分布
                    $('#chartLi1,#chartLi2').parents('li').show();
                }else if(currentId=== DS_Yield.id){//估产
                    $('#chartLi1,#chartLi2').parents('li').show();
                }else if(currentId=== DS_Trmm.id||currentId=== DS_T.id||currentId=== DS_Weather.id){
                    $('#mytable').parents('li').css('border-bottom','1px solid #a7a7a7');//表格下划线
                    $('#plants,.plantsLable').hide();
                    if(currentId=== DS_Weather.id){
                        $('.tempRow').css('display','inline-block');
                    };

                    $('#chartLi3,#chartLi5').parents('li').show();
                    $('#chartLi3').parent('li').html('').append('<div id="chartLi3"></div>');

                    $('#chartLi5 .reportTxt').empty();
                    // loadData.loadReportTxt();
                }else if(currentId=== DS_Growth.id){//长势
                    $('#chartLi1,#chartLi4').parents('li').show();
                };

                //初始化地图,并加载矢量边界
                // mapClazz.initMap(DEFAULT_REGION_CONFIG.region_id);

                dataUlSet.loadDateUI(); // 加载默认时间
                $('#divMap').show();
                $('.con_content').hide();
            }
        },

        /**
         * 左侧菜单(各数据集)
         * @param {Object} dsList
         * @param {Object} oldDsId
         */
        loadDatasetUI:function(dsList,oldDsId){
            var param = getParam();
            var selDataset = document.getElementById("selDsIndex");
            selDataset.innerHTML="";
            var dsListArry = dsList;
            for(var i in dsListArry){
                var dsId = dsListArry[i].datasetId;
                var dsCode = dsListArry[i].datasetCode;
                var dsName = dsListArry[i].datasetName;
                var selLiHtml = '<li dsCode="'+dsCode+'" dsId="'+dsId+'"><a class="'+'icon'+i+'">'+dsName+'</a></li>';
                selDataset.innerHTML+=selLiHtml;
                $('.sideIconUl li').eq(0).addClass('on');//默认分布
            };

            $('.sideIconUl li a').on('click',function(){
                mapClazz.loadVector();
                //初始化表格
                $('#mytable caption,#mytable thead,#mytable tbody,.data-table-bottom-box').empty();

                var _index = $(this).parents('li').index();
                var _dsid = $(this).parents('li').attr('dsId');
                $('.sideIconUl li').removeClass('on').eq(_index).find('a').parents('li').addClass('on');

                var currentId = _dsid;
                dataUlSet.loadDataSetChart(currentId);
                // dataUlSet.changeRegionEvent();
                //重新加载时间控件
                // dataUlSet.loadDateUI();
                if(UserModule.UserUtil.getUserInfo().roleCode!='Role_Admin'&&UserModule.UserUtil.getUserInfo().roleCode!=null) {
                    dataUlSet.loadDataTimeEvent();
                }
                dataUlSet.changeCropDataEvent();

            });
        },
        //加载时间
        loadDataTimeEvent:function(){
            var param = getParam();
            var regionId =  param.regionId;
            var cropId = null;
            if(param.cropId!=undefined) {
                cropId = param.cropId;
            }
            var accuracyId = param.accuracyId;
            var dsId =  param.dsId;
            var opts = {
                url:User_Env.DataTime_URL,
                data:{regionId:regionId,cropId:cropId,dataTypeId:accuracyId,dsId:dsId},
                type:"GET",
                async:false
            };
            alert(JSON.stringify(opts));
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
                    $('#startDate').val(showSDate);
                    $('#endDate').val(showDDate);
                    param.startDate = showSDate;
                    param.endDate = showDDate;
                    /*	$('#txtStartDate').val(result.data.startDate);
                        $('#txtEndDate').val(result.data.startDate);
                        param.startDate = result.data.startDate;
                        param.endDate = result.data.startDate;*/
                    dateUtil.oldEndDate = showDDate;
                    // findDsDataTime();
                }
            };
            ajax.opts.errorFun = function(){
                console.info("加载时间失败");
            };
            ajax.run();

        },

        /**
         * 1.加载左侧菜单(各数据集+简报和报告)
         * @version <1> 2017-12-25 limeiling:Created.
         */
        loadDatasetList:function(regionId){
            var param = getParam();
            //加载数据集
            //加载数据集增加权限控制
            var opts = {
                url:User_Env.Region_DsList_URL,
                data:{regionId:regionId},
                type:"GET",
                async:false
            };
            var ajax = new BaseAjax();
            ajax.opts = opts;
            ajax.opts.successFun = function(result){
                if(result && result.data){
                    var dsList = result.data;
                    dataUlSet.loadDatasetUI(dsList);
                    //改变数据集的时候作物下拉框也隐藏
                    dataUlSet.changeCropDataEvent();
                    if(param.dsId === DS_Trmm.id || param.dsId === DS_T.id || param.dsId === DS_Weather.id){
                        $('#plants,.plantsLable').hide();
                    }
                }
            };
            ajax.opts.errorFun = function(){
                console.info("加载数据集失败");
            };
            ajax.run();
        },

        /**
         * 区域change事件触发:
         * 1.根据区域和数据集加载作物
         * @version<1> 2018-9-28 limeiling:created
         */
        changeRegionEvent:function(){
            // var param = getParam();
            var opts = {url:User_Env.Region_URL,closeFun:function(){
                    var param = getParam();
                    // dataUlSet.loadDatasetList(param.regionId);
                    //长势/估产/分布加载作物
                    if(param.dsId === DS_Distribution.id || param.dsId === DS_Growth.id || param.dsId === DS_Yield.id
                        || param.dsId === DS_Brief.id || param.dsId === DS_Report.id){
                        //根据数据集加载作物列表
                        dataUlSet.changeCropDataEvent();
                    }

                    //先要加载数据集
                    dataUlSet.loadDatasetList(param.regionId);

                    //如果是报告或者简报下，区域变更则重新查询报告或简报
                    if (param.dsId == DS_Brief.id || param.dsId == DS_Report.id) {
                        reportAndBrief.loadReportTableList();
                    }
                    //加载底图
                    mapClazz.loadVector()

                    //加载数据时间点
                    dataUlSet.findDsDataTime();
                }};
            var regionSelector = new RegionModule.RegionSelector("txtRegion",opts);
            regionSelector.show();
        },


        /**
         * 2.根据数据集,区域加载作物列表
         * @version <1> 2017-12-29 Hayden:Created.
         */
        changeCropDataEvent:function(){
            // var userInfo = UserModule.UserUtil.getUserInfo();
            var param = getParam();
            var oldDatatypeId = param.datatypeId;
            var opts = {
                url:User_Env.Region_Ds_CropList_URL,
                data:{regionId:param.regionId,dsId:param.dsId},
                type:'GET',
                async:false//设置异步为false，即作物下拉框加载完后，才继续执行其他代码
            };
            var ajax = new BaseAjax();
            ajax.opts = opts;
            ajax.opts.successFun = function(result){
                // alert(">>>result:"+JSON.stringify(result));
                if(result && result.data){
                    oldCrop = "";
                    var cropList = result.data.cropList;
                    dataUlSet.loadCropUI(cropList);
                    var dataTypeList = result.data.dataTypeList;
                    // alert(JSON.stringify(dataTypeList));
                    dataUlSet.loadDataTypeUI(dataTypeList,oldDatatypeId);
                    // $('#plants').show();
                }
            };
            ajax.opts.errorFun = function(){
                console.info("加载数据集分辨率与作物失败.");
            };
            ajax.run();
        },


        /**
         * 根据当前数据集(左侧菜单)，加载时间控件
         *降雨/地温/气温显示当前日期的前一个月范围内数据
         *分布/长势/估产显示当前年份的数据
         * @version <1> 2017-12-29 Hayden:Created.
         */
        loadDateUI:function(){
            // $('#reservation').val(startDate+' - '+endDate);
            var param = getParam();
            var startDate = '2015-01-01';
            var endDate = new Date().getFullYear()+'-12-31';
            if(param.dsId === DS_Trmm.id || param.dsId === DS_T.id || param.dsId === DS_Weather.id){
                startDate = moment().subtract(30, 'days').format('YYYY-MM-DD');
                // startDate = '2018-09-30'
                endDate = getToday();
                /*$('#startDate').val(startDate);
            $('#endDate').val(endDate);*/
            };

            //加载dom需要在加载数据之前
            if(param.dsId===DS_T.id||param.dsId===DS_Trmm.id||param.dsId===DS_Weather.id){
                $('#mytable').parents('li').css('border-bottom','1px solid #a7a7a7').show();
                $('#plants,.plantsLable').hide();
                if(param.dsId===DS_Weather.id){
                    $('.tempRow').css('display','inline-block');
                }
                $('#chartLi3,#chartLi5').parents('li').show();
                echartsSet.echartsBrokenLineUI(document.getElementById('chartLi3'));

                //气象报告
                // loadData.loadReportTxt();
            }else if(param.dsId===DS_Distribution.id||param.dsId===DS_Yield.id){
                $('#chartLi1,#chartLi2').parents('li').show();
                echartsSet.echartsPieUI(document.getElementById('chartLi1'));
                echartsSet.echartsCategoryUI(document.getElementById('chartLi2'));
                $('#mytable').parents('li').css('border',0);//去掉表格下划线
            }else if(param.dsId===DS_Growth.id){
                $('#chartLi1,#chartLi4').parents('li').show();
                echartsSet.echartsPieUI(document.getElementById('chartLi1'));
                echartsSet.echartsStackUI(document.getElementById('chartLi4'));
                $('#mytable').parents('li').css('border',0);//去掉表格下划线
            }
            $('.tableBox').parents('li').show();

            dataUlSet.findDsDataTime(); //查询时间点
        },

        /**
         * 数据时间点，并加载数据
         * @param:startDate,endDate当年的一月一号到当年的十二月31号
         */
        findDsDataTime:function () {
            // levelData = {};
            // var pointJquery = $('.point');
            var param = getParam();
            var dsInfo = getDsInfo(param.dsCode);
            //报告和简报不需要时间轴
            if (param.dsId == DS_Report.id || param.dsId == DS_Brief.id) return;

            var opts = {
                url:dsInfo.FindDatetimePoint,
                data:param,
                type:'GET'
            };
            var ajax = new BaseAjax();
            ajax.opts = opts;
            ajax.opts.successFun = function(result){
                var _data = result.data;
                //测试数据
                timeSliderSet.showMultiDotTime(_data);
            };
            ajax.opts.errorFun = function(){
                console.info("时间点加载失败.");
            };
            ajax.run();
        },

        /**
         * 获取区域作物列表数据
         * @param cropList : 作物列表数据/默认选中第一条
         * @version <1> 2017-11-10 Hayden:Created.
         */
        loadCropUI:function(cropList){
            var setPlants = document.getElementById('plants');
            setPlants.innerHTML="";
            if(cropList.length>0){
                for(var i in cropList){
                    var cropId = cropList[i].cropId;
                    var cropCode = cropList[i].cropCode;
                    var cropName = cropList[i].cropName;
                    var selLiHtml = '<option value="'+cropId+'" cropcode="'+cropCode+'" cropid="'+cropId+'" cropname="'+cropName+'">'+cropName+'</option>';
                    setPlants.innerHTML+=selLiHtml;
                };
            }

        },

        /**
         * 根据dataTypeList数据，在UI中加载分辨率
         * @version <1> 2017-12-29 Hayden:Created.
         * @version <2> 2018-2-6 cxw update:
         * 1.var datatypeName = dataTypeList[i].datatypeName 改为 var datatypeValue = dataTypeList[i].datatypeValue
         * 2.var option = new Option(datatypeName,datatypeId)改为var option = new Option(datatypeValue,datatypeId)
         */
        /*loadDataTypeUI:function(dataTypeList,oldDatatypeId){
            selDataType.innerHTML="";
            var accuracyName = '高分一号16米';
            var accuracyId = '4010';
            var accuracyCode = 'GF2-2M';
            var option = new Option(accuracyName,accuracyId);
            option.setAttribute("accuracyCode",accuracyCode);
            option.setAttribute("accuracyId",accuracyId);
            selDataType.options.add(option);
        },*/
        loadDataTypeUI:function(dataTypeList,oldDatatypeId){
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
        },
    };

    /**
     * 实现图标组件
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
        },
        //echart  折线图
        echartsBrokenLineUI: function(bindName){
            var myChart = echarts.init(bindName);
            option = {
                backgroundColor: '#fff',
                title:{
                    text: '',
                    x:'center',
                    y:'10px',
                    textStyle: {
                        fontSize: 16,
                        fontWeight: 'normal'
                    }
                },
                tooltip: {
                    trigger: 'axis'
                },
                color:[],
                legend: {
                    data:[],
                    y:'35',
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
                    axisLabel: {
                        show: true,
                        textStyle: {
                            fontSize: 10,
                            color: '#000'
                        }
                    }
                },
                yAxis: {
                    type: 'value',
                    name:'',
                },
                series: []
            };
            myChart.setOption(option,true);
            window.onresize = myChart.resize;
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
        }
    };

    /**
     * 时间轴：当时间到第一个时右移按钮失效和最后一个时左移按钮失效
     * @version<1> 2018-9-28 limeiling:created
     */
    var timeSliderSet = {
        /**
         * 在时间轴上显示时间点
         *  将时间轴分为<=8等份(注意:如果是3个时间点,则应该是4等分),然后顺序将时间点显示在时间轴上,超出部分隐藏
         * @param _data : 时间点集合
         * @version<1> 2018-10-16 lcw :Created.
         */
        showMultiDotTime : function(_data){
            var param = getParam();
            var dsInfo = getDsInfo(param.dsCode);
            mapClazz.removeLayers(); //移除图层
            mapClazz.showLegend(dsInfo.Map_Legend);//加载图例

            if( _data == null || _data.length == 0){
                $(".mainContent .sPrev, .mainContent .sNext, .mainContent .sHr, .mainContent .dot").css("display","none");
                $('#chartList ul').hide();
                $('.noDataBox').show();
            }else{
                $('#chartList ul').show();
                $('.noDataBox').hide();
                $(".mainContent .sPrev, .mainContent .sNext, .mainContent .sHr").css("display","block");
                $(".mainContent .dot").remove();
                var _width = $(".mainContent .sHr").width(); //获取时间轴横线的长度
                var _num = _data.length;

                var perWidth = _width / (_num > 10 ? 10 : _num); //每个时间点

                var _span = "";
                for(var i = 0 ; i < _num; i++){
                    var _left = (0 + perWidth * (i+1) );
                    _span += "<div class='dot'><i></i><span class='dotSan' index='"+ (i+1) +"'></span><span class='dotValue' style='left:"+ (_left - 25) +"px;' >" + _data[i] + "</span></div>";
                }
                $('.mainContent .timeDot').remove();
                $(".mainContent").append('<div class="timeDot"><div class="hiddenDiv">'+_span+'</div></div>');
                $('.dot').width(perWidth);
                $(".mainContent .dotSan:first").addClass("on");

                loadData.loadAllData();  //加载完时间轴后,加载数据
                $('.dotSan').on('click',function(){
                    $(this).addClass('on').parents('.dot').siblings().children('.dotSan').removeClass('on');
                    loadData.loadAllData();
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

                var _index = $('.dotSan.on').index();
                var timeDiv = $('.hiddenDiv');
                var timeSpot = $('.dot');
                if($('.dot').length<=10){
                    $('.sPrev').addClass('noclick');
                }else{
                    $('.sPrev').removeClass('noclick');
                }
                $('.sNext').addClass('noclick');

                if($('.toggleBtn').hasClass('up')){
                    $('.timeDot').css('width','calc(100% - 213px)');
                }else{
                    $('.timeDot').css('width','calc(100% - 619px)');
                }
                var showDotNum = Math.round($('.timeDot').width()/timeSpot.width());
                //左移
                $('.sPrev').off('click').on('click',function(){
                    if(parseInt(timeDiv.css('left'))>=-(_num-showDotNum-1)*timeSpot.width()){
                        timeDiv.stop().animate({'left': -(_index+showDotNum-1)*timeSpot.width()+'px'},200,function(){
                            if(parseInt(timeDiv.css('left'))>=-(_num-showDotNum-1)*timeSpot.width()){
                                $('.sPrev').removeClass('noclick');
                            }else{
                                $('.sPrev').addClass('noclick');
                            }
                            $('.sNext').removeClass('noclick');
                        });
                        $('.dotSan').removeClass('on');
                        $('.dot').eq(_index+showDotNum-1).find('.dotSan').addClass('on');
                        _index = _index+showDotNum;
                        loadData.loadAllData();
                    }else{
                        $(this).addClass('noclick');
                    }
                });
                //右移
                $('.sNext').off('click').on('click',function(){
                    if(parseInt(timeDiv.css('left'))<0&&_index>0){
                        timeDiv.stop().animate({'left': parseInt(timeDiv.css('left'))+showDotNum*timeSpot.width()+'px'},200,function(){
                            if(parseInt(timeDiv.css('left'))<0&&_index>0){
                                $('.sNext').removeClass('noclick');
                            }else{
                                $('.sNext').addClass('noclick');
                            }
                            $('.sPrev').removeClass('noclick');
                        });
                        $('.dotSan').removeClass('on');
                        $('.dot').eq(_index-showDotNum-1).find('.dotSan').addClass('on');
                        _index = _index-showDotNum;
                        loadData.loadAllData();
                    }else{
                        $(this).addClass('noclick');
                    }
                });
            }

        }
    };

    /**
     * 双日历控件
     * @version<1> 2018-9-28 limeiling:created
     */
    var stay = {
        start:$('#startDate'),
        end:$('#endDate'),
        today:(new Date()),
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
                    var param = getParam();
                    stay.end.datepicker('option', 'minDate', new Date(moment(stay.start.val()).add('days', 1)));
                    if(param.dsId===DS_Trmm.id||param.dsId===DS_T.id||param.dsId===DS_Weather.id){//气象
                        var lastMonthDate = moment(stay.start.val()).add(30, 'days').format('YYYY-MM-DD')
                        if(getAll(moment(dateText).format("YYYY-MM-DD"),stay.end.val()).length>31){
                            stay.end.val(lastMonthDate);
                        }
                    }else if (param.dsId == DS_Report.id || param.dsId == DS_Brief.id){
                        reportAndBrief.loadReportTableList();//查询简报和报告
                    }
                    dataUlSet.findDsDataTime(); //查询时间点，加载时间轴
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
                // minDate : new Date(moment(stay.start.val()).add('days', 1)),
                maxDate:'+0D',
                onSelect:function(dateText,inst){
                    var param = getParam();
                    if(param.dsId===DS_Trmm.id||param.dsId===DS_T.id||param.dsId===DS_Weather.id){//气象
                        var lastMonthDate = moment(stay.start.val()).add(30, 'days').format('YYYY-MM-DD')
                        if(getAll(stay.start.val(),moment(dateText).format("YYYY-MM-DD")).length>31){
                            stay.end.val(lastMonthDate);
                            $('.waringInfo').show().delay(1500).hide(0);
                        }
                    }else if (param.dsId == DS_Report.id || param.dsId == DS_Brief.id){
                        reportAndBrief.loadReportTableList();//查询简报和报告
                    }
                    dataUlSet.findDsDataTime(); //查询时间点，加载时间轴
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
            // day == 0 ? strDay: day == 1 ?
            //     (strDay = '明天') : day == 2 ?
            //     (strDay = '后天') : (strDay = stay.transformStr(obj.datepicker('getDate').getDay(),strDay));
            strDay = stay.transformStr(obj.datepicker('getDate').getDay(),30)
            return strDay;
        },
        inputVal:function(){
            // stay.inputTimes(stay.start,1);
            // stay.inputTimes(stay.end,2);
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
     * 加载默认信息
     * @param:
     *@version<1> 2018-09-27 lcw :Created.
     */
    var loadDownBox = function(){
        //区域数据加载
        // txtRegion.value='重庆市';
        // txtRegion.setAttribute("regionId",'3103000019');
        // txtRegion.setAttribute("regionName",'Chongqing');
        // txtRegion.setAttribute("level",'3');
        // txtRegion.setAttribute("regionCode",'CHN-CQ-CQG');

        /*txtRegion.value=DEFAULT_REGION_CONFIG.china_name;
        txtRegion.setAttribute("regionId",DEFAULT_REGION_CONFIG.region_id);
        txtRegion.setAttribute("regionName",DEFAULT_REGION_CONFIG.region_name);
        txtRegion.setAttribute("level",DEFAULT_REGION_CONFIG.level);
        txtRegion.setAttribute("regionCode",DEFAULT_REGION_CONFIG.region_code);*/

        //获取默认用户信息作物()
        // dataUlSet.changeCropDataEvent();

        $('#plants').show();
        $('.plantsLable').show();
        $('#chartList ul').show();
        $('#chartList ul li').hide();

        //设置默认的作物为选中
        // var cropId = userInfo.defaultProduct.cropId;
        // $('#plants').val(cropId);

        // var cropCode = userInfo.defaultProduct.cropCode;
        // var cropName = userInfo.defaultProduct.cropName;
        //
        // var _option = '<option value="'+cropId+'" cropcode="'+cropCode+'" cropid="'+cropId+'" cropname="'+cropName+'">'+cropName+'</option>';
        //
        // var setPlants = document.getElementById('plants');
        // setPlants.innerHTML=_option;//默认作物


        //初始化地图,并加载矢量边界
        var userInfo = UserModule.UserUtil.getUserInfo();
        var regionId = userInfo.defaultProduct.regionId;
        // mapClazz.initMap(DEFAULT_REGION_CONFIG.region_id);
        mapClazz.initMap(DEFAULT_REGION_CONFIG.regionId);

        dataUlSet.loadDateUI(); // 加载默认时间
        dataUlSet.findDsDataTime(); //查询时间点

    };
    /**
     * 登录成功后，根据用户加载默认的数据产品
     * @version <1> 2017-12-29 Hayden:Created.
     */
    var initUserDefaultProduct = function(){
        //设置用户默认数据集产品
        var userInfo = UserModule.UserUtil.getUserInfo();
        if(userInfo){
            if(userInfo.defaultProduct){
                if(userInfo.defaultProduct.regionId){
                    txtRegion.value=userInfo.defaultProduct.chinaName;
                    txtRegion.setAttribute("regionId",userInfo.defaultProduct.regionId);
                    txtRegion.setAttribute("regionName",userInfo.defaultProduct.regionName);
                    txtRegion.setAttribute("level",userInfo.defaultProduct.level);
                    txtRegion.setAttribute("regionCode",userInfo.defaultProduct.regionCode);
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

                    $('#startDate').val(showSDate);
                    $('#endDate').val(showDDate);
                    /*	var param = getParam();
                        param.startDate = showSDate;
                        param.endDate = showDDate;*/
                    /*$('#txtStartDate').val(userInfo.defaultProduct.startDate);
                    $('#txtEndDate').val(userInfo.defaultProduct.endDate);*/
                }
            }
        }
    }
    /**
     * 加载固定侧边栏点击切换和右侧侧边栏伸缩
     * @param
     * @version<1> 2018-9-27 limeiling:created
     */
    var loadSideFixed = function(){
        sideFixedSet.rightSideFixed();
        //加载左侧边栏数据
        // dataUlSet.initDataSetFun(dataSetIdName);
        var userInfo = UserModule.UserUtil.getUserInfo();
        var regionId = userInfo.defaultProduct.regionId;
        dataUlSet.loadDatasetList(regionId);
    };

    /**
     * 加载用户信息
     * @param:修改区域，修改密码，退出
     * @version<1> 2018-9-27 limeiling:created
     */
    var loadAdminInfo = function(){
        adminInfoSet.adminInfo();
        adminInfoSet.exitAdmin();
        commons.menuClickFun();//修改密码
    };

    /**
     * 加载各数据集的图表数据
     * @param
     * @version<1> 2018-9-27 limeiling:created
     */
    var loadData = {
        // funcSet.map();
        loadAllData : function(){
            mapClazz.removeLayers(); //移除图层
            var param = getParam();
            loadData.loadCurrentData();
            loadData.loadBeyondCurrentData();//重载图层

            if (param.dsId ===DS_Report.id || param.dsId === DS_Brief.id){//报告和简报
                return;
            };
            if(param.dsId === DS_Growth.id){//长势
                loadData.loadGrowthData(param);
            }else{
                if(param.dsId === DS_Trmm.id||param.dsId === DS_T.id||param.dsId===DS_Weather.id){//降雨、地温、气温
                    loadData.loadReportTxt();
                }
                loadData.loadThreeYreasData(param);
            }
        },
        loadReportTxt:function(){
            var param = getParam();
            var dsInfo = getDsInfo(param.dsCode);
            $('#chartLi5 h1').html('').html(param.dsName+'报告');
            // $('#chartLi5 .reportTxt').empty();
            // $('#chartLi5').html('');
            var opts = {
                url: dsInfo.Briefing_URL,
                data:param,
                type:'GET'
            };
            var ajax = new BaseAjax();
            ajax.opts = opts;
            ajax.opts.successFun = function(result){
                if(result.data){
                    // $('#chartLi5').append('<div class="reportTxt"></div>');
                    $('#chartLi5 .reportTxt').empty().append(result.data);
                }else{
                    $('#chartLi5 .reportTxt').html('<span style="text-align:center;line-height:220px; display:block; font-size:14px;">暂无报告</span>');
                }
            };
            ajax.opts.errorFun = function(){
                console.info("报告加载失败.");
            };
            ajax.run();
        },
        loadtableListPage:function(result,paramRegion){
            //初始化表格
            var param = getParam();
            var dsInfo = getDsInfo(param.dsCode);
            var selectAreaName = param.chinaName.indexOf("|")>=0?$.trim(param.chinaName.substring(param.chinaName.lastIndexOf('|') + 1, param.chinaName.length)):param.chinaName;

            //初始化表格
            $('#mytable caption').html('');
            $('#mytable thead').html('');
            $('#mytable tbody').html('');
            $('.data-table-bottom-box').html('');
            if(dsInfo.id===DS_Trmm.id||dsInfo.id===DS_T.id||dsInfo.id===DS_Weather.id){
                $('#mytable caption').attr('title',param.startDate+'至'+param.endDate+selectAreaName+dsInfo.threeTitle).html(param.startDate+'至'+param.endDate+selectAreaName+dsInfo.threeTitle);
            }else if(dsInfo.id===DS_Growth.id){
                if(paramRegion===undefined){
                    $('#mytable caption').attr('title',param.dataTime+selectAreaName+param.cropName+'两年长势统计数据').html(param.dataTime+selectAreaName+param.cropName+'两年长势统计数据');
                }else{
                    $('#mytable caption').attr('title',param.dataTime+paramRegion.regionName+param.cropName+'两年长势统计数据').html(param.dataTime+paramRegion.regionName+param.cropName+'两年长势统计数据');
                }
            }else{
                $('#mytable caption').attr('title',param.dataTime+selectAreaName+param.cropName+dsInfo.threeTitle+'数据').html(param.dataTime+selectAreaName+param.cropName+dsInfo.threeTitle+'数据');
            }

            if(result.data!= null && !$.isEmptyObject(result.data)){//有数据时
                var mydata=[];
                if(dsInfo.id===DS_Distribution.id){//分布
                    // $('#mytable caption').html('').html('下级行政区'+param.cropName+dsInfo.threeTitle+'('+DS_Distribution.unit+')');
                    var lastTwoYear = parseInt(param.dataTime.substring(0,4))-2;//前两年
                    var lastYear = parseInt(param.dataTime.substring(0,4))-1;//前一年
                    var currentYear = parseInt(param.dataTime.substring(0,4));//当年
                    $('#mytable thead').html('').append('<tr>'+
                        '<th title="区域">区域</th>'+
                        '<th title="'+lastTwoYear+'年('+dsInfo.unit+')">'+lastTwoYear+'年('+dsInfo.unit+')</th>'+
                        '<th title="'+lastYear+'年('+dsInfo.unit+')">'+lastYear+'年('+dsInfo.unit+')</th>'+
                        '<th title="'+currentYear+'年('+dsInfo.unit+')">'+currentYear+'年('+dsInfo.unit+')</th>'+
                        '</tr>')
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
                    // $('#mytable caption').html(param.dataTime+param.chinaName+param.cropName+dsInfo.title+'('+DS_Growth.unit+')');
                    $('#mytable thead').append('<tr><th title="等级">等级</th><th title="面积('+dsInfo.unit+')">面积('+dsInfo.unit+')</th></tr>');

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
                    for(var i=0;i<mydata.length;i++){
                        var _tr = '<tr><td>'+mydata[i].one+'</td><td>'+mydata[i].two+'</td></tr>'
                        $('#mytable tbody').append(_tr);
                    }
                }else if(dsInfo.id===DS_Yield.id){//估产
                    // $('#mytable caption').html('').html(param.dataTime+param.chinaName+param.cropName+dsInfo.title+'('+DS_Yield.unit+')');
                    $('#mytable thead').html('').append('<tr><th title="区域">区域</th><th title="产量预估('+dsInfo.unit+')">产量预估('+dsInfo.unit+')</th><th title="去年同期('+dsInfo.unit+')">去年同期('+dsInfo.unit+')</th><th title="增长率">增长率</th></tr>');
                    var opts = {
                        url: dsInfo.Mom_URL_table,
                        data:param,
                        type:'GET'
                    };
                    var ajax = new BaseAjax();
                    ajax.opts = opts;
                    ajax.opts.successFun = function(res){
                        for(var i=0;i<res.data.length;i++){
                            var rowJson = {
                                'one': res.data[i].regionName,
                                'two': (res.data[i].value/10000).toFixed(2),
                                'three': (res.data[i].lastValue/10000).toFixed(2),
                                'four':(res.data[i].percent*100).toFixed(2)+'%'
                                // 'four': res.data[i].percent===0?0:(res.data[i].percent>0?((res.data[i].percent)*100).toFixed(2)+'% ↑':((res.data[i].percent)*100).toFixed(2)+'% ↓')
                            }
                            for(attr in rowJson){
                                if(rowJson[attr]===null){
                                    rowJson[attr] = '--';
                                }
                            }
                            mydata.push(rowJson);
                            $('.data-table-bottom-box').html('');
                            $('#mytable').yhhDataTable({
                                'paginate': {
                                    'changeDisplayLen': true,
                                    'type': 'updown',
                                    'visibleGo': true
                                },
                                'tbodyData': {
                                    'enabled': true,
                                    'source': mydata
                                }
                            });
                        }
                    };
                    ajax.opts.errorFun = function(){
                        console.info("当年数据加载失败.");
                    };
                    ajax.run();
                }else if(dsInfo.id===DS_Weather.id){//气温
                    var dsInfo = getDsInfo(param.dsCode);//获取当前数据集的数据
                    // $('#mytable caption').html(param.startDate+'--'+param.endDate+param.chinaName+dsInfo.threeTitle+'('+dsInfo.unit+')');
                    $('#mytable thead').append('<tr><th title="区域">区域</th><th width="100" title="日期">日期</th><th title="最高温('+dsInfo.unit+')">最高温('+dsInfo.unit+')</th><th title="最低温('+dsInfo.unit+')">最低温('+dsInfo.unit+')</th></tr>');
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
                    $('#mytable thead').append('<tr><th title="区域">区域</th><th width="90" title="日期">日期</th><th title='+param.dsName+'('+dsInfo.unit+')>'+param.dsName+'('+dsInfo.unit+')</th><th title="上月同比('+dsInfo.unit+')">上月同比('+dsInfo.unit+')</th><th title="增长率">增长率</th></tr>');
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
                if(dsInfo.id!=DS_Yield.id&&dsInfo.id!=DS_Growth.id){
                    $('#mytable').yhhDataTable({
                        'paginate': {
                            'changeDisplayLen': true,
                            'type': 'updown',
                            'visibleGo': true
                        },
                        'tbodyData': {
                            'enabled': true,
                            'source': mydata
                        }
                    });
                }
            }else{
                // $('#mytable').parents('li').show();
                $('#mytable tbody').html('<tr><td style="color:#fff; text-align:center; border: 0; line-height: 200px;">暂无数据</td></tr>');
            }
        },
        loadGraphYearsData:function(result){
            var param = getParam();
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
                //console.log("result.data[lastTwoYear]",result.data[lastTwoYear]);
                if((result.data[lastTwoYear][i].regionId.toString()!=param.regionId&&result.data[lastTwoYear].length>1)||(result.data[lastTwoYear].length===1&&result.data[lastTwoYear][i].regionId.toString()===param.regionId)){
                    //柱状图数据
                    arry.push(result.data[lastTwoYear][i].regionName);
                    lastTwoData.push(result.data[lastTwoYear][i]===undefined||(result.data[lastTwoYear][i].value/10000).toFixed(2)=='0.00'||result.data[lastTwoYear][i]==null?'--':(result.data[lastTwoYear][i].value/10000).toFixed(2));
                    lastData.push(result.data[lastYear][i]===undefined||(result.data[lastYear][i].value/10000).toFixed(2)=='0.00'||result.data[lastYear][i]==null?'--':(result.data[lastYear][i].value/10000).toFixed(2));
                    currentData.push(result.data[currentYear][i]===undefined||(result.data[currentYear][i].value/10000).toFixed(2)=='0.00'||result.data[currentYear][i]==null?'--':(result.data[currentYear][i].value/10000).toFixed(2));
                }
            }
            option.xAxis[0].data=arry;

            option.series = [
                {
                    name: lastTwoYear.toString(),
                    type: 'bar',
                    label: {
                        normal: {
                            show: true,
                            position: 'top',
                            color:'#fff',
                            // data: ['A','B','C']
                        },
                    },
                    data: lastTwoData,
                },
                {
                    name: lastYear.toString(),
                    type: 'bar',
                    label: {
                        normal: {
                            show: true,
                            position: 'top',
                            color:'#fff',
                            // data: ['A','B','C']
                        },
                    },
                    data: lastData
                },
                {
                    name: currentYear.toString(),
                    type: 'bar',
                    label: {
                        normal: {
                            show: true,
                            position: 'top',
                            color:'#fff',
                            // data: ['A','B','C']
                        },

                    },
                    data: currentData,
                }
            ];
            myChart.setOption(option);
            window.onresize = myChart.resize;
        },
        //获取当年的数据
        loadCurrentData:function(paramRegion){
            var param = getParam();
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
                data:param,
                type:'GET'
            };

            var ajax = new BaseAjax();
            ajax.opts = opts;
            ajax.opts.successFun = function(result){
                if(paramRegion===undefined){
                    var myChart = echarts.init(document.getElementById('chartLi1'));
                    var option = myChart.getOption();
                    var selectAreaName = param.chinaName.indexOf("|")>=0?$.trim(param.chinaName.substring(param.chinaName.lastIndexOf('|') + 1, param.chinaName.length)):param.chinaName;
                    var optionTitle = param.dataTime+selectAreaName+param.cropName+dsInfo.title+'图';
                    option.title[0].text=optionTitle.length > 29 ? (optionTitle.slice(0,29)+"...") : optionTitle ;
                    option.series[0].data = [];
                    option.graphic[0].elements[0].style.text='单位：'+dsInfo.unit;
                    var pieList = [],arr=[];
                    if(param.dsId!=DS_Distribution.id){
                        loadData.loadtableListPage(result);//加载表格
                    }
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
                }else{
                    loadData.loadtableListPage(result,paramRegion);//加载表格
                };
            };
            ajax.opts.errorFun = function(){
                console.info("当年数据加载失败.");
            };
            ajax.run();
        },
        //获取当年的所有下级区域数据
        loadBeyondCurrentData:function(paramRegion){
            var param = getParam();
            // if (param.level == 4 && Municipality.indexOf(param.parentId) >= 0) param.regionId = param.parentId;
            param.isQueryLargeArea = 1;
            var dsInfo = getDsInfo(param.dsCode);//获取当前数据集的数据
            var _url='';
            if(paramRegion===undefined) {
                if(param.dsId === DS_Distribution.id && param.level === '4') {
                    _url=DS_Distribution.FindPercent;
                }else if(param.dsId == DS_Growth.id){
                    _url= DS_Growth.GrowthInRegion;
                }else{
                    _url=dsInfo.Mom_URL;
                }
            }else{
                param.regionId = paramRegion.regionId;
                _url=dsInfo.Mom_URL;
            }
            var opts = {
                url: _url,
                data:param,
                type:'GET'
            };
            var ajax = new BaseAjax();
            ajax.opts = opts;
            ajax.opts.successFun = function(result){
                if(result.data!= null && !$.isEmptyObject(result.data)){
                    var _data = result.data;
                    //加载tif图层或色块()
                    if(dsInfo.Is_ColorBlock_Layer){
                        //根据时间轴上选择的时间点加载色块，如果没有选中时间点则默认第一个时间点
                        var firstData = param['dataTime']? loadData.getTableDataByDay(_data,param['dataTime']) : loadData.getTableDataByDay(_data,param['startDate']);
                        if (param.dsId == DS_Yield.id) {
                            for(var i=0;i<_data.length;i++){
                                var value = _data[i].value/10000
                                _data[i].value =  value.toFixed(2);
                            }
                            loadData.loadColorLayer(_data,dsInfo.Map_Legend);
                        }else {
                            if(param.dsId == DS_Weather.id){
                                $('input[type=radio]').change(function() {
                                    loadData.loadColorLayer(firstData,dsInfo.Weather_Legend);
                                });
                            }
                            loadData.loadColorLayer(firstData,dsInfo.Map_Legend);
                        }
                    }else{
                        loadData.loadDsLayer();
                        if (param.dsId == DS_Distribution.id || param.dsId == DS_Growth.id){
                            loadData.loadColorLayer(_data,null);
                        }
                    }
                }
            };
            ajax.opts.errorFun = function(){
                console.info("当年下级区域数据加载失败.");
            };
            ajax.run();
        },
        //获取三年的数据
        loadThreeYreasData:function(param){
            var param = getParam();
            var dsInfo = getDsInfo(param.dsCode);
            var selectAreaName = param.chinaName.indexOf("|")>=0?$.trim(param.chinaName.substring(param.chinaName.lastIndexOf('|') + 1, param.chinaName.length)):param.chinaName;
            if(param.dsId===DS_Trmm.id||param.dsId===DS_T.id||param.dsId===DS_Weather.id){//气象
                var myChart = echarts.init(document.getElementById('chartLi3'));
                var option = myChart.getOption();
                // option.color = [];
                // option.legend[0].data=[];
                // option.title[0].text='';
                // option.yAxis[0].name='';
                // option.xAxis[0].data=[];
                // option.series = [];
                var lastTwoYear = parseInt(param.dataTime.substring(0,4))-2;//前两年
                var lastYear = parseInt(param.dataTime.substring(0,4))-1;//前一年
                var currentYear = parseInt(param.dataTime.substring(0,4));//当年
                option.color = ['red','green','blue'];
                option.legend[0].data=[lastTwoYear.toString(),lastYear.toString(),currentYear.toString()];
                option.title[0].text=selectAreaName+dsInfo.threeTitle;
                option.yAxis[0].name='单位：'+dsInfo.unit;
                option.xAxis[0].data=getAll(param.startDate, param.endDate);
                // option.series = [];
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
                        myChart.setOption(option,true);
                        window.onresize = myChart.resize;
                    }else{
                        if(param.dsId===DS_Distribution.id){
                            loadData.loadtableListPage(result);
                        };
                        loadData.loadGraphYearsData(result);//降雨柱状图
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
            var param = getParam();
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
                var myChart = echarts.init(document.getElementById('chartLi4'));
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
                            if(i===6) arry.push(((result.data[j].highest)/10000).toFixed(2));
                            else if(i===5) arry.push(((result.data[j].higher)/10000).toFixed(2));
                            else if(i===4) arry.push(((result.data[j].high)/10000).toFixed(2));
                            else if(i===3) arry.push(((result.data[j].normal)/10000).toFixed(2));
                            else if(i===2) arry.push(((result.data[j].low)/10000).toFixed(2));
                            else if(i===1) arry.push(((result.data[j].lower)/10000).toFixed(2));
                            else arry.push(((result.data[j].lowest)/10000).toFixed(2));
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
                myChart.setOption(option,true);
                window.onresize = myChart.resize;
                myChart.off("click");
                myChart.on('click',function (param) {
                    loadData.loadCurrentData(result.data[param.dataIndex]);
                });
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
         * @version <1> 2018-10-15 lcw :Created.
         */
        loadDsLayer:function(){
            var param = getParam();
            var codeSplit = param.regionCode.split("-");
            if (codeSplit.length > 2) {
                param.regionCode = codeSplit[0] + "-" + codeSplit[1];
            }

            var ajax = new BaseAjax();
            ajax.opts.url =  DS_Layer.Layer_Query_URL;
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
         * @version <1> 2018-10-15 lcw :Created.
         */
        loadColorLayer:function(_data, map_legend){
            mapClazz.showColorLayer(_data, map_legend);
        }

    };

    /**
     * 双日历插件
     * @param select对象
     * @version<1> 2018-9-27 limeiling:created
     */
    var loadDateRangePicker = function(){
        // dateRangePicker.selDaterangepicker($('#reservation'));
        stay.init();
    };


    /**
     * 获取简报和报告
     * @type {{loadReportTableList: loadReportTableList, previewPDF: previewPDF, findBriefReportByID: findBriefReportByID}}
     */
    var reportAndBrief  = {
        /**
         * 分页加载报告/简报数据
         */
        loadReportTableList : function(pageNum,pageSize){
            var param = getParam(); //获取参数
            var dsId = param.dsId;
            //---查询报告和简报不需要以下参数------
            param.dsId = param.dsCode = param.cropId = param.cropCode = param.cropName = param.accuracyId = null;
            if(pageNum instanceof Object){
                pageNum = 1;
            }
            if(pageSize instanceof Object){
                pageSize = 1;
            }
            param["pageNum"] = pageNum == undefined ? 1 : pageNum;//第1页
            param["pageSize"] = pageSize == undefined ? 15 : pageSize;//默认每页加载5条数据
            var url = ''

            var reportUl =document.getElementById('report_list');//页面右侧报告/简报结果列表区域
            reportUl.innerHTML='';
            $(".pagination").hide();//分页区域

            if ( dsId == DS_Report.id){
                url = DS_Report.Report_list_url;//报告列表url
                $('#tle').html('最新报告');
                $('#map_report').attr('src', 'js/lib/pdfjs/1.pdf');//默认加载暂无报告提示
            } else if (dsId == DS_Brief.id){
                url = DS_Brief.Brief_list_url;//简报列表url
                $('#tle').html('最新简报');
                $('#map_report').attr('src', 'briefReport.html');//默认加载暂无简报提示
            }

            var permissionAjax = new BaseAjax();
            permissionAjax.opts.url = url;
            permissionAjax.opts.contentType = "application/json";
            permissionAjax.opts.async = false;  //同步请求
            permissionAjax.opts.data = param;
            permissionAjax.opts.type = "GET";
            permissionAjax.opts.successFun = function(data){
                var temps = data.list;
                if(temps != null && temps.length > 0){
                    $(".pagination").show();//查询出来数据则取消隐藏分页区域
                    var li =  "";
                    $('#divReportList').show();
                    $('#divReport').hide();
                    var reportName;
                    var reportId;
                    $.each(temps, function (index, obj) {
                        li = document.createElement("li");
                        if (dsId == DS_Report.id){
                            reportName = obj.reportName;
                            reportId = obj.reportId;
                            li.setAttribute("class","report_li");
                        } else if (dsId == DS_Brief.id){
                            reportName = obj.report_name;
                            reportId = obj.report_id;
                            li.setAttribute("class","brief_li");
                        }
                        li.innerHTML=reportName;
                        li.title = reportName;
                        li.setAttribute("reportId",reportId);
                        li.setAttribute("reportName",reportName);
                        li.onclick=function(){
                            var userInfo = UserModule.UserUtil.getUserInfo();
                            if(userInfo) {
                                if (dsId == DS_Report.id){
                                    reportAndBrief.previewPDF($(this).attr('reportName'),$(this).attr('reportId'));//预览报告pdf
                                } else if (dsId == DS_Brief.id){
                                    reportAndBrief.findBriefReportByID($(this).attr('reportId'),$(this).attr('reportName'));//加载简报详情
                                }
                            }
                            else{
                                location.href="login.html";
                            }
                        };
                        reportUl.appendChild(li);
                    });
                    reportUl.appendChild(li);//将查询到的报告/简报结果集追加到页面右侧列表
                    $('#report_list').children('li').get(0).click();//默认加载第1个报告/简报
                    $('#report_list').children(":first").addClass("active");//将第1个报告/简报在列表中显示为高亮

                    //分页插件
                    $(".pagination").show();
                    $(".pagination").pageM({
                        "pageNum" : data.pageNum,
                        "total" : data.total,
                        "pageSize" : 15,
                        "pageEllipsis":0,
                        "pagePosition" : "CENTER",
                        "isSearch" : false,//是否有搜索项
                        "pageEvent" : function(option){
                            reportAndBrief.loadReportTableList(option.pageNum, option.pageSize);
                        }
                    });

                    //报告/简报中任一条数据点击后加上高亮效果
                    $("#report_list").on("click","li",function(){
                        $('#report_list li').removeClass("active")
                        $(this).addClass("active");
                    });
                }
                else {
                    $(".pagination").hide();
                }
            };
            permissionAjax.opts.errorFun = function(){
                console.log("专题报告加载异常")
            };
            permissionAjax.run();

        },
        /**
         * Description: 预览PDF报告
         * @param obj
         * @version <1> 2018-07-08 13:21 cxw: Created.
         */
        previewPDF : function(reportName,reportId){
            var key = COOKIE_CONFIG.cookieName;
            var accessToken = JHModule.CookieUtil.getCookie(key);
            var download_url = DS_Report.REPORT_PREVIEW_URL +  "?reportId="+ encodeURIComponent(reportId)  +"&AccessToken="+accessToken;
            var url = "js/lib/pdfjs/web/viewer.html?" + download_url;
            $('#map_report').attr('src', url);
        },
        //根据id 查询简报
        findBriefReportByID : function (id, briefName) {
            var srcStr = "briefReportShow.html?file=" + DS_Brief.Brief_info_url + "?reportId=" +
                id + "&title=" + briefName + "&reportId=" + id;
            $('#map_report').attr('src', srcStr);
        }
    }

    /**
     * 天气
     * @param
     * @version<1> 2018-11-02 limeiling@created
     */
    var weatherFun = function(){
        var param = getParam();
        var opts = {
            url: Weather_Prediction.RegionWeather_URL,
            data:param,
            type:'GET'
        };

        var ajax = new BaseAjax();
        ajax.opts = opts;
        ajax.opts.successFun = function(result){
            if(result.data.length>0){
                $('.weatherIcon').attr("style"," background: url("+weatherImgUrl(result.data[0].weather)+") 5px 1px no-repeat;background-size: 26px 26px");
                $('.weatherIcon').html(result.data[0].minValue+'~'+result.data[0].maxValue+'℃');
                for(var i=0;i<3;i++){
                    if(result.data[i]===undefined){
                        var imgUrl = '-';
                        var weather = '-';
                        var value = '-';
                    }else{
                        var imgUrl = '<img class="weather_img" src="'+weatherImgUrl(result.data[i].weather)+'">';
                        var weather = result.data[i].weather;
                        var value = result.data[i].minValue+'~'+result.data[i].maxValue+'℃';
                    }
                    var day = i===1?'明天':(i===0?'今天':'后天');
                    var date = moment().add(i,'days').format('YYYY-MM-DD');

                    $('.weatherInfo').append('<li>'+
                        '<p><span>'+day+'</span></p>'+
                        '<p><span>'+date+'</span></p>'+
                        '<p><span>'+imgUrl+'</span></p>'+
                        '<p><span>'+weather+'</span></p>'+
                        '<p><span>'+value+'</span></p>'+
                        '</li>');
                }
            }else{
                $('.weatherBox').hide();
            };

        };
        ajax.opts.errorFun = function(){
            console.info("天气数据加载失败.");
        };
        ajax.run();

        /**点击隐藏 */
        $('.weatherIcon').hover(function(){
            $('.weatherInfo').fadeToggle();
        });
    }

    //根据天气判断使用何种天气图标
    var weatherImgUrl = function (weather) {
        var imgUrl = '';
        if (!weather) return '';
        for (var i=0;i<weatherNameUrl.length;i++){
            var w = weatherNameUrl[i];
            //如果天气中不包含’转‘
            if (weather.indexOf('转') < 0){
                if(weather == w.name) return w.url;
            } else {
                var arr = weather.split('转');
                var lastWeather = arr[arr.length-1];
                if (w['name'].indexOf(lastWeather) >= 0){
                    // console.log(w['name'].indexOf(lastWeather));
                    return w.url;
                }
            }
        }
    }

    var mapClazz ;
    /**
     * 初始加载方法类
     * @version<1> 2018-09-27 lcw :Created.
     */
    var init = function(){
        initUserDefaultProduct();
        mapClazz = new mapClazz();  //实例化底图操作类

        commons.isLoginFun();//判断是否登录
        // $('canvas').css('height','280px!important');
        loadSideFixed();//加载固定侧边栏点击切换和右侧侧边栏伸缩
        loadDownBox();//加载默认信息：1、默认区域数据加载；2、加载默认的作物；3、加载默认时间；4、初始化地图,并加载矢量边界
        loadAdminInfo();//加载用户信息
        loadDateRangePicker();//加载双日历插件

        //给区域下拉框绑定点击事件
        $('#txtRegion').bind('click',function () {
            dataUlSet.changeRegionEvent();
        })

        //给作物下拉框绑定变更事件
        $('#plants').change(function () {
            var param = getParam();
            if(param.dsId === DS_Distribution.id||param.dsId === DS_Growth.id||param.dsId === DS_Yield.id){//分布，长势，估产
                dataUlSet.findDsDataTime(); //查询时间点，加载时间轴
            }
        });

        //天气
        // weatherFun();

        $(document).click(function(){
            UserModule.UserUtil.updateCookie();
        });
    };
    init();
});