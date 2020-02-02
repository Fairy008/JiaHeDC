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
        public_title:"珈和遥感私有云平台",  //login.html 、main.html、index.html 都有配置 title 名称

        /**
         * login.html 相关 自定义设置
         */

        //login_left_top_logo_pic:"images/public/leftlogo.png",  //login.html 左上角 公司logo (图片大小为：38*39)
        login_left_top_logo_pic:"images/public/logo1.png",
        login_copyright:'<a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=42018502001246"><img src="images/login/beian.png" width="20" height="20" align="absmiddle">鄂公网安备 42018502001246号</a>&nbsp;&nbsp;&nbsp; 鄂ICP备13010685号<br>'
                            +'Copyright©武汉珈和科技有限公司 All Rights Reserved<br>',
    //login.html 最下面版权信息


        /**
         * main.html 相关 自定义设置
         */
        //main_left_top_logo_pic:"images/public/index-logo.png", //main.html 左上角 公司logo图片 (图片大小为：40*40)
        main_left_top_logo_pic:"images/public/logo4.png",
        // main_right_bottom_logo_pic:"images/public/logo.png",//main.html 地图 右下角 logo 图片 (图片大小：79*28)

        main_right_bottom_logo_pic:"",
        main_right_bottom_logo_location:"",//main.html 地图 右下角 logo 图片 点击后要跳转的url地址。
        main_search_region_id:'3100000000',
        main_search_region_code:"CHN",
        main_search_region_name:"中国",
        main_search_beginTime:'2000-01-01',

        /**
         * index.html 相关 自定义设置
         */
        company:"珈和科技", //index.html 左上角公司名
        index_left_top_logo_pic:"images/public/leftlogo.png", //index.html 左上角 公司logo图片 (图片大小为：34*34)


        /**
         * register.html 页面，注册条款 相关修改
         */
        register_center:"珈和数据中心",// 中心公司名称
        register_copyright_company:"武汉珈和科技有限公司",//所有权归属公司全名称
        copyright_company_prefix:"珈和",//所有权归属公司名简称
        register_copyright_company_website_name:"珈和科技网站",//所有权归属公司网站名

        /**
         * 门户配置信息
         */
        welcome_log_pic:"images/login/loginLOGO.png",

        /**
         * 农情遥感平台首页
         */
        jiaheshow_url : "http://localhost:9998/main.html",

        /**
         * 数据字典名称值
         */
        cacheName:"",
        /**
         * 缓存类型：数据字典0
         */
        dataType_dict:"0",
        /**
         * 缓存类型：区域1
         */
        dataType_region:"1",
        /**
         * 缓存类型：用户2
         */
        dataType_account:"2",
        //编辑值限制最大不能超过一亿
        larger_area:100000000,
    };
    return custom_settings;
});