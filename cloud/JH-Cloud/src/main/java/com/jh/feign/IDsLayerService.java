package com.jh.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
     * 发布图层接口
     * @param voMap
     * @return
     * @version<1> 2018-08-01 lcw : Created.
     */
    @PostMapping("/layer/saveOrUpdate")
    ResultMessage saveOrUpdate(@RequestBody Map<String, Object> voMap);
}
