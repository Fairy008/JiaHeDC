//=================================================================================================//
//                                       js依赖引用配置                                            //
//=================================================================================================//
require.config({
	baseUrl:"js",
	map: {
		'*': {
			// jquery:["lib/jquery/jquery.min"],
			'css': 'lib/require-css/css.min' // or whatever the path to require-css is
		}
	},
	paths:{
		jquery:["lib/jquery/jquery.min","https://cdn.bootcss.com/jquery/3.2.1/jquery.min"],
		ol:["lib/ol/ol","https://openlayers.org/en/v4.3.2/build/ol"],
		jsgrid:["lib/jsgrid-1.5.3/jsgrid.min"],
		RegionModule:["lib/module/RegionModule"],
		BaseAjax:["lib/module/BaseAjax"],
		MapModule:["lib/module/MapModule"],
		echarts:["lib/echart/echarts.common.min"],
		JHModule:'lib/module/JHModule',
		UserModule:'lib/module/UserModule',
		dateUtil:["lib/dateUtil/dateUtil"],
		jedate:["lib/dateUtil/jquery.jedate"],
		tableExporter:["lib/jquery/tableExporter"],
		jqueryUi:"lib/jquery-ui/jquery-ui.min",
		slider:"lib/slider/jquery-ui-slider-pips",
		pageM : "lib/page/pageM",
		popWin:'lib/module/PopWin',
		selectordie: 'lib/select/selectordie.min',
		bootstrap:'lib/bootstrap/bootstrap.min',
		moment: 'lib/dateUtil/moment',
		daterangepicker:'lib/dateUtil/daterangepicker',
		formVerfication:["lib/module/formVerfication"],
		PopWin:["lib/module/PopWin"],
		commons:["lib/module/commons"],
		Base64:["lib/security/Base64"],
		custom_settings:["custom_settings"],

		paginate:['tablePage/jquery.paginate'],
		yhhDataTable:['tablePage/jquery.yhhDataTable'],
		page:['tablePage/page'],
	},
	shim:{
		ol:["css!lib/ol/ol.css"],
		jsgrid:["jquery","css!lib/jsgrid-1.5.3/jsgrid.min","css!lib/jsgrid-1.5.3/jsgrid-theme.min"],
		BaseAjax:{
			deps:["jquery"],
			exports:"BaseAjax"
		},
		MapModule:["BaseAjax"],
		UserModule:["BaseAjax","JHModule"],
		jedate:["css!lib/dateUtil/jedate"],
		pageM : {
			deps:["jquery"]
		},
		selectordie: ["jquery"],
		bootstrap: ["jquery"],
		daterangepicker: ["jquery","moment"],
		// superslide: ["jquery"],

		paginate:["jquery"],
		yhhDataTable:["jquery"],
		page:["jquery"]
	}
});


//=================================================================================================//
//                         配置网关地址、各数据集服务地址及方法                                    //
//                         配置系统管理相关服务地址                                                //
//=================================================================================================//

/**
 * 网关地址
 * @version <1> 2017-11-24 Hayden:Created.
 */
//生产环境
var GatWay_URL="http://gateway.datall.cn";
//长沙
// 	var GatWay_URL = "http://10.111.116.112:8001/";
//  var GatWay_URL = "http://192.168.1.15:8001";

 // var GatWay_URL = "http://113.57.163.74:8801";

/**
 * 农情展示平台的cookie常量
 * @type {{cookieName: string}}
 */
var COOKIE_CONFIG = {
 	cookieName : "AccessTokenForShow"
}

/**
 * 系统参数配置
 * @version <1> 2017-11-28 lcw: created.S
 */
var System_Env = {

	//用户注册验证码生成地址
	Region_Valid_Code_URL:GatWay_URL+"/jh-sys/nolog/user/regionValidateCode?validToken=",
	//用户登录验证码生成地址
	Login_Valid_Code_URL:GatWay_URL+"/jh-sys/nolog/user/loginValidateCode?validToken=",
	//登录地址
	Login_URL:GatWay_URL+ "/jh-sys/nolog/user/loginForShow",
	//无需登录，直接访问的页面地址
	Direct_PageList: ["/home.html","/register.html","/registerSuccess.html","/forgotPwd.html","/resetPwdSuccess.html"]
};

