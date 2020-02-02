package com.jh.produce.process.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.produce.process.entity.HandleConfig;
import com.jh.produce.process.model.HandleConfigParam;
import com.jh.produce.process.service.IHandleConfigService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:算法基础配置
 *
 * @version <1> 2018-02-06 lcw： Created.
 */
@RestController
@RequestMapping("/handleConfig")
public class HandleConfigController extends BaseController {

    @Autowired
    private IHandleConfigService handleConfigService;

    /**
     *
     * @param handleConfigParam
     * @return
     * @version <1> 2018-02-06 lcw： Created.
     * @version <2> 2018-02-07 djh： 修改返回值的类型，去掉第一个无用的方法形参.
     */
    @ApiOperation(value = "算法参数配置分页查询",notes = "算法参数配置信息列表")
    @ApiImplicitParam(name = "handleConfigParam",value = "算法参数配置对象",required = false,dataType = "HandleConfigParam")
    @PostMapping("findByPage")
    public PageInfo<HandleConfigParam> findByPage(HandleConfigParam handleConfigParam){
        return handleConfigService.findByPage(handleConfigParam);
    }

    /**
     * 查询所有算法
     * @param handleConfigParam
     * @return
     * @version <1> 2018-03-07 cxj： Created.
     */
    @ApiOperation(value = "查询所有算法参数配置",notes = "算法参数配置信息列表")
    @ApiImplicitParam(name = "handleConfigParam",value = "算法参数配置对象",required = false,dataType = "HandleConfigParam")
    @PostMapping("queryAll")
    public ResultMessage queryAll(@RequestBody HandleConfigParam handleConfigParam){
        return handleConfigService.queryAll(handleConfigParam);
    }

    /**
     * 算法参数记录新增
     * @param request
     * @param handleConfig
     * @return
     * @version <1> 2018-02-06 lcw： Created.
     */
    @ApiOperation(value = "新增算法参数信息",notes = "新增算法参数信息")
    @ApiImplicitParam(name = "handleConfig",value = "算法参数对象",required = false,dataType = "HandleConfig")
    @PostMapping("add")
    public ResultMessage add(HttpServletRequest request, @RequestBody HandleConfig handleConfig){
        ResultMessage result = ResultMessage.fail();
//        PermAccount permAccount = this.getCurrentPermAccount(request);
        handleConfig.setCreator(getCurrentAccountId());
        handleConfig.setCreatorName(getCurrentNickName());
        result = handleConfigService.save(handleConfig);
        return result;
    }


    /**
     * 根据ID获取算法参数对象
     * @param handleConfig
     * @return
     * @version <1> 2018-02-06 lcw： Created.
     * @version <2> 2018-02-07 djh： 修改方法形参.
     */
    @ApiOperation(value = "根据ID获取算法配置对象信息",notes = "根据ID获取算法配置对象信息")
    @ApiImplicitParam(name = "handleConfig",value = "算法配置对象",required = true,dataType = "HandleConfig")
    @PostMapping("getById")
    public ResultMessage getById(@RequestBody HandleConfig handleConfig){
        return handleConfigService.getById(handleConfig.getHandleMetaId());
    }

    /**
     * 算法参数记录修改
     * @param request
     * @param handleConfig
     * @return
     * @version <1> 2018-02-06 lcw： Created.
     */
    @ApiOperation(value = "修改算法参数信息",notes = "修改算法参数信息")
    @ApiImplicitParam(name = "handleConfig",value = "算法参数对象",required = false,dataType = "HandleConfig")
    @PostMapping("update")
    public ResultMessage update(HttpServletRequest request, @RequestBody HandleConfig handleConfig){
        ResultMessage result = ResultMessage.fail();
//        PermAccount permAccount = this.getCurrentPermAccount(request);
//        if(permAccount != null){
            handleConfig.setModifier(getCurrentAccountId());
            handleConfig.setModifierName(getCurrentNickName());
//        }
        result = handleConfigService.update(handleConfig);
        return result;
    }

