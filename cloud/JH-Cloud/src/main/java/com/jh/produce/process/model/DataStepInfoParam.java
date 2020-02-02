package com.jh.produce.process.model;

/**
 * description: 数据步骤信息参数
 * @version <1> 2018-03-22 cxj： Created.
 */
public class DataStepInfoParam {
    private Integer handleMetaId;
    private Integer orderInTask;
    private String handleParams;

    public Integer getHandleMetaId() {
        return handleMetaId;
    }

    public void setHandleMetaId(Integer handleMetaId) {
        this.handleMetaId = handleMetaId;
    }

    public Integer getOrderInTask() {
        return orderInTask;
    }

    public void setOrderInTask(Integer orderInTask) {
        this.orderInTask = orderInTask;
    }

    public String getHandleParams() {
        return handleParams;
    }

    public void setHandleParams(String handleParams) {
        this.handleParams = handleParams;
    }
}
