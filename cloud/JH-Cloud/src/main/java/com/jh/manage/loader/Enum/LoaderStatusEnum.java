package com.jh.manage.loader.Enum;

/**
 * Description: 入库状态枚举类
 *
 * @version <1> 2018-03-07  lcw : Created.
 */
public enum LoaderStatusEnum {

    DATA_LOADER_STATUS_READY(901,"DATA_LOADER_STATUS_READY",0,"待入库"),
    DATA_LOADER_STATUS_ON(902,"DATA_LOADER_STATUS_ON",1,"入库中"),
    DATA_LOADER_STATUS_OK(903,"DATA_LOADER_STATUS_OK",2,"入库成功"),
    DATA_LOADER_STATUS_FAILURE(904,"DATA_LOADER_STATUS_FAILURE",3,"入库失败");


    private Integer id;

    private String key;

    private Integer value ;

    private String msg;

    private String classStyle;

    LoaderStatusEnum(Integer id , String key, Integer value, String msg) {
        this.id = id;
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
