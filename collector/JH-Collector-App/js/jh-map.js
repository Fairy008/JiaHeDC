// <script src="https://webapi.amap.com/maps?v=1.4.13&key=fabc6d27565ea4e6d7b0008cd2a5340d&&
// 		plugin=AMap.OverView,AMap.Autocomplete,AMap.PlaceSearch,AMap.MouseTool,AMap.Geocoder,AMap.ToolBar,AMap.PolyEditor"></script>
// 		

var jhMap = {
	jhAmap: '', //高德地图对象
	mMap: null, //地图容器
	mMarkers: [], //当前坐标点集合
	mPolygon: null, //当前区域
	mInfoWindow: null, //当前窗口
	mpolyEditor:null,//区域编辑器
	
	buildMap: function(idStr) {
		jhMap.mMap = new jhMap.jhAmap.Map(idStr, {
			zoom: 20,
			layers: [
				//图层
				new AMap.TileLayer.Satellite(),
				new AMap.TileLayer.RoadNet()
			],
			resizeEnable: true
		});
		return jhMap.mMap;
	},
	buildMarker: function(mposition,isDrag) {//构建marker
		var marker = new AMap.Marker({
			position: mposition,
			map: jhMap.mMap,
			draggable: isDrag,
		});
		jhMap.add2Markers(marker)
		return marker;
	},
	add2Markers: function(marker) { //添加坐标点到数组
		jhMap.mMarkers.push(marker);
	},
	removeMarker: function(index) { //移除坐标点
		jhMap.mMarkers.splice(Index, 1)
	},
	buildPolygon: function(isDraggable) { //初始化区域
		var polygon = new jhMap.jhAmap.Polygon({
			path: [],
			strokeColor: "red",
			strokeOpacity: 1,
			strokeWeight: 3,
			strokeDasharray: [10, 5],
			draggable: isDraggable,
		});
		jhMap.mPolygon = polygon;
		return jhMap.mPolygon;
	},
	setPolygon(mpoints, dragfunc, clickfunc) { //更新设置区域
		var center = jhMap.getCenterPoint(jhMap.mPolygon)
		jhMap.mPolygon.setPath(mpoints);
		jhMap.mPolygon.setMap(jhMap.mMap);
		jhMap.mPolygon.on('dragging', function() {
			dragfunc()
		})
		jhMap.mPolygon.on('click', function() {
			clickfunc()
		})
	},
	buildInfoWindow: function(areaStr, centerPoint) { //初始化信息弹窗
		jhMap.mInfoWindow = new jhMap.jhAmap.InfoWindow({
			content: areaStr, //使用默认信息窗体框样式，显示信息内容
			position: centerPoint,
			// offset: new AMap.Pixel(0, -30),
		});
		return jhMap.mInfoWindow;
	},
	buildPolyEditor:function(){//初始化区域编辑器
		jhMap.mpolyEditor = new AMap.PolyEditor(jhMap.mMap, jhMap.mPolygon)
		return jhMap.mpolyEditor
	},
	getCenterPoint: function(path) { //获取中心点
		//var path =e.;//Array<Point> 返回多边型的点数组
		//var ret=parseFloat(num1)+parseFloat(num2);
		var x = 0.0;
		var y = 0.0;
		for (var i = 0; i < path.length; i++) {
			x = x + parseFloat(path[i].lng);
			y = y + parseFloat(path[i].lat);
		}
		x = x / path.length;
		y = y / path.length;
		return [x, y];
	},
	convertUnits: function(marea) { //平方米换成亩
		marea = marea * 15 / 10000
		marea = Math.floor(marea * 100) / 100 + '亩'
		return marea;
	},
}
