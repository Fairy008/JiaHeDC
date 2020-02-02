package com.jh.forum.cms.controller;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.entity.ForumExpert;
import com.jh.forum.bbs.service.IForumExpertService;
import com.jh.forum.bbs.vo.ExpertVO;
import com.jh.util.DownloadUtil;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

/**
 * 后台-论坛专家
 */
@Api(value = "论坛专家",description = "专家接口")
@RestController
@RequestMapping("/expertManage")
public class ForumExpertManageController extends BaseController {

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
    public PageInfo<ExpertVO> findExpertList(ExpertVO expertVO){
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
     * 新增专家
     * @param request
     * @return ResultMessage
     * @version <1> 2019/3/7 mason:Created.
     */
    @ApiOperation(value = "新增专家",notes = "新增专家")
    @ApiImplicitParam(name = "request",value = "专家实体",required = false,dataType = "HttpServletRequest")
    @PostMapping("/addExpert")
    public ResultMessage addExpert(HttpServletRequest request){
        ExpertVO expertVO = getForumExpert(request);
        Integer accountId = this.getCurrentAccountId();
        expertVO.setCreator(accountId);
        return forumExpertService.saveForumExpert(request,expertVO);
    }

    /**
     * 修改专家
     * @param request
     * @return ResultMessage
     * @version <1> 2019/3/7 mason:Created.
     */
    @ApiOperation(value = "修改专家",notes = "修改专家")
    @ApiImplicitParam(name = "request",value = "专家实体",required = false,dataType = "HttpServletRequest")
    @PostMapping("/editExpert")
    public ResultMessage editExpert(HttpServletRequest request){
        return forumExpertService.updateForumExpert(request,getForumExpert(request));
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
     * 头像预览
     * @param expertId
     * @return
     */
    @ApiOperation(value = "头像预览",notes = "头像预览")
    @ApiImplicitParam(name = "expertId",value = "专家ID",required = true,dataType = "Integer")
    @GetMapping("/previewPhoto")
    public void previewPhoto(@RequestParam Integer expertId, HttpServletResponse response){
        ResultMessage result = forumExpertService.getById(expertId);
        if(result.isFlag()){
            ForumExpert expert = (ForumExpert) result.getData();
            String fileAbsolutePath = CephUtils.getAbsolutePath(expert.getPhotoUrl());
            fileAbsolutePath = fileAbsolutePath.replace("\\", File.separator);
            DownloadUtil.downloadFileStream(fileAbsolutePath, "image/png", response);
        }
    }


    private ExpertVO getForumExpert(HttpServletRequest request){
        ExpertVO expert=new ExpertVO();
        if(StringUtils.isNotBlank(request.getParameter("expertId"))){
            expert.setExpertId(Integer.parseInt(request.getParameter("expertId")));
        }
        if(StringUtils.isNotBlank(request.getParameter("accountId"))){
            expert.setAccountId(Integer.parseInt(request.getParameter("accountId")));
        }
        expert.setExpertName(request.getParameter("expertName"));
        expert.setIndustry(request.getParameter("industry"));
        expert.setCompany(request.getParameter("company"));
        expert.setPhone(request.getParameter("phone"));
        expert.setIntroduction(request.getParameter("introduction"));
        if(StringUtils.isNotBlank(request.getParameter("sex"))){
            expert.setSex(Integer.parseInt(request.getParameter("sex")));
        }
        if(StringUtils.isNotBlank(request.getParameter("authStatus"))){
            expert.setAuthStatus(Integer.parseInt(request.getParameter("authStatus")));
        }
        if(StringUtils.isNotBlank(request.getParameter("grade"))){
            expert.setGrade(Integer.parseInt(request.getParameter("grade")));
        }
        return expert;
    }

}
