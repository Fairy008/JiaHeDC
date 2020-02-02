package com.jh.forum.bbs.controller;

import com.jh.forum.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:/alipay.properties"},encoding="utf-8")
public abstract class PayBaseController extends BaseController {
    // 支付宝支付参数配置 //
    @Value("${appId}")
    protected String app_id;//应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    @Value("${privateKey}")
    protected String merchant_private_key;//商户私钥，您的PKCS8格式RSA2私钥
    @Value("${publicKey}")
    protected String alipay_public_key;//支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    @Value("${notifyUrl}")
    protected String notify_url;//服务器异步通知页面路径
    @Value("${returnUrl}")
    protected String return_url;//页面跳转同步通知页面路径
    @Value("${signType}")
    protected String sign_type = "RSA2";//签名方式
    @Value("${charset}")
    protected String charset = "utf-8";//字符编码格式
    @Value("${gatewayUrl}")
    protected String gateway_url;//支付宝网关

}
