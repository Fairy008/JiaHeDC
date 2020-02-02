package com.jh.manage.storage.Enum;

/**
 * Description:
 * 1.再加工数据发布状态
 *
 * @version <1> 2018-06-12 12:32 zhangshen: Created.
 */
public enum ReprocessPubStaEnum {

    DATA_REPROCESS_PUB_STA_WFB(2701,"待发布"),
    //DATA_REPROCESS_PUB_STA_DFB(2702,"待发布"),//去掉未发布
    DATA_REPROCESS_PUB_STA_FBCG(2703,"发布成功"),
    DATA_REPROCESS_PUB_STA_FBSB(2704,"发布失败");

    private Integer id;

    private String msg;

    ReprocessPubStaEnum(Integer id, String msg) {
        this.id = id ;
        this.msg = msg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
