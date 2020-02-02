package com.jh.base;

import com.jh.util.AccountTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @version <1> 2018-07-13  lcw : Created.
 */
public class BaseController {

    @Autowired
    public HttpServletRequest request;

    public Map<String,Object> getUserInfo(){
        String token = AccountTokenUtil.getToken(request);
        Map<String,Object> userInfo = AccountTokenUtil.getUserInfoFromRedis(token);

        Map<String,Object> voMap = null;
        if(userInfo != null){
            voMap = new HashMap();
            voMap.put("accountId",  userInfo.get("accountId"));

            voMap.put("accountName", ("null".equals(userInfo.get("nickName").toString())) ? userInfo.get("accountName")+"" : userInfo.get("nickName") +"");
        }


        return voMap;
    }
}
