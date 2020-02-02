package com.jh.manage.download.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.PageEntity;
import com.jh.manage.download.entity.DataDownloadConfig;

import java.time.LocalDateTime;

/**
 * description:
 *
 * @version <1> 2018-01-24 lcw： Created.
 * @version <2> 2018-02-05 djh： 添加相关业务字段.
 */

public class DownloadConfigParam extends PageEntity {
    private DataDownloadConfig dataDownloadConfig;

    private Integer downloadMetaId;
    private Integer satId;
    private String satName;
    private String downloadUrl;
    private String path;
    private Integer port;
    private String username;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private String creatorName;
    private Integer creator;
    private Integer downloadType;
    private String downloadTypeName;

    public Integer getDownloadMetaId() {
        return downloadMetaId;
    }

    public void setDownloadMetaId(Integer downloadMetaId) {
        this.downloadMetaId = downloadMetaId;
    }

    public Integer getSatId() {
        return satId;
    }

    public void setSatId(Integer satId) {
        this.satId = satId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getSatName() {
        return satName;
    }

    public void setSatName(String satName) {
        this.satName = satName;
    }

    public DataDownloadConfig getDataDownloadConfig() {
        return dataDownloadConfig;
    }

    public void setDataDownloadConfig(DataDownloadConfig dataDownloadConfig) {
        this.dataDownloadConfig = dataDownloadConfig;
    }

    public Integer getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(Integer downloadType) {
        this.downloadType = downloadType;
    }

    public String getDownloadTypeName() {
        return downloadTypeName;
    }

    public void setDownloadTypeName(String downloadTypeName) {
        this.downloadTypeName = downloadTypeName;
    }
}
