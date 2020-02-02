package com.jh.forum.bbs.service;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.service.IBaseService;
import com.jh.forum.bbs.entity.ForumArticle;
import com.jh.forum.bbs.vo.ArticleVO;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
* @Description:    论坛文章接口
* @Author:         mason
* @CreateDate:     2019/3/4 15:34
* @Version:        1.0
*/
public interface IForumArticleService extends IBaseService<ArticleVO, ArticleVO,Integer> {


    /**
     * 首页根据关键词、标签、报告类型组合查询
     * @param forumArticleVo
     *      搜索关键词、标签、报告类型混合查询
     * @return
     */
    PageInfo<ArticleVO> getListByCombination(ArticleVO forumArticleVo);




    /**
     * 查询最热报告
     * 若用户已登录，则需要关联查询是否已收藏的状态
     * @param articleType
     * @param creator
     * @return
     */
    ResultMessage getHotArticleList(Integer articleType, Integer creator);

    /**
     * 查询最新调研
     * @param articleType
     *      报告类型
     * @return
     */
    ResultMessage getNewArticleList(Integer articleType, Integer creator);

    /**
     * 查询报告/调研/问答/数据分享详情
     * @param articleId 报告/调研/问答/数据分享id
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    ResultMessage findArticleInfo(Integer articleId, Integer currentAccountId);

    /**
     * 删除报告/调研/问答/数据分享详情
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    ResultMessage deleteArticle(ArticleVO articleVO);

    /**
     * 后台根据关键词、标签、报告类型组合查询
     * @param forumArticleVo
     * @return
     */
    PageInfo<ArticleVO> findByPageCms(ArticleVO forumArticleVo);

    /**
     * 根据帖子ID查询当前登录人的点赞与收藏情况
     * @param articleId
     * @param creator
     * @return
     * @version<1> 2019-03-12 lcw :Created.
     */
    ResultMessage findArticleFollows(Integer articleId, Integer creator);

    /**
     * 我的收藏根据关键词、标签、报告类型组合查询
     * @param forumArticleVo
     *      搜索关键词、标签、报告类型混合查询
     * @return
     */
    PageInfo<ArticleVO> getListByFollower(ArticleVO forumArticleVo);

    /**
     * 新增报告/调研/问答/数据分享详情
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    ResultMessage saveArticle(ArticleVO articleVO);

    /**
     * 编辑报告/调研/问答/数据分享详情
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    ResultMessage updateArticle(ArticleVO articleVO);

    /**
     * 根据articleId查询是否是自己的帖子
     * @param articleId accountId
     * @return ResultMessage
     * @version <1> 2019/3/20 13:32 zhangshen:Created.
     */
    ResultMessage findArticleInfoByAccountId(Integer articleId, Integer accountId);
}
