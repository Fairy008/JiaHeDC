package com.jh.forum.bbs.controller;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.service.IForumAdvertService;
import com.jh.forum.bbs.vo.AdvertVo;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sxj
 * @Description 广告controller
 * @Date 2019/8/13 17:28
 * @Version 1.0
 **/
@Api(value = "广告位管理",description = "广告位管理接口")
@RestController
@RequestMapping("/advert")
public class ForumAdvertController extends BaseController {

    @Autowired
    private IForumAdvertService forumAdvertService;


    /**
     * 新增首页顶部广告位
     * @param
     * @return
     */
    @ApiOperation(value = "新增广告位",notes = "新增广告位")
    @ApiImplicitParam(name = "request", value = "表单数据", required = false, dataType = "HttpServletRequest")
    @PostMapping("/saveAdvert")
    public @ResponseBody ResultMessage saveAdvert(HttpServletRequest request){
        ResultMessage resultMessage = forumAdvertService.saveAdvert(request);
        return resultMessage;
    }

    @ApiOperation(value = "查询广告位详情",notes = "查询广告位详情")
    @ApiImplicitParam(name = "advertId",value = "广告位id",required = true,paramType="query",dataType = "Integer")
    @GetMapping("/findAdvertInfo")
    public ResultMessage findAdvertInfo(@RequestParam Integer advertId){
        return forumAdvertService.findAdvertInfo(advertId);
    }



    @ApiOperation(value = "修改广告位",notes = "修改广告位")
    @ApiImplicitParam(name = "request", value = "表单数据", required = false, dataType = "HttpServletRequest")
    @PostMapping("/editAdvert")
    public @ResponseBody ResultMessage editAdvert(HttpServletRequest request){
        return forumAdvertService.editAdvert(request);
    }


    @ApiOperation(value = "修改状态",notes = "修改状态")
    @ApiImplicitParam(name = "AdvertVo",value = "广告实体",required = true,dataType = "AdvertVo")
    @PostMapping("/editStatus")
    public ResultMessage editStatus(@RequestBody AdvertVo advertVo){
        return forumAdvertService.editStatus(advertVo);
    }


    @ApiOperation(value = "下架",notes = "下架")
    @ApiImplicitParam(name = "AdvertVo",value = "广告实体",required = true,dataType = "AdvertVo")
    @PostMapping("/editOff")
    public ResultMessage editOff(@RequestBody AdvertVo advertVo){
        return forumAdvertService.editOff(advertVo);
    }


    @ApiOperation(value = "删除广告位",notes = "删除广告位")
    @ApiImplicitParam(name = "AdvertVo",value = "广告实体",required = true,dataType = "AdvertVo")
    @PostMapping("/deleteAdvert")
    public ResultMessage deleteAdvert(@RequestBody AdvertVo advertVo){
        return forumAdvertService.deleteAdvert(advertVo);
    }

    @ApiOperation(value = "查询广告位",notes = "查询广告位")
    @ApiImplicitParam(name = "advertId",value = "广告位id",required = true,paramType="query",dataType = "Integer")
    @GetMapping("/queryAdvertList")
    public ResultMessage queryAdvertList(@RequestParam Integer advertId){
        return forumAdvertService.queryAdvertList(advertId);
    }


    @ApiOperation(value = "查询广告位列表",notes = "查询广告位列表")
    @ApiImplicitParam(name = "advertVo",value = "广告位实体",required = true,dataType = "AdvertVo")
    @PostMapping("/getListByAdvert")
    public PageInfo<AdvertVo> getListByAdvert(AdvertVo advertVo){
        return forumAdvertService.getListByAdvert(advertVo);
    }



}
