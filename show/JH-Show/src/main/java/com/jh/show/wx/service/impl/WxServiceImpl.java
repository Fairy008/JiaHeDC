package com.jh.show.wx.service.impl;


import com.github.sd4324530.fastweixin.api.CustomAPI;
import com.jh.biz.feign.IDictService;
import com.jh.biz.feign.IRegionService;
import com.jh.constant.SysConstant;
import com.jh.show.wx.enums.WechatUserEnum;
import com.jh.show.wx.mapping.IBriefingReporterMapper;
import com.jh.show.wx.mapping.WXInformationMapper;
import com.jh.show.wx.mapping.WXRegionCropMapper;
import com.jh.show.wx.mapping.WXRelateAccountMapper;
import com.jh.show.wx.model.*;
import com.jh.show.wx.service.IWxService;
import com.jh.show.wx.util.SendTempReporterMsgConsumer;
import com.jh.show.wx.util.WeChatUtils;
import com.jh.show.wx.vo.BriefReporterVO;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

/**
 *  业务逻辑处理,调用微服务接口
 * @version <1> 2018-05-06 lxy： Created.
 */

@Service
public class WxServiceImpl implements IWxService{
    @Autowired
    private WXRelateAccountMapper relateAccountMapper;
    @Autowired
    private WXInformationMapper informationMapper;
    @Autowired
    private WXRegionCropMapper regionCropMapper;
    @Autowired
    private IBriefingReporterMapper briefingReporterMapper;
    @Autowired
    private IRegionService regionService;
    @Autowired
    private IDictService dictService;

    /**
     *
     * @param wechatID 微信用户编号
     * @param keyWord 搜索关键字
     * @return 返回最新的简报信息
     * @version <1> 2018-05-06 lxy： Created.
     */
    public ResultMessage queryReporterByRegionAndCrops(String wechatID, String keyWord){
        if (StringUtils.isEmpty(wechatID)){
            return ResultMessage.fail(SysConstant.Msg_Wechat_WechatId_Enpty);
        }

        Map<String,Object> param = new HashMap<String,Object>();
        List<Integer> cropIdList = new ArrayList<Integer>();
        param.put("cropIdList",cropIdList);
        //根据用户微信号，查询已绑定的关键字
        WXInformation userInformation = informationMapper.findKeyWordByWxid(wechatID);
        if(!StringUtils.isEmpty(keyWord)){
            //关键字不为空，根据关键字查询
            ResultMessage testKeyWord =  getRegionCropId(keyWord);
            if (!testKeyWord.isFlag()){
                return testKeyWord;
            }
            param = (Map<String,Object>)testKeyWord.getData();

            WXInformation information = new WXInformation();
            information.setWxId(wechatID);
            information.setPushTime(new Date());
            information.setKeyWord(keyWord);
            if (userInformation == null){
                //保存关键字
                informationMapper.insertWXAccount(information);
            } else {
                //更新发布时间 、 关键字
                informationMapper.updateInformation(information);
            }
        } else{
            //关键字为空，根据用户订阅简报  》  用户绑定过的关键字
            //   根据微信号 查询用户订阅的简报
            List<WXRegionCrop> userLegionCropList = regionCropMapper.findByWXid(wechatID);
            if (userLegionCropList != null && userLegionCropList.size() != 0){
                //用户已订阅
                param.put("regionId",userLegionCropList.get(0).getRegionId());
                for (WXRegionCrop regionCrop : userLegionCropList){
                    cropIdList.add(regionCrop.getCropId());
                }
            } else if (userInformation != null){
                //用户未订阅简报，已添加关键字
                ResultMessage testKeyWord =  getRegionCropId(userInformation.getKeyWord());
                if (!testKeyWord.isFlag()){
                    return testKeyWord;
                }
                param = (Map<String,Object>)testKeyWord.getData();
                //更新发布时间
                WXInformation information = new WXInformation();
                information.setWxId(wechatID);
                information.setPushTime(new Date());
                informationMapper.updateInformation(information);
            } else {
                // 用户未订阅简报，未添加关键字
                return ResultMessage.fail(SysConstant.Msg_Wechat_Not_BriefingOrKeyword);
            }
        }

        List<Map<String, Object>> briefingReporterList = briefingReporterMapper.findBriefingByRegionCrop(param);
        if(briefingReporterList.size() == 0){
            return ResultMessage.fail(SysConstant.Msg_Wechat_FindBriefing_Fail);
        }
        return ResultMessage.success(briefingReporterList);
    }

