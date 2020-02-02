package com.jh.produce.process.entity;

/**
 * Description:
 *
 * @version <1> 2018-05-10  lcw : Created.
 */
public class RelateTaskStorage {

    private Integer handleId;

    private Integer storageId;

    private String filePathLink;

    public Integer getHandleId() {
        return handleId;
    }

    public void setHandleId(Integer handleId) {
        this.handleId = handleId;
    }

    public String getFilePathLink() {
        return filePathLink;
    }

    public void setFilePathLink(String filePathLink) {
        this.filePathLink = filePathLink;
    }


    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }


}
