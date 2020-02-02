package com.jh.manage.download.entity;

import com.jh.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "数据下载参数配置对象")
public class DataDownloadConfig extends BaseEntity {

    @ApiModelProperty(value = "下载参数主键")
    private Integer downloadMetaId;

    @ApiModelProperty(value = "下载URL")
    private String downloadUrl;

    @ApiModelProperty(value = "下载文件路径")
    private String path;

    @ApiModelProperty(value = "端口")
    private Integer port;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "数据分类ID")
    private Integer satId;

    @ApiModelProperty(value = "下载方式")
    private Integer downloadType;

    @ApiModelProperty(value = "下载方式名称")
    private String downloadTypeName;

    public Integer getDownloadMetaId() {
        return downloadMetaId;
    }

    public void setDownloadMetaId(Integer downloadMetaId) {
        this.downloadMetaId = downloadMetaId;
    }


    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
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
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getSatId() {
        return satId;
    }

    public void setSatId(Integer satId) {
        this.satId = satId;
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