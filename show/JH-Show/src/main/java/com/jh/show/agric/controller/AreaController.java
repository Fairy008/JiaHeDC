package com.jh.show.agric.controller;

import com.jh.biz.controller.BaseController;
import com.jh.show.agric.service.IAreaService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:区域服务
 * 1.根据用户ID查询区域
 * 2.根据区域查询时间轴时间点
 * @version <1> 2018-08-10 cxw: Created.
 */
@Api(value = "区域查询接口",description="区域查询接口")
@RestController
@RequestMapping("/area")
public class AreaController extends BaseController {

    @Autowired
    private IAreaService areaService;

    /*
     * 根据用户ID查询区域
     * @return ResultMessage
     * @version <1> 2018-08-10 cxw: Created.
     */
    @ApiOperation(value = "根据用户ID查询区域", notes = "根据用户ID查询区域")
    @GetMapping("/queryAreaByUserId")
    public ResultMessage queryAreaByUserId(){
        String token = getToken();
        return  areaService.findAreaByUserId(token);
    }

    /**
     * 查询时间轴
     * @param
     * regionId  区域id
     * resolution 精度
     * @return ResultMessage :
     * @version <1> 2018-08-13 cxw:Created.
     */
    @ApiOperation(value = "查询时间轴",notes = "查询时间轴")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regionId",value = "区域ID",required = true, dataType = "Long" ,paramType="query",defaultValue="3102000006"),
            @ApiImplicitParam(name = "resolution",value = "精度",required = true, dataType = "int" ,paramType="query",defaultValue="4011"),
    })
    @GetMapping("queryTimeAxisForWx")
    public ResultMessage queryTimeAxisForWx(Long regionId,Integer resolution ){
        return areaService.findTimeAxisForWx(regionId,resolution);
    }


}
