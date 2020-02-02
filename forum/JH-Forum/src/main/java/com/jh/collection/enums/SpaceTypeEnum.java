package com.jh.collection.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by xy on 2018/11/27.
 */
public enum SpaceTypeEnum {

    SPOT("SPOT","00","点"),
    LINE("LINE","01","线"),
    NOODLES("NOODLES","02","面");

    private String key;
    private String value;
    private String message;

    private SpaceTypeEnum(String key, String value, String message){
        this.key = key;
        this.value = value;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public static SpaceTypeEnum getSpaceTypeEnumByValue(String value){
        if(StringUtils.isEmpty(value)){
            return SPOT;
        }
        switch (value) {
            case "00":
                return SPOT;
            case "01":
                return LINE;
            case "02":
                return NOODLES;
            default:
                return SPOT;
        }
    }
}
