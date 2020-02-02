/**
 * 前端页面分两个部分处理，此处显示两个页面的公共js方法
 * 1.main.html  数据检索首页（外部用户默认访问页面）
 * 2.index.html 后台管理部分的前端首页
 * @version<1> 2018-02-05 lcw : Created.
 * @version<2> 2018-03-06 cxj : 将isLoginFun、quitFun等函数提取到公共模块中,以便其它处使用
 */
require(["jquery","BaseAjax","jqGrid","jqueryUi","commons"],function($,BaseAjax,jqGrid,jqueryUi,commons){
    var init = function(){
        commons.initSystem();
    }

    init();
});