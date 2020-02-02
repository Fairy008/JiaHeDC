package com.jh.manage.storage.model;


import com.jh.entity.PageEntity;
import com.jh.manage.storage.entity.DataReprocess;

/**
 * Description:
 * 1.
 *
 * @version <1> 2018-04-25 12:25 zhangshen: Created.
 */
public class ImportReprocessParam extends PageEntity {

    private DataReprocess dataReprocess;//再加工数据

    private DataReprocess[]reprocessDataArray;

    private Integer []reprocessIds;//再加工数据主键集合

    private String fileName; //文件名

    private int fileType; //文件属性 1文件夹 2文件

    private Integer storageType;//从字典表中获取

    private String beginTime;//开始时间

    private String endTime;//结束时间

    private Integer publishStatus;//发布状态

    private String style;//样式名称

    private Integer creator ;

    private String creatorName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public Integer getStorageType() {
        return storageType;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public DataReprocess getDataReprocess() {
        return dataReprocess;
    }

    public void setDataReprocess(DataReprocess dataReprocess) {
        this.dataReprocess = dataReprocess;
    }

    public Integer[] getReprocessIds() {
        return reprocessIds;
    }

    public void setReprocessIds(Integer[] reprocessIds) {
        this.reprocessIds = reprocessIds;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public DataReprocess[] getReprocessDataArray() {
        return reprocessDataArray;
    }

    public void setReprocessDataArray(DataReprocess[] reprocessDataArray) {
        this.reprocessDataArray = reprocessDataArray;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }


    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}
