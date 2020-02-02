/**
 * 全局文件： require管理js
 * @version<1> 2018-01-13 cxj: Created.
 */
require.config({
    baseUrl:'',
    map: {
        '*': {
            'css': 'js/lib/require-css/css.min'
        }
    },
    paths:{
        jquery:["js/lib/jquery/jquery-1.8.3.min","https://cdn.bootcss.com/jquery/3.2.1/jquery.min"],
        ol:["js/lib/ol/ol","https://openlayers.org/en/v4.3.2/build/ol"],
        BaseAjax:["js/lib/module/BaseAjax"],
        RegionModule:["js/lib/module/RegionModule"],
        MapModule:["js/lib/module/MapModule"],
        jqGrid:["js/lib/jqGrid-4.4.3/js/jquery.jqGrid.src"],
        jqGridLocale:["js/lib/jqGrid-4.4.3/js/i18n/grid.locale-cn"],
        jqueryUi:["js/lib/jquery-ui/jquery-ui.min"],
        dateUtil:["js/lib/dateUtil/dateUtil"],
        jedate:["js/lib/dateUtil/jquery.jedate.min"],
        ztree:["js/lib/ztree/jquery.ztree.all.min"],
        Base64:["js/lib/security/Base64"],
        login:["js/login/login"],
        map:["js/map/map"],
        formVerfication:["js/lib/module/formVerfication"],
        PopWin:["js/lib/module/PopWin"],
        commons:["js/lib/module/commons"],
        fly:["js/lib/jquery/jquery.fly.min"],
        custom_settings:["js/custom_settings"],
        multiselect:["js/lib/jq-multiselect/js/jquery.multi-select"],
        echarts:["js/lib/echarts/echarts"],
        jqExtends:["js/lib/jquery/jquery-extend/jqueryExtend"],
        tableExporter:["js/lib/jquery/tableExporter"],
        jsGrid:["js/lib/jsgrid-1.5.3/jsgrid.min"],
        UserModule:["js/lib/module/UserModule"],
        //枚举常量配置类
        enums:["js/enums_config"],

    },
    shim:{
        ol:["css!js/lib/ol/ol.css"],
        jedate:["css!js/lib/dateUtil/jedate.css"],
        jqGridLocale:["jquery"],
        jqGrid: {
            deps: [ "css!js/lib/jqGrid-4.4.3/css/ui.jqgrid.css","css!js/lib/jquery-ui/themes/hot-sneaks/jquery-ui.css","jqGridLocale"],
            exports: "jqGrid"
        },
        ztree: {
            deps: ["css!js/lib/ztree/zTreeStyle.css","css!js/lib/ztree/metroStyle.css"],
            exports:"ztree"
        },
        login:["css!css/login/login.css"],
        map:["css!css/map/map.css"],
        BaseAjax:{
            exports:"BaseAjax"
        },
        formVerfication:{
            exports:"formVerfication"
        },
        fly:{
            exports:"fly"
        },
        multiselect:{
            deps: ["css!js/lib/jq-multiselect/css/multi-select.css"],
            exports:"multiselect"
        },
        jqExtends:{
            exports:"jqExtends"
        },
        UserModule:{
            exports:"UserModule"
        },
        enums:{
            exports:"enums"
        }
    }
});

/**
 * 跳转到首页
 * 目前BaseAjax和jquery.jqGrid.src用到此方法
 * @version <1> 2018-02-02 cxj:Created.
 */
function toIndexPage(data){
    if(data && data.code && data.code == "00000002"){
        // window.location.href = LOGIN_CONFIG.welcome_page_url;
        window.location.href = LOGIN_CONFIG.login_page_url;
        return false;
    }
    return true;
}

/**
 *日期格式化
 *fmt  : 日期格式
 * @version <1> 2017-11-07 Hayden:Created.
 */
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

// window.project_path = "http://gateway.datall.cn/";
 // window.project_path="http://192.168.1.15:8001/"

// window.project_path="http://113.57.163.74:8801/"
window.project_path="http://192.168.1.227:8001/"
var project_path2 = "";


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
    cookieName : "AccessTokenForWeb"
}

