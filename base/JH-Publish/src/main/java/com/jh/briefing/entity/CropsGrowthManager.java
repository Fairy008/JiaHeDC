package com.jh.briefing.entity;

import java.time.LocalDateTime;
import java.util.Date;

public class CropsGrowthManager {

    private Integer id;

    private String growthName;

    private Integer cropsId;

    private String cropsName;

    private Integer creator;

    private LocalDateTime createTime;

    private Integer modifier;

    private LocalDateTime modifyTime;

    private String remark;

    private Integer ifGrowthStart;

    public Integer getIfGrowthStart() {
        return ifGrowthStart;
    }

    public void setIfGrowthStart(Integer ifGrowthStart) {
        this.ifGrowthStart = ifGrowthStart;
    }


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}