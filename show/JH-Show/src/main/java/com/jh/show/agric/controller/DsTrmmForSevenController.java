package com.jh.show.agric.controller;

import com.jh.biz.feign.IDsTrmmForSevenService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:7天降雨数据集服务:
 * 1.根据区域，日期构造7天降雨数据查询服务(91期货)
 * @version <1> 2018-09-03 cxw: Created.
 */
@Api(value="7天降雨数据集接口",description="7天降雨数据集接口")
@RestController
@RequestMapping("/sevenTrmm")
public class DsTrmmForSevenController {

    @Autowired
    private IDsTrmmForSevenService trmmForSevenService;

    /*
    * 根据区域，日期构造7天降雨数据查询服务
     * @param 数据集查询参数
     *   regionId: 区域ID
     *       date: 日期
    * @return ResultMessage
    * @version <1> 2018-09-03 cxw: Created.
    */
    @ApiOperation(value="构造7天降雨数据查询服务",notes="根据区域、日期段查询区域在日期段内的降雨" )
    @GetMapping("/trmmForSeven")
    public ResultMessage queryTrmmForSeven(Long[] regionId, String date){
        return  trmmForSevenService.queryTrmmForSeven(regionId,date);
    }
}
