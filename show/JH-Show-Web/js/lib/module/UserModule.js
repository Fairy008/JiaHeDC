
//=================================================================================================//
//                                    用户操作类                                                   //
//=================================================================================================//

define(["BaseAjax","JHModule"],function(BaseAjax,JHModule){
	
	/**
	* 用户操作类，主要有如下功能：
	* 1.用户登录
	* 2.检查用户是否登录
	* 3.用户退出
	* 4.用户信息保存在Cookie中
	* @version <1> 2017-12-24 Hayden:Created.
	*/
	var UserUtil = function(){};

	//Cookie中保存accessToken的KEY
	UserUtil.AccessTokenKey = COOKIE_CONFIG.cookieName;

	/**
	* 检查cookie中是否保存用户信息,如没有则直接跳转到登录页面
	* @version <1> 2017-11-14 Hayden:Created.
	*/
	UserUtil.checkUserInfo = function(){
		var isCheck = true;
		
		var pageName = window.location.pathname;
		var lastIdx = pageName.lastIndexOf("/");
		pageName = pageName.substring(lastIdx);
		//判断是否为无需登录页面，否则检查accessToken是否正确,不正确，需跳到login.html
		for(var i=0;i<System_Env.Direct_PageList.length;i++){
			if(System_Env.Direct_PageList[i]==pageName) isCheck = false;
		};
		//if(isCheck&&(JHModule.CookieUtil.getCookie("registerPhone")==null)){
		//if(isCheck){
		if(isCheck){
			var accessToken = JHModule.CookieUtil.getCookie(UserUtil.AccessTokenKey);
			var data={};
			data[UserUtil.AccessTokenKey] = accessToken;
			//to do : invoke restful service to check accessToken is right.
			var ajax = new BaseAjax();
			ajax.opts.url = User_Env.Login_CheckToken_URL;
			ajax.opts.data = data;
			ajax.opts.async = false;
			ajax.opts.type="get";
			ajax.opts.successFun = function(result){
				var ajaxCheck = result.flag;
				if(!ajaxCheck){
					UserUtil.quitLogin();
				}else{
					var userInfo = UserUtil.getUserInfo();
					UserUtil.saveUserInfo(accessToken,userInfo);
				}
			};
			ajax.opts.errorFun = function(){
				UserUtil.quitLogin();
			};
			ajax.run();
		}
	};

	/**
	* 在cookie中设置登录成功后的信息
	* @param accessToken : 登录成功Token
	* @param nickname : 用户昵称
	* @version <1> 2017-11-14 Hayden:Created.
	*/
	UserUtil.saveUserInfo = function(accessToken,userInfo){
		if(userInfo){
			var defaultProduct = userInfo.defaultProduct;
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
					endDate:defaultProduct.endDate,
					cropId:defaultProduct.cropId,
					cropName:defaultProduct.cropName
				}
			};
		}

		if(accessToken){
			userInfo.accessToken = escape(accessToken);
			var userInfoStr = JSON.stringify(userInfo);
			JHModule.CookieUtil.setCookie(UserUtil.AccessTokenKey,userInfo.accessToken);
			JHModule.CookieUtil.setCookie(userInfo.accessToken,userInfoStr);
		}
	};

	/**
	 * 更新cookie时间
	 * @version <1> 2018-07-02 cxw:Created.
	 */
	UserUtil.updateCookie = function () {
		var accessToken = JHModule.CookieUtil.getCookie(UserUtil.AccessTokenKey);
		if(accessToken!=null)
		{
			var userInfo = UserUtil.getUserInfo();
			userInfo.nickName = userInfo.nickname;
			/*if(defaultProduct != null && defaultProduct != undefined) {
				userInfo.defaultProduct = defaultProduct;
			}*/
			UserUtil.saveUserInfo(accessToken,userInfo);
		}else{
			UserUtil.quitLogin();
		}
	}


	/**
	* 退出并删除cookie中的信息
	* @version <1> 2017-11-14 Hayden:Created.
	*/
	UserUtil.quitLogin = function(){
		var accessToken = JHModule.CookieUtil.getCookie(UserUtil.AccessTokenKey);
		JHModule.CookieUtil.delCookie(UserUtil.AccessTokenKey);
		JHModule.CookieUtil.delCookie(accessToken);
		location.href="index.html";
	};

	/**
	* 从Cookie中获取用户信息
	* @version <1> 2017-12-24 Hayden:Created.
	*/
	UserUtil.getUserInfo = function(){
		var _userInfo;
		var accessToken = JHModule.CookieUtil.getCookie(UserUtil.AccessTokenKey);

		if(accessToken){
			var userInfo = JHModule.CookieUtil.getCookie(accessToken);
			_userInfo = JSON.parse(userInfo);
		}
		return _userInfo;
	}

	//暴露区域选择器控件
	return {
		UserUtil:UserUtil
	};
});



