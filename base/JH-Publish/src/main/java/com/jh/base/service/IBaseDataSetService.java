package com.jh.base.service;

import com.github.pagehelper.PageInfo;
import com.jh.data.entity.DsResultimage;
import com.jh.data.model.ProductQueryParam;
import com.jh.vo.ResultMessage;

/**
 * Description:各指数接口服务类
 * @version <1> 2018-09-26 lijie： Created.
 */
public interface IBaseDataSetService {

    /**
     * 根据任务ID当年指数
     * @param productQueryParam
     * @return
     */
    PageInfo<Object> findDataSatListByTaskId(ProductQueryParam productQueryParam);

    /**
     * 根据任务ID更新各项指数发布信息
     * @version <1> 2018-09-26 lijie： Created.
     */
    ResultMessage updatePublishStatusByTaskId(DsResultimage product);

    /**
     * 根据任务ID删除各项指数发布信息
     * @version <1> 2018-09-26 lijie： Created.
     */
    ResultMessage deleteDataSetByTaskId(Integer taskId);

    /**
     * 修改指数数据
     * @param dataStr 具体指数
     * @return
     * @version <1> 2018-09-26 lijie： Created.
     */
    ResultMessage updateDataSat(String dataStr);


}
