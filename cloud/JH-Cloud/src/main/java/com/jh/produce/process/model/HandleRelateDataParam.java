package com.jh.produce.process.model;


/**
 * description:
 * @version <1> 2019-10-08 cxw： Created.
 */
public class HandleRelateDataParam {

    private  Integer dataIndex;
    private  Integer handleId;
    private  Integer storageId;
    private  String  filePath;
    private  Integer handleDataId;

    public Integer getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(Integer dataIndex) {
        this.dataIndex = dataIndex;
    }

    public Integer getHandleId() {
        return handleId;
    }

    public void setHandleId(Integer handleId) {
        this.handleId = handleId;
    }

    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getHandleDataId() {
        return handleDataId;
    }

    public void setHandleDataId(Integer handleDataId) {
        this.handleDataId = handleDataId;
    }
}