/**
 * 初始化进入时：默认区域配置
 * @type {{}}
 * @version<1> 2018-11-12 lcw :Created.
 */
var DEFAULT_REGION_CONFIG = {
    region_id :3102000006,
    region_name : "Hubei",
    level : 2,
    region_code : "CHN-HU",
    china_name : '湖北省'
}

/**
 * 用户数据产品相关配置
 * @version <1> 2017-12-24 Hayden:Created.
 */
var User_Env={
	//用户在Cookie中保存token的key
	KEY_AccessToken:"AccessTokenForShow",

	//检查用户AccessToken是否登录地址
	Login_CheckToken_URL:GatWay_URL+"/jh-sys/nolog/user/checkAccessToken",

	//注册
	Register_URL:GatWay_URL+"/jh-sys/nolog/user/register",
	//注册时检查电话号码是否存在
	CheckPhoneExists_URL:GatWay_URL + "/jh-sys/nolog/user/checkAccountExists",
	//注册时发送手机验证码
	Register_SendSmsCode_URL:GatWay_URL + "/jh-sys/nolog/user/findValidCodeForRegister",

	//找回密码，发送短信
	FindPwd_SendSmsCode_URL:GatWay_URL + "/jh-sys/nolog/user/findValidCodeForPwd",

	//修改密码时验证手机号与验证码是否匹配
	ReSetPwd_CheckPhoneCode_URL:GatWay_URL + "/jh-sys/nolog/user/checkPhoneCode",
	//修改密码
	ReSetPwd_URL:GatWay_URL + "/jh-sys/nolog/user/resetPwd",

	//获取用户权限区域列表（分级显示）
	Region_URL:GatWay_URL+"/jh-sys/region/userRegionList",
	//根据regionId查询家族祖先（到省一级）
	REGION_FAMILY_URL : GatWay_URL + "/jh-sys/region/queryRegionFamily",
	
	// Region_URL:"http://localhost:8004/region/userRegionList",
	//获取用户权限区域数据集列表地址
	Region_DsList_URL:GatWay_URL+"/jh-sys/user/dsListForRegion",
	//用户选择区域中相关数据集作物列表、数据源即分辨率
	Region_Ds_CropList_URL:GatWay_URL+"/jh-sys/user/cropDataTypeListForDsRegion",
	//根据区域，作物，数据集，分辨率获取用户权限时间
	DataTime_URL:GatWay_URL+"/jh-sys/user/dataTimeForPerm",
	findRegion_url:GatWay_URL+"/jh-sys/region/userAllRegionList",
	//获取用户权限区域数据集列表地址
	Region_AllDsList_URL:GatWay_URL+"/jh-sys/user/dsListForAllRegion",
	//用户选择区域中相关数据集作物列表、数据源即分辨率
	Region_Ds_AllCropList_URL:GatWay_URL+"/jh-sys/user/allCropDataTypeListForDsRegion",
	//查询是否有数据权限
	queryHasProduct_URL:GatWay_URL+"/jh-sys/dataProduct/queryHasProduct",
	//导出数据
	exportData_URL : GatWay_URL+"/jh-show/dataExport/export",

};

/**
 * 字典配置中心
 *
 * @version <1> 2018-02-06 djh： Created.
 */
