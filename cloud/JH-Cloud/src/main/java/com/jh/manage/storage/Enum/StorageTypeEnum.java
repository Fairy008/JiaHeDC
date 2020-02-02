package com.jh.manage.storage.Enum;

/**
 * Description: 数据类型枚举类
 *
 * @version <1> 2018-03-13  lcw : Created.
 */
public enum StorageTypeEnum {

    DATA_STORAGE_TYPE_YSSJ(701,"DATA_STORAGE_TYPE_YSSJ",1,"原始数据"),
    DATA_STORAGE_TYPE_CGSJ(702,"DATA_STORAGE_TYPE_CGSJ",2,"成果数据"),
    DATA_STORAGE_TYPE_SLSJ(703,"DATA_STORAGE_TYPE_SLSJ",3,"矢量数据");

    private Integer id;

    private String key;

    private Integer value ;

    private String msg;

    StorageTypeEnum(Integer id , String key, Integer value, String msg) {
        this.id = id ;
        this.key = key;
        this.value = value;
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
