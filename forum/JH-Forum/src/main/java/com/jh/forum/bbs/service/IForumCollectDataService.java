package com.jh.forum.bbs.service;

import com.jh.forum.bbs.entity.ForumCollectData;
import com.jh.vo.ResultMessage;

public interface IForumCollectDataService {

    ResultMessage collectData(ForumCollectData forumCollectData);

    ResultMessage cancelData(ForumCollectData forumCollectData);

    ResultMessage selectCollectStatus(ForumCollectData forumCollectData);

    ResultMessage queryMyCollect (Integer userId);

}
