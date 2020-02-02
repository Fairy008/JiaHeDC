package com.jh.forum.cache.service.impl;

import com.jh.enums.CacheTypeEnum;
import com.jh.forum.cache.mapping.ICacheMapper;
import com.jh.forum.cache.service.ICacheService;
import com.jh.forum.cache.vo.CommentCacheVO;
import com.jh.forum.cache.vo.ForumCacheVO;
import com.jh.util.CacheUtil;
import com.jh.util.JsonUtils;
import com.jh.util.PropertyUtil;
import com.jh.util.RedisUtil;
import com.jh.vo.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 点赞数、一级评论数、二级评论数缓存实现类
 * @version <1> 2019/3/6 10:34 zhangshen:Created.
 */
@Service
@Transactional
public class CacheServiceImpl implements ICacheService {

    public static final Logger logger= LoggerFactory.getLogger(CacheServiceImpl.class);

    @Autowired
    private ICacheMapper cacheMapper;

    /**
     * 缓存过期时间 永不过期
     */
    Integer expireSecond = Integer.valueOf(PropertyUtil.getPropertiesForConfig("Cache_Expire"));

    /**
     * 初始化社区服务所有缓存
     * @param
     * @return void
     * @version <1> 2019/3/6 10:37 zhangshen:Created.
     */
    @Override
    public void initAllDataCache() {
        initFollowCommentNumCache();
        initSecondCommentNumCache();
    }

    /**
     * 初始化点赞数、一级评论数缓存
     * @param
     * @return void
     * @version <1> 2019/3/6 10:35 zhangshen:Created.
     */
    @Override
    public void initFollowCommentNumCache() {
        //long startTime = new Date().getTime();
        //logger.info("初始化点赞数、一级评论数缓存开始》》》》》》》》》》》》》》》》》！");
        //1.删除原有点赞数、一级评论数缓存
        Set<String > set = RedisUtil.getJedis().keys(CacheTypeEnum.CACHE_TYPE_FOLLOW_FIRSTCOMMENT.getType()+"*");
        for(String str:set){
            RedisUtil.del(str);
        }
        //logger.info("删除所有点赞数、一级评论数缓存成功！删除点赞数、一级评论数缓存个数：{}",(set!=null?set.size():0));

        //2.赋值缓存
        List<ForumCacheVO> list = cacheMapper.findFollowCommentNum();
        if (list != null && list.size() > 0) {
            for (ForumCacheVO forumCacheVO : list) {
                String key = CacheUtil.follow_firstComment_object_key + forumCacheVO.getArticleId().toString();
                RedisUtil.setJsonObj(key, forumCacheVO, expireSecond);
            }
            //logger.info("点赞数、一级评论数缓存成功！缓存点赞数、一级评论数：{}",new Integer [] {(list!=null?list.size():0)});
        }
        //long endTime=new Date().getTime();
        //logger.info("点赞数、一级评论数缓存结束》》》》》》》》》》》》》》》》》！耗时："+(endTime-startTime)/1000.0+"秒");
    }

    /**
     * 初始化二级评论数缓存
     * @param
     * @return void
     * @version <1> 2019/3/6 11:42 zhangshen:Created.
     */
    @Override
    public void initSecondCommentNumCache() {
        //long startTime = new Date().getTime();
        //logger.info("初始化二级评论数缓存开始》》》》》》》》》》》》》》》》》！");
        //1.删除原有点赞数、一级评论数缓存
        Set<String > set = RedisUtil.getJedis().keys(CacheTypeEnum.CACHE_TYPE_SECONDCOMMENT.getType()+"*");
        for(String str:set){
            RedisUtil.del(str);
        }
        //logger.info("删除所有二级评论数缓存成功！删除二级评论数缓存个数：{}",(set!=null?set.size():0));

        //2.赋值缓存
        List<CommentCacheVO> list = cacheMapper.findSecondCommentNum();
        if (list != null && list.size() > 0) {
            for (CommentCacheVO commentCacheVO : list) {
                String key = CacheUtil.secondComment_object_key + commentCacheVO.getCommentId().toString();
                RedisUtil.setJsonObj(key, commentCacheVO, expireSecond);
            }
            //logger.info("二级评论数缓存成功！缓存二级评论数：{}",new Integer [] {(list!=null?list.size():0)});
        }
        //long endTime=new Date().getTime();
        //logger.info("二级评论数缓存结束》》》》》》》》》》》》》》》》》！耗时："+(endTime-startTime)/1000.0+"秒");
    }

