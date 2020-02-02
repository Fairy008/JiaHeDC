
// window.project_path = "http://gateway.datall.cn/";
//  window.project_path="http://192.168.1.223:8001/"

//window.project_path="http://113.57.163.74:8801/"
//  window.project_path="http://localhost:8001/"

// window.project_path="http://192.168.1.223:8001/"

window.project_path = "http://gateway.datall.cn/";

var project_path2 = "";

var project_app_path ="http://cloud.datall.cn"

/**
 * cookie信息
 * @type {{cookieName: string}}
 * 如下地方使用到了CookieName
 * 1.setCookie()
 * 2.getAccessToken()
 * 3.BaseAjax.js
 * 4.jqGrid.src.js
 */
var COOKIE_CONFIG = {
    cookieName : "AccessTokenForForum"
}

/**
 * 在线客户的QQ状态必须是在线的，否则调用不到
 * @type {{qq: string}}
 */
var QQ_ONLINE = {
    qq : "2357667118"
    // qq : "1035466203"
}


//所有直辖市的id
var Municipality = ['3103000019','3102000003','3102000002','3103000018','3103000295','3102000026','3102000024','3103000261'
    ,3103000019,3102000003,3102000002,3103000018,3103000295,3102000026,3102000024,3103000261];


/**
 *告警消息URl
 * 1.告警消息主界面
 * 2.获取告警消息条数
 * @version<1> 2018-02-05 lcw : Created.
 */
var ALARM_CONFIG = {
    alarm_page_url : project_path2 + "module/gf/alarm.html",
    alarm_count_url : project_path + "jh-datamanage/alarm/getAlarmNum",
    alarm_findByPage : project_path + "jh-datamanage/alarm/findByPage",
    read_url : project_path + "jh-datamanage/alarm/read",
    getById_url:project_path+"jh-datamanage/alarm/findById"
};


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


/*
 * 降雨数据集配置
 * @version <1> 2017-12-24 Hayden:Created.
 */
var DS_Trmm={
    id:1805,
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
    Mom_URL:project_path+"/jh-show/trmm/trmmForMon",
    //三年同比数据获取地址
    An_ThreeYear_URL:project_path+"/jh-show/trmm/queryTrmmForChart",
    //本期数据简报数据获取地址
    Briefing_URL:project_path + "/jh-show/trmm/trmmForReport",
    //时间点
    FindDatetimePoint:project_path+"/jh-show/trmm/queryTrmmTimes",
};

/*
 * 地温数据集配置
 * @version <1> 2017-12-24 Hayden:Created.
 */
var DS_T={
    id:1804,
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
    Mom_URL:project_path+"/jh-show/t/tForMon",
    //三年同比数据获取地址
    An_ThreeYear_URL:project_path+"/jh-show/t/queryTForChart",
    //本期数据简报数据获取地址
    Briefing_URL:project_path + "/jh-show/t/tForReport",
    //时间点
    FindDatetimePoint:project_path+"/jh-show/t/queryTTimes",
};


/**
 * 气温数据集配置
 * @version<1> 2018-10-15 lcw :Created.
 */
var DS_Weather = {
    id:1807,
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
    Mom_URL:project_path+"/jh-show/temp/tempForMon",
    //三年同比数据获取地址
    An_ThreeYear_URL:project_path+"/jh-show/temp/queryTempForChart",
    //本期数据简报数据获取地址
    Briefing_URL:project_path + "/jh-show/temp/tempForReport",
    //时间点
    FindDatetimePoint:project_path+"/jh-show/temp/queryTempTimes"
}
/*
 * 分布数据集配置
 * @version <1> 2017-12-24 Hayden:Created.
 */
var DS_Distribution={
    id:1801,
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
        ['棉花','#ffab00',''],
        ['棕榈','#7b5404',''],
        ['小麦','#4ce700','']
    ],
    title:'分布占比统计',
    threeTitle:'三年分布统计',
    //查询当年与前一年区域及下一级区域的作物种植面积数据,需要参数:regionId,cropId,dataTime,accuracyId
    Mom_URL:project_path+"/jh-show/distribution/distributionForPrevious",
    //查询当前区域及下一级区域的作物种植面积近三年及历年10年均值,需要参数:regionId,cropId,dataTime,accuracyId
    An_ThreeYear_URL:project_path+"/jh-show/distribution/distributionForThree",
    //首页最新报告地址
    Briefing_URL:project_path + "/jh-publish/dsReport/findTopReportList",
    //查询分布的时间点,需要参数：regionId,cropId,startDate,endDate,resolution
    FindDatetimePoint:project_path+"/jh-show/distribution/queryDistributionTimes",
    //查询重庆指定分区下在重庆市的百分占比
    FindPercent:project_path+"/jh-show/distribution/queryRegionPercentage",
    //（下载用）查询当年与前一年区域及下一级区域的作物种植面积数据,需要参数:regionId,cropId,dataTime,accuracyId
    MomAll_URL:project_path+"/jh-ds-dgy/distribution/queryAllDistributionByTime",
};

/*
 * 估产数据集配置
 * @version <1> 2017-12-24 Hayden:Created.
 */