/**
 * 数据管理模块
 * @version<1> 2018-05-02 zhangshen: Created.
 */
var DATA_MANAGEMENT_CONFIG = {
    findDataResultChangeByPage_url:project_path + 'dataResult/findDataResultChangeByPage',
    dataReprocessDown_url:project_path + 'dataReprocess/down',
    dataStaticDown_url:project_path + 'dataStatic/down'
};


/**
 * 数据集图层明细
 * @version<1> 2018-06-07 xzg: Created.
 */
var DATA_PRODUCT = {
    find_dataProduct : project_path + 'jh-sys/dataProduct/queryProduct',
    del_dataProduct : project_path + 'jh-sys/dataProduct/delProduct',
    add_dataProduct : project_path + 'jh-sys/dataProduct/saveProduct',
    setting_defaultShow : project_path + 'jh-sys/dataProduct/settingDefaultShow'
}





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
    checkPhoneValicode_url : project_path + "jh-sys/nolog/user/checkPhoneCode",
    updateUserPwd_url : project_path + "jh-sys/nolog/user/updateUserPwd",

    login_page_url : project_path2 + "login.html",
    welcome_page_url : project_path2 + "welcome.html",
    index_page_url : project_path2 + "index.html",
    main_page_url : project_path2 + "main.html",
    exhibition : project_path2 + "exhibition.html",
    building : project_path2 + "building.html",
    register_url : project_path2 + "register.html",

    resetUserPwd_url : project_path + "jh-sys/permAccount/resetUserPwd",
    checkAccountCodeExists_url : project_path +  "jh-sys/permAccount/checkAccountCodeExists"
}


/**
 * 注册服务
 * @type {{checkAccountExists: string}}
 */
var REGISTER_CONFIG = {
    verify_url : project_path + "jh-sys/nolog/user/regionValidateCode",
    checkAccountExists : project_path +  "jh-sys/nolog/user/checkAccountExists",
    //注册时发送手机验证码
    sendSmsCode_URL:project_path + "/jh-sys/nolog/user/findValidCodeForRegister",
    register_URL:project_path +"/jh-sys/nolog/user/register",
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
    searchTypeUrl: project_path + 'jh-sys/dict/querySubDictListByParentId',
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
    addDatasetAndResolutionData_url: project_path + 'jh-sys/dataSetAndResolution/addDatasetAndResolutionData'

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
    findRegionInChina_url : project_path + "jh-sys/region/findRegionInChina"

}



var LOGS_CONFIG = {
    findByPage_url : project_path + "jh-sys/logs/findByPage"
}

var REDIS_CONFIG = {
    getCacheTime_url : project_path + "jh-sys/nolog/cache/getCacheLastTime",
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



/***********************************************************************************************************************************
 * *****************************************************数据管理URL管理部分************************************************************
 * *********************************************************************************************************************************/



 /**
 * 数据共享：订单管理配置中心
 * @type {{findByPage_url: string}}
 * @version<1> 2018-02-27 cxw : Created.
 */
var ORDER_CONFIG = {
    findOrderHandleByPage_url : project_path + "jh-datamanage/order/queryOrderHandleByPage",
    findOrderAuditByPage_url : project_path + "jh-datamanage/order/queryOrderAuditByPage",
    findOrderListByPage_url : project_path + "jh-datamanage/order/queryOrderListByPage",
    /*    findOuterOrderListByPage_url : project_path + "jh-datamanage/order/queryOuterOrderListByPage",*/
    createOrder_url : project_path + "zuul/jh-datamanage/order/createOrder",
    delOrderById_url : project_path + "jh-datamanage/order/delOrderById",
    queryOrderById_url : project_path + "jh-datamanage/order/queryOrderById",
    // createNeedOrder_url : project_path + "jh-datamanage/order/createNeedOrder",
    updateNeedOrder_url:project_path + "zuul/jh-datamanage/order/updateNeedOrder",
    auditOrder_url:project_path + "jh-datamanage/order/auditOrder",
    queryOrderDetailByOrderId_url:project_path + "jh-datamanage/order/queryOrderDetailByOrderId",
    queryOrderFileByPage_url:project_path + "jh-datamanage/order/queryOrderFileByPage",
    distributeOrder_url:project_path + "jh-datamanage/order/distributeOrder",
    downLoadOrderDataById_url:project_path + "jh-datamanage/order/downLoadOrderDataById",
    queryFileDetailByOrderId_url:project_path + "jh-datamanage/order/queryFileDetailByOrderId",
    queryOrderDistributeDetailById_url:project_path + "jh-datamanage/order/queryOrderDistributeDetailById",
    queryOrderCount_url:project_path + "jh-datamanage/order/queryOrderCount",
    queryOrderDetailCount_url:project_path + "jh-datamanage/order/queryOrderDetailCount",
    queryDataOrderBySatellite_url:project_path + "jh-datamanage/order/queryDataOrderBySatellite",
    queryDataOrderSum_url:project_path + "jh-datamanage/order/queryDataOrderSum",
    downloadOrderDetail_url : project_path + "jh-datamanage/order/downloadByDetailId",
    downloadOrderWord_url : project_path + "jh-datamanage/order/downLoadOrderWordById",

     queryOrderStatistics_url : project_path + "jh-datamanage/order/queryOrderStatistics"
}




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
}


