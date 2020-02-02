define(["jquery","BaseAjax","custom_settings","formVerfication","PopWin","Base64","enums"],function($,BaseAjax,custom_settings,formVerfication,PopWin,Base64,enums){
    var commons = {
        /**
         * 用户退出登录
         * @version<1>2018-02-02 lcw : created.
         * @version<2> 2018-03-06 cxj : 从main.js移到此
         */
        quitFun : function(){
            var ajax = new BaseAjax();
            ajax.opts.url = LOGIN_CONFIG.logout_url;
            ajax.opts.successFun = function(msg){
                commons.delCookie("myData");//清空我的数据列表cookie
                var exp = new Date();
                exp.setTime(exp.getTime() - 1 * 24 * 60 * 60 * 1000);
                var cval=null;
                var arr,reg=new RegExp("(^| )"+"personType"+"=([^;]*)(;|$)");
                if(arr=document.cookie.match(reg))
                    cval =  unescape(arr[2]);
                if(cval!=null)
                    document.cookie= "personType" + "=;expires="+exp.toGMTString();
                // window.location.href = LOGIN_CONFIG.welcome_page_url;
                window.location.href = LOGIN_CONFIG.login_page_url;
            }
            ajax.run();
        },
        /**
         * 进入页面时，判断用户是否已登录
         * @version<1>2018-02-02 lcw : created.
         * @version<2> 2018-03-06 cxj : 从main.js移到此
         */
        isLoginFun : function(){
            var USER_TYPE_INNER = 1701; //内部用户
            var USER_TYPE_OUTER = 1702;//外部用户
            var obj = this;
            // obj.delCookie("personType");
            var ajax = new BaseAjax();
            ajax.opts.type = "GET";
            ajax.opts.url = LOGIN_CONFIG.check_user_login;
            ajax.opts.successFun = function (msg){
                if(msg.flag){
                    if(msg.data != null ){
                        var data = eval("("+ msg.data +")");
                        var personType =  data.personType;
                        $(".userCenter .user").html(data.nickName || data.personName || data.accountName || data.accountCode);
                        //将personId,accountId 放入 .user的新增属性（personId，accountId）中
                        $(".user").attr("personId",data.personId);
                        $(".user").attr("accountId",data.accountId);
                        $(".user").attr("personType",personType);
                        var exp = new Date();
                        commons.setCookie(COOKIE_CONFIG.cookieName,commons.getAccessToken(),120);
                        obj.setCookie('personType',personType,exp.toGMTString());

                        if(personType == USER_TYPE_INNER){
                            $(".out_user").hide();
                            $(".inner_user").show();
                        }else{
                            $(".out_user").show();
                            $(".inner_user").hide();
                        }

                    }else{

                        window.location.href = LOGIN_CONFIG.login_page_url
                    }
                }else{
                    window.location.href = LOGIN_CONFIG.login_page_url
                }
            };
            ajax.run();
        },
        /**
         * 配置系统标题、版权等信息
         * @version<1> 2018-02-06 lcw : Created.
         * @version<2> 2018-03-06 cxj : 从config.js移到此
         */
        setTitleFun : function(){
            /**
             * 公共title的设置
             */
            $("title,.title").html(custom_settings.public_title);//设置标题


            /**
             * login.html 相关标题设置 和 图片路径 修改
             */
            if(custom_settings.login_left_top_logo_pic == null || custom_settings.login_left_top_logo_pic == ""){
                $(".topLogin img").css("display","none");//设置左上角图片路径
            }else{
                $(".topLogin img").css("display","block");//设置左上角图片路径
                $(".topLogin img").attr("src",custom_settings.login_left_top_logo_pic);//设置左上角图片路径
            }

            if(custom_settings.login_copyright == "" || custom_settings.login_copyright == null){
                $(".copyright").css("display","none");
            }else{
                $(".copyright").css("display","block");
                $(".copyright").html(custom_settings.login_copyright);//设置版权
            }


            /**
             * main.html 相关logo图片设置
             */
            if(custom_settings.main_left_top_logo_pic == null || custom_settings.main_left_top_logo_pic ==""){
                $(".logo img").css("display","none");
            }else{
                $(".logo img").css("display","block");
                $(".logo img").attr("src",custom_settings.main_left_top_logo_pic);//设置左上角logo图片地址
            }

            /**
             * 设置门户标题
             */
            $("#log_imgurl").attr("src",custom_settings.welcome_log_pic);//设置左上角图片路径


        },
        /**
         * 加载未读告警信息条数
         *  1.ajax加载未读信息条数
         *  2.tips显示条数
         * @version<1> 2018-02-05 lcw : created.
         * @version<2> 2018-03-06 cxj : 从main.js移到此
         */
        loadAlarmMsgFun : function(){
            var ajax = new BaseAjax();
            ajax.opts.url = ALARM_CONFIG.alarm_count_url;
            ajax.opts.successFun = function(data){
                if(data.flag){
                    $(".tips").css("display","none");
                    if(data.data > 0){
                        var num = data.data;
                        if(data.data >= 100){
                            num = "99+";
                        }
                        $(".tips").css("display","block");
                        $("#noticeId").html(num);
                        $("#noticeId").attr("title","您有"+ data.data +"条告警消息,请注意查看");

                    }
                }
            };
            ajax.run();
        },
        /**
         * 显示告警信息列表
         *  1.点击tips，进入告警信息列表页面
         *  2.具体操作在告警信息列表中
         *  3.若已读，则需要刷新tips提示的数字
         * @version<1> 2018-02-05 lcw : created.
         * @version<2> 2018-03-06 cxj : 从main.js移到此
         */
        noticeFun : function(){
            // $('#content,.rightMain').load(ALARM_CONFIG.alarm_page_url);
            var aHrefObj = $('.userContent li').first().find('a');
            aHrefObj.attr('data-url',project_path2 + 'index.html?resCode=GJXX');
            aHrefObj.trigger('click');
        },
        /**
         * 鼠标滑入滑出效果
         * @version<2> 2018-03-06 cxj : 从main.js移到此
         */
        timer : null,
        showUserMenuFun : function(){
            var obj = this;
            $(".userContent").hide();
            $(".userCenter").hover(function(){
                $(".userContent").show();
                clearTimeout(obj.timer);
            },function(){
                obj.timer = setTimeout(function () {
                    $(".userContent").hide();
                }, 500);
            })
        },
        showMenuItemFun : function(){
            var obj = this;
            $(".userContent").hover(function(){
                clearTimeout(obj.timer);
            },function(){
                $(this).hide();
            })
        },
        /**
         * 用户中心-菜单项操作，跳转至对应的页面
         *  page-type = 1 : 是后台管理与数据检索页面间的跳转，是完整页面跳转
         *  page-type = 2 : 直接在当前主体界面中load页面数据（在当前页面上弹出层，显示数据。未完成，后续整理）
         * @version<1> 2018-02-05 lcw : created.
         * @version<2> 2018-03-06 cxj : 从main.js移到此
         */
        menuClickFun : function(){
            //数据管理 和 数据检索 跳转
            $(".logo a").click(function(){
                var url = $(this).attr("data-url");
                window.location.href = url;
            });

            $(".userContent li a").click(function(){
                var personId = $(".user").attr("personId");//personId 用户编号
                var accountId = $(".user").attr("accountId");//账户编号
                var url = $(this).attr("data-url");
                var pageType = $(this).attr("page-type");
                if(pageType == 1){
                    window.location.href = url; //跳转至后台管理页面
                }else{
                    var title = "完善资料";
                    if(pageType == 3){
                        title = "修改密码";
                    }
                    //弹出层
                    // $('#content,.rightMain').load(url); //跳转至告警页面
                    var dialog=$('#menuInfo').dialog({
                        title: title,
                        //top:100,
                        width: 550,
                        //height: 506,
                        closed: false,
                        cache: false,
                        modal: true,
                        position: {
                            using:function(pos){
                                var topOffset = $(this).css(pos).offset().top;
                                if (topOffset = 0||topOffset>0) {
                                    $(this).css('top', ($(window).height()-530)/2);
                                }
                            }
                        },
                        buttons:[{
                            text:"保存",
                            click:function(){
                                if(pageType == 2 ){ //完善资料修改
                                    commons.editUserSaveFun(dialog,personId);
                                }
                                if(pageType ==3 ){//修改用户密码
                                    commons.updateUserPwdFun(dialog,accountId);
                                }
                            }
                            },{
                            text:"取消",
                            click:function(){
                                $('.formInputHighlight').removeClass('formInputHighlight');//清除错误样式
                                $("#errorInfo").html("");
                                $(this).dialog("close");
                            }
                        }]
                    }).load(url+"?version="+new Date().getTime());
                    //将当前用户的personId、accountId上传到模式对话框里
                    dialog.data("personId",personId);
                    dialog.data("accountId",accountId);
                }
            })
        },
        formVerf : function(){
            var userInfo = $('#userInfo');
            if(formVerfication.checkInputLength(0,20,$('#personName'),userInfo,'姓名不能超过20位字符')){
                return false;
            }

            if(formVerfication.checkInputLength(0,20,$('#nickName'),userInfo,'昵称不能超过20位字符')){
                return false;
            }

            if(formVerfication.checkPhoneInput($("#mobile"),userInfo)){
                return false;
            }

            if(formVerfication.checkInputIsQQ($('#qq'),userInfo,'QQ号格式：首位为非0且长度为5-15位数字')){
                return false;
            }


            if(formVerfication.checkEmailInput($("#email"),userInfo)){
                return false;
            }

            if(formVerfication.checkInputLength(0,20,$('#email'),userInfo,'邮箱不能超过20位字符')){
                return false;
            }



            if(formVerfication.checkInputLength(0,100,$('#address'),userInfo,'联系地址不能超过100位字符')){
                return false;
            }

            return true;
        },
        /**
         * 检查密码
         */
        formCheckPwd:function(){
            var old = $("#oldpwd");
            if (formVerfication.isEmpty(old.val())) {
                $("#errorInfo").text("请输入原密码");
                $("#errorInfo").addClass('red');
                old.addClass('formInputHighlight');
                return false;
            } else if (!formVerfication.isScopeLength(old.val(), 6, 20)) {
                $("#errorInfo").text("原密码为6-20位字母/数字/符号组合");
                $("#errorInfo").addClass('red');
                old.addClass('formInputHighlight');
                return false;
            }
            var pwd = $("#pwd");
            if (formVerfication.isEmpty(pwd.val())) {
                $("#errorInfo").text("请输入新密码");
                $("#errorInfo").addClass('red');
                pwd.addClass('formInputHighlight');
                return false;
            } else if (!formVerfication.isScopeLength(pwd.val(), 6, 20)) {
                $("#errorInfo").text("新密码为6-20位字母/数字/符号组合");
                $("#errorInfo").addClass('red');
                pwd.addClass('formInputHighlight');
                return false;
            }

            if (!formVerfication.isScopeLength($("#qpwd").val(), 6, 20)) {
                $("#errorInfo").text("新密码为6-20位字母/数字/符号组合");
                $("#errorInfo").addClass('red');
                $("#qpwd").addClass('formInputHighlight');
                return false;
            }

            var qpwd = $.trim($("#qpwd").val());
            if (qpwd == "") {
                $("#errorInfo").text("请输入确认新密码");
                $("#errorInfo").addClass('red');
                $("#qpwd").addClass('formInputHighlight');
                return false;
            }

            var pwdstr = $.trim($("#pwd").val());
            if (pwdstr != qpwd) {
                $("#errorInfo").text("密码不一致");
                $("#errorInfo").addClass('red');
                $("#qpwd").addClass('formInputHighlight');
                return false;
            }


            return true;
        },
        /**
         * 完善用户资料
         * @param userDialog
         */
        editUserSaveFun:function(userDialog,id){
            if(!this.formVerf()){
                return ;
            }
            var person = {};
            person.personName = $("#personName").val().trim();
            person.sex = $("input[type='radio'][name='sex']:checked").val();
            person.personBorn = $("#personBorn").val();
            person.qq = $("#qq").val().trim();
            person.email = $("#email").val().trim();
            person.mobile = $("#mobile").val().trim();
            person.address = $("#address").val().trim();
            person.personId = id;
            var account = {};
            account.nickName = $("#nickName").val().trim();
            account.accountName = $("#mobile").val().trim();
            account.accountId =  $("#accountId").val();
            account.accountCode = $("#accountCode").val().trim();
            var personParam  = {};
            personParam.permPerson  = person;
            personParam.permAccount = account;
            var ajax = new BaseAjax();
            ajax.opts.url = USER_CONFIG.edit_url;
            ajax.opts.contentType = "application/json";
            ajax.opts.data = JSON.stringify(personParam);
            ajax.opts.successFun = function(result){
                if(result.flag){
                    userDialog.dialog("close");
                    PopWin.showMessageWin("用户修改成功");
                }else{
                    PopWin.showMessageWin("用户修改失败");
                }
            };
            ajax.opts.errorFun = function (result) {
                PopWin.showMessageWin("用户修改失败");
            };
            ajax.run();
        },
        /**
         * 修改密码
         */
        updateUserPwdFun:function(userDialog,id){
            if(!this.formCheckPwd()){
                return ;
            }
            var personParam = {
                accountId:id,
                oldPass:Base64.encode($("#oldpwd").val()),
                newPass:Base64.encode($("#pwd").val()),
                truePass:Base64.encode($("#qpwd").val())
            };
            var ajax = new BaseAjax();
            ajax.opts.url = LOGIN_CONFIG.updateUserPwd;
            ajax.opts.contentType = "application/json";
            ajax.opts.data = JSON.stringify(personParam);
            ajax.opts.successFun = function(result){
                if(result.code == 1){
                    userDialog.dialog("close");
                    PopWin.showMessageWin("密码修改成功");
                }else{
                    $("#errorInfo").html(result.msg);
                    $("#errorInfo").addClass('red');
                }
            };
            ajax.opts.errorFun = function (result) {
                PopWin.showMessageWin("密码修改失败！");
            };
            ajax.run();
        },
        /**
         * 用户中心-菜单权限查询，根据用户id查询所有菜单权限
         * @version<1> 2018-03-06 wl : created.
         */
        menuShow : function(id,roleType){

            var obj = this;
            var ajax = new BaseAjax();
            ajax.opts.url = RESOURCE_CONFIG.findMenu_url + "?roleType=" + roleType;
            ajax.opts.successFun = function (msg) {

                if(msg.flag){
                    var result = msg.data;
                    var html ="<ul>";
                    var first=0;
                    $.each(result,function(index, element){
                        if(element.resType == 201){ //子系统
                            var parentId = element.resId;
                            $.each(result,function(v, k){ //获取目录
                                if(k.resType != 201){

                                    if(k.parentId == parentId){ //获取下一级
                                        if(k.isLeaf){
                                            html+='<li class="leftFirstMenu"><a  href="javascript:void(0)" leaf="t" data-url=\"'+k.resUrl+'\" ><img src=\"'+k.icoStyle+'\" align="absmiddle">'+k.resName+'</a></li>';

                                        }else{
                                            //判断下级是否含有菜单，若没有，则直接过滤掉该目录菜单项
                                            if(obj.hasLeaf(result, k.resId)){
                                                html+='<li class="leftFirstMenu"><a  href="javascript:void(0)" leaf="f" ><img src=\"'+k.icoStyle+'\" align="absmiddle">'+k.resName+'</a></li>';

                                                if(first == 0){

                                                    html+= obj.getNextMenu(result,k.resId,0);
                                                    first = 1;
                                                }else{
                                                    html+= obj.getNextMenu(result,k.resId,1);
                                                }
                                            }


                                        }
                                    }
                                }
                            })
                        }
                    })
                    html+='</ul>';

                    $(".leftMenuCoat > div").append(html);
                    $(".open").prev().find("a").css("background","url(images/public/toDown.png) right center no-repeat")
                    $('.leftFirstMenu').click(function(event){//选中父级菜单 展开当前 关闭其他
                        var leftSecondMenu = $(this).next();
                        if ($(leftSecondMenu).is(':hidden')) {
                            leftSecondMenu.siblings("ul").slideUp();
                            $(leftSecondMenu).slideDown('fast');
                            $(this).siblings("li").find("a").css("background","url(images/public/toOpen.png) right center no-repeat")
                            $(this).find("a").css("background","url(images/public/toDown.png) right center no-repeat")
                        } else {

                            $(this).find("a").css("background","url(images/public/toOpen.png) right center no-repeat")
                            $(leftSecondMenu).slideUp('fast');

                        }
                    });

                    $(".leftMenuCoat a").on('click',function () {//选中子级菜单 打开连接 文字变色
                        var $this=$(this);

                        if($this.attr("leaf") == "t"){
                            $(".leftMenuCoat a").removeClass("nowpage");
                            $this.addClass("nowpage");
                            if($this.attr("data-url") != null && $this.attr("data-url") != ''){

                                //先判断session是否存在，如果不存在，则跳出
                                commons.isLoginFun();

                                window.onresize="";//打开页面前 清空其他页面的自适应方法 避免找不到方法报错
                                $('.rightMain').load(project_path2+$this.attr("data-url"));
                            }
                        }

                    });

                    obj.menuHref("GJXX"); //告警消息
                    obj.menuHref("WDSJCLRW");
                }
            };
            ajax.run();
        },
        /**
         * 判断是否有下级节点
         * @param result
         * @param resId
         */
        hasLeaf:function(result,resId){
            var flag = false;
            $.each(result,function(index, element){
                if(element.parentId == resId){
                    flag = true;
                }
            })
            return flag;
        },

        /**
         * 对请求URl进行判断，若包含“?myData"则认为是我的数据列表内容， 默认查询的是当前登录人的对应信息
         * @returns {boolean}
         * @version<1> 2018-04-16 lcw :Created.
         */
        getMyDataFun : function(){ //当前选中页面的url,判断该页面是否包含“?myData"字符串
            var flag = false;
            var url = $(".nowpage").attr("data-url");
            if(url.indexOf("?myData") != -1){
                flag = true;
            }
            return flag;

        },
        menuHref : function(resCode){
            //跳转到警告信息
            var searchParam = window.location.search;
            if(searchParam.indexOf(resCode)!=-1){
                //将订单管理框，收拢
                // $("a[data-rescode='DDFF']").parent().parent().hide();
                //获得警告信息的<a>标签
                var a  = $("a[data-rescode='"+ resCode +"']");
                //获得用户类别 为 外部用户
                // var personType = $(".user").attr("personType");//账户编号
                // var USER_TYPE_OUTER = 1702;//外部用户
                // if(personType == USER_TYPE_OUTER) {
                //     a = $("a[data-rescode='WDGJXX']");
                // }
                //获得它的主框
                var parentUL = a.parent().parent();
                //展开主框
                $(parentUL).show();
                //添加选中标记
                $(a).addClass("nowpage");
                //最后触发点击
                $(a).trigger("click");
            }
        },
        getNextMenu : function(menu, resId, displayFlag){
            var obj = this;
            var _html = '<ul class="leftSecondMenu" leaf="f" style="display:none;" >';
            var flag = true;
            if(displayFlag == 0){
                _html = '<ul class="leftSecondMenu open" leaf="f" style="display:block;" >';
                flag = false;
            }
            $.each(menu,function(index, element){
                if(element.parentId == resId){
                    //获得地址的查询条件，如果不带GJXX这个查询条件，就可以加载订单管理的第一个菜单项。
                    var searchParam = window.location.search;
                    if(!flag && (searchParam.indexOf("GJXX")==-1  && searchParam.indexOf("WDSJCLRW")==-1 )){
                        _html += '<li><a class="nowpage" href="javascript:void(0);" data-rescode = "' + element.resCode + '" leaf="t" data-url=\"'+element.resUrl+'\">'+element.resName+'</a></li>';

                        $('.rightMain').load(project_path2+element.resUrl);
                        flag = true;
                    }else{

                        _html += '<li><a href="javascript:void(0);" data-rescode = "' + element.resCode + '" leaf="t" data-url=\"'+element.resUrl+'\">'+element.resName+'</a></li>';
                    }
                }
            })

            _html += "</ul>"


            return _html;
        },
        /**
         * 获取url中"?"符后的字串
         * @version<1> 2018-03-08 cxj : created.
         */
        requestParam : function(){
            var url = location.search;
            var theRequest = new Object();
            if (url.indexOf("?") != -1) {
                var str = url.substr(1);
                strs = str.split("&");
                for(var i = 0; i < strs.length; i ++) {
                    theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
                }
            }
            return theRequest;
        },
        /**
         * 初始化系统
         * @version<1> 2018-03-06 cxj : created.
         */
        initSystem : function(id){


            var obj = this;

            var quitBtn = document.getElementById("quitBtn");
            var noticeId = document.getElementById("noticeId");
            var userCenter = document.getElementById("userCenter");

            obj.setTitleFun();
            quitBtn.onclick = obj.quitFun;
            noticeId.onclick = obj.noticeFun;
            obj.loadAlarmMsgFun();
            obj.isLoginFun();

            obj.showUserMenuFun();
            obj.showMenuItemFun();
            obj.menuClickFun();
            obj.menuShow(id, system_role_group.system_role_web);
            // $('.leftSecondMenu a').live('click',function () {
            //     obj.loadAlarmMsgFun();
            // });
        },

         /**
         * 设置cookie值
         * @param key :
         * @param value:
         * @param expires :
         * @version <1> 2017-11-14 Hayden:Created.
        */
        setCookie: function (key,value,minuties){
            var obj = this;
            if(key && value){
                var expires = obj.getExpires(minuties);
                document.cookie = key+"="+value+";expires="+expires;
            }
         },

        /**
         * 获取Cookie过期时间*
         * @version <1> 2017-12-24 Hayden:Created.
         */
        getExpires :function(minuties){
            if(!minuties){
                minuties = 120;
            }
            var exp = new Date();
            exp.setTime(exp.getTime() + 60 * 1000 * minuties);//过期时间 30分钟
            return exp.toGMTString();
        },

        /**
         * 从cookie中根据key值获取信息
         * @param key : cookie键值
         * @version <1> 2017-11-14 Hayden:Created.
         */
        getCookie: function(key)
        {
            var arr,reg=new RegExp("(^| )"+key+"=([^;]*)(;|$)");
            if(arr=document.cookie.match(reg))
                return unescape(arr[2]);
            else
                return null;
        },
        /**
         * 从cookie中根据key值删除信息
         * @param key : cookie键值
         * @version <1> 2018-03-22 cxw:Created.
         */
        delCookie :function(key){
            var exp = new Date();
            exp.setTime(exp.getTime() - 1 * 24 * 60 * 60 * 1000);
            var cval=this.getCookie(key);
            if(cval!=null)
                document.cookie= key + "=;expires="+exp.toGMTString();
        },

        /**
         * jqgrid 下一页是否置灰
         * @version <1> 2018-03-22 xzg:Created.
         */
        isNextDisable:function () {
            //没有数据，下一页按钮置灰
            var pages = $.trim($('#sp_1_pager2').text());
            if (pages == 0){
                $('#next_pager2,#last_pager2').addClass("ui-state-disabled");
            }
        },
        createIframe: function(url, triggerDelay, removeDelay) {
            commons.isLoginFun();
            //动态添加iframe，设置src，然后删除
            setTimeout(function () {
                var frame = $('<iframe style="display: none;" class="multi-download"></iframe>');
                frame.attr('src', url);
                $(document.body).after(frame);
                setTimeout(function () {
                    frame.remove();
                }, 60000);
            }, triggerDelay);
        },
        getAccessToken: function(){
            return commons.getCookie(COOKIE_CONFIG.cookieName);
        },
        /**
         * 根据字典pid获取子集下拉框
         * parentId 字典父ID
         * selectId 下拉框ID
         * id子集ID不为空时，回显值
         * @version <1> 2019-02-13 lijie:Created.
         */
        findDictList:function(parentId,selectId,id){
            var ajax = new BaseAjax();
            ajax.opts.url = REDIS_CONFIG.findSubListByDictId_url+"?parentId="+parentId;
            ajax.opts.contentType = "application/json";
            ajax.opts.async = false;

            ajax.opts.successFun = function (result) {
                var str = "<option value=''>--请选择--</option>";
                if (result.flag) {
                    $.each(result.data, function (index, element) {
                        str += "<option value='" + element.dictId + "'>" + element.dataName + "</option>";
                    })
                    $("#" + selectId).html("");//清空
                    $("#" + selectId).append(str);
                }
                if(id){
                    //id不为空回显值
                    $("#"+selectId+" option[value='"+id+"']").prop("selected",true);
                }
            }
            ajax.run();
        },
        /**
         * 根据区域pid获取区域下拉框
         * parentId 区域父ID
         * selectId 下拉框ID
         * id子集ID不为空时，回显值
         * @version <1> 2019-02-13 lijie:Created.
         */
        findRegionList:function(parentId,selectId,id){
            var ajax = new BaseAjax();
            ajax.opts.url = REDIS_CONFIG.findSubListByRegionId_url+"?parentId="+parentId;
            ajax.opts.contentType = "application/json";
            ajax.opts.async = false;
            ajax.opts.successFun = function (result) {
                var str = "<option value=''>--请选择--</option>";
                if (result.flag) {
                    $.each(result.data, function (index, element) {
                        str += "<option value='" + element.regionId + "'>" + element.chinaName + "</option>";
                    })
                    $("#" + selectId).html("");//清空
                    $("#" + selectId).append(str);
                }
                if(id){
                    //id不为空回显值
                    $("#"+selectId+" option[value='"+id+"']").prop("selected",true);
                }
            }
            ajax.run();
        },
        /**
         * 根据字典id获取字典名称
         * dictId 字典ID
         * nameId 名称文本框
         * flag 是否自定义赋值
         * @version <1> 2019-02-13 lijie:Created.
         */
        queryDictName:function(dictId,nameId){
            var name="";
            var ajax = new BaseAjax();
            ajax.opts.url = REDIS_CONFIG.queryNameByDictId_url+"?dictId="+dictId;
            ajax.opts.contentType = "application/json";
            ajax.opts.async = false;
            ajax.opts.successFun = function (result) {
                if (result.flag) {
                    if(nameId){
                        $("#" + nameId).text(result.data);
                    }else{
                        name=result.data;
                    }
                }
            }
            ajax.run();
            return name;
        },
        /**
         * 根据区域id获取区域名称
         * regionId 区域ID
         * nameId 名称文本框
         * flag 是否自定义赋值
         * @version <1> 2019-02-13 lijie:Created.
         */
        queryRegionName:function(regionId,nameId){
            var name="";
            var ajax = new BaseAjax();
            ajax.opts.url = REDIS_CONFIG.queryNameByRegionId_url+"?regionId="+regionId;
            ajax.opts.contentType = "application/json";
            ajax.opts.async = false;
            ajax.opts.successFun = function (result) {
                if (result.flag) {
                    if(nameId){
                        $("#" + nameId).text(result.data);
                    }else{
                        name=result.data;
                    }
                }
            }
            ajax.run();
            return name;

        },
        /**
         * 根据用户id获取用户名称
         * accountId 用户ID
         * nameId 名称文本框
         * flag 是否自定义赋值
         * @version <1> 2019-02-13 lijie:Created.
         */
        queryAccountName:function(accountId,nameId){
            var ajax = new BaseAjax();
            ajax.opts.url = REDIS_CONFIG.queryNameByAccountId_url+"?accountId="+accountId;
            ajax.opts.contentType = "application/json";
            ajax.opts.async = false;
            ajax.opts.successFun = function (result) {
                if (result.flag) {
                    if(nameId){
                        $("#" + nameId).text(result.data);
                    }else{
                        name=result.data;
                    }
                }
            }
            ajax.run();
            return name;
        },
        /**
         * 根据字典,区域，用户id获取名称
         * dictId 字典ID
         * nameId 名称文本框
         * dataType 0字典 1区域 2 用户
         * flag 是否自定义赋值
         * @version <1> 2019-02-13 lijie:Created.
         */
        queryNameById:function(dataType,dataId,nameId){
            var name="";
            if(dataType==custom_settings.dataType_dict){
                name=this.queryDictName(dataId,nameId);
            }else if(dataType==custom_settings.dataType_region){
                name=this.queryRegionName(dataId,nameId);
            }else if(dataType==custom_settings.dataType_account){
                name=this.queryAccountName(dataId,nameId);
            }
            return name;
        },
        /**
         * 根据字典id获取字典编码
         * dictId 字典ID
         * nameId 名称文本框
         * flag 是否自定义赋值
         * @version <1> 2019-02-13 lijie:Created.
         */
        queryDictCode:function(dictId,codeId){
            var code="";
            var ajax = new BaseAjax();
            ajax.opts.url = REDIS_CONFIG.queryCodeByDictId_url+"?dictId="+dictId;
            ajax.opts.contentType = "application/json";
            ajax.opts.async = false;
            ajax.opts.successFun = function (result) {
                if (result.flag) {
                    if(codeId){
                        $("#" + codeId).text(result.data);
                    }else{
                        code=result.data;
                    }
                }
            }
            ajax.run();
            return code;
        },
        /**
         * 根据区域id获取区域编码
         * regionId 区域ID
         * nameId 名称文本框
         * flag 是否自定义赋值
         * @version <1> 2019-02-13 lijie:Created.
         */
        queryRegionCode:function(regionId,codeId){
            var code="";
            var ajax = new BaseAjax();
            ajax.opts.url = REDIS_CONFIG.queryCodeByRegionId_url+"?regionId="+regionId;
            ajax.opts.contentType = "application/json";
            ajax.opts.async = false;
            ajax.opts.successFun = function (result) {
                if (result.flag) {
                    if(codeId){
                        $("#" + codeId).text(result.data);
                    }else{
                        code=result.data;
                    }
                }
            }
            ajax.run();
            return code;

        },
        /**
         * 根据字典,区域，用户id获取名称
         * dictId 字典ID
         * nameId 名称文本框
         * dataType 0字典 1区域 2 用户
         * flag 是否自定义赋值
         * @version <1> 2019-02-13 lijie:Created.
         */
        queryCodeById:function(dataType,dataId,codeId){
            var code="";
            if(dataType==custom_settings.dataType_dict){
                code=this.queryDictCode(dataId,codeId);
            }else if(dataType==custom_settings.dataType_region){
                code=this.queryRegionCode(dataId,codeId);
            }else if(dataType==custom_settings.dataType_account){
                code=this.queryAccountCode(dataId,codeId);
            }
            return code;
        }

    };

    return commons;
});