var DICT_COFING = {
    dataType_url: GatWay_URL + '/jh-sys/dict/queryDataType',
    downloadStatus_url: GatWay_URL + '/jh-sys/dict/queryDownloadStatus',//查询所有的下载状态
    queryDictList_url: GatWay_URL + '/jh-sys/dict/queryDictList',//初始化查询数据字典所有数据
    queryDictByParentId_url: GatWay_URL + '/jh-sys/dict/queryDictByParentId',//根据父ID查询子节点
    queryDictById_url: GatWay_URL + '/jh-sys/dict/queryDictById',//根据id查询数据字典详情
    saveDict_url: GatWay_URL + '/jh-sys/dict/saveDict',//添加数据字典
    editDict_url: GatWay_URL + '/jh-sys/dict/editDict',//修改数据字典
    delDict_url: GatWay_URL + '/jh-sys/dict/delDict', //删除数据字典
    queryDictByCode_url: GatWay_URL + '/jh-sys/dict/queryDictByCode', //初始化根据编码查询出id，然后根据查询出的id匹配父ID查询出所有字典信息
    searchTypeUrl: GatWay_URL + '/jh-sys/dict/querySubDictListByParentId',
    checkDictCode_url:GatWay_URL+'/jh-sys/dict/checkDictCode',
    findCropByRegion_url:GatWay_URL + '/jh-sys/dict/findCropByRegionId',
    queryDictListByPid_url: GatWay_URL + "/jh-sys/dict/queryDictListByPid",
    /*  checkDictIdIsExistsUrl : GatWay_URL + 'dict/checkDictIdIsExists',
     checkDictCodeIsExistsUrl : GatWay_URL + 'dict/checkDictCodeIsExists'*/
    queryResolutionListByDataSetId_url: GatWay_URL + '/jh-sys/dataSetAndResolution/queryResolutionListByDataSetId',
    queryResolutionListByDSId_url: GatWay_URL + '/jh-sys/dataSetAndResolution/queryResolutionListByDSId',
    addDatasetAndResolutionData_url: GatWay_URL + '/jh-sys/dataSetAndResolution/addDatasetAndResolutionData'

}

/*
 * 数据权限URL集合
 * @type {{PRODUCT_LIST_URL: string}}
 * @version<1> 2018-01-05 lcw : created.
 */
var Data_PRODUCT = {
	// PRODUCT_LIST_URL : GatWay_URL + "/jh-show/dsReport/queryReportPage"
}

/*
 * 报告数据集配置
 * @version <1> 2017-12-24 Hayden:Created.
 */
var DS_Report={
	id:'1808',
	name:"dsreport",
	chinaName:"报告",
	//最新报告，前15条最新报告获取地址
	Report_New_URL: GatWay_URL + "/jh-publish/dsReport/findTopReportList",
	//报告查询地址
	Report_Query_URL:GatWay_URL+"/",
	//报告下载地址
	REPORT_DOWNLOAD_URL:GatWay_URL + "/jh-publish/dsReport/reportDown",
	//查询报告
	Report_list_url : GatWay_URL + "/jh-show/dsReport/queryReportPage",
	//报告预览
	REPORT_PREVIEW_URL:GatWay_URL + "/jh-show/dsReport/previewReportPdf"
};


/**
 * 简报数据集配置
 * @version <1> 2018-10-18 huxiaoqiang:Created.
 */
var DS_Brief = {
	id:'1809',
	name:"brief",
	chinaName:"简报",
	//简报列表
	Brief_list_url:GatWay_URL + "/jh-show/nolog/briefingReport/findBriefPage",
	//简报详情
	Brief_info_url:GatWay_URL +"/jh-show/nolog/briefingReport/findBriefReportById",
	//预览简报url
    Preview_brief_url:GatWay_URL +"/jh-show/nolog/briefingReport/previewBriefReportByIdNew",
	//分享简报url
    Trmm_brief_url:GatWay_URL +"/jh-show/nolog/briefingReport/previewBriefReportByIdTrrm"
}



/**
 *图层数据集地址
 *@version <1> 2017-11-28 XuZhenguo:Created.
 */
var DS_Layer = {
	name:"layer",
	//图层查询地址
	Layer_Query_URL : GatWay_URL+ "/jh-map/layer/findLayer",
	Layer_Init_URL : GatWay_URL + "/jh-map/layer",
};

/*
 * 降雨数据集配置
 * @version <1> 2017-12-24 Hayden:Created.
 */