/**
 * 下载配置中心
 *
 * @version <1> 2018-02-06 djh： Created.
 */
var DOWNLOAD_CONFIG = {
    findByPage_url : project_path + 'jh-datamanage/downloadConfig/findByPage',
    findById_url: project_path + 'jh-datamanage/downloadConfig/queryDataDownloadConfigById',
    updateDataDownloadConfigUrl: project_path + 'jh-datamanage/downloadConfig/updateDataDownloadConfigById',
    saveDataDownloadConfigUrl: project_path + 'jh-datamanage/downloadConfig/saveDataDownloadConfig',
    del_url : project_path + 'jh-datamanage/downloadConfig/deleteDataDownloadConfigById',
    queryByDataType_url : project_path + 'jh-datamanage/downloadConfig/queryByDataType'
}

/**
 * 算法配置中心
 *
 * @version <1> 2018-02-07 djh： Created.
 */
var HANDLE_CONFIG = {
    findByPage_url : project_path + 'jh-datamanage/handleConfig/findByPage',
    queryAll_url : project_path + 'jh-datamanage/handleConfig/queryAll',
    del_url: project_path + 'jh-datamanage/handleConfig/delete',
    saveHandleConfigUrl: project_path + 'jh-datamanage/handleConfig/add',
    updateHandleConfigUrl: project_path + 'jh-datamanage/handleConfig/update',
    findById_url:project_path + 'jh-datamanage/handleConfig/getById',
    saveRalateStat: project_path + 'jh-datamanage/handleConfig/saveRalateStat',
    loadRelateStatByHandleMataId: project_path + 'jh-datamanage/handleConfig/loadRelateStatByHandleMataId',
    // queryDictsByParentId_url : project_path + 'jh-datamanage/dict/queryDictByParentId'
}



/**
 * 数据入库配置中心
 * @type {{findByPage_url: string}}
 * @version<1> 2018-03-01 lcw : Created.
 */
var LOADER_CONFIG = {
    findByPage_url : project_path + "jh-datamanage/loader/findByPage",
    makeTask_url : project_path + "jh-datamanage/loader/makeImportLoaderTask" ,
    import_url : project_path + "jh-datamanage/loader/queryImportDataFromCeph",
    start_url : project_path + "jh-datamanage/loader/start",
    detail_url : project_path + "jh-datamanage/loader/queryImportDataDetail",
    dataDetail_url:project_path + "jh-datamanage/loader/queryLoaderDetail",
    reload_url:project_path+"jh-datamanage/loader/reload",
    findById_url:project_path + 'jh-datamanage/loader/getById',
    importReprocess_url : project_path + 'jh-datamanage/dataReprocess/importReprocess',
    publishReprocessData_url : project_path + 'jh-datamanage/dataReprocess/publishReprocessData',
    importStatic_url : project_path + 'jh-datamanage/dataStatic/importStatic',
    findDateReprocessByPage_url : project_path + 'jh-datamanage/dataReprocess/findDateReprocessByPage',
    dataReprocessDown_url:project_path + 'jh-datamanage/dataReprocess/down',

    dataStaticDown_url:project_path + 'jh-datamanage/dataStatic/down',
    findDateStaticByPage_url : project_path + 'jh-datamanage/dataStatic/findDateStaticByPage',
    checkNull_url : project_path + "jh-datamanage/loader/checkNull",


    queryLoaderSateSum_url : project_path + "jh-datamanage/loader/nolog/queryLoaderSateSum",
    queryLoaderStateSumTable_url : project_path + "jh-datamanage/loader/nolog/queryLoaderSateSumTable",

    getEchartsShowData_url : project_path + "jh-datamanage/loader/nolog/getEchartsShowData",//获取大屏显示所需的数据(入库和下载数据)
    getEchartsShowData2_url : project_path + "jh-datamanage/loader/nolog/getEchartsShowData2"

}

