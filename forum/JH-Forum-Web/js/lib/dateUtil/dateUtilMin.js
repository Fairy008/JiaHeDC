/**
 * 时间控件调用工具类
 * version<1> Created by cxw on 2017-11-28.
 */
define(['jquery','require','jedatemin'],function($,require,jedatemin){

    var handle = {
        init: function () {
            var currentObj;
            currentObj.timeMultiEvent()
        },

        //双面板选择时间范围
        timeMultiEvent:function (id) {
            id = "#"+id;
            $(id).jeDate({
                format: "YYYY-MM-DD",
                multiPane:false,
                range:" 至 "
            });
        },

        //单面板选择时间
        timeEvent:function (id) {
            id = "#"+id;
            $(id).jeDate({
                format: "YYYY-MM-DD",
            });
        },

    }
    return handle;
});
