package com.jh.layer.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.constant.CommonDefineEnum;
import com.jh.constant.SysConstant;
import com.jh.enums.PublishStatus_Enum;
import com.jh.feign.IRegionFeignService;
import com.jh.layer.entity.DsLayer;
import com.jh.layer.enums.PublishStatusEnum;
import com.jh.layer.mapping.IDsLayerMapper;
import com.jh.layer.model.DsLayerParam;
import com.jh.layer.service.IDsLayerService;
import com.jh.util.DateUtil;
import com.jh.util.JsonByHttp;
import com.jh.util.StringUtil;
import com.jh.util.ceph.CephUtils;
import com.jh.util.geoserver.GeoServerUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Description:
 * 1.图层数据信息 实现类
 *
 * @version <1> 2018-05-16 14:54 zhangshen: Created.
 */
@Service
@Transactional
public class DsLayerServiceImpl implements IDsLayerService {

    @Autowired
    private IDsLayerMapper dsLayerMapper;

    @Autowired
    private IRegionFeignService regionFeignService;


    private static Logger logger = Logger.getLogger(DsLayerServiceImpl.class);

//    @Autowired
//    private IDataReprocessMapper reprocessMapper;
//    @Autowired
//    private IDataReprocessService dataReprocessService;

    /**
     *  分页查询所有的图层信息
     * @param dsLayerParam 图层查询参数
     * @return 分页记录
     * @version<1> 2018-06-07 lxy: Created.
     */
    @Override
    public PageInfo<DsLayer> findLayerPageInfo(DsLayerParam dsLayerParam){
        PageHelper.startPage(dsLayerParam.getPage(), dsLayerParam.getRows());
        List<DsLayer> list = dsLayerMapper.findByPage(dsLayerParam);
        return new PageInfo<DsLayer>(list);
    }

    /**
     * 根据图层编号查询对应的图层
     * @param layerId
     * @return 返回对应的图层（DsLayer）
     * @version<1> 2018-06-07 lxy: Created.
     */
    @Override
    public ResultMessage findDsLayerByLayerId(Integer layerId) {
        DsLayer dsLayer = dsLayerMapper.findDsLayerByLayerId(layerId);
        if(dsLayer != null){
            return ResultMessage.success(dsLayer);
        }else{
            return ResultMessage.fail();
        }
    }

    /**
     * 发布图层
     * @param dsLayerParam 图层实体
     * @return
     * @version<1> 2018-05-17 wl: Created.
     * @version<2> 2018-06-07 lxy: Updated:修改了方法
     */
    @Override
    public ResultMessage publish(DsLayerParam dsLayerParam) {
        int num = dsLayerMapper.publish(dsLayerParam);
        if(num > 0){
            return ResultMessage.success();
        }else{
            return ResultMessage.success();
        }
    }

    @Override
    public ResultMessage saveLayer(DsLayer dsLayer) {

        ResultMessage result = ResultMessage.fail();
        if(dsLayer.getRegionId() == null){
            result.setMsg("区域不能为空");
            return result;
        }


        if(StringUtils.isBlank(dsLayer.getName())){
            result.setMsg("图层名称不能为空");
            return result;
        }

        dsLayerMapper.saveLayer(dsLayer);
        result = ResultMessage.success("图层数据保存成功");

        return result;
    }

    @Override
    public ResultMessage modifyLayerStyle(DsLayerParam dsLayerParam) {
        ResultMessage result = new ResultMessage();
        if (dsLayerParam.getLayerId() == null || !StringUtil.isNotNull(dsLayerParam.getLayerStyleName())){
            result.setCode(CommonDefineEnum.FIND_OBJECT_NOT_EXISTS.getValue());
            result.setMsg(CommonDefineEnum.FIND_OBJECT_NOT_EXISTS.getMesasge());
            return result;
        }

        DsLayer layer =  dsLayerMapper.findDsLayerByLayerId(dsLayerParam.getLayerId());
        if (layer == null){
            result.setCode(CommonDefineEnum.FIND_OBJECT_NOT_EXISTS.getValue());
            result.setMsg(CommonDefineEnum.FIND_OBJECT_NOT_EXISTS.getMesasge());
            return result;
        }

//        DataReprocess dataReprocess = reprocessMapper.findDateReprocessById(layer.getReprocessId());
//        if (dataReprocess == null){
//            result.setCode(CommonDefineEnum.FIND_OBJECT_NOT_EXISTS.getValue());
//            result.setMsg(CommonDefineEnum.FIND_OBJECT_NOT_EXISTS.getMesasge());
//            return result;
//        }

        GeoServerUtil geoUtil = new GeoServerUtil();
        String layerName = layer.getName().substring(layer.getName().indexOf(":")+1);

        File layerFile = new File(CephUtils.getAbsolutePath(layer.getFilePath()));

        result = geoUtil.publishTiff(layerName, layerFile, dsLayerParam.getLayerStyleName());
//        result = util.modifyLayerStyle(layerFile,layerName,dsLayerParam.getLayerStyleName());
        return result;
    }

