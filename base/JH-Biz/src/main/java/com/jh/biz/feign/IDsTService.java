package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description:地温数据集服务:
 * 1.根据区域，日期构造某一时间段内地温的近三年和10年均值数据查询服务
 * 2.根据区域，日期构造地温环比查询服务
 * @version <1> 2018-08-10 cxw: Created.
 */
@FeignClient(name="jh-ds-ttn")
public interface IDsTService {

    /*
    * 根据区域，日期构造某一时间段内地温的近三年和10年均值数据查询服务
    * @param  数据集查询参数
    *  regionId: 区域ID
    *  startDate: 开始日期
    *  endDate: 结束日期
    *  accuracyId: 精度
    * @return ResultMessage
    * @version <1> 2018-08-10 cxw: Created.
     */
    @GetMapping("/t/queryTForChart")
    public ResultMessage queryTForChart(@RequestParam(name="regionId")Long regionId,
                                        @RequestParam(name="startDate")String startDate,
                                        @RequestParam(name="endDate")String endDate,
                                        @RequestParam(name="accuracyId")Integer accuracyId);

    /*
    * 根据区域，日期构造地温环比查询服务
    * @param  数据集查询参数
    *  regionId: 区域ID
    *  startDate: 开始日期
    *  endDate: 结束日期
    *  accuracyId: 精度
    * @return ResultMessage
    * @version <1> 2018-08-10 cxw: Created.
    */
    @GetMapping("/t/tForMon")
    public ResultMessage queryTForMon(@RequestParam(name="regionId")Long regionId,
                                      @RequestParam(name="startDate")String startDate,
                                      @RequestParam(name="endDate")String endDate,
                                      @RequestParam(name="accuracyId")Integer accuracyId);

    /*
    * 根据区域，日期构造地温报告生成服务
    * @param  数据集查询参数
    *  regionId: 区域ID
    *  startDate: 开始日期
    *  endDate: 结束日期 
    *  accuracyId: 精度
    * @return ResultMessage
    * @version <1> 2018-08-10 cxw: Created.
    */
    @GetMapping("/t/tForReport")
    public ResultMessage queryTForReport(@RequestParam(name="chinaName")String chinaName,
                                         @RequestParam(name="regionId")Long regionId,
                                         @RequestParam(name="startDate")String startDate,
                                         @RequestParam(name="endDate")String endDate,
                                         @RequestParam(name="accuracyId")Integer accuracyId);


    /*
     * 查询指定之间段内地温有数据的日期
     * @param  数据集查询参数
     *  regionId: 区域ID
     *  startDate: 开始日期
     *  endDate: 结束日期
     *  accuracyId: 精度
     * @return ResultMessage
     * @version <1> 2018-11-14 Roach: Created.
     */
    @GetMapping("/t/queryTTimes")
    public ResultMessage queryTTimes(@RequestParam(name="regionId")Long regionId,
                                        @RequestParam(name="startDate")String startDate,
                                        @RequestParam(name="endDate")String endDate,
                                        @RequestParam(name="accuracyId")Integer accuracyId);

    /*
     * 查询指定之间段内地温所有数据
     * @param  数据集查询参数
     *  regionId: 区域ID
     *  startDate: 开始日期
     *  endDate: 结束日期
     * @return ResultMessage
     * @version <1> 2019-03-20 cxw: Created.
     */
    @GetMapping("/t/queryAllT")
    public ResultMessage queryAllT(@RequestParam(name="regionId")Long regionId,
                                     @RequestParam(name="startDate")String startDate,
                                     @RequestParam(name="endDate")String endDate);
}
