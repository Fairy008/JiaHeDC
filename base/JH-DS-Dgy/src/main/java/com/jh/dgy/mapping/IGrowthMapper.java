package com.jh.dgy.mapping;

import com.jh.dgy.entity.GrowthEntity;
import com.jh.dgy.vo.GrowthVo;
import com.jh.vo.ResultMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 *1.根据区域、日期段查询区域及下一级区域在日期段内的同期环比长势数据
 *2.根据区域及指定日期段，查询近三年及近十年的长势均值
 *3.根据年份查询区域及子区域作物当年与上一年的长势数据
 *@version <1> 2018-05-23 cxw: Created.
 */
@Component
@Mapper
public interface IGrowthMapper {

    /**
     * 根据区域、日期段查询区域及下一级区域在日期段内的同期环比长势数据
     * @param param：参数，（区域ID，起止日期）
     *  regionId: 区域ID
     *  cropId: 作物ID
     *  startDate: 开始日期
     *  endDate: 结束日期
     *   resolution: 分辨率
     * @return  List<Growth> : 返回一年同期环比长势数据
     * @version <1> 2018-05-03 cxw: Created.
     */
//    List<GrowthVo> findGrowth(GrowthEntity param);

    /**
     * 根据区域及指定日期段，查询近三年及近十年的长势均值
     * @param param：参数，（区域ID，起止日期）
     *  regionId: 区域ID
     *  cropId: 作物ID
     *  startDate: 开始日期
     *  endDate: 结束日期
     *   resolution: 分辨率
     * @return  List<Growth> : 返回一年同期环比长势数据
     * @version <1> 2018-05-03 cxw: Created.
     */
//    List<GrowthVo> findGrowthByYear(GrowthEntity param);

    /**
     * 查询指定日期范围内所有长势数据的时间点
     * @param param
     * @return
     */
    List<LocalDate> findGrowthTimes(GrowthEntity param);


    /**
     * 查询指定日期范围内所有长势数据的时间点
     * @param param
     * @return
     */
    List<String> findGrowthTimesString(GrowthEntity param);

    /**
     * 查询所有长势
     * @param param 长势对象
     * @return
     * @version <1> 2019-03-20 cxw: Created.
     */
    List<GrowthVo> findAllGrowth(GrowthEntity param);

    /**
     * 查询该区域作物各种长势情况的面积分布和占比数据
     * @param param
     * @return
     */
    List<GrowthVo> queryGrowthArea(GrowthEntity param);

    /**
     * 查询该区域下各级区域的作物长势数据
     * @param param
     * @return
     */
    List<GrowthVo> queryGrowthInRegion(GrowthEntity param);

    /**
     * 查询该区域下各级各级区域的作物长势数据及上年同期作物长势数据
     * @param param
     * @return
     */
//    List<GrowthVo> queryGrowthWithLastYear(GrowthEntity param);

}
