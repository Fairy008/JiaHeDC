package com.jh.manage.download.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel(value = "下载明细对象")
public class DataDownloadDetail{
    @ApiModelProperty(value = "下载明细主键")
    private Integer downloadDetailId;

    @ApiModelProperty(value = "下载任务ID")
    private Integer downloadId;

    @ApiModelProperty(value = "产品文件名称")
    private String productName;

    @ApiModelProperty(value = "产品时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate productTime;

    @ApiModelProperty(value = "下载完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime downloadTime;

    @ApiModelProperty(value = "文件大小")
    private String fileSize;

    public Integer getDownloadDetailId() {
        return downloadDetailId;
    }

    public void setDownloadDetailId(Integer downloadDetailId) {
        this.downloadDetailId = downloadDetailId;
    }

    public Integer getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(Integer downloadId) {
        this.downloadId = downloadId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public LocalDate getProductTime() {
        return productTime;
    }

    public void setProductTime(LocalDate productTime) {
        this.productTime = productTime;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDateTime getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(LocalDateTime downloadTime) {
        this.downloadTime = downloadTime;
    }
}