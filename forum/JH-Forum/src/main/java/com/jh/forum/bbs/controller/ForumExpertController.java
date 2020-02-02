package com.jh.forum.bbs.controller;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.service.IForumExpertService;
import com.jh.forum.bbs.vo.ExpertVO;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 论坛专家
 */
@Api(value = "论坛专家",description = "专家接口")
@RestController
@RequestMapping("/expert")
public class ForumExpertController extends BaseController {

    @Autowired
    private IForumExpertService forumExpertService;


    /**
     * 分页查询专家列表
     * @param expertVO
     * @return PageInfo<ExpertVO>
     * @version <1> 2019/3/5 mason:Created.
     */
    @ApiOperation(value = "分页查询专家列表",notes = "分页查询专家列表")
    @ApiImplicitParam(name = "expertVO",value = "专家实体",required = true,dataType = "ExpertVO")
    @PostMapping("/findExpertList")
    public PageInfo<ExpertVO> findExpertList(@RequestBody ExpertVO expertVO){
        return forumExpertService.findExpertList(expertVO);
    }

    /**
     * 查询专家详情
     * @param expertId  专家id
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @ApiOperation(value = "查询专家详情",notes = "查询专家详情")
    @ApiImplicitParam(name = "expertId",value = "专家id",required = true,paramType="query",dataType = "Integer")
    @GetMapping("/findExpertInfo")
    public ResultMessage findExpertInfo(@RequestParam Integer expertId){
        //还需要查询专家相关的报告、调研、问答
        return forumExpertService.getById(expertId);
    }

    /**
     * 查询随机5名专家
     * @param
     * @return
     * @version <1> 2019/3/6 mason:Created.
     */
    @ApiOperation(value = "查询随机5名专家",notes = "查询随机5名专家")
    @GetMapping("/findRandomExpert")
    public ResultMessage findRandomExpert(){
        return forumExpertService.findRandomExpert();
    }


    /**
     * 新增专家
     * @param expertVO
     * @return ResultMessage
     * @version <1> 2019/3/7 mason:Created.
     */
    @ApiOperation(value = "新增专家",notes = "新增专家")
    @ApiImplicitParam(name = "expertVO",value = "专家实体",required = true,dataType = "ExpertVO")
    @PostMapping("/addExpert")
    public ResultMessage addExpert(@RequestBody ExpertVO expertVO){
        return forumExpertService.save(expertVO);
    }

    /**
     * 修改专家
     * @param expertVO
     * @return ResultMessage
     * @version <1> 2019/3/7 mason:Created.
     */
    @ApiOperation(value = "修改专家",notes = "修改专家")
    @ApiImplicitParam(name = "expertVO",value = "专家实体",required = true,dataType = "ExpertVO")
    @PostMapping("/editExpert")
    public ResultMessage editExpert(@RequestBody ExpertVO expertVO){
        return forumExpertService.update(expertVO);
    }

    /**
     * 删除专家
     * @param expertVO
     * @return ResultMessage
     * @version <1> 2019/3/7 mason:Created.
     */
    @ApiOperation(value = "删除专家",notes = "删除专家")
    @ApiImplicitParam(name = "expertVO",value = "专家实体",required = true,dataType = "ExpertVO")
    @PostMapping("/deleteExpert")
    public ResultMessage deleteExpert(@RequestBody ExpertVO expertVO){
        return forumExpertService.delete(expertVO);
    }

    /**
     * 修改专家
     * @param expertVO
     * @return ResultMessage
     * @version <1> 2019/3/7 mason:Created.
     */
    @ApiOperation(value = "修改专家基础信息",notes = "修改专家基础信息")
    @ApiImplicitParam(name = "expertVO",value = "专家实体",required = true,dataType = "ExpertVO")
    @PostMapping("/updateByAccount")
    public ResultMessage updateByAccount(@RequestBody ExpertVO expertVO){
        return forumExpertService.updateByAccount(expertVO);
    }

}