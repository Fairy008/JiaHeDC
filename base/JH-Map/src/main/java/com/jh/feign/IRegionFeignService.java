package com.jh.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description:
 *
 * @version <1> 2018-07-23  lcw : Created.
 */
@FeignClient(name="jh-sys")
public interface IRegionFeignService {

    @PostMapping(value = "/region/queryRegionById")
    ResultMessage findRegionById(@RequestParam(name="regionId") Long regionId);

    @PostMapping(value = "/region/queryRegionListByParentId")
    ResultMessage queryRegionListByParentId(@RequestParam(name="parentId") Long parentId);

    @PostMapping(value = "/region/queryInitRegionByInitRegionId")
    ResultMessage queryInitRegionByInitRegionId(@RequestParam(name="regionId") Long regionId);
}