var DOWNLOAD_TASK = {
    findByPage_url:project_path + 'jh-datamanage/downLoadTask/downloadPage',
    saveDownload_url : project_path + 'jh-datamanage/downLoadTask/saveDownload',
    tryAgain_url : project_path + 'jh-datamanage/downLoadTask/tryAgain',
    suspend_url : project_path + 'jh-datamanage/downLoadTask/suspend',
    continueDownload_url : project_path + 'jh-datamanage/downLoadTask/continueDownload',
    findById_url : project_path+'jh-datamanage/downLoadTask/findById',
    startAgain_url : project_path + 'jh-datamanage/downLoadTask/startAgain'
}

var DOWNLOAD_DETAIL = {
    findDetail_url : project_path + "jh-datamanage/downloadDetail/findDetailList"
}


/**
 * 数据处理配置
 * @version<1> 2018-03-07 cxj : Created.
 */
var HANDLE_DATA = {
    saveHandleTask_url : project_path + 'jh-datamanage/handleData/saveTask',
    findTaskPage_url : project_path + 'jh-datamanage/handleData/taskPage',
    findTaskParams_url : project_path + 'jh-datamanage/handleData/findTaskParams',
    addHandleData_url : project_path + 'jh-datamanage/handleData/handleData',
    updateTaskAndStepStatus : project_path + 'jh-datamanage/handleData/againStatus',
    download_url : project_path + "jh-datamanage/handleData/down",
    createHandleTask_url : project_path + "jh-datamanage/handleData/createHandleTask",
    findTaskByHandleId_url : project_path + "jh-datamanage/handleData/findTaskByHandleId",
    findHandleFileInfo_url : project_path + 'jh-datamanage/handleData/findHandleFileInfo',
    deleteHandle_url : project_path + 'jh-datamanage/handleData/deleteHandle'
}

/**
 * 数据归档配置
 * @version<1> 2018-03-21 wl: Created.
 */
var ARCHIVE_CONFIG = {
    findByPage_url : project_path + 'jh-datamanage/archive/findByPage',
    findAll_url: project_path + 'jh-datamanage/archive/findAll',
    // queryDictsByParentId_url : project_path + 'jh-datamanage/dict/queryDictByParentId',
    add_url : project_path + 'jh-datamanage/archive/add',
    getById_url : project_path + 'jh-datamanage/archive/findById',
    del_url : project_path + 'jh-datamanage/archive/delete',
    edit_url : project_path + 'jh-datamanage/archive/edit',
    exec_url:project_path + 'jh-datamanage/archive/exec',
    detail_url:project_path + 'jh-datamanage/archive/findDetail',
    queryArchiveSateNum_url:project_path + 'jh-datamanage/archive/queryArchiveSateNum',

    queryArchiveSateSum_url:project_path + 'jh-datamanage/archive/nolog/queryArchiveSateSum',
    queryArchiveStateSumTable_url:project_path + "jh-datamanage/archive/nolog/queryArchiveSateSumTable"

}


/**
 * 原始数据
 * @version<1> 2018-06-12 wl: Created.
 */
