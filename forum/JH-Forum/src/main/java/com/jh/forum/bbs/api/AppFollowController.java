package com.jh.forum.bbs.api;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.entity.ForumFollow;
import com.jh.forum.bbs.service.IForumFollowService;
import com.jh.forum.cache.service.ICacheService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description：移动端关注和收藏相关接口
 * @version<1> 2019-04-11 mason : Created.
 */
@Api(value = "移动端关注和收藏相关接口",description = "移动端关注和收藏相关接口")
@RestController
@RequestMapping("api/follow")
public class AppFollowController extends BaseController {

    @Autowired
    private IForumFollowService forumFollowService;

    @Autowired
    private ICacheService cacheService;

   /**
    * 移动端查询关注或点赞列表
    * @param
    * @return
    * @version <1> 2019/4/11 mason:Created.
    */
    @ApiOperation(value = "查询关注或点赞列表",notes = "查询关注或点赞列表")
    @ApiImplicitParam(name = "forumFollow",value = "收藏或点赞实体",required = true,dataType = "ForumFollow")
    @PostMapping("/findByPage")
    public ResultMessage findByPage(@RequestBody ForumFollow forumFollow, @RequestParam String phone){
        forumFollow.setCreator(getCurrentAccountIdApp(phone));
        return forumFollowService.findByPage(forumFollow);
    }


    /**
     * 移动端查询关注或点赞列表
     * @param
     * @return
     * @version <1> 2019/4/11 mason:Created.
     */
    @ApiOperation(value = "查询关注或点赞列表",notes = "查询关注或点赞列表")
    @ApiImplicitParam(name = "forumFollow",value = "收藏或点赞实体",required = true,dataType = "ForumFollow")
    @PostMapping("/findByPageInfo")
    public PageInfo<ForumFollow> findByPageInfo(@RequestBody ForumFollow forumFollow, @RequestParam String phone){
        forumFollow.setCreator(getCurrentAccountIdApp(phone));
        return forumFollowService.findByPageInfo(forumFollow);
    }

    /**
     *移动端取消收藏或点赞
     * @param forumFollow
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @ApiOperation(value = "取消收藏或点赞",notes = "取消收藏或点赞")
    @ApiImplicitParam(name = "forumFollow",value = "收藏或点赞实体",required = true,dataType = "ForumFollow")
    @PostMapping("/removeFollow")
    public ResultMessage removeFollow(@RequestBody ForumFollow forumFollow, @RequestParam String phone){
        if (null == forumFollow.getArticleId() && null == forumFollow.getFollowType()){
            return ResultMessage.fail();
        }
        forumFollow.setCreator(getCurrentAccountIdApp(phone));
        forumFollow.setCreatorName(getCurrentNickNameApp(phone));
        ResultMessage result = forumFollowService.delete(forumFollow);
        cacheService.setFollowCommentNumByArticleId(forumFollow.getArticleId());//重新设置缓存
        return result;
    }

    /**
     * 新增收藏或者点赞
     * @param forumFollow
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @ApiOperation(value = "新增收藏或点赞",notes = "新增收藏或点赞")
    @ApiImplicitParam(name = "forumFollow",value = "收藏或点赞实体",required = true,dataType = "ForumFollow")
    @PostMapping("/addFollow")
    public ResultMessage addFollow(@RequestBody ForumFollow forumFollow, @RequestParam String phone){
        ResultMessage result = ResultMessage.fail();
        if (null == forumFollow.getArticleId() && null == forumFollow.getFollowType()){
            return ResultMessage.fail();
        }
        try{
            forumFollow.setCreator(getCurrentAccountIdApp(phone));
            forumFollow.setCreatorName(getCurrentNickNameApp(phone));
            result = forumFollowService.save(forumFollow);
            cacheService.setFollowCommentNumByArticleId(forumFollow.getArticleId());//重新设置缓存
        }catch (Exception e){
            return ResultMessage.fail();
        }
        return result;
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
    public PageInfo<Object> findMyFollowPageInfo(@RequestBody ForumFollow forumFollow, @RequestParam String phone){
        forumFollow.setCreator(getCurrentAccountIdApp(phone));
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
    public ResultMessage findMyFollowList(@RequestBody ForumFollow forumFollow, @RequestParam String phone){
        forumFollow.setCreator(getCurrentAccountIdApp(phone));
        return forumFollowService.findMyFollowList(forumFollow);
    }
}
