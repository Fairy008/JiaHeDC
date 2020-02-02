package com.jh.data.enums;

/**
 * Description: 图层类型：2001矢量，2002栅格
 * 1.
 *
 * @version <1> 2018-06-15 15:01 zhangshen: Created.
 */
public enum LayerTypeEnum {
    LAYER_TYPE_SHP("LAYER_TYPE_SHP",2001,"矢量"),
    LAYER_TYPE_TIF("LAYER_TYPE_TIF",2002,"栅格");

    private String key ;
    private Integer value;
    private String text;

    private LayerTypeEnum(String key, Integer value, String text){
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
