package com.jh.produce.process.model;

/**
 * description: 数据参数
 * @version <1> 2018-03-20 cxj： Created.
 */
public class DataParam {
    private Integer handleDetailId;
    private Integer ioType;
    private String fileName;

    public Integer getHandleDetailId() {
        return handleDetailId;
    }

    public void setHandleDetailId(Integer handleDetailId) {
        this.handleDetailId = handleDetailId;
    }

    public Integer getIoType() {
        return ioType;
    }

    public void setIoType(Integer ioType) {
        this.ioType = ioType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
