/**
 * 此文件用来定义珈和科技前端公用的控件，如区域选择器控件、等。
 *
 * 1. 区域选择器控件：RegionSelector 实现如下功能：
 *	(1). 区域的层级选择
 *	(2). 设置默认样式,可以让使用者定义控件的width , height , colNum（每行显示的区域个数)
 *
 * @version <1> 2017-11-04 Hayden : Created.
 */
define(["BaseAjax"],function(BaseAjax){

	// var defaultRegion = 3100000000;
	var defaultRegion;
	/**
	 * 获取DOM元素样式中的某个属性值，如paddingLeft等
	 * @param dom : Dom 元素对象
	 * @param attr: 样式属性
	 * @version : 样式属性的值
	 * @version <1> <1> 2017-11-04 Hayden : Created.
	 */
	var getStyleValue = function(dom, attr){
		return dom.currentStyle ? dom.currentStyle[attr] : getComputedStyle(dom, false)[attr];
	};

	/**
	 * 获取DOM元素样式中的某个属性值，如paddingLeft等
	 * @param dom : Dom 元素对象
	 * @param attr: 样式属性
	 * @version : 样式属性的值
	 * @version <1> <1> 2017-11-04 Hayden : Created.
	 */
	var parseStyleValueToInt = function(dom,attr){
		var styleAttr = getStyleValue(dom,attr);
		var temp = styleAttr.split("px").join("");
		var value = parseInt(temp);
		return value;
	};

	/**
	 * 定义区域选择器控件构造函数
	 * @param targetId : 承载区域选择器的DOM元素ID
	 * @param opts     : 选择器控件的可选参数,如
	 * 						opts.url 			: 获取区域服务地址
	 *						opts.width 			: 控件宽度 , default:300
	 *						opts.height 		: 控件高度 , default:260
	 *						opts.colNum			: 每一行显示的区域名称个数 default: 3
	 *						opts.areaList 		: 初始区域列表[{regionId:3100000000,regionName:中国}....{}]
	 *						opts.showFieldName	: 区域名称字段名,默认值 regionName
	 *						opts.idFieldName	: 区域值字段名 ,default : regionId
	 *
	 * @version <1> 2017-11-04 Hayden : Created.
	 */
	function RegionSelector(targetId,opts){
		var _this = this;

		//区域控件ID
		_this.divAreaId = "divArea";
		//区域控件中所有元素的样式
		_this.flagClass="regionClass";
		_this.targetId = targetId;

		//已选择区域的键值对数组
		_this.selectedAreaList = [];
		//承载区域选择器的DOM元素
		_this.targetObj = document.getElementById(_this.targetId);

		var __init = function(){
			var flag = true;
			if(!_this.targetObj){
				alert("请确定承载区域选择器控件的元素ID是否正确。");
				flag = false;
			}

			//可选参数默认值
			if(!opts) _this.opts = {};
			else _this.opts = opts;
			// if(!_this.opts.url) _this.opts.url = "";
			if(!_this.opts.width) _this.opts.width= _this.targetObj.clientWidth>300?_this.targetObj.clientWidth:300;
			if(!_this.opts.height) _this.opts.height= _this.targetObj.clientHeight>260?_this.targetObj.clientHeight:260;
			if(!_this.opts.colNum) _this.opts.colNum= 3;
			if(!_this.opts.showFieldName) _this.opts.showFieldName="chinaName";
			if(!_this.opts.idFieldName) _this.opts.idFieldName= "regionId";
			if(!(typeof _this.opts.areaList=="object" && !isNaN(_this.opts.areaList))) _this.opts.areaList = [];

			if(!_this.opts.url){
				alert("请确定设置获取区域数据的URL");
				flag = false;
			}
			return flag;
		};

		/**
		 * 创建默认的样式
		 * @version <1> 2017-11-03 Hayden:Create.
		 **/
		var __buildDefaultStyle = function(){
			var styleElementId = "styleArea";

			var styleList = [];
			styleList.push(".divArea{position:absolute;background-color:#fff;border:1px solid #41bfc6;text-align:center;font-size:12px;z-index: 100000;}");
			styleList.push(".divHeader,.divContent {padding:10px;box-sizing:border-box;width:100%;}");
			styleList.push(".divHeader{overflow:hidden;border-bottom:1px solid #41bfc6;}");
			styleList.push(".divHeader .spanLabel,.divHeader .spanValue{float: left;}");
			styleList.push(".divHeader .spanLabel{width:50px;border:1px solid #41bfc6;padding:5px;}");
			styleList.push(".divHeader .spanValue{border-bottom:1px dashed #41bfc6;padding:5px;}");
			styleList.push(".divHeader .spanValue a{cursor:pointer;text-decoration:none;}");
			styleList.push(".divArea,.spanLabel{border-radius:5px;}");
			styleList.push(".divContent {margin:0;list-style:none; }");
			styleList.push(".divContent li {float:left; padding: 10px 10px;cursor:pointer;}");
			styleList.push(".divContent li:hover{background-color: #41bfc6;}");

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
		};

		/**
		 * 创建区域选择器元素的界面UI
		 *
		 * @version <1> 2017-11-04 Hayden: Created.
		 */
		var __buildMainUi = function(){
			//区域选择器元素默认ID为divArea
			//判断元素是否存在于页面，如存在，则显示出来，并获取焦点
			var divArea = document.getElementById(_this.divAreaId);
			if(divArea){
				divArea.style.display="block";
				divArea.focus();
				return;
			}

			if(_this.opts.areaList.length==0 && typeof _this.opts.updateRegionFun =="function") _this.opts.updateRegionFun();

			//区域选择器元素如不存在，则在body内增加
			var bodyObj = document.getElementsByTagName('body')[0];

			//1.创建外层DIV
			divArea = document.createElement("div");
			divArea.id=_this.divAreaId;
			divArea.className="divArea regionClass";
			divArea.style.width = _this.opts.width + "px";
			// divArea.style.top = (_this.targetObj.offsetTop+_this.targetObj.clientHeight+2)+"px";
			divArea.style.height=_this.opts.height + "px";
			// divArea.style.left=_this.targetObj.offsetLeft+"px";
			// divArea.style.top = (_this.targetObj.offsetTop+_this.targetObj.clientHeight)+"px";
			divArea.style.top = ($(_this.targetObj).offset().top + $(_this.targetObj).height() ) + "px";
			divArea.style.left = $(_this.targetObj).offset().left + "px";

			bodyObj.appendChild(divArea);

			// divArea.setAttribute("tabindex","-1");
			divArea.focus();
			//失去焦点时，控件隐藏
			divArea.onblur=function(){
				if(divArea.style.display!="none") {_this.close();}
			};
			divArea.style.zIndex=9999;
			document.onclick = function(event){
				var evtTarget = event.target || event.srcElement;
				var id = evtTarget.id;
				var className = evtTarget.className;
				var flag = new RegExp(_this.flagClass).test(className);
				if(id!=_this.targetObj.id && !flag){
					if(divArea.style.display!="none") _this.close();
				}

			};

			//2. 创建头
		/*	var divHeader = document.createElement("div");
			divHeader.className="divHeader regionClass";
			divArea.appendChild(divHeader);

			var spanLabel = document.createElement("span");
			spanLabel.className="spanLabel regionClass";
			spanLabel.innerHTML = !_this.targetObj.value ? "请选择" : "请清除";
			divHeader.appendChild(spanLabel);
			spanLabel.onclick=function(){
				spanLabel.innerHTML ="请选择";
				spanValue.innerHTML="&nbsp;";
				_this.selectedAreaList = [];
				_this.targetObj.removeAttribute('parentid');
				_this.targetObj.removeAttribute('chinaname');
				_this.targetObj.removeAttribute('regionid');
				_this.targetObj.removeAttribute('regioncode');
				_this.targetObj.value = '';
				__loadRegion(defaultRegion);
			};*/
			var divHeader = document.createElement("div");
			divHeader.className="divHeader regionClass";
			divArea.appendChild(divHeader);

			var spanLabel = document.createElement("span");
			spanLabel.className="spanLabel regionClass";
			spanLabel.innerHTML ="请选择";
			divHeader.appendChild(spanLabel);
			spanLabel.onclick=function(){
				spanLabel.innerHTML ="请选择";
				spanValue.innerHTML="&nbsp;"
				_this.selectedAreaList = [];
				__loadRegion();
			};

			var spanValue = document.createElement("span");
			spanValue.className="spanValue regionClass";

			spanValue.innerHTML="&nbsp;";
			divHeader.appendChild(spanValue);
			var divHeaderPaddingLeft = parseStyleValueToInt(divHeader,"padding-left");
			var divHeaderPaddingRight = parseStyleValueToInt(divHeader,"padding-right");
			var spanValueWidth = divHeader.offsetWidth - divHeaderPaddingLeft - divHeaderPaddingRight - spanLabel.offsetWidth - 10;
			spanValueWidth = spanValueWidth + "px";
			spanValue.style.width=spanValueWidth;

			//3. 创建内容
			var divContent = document.createElement("ul");
			divContent.className="divContent regionClass";
			divContent.style.overflowY="scroll";
			divArea.appendChild(divContent);
			var tempHeight = divHeader.clientHeight;
			divContent.style.height = (divArea.clientHeight - tempHeight)+"px";

			__buildRegionUI();
		};

		/**
		 * 在区域选择器控件中创建显示区域的UI元素
		 *
		 * @version <1> 2017-11-04 Hayden : Created.
		 */
		var __buildRegionUI = function(){
			//获得控件的区域UI元素的容器元素
			var divContent = document.getElementsByClassName("divContent")[0];
			if(!divContent) return;
			//如果区域列表不为空，则清楚UI中原有的区域元素
			if (_this.opts.areaList && _this.opts.areaList.length>0) divContent.innerHTML = "";

			//根据区域列表数据，创建新的区域UI元素
			for(var i in _this.opts.areaList){
				var liElement = document.createElement("li");
				liElement.className="regionClass";
				var region = _this.opts.areaList[i];
				for(var key in region){
					liElement.setAttribute(key,region[key]);
					if(key == _this.opts.showFieldName) liElement.innerHTML=region[key];
				}

				//设置区域UI元素的样式，如大小
				divContent.appendChild(liElement);
				var paddingLeft = parseStyleValueToInt(divContent,"padding-left");
				var paddingRight = parseStyleValueToInt(divContent,"padding-right");
				var divContentPadding = paddingLeft + paddingRight;
				var paddingLeft = parseStyleValueToInt(liElement,"padding-left");
				var paddingRight = parseStyleValueToInt(liElement,"padding-right");
				var liElementPadding = paddingLeft + paddingRight;
				var liElementWidth = (divContent.clientWidth - divContentPadding - liElementPadding*_this.opts.colNum )/_this.opts.colNum;
				liElement.style.width =liElementWidth+"px";

				liElement.onclick=__regionClick;
			}
		};

		/**
		 * 获取当前选择区域信息，并根据选择区域获取下一级区域列表
		 *
		 * @version <1> 2017-11-04 Hayden : Created.
		 */
		var __regionClick = function(event){
			//获得选择的区域信息
			var evtTarget = event.target || event.srcElement;
			var regionName = evtTarget.innerHTML;
			var regionId = evtTarget.getAttribute(_this.opts.idFieldName);

			//将选择的区域放在控件头及目标元素上
			var tempDict = {};
			for(var i=0;i<evtTarget.attributes.length;i++)
			{
				if(evtTarget.attributes[i].name!="style"){
					var name =evtTarget.attributes[i].name;
					var value = evtTarget.attributes[i].value;
					tempDict[name]=value;
				}
			}

			//将选择区域加入选择列表中，如没有下一级区域列表，加入前需去掉最后个（即判断选择区域与上一次选择区域是同一级）
			if(!_this.opts.areaList || _this.opts.areaList.length == 0)
				_this.selectedAreaList.splice(_this.selectedAreaList.length-1,1,tempDict);
			else{
				//当ajax返回速度慢，重复选择同一个区域判断
				var idFieldName = _this.opts.idFieldName.toLowerCase();
				for(var i=0;i<_this.selectedAreaList.length;i++){
					if(_this.selectedAreaList[i][idFieldName] == tempDict[idFieldName]){
						return;
					}
				}
				_this.selectedAreaList.push(tempDict);
			}

			//获取当前选择区域的下一级区域列表，并重构控件的区域UI
			__loadRegion(regionId);
		};

		/**
		 * 在区域选择器头部分显示已选择区域的层级UI元素
		 * 1. 将选择的区域放在控件头及目标元素上.
		 *
		 * @version <1> 2017-11-04 Hayden : Created.
		 */
		var __buildSelectedRegionUI = function(){
			//获得显示已选择区域的容器元素UI
			var spanValue = document.getElementsByClassName("spanValue")[0];
			if(!spanValue) return;
			if(_this.selectedAreaList.length>0) spanValue.innerHTML = "";

			for(var pos in _this.selectedAreaList){
				if(pos!=0){
					var txtNode = document.createTextNode(" | ");
					spanValue.appendChild(txtNode);
				}

				var aElem = document.createElement("a");
				// aElem.className="regionClass";
				var region = _this.selectedAreaList[pos];
				for(var key in region){
					aElem.setAttribute(key,region[key]);
					if(key == _this.opts.showFieldName.toLowerCase()) aElem.innerHTML=region[key];
				}

				spanValue.appendChild(aElem);
				aElem.onclick = __selectedRegionClick;
			}


			var regionId = "";
			var aTarget = document.getElementById(_this.targetId);
			// console.info(aTarget==_this.targetObj);
			if(_this.selectedAreaList.length>0){
				_this.targetObj.value = spanValue.innerText;
				regionId = _this.selectedAreaList[_this.selectedAreaList.length-1][_this.opts.idFieldName.toLowerCase()];
				spanValue.previousElementSibling.innerHTML="请清除";
				var tempRegionDict = _this.selectedAreaList[_this.selectedAreaList.length-1];
				for(var key in tempRegionDict){
					if("class"!=key){
						_this.targetObj.setAttribute(key,tempRegionDict[key]);
					}

				}
			}
		};

		/*
		 * 在控件头部分对已选区域单击时，
		 *
		 * @version <1> 2017-11-06 Hayden:Created.
		 */
		var __selectedRegionClick = function(event){
			//获得选择的区域信息
			var evtTarget = event.target || event.srcElement;
			var regionName = evtTarget.innerHTML;
			var regionId = evtTarget.getAttribute(_this.opts.idFieldName);
			//点哪一级区域，就把这级区域的下一级删除掉。并获取选择区域的下一级列表
			for(var i in _this.selectedAreaList){
				if(_this.selectedAreaList[i][_this.opts.idFieldName.toLowerCase()]==regionId){
					//i=2 length=3  --- 2+1==3?2:3 ,3-2-1
					//i=0 length=3  --- 0+1==3?0:0 ,3-0-1
					i = parseInt(i);
					var delPos = (i+1)==_this.selectedAreaList.length?i:eval(i+1);
					var delNum = _this.selectedAreaList.length-i-1;
					_this.selectedAreaList.splice(delPos,delNum);
					break;
				}
			}

			//获取当前选择区域的下一级区域列表，并重构控件的区域UI
			__loadRegion(regionId);

		};

		/**
		 * 默认构造数据的方法,将使用ajax方式去获取服务数据。
		 * @version <1> 2017-11-03 Hayden:Create.
		 **/
		var isLoadDataFlag = true;
		var __loadRegion = function(parentRegionId){

			if(!isLoadDataFlag) return;
			isLoadDataFlag = false;
			var ajax = new BaseAjax();
			ajax.opts.url = _this.opts.url;
			ajax.opts.type = "POST";
			// ajax.opts.data = {parentId:!parentRegionId?null:parentRegionId};
			ajax.opts.data = JSON.stringify({parentId:parentRegionId});
			ajax.opts.async = false;
			ajax.opts.successFun=function(msg){
				_this.opts.areaList = msg.data;
				__buildRegionUI();
				__buildSelectedRegionUI();
				if(!_this.opts.areaList || _this.opts.areaList.length<=0) {
					if(divArea.style.display!="none") _this.close();
				}
				isLoadDataFlag = true;
			};
			ajax.opts.errorFun = function(msg){
				_this.opts.areaList = [];
				__buildRegionUI();
				__buildSelectedRegionUI();
				if(!_this.opts.areaList || _this.opts.areaList.length<=0) {
					if(divArea.style.display!="none") _this.close();
				}
				isLoadDataFlag = true;
			};
			ajax.run();

		};



		/**
		 * 显示区域选择器控件
		 * @version <1> 2017-11-04 Hayden : Created.
		 */
		_this.show = function(){
			var divArea = document.getElementById(_this.divAreaId);
			if(divArea){
				var parentElement = divArea.parentNode;
				if(parentElement){
					parentElement.removeChild(divArea);
				}
			}

			if(__init()){
				__buildDefaultStyle();
				__buildMainUi();
				__loadRegion(defaultRegion); //默认显示中国区域下的
			}

		};

		/**
		 * 控件关闭
		 * @version <1> 2017-11-09 Hayden:Created.
		 */
		this.close = function(){
			var divArea = document.getElementById(_this.divAreaId);
			divArea.style.display="none";
			if(_this.opts.closeFun&& typeof _this.opts.closeFun =="function" ){
				_this.opts.closeFun();
			}
			// var defaultRegion = _this.opts.areaList[0];
			// if(defaultRegion){
			// 	_this.targetObj.value = defaultRegion.chinaName;
			// 	_this.targetObj.setAttribute("regionid", defaultRegion.regionId);
			// 	_this.targetObj.setAttribute("regioncode", defaultRegion.regionCode);
			// 	_this.targetObj.setAttribute("regionname", defaultRegion.regionName);
			// 	_this.targetObj.setAttribute("chinaname", defaultRegion.chinaName);
			// }
		};

		/**
		 * 弹框控件关闭
		 * @version <1> 2018-06-20 cxw:Created.
		 */
		_this.closeRegion = function(){
			var divArea = document.getElementById(_this.divAreaId);
			if(divArea!=null)
				divArea.style.display="none";
		};

	}
	//暴露区域选择器控件
	return {
		RegionSelector : RegionSelector
	};
});



