package com.jh.collection.enums;

/**
 * Created by xy on 2018/12/9.
 */
public enum MediaTypeEnum {

    MEDIA_AMR ("amr","录音"),
    MEDIA_PNG("png","图片"),
    MEDIA_JPG("jpg","图片");

    MediaTypeEnum(String value, String describe) {
        this.value = value;
        this.describe = describe;
    }

    private String value;
    private String describe;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