var DATA_STORAGE ={
    findDataStorageSateSum_url : project_path + 'jh-datamanage/storage/queryDataStorageSateSum',
    findDataSum_url : project_path + 'jh-datamanage/storage/queryDataSum',
    findByPage_url : project_path + "jh-datamanage/storage/findByPage",
    findStorageByIds_url : project_path + "jh-datamanage/storage/findStorageByIds",    //根据ID集合获取所有源数据信息
    queryDataStorageSateNum_url : project_path + "jh-datamanage/storage/queryDataStorageSateNum",
    download_url : project_path + "jh-datamanage/storage/down",
    showThumbnail_url : project_path + "jh-datamanage/storage/showThumbnail",
    download_suffix:"/download/",
    queryGFDatas: project_path + "jh-datamanage/storage/queryGFDatas",
    queryNoSceneDatas: project_path + "jh-datamanage/storage/queryNoSceneDatas"
}


var DATA_REPORT = {
    report_show : project_path2 + "show.html"
}


/***********************************************************************************************************************************
 * *****************************************************数据发布URL管理部分************************************************************
 * *********************************************************************************************************************************/


/**
 * 农作物生育周期管理
 * @version<1> 2018-04-12 lxy: Created.
 */
var CROPS_GROWTH_PERIOD_CONFIG = {
    findByPage_url : project_path + 'jh-publish/growthPeriod/findByPage',
    queryAll_url : project_path + 'jh-publish/growthPeriod/queryAll',
    del_url: project_path + 'jh-publish/growthPeriod/delete',
    save_url: project_path + 'jh-publish/growthPeriod/add',
    update_url: project_path + 'jh-publish/growthPeriod/update',
    findById_url:project_path + 'jh-publish/growthPeriod/getById',
    queryGrowthPeriodList_url:project_path +"jh-publish/growthPeriod/queryGrowthPeriodList"
};

/**
 * Description: 农作物生育周期基本配置管理(作物、物候期、病虫害等配置管理)
 * @version <1> 2018/8/10 13:32 zhangshen: Created.
 */
var CROPS_GROWTH_MANAGER = {
    findByPage_url : project_path + 'jh-publish/growthManager/findByPage',
    save_url : project_path + 'jh-publish/growthManager/add',
    findById_url : project_path + 'jh-publish/growthManager/getById',
    update_url : project_path + 'jh-publish/growthManager/update',
    del_url : project_path + 'jh-publish/growthManager/delete',
    findList_url : project_path + 'jh-publish/growthManager/findList'
};


/**
 * 作物信息配置中心
 * @type {{}}
 * @version <1> 2018-05-03 lcw： Created.
 */
var CROP_CONFIG = {
    queryCropListByRegionId_url:project_path+"jh-publish/crop/queryCropListByRegionId"
}

var BRIEF_CROP = {
    queryCropListByRegionId_url : project_path + "jh-publish/briefCrop/queryCropListByRegionId"
}


/**
 * 农作物病情防治管理
 * @version<1> 2018-04-12 lxy: Created.
 */
var DISEASE_CONFIG = {
    findByPage_url : project_path + 'jh-publish/disease/queryByPage',
    queryAll_url : project_path + 'jh-publish/disease/queryAll',
    del_url: project_path + 'jh-publish/disease/delete',
    save_url: project_path + 'jh-publish/disease/add',
    update_url: project_path + 'jh-publish/disease/update',
    findById_url:project_path + 'jh-publish/disease/getById'
};

/**
 * 农作物害虫防治管理
 * @version<1> 2018-04-13 lxy: Created.
 */
var BUG_DISEASE_CONFIG = {
    findByPage_url : project_path + 'jh-publish/bugDisease/queryByPage',
    queryAll_url : project_path + 'jh-publish/bugDisease/queryAll',
    del_url: project_path + 'jh-publish/bugDisease/delete',
    save_url: project_path + 'jh-publish/bugDisease/add',
    update_url: project_path + 'jh-publish/bugDisease/update',
    findById_url:project_path + 'jh-publish/bugDisease/getById'
};

/**
 * 简报信息管理
 * @version<1> 2018-04-13 lxy: Created.
 */
