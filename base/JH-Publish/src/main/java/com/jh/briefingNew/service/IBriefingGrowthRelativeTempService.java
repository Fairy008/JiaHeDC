package com.jh.briefingNew.service;

import com.jh.briefingNew.entity.BriefingGrowthRelativeTemp;

import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-07-20 wl: Created.
 */
public interface IBriefingGrowthRelativeTempService {

    public List<BriefingGrowthRelativeTemp> queryGrowthRelativeTemp(Integer growthId);
}
