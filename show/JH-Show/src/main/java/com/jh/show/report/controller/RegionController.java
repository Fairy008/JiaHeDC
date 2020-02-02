package com.jh.show.report.controller;

import com.jh.biz.feign.IRegionService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * description:查询区域
 *
 * @version <1> 2018-06-22 wl: Created.
 */
@RestController
@RequestMapping("nolog/region")
public class RegionController {

    @Autowired
    private IRegionService regionService;

    /**
     * 根据父级ID查询子级城市或者区域
     * @param  parentId
     * @return ResultMessage :
     * @version <1> 2018-06-24 wl:Created.
     */
    @ApiOperation(value = "根据父级ID查询子级城市或者区域",notes = "根据父级ID查询子级城市或者区域")
    @ApiImplicitParam(name = "parentId",value = "父级ID",required = true,dataType = "Long")
    @GetMapping("findRegionByParentId")
    public ResultMessage findRegionByParentId(@RequestParam Long parentId){
        Map<String,Object> map=new HashMap<>();
        map.put("parentId",parentId);
        ResultMessage resultMessage = regionService.findRegion(map);
        List<Map<String,Object>> list = new ArrayList<>();
        if(resultMessage.getData()!=null){
           list = (List<Map<String,Object>>) resultMessage.getData();
        }
        return  ResultMessage.success(list);
    }

}
