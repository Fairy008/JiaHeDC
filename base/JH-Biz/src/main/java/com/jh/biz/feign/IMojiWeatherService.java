package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "jh-ds-ttn")
public interface IMojiWeatherService {

    /**
     * @Description: 墨迹天气24小时天气预报
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id
     * @return: 24小时天气预报
     * @throws:
     */
    @GetMapping("/nolog/mojiWeather/forecast24hours")
    public ResultMessage forecast24hours(@RequestParam(name = "cityId") String cityId);

    /**
     * @Description: 墨迹天气AQI预报5天
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id
     * @return: AQI预报5天
     * @throws:
     */
    @GetMapping("/nolog/mojiWeather/aqiforecast5days")
    public ResultMessage aqiforecast5days(@RequestParam(name="cityId") String cityId);

    /**
     * @Description: 墨迹天气15天天气预报
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id
     * @return: 15天天气预报
     * @throws:
     */
    @GetMapping("/nolog/mojiWeather/forecast15days")
    public ResultMessage forecast15days(@RequestParam(name = "cityId") String cityId);

    /**
     * @Description: 墨迹天气天气实况
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id
     * @return: 天气实况
     * @throws:
     */
    @GetMapping("/nolog/mojiWeather/condition")
    public ResultMessage condition(@RequestParam(name = "cityId") String cityId);

    /**
     * @Description: 墨迹天气生活指数
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id
     * @return: 生活指数
     * @throws:
     */
    @GetMapping("/nolog/mojiWeather/index")
    public ResultMessage index(@RequestParam(name= "cityId") String cityId);

    /**
     * @Description: 墨迹天气天气预警
     * @Author: huxiaoqiang
     * @Date: 2018/9/13 14:04
     * @param: cityId 城市id
     * @return: 天气预警
     * @throws:
     */
    @GetMapping("/nolog/mojiWeather/alert")
    public ResultMessage alert(@RequestParam(name="cityId")String cityId);
}
