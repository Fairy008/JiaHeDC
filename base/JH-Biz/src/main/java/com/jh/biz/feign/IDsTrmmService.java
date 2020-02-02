package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description:降雨数据集服务:
 * 1.根据区域，日期构造某一时间段内降雨的近三年和10年均值数据查询服务
 * 2.根据区域，日期构造降雨环比查询服务
 * @version <1> 2018-08-10 cxw: Created.
 */
@FeignClient(name="jh-ds-ttn")
public interface IDsTrmmService {
    /*
     * 根据区域，日期构造某一时间段内降雨的近三年和10年均值数据查询服务
     * @param 数据集查询参数
     *  regionId: 区域ID
     *  startDate: 开始日期
     *  endDate: 结束日期
     *  accuracyId: 精度
     * @return ResultMessage
     * @version <1> 2018-08-10 cxw: Created.
     */
    @GetMapping("/trmm/queryTrmmForChart")
    public ResultMessage queryTrmmForChart(@RequestParam(name="regionId")Long regionId,
                                           @RequestParam(name="startDate")String startDate,
                                           @RequestParam(name="endDate")String endDate,
                                           @RequestParam(name="accuracyId")Integer accuracyId);

    /*
    * 根据区域，日期构造降雨环比查询服务
    * @param 数据集查询参数
    *  regionId: 区域ID
    *  startDate: 开始日期
    *  endDate: 结束日期
    *  accuracyId: 精度
    * @return ResultMessage
    * @version <1> 2018-08-10 cxw: Created.
    */
    @GetMapping("/trmm/trmmForMon")
    public ResultMessage queryTrmmForMon(@RequestParam(name="regionId")Long regionId,
                                         @RequestParam(name="startDate")String startDate,
                                         @RequestParam(name="endDate")String endDate,
                                         @RequestParam(name="accuracyId")Integer accuracyId);

    /*
    * 根据区域，日期构造降雨报告生成服务
    * @param 数据集查询参数
    * regionId: 区域ID
    * chinaName:区域中文名
    * startDate: 开始日期
    * endDate: 结束日期
    * accuracyId: 精度
    * @return ResultMessage
    * @version <1> 2018-08-10 cxw: Created.
    */
    @GetMapping("/trmm/trmmForReport")
    public ResultMessage queryTrmmForReport(@RequestParam(name="chinaName")String chinaName,
                                            @RequestParam(name="regionId")Long regionId,
                                            @RequestParam(name="startDate")String startDate,
                                            @RequestParam(name="endDate")String endDate,
                                            @RequestParam(name="accuracyId")Integer accuracyId);


    /*
     * 查询开始和结束日期时间段内有降雨数据的所有日期
     * @param 数据集查询参数
     *  regionId: 区域ID
     *  startDate: 开始日期
     *  endDate: 结束日期
     *  accuracyId: 精度
     * @return ResultMessage
     * @version <1> 2018-11-14 Roach: Created.
     */
    @GetMapping("/trmm/queryTrmmTimes")
    public ResultMessage queryTrmmTimes(@RequestParam(name="regionId")Long regionId,
                                         @RequestParam(name="startDate")String startDate,
                                         @RequestParam(name="endDate")String endDate,
                                         @RequestParam(name="accuracyId")Integer accuracyId);

    /*
     * 查询开始和结束日期时间段内所有降雨数据
     * @param 数据集查询参数
     *  regionId: 区域ID
     *  startDate: 开始日期
     *  endDate: 结束日期
     * @return ResultMessage
     * @version <1> 2019-03-20 cxw: Created.
     */
    @GetMapping("/trmm/queryAllTrmm")
    public ResultMessage queryAllTrmm(@RequestParam(name="regionId")Long regionId,
                                        @RequestParam(name="startDate")String startDate,
                                        @RequestParam(name="endDate")String endDate);

}
