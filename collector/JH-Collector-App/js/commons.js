var commons = {
	quitFun: function() {

	},
	encode: function(input) {
		var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
		var output = "";
		var chr1, chr2, chr3 = "";
		var enc1, enc2, enc3, enc4 = "";
		var i = 0;
		do {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);
			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;
			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}
			output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2) +
				keyStr.charAt(enc3) + keyStr.charAt(enc4);
			chr1 = chr2 = chr3 = "";
			enc1 = enc2 = enc3 = enc4 = "";
		} while (i < input.length);

		return output;
	},
	getToken: function() {
		var mUserBean = JSON.parse(localStorage.getItem("userBean"));
		// console.log(localStorage.getItem('userState')); 
		// console.log(JSON.stringify(mUserBean)); 
		if (mUserBean && mUserBean.data.userInfo.accountName && mUserBean.data.userInfo.serviceKey) {
			//获取到数据不为空
			var serviceKey = mUserBean.data.userInfo.serviceKey;
			var phone = mUserBean.data.userInfo.accountName;
			var token = "?serviceKey=" + serviceKey + "&phone=" + phone;
			// console.log(token);
			return token;
		};
		return false;
	},
	//尝试登录直到次数用尽
	isUserLogin: function(trytimes, timeout, funcPre, funcSuccess, funcFuilure) {
		//先判断本地存储有用户信息
		var loginInfo = JSON.parse(localStorage.getItem("userBean"));

		if (loginInfo) {
			// console.log('true' + loginInfo.data.userInfo.accountId);
			funcSuccess();
		} else {
			funcPre();
			trytimes -= 1;
			if (trytimes > 0) {
				commons.isUserLogin(trytimes, timeout, funcPre, funcSuccess, funcFuilure);
			} else {
				funcFuilure();
			}
		}
	},
	//请求登录 存储本地
	askLogin: function(mmui, url, accountNo, pwd, callback) {
		var str = '?accountNo=' + accountNo + '&pwd=' + pwd;
		console.log(url + str)
		mui.ajax(url + str, {
			data: {},
			dataType: 'json',
			type: 'post',
			timeout: 10000,
			headers: {
				'Content-Type': 'application/json'
			},
			success: function(result) {
				console.log(JSON.stringify(result))
				//转换成字符串才能保存到localStorage
				if (result.flag == true) {
					result = JSON.stringify(result);
					// console.log(result);
					localStorage.setItem('userBean', result);
					callback(result);
					// console.log('存储：' + localStorage.getItem('userBean'));
				} else {
					plus.nativeUI.toast(result.msg, {
						'verticalAlign': 'center'
					});
					console.log(JSON.stringify(result));
				}
			},
			error: function(xhr, ertype, errorThrown) { //异常处理；
				plus.nativeUI.toast('登陆失败，请检查网络环境', {
					'verticalAlign': 'center'
				});
				console.log(JSON.stringify(xhr) + '  ' + ertype + ' ' + errorThrown);
			}
		});
	},
	//获取字典（存储本地）
	askDicByPId: function(mmui, url, parentid, callback) {
		mmui.ajax(url, {
			data: {
				"parentId": parentid
			}, //500是作物，1800是数据集
			dataType: 'json',
			type: 'post',
			timeout: 3000,
			headers: {
				'Content-Type': 'application/json'
			},
			success: function(result) {
				//转换成字符串才能保存到localStorage
				result = JSON.stringify(result);
				if (parentid == 500) {
					localStorage.setItem('cropDicBean', result);
					console.log('存储作物字典到本地');
				}
				if (parentid == 1800) {
					localStorage.setItem('taskDicBean', result);
					console.log('存储数据集字典到本地');
				}
				callback(result);
			},
			error: function(xhr, ertype, errorThrown) { //异常处理；
				console.log(JSON.stringify(xhr) + '  ' + ertype + ' ' + errorThrown);
				console.log('请求' + parentid + '字典相关接口失败');
			}
		});
	},
	//根据父id查询区域列表
	askRegionListByPId: function(mmui, url, parentid, callback) {

	},
	//获取模版 （分页 不存储到本地）
	askTemplate: function(mmui, url, mTokenStr, bodyData, callback) {
		mmui.ajax(url + mTokenStr, {
			data: bodyData,
			dataType: 'json',
			type: 'post',
			timeout: 10000,
			headers: {
				'Content-Type': 'application/json'
			},
			success: function(result) {
				//转换成字符串才能保存到localStorage
				result = JSON.stringify(result);
				console.log('存储模版到本地');

				callback(result);
			},
			error: function(xhr, ertype, errorThrown) { //异常处理；
				console.log(JSON.stringify(xhr) + '  ' + ertype + ' ' + errorThrown);
				console.log('请求模版相关接口失败');
			}
		});
	},
	//请求任务列表(无分页并存储到本地)
	askTaskListAll: function(mmui, mplus, url, mTokenStr, callback, errBack) {
		// console.log(url)
		// console.log(mTokenStr)
		mmui.ajax(url + mTokenStr, {
			data: {},
			dataType: 'json',
			type: 'post',
			timeout: 10000,
			headers: {
				'Content-Type': 'application/json'
			},
			success: function(result) {
				//若本地列表没有对应的存储采集点的位置，初始化
				// console.log(JSON.stringify(result.list[0].cltTemplate));
				for (var i in result.list) {
					var strKey = lstorage.getItemListKey(result.list[i].taskId);
					if (mplus.storage.getItem(strKey) == null) {
						mplus.storage.setItem(strKey, '{"data":[]}');
						console.log(strKey + ':' + mplus.storage.getItem(strKey));
					}
				}

				//转换成字符串才能保存到localStorage
				result = JSON.stringify(result);

				localStorage.setItem(lstorage.getBeanKey('taskListBean'), result);
				//尝试更新采集页面中进行中的任务列表

				mui.fire(mplus.webview.getWebviewById('html/collect/collect-no-task.html'), 'updataTaskList');
				console.log('存储任务列表到本地');
				callback(result);
			},
			error: function(xhr, ertype, errorThrown) { //异常处理；
				console.log(JSON.stringify(xhr) + '，ertype：' + ertype + '，errorThrown： ' + errorThrown);
				console.log('请求任务列表接口失败');
				errBack();
			}
		});
	},
	//根据任务id查询采集点数据列表
	askFindTaskItemByTaskId: function(mmui, url, mTokenStr, dataJson, callback, errBack) {
		mmui.ajax(url + mTokenStr, {
			data: dataJson,
			dataType: 'json',
			type: 'post',
			timeout: 10000,
			headers: {
				'Content-Type': 'application/json'
			},
			success: function(result) {
				//将返回所有采集点列表添加同步标签
				if (result.data.length > 0) {
					// console.log(result.data.length);
					for (var n in result.data) {
						result.data[n].isUpData = true;
						// console.log(result.data[n].taskId+'____'+JSON.stringify(result.data[n]));
					}
				}
				var localSave = plus.storage.getItem(lstorage.getItemListKey(dataJson.taskId));
				localSave = JSON.parse(localSave)
				var compareA = localSave.data
				var compareB = result.data
				var result = compareA
				// console.log("compareA : "+JSON.stringify(compareA))
				// console.log("compareB : "+JSON.stringify(compareB))


				if (JSON.stringify(compareA) != JSON.stringify(compareB)) {
					//开始比对

					for (var j in compareA) {
						for (var i in compareB) {
							if (compareA[j].itemId == compareB[i].itemId) {
								//采集点本地显示已同步，服务端显示已同步
								//本地无该项数据
							}

						}
					}

					//比对完成调整顺序

					// 			if (compareA[j].itemId == compareB[i].itemId) {
					// 				//匹配采集点
					// 				if (JSON.stringify(compareA[j]) != JSON.stringify(compareB[i])) {
					// 					//内容不一致
					// 					if (compareA.isUpData == true) {
					// 						//已同步，以服务器端数据为准
					// 					} else if (compareA.isUpData == false) {
					// 						//未同步，以本地数据为准
					// 						compareB[i] = compareA[j];
					// 					}
					// 				} else {
					// 					//内容一致
					// 				}
					// 			}else if(compareA[j].itemId==null){
					// 				compareB.
					// 			}
					// 		}
					// 	}
				}

				// plus.storage.setItem(lstorage.getItemListKey(dataJson.taskId), result);
				// console.log(lstorage.getItemListKey(dataJson.taskId) + '---存储采集点数据列表到本地');
				callback(result);

			},
			error: function(xhr, ertype, errorThrown) { //异常处理；
				console.log(JSON.stringify(xhr) + '  ' + ertype + ' ' + errorThrown);
				console.log(lstorage.getItemListKey(dataJson.taskId) + '根据任务id查询采集点数据列表');
				errBack();
			},
		});
	},
	//请求任务列表(带分页 不存储)
	askTaskList: function(mmui, url, mTokenStr, taskStatus, page, rows, callback, errBack) {
		var mData;
		if (taskStatus == "All") {
			mData = {
				"page": page,
				"rows": rows,
			};
		} else {
			mData = {
				"page": page,
				"rows": rows,
				"taskStatus": taskStatus,
			};
		}
		mmui.ajax(url + mTokenStr, {
			data: mData,
			dataType: 'json',
			type: 'post',
			timeout: 10000,
			headers: {
				'Content-Type': 'application/json'
			},
			success: function(result) {
				//转换成字符串才能保存到localStorage
				//result = JSON.stringify(result);
				//console.log('存储任务列表到本地');
				callback(result);
			},
			error: function(xhr, ertype, errorThrown) { //异常处理；
				console.log(JSON.stringify(xhr) + '  ' + ertype + ' ' + errorThrown);
				console.log('请求任务列表接口失败');
				errBack();
			}
		});
	},
	//请求新增模版
	askaddTemplate: function(mmui, url, mTokenStr, mData, callback, errback) {
		// console.log('123');
		mmui.ajax(url + mTokenStr, {
			data: mData,
			dataType: 'json',
			type: 'post',
			timeout: 10000,
			headers: {
				'Content-Type': 'application/json'
			},
			success: function(result) {
				//转换成字符串才能保存到localStorage
				result = JSON.stringify(result);
				console.log(result);
				callback(result);
			},
			error: function(xhr, ertype, errorThrown) { //异常处理；
				console.log(JSON.stringify(xhr) + '  ' + ertype + ' ' + errorThrown);
				console.log('请求模版相关接口失败');
				errback();
			}
		});
	},
	//请求删除模版
	askDeleteCltTemplate: function(mmui, url, mTokenStr, mTemplateId, callback, errback) {
		mmui.ajax(url + mTokenStr, {
			data: {
				"templateId": mTemplateId,
			},
			dataType: 'json',
			type: 'post',
			timeout: 10000,
			headers: {
				'Content-Type': 'application/json'
			},
			success: function(result) {
				//转换成字符串才能保存到localStorage
				result = JSON.stringify(result);
				callback(result);
			},
			error: function(xhr, ertype, errorThrown) { //异常处理；
				console.log(JSON.stringify(xhr) + '  ' + ertype + ' ' + errorThrown);
				console.log('请求模版相关接口失败');
				errback();
			}
		});
	},
	//请求新增采集点
	askTaskItem: function(mmui, url, mTokenStr, mTaskId, bodyData, callback, errback) {
		// console.log(JSON.stringify(bodyData));
		mmui.ajax(url + mTokenStr, {
			data: bodyData,
			dataType: 'json',
			type: 'post',
			timeout: 10000,
			headers: {
				'Content-Type': 'application/json'
			},
			success: function(result) {
				//转换成字符串才能保存到localStorage
				result = JSON.stringify(result);
				callback(result);
			},
			error: function(xhr, ertype, errorThrown) { //异常处理；
				console.log(JSON.stringify(xhr) + '  ' + ertype + ' ' + errorThrown);
				console.log('请求新增采集点接口失败');
				errback();
			},
		});
	},
	//同步位更新采集点
	updataMyItem: function(mmui, url, mTokenStr, mTaskId, bodyData, callback, errback) {
		// console.log(JSON.stringify(bodyData));
		var mdata = bodyData;
		mmui.ajax(url + mTokenStr, {
			data: bodyData,
			dataType: 'json',
			type: 'post',
			timeout: 10000,
			headers: {
				'Content-Type': 'application/json'
			},
			success: function(result) {
				//转换成字符串才能保存到localStorage
				mdata.itemId = result.data;
				mdata.isUpData = true;
				// console.log(JSON.stringify(result));
				// console.log(JSON.stringify(mdata));
				result = JSON.stringify(result);
				callback(result, mdata);
			},
			error: function(xhr, ertype, errorThrown) { //异常处理；
				console.log(JSON.stringify(xhr) + '  ' + ertype + ' ' + errorThrown);
				console.log('请求新增采集点接口失败');
				errback();
			},
		});
	},
	//获取数据集字典（网络）
	getTaskDic: function(mmui, url, callback) {
		// if(localStorage.getItem('taskDicBean')){
		//若数据集字典为空
		commons.askDicByPId(mmui, url, 1800, function(result) {
			var list = [];
			result = JSON.parse(result);
			for (var i = 0; i < result.data.length; i++) {
				var a = {
					value: result.data[i].dictId,
					text: result.data[i].dataName,
				}
				list.push(a);
				// console.log(JSON.stringify(a));
			}
			// console.log(mtaskDic);
			callback(list);
		});
		// }
	},
	//获取作物字典(网络)
	getCropDic: function(mmui, url, callback) {
		commons.askDicByPId(mmui, url, 500,
			function(result) {
				var list = [];
				result = JSON.parse(result);
				for (var i = 0; i < result.data.length; i++) {
					var a = {
						value: result.data[i].dictId,
						text: result.data[i].dataName,
					}
					list.push(a);
					// console.log(JSON.stringify(a));
				}
				// console.log(mtaskDic);
				callback(list);
			});
	},
	//获取模版原始列表（不带分页存储本地）
	getRTPList: function(mmui, url, mTokenStr, callback) {
		commons.askTemplate(mmui, url, mTokenStr, {},
			function(result) {
				result = JSON.parse(result);

				callback(JSON.stringify(result.list));
			})
	},
	//获取模版列表（不带分页存储本地）
	getTPList: function(mmui, url, mTokenStr, callback) {
		commons.askTemplate(mmui, url, mTokenStr, {},
			function(result) {
				localStorage.setItem(lstorage.getUserPhone() + '_templateBean', result);
				var list = [];
				// console.log('askTemplate'+result);
				result = JSON.parse(result);
				for (var i = 0; i < result.list.length; i++) {
					var a = {
						templateName: result.list[i].templateName,
						purpose: result.list[i].purpose,
						templateId: result.list[i].templateId,
						type: result.list[i].type,
						useFlag: result.list[i].useFlag,
					}
					list.push(a);
					// console.log(JSON.stringify(a));
				}
				callback(list);
			});
	},
	//列表返回数据转换为列表
	transTPlist: function(result) {
		var list = [];
		result = JSON.parse(result);
		for (var i = 0; i < result.list.length; i++) {
			var a = {
				templateName: result.list[i].templateName,
				purpose: result.list[i].purpose,
				templateId: result.list[i].templateId,
				type: result.list[i].type,
				useFlag: result.list[i].useFlag,
			}
			list.push(a);
		}
		return list;
	},
	//获取模版列表（带分页 不存储）
	getTPPage: function(mmui, url, mTokenStr, bodyData, callback) {
		commons.askTemplate(mmui, url, mTokenStr, bodyData,
			function(result) {
				var list = [];
				result = JSON.parse(result);
				for (var i = 0; i < result.list.length; i++) {
					var a = {
						templateName: result.list[i].templateName,
						purpose: result.list[i].purpose,
						templateId: result.list[i].templateId,
						type: result.list[i].type,
						useFlag: result.list[i].useFlag,
						templateVO: result.list[i].templateVO,
					}
					list.push(a);
					// console.log(JSON.stringify(a));
				}
				pageInfo = {
					hasNextPage: result.hasNextPage,
					nextPage: result.nextPage,
					pages: result.pages,
				}
				// console.log('getTPPage-list '+JSON.stringify(list))
				// console.log('getTPPage-pageInfo '+JSON.stringify(pageInfo))
				callback(list, pageInfo);
			});
	},
	// 获取url中的参数
	getUrlParam: function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) {
			return unescape(r[2]);
		} else {
			return null;
		}
	},
	// 根据accountId查询该用户person表信息
	findPersonInfo: function(accountId, callback) {
		var url = SYS_USER.nolog_getByAccountId_url + '?accountId=' + accountId;
		// url = "http://192.168.1.227:8002/nolog/user/getByAccountId?accountId=436"
		// url = 'http://192.168.1.227:8001/jh-sys/nolog/user/queryPersonByAccountId'
		// console.log(url)
		mui.ajax(url, {
			// data:{"accountId":436},
			dataType: 'json',
			type: 'get',
			timeout: 10000,
			headers: {
				'Content-Type': 'application/json'
			},
			success: function(result) {
				var person = result.data;
				callback(person);
			},
			error: function(xhr, type, errorThrown) {
				// console.log('查询作者详情失败'+JSON.stringify(xhr)+'  ， '+type+'  ， '+errorThrown)
			}
		});
	},
	//批量查询用户
	batchFindPerson: function(ids, callback) {
		var url = SYS_USER.nolog_batchFindPersonByAccountIdArr_url;
		mui.ajax(url, {
			data: {
				'ids': ids
			},
			dataType: 'json',
			type: 'get',
			timeout: 10000,
			success: function(result) {
				callback(result);
			},
			error: function(xhr, type, errorThrown) {
				console.log('批量查询用户信息失败'+xhr+'  ， '+type+'  ， '+errorThrown)
			}
		});
	},
	//将时间字符串转换为时间戳
	getDateTimeStamp: function(dateStr) {
		if (!dateStr || '' == dateStr) return;
		return Date.parse(dateStr.replace(/-/gi, "/"));
	},
	//格式化评论时间
	getDateDiff: function(dateTimeStamp) {
		var minute = 1000 * 60;
		var hour = minute * 60;
		var day = hour * 24;
		var halfamonth = day * 15;
		var month = day * 30;
		var now = new Date().getTime();
		var diffValue = now - dateTimeStamp;
		if (diffValue < 0) {
			return;
		}
		var monthC = diffValue / month;
		var weekC = diffValue / (7 * day);
		var dayC = diffValue / day;
		var hourC = diffValue / hour;
		var minC = diffValue / minute;
		if (monthC >= 1) {
			result = "" + parseInt(monthC) + "月前";
		} else if (weekC >= 1) {
			result = "" + parseInt(weekC) + "周前";
		} else if (dayC >= 1) {
			result = "" + parseInt(dayC) + "天前";
		} else if (hourC >= 1) {
			result = "" + parseInt(hourC) + "小时前";
		} else if (minC >= 1) {
			result = "" + parseInt(minC) + "分钟前";
		} else
			result = "刚刚";
		return result;
	},
	//加载各个评论人的头像
	initUserPhoto: function(userIdArr) {
		if (!userIdArr || userIdArr.length == 0) return;
		commons.batchFindPerson(userIdArr, function(result) {
			if (!result) return;
			for (var i = 0; i < userIdArr.length; i++) {
				for (var j = 0; j < result.data.length; j++) {
					if (result.data[j].accountId == userIdArr[i]) {
						if (null == result.data[j].photoUrl) continue;
						$('.' + userIdArr[i] + '').attr('src', img_prefix + result.data[j].photoUrl);
					}
				}
			}
		})
	},
	//弹框是否跳到登录页面
	jumpLogin: function(url) {
		mui.confirm('未登录, 是否登录', '提示', ['取消', '确认'], function(e) {
			if (e.index == 1) {
				mui.openWindow({
					id: 'login.html',
					url: url
				});
			}
		});
	},

};



