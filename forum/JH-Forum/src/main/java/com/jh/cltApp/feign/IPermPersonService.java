package com.jh.cltApp.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 查询用户信息
 * @param 
 * @return 
 * @version <1> 2019/4/19 12:08 zhangshen:Created.
 */
@FeignClient(name="jh-sys")
public interface IPermPersonService {

    /**
     *  @description: 根据用户id集合，查询用户信息列表
     *  用于查询我的关注用户列表信息
     *  @param accIds 用户id集合
     *  @return
     *  @version <1> 2019-04-19 zhangshen： Created.
     */
    @PostMapping(value = "/person/findPermPersonListByAccIds")
    ResultMessage findPermPersonListByAccIds(@RequestBody List<Integer> accIds);
}
