package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Description: 内部图层接口定义，用于调用私有图层数据服务
 * 1. 图层发布接口
 * 2. 查询作物图层接口
 *
 * @version <1> 2018-07-26 13:29 zhangshen: Created.
 */
@FeignClient(name="jh-map")
public interface ILayerService {

    /**
     * 根据区域、作物、时间查询图层
     * 在当前区域下查询图层，如果没有查询父级区域
     * @param
     *  cropId:作物ID
     *  resolution：分辨率
     *  dataSet:数据集
     *  regionId：区域ID
     *  layerDate：图层时间
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
}
