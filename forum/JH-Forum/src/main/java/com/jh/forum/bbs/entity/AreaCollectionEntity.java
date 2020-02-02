package com.jh.forum.bbs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.entity.BaseEntity;
import com.jh.enums.PublishStatus_Enum;

import java.time.LocalDateTime;

/**
 * @Description:  分布采集数据实体类
 * @version<1> 2019-3-8 cxw :Created.
 */
public class AreaCollectionEntity  extends BaseEntity{

    //主键
    private  Long  id;
    //区域ID
    private Long regionId;
    //作物ID
    private  Integer cropId;
    //数据时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;
    //种植面积
    private  Float area ;
    //发布状态
    private Integer publishStatus;
    //发布时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;
    //发布人名称
    private String publisherName;
    //发布者
    private Integer publisher;
    //种植顶点区域
    private String vertexBbox;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public String getVertexBbox() {
        return vertexBbox;
    }

    public void setVertexBbox(String vertexBbox) {
        this.vertexBbox = vertexBbox;
    }
}
