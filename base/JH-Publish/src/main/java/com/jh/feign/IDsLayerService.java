package com.jh.feign;

import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Description:
 * 1.
 *
 * @version <1> 2018-07-26 13:29 zhangshen: Created.
 */
@FeignClient(name="jh-map")
public interface IDsLayerService {

    /**
     * 根据区域、作物、时间查询图层
     * 在当前区域下查询图层，如果没有查询父级区域
     * @param
     * cropId:作物ID
     * resolution：分辨率
     * dataSet:数据集
     * regionId：区域ID
     * layerDate：图层时间
     * @version <1> 2018-05-22 cxw: Created.
     */
    @GetMapping(value = "/layer/findLayer")
    ResultMessage layer(@RequestParam(name="cropId")Integer cropId,
                            @RequestParam(name="dsId")Integer dataSet,
                            @RequestParam(name="accuracyId")Integer resolution,
                            @RequestParam(name="regionId")Long regionId,
                            @RequestParam(name="dataTime")String layerDate);


    /**
     * 发布图层接口
     * @param voMap
     * @return
     * @version<1> 2018-08-01 lcw : Created.
     */
    @PostMapping("/layer/saveOrUpdate")
    ResultMessage saveOrUpdate(@RequestBody Map<String,Object> voMap);

    /**
     * 根据生产任务ID集合撤回图层
     * @version <1> 2019-02-18 lijie: Created.
     */
    @GetMapping("/layer/backTiff")
    ResultMessage backTiff(@RequestParam(name="productIds")Integer [] productIds,@RequestParam(name="type") Integer type);
}
