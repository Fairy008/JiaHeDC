package com.jh.show.report.service.impl;

import com.jh.biz.feign.INoLogService;
import com.jh.biz.feign.IRegionService;
import com.jh.enums.ImageValidateCodeEnum;
import com.jh.enums.IsEnum;
import com.jh.show.report.common.ResultConstantMsg;
import com.jh.show.report.enums.ReportEnum;
import com.jh.show.report.enums.UserEnum;
import com.jh.show.report.mapping.IWechatLoginMapper;
import com.jh.show.report.service.IReportService;
import com.jh.show.report.service.IWechatLoginService;
import com.jh.util.AccountTokenUtil;
import com.jh.util.PropertyUtil;
import com.jh.util.RedisUtil;
import com.jh.vo.ResultMessage;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jh.constant.SysConstant.Key_Login_Token;

/**
 * description:用户微信登录及信息查询
 *
 * @version <1> 2018-06-25 wl: Created.
 */
@Service
public class IWechatLoginServiceImpl implements IWechatLoginService {
    private Logger logger = Logger.getLogger(IWechatLoginServiceImpl.class);

    @Autowired
    private IWechatLoginMapper wechatLoginMapper;

    @Autowired
    private INoLogService noLogService;

    @Autowired
    private IRegionService regionService;

    @Autowired
    private IReportService reportService;


    @Override
    public ResultMessage register(Map<String, String> userMap) {
        ResultMessage result = new ResultMessage();
        String accountName = userMap.get("tel");
        String accountPwd = userMap.get("passWord");
        String confirmPwd = userMap.get("confirmPwd");
        String checkCode = userMap.get("checkCode");
        String companyName = userMap.get("companyName");
        String smsCode = userMap.get("smsCode");
        String validToken = userMap.get("validToken");
        //非空验证
        if (StringUtils.isBlank(accountName)) {//验证手机号
            result.setMsg(IsEnum.Error.getValue());
            result.setMsg(ResultConstantMsg.noPhone);
            return result;
        }
        if (StringUtils.isBlank(accountPwd)) {//验证密码
            result.setMsg(IsEnum.Error.getValue());
            result.setMsg(ResultConstantMsg.noPwd);
            return result;
        }
        if (StringUtils.isBlank(checkCode)) {//验证图片验证码
            result.setMsg(IsEnum.Error.getValue());
            result.setMsg(ResultConstantMsg.noImgCode);
            return result;
        }
        if (StringUtils.isBlank(companyName)) {//验证公司名称
            result.setMsg(IsEnum.Error.getValue());
            result.setMsg(ResultConstantMsg.noCompany);
            return result;
        }

        String token= ImageValidateCodeEnum.Web_Image_code.getRedisCode()+validToken;
        String serverVerifyCode = RedisUtil.get(token);
        if (StringUtils.isBlank(serverVerifyCode)) {//验证图片验证码是否正确
            return ResultMessage.fail(ResultConstantMsg.imgCodeTimeOut);
        }
        if(!serverVerifyCode.equalsIgnoreCase(checkCode)){
            return ResultMessage.fail(ResultConstantMsg.imgCodeError);
        }

        //判断用户是否已经注册过私有云平台
        Boolean isRegister = Boolean.parseBoolean(noLogService.checkAccountExists(accountName).getData().toString());

        if(isRegister){ //用户已经注册过   --- 返回提示
            result.setCode(IsEnum.Error.getValue());
            result.setMsg(UserEnum.AREADY_REGISTER.getMesasge());
        }else{ //用户未注册

            //调用私有云平台的注册方法
            result = noLogService.registerRelateWx(accountName,accountPwd,confirmPwd,companyName,smsCode);

        }
        return  result;
    }

    @Override
    public ResultMessage setRegion(Map<String, Object> requestParam) {
        ResultMessage result = new ResultMessage();
        Long regionId = Long.parseLong(requestParam.get("regionId").toString());
        if (regionId == null) {
            result.setMsg(IsEnum.Error.getValue());
            result.setMsg(ResultConstantMsg.noAreaOfInterest);
            return result;
        }

        Map<String ,Object> map=new HashMap<>();
        Map<String, Object> mapt = AccountTokenUtil.getUserInfoFromRedis(requestParam.get("AccessToken").toString());
        map.put("userId",mapt.get("accountId"));
        map.put("regionType", ReportEnum.CUSTOMIZED_AREA.getValue());
        //查询是否已经设置过区域，如果已经设置过则不可再次添加
        List<Map<String,Object>> userRegion = reportService.findCustomLocaleList(map);


        result = ResultMessage.fail(ResultConstantMsg.Msg_CustomLocal_Fail);
        map.put("regionId", Long.parseLong(requestParam.get("regionId").toString()));
        if(userRegion.size()>0){

            result = reportService.updateUserRegion(map);
        } else if (requestParam != null && requestParam.containsKey("regionId")){
            //添加用户关注区域
            result = reportService.insertUserRegion(map);
        }

        Map<String, Object> userInfo = new HashMap<String,Object>();
        mapt.put("region_id",regionId);
        //查询区域的中文名  返回
        // 用户已关注区域，进行关注区域修改
        ResultMessage resultRegion = regionService.findRegionById(regionId);
        Map<String,Object> region = new HashMap<>();
        if(resultRegion.isFlag()){
            region = (Map<String,Object>) resultRegion.getData();
        }
        mapt.put("chinaName",region.get("chinaName"));
        Integer expireSecond = Integer.valueOf(PropertyUtil.getPropertiesForConfig("User_Login_Expire"));
        userInfo.put("userInfo",mapt);
        RedisUtil.setJsonStr( requestParam.get(Key_Login_Token).toString(),userInfo,expireSecond);


        return result;
    }

