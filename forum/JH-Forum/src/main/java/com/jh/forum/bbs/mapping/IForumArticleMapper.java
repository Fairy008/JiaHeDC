package com.jh.forum.bbs.mapping;

import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.bbs.entity.ForumArticle;
import com.jh.forum.bbs.vo.ArticleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 论坛文章数据查询接口
 */
@Mapper
public interface IForumArticleMapper extends IBaseMapper<ArticleVO,ArticleVO,Integer> {

    /**
     * 查询最热报告
     * @param articleType
     *      报告类型
     * @return
     */
    List<ArticleVO> getHotArticleList(@Param("articleType")Integer articleType, @Param("creator") Integer creator);


    /**
     * 查询最新调研
     * @param articleType
     *      报告类型
     * @return
     */
    List<ArticleVO> getNewArticleList(@Param("articleType")Integer articleType, @Param("creator") Integer creator);


    /**
     * 首页分页查询
     * @param articleVO
     * @return
     */
    List<ArticleVO> getListByCombination(ArticleVO articleVO);

    /**
     * 后台分页查询
     * @param articleVO
     * @return
     */
    List<ArticleVO> findByPageCms (ArticleVO articleVO);


    /**
     * 根据帖子ID查询当前等路人的点赞与收藏
     * @param articleId
     * @param creator
     * @return
     */
    List<Integer> findArticleFollows(@Param("articleId") Integer articleId,@Param("creator") Integer creator);

    /**
     * 我的收藏分页查询
     * @param articleVO
     * @return
     */
    List<ArticleVO> getListByFollower(ArticleVO articleVO);

    /**
     * 根据articleId查询是否是自己的帖子
     * @param articleId accountId
     * @return ResultMessage
     * @version <1> 2019/3/20 13:32 zhangshen:Created.
     */
    ArticleVO findArticleInfoByAccountId(@Param("articleId") Integer articleId,@Param("creator") Integer creator);
}