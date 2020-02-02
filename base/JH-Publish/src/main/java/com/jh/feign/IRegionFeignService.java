package com.jh.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description:
 * 1.
 *
 * @version <1> 2018-07-24 14:37 zhangshen: Created.
 */
@FeignClient(name="jh-sys")
public interface IRegionFeignService {

    @PostMapping(value = "/region/queryRegionListByParentId")
    ResultMessage queryRegionListByParentId(@RequestParam(name="parentId") Long parentId);

    /*@PostMapping(value = "/region/queryInitRegionByInitRegionId")
    ResultMessage queryInitRegionByInitRegionId(@RequestParam(name="regionId") Long regionId);*/

    @PostMapping(value = "/region/queryRegionById")
    ResultMessage queryRegionById(@RequestParam(name = "regionId") Long regionId);

    /**
     * 查询所有的省级直管县或者市
     * @return
     */
    @PostMapping(value = "/region/queryMunicipalityArea")
    ResultMessage queryMunicipalityArea();
}
