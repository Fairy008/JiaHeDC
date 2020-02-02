define(['jquery','MapModule','ol','BaseAjax','custom_settings'],function($,MapModule,ol,BaseAjax,custom_settings){


    function mapClazz(){

        /**
         *图层层级显示
         * @version<1> 2019-03-19 cxw :Created.
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

        this.provinceLayer=  "JiaHeDC:ly_WORLD_GF";

        var _this = this;
        var highStrokeColor = '#6495ED';//鼠标移入区域后高亮显示的边框颜色

        //类成员变量
        var mapTool,countryLayer,provinceLayer,cityLayer,districtLayer,colorLayer,tifLayer;
        var vectorLayers = [];
        var highStrokeColor = '#6495ED';//鼠标移入区域后高亮显示的边框颜色
        var gfLayer; //高清底图
        var gfNextLayer; //下一级区域图层
        this.loadSelectLayerFun = function () {
            //增加google map 作为底图
            var defaults = {
                url: 'http://www.google.cn/maps/vt/pb=!1m4!1m3!1i{z}!2i{x}!3i{y}!2m3!1e0!2sm!3i380072576!3m8!2szh-CN!3scn!5e1105!12m4!1e68!2m2!1sset!2sRoadmap!4e0!5m1!1e0',
                mapMinZoom: 1,
                mapMaxZoom: 14,
                tilePixelRatio: 1,
                zIndex: _this.layerIndex.bgLayer
            };
            bgLayer = mapTool.addXYZTileLayer(defaults);
            mapTool.addLayer(bgLayer);
            return bgLayer;
        }

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
         * @param _level : 当前区域的级别 ， 省2 ，市3，区4
         * @version <1> 2017-11-07 Hayden:Created.
         */
        this.initMap = function(_regionId){
            /*  param = getParam()
              reginId = param.regionId;*/
            if(mapTool===undefined){
                var url = FORUM__Layer.Layer_Init_URL;
                mapTool = new MapModule.MapTool("divMap",{"url":url,"zoom":8,"maxZoom":14,"minZoom":3,"logoSrc":"","logoHref":""});

                mapTool.createMap();
                //加载世界地图
                _this.loadHighDefinitionMap(); //高清地图
                bgLayer = _this.loadSelectLayerFun();
                _this.loadVector(_regionId)
//                    _this.loadPointDiglog();//鼠标移动事件，显示提示框
            }

        };
        this.resize = function(regionId, regionCode, level){
          mapTool.resize();
        }


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

            var opts = {
                url:USER_CONFIG.REGION_FAMILY_URL,
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
                    var _regionId = data.regionId
                    var centroid = data.centroid


                    if(!!districtLayer){
                        mapTool.removeLayer(districtLayer);
                    }

                    if(!!cityLayer){
                        mapTool.removeLayer(cityLayer);
                    }

                    if(!!provinceLayer){
                        mapTool.removeLayer(provinceLayer);
                    }

                    if(!!countryLayer){
                        mapTool.removeLayer(countryLayer)
                    }

                    // console.log((_regionId+"").substring(0,(_regionId+"").length-8) + "00000000")
                    _this.addVectoryInCountry((_regionId+"").substring(0,(_regionId+"").length-8) + "00000000"); //加载国家级各省的矢量边界

                    if(_level >= 2){
                        //非直辖市才加载省级，避免直辖市省级和市级边界和名字与市级重叠
                        if (Municipality.indexOf(_provinceId) < 0){
                            _this.addVectorInProvince(_regionCode) //加载省级矢量边界，默认市全国

                        }


                        _this.addCityVector(_provinceId, 3); //加载选中省份的市州级矢量边界
                        if(_level >=3){
                            _this.addDistrictVector(_cityId, 4); //加载选中省份的区县矢量边界
                        }
                        //当前选中的区域为直辖市
                        if(_level ==2 && Municipality.indexOf(_provinceId) > 0){
                            _this.addDistrictVector(data.capitalId, 4); //加载选中省份的区县矢量边界
                        }
                    }

                    var wkt_parser = new ol.format.WKT();
                    var geom = wkt_parser.readGeometry(centroid);
                    if (_level < 2){
                        mapTool.moveToCenter(geom); //移动至中心点，并加载底图层级
                    } else {


                        console.log(_regionCode , _level)
                        _this.moveToCenter(_regionCode,_level) //移动至当前选中区域
                    }
                    _this.resize();

                }
            };
            ajax.opts.errorFun = function(){
                console.info("加载区域家族关系失败");
            };
            ajax.run();

        }
        /**
         *wmslayer：国家级的各省
         *@param _parentId : 国家及区域ID
         *@version<1> 2018-11-23 lcw :Created.
         */
        this.addVectoryInCountry = function(_parentId){
            var opts = {zIndex:_this.layerIndex.countryLayer};

            countryLayer = mapTool.addTileWMSLayer(this.provinceLayer, {'filter':"parent_id='"+ _parentId +"'",'zIndex':_this.layerIndex.countryLayer})

        }

        /**
         * 在各省份上覆盖图层，只显示省级geometry的名称，当前选中区域除外
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
            if(_level == 1){
                _layer = countryLayer;

                mapTool.moveToLayerFeature(countryLayer,"region_code",_regionCode,true);

            }else if(_level == 2 ){
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
        this.showLegend = function(map_legend,dsId,cropName){
            var mapLegendArry = [];
            for(var i=0;i<map_legend.length;i++){

                if(map_legend[i][0]==cropName){
                    mapLegendArry.push(map_legend[i]);
                }
            }
            var legendTitle = document.getElementById('legendTitle');
            // var param = getParam();
            // var dsId = param.dsId;
            if (dsId == DS_Distribution.id){//分布
                   mapTool.repaintLegend(mapLegendArry);
            } else {
                mapTool.repaintLegend(map_legend);
            }
            // if ($('.toggleBtn').hasClass('up')){
            //     $('.ol-control').css('bottom','88px')
            // }else {
            //     $('.ol-control').css('bottom','339px')
            // }

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

        //移除瞄点图层
        this.removeCollectionLayers = function(layer){
            if(!!vectorLayers){
                for(var i = 0 ;i< vectorLayers.length ;i++){
                    mapTool.removeLayer(vectorLayers[i]);
                }
                vectorLayers = [];
            }

        }

        /**
         * 显示色块
         */
        this.showColorLayer = function(_data, map_legend,param){
            mapTool.removeHighFeature(districtLayer,'#6495ED');//加载色块前清除区域选中效果
            // var param = getParam();
            var regionId = param.regionId;
            var parentId = param.parentId;
            var dsId = param.dsId;
            var level = param.level;
            var thisColorLayer = this.layerIndex.colorLayer;
            if (dsId == DS_Growth.id) {
                var levelData = _this.buildGrowthLayerData(_data); //构建长势色块
            }else {
                var levelData = _this.buildCurrentLayerData(_data, map_legend, param); //构建色块
            }

            //遍历图例，构造feature样式
            var lvStyle = MapModule.MapTool.defineStyle({fillColor:"rgba(255,255,255, 0)",strokeColor:"#20B2AA"});
            for(var x in map_legend){
                lvStyle[map_legend[x][0]] = MapModule.MapTool.setLayerStyle({fillColor:map_legend[x][1]});
            }
            if (level > 2   ||  ( Municipality.indexOf(regionId) >= 0 || Municipality.indexOf(parentId) >= 0)){//如果是直辖市，那么在填充色块的时候应该调用区县一级的图层districtLayer
                if (districtLayer.getSource() && districtLayer.getSource().getFeatures() && districtLayer.getSource().getFeatures().length > 0){
                    colorLayer = mapTool.addColorLayer(districtLayer,lvStyle,levelData,thisColorLayer,param);
                } else {
                    setTimeout(function () {
                        colorLayer = mapTool.addColorLayer(districtLayer,lvStyle,levelData,thisColorLayer,param);
                    }, 1500);
                }
            } else { //省级使用市级layer
                colorLayer = mapTool.addColorLayer(cityLayer,lvStyle,levelData,this.layerIndex.colorLayer,param);
            }
        };
        this.moveFeaturesByItemId =function (itemId){
            var selectFeatures = [];
            for(var i=0;i<vectorLayers.length;i++){
                var source = vectorLayers[i].getSource();
                if(source instanceof ol.source.Vector){
                    var features = source.getFeatures();
                    if(features.length>0){
                        for(var j=0;j<features.length;j++){
                            if(features[j].get("itemId") == itemId && features[j].get("type") == 1){
                                selectFeatures = features[j];
                                break;
                            }
                        }
                    }
                }
            }
            var coordinate = selectFeatures.getGeometry().getCoordinates()
            mapTool.map.getView().fit([coordinate[0],coordinate[1],coordinate[0],coordinate[1]],11);
            mapTool.map.getView().setZoom(11)
        }
        /**
         * 根据数据构建色块
         */
        this.buildCurrentLayerData = function(_data,map_legend, param){
            var levelData = {};
            //将指定日期的数据解析为分析数据，格式为键值对 : { 区域ID，图例数据 }
            for (var i = 0;i< _data.length;i++) {
                // console.log($('input:radio:checked').val());
                if(param.dsId===DS_Weather.id){
                    if($('input:radio:checked').val()==='最高温'){
                        var value = _data[i].maxValue;
                    }else{
                        var value = _data[i].minValue;
                    }
                }else{
                    var value = _data[i].value;
                }
                var percent = _data[i].percent;
                var percentAge = _data[i].percentAge?_data[i].percentAge:_data[i].percentage;
                if (!value) continue;
                var tempObj={'value':value,'percent':percent,'percentAge':Number(percentAge)};
                if(map_legend){
                    for(var j=0;j<map_legend.length;j++){
                        var legend = map_legend[j];
                        var legend_value = legend[0].match(/-?\d+(.\d+)?/)[0];
                        if (Number(value) >= Number(legend_value)) {
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

        //根据经纬度在地图上瞄点
        this.addPointMarker = function (data,taskName,cropName) {
            var container = document.getElementById('popup');
            var content = document.getElementById('popup-content');
            var vectorSource;
            // var vectorLayers = [];
            var preFeature
            var features = [];
            for (var i = 0; i < data.length; i++) {
                //面图层
                var gonvectorLayer;
                //点图层
                var vectorLayer;
                var obj = data[i];
                //大于3个点则画面，小于3个点画点
                var polygonArr;
                var positionStr = obj.position;
                var positionArr = positionStr.replace(/\[/g, "").replace(/\]/g, "").replace(/\(/g, "").replace(/\)/g, "")
                positionArr = positionArr.split("lat/lng:");
                if (positionArr && positionArr.length > 0) {
                    //第一个会有空格，去掉第一个
                    positionArr.shift();
                }
                if (positionArr.length >= 3) {
                    var innerPoint = [];
                    for (var p in positionArr) {
                        var positionPoint = positionArr[p];
                        if (positionPoint) {
                            var pArr = positionPoint.split(",");
                            if (pArr[1] && pArr[0]) {
                                innerPoint.push([pArr[1], pArr[0]]);
                            }
                        }
                    }
                    //后台点需要收尾连接
                    var positionPoint = positionArr[0];
                    if (positionPoint) {
                        var pArr = positionPoint.split(",");
                        if (pArr[1] && pArr[0]) {
                            innerPoint.push([pArr[1], pArr[0]]);
                        }
                    }
                    polygonArr = [innerPoint];
                    if (innerPoint && innerPoint.length >= 4) {
                        //面
                        var gonFeature = new ol.Feature({
                            geometry: new ol.geom.Polygon(polygonArr),
                            taskItemId: obj.itemId,
                        });
                        var gonvectorSource = new ol.source.Vector({
                            features: [gonFeature]
                        });
                        var gonStyle = [
                            new ol.style.Style({
                                fill: new ol.style.Fill({color: 'rgba(230,146,0, 0.6)'}),//文字填充颜色
                                stroke: new ol.style.Stroke({color: "red", width: 1}),//文字线颜色
                            })
                        ];
                        gonvectorLayer = new ol.layer.Vector({
                            style: gonStyle,
                            source: gonvectorSource
                        });
                        vectorLayers.push(gonvectorLayer);
                        gonvectorLayer.setZIndex(9999);//顶层
                        mapTool.map.addLayer(gonvectorLayer);
                        var center = ol.extent.getCenter(gonFeature.getGeometry().getExtent());
                        var iconFeature = new ol.Feature({
                            geometry: new ol.geom.Point(center),
                            name: obj.surveyAddress,
                            population: 4000,
                            rainfall: 500,
                            type: '1',
                            taskItemId: obj.itemId,
                            jwd: obj.position,
                            dataTime: obj.surveyTime,
                            taskName: taskName,
                            collectionAddress: obj.surveyAddress,
                            cropName: cropName
                        });
                        iconFeature.setProperties(obj, true);
                        iconFeature.set('style', _this.createStyle('images/public/forum/icon-location.png', undefined));
                        features.push(iconFeature);
                        var vectorSource = new ol.source.Vector({
                            features: [iconFeature]
                        });
                        vectorLayer = new ol.layer.Vector({
                            style: function (feature) {
                                return feature.get('style');
                            },
                            source: vectorSource
                        });
                        vectorLayers.push(vectorLayer);
                        vectorLayer.setZIndex(9999);//顶层
                        mapTool.map.addLayer(vectorLayer);
                    }
                } else {
                    var id = obj.itemId;
                    var start = obj.position.indexOf("(");
                    var end = obj.position.indexOf(")");
                    var coordinate = obj.position.substring(start + 1, end);
                    var iconFeature = new ol.Feature({
                        geometry: new ol.geom.Point([coordinate.split(",")[1], coordinate.split(",")[0]]),
                        name: obj.surveyAddress,
                        population: 4000,
                        rainfall: 500,
                        type: '1',
                        taskItemId: id,
                        jwd: obj.position,
                        dataTime: obj.surveyTime,
                        taskName: taskName,
                        collectionAddress: obj.surveyAddress,
                        cropName: cropName
                    });
                    iconFeature.setProperties(obj, true);
                    if (i == 0) {
                        preFeature = iconFeature;
                        iconFeature.set('style', _this.createStyle('images/public/forum/icon-location.png', undefined));
                    } else {
                        iconFeature.set('style', _this.createStyle('images/public/forum/icon-location.png', undefined));
                    }

                    features.push(iconFeature);
                    var vectorSource = new ol.source.Vector({
                        features: [iconFeature]
                    });
                    vectorLayer = new ol.layer.Vector({
                        style: function (feature) {
                            return feature.get('style');
                        },
                        source: vectorSource
                    });
                    vectorLayers.push(vectorLayer);
                    vectorLayer.setZIndex(9999);//顶层
                    mapTool.map.addLayer(vectorLayer);
                }
            }
            var defultTaskItem = data[0];
            //默认展示区域内的点
            // _this.loadDefaultPoinInfo(defultTaskItem);
            popup  = new ol.Overlay(({
                //元素内容
                element: container,
                autoPan: true,
                ////覆盖层如何与位置坐标匹配
                positioning: 'bottom-center',
                //事件传播到地图视点的时候是否应该停止
                stopEvent: true,
                autoPanAnimation: {
                    //动画持续时间
                    duration:0
                }
            }));
            mapTool.map.addOverlay(popup);
            mapTool.map.on('click', function (evt) {

                //popup.setPosition(undefined);//关闭弹窗
                var feature = mapTool.map.forEachFeatureAtPixel(evt.pixel,
                    function(feature) {
                        return feature;
                    });
                if(feature && feature.get('type') !='1'){
                    // $(element).popover('destroy');
                }else{
                    if (feature) {
                        if(preFeature){
                            preFeature.set('style', _this.createStyle('images/public/forum/icon-location.png', undefined));
                        }
                        preFeature = feature
                        feature.set('style', _this.createStyle('images/public/forum/icon-dot2.png', undefined));
                        var property=feature.getProperties();
                        var coordinates = feature.getGeometry().getCoordinates();
                        $(content).html($("#popBox").html());
                        popup.setPosition(coordinates);
                        //关闭弹窗
                        $("#popup-closer").on("click",function(){
                            popup.setPosition(undefined);
                            feature.set('style', _this.createStyle('images/public/forum/icon-location.png', undefined));
                        });
                        var itemId = feature.get('taskItemId');
                        var collectionDate = feature.get('dataTime');
                        var collectionAddress =feature.get('surveyAddress');
                        var position =feature.get('jwd');
                        var taskName =feature.get('taskName');
                        var cropName = feature.get('cropName');
                        if(custom_settings.isNewTask == 1) {
                            _this.loadTaskItemStatistics_new(itemId, collectionDate, collectionAddress, position, taskName, cropName);
                        }else{
                            _this.loadTaskItemStatistics(itemId, collectionDate, collectionAddress, position, taskName, cropName);
                        }
                    } else {
                        //  $(element).popover('destroy');
                    }
                }
            });
            mapTool.map.on('pointermove',function(evt){
                var feature = mapTool.map.forEachFeatureAtPixel(evt.pixel,function (feature) {return feature;});
                if(feature && feature.get('type') == '1'){
                    mapTool.map.getTargetElement().style.cursor = 'pointer';
                }
                else{
                    mapTool.map.getTargetElement().style.cursor = '';
                }
            });
        }

        //加载子任务图片
        this.loadTaskItemImage = function(taskItemId){
            var ajax = new BaseAjax();
            ajax.opts.url = COLLECTION_TASK.getTaskItemeById_url + "?id=" + taskItemId;
            ajax.opts.async = false;
            ajax.opts.contentType = "application/json";
            ajax.opts.successFun = function (result) {
                if (result.flag && result.data) {
                    var pngMediaList = result.data.pngMediaList;
                    if(pngMediaList && pngMediaList.length > 0){
                        //先清空
                        $("#List1").html("");
                        $.each(pngMediaList,function(i,e){
                            var pngMediaPath = COLLECTION_TASK.nolog_downloadMeidaById_url+ e.id;

                            var itemStr = '<div class="pic"><a href="'+pngMediaPath+'" target="_blank"><img src="'+pngMediaPath+'" width="90" height="70" /></a></div>'
                            $("#List1").append(itemStr);
                        })
                    }
                }else{
                    console.info("获取子任务信息失败")
                }
            };
            ajax.run();

        };

        this.loadTaskItemStatistics = function(taskItemId,collectionDate,collectionAddress,postion,taskName,cropName){
            if(!taskItemId){
                return;
            }
            var $this = $(this);
            var ajax = new BaseAjax();
            ajax.opts.url = COLLECTION_TASK.queryDistributionHistory_url+"?taskItemId="+taskItemId;
            ajax.opts.async = false;
            ajax.opts.type = "get";
            ajax.opts.successFun = function (result) {
                if(result.flag && result.data){
                    console.info(JSON.stringify(result.data));
                    var maxValue = result.data.maxArea;;
                    var xData =result.data.collectionDateList;
                    var dataList = result.data.areaList;
                    var collectionData = result.data.currentYearCollectionData;
                    var $collectionList = $("#collectionData");
                    var tbodyStr = "";
                    //清空数据
                    $("#collectionData li:gt(4)").remove();
                    if(collectionData){
                        $.each(collectionData,function(i,e){
                            var collectionFeildCh = e.fieldNameCh;
                            var collectionFeildEn = e.fieldNameEn;
                            var collectionFeildChTitle = e.fieldNameCh;
                            var collectionValue = e.collectionValue;
                            if(collectionFeildCh && collectionFeildCh.length>6){
                                collectionFeildCh = collectionFeildCh.substring(0,6)+"...";
                            }
                            tbodyStr +="<li>";
                            tbodyStr +="<span class='collection_left' title='"+collectionFeildChTitle+"'>"+collectionFeildCh+"</span>";
                            tbodyStr +="<span class='collection_right'>"+collectionValue+"</span>";
                            tbodyStr +="</li>";
                        })

                        $("#collectionTask").text(taskName);
                        $("#collectionDate").text(new Date(collectionDate).Format("yyyy-MM-dd"));
                        $("#collectionCrop").text(cropName);
                        $("#collectionPosition").text(postion);
                        $("#collectionAddress").text(collectionAddress);
                        $collectionList.append(tbodyStr);
                    }
                    //加载子任务图片
                    _this.loadTaskItemImage(taskItemId);
                }
            };
            ajax.run();
        }

        this.loadTaskItemStatistics_new = function(taskItemId,collectionDate,collectionAddress,postion,taskName,cropName){
            if(!taskItemId){
                return;
            }
            var itemInfo={};
            var ajax = new BaseAjax();
            ajax.opts.url = COLLECTION_TASK_NEW.nolog_findTaskItemByItemId_url;
            ajax.opts.data = JSON.stringify({itemId:taskItemId})
            ajax.opts.contentType = "application/json";
            ajax.opts.async = false;
            ajax.opts.type="POST";
            ajax.opts.successFun = function(result){
                if(result.flag){
                    itemInfo=result.data;
                }
            }
            ajax.run();
            var $this = $(this);
            $("#cllecdtionTask").text(taskName);
            $("#collectionDate").text(new Date(collectionDate).Format("yyyy-MM-dd"));
            $("#collectionCrop").text(cropName);
            $("#collectionPosition").text(postion);
            $("#collectionAddress").attr("title",collectionAddress);
            collectionAddress = collectionAddress.length>20 ? collectionAddress.substring(0,20)+"...":collectionAddress;
            $("#collectionAddress").text(collectionAddress);
            if(itemInfo){
                var templateVO= itemInfo.templateVO;
                var fieldModelVOList = templateVO.fieldModelVOList;
                var collectionData = fieldModelVOList;
                var $collectionList = $("#collectionData");
                var tbodyStr = "";
                //清空数据
                $("#collectionData li:gt(3)").remove();
                if(collectionData){
                    $.each(collectionData,function(i,e){
                        var collectionFeildChTitle = e.fieldNameCh;
                        var collectionFeildCh = e.fieldNameCh;
                        var collectionFeildEn = e.fieldNameEn;
                        var collectionValue = e.value;
                        if(collectionFeildCh && collectionFeildCh.length>5){
                            collectionFeildCh = collectionFeildCh.substring(0,5)+"...";
                        }
                        tbodyStr +="<li>";
                        tbodyStr +="<span class='collection_left' title='"+collectionFeildChTitle+"'>"+collectionFeildCh+"</span>";
                        tbodyStr +="<span class='collection_right'>"+collectionValue+"</span>";
                        tbodyStr +="</li>";
                    })
                    $collectionList.append(tbodyStr);
                }
                //加载子任务图片
                _this.loadTaskItemImage_new(taskItemId);
            }

        },

        //加载子任务图片
        this.loadTaskItemImage_new = function(taskItemId){
            var itemInfo={};
            var ajax = new BaseAjax();
            ajax.opts.url = COLLECTION_TASK_NEW.nolog_findTaskItemByItemId_url;
            ajax.opts.data = JSON.stringify({itemId:taskItemId})
            ajax.opts.contentType = "application/json";
            ajax.opts.async = false;
            ajax.opts.type="POST";
            ajax.opts.successFun = function(result){
                if(result.flag){
                    itemInfo=result.data;
                }
            }
            ajax.run();
            if (itemInfo) {
                //回显图片和录音
                var pngMediaList = [];
                var cltMediaSourceList = itemInfo.cltMediaSourceList;
                $.each(cltMediaSourceList,function(i,obj){
                    if(obj.mediaType == 1){
                        pngMediaList.push(obj);
                    }
                })
                //先清空
                $("#List1").html("");
                if(pngMediaList && pngMediaList.length > 0){
                    $.each(pngMediaList,function(i,e){
                        var pngMediaPath = download_config.DOWNLOD_URL+e.mediaPath;
                        var itemStr = '<div class="pic"><img src="'+pngMediaPath+'" onclick="imgShow(\'#outerdiv\', \'#innerdiv\', \'#bigimg\', $(this))" width="90" height="70" /></div>'
                        $("#List1").append(itemStr);
                    })
                    $(".rollBox").show();
                    // $("#collectionData").css("min-height","250px");
                }else{
                    $(".rollBox").hide();
                    // $("#collectionData").css("min-height","340px");
                }
            };
        },
        this.createStyle = function(src, img){
            return new ol.style.Style({
                image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
                    anchor: [0.5, 0.96],
                    src: src,
                    img: img,
                    imgSize: img ? [img.width, img.height] : undefined
                }))
            });
        }

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
        var params = {};

        this.setParams = function(param){
            params = param;
        }
        /**
         *鼠标移动事件 显示提示框
         *@version <1> 2017-11-27 XuZhenguo:
         */
        this.loadPointDiglog = function(){
            this.pointPosition()
            mapTool.map.on('pointermove',function(event) {
                //调研数据不显示
                if($("#tab_dy").hasClass("active")){
                    return;
                }
                var highStrokeColor = '#6495ED';//鼠标移入区域后高亮显示的边框颜色
                var feature = null;
                var colorFeature = mapTool.getFeatureByClick(event.pixel, colorLayer);//色块图层
                var districtFeature = mapTool.getFeatureByClick(event.pixel, districtLayer);//区县图层
                var cityFeature = mapTool.getFeatureByClick(event.pixel, cityLayer);//城市图层
                //加此段是为了解决色块图层覆盖和区域图层之间覆盖导致数据加载不出来的问题
                if(params.dsId == DS_Distribution.id || params.dsId == DS_Growth.id){
                    if(districtFeature){
                        feature = districtFeature;
                    }else if(cityFeature){
                        feature = cityFeature;
                    }
                }else{
                    feature = colorFeature;
                }
                if (feature && feature.get('levelData')) {
                    var dsId = params.dsId;//当前选中的数据集的id
                    var dsCode = params.dsCode;//当前选中的数据集英文代码
                    var dsName = params.dsName;//当前选中的数据集中文名
                    var unit = getDsInfo(dsCode).unit;//当前选中的数据集的度量单位
                    var levelData = feature.get('levelData');
                    if (!!feature) {
                        var highStyle = MapModule.MapTool.defineStyle({fillColor:"rgba(255,255,255, 0)",strokeColor:highStrokeColor,strokeWidth:4});
                        var chinaName = feature.get('china_name');
                        var local_name = feature.get('local_name');
                        var text = " " + chinaName +" ";
                        mapTool.highFeature(feature, highStyle);
                        if (local_name)
                            local_name += "&nbsp;&nbsp;&nbsp;";
                        else
                            local_name = "";
                        var content = '';
                        if (dsId == DS_Distribution.id) {
                            var val = levelData.value / 10000
                            content += local_name + chinaName + "<br/> 分布面积：" + val.toFixed(2) + " (" + unit + ")<br/>";
                            content += "占比：" + levelData.percent.toFixed(2) + "%"


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
                    if(districtLayer){
                        mapTool.removeHighFeature(districtLayer,highStrokeColor);
                    }
                    if(colorLayer) {
                        mapTool.removeHighFeature(cityLayer, highStrokeColor);
                    }
                    //移除色块图层上的高亮选中效果
                    if(colorLayer){
                        mapTool.removeHighFeature(colorLayer,highStrokeColor);
                    }
                }

            });
        };
        /**
         * 为鼠标移动添加mouseover事件
         * 解决鼠标在图层上移动时，突然无法获取canvas时导致提示层无法关闭的问题
         * @version<1> 2018-11-22 lcw :created.
         */
        this.pointPosition = function(){
            $(document).on("mouseover",function(e){
                var _html = $(e.target).prop("outerHTML")
                if(_html.indexOf("ol-unselectable") == -1){
                    mapTool.infoDlgClose();
                    mapTool.removeHighFeature(districtLayer,highStrokeColor);
                }
            })

        }

    };

    return {
        mapClazz : mapClazz
    }
})