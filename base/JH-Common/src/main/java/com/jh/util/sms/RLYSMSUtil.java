package com.jh.util.sms;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.jh.constant.PropertiesConstant;
import com.jh.constant.SysConstant;
import com.jh.util.PropertyUtil;
import com.jh.util.RandomUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 容联云短信
 * Created by zhaoyong on 2016年12月13日.
 */
public class RLYSMSUtil implements SMSUtil {

    private static Logger logger = Logger.getLogger(RLYSMSUtil.class);

    /**
     * 当前使用的用户环境
     * @version <1> 2017-10-30 ZhaoYong:Created.
     */
    private static String RLY_SMS_CUR_URL = PropertyUtil.getPropertiesForConfig("RLY_SMS_RUNTIME_URL",PropertiesConstant.SMS_CONFIG);

    /**
     * 初始化短信
     * @version <1> 2017-10-30 ZhaoYong:Created.
     */
    private static CCPRestSmsSDK initSMS() {
        CCPRestSmsSDK rest = new CCPRestSmsSDK();

        try {
            //初始化服务器地址和端口
            rest.init(RLY_SMS_CUR_URL, PropertyUtil.getPropertiesForConfig("RLY_SMS_PORT",PropertiesConstant.SMS_CONFIG));
            //初始化主账号和主账号令牌
            rest.setAccount(PropertyUtil.getPropertiesForConfig("RLY_ACCOUNT_SID",PropertiesConstant.SMS_CONFIG),
                    PropertyUtil.getPropertiesForConfig("RLY_ACCOUNT_AUTHTOKEN",PropertiesConstant.SMS_CONFIG));
            //初始化应用ID
            rest.setAppId(PropertyUtil.getPropertiesForConfig("RLY_APP_ID",PropertiesConstant.SMS_CONFIG));
        }catch (Exception e){
            e.printStackTrace();
        }


        return rest;
    }

    /**
     * 使用短信发送接口发送短信息。
     *
     * @param mobile         手机号码
     * @param verifyTemplate 短信模板
     * @return
     * @version <1> 2016-12-13 zhaoyong:Created.
     */
    private static ResultMessage sendVerifyMessage(String mobile, String verifyTemplate) {
        Map<String,Object> map = new HashMap<String,Object>();
        if (verifyTemplate == null || StringUtils.isEmpty(verifyTemplate)) {
            verifyTemplate = PropertyUtil.getPropertiesForConfig("RLY_VERIFY_TEMPLATE");
        }

        try {
            CCPRestSmsSDK rest = initSMS();
            if (rest != null) {
                String smsCode = RandomUtil.RandomFourNumber();
                HashMap<String, Object> smsResult = rest.sendTemplateSMS(mobile, verifyTemplate, new String[]{smsCode,
                        PropertyUtil.getPropertiesForConfig("RLY_VERIFYCODE_TIME",PropertiesConstant.SMS_CONFIG)});

                if (PropertyUtil.getPropertiesForConfig("RLY_SEND_SUCCESSCODE",PropertiesConstant.SMS_CONFIG).equals(smsResult.get("statusCode"))) {
//                    String smsCodeKey = mobile + "_sms_code";
//                    Map<String,Object> cacheMap = new HashMap<String,Object>();
//                    cacheMap.put("keyName",smsCodeKey);
//                    cacheMap.put("keyValue",smsCode);

                    return ResultMessage.success(smsCode,smsCode);
                } else if (PropertyUtil.getPropertiesForConfig("RLY_SEND_UPPERLIMIT",PropertiesConstant.SMS_CONFIG).equals(smsResult.get("statusCode"))){
//                    文件发送次数已达上限
                    return ResultMessage.fail(SysConstant.Msg_Sms_Verification_Send_Upperlimit);
                }else {
                    return ResultMessage.fail(SysConstant.Msg_Sms_Verification_Send_Fail);
                }
            } else {
                //短信接口初始化失败
                return ResultMessage.fail(SysConstant.Msg_Sms_Verification_Init_Fail);
            }
        }catch(Exception e){
            return ResultMessage.fail(e.getMessage());
        }
    }

    @Override
    public ResultMessage sendVerifyMessage(String moblie) {
        return sendVerifyMessage(moblie, PropertyUtil.getPropertiesForConfig("RLY_VERIFY_TEMPLATE",PropertiesConstant.SMS_CONFIG));
    }
}
