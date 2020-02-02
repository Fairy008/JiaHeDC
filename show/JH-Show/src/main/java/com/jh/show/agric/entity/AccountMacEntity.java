package com.jh.show.agric.entity;

import javax.persistence.Table;
import javax.persistence.Column;

@Table(name = "sys_perm_device")
public class AccountMacEntity {
	@Column(name="account_id")
	private Integer accountId;
	@Column(name="mac")
	private String mac;

	public void setAccountId(Integer accountId){this.accountId=accountId;}
	public Integer getAccountId(){return this.accountId;}

	public void setMac(String mac){this.mac = mac;}
	public String getMac(){return this.mac;}
}