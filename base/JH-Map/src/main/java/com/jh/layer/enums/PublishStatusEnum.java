package com.jh.layer.enums;

/**
 * @description: Yes or No Enum .
 * @version <1> 2018/1/22 djh： Created.
 */
public enum PublishStatusEnum {
    PUBLISH_STATUS_Y("PUBLISH_STATUS_Y",2201,"发布状态"),
    PUBLISH_STATUS_N("PUBLISH_STATUS_N",2202,"待发布状态"),

    DATA_PUB_STA_WFB("DATA_PUB_STA_WFB",2701,"待发布"),
    //DATA_REPROCESS_PUB_STA_DFB("DATA_REPROCESS_PUB_STA_DFB",2702,"待发布"),//去掉未发布
    DATA_PUB_STA_FBCG("DATA_PUB_STA_FBCG",2703,"发布成功"),
    DATA_PUB_STA_FBSB("DATA_PUB_STA_FBSB",2704,"发布失败");

	private String key ;
    private Integer value;
    private String text;

    private PublishStatusEnum(String key, Integer value, String text){
        this.key = key;
        this.value = value;
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

}