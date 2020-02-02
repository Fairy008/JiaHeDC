package com.jh.collection.service;

import com.github.pagehelper.PageInfo;
import com.jh.collection.entity.CollectionMediaSource;
import com.jh.vo.ResultMessage;

/**
 * 媒体资源接口
 * @version <1> 2018-11-15 xy: Created.
 */
public interface ICollectionMediaSourceService {

    /**
     * 查询采集任务
     * @param mediaSourceQuery
     * @return
     * @version <1> 2018-07-23 xy： Created.
     */
    PageInfo<CollectionMediaSource> findByPage(CollectionMediaSource mediaSourceQuery);

    /**
     * 新增任务
     * @return
     * @version <1> 2018-11-08 xy： Created.
     */
    public ResultMessage addMediaSource(CollectionMediaSource mediaSource);

    /**
     * 更新采集任务
     * @return
     * @version <1> 2018-11-16 xy: Created.
     */
    public ResultMessage updateMediaSource(CollectionMediaSource collectionMediaSource);

    /**
     * 删除任务（逻辑删除）
     * @param mediaSourceId 媒体资源Id
     * @return
     * @version <1> 2018-11-16 xy： Created.
     */
    public ResultMessage deleteMediaSource(Integer mediaSourceId);

    public ResultMessage getMediaSource(Integer mediaSourceId);

}
