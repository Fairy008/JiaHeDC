package com.jh.collection.entity;

import java.util.Date;

/**
 * 采集媒体资源实体
 * @version <1> 2018-11-15 xy： Created.
 */
public class CollectionMediaSource {
    private Integer id;//主键Id

    private String mediaType;//媒体类型

    private Date createTime;//创建时间

    private Date updateTime;//更新时间

    private String createor;//创建者

    private String updateor;//更新者

    private String remark;//备注

    private Integer delFlag;//删除标志

    private byte[] mediaBin;//资源二进制文件

    private String mediaBase64;//二进制对应的Base64媒体资源文件

    private Integer soundTime;//录音时间

    private String mediaPath;//资源上传路径

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public Integer getSoundTime() {
        return soundTime;
    }

    public void setSoundTime(Integer soundTime) {
        this.soundTime = soundTime;
    }

    public String getMediaBase64() {
        return mediaBase64;
    }

    public void setMediaBase64(String mediaBase64) {
        this.mediaBase64 = mediaBase64;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType == null ? null : mediaType.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateor() {
        return createor;
    }

    public void setCreateor(String createor) {
        this.createor = createor == null ? null : createor.trim();
    }

    public String getUpdateor() {
        return updateor;
    }

    public void setUpdateor(String updateor) {
        this.updateor = updateor == null ? null : updateor.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public byte[] getMediaBin() {
        return mediaBin;
    }

    public void setMediaBin(byte[] mediaBin) {
        this.mediaBin = mediaBin;
    }
}