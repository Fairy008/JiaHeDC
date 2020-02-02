(function ($) {
    var Method = {
        'default': function ($container, pluginName, methodName) {
            var opts = optHandle.get($container, pluginName);
            if (methodName == 'default') {
                return yhhDataTable.init($container, pluginName, opts);
            } else {
                if (this[methodName]) {
                    return this[methodName]($container, pluginName, opts);
                }
            }
        }, 'update': function ($container, pluginName, opts) {}, 'getData': function ($container, pluginName, opts) {
            return opts;
        }, 'refresh': function ($container, pluginName, opts) {
            yhhDataTable.refresh($container, pluginName);
        }
    };
    var yhhDataTable = {
        'init': function ($table, pluginName, opts) {
            $table.addClass('yhh-data-table').wrap('<div class="yhh-data-table-frame"></div>');
            var $frame = $table.parent('.yhh-data-table-frame');
            var $tbody = $table.children('tbody');
            if (opts.paginate.enabled && opts.paginate.changeDisplayLen) {
                this.topBox.draw($frame);
                var $topBox = $frame.children('.data-table-top-box');
                this.changeDisplayLen.draw($topBox, opts.paginate.displayLen, opts.paginate.displayLenMenu);
                var $change = $topBox.children('.per-length-box');
                $change.find('.sel-list').hide();
                this.changeDisplayLen.action($frame, $change, $table, $tbody, pluginName, this);
            }
            if (opts.showInfo || opts.paginate.enabled) {
                this.bottomBox.draw($frame);
                var $bottomBox = $frame.children('.data-table-bottom-box');
                if (opts.showInfo) {
                    this.info.draw($bottomBox);
                }
                if (opts.paginate.enabled) {
                    this.paginate.init($frame, $table, $tbody, $bottomBox, pluginName, opts, this);
                }
            }
            if (opts.tbodyRow.hover) {
                this.tbody.row.hover($tbody);
            }
            if (opts.tbodyRow.selected) {
                this.tbody.row.selected($tbody);
            }
            this.view($frame, $table, $tbody, pluginName, true);
        }, 'calculate': {
            'pageLen': function (dataLen, displayDataLen) {
                if (displayDataLen <= 0) {
                    return 0;
                } else {
                    var pageLen = parseInt(dataLen / displayDataLen);
                    if (dataLen % displayDataLen != 0) {
                        pageLen++
                    };
                    if (pageLen < 0) pageLen = 0;
                    return pageLen;
                }
            }, 'displayDataStart': function (displayDataLen, currentPage, dataLen) {
                var r = ((currentPage - 1) * displayDataLen) + 1;
                if (dataLen <= 0 || r < 0) r = 0;
                return r;
            }, 'displayDataEnd': function (displayDataLen, currentPage, dataLen) {
                var r = currentPage * displayDataLen;
                if (dataLen <= 0 || r < 0) r = 0;
                if (r > dataLen) r = dataLen;
                return r;
            }
        }, 'topBox': {
            'draw': function ($frame) {
                $frame.prepend('<div class="data-table-top-box"></div>');
            }
        }, 'bottomBox': {
            'draw': function ($frame) {
                $frame.append('<div class="data-table-bottom-box"></div>');
            }
        }, 'tbody': {
            'show': function ($tbody, startLen, endLen) {
                $tbody.children('tr').show();
                $tbody.children('tr:lt(' + (startLen - 1) + ')').hide();
                $tbody.children('tr:gt(' + (endLen - 1) + ')').hide();
            }, 'draw': function ($tbody, d, startNo, endNo, writeRow) {
                var newhtml = '';
                for (var i = startNo - 1; i < endNo; i++) {
                    newhtml += writeRow(d[i]);
                }
                $tbody.html(newhtml);
            }, 'row': {
                'zebra': function ($tbody) {
                    $tbody.children('tr:odd').addClass('even');
                    $tbody.children('tr:even').addClass('odd');
                }, 'hover': function ($tbody) {
                    $tbody.on('mouseenter', 'td', function () {
                        $(this).parent().addClass('hover-row');
                    });
                    $tbody.on('mouseleave', 'td', function () {
                        $(this).parent().removeClass('hover-row');
                    });
                }, 'selected': function ($tbody) {
                    $tbody.on('click', 'td', function () {
                        $tbody.children('tr').removeClass('selected-row');
                        $(this).parent().addClass('selected-row');
                    });
                }
            }
        }, 'changeDisplayLen': {
            'draw': function ($topBox, displayLen, displayLenMenu) {
                var newhtml = '<div class="per-length-box"><label>每页显示</label>' + '<dl class="per-length-select"><dt class="sel-choosen-box">' + '<span class="val">' + displayLen + '</span>' + '<i class="sel-icon fa fa-caret-down"></i></dt>' + '<dd class="sel-list">';
                $.each(displayLenMenu, function (i, val) {
                    newhtml += '<a class="sel-option">' + val + '</a>';
                });
                newhtml += '</dd></dl></div>';
                $topBox.append(newhtml);
                $topBox.children('.per-length-box').find('.sel-option:last').addClass('last-option');
            }, 'action': function ($frame, $change, $table, $tbody, pluginName, top) {
                var $choosen = $change.find('dt.sel-choosen-box');
                var $list = $change.find('dd.sel-list');
                var that = this;
                $choosen.click(function (e) {
                    if ($(this).hasClass('expand')) {
                        that.contract($choosen, $list);
                    } else {
                        that.expand($choosen, $list);
                    }
                    e.stopPropagation();
                });
                $frame.click(function () {
                    if ($choosen.hasClass('expand')) {
                        that.contract($choosen, $list);
                    }
                });
                $list.on('click', 'a.sel-option', function (e) {
                    that.select($frame, $(this), $choosen, $table, $tbody, pluginName, top);
                    that.contract($choosen, $list);
                    e.stopPropagation();
                });
            }, 'expand': function ($choosen, $list) {
                $list.addClass('expand').slideDown(200);
                $choosen.addClass('expand').children('i.sel-icon').removeClass('fa-caret-down').addClass('fa-caret-up');
            }, 'contract': function ($choosen, $list) {
                $list.removeClass('expand').slideUp(200);
                $choosen.removeClass('expand').children('i.sel-icon').removeClass('fa-caret-up').addClass('fa-caret-down');
            }, 'select': function ($frame, $selected, $choosen, $table, $tbody, pluginName, top) {
                var selected = parseInt($selected.text());
                $choosen.children('.val').text(selected);
                var opt = optHandle.get($table, pluginName);
                opt.paginate.displayLen = selected;
                opt.paginate.currentPage = 1;
                optHandle.set($table, pluginName, opt);
                top.view($frame, $table, $tbody, pluginName, false);
            }
        }, 'info': {
            'draw': function ($bottomBox) {
                var newhtml = '<div class="info-box">显示' + '<span class="start-show-num info-num"></span>鍒�' + '<span class="end-show-num info-num"></span>椤癸紝鍏�' + '<span class="total-num info-num"></span>椤�</div>';
                $bottomBox.prepend(newhtml);
            }, 'set': function ($info, startLen, endLen, totalLen) {
                $info.children('.start-show-num').text(startLen);
                $info.children('.end-show-num').text(endLen);
                $info.children('.total-num').text(totalLen);
            }
        }, 'paginate': {
            'init': function ($frame, $table, $tbody, $paginateFrame, pluginName, opts, top) {
                $paginateFrame.paginate({
                    'visibleGo': opts.paginate.visibleGo,
                    'type': opts.paginate.type,
                    'pageBtnFun': function (page) {
                        var cOpts = optHandle.get($table, pluginName);
                        cOpts.paginate.currentPage = page;
                        optHandle.set($table, pluginName, cOpts);
                        top.view($frame, $table, $tbody, pluginName, false);
                    }
                });
            }, 'refresh': function ($paginateFrame, opts, dataLen) {
                $paginateFrame.paginate('refreshPageBtn', {
                    'pageData': {
                        'currentPage': opts.paginate.currentPage,
                        'dataLen': dataLen,
                        'displayDataLen': opts.paginate.displayLen
                    }
                });
            }
        }, 'view': function ($frame, $table, $tbody, pluginName, isStart) {
            var opts = optHandle.get($table, pluginName);
            opts.beforeShow();
            var that = this;
            if (opts.tbodyData.enabled) {
                that.change($frame, $table, $tbody, '1', opts, pluginName, isStart);
            } else if (opts.serverSide) {
                that.ajax(opts, function (d) {
                    that.change($frame, $table, $tbody, '2', opts, pluginName, isStart, d);
                });
            } else {
                that.change($frame, $table, $tbody, '0', opts, pluginName, isStart);
            }
        }, 'change': function ($frame, $table, $tbody, dataSourceType, opts, pluginName, isStart, ajaxBack) {
            var data = [],
                dataLen = 0,
                ajaxBackData = null,
                ajaxListData = [];
            if (dataSourceType == '1') {
                data = opts.backDataHandle(opts.tbodyData.source);
                dataLen = data.length;
            } else if (dataSourceType == '2') {
                ajaxBack = opts.backDataHandle(ajaxBack);
                ajaxBack = $.extend({}, $.fn[pluginName].defaults.ajaxBack.defaults, ajaxBack);
                if ($.isEmptyObject(ajaxBack)) {
                    opts.errFlag = true;
                } else {
                    opts.errFlag = ajaxBack.errFlag;
                    opts.errMsg = ajaxBack.errMsg;
                    dataLen = ajaxBack.dataLen;
                    ajaxListData = ajaxBack.data;
                    ajaxBackData = ajaxBack.origData;
                }
            } else {
                dataLen = $tbody.children('tr').length;
            }
            var currentPage = opts.paginate.currentPage;
            var displayDataLen = opts.paginate.displayLen;
            var startNo, endNo, pageLen;
            if (opts.paginate.enabled) {
                startNo = this.calculate.displayDataStart(displayDataLen, currentPage, dataLen);
                endNo = this.calculate.displayDataEnd(displayDataLen, currentPage, dataLen);
            } else {
                startNo = 1;
                endNo = dataLen;
            }
            if (dataLen <= 0) {
                startNo = 0;
                endNo = 0;
            }
            if (dataSourceType == '1') {
                this.tbody.draw($tbody, data, startNo, endNo, opts.tbodyRow.write);
                if (opts.tbodyRow.zebra) {
                    this.tbody.row.zebra($tbody);
                }
            } else if (dataSourceType == '2') {
                this.tbody.draw($tbody, ajaxListData, 1, ajaxListData.length, opts.tbodyRow.write);
                if (opts.tbodyRow.zebra) {
                    this.tbody.row.zebra($tbody);
                }
            } else {
                this.tbody.show($tbody, startNo, endNo);
                if (isStart && opts.tbodyRow.zebra) {
                    this.tbody.row.zebra($tbody);
                }
            }
            if (opts.showInfo) {
                var $info = $frame.children('.data-table-bottom-box').children('.info-box');
                this.info.set($info, startNo, endNo, dataLen);
            }
            if (opts.paginate.enabled) {
                this.paginate.refresh($frame.children('.data-table-bottom-box'), opts, dataLen);
            }
            opts.afterShow(opts.errFlag, opts.errMsg, dataLen, ajaxBackData);
        }, 'ajax': function (opts, callback) {
            var toData = {};
            if (opts.paginate.enabled) {
                toData.paginate = true;
                toData.currentPage = opts.paginate.currentPage;
                toData.displayDataLen = opts.paginate.displayLen;
            } else {
                toData.paginate = false;
            }
            toData = $.extend({}, toData, opts.ajaxParam.data);
            toData = opts.sendDataHandle(toData);
            var ajaxParam = {
                'url': opts.ajaxParam.url,
                'type': opts.ajaxParam.type,
                'dataType': opts.ajaxParam.dataType,
                'data': toData,
                'success': function (d) {
                    callback(d);
                }, 'error': function (d) {
                    callback(null);
                }, 'timeout': opts.ajaxParam.timeout
            };
            if (opts.ajaxParam.jsonp != null) {
                ajaxParam.jsonp = opts.ajaxParam.jsonp;
            }
            if (opts.ajaxParam.jsonpCallback != null) {
                ajaxParam.jsonpCallback = opts.ajaxParam.jsonpCallback;
            }
            $.ajax(ajaxParam);
        }, 'refresh': function ($table, pluginName) {
            var $frame = $table.parent('.yhh-data-table-frame');
            var $tbody = $table.children('tbody');
            this.view($frame, $table, $tbody, pluginName, false);
        }
    };
    var optHandle = {
        'get': function ($obj, pluginName) {
            return $obj.data(pluginName);
        }, 'set': function ($obj, pluginName, opt) {
        	var newOpt;
//          var nowOpt = $obj.data(pluginName);
//          if (nowOpt) {
//              newOpt = $.extend(true, {}, nowOpt, opt);
//          } else {
                var defaultOpt = $.fn[pluginName].defaults;
                newOpt = $.extend(true, {}, defaultOpt, opt);
//          }
            $obj.data(pluginName, newOpt);
        }
    }
    $.fn.yhhDataTable = function (options, param) {
    	
        var pluginName = 'yhhDataTable';
        options = options || {};
        var r = [];
        this.each(function () {
            if (typeof options == 'string') {
            	
                if (Method[options]) {
                    if ((typeof (param) == "object" && Object.prototype.toString.call(param).toLowerCase() == "[object object]" && !param.length) || param == undefined) {
                        optHandle.set($(this), pluginName, param);
                        r.push(Method.default($(this), pluginName, options));
                    } else {
                        r.push(Method[options]($(this), pluginName, param));
                    }
                }
            } else {
                optHandle.set($(this), pluginName, options);
                r.push(Method.default($(this), pluginName, 'default'));
            }
        });
        if (r.length == 1) r = r[0];
        return r;
    };
    $.fn.yhhDataTable.defaults = {
        'showInfo': true,
        'tbodyRow': {
            'zebra': true,
            'selected': false,
            'hover': false,
            'write': function (d) {
                // console.log(d);
                var r = '<tr>'
                $.each(d, function (i, val) {
                    if(val.indexOf("td")==-1)
                    {
                        r += '<td title=' + val + '>' + val + '</td>';
                    }
                    else{
                        r += val ;
                    }
                });
                r += '</tr>';
                return r;
            }
        },
        'paginate': {
            'enabled': true,
            'visibleGo': false,
            'type': 'numbers',
            'displayLen': 15,
            'currentPage': 1,
            'changeDisplayLen': false,
            'displayLenMenu': [10, 20, 30, 50]
        },
        'tbodyData': {
            'enabled': false,
            'source': []
        },
        'serverSide': false,
        'ajaxParam': {
            'url': '',
            'type': 'GET',
            'dataType': 'json',
            'jsonp': null,
            'jsonpCallback': null,
            'data': {},
            'timeout': 10 * 1000
        },
        'errFlag': false,
        'errMsg': '',
        'sendDataHandle': function (d) {
            return d;
        }, 'backDataHandle': function (d) {
            return d;
        }, 'beforeShow': function () {}, 'afterShow': function () {}
    };
    $.fn.yhhDataTable.defaults.ajaxBack = {};
    $.fn.yhhDataTable.defaults.ajaxBack.defaults = {
        'errFlag': false,
        'errMsg': '',
        'dataLen': 0,
        'data': [],
        'origData': null
    };
})(jQuery);