var DS_Trmm={
	id:'1805',
	chinaName:'降雨',
	name:"trmm",
	//数据基础单位
	unit:"mm",
	//数据日期格式
	Date_Format:"yyyy-MM-dd",
	//数据集是叠加图层，还是色块
	Is_ColorBlock_Layer:true,
	//是否显示时间滑块
	Is_Show_Slide:true,
	//日期控件是否只能显示同月
	sameDate:"month",
	//数据集图例，
	Map_Legend : [
		['特大暴雨(>250)','#003896',''],
		['大暴雨(>100)','#0043b3',''],
		['暴雨(>50)','#005993',''],
		['大雨(>25)','#0374be',''],
		['中雨(>10)','#0091ef',''],
		['小雨(>0)','#43b4fd',''],
		['无降水(=0)','#d2dce1','']
	],
	threeTitle:'降雨量',
	//上期环比数据获取地址
	Mom_URL:GatWay_URL+"/jh-show/trmm/trmmForMon",
	//三年同比数据获取地址
	An_ThreeYear_URL:GatWay_URL+"/jh-show/trmm/queryTrmmForChart",
	//本期数据简报数据获取地址
	Briefing_URL:GatWay_URL + "/jh-show/trmm/trmmForReport",
	//时间点
	FindDatetimePoint:GatWay_URL+"/jh-show/trmm/queryTrmmTimes",
};

/*
 * 地温数据集配置
 * @version <1> 2017-12-24 Hayden:Created.
 */
var DS_T={
	id:'1804',
	chinaName:'地温',
	name:"t",
	//数据基础单位
	unit:"℃",
	//数据日期格式
	Date_Format:"yyyy-MM-dd",
	//数据集是叠加图层，还是色块
	Is_ColorBlock_Layer:true,
	//是否显示时间滑块
	Is_Show_Slide:true,
	//日期控件是否只能显示同月
	sameDate:"month",
	//数据集图例，
	Map_Legend : [
		['>30','rgb(254,19,12)',''],
		['>20','rgb(255,113,25)',''],
		['>10','rgb(255,193,36)',''],
		['>0','rgb(238,255,46)',''],
		['>-10','rgb(58,222,104)',''],
		['>-30','rgb(64,129,189)',''],
		['<-30','rgb(129,65,157)','']
	],
	threeTitle:'地温',
	//上期环比数据获取地址
	Mom_URL:GatWay_URL+"/jh-show/t/tForMon",
	//三年同比数据获取地址
	An_ThreeYear_URL:GatWay_URL+"/jh-show/t/queryTForChart",
	//本期数据简报数据获取地址
	Briefing_URL:GatWay_URL + "/jh-show/t/tForReport",
	//时间点
	FindDatetimePoint:GatWay_URL+"/jh-show/t/queryTTimes",
};


/**
 * 气温数据集配置
 * @version<1> 2018-10-15 lcw :Created.
 */
