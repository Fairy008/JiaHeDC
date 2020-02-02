package com.jh.briefing.model;


import com.jh.entity.PageEntity;

/**
 * 作物生育期生长速度与地温关系
 */
public class GrowthRelativeTempManagerParam extends PageEntity {
    private Integer relativeId; //主键

    private Long regionId;//区域编号

    private String regionName;//区域名称

    private Integer cropsId;//作物编号

    private String cropsName;//作物名称

    private Integer growthId;//生育期编号

    private String growthName; //生育期名称

    private Integer tempRangeStart; //地温范围开始

    private Integer tempRangeEnd; //地温范围结束

    private String instruction; //说明

    private Short ifavg; //是否平均温度 (0:否，1：是）

    private Short ifOr; //是否 or 的条件 (0:否，1：是）

    private Short rangeLimit; //上线范围

    public Integer getRelativeId() {
        return relativeId;
    }

    public void setRelativeId(Integer relativeId) {
        this.relativeId = relativeId;
    }

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

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction == null ? null : instruction.trim();
    }

    public Short getIfavg() {
        return ifavg;
    }

    public void setIfavg(Short ifavg) {
        this.ifavg = ifavg;
    }

    public Short getIfOr() {
        return ifOr;
    }

    public void setIfOr(Short ifOr) {
        this.ifOr = ifOr;
    }

    public Short getRangeLimit() {
        return rangeLimit;
    }

    public void setRangeLimit(Short rangeLimit) {
        this.rangeLimit = rangeLimit;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
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
        this.cropsName = cropsName;
    }

    public Integer getTempRangeStart() {
        return tempRangeStart;
    }

    public void setTempRangeStart(Integer tempRangeStart) {
        this.tempRangeStart = tempRangeStart;
    }

    public Integer getTempRangeEnd() {
        return tempRangeEnd;
    }

    public void setTempRangeEnd(Integer tempRangeEnd) {
        this.tempRangeEnd = tempRangeEnd;
    }
}