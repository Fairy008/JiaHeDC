package com.jh.show.report.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.jh.biz.feign.IDsReportService;
import com.jh.biz.feign.IRegionService;
import com.jh.constant.CommonDefineEnum;
import com.jh.show.report.common.ResultConstantMsg;
import com.jh.show.report.enums.ReportEnum;
import com.jh.show.report.mapping.IReportMapper;
import com.jh.show.report.mapping.IWechatLoginMapper;
import com.jh.show.report.service.IReportService;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:查询报告信息
 *
 * @version <1> 2018-06-24 wl: Created.
 */
@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private IReportMapper reportMapper;

    @Autowired
    private IWechatLoginMapper wechatLoginMapper;

    @Autowired
    private IRegionService regionService;

    @Autowired
    IDsReportService dsReportService;

    @Override
    public PageInfo<Map<String, Object>> findReportByPage(Map<String, Object> map) {
        //map.put("publishStatus", ReportEnum.PUBLISHED.getValue());//已发布
        //PageHelper.startPage(Integer.parseInt(map.get("page").toString()),Integer.parseInt( map.get("rows").toString()));
        //List<Map<String, Object>> list = reportMapper.findReportByPage(map);
        PageInfo<Map<String, Object>> list =dsReportService.findReportShowByPage(Integer.parseInt(map.get("page").toString()),Integer.parseInt( map.get("rows").toString()),ReportEnum.PUBLISHED.getValue(), Long.parseLong(map.get("regionId").toString()));
        return list;
    }

    @Override
    public Map<String,Object> findReportById(Integer reportId) {
        ResultMessage result=dsReportService.findReportShowById(reportId);
        return (Map<String,Object> )result.getData();
    }

    @Override
    public ResultMessage findTimeAxis(Map<String,Object> map) {
        map.put("publishStatus",ReportEnum.PUBLISHED.getValue());//已发布
        map.put("resolution", ResultConstantMsg.Value_Dict_Resolution);//只查询高分卫星数据
        List<Map<String, Object>> list = reportMapper.findTimeAxis(map);
        return ResultMessage.success(list);
    }

    @Override
    public ResultMessage findTimeAxisByDraw(List<Long> list) {
        List<Map<String, Object>> listTime = reportMapper.findTimeAxisByDraw(list);
        return ResultMessage.success(listTime);
    }

    @Override
    public ResultMessage findLayers(Map<String,Object> map) {
        map.put("ds", ReportEnum.DS_AREA.getValue());//分布
        map.put("publishStatus",ReportEnum.PUBLISHED.getValue());//已发布
        List<Map<String, Object>> layerList = reportMapper.findLayers(map);
        //如果
        while (layerList==null || layerList.isEmpty() && map.get("regionId") != null){
            //查询上一级区域
            Map<String,Object> parentRegion = wechatLoginMapper.findParentRegion(Long.parseLong(map.get("regionId").toString()));
            if(parentRegion == null ){
                break;
            } else {
                map.put("regionId",parentRegion.get("parent_id"));
                layerList = reportMapper.findLayers(map);
            }
        }
        if (layerList==null || layerList.isEmpty()){
            return ResultMessage.fail();
        }
        List<Map<String, Object>> cropList = reportMapper.findLayersCrop(map);
        Map<String,Object> layerCrop=new HashMap<>();
        layerCrop.put("layerList",layerList);
        layerCrop.put("cropList",cropList);

        return ResultMessage.success(layerCrop);
    }

    @Override
    public ResultMessage saveRegionByUser(Map<String,Object> map) {
        //查询该自定义的图层名称是否重复
        List<Map<String,Object>> userRegion=reportMapper.findCustomLocaleList(map);
        if(userRegion.size()!=0){
            return new ResultMessage(false, CommonDefineEnum.REQUEST_FAIL.getValue(),null, ResultConstantMsg.noRepeat);
        }
        //保存用户绘制的区域信息
        map.put("regionType",ReportEnum.DRAW_AREA.getValue());
        Integer id= reportMapper.saveRegionByUser(map);
        return ResultMessage.success(id);
    }

    @Override
    public ResultMessage findAreas(String bbox) {
        Map<String,Object> map =new HashMap<>();
        map.put("bbox",bbox);
        //查询边界区域交集
        ResultMessage resultMessage = regionService.findRegion(map);
        List<Map<String,Object>> regionList = new ArrayList<>();
        if(resultMessage.getData()!=null){
            regionList = (List<Map<String,Object>>) resultMessage.getData();
        }

        //查询分布信息
        map.put("publishStatus",ReportEnum.PUBLISHED.getValue());//已发布
        if(regionList.size()>0){
            List listRegions=new ArrayList();
            for(int i=0;i<regionList.size();i++){
                listRegions.add(Long.parseLong(regionList.get(i).get("region_id").toString()));
            }
            map.put("regionList",listRegions);
        }
        List<Map<String, Object>> list = reportMapper.findAreaByYear(map);
        return ResultMessage.success(list);
    }


    @Override
    public Map<String, Object> findCustomLocale(Map<String ,Object> map)    {

        return reportMapper.findCustomLocale(map);
    }

    @Override
    public List<Map<String, Object>> findCustomLocaleList(Map<String, Object> map) {
        List<Map<String, Object>> userAreaList=reportMapper.findCustomLocaleList(map);
        return userAreaList;

    }

    @Override
    public ResultMessage findCustomRegionArea(String bbox, Long regionId, String dataTime) {
        ResultMessage resultMessage = ResultMessage.fail(ResultConstantMsg.Msg_CustomRegionArea_Fail);;
        ResultMessage resultRegion = regionService.findRegionById(regionId);
        Map<String,Object> regionMap = new HashMap<>();
        if(resultRegion.isFlag()){
            regionMap = (Map<String,Object>) resultRegion.getData();
        }
        if (StringUtils.isBlank(bbox) || regionMap == null || StringUtils.isBlank(dataTime)){
            return ResultMessage.fail(ResultConstantMsg.Msg_CustomRegionArea_ParamError);
        }
        Map<String,String> params = new HashMap<String,String>();
        params.put("bbox",bbox);
        params.put("regionCode",regionMap.get("regionCode").toString());
        params.put("dataTime",dataTime);
        try{
            OutputStream out = null;
            BufferedReader in = null;
            String result = "";

            URL url = new URL(PropertyUtil.getPropertiesForConfig("calcAllCropAreaRestService"));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/json; charset=UTF-8");


            //设置超时时间
            con.setConnectTimeout(5000);
            con.setReadTimeout(30000);

            con.setDoOutput(true);
            con.setDoInput(true);

            out = con.getOutputStream();
            Gson gson = new Gson();
            String gsonStr = gson.toJson(params);
            out.write(gsonStr.getBytes());
            out.flush();

            in = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null){
                result += line;
            }

            if(out != null){
                out.close();
            }
            if (in != null){
                in.close();
            }
            JSONArray jsonObject = JSONArray.fromObject(result);
            resultMessage = ResultMessage.success(jsonObject);
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return resultMessage;
    }


    @Override
    public ResultMessage updateUserRegion(Map<String, Object> userRegion) {
        ResultMessage resultMessage = null;

        if (userRegion == null || !userRegion.containsKey("userId") || !userRegion.containsKey("regionId") || userRegion.get("regionId") == null){
            resultMessage = ResultMessage.fail(ResultConstantMsg.Msg_CustomLocale_ParamError);
            return resultMessage;
        }

        resultMessage = ResultMessage.fail(ResultConstantMsg.Msg_CustomLocal_ModifyFail);

        //1、更新用户关注的区域
        userRegion.put("regionType",ReportEnum.CUSTOMIZED_AREA.getValue());
        Integer updateNum = reportMapper.updateCustomLocale(userRegion);
        if (updateNum != null){
            //2、删除用户自定义的区域
            userRegion.put("regionType",ReportEnum.DRAW_AREA.getValue());
            Integer deleteNum = reportMapper.deleteCustomLocale(userRegion);
            if (deleteNum != null){
                resultMessage = ResultMessage.success(ResultConstantMsg.Msg_CustomLocal_ModifySuccess);
            }
        }

        return resultMessage;
    }

    @Override
    public ResultMessage insertUserRegion(Map<String,Object> userRegion){
        ResultMessage resultMessage = null;

        if(null == userRegion || !userRegion.containsKey("userId") || !userRegion.containsKey("regionId") || userRegion.get("regionId") == null){
            resultMessage = ResultMessage.fail(ResultConstantMsg.Msg_CustomLocal_AddRegion_ParamError);
            return resultMessage;
        }

        resultMessage = ResultMessage.fail(ResultConstantMsg.Msg_CustomLocal_Fail);

        Integer insertNum = wechatLoginMapper.setRegion(userRegion);
        if(null != insertNum && insertNum > 0){
            resultMessage = ResultMessage.success(ResultConstantMsg.Msg_CustomLocal_AddRegion_Success);
        }
        return resultMessage;
    }

}
