package com.jh.forum.bbs.vo;

import com.jh.forum.bbs.entity.ForumComment;
import com.jh.forum.cache.base.ForumIdTransform;
import com.jh.util.CacheUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description:
 * @version<1> 2019-03-05 lcw :Created.
 */
public class CommentVo extends ForumComment {

    //二级评论的数量
    @ForumIdTransform(type = CacheUtil.CACHE_SECONDCOMMENT, propName = "commentId", transType = "")
    private Integer secondCommentCount;

    /**
     * 第一级评论字段校验
     * @param forumComment
     * @return
     */
    public static ResultMessage checkFirstComment(ForumComment forumComment) {
        ResultMessage result = ResultMessage.fail();

        if(forumComment.getArticleId() == null){
            result.setMsg("帖子ID不能为空");
            return result;
        }

        if(StringUtils.isBlank(forumComment.getContent())){
            result.setMsg("评论内容不能为空");
            return result;
        }

        result = ResultMessage.success();
        return result;
    }



    /**
     * 第二级评论字段校验
     * @param forumComment
     * @return
     */
    public static ResultMessage checkCommentInFirstCommentId(ForumComment forumComment) {
        ResultMessage result = checkFirstComment(forumComment);

        if(forumComment.getFirstCommentId() == null){
            result = ResultMessage.fail("上级评论不能为空");
            return result;
        }

        return result;
    }

    public Integer getSecondCommentCount() {
        return secondCommentCount;
    }

    public void setSecondCommentCount(Integer secondCommentCount) {
        this.secondCommentCount = secondCommentCount;
    }
}
