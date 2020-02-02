package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="jh-ds-ttn")
public interface IDsWeatherService {
    /*
     * 根据区域查询区域及下一级区域的天气预报数据
     *   regionId: 区域ID
     * @return ResultMessage
     * @version <1> 2018-11-02 Roach: Created.
     */
    @GetMapping("/weather/queryRegionAndBeyond")
    ResultMessage queryRegionAndBeyond(@RequestParam(name = "regionId") Long regionId);

    /*
     *根据区域查询该区域的天气预报数据
     * @param 数据集查询参数
     *   regionId: 区域ID
     *@return ResultMessage
     * @version <1> 2018-11-02 Roach: Created.
     */
    @GetMapping("/weather/queryRegionWeather")
    ResultMessage queryRegionWeather(@RequestParam(name = "regionId") Long regionId);



}
