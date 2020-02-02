package com.jh.briefing.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.data.enums.YNEnum;
import com.jh.entity.BaseEntity;
import com.jh.entity.PageEntity;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 作物生育期查询参数类
 * Created by lxy on 2018/4/11.
 */
public class CropsGrowthManagerParam extends PageEntity {
    private Integer id; //生育期编号（主键）

    private String growthName;//生育期名称

    private Integer cropsId;//作物编号

    private String cropsName;//作物名称

    private Integer creator;//创建人


    private String modifier; //修改人

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;//修改时间

    private String remark;//备注

    private Integer ifGrowthStart;//是否为播种期（物候期的第一个期）

    private String ifGrowthStartName;//是否为播种期中文

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间

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

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
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
        this.remark = remark;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getIfGrowthStart() {
        return ifGrowthStart;
    }

    public void setIfGrowthStart(Integer ifGrowthStart) {
        this.ifGrowthStart = ifGrowthStart;
    }

    public String getIfGrowthStartName() {
        String name=getIfGrowthStart()!=null?getIfGrowthStart().toString():null;
        return YNEnum.getChineseName(name);
    }

    public void setIfGrowthStartName(String ifGrowthStartName) {
        this.ifGrowthStartName = ifGrowthStartName;
    }
}
