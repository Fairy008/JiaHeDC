package com.jh.cltApp.service.impl;

import com.jh.cltApp.entity.CltMediaSource;
import com.jh.cltApp.mapping.ICltMediaSourceMapper;
import com.jh.cltApp.service.ICltMediaSourceService;
import com.jh.vo.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 媒体资源实现类
 * @version <1> 2019/4/9 10:02 zhangshen:Created.
 */
@Service
@Transactional
public class CltMediaSourceServiceImpl implements ICltMediaSourceService {

    @Autowired
    private ICltMediaSourceMapper cltMediaSourceMapper;

    private static Logger log = LoggerFactory.getLogger(CltMediaSourceServiceImpl.class);

    @Override
    public ResultMessage findCltMediaSourceListByItemId(Integer itemId) {
        List<CltMediaSource> cltMediaSourceList = cltMediaSourceMapper.findCltMediaSourceListByItemId(itemId);
        return ResultMessage.success(cltMediaSourceList);
    }

    @Override
    public ResultMessage cerateMediaSource(CltMediaSource cltMediaSource) {
        cltMediaSourceMapper.insertSelective(cltMediaSource);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage deleteMediaSourceByTaskId(Integer taskId) {
        cltMediaSourceMapper.deleteMediaSourceByTaskId(taskId);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage deleteMediaSourceByItemId(Integer itemId) {
        cltMediaSourceMapper.deleteMediaSourceByItemId(itemId);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage deleteMediaSourceById(Integer id) {
        cltMediaSourceMapper.deleteByPrimaryKey(id);
        return ResultMessage.success();
    }
}
