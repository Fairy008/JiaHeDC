/**
*日期格式化
*fmt  : 日期格式
*@version <1> 2017-11-07 Hayden:Created.
*/
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

/**
* 在日期上增加天数获得新的日期
* @param days:增加天数
* @version <1> 2017-12-24 Hayden:Created.
*/
Date.prototype.addDay = function (days) { 
	var millSeconds = Math.abs(this) + (days * 24 * 60 * 60 * 1000);  
	var rDate = new Date(millSeconds); 
	return rDate;
}

define(function(){
	//=================================================================================================//
	//                                   开始类Base64的定义                                            //
	//=================================================================================================//

	/**
	 * Base64 encode / decode
	 * @version <1> 2017-12-04 lcw :created
	 */
	function Base64() {
		// private property
		_keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

		/*
		 * 生成唯一uuid，作为验证码的token使用
		 * @version <1> 2017-11-24 lcw :created
		 */
		this.guid = function() {
			var str = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx";
			return str.replace(/[xy]/g, function(c) {
				var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
				return v.toString(16);
			});
		}

		/**
		 * method for encode
		 * @version <1> 2017-12-04 lcw :created
		 */
		this.encode=function(input) {
			var output = "";
			var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
			var i = 0;
			input = this.encode_utf8(input);
			while (i < input.length) {
				chr1 = input.charCodeAt(i++);
				chr2 = input.charCodeAt(i++);
				chr3 = input.charCodeAt(i++);
				enc1 = chr1 >> 2;
				enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
				enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
				enc4 = chr3 & 63;
				if (isNaN(chr2)) {
					enc3 = enc4 = 64;
				} else if (isNaN(chr3)) {
					enc4 = 64;
				}
				output = output +
						_keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
						_keyStr.charAt(enc3) + _keyStr.charAt(enc4);
			}
			return output;
		}

		/**
		 * method for decode
		 * @version <1> 2017-12-04 lcw :created
		 */
		this.decode=function(input) {
			var output = "";
			var chr1, chr2, chr3;
			var enc1, enc2, enc3, enc4;
			var i = 0;
			input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
			while (i < input.length) {
				enc1 = _keyStr.indexOf(input.charAt(i++));
				enc2 = _keyStr.indexOf(input.charAt(i++));
				enc3 = _keyStr.indexOf(input.charAt(i++));
				enc4 = _keyStr.indexOf(input.charAt(i++));
				chr1 = (enc1 << 2) | (enc2 >> 4);
				chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
				chr3 = ((enc3 & 3) << 6) | enc4;
				output = output + String.fromCharCode(chr1);
				if (enc3 != 64) {
					output = output + String.fromCharCode(chr2);
				}
				if (enc4 != 64) {
					output = output + String.fromCharCode(chr3);
				}
			}
			output =this.decode_utf8(output);
			return output;
		}

		/**
		 * method for utf8 encode 
		 * @version <1> 2017-12-04 lcw :created
		 */
		this.encode_utf8=function(string) {
			string = string.replace(/\r\n/g,"\n");
			var utftext = "";
			for (var n = 0; n < string.length; n++) {
				var c = string.charCodeAt(n);
				if (c < 128) {
					utftext += String.fromCharCode(c);
				} else if((c > 127) && (c < 2048)) {
					utftext += String.fromCharCode((c >> 6) | 192);
					utftext += String.fromCharCode((c & 63) | 128);
				} else {
					utftext += String.fromCharCode((c >> 12) | 224);
					utftext += String.fromCharCode(((c >> 6) & 63) | 128);
					utftext += String.fromCharCode((c & 63) | 128);
				}
			}
			return utftext;
		}

		/**
		 * method for utf8 encode 
		 * @version <1> 2017-12-04 lcw :created
		 */
		this.decode_utf8 = function(string) {
			var string = "";
			var i = 0;
			var c = c1 = c2 = 0;
			while ( i < utftext.length ) {
				c = utftext.charCodeAt(i);
				if (c < 128) {
					string += String.fromCharCode(c);
					i++;
				} else if((c > 191) && (c < 224)) {
					c2 = utftext.charCodeAt(i+1);
					string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
					i += 2;
				} else {
					c2 = utftext.charCodeAt(i+1);
					c3 = utftext.charCodeAt(i+2);
					string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
					i += 3;
				}
			}
			return string;
		}
	}
	//=================================================================================================//
	//                                   结束类Base64的定义                                            //
	//=================================================================================================//


	//=================================================================================================//
	//                         开始类公用Cookie操作类的定义                                            //
	//=================================================================================================//
	function CookieUtil(){};

	/**
	* 设置cookie值
	* @param key : 
	* @param value:
	* @param minuties : 过期时间，分钟
	* @version <1> 2017-11-14 Hayden:Created.
	*/
	CookieUtil.setCookie=function(key,value,minuties){
		if(key && value){
			expires = CookieUtil.getExpires(minuties);
			document.cookie = key+"="+value+";expires="+expires;
		} 
	}

	/**
	* 获取Cookie过期时间*
	* @version <1> 2017-12-24 Hayden:Created.
	*/
	CookieUtil.getExpires=function(minuties){
		if(!minuties){
			minuties = 30;
		}
		var exp = new Date();
		exp.setTime(exp.getTime() + 60 * 1000 * minuties);//过期时间 30分钟
		return exp.toGMTString();
	}

	/**
	* 从cookie中根据key值获取信息
	* @param key : cookie键值
	* @version <1> 2017-11-14 Hayden:Created.
	*/
	CookieUtil.getCookie=function(key){
		var arr,reg=new RegExp("(^| )"+key+"=([^;]*)(;|$)");
		if(arr=document.cookie.match(reg))
			return unescape(arr[2]);
		else
			return null;
	}

	/**
	* 从cookie中根据key值删除信息
	* @param key : cookie键值
	* @version <1> 2017-11-14 Hayden:Created.
	*/
	CookieUtil.delCookie=function(key){
		var exp = new Date();
		exp.setTime(exp.getTime() - 1 * 24 * 60 * 60 * 1000);
		var cval=CookieUtil.getCookie(key);
		if(cval!=null)
			document.cookie= key + "=;expires="+exp.toGMTString();  
	}
	//=================================================================================================//
	//                         结束类公用Cookie操作类的定义                                            //
	//=================================================================================================//

	//暴露区域选择器控件
	return {
		Base64:Base64,
		CookieUtil:CookieUtil
	};
});