var TEMPLATE_REPORTER_CONFIG = {
    findByPage_url : project_path + 'jh-publish/templateReporter/findByPage',
    queryAll_url : project_path + 'jh-publish/templateReporter/queryAll',
    del_url: project_path + 'jh-publish/templateReporter/delete',
    save_url: project_path + 'jh-publish/templateReporter/generatorReporter',
    batch_save_url: project_path + 'jh-publish/templateReporter/batchGeneratorReporter',
    update_url: project_path + 'jh-publish/templateReporter/update',
    update_audis_status_url:project_path+'jh-publish/templateReporter/updateAudisStatus',
    findById_url:project_path + 'jh-publish/templateReporter/getById',
    findBriefReportById_url:project_path + 'jh-publish/templateReporter/findBriefReportById',
    findBriefReportByType_url:project_path + 'jh-publish/templateReporter/findBriefReportByType',
    checkExist_url: project_path + 'jh-publish/templateReporter/checkBriefIsExist',
};
/**
 * 简报信息管理
 * @version<1> 2018-07-23 wl: Created.
 */
var BRIEFING_REPORT_CONFIG = {
    findByPage_url : project_path + 'jh-publish/briefingReport/findByPage',
    queryAll_url : project_path + 'jh-publish/briefingReport/queryAll',
    del_url: project_path + 'jh-publish/briefingReport/delete',
    save_url: project_path + 'jh-publish/briefingReport/createReport',
    batch_save_url: project_path + 'jh-publish/briefingReport/batchGeneratorReporter',
    update_url: project_path + 'jh-publish/briefingReport/update',
    update_audis_status_url:project_path+'jh-publish/briefingReport/updateAudisStatus',
    findById_url:project_path + 'jh-publish/briefingReport/getById'
};
/**
 * 物候期地温关系
 * @version<1> 2018-04-13 lxy: Created.
 */
var GROWTH_REPORTER_TEMP_CONFIG = {
    findByPage_url : project_path + 'jh-publish/growthTemp/findByPage',
    queryAll_url : project_path + 'jh-publish/growthTemp/queryAll',
    del_url: project_path + 'jh-publish/growthTemp/delete',
    save_url: project_path + 'jh-publish/growthTemp/add',
    update_url: project_path + 'jh-publish/growthTemp/update',
    findById_url:project_path + 'jh-publish/growthTemp/getById'
};

/**
 * 模板模块管理
 * @version<1> 2018-04-13 lxy: Created.
 */
var TEMPLATE_MODULE_CONFIG = {
    findByPage_url : project_path + 'jh-publish/templateModule/queryByPage',
    queryAll_url : project_path + 'jh-publish/templateModule/queryAll',
    del_url: project_path + 'jh-publish/templateModule/delete',
    save_url: project_path + 'jh-publish/templateModule/add',
    update_url: project_path + 'jh-publish/templateModule/update',
    findById_url:project_path + 'jh-publish/templateModule/getById',
    checkModuleExists:project_path + 'jh-publish/templateModule/checkModuleExists'
};



/**
 * 数据集分布数据明细
 * @version<1> 2018-05-08 wl: Created.
 */
var DS_AREA = {
    findByPage_url : project_path + 'jh-publish/dsArea/findByPage',
    findAll_url: project_path + 'jh-publish/dsArea/findAll',
    getById_url : project_path + 'jh-publish/dsArea/findById',
    del_url : project_path + 'jh-publish/dsArea/delete',
    edit_url : project_path + 'jh-publish/dsArea/edit',
    publish_url : project_path + 'jh-publish/dsArea/publish',
    findDsAreaReportCreateData_url : project_path + 'jh-publish/dsArea/findDsAreaReportCreateData'//分布-----数据
}

/**
 * 数据集长势数据明细
 * @version<1> 2018-05-09 wl: Created.
 */
var DS_GROWTH = {
    findByPage_url : project_path + 'jh-publish/dsGrowth/findByPage',
    findAll_url: project_path + 'jh-publish/dsGrowth/findAll',
    getById_url : project_path + 'jh-publish/dsGrowth/findById',
    del_url : project_path + 'jh-publish/dsGrowth/delete',
    edit_url : project_path + 'jh-publish/dsGrowth/edit',
    publish_url : project_path + 'jh-publish/dsGrowth/publish',
    findDsGrowthReportCreateData_url : project_path + 'jh-publish/dsGrowth/findDsGrowthReportCreateData'//长势-----数据
}

