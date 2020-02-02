package com.jh.forum.bbs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.base.service.impl.BaseServiceImpl;
import com.jh.forum.bbs.constant.ForumConstant;
import com.jh.forum.bbs.entity.ForumComment;
import com.jh.forum.bbs.mapping.IForumCommentMapper;
import com.jh.forum.bbs.service.IForumCommentService;
import com.jh.forum.bbs.vo.CommentVo;
import com.jh.forum.cache.base.ForumIdTransform;
import com.jh.forum.cache.base.ForumIdTransformUtils;
import com.jh.forum.cache.service.ICacheService;
import com.jh.util.cache.IdTransformUtils;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Description:  用户评论实现类
 * @version<1> 2019-3-5 lcw :Created.
 */
@Service
@Transactional
public class ForumCommentServiceImpl extends BaseServiceImpl<ForumComment, ForumComment,Integer> implements IForumCommentService {

    @Autowired
    private IForumCommentMapper forumCommentMapper;

    @Autowired
    private ICacheService cacheService;

    /**
     * @Description:    继承基础接口的增删改查功能
     * @version<1> 2019-3-5 lcw :Created.
     */
    @Override
    protected IBaseMapper<ForumComment, ForumComment, Integer> getDao() {
        return forumCommentMapper;
    }


    @Override
    public ResultMessage addFirstComment(ForumComment forumComment) {

        forumComment.setLevel(ForumConstant.LEVEL_FIRST);
        ResultMessage checkData = CommentVo.checkFirstComment(forumComment); //字段校验
        if(checkData.isFlag()){
            forumCommentMapper.save(forumComment);
        }
        cacheService.setFollowCommentNumByArticleId(forumComment.getArticleId());

        return ResultMessage.success("评论成功");
    }

    @Override
    public ResultMessage addCommentInFirstCommentId(ForumComment forumComment) {

        ResultMessage checkData = CommentVo.checkCommentInFirstCommentId(forumComment);
        forumComment.setLevel(ForumConstant.LEVEL_SECOND);
        if(checkData.isFlag()){
            forumCommentMapper.save(forumComment);
        }else{
            return checkData;
        }
        cacheService.setSecondCommentNumByCommentId(forumComment.getArticleId());
        return ResultMessage.success("评论成功");
    }

    @Override
    public PageInfo<CommentVo> findCommentsByPage(CommentVo commentVo) {
        PageHelper.startPage(commentVo.getPage(), commentVo.getRows());
        List<CommentVo> list = forumCommentMapper.findCommentsByPage(commentVo);
        ForumIdTransformUtils.idTransForList(list);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<CommentVo>(list);
    }

    @Override
    public ResultMessage findCommentsByFirstCommentId(Integer firstCommentId) {

        if(firstCommentId == null){
            return ResultMessage.fail("暂无相关评论");
        }

        List<CommentVo> list = forumCommentMapper.findCommentsByFirstCommentId(firstCommentId);

        return ResultMessage.success(list);
    }

    @Override
    public ResultMessage deleteComment(CommentVo CommentVo) {
        if(CommentVo == null){
            return ResultMessage.fail("请选择相应的评论");
        }

        forumCommentMapper.deleteByCommentId(CommentVo.getCommentId());

        cacheService.setFollowCommentNumByArticleId(CommentVo.getArticleId());
        return ResultMessage.success("评论删除成功");
    }

    @Override
    public ResultMessage deleteCommentByArticleId(Integer articleId) {

        if(articleId == null){
            return  ResultMessage.fail("帖子不存在，无法删除评论消息");
        }

        forumCommentMapper.deleteCommentByArticleId(articleId);

        return ResultMessage.success("评论删除成功");
    }
}
