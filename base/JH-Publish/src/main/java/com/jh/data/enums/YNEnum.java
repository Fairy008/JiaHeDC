package com.jh.data.enums;

/**
 * Description:
 * 是否
 *
 * @version <1> 2018-08-13 14:42 lj: Created.
 */
public enum YNEnum {
    YNEnum_NO("否","0"),
    YNEnum_YES("是","1");

    private String name;
    private String value;

    YNEnum(String name, String value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取name
     * @param value
     * @return
     */
    public static String getChineseName(String value){
        for(YNEnum yn:YNEnum.values()){
            if(yn.getValue().equals(value)){
                return yn.getName();
            }
        }
        return null;
    }
}
