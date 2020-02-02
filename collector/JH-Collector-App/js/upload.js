upload = {
	//TODO 测试后期调整到url_config中
	testUrl: "http://192.168.1.145:8006/calibrationInfo/uploadImage",
	testSUBTASK: "http://192.168.1.145:8006/calibrationInfo/submitSubTask",
	subtaskJson: {
		"sub_task_id": "123",
		// "village_id": "123",
		"policy_list": [{
			"policy_no": "123",
			"area_list": [{
				"bsm": "12",
				"area_coordinates": [], //经纬度数组
				"img_url": [],
				// "calibrateCrop": "1",//TODO 未获取作物字典，且设计图上作物为input形式
				"calibratorArea": "50",
			}],
		}],
	},
	starUpLoad(mPlus, subtaskId, policyNo, FeatureId, item, back, errorback) {
		console.log(JSON.stringify(item))
		var path = item.localUrl;
		var url = upload.testUrl;
		// var url = "http://192.168.1.145:8006/calibrationInfo/uploadImage?policyNo=1"
		url = url + "?policyNo=" + policyNo
		path = "file://" + plus.io.convertLocalFileSystemURL(path)
		mPlus.nativeUI.showWaiting();
		console.log(url)
		console.log(path)


		var task = mPlus.uploader.createUpload(url, {
			method: 'POST'
		}, function(t, status) { //上传完成
			mPlus.nativeUI.closeWaiting();
			if (status == 200) {
				console.log(t.responseText)
				mToastSet('上传成功');
				var result = JSON.parse(t.responseText)
				item.url = result.data[0];
				console.log(FeatureId)

				back(t.responseText, policyNo, FeatureId, item)
			} else {
				mToastSet('上传失败');
				console.log('上传失败：' + status);
				errorback()
			}
		});
		// console.log(policyNo)
		// task.addData('policyNo', policyNo);
		task.addFile(path, {
			key: "file"
		});
		task.start();
	},
	//获取到的网络地址添加到文件中
	addUrl2Data: function(data, url) {
		if (data.localUrl == null || (data.localUrl == '')) {
			throw "该图片项无本地储存路径"
		} else {
			data.url = url;
			return data;
		}
	},
	//检查是否所有图片都有网络地址
	checkFiles: function(files) {
		var isChecked = true;
		for (var n in files) {
			if (files.url == null) {
				isChecked = false
			}
			if (files.url == '') {
				isChecked = false
			}
		}
		return isChecked;
	},
	getImagesNum: function(subtaskid) {
		var num = 0
		var thedata = areaInfo.getPolicyList(subtaskid)
		var villagelist = thedata;
		for (var i in villagelist) {
			if (villagelist[i].insuredArea.length > 0) {
				var policyArealist = villagelist[i].insuredArea
				for (var j in policyArealist) {
					var piclist = policyArealist[j].imageList
					for (var n in piclist) {
						num++
					}
				}
			}
		}
		// console.log(num)
		return num
	},
	getPercent: function(current, num) {
		var result = current / num
		result = Math.floor(result * 10000) / 100 + '%';
		return result
	},
	uploadVillageImages: function(mPlus, subtaskId, jsonData, inback, back, errorback) {
		var villagelist = jsonData;
		// console.log(JSON.stringify(jsonData))
		for (var i in villagelist) {
			if (villagelist[i].insuredArea.length > 0) {
				// console.log(JSON.stringify(villagelist[i]))
				var policyNo = villagelist[i].policyNo
				for (var j in villagelist[i].insuredArea) {
					for (var n in villagelist[i].insuredArea[j].imageList) {
						// console.log(policyNo)
						var FeatureId = villagelist[i].insuredArea[j].FeatureId
						var item = villagelist[i].insuredArea[j].imageList[n]
						upload.starUpLoad(plus, subtaskId, policyNo, FeatureId, item, inback, errorback)
					}
				}
			}
		}
	},
	upLoadSubTask: function(mMui, mPlus, jsonData, back) {
		var url = upload.testSUBTASK;
		// console.log(url)
		console.log(JSON.stringify(jsonData))
		mMui.ajax(url, {
			data: jsonData,
			dataType: 'json',
			type: 'post',
			timeout: 10000,
			headers: {
				'Content-Type': 'application/json'
			},
			success: function(result) {
				//转换成字符串才能保存到localStorage
				// console.log(JSON.stringify(result));
				if (result.flag == true) {
					back();
				} else {
					mPlus.nativeUI.toast(result.msg, {
						'verticalAlign': 'center'
					});
					console.log(JSON.stringify(result));
				}
			},
			error: function(xhr, ertype, errorThrown) { //异常处理；
				mPlus.nativeUI.toast('上传失败，请检查网络环境', {
					'verticalAlign': 'center'
				});
				console.log(JSON.stringify(xhr) + '  ' + ertype + ' ' + errorThrown);
			}
		});
	},
	//转化格式
	transf2UploadForm(subtaskid) {
		var subtaskJson = {
			"sub_task_id": subtaskid,
			// "village_id": "123",
			"policy_list": [
				// 	{
				// 	"policy_no": "123",
				// 	"area_list": [{
				// 		"bsm": "12",
				// 		"area_coordinates": [], //经纬度数组
				// 		"img_url": [],
				// 		// "calibrateCrop": "1",//TODO 未获取作物字典，且设计图上作物为input形式
				// 		"calibratorArea": "50",
				// 	}],
				// },
			],
		}
		var thedata = areaInfo.getPolicyList(subtaskid)
		// console.log(JSON.stringify(thedata))

		for (var i in thedata) {
			var policyItem = {
				"policy_no": thedata[i].policyNo,
				"area_list": [],
			}
			if (thedata[i].insuredArea.length > 0) {
				// console.log(thedata.length)
				var list = thedata[i].insuredArea
				for (var j in list) {
					var areaitem = {
						// "bsm": list[j].FeatureId,
						"area_coordinates": list[j].coordinates, //经纬度数组
						"img_url": [],
						// "calibrateCrop": "1",//TODO 未获取作物字典，且设计图上作物为input形式
						"calibratorArea": list[j].area,
					}

					if (list[j].imageList.length > 0) {
						var images = list[j].imageList
						for (var k in images) {
							// console.log(JSON.stringify(images[k]))
							areaitem.img_url.push(images[k].url)
							// console.log(JSON.stringify(areaitem))
						}
					}
					// console.log(JSON.stringify(areaitem))
					policyItem.area_list.push(areaitem)
				}
			}
			subtaskJson.policy_list.push(policyItem)
		}
		// console.log(JSON.stringify(subtaskJson))
		return subtaskJson
	}
}
