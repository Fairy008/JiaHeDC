package com.jh.data.entity;

import com.jh.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Description:
 * 1.成果数据
 *
 * @version <1> 2018-05-02 15:54 zhangshen: Created.
 */
@ApiModel(value = "成果数据对象")
public class DataResult extends BaseEntity {

    @ApiModelProperty(value = "成果数据主键ID")
    private Integer resultdataId;

    @ApiModelProperty(value = "区域ID")
    private Long regionId;

    @ApiModelProperty(value = "数据分类ID（卫星Id）")
    private Integer satId;

    @ApiModelProperty(value = "农作物ID")
    private Integer cropId;

    @ApiModelProperty(value = "分辨率")
    private Integer resolution;

    @ApiModelProperty(value = "数据日期")
    private Date dataTime;

    @ApiModelProperty(value = "文件路径")
    private String filePath;

    @ApiModelProperty(value = "图层路径")
    private String tiffPath;

    public Integer getResultdataId() {
        return resultdataId;
    }

    public void setResultdataId(Integer resultdataId) {
        this.resultdataId = resultdataId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Integer getSatId() {
        return satId;
    }

    public void setSatId(Integer satId) {
        this.satId = satId;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTiffPath() {
        return tiffPath;
    }

    public void setTiffPath(String tiffPath) {
        this.tiffPath = tiffPath;
    }
}
