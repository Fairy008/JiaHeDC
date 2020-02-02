package com.jh.show.report.common;

/**
 * 返回信息常量
 * Created by XZG on 2018/6/22.
 */
public class ResultConstantMsg {


    private static final String testConstant = "返回信息  测试";
    public static final String noData = "暂无数据";
    public static final String noRepeat = "自定义区域名称不可重复";
    public static final String userInfoSessionKey = "userInfo";
    public static final String wechatInfoKey = "wechatInfo";
    public static final String wechatOpenIdKey = "openIdKey";
    public static final String loginMsg = "用户名或密码错误";
    public static final String drawRegionNum = "自定义区域数量不可超过五个";
    public static final String onlyOneRegion = "用户只能定制一个区域";
    public static final String Msg_NoRelateRegion = "未关注区域";
    public static final String Msg_NoParam = "查询条件不全";
    public static final String Msg_ServiceSmsCode_IsEmpty = "手机验证码为空，请先获取验证码";
    public static final String alreadyRegister = "该手机号已注册，请直接登录";
    public static final String noRegistered = "该用户尚未注册，请先注册";
    public static final String imgCodeTimeOut = "图形验证码已过期";
    public static final String imgCodeError = "图形验证码错误";
    public static final String loginFail = "用户登录失败";

    public static final String regionIdKey = "region_id";

    //===================================非空提示================================
    public static final String noPhone = "请输入手机号";
    public static final String noPwd = "请输入密码";
    public static final String noNewPwd = "请确认新密码";
    public static final String pwdDifferent = "两次输入的密码不一致";
    public static final String noImgCode = "请输入图形验证码";
    public static final String noCompany = "请输入单位名称";
    public static final String noAreaOfInterest = "请选择关注区域";
    public static final String noWechatLogin = "微信未登录，请重新登录";
    public static final String noSmsCode = "请输入短信验证码";
    public static final String noConfirmPwd = "请输入确认密码";


    //==================================下载 or 显示==============================
    public static String Value_ContentType_Download = "application/octet-stream";
    public static String Value_ContentType_ShowPdf = "application/pdf";
    public static String Value_ContentType_Html = "text/html";


//    =================================自定义区域面积统计计算=============================
    public static String Msg_CustomRegionArea_Fail = "面积统计失败";
    public static String Msg_CustomRegionArea_ParamError = "面积统计条件不全";


//    =================================关注区域=========================================
    public static String Msg_CustomLocale_ParamError = "修改关注区域参数不全";
    public static String Msg_CustomLocal_ModifySuccess = "修改关注区域成功";
    public static String Msg_CustomLocal_ModifyFail = "修改关注区域失败";
    public static String Msg_CustomLocal_Fail = "用户关注区域失败";
    public static String Msg_CustomLocal_AddRegion_ParamError = "添加关注区域参数不全";
    public static String Msg_CustomLocal_AddRegion_Success = "添加关注区域成功";




//    ================================微信登录====================================
    public static String Msg_Wechatlogin_Fail = "微信登录失败";
    public static String Msg_Wechatlogin_Success = "微信登录成功";
    public static String Msg_Wechat_NoRegister = "微信用户未注册";
    public static String Msg_Wechat_Register = "手机号已绑定微信";
    public static String Msg_Wechat_SmsCode_NotEqual = "手机验证码不正确";


//    =================================字典==================================================
    public static Integer Value_Dict_Resolution = 4010;//GF1 16m

}
