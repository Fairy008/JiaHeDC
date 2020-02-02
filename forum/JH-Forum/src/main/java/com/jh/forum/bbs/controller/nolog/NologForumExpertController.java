package com.jh.forum.bbs.controller.nolog;

import com.github.pagehelper.PageInfo;
import com.jh.forum.bbs.entity.ForumExpert;
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
@Api(value = "nolog论坛专家",description = "nolog专家接口")
@RestController
@RequestMapping("/nolog/expert")
public class NologForumExpertController {

    @Autowired
    private IForumExpertService forumExpertService;

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
     * 根据专家accountId查询专家详情
     * @param accountId  专家accountId
     * @return ResultMessage
     * @version <1> 2019/3/18 mason:Created.
     */
    @ApiOperation(value = "根据专家accountId查询专家详情",notes = "根据专家accountId查询专家详情")
    @ApiImplicitParam(name = "accountId",value = "专家id",required = true,paramType="query",dataType = "Integer")
    @GetMapping("/findExpertByAccountId")
    public ResultMessage findExpertByAccountId(@RequestParam Integer accountId){
        //还需要查询专家相关的报告、调研、问答
        return forumExpertService.getByAccountId(accountId);
    }

}
