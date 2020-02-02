package com.jh.show.agric.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="登录对象",description="用于App登录")
public class LoginParam{
	@ApiModelProperty(value="账号",name="账号",example="15392952557")
	private String accountNo;
	@ApiModelProperty(value="密码",name="密码",example="MTIzNDU2")
	private String pwd ;
	@ApiModelProperty(value="设备Mac地址",name="mac",example="abcdefalala")
	private String mac;

	public void setAccountNo(String accountNo){this.accountNo=accountNo;}
	public String getAccountNo(){return this.accountNo;}

	public void setPwd(String pwd){this.pwd = pwd;}
	public String getPwd(){return this.pwd;}

	public void setMac(String mac){this.mac = mac;}
	public String getMac(){return this.mac;}
}