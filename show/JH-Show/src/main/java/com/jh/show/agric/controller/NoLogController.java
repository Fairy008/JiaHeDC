package com.jh.show.agric.controller;

import org.apache.log4j.Logger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.jh.vo.ResultMessage;
import com.jh.show.agric.vo.LoginParam;
import com.jh.show.agric.entity.AccountMacEntity;
import com.jh.biz.persist.IBaseService;
import com.jh.biz.persist.BaseService;
import com.jh.show.agric.service.ILoginService;


@Api(value = "NoLogController Service Interface",description="登录")
@RestController
@RequestMapping("/nolog/user")
public class NoLogController{
	private static Logger logger = Logger.getLogger(NoLogController.class);

	@Autowired 
	private ILoginService loginService;

	@ApiOperation(value="App账号登录",notes="用于App通过账号、密码、及设备MAC地址登录" )
	@PostMapping("/loginForApp")
	public ResultMessage login(@ApiParam @RequestBody LoginParam loginParam){
		return loginService.loginForApp(loginParam);
	}

}