package com.jh.forum.bbs.Enum;

/**
* @Description:    论坛帖子类型枚举类
* @Author:         mason
* @CreateDate:     2019/3/5 14:38
* @Version:        1.0
*/
public enum ArticleTypeEnum {

    ARTICLE_TYPE_PAPER(11001,"paper","报告"),
    ARTICLE_TYPE_SURVEY(11002,"survey","约调研"),
    ARTICLE_TYPE_FAQ(11003,"faq","问答"),
    ARTICLE_TYPE_SHARE(11004,"share","数据分享"),
    ARTICLE_TYPE_OTHER(11005,"other","其他");

    private Integer id;
    private String code;
    private String name;

    ArticleTypeEnum(Integer id, String code, String name) {
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
    }}
