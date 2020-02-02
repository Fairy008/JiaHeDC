package com.jh.briefingNew.service.impl;

import com.jh.briefingNew.entity.BriefingGrowthRelativeTemp;
import com.jh.briefingNew.mapping.IBriefingGrowthRelativeTempMapper;
import com.jh.briefingNew.service.IBriefingGrowthRelativeTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-07-20 wl: Created.
 */
@Service
public class BriefingGrowthRelativeTempServiceImpl implements IBriefingGrowthRelativeTempService {
    @Autowired
    private IBriefingGrowthRelativeTempMapper briefingGrowthRelativeTempMapper;

    /**
     * 根据growthId（物候期编号）查询对应生长与地温关系信息列表
     * @param growthId
     * @return List<BriefingGrowthRelativeTemp>
     */
    @Override
    public List<BriefingGrowthRelativeTemp> queryGrowthRelativeTemp(Integer growthId) {
        return briefingGrowthRelativeTempMapper.queryGrowthRelativeTemp(growthId);
    }
}
