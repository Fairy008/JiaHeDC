package com.jh.show.wx.mapping;

import com.jh.show.wx.model.WXRelateAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by XZG on 2018/4/27.
 */
@Mapper
public interface WXRelateAccountMapper {


    /**
     * 添加微信用户关联信息
     * @param relateAccount
     * @return
     */
    Integer insertRelateAccount(WXRelateAccount relateAccount);

    /**
     * 根据微信ID 查询关联的手机号
     * @param wxId
     * @return
     */
    WXRelateAccount findWxUserByWxid(String wxId);

    /**
     * 获取所有微信号已经注册到我们平台的用户
     * @return 返回简报
     */
    List<WXRelateAccount> findAllWxUsers();

    /**
     * 根据微信用户id删除单个微信用户的账户信息
     * @param wxId 微信用户id
     * @return 删除成功的条目数
     */
    Integer deleteWxUserById(String wxId);

}
