package com.jh.collection.service.impl;

import com.github.pagehelper.PageInfo;
import com.jh.collection.entity.CollectionMediaSource;
import com.jh.collection.mapping.CollectionMediaSourceMapper;
import com.jh.collection.service.ICollectionMediaSourceService;
import com.jh.util.JsonUtils;
import com.jh.vo.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.jh.constant.CommonDefineEnum.FIND_OBJECT_NOT_EXISTS;
import static com.jh.constant.CommonDefineEnum.SAVE_OBJECT_FAIL;

/**
 * 媒体资源Service
 * @version <1> 2018-11-15 xy: Created.
 */
@Service
public class CollectionMediaSourceServiceImpl implements ICollectionMediaSourceService {

    private static final Logger LOG = LoggerFactory.getLogger(CollectionMediaSourceServiceImpl.class);

    @Autowired
    private CollectionMediaSourceMapper collectionMediaSourceMapper;

    @Override
    public PageInfo<CollectionMediaSource> findByPage(CollectionMediaSource mediaSourceQuery) {
        return null;
    }


    @Transactional(readOnly = false)
    @Override
    public ResultMessage addMediaSource(CollectionMediaSource mediaSource) {
        LOG.info(">>>addMediaSource params:{}", JsonUtils.objectToJson(mediaSource));
        mediaSource.setCreateTime(new Date());
        mediaSource.setDelFlag(1);
        int count = collectionMediaSourceMapper.insert(mediaSource);
        if(count>0){
            return ResultMessage.success(mediaSource.getId());
        }else{
            return ResultMessage.fail(SAVE_OBJECT_FAIL.getMesasge());
        }
    }

    @Override
    public ResultMessage updateMediaSource(CollectionMediaSource collectionMediaSource) {
        return null;
    }

    @Override
    public ResultMessage deleteMediaSource(Integer mediaSourceId) {
        LOG.info(">>>add mediaSource id:{}", mediaSourceId);
        if(mediaSourceId == null){
            ResultMessage.fail("删除ID不能为空");
        }
        CollectionMediaSource collectionMediaSource = new CollectionMediaSource();
        collectionMediaSource.setId(mediaSourceId);
        collectionMediaSource.setDelFlag(0);
        collectionMediaSource.setUpdateTime(new Date());
        collectionMediaSourceMapper.updateByPrimaryKeySelective(collectionMediaSource);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage getMediaSource(Integer mediaSourceId) {
        CollectionMediaSource collectionMediaSource = collectionMediaSourceMapper.selectByPrimaryKey(mediaSourceId);
        if(collectionMediaSource == null){
            return ResultMessage.fail(FIND_OBJECT_NOT_EXISTS.getMesasge());
        }
        return ResultMessage.success(collectionMediaSource);
    }


}
