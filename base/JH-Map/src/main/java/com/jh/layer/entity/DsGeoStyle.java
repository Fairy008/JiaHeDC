package com.jh.layer.entity;

import com.jh.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Description: 样式信息
 * @version <1> 2018/6/19 15:11 zhangshen: Created.
 */
@ApiModel(value = "样式信息")
public class DsGeoStyle extends BaseEntity {

    @ApiModelProperty(value = "样式ID")
    private Integer styleId;

    @ApiModelProperty(value = "样式名称")
    private String styleName;

    @ApiModelProperty(value = "样式中文名")
    private String styleNameCn;

    @ApiModelProperty(value = "工作区")
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
        this.styleName = styleName == null ? null : styleName.trim();
    }

    public String getStyleNameCn() {
        return styleNameCn;
    }

    public void setStyleNameCn(String styleNameCn) {
        this.styleNameCn = styleNameCn == null ? null : styleNameCn.trim();
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace == null ? null : workspace.trim();
    }
}