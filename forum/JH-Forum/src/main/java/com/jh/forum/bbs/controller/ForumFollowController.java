package com.jh.forum.bbs.controller;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.entity.ForumFollow;
import com.jh.forum.bbs.service.IForumFollowService;
import com.jh.forum.cache.service.ICacheService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 收藏与点赞
 */
@Api(value = "收藏与点赞",description = "收藏与点赞接口")
@RestController
@RequestMapping("/follow")
public class ForumFollowController extends BaseController {


    private static Logger logger = Logger.getLogger(ForumFollowController.class);

    @Autowired
    private IForumFollowService forumFollowService;

    @Autowired
    private ICacheService cacheService;

    /**
     * 新增收藏或者点赞
     * @param forumFollow
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @ApiOperation(value = "新增收藏或点赞",notes = "新增收藏或点赞")
    @ApiImplicitParam(name = "forumFollow",value = "收藏或点赞实体",required = true,dataType = "ForumFollow")
    @PostMapping("/addFollow")
    public ResultMessage addFollow(@RequestBody ForumFollow forumFollow){
        ResultMessage result = ResultMessage.fail();
        if (null == forumFollow.getArticleId() && null == forumFollow.getFollowType()){
            return ResultMessage.fail();
        }
        try{
            forumFollow.setCreator(getCurrentAccountId());
            forumFollow.setCreatorName(getCurrentNickName());
             result = forumFollowService.save(forumFollow);
            cacheService.setFollowCommentNumByArticleId(forumFollow.getArticleId());//重新设置缓存
        }catch (Exception e){
            logger.info(e);
            return ResultMessage.fail();
        }
        return result;
    }



    /**
     * 取消收藏或点赞
     * @param forumFollow
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @ApiOperation(value = "取消收藏或点赞",notes = "取消收藏或点赞")
    @ApiImplicitParam(name = "forumFollow",value = "收藏或点赞实体",required = true,dataType = "ForumFollow")
    @PostMapping("/removeFollow")
    public ResultMessage removeFollow(@RequestBody ForumFollow forumFollow){
        if (null == forumFollow.getArticleId() && null == forumFollow.getFollowType()){
            return ResultMessage.fail();
        }
        forumFollow.setCreator(getCurrentAccountId());
        forumFollow.setCreatorName(getCurrentNickName());
        ResultMessage result = forumFollowService.delete(forumFollow);
        cacheService.setFollowCommentNumByArticleId(forumFollow.getArticleId());//重新设置缓存
        return result;
    }

    /**
     * 查询关注或点赞列表
     * @param
     * @return
     * @version <1> 2019/3/13 mason:Created.
     */
    @ApiOperation(value = "查询关注或点赞列表",notes = "查询关注或点赞列表")
    @ApiImplicitParam(name = "forumFollow",value = "收藏或点赞实体",required = true,dataType = "ForumFollow")
    @PostMapping("/findByPage")
    public ResultMessage findByPage(@RequestBody ForumFollow forumFollow){
        return forumFollowService.findByPage(forumFollow);
    }

    /**
     * 查询我的关注列表(带分页)
     * @param
     * @return 返回用户信息
     * @version <1> 2019/4/19 10:36 zhangshen:Created.
     */
    @ApiOperation(value = "查询我的关注列表(带分页)",notes = "查询我的关注列表(带分页)")
    @ApiImplicitParam(name = "forumFollow",value = "关注实体",required = true,dataType = "ForumFollow")
    @PostMapping("/findMyFollowPageInfo")
    public PageInfo<Object> findMyFollowPageInfo(@RequestBody ForumFollow forumFollow){
        if (forumFollow.getCreator() == null) {
            forumFollow.setCreator(getCurrentAccountId());
        }
        return forumFollowService.findMyFollowPageInfo(forumFollow);
    }

    /**
     * 查询我的关注列表(不带分页)
     * @param
     * @return
     * @version <1> 2019/4/19 10:36 zhangshen:Created.
     */
    @ApiOperation(value = "查询我的关注列表(不带分页)",notes = "查询我的关注列表(不带分页)")
    @ApiImplicitParam(name = "forumFollow",value = "关注实体",required = true,dataType = "ForumFollow")
    @PostMapping("/findMyFollowList")
    public ResultMessage findMyFollowList(@RequestBody ForumFollow forumFollow){
        if (forumFollow.getCreator() == null) {
            forumFollow.setCreator(getCurrentAccountId());
        }
        return forumFollowService.findMyFollowList(forumFollow);
    }




    /**
     * 查询我的关注列表(带分页)
     * @param
     * @return 返回用户信息
     * @version <1> 2019-05-08 lcw :Created.
     */
    @ApiOperation(value = "查询我的关注列表(带分页)",notes = "查询我的关注列表(带分页)")
    @ApiImplicitParam(name = "forumFollow",value = "关注实体",required = true,dataType = "ForumFollow")
    @PostMapping("/findMyFollowsByPage")
    public PageInfo<ForumFollow> findMyFollowsByPage(@RequestBody ForumFollow forumFollow){
        if (forumFollow.getCreator() == null) {
            forumFollow.setCreator(getCurrentAccountId());
        }
        return forumFollowService.findMyFollowByPage(forumFollow);
    }


}
