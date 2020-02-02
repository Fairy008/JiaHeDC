package com.jh.forum.bbs.service.impl;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.base.service.impl.BaseServiceImpl;
import com.jh.forum.bbs.entity.ForumKeyWords;
import com.jh.forum.bbs.mapping.IForumKeyWordsMapper;
import com.jh.forum.bbs.service.IForumKeyWordsService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class ForumKeyWordsServiceImpl  implements IForumKeyWordsService {

    @Autowired
    private IForumKeyWordsMapper forumKeyWordsMapper;

    @Override
    public ResultMessage getKeyWords() {
        List<ForumKeyWords> list = forumKeyWordsMapper.getKeyWords();
        return ResultMessage.success(list);
    }


}
