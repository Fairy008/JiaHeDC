package com.jh.forum.cms.service.impl;

import com.github.pagehelper.PageInfo;
import com.jh.forum.bbs.service.IForumArticleService;
import com.jh.forum.bbs.vo.ArticleVO;
import com.jh.forum.cache.service.ICacheService;
import com.jh.forum.cms.service.IForumArticleManageService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 后台管理-帖子操作服务类
 * @version <1> 2019-03-09 lijie： Created.
 */
@Service
@Transactional
public class ForumArticleManageServiceImpl implements IForumArticleManageService {

    @Autowired
    private IForumArticleService forumArticleService;

    @Autowired
    private ICacheService cacheService;


    @Override
    public PageInfo<ArticleVO> findPage(ArticleVO forumArticleVo) {
        PageInfo<ArticleVO> page = forumArticleService.findByPageCms(forumArticleVo);



        return page;
    }


    /**
     * 查询报告/调研/问答/数据分享详情
     * @param articleId 报告/调研/问答/数据分享id
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @Override
    public ResultMessage findById(Integer articleId) {
        //查询报告/调研/问答/数据分享详情,评论涉及分页，需要在页面上单独调用
        ResultMessage result = forumArticleService.getById(articleId);
        return result;
    }

    /**
     * 删除报告/调研/问答/数据分享详情
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @Override
    public ResultMessage deleteArticle(ArticleVO articleVO) {
        for(Integer id:articleVO.getArticleIds()){
            articleVO.setArticleId(id);
            forumArticleService.deleteArticle(articleVO);
        }
        return ResultMessage.success();
    }

    /**
     * 删除报告/调研/问答/数据分享详情
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @Override
    public ResultMessage update(ArticleVO articleVO) {
        return forumArticleService.update(articleVO);
    }

}
