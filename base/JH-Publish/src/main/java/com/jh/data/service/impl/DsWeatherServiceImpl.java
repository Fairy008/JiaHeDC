package com.jh.data.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.data.entity.DsWeather;
import com.jh.data.mapping.IDsWeatherMapper;
import com.jh.data.model.DsWeatherParam;
import com.jh.data.service.IDsWeatherService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 天气数据管理接口
 */
@Service
@Transactional
public class DsWeatherServiceImpl implements IDsWeatherService {

    @Autowired
    private IDsWeatherMapper dsWeatherMapper;



    public PageInfo<DsWeather> findByPage(DsWeatherParam dsWeatherParam){
        PageHelper.startPage(dsWeatherParam.getPage(),dsWeatherParam.getRows());
        List<DsWeather>  list = dsWeatherMapper.findByPage(dsWeatherParam);
        return new PageInfo<DsWeather>(list);
    }

    /**
     * 根据id查询详细天气信息
     * @param id
     * @return
     */
    public ResultMessage findById(Integer id){
        DsWeather dsWeather = dsWeatherMapper.findById(id);
        return ResultMessage.success(dsWeather);
    }

    /**
     * 更新天气数据
     * @param dsWeatherParam
     * @return
     */
    public ResultMessage updateDsWeather(DsWeatherParam dsWeatherParam){
        dsWeatherMapper.updateDsWeather(dsWeatherParam);
        return ResultMessage.success();
    }

    /**
     * 发布天气数据
     * @param dsWeatherParam
     * @return
     */
    public ResultMessage publish(DsWeatherParam dsWeatherParam){
        int num = dsWeatherMapper.publish(dsWeatherParam);
        if (num>0){
            return ResultMessage.success();
        }else{
            return ResultMessage.fail();
        }



    }


}