var DS_Yield={
    id:1802,
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
    // Mom_URL:project_path+"/jh-show/yield/yieldForPrevious",
    //区域及子区域作物三年的估产及十年的估产均值,需要参数:regionId,cropId,dataTime,resolution
    An_ThreeYear_URL:project_path+"/jh-show/yield/yieldForThree",
    //首页最新报告地址
    Briefing_URL:project_path + "/jh-publish/dsReport/findTopReportList",
    //查询估产的时间点,需要参数:regionId,cropId,startDate,endDate,resolution
    FindDatetimePoint:project_path+"/jh-show/yield/queryYieldTimes",
    //查询该区域下各级区域的作物估产情况,需要参数:regionId,cropId,dataTime,resolution
    Mom_URL:project_path+"/jh-show/yield/queryYieldInRegion",
    Mom_URL_table:project_path+"/jh-show/yield/yieldForPrevious",
    //查询重庆指定分区下在重庆市的百分占比
    FindPercent:project_path+"/jh-show/yield/queryRegionPercentage",
    //下载数据用 区域及子区域作物当年与上一年的估产数据,需要参数:regionId,cropId,dataTime,resolution
    MomAll_URL:project_path+"/jh-ds-dgy/yield/queryAllYieldByTime",
};

/*
 * 长势数据集配置
 * @version <1> 2017-12-24 Hayden:Created.
 */
var DS_Growth={
    id:1803,
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
   /* Map_Legend : [
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
     Mom_URL:project_path+"/jh-ds-dgy/growth/GrowthForPrevious",
     //三年同比数据获取地址
     An_ThreeYear_URL:project_path+"/jh-ds-dgy/growth/GrowthForThree",
     //本期数据简报数据获取地址
     Briefing_URL:project_path + "/jh-ds-dgy/growth/growthForReport",*/

    //查询某时间段内的长势数据时间点,需要参数:regionId,cropId,startDate,endDate,resolution
    FindDatetimePoint:project_path+"/jh-show/growth/queryGrowthTimes",
    //查询该区域作物各种长势情况的面积分布和占比数据,需要参数：regionId,cropId,dataTime,resolution
    Mom_URL:project_path+"/jh-show/growth/queryGrowthArea",
    //查询该区域下各级各级区域的作物长势数据,需要参数：regionId,cropId,dataTime,resolution
    GrowthInRegion:project_path+"/jh-show/growth/queryGrowthInRegion",
    //(下载用)查询该区域作物各种长势情况的面积分布和占比数据,需要参数：regionId,cropId,dataTime,resolution
    MomAll_URL:project_path+"/jh-ds-dgy/growth/queryAllGrowthByTime",

};

