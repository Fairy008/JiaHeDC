package com.jh.forum.bbs.api;

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
 * @Description：移动端评论相关接口
 * @version<1> 2019-04-18 mason : Created.
 */
@Api(value = "移动端评论相关接口",description = "移动端评论相关接口")
@RestController
@RequestMapping("api/comment")
public class AppCommentController extends BaseController {

    @Autowired
    private IForumCommentService commentService;

    @ApiOperation(value = "新增评论",notes = "针对帖子新增评论")
    @ApiImplicitParam(name = "comment",value = "帖子评论实体",required = true,dataType = "ForumComment")
    @PostMapping("/addComment")
    public ResultMessage addComment(@RequestBody ForumComment comment, @RequestParam String phone){
        comment.setCreator(getCurrentAccountIdApp(phone));
        comment.setCreatorName(getCurrentNickNameApp(phone));

        ResultMessage result = commentService.addFirstComment(comment);

        return result;
    }



    @ApiOperation(value = "针对评论进行下级评论",notes = "针对帖子新增评论")
    @ApiImplicitParam(name = "comment",value = "帖子评论实体",required = true,dataType = "ForumComment")
    @PostMapping("/addCommentInFirstCommentId")
    public ResultMessage addCommentInFirstCommentId(@RequestBody  ForumComment forumComment, @RequestParam String phone){

        forumComment.setCreator(getCurrentAccountIdApp(phone));
        forumComment.setCreatorName(getCurrentNickNameApp(phone));

        ResultMessage result = commentService.addCommentInFirstCommentId(forumComment);

        return result;
    }

    @ApiOperation(value = "根据评论VO删除评论",notes = "根据评论VO删除评论")
    @ApiImplicitParam(name = "commentVo",value = "评论ID",required = true,dataType = "CommentVo")
    @PostMapping("/deleteComment")
    public ResultMessage deleteComment(@RequestBody CommentVo commentVo, @RequestParam String phone){
        commentVo.setCreator(getCurrentAccountIdApp(phone));
        ResultMessage result = commentService.deleteComment(commentVo);
        return result;
    }


}
