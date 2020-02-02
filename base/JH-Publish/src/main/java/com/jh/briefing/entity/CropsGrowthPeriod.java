package com.jh.briefing.entity;

/**
 * 农作物生育周期
 */
public class CropsGrowthPeriod {
    private Integer growthId; //生育期编号（主键）

    private String growthName;//生育期名称

    private Integer cropsId;//作物编号

    private String cropsName;//作物名称

    private Long regionId;//区域编号

    private String rangeStart;//生育周期范围开始

    private String rangeEnd;//生育周期范围结束

    private Short ifspan;//是否跨年

    private Short ifGrowthStart;//是否为播种期

    private Integer indexNo;//序号

    private String remark;//备注

    public Integer getGrowthId() {
        return growthId;
    }

    public void setGrowthId(Integer growthId) {
        this.growthId = growthId;
    }

    public String getGrowthName() {
        return growthName;
    }

    public void setGrowthName(String growthName) {
        this.growthName = growthName == null ? null : growthName.trim();
    }

    public Integer getCropsId() {
        return cropsId;
    }

    public void setCropsId(Integer cropsId) {
        this.cropsId = cropsId;
    }

    public String getCropsName() {
        return cropsName;
    }

    public void setCropsName(String cropsName) {
        this.cropsName = cropsName == null ? null : cropsName.trim();
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(String rangeStart) {
        this.rangeStart = rangeStart == null ? null : rangeStart.trim();
    }

    public String getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(String rangeEnd) {
        this.rangeEnd = rangeEnd == null ? null : rangeEnd.trim();
    }

    public Short getIfspan() {
        return ifspan;
    }

    public void setIfspan(Short ifspan) {
        this.ifspan = ifspan;
    }

    public Short getIfGrowthStart() {
        return ifGrowthStart;
    }

    public void setIfGrowthStart(Short ifGrowthStart) {
        this.ifGrowthStart = ifGrowthStart;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}