/**
 * 区域统计数据作物--作物估产
 * @version<1> 2018-05-11 zhangshen: Created.
 */
var DS_YIELD = {
    findDsYieldReportCreateData_url : project_path + 'jh-publish/dsYield/findDsYieldReportCreateData',//估产-----数据
    findByPage_url : project_path + 'jh-publish/dsYield/findByPage',
    findAll_url: project_path + 'jh-publish/dsYield/findAll',
    getById_url : project_path + 'jh-publish/dsYield/findById',
    del_url : project_path + 'jh-publish/dsYield/delete',
    edit_url : project_path + 'jh-publish/dsYield/edit',
    publish_url : project_path + 'jh-publish/dsYield/publish'
}

/**
 * 数据集地温、降雨、干旱数据明细
 * @version<1> 2018-05-11 wl: Created.
 */
var DS_TTN = {
    findByPage_url : project_path + 'jh-publish/dsTtn/findByPage',
    findAll_url: project_path + 'jh-publish/dsTtn/findAll',
    getById_url : project_path + 'jh-publish/dsTtn/findById',
    del_url : project_path + 'jh-publish/dsTtn/delete',
    edit_url : project_path + 'jh-publish/dsTtn/edit',
    publish_url : project_path + 'dsTtn/publish',
    findDsTtnldReportCreateData_url : project_path + 'jh-publish/dsTtn/findDsTtnldReportCreateData'
}

/**
 * Description: 报告
 * @version <1> 2018-5-13 10:36 zhangshen: Created.
 */
var DS_REPORT = {
    createReport_url : project_path + 'jh-publish/dsReport/createReport',
    findDsReportByPage_url : project_path + 'jh-publish/dsReport/findDsReportByPage',
    previewReportPdf_url : project_path + 'jh-publish/dsReport/previewReportPdf',
    batchPublishReport_url : project_path + 'jh-publish/dsReport/batchPublishReport',
    findDsReportById_url : project_path + 'jh-publish/dsReport/findDsReportById',
    updateReportById_url : project_path + 'jh-publish/dsReport/updateReportById',
    batchCancelReport_url : project_path + 'jh-publish/dsReport/batchCancelReport',
    batchDeleteReport_url : project_path + 'jh-publish/dsReport/batchDeleteReport',
    reportDown_url : project_path + "jh-publish/dsReport/down2",
    queryDateTimeList_url : project_path + "jh-publish/dsReport/queryDateTimeList",
    checkReportIsExist_url: project_path + "jh-publish/dsReport/checkReportIsExist",
    _url: project_path + "jh-publish/dsReport/checkReportIsExist",
    findAllReportTempType_url:project_path + "jh-publish/dsReport/findAllReportTempType",
}

/**
 * 数据集地温数据明细
 * @version<1> 2018-05-11 wl: Created.
 */
var DS_T = {
    findByPage_url : project_path + 'jh-publish/dsT/findByPage',
    findAll_url: project_path + 'jh-publish/dsT/findAll',
    getById_url : project_path + 'jh-publish/dsT/findById',
    del_url : project_path + 'jh-publish/dsT/delete',
    edit_url : project_path + 'jh-publish/dsT/edit',
    publish_url : project_path + 'jh-publish/dsT/publish',

}

/**
 * 数据集降雨数据明细
 * @version<1> 2018-06-06 wl: Created.
 */
var DS_TRMM = {
    findByPage_url : project_path + 'jh-publish/dsTrmm/findByPage',
    findAll_url: project_path + 'jh-publish/dsTrmm/findAll',
    getById_url : project_path + 'jh-publish/dsTrmm/findById',
    del_url : project_path + 'jh-publish/dsTrmm/delete',
    edit_url : project_path + 'jh-publish/dsTrmm/edit',
    publish_url : project_path + 'jh-publish/dsTrmm/publish'
}

/**
 * 数据集天气数据明细
 * @version<1> 2018-09-29 i huxiaoqiang: Created.
 */
