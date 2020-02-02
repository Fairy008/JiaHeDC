package com.jh.manage.download.Enum;

/**
 * 数据采集模块返回状态码
 * Created by XZG on 2018-02-28
 */
public enum DataAcquisitionEnum {

    PARAM_EMPTY("PARAM_EMPTY","11111","参数为空"),
    SAVE_DOWNLOAD_FAIL("SAVE_DOWNLOAD_FAIL","2222","新增下载任务失败"),
    SAVE_DOWNLOAD_SUCCESS("SAVE_DOWNLOAD_SUCCESS","3333","新增下载任务成功"),
    TRYAGAIN_FAIL("TRYAGAIN_FAIL","4444","重启下载任务失败"),
    TRYAGAIN_SUCCESS("TRYAGAIN_SUCCESS","5555","重启下载任务成功"),
    EDIT_DOWNLOAD_FAIL("EDIT_DOWNLOAD_FAIL","6666","修改下载任务信息失败"),
    EDIT_DOWNLOAD_SUCCESS("EDIT_DOWNLOAD_SUCCESS","7777","修改下载任务信息成功"),
    NOT_EDIT_DOWNLOAD("NOT_EDIT_DOWNLOAD","8888","不能修改下载任务信息"),
    SUSPEND_DOWNLOAD_FAIL("SUSPEND_DOWNLOAD_FAIL","8888","暂停下载任务失败"),
    SUSPEND_DOWNLOAD_SUCCESS("SUSPEND_DOWNLOAD_SUCCESS","8888","暂停下载任务成功"),
    NOT_SUSPEND_DOWNLOAD("NOT_SUSPEND_DOWNLOAD","8888","当前任务不可以暂停"),
    NOT_CONTINUE_DOWNLOAD("NOT_CONTINUE_DOWNLOAD","8888","当前任务不可以继续下载"),
    CONTINUE_DOWNLOAD_SUCCES("CONTINUE_DOWNLOAD_SUCCES","8888","继续下载任务成功"),
    CONTINUE_DOWNLOAD_FAIL("CONTINUE_DOWNLOAD_FAIL","8888","继续下载任务失败"),
    DOWNLOAD_NOT_EXISTS("DOWNLOAD_NOT_EXISTS","8888","下载任务不存在");


    private String key;
    private String value;
    private String mesasge;

    private DataAcquisitionEnum(String key, String value, String message){
        this.key = key;
        this.value = value;
        this.mesasge = message;
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

    public String getMesasge() {
        return mesasge;
    }

    public void setMesasge(String mesasge) {
        this.mesasge = mesasge;
    }

}
