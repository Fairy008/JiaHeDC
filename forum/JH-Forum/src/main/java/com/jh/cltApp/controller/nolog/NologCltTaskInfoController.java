package com.jh.cltApp.controller.nolog;

import com.github.pagehelper.PageInfo;
import com.jh.cltApp.entity.CltTaskInfo;
import com.jh.cltApp.service.ICltTaskInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description：免登录任务相关接口
 * @version<1> 2019-04-11 mason : Created.
 */
@Api(value = "免登录任务相关接口",description = "免登录任务相关接口")
@RestController
@RequestMapping("/nolog/cltTaskInfo")
public class NologCltTaskInfoController {

    @Autowired
    private ICltTaskInfoService cltTaskInfoService;


    /**
     * 多条件查询任务列表
     * @param cltTaskInfo
     * @return ageInfo<CltTaskInfo>
     * @version <1> 2019/4/12 mason:Created.
     */
    @ApiOperation(value = "多条件查询任务列表",notes = "多条件查询任务列表")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/findCombinationPage")
    public PageInfo<CltTaskInfo> findCombinationPage(@RequestBody CltTaskInfo cltTaskInfo){
        return cltTaskInfoService.getListByCombination(cltTaskInfo);
    }



}