var lstorage = {
	//判断用户是否登录
	isUserLogin: function() {
		var userState = localStorage.getItem('userState');
		userState = JSON.parse(userState);
		return userState.isLogin != null ? userState.isLogin : null;
		// if (userState.isLogin == true) {
		// 	return true;
		// } else if (userState.isLogin == false) {
		// 	return false;
		// } else {
		// 	return null;
		// }
	},
	//获取用户号码
	getUserPhone: function() {
		if (localStorage.getItem('userBean')) {
			var mUserBean = JSON.parse(localStorage.getItem("userBean"));
			var accountName = mUserBean.data.userInfo.accountName;
			return accountName;
		} else {
			return '';
		};
	},
	getBeanKey: function(str) {
		return lstorage.getUserPhone() + '_' + str;
	},
	getItemListKey: function(mTaskId) {
		return lstorage.getUserPhone() + '_taskItem_taskId_' + mTaskId;
	},
	//获取token
	getUserToken: function() {
		if (localStorage.getItem('userBean')) {
			var mUserBean = JSON.parse(localStorage.getItem("userBean"));
			var serviceKey = mUserBean.data.userInfo.serviceKey;
			var phone = mUserBean.data.userInfo.accountName;
			var token = "?serviceKey=" + serviceKey + "&phone=" + phone;
			// console.log(token);
			return token;
		} else {
			return '';
		};
	},
	//获取作物字典(本地)
	getCropDicLoC: function() {
		if (localStorage.getItem('cropDicBean')) {
			var result = localStorage.getItem('cropDicBean');
			var list = [];
			result = JSON.parse(result);
			for (var i = 0; i < result.data.length; i++) {
				var a = {
					value: result.data[i].dictId,
					text: result.data[i].dataName,
				}
				list.push(a);
				// console.log(JSON.stringify(a));
			}
			return list;
		} else {
			return [];
		}
	},
	//获取数据集字典（本地）
	getTaskDicLoC: function() {
		if (localStorage.getItem('taskDicBean')) {
			var result = localStorage.getItem('taskDicBean');
			var list = [];
			result = JSON.parse(result);
			var resultArr = [1801, 1802, 1803]; //分布、估产、长势
			for (var i = 0; i < result.data.length; i++) {
				var a = {
					value: result.data[i].dictId,
					text: result.data[i].dataName,
				}
				if (resultArr.indexOf(result.data[i].dictId) > -1) {
					list.push(a);
				}

				// console.log(JSON.stringify(a));
			}
			return list;
		} else {
			return [];
		}
	},
	//获取任务列表(本地)
	getTaskList: function() {
		if (localStorage.getItem(lstorage.getBeanKey('taskListBean'))) {
			var mTaskList = localStorage.getItem(lstorage.getBeanKey('taskListBean'));

			mTaskList = JSON.parse(mTaskList);
			var list = mTaskList.list;
			var picklist = [];

			for (var i in list) {
				// console.log(JSON.stringify(list[i]));
				if (list[i].taskStatus == 21002) { //21001未开始 21002进行中 21003已完成
					// console.log(JSON.stringify(list[i]))
					var item = {
						value: list[i].taskId,
						text: list[i].taskName,
						region: list[i].regionId,
					}
					// console.log(JSON.stringify(item));
					picklist.push(item);
				}
			}
			// console.log(picklist);
			return picklist;
		} else {
			return '';
		}
	},
	//获取模版列表（本地）
	getTPList: function() {
		var list = [];
		if (localStorage.getItem(lstorage.getUserPhone() + '_templateBean')) {
			var result = localStorage.getItem(lstorage.getUserPhone() + '_templateBean');
			result = JSON.parse(result);
			for (var i = 0; i < result.list.length; i++) {
				var a = {
					templateName: result.list[i].templateName,
					purpose: result.list[i].purpose,
					templateId: result.list[i].templateId,
					type: result.list[i].type,
					useFlag: result.list[i].useFlag,
				}
				list.push(a);
				// console.log(JSON.stringify(a));
			}
		}
		return list;
	},
	//获取初始模版列表（本地）
	getRTPList: function() {
		var list = [];
		if (localStorage.getItem(lstorage.getUserPhone() + '_templateBean')) {
			var result = localStorage.getItem(lstorage.getUserPhone() + '_templateBean');
			result = JSON.parse(result);
		}
		return (JSON.stringify(result.list));
	},
	//根据任务id获取模版信息
	getTPByTaskID: function(taskid) {
		var mTaskList = JSON.parse(localStorage.getItem(lstorage.getBeanKey('taskListBean')));
		if (mTaskList) {
			var list = mTaskList.list;
			for (var i in list) {
				if (taskid == list[i].taskId) {
					// console.log(JSON.stringify(list[i]));
					var fieldModelVOList = [];
					if (list[i].cltTemplate != null) {
						var fieldModelVOList = list[i].cltTemplate.templateVO.fieldModelVOList;
					}
					// console.log(JSON.stringify(fieldModelVOList));
					return fieldModelVOList;
				}
			}

		} else return null;

	},
	//根据任务id获取模版信息
	getTemplate: function(taskid) {
		var mTPlist = localStorage.getItem(lstorage.getUserPhone() + '_templateBean');
		var mTaskList = localStorage.getItem(lstorage.getBeanKey('taskListBean'));
		mTPlist = JSON.parse(mTPlist);
		mTaskList = JSON.parse(mTaskList);
		// 		console.log('templateBean:' + mTPlist.list);
		// 		console.log('taskList:' + mTaskList.list);
		if (mTPlist && mTaskList) {
			var list = mTaskList.list;
			// console.log(list);
			for (var i in list) {
				if (taskid == list[i].taskId) {
					var templateId = list[i].templateId;
					var Tplist = mTPlist.list;
					for (var i in Tplist) {
						// console.log(JSON.stringify(Tplist[i].templateVO));
						if (templateId == Tplist[i].templateId) {
							// console.log(templateId);
							var fieldModelVOList = Tplist[i].templateVO.fieldModelVOList;
							// console.log(JSON.stringify(fieldModelVOList));
							return fieldModelVOList;
						}
					}
				}
			}
			return '';
		} else {
			return '';
		}
	},
	getFMVOList: function(mTemplateId) {
		for (var i = 0; i < mTPlist.list.length; i++) {
			if (templateId == mTPlist.list[i].templateId) {
				var fieldModelVOList = mTPlist.list[i].templateVO.fieldModelVOList;
				console.log(fieldModelVOList);
				return fieldModelVOList;
			}
		}
	},
};

