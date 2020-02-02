
//图片滚动列表 mengjia 070816
var Speed = 1; //速度(毫秒)
var Space = 5; //每次移动(px)
var PageWidth = 420; //翻页宽度
var fill = 0; //整体移位
var MoveLock = false;
var MoveTimeObj;
var Comp = 0;
var AutoPlayObj = null;
GetObj("List2").innerHTML = GetObj("List1").innerHTML;
GetObj('ISL_Cont').scrollLeft = fill;
GetObj("ISL_Cont").onmouseover = function(){clearInterval(AutoPlayObj);}
GetObj("ISL_Cont").onmouseout = function(){AutoPlay();}
AutoPlay();
function GetObj(objName){if(document.getElementById){return eval('document.getElementById("'+objName+'")')}else{return eval('document.all.'+objName)}}
function AutoPlay(){ //自动滚动
    clearInterval(AutoPlayObj);
    AutoPlayObj = setInterval('ISL_GoDown();ISL_StopDown();',3000); //间隔时间
}
function ISL_GoUp(){ //上翻开始
    if(MoveLock) return;
    clearInterval(AutoPlayObj);
    MoveLock = true;
    MoveTimeObj = setInterval('ISL_ScrUp();',Speed);
}
function ISL_StopUp(){ //上翻停止
    clearInterval(MoveTimeObj);
    if(GetObj('ISL_Cont').scrollLeft % PageWidth - fill != 0){
        Comp = fill - (GetObj('ISL_Cont').scrollLeft % PageWidth);
        CompScr();
    }else{
        MoveLock = false;
    }
    AutoPlay();
}
function ISL_ScrUp(){ //上翻动作
    if(GetObj('ISL_Cont').scrollLeft <= 0){GetObj('ISL_Cont').scrollLeft = GetObj('ISL_Cont').scrollLeft + GetObj('List1').offsetWidth}
    GetObj('ISL_Cont').scrollLeft -= Space ;
}
function ISL_GoDown(){ //下翻
    clearInterval(MoveTimeObj);
    if(MoveLock) return;
    clearInterval(AutoPlayObj);
    MoveLock = true;
    ISL_ScrDown();
    MoveTimeObj = setInterval('ISL_ScrDown()',Speed);
}
function ISL_StopDown(){ //下翻停止
    clearInterval(MoveTimeObj);
    if(GetObj('ISL_Cont').scrollLeft % PageWidth - fill != 0 ){
        Comp = PageWidth - GetObj('ISL_Cont').scrollLeft % PageWidth + fill;
        CompScr();
    }else{
        MoveLock = false;
    }
    AutoPlay();
}
function ISL_ScrDown(){ //下翻动作
    if(GetObj('ISL_Cont').scrollLeft >= GetObj('List1').scrollWidth){GetObj('ISL_Cont').scrollLeft = GetObj('ISL_Cont').scrollLeft - GetObj('List1').scrollWidth;}
    GetObj('ISL_Cont').scrollLeft += Space ;
}
function CompScr(){
    var num;
    if(Comp == 0){MoveLock = false;return;}
    if(Comp < 0){ //上翻
        if(Comp < -Space){
            Comp += Space;
            num = Space;
        }else{
            num = -Comp;
            Comp = 0;
        }
        GetObj('ISL_Cont').scrollLeft -= num;
        setTimeout('CompScr()',Speed);
    }else{ //下翻
        if(Comp > Space){
            Comp -= Space;
            num = Space;
        }else{
            num = Comp;
            Comp = 0;
        }
        GetObj('ISL_Cont').scrollLeft += num;
        setTimeout('CompScr()',Speed);
    }
}



/*点击弹出按钮*/
var popBox = function() {
    var popBox = document.getElementById("popBox");
    popBox.style.display = "block";
};
/*点击关闭按钮*/
var closeBox = function() {
    var popBox = document.getElementById("popBox");
    popBox.style.display = "none";
}

var imgShow = function (outerdiv, innerdiv, bigimg, _this){
    var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
    $(bigimg).attr("src", src);//设置#bigimg元素的src属性

    /*获取当前点击图片的真实大小，并显示弹出层及大图*/
    $("<img/>").attr("src", src).load(function(){
        var windowW = $(window).width();//获取当前窗口宽度
        var windowH = $(window).height();//获取当前窗口高度
        var realWidth = this.width;//获取图片真实宽度
        var realHeight = this.height;//获取图片真实高度
        var imgWidth, imgHeight;
        var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

        if(realHeight>windowH*scale) {//判断图片高度
            imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放
            imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度
            if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度
                imgWidth = windowW*scale;//再对宽度进行缩放
            }
        } else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度
            imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放
            imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度
        } else {//如果图片真实高度和宽度都符合要求，高宽不变
            imgWidth = realWidth;
            imgHeight = realHeight;
        }
        $(bigimg).css("width",imgWidth);//以最终的宽度对图片缩放

        var w = (windowW-imgWidth)/2;//计算图片与窗口左边距
        var h = (windowH-imgHeight)/2;//计算图片与窗口上边距
        $(innerdiv).css({"top":h, "left":w});//设置#innerdiv的top和left属性
        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
    });

    $(outerdiv).click(function(){//再次点击淡出消失弹出层
        $(this).fadeOut("fast");
    });
}
