package com.jh.show.report.service;


import com.jh.vo.ResultMessage;

import java.util.Map;

/**
 * description:
 *
 * @version <1> 2018-06-25 wl: Created.
 */
public interface IWechatLoginService {

    /**
     * @description: 用户注册
     * @param userMap
     * @return
     * @version <1> 2018-06-25 wl:Created.
     */
    ResultMessage register(Map<String,String> userMap);

    /**
     * @description: 保存用户设定的区域信息
     * @param map  region_id   user_id
     * @return
     * @version <1> 2018-06-25 wl:Created.
     */
    ResultMessage setRegion(Map<String, Object> map);

    /**
     * @description: 修改密码
     * @param map
     * @return
     * @version <1> 2018-08-30 wl:Created.
     */
    ResultMessage resetAccountPwd(Map<String, Object> map);


    /**
     * @description: 绑定微信号
     * @param map
     * @return
     * @version <1> 2018-08-30 wl:Created.
     */
    ResultMessage relateWechat(Map<String, String> map);


    /**
     * @description: 微信登录
     * @param code
     * @return
     * @version <1> 2018-08-30 wl:Created.
     */
    ResultMessage wechatLogin(String code ,String state);



    /**
     * 查询用户信息
     * @param  map  包含 id  openId  tel
     * @return ResultMessage :
     * @version <1> 2018-06-25 wl:Created.
     */
    public Map<String,Object>  findUser(Map<String, Object> map);



    /**
     * 查询用户信息
     * @param  map
     * @return  Map<String,Object>
     * @version <1> 2018-06-29 wl:Created.
     */
    public Map<String,Object> checkRegion(Map<String, Object> map);

    /**
     * 产品用户登录
     * @param tel
     * @param passWord
     * @return
     * @version<1>2018-08-30 lcw : Created.
     */
    ResultMessage loginForProduct(String tel, String passWord);


    /**
     * 根据用户账号查询关联的区域信息
     * @param accountNo
     * @return
     */
    ResultMessage queryRegionByAccountNo(String accountNo);


    ResultMessage sendSmsForRegister(String tel,String imgCode,String validToken);



}
