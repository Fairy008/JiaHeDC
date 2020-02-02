package com.jh.briefing.mapping;

import com.jh.briefing.entity.SoilMoisture;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ISoilMoistureMapper {
    int deleteByPrimaryKey(Integer soidId);

    int insert(SoilMoisture record);

    int insertSelective(SoilMoisture record);

    SoilMoisture selectByPrimaryKey(Integer soidId);

    int updateByPrimaryKeySelective(SoilMoisture record);

    int updateByPrimaryKey(SoilMoisture record);
}