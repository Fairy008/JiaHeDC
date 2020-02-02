package com.jh.forum.bbs.Enum;

/**
* @Description:     论坛帖子状态枚举类
* @Author:         mason
* @CreateDate:     2019/3/5 14:46
* @Version:        1.0
*/
public enum CltTaskStatusEnum {

    ARTICLE_STATUS_DRAFT(22001,"draft","未分享"),
    ARTICLE_STATUS_PENDING(22002,"pending","待审核"),
    ARTICLE_STATUS_CHECKED(22003,"checked","已审核"),
    ARTICLE_STATUS_PUBLISHED(22004,"published","已分享"),
    ARTICLE_STATUS_WITHDRAW(22005,"withdraw","已撤回");

    private Integer id;
    private String code;
    private String name;

    CltTaskStatusEnum(Integer id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
