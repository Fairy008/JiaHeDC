package com.jh.manage.download.Enum;

/**
 * Created by XZG on 2018/2/28.
 */
public enum DownloadStatusEnum {

    DOWNLOAD_STATUS("DOWNLOAD_STATUS",800,"下载状态"),
    WAIT("WAIT",801,"待下载"),
    DOWNING("DOWNING",802,"下载中"),
    DOWN_SUCCESS("DOWN_SUCCESS",803,"下载成功"),
    DOWN_FAIL("DOWN_FAIL",804,"下载失败"),
    SUSPEND("SUSPEND",805,"暂停");

    private String key;
    private Integer value;
    private String statusName;
    private DownloadStatusEnum(String key,Integer value,String statusName){
        this.key = key;
        this.value = value;
        this.statusName = statusName;
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