    /**
     * 算法参数记录修改
     * @param request
     * @param handleConfigParam
     * @return
     * @version <1> 2018-02-06 lcw： Created.
     * @version <2> 2018-02-07 djh： 修改第二个方法形参类型
     */
    @ApiOperation(value = "删除算法参数信息",notes = "删除算法参数信息")
    @ApiImplicitParam(name = "handleConfigParam",value = "算法参数",required = false,dataType = "HandleConfigParam")
    @PostMapping("delete")
    public ResultMessage delete(HttpServletRequest request, @RequestBody HandleConfigParam handleConfigParam){
        ResultMessage result = ResultMessage.fail();
        HandleConfig handleConfig = handleConfigParam.getHandleConfig();
        handleConfig.setModifier(getCurrentAccountId());
        handleConfig.setModifierName(getCurrentNickName());
        result = handleConfigService.delete(handleConfig);
        return result;
    }


    /**
     * 保存卫星关联
     * @param request
     * @param paramMap
     * @return
     * @version <1> 2018/4/3 lxy created 保存卫星关联
     */
    @ApiOperation(value = "保存卫星关联",notes = "保存卫星关联")
    @ApiImplicitParam(name = "paramMap",value = "相关要保存的数据",required = false,dataType = "HashMap<String,Object>")
    @PostMapping("saveRalateStat")
    public ResultMessage saveRalateStat(HttpServletRequest request, @RequestBody HashMap<String,Object> paramMap){
        ResultMessage result = ResultMessage.fail();
        Object handleMetaId = paramMap.get("handleMetaId");
        if(handleMetaId == null){
            result = ResultMessage.fail(null,"算法配置编号不能为空");
            return result;
        }
        Object dicIds = paramMap.get("dicIds");
        if(handleMetaId == null){
            result = ResultMessage.fail(null,"请勾选卫星");
            return result;
        }

        //新增前，删除 所对应的卫星
        handleConfigService.deleteRelateHandleSatByHandleMetaId(new Integer(handleMetaId.toString()));

        //用于批量 新增
        List<HandleConfigParam> handleConfigParams = new ArrayList<>();
        String []dicIdsStr =  StringUtils.split(dicIds.toString(),',');
        for(String dicId : dicIdsStr){
            HandleConfigParam handleConfigParam = new HandleConfigParam();
            handleConfigParam.setHandleMetaId(new Integer(handleMetaId.toString()));
            handleConfigParam.setSatId(new Integer(dicId));
            handleConfigParams.add(handleConfigParam);
        }

        Boolean bitt = handleConfigService.saveRelateHandleSat(handleConfigParams);
        if(bitt){
            result = ResultMessage.success("卫星关联成功");
        }else{
            result = ResultMessage.fail("卫星关联失败");
        }

        return result;
    }


    /**
     * 根据指定handleMetaID来查询卫星
     * @param request
     * @param paramMap
     * @return
     * @version <1> 2018/4/3 lxy created 根据指定handleMetaID来查询卫星
     */
    @ApiOperation(value = "根据指定handleMetaID来查询卫星",notes = "根据指定handleMetaID来查询卫星")
    @ApiImplicitParam(name = "paramMap",value = "相关请求参数",required = false,dataType = "HashMap<String,Object>")
    @PostMapping("loadRelateStatByHandleMataId")
    public ResultMessage loadRelateStatByHandleMataId(HttpServletRequest request, @RequestBody HashMap<String,Object> paramMap){
        ResultMessage result = ResultMessage.fail();
        Object handleMetaId = paramMap.get("handleMetaId");
        List<Map<String,Object>> stats =  handleConfigService.findRelateHandleSatByHandleMetaId(new Integer(handleMetaId.toString()));
        if(stats.size() == 0){
            return result;
        }
        result = ResultMessage.success(stats);
        return result;
    }
}
