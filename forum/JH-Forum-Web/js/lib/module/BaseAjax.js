 //-----------------------  Dzsw Ajax Object -----------------------------------------
 
/**
 * Ajax Object .When create instanse of the ajax object ,must set opts.url property.
 * @param opts
 * @version <1> 2012-09-27 Hayden: Create ajax object base on JQuery Ajax.
 */
 function BaseAjax(opts){
	 if(opts) this.opts = opts;
	 else this.opts={};
 }
 
 /**
  * @version <1> 2012-09-27 Hayden : Ajax Object instanse method , to set default value for ajax properties.
  */
 BaseAjax.prototype.setDefaultValue = function(){
	 if(this.opts.url == undefined) alert("Please set url property.");
	 if(this.opts.type == undefined) this.opts.type="post";
	 if(this.opts.dataType == undefined) this.opts.dataType = "json";
     if(this.opts.contentType == undefined) this.opts.contentType = "application/json;charset=UTF-8";
     if(this.opts.async == undefined) this.opts.async = true;
     if(!this.opts.headers){
         var key = COOKIE_CONFIG.cookieName;
         var arr,reg=new RegExp("(^| )"+key+"=([^;]*)(;|$)");
         if(arr=document.cookie.match(reg)){
             var accessToken = arr[2];
             this.opts.headers = {"AccessToken":accessToken};

         }
     }
 };
 
 /**
  * @version <1> 2012-09-27 Hayden: run Ajax .
  */
 BaseAjax.prototype.run = function(){
	 this.setDefaultValue();
	 var opts = this.opts;
	 $.ajax({
			type:opts.type,
			url:opts.url,
			data: opts.data,
			dataType:opts.dataType,
         	contentType:opts.contentType,
		 	async:opts.async,
         	headers:opts.headers,
			error:function(xhr,msg,e){
				var status = xhr.status;
				if( status == 6000 || status == 6001){
					//登录过期
					// commons.delCookie("AccessToken");
					location.href="index.html";
				}
				else if(status == 6004){
					showMessageWin(xhr.responseJSON.msg)
				} else if(typeof opts.errorFun =="function") opts.errorFun(xhr,msg,e);
			},
			success:function(msg){

				if(typeof opts.successFun =="function") {
					if(toIndexPage(msg)){
						opts.successFun(msg);
					}
				}
			}
		});
 };
 