package com.jh.forum.bbs.controller;


import com.github.pagehelper.PageInfo;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.entity.ForumLabel;
import com.jh.forum.bbs.service.IForumLabelService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 论坛标签
 */
@Api(value = "论坛标签",description = "标签接口")
@RestController
@RequestMapping("/label")
public class ForumLabelController extends BaseController {

    @Autowired
    private IForumLabelService forumLabelService;

    /**
     * 查询标签列表
     * @param forumLabel
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @ApiOperation(value = "查询标签列表",notes = "查询标签列表")
    @ApiImplicitParam(name = "forumLabel",value = "标签实体",required = true,dataType = "ForumLabel")
    @PostMapping("/findLabelList")
    public PageInfo<ForumLabel> findLabelList(@RequestBody ForumLabel forumLabel){
        return forumLabelService.findLabelList(forumLabel);
    }

    /**
     * 新增标签
     * @param forumLabel
     * @return ResultMessage
     * @version <1> 2019/3/7 mason:Created.
     */
    @ApiOperation(value = "新增标签",notes = "新增标签")
    @ApiImplicitParam(name = "forumLabel",value = "标签实体",required = true,dataType = "ForumLabel")
    @PostMapping("/addLabel")
    public ResultMessage addLabel(@RequestBody ForumLabel forumLabel){
        forumLabel.setCreator(getCurrentAccountId());
        forumLabel.setCreatorName(getCurrentNickName());
        return forumLabelService.save(forumLabel);
    }

    /**
     * 修改标签
     * @param forumLabel
     * @return ResultMessage
     * @version <1> 2019/3/7 mason:Created.
     */
    @ApiOperation(value = "修改标签",notes = "修改标签")
    @ApiImplicitParam(name = "forumLabel",value = "标签实体",required = true,dataType = "ForumLabel")
    @PostMapping("/editLabel")
    public ResultMessage editLabel(@RequestBody ForumLabel forumLabel){
        return forumLabelService.update(forumLabel);
    }

    /**
     * 删除标签
     * @param forumLabel
     * @return ResultMessage
     * @version <1> 2019/3/7 mason:Created.
     */
    @ApiOperation(value = "删除标签",notes = "删除标签")
    @ApiImplicitParam(name = "forumLabel",value = "标签实体",required = true,dataType = "ForumLabel")
    @PostMapping("/deleteLabel")
    public ResultMessage deleteLabel(@RequestBody ForumLabel forumLabel){
        return forumLabelService.delete(forumLabel);
    }
    /**
     * 查询标签
     * @param forumLabel
     * @return labelId
     * @version <1> 2019/4/11 lijie:Created.
     */
    @ApiOperation(value = "查询标签详情",notes = "查询标签详情")
    @ApiImplicitParam(name = "labelId",value = "标签Id",required = true,dataType = "Integer")
    @GetMapping("/getLabelDetail")
    public ResultMessage getLabelDetail(Integer labelId){
        return forumLabelService.getById(labelId);
    }

    /**
     * 查询标签列表
     * @param forumLabel
     * @return ResultMessage
     * @version <1> 2019-04-11 lijie:Created.
     */
    @ApiOperation(value = "后台查询标签列表",notes = "后台查询标签列表")
    @ApiImplicitParam(name = "forumLabel",value = "标签实体",required = true,dataType = "ForumLabel")
    @PostMapping("/findLabelListCms")
    public PageInfo<ForumLabel> findLabelListCms(ForumLabel forumLabel){
        return forumLabelService.findLabelListCms(forumLabel);
    }

    /**
     * 检查标签名称是否存在
     * @param labelName
     * @return labelId
     * @version <1> 2019/4/11 lijie:Created.
     */
    @ApiOperation(value = "检查标签名称是否存在",notes = "检查标签名称是否存在")
    @ApiImplicitParam(name = "labelName",value = "标签名称",required = true,dataType = "String")
    @GetMapping("/getListByLabelName")
    public ResultMessage getLabelDetail(String labelName){
         List<ForumLabel> list=forumLabelService.findLabelByName(labelName);
        return ResultMessage.success(list);
    }
}
