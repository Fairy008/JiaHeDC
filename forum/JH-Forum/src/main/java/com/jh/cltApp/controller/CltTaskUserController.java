package com.jh.cltApp.controller;

import com.github.pagehelper.PageInfo;
import com.jh.cltApp.entity.CltTaskUser;
import com.jh.cltApp.service.ICltTaskUserService;
import com.jh.forum.base.controller.BaseController;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 任务参与者controller
 * @version <1> 2019/4/9 16:37 zhangshen:Created.
 */
@Api(value = "任务参与者",description = "任务参与者")
@RestController
@RequestMapping("/cltTaskUser")
public class CltTaskUserController extends BaseController {

    @Autowired
    private ICltTaskUserService cltTaskUserService;

    /**
     * 查询任务参与人(带分页)
     * @param
     * @return
     * @version <1> 2019/4/10 10:55 zhangshen:Created.
     */
    @ApiOperation(value = "查询任务参与人(带分页)",notes = "查询任务参与人(带分页)")
    @ApiImplicitParam(name = "cltTaskUser",value = "参与人实体",required = true,dataType = "CltTaskUser")
    @PostMapping("/findCltTaskUserPageInfo")
    public PageInfo<CltTaskUser> findCltTaskUserPageInfo(@RequestBody CltTaskUser cltTaskUser, @RequestParam String phone){
        cltTaskUser.setCreator(getCurrentAccountIdApp(phone));
        return cltTaskUserService.findCltTaskUserPageInfo(cltTaskUser);
    }

    /**
     * 查询任务参与人(不带分页)
     * @param 
     * @return 
     * @version <1> 2019/4/10 10:55 zhangshen:Created.
     */
    @ApiOperation(value = "查询任务参与人(不带分页)",notes = "查询任务参与人(不带分页)")
    @ApiImplicitParam(name = "cltTaskUser",value = "参与人实体",required = true,dataType = "CltTaskUser")
    @PostMapping("/findCltTaskUserList")
    public ResultMessage findCltTaskUserList(@RequestBody CltTaskUser cltTaskUser, @RequestParam String phone){
        cltTaskUser.setCreator(getCurrentAccountIdApp(phone));
        return cltTaskUserService.findCltTaskUserList(cltTaskUser);
    }

    /**
     * 新增参与人
     * @param
     * @return
     * @version <1> 2019/4/8 19:40 zhangshen:Created.
     */
    @ApiOperation(value = "新增参与人",notes = "新增参与人")
    @ApiImplicitParam(name = "cltTaskUser",value = "参与人实体",required = true,dataType = "CltTaskUser")
    @PostMapping("/createCltTaskUser")
    public ResultMessage createCltTaskUser(@RequestBody CltTaskUser cltTaskUser){
        return cltTaskUserService.createCltTaskUser(cltTaskUser);
    }

    /**
     * 根据id删除参与人
     * @param
     * @return
     * @version <1> 2019/4/8 19:42 zhangshen:Created.
     */
    @ApiOperation(value = "根据id删除参与人",notes = "根据id删除参与人")
    @ApiImplicitParam(name = "cltTaskUser",value = "参与人实体",required = true,dataType = "CltTaskUser")
    @PostMapping("/deleteCltTaskUserById")
    public ResultMessage deleteCltTaskUserById(@RequestBody CltTaskUser cltTaskUser){
        return cltTaskUserService.deleteCltTaskUserById(cltTaskUser.getId());
    }

    /**
     * 根据任务id删除参与人
     * @param
     * @return
     * @version <1> 2019/4/8 19:42 zhangshen:Created.
     */
    @ApiOperation(value = "根据任务id删除参与人",notes = "根据任务id删除参与人")
    @ApiImplicitParam(name = "cltTaskUser",value = "参与人实体",required = true,dataType = "CltTaskUser")
    @PostMapping("/deleteCltTaskUserByTaskId")
    public ResultMessage deleteCltTaskUserByTaskId(@RequestBody CltTaskUser cltTaskUser){
        return cltTaskUserService.deleteCltTaskUserById(cltTaskUser.getTaskId());
    }
}
