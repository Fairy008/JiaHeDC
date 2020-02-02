package com.jh.forum.bbs.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.google.gson.Gson;
import com.jh.forum.bbs.service.IForumOrdersService;
import com.jh.forum.bbs.vo.AlipayVo;
import com.jh.forum.bbs.vo.OrdersVo;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/alipay")
public class AlipayController extends PayBaseController {
    private final static Logger logger = LoggerFactory.getLogger(AlipayController.class);

    @Autowired
    private IForumOrdersService forumOrdersService;

    @GetMapping("/pay")
    private ResultMessage alipayPay(HttpServletRequest request, HttpServletResponse response, String trade_no) throws AlipayApiException, IOException {
        //前端传递订单号  后台查询订单信息
        OrdersVo ordersVo = forumOrdersService.findOrderById(trade_no);
        //判断该订单是否已经支付 如果已经支付给出提示  如果未支付就发送请求到支付宝
        ResultMessage resultMessage = new ResultMessage();
        if (ordersVo.getPayStatus()==0) {
            AlipayVo vo = new AlipayVo();
            vo.setOut_trade_no(UUID.randomUUID().toString().replace("-", ""));
            vo.setTotal_amount(ordersVo.getTotalPrice().toString());
            vo.setSubject(ordersVo.getSubject());
            vo.setProduct_code("FAST_INSTANT_TRADE_PAY"); //这个是固定的
            vo.setTimeout_express("2m");
            String json = new Gson().toJson(vo);
            logger.info("json: {}", json);

            AlipayClient alipayClient = new DefaultAlipayClient(gateway_url, app_id, merchant_private_key, "json", charset, alipay_public_key, sign_type);
            // 设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(return_url);
            alipayRequest.setNotifyUrl(notify_url);
            alipayRequest.setBizContent(json);
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            logger.info("result: {}", result);
            resultMessage.setFlag(true);
            resultMessage.setMsg(result);
        } else {
            resultMessage.setFlag(false);
            resultMessage.setMsg("该订单已经支付过，请勿重新支付");
        }
        return resultMessage;
    }


}
