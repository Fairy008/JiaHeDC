package com.jh.briefing.mapping;

import com.jh.briefing.entity.Humidity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IHumidityMapper {
    int insert(Humidity record);

    int insertSelective(Humidity record);
}