// var areaList = [
// 	{regionName:"中国",regionId:3100000000,level:1},
// 	{regionName:"美国",regionId:3200000000,level:1},
// 	{regionName:"加拿大",regionId:3300000000,level:1},
// 	{regionName:"印度",regionId:3400000000,level:1},
// 	{regionName:"日本",regionId:3500000000,level:1},
// 	{regionName:"俄罗期",regionId:3600000000,level:1},
// 	{regionName:"马来西亚",regionId:3700000000,level:1}
// ];
// var areaListNew = [];
// if(!parentRegionId) areaListNew = areaList;

// if(parentRegionId==3200000000){
// 	areaListNew = [
// 		{regionName:"湖北省",regionId:3102000001,level:2},
// 		{regionName:"湖南省",regionId:3102000002,level:2},
// 		{regionName:"广东省",regionId:3102000002,level:2},
// 		{regionName:"广西省",regionId:3102000002,level:2},
// 		{regionName:"山西省",regionId:3102000002,level:2},
// 		{regionName:"山东省",regionId:3102000002,level:2},
// 		{regionName:"吉林省",regionId:3102000002,level:2}
// 	];
// }

// if(parentRegionId == 3102000001){
// 	areaListNew = [
// 		{regionName:"荆州市",regionId:3103000001,level:3},
// 		{regionName:"武汉市",regionId:3103000002,level:3},
// 		{regionName:"宜昌市",regionId:3103000002,level:3},
// 		{regionName:"黄石市",regionId:3103000002,level:3},
// 		{regionName:"恩施市",regionId:3103000002,level:3},
// 		{regionName:"黄冈市",regionId:3103000002,level:3},
// 		{regionName:"十堰市",regionId:3103000002,level:3}
// 	];
// }


