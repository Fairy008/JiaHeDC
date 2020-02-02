package com.jh.layer.model;


import com.jh.entity.PageEntity;

/**
 * Description:
 * 1.样式管理参数
 *
 * @version <1> 2018-06-19 15:31 zhangshen: Created.
 */
public class DsGeoStyleParam extends PageEntity {

    private Integer styleId;

    private String styleName;

    private String styleNameCn;

    private String workspace;

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getStyleNameCn() {
        return styleNameCn;
    }

    public void setStyleNameCn(String styleNameCn) {
        this.styleNameCn = styleNameCn;
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }
}
