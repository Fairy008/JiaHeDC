package com.jh.briefingNew.mapping;

import com.jh.briefingNew.entity.BriefingGrowthRelativeTemp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-07-20 wl: Created.
 */
@Mapper
public interface IBriefingGrowthRelativeTempMapper {
    /**
     * 根据growthId（物候期编号）查询对应生长与地温关系信息 列表
     * @param growthId
     * @return List<GrowthRelativeGroundTempParam>
     */
    List<BriefingGrowthRelativeTemp> queryGrowthRelativeTemp(@Param("growthId") Integer growthId);
}
