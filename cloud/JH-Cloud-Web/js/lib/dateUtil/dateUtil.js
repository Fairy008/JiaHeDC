/**
 * 时间控件调用工具类
 * version<1> Created by cxw on 2017-11-28.
 */
define(['jquery','require','jedate'],function($,require,jedate){

    var handle = {
        init: function () {
            var currentObj;
            currentObj = this;
            currentObj.timeEvent()
            currentObj.timeEventTwo()
            currentObj.timeEventThree()
        },

        //开始时间与结束时间(年-月-日)
        timeEvent:function (inpstart,inpend,funtest,dy) {
            inpstart = "#"+inpstart;
            $(inpstart).jeDate({
                format: "YYYY-MM-DD",
                multiPane:false,
                range:" 至 ",
                okfun:function () {
                    funtest();
                }
            });
         $(inpstart).jeDate({
                isinitVal:true,
                determine:false,
                format:dy,
                zIndex:3000,
            })

        },

        //开始时间与结束时间(年-月-日)
        timeEventThree:function (inpstart,inpend,funtest) {

            inpstart = "#"+inpstart;
            inpend = "#"+inpend;
            var cancel ={
                minDate:'1970-06-16 23:59:59',
                //maxDate:'2017-12-16'
            }
            var opts = $.extend({
                type:"je",
                minDate:undefined,
                maxDate:undefined
            },cancel);


            //实现日期选择联动
            var start = {
                format: 'YYYY-MM-DD',
                minDate: '1970-06-16 23:59:59', //设定最小日期为当前日期
                //festival:true,
                maxDate: $.nowDate({DD:0}), //最大日期
                choosefun: function(elem,datas){
                    end.minDate = datas; //开始日选好后，重置结束日的最小日期
                    endDates();
                },
                okfun:function (elem,datas) {
                    funtest();
                }
            };
            var end = {
                format: 'YYYY-MM-DD',
                minDate: $.nowDate({DD:0}), //设定最小日期为当前日期
                //festival:true,
                // maxDate: '2099-06-16 23:59:59', //最大日期
                choosefun: function(elem,datas){
                    start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
                },
                okfun:function(elem, datas){
                    funtest();
                }
            };
            function endDates() {
                end.trigger = false;
                $(inpend).jeDate(end);
                // $("#reportinpend").jeDate(end);
            }
            $(inpstart).jeDate(start);
            $(inpend).jeDate(end);

        },

        //设置时间控件，不设置最小时间
        timeEvent_Self:function (inpstart,funtest) {
            inpstart = "#"+inpstart;
            var cancel ={
                minDate:'1970-06-16 23:59:59'
            };
            var opts = $.extend({
                type:"je",
                minDate:undefined,
                maxDate:undefined
            },cancel);


            //实现日期选择联动
            var start = {
                format: 'YYYY-MM-DD',
                maxDate: $.nowDate({DD:0}), //最大日期
                choosefun: function(elem,datas){
                    end.minDate = datas; //开始日选好后，重置结束日的最小日期
                },
                okfun:function (elem,datas) {
                    funtest();
                }
            };
            $(inpstart).jeDate(start);
        },

        //时间(年)
        timeEventTwo:function (dateyyyy,funtest) {
            dateyyyy = "#"+dateyyyy;
            var cancel ={
                minDate:'1970',
                //maxDate:'2017-12-16'
            }
            var opts = $.extend({
                type:"je",
                minDate:undefined,
                maxDate:undefined
            },cancel);

            //实现日期选择联动
            var start = {
                format: 'YYYY',
                minDate: '1970', //设定最小日期为当前日期
                //festival:true,
                maxDate: $.nowDate({DD:0}), //最大日期
                choosefun: function(elem,datas){
                    funtest();
                },
                okfun:function (elem,datas) {
                    funtest();
                }
            };
            $(dateyyyy).jeDate(start);
            $(dateyyyy).jeDate({
                isinitVal:true,
                determine:false,
                format:"YYYY",
                zIndex:3000,
            })
        },

        /**
         *  用户 数据导出（按旬） 的查询
         * @param inpstart
         * @param inpend
         * @param funtest
         * @version (1) 2018-3-22 created:lxy
         */
        timeEvent_yyyymm:function (inpstart,inpend,funtest) {
            inpstart = "#"+inpstart;
            inpend = "#"+inpend;
            var cancel ={
                minDate:'1970-06-16 23:59:59',
                //maxDate:'2017-12-16'
            }
            var opts = $.extend({
                type:"je",
                minDate:undefined,
                maxDate:undefined
            },cancel);


            //实现日期选择联动
            var start = {
                format: 'YYYY-MM',
                minDate: '1970-06-16 23:59:59', //设定最小日期为当前日期
                //festival:true,
                maxDate: $.nowDate({DD:0}), //最大日期
                choosefun: function(elem,datas){
                    end.minDate = datas; //开始日选好后，重置结束日的最小日期
                    endDates();
                },
                okfun:function (elem,datas) {
                    funtest();
                }
            };
            var end = {
                format: 'YYYY-MM',
                minDate: '1970-06-16 23:59:59', //设定最小日期为当前日期
                //festival:true,
                // maxDate: '2099-06-16 23:59:59', //最大日期
                choosefun: function(elem,datas){
                    start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
                },
                okfun:function(elem, datas){
                    funtest();
                }
            };
            function endDates() {
                end.trigger = false;
                $(inpend).jeDate(end);
                // $("#reportinpend").jeDate(end);
            }
            $(inpstart).jeDate(start);
            $(inpend).jeDate(end);

        },

        //错误提示弹出框
        showMessageWin:function(message){
            var body = document.getElementsByTagName('body')[0];
            var messageDiv = document.createElement('div');
            messageDiv.id = 'showMessageWin';
            messageDiv.innerText = message;
            body.appendChild(messageDiv);
            window.setTimeout(function(){
                body.removeChild(document.getElementById('showMessageWin'));
            },1500);
        },

        //增减日期天数
        addDate:function(date,days){
            date.setDate(date.getDate()+days);
            var m=date.getMonth()+1;
            var d = date.getDate();
            if(m<10)
            {
                m = '0'+m;
            }
            if(d<10)
            {
                d = '0'+d;
            }
            return date.getFullYear()+'-'+m+'-'+d;
    }
    }
    return handle;
    });
