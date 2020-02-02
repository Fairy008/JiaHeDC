package com.jh.show.agric.service.impl;

import com.jh.show.report.enums.ReportEnum;
import com.jh.show.report.mapping.IWechatLoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jh.biz.persist.BaseService;
import com.jh.biz.feign.INoLogService;

import com.jh.vo.ResultMessage;
import com.jh.show.agric.vo.LoginParam;
import com.jh.show.AgricConstant;
import com.jh.show.agric.service.ILoginService;
import com.jh.show.agric.entity.AccountMacEntity;
import com.jh.show.agric.entity.DictEntity;
import com.jh.biz.persist.BaseService;
import com.jh.biz.persist.IBaseService;

@Service
public class LoginService extends BaseService<AccountMacEntity> implements ILoginService{
    @Autowired
    private INoLogService noLogService;
	@Autowired
	private IWechatLoginMapper wechatLoginMapper;

	/**
	* 账号、密码、MAC地址登录
	* @param accountNo :账号
	* @param pwd :密码
	* @param mac :登录设置的MAC地址
	* @version Hayden 2018-08-08 17:17:30 : Created.
	*/
	public ResultMessage loginForApp(LoginParam loginParam){
		ResultMessage msg = ResultMessage.success();
		String accountNo = loginParam.getAccountNo();
		String pwd = loginParam.getPwd();
		String mac = loginParam.getMac();

		//检查账号不能为空参数
		if (StringUtils.isBlank(accountNo)) msg = ResultMessage.fail(AgricConstant.Login_Account_Null);
		//检查密码不能为空参数
		if (StringUtils.isBlank(pwd)) msg = ResultMessage.fail(AgricConstant.Login_Pwd_Null);
		//检查MAC地址不能为空参数
		if (StringUtils.isBlank(mac)) msg = ResultMessage.fail(AgricConstant.Login_Mac_Null);

		if(msg.isFlag()){
			// 验证mac地址是否正确，正确则调用jh-sys中的登录接口，否则返回“不支持此设备登录”
			AccountMacEntity accountMac = new AccountMacEntity();
			accountMac.setMac(mac);

			List<AccountMacEntity> accountMacList = this.queryListByWhere(accountMac);
			if(accountMacList.size()>0){
				//通过feign调用jh-sys中的无图形验证码登录的接口
				msg = noLogService.loginForApp(accountNo,pwd);
				if(msg.isFlag()){
					Map<String,Object> voMap = (Map<String,Object>)msg.getData();
					Map<String,Object> userInfoMap = (Map<String,Object>)voMap.get("userInfo");
					Integer accountIdBind = (Integer)userInfoMap.get("accountId");

					//判断Mac地址是否与此账号绑定
					boolean isBind = false;
					for(AccountMacEntity accountMap : accountMacList){
						Integer accountId = accountMap.getAccountId();
						if(accountId.intValue()==accountIdBind.intValue()){
							isBind = true;
							break;
						}
					}
					//如果没有与账号绑定，则返回提示
					if(!isBind){
						msg = ResultMessage.fail(AgricConstant.Login_Mac_Bind);
					}

					//默认地址
					ResultMessage result = queryRegionByAccountNo(accountNo);
					if (result.isFlag()) {
						Map<String, Object> region = (Map<String, Object>) result.getData();
						Long regionId = null;
						if (region != null && region.size() > 0) {
							regionId = region.get("region_id") == null ? null : (Long) region.get("region_id");
							userInfoMap.put("region_id",regionId);
						}
					}
				}

			}else{
				msg = ResultMessage.fail(AgricConstant.Login_Mac_Exists);
			}
		}
		return msg;
	}

		private ResultMessage queryRegionByAccountNo(String accountNo){
			Map<String,Object> voMap = new HashMap<String,Object>();
			voMap.put("regionType", ReportEnum.CUSTOMIZED_AREA.getValue());
			voMap.put("accountName", accountNo);
			return ResultMessage.success(wechatLoginMapper.queryRegionWithAccount(voMap));
		}
}