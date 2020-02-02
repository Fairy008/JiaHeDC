/**
 * 配置登录页面 相关的标题，图片等，能够自定义话
 * Created by lxy on 2018/3/27.
 */
define(function(){
    /**
     *custom_setting 自定义设置
     */
    var custom_settings = {
        /**
         * 公共的title配置
         */
        public_title:"卫星遥感数据库及展示平台",  //login.html 、main.html、index.html 都有配置 title 名称

        /**
         * login.html 相关 自定义设置
         */

        login_left_top_logo_pic:"images/public/logo3.png",  //login.html 左上角 公司logo (图片大小为：38*39)
        login_copyright:"",//login.html 最下面版权信息


        /**
         * main.html 相关 自定义设置
         */
        main_left_top_logo_pic:"images/public/logo3.png", //main.html 左上角 公司logo图片 (图片大小为：40*40)
        main_right_bottom_logo_pic:"",//main.html 地图 右下角 logo 图片 (图片大小：79*28)
        main_right_bottom_logo_location:"",//main.html 地图 右下角 logo 图片 点击后要跳转的url地址。

        main_search_region_id:'3102000003',
        main_search_region_code:'CHN-CQ',
        main_search_region_name:"中国 | 重庆",
        main_search_beginTime:'2000-01-01',

        /**
         * index.html 相关 自定义设置
         */
        company:"", //index.html 左上角公司名
        index_left_top_logo_pic:"", //index.html 左上角 公司logo图片 (图片大小为：34*34)

        /**
         * 门户配置信息
         */
        welcome_log_pic:"images/login/loginLOGO.png",

        /**
         * 农情遥感平台首页
         */
        jiaheshow_url : "http://localhost:9998/main.html"
    };
    return custom_settings;
});