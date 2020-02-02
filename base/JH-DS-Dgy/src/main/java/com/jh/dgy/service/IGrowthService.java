package com.jh.dgy.service;

import com.jh.vo.ResultMessage;

/**
 * 长势数据集服务:
 * 1. 根据年份查询区域及子区域作物当年与上一年的长势数据
 * 2. 根据区域及指定日期段，查询近三年及近十年的长势数据
 * @version <1> 2018-04-27 cxw: Created.
 */
public interface IGrowthService {

    /*
     * 根据区域、日期段查询区域及子区域作物当年与上一年的长势数据
     * @param  :
     *   regionId: 区域ID
     *     cropId: 作物ID
     *  startDate: 结束日期
     *    endDate: 结束日期
     *   resolution: 分辨率
     * @return ResultMessage ：返回区域作物当年与上一年的长势数据
     * @version <1> 2018-04-27 cxw: Created.
     */
//    ResultMessage findGrowthForPrevious(Long regionId, Integer cropId,String startDate, String endDate,Integer resolution);

    /**
     * 根据区域、日期段查询近三年及近十年的长势数据
     * @param  :
     *   regionId: 区域ID
     *     cropId: 作物ID
     *  startDate: 结束日期
     *    endDate: 结束日期
     *   resolution: 分辨率
     * @return : ResultMessage :返回近三年及近十年，区域日期段内的长势数据及均值。
     * @version <1> 2018-04-27 cxw: Created.
     */
//    ResultMessage findGrowthThreeList(Long regionId, Integer cropId,String startDate, String endDate,Integer resolution);

    /**
     * 查询指定日期范围内所有长势数据的时间点
     * @param regionId  区域id
     * @param cropId    作物id
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param resolution    数据精度
     * @return
     */
    ResultMessage findGrowthTimes(Long regionId, Integer cropId, String startDate, String endDate, Integer resolution);

    /**
     * 查询所有长势
     * @param regionId  区域id
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     * @version <1> 2019-03-20 cxw: Created.
     */
    public  ResultMessage findAllGrowth(Long regionId,String startDate,String endDate);

    /**
     * 查询该区域作物各种长势情况的面积分布和占比数据
     * @param regionId  区域id
     * @param cropId    作物id
     * @param dataTime  数据时间
     * @param resolution    数据精度
     * @return
     */
    ResultMessage queryGrowthArea(Long regionId, Integer cropId, String dataTime, Integer resolution);

    /**
     * 查询该区域下各级区域的作物长势数据
     * @param regionId  区域id
     * @param cropId    作物id
     * @param dataTime  数据时间
     * @param resolution    数据精度
     * @return
     */
    ResultMessage queryGrowthInRegion(Long regionId, Integer cropId, String dataTime , Integer resolution);

    /**
     * 查询该区域下各级各级区域的作物长势数据及上年同期作物长势数据
     * @param regionId  区域id
     * @param cropId    作物id
     * @param dataTime  数据时间
     * @param resolution    数据精度
     * @return
     */
//    ResultMessage queryGrowthWithLastYear(Long regionId, Integer cropId, String dataTime, Integer resolution);

    /*
    * 根据区域、时间，作物，分辨率查询作物长势
    * @param :
    *   regionId: 区域ID
    *     cropId: 作物ID
    *  startDate: 开始日期
    *    endDate: 结束日期
    *   resolution: 分辨率
    * @return ResultMessage
    * @version <1> 2019-03-21 cxw: Created.
    */
    public ResultMessage queryAllGrowthByTime(Long regionId,Integer cropId,String startDate,String endDate,Integer resolution);


}
