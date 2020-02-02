/**
 * MapTool对象用来操作地图，操作如下：
 *	1. 构造地图对象
 *	2. 叠加世界地图矢量
 *	3. 叠加区域高清影像
 *	4. 根据区域中心点，将地图中心点移至区域中心点
 *	5. 叠加区域作物分布、降雨、地温、长势、估产图层
 *	6. 为分布、降雨、地温、长势、估产增加图例显示
 *
 * @version <1> 2017-11-07 Hayden:Created.
 */
define(["jquery","ol","BaseAjax"],function($,ol,BaseAjax){
    function MapTool(targetId,opts){
        var _this = this;
        this.map = undefined;//内部map对象
        var __drawLayer;//用于绘制形状的图层

        // var __layerDict ={};//用于保存地图上所有的图层，暂还没有用到。

        /********************************************************************************************************
         *
         * 								在地图上初始化参数、创建地图对象 、事件
         *
         ********************************************************************************************************/

        /**
         * 初始化地图对象，具体如下：
         * 1. 设置地图的初始化参数：targetId,url,version,srs,units等
         * 2. 创建地图对象
         *
         * @version <1> 2017-11-07 Hayden:Created.
         */
        var __init = function(){
            var flag = true;
            if(!targetId){
                alert("请正确设置地图元素ID.");
                flag = false;
            }else{
                _this.targetId = targetId;
            }

            if(!opts) _this.opts = {};
            else _this.opts = opts;

            //设置地图控件默认值。
            if(!_this.opts.url) _this.opts.url = window.project_path +  "jh-map/layer";
            if(!_this.opts.version) _this.opts.version = "1.1.1";
            if(!_this.opts.srs) _this.opts.srs = "EPSG:4326";
            if(!_this.opts.units) _this.opts.units = "degrees";
            if(!_this.opts.extent) _this.opts.extent = [-179.9999999999999, -90,180.0000000000002,83.63410065300012];//世界范围
            if(!_this.opts.zoom) _this.opts.zoom=8;
            if(!_this.opts.minZoom) _this.opts.minZoom=4;
            if(!_this.opts.maxZoom) _this.opts.maxZoom=18;
//		     if(!_this.opts.logoSrc) _this.opts.logoSrc="";
//		     if(!_this.opts.logoHref) _this.opts.logoHref="";
            if(!_this.opts.format) _this.opts.format="image/png";

            return flag;
        };

        /**
         * 创建地图对象
         *
         * @version <1> 2017-11-07 Hayden:Creeated.
         */
        this.createMap = function(){
            if(!__init()) return;

            // 实例化鼠标位置控件
            var mousePositionControl = new ol.control.MousePosition({
                coordinateFormat: ol.coordinate.createStringXY(4), //坐标格式
                projection: 'EPSG:4326', //地图投影坐标系
                className: 'custom-mouse-position', //坐标信息显示样式
                // 显示鼠标位置信息的目标容器
                target: document.getElementById('mouse-position'),
                undefinedHTML: '&nbsp' //未定义坐标的标记
            });


            //设置默认地图控件的属性
            var defaultControl = new ol.control.defaults({zoom:false,rotate:false}).extend([mousePositionControl]);
            //创建投影信息
            var proj = new ol.proj.Projection({code: _this.opts.srs,units: _this.opts.units,axisOrientation: 'neu'});
            //根据地图边界获取中心点
            var center = ol.extent.getCenter(_this.opts.extent);
            //创建地图View对象
            var view = new ol.View({center:center,projection:proj,minZoom:_this.opts.minZoom,maxZoom:_this.opts.maxZoom,zoom:_this.opts.zoom});
            //构造地图对象
            _this.map = new ol.Map({
                target:_this.targetId,
                controls:defaultControl,
                // 加载鼠标位置控件
                // controls: ol.control.defaults().extend([mousePositionControl]),
                view:view,
                logo:{href:_this.opts.logoHref,src:_this.opts.logoSrc}
            });

            var urlPathname = window.location.pathname;
            var _flagControl = false;//是否显示地图控件

            if(urlPathname != null && urlPathname.indexOf("main.html") == -1){//不是首页
                _flagControl = true;
            }

            __addDefaultControl(_flagControl);  //不添加默认控件


            _this.map.on("click",function(evt){
                //如果绘制形状时，不执行地图单击事件
                var interactions = _this.map.getInteractions();
                for(var i=interactions.getLength()-1;i>=0;i--){
                    var interaction = interactions.item(i);
                    if(interaction instanceof ol.interaction.Draw){
                        var flag = interaction.getActive();
                        if(flag) evt.preventDefault();
                        return;
                    }
                }

                //执行自定义地图单击事件
                if(typeof _this.opts.mapClickFun =="function")  _this.opts.mapClickFun();
            });
        };

        this.resize = function(){
            // _this.map.getView().fit(_this.opts.extent,_this.map.getSize());
            _this.map.updateSize();

        };

        /********************************************************************************************************
         *
         * 								在地图上增加WMS图层 、过滤WMS图层转为Vector图层 、增加图层、删除图层
         *
         ********************************************************************************************************/

        /**
         * 叠加栅格影像
         * 1. 直接加载给定URL的图层信息
         * 2. 加载对URL过滤后的图层信息,如filter= "code like 'USA%' and level='2'"
         * @param layerName:图层名称(带工作空间:名称)
         * @param opts.url : 图层url
         * @param opts.filter : 过滤条件"code like 'USA%' and level='2'"
         * @param opts.opacity : 透明度
         * @version <1> 2017-11-07 Hayden:Created.
         */
        this.addWMSLayer = function(layerName,opts){
            if(!opts) opts = {};
            if(!opts.url) opts.url = _this.opts.url + "/wms";
            if(!opts.opacity) opts.opacity = 1;
            var source = new ol.source.ImageWMS();
            source.setUrl(opts.url);

            var param = {layers:layerName};
            param['AccessToken'] =MapTool.getUserToken();
            // if(opts.filter) param["CQL_FILTER"]=opts.filter;
            source.updateParams(param);

            var layer = new ol.layer.Image();
            layer.setSource(source);
            layer.setExtent(_this.opts.extent);
            layer.setOpacity(opts.opacity);
            if (!!opts.zIndex) {
                layer.setZIndex(opts.zIndex);
            }


            _this.addLayer(layer);
            return layer;
        };

       /* @return ol.layer.Tile
        * @version <1> 2018-07-11 17:11:19 Hayden : Created.
        */
        this.addXYZTileLayer = function(opts){
            var layer = new ol.layer.Tile({
                // extent: ol.proj.transformExtent(options.mapExtent, options.fromProject, options.toProject),
                source: new ol.source.XYZ({
                    // attributions: [opts.attribution],
                    url: opts.url,
                    tilePixelRatio: opts.tilePixelRatio, // THIS IS IMPORTANT
                    minZoom: opts.mapMinZoom,
                    maxZoom: opts.mapMaxZoom
                })
            });
            return layer;
        };

        /**
         * 加载Tile图层
         * 1. 直接加载给定URL的图层信息
         * 2. 加载对URL过滤后的图层信息,如filter= "code like 'USA%' and level='2'"
         * @param layerName:图层名称(带工作空间:名称)
         * @param opts.url : 图层url
         * @param opts.filter : 过滤条件"code like 'USA%' and level='2'"
         * @param opts.opacity : 透明度
         * @version <1> 2018-05-22 15:43:13 Hayden : Created.
         */
        this.addTileWMSLayer = function(layerName,opts){
            if(!opts) opts = {};
            if(!opts.url) opts.url = _this.opts.url + "/wms";
            if(!opts.opacity) opts.opacity = 1;

            var source = new ol.source.TileWMS();
            source.setUrl(opts.url);
            var param = {layers:layerName};
            param['AccessToken'] =MapTool.getUserToken();
            if(opts.filter) param["CQL_FILTER"]=opts.filter;

            source.updateParams(param);

            var layer = new ol.layer.Tile();
            layer.setSource(source);
            layer.setExtent(_this.opts.extent);
            layer.setOpacity(opts.opacity);
            if (!!opts.zIndex) {
                layer.setZIndex(opts.zIndex);
            }

            _this.addLayer(layer);
            return layer;
        };

        /**
         * 将WMS图层过滤，构建成新的Vector Layer.
         * @param layerName:图层名称(带工作空间:名称)
         * @param opts.url : 图层url
         * @param opts.filter : 过滤条件"code like 'USA%' and level='2'"
         * @param opts.opacity : 透明度
         * @version <1> 2017-11-07 Hayden:Created.
         */
        this.filterWMSToImageLayer = function(layerName,opts){
            if(!opts) opts = {};
            if(!opts.url) opts.url = _this.opts.url + "/ows";
            if(!opts.opacity) opts.opacity = 1;
            if(!opts.style) opts.style = MapTool.defineStyle({fillColor:"rgba(255,255,255, 0.1)",strokeColor:"#00f"});

            var format = new ol.format.GeoJSON();
            var source = new ol.source.Vector({
                format:format,
                url:function(extent){
                    var tempUrl = opts.url +"?service=WFS&request=GetFeature&outputFormat=application/json&format_options=callback:loadFeatures";
                    tempUrl +="&typeName="+layerName+"&AccessToken="+MapTool.getUserToken();
                    if(opts.filter) tempUrl +="&CQL_FILTER="+opts.filter;
                    else tempUrl +="&bbox="+extent.join(",");
                    return tempUrl;
                },
                strategy: ol.loadingstrategy.bbox
            });

            var imageSource = new ol.source.ImageVector({
                source:source,
                style:function(feature,resolution){
                    if(resolution<=0.05){
                        var chinaName = feature.get("CHINA_NAME");
                        var name = feature.get("REGION_NAME");
                        name = chinaName!="" ?chinaName:name;
                        var geom = feature.getGeometry();
                        opts.style.getText().setText(name);
                    }else{
                        opts.style.getText().setText("");
                    }
                    return opts.style;
                }
            });

            var layer = new ol.layer.Image();
            layer.setSource(imageSource);
            layer.setOpacity(opts.opacity);
            if (!!opts.zIndex) {
                layer.setZIndex(opts.zIndex);
            }

            _this.addLayer(layer);
            return layer;
        };

        /**
         * 将WMS图层过滤，构建成新的Vector Layer.
         * @param layerName:图层名称(带工作空间:名称)
         * @param opts.url : 图层url
         * @param opts.filter : 过滤条件"code like 'USA%' and level='2'"
         * @param opts.opacity : 透明度
         * @version <1> 2017-11-07 Hayden:Created.
         */
        this.filterWMSToVectorLayer = function(layerName,opts){
            if(!opts) opts = {};
            if(!opts.url) opts.url = _this.opts.url + "/ows";
            if(!opts.opacity) opts.opacity = 1;
            if(!opts.style) opts.style = MapTool.defineStyle({fillColor:"rgba(255,255,255, 0.1)",strokeColor:"#00f"});

            var layer = new ol.layer.Vector({});
            var wfsParams = {
                service:'WFS',
                request:'GetFeature',
                typeName:layerName,
                outputFormat:'application/json',
                CQL_FILTER:opts.filter
            };
            var format = new ol.format.GeoJSON();
            var source = new ol.source.Vector({
                format:format,
                loader:function(extent,resolution,projection){
                    var ajax = new BaseAjax();
                    ajax.opts.url = opts.url;
                    ajax.opts.data = wfsParams,
                        ajax.opts.type="get";
                    ajax.opts.successFun = function(data){
                        if(data){
                            var format = new ol.format.GeoJSON();
                            var features = format.readFeatures(data);
                            source.addFeatures(features);
                        }
                    };
                    ajax.opts.errorFun = function(msg){
                        console.info(msg);
                    };
                    ajax.run();
                },
                strategy: ol.loadingstrategy.bbox
            });

            layer.setSource(source);
            layer.setStyle(opts.style);
            layer.setOpacity(opts.opacity);
            if (!!opts.zIndex) {
                layer.setZIndex(opts.zIndex);
            }
            _this.addLayer(layer);
            return layer;
        };

        /**
         * 将查询到的图层填充背景色并设置中文名称.
         * @param layerName:图层名称(带工作空间:名称)
         * @param opts.url : 图层url
         * @param opts.filter : 过滤条件"code like 'USA%' and level='2'"
         * @param opts.opacity : 透明度
         */
        this.filterWMSToVectorLayerFillColor = function(layerName,opts){

            //console.log("filterWMSToVectorLayerFillColor","1");


            if(!opts) opts = {};
            if(!opts.url) opts.url = _this.opts.url + "/ows";
            if(!opts.opacity) opts.opacity = 1;
            if(!opts.style) opts.style = MapTool.defineStyle({fillColor:"rgba(255,255,255, 0.1)",strokeColor:"#00f"});

            var wfsParams = {
                service:'WFS',
                request:'GetFeature',
                typeName:layerName,
                outputFormat:'application/json',
                CQL_FILTER:opts.filter
            };
            var format = new ol.format.GeoJSON();
            var source = new ol.source.Vector({
                format:format,
                loader:function(extent,resolution,projection){
                    var ajax = new BaseAjax();
                    ajax.opts.url = opts.url;
                    ajax.opts.data = wfsParams,
                        ajax.opts.type="get";
                    ajax.opts.successFun = function(data){
                        if(data){
                            var format = new ol.format.GeoJSON();
                            var features = format.readFeatures(data);
                            source.addFeatures(features);
                        }
                    };
                    ajax.opts.errorFun = function(msg){
                        console.info(msg);
                    };
                    ajax.run();
                },

                strategy: ol.loadingstrategy.bbox
            });

            var strokeColor = opts.strokeColor;
            if(!strokeColor){
                strokeColor = 'rgba(227,196,88,0.5)';
            }
            var layer = new ol.layer.Vector({
                source:source,
                style: function(feature,resolution){
                    var polyStyleConfig = {//设置多边形样式
                        stroke: new ol.style.Stroke({//线样式
                            color: strokeColor,
                            width: 1
                        }),
                        fill: new ol.style.Fill({//填充样式
                            color: 'rgba(255,255,255,0)'
                        })
                    }

                    var _area = feature.getGeometry().getArea();

                    var _text = "";
                    if(_area >= 0.25 || (resolution <=0.0055 && _area >= 0.15) || (resolution <= 0.00275 && _area >= 0.13) || (resolution <= 0.002)){

                        _text = " "+feature.get('china_name')+" " ;
                    }
                    var textStyle = _this.highFeatureText(feature,_text);
                    var style = new ol.style.Style(polyStyleConfig);
                    return [style,textStyle];
                }
            });


//			layer.setSource(source);
//			layer.setStyle(opts.style);
            layer.setOpacity(opts.opacity);
            if (!!opts.zIndex) {
                layer.setZIndex(opts.zIndex);
            }
            _this.addLayer(layer);
            return layer;
        };

        this.getMaxPoly = function(polys){
            var polyObj = [];
            //now need to find which one is the greater and so label only this
            for (var b = 0; b < polys.length; b++) {
                polyObj.push({ poly: polys[b], area: polys[b].getArea() });
            }
            polyObj.sort(function (a, b) { return a.area - b.area });

            return polyObj[polyObj.length - 1].poly;

        };

        /**
         *基于矢量图层构造色块显示
         * @version<1> 2018-10-16 lcw :Created.
         */
        this.fillColorInVectorLayer = function(){

        };



        /**
         * 图层色块显示
         *showLayer : 子集图层
         * lvStyle  ：分级图层分级样式
         * lvDate ：处理后的分级数据
         *@version <1> 2017-11-24 XuZhenguo:Created.
         */
        var colorLayer;
        this.addColorLayer = function(showLayer,lvStyle,levelData,zIndex,param){

            if(!showLayer) {return false;}

            if (colorLayer) {
                _this.map.removeLayer(colorLayer);
            }

            colorLayer = new ol.layer.Vector({
                source :new ol.source.Vector({
                    format:new ol.format.GeoJSON()
                }),
                style:function (feature,resolution) {
                    var region_code = feature.get('region_code');
                    if(levelData[region_code] && levelData[region_code]['LV']){
                        feature.setStyle(lvStyle[levelData[region_code]['LV']]);
                    }else {
                        _this.moveToLayerFeature(showLayer,"region_code",param.regionCode,true)
                        // feature.setStyle(lvStyle);
                    }
                },
                'opacity':1,
                'zIndex':zIndex
            });

            if (_this.map && _this.map instanceof ol.Map) _this.map.addLayer(colorLayer);

            showLayer.getSource().forEachFeature(function(feature){
                feature.set('levelData',levelData[feature.get('region_code')]);//给色块图层添加业务数据
                colorLayer.getSource().addFeature(feature.clone());
            });

            return colorLayer;
        };

        /**
         * 将图层增加到地图对象中
         * @version <1> 2017-11-08 Hayden:Created.
         */
        this.addLayer = function(layer){
            if(_this.map && _this.map instanceof ol.Map) _this.map.addLayer(layer);
        };

        /**
         * 删除图层
         * @param layer : 待删除的图层
         * @version <1> 2017-11-09 Hayden:Created.
         */
        this.removeLayer = function(layer){
            if(_this.map && _this.map instanceof ol.Map){
                if(layer) _this.map.removeLayer(layer);
            }
        }

        /********************************************************************************************************
         *
         * 								在地图上高亮显示、移动中心点
         *
         ********************************************************************************************************/
        /**
         *地图弹出框显示
         * @param highLayer : feature 所在的图层
         * @param highStyle : 高亮度显示的样式
         * @version <1> 2017-11-27 XuZhenguo:Created.
         */
        var highFeature;
        this.highFeatureForVectoryLayer = function(highLayer,highStyle){
            if(!highStyle) highStyle = MapTool.defineStyle({fillColor:"rgba(255,215,0, 0.1)",strokeColor:"#20B2AA"});
            _this.map.on("pointermove",function(event){
                if (event.dragging)  return;
                _this.map.forEachFeatureAtPixel(event.pixel, function (feature, layer) {
                    if(layer == highLayer){
                        if(highFeature && highFeature!=feature) highFeature.setStyle(null);
                        var name = feature.get("CHINA_NAME");
                        highStyle.getText().setText(name);
                        feature.setStyle(highStyle);
                        highFeature = feature;
                    }
                });
            });
        };

        /**
         * 指定feature高亮度显示
         * @param feature : feature
         * @param hgithStyle : 高亮度显示的样式
         * @version <1> 2017-11-08 Hayden:Created.
         */
        this.highFeature = function(feature,highStyle,/*optional*/_text){
            if(!highStyle) highStyle = MapTool.defineStyle({fillColor:"rgba(255,215,0, 0)",strokeColor:"#007FFF",strokeWidth:2});
            if(highFeature && highFeature!=feature) highFeature.setStyle(null);
            if (_text){//当有文字需要高亮显示时
                var textStyle = _this.highFeatureText(feature,_text);
                feature.setStyle([highStyle,textStyle]);
            } else {
                feature.setStyle(highStyle);
            }
            highFeature = feature;
        };

        /**
         * .移除图层边框高亮效果
         * @param layer 图层
         * @param strokeColor 边框颜色，根据边框颜色找到高亮图层
         * @param isRemoveData是否删除图层上绑定的数据
         */
        this.removeHighFeature = function (layer,strokeColor,/**optional**/isRemoveData) {
            var selectedFeature ;
            var source ;

            if(layer instanceof ol.layer.Image){
                source = layer.getSource().getSource();
            }else if(layer instanceof ol.layer.Vector){
                source = layer.getSource();
            }

            if(source){
                source.forEachFeature(function(feature){
                    //根据高亮的边框颜色找到高亮的区域
                    if (!!feature.getStyle()){
                        if (feature.getStyle() instanceof Array){//如果该feature有多个Style
                            for (var i=0;i<feature.getStyle().length;i++){
                                var style = feature.getStyle()[i];
                                var stroke = style.getStroke();
                                var color = null;
                                if (stroke){
                                    color = stroke.getColor();
                                } else {
                                    continue;
                                }
                                color == strokeColor?selectedFeature = feature:null;
                            }
                        } else {
                            feature.getStyle().getStroke().getColor() == strokeColor?selectedFeature = feature:null;
                        }
                    }
                    if (isRemoveData){
                        feature.set("levelData",null);
                    }
                });

                if(selectedFeature){
                    selectedFeature.setStyle(null);//取消高亮区域的边框高亮效果
                }
            }

        }

        /**
         * 根据图层属性找到符合要求的feature，并将地图中心点移至feature的中心点
         * 如，在国家图层上根据code找到国家的feature
         * @param layer : 待查询的layer
         * @param fieldName: 待匹配的图层feature字段名
         * @param fieldValue: 待匹配的图层feature字段值
         * @return : 返回查询到的feature.
         * @version <1> 2017-11-08 Hayden:Created.
         */
        this.moveToLayerFeature = function(layer,fieldName,fieldValue,isHighLight){

            var selectedFeature ;
            var source ;

            if(layer instanceof ol.layer.Image){
                source = layer.getSource().getSource();
            }else if(layer instanceof ol.layer.Vector){
                source = layer.getSource();
            }

            if(source){
                source.forEachFeature(function(feature){
                    if(feature.get(fieldName) == fieldValue){
                        selectedFeature = feature;
                    }
                });

                if(selectedFeature){
                    var geom = selectedFeature.getGeometry();
                    var point = ol.extent.getCenter(geom.getExtent());
                    var highStyle = MapTool.defineStyle({fillColor:"rgba(255,215,0, 0)",strokeColor:"#007FFF",strokeWidth:2});
                    _this.map.getView().setCenter(point);

                    if (isHighLight) {
                        var text = selectedFeature.get("china_name");
                        _this.highFeature(selectedFeature,highStyle,text);
                    }
                    //z在社区写报告页面中， 不使用fit方法
                    // _this.map.getView().fit(geom.getExtent(),_this.map.getSize());
                }
            }

            return selectedFeature;
        };

        /**
         * 移动至中心点，底图层级更新为5
         * @param geom
         * @version <1> 2017-12-29 lcw : created.
         */
        this.moveToCenter = function(geom){
            var point = ol.extent.getCenter(geom.getExtent());
            _this.map.getView().setZoom(8);
            _this.map.getView().setCenter(point);
        }

        /**
         * 根据传入的文字高亮显示再图层上
         * @param text
         * @returns {*|Style|void}
         */
        this.highFeatureText = function (feature,text) {
            var textStyleConfig = {//设置多边形内文字样式
                text:new ol.style.Text({
                    text:text,//文字内容
                    scale:1,//文字大小
                    fill: new ol.style.Fill({ color: "#fff" }),//文字填充颜色
                    stroke: new ol.style.Stroke({ color: "#E3C458", width: 0.2 }),//文字线颜色
                    placement:'point',
                    // backgroundStroke:new ol.style.Stroke({
                    //   color:'rgba(255,51,0,1)',
                    //   width:1
                    // }),
                    backgroundFill:new ol.style.Fill({
                        color:'rgba(129,171,216,0.7)',
                        width:1
                    }),
                }),
                geometry: function(feature){
                    var retPoint;
                    if (feature.getGeometry().getType() === 'MultiPolygon') {
                        retPoint =  _this.getMaxPoly(feature.getGeometry().getPolygons()).getInteriorPoint();
                    } else if (feature.getGeometry().getType() === 'Polygon') {
                        retPoint = feature.getGeometry().getInteriorPoint();
                    }
                    return retPoint;
                }
            }

            var textStyle = new ol.style.Style(textStyleConfig);
            return textStyle;
        }

        /********************************************************************************************************
         *
         * 								在地图上增加控件、增加图例
         *
         ********************************************************************************************************/
        /**
         * 为地图默认控件增加按钮
         * @param opts.id:
         * @param opts.title:
         * @param opts.innerHTML:
         * @param opts.clickFun:
         * @version <1> 2017-11-10 Hayden:Created.
         */
        this.addBtnToDefaultControl = function(opts){
            var zoomElemBar = document.getElementsByClassName("ol-zoom")[0];
            var btn = document.createElement("button");
            if(!opts) opts = {};
            if(opts.id) btn.id = opts.id;
            if(opts.title) btn.title = opts.title;
            if(opts.style) btn.style = opts.style;
            if(opts.className) btn.className = opts.className;
            if(opts.innerHTML) btn.innerHTML = opts.innerHTML;
            if(opts.clickFun) btn.onclick = opts.clickFun;
            zoomElemBar.appendChild(btn);
        };

        /**
         * 在地图上增加控年
         * @version <1> 2017-11-08 Hayden:Created.
         * @version <2> 2017-12-29 lcw : 拆分自定义控件
         */
        var __addDefaultControl =  function(_flagControl){
            _this.map.addControl(new ol.control.Zoom({
                'zoomInTipLabel':'放大',
                'zoomOutTipLabel':'缩小'
            }));

//			_this.map.addControl(new ol.control.Zoom());

            var style = "background-color:#fff;margin-bottom:5px;left:0;";
            var zoomElemBar = document.getElementsByClassName("ol-zoom")[0];
            zoomElemBar.style="top:60px;right:20px;left:auto;"
            // __addCustomControl(zoomElemBar); /**添加自定义控件*/

            if(_flagControl){//是否显示控件
                zoomElemBar.style.display = "inline";//显示自定义控件
            }else{
                zoomElemBar.style.display = "none";//隐藏自定义控件
            }
        };

        /**
         * 地图上添加自定义控件(从defaultControl中拆分出来)
         * @param zoomElemBar
         * @private
         * @version<1> 2017-12-29 lcw : Created.
         */
        var gfLayer;
        var __addCustomControl = function(zoomElemBar){
            var btn = document.createElement("button");
            /**btn.title="选择";
             btn.innerHTML="<img src='img/map/sicon3.png' width='20' height='20'/>";
             zoomElemBar.appendChild(btn);
             btn.onclick = function(){
				_this.drawClose();
			};

             btn = document.createElement("button");
             btn.title="全景";
             btn.innerHTML="<img src='img/map/sicon6.png' width='20' height='20'/>";
             zoomElemBar.appendChild(btn);
             btn.onclick = function(){
				_this.map.getView().fit(_this.opts.extent,_this.map.getSize());
				_this.drawClose();
			};

             btn = document.createElement("button");
             btn.id="btnLength";
             btn.title="测长度";
             btn.innerHTML="<img src='img/map/sicon5.png' width='20' height='20'/>";
             zoomElemBar.appendChild(btn);
             btn.onclick = function(){
				_this.drawFeature("LineString");
			};

             btn = document.createElement("button");
             btn.id="btnArea";
             btn.title="测面积";
             btn.innerHTML="<img src='img/map/sicon4.png' width='20' height='20'/>";
             zoomElemBar.appendChild(btn);
             btn.onclick = function(){
				_this.drawFeature("Polygon");
			};**/

            //加载是否显示高清底图
            btn = document.createElement("button");
            btn.id="btnShowGF";
            btn.title="高清底图";
            btn.innerHTML="<img src='img/map/sicon7.png' width='20' height='20'/>";
            zoomElemBar.appendChild(btn);
            $(btn).toggle(function () {
                $(btn).css('background-color','rgba(0,60,136,.8)');
                var opts = {
                    'zIndex':2
                };
                gfLayer = _this.addTileWMSLayer("JiaHeDC:ly_WORLD_GF",opts);
            },function () {
                $(btn).css('background-color','');
//              gfLayer.setVisible(false);
            });
        };




        /**
         * 重绘地图图例
         * @param legend : 图例数据，二维数组，一维表示颜色，二维表示含义。
         * @version <1> 2017-11-09 Hayden:Created.
         */
        var legendControl;
        this.repaintLegend = function(legend){
            if(legendControl) _this.map.removeControl(legendControl);

            if(!legend) return;

            var divLegend = document.createElement("div");
            divLegend.className="ol-control";
            divLegend.style="left:20px;bottom:55px;background-color: rgba(255,255,255,.8);"
            legendControl = new ol.control.Control({element: divLegend});
            _this.map.addControl(legendControl);

            var ul = document.createElement("ul");
            ul.style='padding-bottom:10px;';
            var li = document.createElement("li");
            li.id = 'legendTitle';
            li.innerHTML = "图例";
            li.style="list-style:none;text-align:center;margin:10px;font-weight: bold;";
            ul.appendChild(li);
            for(var i=0;i<legend.length;i++){
                li = document.createElement("li");
                li.style = "list-style:none;";
                var spanLabel = document.createElement("span");
                spanLabel.style="margin:2px 15px;display:inline-block;width:30px;vertical-align: middle;height: 15px;background-color:"+legend[i][1]+";";
                li.appendChild(spanLabel);
                var spanValue = document.createElement("span");
                spanValue.innerHTML=legend[i][0];
                spanValue.style="margin:2px 10px;display:inline-block;font-size:80%;";
                li.appendChild(spanValue);
                var spanName = document.createElement("span");
                spanName.innerHTML=legend[i][2];
                spanName.style="margin:2px 10px;display:inline-block;font-size:80%;";
                li.appendChild(spanName);
                ul.appendChild(li);
            }
            divLegend.appendChild(ul);

        };

        /********************************************************************************************************
         *
         * 								在地图上画线、面并测距，测距
         *
         ********************************************************************************************************/

        /**
         * 绘制feature，如Polygon,LineString
         * @param shape : 形状名称,如'Polygon','LineString' , 'Rectangle'
         * @version <1> 2017-11-08 Hayden:Created.
         * @version<2> 2018-05-30 lcw : 自定义形状，除了默认支持的形状外， 还支持Rectangle
         */
        this.drawFeature = function(shape,domObj){
            var draw , drawSource;
            _this.drawClose();

            //构造绘制feature的图层
            if(!__drawLayer){
                drawSource = new ol.source.Vector();
                __drawLayer = new ol.layer.Vector();
                __drawLayer.setZIndex(15);
                __drawLayer.setSource(drawSource);
                // __addLayer(__drawLayer);
                _this.addLayer(__drawLayer);
            }else{
                drawSource = __drawLayer.getSource();
            }

            //根据绘制的形状，创建draw对象，开始绘制
            if(shape){
                var opts = {source:drawSource,type:shape,style:undefined  };
                if(shape == "Rectangle"){
                    //设置最大点数为2
                    var maxPoints = 2;
                    var geometryFunction = function (coordinates, geometry) {
                        //如果geometry对象不存在或者为空，则创建
                        if (!geometry) {
                            geometry = new ol.geom.Polygon(null);
                        }
                        //开始点的坐标
                        var start = coordinates[0];
                        //结束点的坐标
                        var end = coordinates[1];
                        //根据开始坐标和结束坐标设置绘图点坐标
                        geometry.setCoordinates([
                            [start, [start[0], end[1]], end, [end[0], start[1]], start]
                        ]);
                        return geometry;
                    };

                    opts.type = "LineString" ; //绘制矩形时，type=LineString
                    opts.geometryFunction = geometryFunction;
                    opts.maxPoints = maxPoints;

                }

                draw = new ol.interaction.Draw(opts);
                _this.map.addInteraction(draw);
            }

            //绘制形状
            draw.on("drawstart",function(evt){
                //创建浮动提示标签
                var divTip = document.createElement("div");
                divTip.style="color:#f00;";
                var tipOverlay = new ol.Overlay({
                    element:divTip,
                    offset:[0,-15],
                    positioning:"bottom-center"
                });
                _this.map.addOverlay(tipOverlay);

                //为绘制的feature增加浮动提示内容，及设置浮动提示标签所在位置
                var feature = evt.feature;
                var tooltipCoord = evt.coordinate;
                feature.getGeometry().on('change', function (evt) {
                    var geom = evt.target;
                    if (geom instanceof ol.geom.Polygon) {
                        tooltipCoord = geom.getInteriorPoint().getCoordinates();
                        tipOverlay.setOffset([0, -7]);
                    }else{
                        tooltipCoord = geom.getLastCoordinate();
                    }
                    // var output = _this.calculateAreaOrLength(geom);
                    // divTip.innerHTML = output;
                    tipOverlay.setPosition(tooltipCoord);
                });
            });


            /**
             * 绘制完成后，将值填充值bbox的查询区域
             * @version<1> 2018-05-28 lcw :Created.
             */
            draw.on("drawend",function(evt){
                var extent = evt.feature.getGeometry().getExtent();
                if(domObj != undefined){
                    domObj.long1.value = extent[0].toFixed(7)
                    domObj.lat1.value = extent[1].toFixed(7)
                    domObj.long2.value = extent[2].toFixed(7)
                    domObj.lat2.value = extent[3].toFixed(7)
                }

                //移除控制
                _this.removeInteraction();

            })
        };

        /**
         * 移除鼠标点控制
         * @version<1> 2018-05-28 lcw :Created.
         */
        this.removeInteraction = function(){

            //清除地图上绘制形状时的interaction对象
            var interactions = _this.map.getInteractions();
            for(var i=interactions.getLength()-1;i>=0;i--){
                var interaction = interactions.item(i);
                if(interaction instanceof ol.interaction.Draw)
                    _this.map.removeInteraction(interaction);
            }
        }
        /**
         * 清除在地图上的绘制形状信息
         * 1. 绘制的feature
         * 2. 绘制的标签元素
         * 3. 绘制对象
         * @version <1> 2017-11-08 Hayden:Created.
         */
        this.drawClose = function(){
            //清除在地图上绘制形状时，绘制图层上的feature.
            if(__drawLayer){
                var drawSource = __drawLayer.getSource();

                drawSource.clear();
            }

            //清除在地图上绘制形状时的浮动标签元素
            var overlays = _this.map.getOverlays();
            for(var i=overlays.getLength()-1;i>=0;i--){
                _this.map.removeOverlay(overlays.item(i));
            }

            //清除地图上绘制形状时的interaction对象
            var interactions = _this.map.getInteractions();
            for(var i=interactions.getLength()-1;i>=0;i--){
                var interaction = interactions.item(i);
                if(interaction instanceof ol.interaction.Draw)
                    _this.map.removeInteraction(interaction);
            }
        };

        /**
         * 地图弹出框显示 和 关闭
         * @version <1> 2017-11-24 XuZhenguo:Created.
         */
        var _infoDlg;

        var infoDlgInit = function () {
            MapTool.crreateOverlayCss();
            var divContainer = document.createElement("div");
            divContainer.id = "ol_divContainer";
            divContainer.className = "ol-popup";

            var divContent = document.createElement("div");
            divContent.id = "ol_divContent";
            divContainer.appendChild(divContent);

            _infoDlg = new ol.Overlay({});
            _infoDlg.setElement(divContainer);

            _this.map.addOverlay(_infoDlg);
        }

        /**
         * 地图弹出框显示
         * content ： 显示内容html
         * @version <1> 2017-11-24 XuZhenguo:Created.
         */
        this.infoDlgShow = function (coordinate, content) {
            if (!(_infoDlg instanceof ol.Overlay)) infoDlgInit();
            var divContent = document.getElementById("ol_divContent");
            divContent.innerHTML = content;
            _infoDlg.setPosition(coordinate);
        }

        /**
         * 地图弹出框关闭
         * @version <1> 2017-11-24 XuZhenguo:Created.
         */
        this.infoDlgClose = function () {
            if (_infoDlg) _infoDlg.setPosition(undefined);
        }

        /**
         * ==================================================================================================
         *
         * Calculate Area or Length on Map.
         *
         * ==================================================================================================
         */

        /**
         * Calculate  feature of polygon area.
         * Calculate feature of line length.
         * @param feature
         */
        this.calculateAreaOrLength = function (geom, units) {
            var output = 0;
            var sphere = new ol.Sphere(6378137);

            var geom_projection = _this.map.getView().getProjection();
            var cal_projection = new ol.proj.Projection({code: "EPSG:4326"});
            //must use clone() method,otherwise the transform() will dead circulation
            geom = geom.clone().transform(geom_projection, cal_projection);

            if (geom instanceof ol.geom.Polygon) {
                var coordinates = geom.getLinearRing(0).getCoordinates();
                output = calculateArea(sphere, coordinates, units);
            }
            if (geom instanceof ol.geom.MultiPolygon) {
                var coordinates = geom.getFirstCoordinate();
                output = calculateArea(sphere, coordinates, units);
            }
            else if (geom instanceof ol.geom.LineString) {
                var coordinates = geom.getCoordinates();
                output = calculateLength(sphere, geom_projection, cal_projection, coordinates);
            }
            // console.log(coordinates);
            return output;
        }


        /**
         * 根据坐标点获取图层feature
         * pixel ： 坐标点
         * vector_layer_name ： 需要获取 feature 所在的 图层
         * @version <1> 2017-11-24 XuZhenguo:Created.
         */
        this.getFeatureByClick = function (pixel, vector_layer_name) {
            // if (_draw && (_draw instanceof ol.interaction.Draw && _draw.getMap())) return;
            var feature = _this.map.forEachFeatureAtPixel(pixel, function (feature, layer) {
                if (layer == vector_layer_name) return feature;
            });
            return feature;
        }

        /**
         * The method is a private method.
         * calculate area from coordinates for Polygon or Multipolygon .
         * @param sphere
         * @param coordinates
         * @param scale
         */
        var calculateArea = function (sphere, coordinates, units) {
            var output = 0.00;

            var area = Math.abs(sphere.geodesicArea(coordinates));
            if (units) {
                output = MapTool.transformSquareMeterToMu(area);
            } else {
                if (area > 10000) {
                    output = (Math.round(area / 1000000 * 100) / 100) + "km<sup>2</sup>";//square km.
                } else {
                    output = (Math.round(area * 100) / 100) + "m<sup>2</sup>";//square m.
                }
            }

            return output;
        };

        /**
         * This is a private method.
         * To calculate multi points distance.
         * @param coordinates:coordinates for multi points.
         * @param geom_projection
         * @param cal_projection
         */
        var calculateLength = function (sphere, geom_projection, cal_projection, coordinates) {
            var output, length = 0;
            for (var i = 0; i < coordinates.length - 1; ++i) {
                var c1 = ol.proj.transform(coordinates[i], geom_projection, cal_projection);
                var c2 = ol.proj.transform(coordinates[i + 1], geom_projection, cal_projection);
                length += sphere.haversineDistance(c1, c2);
            }
            if (length > 100) {
                length = (Math.round(length / 1000 * 100) / 100) + ' ' + 'km';
            } else {
                length = (Math.round(length * 100) / 100) + ' ' + 'm';
            }
            output = length;
            return output;
        };




        /**************************************************************************
         *				geometry操作
         *
         * ************************************************************************/
        /**
         * ==================================================================================================
         * Common methods for map operation .
         * Such as
         *  : Get feature from a Vector layer by click .
         *  : Add geometry to a new vector layer for  map .
         *  : Move map view to same point and zoom in/out.
         * ==================================================================================================
         */

        var _vectorLayer;

        /**
         * build a vector layer to draw geom or add vector feature.
         *
         * @version (1) 2017-06-28 Hayden : Created.
         */
        this.buildVectorLayer = function(){
            if(!(_vectorLayer instanceof ol.layer.Vector)){
                var features = new ol.Collection();
                _vectorLayer = new ol.layer.Vector({
                    source:new ol.source.Vector({features:features}),
                    zIndex:999
                });
                _this.map.addLayer(_vectorLayer);
            }
        }

        this.getVectorLayerFeature = function (pixel) {
            return _this.getFeatureByClick(pixel,_vectorLayer);
        }


        /**
         * Add point on map by specified by point coordinate.
         *
         * @param pointCoordinateArray
         * @version (1) 2017-06-28 Hayden : Updated.
         */
        this.addGeometry = function (pointCoordinateArray,styleOpts) {
            this.buildVectorLayer();
            var vectorSource = _vectorLayer.getSource();

            var style = MapTool.defineStyle(styleOpts);
            for(var i in  pointCoordinateArray){
                var pointFeature = new ol.Feature({});
                var point = new ol.geom.Point(pointCoordinateArray[i]);
                pointFeature.setGeometry(point);
                pointFeature.setStyle(style);
                vectorSource.addFeature(pointFeature);
            }
        }

        /**
         * add polygon geometry
         * @param polygon : bbox string : "polygon[[],[]]"
         */
        this.addGeometry = function (polygon, id,styleOpts) {
            this.buildVectorLayer();
            var style = MapTool.defineStyle(styleOpts);

            var wkt_parser = new ol.format.WKT();
            var geometryFeature = wkt_parser.readFeature(polygon);
            geometryFeature.setId(id);
            geometryFeature.setStyle(style);
            _vectorLayer.getSource().addFeature(geometryFeature);
        }

        /**
         * remove polygon geometry  by id
         */
        this.removeGeometry = function (featureId) {
            if(_vectorLayer){
                var vectorFeature = _vectorLayer.getSource();
                if(featureId){
                    vectorFeature.removeFeature(vectorFeature.getFeatureById(featureId));
                } else {
                    vectorFeature.clear();
                }
            }
        }

        this.removeGeometrys = function () {
            if(_vectorLayer){
                var source = _vectorLayer.getSource();
                if(source){
                    source.clear();
                }
            }
        }


        /*===============================Thumbnail =========================================*/

        var _thumbnailLayers=[];

        /**
         * Show data `s thumbnail in polygon which consist of data`s bound.
         * 1. Show image info via ImageStatic
         * 2. Use polygon clip image .
         * @param imageUrl : thumbnail file url
         * @param geom : thumbnail `s area.
         * @version (1) 2017-06-28 Hayden:created.
         */
        this.showThumbnail = function(imageUrl,geom,layerId){
            //find layer by layerId, if the layer which present by layerID is exists, then return it.
            //else create new image layer .
            for(var i=0;i<_thumbnailLayers.length;i++){
                var tempLayer = _thumbnailLayers[i];
                var existsId = tempLayer.get("id");
                if(layerId && existsId){
                    if(layerId==existsId) return tempLayer;
                }
            }

            var extent = geom.getExtent();
            //add image layer
            var layer = new ol.layer.Image({
                source: new ol.source.ImageStatic({
                    url: imageUrl,
                    crossOrigin:null,
                    imageExtent: extent
                }),
                zIndex:10
            });

            // A style for the geometry.
            var style = new ol.style.Style({
                fill:new ol.style.Fill({color: [0, 0, 0, 0]})
            });

            layer.on('precompose',function(event){
                var ctx = event.context;
                var vecCtx = event.vectorContext;

                ctx.save();
                vecCtx.setStyle(style, null);
                vecCtx.drawGeometry(geom);

                ctx.clip();

            });

            layer.on('postcompose',function(event){
                var ctx = event.context;
                ctx.restore();
            });

            _this.map.addLayer(layer);
            layer.set("id",layerId);
            _thumbnailLayers.push(layer);

            return layer;
        }

        /**
         * remove thumbnail layer by layer ID.
         *
         * @param layerId : layer id.
         * @version (1) 2017-06-28 Hayden:created.
         */
        this.removeThumbnailLayer = function(layerId){
            for(var i=0;i<_thumbnailLayers.length;i++){
                var layer = _thumbnailLayers[i];
                var existsId = layer.get("id");
                if(layerId && existsId){
                    if(layerId==existsId){
                        _this.map.removeLayer(layer);
                        _thumbnailLayers.splice(i,1);
                        break;
                    }
                }
            }
        }

        /**
         * remove thumbnail layer by layer ID.
         *
         * @param layerId : layer id.z
         * @version (1) 2017-06-28 Hayden:created.
         */
        this.removeThumbnailLayers = function(){
            for(var i=_thumbnailLayers.length-1;i>=0;i--){
                var layer = _thumbnailLayers[i];
                if(layer){
                    _this.map.removeLayer(layer);
                    _thumbnailLayers.pop(i,1);
                }
            }
        }

    };




    /********************************************************************************************************
     *
     * 								MapTool静态方法： 定义样式
     *
     ********************************************************************************************************/

    /**
     * define style with fill and stroke.
     *
     * @param fillColor   ：填充颜色
     * @param strokeColor ：边框颜色
     * @param strokeWidth : 边框宽度
     * @param radius 	  : 圆半径
     * @param font_family : 文字字体
     * @param fon_color : 文字颜色
     * @returns {ol.style.Style}
     * @version <1> 2017-11-08 Hayden:Created.
     */
    MapTool.defineStyle = function (opts) {
        if(!opts) opts = {};
        // fillColor, strokeColor, strokeWidth,radius,text
        if(!opts.fillColor) opts.fillColor = "rgba(255, 255, 255, 1)";
        if(!opts.strokeColor) opts.strokeColor = "#ffcc33";
        if(!opts.strokeWidth) opts.strokeWidth = 1;
        if(!opts.radius) opts.radius = 0;
        if(!opts.font_family) opts.font_family = "sans-serif";
        if(!opts.font_color) opts.font_color = "#000";

        var fill = new ol.style.Fill();
        fill.setColor(opts.fillColor);

        var stroke = new ol.style.Stroke();
        stroke.setColor(opts.strokeColor);
        stroke.setWidth(opts.strokeWidth);

        var text = new ol.style.Text({
            font: (opts.radius * 2 - 5) +'px '+opts.font_family,
            fill: new ol.style.Fill({ color: opts.font_color }),
            textAlign:'Center',
            textBaseline:'Middle'
        });

        var image = new ol.style.Circle();
        image.setRadius(opts.radius);

        var style = new ol.style.Style();
        style.setFill(fill);
        style.setStroke(stroke);
        style.setImage(image);
        style.setText(text);
        return style;
    };



    MapTool.setLayerStyle = function (opts) {
        if(!opts) opts = {};
        // fillColor, strokeColor, strokeWidth,radius,text
        if(!opts.radius) opts.radius = 0;
        if(!opts.font_family) opts.font_family = "sans-serif";
        if(!opts.font_color) opts.font_color = "#000";




        var style = new ol.style.Style();
        if (!!opts.fillColor) {
            var fill = new ol.style.Fill();
            fill.setColor(opts.fillColor);
            style.setFill(fill);
        }

        if (!!opts.strokeColor || !!opts.strokeWidth) {
            var stroke = new ol.style.Stroke();
            if (!!opts.strokeColor) {
                stroke.setColor(opts.strokeColor);
            }
            if (!!opts.strokeWidth) {
                stroke.setWidth(opts.strokeWidth);
            }
            style.setStroke(stroke);
        }

        var image = new ol.style.Circle();
        image.setRadius(opts.radius);

        var text = new ol.style.Text({
            font: (opts.radius * 2 - 5) +'px '+opts.font_family,
            fill: new ol.style.Fill({ color: opts.font_color }),
            textAlign:'Center',
            textBaseline:'Middle'
        });

        style.setImage(image);
        style.setText(text);
        return style;
    };

    /**
     * 加载地图弹出框样式
     * @version <1> 2017-11-27 XuZhenguo:Created.
     */
    MapTool.crreateOverlayCss = function(){
        var styleElementId = "overlays"

        var styleList = [];
        styleList.push(".ol-popup {position: absolute;background-color: white;-webkit-filter: drop-shadow(0 1px 4px rgba(0,0,0,0.2));filter: drop-shadow(0 1px 4px rgba(0,0,0,0.2));padding: 15px;border-radius: 10px;border: 1px solid #cccccc;bottom: 12px;left: -50px;min-width: 200px;}");
        styleList.push(".ol-popup:after, .ol-popup:before {top: 100%;border: solid transparent;content:'';height: 0;width: 0;position: absolute;pointer-events: none;}");
        styleList.push(".ol-popup:after {border-top-color: white;border-width: 10px;left: 48px;margin-left: -10px; }");
        styleList.push(".ol-popup:before {border-top-color: #cccccc;border-width: 11px;left: 48px;margin-left: -11px;}");
        styleList.push(".ol-popup-closer {text-decoration: none;position: absolute;top: 2px;right: 8px;}");
        styleList.push(".ol-popup-closer:after {content: 'X';}");

        //如果样式已存在，则不在增加
        styleArea = document.getElementById(styleElementId);
        if(!styleArea){
            styleArea = document.createElement("style");
            styleArea.type="text/css";
            styleArea.id=styleElementId;
            document.getElementsByTagName("head")[0].appendChild(styleArea);
            for(var i in styleList){
                var newStyleNode = document.createTextNode(styleList[i]);
                styleArea.appendChild(newStyleNode);
            }
        }
    }

    MapTool.getUserToken = function(){
        var key = COOKIE_CONFIG.cookieName;
        var arr,reg=new RegExp("(^| )"+key+"=([^;]*)(;|$)");
        if(arr=document.cookie.match(reg)){
            return arr[2];
        }
        return '';
    }

    //暴露区域选择器控件
    return {
        MapTool : MapTool
    };
});
