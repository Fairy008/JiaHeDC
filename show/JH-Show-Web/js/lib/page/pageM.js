/*
 * 分页page
 */
(function($) {
	$.fn.pageM = function (opt,vals) {
		var target = $(this),
			operate = $.fn.pageM.prototype.func;
		if(typeof(opt) == "object"){
			if(operate.isPage(target)){//生成
				var option = $.extend({}, $.fn.pageM.defaults, opt);
				option = operate.faultTolerant(option);//初始化容错处理
				operate.addPage(target,option);//添加默认html
				operate.setPage(target,option);//设置页面分页数目
				operate.setNum(target,option);//设置当前页码
				operate.setEvents(target,option);//设置事件
			}else{//设置
				var option = target.data().pageM;
				operate.getPageObj(target,option,opt);
				return operate.returnData(target);
			}
		}else if(typeof(opt) == "string"){//查询属性或设置属性
			if(typeof(vals) != "undefined" ){//设置
				var option = target.data().pageM;
				if( opt == "pageNum" || opt == "total" || opt == "pageSize" ){
					operate.getPage(target,option,opt,vals);//设置
					return operate.returnData(target);
				}else{
					return false;
				}			
			}else{//查询属性
				if(target.data().pageM[opt] !== undefined){
					return target.data().pageM[opt];
				}else{
					return null;
				}
			}
		}else if(typeof(opt) == "undefined"){//返回所有对象
			return operate.returnData(target);
		}
	};
	$.fn.pageM.prototype = {
		func : {//正常操作
			faultTolerant : function(option){//初始化容错处理
				option.pageNum = (typeof(option.pageNum)=="string"||typeof(option.pageNum)=="number") && !isNaN(option.pageNum) ? parseInt(option.pageNum,10) : 0;
				option.total = (typeof(option.total)=="string"||typeof(option.total)=="number") && !isNaN(option.total) ? parseInt(option.total,10) : 0;					
				option.pageSize = (typeof(option.pageSize)=="string"||typeof(option.pageSize)=="number") && !isNaN(option.pageSize) ? parseInt(option.pageSize,10) : 0;
				option.pageEllipsis = (typeof(option.pageEllipsis)=="string"||typeof(option.pageEllipsis)=="number") && !isNaN(option.pageEllipsis) ? parseInt(option.pageEllipsis,10) : 0;
				
				return option;
			},
			addPage : function($this,option){//添加默认的html
				
				if($this.find(".pageMain").length>1)return;
				var searchHtml = '<input type="text" value="" /><span class="pageMainSear">'+option.pageSeaTexe+'</span>',
					labelHtml = '<label class="pageMainLabel"></label>',
					pagePosi = '';
				if( option.pagePosition == "RIGHT" ){
					pagePosi = 'right';
				}else if( option.pagePosition == "LEFT" ){
					pagePosi = 'left';
				}else{
					pagePosi = 'center';
				}
				$this.html(function(){
					return 	'<style>.pageMain{float:right; overflow: hidden; zoom:1; text-align: '+pagePosi+'; padding-top: 10px; display:block; } .pageMain *{ font-family: "'+option.pageFontFamily+'";} .pageMain span{ display: inline-block; padding: 3px 8px; border: 1px #41bfc6 solid; font-size:12px; cursor: pointer; color: #333 !important; margin: 0px 4px; border-radius: 3px;background:#fff;} .pageMain div{ display: inline-block;} .pageMain label{ padding: 2px 0px; font-size:14px; color:#a2a2a2;} .pageMain span.active{ cursor:auto; color: #fff !important; background: #41bfc6; border-color: #41bfc6;} .pageMain span.spanPreNex{ cursor:auto !important; color: #333 !important; border-color: #41bfc6 !important; background: #fff !important;} .pageMain input{ width: 40px;  border: 1px #41bfc6 solid; text-align: center; color: #333; font-size: 12px; border-radius: 3px; outline:none;} .pageMain input[type="text"]{ height:22px; line-height:22px;} .pageMain span:hover,.pageMain input.pageMainSear:hover{ color: #fff; background: #41bfc6; border-color: #41bfc6;} .pageMain .pageMainLabel{ color: #fe2725;font-size:11px;}</style>'+
							'<div class="pageMain">'+( option.isLabelPrompt === true ?  (option.pagePosition == "RIGHT" ? labelHtml : '') : '' )+'<span class="pageMainPre">'+option.pagePrev+'</span><div><span>1</span></div><span class="pageMainNex">'+option.pageNext+'</span>'+( option.isSearch === true ? searchHtml : '' )+( option.isLabelPrompt === true ? (option.pagePosition != "RIGHT" ? labelHtml : '') : '' )+'</div>';
				});
			},
			setPage : function($this,option){//设置全部页码显示
				$this.find(".pageMain").find("div").html(function() {
					
					if( option.total !== 0 ){//option.total不为0
						var str = "";
						if (option.total / option.pageSize > option.pageEllipsis || option.pageNum > option.pageEllipsis ){//需要省略号了
							if (option.total % option.pageSize == 0) {//正好
								if(option.pageNum-option.pageEllipsis>2){//左侧隐藏
									str = str + "<span>1</span>"+"<label>...</label>";
									if(option.pageNum+option.pageEllipsis <(parseInt(option.total / option.pageSize) + 1)){//右侧隐藏
										for(var i = option.pageNum-option.pageEllipsis, l = option.pageNum+option.pageEllipsis+1; i < l; i++){
											str = str + "<span>" + i + "</span>";
										}
										str = str +"<label>...</label>"+"<span>"+(parseInt(option.total / option.pageSize)+1)+"</span>";
									}else{
										for(var i = option.pageNum-option.pageEllipsis, l = option.total / option.pageSize+1 ; i < l; i++){
											str = str + "<span>" + i + "</span>";
										}
									}						
								}else{
									if(option.pageNum+option.pageEllipsis <(parseInt(option.total / option.pageSize))){
										for(var i = 1, l = option.pageNum+option.pageEllipsis+1; i < l; i++){
											str = str + "<span>" + i + "</span>";
										}
										str = str +"<label>...</label>"+"<span>"+parseInt(option.total / option.pageSize)+"</span>";
									}else{
										for(var i = 1, l = option.total / option.pageSize +1; i < l; i++){
											str = str + "<span>" + i + "</span>";
										}
									}								
								}					
							} else {//多余
								if(option.pageNum-option.pageEllipsis>2){//左侧隐藏								
									str = str + "<span>1</span>"+"<label>...</label>";
									if(option.pageNum+option.pageEllipsis <(parseInt(option.total / option.pageSize) + 1)){//右侧隐藏
										for(var i = option.pageNum-option.pageEllipsis, l = option.pageNum+option.pageEllipsis+1; i < l; i++){
											str = str + "<span>" + i + "</span>";										
										}
										str = str +"<label>...</label>"+"<span>"+(parseInt(option.total / option.pageSize)+1)+"</span>";
									}else{
										for(var i = option.pageNum-option.pageEllipsis, l = option.total / option.pageSize + 1; i < l; i++){
											str = str + "<span>" + i + "</span>";
										}
									}							
								}else{
									if(option.pageNum+option.pageEllipsis < parseInt(option.total / option.pageSize) ){									
										for(var i = 1, l = option.pageNum+option.pageEllipsis+1; i < l; i++){
											str = str + "<span>" + i + "</span>";
										}
										str = str +"<label>...</label>"+"<span>"+(parseInt(option.total / option.pageSize)+1)+"</span>";
									}else{
										for(var i = 1, l = option.total / option.pageSize + 1; i < l; i++){
											str = str + "<span>" + i + "</span>";
										}
									}								
								}							
							}
						} else {//不用省略，长度小于7
							if (option.total % option.pageSize == 0) {//正好
								for (var i = 1, l = option.total / option.pageSize + 1; i < l; i++) {
									str = str + "<span>" + i + "</span>";
								}
							} else {//多余
								for (var i = 1, l = parseInt(option.total / option.pageSize) + 2; i < l; i++) {
									str = str + "<span>" + i + "</span>";
								}
							}
						}
						return str;
					}else {
						return "";
					}
					
				});
			},
			setNum : function(target,option){//设置当前页码是那一页
				var $list = target.find(".pageMain div").find("span");
				target.find(".pageMain div").find("span").removeClass('active');
				for(var i=0,len=$list.length;i<len;i++){
					if(parseInt($.trim($list.eq(i).text()),10) == option.pageNum){
						target.find(".pageMain div").find("span").eq(i).addClass('active');
					}
				}
				target.find(".pageMain").find("input").val(option.pageNum);
				if( option.total > 0 ){
					if( option.pageNum <= 1 ){
						target.find(".pageMain .pageMainPre").addClass("spanPreNex");
					}else{
						target.find(".pageMain .pageMainPre").removeClass("spanPreNex");
					}
					if( option.pageNum >= parseInt(target.find(".pageMain div").find("span").eq(target.find(".pageMain div").find("span").length-1).text(),10)){
						target.find(".pageMain .pageMainNex").addClass("spanPreNex");
					}else{
						target.find(".pageMain .pageMainNex").removeClass("spanPreNex");
					}
				}
				
				
				
				target.data({"pageM" :option});
			},
			setEvents : function(target,option){//添加默认事件
				var thisObj = this;
				target.on("click",".pageMain .pageMainPre",function(){//上一页
					if(!$(this).hasClass("spanPreNex")){
						option.pageNum = option.pageNum-1;
						thisObj.eventData(target,option,true);
					}
				}).on("click",".pageMain .pageMainNex",function(){//下一页
					if(!$(this).hasClass("spanPreNex")){
						option.pageNum = option.pageNum + 1;
						thisObj.eventData(target,option,true);
					}
				}).on("click",".pageMain div span",function(){//页码翻页
					var $this = $(this);
					if(!$(this).hasClass("active")){
						option.pageNum = parseInt($.trim($this.text()),10);
						thisObj.eventData(target,option,true);
					}
				}).on("click",".pageMain .pageMainSear",function(){//到第几页
					var str = parseInt($.trim(target.find(".pageMain").find("input").val()),10);//.replace(/(^\s*)|(\s*$)/g, "")
					if(!isNaN($.trim(target.find(".pageMain").find("input").val()))){
						if(str >= 1 && str <= parseInt(target.find(".pageMain div").find("span").eq(target.find(".pageMain div").find("span").length-1).text()) ){
							option.pageNum = str;
							thisObj.eventData(target,option,true);
						}else{
							target.find(".pageMain").find(".pageMainLabel").text("请输入满足范围的数字：");
						}
					}else{
						target.find(".pageMain").find(".pageMainLabel").text("请输入正确的数字：");
					}
				}).on("keypress",".pageMain input",function(e){//到第几页搜索
					var keycode = (e.keyCode ? e.keyCode : e.which);  
				    if(keycode == '13'){
				    	var str = parseInt($.trim(target.find(".pageMain").find("input").val()),10);		
						if(!isNaN($.trim(target.find(".pageMain").find("input").val()))){
							if(str >= 1 && str <= parseInt(target.find(".pageMain div").find("span").eq(target.find(".pageMain div").find("span").length-1).text(),10) ){
								option.pageNum = str;
								thisObj.eventData(target,option,true);
							}else{
								target.find(".pageMain").find(".pageMainLabel").text("请输入满足范围的数字：");
							}
						}else{
				    		target.find(".pageMain").find(".pageMainLabel").text("请输入正确的数字：");
				    	}
				    }	    
				}).on("focus",".pageMain input",function(){
					thisObj.eventData(target,option,false);
				});
			},
			eventData : function(target,option,bool){//事件执行
				var thisObj = this;
				if(bool === true){
					thisObj.setPage(target,option);
					thisObj.setNum(target,option);
					option.pageEvent(option);
				}
				target.find(".pageMain").find(".pageMainLabel").text('');
			},
			getPage : function(target,option,strItem,values){//设置当前页面
				var thisObj = this;
				option[strItem] = parseInt(values,10);
				thisObj.setPage(target,option);//设置页面分页数目
				thisObj.setNum(target,option);//设置当前页码
			},
			isPage : function(target){//判断是否已经有分页的插件了
				
				if(target.data() && target.data().pageM!= undefined){
					return false;
				}else{
					return true;	
				}
			},
			getPageObj : function(target,option,obj){//对象形式的参数写入
				var thisObj = this;
				if(obj.constructor === Object){
					for( var i in obj){
						if( i == "pageNum" || i == "total" || i == "pageSize" ){
							option[i] = obj[i];
						}
					}
				}else{
					// console.info('方法使用错误，参数非对象');
				}
				thisObj.setPage(target,option);//设置页面分页数目
				thisObj.setNum(target,option);//设置当前页码
			},
			returnData : function(target){//返回所有属性
				return target.data().pageM;
			}
		}
	};
	$.fn.pageM.defaults = {
		"pageNum" : 1,//当前页码
		"total" : 0,//数据总数
		"pageSize" : 12,//一页显示多少条数据
		"pageEllipsis" : 3,//多少个后就开始用省略（如果是0）将不使用省略号
		"pagePrev" : "上一页",//上一页
		"pageNext" : "下一页",//下一页
		"pageSeaTexe" : "确定",//搜索项
		"pageFontFamily" : "微软雅黑",//默认字体
		"pagePosition" : "CENTER",//page的位置是在左边还是右边还是中间（目前只有CENTER/LEFT/RIGHT）
		"pageEvent" : function(option){},//翻页后执行的事件
		"isLabelPrompt" : true,//是否开启label提示信息
		"isSearch" : true//是否有搜索项
	};
})(jQuery);
