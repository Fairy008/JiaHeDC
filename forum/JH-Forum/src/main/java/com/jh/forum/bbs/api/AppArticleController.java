package com.jh.forum.bbs.api;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.Enum.ArticleStatusEnum;
import com.jh.forum.bbs.service.IForumArticleService;
import com.jh.forum.bbs.vo.ArticleVO;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;


/**
 * 移动端报告相关接口
 * @version <1> 2019-03-05 xiayong： Created.
 */
@Api(value = "移动端报告相关接口",description = "移动端报告相关接口")
@RestController
@RequestMapping("api/article")
public class AppArticleController extends BaseController {

    @Autowired
    private IForumArticleService forumArticleService;

    /**
     * 移动端个人主页报告复合查询分页列表
     * @param articleVO
     *      查询参数（关键字、标签、报告类型）
     * @return
     */
    @ApiOperation(value = "查询报告列表",notes = "查询报告列表")
    @ApiImplicitParam(name = "articleVO",value = "报告实体",required = true,dataType = "ArticleVO")
    @PostMapping("/findCombinationPage")
    public PageInfo<ArticleVO> findCombinationPage(@RequestBody ArticleVO articleVO, @RequestParam String phone){
        articleVO.setCreator(getCurrentAccountIdApp(phone));
        return forumArticleService.getListByCombination(articleVO);
    }

    /**
     * 移动端我的收藏报告复合查询分页列表
     * @param articleVO
     *      查询参数（关键字、标签、报告类型）
     * @return
     */
    @ApiOperation(value = "查询收藏报告列表",notes = "查询收藏报告列表")
    @ApiImplicitParam(name = "articleVO",value = "报告实体",required = true,dataType = "ArticleVO")
    @PostMapping("/findListByFollower")
    public PageInfo<ArticleVO> getListByFollower(@RequestBody ArticleVO articleVO, @RequestParam String phone){
        articleVO.setFollowerId(getCurrentAccountIdApp(phone));
        return forumArticleService.getListByFollower(articleVO);
    }

    /**
     * 移动端新增调研/问答
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/4/22 zhangshen:Created.
     */
    @ApiOperation(value = "移动端新增调研/问答",notes = "移动端新增调研/问答")
    @ApiImplicitParam(name = "articleVO",value = "帖子实体",required = true,dataType = "ArticleVO")
    @PostMapping("/addArticle")
    public ResultMessage addArticle(@RequestBody ArticleVO articleVO, @RequestParam String phone){
        articleVO.setCreator(getCurrentAccountIdApp(phone));
        articleVO.setCreatorName(getCurrentNickNameApp(phone));
        articleVO.setCreateTime(LocalDateTime.now());
        if(ArticleStatusEnum.ARTICLE_STATUS_PENDING.getId().equals(articleVO.getArticleStatus()) ){
            articleVO.setPublishTime(new Date());
            articleVO.setPublisher(articleVO.getCreator());
        }
        ResultMessage result = ArticleVO.checkAddArticleParam(articleVO);
        if (!result.isFlag()) return result;
        return forumArticleService.saveArticle(articleVO);
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
    public ResultMessage deleteArticle(@RequestBody ArticleVO articleVO, @RequestParam String phone){
        if (null == articleVO || null == articleVO.getArticleId()){
            return ResultMessage.fail();
        }
        articleVO.setCreator(getCurrentAccountIdApp(phone));
        return forumArticleService.deleteArticle(articleVO);
    }

    /**
     * 修改报告/调研/问答/数据分享详情
     * @param articleVO
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @ApiOperation(value = "修改报告/调研/问答/数据分享详情",notes = "修改报告/调研/问答/数据分享详情")
    @ApiImplicitParam(name = "articleVO",value = "报告实体",required = true,dataType = "ArticleVO")
    @PostMapping("/updateArticle")
    public ResultMessage updateArticle(@RequestBody ArticleVO articleVO, @RequestParam String phone){

        if(StringUtils.isNotBlank(articleVO.getAdminFlag())){

            articleVO.setAuditor(getCurrentAccountIdApp(phone));
            articleVO.setAuditName(getCurrentNickNameApp(phone));
            articleVO.setAuditTime(LocalDateTime.now());

            if(ArticleStatusEnum.ARTICLE_STATUS_PENDING.getId().equals(articleVO.getArticleStatus()) ){
                articleVO.setPublishTime(new Date());
                articleVO.setPublisher(articleVO.getCreator());
            }

        }
        return forumArticleService.updateArticle(articleVO);
    }

}
