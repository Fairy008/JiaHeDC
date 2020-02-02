package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 长势数据集服务:
 * 1. 根据区域、日期段查询区域作物当年与上一年的长势数据
 * 2. 根据区域、日期段查询近三年及近十年的长势数据（同比分页查询）
 * @version <1> 2018-04-27 cxw: Created.
 */
@FeignClient(name = "jh-ds-dgy")
 public interface IGrowthService {

    /*******************************************长势数据接口调用***************************************************************
     ***1.获取指定区域一个时间段内的长势数据时间点
     ***2.获取指定区域各种长势（7种数值:异常偏低，明显偏低，偏低，正常持平，偏高，明显偏高，异常偏高）的面积和占比情况*******************
     ***3.获取指定区域下各区域的作物长势情况(异常偏低，明显偏低，偏低，正常持平，偏高，明显偏高，异常偏高)******************************
     ***4.获取指定区域下各区域的作物长势数据和上年同比数据 ************************************************************************
     ************************************************************************************************************************
     ************************************************************************************************************************/

    /**
     *查询时间段内的长势数据时间点
     * @param regionId 区域
     * @param cropId 作物
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param resolution 精度
     * @version  <1> 2018-10-08 huxiaoqiang : Created.:
     *
     */
    @GetMapping("/growth/queryGrowthTimes")
    ResultMessage queryGrowthTimes(@RequestParam(name = "regionId") Long regionId,
                                   @RequestParam(name = "cropId") Integer cropId,
                                   @RequestParam(name = "startDate") String startDate,
                                   @RequestParam(name = "endDate") String endDate,
                                   @RequestParam(name = "resolution") Integer resolution);



    /**
     * 查询所指区域
     * @param regionId  区域
     * @param cropId    作物
     * @param dataTime 数据日期
     * @param resolution 精度
     * @return
     * @version  <1> 2018-10-08 huxiaoqiang : Created.:
     */
    @GetMapping("/growth/queryGrowthArea")
    ResultMessage queryGrowthArea(@RequestParam(name = "regionId") Long regionId,
                                  @RequestParam(name = "cropId") Integer cropId,
                                  @RequestParam(name = "dataTime") String dataTime,
                                  @RequestParam(name = "resolution") Integer resolution);


    /**
     * 查询所指区域
     * @param regionId  区域
     * @param cropId    作物
     * @param dataTime 数据日期
     * @param resolution 精度
     * @return
     * @version  <1> 2018-10-08 huxiaoqiang : Created.:
     */
    @GetMapping("/growth/queryGrowthInRegion")
    ResultMessage queryGrowthInRegion(@RequestParam(name = "regionId") Long regionId,
                                      @RequestParam(name = "cropId") Integer cropId,
                                      @RequestParam(name = "dataTime") String dataTime ,
                                      @RequestParam(name = "resolution") Integer resolution);

   /**
    * 根据区域、时间查询所有长势数据
    * @version cxw 2019-03-20: Created.
    */
   @GetMapping(value = "/growth/queryAllGrowth")
   public ResultMessage queryAllGrowth(
           @RequestParam(name="regionId")Long regionId,
           @RequestParam(name="startDate")String startDate,
           @RequestParam(name="endDate")String endDate
   );

    /**
     *根据区域、时间，作物，分辨率查询作物长势
     * @param regionId 区域
     * @param cropId 作物
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param resolution 精度
     * @version  <1> 2019-03-21 cxw : Created.:
     */
    @GetMapping("/growth/queryAllGrowthByTime")
    ResultMessage queryAllGrowthByTime(@RequestParam(name = "regionId") Long regionId,
                                   @RequestParam(name = "cropId") Integer cropId,
                                   @RequestParam(name = "startDate") String startDate,
                                   @RequestParam(name = "endDate") String endDate,
                                   @RequestParam(name = "resolution") Integer resolution);
}
