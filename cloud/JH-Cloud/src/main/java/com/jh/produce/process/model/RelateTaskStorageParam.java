package com.jh.produce.process.model;

/**
 * description: 任务存储关联参数
 * @version <1> 2018-03-14 cxj： Created.
 */
public class RelateTaskStorageParam {
    private Integer handleId;
    private Integer storageId;

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
}
