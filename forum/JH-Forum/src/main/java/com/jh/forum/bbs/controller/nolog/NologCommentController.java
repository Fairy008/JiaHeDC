package com.jh.forum.bbs.controller.nolog;

import com.github.pagehelper.PageInfo;
import com.jh.forum.bbs.service.IForumCommentService;
import com.jh.forum.bbs.vo.CommentVo;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description：免登录评论控制器
 * @version<1> 2019-03-11 mason : Created.
 */
@Api(value = "用户评论信息",description = "评论接口")
@RestController
@RequestMapping("/nolog/comment")
public class NologCommentController {

    @Autowired
    private IForumCommentService commentService;

    @ApiOperation(value = "查询帖子的一级评论列表",notes = "查询帖子的一级评论列表")
    @ApiImplicitParam(name = "commentVo",value = "评论列表",required = true,dataType = "CommentVo")
    @PostMapping("/queryCommentsByPage")
    public PageInfo<CommentVo> queryCommentsByPage(@RequestBody CommentVo commentVo){

        PageInfo<CommentVo> pageInfos = commentService.findCommentsByPage(commentVo);

        return pageInfos;
    }


    @ApiOperation(value = "查看一级评论下的所有回复信息",notes = "查看一级评论下的所有子评论信息")
    @ApiImplicitParam(name = "firstCommentId",value = "一级评论ID",required = true,dataType = "Integer", paramType = "query")
    @GetMapping("/findCommentsByFirstCommentId")
    public ResultMessage findCommentsByFirstCommentId(@RequestParam Integer firstCommentId){

        ResultMessage result = commentService.findCommentsByFirstCommentId(firstCommentId);
        return result;

    }

}
