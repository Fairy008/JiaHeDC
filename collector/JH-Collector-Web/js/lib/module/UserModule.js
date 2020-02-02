
//=================================================================================================//
//                                    用户操作类                                                   //
//=================================================================================================//

define(["BaseAjax"],function(BaseAjax){
	
	/**
	* 用户操作类，主要有如下功能：
	* 1.用户登录
	* 2.检查用户是否登录
	* 3.用户退出
	* 4.用户信息保存在Cookie中
	* @version <1> 2017-12-24 Hayden:Created.
	*/
	var UserUtil = function(){};


	/**
	* 在cookie中设置登录成功后的信息
	* @param accessToken : 登录成功Token
	* @param nickname : 用户昵称
	* @version <1> 2017-11-14 Hayden:Created.
	*/
	UserUtil.saveUserInfo = function(accessToken,userInfo){
		if(userInfo){
			var defaultProduct = userInfo.defaultProduct;
			if(defaultProduct){
                userInfo = {
                    // accessToken:accessToken,
                    nickname:userInfo.nickName,
                    roleCode:userInfo.roleCode,
                    defaultProduct:{
                        regionId:defaultProduct.regionId,
                        regionCode:defaultProduct.regionCode,
                        regionName:defaultProduct.regionName,
                        chinaName:defaultProduct.chinaName,
                        level:defaultProduct.level,
                        accuracyId:defaultProduct.accuracyId,
                        dsId:defaultProduct.datasetId || defaultProduct.dsId,
                        dsCode:defaultProduct.datasetCode || defaultProduct.dsCode,
                        dsName:defaultProduct.datasetName || defaultProduct.dsName,
                        dataTypeId:defaultProduct.datatypeId || defaultProduct.dataTypeId,
                        dataTypeCode:defaultProduct.datatypeCode || defaultProduct.dataTypeCode,
                        dataTypeName:defaultProduct.datatypeName || defaultProduct.dataTypeName,
                        startDate:defaultProduct.startDate,
                        endDate:defaultProduct.endDate
                    }
                };
			}
		}
		return JSON.stringify(userInfo)

	};


	//暴露区域选择器控件
	return {
		UserUtil:UserUtil
	};
});



