package com.jh.briefingNew.service;

import com.jh.base.service.IBaseService;
import com.jh.briefingNew.entity.BriefingCropsGrowthPeriod;
import com.jh.briefingNew.model.BriefingCropsGrowthPeriodParam;

import java.util.List;

/**
 *  作物生育周期服务接口类
 * Created by lxy on 2018/4/11.
 */
public interface IBriefingCropsGrowthPeriodService extends IBaseService<BriefingCropsGrowthPeriodParam,BriefingCropsGrowthPeriod,Integer> {


    /**
     * 根据作物编号、区域编号查询对应的物候期
     * @param briefingCropsGrowthPeriodParam 农作物生育周期配置参数对象
     * @return
     * @version <1> 2018-07-19 wl： Created.
     */
    List<BriefingCropsGrowthPeriod> findGrowthPeriodListByCropsIdAndRegionId(BriefingCropsGrowthPeriodParam briefingCropsGrowthPeriodParam);



}