    @Override
    public ResultMessage resetAccountPwd(Map<String, Object> requestParam) {
        ResultMessage result = new ResultMessage();
        //参数判断
        String accountName = requestParam.get("tel").toString();//手机号
        String accountPwd = requestParam.get("passWord").toString();//新密码
        String confirmPwd = requestParam.get("confirmPwd").toString();//确认密码
        String checkCode = requestParam.get("checkCode").toString();//短信验证码
        //判断该账号是否已经注册  true为已注册  flase 为未注册
        Boolean isRegister = Boolean.parseBoolean(noLogService.checkAccountExists(accountName).getData().toString());
        if (StringUtils.isBlank(accountName)) {
            result.setMsg(IsEnum.Error.getValue());
            result.setMsg(ResultConstantMsg.noPhone);
            return result;
        }
        //判断手机号是否已经注册
        if(!isRegister){
            result.setMsg(IsEnum.Error.getValue());
            result.setMsg(ResultConstantMsg.noRegistered);
            return result;
        }

        if (StringUtils.isBlank(accountPwd)) {
            result.setMsg(IsEnum.Error.getValue());
            result.setMsg(ResultConstantMsg.noNewPwd);
            return result;
        }
        if (StringUtils.isBlank(confirmPwd)) {
            result.setMsg(IsEnum.Error.getValue());
            result.setMsg(ResultConstantMsg.noConfirmPwd);
            return result;
        }
        if(!accountPwd.equals(confirmPwd)){
            result.setMsg(IsEnum.Error.getValue());
            result.setMsg(ResultConstantMsg.pwdDifferent);
            return result;
        }
        if (StringUtils.isBlank(checkCode)) {
            result.setMsg(IsEnum.Error.getValue());
            result.setMsg(ResultConstantMsg.noSmsCode);
            return result;
        }
        //检查验证码是否正确
        ResultMessage validCodeResult=noLogService.checkPhoneCode(accountName,checkCode);
        if(!validCodeResult.isFlag()){
            return validCodeResult;
        }
        //修改用户密码
        result = noLogService.resetAccountPwd(accountName,accountPwd,checkCode);

        return result;
    }