var DS_Weather = {
	id:'1807',
	chinaName:'气温',
    name:'temperature',
    unit:"℃",
    //数据日期格式
    Date_Format:"yyyy-MM-dd",
    //数据集是叠加图层，还是色块
    Is_ColorBlock_Layer:true,
    //是否显示时间滑块
    Is_Show_Slide:true,
    //日期控件是否只能显示同月
    sameDate:"month",
    //数据集图例，
    Map_Legend : [
		['>40','rgb(255,31,31)',''],
        ['>35','rgb(255,71,31)',''],
        ['>30','rgb(255,88,31)',''],
        ['>25','rgb(255,176,31)',''],
        ['>20','rgb(255,237,31)',''],
        ['>15','rgb(226,255,31)',''],
        ['>10','rgb(138,255,31)',''],
        ['>5','rgb(76,255,31)',''],
        ['>0','rgb(31,255,73)',''],
        ['>-5','rgb(31,255,170)',''],
        ['>-10','rgb(31,255,221)',''],
        ['>-15','rgb(31,241,255)',''],
        ['>-20','rgb(31,180,255)',''],
        ['>-25','rgb(31,92,255)',''],
        ['>-30','rgb(31,49,255)','']
	],
    Weather_Legend:[
        ['>40','rgb(255,31,31)',''],
        ['>38','rgb(255,31,31)',''],
        ['>36','rgb(255,62,31)',''],
        ['>34','rgb(255,71,31)',''],
        ['>32','rgb(255,88,31)',''],
        ['>30','rgb(255,119,31)',''],
        ['>28','rgb(255,146,31)',''],
        ['>26','rgb(255,176,31)',''],
        ['>24','rgb(255,206,31)',''],
        ['>22','rgb(255,237,31)',''],
        ['>20','rgb(255,237,31)',''],
        ['>18','rgb(255,255,31)',''],
        ['>16','rgb(226,255,31)',''],
        ['>14','rgb(195,255,31)',''],
        ['>12','rgb(165,255,31)',''],
        ['>10','rgb(138,255,31)',''],
        ['>8','rgb(107,255,31)',''],
        ['>6','rgb(76,255,31)',''],
        ['>4','rgb(46,255,31)',''],
        ['>2','rgb(31,255,42)',''],
        ['>0','rgb(31,255,73)',''],
        ['>-2','rgb(31,255,103)',''],
        ['>-4','rgb(31,255,103)',''],
        ['>-6','rgb(31,255,170)',''],
        ['-8','rgb(31,255,192)',''],
        ['-10','rgb(31,255,221)',''],
        ['>-12','rgb(31,255,221)',''],
        ['>-14','rgb(31,255,253)',''],
        ['>-16','rgb(31,241,255)',''],
        ['>-18','rgb(31,211,255)',''],
        ['>-20','rgb(31,180,255)','']
    ],
	threeTitle:'气温',
    //上期环比数据获取地址
    Mom_URL:GatWay_URL+"/jh-show/temp/tempForMon",
    //三年同比数据获取地址
    An_ThreeYear_URL:GatWay_URL+"/jh-show/temp/queryTempForChart",
    //本期数据简报数据获取地址
	Briefing_URL:GatWay_URL + "/jh-show/temp/tempForReport",
	//时间点
	FindDatetimePoint:GatWay_URL+"/jh-show/temp/queryTempTimes"
}
/*
 * 分布数据集配置
 * @version <1> 2017-12-24 Hayden:Created.
 */
var DS_Distribution={
	id:'1801',
	chinaName:'分布',
	name:"distribution",
	//数据基础单位
	unit:"万亩",
	//数据日期格式
	Date_Format:"yyyy-MM-dd",
	//数据集是叠加图层，还是色块
	Is_ColorBlock_Layer:false,
	//是否显示时间滑块
	Is_Show_Slide:false,
	//日期控件是否只能显示同月
	sameDate:"year",
	//数据集图例，
	Map_Legend : [
		['大豆','#00e7a9',''],
		['玉米','#ff7a4e',''],
		['春玉米','#ff7a4e',''],
		['夏玉米','#ff7a4e',''],
		['春小麦','#4ce700',''],
		['冬小麦','#4ce700',''],
		['水稻','#38a900',''],
		['早稻','#38a900',''],
		['中稻','#38a900',''],
		['晚稻','#38a900',''],
		['油菜','#ffe700',''],
		['冬油菜','#ffe700',''],
		['棉花','#ffab00',''],
		['棕榈','#7b5404',''],
		['小麦','#4ce700',''],
		['大麻','#FFC125',''],
		['苹果','#49ea00',''],
		['茶叶','#ff7a4e',''],
		['大棚','#BBFFFF',''],
		['池塘','#1C86EE',''],
		['林地','#7FFF00',''],
		['柑橘','#ff7a4e','']
	],
	//田块分布图例，
	Rank_Legend : [
		['冬小麦','#f5b800',''],
		['早稻','#49ea00',''],
		['中稻','#b527ff',''],
		['晚稻','#27fffc',''],
		['冬油菜','#276eff',''],
	],
	//上海田块分布图例，
	Rank_Legend : [
		['水稻','#38a900',''],
		['大棚','#BBFFFF',''],
		['池塘','#1C86EE',''],
		['林地','#7FFF00',''],
		['其它','#FFEFDB','']
	],
	title:'分布占比统计',
	threeTitle:'三年分布统计',
	//查询当年与前一年区域及下一级区域的作物种植面积数据,需要参数:regionId,cropId,dataTime,accuracyId
	Mom_URL:GatWay_URL+"/jh-show/distribution/distributionForPrevious",
	//查询当前区域及下一级区域的作物种植面积近三年及历年10年均值,需要参数:regionId,cropId,dataTime,accuracyId
	An_ThreeYear_URL:GatWay_URL+"/jh-show/distribution/distributionForThree",
	//首页最新报告地址
	Briefing_URL:DS_Report.Report_New_URL,
	//查询分布的时间点,需要参数：regionId,cropId,startDate,endDate,resolution
	FindDatetimePoint:GatWay_URL+"/jh-show/distribution/queryDistributionTimes",
	//查询重庆指定分区下在重庆市的百分占比
	FindPercent:GatWay_URL+"/jh-show/distribution/queryRegionPercentage",
};

