package com.jh.show.agric.service;

import com.jh.vo.ResultMessage;
import com.jh.biz.persist.IBaseService;
import com.jh.show.agric.vo.LoginParam;
import com.jh.show.agric.entity.AccountMacEntity;

public interface ILoginService extends IBaseService<AccountMacEntity> {
	/**
	* 账号、密码、MAC地址登录
	* @param accountNo :账号
	* @param pwd :密码
	* @param mac :登录设置的MAC地址
	* @version Hayden 2018-08-08 17:17:30 : Created.
	*/
	public ResultMessage loginForApp(LoginParam loginParam);
}