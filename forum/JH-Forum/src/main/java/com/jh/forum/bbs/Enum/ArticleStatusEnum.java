package com.jh.forum.bbs.Enum;

/**
* @Description:     论坛帖子状态枚举类
* @Author:         mason
* @CreateDate:     2019/3/5 14:46
* @Version:        1.0
*/
public enum ArticleStatusEnum {

    ARTICLE_STATUS_DRAFT(12001,"draft","待提交"),
    ARTICLE_STATUS_PENDING(12002,"pending","待审核"),
    ARTICLE_STATUS_CHECKED(12003,"checked","已审核"),
    ARTICLE_STATUS_PUBLISHED(12004,"published","已发布"),
    ARTICLE_STATUS_WITHDRAW(12005,"withdraw","已撤回");

    private Integer id;
    private String code;
    private String name;

    ArticleStatusEnum(Integer id, String code, String name) {
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
