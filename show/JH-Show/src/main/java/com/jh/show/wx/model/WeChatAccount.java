package com.jh.show.wx.model;

/**
 * 提交后台表单数据 用户注册
 * @version <1> 2018-05-06 lxy： Created.
 */
public class WeChatAccount {
    private String wechatId;
    private String phone;
    private String code;

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
