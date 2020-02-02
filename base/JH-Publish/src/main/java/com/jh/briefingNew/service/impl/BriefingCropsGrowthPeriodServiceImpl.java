package com.jh.briefingNew.service.impl;

import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.briefingNew.entity.BriefingCropsGrowthPeriod;
import com.jh.briefingNew.mapping.IBriefingCropsGrowthPeriodMapper;
import com.jh.briefingNew.model.BriefingCropsGrowthPeriodParam;
import com.jh.briefingNew.service.IBriefingCropsGrowthPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-07-19 wl: Created.
 */
@Service
public class BriefingCropsGrowthPeriodServiceImpl extends BaseServiceImpl<BriefingCropsGrowthPeriodParam,BriefingCropsGrowthPeriod,Integer> implements IBriefingCropsGrowthPeriodService {

    @Autowired
    private IBriefingCropsGrowthPeriodMapper briefingCropsGrowthPeriodMapper;

    /**
     * 根据作物编号、区域编号查询对应的物候期
     * @param briefingCropsGrowthPeriodParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-07-19 wl： Created.
     */
    @Override
    public List<BriefingCropsGrowthPeriod> findGrowthPeriodListByCropsIdAndRegionId(BriefingCropsGrowthPeriodParam briefingCropsGrowthPeriodParam) {
        return briefingCropsGrowthPeriodMapper.findGrowthPeriodListByCropsIdAndRegionId(briefingCropsGrowthPeriodParam);
    }


    @Override
    protected IBaseMapper<BriefingCropsGrowthPeriodParam, BriefingCropsGrowthPeriod, Integer> getDao() {
        return null;
    }
}
