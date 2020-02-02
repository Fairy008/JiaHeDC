package com.jh.forum.base.controller;

import com.jh.forum.base.feign.IUserService;
import com.jh.util.AccountTokenUtil;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 操作接口基类
 * @version<1> 2018-01-17 cxj: Created.
 */
public class BaseController {


    @Autowired
    public HttpServletRequest request;

    @Autowired
    public IUserService userService;


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

    /**
     * 移动端通过用户phone,获取用户信息,通过feign的方式查询
     * @param 
     * @return 
     * @version <1> 2019/4/10 14:51 zhangshen:Created.
     */
    public Map<String,Object> getUserInfoApp(String phone) {
        Map<String,Object> userInfo = new HashMap<String,Object>();
        ResultMessage resultMessage = userService.findUserInfoByPhone(phone);
        userInfo = (Map<String,Object>) resultMessage.getData();
        return userInfo;
    }

    /**
     * 移动端获取当前登录人名称
     * @return
     */
    public String getCurrentNickNameApp(String phone){
        Map<String,Object> userInfo = getUserInfoApp(phone);
        String accountName = null;
        if(userInfo != null && userInfo.size() > 0){
            accountName = userInfo.get("nickName") == null || (userInfo.get("nickName").equals("null")  || userInfo.get("nickName").toString().trim().equals("")) ? userInfo.get("accountName")+"" : userInfo.get("nickName") +"";
        }
        return accountName;
    }

    /**
     * 移动端获取当前登录人ID
     * @return
     */
    public Integer getCurrentAccountIdApp(String phone){
        Map<String,Object> userInfo = getUserInfoApp(phone);
        Integer accountId = null;
        if(userInfo != null && userInfo.size() > 0){
            accountId = (Integer)userInfo.get("accountId");
        }
        return accountId;
    }
}