    @Override
    public ResultMessage updateDsLayer(DsLayer dsLayer) {
        int num = dsLayerMapper.updateDsLayer(dsLayer);
        if(num>0)
            return ResultMessage.success();
        else
            return ResultMessage.success();
    }

    /**
     * Description: 报告生成图层明细列表查询
     * @param reportCreateParam
     * @return 图层列表分页数据。
     * @version <1> 2018/5/22 16:49 zhangshen: Created.
     */
//    @Override
//    public PageInfo<DsLayer> findReportCreateLayer(ReportCreateParam reportCreateParam){
//        PageHelper.startPage(reportCreateParam.getPage(), reportCreateParam.getRows());
//        List<DsLayer> list = dsLayerMapper.findReportCreateLayer(reportCreateParam);
//        return new PageInfo<DsLayer>(list);
//    }

    /**
     * 根据图层编号删除图层
     * @param layerId 图层编号
     * @return 返回操作的记录数
     * @version<1> 2018-06-08 lxy: Created.
     */
    @Override
    public ResultMessage deleteDsLayerByLayerId(Integer layerId) {
        ResultMessage result = new ResultMessage();
        DsLayer dsLayer = dsLayerMapper.findDsLayerByLayerId(layerId);
        if (dsLayer == null){
            result.setCode(CommonDefineEnum.FIND_OBJECT_NOT_EXISTS.getValue());
            result.setMsg(CommonDefineEnum.FIND_OBJECT_NOT_EXISTS.getMesasge());
            return result;
        }

        //删除geoserver 中图层
        GeoServerUtil geoUtil = new GeoServerUtil();

        String layerName = dsLayer.getName();

        geoUtil.removeStore(layerName.substring(layerName.indexOf(":")+1).replace("ly_","store_"));

        //删除图层表中的数据
        int num = dsLayerMapper.deleteDsLayerByLayerId(layerId);

        //更新再加工数据状态为待发布
//
//        DataReprocess dataReprocess = new DataReprocess();
//        dataReprocess.setPublishStatus(ReprocessPubStaEnum.DATA_REPROCESS_PUB_STA_WFB.getId());
//        dataReprocess.setModifierName(dsLayer.getModifierName());
//        dataReprocess.setModifier(dsLayer.getModifier());
//        List<Integer> rIds = new ArrayList<Integer>();
//        rIds.add(dsLayer.getReprocessId());
//        dataReprocessService.updateReprocessDataStatus(rIds,dataReprocess);


        if(num > 0){
            return ResultMessage.success();
        }else{
            return ResultMessage.fail();
        }
    }

    /**
     * 查询图层信息
     * @param dsLayer (目前仅有的条件是resultimageId查询)
     * @return
     */
    @Override
    public ResultMessage findDsLayer(DsLayer dsLayer) {
        ResultMessage result = ResultMessage.fail();
        DsLayer layer = dsLayerMapper.findDsLayer(dsLayer);
        if(layer != null){
            result = ResultMessage.success(layer);
        }
        return result;
    }



