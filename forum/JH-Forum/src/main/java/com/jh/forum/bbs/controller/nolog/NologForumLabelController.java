package com.jh.forum.bbs.controller.nolog;

import com.github.pagehelper.PageInfo;
import com.jh.forum.bbs.entity.ForumLabel;
import com.jh.forum.bbs.service.IForumLabelService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * nolog论坛标签
 */
@Api(value = "nolog论坛标签",description = "nolog标签接口")
@RestController
@RequestMapping("/nolog/label")
public class NologForumLabelController {

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
     * 查询所有标签
     * @return ResultMessage
     * @version <1> 2019/3/5 mason:Created.
     */
    @ApiOperation(value = "查询最热报告、调研、问答",notes = "查询最热报告")
    @ApiImplicitParam(name = "articleVO",value = "报告实体",required = false,dataType = "ArticleVO")
    @PostMapping("/findAllLabel")
    public ResultMessage findAllLabel(){
        return forumLabelService.findAllLabel();
    }


    /**
     * 查询随机标签列表
     * @param
     * @return
     * @version <1> 2019/3/13 mason:Created.
     */
    @ApiOperation(value = "查询标签列表",notes = "查询标签列表")
    @PostMapping("/findRandomLabelList")
    public ResultMessage findRandomLabelList(){
        return forumLabelService.findRandomLabelList();
    }
}
