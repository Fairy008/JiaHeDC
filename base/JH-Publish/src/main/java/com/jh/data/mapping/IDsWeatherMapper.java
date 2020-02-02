package com.jh.data.mapping;

import com.jh.data.entity.DsWeather;
import com.jh.data.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IDsWeatherMapper {

    /**
     * @description: 查询降雨明细
     * @param dsWeatherParam 降雨明细对象
     * @version <1> 2018-06-08 wl： Created.
     */
    List<DsWeather> findByPage(DsWeatherParam dsWeatherParam);

    /**
     * @description: 更新降雨明细
     * @param dsWeatherParam 降雨明细对象
     * @return
     * @version <1> 2018-06-08 wl： Created.
     */
    int updateDsWeather(DsWeatherParam dsWeatherParam);

    /**
     * @description: 根据id查询详细信息
     * @param id 降雨明细主键
     * @return
     * @version<1> 2018-06-08 wl: Created.
     */
    DsWeather findById(Integer id);

    /**
     * @description: 发布降雨明细
     * @param dsWeatherParam 发布人名称  发布人id  发布状态（发布、撤销）
     * @return
     * @version <1> 2018-06-17 wl： Created.
     */
    int publish(DsWeatherParam dsWeatherParam);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ds_trmm
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ds_trmm
     *
     * @mbggenerated
     */
    int insert(DsWeather record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ds_trmm
     *
     * @mbggenerated
     */
    int insertSelective(DsWeather record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ds_trmm
     *
     * @mbggenerated
     */
    DsWeather selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ds_trmm
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(DsWeather record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ds_trmm
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(DsWeather record);

}