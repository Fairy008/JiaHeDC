package com.jh.show.wx.mapping;


import com.jh.show.wx.model.WXInformation;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by XZG on 2018/4/27.
 */
@Mapper
public interface WXInformationMapper {

    /**
     * 添加微信用户定制的关键字
     * @param informatiion
     * @return
     */
    Integer insertWXAccount(WXInformation informatiion);

    /**
     * 根据微信ID 查询关键字
     * @param wxID
     * @return
     */
    WXInformation findKeyWordByAccount(String wxID);

    /**
     * 根据微信ID 更新记录
     * @param informatiion
     */
    void updateInformation(WXInformation informatiion);

    /**
     * 根据微信号查询添加的关键字
     * @param wxID
     * @return
     */
    WXInformation findKeyWordByWxid(String wxID);

    /**
     * 根据微信id删除该用户下的简报关键字信息
     * @param wxID
     * @return
     */
    Integer delKeyWordByWxId(String wxID);

}
