package com.jh.show.report.controller;

import com.jh.biz.controller.BaseController;
import com.jh.biz.feign.INoLogService;
import com.jh.constant.ConstantUtil;
import com.jh.enums.IsEnum;
import com.jh.show.report.common.ResultConstantMsg;
import com.jh.show.report.service.IReportService;
import com.jh.show.report.service.IWechatLoginService;
import com.jh.util.AccountTokenUtil;
import com.jh.util.CaptchaUtil;
import com.jh.util.DownloadUtil;
import com.jh.util.PropertyUtil;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * description:微信扫码登录
 *
 * @version <1> 2018-06-25 wl: Created.
 */
@RestController
@RequestMapping("nolog/wechat")
public class WechatLoginController extends BaseController {

    @Autowired
    private IWechatLoginService wechatLoginService;

    @Autowired
    private IReportService reportService;


    @Autowired
    private INoLogService noLogService;


    /**
     * 查询用户信息
     * @param
     * @return ResultMessage :
     * @version <1> 2018-06-25 wl:Created.
     */
    @ApiOperation(value = "查询用户信息",notes = "查询用户信息")
    @ApiImplicitParam(name = "id",value = "用户微信id",required = true,dataType = "String")
    @GetMapping("currentUserInfo")
    public ResultMessage CurrentUserInfo(HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        map.put("accountId",getCurrentAccountId(request));
        Map<String,Object> userMap=wechatLoginService.findUser(map);
        if(userMap!=null){
            return ResultMessage.success(userMap);
        }else {
            return ResultMessage.fail();
        }
    }


    /**
     * 用户注册
     * @param  userMap
     * @return ResultMessage :
     * @version <1> 2018-06-27 wl:Created.
     */
    @ApiOperation(value = "用户注册",notes = "用户注册")
    @ApiImplicitParam(name = "tel",value = "用户电话",required = true,dataType = "String")
    @PostMapping("register")
    public ResultMessage register(HttpServletRequest request, @RequestBody Map<String,String> userMap){
        return wechatLoginService.register(userMap);
    }

    /**
     * 用户注册
     * @param paramMap tel,passWord
     * @return ResultMessage :
     * @version <1> 2018-06-27 wl:Created.
     */
    @ApiOperation(value = "用户登录",notes = "用户登录")
    @ApiImplicitParam(name = "tel",value = "用户电话",required = true,dataType = "String")
    @PostMapping("login")
    public ResultMessage login(HttpServletRequest request, @RequestBody Map<String,String> paramMap){
        System.out.println("login===================");
        ResultMessage result = new ResultMessage();
        String tel = paramMap.get("tel");
        String passWord = paramMap.get("passWord");

        //调用JH-SYS登录方法
        result = wechatLoginService.loginForProduct(tel,passWord);
      /*  if(result.isFlag()){
            Map<String,Object> resultmap =(Map<String,Object> )result.getData();
            String token=resultmap.get(Key_Login_Token)!=null?resultmap.get(Key_Login_Token).toString():null;
            //查询用户是否已经关注区域
            Map<String, Object> map  = AccountTokenUtil.getUserInfoFromRedis(token);
            map.put("accountId",map.get("accountId"));//获取用户id
            Map<String,Object> checkRegion=wechatLoginService.checkRegion(map);
            if(checkRegion!=null && checkRegion.get("region_id")!=null){
                resultmap.put("region_id",checkRegion.get("region_id"));
            }
        }*/
        return result;
    }


    /**
     * 用户退出
     * @param request
     * @return
     */
    @GetMapping("userQuit")
    public ResultMessage userQuit(HttpServletRequest request){
        request.getSession().removeAttribute(ResultConstantMsg.userInfoSessionKey);
        return  ResultMessage.success();
    }


    /**
     * 设置用户关注区域、用户关注区域已存在进行修改
     * @return ResultMessage :
     * @version <1> 2018-06-25 wl:Created.
     */
    @ApiOperation(value = "设置用户默认区域",notes = "设置用户默认区域")
    @ApiImplicitParam(name = "map",value = "用户微信id",required = true,dataType = "Map<String,Object> map")
    @PostMapping("setUserRegion")
    public ResultMessage setUserRegion(@RequestBody Map<String,Object> requestParam, HttpServletRequest request){
       return wechatLoginService.setRegion(requestParam);
    }