var DS_WEATHER = {
    findByPage_url : project_path + 'jh-publish/dsWeather/findByPage',
    findAll_url: project_path + 'jh-publish/dsWeather/findAll',
    getById_url : project_path + 'jh-publish/dsWeather/findById',
    del_url : project_path + 'jh-publish/dsWeather/delete',
    edit_url : project_path + 'jh-publish/dsWeather/edit',
    publish_url : project_path + 'jh-publish/dsWeather/publish'
}



/**
 * 数据集入库
 * @type {{findImport_url: string}}
 * @version<1>2018-07-02 lcw:Created.
 */
var DS_RESULTIMAGE = {
    findImport_url: project_path + 'jh-publish/resultimage/queryImportDataFromCeph',
    loader_url : project_path + "jh-publish/resultimage/loader",
    findByPage_url : project_path + "jh-publish/resultimage/findByPage",
    download_url : project_path + "jh-publish/resultimage/download",
    activate_url : project_path + "jh-publish/resultimage/activateTask",
    publish_url : project_path + "jh-publish/resultimage/publishTifAndData",
    back_url : project_path + "jh-publish/resultimage/backTifAndData",
    delete_url : project_path + "jh-publish/resultimage/deleteTifAndData",
    checkNull_url : project_path + "jh-publish/resultimage/checkNull",
    detail_url : project_path + "jh-publish/resultimage/queryImportDataDetail",
    updateResultimage_url : project_path + "jh-publish/resultimage/updateResultimage",
    findById_url : project_path + "jh-publish/resultimage/queryResultimage",
    findDataSatListByTaskId_url : project_path + "jh-publish/resultimage/findDataSatListByTaskId",
    updateDataSat_url : project_path + "jh-publish/resultimage/updateDataSat",
    handleDataLoader_url : project_path + "jh-publish/resultimage/handleDataLoader"
}


/**
 * 降雨地温数据下载URl
 * @type {{TrmmDataDown_url: string, TempDataDown_url: string, TrmmIsExistData_url: string, TempIsExistData_url: string, TrmmExportData_url: string, TempExportData_url: string, DsTrmmDataDown_url: string, DsTDataDown_url: string}}
 */
var TrmmData_CONFIG = {
    TrmmDataDown_url:project_path + 'jh-publish/dsTrmm/queryTrmmForTenDaysAndHistory',
    TempDataDown_url:project_path + 'jh-publish/dsT/queryTForTenDaysAndHistory',
    TrmmIsExistData_url:project_path + 'jh-publish/dsTrmm/isExistTrmmData',
    TempIsExistData_url:project_path + 'jh-publish/dsT/isExistTData',
    TrmmExportData_url:project_path + 'jh-publish/dsTrmm/exportTrmmData',
    TempExportData_url:project_path + 'jh-publish/dsT/exportTData',
    DsTrmmDataDown_url:project_path + 'jh-publish/dsTrmm/queryTrmmListForTenDaysAndHistory',
    DsTDataDown_url:project_path + 'jh-publish/dsT/queryTListForTenDaysAndHistory',
}

/**
 * 区域作物配置
 */
var REGION_CROP_CONFIG = {
    crop_url:project_path+"jh-sys/regionAndCrop/queryCropByRegionId",
    add_regionAndcrop_url:project_path+"jh-sys/regionAndCrop/addRegionAndCropRelateData",
}


/**
 * 周报URl配置
 * @type {{storage_week_url: string}}
 */
var WEEKREPORT_CONFIG = {
    week_report_url : project_path + "jh-datamanage/storage/getWeeklyReport",
    date_report_url : project_path + "jh-datamanage/storage/getDateReport"
}

var COLLECTOR_CONFIG = {
    queryTaskList : project_path + "jh-datamanage/taskInfoController/queryTaskList",
    queryTaskById : project_path + "jh-datamanage/taskInfoController/queryTaskById",
    deleteTask : project_path + "jh-datamanage/taskInfoController/deleteTask",
    querySubTaskList : project_path + "jh-datamanage/subTaskController/queryTaskVillage",
    queryVillageDetail : project_path + "jh-datamanage/policyInfo/queryVillageDetail"
}