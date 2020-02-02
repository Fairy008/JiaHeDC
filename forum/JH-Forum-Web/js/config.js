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
        jsgrid:["js/lib/jsgrid-1.5.3/jsgrid.min"],
        Marquee:["js/lib/module/Marquee"],
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
        // map:["js/map/map"],
        formVerfication:["js/lib/module/formVerfication"],
        PopWin:["js/lib/module/PopWin"],
        commons:["js/lib/module/commons"],
        fly:["js/lib/jquery/jquery.fly.min"],
        custom_settings:["js/custom_settings"],
        multiselect:["js/lib/jq-multiselect/js/jquery.multi-select"],
        echarts:["js/lib/echart/echarts.common.min"],
        jqExtends:["js/lib/jquery/jquery-extend/jqueryExtend"],
        tableExporter:["js/lib/jquery/tableExporter"],
        UserModule:["js/lib/module/UserModule"],
        //枚举常量配置类
        enums:["js/enums_config"],
        ueditorConfig:["js/ueditor/ueditor.config"],
        ueditorAll:["js/ueditor/ueditor.all"],
        ZeroClipboard: ["js/ueditor/third-party/zeroclipboard/ZeroClipboard"],
        urlConfig: ["js/url_config"],
        daterangepicker:'js/lib/dateUtil/daterangepicker',
        moment: 'js/lib/dateUtil/moment',
        paginate:['js/tablePage/jquery.paginate'],
        botpaginate:['js/tablePage/jquery.botpaginate'],
        yhhDataTable:['js/tablePage/jquery.yhhDataTable'],
        botDataTable:['js/tablePage/jquery.botDataTable'],
        page:['js/tablePage/page'],
        emoji:['js/lib/emoji/jquery-sina-emotion'],
        //给图片添加水印工具类
        waterMask:['js/lib/watermask/watermask'],
        //自定义组件
        BaseMap:["js/report/BaseMap"],
        BaseChart:["js/report/BaseChart"],
        jscolor:['js/lib/jscolor/jscolor'],
        cropper:['js/lib/cropper/js/cropper.min']
    },
    waitSeconds: 0,
    shim:{
        ol:["css!js/lib/ol/ol.css"],
        Marquee:["jquery"],
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
        },
        ueditorConfig:{
            exports:"ueditorConfig"
        },
        ueditorAll:{
            exports:"ueditorAll"
        },
        yhhDataTable:["jquery"],
        botDataTable:["jquery"],
        jsgrid:["jquery","css!js/lib/jsgrid-1.5.3/jsgrid.css","css!js/lib/jsgrid-1.5.3/jsgrid-theme.css"],
        botpaginate:["jquery"],
        paginate:["jquery"],
        daterangepicker:["jquery"],
        emoji:["css!js/lib/emoji/jquery-sina-emotion.css"],
        cropper:["css!js/lib/cropper/css/cropper.min.css","css!js/lib/cropper/css/ImgCropping.css"],
        jqueryUi:["css!js/lib/jquery-ui/themes/hot-sneaks/jquery-ui.css"],
        slider:["css!js/lib/slider/jquery-ui-slider-pips.css"],
        pagination:["css!js/lib/pagination/pagination.css"],
        horizon:["css!js/lib/horizon/css/reset.css","css!js/lib/horizon/css/style.css"]
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


//行业标签颜色配置
var industryList = [
    {name:"农业",color:"#65D664",code:"agriculture"},
    {name:"气象",color:"#00A3DA",code:"meteorology"},
    {name:"遥感",color:"#D586EC",code:"remotesensing"},
    {name:"期货",color:"#DBA801",code:"futures"},
    {name:"保险",color:"#0C6D87",code:"insurance"}
]

//帖子类型
var articleTypeList = [
    {id:11001,code:"paper",name:"报告"},
    {id:11002,code:"survey",name:"调研"},
    {id:11003,code:"faq",name:"问答"},
    {id:11004,code:"share",name:"数据分享"},
    {id:11005,code:"other",name:"其他"}
]
