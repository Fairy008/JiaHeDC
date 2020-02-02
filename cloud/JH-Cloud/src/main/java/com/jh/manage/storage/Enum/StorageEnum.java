package com.jh.manage.storage.Enum;

/**
 * Description: 元数据Enum
 *
 * @version <1> 2018-02-28 lcw: Created
 */
public enum StorageEnum {

    STORAGE_AREA_NULL("STORAGE_AREA_NULL_ERROR","00020001","行政区不能为空"),
    STORAGE_BBOX_NULL("STORAGE_BBOX_NULL_ERROR","00020002","经纬度不能为空"),
    STORAGE_AREA_BBOX_NULL("STORAGE_AREA_BBOX_NULL_ERROR","00020003","行政区或经纬度不能为空"),
    STORAGE_SAT_NULL("STORAGE_SAT_NULL_ERROR","00020004","卫星不能为空"),
    STORAGE_STORAGETYPE_NULL("STORAGE_STORAGETYPE_NULL_ERROR","00020005","数据类型不能为空"),
    STORAGE_DATATIME_NULL("STORAGE_DATATIME_NULL_ERROR","00020006","影像时间不能为空");

    private String key;
    private String value;
    private String message;

    private StorageEnum(String key, String value, String message){
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
}
