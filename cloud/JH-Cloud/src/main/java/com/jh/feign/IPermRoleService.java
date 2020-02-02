package com.jh.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="jh-sys")
public interface IPermRoleService {

    @PostMapping(value = "/role/getDefaultRoleId")
    public ResultMessage getDefaultRoleId();

}
