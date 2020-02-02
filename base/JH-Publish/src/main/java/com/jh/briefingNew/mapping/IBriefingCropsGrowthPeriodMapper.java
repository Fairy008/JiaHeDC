package com.jh.briefingNew.mapping;

import com.jh.briefingNew.entity.BriefingCropsGrowthPeriod;
import com.jh.briefingNew.model.BriefingCropsGrowthPeriodParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-07-19 wl: Created.
 */
@Mapper
public interface IBriefingCropsGrowthPeriodMapper {
    /**
     * 根据作物编号、区域编号查询对应的物候期
     * @param briefingCropsGrowthPeriodParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-07-19 wl： Created.
     */
    List<BriefingCropsGrowthPeriod> findGrowthPeriodListByCropsIdAndRegionId(BriefingCropsGrowthPeriodParam briefingCropsGrowthPeriodParam);
}
