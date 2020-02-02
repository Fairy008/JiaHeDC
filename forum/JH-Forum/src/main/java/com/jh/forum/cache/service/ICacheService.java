package com.jh.forum.cache.service;

import com.jh.vo.ResultMessage;

/**
 * 关注数、点赞数、评论数缓存接口
 * @param
 * @return
 * @version <1> 2019/3/6 10:33 zhangshen:Created.
 */
public interface ICacheService {

    /**
     * 启动服务时，加载社区缓存
     * 1.加载点赞数、一级评论数缓存
     * 2.加载二级评论数缓存
     * @param
     * @return void
     * @version <1> 2019/3/6 10:37 zhangshen:Created.
     */
    void initAllDataCache();

    /**
     * 初始化点赞数、一级评论数缓存
     * @param
     * @return void
     * @version <1> 2019/3/6 10:35 zhangshen:Created.
     */
    void initFollowCommentNumCache();

    /**
     * 初始化二级评论数缓存
     * @param
     * @return
     * @version <1> 2019/3/6 11:42 zhangshen:Created.
     */
    void initSecondCommentNumCache();

    /**
     * 根据帖子ID查询缓存点赞数、一级评论数
     * @param articleId 帖子ID
     * @return ResultMessage
     * @version <1> 2019/3/6 13:05 zhangshen:Created.
     */
    ResultMessage queryFollowCommentNumByArticleId(Integer articleId);

    /**
     * 根据评论ID查询对应的二级评论数
     * @param commentId 评论ID
     * @return ResultMessage
     * @version <1> 2019/3/6 13:11 zhangshen:Created.
     */
    ResultMessage querySecondCommentNumByCommentId(Integer commentId);

    /**
     * 根据帖子ID重新设置对应的缓存点赞数、一级评论数
     * @param articleId 帖子ID
     * @return ResultMessage
     * @version <1> 2019/3/6 13:05 zhangshen:Created.
     */
    ResultMessage setFollowCommentNumByArticleId(Integer articleId);

    /**
     * 根据评论ID重新设置对应的二级评论数
     * @param commentId 评论ID
     * @return ResultMessage
     * @version <1> 2019/3/6 13:11 zhangshen:Created.
     */
    ResultMessage setSecondCommentNumByCommentId(Integer commentId);

    /**
     * 根据帖子ID删除对应的缓存数
     * 1.删除点赞数、一级评论数的缓存
     * 2.删除二级评论数的缓存
     * @param articleId 帖子ID
     * @return ResultMessage
     * @version <1> 2019/3/6 20:19 zhangshen:Created.
     */
    ResultMessage deleteCacheByByArticleId(Integer articleId);
}
