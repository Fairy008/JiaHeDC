function getAllWebId() {
	var wvs = plus.webview.all();
	var launch = plus.webview.getLaunchWebview(); //首页窗口对象   
	for (var i = 0, len = wvs.length; i < len; i++) {
		console.log(wvs[i].id);
	}
};

//判断当前网络状态
function getNetState() {
	var nt = plus.networkinfo.getCurrentType();
	switch (nt) {
		case plus.networkinfo.CONNECTION_ETHERNET:
		case plus.networkinfo.CONNECTION_WIFI:
			return 'wifi'
			break;
		case plus.networkinfo.CONNECTION_CELL2G:
		case plus.networkinfo.CONNECTION_CELL3G:
		case plus.networkinfo.CONNECTION_CELL4G:
			return 'mobile network'
			break;
		default:
			return 'none'
			break;
	}
	return null;
}

//多次尝试
function tryTimesUntils(trytimes, isFinshed, funcPre, funcFuilure) {
	var times = 0
	var finshed = null;
	// console.log(isFinshed)
	while ((finshed == null) && (times < trytimes)) {
		if (finshed != isFinshed) {
			finshed = isFinshed
		}
		times = times + 1;
		// console.log(times*1000)
		setTimeout(function() {
			funcPre()
		}, times * 1000)
	}
	// console.log(trytimes)
	// console.log(times)
	if (times == (trytimes - 1) && (finshed == null)) {
		funcFuilure()
	}
}
//获取中心点
function getCenterPoint(path) {
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

	//return new BMap.Point(path[0].lng,path[0].lat);
	return [x, y];
	//return path[0];
}

function mToastSet(toastStr) {
	// plus.nativeUI.toast(toastStr, {
	// 	// background: "rgba(0,0,0,0.1)",
	// 	duration: "short", // 持续3.5s，short---2s
	// 	align: "center", // 水平居中
	// 	verticalAlign: "center", // 垂直底部
	// });
	plus.nativeUI.closeToast();
	plus.nativeUI.toast(
		"<font style=\"font-size:14px\">" + toastStr + "</font>", {
			type: 'richtext',
			duration: 'long',
			richTextStyle: {
				align: 'center'
			},
			verticalAlign: "center", // 垂直底部
		});
}

// 判断是否为手机号
function isPoneAvailable(pone) {
	var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
	if (!myreg.test(pone)) {
		return false;
	} else {
		return true;
	}
};

//判断是否登录
function isLogined(Yback, Nback) {
	var userBean = localStorage.getItem('userBean');
	var userState = JSON.parse(localStorage.getItem('userState'))
	// console.log("isLogined是否登录："+(userBean != null))
	// console.log(JSON.stringify(userState))
	if (userBean != null && (userState.isLogin == true)) {
		Yback();
	} else {
		Nback();
	}
}

//获取页面所有web对象
function outputWebObj(mplus) {
	var wvs = mplus.webview.all();
	// console.log('webview'+i+': '+wvs[i].getLength());
	for (var i = 0; i < wvs.length; i++) {
		console.log('webview' + i + ': ' + wvs[i].id);
	}
}

//获取时间
function getdate() {
	var now = new Date(),
		y = now.getFullYear(),
		m = now.getMonth() + 1,
		d = now.getDate();
	return y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d) + " " + now.toTimeString().substr(0, 5);
}

//添加水印
function addWaterMark(src, callback) {
	var i = new Image();
	i.src = src;
	i.onload = function() {
		var canvas = document.createElement('canvas');
		var context = canvas.getContext('2d');
		context.drawImage(i, 0, 0, i.width, i.height);
		var font = "16px microsoft yahei";
		var style = "rgba(255,255,255,0.9)";
		var text = "武汉珈和科技有限公司";
		context.fillStyle = style;
		callback(canvas);
		// context.fillText(text, 140, 10);
	}

}

