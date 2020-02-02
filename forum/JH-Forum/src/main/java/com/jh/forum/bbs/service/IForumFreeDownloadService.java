package com.jh.forum.bbs.service;

import com.jh.forum.bbs.entity.ForumFreeDownload;
import com.jh.vo.ResultMessage;

public interface IForumFreeDownloadService {

    ResultMessage countFree(Integer userId);

    ResultMessage insertSelective(ForumFreeDownload record);
}
