package com.jh.forum.cms.controller;


import com.github.pagehelper.PageInfo;
import com.jh.biz.feign.IPersonService;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.constant.ForumConstant;
import com.jh.forum.bbs.vo.ArticleVO;
import com.jh.forum.cms.service.IForumArticleManageService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 后台管理-用户后台接口
 * @version <1> 2019-03-09 lijie： Created.
 */
@Api(value = "后台用户接口",description = "后台用户接口")
@RestController
@RequestMapping("/userManage")
public class ForumUserManageController extends BaseController {

    @Autowired
    private IPersonService personService;

    /**
     * 用户是否有论坛后台管理权限
     * @param accountId 用户ID
     * @return ResultMessage
     * @version <1> 2019/3/5 lijie:Created.
     */
    @ApiOperation(value = "用户是否有论坛后台管理权限",notes = "用户是否有论坛后台管理权限")
    @ApiImplicitParam(name = "accountId",value = "用户id",required = true,paramType="query",dataType = "Integer")
    @GetMapping("/isExistForumRole")
    public ResultMessage isExistForumRole(@RequestParam Integer accountId){
        return personService.isExistRole(accountId, ForumConstant.FORUN_ROLE_CODE);
    }

}
