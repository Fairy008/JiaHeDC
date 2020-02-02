package com.jh.produce.process.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "算法处理输入输出文件对象")
public class HandleStepFile {

    @ApiModelProperty(value = "算法步骤ID")
    private Integer handleDetailId;

    @ApiModelProperty(value = "文件存储路径")
    private String filePath;

    @ApiModelProperty(value = "存储ID")
    private Integer storageId;

    @ApiModelProperty(value = "输入输出类型")
    private Integer ioType;

    public Integer getHandleDetailId() {
        return handleDetailId;
    }

    public void setHandleDetailId(Integer handleDetailId) {
        this.handleDetailId = handleDetailId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }

    public Integer getIoType() {
        return ioType;
    }

    public void setIoType(Integer ioType) {
        this.ioType = ioType;
    }
}