//天气预报
var Weather_Prediction = {
    //根据区域查询区域及下一级区域的天气预报数据
    RegionAndBeyondWeather_URL:project_path+"/jh-show/weather/queryRegionAndBeyond",
    //根据区域查询该区域的天气预报数据
    RegionWeather_URL:project_path+"/jh-show/weather/queryRegionWeather",
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

/***********************************************************************************************************************************
 * *****************************************************系统管理URL管理部分************************************************************
 * *********************************************************************************************************************************/

/**
 *登录页url配置
 * 登录页面：login.html
 * 检索首页为main.html
 * 后台管理首页为index.html
 * @version <1> 2018-2-2 lcw : created.
 */
var LOGIN_CONFIG = {

    //获取验证码
    verify_url : project_path + "jh-sys/nolog/user/loginValidateCode",

    //登录
    login_url : project_path + "jh-sys/nolog/user/loginForWeb",
    check_user_login : project_path + "jh-sys/nolog/user/checkUserLogin",
    logout_url : project_path + "jh-sys/nolog/user/logout",
    checkAccountExists : project_path +  "jh-sys/nolog/user/checkAccountExists",


    updateUserPwd : project_path + "jh-sys/permAccount/updatePwd",
    // verify_url : project_path + "index/verifyCode",
    sendsms_url : project_path + 'jh-sys/nolog/user/findValidCodeForPwd',
    resetPwd_url : project_path + 'jh-sys/nolog/user/resetPwd',
    checkPhoneValicode_url : project_path + "jh-sys/nolog/user/checkPhoneCode",
    updateUserPwd_url : project_path + "jh-sys/nolog/user/updateUserPwd",

    login_page_url : project_path2 + "login.html",
    index_page_url : project_path2 + "index.html",
    cms_index_page_url : project_path2 + "admin.html",
    exhibition : project_path2 + "exhibition.html",
    building : project_path2 + "building.html",
    register_url : project_path2 + "register.html",

    resetUserPwd_url : project_path + "jh-sys/permAccount/resetUserPwd",
    checkAccountCodeExists_url : project_path +  "jh-sys/permAccount/checkAccountCodeExists",
    findForumMenu_url:project_path+'jh-sys/resource/findMenu',
    //无需登录查看用户信息
    nolog_getByAccountId_url : project_path + "jh-sys/nolog/user/getByAccountId",
}


/**
 * 注册服务
 * @type {{checkAccountExists: string}}
 */
var REGISTER_CONFIG = {
    verify_url : project_path + "jh-sys/nolog/user/regionValidateCode",
    checkAccountExists : project_path +  "jh-sys/nolog/user/checkAccountExists",
    //注册时发送手机验证码
    sendSmsCode_URL:project_path + "/jh-sys/nolog/user/findForumValidCodeForRegister",
    register_URL:project_path +"/jh-sys/nolog/user/forumRegister",
}


//用户管理URL配置中心
var USER_CONFIG = {
    findByPage_url : project_path + 'jh-sys/person/findByPage',
    checkAccountName_url : project_path + 'jh-sys/person/checkAccountName',
    add_url : project_path + 'jh-sys/person/add',
    getById_url : project_path + 'jh-sys/person/getById',
    edit_url : project_path + 'jh-sys/person/edit',
    setPersonDataStatus_url : project_path + 'jh-sys/person/setPersonDataStatus',
    findAll_url : project_path + 'jh-sys/role/findAll',
    relate_url : project_path + 'jh-sys/person/relateRole',
    getCurrentPerson_url : project_path + 'jh-sys/person/findCurrentPerson',
    queryPerson_url: project_path + 'jh-sys/person/queryPerson',
    //根据regionId查询家族祖先（到省一级）
    REGION_FAMILY_URL : project_path + "/jh-sys/region/queryRegionFamily",
    editPersonPhoto_url : project_path + 'jh-sys/person/editPersonPhoto',
    nolog_REGION_FAMILY_URL : project_path + "/jh-sys/nolog/region/queryRegionFamily",
    queryPersonByAccountId : project_path + "jh-sys/person/queryPersonByAccountId",
    no_logQueryPersonByAccountId : project_path + "jh-sys/nolog/user/queryPersonByAccountId"
}

//角色管理配置中心
var ROLE_CONFIG = {
    findByPage_url: project_path + 'jh-sys/role/findByPage',
    add_url: project_path + 'jh-sys/role/add',
    checkRoleCode_url: project_path + 'jh-sys/role/checkRoleCode',
    getById_url: project_path + 'jh-sys/role/getById',
    edit_url: project_path + 'jh-sys/role/edit',
    findAll_url: project_path + 'jh-sys/resource/findAll',
    relateResource_url : project_path + 'jh-sys/role/relateResource'
}

//资源管理配置中心
var RESOURCE_CONFIG = {
    findByPid_url : project_path + 'jh-sys/resource/findResourceByPid',
    findByPage_url : project_path + 'jh-sys/resource/findByPage',
    findAll_url: project_path + 'jh-sys/resource/findAll',
    findAllResource_url: project_path + 'jh-sys/resource/findAllResource',
    queryDictsByParentId_url : project_path + 'jh-sys/dict/queryDictByParentId',
    add_url : project_path + 'jh-sys/resource/add',
    checkResCode_url : project_path + 'jh-sys/resource/checkResCode',
    getById_url : project_path + 'jh-sys/resource/getById',
    del_url : project_path + 'jh-sys/resource/delete',
    edit_url : project_path + 'jh-sys/resource/edit',
    findMenu_url:project_path+'jh-sys/resource/findMenu',
    findButton_url:project_path + 'jh-sys/resource/findButton',
    checkResLeaf_url:project_path+'jh-sys/resource/checkResleaf'
}


/**
 * 字典配置中心
 *
 * @version <1> 2018-02-06 djh： Created.
 */
var DICT_COFING = {
    dataType_url: project_path + 'jh-sys/dict/queryDataType',
    downloadStatus_url: project_path + 'jh-sys/dict/queryDownloadStatus',//查询所有的下载状态
    queryDictList_url: project_path + 'jh-sys/dict/queryDictList',//初始化查询数据字典所有数据
    queryDictByParentId_url: project_path + 'jh-sys/dict/queryDictByParentId',//根据父ID查询子节点
    queryDictById_url: project_path + 'jh-sys/dict/queryDictById',//根据id查询数据字典详情
    saveDict_url: project_path + 'jh-sys/dict/saveDict',//添加数据字典
    editDict_url: project_path + 'jh-sys/dict/editDict',//修改数据字典
    delDict_url: project_path + 'jh-sys/dict/delDict', //删除数据字典
    queryDictByCode_url: project_path + 'jh-sys/dict/queryDictByCode', //初始化根据编码查询出id，然后根据查询出的id匹配父ID查询出所有字典信息
    searchTypeUrl: project_path + 'jh-sys/dict/querySubDictListByParentId',
    checkDictCode_url:project_path+'jh-sys/dict/checkDictCode',
    findCropByRegion_url:project_path + 'jh-sys/dict/findCropByRegionId',
    queryDictListByPid_url: project_path + "jh-sys/dict/queryDictListByPid",
    /*  checkDictIdIsExistsUrl : project_path + 'dict/checkDictIdIsExists',
     checkDictCodeIsExistsUrl : project_path + 'dict/checkDictCodeIsExists'*/
    queryResolutionListByDataSetId_url: project_path + 'jh-sys/dataSetAndResolution/queryResolutionListByDataSetId',
    queryResolutionListByDSId_url: project_path + 'jh-sys/dataSetAndResolution/queryResolutionListByDSId',
    addDatasetAndResolutionData_url: project_path + 'jh-sys/dataSetAndResolution/addDatasetAndResolutionData',
    checkKeyword_url: project_path + 'jh-sys/nolog/dict/checkKeyWord'

}


/**
 * 区域配置中心
 *
 * @version <1> 2018-02-10 djh： Created.
 */
var REGION_CONFIG = {
    checkRegionCodeIsExistsUrl : project_path + 'jh-sys/region/checkRegionCodeIsExists',
    findRegion_url : project_path + 'jh-sys/region/regionList',
    findRegionForReport_url:project_path+"jh-sys/region/regionListForReport",
    queryFirstLevelInitRegionList_url : project_path + "jh-sys/region/queryFirstLevelInitRegionList",
    querySubRegionListByParentId_url : project_path + "jh-sys/region/querySubRegionListByParentId",
    queryInitRegionByInitRegionId_url : project_path + "jh-sys/region/queryRegionById",
    saveInitRegion_url : project_path + "jh-sys/region/saveInitRegion",
    updateInitRegionByInitRegionId_url : project_path + "jh-sys/region/updateInitRegionByInitRegionId",
    deleteInitRegionByInitRegionId_url : project_path + "jh-sys/region/deleteInitRegionByInitRegionId",
    findAll_url : project_path + "jh-sys/region/findAll",
    findRegionInChina_url : project_path + "jh-sys/region/findRegionInChina",
    //获取用户权限区域列表（分级显示）
    Region_URL:project_path+"/jh-sys/region/userRegionList",
    //获取用户权限区域列表（分级显示）
    findRegionsByPid_URL:project_path+"/jh-sys/region/regionList",
}



var LOGS_CONFIG = {
    findByPage_url : project_path + "jh-sys/logs/findByPage"
}

var REDIS_CONFIG = {
    getCacheTime_url : project_path + "jh-sys/nolog/cache/getCacheLastTime",
    findType_url : project_path + "jh-sys/nolog/cache/findType",
    initCache_url : project_path + "jh-sys/nolog/cache/",
    initDictCache_url : project_path + "jh-sys/nolog/cache/initDictCache",
    initRegionCache_url : project_path + "jh-sys/nolog/cache/initRegionCache",
    initUserCache_url : project_path + "jh-sys/nolog/cache/initUserCache",
    initAllDateCache_url : project_path + "jh-sys/nolog/cache/initAllDateCache",
    findSubListByDictId_url : project_path + "jh-sys/nolog/cache/findSubListByDictId",
    findSubListByRegionId_url : project_path + "jh-sys/nolog/cache/findSubListByRegionId",
    queryNameByDictId_url : project_path + "jh-sys/nolog/cache/queryNameByDictId",
    queryNameByRegionId_url : project_path + "jh-sys/nolog/cache/queryNameByRegionId",
    queryNameByAccountId_url : project_path + "jh-sys/nolog/cache/queryNameByAccountId",
    queryCodeByDictId_url : project_path + "jh-sys/nolog/cache/queryCodeByDictId",
    queryCodeByRegionId_url : project_path + "jh-sys/nolog/cache/queryCodeByRegionId",

    findAllLeaf_url: project_path + "jh-sys/nolog/cache/queryAllLeaf",

}




/***********************************************************************************************************************************
 * *****************************************************图层管理URL管理部分************************************************************
 * *********************************************************************************************************************************/

/**
 * 数据集图层明细
 * @version<1> 2018-05-14 wl: Created.
 */
var DS_LAYER = {
    findLayerPageInfo : project_path + 'jh-map/layer/findLayerPageInfo',
    findById_url : project_path + 'jh-map/layer/findLayerById',
    delete_url : project_path + 'jh-map/layer/delete',
    edit_url : project_path + 'jh-map/layer/edit',
    publish_url : project_path + 'jh-map/layer/publish',
    modifyStyle_url : project_path + 'jh-map/layer/modifyStyle',
    findLayerByProductIds_url : project_path + 'jh-map/layer/findLayerByProductIds'
};



/**
 *图层数据集地址
 *@version <1> 2019-03-11 cxw:Created.
 */
var FORUM__Layer = {
    name:"layer",
    //图层查询地址
    Layer_Query_URL : project_path+ "/jh-map/layer/findLayer",
    Layer_Init_URL : project_path + "/jh-map/layer",
    nolog_Layer_Init_URL : project_path + "/jh-map/nolog/layer",
};
/**
 * Description: 样式管理
 * @version <1> 2018/6/19 15:24 zhangshen: Created.
 */
var DS_GEO_STYLE = {
    findDsGeoStylePageInfo_url : project_path + 'jh-map/dsGeoStyle/findDsGeoStylePageInfo',
    findGeoWorkspaceList_url : project_path + 'jh-map/dsGeoStyle/findGeoWorkspaceList',
    findDsGeoStyleById_url : project_path + 'jh-map/dsGeoStyle/findDsGeoStyleById',
    saveStyleInfo_url : project_path + 'jh-map/dsGeoStyle/saveStyleInfo',
    batchDeleteStyle_url : project_path + 'jh-map/dsGeoStyle/batchDeleteStyle',
    getGeoStyleList_url : project_path + "jh-map/dsGeoStyle/getGeoStyleList",
    findDsGeoStyleByName_url : project_path + 'jh-map/dsGeoStyle/findDsGeoStyleByName'
}



/**
 * 区域作物配置
 */
var REGION_CROP_CONFIG = {
    crop_url:project_path+"jh-sys/regionAndCrop/queryCropByRegionId",
    add_regionAndcrop_url:project_path+"jh-sys/regionAndCrop/addRegionAndCropRelateData",
}

/**
 * 标签
 * @version <1> 2019/3/7 15:18 zhangshen:Created.
 */
var FORUM_LABEL = {
    nolog_findLabelList_url : project_path + "jh-forum/nolog/label/findLabelList",
    nolog_findAllLabel_url : project_path + "jh-forum/nolog/label/findAllLabel",
    //随机查询标签列表
    nolog_findRandomLabelList_url : project_path + "jh-forum/nolog/label/findRandomLabelList",
};

/**
 * 关注点赞
 * @version <1> 2019/3/7 15:18 zhangshen:Created.
 */
var FORUM_FOLLOW = {
    //新增关注或点赞
    addFollow_url : project_path + "jh-forum/follow/addFollow",
    //取消关注或点赞
    removeFollow_url : project_path + "jh-forum/follow/removeFollow",
    //查询关注或点赞列表
    findByPage_url : project_path + "jh-forum/follow/findByPage",

    //我的关注列表（关注的系统用户）
    findMyFollowPageInfo_url : project_path + "/jh-forum/follow/findMyFollowsByPage",
};

/**
 * 帖子
 * @version <1> 2019/3/7 15:18 zhangshen:Created.
 */
var FORUM_ARTICLE = {
    //查询最热帖子(无需登陆)
    nolog_findHotArticleList_url : project_path + "jh-forum/nolog/article/findHotArticleList",
    //查询最新帖子(无需登陆)
    nolog_findNewArticleList_url : project_path + "jh-forum/nolog/article/findNewArticleList",
    ////分页查询帖子(无需登陆)
    nolog_findCombinationPage_url : project_path + "jh-forum/nolog/article/findCombinationPage",
    //查询帖子详情(无需登陆)
    nolog_findArticleInfo_url : project_path + "jh-forum/nolog/article/findArticleInfo",
    //查询帖子的点赞与收藏
    nolog_articleFollow_url : project_path + "jh-forum/nolog/article/findArticleFollows",
    //新增帖子（报告/问答/数据分享）
    addArticle_url : project_path + "jh-forum/article/addArticle",
    //删除帖子
    deleteArticle_url : project_path + "jh-forum/article/deleteArticle",
    //编辑帖子
    editArticle_url : project_path + "jh-forum/article/editArticle",
    //分页查询帖子
    findCombinationPage_url : project_path + "jh-forum/article/findCombinationPage",
    //我的收藏分页查询帖子
    findListByFollower_url : project_path + "jh-forum/article/findListByFollower",
    //查询帖子详情
    findArticleInfoByAccountId_url : project_path + "jh-forum/article/findArticleInfoByAccountId"

};

/**
 * 专家
 * @version <1> 2019/3/7 15:18 zhangshen:Created.
 */
var FORUM_EXPERT = {
    //查询随机5名专家(无需登陆)
    nolog_findRandomExpert_url : project_path + "jh-forum/nolog/expert/findRandomExpert",
    //查询专家详情(无需登陆)
    nolog_findExpertInfo_url : project_path + "jh-forum/nolog/expert/findExpertInfo",
    //分页查询专家列表(无需登陆)
    nolog_findExpertList_url : project_path + "jh-forum/nolog/expert/findExpertList",
    //根据专家accountId查询专家详情
    nolog_findExpertByAccountId_url : project_path + "jh-forum/nolog/expert/findExpertByAccountId",
    //新增专家
    addExpert_url : project_path + "jh-forum/expert/addExpert",
    //删除专家
    deleteExpert_url : project_path + "jh-forum/expert/deleteExpert",
    //编辑专家信息
    editExpert_url : project_path + "jh-forum/expert/editExpert",
    //编辑专家基础信息
    editExpertByAccount_url : project_path + "jh-forum/expert/updateByAccount",
    //查询专家详情
    findExpertInfo_url : project_path + "jh-forum/expert/findExpertInfo",
    //分页查询专家列表
    findExpertList_url : project_path + "jh-forum/expert/findExpertList",
    //查询随机5名专家
    findRandomExpert_url : project_path + "jh-forum/expert/findRandomExpert",
};

/**
 * 后台管理
 * @version <1> 2019/3/7 15:18 lijie:Created.
 */
var CMS_MANAGE = {
    //删除帖子
    deleteArticle_url : project_path + "jh-forum/articleManage/deleteArticle",
    //编辑帖子
    editArticle_url : project_path + "jh-forum/articleManage/editArticle",
    //查询帖子详情
    findArticleInfo_url : project_path + "jh-forum/articleManage/findById",
    //分页查询帖子列表
    findPage_url : project_path + "jh-forum/articleManage/findPage",
    //审核通过或驳回帖子
    auditArticleList_url : project_path + "jh-forum/articleManage/auditArticle",
    //设置最新或最热
    signNewArticle_url : project_path + "jh-forum/articleManage/signNewArticle",
    //设置最新或最热
    signHotArticle_url : project_path + "jh-forum/articleManage/signHotArticle",
    //新增专家
    addExpert_url : project_path + "jh-forum/expertManage/addExpert",
    //删除专家
    deleteExpert_url : project_path + "jh-forum/expertManage/deleteExpert",
    //编辑专家信息
    editExpert_url : project_path + "jh-forum/expertManage/editExpert",
    //查询专家详情
    findExpertInfo_url : project_path + "jh-forum/expertManage/findExpertInfo",
    //分页查询专家列表
    findExpertList_url : project_path + "jh-forum/expertManage/findExpertList",
    //查看头像
    findExpertPhoto_url : project_path + "jh-forum/expertManage/previewPhoto",
    //验证是否有后台管理权限
    findUserCms_url : project_path + "jh-forum/userManage/isExistForumRole",
    //新增标签
    addLabel_url : project_path + "jh-forum/label/addLabel",
    //删除标签
    deleteLabel_url : project_path + "jh-forum/label/deleteLabel",
    //编辑标签信息
    editLabel_url : project_path + "jh-forum/label/editLabel",
    //查询标签详情
    findLabelInfo_url : project_path + "jh-forum/label/getLabelDetail",
    //分页查询标签列表
    findLabelList_url : project_path + "jh-forum/label/findLabelListCms",
    //检查标签名称
    findLabelName_url : project_path + "jh-forum/label/getListByLabelName",
    //查询自定义数据订单数据，并支持分页
    findCustomDataOrder_url : project_path + "jh-forum/orderData/findPageCms",
    //查询定制订单数据
    findCustomDataOrderInfo_url : project_path + "jh-forum/orderData/findOrder",
    //定制订单数据
    findCustomDataOrderHandler_url : project_path + "jh-forum/orderData/handlerOrder",
    //支付接口
    alipay_url : project_path + "jh-forum/order/alipay",

    //支付查询接口
    updateByPay_url : project_path + "jh-forum/order/updateByPay",

    //查询免费下载样例次数
    freeDownloadData_url :  project_path + "jh-forum/freeDownload/countFree",

    //记录免费下载
    freeDown_url : project_path + "jh-forum/freeDownload/save",

   //下载商品链接
    downloadExample_url :  project_path + "jh-forum/downloadData/downloadExample",

    //新增数据收藏
    addDataCollection_url: project_path + "jh-forum/collect/collectData",

    //删除数据收藏
    removeDataCollection_url: project_path + "jh-forum/collect/cancelData",

    //检查收藏
    checkCollection_url:project_path + "jh-forum/collect/checkCollectStatus",

    //查询收藏结果
    queryMyCollect_url :project_path + "jh-forum/collect/queryMyCollect",



};

/**
 * comment
 * @version <1> 2019/3/7 15:18 zhangshen:Created.
 */
var FORUM_COMMENT = {
    upFile : project_path + "zuul/jh-forum/config",
    //无需登录查看帖子的一级评论
    nolog_queryCommentsByPage_url : project_path + 'jh-forum//nolog/comment/queryCommentsByPage',
    //无需登录，根据帖子的一级评论id，查询该评论下的所有二级评论
    nolog_findCommentsByFirstCommentId_url : project_path + 'jh-forum//nolog/comment/findCommentsByFirstCommentId',
    //新增一级评论
    addComment_url : project_path + 'jh-forum/comment/addComment',
    //新增二级评论
    addCommentInFirstCommentId : project_path + 'jh-forum/comment/addCommentInFirstCommentId',
    //根据评论id删除评论
    deleteComment_url : project_path + 'jh-forum/comment/deleteComment',
};


/**
 * 采集任务
 * @version <1> 2019/3/11 13:18 xiayong:Created.
 */
var COLLECTION_TASK = {
    indexShowTaskInfoList_url : project_path + "jh-forum/collection/taskInfo/updateIndexShow",
    findCollectionTaskListByPhone_url : project_path + "jh-forum/collection/taskInfo/queryCollectDataList",

    findCollectionTaskList_url : project_path + "jh-forum/collection/taskInfo/findByPage",
    findCollectionTaskApiList_url : project_path + "jh-forum/collection/taskInfo/findApiByPage",
    findTemplateList_url : project_path + "jh-forum/collection/template/findApiByPage",
    findTaskItemList_url : project_path + "jh-forum/collection/taskItem/findApiByPage",


    delTemplateById_url : project_path + "jh-forum/collection/template/delete",
    getTemplateById_url : project_path + "jh-forum/collection/template/getById",
    updateTemplateList_url : project_path + "jh-forum/collection/template/update",
    addTemplateList_url : project_path + "jh-forum/collection/template/add",
    queryTemplateList_url : project_path + "jh-forum/collection/template/findByPage",

    queryTaskInfoList_url : project_path + "jh-forum/collection/taskInfo/findByPage",
    addTaskInfoList_url : project_path + "jh-forum/collection/taskInfo/add",
    updateTaskInfoList_url : project_path + "jh-forum/collection/taskInfo/update",
    getTaskInfoeById_url : project_path + "jh-forum/collection/taskInfo/getById",
    delTaskInfoById_url : project_path + "jh-forum/collection/taskInfo/delete",
    exportTaskInfoById_url : project_path + "jh-forum/collection/taskInfo/export",
    // exportZipTaskInfoById_url : project_path + "jh-forum/collection/taskInfo/downloadZip",
    exportZipTaskInfoById_url : project_path + "jh-forum/collection/taskInfo/downloadMedia",

    queryTaskItemList_url : project_path + "jh-forum/collection/taskItem/findByPage",
    addTaskItemList_url : project_path + "jh-forum/collection/taskItem/add",
    updateTaskItemList_url : project_path + "jh-forum/collection/taskItem/update",
    getTaskItemeById_url : project_path + "jh-forum/collection/taskItem/getById",
    delTaskItemById_url : project_path + "jh-forum/collection/taskItem/delete",

    getCoordinates_url : project_path + "jh-forum/collection/taskItem/getCoordinates",
    getCoordinatesByTaskId_url : project_path + "jh-forum/collection/taskItem/getCoordinatesByTaskId",
    queryDistributionHistory_url : project_path + "jh-forum/collection/analysis/queryDistributionHistory",

    getMediaResource_url:project_path + "jh-forum/collection/media/",
    downloadMeidaById_url:project_path + "jh-forum/collection/taskInfo/",
    delMediaById_url : project_path + "jh-forum/collection/media/delete",

    //数据报告分析
    addDataReport_url : project_path + "jh-forum/collection/report/add",
    updataDataReport_url : project_path + "jh-forum/collection/report/update",
    queryDataReportList_url : project_path + "jh-forum/collection/report/findByPage",
    queryDataReportDetail_url : project_path + "jh-forum/collection/report/queryReportDetail",
    queryDataReportReply_url : project_path + "jh-forum/collection/reportReply/findByPage",

    addReportReply_url : project_path + "jh-forum/collection/reportReply/add",
    addReportFollow_url : project_path + "jh-forum/collection/report/reportFollow",
    cancelReportFollow_url : project_path + "jh-forum/collection/report/cancelReportFollow",
    addReportPublish_url : project_path + "jh-forum/collection/report/reportPublish",
    cancelReportPublish_url : project_path + "jh-forum/collection/report/cancelReportPublish",
    reportFabulous_url : project_path + "jh-forum/collection/report/reportFabulous",

    replyFabulous_url : project_path + "jh-forum/collection/reportReply/replyFabulous",

    addSubReply_url : project_path + "jh-forum/collection/reportReply/reply",
    getSubReplyByParentId_url : project_path + "jh-forum/collection/reportReply/getSubReplyByParentId",
    isFollow_url : project_path + "jh-forum/collection/report/isFollow",

    nolog_findCollectionTaskList_url : project_path + "jh-forum/nolog/collection/taskInfo/findByPage",
    nolog_getTaskInfoeById_url : project_path + "jh-forum/nolog/collection/taskInfo/getById",
    nolog_findTaskItemList_url : project_path + "jh-forum/nolog/collection/taskItem/findApiByPage",
    nolog_queryDistributionHistory_url : project_path + "jh-forum/nolog/collection/analysis/queryDistributionHistory",
    nolog_getTaskItemeById_url : project_path + "jh-forum/nolog/collection/taskItem/getById",
    nolog_downloadMeidaById_url:project_path + "jh-forum/nolog/collection/taskInfo/",
}

/**
 * 写报告数据集查询
 * @type {{checkAccountExists: string}}
 */
var REPORT_CONFIG = {
    dataset_url : project_path + "jh-forum/dataSet/queryDsList", //遥感数据查询
    collectionTask_url : project_path + "jh-forum/collection/taskInfo/queryCollectDatas",
    cltTask_url : project_path + "jh-forum/cltTaskInfo/queryCltTasks"//调研任务查询URL
};

/*
 * 报告数据集配置
 * @version <1> 2017-12-24 Hayden:Created.
 */
var DS_Report={
    id:'1808',
    name:"report",
    chinaName:"报告",
    //最新报告，前15条最新报告获取地址
    Report_New_URL: project_path + "/jh-publish/dsReport/findTopReportList",
    //报告查询地址
    Report_Query_URL:project_path+"/",
    //报告下载地址
    REPORT_DOWNLOAD_URL:project_path + "/jh-publish/dsReport/reportDown",
    //查询报告
    Report_list_url : project_path + "/jh-show/dsReport/queryReportPage",
    //报告预览
    REPORT_PREVIEW_URL:project_path + "/jh-show/dsReport/previewReportPdf"
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
    Brief_list_url:project_path + "/jh-show/nolog/briefingReport/findBriefPage",
    //简报详情
    Brief_info_url:project_path +"/jh-show/nolog/briefingReport/findBriefReportById",
    //预览简报url
    Preview_brief_url:project_path +"/jh-show/nolog/briefingReport/previewBriefReportByIdNew",
    //分享简报url
    Trmm_brief_url:project_path +"/jh-show/nolog/briefingReport/previewBriefReportByIdTrrm"
}

/**
 * 采集任务新版
 * @version <1> 2019/3/11 13:18 lijie:Created.
 */
var COLLECTION_TASK_NEW = {
    // 后台模板
    getTemplateById_url : project_path + "jh-forum/cltTemplateManage/getById",
    addTemplateList_url : project_path + "jh-forum/cltTemplateManage/createCltTemplate",
    updateTemplateList_url : project_path + "jh-forum/cltTemplateManage/updateCltTemplate",
    delTemplateById_url : project_path + "jh-forum/cltTemplateManage/deleteCltTemplate",
    findByPage_url : project_path + "jh-forum/cltTemplateManage/findByPage",
    findCltTemplateList_url : project_path + "jh-forum/cltTemplateManage/findCltTemplateList",
    validTemplateName_url : project_path + "jh-forum/cltTemplateManage/validTemplateName",

    // 个人中心我的模板
    my_getTemplateById_url : project_path + "jh-forum/myTemplate/getById",
    my_addTemplateList_url : project_path + "jh-forum/myTemplate/createCltTemplate",
    my_updateTemplateList_url : project_path + "jh-forum/myTemplate/updateCltTemplate",
    my_delTemplateById_url : project_path + "jh-forum/myTemplate/deleteCltTemplate",
    my_findByPage_url : project_path + "jh-forum/myTemplate/findByPage",
    my_findCltTemplateList_url : project_path + "jh-forum/myTemplate/findCltTemplateList",
    my_validTemplateName_url : project_path + "jh-forum/myTemplate/validTemplateName",


    // 任务
    getTaskById_url : project_path + "jh-forum/cltTaskInfoManage/getCltTaskInfoByTaskId",
    addTaskList_url : project_path + "jh-forum/cltTaskInfoManage/createTaskInfo",
    updateTaskList_url : project_path + "jh-forum/cltTaskInfoManage/updateTaskInfoByTaskId",
    delTaskById_url : project_path + "jh-forum/cltTaskInfoManage/deleteTaskInfoByTaskId",
    delTaskByIds_url : project_path + "jh-forum/cltTaskInfoManage/deleteTaskInfoByTaskIds",
    findTaskByPage_url : project_path + "jh-forum/cltTaskInfoManage/findPage",
    indexShowTaskInfoList_url : project_path + "jh-forum/cltTaskInfoManage/updateIndexShow",
    updateTaskStatusByTaskId_url : project_path + "jh-forum/cltTaskInfoManage/updateTaskStatusByTaskId",
    findCltTaskUserList_url : project_path + "jh-forum/cltTaskInfoManage/findCltTaskUserList",
    findMyFollowList_url : project_path + "jh-forum/cltTaskInfoManage/findMyFollowList",
    auditTaskList_url :project_path + "jh-forum/cltTaskInfoManage/auditTaskList",
    getIndexShowNum_url :project_path + "jh-forum/cltTaskInfoManage/getIndexShowNum",


    //数据明细
    findTaskItemByItemId_url : project_path + "jh-forum/cltTaskItemManage/findTaskItemByItemId",
    findTaskItemPageInfo_url : project_path + "jh-forum/cltTaskItemManage/findTaskItemPageInfo",
    findTaskItemList_url : project_path + "jh-forum/cltTaskItemManage/findTaskItemList",
    deleteTaskItemByItemId_url : project_path + "jh-forum/cltTaskItemManage/deleteTaskItemByItemId",
    updateTaskItemByItemId_url : project_path + "jh-forum/cltTaskItemManage/updateTaskItemByItemId",
    createTaskItem_url : project_path + "jh-forum/cltTaskItemManage/createTaskItem",

    //媒体资源
    deleteMediaSourceById_url: project_path + "jh-forum/cltMediaSource/deleteMediaSourceById",
    exportTaskInfoById_url : project_path + "jh-forum/cltTaskInfoManage/export",
    downloadMediaById_url : project_path + "jh-forum/cltTaskInfoManage/downloadMedia",

    // 无需登录
    nolog_findTaskByPage_url : project_path + "jh-forum/nolog/collectionTask/findPage",
    nolog_getTaskById_url : project_path + "jh-forum/nolog/collectionTask/getCltTaskInfoByTaskId",
    nolog_findTaskItemByItemId_url : project_path + "jh-forum/nolog/collectionTask/findTaskItemByItemId",
    nolog_findTaskItemList_url : project_path + "jh-forum/nolog/collectionTask/findTaskItemList",

    //我的任务
    my_getTaskById_url : project_path + "jh-forum/myCollectionTask/getCltTaskInfoByTaskId",
    my_addTaskList_url : project_path + "jh-forum/myCollectionTask/createTaskInfo",
    my_updateTaskList_url : project_path + "jh-forum/myCollectionTask/updateTaskInfoByTaskId",
    my_findTaskByPage_url : project_path + "jh-forum/myCollectionTask/findPage",
    my_updateTaskStatusByTaskId_url : project_path + "jh-forum/myCollectionTask/updateTaskStatusByTaskId",
    my_findCltTaskUserList_url : project_path + "jh-forum/myCollectionTask/findCltTaskUserList",
    my_findMyFollowList_url : project_path + "jh-forum/myCollectionTask/findMyFollowList",
    my_findTaskList_url : project_path + "jh-forum/myCollectionTask/findTaskList",

}


/**微信相关**/
var weChat = {
    getWechatParam_url : project_path + "jh-forum/nolog/weChat/getWechatParam",
}


var download_config = {
    DOWNLOD_URL : "/a/"
}


/**
 * 定制数据URL
 *
 * @type {{save_url: string}}
 */
var ORDERDATA_CONFIG = {
    save_url : project_path + "jh-forum/orderData/save",
    findByPage_url : project_path + "jh-forum/orderData/findByPage"


}

/**
 * 可下载数据URL
 * @type {{findByPage_url: string}}
 */
var DOWNLOADDATA_CONFIG = {
    findCmsPage_url : project_path + "jh-forum/downloadData/findCmsPage",
    findByPage_url : project_path + "jh-forum/nolog/downloadData/findByPage",
    getById_url : project_path + "jh-forum/nolog/downloadData/getById",
    findHotDataList_url : project_path + "jh-forum/nolog/downloadData/findHotDataList",
    saveDownloadData_url : project_path + "jh-forum/downloadData/saveDownloadData",
    findDownloadDataInfo_url : project_path + "jh-forum/downloadData/findDownloadData",
    deleteDownload_url : project_path + "jh-forum/downloadData/delDownloadData",
    audit_url : project_path + "jh-forum/downloadData/audit",
    findAllByPage_url : project_path + "jh-forum/nolog/downloadData/findAllByPage",
    findAgriculturalData_url : project_path + "jh-forum/nolog/downloadData/findAgriculturalData",
    getSimilarData_url : project_path + "jh-forum/nolog/downloadData/getSimilarData",
    confirmationOfOrder_url : project_path + "jh-forum/downloadData/confirmationOfOrder",
    downloadImage_url : project_path + "jh-forum/downloadData/downloadImage"
}


/*
   查询搜索热词
 */
var KEYWORDS_CONFIG = {
    getKeywords_url : project_path + "jh-forum/nolog/keyWords/getKeyWords"
}

/*
* 添加到购物车
* */
var SHOPPING_CONFIG = {
    addData_url : project_path + "jh-forum/shoppingCar/addShoppingCar",
    queryShoppingCar_url : project_path + "jh-forum/shoppingCar/queryShoppingCar",
    delShoppingCarRecord_url : project_path + "jh-forum/shoppingCar/deleteShoppingCar"
}

var ORDERS_CONFIG = {
    orders_url : project_path + "jh-forum/orders/createOrder",
    findOrders_url : project_path + "jh-forum/orders/findByType",
    delete_url : project_path + "jh-forum/orders/deleteOrder",
    //查询前台用户的下载订单
    queryDataGoods_url :project_path + "jh-forum/orders/findAllOrder"
}

var ORDER_DETAILS_CONFIG = {
    queryOrderDetails_url : project_path + "jh-forum/orderDetails/findOrderDetails"
}

var PAY_CONFIG = {
    pay_url : project_path + "jh-forum/alipay/pay",
    auth_url : project_path + "jh-forum/nolog/alipay/return"
}

var DATA_STORAGE ={
    download_suffix:"/download/"
}


var app_path = project_app_path+download_config.DOWNLOD_URL+"data/forum/apk/JiaHeForum.apk";