package com.jh.show.agric;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloopen.rest.sdk.utils.encoder.BASE64Encoder;
import com.jh.show.agric.service.IAreaService;
import com.jh.show.agric.service.ILoginService;
import com.jh.show.agric.vo.LoginParam;
import com.jh.util.Base64Util;
import com.jh.vo.ResultMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作物生育期配置管理测试类
 * Created by lj on 2018/8/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ILoginServiceTest {

    @Autowired
    private ILoginService loginService;
    @Autowired
    private IAreaService areaService;


    @Test
    public void login() throws Exception {
        LoginParam param=new LoginParam();
        param.setAccountNo("");
        BASE64Encoder e=new BASE64Encoder();
        String pwd=e.encode("123456".getBytes());
        param.setPwd(pwd);
        param.setMac("11");
        ResultMessage result=loginService.loginForApp(param);
        System.out.println(result);
    }

    @Test
    public void findTimeAxisForWx() throws Exception {
        ResultMessage result=areaService.findTimeAxisForWx(null,null);
        List<Map<String,Object>> list=(List<Map<String,Object>>)result.getData();
        String str=JSONObject.toJSONString(list);
        System.out.println(str);
    }

}