package com.jh.forum.bbs.controller;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.entity.ForumComment;
import com.jh.forum.bbs.service.IForumCommentService;
import com.jh.forum.bbs.vo.CommentVo;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:  用户评论控制器
 * @version<1> 2019-3-5 lcw :Created.
 */
@Api(value = "用户评论信息",description = "评论接口")
@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Autowired
    private IForumCommentService commentService;

    @ApiOperation(value = "新增评论",notes = "针对帖子新增评论")
    @ApiImplicitParam(name = "comment",value = "帖子评论实体",required = true,dataType = "ForumComment")
    @PostMapping("/addComment")
    public ResultMessage addComment(@RequestBody  ForumComment comment){

        comment.setCreator(getCurrentAccountId());
        comment.setCreatorName(getCurrentNickName());

        ResultMessage result = commentService.addFirstComment(comment);

        return result;
    }



    @ApiOperation(value = "针对评论进行下级评论",notes = "针对帖子新增评论")
    @ApiImplicitParam(name = "comment",value = "帖子评论实体",required = true,dataType = "ForumComment")
    @PostMapping("/addCommentInFirstCommentId")
    public ResultMessage addCommentInFirstCommentId(@RequestBody  ForumComment forumComment){

        forumComment.setCreator(getCurrentAccountId());
        forumComment.setCreatorName(getCurrentNickName());

        ResultMessage result = commentService.addCommentInFirstCommentId(forumComment);

        return result;
    }


    @ApiOperation(value = "查询帖子的一级评论列表",notes = "查询帖子的一级评论列表")
    @ApiImplicitParam(name = "commentVo",value = "评论列表",required = true,dataType = "CommentVo")
    @PostMapping("/queryCommentsByPage")
    public PageInfo<CommentVo> queryCommentsByPage(@RequestBody  CommentVo commentVo){

        PageInfo<CommentVo> pageInfos = commentService.findCommentsByPage(commentVo);

        return pageInfos;
    }


    @ApiOperation(value = "查看一级评论下的所有回复信息",notes = "查看一级评论下的所有子评论信息")
    @ApiImplicitParam(name = "firstCommentId",value = "一级评论ID",required = true,dataType = "Integer", paramType = "query")
    @PostMapping("/findCommentsByFirstCommentId")
    public ResultMessage findCommentsByFirstCommentId(@RequestParam Integer firstCommentId){

        ResultMessage result = commentService.findCommentsByFirstCommentId(firstCommentId);
        return result;

    }


    @ApiOperation(value = "根据评论VO删除评论",notes = "根据评论VO删除评论")
    @ApiImplicitParam(name = "commentVo",value = "评论ID",required = true,dataType = "CommentVo")
    @PostMapping("/deleteComment")
    public ResultMessage deleteComment(@RequestBody CommentVo commentVo){
        ResultMessage result = commentService.deleteComment(commentVo);
        return result;
    }


    @ApiOperation(value = "根据帖子ID删除评论",notes = "根据帖子ID删除评论")
    @ApiImplicitParam(name = "articleId",value = "帖子ID",required = true,dataType = "Integer", paramType = "query")
    @PostMapping("/deleteCommentByArticleId")
    public ResultMessage deleteCommentByArticleId(@RequestParam Integer articleId){

        ResultMessage result = commentService.deleteCommentByArticleId(articleId);
        return result;
    }


}