    /**
     * 获取用户的微信号的注册信息，是否注册和订阅简报信息
     * @param wxId 用户微信openId
     * @return 返回微信号是否注册和订阅简报信息。
     * @version <1> 2018-05-06 lxy： Created.
     */
    public ResultMessage getWeChatUserRegisterStatus(String wxId){
        if(wxId == null || StringUtils.isEmpty(wxId)){
            return ResultMessage.fail(SysConstant.Msg_Wechat_WechatId_Enpty);
        }
        WXRelateAccount wxUser= relateAccountMapper.findWxUserByWxid(wxId);
        Map<String,Object> userAndBriefMap = new HashMap<String,Object>();
        if (wxUser == null){
            return ResultMessage.fail(SysConstant.Key_Wechat_User_Not_Relate);
        }
        userAndBriefMap.put("wxUser",wxUser);//设置当前微信用户
        List<WXRegionCrop> briefingList  = regionCropMapper.findByWXid(wxId);
        if (briefingList == null || briefingList.size() == 0){
//            return ResultMessage.fail(SysConstant.Key_Wechat_Not_Subscribe);
            return ResultMessage.fail("","",userAndBriefMap);

        }
        userAndBriefMap.put("briefList",briefingList);//设置订阅的简报信息
//        return ResultMessage.success(SysConstant.Msg_Wechat_Registered_Subscribed);
        return ResultMessage.success(userAndBriefMap);
    }

    /**
     * 根据简报编号查询对应的简报
     * @param reporterId 简报编号
     * @param wechatId 微信号
     * @return 返回最新简报
     * @version <1> 2018-05-06 lxy： Created.
     */
    public ResultMessage findBriefReporterByReporterId(Long reporterId,String wechatId){
        if (reporterId == null){//为空的处理
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }
        BriefReporterVO reporterVO = briefingReporterMapper.findBriefReporterByReporterId(reporterId);//查找简报
        if(reporterVO == null){
            return ResultMessage.fail(SysConstant.Msg_Wechat_FindBriefing_Fail);
        }
        return ResultMessage.success(reporterVO);//返回简报
    }

    /**
     * 定时查询所有注册微信用户订阅的最新简报
     * @return 返回最新简报
     * @version <1> 2018-05-06 lxy： Created.
     */
    public ResultMessage findScheduleBriefReporter(){
        Map<String,List<BriefReporterVO>> briefMap =new HashMap<>();//装载微信号对应的最新简报信息容器
        List<WXRelateAccount> wxRelateAccounts = relateAccountMapper.findAllWxUsers();//获取所有已经注册的微信用户
        for(WXRelateAccount wxRelateAccount : wxRelateAccounts){
            String wxId = wxRelateAccount.getWxId();//微信编号
            //查询最新简报
            ResultMessage briefMessage = this.queryReporterByRegionAndCrops(wxId,null);
            if(!briefMessage.isFlag()) continue;
            List<BriefReporterVO> briefReporterVOs = (List<BriefReporterVO>) briefMessage.getData();
            if(briefReporterVOs.size() > 0){
                //以微信号为键，对应最新简报信息为值
                briefMap.put(wxId,briefReporterVOs);
            }
        }
        if(briefMap.size()>0){
            return ResultMessage.success(briefMap);
        }
        return ResultMessage.fail(SysConstant.Msg_Wechat_FindBriefing_Fail);
    }

    /**
     * 发送微信简报模板消息
     * @return 操作结果
     * @version <1> 2018-05-16 lxy： Created.
     */
    public ResultMessage sendTemplateMessage(){
        //获取发送模板消息的API
        CustomAPI customAPI = WeChatUtils.getCustomAPI();
        //获取所有订阅的微信用户要发送的简报信息
        ResultMessage scheduleMessage = findScheduleBriefReporter();
        //获取成功
        if(scheduleMessage.isFlag()){
            Map<String,List<Map<String,Object>>> briefMap = (Map<String, List<Map<String, Object>>>) scheduleMessage.getData();//获取最新要推送的简报。
            try{
                List<WXSentReporter> sentReporters =new ArrayList<>();//装载需要入库的简报消息实体的容器。
                //遍历所有需要发送的最新简报
                briefMap.forEach(new SendTempReporterMsgConsumer(customAPI,sentReporters));
//                new SendTempReporterMsgConsumer(customAPI,sentReporters);
                if(sentReporters.size()>0){
                    //保存已发送简报模板消息实体到数据库。
//                    String url = PropertyUtil.getPropertiesForConfig("weixin.system.url")+"/nolog/wechat/saveWxSentReporter";
//                    restTemplate.postForObject(url,sentReporters, ResultMessage.class);
                    saveWxSentReporter(sentReporters);
                }
                return ResultMessage.success("发送简报消息成功！");
            }catch (Exception e){
                e.printStackTrace();
                return ResultMessage.fail("发送简报模板消息错误，发生系统错误");
            }
        }else{
            return ResultMessage.fail("发送简报消息错误，没有最新简报！");
        }
    }

