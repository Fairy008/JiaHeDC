package com.jh.forum.cache;

import com.jh.forum.cache.base.ForumIdTransformUtils;
import com.jh.forum.cache.service.ICacheService;
import com.jh.forum.cache.vo.CommentCacheVO;
import com.jh.forum.cache.vo.ForumCacheVO;
import com.jh.util.CacheUtil;
import com.jh.util.JsonUtils;
import com.jh.util.RedisUtil;
import com.jh.vo.ResultMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 社区缓存测试类
 * @param 
 * @return 
 * @version <1> 2019/3/6 16:51 zhangshen:Created.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ICacheServiceTest {

    @Autowired
    private ICacheService cacheService;

    @Test
    public void queryFollowCommentNumByArticleId(){
        ResultMessage rm = cacheService.queryFollowCommentNumByArticleId(1);
        System.out.println(JsonUtils.objectToJson(rm));

        ResultMessage rm2 = cacheService.queryFollowCommentNumByArticleId(12);
        System.out.println(JsonUtils.objectToJson(rm2));
    }

    @Test
    public void querySecondCommentNumByCommentId(){
        ResultMessage rm = cacheService.querySecondCommentNumByCommentId(1);
        System.out.println(JsonUtils.objectToJson(rm));

        ResultMessage rm2 = cacheService.querySecondCommentNumByCommentId(12);
        System.out.println(JsonUtils.objectToJson(rm2));
    }

    @Test
    public void setFollowCommentNumByArticleId(){
        ResultMessage rm = cacheService.setFollowCommentNumByArticleId(1);
        System.out.println(JsonUtils.objectToJson(rm));
        ForumCacheVO vo = (ForumCacheVO) RedisUtil.getJsonObj(CacheUtil.follow_firstComment_object_key + 1, ForumCacheVO.class);
        System.out.println(JsonUtils.objectToJson(vo));

        ResultMessage rm2 = cacheService.setFollowCommentNumByArticleId(12);
        System.out.println(JsonUtils.objectToJson(rm2));
        ForumCacheVO vo2 = (ForumCacheVO) RedisUtil.getJsonObj(CacheUtil.follow_firstComment_object_key + 12, ForumCacheVO.class);
        System.out.println(JsonUtils.objectToJson(vo2));
    }

    @Test
    public void setSecondCommentNumByCommentId(){
        ResultMessage rm = cacheService.setSecondCommentNumByCommentId(1);
        System.out.println(JsonUtils.objectToJson(rm));
        CommentCacheVO vo = (CommentCacheVO) RedisUtil.getJsonObj(CacheUtil.secondComment_object_key + 1, CommentCacheVO.class);
        System.out.println(JsonUtils.objectToJson(vo));

        ResultMessage rm2 = cacheService.setSecondCommentNumByCommentId(12);
        System.out.println(JsonUtils.objectToJson(rm2));
        CommentCacheVO vo2 = (CommentCacheVO) RedisUtil.getJsonObj(CacheUtil.secondComment_object_key + 12, CommentCacheVO.class);
        System.out.println(JsonUtils.objectToJson(vo2));
    }

    @Test
    public void deleteCacheByByArticleId(){
        Integer articleId = 1;
        ResultMessage rm1 = cacheService.queryFollowCommentNumByArticleId(articleId);
        System.out.println(JsonUtils.objectToJson(rm1));

        ResultMessage rm2 = cacheService.querySecondCommentNumByCommentId(articleId);
        System.out.println(JsonUtils.objectToJson(rm2));

        ResultMessage rm3 = cacheService.deleteCacheByByArticleId(articleId);
        System.out.println(JsonUtils.objectToJson(rm3));
    }

    @Test
    public void test(){
        ForumCacheVO f = new ForumCacheVO();
        f.setArticleId(1);
        ForumIdTransformUtils.forumIdTransForObj(f);
        System.out.println(f.getFollowNum() + "------" + f.getCommentNum());

        CommentCacheVO c = new CommentCacheVO();
        c.setArticleId(1);
        ForumIdTransformUtils.forumIdTransForObj(c);
        System.out.println("------" + c.getCommentNum());
    }
}
