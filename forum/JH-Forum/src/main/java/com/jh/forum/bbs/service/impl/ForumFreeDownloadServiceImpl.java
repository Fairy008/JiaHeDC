package com.jh.forum.bbs.service.impl;

import com.jh.forum.bbs.entity.ForumFreeDownload;
import com.jh.forum.bbs.mapping.IForumFreeDownloadMapper;
import com.jh.forum.bbs.service.IForumFreeDownloadService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ForumFreeDownloadServiceImpl implements IForumFreeDownloadService {

    @Autowired
    private IForumFreeDownloadMapper iForumFreeDownloadMapper;

    @Override
    public ResultMessage countFree(Integer userId) {

        int countFree = iForumFreeDownloadMapper.countFree(userId);

        return ResultMessage.success(countFree);
    }

    @Override
    public ResultMessage insertSelective(ForumFreeDownload record) {
        int i = iForumFreeDownloadMapper.insertSelective(record);
        return ResultMessage.success(i);
    }
}