//字符串模板生成
String.prototype.formatCLT = function() {
	if (arguments.length == 0) return this;
	var param = arguments[0];
	var s = this;
	if (typeof(param) == 'object') {
		for (var key in param)
			s = s.replace(new RegExp("\\{" + key + "\\}", "g"), param[key]);
		return s;
	} else {
		for (var i = 0; i < arguments.length; i++)
			s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
		return s;
	}
};

// 对Date的扩展，将 Date 转化为指定格式的String   
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
// 例子：   
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.Format = function(fmt) { //author: meizz   
	var o = {
		"M+": this.getMonth() + 1, //月份   
		"d+": this.getDate(), //日   
		"h+": this.getHours(), //小时   
		"m+": this.getMinutes(), //分   
		"s+": this.getSeconds(), //秒   
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度   
		"S": this.getMilliseconds() //毫秒   
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};


var insurace = {
	getTaskList: function(callback) {
		var mTokenStr = commons.getToken()
		var url = AgriculturalInsurance.log_askTaskList_url_current+mTokenStr
		//TODO 未打包测试环境,临时路径替换
		url = " http://192.168.1.145:8006/taskInfoController/taskList"+mTokenStr
		console.log(url)
		mui.ajax(url, {
			dataType: 'json',
			type: 'GET',
			timeout: 5000,
			headers: {
				'Content-Type': 'application/json'
			},
			success: function(result) {
				// console.log(JSON.stringify(result))
				if (result.flag == true) {
					console.log("getTaskList："+result.flag)
					result = JSON.stringify(result);
					download.setLocalData(result)
					
					callback(result);
				}
			},
			error: function(xhr, ertype, errorThrown) { //异常处理；
				console.log(JSON.stringify(xhr) + '  ' + ertype + ' ' + errorThrown);
			}
		});
	},
};