// var __loadRegionDefault = function(parentRegionId){

// 			if(!isLoadDataFlag) return;
// 			if(parentRegionId){
// 				isLoadDataFlag = false;
// 				var ajax = new BaseAjax();
// 				ajax.opts.url = _this.opts.url+"/"+parentRegionId;
// 				// ajax.opts.data = {parentRegionId:!parentRegionId?null:parentRegionId};
// 				ajax.opts.async = false;
// 				ajax.opts.successFun=function(msg){
// 					_this.opts.areaList = msg.data;
// 					__buildRegionUI();
// 					__buildSelectedRegionUI();
// 					if(!_this.opts.areaList || _this.opts.areaList.length<=0) {
// 						if(divArea.style.display!="none") _this.close();
// 					}
// 					isLoadDataFlag = true;
// 				};
// 				ajax.opts.errorFun = function(msg){
// 					_this.opts.areaList = [];
// 					__buildRegionUI();
// 					__buildSelectedRegionUI();
// 					if(!_this.opts.areaList || _this.opts.areaList.length<=0) {
// 						if(divArea.style.display!="none") _this.close();
// 					}
// 					isLoadDataFlag = true;
// 				};
// 				ajax.run();
// 			}else{
// 				_this.opts.areaList = [
// 					{regionName:"阿根廷",regionId:500000000,regionCode:"ARG",level:1},
// 					{regionName:"澳大利亚",regionId:900000000,regionCode:"AUS",level:1},
// 					{regionName:"孟加拉国",regionId:1600000000,regionCode:"BGD",level:1},
// 					{regionName:"巴西",regionId:2300000000,regionCode:"BRA",level:1},
// 					{regionName:"加拿大",regionId:2800000000,regionCode:"CAN",level:1},
// 					{regionName:"中国",regionId:3100000000,regionCode:"CHN",level:1},
// 					{regionName:"印尼",regionId:7300000000,regionCode:"IDN",level:1},
// 					{regionName:"印度",regionId:7400000000,regionCode:"IND",level:1},
// 					{regionName:"日本",regionId:8300000000,regionCode:"JPN",level:1},
// 					{regionName:"墨西哥",regionId:10300000000,regionCode:"MEX",level:1},
// 					{regionName:"缅甸",regionId:10600000000,regionCode:"MMR",level:1},
// 					{regionName:"马来西亚",regionId:11200000000,regionCode:"MYS",level:1},
// 					{regionName:"巴拉圭",regionId:13200000000,regionCode:"PRY",level:1},
// 					{regionName:"俄罗斯",regionId:13600000000,regionCode:"RUS",level:1},
// 					{regionName:"泰国",regionId:15700000000,regionCode:"THA",level:1},
// 					{regionName:"美国",regionId:16900000000,regionCode:"USA",level:1}
// 				];
// 				__buildRegionUI();
// 				__buildSelectedRegionUI();
// 			}

// 		};