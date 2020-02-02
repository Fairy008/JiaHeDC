/**
 * 用于扩展jquery的方法
 * @version <1> 2018-04-26 lxy： Create
 */
(function ($) {
    var oldHTML = $.fn.html;
    /**
     * 扩展jQuery的html()方法，获取html页面的时候，在文本框、文本域、radio、checkbox、select 有变化后，
     * 可以自动复制到原来的html页面中。总的来说，就是使用formhtml()方法，可以获取到有变化值的html页面，
     * 在农情简报的编辑页面，刚好可以解决这个问题。
     * @returns {*}
     */
    $.fn.formhtml = function () {
        if (arguments.length) return oldHTML.apply(this, arguments);
        $("input,textarea,button", this).each(function () {
            this.setAttribute('value', this.value);
        });
        //解决textarea在火狐不显示值
        if(window.addEventListener){//不是IE
            $("textarea", this).each(function(){
                $(this).html(this.value);
            });
        }
        $(":radio,:checkbox", this).each(function () {
            // im not really even sure you need to do this for "checked"
            // but what the heck, better safe than sorry
            if (this.checked) this.setAttribute('checked', 'checked');
            else this.removeAttribute('checked');
        });
        $("option", this).each(function () {
            // also not sure, but, better safe...
            if (this.selected) this.setAttribute('selected', 'selected');
            else this.removeAttribute('selected');
        });
        return oldHTML.apply(this);
    };
    //optional to override real .html() if you want
    // $.fn.html = $.fn.formhtml;

})(jQuery);
