package com.jh.manage.order.Enum;

/**
 * Description: 订单操作状态分类枚举类
 * @version <1> 2018-03-29  cxw : Created.
 */
public enum OrderStatusEnum {

    ORDER_HANDLE_SUCCESS(101,"HANDLESUCCESS","分发成功"),
    ORDER_HANDLE_FAIL(102,"HANDLEFAIL","分发失败"),
    ORDER_NO_HANDLE_SUCCESS(103,"NOHANDLESUCCESS","拒绝分发"),
//    ORDER_NO_HANDLE_FAIL(104,"NOHANDLEFAIL","订单拒绝分发失败"),
//    ORDER_HANDLE_OP_FAIL(105,"HANDLEOPFAIL","订单分发操作失败"),
    DATA_ORDER_CREATE_SUCCESS(106,"DATACREATESUCCESS","创建成功"),
    DATA_ORDER_CREATE_FAIL(107,"DATACREATEFAIL","创建失败"),
//    NEED_ORDER_CREATE_SUCCESS(108,"NEEDCREATESUCCESS","创建成功"),
//    NEED_ORDER_CREATE_FAIL(109,"NEEDCREATEFAIL","创建失败"),
    NEED_ORDER_EDIT_SUCCESS(110,"NEEDCREATESUCCESS","修改成功"),
    NEED_ORDER_EDIT_FAIL(111,"NEEDCREATEFAIL","修改失败"),
    DATA_ORDER_AUDIT_SUCCESS(112,"DATAAUDITSUCCESS","审核通过"),
    DATA_ORDER_AUDIT_FAIL(113,"DATAAUDITFAIL","审核失败"),
//    NEED_ORDER_AUDIT_SUCCESS(114,"NEEDAUDITSUCCESS","审核通过"),
//    NEED_ORDER_AUDIT_FAIL(115,"NEEDAUDITFAIL","需求订单审核通过操作失败"),
//    ORDER_NO_AUDIT_SUCCESS(116,"NOAUDITSUCCESS","审核"),
    ORDER_NO_AUDIT_FAIL(117,"NOAUDITFAIL","审核不通过"),
//    ORDER_AUDIT_FAIL(118,"NOAUDITFAIL","订单审核操作失败"),
    ORDER_DOWNLOAD_SUCCESS(119,"DOWNLOADSUCCESS","订单数据下载完成"),
    ORDER_DOWNLOAD_FAIL(120,"DOWNLOADFAIL","订单数据下载失败"),
    ORDER_DOWNLOAD_OVERTIME(121,"DOWNLOADFAIL","订单下载数据已过期");

    private Integer id;

    private String key;

    private String msg;

    OrderStatusEnum(Integer id , String key,  String msg) {
        this.id = id;
        this.key = key;
        this.msg = msg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
