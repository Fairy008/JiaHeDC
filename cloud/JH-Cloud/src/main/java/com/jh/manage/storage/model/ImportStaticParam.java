package com.jh.manage.storage.model;


import com.jh.entity.PageEntity;
import com.jh.manage.storage.entity.DataStatic;

/**
 * Description:
 * 1.
 *
 * @version <1> 2018-04-25 13:09 zhangshen: Created.
 */
public class ImportStaticParam extends PageEntity {

    private DataStatic dataStatic;

    private String fileName; //文件名

    private int fileType; //文件属性 1文件夹 2文件

    private Integer storageType;//从字典表中获取

    private String regionCode;//区域Code

    private String beginTime;//开始时间

    private String endTime;//结束时间

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

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public DataStatic getDataStatic() {
        return dataStatic;
    }

    public void setDataStatic(DataStatic dataStatic) {
        this.dataStatic = dataStatic;
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
}
