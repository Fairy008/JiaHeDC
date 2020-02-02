package com.jh.forum.cms.service;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.service.IBaseService;
import com.jh.forum.bbs.vo.ArticleVO;
import com.jh.vo.ResultMessage;

/**
 * 后台管理-帖子操作服务类
 * @version <1> 2019-03-09 lijie： Created.
 */
public interface IForumArticleManageService{


    /**
     * 首页根据关键词、标签、报告类型组合查询
     * @param forumArticleVo
     * @return
     */
    PageInfo<ArticleVO> findPage(ArticleVO forumArticleVo);

    /**
     * 查询报告/调研/问答/数据分享详情
     * @param articleId 报告/调研/问答/数据分享id
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    ResultMessage findById(Integer articleId);

    /**
     * 删除报告/调研/问答/数据分享详情
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    ResultMessage deleteArticle(ArticleVO articleVO);

    /**
     * 修改报告/调研/问答/数据分享详情
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    ResultMessage update(ArticleVO articleVO);

}
