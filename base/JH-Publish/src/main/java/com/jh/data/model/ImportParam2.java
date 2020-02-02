package com.jh.data.model;


import com.jh.entity.PageEntity;

/**
 * Description:
 *
 * @version <1> 2018-03-07  lcw : Created.
 */
public class ImportParam2 extends PageEntity{

    private String fileName; //文件名

    private long  fileSize; //文件大小


    private int fileType; //文件属性 1文件夹 2文件

    private String fileTypeName; //文件属性：文件夹或文件

    private String path ;  //文件路径

    private String ignorePrefix;  //包含前缀的忽略

    private Integer storageType;//从字典表中获取

    private String remark;//文件描述


    //再加工数据属性：区域ID
    private Long regionId;

    //再加工数据属性： 卫星
    private String satellite;

    //再加工数据属性： 传感器
    private String sensor;

    //再加工数据属性： 关键字
    private String words;

    //再加工数据属性：数据日期
    private String dataTime;

    //是否有正确文件，0全部无效文件 1包含有效文件
    private String isValidFile;



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

    public String getFileTypeName() {
        return fileTypeName;
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIgnorePrefix() {
        return ignorePrefix;
    }

    public void setIgnorePrefix(String ignorePrefix) {
        this.ignorePrefix = ignorePrefix;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getStorageType() {
        return storageType;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSatellite() {
        return satellite;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getIsValidFile() {
        return isValidFile;
    }

    public void setIsValidFile(String isValidFile) {
        this.isValidFile = isValidFile;
    }
}