    /**
     * 根据关键字，查询区域  作物 对应的 id
     * @param keyWord 关键字
     * @return 关键字 区域和作物 正确性判断
     * @version <1> 2018-05-06 xzg： Created.
     */
    private ResultMessage getRegionCropId(String keyWord){
        if (org.apache.commons.lang3.StringUtils.isEmpty(keyWord)){
            return ResultMessage.fail(SysConstant.Msg_Wechat_Keyword_Empty);
        }

        Pattern pattern = Pattern.compile("\\s+");
        String[] keyWorldSplit = pattern.split(keyWord);
        String chinaName = keyWorldSplit[0];
        String[] cropNameArray = Arrays.copyOfRange(keyWorldSplit,1,keyWorldSplit.length);
        StringBuilder sb = new StringBuilder("");
        for(String str:cropNameArray){
            sb.append(str);
            sb.append(",");
        }
        String dictNameStr = sb.substring(0,sb.length()-1);

//        ResultMessage resultRegion = restTemplate.getForObject(PropertyUtil.getPropertiesForConfig("weixin.system.url")+"/nolog/region/queryRegionByChinaName?chinaName={chinaName}", ResultMessage.class,chinaName);
        ResultMessage resultRegion = regionService.queryRegionByChinaName(chinaName);
        List<Map<String,Object>> initRegions = (List<Map<String,Object>>)(resultRegion.getData());
        if(initRegions == null || initRegions.size() == 0){
            return ResultMessage.fail(String.format(SysConstant.Msg_Wechat_ChinaName_NotExist,chinaName));
        } else if (initRegions.size() >= 2){
            String regionName = "";
            boolean first=true;
            for(Map<String,Object> region : initRegions){
                if(first){
                    regionName = region.get("chinaName").toString();
                    first = false;
                }else{
                    regionName+=","+region.get("chinaName").toString();
                }
            }
            return ResultMessage.fail(String.format(SysConstant.Msg_Wechat_ChinaName_Multiple,chinaName,regionName));
        }

//        List<Dict> cropList = dictMapper.findDictByName(Arrays.asList(cropNameList));
//        ResultMessage resultCropList = restTemplate.getForObject(PropertyUtil.getPropertiesForConfig("weixin.system.url")+"/nolog/dict/queryDictByName?dictNameStr={dictNameStr}",ResultMessage.class, org.apache.commons.lang3.StringUtils.join(cropNameArray,","));
        ResultMessage resultCropList = dictService.queryDictByName(dictNameStr);

        List<Map<String,Object>> cropList =  (List<Map<String,Object>>)(resultCropList.getData());
        List<Integer> cropIdList = new ArrayList<Integer>();
        if (cropList == null || cropList.size() == 0){
            return ResultMessage.fail(String.format(SysConstant.Msg_Wechat_CropName_NotExist, org.apache.commons.lang3.StringUtils.join(cropList,",")));
        } else {
            for (Map<String,Object> dict : cropList){
                cropIdList.add(Integer.valueOf(dict.get("dictId").toString()));
            }
        }

        Map<String,Object> paramId = new HashMap<String,Object>();
        Map<String,Object> initReginMap = (Map<String,Object>)initRegions.get(0);
        paramId.put("regionId",Long.valueOf(initReginMap.get("regionId").toString()));
        paramId.put("cropIdList",cropIdList);
        return ResultMessage.success(paramId);
    }


//    /**
//     * 微信用户注册
//     * @param relateAccount
//     * @return
//     */
//    @Override
//    public ResultMessage addWechatUser(WXRelateAccount relateAccount) {
//        /**
//         *
//         * 注册账号 ，并添加微信号 跟 手机号 关联记录
//         */
//        if (relateAccount == null || org.apache.commons.lang3.StringUtils.isEmpty(relateAccount.getTel())
//                || org.apache.commons.lang3.StringUtils.isEmpty(relateAccount.getVerifCode()) || org.apache.commons.lang3.StringUtils.isEmpty(relateAccount.getWxId())){
//            return ResultMessage.fail(SysConstant.Request_Param_Empty);
//        }
//
//        WXRelateAccount saveAccount =  relateAccountMapper.findWxUserByWxid(relateAccount.getWxId());
//        if (saveAccount != null){
//            return ResultMessage.fail(SysConstant.Msg_Account_Registered);
//        }
//
//        RegisterEntity registerEntity = new RegisterEntity();
//        registerEntity.setPhone(relateAccount.getTel());
//        registerEntity.setPwd(PropertyUtil.getPropertiesForConfig("LOGIN_DEFAULT_PASSWORD"));//默认密码123456
//        registerEntity.setSmsVerifCode(relateAccount.getVerifCode());
//        ResultMessage returnRegister = userService.registerWechatUser(registerEntity);
//
//        if (returnRegister.isFlag()){
//            //用户账号添加成功
//            Integer insertCount = relateAccountMapper.insertRelateAccount(relateAccount);
//            if (insertCount != null && insertCount == 1){
//                return ResultMessage.success(SysConstant.Msg_Account_Register_Success);
//            } else {
//                return ResultMessage.fail(SysConstant.Msg_Account_Register_Fail);
//            }
//        } else {
//            //注册失败
//            return  returnRegister;
//        }
//    }

