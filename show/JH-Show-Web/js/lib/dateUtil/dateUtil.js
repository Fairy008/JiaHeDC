/**
 * 时间控件调用工具类
 * version<1> Created by cxw on 2017-11-28.
 */
define(['jquery','require','jedate'],function($,require,jedate){
    var rangeChar = " 至 ";
    var handle = {
        sameDate:'',
        oldEndDate:'',
        init: function () {
            var currentObj;
            currentObj = this;
            currentObj.timeEventThree();
        },

        show:function(targetId,format,selectFun,sameDate){
            var targetObj = document.getElementById(targetId);
            var parentNode = targetObj.parentNode;
            parentNode.removeChild(targetObj);

            var el = document.createElement("input");
            el.id=targetId;
            el.type="text";
            parentNode.appendChild(el);
            if("yyyy"==format){
                var dy = new Date().Format("yyyy")+"年";//默认时间
                //判断初始时间是否是下半年 不是则显示上一年
                if(new Date().getMonth()<6)
                {
                    dy = (new Date().getFullYear()-1)+"年";//默认时间
                }
                //实现日期选择联动
                $("#"+targetId).jeDate({
                    format: "YYYY年",
                    range:false,
                    zIndex:3000,
                    choosefun: function(elem,datas){
                         alert("choose");
                    },
                    okfun:function (elem,datas) {
                        if(typeof selectFun =="function"){
                           selectFun();
                        }
                    }
                });
               $("#"+targetId).jeDate(dy);
            }else{
                var endDate = new Date().Format("yyyy-MM-dd");
                var startDate = new Date().addDay(-6).Format("yyyy-MM-dd");
                var dy = startDate+rangeChar+endDate;//默认时间
                //判断初始时间段是否是同年 不同年则显示上一年并且截止时间为12月31日
                if((new Date(startDate).getFullYear()!=new Date(endDate).getFullYear())){
                    dy = (new Date(endDate).getFullYear()-1)+"-"+12+"-"+25+" 至 "+(new Date(endDate).getFullYear()-1)+"-"+12+"-"+31;//默认时间
                }
                else if(new Date(endDate).getMonth()<6){
                    dy = (new Date(endDate).getFullYear()-1)+"-"+12+"-"+25+" 至 "+(new Date(endDate).getFullYear()-1)+"-"+12+"-"+31;//默认时间
                }
                $("#"+targetId).jeDate({
                    format: "YYYY-MM-DD",
                    multiPane:true,
                    range:rangeChar,
                    sameDate:sameDate,//是否是同月选择
                    okfun:function(){
                        if(typeof selectFun =="function"){
                           selectFun();
                        }
                    }
                });
                $("#"+targetId).val(dy);
            }
        },

        /**
        * 根据日期格式获取日期或者年
        * @version <1> 2017-12-16 Hayden:Created.
        */
        getSelectedDate:function(targetId,format){
            var selectedDateObj={};
            var targetEl = document.getElementById(targetId);
            var selectDate = targetEl.value;
            if(!selectDate) return;

            if("yyyy"==format){
                 // selectedDateObj["year"] = selectDate.substring(0,4);
                 selectedDateObj["endYear"] = selectDate.substring(0,4);
            }else{
                var dateArray = selectDate.split(rangeChar.trim());
                if(dateArray && dateArray.length==2){
                    selectedDateObj["startDate"] = dateArray[0].trim();
                    selectedDateObj["endDate"] = dateArray[1].trim();
                }
            }
            return selectedDateObj;
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
                minDate: '1970-06-16 23:59:59',
                maxDate:$.nowDate({DD:0}), //最大日期
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
        showDoubleDate:function (startId,endId,startDate,endDate,selectFun) {

            var nowDate = new Date();
            var nowDateStr = nowDate.Format("yyyy-MM-dd");

            //设置初始值
            var startDateValue,endDateValue;
            startDateValue = startDate;
            //if(startDate==null||startDate=='') {
                if (handle.sameDate == "year") {
                   // startDateValue = "" + nowDate.getFullYear() + "-01-01";
                    endDateValue =  "" + new Date(startDate).getFullYear() + "-12-31";
                } else if (handle.sameDate == "month") {
                    var month =new Date(startDate).getMonth() + 1;
                    if (month < 10) {
                        month = "0" + month;
                    }
                   // startDateValue = "" + nowDate.getFullYear() + "-" + month + "-01";
                    var days = handle.getDaysNum(new Date(startDate).getFullYear(),month);
                    var temTime = new Date(startDate).getFullYear()+ '-' + month + '-' + days;
                    endDateValue =  temTime;
                }
                $("#" + startId).val(startDateValue)
                $("#" + endId).val(endDateValue);
            /*}
            else{
                $("#" + startId).val(startDate);
                $("#" + endId).val(endDate);
            }*/

            //handle.oldEndDate = endDateValue;
            var startSetting = {
                format:"YYYY-MM-DD",
                minDate:"1990-01-01",
                maxDate:"2999-12-31",
                onClose:false,
                isClear:false,
                okfun:function (elem) {
                    $('#'+endId).val('');//清空结束时间
                    //设置结束时间最大值
                    if(handle.sameDate == "year"){
                        //同年
                        //endSetting.maxDate = elem.date.YYYY+'-12-31';
                        $("#" + endId).val(elem.date.YYYY+'-12-31');
                        handle.oldEndDate = elem.date.YYYY+'-12-31';

                    } else if(handle.sameDate == "month"){
                        //同月
                        var days = handle.getDaysNum(elem.date.YYYY,elem.date.MM);
                       // endSetting.maxDate = elem.date.YYYY+ '-' + elem.date.MM + '-' + days;
                        $("#" + endId).val(elem.date.YYYY+ '-' + elem.date.MM + '-' + days);
                        handle.oldEndDate = elem.date.YYYY+ '-' + elem.date.MM + '-' + days
                    }
                   // $('#'+endId).val(endSetting.maxDate);
                   // endSetting.minDate = datas; //开始日选好后，重置结束日的最小日期
                    endSetting.minDate = elem.val;
                    endDates();
                    if(typeof selectFun =="function"){
                        selectFun();
                    }

                }
            };


            var endSetting = {
                format:"YYYY-MM-DD",
                minDate:"1990-01-01",
                maxDate:"2999-12-31",
                onClose:false,
                isClear:false,
                okfun:function (elem) {
                    if(handle.sameDate == "year"){
                        if( new Date($("#" + startId).val()).getFullYear()!=new Date(elem.val).getFullYear())
                        {

                            $("#" + endId).val(  handle.oldEndDate)
                            showMessageWin("日期必须选择同年")
                           return;
                        }
                        else{
                            handle.oldEndDate = elem.val;
                        }

                    } else if(handle.sameDate == "month"){
                        if( ((new Date($("#" + startId).val()).getFullYear())!=(new Date(elem.val).getFullYear())) || ((new Date($("#" + startId).val()).getMonth()+1)!=(new Date(elem.val).getMonth()+1)))
                        {

                            $("#" + endId).val(  handle.oldEndDate)
                            showMessageWin("日期必须选择同年同月")
                            return;
                        }
                        else{
                            handle.oldEndDate = elem.val;
                        }
                    }

                    if(typeof selectFun =="function"){
                        selectFun();
                    }
                  //  startSetting.maxDate = elem.val; //将结束日的初始值设定为开始日的最大日期


                }
            };

            function endDates() {
                endSetting.trigger = false;
                $('#'+endId).jeDate(endSetting);
            }
            $('#'+startId).jeDate(startSetting);
            $('#'+endId).jeDate(endSetting);

        },

        getDaysNum : function(y, m) {
            var num = 31;
            switch (parseInt(m)) {
                case 2: num = handle.isLeap(y) ? 29 : 28; break;
                case 4: case 6: case 9: case 11: num = 30; break;
            }
            return num;
        },
        isLeap : function(y) {
        return (y % 100 !== 0 && y % 4 === 0) || (y % 400 === 0);
    }
    }
    return handle;
    });
