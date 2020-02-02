package com.jh.manage.storage.entity;

import com.jh.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Description:
 * 1.矢量数据入库对象实体
 *
 * @version <1> 2018-04-25 9:39 zhangshen: Created.
 */
@ApiModel(value = "矢量数据入库对象")
public class DataStatic extends BaseEntity {

    @ApiModelProperty(value = "矢量数据入库主键ID")
    private Integer staticId;

    @ApiModelProperty(value = "区域ID")
    private Long regionId;

    @ApiModelProperty(value = "关键字")
    private String words;

    @ApiModelProperty(value = "文件地址")
    private String filePath;

    @ApiModelProperty(value = "文件大小")
    private String fileSize;

    private String regionName;//区域名称

    public Integer getStaticId() {
        return staticId;
    }

    public void setStaticId(Integer staticId) {
        this.staticId = staticId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
