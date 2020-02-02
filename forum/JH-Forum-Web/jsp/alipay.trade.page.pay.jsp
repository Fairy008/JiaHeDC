<%@ page import="com.alipay.api.DefaultAlipayClient" %>
<%@ page import="com.alipay.api.AlipayClient" %>
<%@ page import="com.alipay.api.request.AlipayTradePagePayRequest" %>
<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>付款</title>
</head>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%


    String serverUrl = "https://openapi.alipaydev.com/gateway.do";
    String appId = "2016080600181102";
    String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCH9oK7vE7bD7svNqo110jh/lEwPCc2sM1vhJI/S5TKwC3OCIeBPa1Zrku3QjzqdOoQSDLZhAf1Uwkp3gMDN3PqfAYOndfT5Zfk22pmsvjkNZvsWl1pO5PAhvBVDvnnBDKbFlG++8GFcY53fDxAks6Dtx2eMS+6rsHZZ15uwwTzJvv0RTBM5eQ7y6BO6CiBbQWzviEurPZLIrP2u/qqPVQLkR8hXOvXZ/lAM8H/rN/cwGFmzhGgqiYQ6xzOAnR+8iPTFe4uFZPwlCX136zhTjKC5xx0SFswTrkaMDE7BFexpis9nJ/FU1fE4gEMCtYnmW87UY88xFdyrhS8ZMmgXiF5AgMBAAECggEBAIE9OZfqr6pbkbqXY8Q6F/V6IQKQq1W5Sg4vHjU8SvtPIjsusF+RmS4eF6rjM7RJ3hQMDQnmDwqLG7LIbmSoWW8S5U8PixDItVG9n65vgVar/sL0Qsa88v2XRuwTiEioHNJ+rMW12YZXIu1BEqTu548ciPLOHmh9pZZaQ9FVX2cgzLQ0iFYmebJIur1QcA85HKhQBoIuld8vCXg2yYOXkiON66PrNnB1q6rYzh77Cm7C0srwSMa/+CUjNiFhPIzxpX0gH+ok3pjgnUT5OCr4YN8oGbHFDWwADp9trGMaKToRBBJESYZTYT1DL2Yt0fMpJFqM4QTBQbdD6dYohO//3mECgYEAvZJHJSi5KOpwrICQ4Jmj4uxJV8OiqR84/5YXnUeJfbn0ZhGI6ZsE2owBF1LjwLsQlTwKe8Stx7WcBHZzFuj7iszCm1DpG8LDEQLXQ7lW5YrDki2LimBl9ZRuk1qZYLTaOLeBJIIlVcwYyL0QkK8lk6vsRHbrC0AgVhhqQxE+wksCgYEAt5s5InjEjO14Wuk4Sk6EJcPSF3znSf3mcn+cCkzFK/qft8WqixSoLIrSiQtc9L93cV/pJYdDWdTvDYpBn70Gy5eLprgzw8TAIDPeganz5hJNtzWMhnGiGpx9Bex9MVtNz/3FoOTXHrwtu8VwU3irhH28j7SlS5IZXJCjZLIEMMsCgYAE1h6KjCdtK84EGKOZl1HfEDguzQbxWaKSormngZO4aW4azIe5rgLJsJi2h57X4+3gYM/DqJjmZ3O7iWoWQ4tnVyH/5GiOFMH1RSVIzQmygX/L0u2kciEnysmnaMBQ/0wx9jyNOeTJ85MOTASILG/A/LniZUZJ2zD4AVd0rpPzUQKBgQCut4sa/okrAh7xg9UJAMZHmZwmtbjydCYC9uPp+Tv3qbqrCfJL/xA5srC9sI54aGqk/HvENn8k0LfazqG7/AinXEKMF+VKecPJ4RHvmWZ6nRhPKynaFfAIGrnuFSjc+uGE9xxFCv6aRL/QWwWUqNOudGgm5+QTkaeIqiqR+8R5GQKBgQCs58ZGGroVWYqDUI3P+ZT6OJj6PwMIj2yrv3pgWM8QkgXitnrdIcv/WJEpbTAKafwNEWQHyEuDhtLZBNGcc8dvzIxxCrZ5S9AzPdo+yxeD8v0VBi7RAd2wPV2xaV9dsQaYB9uSGutCuygNcUfJhiGugxOq4tE9prhkWbX+HgOAXA==";
    String format = "json";
    String charset = "utf-8";
    String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3FrpqxDZjglaMpk8Rheu9QTtj2dRjkn5HoTInvN9sEaYZaTOFxfrYvBeAqVJH9piyUv7nkCyErUrKP2uyHulinL8WV05kd0lUxh328y1FRVHyJ17Q+hOWX/FiXHcHJuRPxsmOsmWVh8CcJyEJ2qQGB9fhsy0HbHVnzBHYBLOxpePeoL1FHRslMwIN8oZJCKEEfo6TCipHf8Ss+FXQpGi2HXX4xuWG/BVEsPl6/TG7oYdg0V9kkeAK9gUvg9htSnYVerhKV9G3xnz7htG9F4YxoHfrJG40nZ/LCfmYq0EWa1IaA7fzDBdKOAuN+iuN5lEtLTeC86VqrXViZzOe7hNiwIDAQAB";
    String signType = "RSA2";
//    String returnUrl = "http://localhost:8080/paySuccess.html";
    String returnUrl = "http://192.168.1.223:8080/Forum/paySuccess.html";
    String notifyUrl = "http://192.168.1.223:8080/JH_Forum_Web/";
    AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);

    //获得初始化的AlipayClient
    //AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);


    //设置请求参数
    AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
    alipayRequest.setReturnUrl(returnUrl);
    alipayRequest.setNotifyUrl(notifyUrl);


    //商户订单号，商户网站订单系统中唯一订单号，必填
    String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
    //付款金额，必填
    String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
    //订单名称，必填
    String subject = new String(request.getParameter("subject").getBytes("ISO-8859-1"),"UTF-8");
    //商品描述，可空
    String body = new String(request.getParameter("body").getBytes("ISO-8859-1"),"UTF-8");

    alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
            + "\"total_amount\":\""+ total_amount +"\","
            + "\"subject\":\""+ subject +"\","
            + "\"body\":\""+ body +"\","
            + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

    //请求
    String result = alipayClient.pageExecute(alipayRequest).getBody();//调用sdk生成表单

    //输出，注意输出格式，错误容易验签失败
    response.setContentType("text/html; charset=utf-8");
    PrintWriter outas = response.getWriter();

    //输出
    outas.println(result);


%>
<body>
</body>
</html>