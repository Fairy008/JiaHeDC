areaData = {
	initArea: function() {

	},
	selectById: function(layer, id) {
		// console.log("id: "+id)
		var features = layer.getSource().getFeatures();
		// console.log(layer.getSource().getExtent())
		// console.log(features.length)
		var isFoundFeature = false
		for (var i in features) {
			// if (features[i].get("name") === value) {
			// 	selectedByAttriFeature = features[i];
			// 	break;
			// }
			// console.log('features: '+features[i].getId()+' ,id: '+id)

			if (features[i].getId() == id) {
				isFoundFeature = true
				// console.log("123")
				var feature = features[i]
				console.log(feature.getId())
				// areaData.setFeatureStyle(feature)
				return feature;
			}
		}
		if (!isFoundFeature) {
			throw "未找到查找到该feature信息";
		}
	},
	setFeatureStyle: function(feature) {
		var style = new ol.style.Style({
			fill: new ol.style.Fill({ //填充样式
				color: 'rgba(255, 0, 0, 0.8)'
			}),
			stroke: new ol.style.Stroke({ //线样式
				color: 'blue',
				width: 0.5
			}),
			image: new ol.style.Circle({ //点样式
				radius: 7,
				fill: new ol.style.Fill({
					color: '#FFFFFF'
				})
			})
		})
		feature.setStyle(style);
	},
	getFeatureCenter: function(feature) {
		var Extent = feature.getGeometry().getExtent();
		var X = Extent[0] + (Extent[2] - Extent[0]) / 2;
		var Y = Extent[1] + (Extent[3] - Extent[1]) / 2;
		return [X, Y];
	},
	clearSelectByAttribute: function(feature) {
		if (feature != null) {
			feature.setStyle(null);
		}
	},
}

areaInfo = {
	PolicyList: [],
	currentIndex: -1,
	initPolicyList: function(subtaskId, jsonData) {
		var keyStr = "subtaskId" + subtaskId
		console.log(keyStr)
		var currentData = plus.storage.getItem(keyStr)
		currentData = JSON.parse(currentData)
		if (currentData == null) {
			for(var i in jsonData){
				if (jsonData[i].insuredArea==null) {
					jsonData[i].insuredArea = []
				}
			}
			console.log(JSON.stringify(jsonData))
			plus.storage.setItem("subtaskId" + subtaskId, JSON.stringify(jsonData))
		}
	},
	setPolicyList: function(subtaskId, data) {
		plus.storage.setItem("subtaskId" + subtaskId, JSON.stringify(data))
	},
	getPolicyList: function(subtaskId) {
		var data = JSON.parse(plus.storage.getItem("subtaskId" + subtaskId))
		return data
	},
	getPolicyNum: function(subtaskId) {
		var data = JSON.parse(plus.storage.getItem("subtaskId" + subtaskId))
		var num = data.length;
		return num;
	},
	getPolicyNumEdit: function(subtaskId) {
		var data = JSON.parse(plus.storage.getItem("subtaskId" + subtaskId))
		var num = 0;
		for (var i in data) {
			if (data[i].isEdit == true) {
				num++;
			}
		}
		return num;
	},
	getPolicyNumNoEdit: function(subtaskId) {
		var data = JSON.parse(plus.storage.getItem("subtaskId" + subtaskId))
		var num = 0;
		for (var i in data) {
			if (data[i].isEdit == false) {
				num++;
			}
		}
		return num;
	},
	setCurrentPolicy: function(policy) {
		localStorage.setItem("policyId", policy);
	},
	getCurrentPolicy: function() {
		return localStorage.getItem("policyId");
	},
	getItemById: function(data, policy) {
		for (var i in data) {
			// console.log(data[i].policyNo ==policy)
			if (data[i].policyNo == policy) {
				return data[i]
			}
		}
	},
	// setItemById:function(policy,saveItem){
	// 	var data =areaInfo.getPolicyList()
	// 	for (var i in data) {
	// 		// console.log(data[i].policyNo ==policy)
	// 		if (data[i].policyNo == policy) {
	// 			data[i] = saveItem
	// 			plus.storage.setItem("policyList", JSON.stringify(data))
	// 		}
	// 	}
	// },
	//获取保单向下地块列表
	getAreaById: function(data, policy) {
		for (var i in data) {
			if (data[i].policyNo == policy) {
				// console.log(JSON.stringify(data[i]))
				var result = data[i].insuredArea
				// console.log(JSON.stringify(result))
				return result
			}
		}
	},
	//保存保单列表向下地块列表
	setAreaById: function(subtaskId, policy, saveArea) {
		var keyStr = "subtaskId" + subtaskId
		if (subtaskId == null) {
			throw "subtaskId为空"
		}
		var data = areaInfo.getPolicyList(subtaskId)
		// console.log(JSON.stringify(data))
		console.log(JSON.stringify(saveArea))

		for (var i in data) {
			if (data[i].policyNo == policy) {
				if (data[i].insuredArea == null) {
					data[i].insuredArea = []
				}
				// data[i].insuredArea.push(saveArea)
				data[i].insuredArea=saveArea
				// console.log(JSON.stringify(data[i]))
				plus.storage.setItem(keyStr, JSON.stringify(data))
			}
		}
	},
	//保存或新增地块信息到保单
	setAreaItem2Data: function(subtaskId, policy, saveArea) {
		var keyStr = "subtaskId" + subtaskId
		if (subtaskId == null) {
			throw "subtaskId为空"
		}
		var data = areaInfo.getPolicyList(subtaskId)
		// console.log(JSON.stringify(data))
		console.log(JSON.stringify(saveArea))
		
		for (var i in data) {
			if (data[i].policyNo == policy) {
				if (data[i].insuredArea==[]) {
					data[i].insuredArea.push(saveArea)
					console.log(JSON.stringify(data[i].insuredArea))
					// plus.storage.setItem(keyStr, JSON.stringify(data))
				}else {
					var isFound = false
					console.log(saveArea.FeatureId)
					console.log(JSON.stringify(data[i].insuredArea[n]) )
					for (var n in data[i].insuredArea) {
						if (data[i].insuredArea[n].FeatureId==saveArea.FeatureId) {
							isFound = true
							data[i].insuredArea[n] = saveArea
							console.log(JSON.stringify(data[i].insuredArea))
							// plus.storage.setItem(keyStr, JSON.stringify(data))
						}
					}
					if (isFound==false) {
						data[i].insuredArea.push(saveArea)
						console.log(JSON.stringify(data[i].insuredArea))
						// plus.storage.setItem(keyStr, JSON.stringify(data))
					}
				}
				
			}
		}
	},
}