/*
返回值
var target = event.target; // 压缩转换后的图片url路径，以"file://"开头
	var size = event.size; // 压缩转换后图片的大小，单位为字节（Byte）
	var width = event.width; // 压缩转换后图片的实际宽度，单位为px
	var height = event.height; // 压缩转换后图片的实际高度，单位为px
*/
function compressImage(msrc, dsrc, mwidth, mheight, mquality, callback) {
	plus.nativeUI.showWaiting();
	plus.zip.compressImage({
			src: msrc, //目标地址
			dst: dsrc, //存储地址
			quality: mquality, //质量指数1～100
			format: 'jpg', //格式
			overwrite: true, //是否覆盖
			width: mwidth, //宽 '100px' '80%'
			height: mheight, //长
		},
		function(event) {
			plus.nativeUI.closeWaiting();
			console.log("压缩图片成功：" + JSON.stringify(event));
			callback(event);
		},
		function(e) {
			plus.nativeUI.closeWaiting();
			console.log("压缩图片失败: " + JSON.stringify(e));
		});
};

//检查登录状态
function confrimLogin(mplus) {
	var myUserDtate = JSON.parse(localStorage.getItem('userState'));
	// console.log(JSON.stringify(myUserDtate));
	if (myUserDtate.isLogin == true) {
		return true
		// mplus.nativeUI.toast('未登录，请先登录！',{'verticalAlign': 'center'});
	} else {
		return false
	}
};

function setMuiFire($, mplus, eventStr) {
	var taskBasePage = mplus.webview.getWebviewById('html/collect/collect-no-task.html');
	var collectPage = mplus.webview.getWebviewById('html/task/task-base.html');
	var mindPage = mplus.webview.getWebviewById('html/mind/mind.html');
	var setting = mplus.webview.getWebviewById('setting.html');
	// console.log(' taskBasePage: '+taskBasePage.id+',collectPage:'+collectPage.id);
	$.fire(taskBasePage, eventStr);
	$.fire(collectPage, eventStr);
	$.fire(mindPage, eventStr);
	$.fire(setting, eventStr);
};

