package com.jh.collector.enums;


/**
 *
 * 子任务数据状态
 * XZG 2019-07-29  18:25
 */
public enum SubtaskDataPackageEnum {

    UPLOAD_YES(1,"已上传"),
    UPLOAD_NO(1,"未上传");

    private Integer value;
    private String name;
    SubtaskDataPackageEnum(Integer value,String name){
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
