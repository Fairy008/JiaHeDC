package com.jh.forum.bbs.Enum;

/**
* @Description:    关注点赞枚举类
* @Author:         mason
* @CreateDate:     2019/3/5 14:49
* @Version:        1.0
*/
public enum FollowTypeEnum {

    FOLLOW_TYPE_FOLLOW(13001,"follow","收藏"),
    FOLLOW_TYPE_LIKE(13002,"like","点赞"),
    FOLLOW_TYPE_FOCUS(13003,"focus","关注");

private Integer id;
private String code;
private String name;

        FollowTypeEnum(Integer id, String code, String name) {
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
