package com.jh.show.report.mapping;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * description:用户登录及信息查询
 *
 * @version <1> 2018-06-25 wl: Created.
 */
@Mapper
public interface IWechatLoginMapper {

    /**
     * @description: 用户注册
     * @param openId  微信id
     * @return
     * @version <1> 2018-06-25 wl:Created.
     */
    void saveUser(Map<String, Object> map);

    /**
     * 查询用户信息
     * @param  map
     * @return  Map<String,Object>
     * @version <1> 2018-06-25 wl:Created.
     */
    Map<String,Object> findUser(Map<String, Object> map);

    /**
     * 查询区域上级信息
     * @param  regionId
     * @return  Map<String,Object>
     * @version <1> 2018-06-26 wl:Created.
     */
    Map<String,Object> findParentRegion(Long regionId);

    /**
     * 查询账号是否存在
     * @param  map
     * @return  Map<String,Object>
     * @version <1> 2018-06-29 wl:Created.
     */
    Map<String,Object> checkUser(Map<String, Object> map);

    /**
     * 查询用户信息
     * @param  map
     * @return  Map<String,Object>
     * @version <1> 2018-06-29 wl:Created.
     */
    Map<String,Object> checkRegion(Map<String, Object> map);

    /**
     * @description: 用户注册
     * @param map accountName  accountPwd
     * @return
     * @version <1> 2018-06-25 wl:Created.
     */
    Integer saveAccount(Map<String, Object> map);

    /**
     * @description: 用户注册
     * @param map  mobile  accountId  personType
     * @return
     * @version <1> 2018-06-25 wl:Created.
     */
    Integer savePerson(Map<String, Object> map);

    /**
     * @description: 保存用户设定的区域信息
     * @param map  region_id   user_id
     * @return
     * @version <1> 2018-06-25 wl:Created.
     */
    Integer setRegion(Map<String, Object> map);

    /**
     * 更新用户信息
     * @param param  key [personId  , wetchatId]
     * @return
     */
    Integer updatePerson(Map<String, Object> param);

    /**
     * 使用用户账号查询用户关注的区域
     * @param voMap
     * @return
     */
    Map<String,Object> queryRegionWithAccount(Map<String, Object> voMap);
}
