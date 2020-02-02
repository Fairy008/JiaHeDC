package com.jh.forum.bbs.Enum;

public enum DownloadDataTypeEnum {
    classify_trmm(18701, "classify_trmm","降雨字典id"),
    classify_t(18702, "classify_t","地温字典id"),
    classify_weather(18703, "classify_weather","气温字典id"),
    classify_drought_index(18801, "classify_drought_index","干旱指数字典id"),
    classify_diseases(18607, "classify_diseases","病虫害监测字典id");

    private Integer id;
    private String code;
    private String info;

    DownloadDataTypeEnum(Integer id, String code, String name) {
        this.id = id;
        this.code = code;
        this.info = info;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