    /**
     * 获取验证码
     *
     * @param request  请求对象
     * @param response 请求响应
     * @version <1> 2018-01-22 djh： Created.
     */
    @ApiOperation("获取图形验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request",value = "request请求对象",required = true, dataType = "HttpServletRequest"),
            @ApiImplicitParam(name = "response",value = "response响应对象",required = true, dataType = "HttpServletResponse")
    })
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //生成随机字串
        String verifyCode = CaptchaUtil.generateVerifyCode(4);
        request.getSession().setAttribute(ConstantUtil.VERIFY_CODE, verifyCode);
        System.out.print(request.getSession().getId());
        int w = 76, h = 30;

        CaptchaUtil.outputImage(w, h, response.getOutputStream(), verifyCode);
    }


    /**
     * 获取微信配置信息,生成二维码
     * @return
     * @version <1> 2018-07-06 xzg： Created.
     */
    @GetMapping("wechatInfo")
    public ResultMessage wechatInfo(HttpServletRequest request){
        Map<String,String> wechatInfo = new HashMap<String,String>();
        wechatInfo.put("appId", PropertyUtil.getPropertiesForConfig("APP_ID"));
        wechatInfo.put("redirectUrl", PropertyUtil.getPropertiesForConfig("BACK_URL"));
        String state = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        wechatInfo.put("state",state);
        request.setAttribute(ResultConstantMsg.wechatInfoKey,state);
        return ResultMessage.success(wechatInfo);
    }


    //微信登录
    @ApiOperation(value = "微信登录",notes = "通过扫描二维码登录系统")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",value = "微信ID",required = false, dataType = "String" ,paramType="query"),
            @ApiImplicitParam(name = "state",value = "精度",required = true, dataType = "String" ,paramType="query"),
    })
    @GetMapping("testWechatStatus")
    public ResultMessage testWechatStatus(HttpServletRequest request, @RequestParam(required = false) String code, String state){
      return  wechatLoginService.wechatLogin(code,state);

    }

    //用于微信绑定手机号
    @PostMapping("saveWechatAccount")
    public ResultMessage saveWechatAccount(HttpServletRequest request, @RequestBody Map<String,String> wechatAccount){
        return wechatLoginService.relateWechat(wechatAccount);
    }

    /**
     *
     * 判断手机号是否已经注册过
     * @param accountName 账号
     * @param verificationCode 验证码
     * @return
     *  @version <1> 2018-07-13 xzg： Created.
     */
    @GetMapping("testAccount")
    public ResultMessage testAccount(String accountName,String verificationCode,String dataMsg,HttpServletRequest request){
        ResultMessage result = new ResultMessage();
        if (StringUtils.isBlank(accountName)) {
            result.setMsg(IsEnum.Error.getValue());
            result.setMsg(ResultConstantMsg.noPhone);
            return result;
        }
        if (StringUtils.isBlank(verificationCode)) {
            result.setMsg(IsEnum.Error.getValue());
            result.setMsg(ResultConstantMsg.noSmsCode);
            return result;
        }
        //检查验证码是否正确
        ResultMessage validCodeResult=noLogService.checkPhoneCode(accountName,verificationCode);
        if(!validCodeResult.isFlag()){
            return validCodeResult;
        }
        //判断该账号是否已经注册  true为已注册  flase 为未注册
        return noLogService.checkAccountExists(accountName);
    }


