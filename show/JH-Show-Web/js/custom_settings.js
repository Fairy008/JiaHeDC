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
         * 重庆农情项目
         */
        /**公共的title配置*/
        // public_title:"重庆市农业大数据平台",  //login.html 、main.html、home.html 都有配置 title 名称
        // ico_href:"img/logo/favicon_cq.ico",
        // /**主页logo */
        // main_logo:"img/logo/logo_cq.png",
        // /**login.html 相关 自定义设置*/
        // login_logo:"img/logo/login_logo_cq.png",
        // login_background:"img/logo/loginBG_cq.jpg",
        // login_copyright:'<a target="_blank" style="color: #0a0a0a !important;"><img src="img/beian.png" width="20" height="20" align="absmiddle">Copyright©重庆市农科院版权所有',
        // /**初始区域配置 */
        // region_id :3103000019,
        // region_name : "Chongqing",
        // level : 3,
        // region_code : "CHN-CQ-CQG",
        // china_name : '重庆市',
        // default_parent_region_id:3102000003,
        // capital_id:3103000019


        /**
         *山东农情项目
         */
        /**公共的title配置*/
        // public_title:"山东农情遥感大数据平台",  //login.html 、main.html、index.html 都有配置 title 名称
        // ico_href:"img/logo/favicon.ico",
        // /**主页logo */
        // main_logo:"img/logo/logo_sd.png",
        // /**login.html 相关 自定义设置*/
        // login_logo:"img/logo/login_logo_sd.png",
        // login_background:"img/logo/loginBG.jpg",
        // login_copyright:'<a target="_blank" style="color: #0a0a0a !important;" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=42018502001246"><img src="img/beian.png" width="20" height="20" align="absmiddle">Copyright©珈和科技版权所有</a>',
        // /**初始区域配置 */
        // region_id :3102000027,
        // region_name : "Shandong",
        // level : 2,
        // region_code : "CHN-SD",
        // china_name : '山东省',
        // default_parent_region_id:3102000027,
        // capital_id:3103000248


        /**
         *河南农情项目
         */
        /**公共的title配置*/
        // public_title:"河南农情遥感大数据平台",  //login.html 、main.html、index.html 都有配置 title 名称
        // ico_href:"img/logo/favicon.ico",
        // /**主页logo */
        // main_logo:"img/logo/logo_hn.png",
        // /**login.html 相关 自定义设置*/
        // login_logo:"img/logo/login_logo_hn.png",
        // login_background:"img/logo/loginBG.jpg",
        // login_copyright:'<a target="_blank" style="color: #0a0a0a !important;" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=42018502001246"><img src="img/beian.png" width="20" height="20" align="absmiddle">Copyright©珈和科技版权所有</a>',
        // /**初始区域配置 */
        // region_id :3102000005,
        // region_name : "Henan",
        // level : 2,
        // region_code : "CHN-HE",
        // china_name : '河南省',
        // default_parent_region_id:3102000005


        /**
         * 京南科技农情项目
         */
        // /**公共的title配置*/
        // public_title:"京蓝农情遥感大数据平台",  //login.html 、main.html、index.html 都有配置 title 名称
        // ico_href:"img/logo/favicon_jn.ico",
        // /**主页logo */
        // main_logo:"img/logo/logo_jn.png",
        // /**login.html 相关 自定义设置*/
        // login_logo:"img/logo/login_logo_jn.png",
        // login_background:"img/logo/loginBG.jpg",
        // login_copyright:'<a target="_blank" style="color: #0a0a0a !important;" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=42018502001246"><img src="img/beian.png" width="20" height="20" align="absmiddle">Copyright©珈和科技版权所有</a>',
        // /**初始区域配置 */
        // region_id :3103000019,
        // region_name : "Chongqing",
        // level : 3,
        // region_code : "CHN-CQ-CQG",
        // china_name : '重庆市',
        // default_parent_region_id:3100000000,
        // capital_id:3103000019


        /**
         * 珈和农情项目
         */
        /**公共的title配置*/
        public_title:"珈和农情遥感大数据平台",  //login.html 、main.html、index.html 都有配置 title 名称
        ico_href:"img/logo/favicon.ico",
        /**主页logo */
        main_logo:"img/logo/logo.png",
        /**login.html 相关 自定义设置*/
        login_logo:"img/logo/login_logo.png",
        login_background:"img/logo/loginBG.jpg",
        login_copyright:'<a target="_blank" style="color: #0a0a0a !important;" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=42018502001246"><img src="img/beian.png" width="20" height="20" align="absmiddle">Copyright©珈和科技版权所有</a>',
        /**初始区域配置 */
        region_id :3102000006,
        region_name : "Hubei",
        level : 2,
        region_code : "CHN-HU",
        china_name : '湖北省',
        default_parent_region_id:3100000000,
        capital_id:3103000143,

        /**默认精度：分布，长势，估产，降雨，地温*/
        defautResolution : [4010,4006,4006,4009,4007],
        defautResolutionCode : ['GF1-16M','MOD09A1-500M','MOD09A1-500M','TRMM-0.25','MOD11A1-1KM'],
        defautResolutionName : ['GF1-16M','MOD09A1-500M','MOD09A1-500M','TRMM-0.25','MOD11A1-1KM'],

        crop_ids : [501,502,503,504,505,506,507,508,509,510,511,512,513,514,515,519,520,521,522,523,524,525,526],

        /**
         *常熟市农情项目
         */
        /**公共的title配置*/
        // public_title:"常熟市农情遥感大数据平台",  //login.html 、main.html、index.html 都有配置 title 名称
        // ico_href:"img/logo/favicon.ico",
        // /**主页logo */
        // main_logo:"img/logo/logo_cs.png",
        // /**login.html 相关 自定义设置*/
        // login_logo:"img/logo/login_logo_cs.png",
        // login_background:"img/logo/loginBG.jpg",
        // login_copyright:'<a target="_blank" style="color: #0a0a0a !important;" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=42018502001246"><img src="img/beian.png" width="20" height="20" align="absmiddle">Copyright©珈和科技版权所有</a>',
        // /**初始区域配置 */
        // region_id :3104001146,
        // region_name : "Changshu",
        // level : 4,
        // region_code : "CHN-JS-SUZ-CHA",
        // china_name : '常熟市',
        // default_parent_region_id:3103000169,
        // home_html:"homeCS.html",
        //capital_id:3103000248

        /**
         *奎屯市农情项目
         */
        /**公共的title配置*/
        // public_title:"奎屯市农情遥感大数据平台",  //login.html 、main.html、index.html 都有配置 title 名称
        // ico_href:"img/logo/favicon.ico",
        // /**主页logo */
        // main_logo:"img/logo/logo_kt.png",
        // /**login.html 相关 自定义设置*/
        // login_logo:"img/logo/login_logo_kt.png",
        // login_background:"img/logo/loginBG.jpg",
        // login_copyright:'<a target="_blank" style="color: #0a0a0a !important;" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=42018502001246"><img src="img/beian.png" width="20" height="20" align="absmiddle">Copyright©珈和科技版权所有</a>',
        // /**初始区域配置 */
        // region_id :3104002092,
        // region_name : "Kuitun",
        // level : 4,
        // region_code : "CHN-XU-IKA-KUI",
        // china_name : '奎屯市',
        // default_parent_region_id:3103000303,
        //capital_id:3103000248

    };
    return custom_settings;
});