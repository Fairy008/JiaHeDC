package com.jh.show.wx.mapping;

import com.jh.show.wx.model.WXSentReporter;
import com.jh.show.wx.vo.BriefReporterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 提供给微信公众号，简报信息查询
 * @version <1> 2018-05-06 xzg： Created.
 */
@Mapper
public interface IBriefingReporterMapper {

    /**
     * 根据区域 、作物 查询最新简报
     * @param params key(regionId string , cropIdList list)
     * @return
     */
    List<Map<String, Object>> findBriefingByRegionCrop(Map<String, Object> params);

    /**
     * 根据区域、作物、数据开始 结束 时间查询简报
     * @param params key (regionId string , cropIdList list , startDate string , endDate string)
     * @return
     */
    List<Map<String,Object>> findBriefingHistory(Map<String, Object> params);

    /**
     * 根据简报编号获取简报内容
     * @param reporterId  简报编号
     * @return 返回查找到的简报
     * @version <1> 2018-05-06 lxy： Created.
     */
    BriefReporterVO findBriefReporterByReporterId(@Param("reporterId") Long reporterId);

    /**
     * 查找所有注册的微信用户需要推送的最新简报信息
     * @return 返回所有需要推送的最新简报
     */
    List<BriefReporterVO> findScheduleBriefReporters();

    /**
     * 批量保存微信用户已经推送的模板消息
     * @param sentReporters 微信用户已经推送的模板消息
     * @return 返回结果
     * @version <1> 2018-05-16 lxy： Created.
     */
    Integer saveWXSentReporter(@Param("sentReporters") List<WXSentReporter> sentReporters);

    /**
     * 根据多个微信ID ，获取 已发送简报消息实体
     * @param wxIds 多个微信编号
     * @return 返回查找到的已发送的简报消息实体。
     */
    List<WXSentReporter> findSentReporterByWxids(@Param("wxIds") List<String> wxIds);

}