package com.jh.manage.loader.Enum;

/**
 * Description: 条件判断枚举类
 *
 * @version <1> 2018-03-01 lcw: Created
 */
public enum LoaderEnum {

    DATA_LOADER_ID_NULL("DATA_LOADER_ID_NULL","00030001","入库任务ID不能为空"),
    DATA_LOADER_STORAGE_PATH_NULL("DATA_LOADER_STORAGE_PATH_NULL","00030002","导入文件路径不能为空"),
    DATA_LOADER_PREFIX_ERROR("DATA_LOADER_PREFIX_ERROR","00030003","生成入库任务失败");

    private String key;
    private String value;
    private String message;

    private LoaderEnum(String key, String value, String message){
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
