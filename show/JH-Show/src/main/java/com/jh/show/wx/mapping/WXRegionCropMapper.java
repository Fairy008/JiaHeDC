package com.jh.show.wx.mapping;

import com.jh.show.wx.model.WXRegionCrop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by XZG on 2018/4/27.
 */
@Mapper
public interface WXRegionCropMapper {

    /**
     * 添加简报订阅的区域、作物
     * @param regionCrop
     * @return
     */
    Integer insertRegionCrop(WXRegionCrop regionCrop);

    /**
     * 根据手机号查询订阅的简报区域、作物
     * @param tel
     * @return
     */
    List<WXRegionCrop> findByTel(String tel);

    /**
     * 根据微信号查询订阅的简报区域、作物
     * @param wechatID
     * @return
     */
    List<WXRegionCrop> findByWXid(String wechatID);

    /**
     * 根据电话号码删除原先订阅的简报信息
     * @param tel 电话号码
     * @return 删除的结果
     */
    Integer deleteByTel(String tel);

}