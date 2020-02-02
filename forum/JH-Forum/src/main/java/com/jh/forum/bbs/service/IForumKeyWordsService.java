package com.jh.forum.bbs.service;

import com.jh.biz.persist.IBaseService;
import com.jh.forum.bbs.entity.ForumKeyWords;
import com.jh.vo.ResultMessage;

public interface IForumKeyWordsService  {
    /*
     * 功能描述: 查询关键字
     * @Param:
     * @Return: []
     * @version<1>  2019/8/28  wangli :Created
     */
    ResultMessage getKeyWords();
}