    @Override
    public ResultMessage relateWechat(Map<String, String> wechatAccount) {

        String openId = wechatAccount.get("openId");//微信id
        String accountName = wechatAccount.get("tel");//手机号
        String accountPwd = wechatAccount.get("passWord");//密码可能为空
        String confirmPwd = wechatAccount.get("confirmPwd");//确认密码
        //String imgCode = wechatAccount.get("imgCode");//图片验证码
        String smsCode = wechatAccount.get("verificationCode");//手机验证码
        String company=wechatAccount.get("company");//公司名称
        String validToken = wechatAccount.get("validToken");//验证图片验证码

        //首先 判断 微信id 手机号 公司名称  短信验证码是否为空   短信验证码是否正确
        ResultMessage result = new ResultMessage();
        if(StringUtils.isBlank(openId)){
            return ResultMessage.fail(ResultConstantMsg.noWechatLogin);
        }
        if (StringUtils.isBlank(accountName)) {
            return ResultMessage.fail(ResultConstantMsg.noPhone);
        }
        if (StringUtils.isBlank(company)) {
            return ResultMessage.fail(ResultConstantMsg.noCompany);
        }
       /* if (StringUtils.isBlank(imgCode)) {
            return ResultMessage.fail("请输入图片验证码");
        }

        String token= ImageValidateCodeEnum.Web_Region_Image.getRedisCode()+validToken;
        String serverVerifyCode = RedisUtil.get(token);
        if (null == serverVerifyCode) {
            return ResultMessage.fail("图形验证码已过期");
        }
        if(!serverVerifyCode.equalsIgnoreCase(imgCode)){
            return ResultMessage.fail("图形验证码错误");
        }*/

        if (StringUtils.isBlank(smsCode)) {
            return ResultMessage.fail(ResultConstantMsg.noSmsCode);
        }

        //检查验证码是否正确
        ResultMessage validCodeResult=noLogService.checkPhoneCode(accountName,smsCode);
        if(!validCodeResult.isFlag()){//短信验证不通过
            return validCodeResult;
        }

        //判断用户是否已经注册过私有云平台
        Boolean isRegister = Boolean.parseBoolean(noLogService.checkAccountExists(accountName).getData().toString());

        if(isRegister){ //用户已经注册过私有云平台   --- 设置用户的wechat_id
            result = noLogService.setWechatId(accountName,openId);
        }else{ //用户未注册过私有云平台   --- 注册私有云平台  并保存用户的wechat_id

            //未注册过的用户必须设置密码  且必须确认密码  两次密码必须一致
            if(StringUtils.isBlank(accountPwd)){
                return ResultMessage.fail(ResultConstantMsg.noPwd);
            }
            if(StringUtils.isBlank(confirmPwd)){
                return ResultMessage.fail(ResultConstantMsg.noConfirmPwd);
            }
            if(!accountPwd.equals(confirmPwd)){
                return ResultMessage.fail(ResultConstantMsg.pwdDifferent);
            }
            //调用私有云平台的注册方法
            noLogService.registerRelateWx(accountName,accountPwd,confirmPwd,company,smsCode);
            //设置微信id
            noLogService.setWechatId(accountName,openId);

        }

        //查询用户基础信息  生成token 并且返回
//        result=noLogService.loginForWx(accountName,openId,regionId);
//        Map<String,Object> mapUser= ( Map<String,Object>)result.getData();//获取token信息
//        String accountToken = mapUser.get(Key_Login_Token).toString();
//        Map<String,Object> mapT =new HashMap<String,Object>();
//        mapT.put(Key_Login_Token,accountToken);
//        if(result.isFlag()){
//            //判断用户是否已经 关注过  区域
//            Map<String, Object> map  = AccountTokenUtil.getUserInfoFromRedis(accountToken);
//            if(map.get("region_id").toString()==null || "".equals(map.get("region_id").toString()) || "null".equals(map.get("region_id").toString()) ){
//                //未关注区域
//                result.setCode("noRelateRegion");
//            }
//        }
//        result.setData(mapT);


        ResultMessage resultWx = loginForWx(accountName,openId);
        return resultWx;
    }

    @Override
    public ResultMessage wechatLogin(String code, String state) {
        //1、判断微信登录是否成功
        if (code == null || StringUtils.isBlank(code)){
            //登录失败
            return ResultMessage.fail("loginFail",ResultConstantMsg.Msg_Wechatlogin_Fail);
        }
        //获取微信用户唯一标识码
        Map<String,Object> wechatMap = getWechatAccessToken(code);
        if (wechatMap == null || !wechatMap.containsKey("openId") || StringUtils.isBlank(wechatMap.get("openId").toString())){
            //扫码登录失败
            return ResultMessage.fail("loginFail",ResultConstantMsg.Msg_Wechatlogin_Fail);
        }

        String openId = wechatMap.get("openId").toString();

        //2、判断用户是否已关联手机号
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("wechatId",openId);
        Map<String,Object> account = findUser(params);
        if (account == null || account.isEmpty()){
            ResultMessage resultMessage=ResultMessage.fail();
            Map<String,Object> mapT =new HashMap<String,Object>();
            mapT.put("openId",openId);
            resultMessage.setCode("noRegister");
            resultMessage.setData(mapT);
            //用户未注册
            return resultMessage;
        }

        ResultMessage result = loginForWx(account.get("account_name").toString(),openId);
        if("noRelateRegion".equals(result.getCode())){
            result.setFlag(false);
        }

        return result;
    }



    public ResultMessage loginForWx(String accountName, String openId) {

        ResultMessage result = queryRegionByAccountNo(accountName);
        if (result.isFlag()) {
            Map<String, Object> region = (Map<String, Object>) result.getData();
            Long regionId = null;
            if (region != null && region.size() > 0) {
                regionId = region.get("region_id") == null ? null : (Long) region.get("region_id");
            }


            //登录成功返回token信息
            result = noLogService.loginForWx(accountName, openId, regionId);
            if (result.isFlag()) {
                if (regionId == null) {
                    result.setCode("noRelateRegion");
                }
            }

        }
        return result;
    }




