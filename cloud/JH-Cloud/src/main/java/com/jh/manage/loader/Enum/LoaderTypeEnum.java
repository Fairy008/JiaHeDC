package com.jh.manage.loader.Enum;

/**
 * Description:入库方式枚举类
 *
 * @version <1> 2018-03-08  lcw : Created.
 */
public enum LoaderTypeEnum {

    DATA_LOADER_TYPE_DOWNLOAD(1401,"DATA_LOADER_TYPE_DOWNLOAD",1,"自动入库"),
    DATA_LOADER_TYPE_IMPORT(1402,"DATA_LOADER_TYPE_IMPORT",2,"数据导入");

    private Integer id;

    private String key;

    private Integer value ;

    private String msg;

    LoaderTypeEnum(Integer id , String key, Integer value, String msg) {
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