/*
 * 估产数据集配置
 * @version <1> 2017-12-24 Hayden:Created.
 */
var DS_Yield={
	id:'1802',
	chinaName:'估产',
	name:"yield",
	//数据基础单位
	unit:"万吨",
	//数据日期格式
	Date_Format:"yyyy-MM-dd",
	//数据集是叠加图层，还是色块
	Is_ColorBlock_Layer:true,
	//是否显示时间滑块
	Is_Show_Slide:false,
	//日期控件是否只能显示同月
	sameDate:"year",
	//数据集图例，
	Map_Legend : [
		['>50','rgb(74,171,2)',''],
        ['>45','rgb(81,187,2)',''],
		['>40','rgb(88,193,9)',''],
        ['>35','rgb(96,196,22)',''],
		['>30','rgb(104,202,31)',''],
        ['>25','rgb(123,215,54)',''],
		['>20','rgb(133,220,70)',''],
        ['>15','rgb(148,229,88)',''],
        ['>5','rgb(159,237,102)',''],
		['>0','rgb(171,245,116)','']
	],
	title:'估产占比统计',
	threeTitle:'三年估产统计',
	//区域及子区域作物当年与上一年的估产数据,需要参数:regionId,cropId,dataTime,resolution
	// Mom_URL:GatWay_URL+"/jh-show/yield/yieldForPrevious",
	//区域及子区域作物三年的估产及十年的估产均值,需要参数:regionId,cropId,dataTime,resolution
	An_ThreeYear_URL:GatWay_URL+"/jh-show/yield/yieldForThree",
	//首页最新报告地址
	Briefing_URL:DS_Report.Report_New_URL,
	//查询估产的时间点,需要参数:regionId,cropId,startDate,endDate,resolution
	FindDatetimePoint:GatWay_URL+"/jh-show/yield/queryYieldTimes",
	//查询该区域下各级区域的作物估产情况,需要参数:regionId,cropId,dataTime,resolution
	Mom_URL:GatWay_URL+"/jh-show/yield/queryYieldInRegion",
	Mom_URL_table:GatWay_URL+"/jh-show/yield/yieldForPrevious",
	//查询重庆指定分区下在重庆市的百分占比
	FindPercent:GatWay_URL+"/jh-show/yield/queryRegionPercentage"
};

/*
 * 长势数据集配置
 * @version <1> 2017-12-24 Hayden:Created.
 */
