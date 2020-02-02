package com.jh.forum.cms.controller;


import com.github.pagehelper.PageInfo;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.vo.ArticleVO;
import com.jh.forum.cms.service.IForumArticleManageService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 后台管理-帖子操作接口
 * @version <1> 2019-03-09 lijie： Created.
 */
@Api(value = "后台帖子操作",description = "后台帖子操作接口")
@RestController
@RequestMapping("/articleManage")
public class ForumArticleManageController extends BaseController {

    @Autowired
    private IForumArticleManageService forumArticleManageService;

    /**
     * 帖子列表查询
     * @param articleVO
     *      查询参数（标签、报告类型）
     * @return
     */
    @ApiOperation(value = "查询报告列表",notes = "查询报告列表")
    @ApiImplicitParam(name = "articleVO",value = "报告实体",required = true,dataType = "ArticleVO")
    @PostMapping("/findPage")
    public PageInfo<ArticleVO> findPage(ArticleVO articleVO){
        return forumArticleManageService.findPage(articleVO);
    }


    /**
     * 查询帖子详情
     * @param articleId 帖子id
     * @return ResultMessage
     * @version <1> 2019/3/5 lijie:Created.
     */
    @ApiOperation(value = "查询帖子详情",notes = "查询帖子详情")
    @ApiImplicitParam(name = "articleId",value = "帖子id",required = true,paramType="query",dataType = "Integer")
    @GetMapping("/findById")
    public ResultMessage findById(@RequestParam Integer articleId){
        return forumArticleManageService.findById(articleId);
    }

    /**
     * 审核帖子
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 lijie:Created.
     */
    @ApiOperation(value = "审核帖子",notes = "审核帖子")
    @ApiImplicitParam(name = "articleVO",value = "报告实体",required = true,dataType = "ArticleVO")
    @PostMapping("/auditArticle")
    public ResultMessage auditArticle(@RequestBody ArticleVO articleVO){
        articleVO.setModifier(getCurrentAccountId());
        articleVO.setModifierName(getCurrentNickName());
        articleVO.setModifyTime(LocalDateTime.now());
        articleVO.setAuditTime(LocalDateTime.now());
        articleVO.setAuditor(getCurrentAccountId());
        return forumArticleManageService.update(articleVO);
    }

    /**
     * 标记帖子为最新
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 lijie:Created.
     */
    @ApiOperation(value = "标记帖子为最新",notes = "标记帖子为最新")
    @ApiImplicitParam(name = "articleVO",value = "报告实体",required = true,dataType = "ArticleVO")
    @PostMapping("/signNewArticle")
    public ResultMessage signNewArticle(@RequestBody ArticleVO articleVO){
        return forumArticleManageService.update(articleVO);
    }

    /**
     * 标记帖子最热
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 lijie:Created.
     */
    @ApiOperation(value = "标记帖子最热",notes = "标记帖子最热")
    @ApiImplicitParam(name = "articleVO",value = "报告实体",required = true,dataType = "ArticleVO")
    @PostMapping("/signHotArticle")
    public ResultMessage signHotArticle(@RequestBody ArticleVO articleVO){
        return forumArticleManageService.update(articleVO);
    }


    /**
     * 删除帖子
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 lijie:Created.
     */
    @ApiOperation(value = "删除帖子",notes = "删除帖子")
    @ApiImplicitParam(name = "articleVO",value = "报告实体",required = true,dataType = "ArticleVO")
    @PostMapping("/deleteArticle")
    public ResultMessage deleteArticle(@RequestBody ArticleVO articleVO){
        if (null == articleVO || null == articleVO.getArticleIds()){
            return ResultMessage.fail();
        }
        return forumArticleManageService.deleteArticle(articleVO);
    }




}
