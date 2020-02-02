package com.jh.data.service;

import com.jh.data.entity.DsWeather;
import com.github.pagehelper.PageInfo;
import com.jh.data.model.DsWeatherParam;
import com.jh.vo.ResultMessage;
import io.swagger.models.auth.In;

/**
 * 天气数据接口
 */
public interface IDsWeatherService {

    /**
     * 查询天气数据
     * @param dsWeatherParam 查询天气相关的参数
     * @return
     */
    PageInfo<DsWeather> findByPage(DsWeatherParam dsWeatherParam);

    /**
     * 根据id查询详细天气信息
     * @param id
     * @return
     */
    ResultMessage findById(Integer id);

    /**
     * 更新天气明细
     * @param dsWeatherParam
     * @return
     */
    ResultMessage updateDsWeather(DsWeatherParam dsWeatherParam);

    /**
     * 发布天气数据
     * @param dsWeatherParam
     * @return
     */
    ResultMessage publish(DsWeatherParam dsWeatherParam);

}