//    /**
//     *
//     * 判断手机号是否已经注册过
//     * @param accountName 账号
//     * @param verificationCode 验证码
//     * @return
//     *  @version <1> 2018-07-13 xzg： Created.
//     */
//    @GetMapping("checkPhoneCodeByProduct")
//    public ResultMessage checkPhoneCodeByProduct(String accountName,String verificationCode,String dataMsg,HttpServletRequest request){
//        ResultMessage result = new ResultMessage();
//        if (StringUtils.isBlank(accountName)) {
//            result.setMsg(IsEnum.Error.getValue());
//            result.setMsg(ResultConstantMsg.noPhone);
//            return result;
//        }
//        if (StringUtils.isBlank(verificationCode)) {
//            result.setMsg(IsEnum.Error.getValue());
//            result.setMsg(ResultConstantMsg.noSmsCode);
//            return result;
//        }
//        //检查验证码是否正确
//        ResultMessage validCodeResult=noLogService.checkPhoneCodeByProduct(accountName,verificationCode);
//        if(!validCodeResult.isFlag()){
//            return validCodeResult;
//        }
//        //判断该账号是否已经注册  true为已注册  flase 为未注册
//        return noLogService.checkAccountExists(accountName);
//    }





    /**
     * 发送绑定手机号时的短信验证码
     * @param tel 电话号码
     * @version <1> 2018-08-19 wl： Created.
     */
    @ApiOperation("发送短信验证码")
    @ApiImplicitParam(name = "tel",value = "电话号码",required = true, dataType = "String")
    @GetMapping("/sendsmsToPhone")
    public ResultMessage sendsmsToPhone(String tel) {
        return noLogService.findSmsValidCode(tel);
    }


    /**
     * 发送绑定手机号时的短信验证码
     * @param tel 电话号码
     * @version <1> 2018-08-19 wl： Created.
     */
    @ApiOperation("发送短信验证码")
    @ApiImplicitParam(name = "tel",value = "电话号码",required = true, dataType = "String")
    @GetMapping("/sendSmsForRegister")
    public ResultMessage sendSmsForRegister(String tel,String imgCode,String validToken) {
       return  wechatLoginService.sendSmsForRegister(tel,imgCode,validToken);
    }



    /**
     * 发送短信验证码用于重置密码
     * @param tel 电话号码
     * @version <1> 2018-08-24 wl： Created.
     */
    @ApiOperation("发送短信验证码")
    @ApiImplicitParam(name = "tel",value = "电话号码",required = true, dataType = "String")
    @GetMapping("/sendResetMsg")
    public ResultMessage sendResetMsg( String tel) {
        return noLogService.findValidCodeForPwd(tel);
    }





    /**
     * @description: 得到当前用户对象
     * @version <1> 2018-01-17 cxj： Created.
     */
    public Map<String,Object> getCurrentPermAccount(){
        String token = AccountTokenUtil.getToken(request);
        Map<String,Object> userInfo = AccountTokenUtil.getUserInfoFromRedis(token);

        return userInfo;
    }

    /**
     * index.html中原从cookie中取值，替换
     * @param
     * @return ResultMessage :
     * @version <1> 2018-08-20 lj:Created.
     */
    @ApiOperation(value = "查询当前用户信息",notes = "查询当前用户信息")
    @GetMapping("findCurrentUserInfo")
    public ResultMessage findCurrentUserInfo(HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        if(getCurrentAccountId(request)!=null){
            map.put("accountId",getCurrentAccountId(request));
            Map<String,Object> userMap=wechatLoginService.findUser(map);
            if(userMap!=null){
                return ResultMessage.success(userMap);
            }
        }
        return ResultMessage.fail();
    }

    /**
     * 根据ID查询报告详情
     * @param  reportId
     * @return ResultMessage :
     * @version <1> 2018-06-24 wl:Created.
     */
    @ApiOperation(value = "根据ID查询报告详情",notes = "根据ID查询报告详情")
    @ApiImplicitParam(name = "reportId",value = "报告id",required = true,dataType = "Long")
    @GetMapping("findReportById")
    public void findReportById(@RequestParam Integer reportId, HttpServletResponse response){
        Map<String,Object> map=reportService.findReportById(reportId);
        if(map != null){
            String filePath = map.get("file_path").toString();
            String fileAbsolutePath = CephUtils.getAbsolutePath(filePath);
            fileAbsolutePath = fileAbsolutePath.replace("\\",File.separator);
            DownloadUtil.downloadFileStream(fileAbsolutePath, ResultConstantMsg.Value_ContentType_ShowPdf,response);
        }
    }

    /**
     * Description: 预览pdf文件报告
     * @param reportId 文件路径
     * @param response
     * @return
     * @version <1> 2018/5/16 15:26 zhangshen: Created.
     */
    @GetMapping("/previewReportPdf/{reportId}")
    public void previewReportPdf(@PathVariable Integer reportId , HttpServletResponse response){
        Map<String,Object> map=reportService.findReportById(reportId);
        if(map != null){
            String filePath = map.get("file_path").toString();
            String fileAbsolutePath = CephUtils.getAbsolutePath(filePath);
            fileAbsolutePath = fileAbsolutePath.replace("\\",File.separator);
            DownloadUtil.downloadFileStream(fileAbsolutePath, ResultConstantMsg.Value_ContentType_ShowPdf,response);
        }
    }


    /**
     * 忘记密码，用户设置新密码
     * @return ResultMessage :
     * @version <1> 2018-08-24 wl:Created.
     */
    @ApiOperation(value = "忘记密码，用户设置新密码",notes = "忘记密码，用户设置新密码")
    @ApiImplicitParam(name = "map",value = "用户微信id",required = true,dataType = "Map<String,Object> map")
    @PostMapping("resetAccountPwd")
    public ResultMessage resetAccountPwd(@RequestBody Map<String,Object> requestParam, HttpServletRequest request){
        return wechatLoginService.resetAccountPwd(requestParam);
    }



    /*@PostMapping("checkImgCode")
    public ResultMessage checkImgCode(String validToken,String imgCode){
        String token= ImageValidateCodeEnum.Web_Region_Image.getRedisCode()+validToken;
        String serverVerifyCode = RedisUtil.get(token);
        if (null == serverVerifyCode) {
            return ResultMessage.fail("图形验证码已过期");
        }
        if(!serverVerifyCode.equalsIgnoreCase(imgCode)){
            return ResultMessage.fail("图形验证码错误");
        }
        return ResultMessage.success();
    }*/

}
