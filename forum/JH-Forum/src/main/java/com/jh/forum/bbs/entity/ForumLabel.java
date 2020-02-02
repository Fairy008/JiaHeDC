package com.jh.forum.bbs.entity;

import com.jh.entity.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 论坛标签实体类
 * @version <1> 2019/3/5 mason:Created.
 */
public class ForumLabel extends BaseEntity {

    //标签id
    private Integer labelId;

    //标签名称
    private String labelName;

    //标签颜色
    private String labelColor;

    private List<Integer> labelIds;

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public List<Integer> getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(List<Integer> labelIds) {
        this.labelIds = labelIds;
    }
}