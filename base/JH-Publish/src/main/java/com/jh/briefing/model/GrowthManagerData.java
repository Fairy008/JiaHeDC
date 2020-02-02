package com.jh.briefing.model;

import com.jh.briefing.entity.CropsGrowthManager;

import java.util.List;

/**
 * 封装关于生育期配置管理相关数据
 * @version <1> 2018-05-22 lxy： created
 */
public class GrowthManagerData {
    Integer growthId;//生育期编号
    CropsGrowthManager growthManager; //生育期实体类
    List<DiseaseControlManagerParam> diseases; //病虫害集合
    List<GrowthRelativeTempManagerParam> growthConditions;//生长条件

    public CropsGrowthManager getGrowthManager() {
        return growthManager;
    }

    public void setGrowthManager(CropsGrowthManager growthManager) {
        this.growthManager = growthManager;
    }

    public List<DiseaseControlManagerParam> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<DiseaseControlManagerParam> diseases) {
        this.diseases = diseases;
    }

    public List<GrowthRelativeTempManagerParam> getGrowthConditions() {
        return growthConditions;
    }

    public void setGrowthConditions(List<GrowthRelativeTempManagerParam> growthConditions) {
        this.growthConditions = growthConditions;
    }

    public Integer getGrowthId() {
        return growthId;
    }

    public void setGrowthId(Integer growthId) {
        this.growthId = growthId;
    }
}