    /**
     * 获取微信用户 的  accessToken
     * @param code
     * @return
     */
    private Map<String,Object> getWechatAccessToken(String code){
        String appId = PropertyUtil.getPropertiesForConfig("APP_ID");
        String secret = PropertyUtil.getPropertiesForConfig("APP_SECRET");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";

        Map<String,Object> accountMap = null;
        try{
            URL localUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection)localUrl.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            if (in != null){
                in.close();
            }
            con.disconnect();
            JSONObject jsonObject = JSONObject.fromObject(content.toString());
            String AccessTokenWx = jsonObject.getString("access_token");
            String openId = jsonObject.getString("openid");
            accountMap  = new HashMap<String,Object>();
            accountMap.put("openId",openId);
            accountMap.put("AccessTokenWx",AccessTokenWx);
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return accountMap;
    }

    @Override
    public Map<String,Object>  findUser(Map<String,Object> map) {
        Map<String,Object> userMap=wechatLoginMapper.findUser(map);
        if(userMap!=null){
            //根据id查询定制的区域
            map.put("regionType", ReportEnum.CUSTOMIZED_AREA.getValue());
            map.put("accountId", userMap.get("account_id"));
            Map<String,Object> mapRegion=wechatLoginMapper.checkRegion(map);
            if(mapRegion != null && mapRegion.get("region_id")!=null){//如果定制区域不为空 返回用户定制的区域名称
                Map<String,Object> mapParent=wechatLoginMapper.findParentRegion(Long.parseLong(mapRegion.get("region_id").toString()));
                userMap.put("regionCode",mapParent.get("region_code").toString());
                userMap.put("region_id",mapRegion.get("region_id"));
                String chinaName=mapParent.get("china_name").toString();
                userMap.put("chinaName",chinaName(Integer.parseInt(mapParent.get("level").toString()),Long.parseLong(mapParent.get("parent_id").toString()),mapParent.get("china_name").toString()));
            }
        }
        return userMap;
    }



    @Override
    public Map<String, Object> checkRegion(Map<String, Object> map) {
        map.put("regionType", ReportEnum.CUSTOMIZED_AREA.getValue());
        return wechatLoginMapper.checkRegion(map);
    }


    @Override
    public ResultMessage queryRegionByAccountNo(String accountNo){
        Map<String,Object> voMap = new HashMap<String,Object>();
        voMap.put("regionType", ReportEnum.CUSTOMIZED_AREA.getValue());
        voMap.put("accountName", accountNo);
        return ResultMessage.success(wechatLoginMapper.queryRegionWithAccount(voMap));
    }

    @Override
    public ResultMessage sendSmsForRegister(String tel, String imgCode, String validToken) {
        //验证图形验证码是否正确
        String token= ImageValidateCodeEnum.Web_Image_code.getRedisCode()+validToken;
        String serverVerifyCode = RedisUtil.get(token);
        if (StringUtils.isBlank(serverVerifyCode)) {
            return ResultMessage.fail(ResultConstantMsg.imgCodeTimeOut);
        }
        if(!serverVerifyCode.equalsIgnoreCase(imgCode)){
            return ResultMessage.fail(ResultConstantMsg.imgCodeError);
        }
        //判断该账号是否已经注册  true为已注册  flase 为未注册
        Boolean isRegister = Boolean.parseBoolean(noLogService.checkAccountExists(tel).getData().toString());
        if(isRegister){//已经注册的不发送短信
            ResultMessage resultMessage = new ResultMessage();
            resultMessage.setFlag(false);
            resultMessage.setMsg(ResultConstantMsg.alreadyRegister);
            return  resultMessage;
        }else{
            return noLogService.findSmsValidCode(tel);
        }

    }


    @Override
    public ResultMessage loginForProduct(String accountNo, String passWord) {


        if(StringUtils.isBlank(accountNo)){
            return ResultMessage.fail(ResultConstantMsg.noPhone);
        }
        if(StringUtils.isBlank(passWord)){
            return ResultMessage.fail(ResultConstantMsg.noPwd);
        }


        ResultMessage result = queryRegionByAccountNo(accountNo);
        if(result.isFlag()){
            Map<String,Object> region = (Map<String,Object>)result.getData();
            Long regionId = null;
            if(region != null && region.size() > 0){
                regionId = region.get("region_id") == null ? null : (Long)region.get("region_id");
            }
            return noLogService.loginForProduct(accountNo, passWord, regionId);
        }

        return ResultMessage.fail(ResultConstantMsg.loginFail);
    }

    String chinaName(Integer level,Long parentId,String chinaName){
        if(level!=2){
            Map<String,Object> mapParent=wechatLoginMapper.findParentRegion(parentId);
            return chinaName(Integer.parseInt(mapParent.get("level").toString()),Long.parseLong(mapParent.get("parent_id").toString()),mapParent.get("china_name").toString()+"|"+chinaName);
        }
        return chinaName;
    }

}
