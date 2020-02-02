package com.jh.manage.order.model;

import com.jh.entity.PageEntity;
import com.jh.manage.storage.entity.DataStorage;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 * description:订单查询参数实体
 * @version <1> 2018-03-07 cxw： Created.
 */
public class OrderParam extends PageEntity{

    private Integer orderId;//订单ID
    private String dataStatus; //数据状态（1有效，0无效)
    private Integer orderType;//订单分类:1数据订单，2需求订单
    private String creatorName;//创建人
    private Integer creator; //创建人ID
    private String createTime; //订单创建时间
    private String modifierName; //修改人姓名
    private Integer modifier; //修改人ID
    private String orderDescription; //订单需求
    private String orderAuditorName; //订单审核人
    private Integer orderAuditor; //订单审核人ID
    private Integer auditStatus; //审核状态：0待审核，1审核通过，2审核不通过
    private String auditTime; //审核时间
    private Integer handleStatus; //分发状态
    private String dataAccessTime; //数据访问截止时间
    private Integer handlerPerson; //分发人
    private String  remark ;// 备注
    private String beginTime; //开始时间
    private String endTime; //结束时间
    private String dataPath; //文件路径
    private List<DataStorage> dataStorages; //元数据集合
    private Integer personType;//用户类型 1701内部用户，1702外部用户
    private String auditSuggestion;//审核意见
    private String handleSuggestion;//分发意见
    private String orderCode; //订单编号

    private Integer refreshFlag; //MUI上拉1，下拉2

    private Integer orderFileNum; //订单文件阈值

    private Integer operator; //操作人ID

    private String operatorName; //操作人名称

    private String timeSlot;//查询时间段


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getOrderAuditorName() {
        return orderAuditorName;
    }

    public void setOrderAuditorName(String orderAuditorName) {
        this.orderAuditorName = orderAuditorName;
    }

    public Integer getOrderAuditor() {
        return orderAuditor;
    }

    public void setOrderAuditor(Integer orderAuditor) {
        this.orderAuditor = orderAuditor;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getDataAccessTime() {
        return dataAccessTime;
    }

    public void setDataAccessTime(String dataAccessTime) {
        this.dataAccessTime = dataAccessTime;
    }

    public Integer getHandlerPerson() {
        return handlerPerson;
    }

    public void setHandlerPerson(Integer handlerPerson) {
        this.handlerPerson = handlerPerson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public List<DataStorage> getDataStorages() {
        return dataStorages;
    }

    public void setDataStorages(List<DataStorage> dataStorages) {
        this.dataStorages = dataStorages;
    }


    public Integer getPersonType() {
        return personType;
    }

    public void setPersonType(Integer personType) {
        this.personType = personType;
    }

    public String getAuditSuggestion() {
        return auditSuggestion;
    }

    public void setAuditSuggestion(String auditSuggestion) {
        this.auditSuggestion = auditSuggestion;
    }

    public String getHandleSuggestion() {
        return handleSuggestion;
    }

    public void setHandleSuggestion(String handleSuggestion) {
        this.handleSuggestion = handleSuggestion;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Integer getOrderFileNum() {
        return orderFileNum;
    }

    public void setOrderFileNum(Integer orderFileNum) {
        this.orderFileNum = orderFileNum;
    }

    public Integer getRefreshFlag() {
        return refreshFlag;
    }

    public void setRefreshFlag(Integer refreshFlag) {
        this.refreshFlag = refreshFlag;
    }

    /**
     * 检查参数是否为空
     * @return
     * @version <1> 2018-03-14 cxw:Created.
     */
    public ResultMessage checkParam() {
        ResultMessage result = ResultMessage.success();
        if (this.orderDescription == null) {
            result = ResultMessage.fail();
            result.setMsg("订单需求描述不能为空");
        }
        return result;
    }

    /**
     * 检查参数是否为空
     * @return
     * @version <1> 2018-03-14 cxw:Created.
     */
    public ResultMessage checkDitributeParam() {
        ResultMessage result = ResultMessage.success();
        if (this.orderId == null) {
            result = ResultMessage.fail();
            result.setMsg("订单ID不能为空");
            return result;
        }
        if (this.dataAccessTime == null) {
            result = ResultMessage.fail();
            result.setMsg("订单截止时间不能为空");
            return result;
        }
        if (this.dataStorages.size() == 0) {
            result = ResultMessage.fail();
            result.setMsg("订单元数据不能为空");
            return result;
        }
        return result;
    }

    /**
     * 检查参数是否为空
     * @return
     * @version <1> 2018-03-14 cxw:Created.
     */
    public ResultMessage checkAuditParam() {
        ResultMessage result = ResultMessage.success();
        if (this.orderId == null) {
            result = ResultMessage.fail();
            result.setMsg("订单Id不能为空");
            return result;
        }
        if (this.auditStatus == null) {
            result = ResultMessage.fail();
            result.setMsg("订单审核状态不能为空");
            return result;
        }
        return result;
    }

    /**
     * 检查参数是否为空
     * @return
     * @version <1> 2018-03-14 cxw:Created.
     */
    public ResultMessage checkHandleParam() {
        ResultMessage result = ResultMessage.success();
        if (this.orderId == null) {
            result = ResultMessage.fail();
            result.setMsg("订单Id不能为空");
            return result;
        }
        if (this.auditStatus == null) {
            result = ResultMessage.fail();
            result.setMsg("订单分发状态不能为空");
            return result;
        }
        return result;
    }

    /**
     * 检查参数是否为空
     * @return
     * @version <1> 2018-03-14 cxw:Created.
     */
    public ResultMessage checkEditParam() {
        ResultMessage result = ResultMessage.success();
        if (this.orderDescription == null) {
            result = ResultMessage.fail();
            result.setMsg("订单需求描述不能为空");
            return result;
        }
        if (this.orderId == null) {
            result = ResultMessage.fail();
            result.setMsg("订单Id不能为空");
            return result;
        }
        return result;
    }
}