    /**
     * 根据帖子ID查询缓存点赞数、一级评论数
     * @param articleId 帖子ID
     * @return ResultMessage
     * @version <1> 2019/3/6 13:05 zhangshen:Created.
     */
    @Override
    public ResultMessage queryFollowCommentNumByArticleId(Integer articleId) {
        ForumCacheVO forumCacheVO = new ForumCacheVO();
        ForumCacheVO vo = (ForumCacheVO) RedisUtil.getJsonObj(CacheUtil.follow_firstComment_object_key + articleId, ForumCacheVO.class);
        if(vo != null){
            forumCacheVO = vo;
            //logger.info("帖子ID articleId:{},从缓存取值点赞数、一级评论数对象成功",articleId);
        }else {
            forumCacheVO = cacheMapper.queryFollowCommentNumByArticleId(articleId);
            if(null != forumCacheVO){
                String key = CacheUtil.follow_firstComment_object_key + forumCacheVO.getArticleId().toString();
                RedisUtil.setJsonObj(key, forumCacheVO, expireSecond);
                //logger.info("帖子ID articleId:{},查询数据库点赞数、一级评论数，并设置缓存成功",articleId);
            }else{
                //logger.error("帖子ID articleId:{}-查询数据库点赞数、一级评论数失败",articleId);
            }
        }
        return ResultMessage.success(forumCacheVO);
    }

    /**
     * 根据评论ID查询对应的二级评论数
     * @param commentId 评论ID
     * @return ResultMessage
     * @version <1> 2019/3/6 13:11 zhangshen:Created.
     */
    @Override
    public ResultMessage querySecondCommentNumByCommentId(Integer commentId) {
        CommentCacheVO commentCacheVO = new CommentCacheVO();
        CommentCacheVO vo = (CommentCacheVO) RedisUtil.getJsonObj(CacheUtil.secondComment_object_key + commentId, CommentCacheVO.class);
        if(vo != null){
            commentCacheVO = vo;
            //logger.info("评论ID commentId:{},从缓存取值二级评论数对象成功", commentId);
        }else {
            commentCacheVO = cacheMapper.querySecondCommentNumByCommentId(commentId);
            if(null != commentCacheVO){
                String key = CacheUtil.secondComment_object_key + commentCacheVO.getCommentId().toString();
                RedisUtil.setJsonObj(key, commentCacheVO, expireSecond);
                //logger.info("评论ID commentId:{},查询数据库二级评论数成功",commentId);
            }else{
                //logger.error("评论ID commentId:{}-查询数据库二级评论数失败",commentId);
            }
        }
        return ResultMessage.success(commentCacheVO);
    }

    /**
     * 根据帖子ID重新设置对应的缓存点赞数、一级评论数
     * @param articleId 帖子ID
     * @return ResultMessage
     * @version <1> 2019/3/6 13:05 zhangshen:Created.
     */
    @Override
    public ResultMessage setFollowCommentNumByArticleId(Integer articleId) {
        RedisUtil.del(CacheUtil.follow_firstComment_object_key + articleId);
        ForumCacheVO forumCacheVO = cacheMapper.queryFollowCommentNumByArticleId(articleId);
        if (null != forumCacheVO) {
            String key = CacheUtil.follow_firstComment_object_key + forumCacheVO.getArticleId().toString();
            RedisUtil.setJsonObj(key, forumCacheVO, expireSecond);
            //logger.info("帖子ID articleId:{},设置数据库点赞数、一级评论数缓存成功", articleId);
        }
        return ResultMessage.success(forumCacheVO);
    }

    /**
     * 根据评论ID重新设置对应的二级评论数
     * @param commentId 评论ID
     * @return ResultMessage
     * @version <1> 2019/3/6 13:11 zhangshen:Created.
     */
    @Override
    public ResultMessage setSecondCommentNumByCommentId(Integer commentId) {
        RedisUtil.del(CacheUtil.secondComment_object_key + commentId);
        CommentCacheVO commentCacheVO = cacheMapper.querySecondCommentNumByCommentId(commentId);
        if (null != commentCacheVO) {
            String key = CacheUtil.secondComment_object_key + commentCacheVO.getCommentId().toString();
            RedisUtil.setJsonObj(key, commentCacheVO, expireSecond);
            //logger.info("评论ID commentId:{},设置数据库二级评论数成功", commentId);
        }
        return ResultMessage.success(commentCacheVO);
    }

    /**
     * 根据帖子ID删除对应的缓存数
     * 1.删除点赞数、一级评论数的缓存：根据帖子ID删除对应的点赞数、一级评论数的缓存（一条）
     * 2.删除二级评论数的缓存：根据帖子ID查询对应帖子的二级评论的父ID，group by过滤查询，再循环删除（多条）
     * @param articleId 帖子ID
     * @return ResultMessage
     * @version <1> 2019/3/6 20:19 zhangshen:Created.
     */
    @Override
    public ResultMessage deleteCacheByByArticleId(Integer articleId) {
        try {
            //删除点赞数、一级评论数的缓存
            RedisUtil.del(CacheUtil.follow_firstComment_object_key + articleId);
            List<CommentCacheVO> list = cacheMapper.findSecondCommentNumByArticleId(articleId);
            if (list != null && list.size() > 0) {
                for (CommentCacheVO vo : list) {
                    RedisUtil.del(CacheUtil.secondComment_object_key + vo.getCommentId());
                }
            }
            //logger.info("根据帖子ID删除对应的缓存数成功");
        } catch (Exception e) {
            //logger.error("根据帖子ID删除对应的缓存数失败", e.toString());
            return ResultMessage.fail("根据帖子ID删除对应的缓存数失败");
        }
        return ResultMessage.success("根据帖子ID删除对应的缓存数成功");
    }
}
