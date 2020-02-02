package com.jh.base.controller;

import com.jh.manage.order.Enum.PersonTypeEnum;
import com.jh.util.AccountTokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 操作接口基类
 * @version<1> 2018-01-17 cxj: Created.
 */
public class BaseController {


    @Autowired
    public HttpServletRequest request;


    public Map<String,Object> getUserInfo(){
        String token = AccountTokenUtil.getToken(request);
        Map<String,Object> userInfo = AccountTokenUtil.getUserInfoFromRedis(token);
        System.out.println(userInfo.toString());

//        Map<String,Object> voMap = null;
//        if(userInfo != null){
//            voMap = new HashMap();
//            voMap.put("accountId",  userInfo.get("accountId"));
//            voMap.put("accountName",  userInfo.get("nickName") == null ? userInfo.get("accountName") : userInfo.get("nickName"));
//        }

        return userInfo;
    }


    /**
     * 获取默认角色信息
     * @return
     */
    public List<Map<String,Object>> getRoleList(){
        String token = AccountTokenUtil.getToken(request);
        List<Map<String,Object>> roleList = AccountTokenUtil.getUserRolesFromRedis(token);
//        if(roleList != null && roleList.size() > 0){
//            for(int i = 0 ; i < roleList.size() ; i++){
//                Map<String,Object> roleInfo = roleList.get(i);
//                if(defaultRoleId.equals(roleInfo.get("roleId"))){
//                    return roleInfo;
//                }
//            }
//        }

        return roleList;
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
            accountName = ("null".equals(userInfo.get("nickName").toString())|| StringUtils.isBlank(userInfo.get("nickName").toString())) ? userInfo.get("accountName")+"" : userInfo.get("nickName") +"";
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
     *
     * 获取当前用户类型
     * @return
     */
    public Integer getCurrentPersonType(){
        Integer personType = PersonTypeEnum.PERSON_TYPE_INNER.getId();
        Map<String,Object> userInfo = getUserInfo();
        if(userInfo != null && userInfo.size() > 0){
            personType = (Integer) userInfo.get("personType");
        }
        return personType;
    }
}