var JsonObj = {
	local2Json: function(str, data) {
		localStorage.setItem(str, JSON.stringify(data));
		if (JSON.parse(localStorage.getItem(str))) {
			return true;
		} else {
			return false;
		}
	},
	json2Local: function(str) {
		var a = localStorage.getItem(str);
		if (a) {
			return JSON.parse(a);
		} else {
			return null;
		}
	},
	isObj: function(object) {
		return object && typeof(object) == 'object' && Object.prototype.toString.call(object).toLowerCase() ==
			"[object object]";
	},

	isArray: function(object) {
		return object && typeof(object) == 'object' && object.constructor == Array;
	},

	getLength: function(object) {
		var count = 0;
		for (var i in object) count++;
		return count;
	},
	Compare: function(objA, objB) {
		if (!JsonObj.isObj(objA) || !JsonObj.isObj(objB)) return false; //判断类型是否正确
		if (JsonObj.getLength(objA) != JsonObj.getLength(objB)) return false; //判断长度是否一致
		return JsonObj.CompareObj(objA, objB, true); //默认为true
	},

	CompareObj: function(objA, objB, flag) {
		for (var key in objA) {
			if (!flag) //跳出整个循环
				break;
			if (!objB.hasOwnProperty(key)) {
				flag = false;
				break;
			}
			if (!JsonObj.isArray(objA[key])) { //子级不是数组时,比较属性值
				if (objB[key] != objA[key]) {
					flag = false;
					break;
				}
			} else {
				if (!JsonObj.isArray(objB[key])) {
					flag = false;
					break;
				}
				var oA = objA[key],
					oB = objB[key];
				if (oA.length != oB.length) {
					flag = false;
					break;
				}
				for (var k in oA) {
					if (!flag) //这里跳出循环是为了不让递归继续
						break;
					flag = JsonObj.CompareObj(oA[k], oB[k], flag);
				}
			}
		}
		return flag;
	},

}
// 
// var taskDicPreData = {
// 	code = "<null>";
// 	data = ({
// 		createTime = "<null>";
// 		creator = "<null>";
// 		creatorName = "<null>";
// 		dataCode = trmm;
// 		dataName = "降雨";
// 		dataStatus = "<null>";
// 		dataType = 2;
// 		dataValue = 5;
// 		delFlag = "<null>";
// 		dictId = 1805;
// 		leaf = 1;
// 		modifier = "<null>";
// 		modifierName = "<null>";
// 		modifyTime = "<null>";
// 		orderNo = 1;
// 		page = 0;
// 		parentId = 1800;
// 		parentName = "<null>";
// 		remark = "<null>";
// 		rows = 0;
// 		sidx = "<null>";
// 		sord = "<null>";
// 		userFlag = 0;
// 	}, {
// 		createTime = "<null>";
// 		creator = "<null>";
// 		creatorName = "<null>";
// 		dataCode = t;
// 		dataName = "地温";
// 		dataStatus = "<null>";
// 		dataType = 2;
// 		dataValue = 4;
// 		delFlag = "<null>";
// 		dictId = 1804;
// 		leaf = 1;
// 		modifier = "<null>";
// 		modifierName = "<null>";
// 		modifyTime = "<null>";
// 		orderNo = 2;
// 		page = 0;
// 		parentId = 1800;
// 		parentName = "<null>";
// 		remark = "<null>";
// 		rows = 0;
// 		sidx = "<null>";
// 		sord = "<null>";
// 		userFlag = 0;
// 	}, {
// 		createTime = "<null>";
// 		creator = "<null>";
// 		creatorName = "<null>";
// 		dataCode = yield;
// 		dataName = "估产";
// 		dataStatus = "<null>";
// 		dataType = 2;
// 		dataValue = 2;
// 		delFlag = "<null>";
// 		dictId = 1802;
// 		leaf = 1;
// 		modifier = "<null>";
// 		modifierName = "<null>";
// 		modifyTime = "<null>";
// 		orderNo = 3;
// 		page = 0;
// 		parentId = 1800;
// 		parentName = "<null>";
// 		remark = "<null>";
// 		rows = 0;
// 		sidx = "<null>";
// 		sord = "<null>";
// 		userFlag = 0;
// 	}, {
// 		createTime = "<null>";
// 		creator = "<null>";
// 		creatorName = "<null>";
// 		dataCode = distribution;
// 		dataName = "分布";
// 		dataStatus = "<null>";
// 		dataType = 2;
// 		dataValue = 1;
// 		delFlag = "<null>";
// 		dictId = 1801;
// 		leaf = 1;
// 		modifier = "<null>";
// 		modifierName = "<null>";
// 		modifyTime = "<null>";
// 		orderNo = 3;
// 		page = 0;
// 		parentId = 1800;
// 		parentName = "<null>";
// 		remark = "<null>";
// 		rows = 0;
// 		sidx = "<null>";
// 		sord = "<null>";
// 		userFlag = 0;
// 	}, {
// 		createTime = "<null>";
// 		creator = "<null>";
// 		creatorName = "<null>";
// 		dataCode = growth;
// 		dataName = "长势";
// 		dataStatus = "<null>";
// 		dataType = 2;
// 		dataValue = 3;
// 		delFlag = "<null>";
// 		dictId = 1803;
// 		leaf = 1;
// 		modifier = "<null>";
// 		modifierName = "<null>";
// 		modifyTime = "<null>";
// 		orderNo = 4;
// 		page = 0;
// 		parentId = 1800;
// 		parentName = "<null>";
// 		remark = "<null>";
// 		rows = 0;
// 		sidx = "<null>";
// 		sord = "<null>";
// 		userFlag = 0;
// 	}, {
// 		createTime = "<null>";
// 		creator = "<null>";
// 		creatorName = "<null>";
// 		dataCode = disaster;
// 		dataName = "灾害";
// 		dataStatus = "<null>";
// 		dataType = 1;
// 		dataValue = 7;
// 		delFlag = "<null>";
// 		dictId = 1807;
// 		leaf = 1;
// 		modifier = "<null>";
// 		modifierName = "<null>";
// 		modifyTime = "<null>";
// 		orderNo = 8;
// 		page = 0;
// 		parentId = 1800;
// 		parentName = "<null>";
// 		remark = "<null>";
// 		rows = 0;
// 		sidx = "<null>";
// 		sord = "<null>";
// 		userFlag = 0;
// 	}];
// 	flag = 1;
// 	msg = "<null>";
// }
// 
// var cropDicPreData = {
// 	"flag": true,
// 	"code": null,
// 	"msg": null,
// 	"data": [{
// 			"rows": 0,
// 			"page": 0,
// 			"userFlag": false,
// 			"sidx": null,
// 			"sord": null,
// 			"dataStatus": null,
// 			"delFlag": null,
// 			"createTime": null,
// 			"creatorName": null,
// 			"creator": null,
// 			"modifyTime": null,
// 			"modifierName": null,
// 			"modifier": null,
// 			"remark": null,
// 			"dictId": 1805,
// 			"dataName": "降雨",
// 			"dataCode": "trmm",
// 			"dataValue": "5",
// 			"orderNo": 1,
// 			"leaf": true,
// 			"parentId": 1800,
// 			"dataType": "2",
// 			"parentName": null
// 		},
// 		{
// 			"rows": 0,
// 			"page": 0,
// 			"userFlag": false,
// 			"sidx": null,
// 			"sord": null,
// 			"dataStatus": null,
// 			"delFlag": null,
// 			"createTime": null,
// 			"creatorName": null,
// 			"creator": null,
// 			"modifyTime": null,
// 			"modifierName": null,
// 			"modifier": null,
// 			"remark": null,
// 			"dictId": 1804,
// 			"dataName": "地温",
// 			"dataCode": "t",
// 			"dataValue": "4",
// 			"orderNo": 2,
// 			"leaf": true,
// 			"parentId": 1800,
// 			"dataType": "2",
// 			"parentName": null
// 		},
// 		{
// 			"rows": 0,
// 			"page": 0,
// 			"userFlag": false,
// 			"sidx": null,
// 			"sord": null,
// 			"dataStatus": null,
// 			"delFlag": null,
// 			"createTime": null,
// 			"creatorName": null,
// 			"creator": null,
// 			"modifyTime": null,
// 			"modifierName": null,
// 			"modifier": null,
// 			"remark": null,
// 			"dictId": 1802,
// 			"dataName": "估产",
// 			"dataCode": "yield",
// 			"dataValue": "2",
// 			"orderNo": 3,
// 			"leaf": true,
// 			"parentId": 1800,
// 			"dataType": "2",
// 			"parentName": null
// 		},
// 		{
// 			"rows": 0,
// 			"page": 0,
// 			"userFlag": false,
// 			"sidx": null,
// 			"sord": null,
// 			"dataStatus": null,
// 			"delFlag": null,
// 			"createTime": null,
// 			"creatorName": null,
// 			"creator": null,
// 			"modifyTime": null,
// 			"modifierName": null,
// 			"modifier": null,
// 			"remark": null,
// 			"dictId": 1801,
// 			"dataName": "分布",
// 			"dataCode": "distribution",
// 			"dataValue": "1",
// 			"orderNo": 3,
// 			"leaf": true,
// 			"parentId": 1800,
// 			"dataType": "2",
// 			"parentName": null
// 		},
// 		{
// 			"rows": 0,
// 			"page": 0,
// 			"userFlag": false,
// 			"sidx": null,
// 			"sord": null,
// 			"dataStatus": null,
// 			"delFlag": null,
// 			"createTime": null,
// 			"creatorName": null,
// 			"creator": null,
// 			"modifyTime": null,
// 			"modifierName": null,
// 			"modifier": null,
// 			"remark": null,
// 			"dictId": 1803,
// 			"dataName": "长势",
// 			"dataCode": "growth",
// 			"dataValue": "3",
// 			"orderNo": 4,
// 			"leaf": true,
// 			"parentId": 1800,
// 			"dataType": "2",
// 			"parentName": null
// 		},
// 		{
// 			"rows": 0,
// 			"page": 0,
// 			"userFlag": false,
// 			"sidx": null,
// 			"sord": null,
// 			"dataStatus": null,
// 			"delFlag": null,
// 			"createTime": null,
// 			"creatorName": null,
// 			"creator": null,
// 			"modifyTime": null,
// 			"modifierName": null,
// 			"modifier": null,
// 			"remark": null,
// 			"dictId": 1807,
// 			"dataName": "灾害",
// 			"dataCode": "disaster",
// 			"dataValue": "7",
// 			"orderNo": 8,
// 			"leaf": true,
// 			"parentId": 1800,
// 			"dataType": "1",
// 			"parentName": null
// 		}
// 	]
// }
//
