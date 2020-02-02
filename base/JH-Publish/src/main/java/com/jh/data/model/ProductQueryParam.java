package com.jh.data.model;

import com.jh.entity.BaseEntity;

/**
 * Created by lijie on 2018/9/25.
 * Description: 数据生产查询实体
 * @version <1> 2018/9/19 lijie: Created.
 */


public class ProductQueryParam extends BaseEntity {

    /**
     * 任务id
     */
    private Integer productId;
    /**
     * 指数类型
     */
    private Integer dsType;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getDsType() {
        return dsType;
    }

    public void setDsType(Integer dsType) {
        this.dsType = dsType;
    }
}