var DS_Growth={
	id:'1803',
	chinaName:'长势',
	name:"growth",
	//数据基础单位
	unit:"万亩",
	//数据日期格式
	Date_Format:"yyyy-MM-dd",
	//数据集是叠加图层，还是色块
	Is_ColorBlock_Layer:false,
	//是否显示时间滑块
	Is_Show_Slide:true,
	//日期控件是否只能显示同月
	sameDate:"year",
	//数据集图例，
/*	Map_Legend : [
        ['很好', "rgb(0,102,0)",""],
        ['好', "rgb(38,122,10)",""],
        ['较好', "rgb(80,143,21)",""],
        ['正常', "rgb(131,166,36)",""],
        ['较差', "rgb(182,186,54)",""],
        ['差', "rgb(209,181,77)",""],
        ['很差', "rgb(232,175,100)",""],
	],*/
	Map_Legend : [
		['很好', "#C0FF3E",""],
		['好', "#B3EE3A",""],
		['较好', "#C1FFC1",""],
		['正常', "#BBFFFF",""],
		['较差', "#FFE4B5",""],
		['差', "#FFD700",""],
		['很差', "#FFFF00",""],
	],
	title:'长势等级占比统计',
	threeTitle:'长势统计',
	/*//上期环比数据获取地址
	Mom_URL:GatWay_URL+"/jh-ds-dgy/growth/GrowthForPrevious",
	//三年同比数据获取地址
	An_ThreeYear_URL:GatWay_URL+"/jh-ds-dgy/growth/GrowthForThree",
	//本期数据简报数据获取地址
	Briefing_URL:GatWay_URL + "/jh-ds-dgy/growth/growthForReport",*/

	//查询某时间段内的长势数据时间点,需要参数:regionId,cropId,startDate,endDate,resolution
    FindDatetimePoint:GatWay_URL+"/jh-show/growth/queryGrowthTimes",
	//查询该区域作物各种长势情况的面积分布和占比数据,需要参数：regionId,cropId,dataTime,resolution
    Mom_URL:GatWay_URL+"/jh-show/growth/queryGrowthArea",
	//查询该区域下各级各级区域的作物长势数据,需要参数：regionId,cropId,dataTime,resolution
    GrowthInRegion:GatWay_URL+"/jh-show/growth/queryGrowthInRegion"

};

//天气预报
var Weather_Prediction = {
	//根据区域查询区域及下一级区域的天气预报数据
    RegionAndBeyondWeather_URL:GatWay_URL+"/jh-show/weather/queryRegionAndBeyond",
	//根据区域查询该区域的天气预报数据
    RegionWeather_URL:GatWay_URL+"/jh-show/weather/queryRegionWeather",
	//小雨
	xiaoyu_img_url:"img/weather/xiaoyu.png",
	// xiaoyu_current_img_url:"img/weather/xiaoyu_current.png",
	//暴雨
	baoyu_img_url:"img/weather/baoyu.png",
	// baoyu_current_img_url:"img/weather/baoyu_current.png",
	//多云
	duoyun_img_url:"img/weather/duoyun.png",
	// duoyun_current_img_url:"img/weather/duoyun_current.png",
	//雷阵雨
	leizhenyu_img_url:"img/weather/leizhenyu.png",
	// leizhenyu_current_img_url:"img/weather/leizhenyu_current.png",
	//晴天
	qing_img_url:"img/weather/qing.png",
	// qing_current_img_url:"img/weather/qing_current.png",
	//雾
	wu_img_url:"img/weather/wu.png",
	// wu_current_img_url:"img/weather/wu_current.png",
	//雪
	xue_img_url:"img/weather/xue.png",
	// xue_current_img_url:"img/weather/xue_current.png",
	//阴天
	yintian_img_url:"img/weather/yintian.png",
	// yintian_current_img_url:"img/weather/yintian_current.png",
	//雨夹雪
	yujiaxue_img_url:"img/weather/yujiaxue.png",
	// yujiaxue_current_img_url:"img/weather/yujiaxue_current.png",
	//阵雨
	zhenyu_img_url:"img/weather/zhenyu.png",
	// zhenyu_current_img_url:"img/weather/zhenyu_current.png",

}

