package com.jh.briefingNew.entity;

/**
 * description:
 *
 * @version <1> 2018-07-20 wl: Created.
 */
public class BriefingGrowthRelativeTemp {

    private Integer relativeId; //主键

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
        this.growthName = growthName;
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

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
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
}