    public void findGeoPng(HttpServletRequest req, HttpServletResponse res, String path) {
        OutputStream out = null;
        BufferedInputStream bis = null;
        try {
            req.setCharacterEncoding("UTF-8");
            out = res.getOutputStream();
            URL url = new URL(path);
            HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            res.setHeader("Content-Type", "image/png");
            StreamUtils.copy(bis,out);
        } catch (Exception e) {
            logger.error("LayerService findGeoPng method :" + e.getMessage());
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
                if(bis != null){
                    bis.close();
                }
            }catch (Exception e){
                logger.error("LayerService findGeoPng method，data flow is closed exception:" + e.getMessage());
            }
        }
    }

    public Object findGeoJson(String path) {
        JsonByHttp jsonByHttp = new JsonByHttp();
        String result= jsonByHttp.sendPost(path.replace(" ", ""),"");
//        JSONObject string_to_json = null;
//        if(!"".equals(result)&&result!=null&&result.indexOf("{")!=-1) {
//            result = result.replaceAll("null","\"null\"");
//             string_to_json = JSONObject.fromObject(result);
//             int num =  Integer.parseInt(string_to_json.getString("totalFeatures"));
//             if(num>0){
//                 return string_to_json;
//             }else {
//                 return null;
//             }
//        }else{
//            return null;
//         }
        return result;
    }


    /**
     * 根据区域、作物、时间查询图层
     * 在当前区域下查询图层，如果没有查询父级区域
     * @param
     * cropId:作物ID
     * resolution：分辨率
     * dataSet:数据集
     * regionId：区域ID
     * layerDate：图层时间
     * @version <1> 2018-05-22 cxw: Created.
     * @version<2> 2018-07-16 lcw :Created.</2>
     */
    @Override
    public ResultMessage findLayer(Integer cropId,Integer dataSet,Integer resolution,Long regionId,String layerDate) {
        if (cropId == null || dataSet==null|| resolution == null || regionId ==  null || StringUtils.isBlank(layerDate) ){
            return ResultMessage.fail(SysConstant.Msg_LayerProxy_Search_Param_Empty);
        }

        Map<String,Object> param = new HashMap<String,Object>();
        param.put("cropId",cropId);
        param.put("layerDate", DateUtil.strToDate(layerDate));
        param.put("resolution",resolution);
        param.put("ds",dataSet);
        param.put("regionId",regionId);
        param.put("publishStatus", PublishStatusEnum.PUBLISH_STATUS_Y.getValue());//已发布

        List<Map<String,Object>> layerList = new ArrayList<Map<String,Object>>();
        layerList = dsLayerMapper.findLayer(param);
        //while(layerList.isEmpty()){
        while (layerList.size()==0 && param.get("regionId") != null){
            ResultMessage regionResult = regionFeignService.findRegionById(regionId); //feign调用jh-sys的http请求
            if(regionResult.isFlag()){
                Map<String,Object> voMap = (Map<String,Object>)regionResult.getData();
                regionId =  voMap.get("parentId") == null ? null : (Long)voMap.get("parentId");
                if(regionId == null){ //如果regionId 为null， 跳出循环， 说明没有检索到符合条件的图层
                    break;
                }
                param.put("regionId",regionId);
                layerList = dsLayerMapper.findLayer(param);
            }
        }
        if (layerList != null && layerList.size() > 0){
            return ResultMessage.success(layerList.get(0));
        } else {
            return ResultMessage.fail(SysConstant.Msg_LayerProxy_Layer_Not_Exists);
        }
    }

    /**
     * 新增或更新图层
     * @param dsLayer
     * @return
     */
    @Override
    public ResultMessage saveOrUpdateLayer(DsLayer dsLayer) {

        ResultMessage layerResult = ResultMessage.fail();
        //再加工数据 或者是数据集影像数据
        if(dsLayer.getReprocessId() != null || dsLayer.getResultimageId() != null){
             layerResult = this.findDsLayer(dsLayer);
        }

        if(layerResult.isFlag()){ //修改
            DsLayer _layer = (DsLayer)layerResult.getData();
            dsLayer.setLayerId(_layer.getLayerId());
            layerResult = this.updateDsLayer(dsLayer);
        }else{//新增
            layerResult = this.saveLayer(dsLayer);
        }

        return layerResult;
    }

    @Override
    public ResultMessage findLayerByProductIds(Integer [] productIds) {
        if(productIds!=null&&productIds.length>0){
            List<DsLayer> relist= dsLayerMapper.findLayerByProductIds(productIds);
            return ResultMessage.success(relist);
        }
        return ResultMessage.success();

    }
    @Override
    public ResultMessage backTif(Integer [] productIds,Integer type){
        logger.info("撤回TIFF图层开始》》》》》》》》》》》》》》》》productIds="+productIds);
        ResultMessage result = ResultMessage.fail("撤回图层失败");
        List<ResultMessage> list = new ArrayList<ResultMessage>();
        if(productIds !=null) {
            String suffix = CephUtils.getLayerSuffix();
            GeoServerUtil geoUtil = new GeoServerUtil();
            ResultMessage results=this.findLayerByProductIds(productIds);
            List<DsLayer>layerList=(List<DsLayer>)results.getData();
            for(DsLayer layer:layerList){
                //删除geoserver 中图层
                String layerName = layer.getName();
                if(org.apache.commons.lang.StringUtils.isNotBlank(layerName)){
                    ResultMessage message =null;
                    message = geoUtil.removeStore(layerName.substring(layerName.indexOf(":") + 1).replace("ly_", "store_"));
                    //修改状态
                    if(message.isFlag()){
                        layer.setPublishStatus(PublishStatus_Enum.WAIT_PUBLISH.getCode());
                        updateDsLayer(layer);
                    }
                }
                //删除图层
                if(type==0) {
                    deleteDsLayerByLayerId(layer.getLayerId());
                }
            }
        }
        logger.info("撤回TIFF图层结束》》》》》》》》》》》》》》》》products={}"+productIds);
        return ResultMessage.success();
    }
}
