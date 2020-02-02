package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description:气温数据集服务:
 * 1.根据区域，日期构造某一时间段内气温的近三年数据查询服务
 * 2.根据区域，日期构造气温环比查询服务
 * 3.根据区域，日期构造气温报告生成服务
 * @version <1> 2018-10-16 huxiaoqiang: Created.
 */
@FeignClient(name="jh-ds-ttn")
public interface IDsTempService {
    /*
     * 根据区域，日期构造某一时间段内气温的近三年和10年均值数据查询服务
     * @param 数据集查询参数
     *  regionId: 区域ID
     *  startDate: 开始日期
     *  endDate: 结束日期
     * @return ResultMessage
     * @version <1> 2018-10-16 huxiaoqiang: Created.
     */
    @GetMapping("/temp/queryTempForChart")
    public ResultMessage queryTempForChart(@RequestParam(name = "regionId") Long regionId,
                                           @RequestParam(name = "startDate") String startDate,
                                           @RequestParam(name = "endDate") String endDate);

    /*
    * 根据区域，日期构造气温环比查询服务
    * @param 数据集查询参数
    *  regionId: 区域ID
    *  startDate: 开始日期
    *  endDate: 结束日期
    * @return ResultMessage
    * @version <1> 2018-10-16 huxiaoqiang: Created.
    */
    @GetMapping("/temp/tempForMon")
    public ResultMessage queryTempForMon(@RequestParam(name = "regionId") Long regionId,
                                         @RequestParam(name = "startDate") String startDate,
                                         @RequestParam(name = "endDate") String endDate);

    /*
    * 根据区域，日期构造气温报告生成服务
    * @param 数据集查询参数
    * regionId: 区域ID
    * chinaName:区域中文名
    * startDate: 开始日期
    * endDate: 结束日期
    * @return ResultMessage
    * @version <1> 2018-10-16 huxiaoqiang: Created.
    */
    @GetMapping("/temp/tempForReport")
    public ResultMessage queryTempForReport(@RequestParam(name = "chinaName") String chinaName,
                                            @RequestParam(name = "regionId") Long regionId,
                                            @RequestParam(name = "startDate") String startDate,
                                            @RequestParam(name = "endDate") String endDate);


    /*
     * 查询开始和结束日期时间段内有气温数据的所有日期
     * @param 数据集查询参数
     *  regionId: 区域ID
     *  startDate: 开始日期
     *  endDate: 结束日期
     * @return ResultMessage
     * @version <1> 2018-11-14 Roach: Created.
     */
    @GetMapping("/temp/queryTempTimes")
    public ResultMessage queryTempTimes(@RequestParam(name = "regionId") Long regionId,
                                         @RequestParam(name = "startDate") String startDate,
                                         @RequestParam(name = "endDate") String endDate);

    /*
     * 查询开始和结束日期时间段内所有气温数据
     * @param 数据集查询参数
     *  regionId: 区域ID
     *  startDate: 开始日期
     *  endDate: 结束日期
     * @return ResultMessage
     * @version <1> 2019-03-20 cxw: Created.
     */
    @GetMapping("/temp/queryAllTemp")
    public ResultMessage queryAllTemp(@RequestParam(name = "regionId") Long regionId,
                                        @RequestParam(name = "startDate") String startDate,
                                        @RequestParam(name = "endDate") String endDate);



}
