package com.jh.collection.service;

import com.github.pagehelper.PageInfo;
import com.jh.collection.entity.CollectionTemplate;
import com.jh.collection.entity.query.CollectionTemplateQuery;
import com.jh.vo.ResultMessage;

/**
 * 采集模板接口
 * @version <1> 2018-12-04 xy: Created.
 */
public interface ICollectionTemplateService {

    /**
     * 查询采集模板
     * @param collectionTemplateQuery
     * @return
     * @version <1> 2018-07-23 xy： Created.
     */
    PageInfo<CollectionTemplate> findByPage(CollectionTemplateQuery collectionTemplateQuery);

    /**
     * 新增任务
     * @return
     * @version <1> 2018-11-08 xy： Created.
     */
    public ResultMessage addTemplate(CollectionTemplate collectionTemplate);

    /**
     * 更新采集任务
     * @return
     * @version <1> 2018-11-16 xy: Created.
     */
    public ResultMessage updateTemplate(CollectionTemplate collectionTemplate);

    /**
     * 删除任务（逻辑删除）
     * @param collectionTemplate 任务Id
     * @return
     * @version <1> 2018-11-16 xy： Created.
     */
    public ResultMessage deleteTemplate(CollectionTemplate collectionTemplate);

    CollectionTemplate getById(Integer id);

}
