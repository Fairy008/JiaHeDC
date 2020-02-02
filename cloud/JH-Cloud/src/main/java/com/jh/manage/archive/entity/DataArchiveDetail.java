package com.jh.manage.archive.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "数据归档明细对象")
public class DataArchiveDetail {

    @ApiModelProperty(value = "归档明细主键")
    private Integer archiveDetailId;

    @ApiModelProperty(value = "归档任务ID")
    private Integer archiveId;

    @ApiModelProperty(value = "数据文件名")
    private String dataName;


    @ApiModelProperty(value = "数据文件大小")
    private String dataSize;

    @ApiModelProperty(value = "源数据id")
    private Integer storageId;

    public Integer getArchiveDetailId() {
        return archiveDetailId;
    }

    public void setArchiveDetailId(Integer archiveDetailId) {
        this.archiveDetailId = archiveDetailId;
    }

    public Integer getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(Integer archiveId) {
        this.archiveId = archiveId;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName == null ? null : dataName.trim();
    }

    public String getDataSize() {
        return dataSize;
    }

    public void setDataSize(String dataSize) {
        this.dataSize = dataSize == null ? null : dataSize.trim();
    }

    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }
}