var weatherNameUrl = [
	{name:'小雨',url:Weather_Prediction.xiaoyu_img_url},
	// {name:'当前小雨',url:Weather_Prediction.xiaoyu_current_img_url},
	{name:'暴雨',url:Weather_Prediction.baoyu_img_url},
	// {name:'当前暴雨',url:Weather_Prediction.baoyu_current_img_url},
	{name:'多云',url:Weather_Prediction.duoyun_img_url},
	// {name:'当前多云',url:Weather_Prediction.duoyun_current_img_url},
	{name:'雷阵雨',url:Weather_Prediction.leizhenyu_img_url},
	// {name:'当前雷阵雨',url:Weather_Prediction.leizhenyu_current_img_url},
	{name:'晴',url:Weather_Prediction.qing_img_url},
	// {name:'当前晴天',url:Weather_Prediction.qing_current_img_url},
	{name:'雾',url:Weather_Prediction.wu_img_url},
	// {name:'当前雾',url:Weather_Prediction.wu_current_img_url},
	{name:'雪',url:Weather_Prediction.xue_img_url},
	// {name:'当前雪',url:Weather_Prediction.xue_current_img_url},
	{name:'阴',url:Weather_Prediction.yintian_img_url},
	// {name:'当前阴天',url:Weather_Prediction.yintian_current_img_url},
	{name:'雨夹雪',url:Weather_Prediction.yujiaxue_img_url},
	// {name:'当前雨夹雪',url:Weather_Prediction.yujiaxue_current_img_url},
	{name:'阵雨',url:Weather_Prediction.zhenyu_img_url},
	// {name:'当前阵雨',url:Weather_Prediction.zhenyu_current_img_url}
					  ]


/**
 * 根据数据集代码获取数据集信息
 *
 * @version <1> 2017-11-07 Hayden:Created.
 */
var getDsInfo =function(dsCode){
	var dsInfo = {};
	if(dsCode==DS_Trmm.name)dsInfo = DS_Trmm;
	if(dsCode==DS_T.name) dsInfo = DS_T;
	if(dsCode==DS_Distribution.name) dsInfo = DS_Distribution;
	if(dsCode==DS_Yield.name) dsInfo = DS_Yield;
	if(dsCode==DS_Growth.name) dsInfo = DS_Growth;
	if(dsCode==DS_Weather.name) dsInfo = DS_Weather;
	return dsInfo;
};


//所有直辖市的id
var Municipality = ['3103000019','3102000003','3102000002','3103000018','3103000295','3102000026','3102000024','3103000261'
,3103000019,3102000003,3102000002,3103000018,3103000295,3102000026,3102000024,3103000261];

// /***********************************************************************************************************************************
//  * *****************************************************系统管理URL管理部分************************************************************
//  * *********************************************************************************************************************************/

/**
 *登录页url配置
 * 登录页面：index.html
 * 检索首页为main.html
 * 后台管理首页为index.html
 * @version <1> 2018-2-2 lcw : created.
 */
var LOGIN_CONFIG = {

    //获取验证码
    verify_url : GatWay_URL + "/jh-sys/nolog/user/loginValidateCode",

    //登录
    login_url : GatWay_URL + "/jh-sys/nolog/user/loginForWeb",
    check_user_login : GatWay_URL + "/jh-sys/nolog/user/checkUserLogin",
    logout_url : GatWay_URL + "/jh-sys/nolog/user/logout",
    checkAccountExists : GatWay_URL +  "/jh-sys/nolog/user/checkAccountExists",


    updateUserPwd : GatWay_URL + "/jh-sys/permAccount/updatePwd",
    // verify_url : GatWay_URL + "index/verifyCode",
    sendsms_url : GatWay_URL + '/jh-sys/nolog/user/findValidCodeForPwd',
    checkPhoneValicode_url : GatWay_URL + "/jh-sys/nolog/user/checkPhoneCode",
    updateUserPwd_url : GatWay_URL + "/jh-sys/nolog/user/updateUserPwd",

    login_page_url : "index.html",
    welcome_page_url : "welcome.html",
    index_page_url : "home.html",
    main_page_url : "main.html",
    exhibition : "exhibition.html",
    building : "building.html",
    register_url : "register.html",

    resetUserPwd_url : GatWay_URL + "/jh-sys/permAccount/resetUserPwd",
    checkAccountCodeExists_url : GatWay_URL +  "/jh-sys/permAccount/checkAccountCodeExists"
}




//=================================================================================================//
//                         用户、菜单权限、数据权限相关方法                                        //
//=================================================================================================//

/**
 * 用于页面检查用户是否登录
 * @version <1> 2017-11-22 Hayden:Created.
 */
require(["UserModule"],function(UserModule){
	//UserModule.UserUtil.checkUserInfo();
});


