download = {
	getLoadUrl: function(taskId, subtaskId) {
		// http://192.168.1.223:8080/a/zip/2/1/1.zip
		url = "http://192.168.1.223:8080/a/zip/";
		taskStr = "" + taskId + "/";
		subtaskStr = "" + subtaskId + "/";
		subtaskIdStr = subtaskId + ".zip"
		url = url + taskStr + subtaskStr + subtaskIdStr
		console.log(url)
		return url
	},
	getUnZipPath: function(subtaskId) {
		return "file:///storage/emulated/0/Android/data/io.dcloud.HBuilder/downloads/subtask"+subtaskId+"/";
	},
	setLocalData: function(JsonStr) {
		localStorage.setItem("TaskInfo", JsonStr)
	},
	// 	saveData: function(JsonStr) {
	// 		var saveTarget = localStorage.getItem("TaskInfo")
	// 		// console.log(saveTarget)
	// 		saveTarget = JSON.parse(saveTarget)
	// 		saveTarget = saveTarget.data
	// 		// console.log(JSON.stringify(saveTarget))
	// var Jsondata =  JSON.parse(JsonStr)
	// 		for (var n in Jsondata) {
	// 			var isMatch = false
	// 			for (var m in saveTarget) {
	// 				var compareA = Jsondata[n]
	// 				var compareB = saveTarget[m]
	// 				if (compareA.task.taskId == compareB.task.taskId) {
	// 					var A = JSON.stringify(compareA)
	// 					var B = JSON.stringify(compareB)
	// 					if (A.length != B.length || (A != B)) {
	// 						var taskA = compareA.task
	// 						var taskB = compareB.task
	// 						var sublistA = compareA.subtaskList
	// 						var sublistB = compareB.subtaskList
	// 						console.log(taskA.taskId)
	// 
	// 					}
	// 				}
	// 			}
	// 		}
	// 	},
	getLocalData: function() {
		return localStorage.getItem("TaskInfo")
	},
	getSelectTask: function(taskId) {
		var data = localStorage.getItem("TaskInfo")
		data = JSON.parse(data)
		data = data.data

		for (var i in data) {
			var itemtaskid = data[i].task.taskId;
			// console.log(itemtaskid)
			if (itemtaskid == taskId) {
				// console.log(JSON.stringify(data[i]))
				return JSON.stringify(data[i].task)
			}
		}
	},
	getSubTaskByTaskid: function(taskId) {
		var data = localStorage.getItem("TaskInfo")
		data = JSON.parse(data)
		data = data.data

		for (var i in data) {
			var itemtaskid = data[i].task.taskId;
			// console.log(itemtaskid)
			if (itemtaskid == taskId) {
				// console.log(JSON.stringify(data[i]))
				return JSON.stringify(data[i].subtaskList)
			}
		}
	},
	getSelectSubTask: function(taskId, subtaskId) {
		var data = localStorage.getItem("TaskInfo")
		data = JSON.parse(data)
		data = data.data

		for (var i in data) {
			var itemtaskid = data[i].task.taskId;
			// console.log(itemtaskid)
			if (itemtaskid == taskId) {
				var list = data[i].subtaskList
				for (var j in list) {
					var itemId = list[j].subtaskId
					if (itemId == subtaskId) {
						console.log(JSON.stringify(list[j]))
						return JSON.stringify(list[j])
					}
				}
			}
		}
	},
	setTask2Data(taskId, taskdata) {
		var mdata = localStorage.getItem("TaskInfo")
		mdata = JSON.parse(mdata)
		var data = mdata.data

		for (var i in data) {
			var itemtaskid = data[i].task.taskId;
			console.log(itemtaskid)
			if (itemtaskid == taskId) {
				// console.log(JSON.stringify(data[i]))
				data[i].task = taskdata
				mdata.data = data
				localStorage.setItem("TaskInfo", JSON.stringify(mdata))
			}
		}
	},
	setSubTask2Data(taskId, subtaskId, subtaskdata) {
		var mdata = localStorage.getItem("TaskInfo")
		mdata = JSON.parse(mdata)
		var data = mdata.data

		for (var i in data) {
			var itemtaskid = data[i].task.taskId;
			
			if (itemtaskid == taskId) {
				// console.log(taskId)
				for (var j in data[i].subtaskList) {
					var itemId = data[i].subtaskList[j].subtaskId
					// console.log(subtaskId)
					// console.log(itemId)
					if (itemId == subtaskId) {
						
						// return JSON.stringify(list[j])
						data[i].subtaskList[j] = subtaskdata
						// console.log(JSON.stringify(data))
						mdata.data = data
						localStorage.setItem("TaskInfo", JSON.stringify(mdata))
					}
				}
			}
		}
	},
	setCalibrator: function(subtaskId, callback, errback) {
		subtaskIdStr = "&subtaskId=" + subtaskId
		var mTokenStr = commons.getToken()
		var url = AgriculturalInsurance.log_askSetCalibrator_url_current + mTokenStr + subtaskIdStr

		//TODO 未打包测试环境,临时路径替换
		url = "http://192.168.1.145:8006/subtask/relsteCalibrator?phone=18717145660&subtaskId=" + subtaskId
		// console.log(url)
		mui.ajax(url, {
			dataType: 'json',
			type: 'POST',
			timeout: 5000,
			headers: {
				'Content-Type': 'application/json'
			},
			success: function(result) {
				if (result.flag == true) {
					// console.log(JSON.stringify(result))
					result = JSON.stringify(result);
					callback(result);
				}
			},
			error: function(xhr, ertype, errorThrown) { //异常处理；
				errback();
				console.log(JSON.stringify(xhr) + '  ' + ertype + ' ' + errorThrown);
			}
		});
	},
	getPolicyBySubTaskId: function(subtaskId, callback, errback) {
		subtaskIdStr = "&subtaskId=" + subtaskId
		var mTokenStr = commons.getToken()
		var url = AgriculturalInsurance.log_askSetCalibrator_url_current + mTokenStr + subtaskIdStr

		//TODO 未打包测试环境,临时路径替换
		url = "http://192.168.1.145:8006/policyInfo/policyInfoList?subtaskId=" + subtaskId
		console.log(url)
		mui.ajax(url, {
			dataType: 'json',
			type: 'GET',
			timeout: 5000,
			headers: {
				'Content-Type': 'application/json'
			},
			success: function(result) {
				if (result.flag == true) {
					// console.log(JSON.stringify(result))
					result = JSON.stringify(result);
					callback(result);
				}
			},
			error: function(xhr, ertype, errorThrown) { //异常处理；
				errback();
				console.log(JSON.stringify(xhr) + '  ' + ertype + ' ' + errorThrown);
			}
		});
	}
};
