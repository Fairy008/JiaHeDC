package com.jh.forum.bbs.controller;

import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.entity.ForumCollectData;
import com.jh.forum.bbs.service.IForumCollectDataService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collect")
public class ForumCollectDataController extends BaseController {

    @Autowired
    private IForumCollectDataService forumCollectDataService;
    /*
     * 功能描述: 收藏数据
     * @Param:
     * @Return:
     * @version<1>  2019/9/24  wangli :Created
     */
    @PostMapping("/collectData")
    public ResultMessage collectData(@RequestBody ForumCollectData forumCollectData){
        forumCollectDataService.collectData(forumCollectData);
        return ResultMessage.success();
    }

    /*
     * 功能描述: 取消收藏数据
     * @Param:
     * @Return:
     * @version<1>  2019/9/24  wangli :Created
     */
    @PostMapping("/cancelData")
    public ResultMessage cancelData(@RequestBody ForumCollectData forumCollectData){
        forumCollectDataService.cancelData(forumCollectData);
        return ResultMessage.success();
    }


    @PostMapping(value = "/checkCollectStatus")
    public ResultMessage checkCollectStatus(@RequestBody ForumCollectData forumCollectData){
        return forumCollectDataService.selectCollectStatus(forumCollectData);
    }

    /*
     * 功能描述: 查询所有
     * @Param:
     * @Return:
     * @version<1>  2019/8/30  wangli :Created
     */
    @ApiOperation(value = "查询所有我的收藏信息",notes = "查询所有我的收藏信息")
    @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "Integer")
    @PostMapping("/queryMyCollect")
    public ResultMessage queryMyCollect (@RequestParam Integer userId){
        return  forumCollectDataService.queryMyCollect(userId);
    }
}
