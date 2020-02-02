package com.jh.briefing.model;

import com.jh.briefing.entity.CropsGrowthPeriod;

import java.util.List;

/**
 * 封装关于生育期相关数据
 * @version <1> 2018-05-22 lxy： created
 */
public class GrowthData {
    Integer growthId;//生育期编号
    CropsGrowthPeriod growthPeriod; //生育期实体类
    List<DiseaseControlParam> diseases; //病虫害集合
    List<GrowthRelativeGroundTempParam> growthConditions;//生长条件

    public CropsGrowthPeriod getGrowthPeriod() {
        return growthPeriod;
    }

    public void setGrowthPeriod(CropsGrowthPeriod growthPeriod) {
        this.growthPeriod = growthPeriod;
    }

    public List<DiseaseControlParam> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<DiseaseControlParam> diseases) {
        this.diseases = diseases;
    }

    public List<GrowthRelativeGroundTempParam> getGrowthConditions() {
        return growthConditions;
    }

    public void setGrowthConditions(List<GrowthRelativeGroundTempParam> growthConditions) {
        this.growthConditions = growthConditions;
    }

    public Integer getGrowthId() {
        return growthId;
    }

    public void setGrowthId(Integer growthId) {
        this.growthId = growthId;
    }
}
