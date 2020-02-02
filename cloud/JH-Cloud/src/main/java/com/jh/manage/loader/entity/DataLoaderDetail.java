package com.jh.manage.loader.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "入库明细对象")
public class DataLoaderDetail {

    @ApiModelProperty(value = "入库明细主键")
    private Integer loaderDetailId;

    @ApiModelProperty(value = "入库ID")
    private Integer loaderId;

    @ApiModelProperty(value = "数据文件名称")
    private String fileName;

    @ApiModelProperty(value = "数据文件大小")
    private String fileSize;

    @ApiModelProperty(value = "数据文件路径")
    private String filePath;

    @ApiModelProperty(value = "入库状态")
    private Integer loaderStatus;

    @ApiModelProperty(value = "元数据ID")
    private Integer storageId;

    @ApiModelProperty(value = "原因")
    private String reason;

    public Integer getLoaderDetailId() {
        return loaderDetailId;
    }

    public void setLoaderDetailId(Integer loaderDetailId) {
        this.loaderDetailId = loaderDetailId;
    }

    public Integer getLoaderId() {
        return loaderId;
    }

    public void setLoaderId(Integer loaderId) {
        this.loaderId = loaderId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getLoaderStatus() {
        return loaderStatus;
    }

    public void setLoaderStatus(Integer loaderStatus) {
        this.loaderStatus = loaderStatus;
    }

    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}