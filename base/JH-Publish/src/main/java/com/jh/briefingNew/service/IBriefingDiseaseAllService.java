package com.jh.briefingNew.service;

import com.jh.briefingNew.entity.BriefingDiseaseAll;

import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-07-24 wl: Created.
 */
public interface IBriefingDiseaseAllService {
    /**
     * 根据物候期编号查询对应的物候期病情和病情防治措施
     * @param growthId 生育期ID
     * @return
     * @version <1> 2018-04-13 lxy： Created.
     */
    List<BriefingDiseaseAll> queryCropsDiseaseByGrowthId(Integer growthId, Integer diseaseType);
}
