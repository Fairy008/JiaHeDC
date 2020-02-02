package com.jh.manage.order.model;


import com.jh.vo.ResultMessage;

/**
 * description:订单查询参数实体
 * @version <1> 2018-03-16 cxw： Created.
 */
public class DataOrderPath {

    private Integer orderId;//订单ID
    private String dataPath;//数据link路径

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    /**
     * 检查参数是否为空
     * @return
     * @version <1> 2018-03-14 cxw:Created.
     */
    public ResultMessage checkParam() {
        ResultMessage result = ResultMessage.success();
        if (this.orderId == null) {
            result = ResultMessage.fail();
            result.setMsg("订单ID不能为空");
            return result;
        }
        if (this.dataPath == null) {
            result = ResultMessage.fail();
            result.setMsg("订单路径不能为空");
            return result;
        }
        return result;
    }
}
