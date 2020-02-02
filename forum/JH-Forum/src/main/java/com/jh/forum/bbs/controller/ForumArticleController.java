package com.jh.forum.bbs.controller;


import com.github.pagehelper.PageInfo;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.Enum.ArticleStatusEnum;
import com.jh.forum.bbs.entity.ForumLabel;
import com.jh.forum.bbs.service.IForumArticleService;
import com.jh.forum.bbs.service.IForumLabelService;
import com.jh.forum.bbs.vo.ArticleVO;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 报告controller
 * @version <1> 2019-03-05 xiayong： Created.
 */
@Api(value = "论坛报告",description = "帖子接口")
@RestController
@RequestMapping("/article")
public class ForumArticleController extends BaseController {

    @Autowired
    private IForumArticleService forumArticleService;

    /**
     * 首页报告复合查询分页列表
     * @param articleVO
     *      查询参数（关键字、标签、报告类型）
     * @return
     */
    @ApiOperation(value = "查询报告列表",notes = "查询报告列表")
    @ApiImplicitParam(name = "articleVO",value = "报告实体",required = true,dataType = "ArticleVO")
    @PostMapping("/findCombinationPage")
    public PageInfo<ArticleVO> findCombinationPage(@RequestBody ArticleVO articleVO){
        articleVO.setCreator(getCurrentAccountId());
        return forumArticleService.getListByCombination(articleVO);
    }

//    /**
//     * 首页查询最热报告、调研、问答
//     * @param articleType
//     *      报告类型
//     * @return
//     */
//    @ApiOperation(value = "查询最热报告、调研、问答",notes = "查询最热报告")
//    @ApiImplicitParam(name = "articleType",value = "报告类型",required = false,dataType = "Integer")
//    @PostMapping("/findHotArticleList")
//    public ResultMessage findHotArticleList(@RequestParam(value = "articleType", required = false)Integer articleType){
//        return forumArticleService.getHotArticleList(articleType);
//    }
//
//    /**
//     * 首页
//     * @param articleType
//     *      报告类型
//     * @return
//     */
//    @ApiOperation(value = "查询最新报告、调研、问答",notes = "查询最新报告、调研、问答")
//    @ApiImplicitParam(name = "articleType",value = "报告类型",required = false,dataType = "Integer")
//    @PostMapping("/findNewArticleList")
//    public ResultMessage findNewArticleList(@RequestParam(value = "articleType", required = false)Integer articleType){
//        return forumArticleService.getNewArticleList(articleType);
//    }

    /**
     * 查询报告/调研/问答/数据分享详情
     * @param articleId 报告/调研/问答/数据分享id
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @ApiOperation(value = "查询报告/调研/问答/数据分享详情",notes = "查询报告/调研/问答/数据分享详情")
    @ApiImplicitParam(name = "articleId",value = "报告/调研/问答/数据分享id",required = true,paramType="query",dataType = "Integer")
    @GetMapping("/findArticleInfo")
    public ResultMessage findArticleInfo(@RequestParam Integer articleId){
        Integer currentAccountId = getCurrentAccountId();
        return forumArticleService.findArticleInfo(articleId, currentAccountId);
    }

    /**
     * 新增报告/调研/问答/数据分享详情
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @ApiOperation(value = "新增报告/调研/问答/数据分享详情",notes = "新增报告/调研/问答/数据分享详情")
    @ApiImplicitParam(name = "articleVO",value = "报告实体",required = true,dataType = "ArticleVO")
    @PostMapping("/addArticle")
    public ResultMessage addArticle(@RequestBody ArticleVO articleVO){
        articleVO.setCreator(getCurrentAccountId());
        articleVO.setCreatorName(getCurrentNickName());
        articleVO.setCreateTime(LocalDateTime.now());

        if(StringUtils.isNotBlank(articleVO.getAdminFlag())){

            articleVO.setAuditor(getCurrentAccountId());
            articleVO.setAuditName(getCurrentNickName());
            articleVO.setAuditTime(LocalDateTime.now());

        }

        if(ArticleStatusEnum.ARTICLE_STATUS_PENDING.getId().equals(articleVO.getArticleStatus()) ){
            articleVO.setPublishTime(new Date());
            articleVO.setPublisher(articleVO.getCreator());
        }



        ResultMessage result = ArticleVO.checkAddArticleParam(articleVO);
        if (!result.isFlag()) return result;
        return forumArticleService.saveArticle(articleVO);
    }

    /**
     * 修改报告/调研/问答/数据分享详情
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @ApiOperation(value = "修改报告/调研/问答/数据分享详情",notes = "修改报告/调研/问答/数据分享详情")
    @ApiImplicitParam(name = "articleVO",value = "报告实体",required = true,dataType = "ArticleVO")
    @PostMapping("/editArticle")
    public ResultMessage editArticle(@RequestBody ArticleVO articleVO){

        if(StringUtils.isNotBlank(articleVO.getAdminFlag())){

            articleVO.setAuditor(getCurrentAccountId());
            articleVO.setAuditName(getCurrentNickName());
            articleVO.setAuditTime(LocalDateTime.now());

        }
        if(ArticleStatusEnum.ARTICLE_STATUS_PENDING.getId().equals(articleVO.getArticleStatus())||ArticleStatusEnum.ARTICLE_STATUS_PUBLISHED.getId().equals(articleVO.getArticleStatus()) ){
            articleVO.setPublishTime(new Date());
            articleVO.setPublisher(articleVO.getCreator());
        }
        return forumArticleService.updateArticle(articleVO);
    }


    /**
     * 删除报告/调研/问答/数据分享详情
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @ApiOperation(value = "删除报告/调研/问答/数据分享详情",notes = "删除报告/调研/问答/数据分享详情")
    @ApiImplicitParam(name = "articleVO",value = "报告实体",required = true,dataType = "ArticleVO")
    @PostMapping("/deleteArticle")
    public ResultMessage deleteArticle(@RequestBody ArticleVO articleVO){
        if (null == articleVO || null == articleVO.getArticleId()){
            return ResultMessage.fail();
        }
        return forumArticleService.deleteArticle(articleVO);
    }


    /**
     * 我的收藏报告复合查询分页列表
     * @param articleVO
     *      查询参数（关键字、标签、报告类型）
     * @return
     */
    @ApiOperation(value = "查询报告列表",notes = "查询报告列表")
    @ApiImplicitParam(name = "articleVO",value = "报告实体",required = true,dataType = "ArticleVO")
    @PostMapping("/findListByFollower")
    public PageInfo<ArticleVO> getListByFollower(@RequestBody ArticleVO articleVO){
        return forumArticleService.getListByFollower(articleVO);
    }

    /**
     * 根据articleId查询是否是自己的帖子
     * @param articleId
     * @return ResultMessage
     * @version <1> 2019/3/20 13:32 zhangshen:Created.
     */
    @ApiOperation(value = "查询报告/调研/问答/数据分享详情",notes = "查询报告/调研/问答/数据分享详情")
    @ApiImplicitParam(name = "articleId",value = "报告/调研/问答/数据分享id",required = true,paramType="query",dataType = "Integer")
    @GetMapping("/findArticleInfoByAccountId")
    public ResultMessage findArticleInfoByAccountId(@RequestParam Integer articleId){
        return forumArticleService.findArticleInfoByAccountId(articleId, getCurrentAccountId());
    }
}
