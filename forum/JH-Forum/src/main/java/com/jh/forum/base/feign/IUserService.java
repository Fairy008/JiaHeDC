package com.jh.forum.base.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 查询用户信息
 * @version <1> 2019/4/10 15:29 zhangshen:Created.
 */
@FeignClient(name="jh-sys")
public interface IUserService {

    @PostMapping(value = "/user/findUserInfoByPhone")
    ResultMessage findUserInfoByPhone(@RequestParam(name = "phone") String phone);
}