    @Override
    public ResultMessage addSubscribeBriefing(WXRegionCrop regionCrop) {
        //1、保存用户订阅的简报 区域、作物
        if (regionCrop == null || org.apache.commons.lang3.StringUtils.isEmpty(regionCrop.getWxId()) || regionCrop.getRegionId() == null
                || regionCrop.getCropIdList() == null || regionCrop.getCropIdList().length == 0){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }

        WXRelateAccount relateAccount = relateAccountMapper.findWxUserByWxid(regionCrop.getWxId());
        if (relateAccount == null){
            return ResultMessage.fail(WechatUserEnum.NOT_REGIST.getKey(),WechatUserEnum.NOT_REGIST.getMessage(),null);
        }

        regionCrop.setTel(relateAccount.getTel());
        regionCropMapper.deleteByTel(relateAccount.getTel());//在新增订阅信息之前就得删除原先订阅的简报信息
        Integer insertCount = regionCropMapper.insertRegionCrop(regionCrop);
        if (insertCount != null && insertCount > 0){
            return ResultMessage.success(WechatUserEnum.BRIEFING_SUCCESS.getKey(),WechatUserEnum.BRIEFING_SUCCESS.getMessage(),null);
        } else {
            return ResultMessage.fail(WechatUserEnum.BRIEFING_FAIL.getKey(),WechatUserEnum.BRIEFING_FAIL.getMessage(),null);
        }
    }

    /**
     * 保存微信用户已经推送的模板消息。
     * @param reporters 记录已发送的微信消息实体
     * @return 返回操作结果
     * @version <1> 2018-05-16 lxy：Created
     */
    @Override
    public ResultMessage saveWxSentReporter(List<WXSentReporter> reporters) {
        try{
            briefingReporterMapper.saveWXSentReporter(reporters);
            return ResultMessage.success();
        }catch(Exception e){
            e.printStackTrace();
            return ResultMessage.fail(e.getMessage());
        }

    }

    /**
     * 根据微信编号查询 微信用户已经推送的模板消息。
     * @param wxIds 多个微信编号
     * @return 返回微信用户已经推送的模板消息。
     * @version <1> 2018-05-16 lxy：Created
     */
    @Override
    public ResultMessage findWxSentReporterByWxIds(List<String> wxIds) {
        List<WXSentReporter> reporterList = briefingReporterMapper.findSentReporterByWxids(wxIds);
        if(reporterList.size() == 0){
            return ResultMessage.fail(SysConstant.Msg_Wechat_FindSentReporter_NotExist);
        }
        //按照wxId为键，WXSentReporter为值来保存数据。
        Map<String,Object> resultMap = new HashMap<>();
        for(WXSentReporter reporter:reporterList){
            String wxId = reporter.getWxId();//微信编号
            Long reporterId  = reporter.getReporterId();//简报编号
            resultMap.put(wxId+"-"+reporterId,reporter);//wxId+"-"+reporterId，组合成键，reporter做为值
        }
        return ResultMessage.success(resultMap);
    }

    /**
     * 根据微信用户id删除单个微信用户的账户信息以及关注的作物区域信息
     * @param wxId 微信用户id
     * @return
     */
    public ResultMessage removeUserRelate(String wxId){
        if(StringUtils.isEmpty(wxId)){
            return ResultMessage.fail(SysConstant.Msg_Wechat_WechatId_Enpty);
        }

        //删除用户订阅关键词
        informationMapper.delKeyWordByWxId(wxId);

        //删除用户关注的作物信息
        WXRelateAccount relateAccount = relateAccountMapper.findWxUserByWxid(wxId);

        if (null == relateAccount) return ResultMessage.success("无该用户信息");

        if (StringUtils.hasText(relateAccount.getTel())){
            regionCropMapper.deleteByTel(relateAccount.getTel());//删除原先订阅的简报信息
        }

        //删除用户账号信息
        int delStatus = relateAccountMapper.deleteWxUserById(wxId);
        if (delStatus > 0){
            return ResultMessage.success("删除用户及订阅信息成功");
        }
        return ResultMessage.fail("删除用户信息失败");
    }



}
