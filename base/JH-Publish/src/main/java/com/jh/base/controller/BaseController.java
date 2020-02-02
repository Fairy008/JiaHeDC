package com.jh.base.controller;

import com.jh.util.AccountTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @description: 操作接口基类
 * @version<1> 2018-01-17 cxj: Created.
 */
public class BaseController {


    @Autowired
    public HttpServletRequest request;


    /**
     * 获取用户当前token
     * @return
     */
    public String getToken(){
        return AccountTokenUtil.getToken(request);
    }

    public Map<String,Object> getUserInfo(){
        String token = AccountTokenUtil.getToken(request);
        Map<String,Object> userInfo = AccountTokenUtil.getUserInfoFromRedis(token);

//        Map<String,Object> voMap = null;
//        if(userInfo != null){
//            voMap = new HashMap();
//            voMap.put("accountId",  userInfo.get("accountId"));
//            voMap.put("accountName",  userInfo.get("nickName") == null ? userInfo.get("accountName") : userInfo.get("nickName"));
//        }

        return userInfo;
    }

    /**
     * 获取当前登录人ID
     * @return
     */
    public Integer getCurrentAccountId(){
        Map<String,Object> userInfo = getUserInfo();
        Integer accountId = null;
        if(userInfo != null && userInfo.size() > 0){
            accountId = (Integer)userInfo.get("accountId");
        }
        return accountId;
    }


    /**
     * 获取当前登录人名称
     * @return
     */
    public String getCurrentNickName(){
        Map<String,Object> userInfo = getUserInfo();
        String accountName = null;
        if(userInfo != null && userInfo.size() > 0){
            accountName = (userInfo.get("nickName").equals("null")  || userInfo.get("nickName") == null || userInfo.get("nickName").toString().trim().equals("")) ? userInfo.get("accountName")+"" : userInfo.get("nickName") +"";
        }
        return accountName;
    }

    /**
     * 获取当前登录人手机号
     * @return
     */
    public String getCurrentAccount(){

        Map<String,Object> userInfo = getUserInfo();
        String accountName = null;
        if(userInfo != null && userInfo.size() > 0){
            accountName = userInfo.get("accountName") +"";
        }
        return accountName;
    }


}
