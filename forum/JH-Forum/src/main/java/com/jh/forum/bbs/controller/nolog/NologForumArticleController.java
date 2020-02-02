package com.jh.forum.bbs.controller.nolog;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.service.IForumArticleService;
import com.jh.forum.bbs.service.IForumFollowService;
import com.jh.forum.bbs.vo.ArticleVO;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 首页的帖子内容
 * @version <1> 2019-03-05 xiayong： Created.
 */
@Api(value = "首页帖子内容接口",description = "首页帖子内容接口")
@RestController
@RequestMapping("/nolog/article")
public class NologForumArticleController  extends BaseController{

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
        return forumArticleService.getListByCombination(articleVO);
    }

    /**
     * 首页查询最热报告、调研、问答
     *  不论用户是否登录，都调用该接口查询首页的最热报告
     *  若用户已登录，则关联查询已关联的帖子
     * @param articleType
     *      报告类型
     * @return
     */
    @ApiOperation(value = "查询最热报告、调研、问答",notes = "查询最热报告")
    @ApiImplicitParam(name = "articleVO",value = "报告实体",required = false,dataType = "ArticleVO")
    @PostMapping("/findHotArticleList")
    public ResultMessage findHotArticleList(@RequestParam(value = "articleType", required = false)Integer articleType){
        Integer creator = getCurrentAccountId();  //当前登录人
        return forumArticleService.getHotArticleList(articleType, creator);
    }

    /**
     * 首页
     * @param articleType
     *      报告类型
     * @return
     */
    @ApiOperation(value = "查询最新报告、调研、问答",notes = "查询最新报告、调研、问答")
    @ApiImplicitParam(name = "articleType",value = "报告实体",required = true,dataType = "Integer")
    @PostMapping("/findNewArticleList")
    public ResultMessage findNewArticleList(@RequestParam(value = "articleType", required = false)Integer articleType){
        Integer creator = getCurrentAccountId(); //当前登陆人
        return forumArticleService.getNewArticleList(articleType, creator);
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
        return forumArticleService.save(articleVO);
    }

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

        Integer currentAccountId = getCurrentAccountId(); //当前登录人

        System.out.println(currentAccountId);
        return forumArticleService.findArticleInfo(articleId, currentAccountId);
    }

    @ApiOperation(value = "根据帖子ID查询当前登录人的点赞与收藏情况",notes = "根据帖子ID查询当前登录人的点赞与收藏情况")
    @ApiImplicitParam(name = "articleId",value = "报告/调研/问答/数据分享id",required = true,paramType="query",dataType = "Integer")
    @GetMapping("/findArticleFollows")
    public ResultMessage findArticleFollows(@RequestParam Integer articleId){

        Integer creator = getCurrentAccountId();
        return forumArticleService.findArticleFollows(articleId, creator);

    }
}
