package com.jh.manage.order.utils;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.jh.util.PropertyUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppPush {

    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
    private static String appId = PropertyUtil.getPropertiesForConfig("APP_PUSH_ID");
    private static String appKey = PropertyUtil.getPropertiesForConfig("APP_PUSH_KEY");
    private static String masterSecret = PropertyUtil.getPropertiesForConfig("APP_MASTER_SECRET");
    private static String url = PropertyUtil.getPropertiesForConfig("APP_PUSH_URL");


    /**
     * 向APP中推送消息
     * @param title
     * @param content
     */
    public static void pushMsg(String title, String content){

//        String url = "http://sdk.open.api.igexin.com/apiex.htm";
//        String appId = "VwCRMCwjvC9iSkOPF0KQf7";
//        String appKey = "kA10qvgLdH6xUMX30YK4H1";
//        String masterSecret = "NgAdU66gVH8CrVIgUQbRG7";


        IGtPush push = new IGtPush(url, appKey, masterSecret);

        // 定义"点击链接打开通知模板"，并设置标题、内容、链接
        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTitle(title);
        template.setText(content);



        // 配置通知栏图标
        template.setLogo("");
        // 配置通知栏网络图标
        template.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(1);
        template.setTransmissionContent("请输入您要透传的内容");


        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);

        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);

        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());

    }



    public static void main(String[] args) throws IOException {

        AppPush.pushMsg("订单审核", "您有新的订单审核消息，请查收");
    }
}
