package com.jh.layer.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.BaseController;
import com.jh.constant.PropertiesConstant;
import com.jh.layer.entity.DsLayer;
import com.jh.layer.enums.LayerTypeEnum;
import com.jh.layer.enums.PublishStatusEnum;
import com.jh.layer.model.DsLayerParam;
import com.jh.layer.model.MapDataParam;
import com.jh.layer.model.MapImageParam;
import com.jh.layer.service.IDsLayerService;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * 1.图层数据信息Controller
 *
 * @version <1> 2018-05-16 14:52 zhangshen: Created.
 */
@RestController
@RequestMapping("/layer")
public class DsLayerController extends BaseController {

    @Autowired
    private IDsLayerService dsLayerService;
    private static Logger logger = Logger.getLogger(DsLayerController.class);




    /**
     * 图层分页查询
     * @param dsLayerParam 图层查询参数
     * @return 图层分页数据
     * @version <1> 2018-06-07 lxy： Created.
     */
    @ApiOperation(value = "图层分页查询", notes = "图层分页查询")
    @ApiImplicitParam(name = "dsLayerParam",value = "图层查询参数",required = false, dataType = "DsLayerParam")
    @PostMapping("/findLayerPageInfo")
    public PageInfo<DsLayer> findLayerPageInfo(DsLayerParam dsLayerParam){
        String dsListStr = dsLayerParam.getDsListStr();//数据集编号集合--字符串格式数组，
        if(!StringUtils.isEmpty(dsListStr)){
            String []arr = dsListStr.split(";");
            if(arr.length>0){
                List<Integer> dsList = new ArrayList<Integer>();
                for(String ds : arr){
                    dsList.add(new Integer(ds));
                }
                dsLayerParam.setDsList(dsList);
            }
        }
        PageInfo<DsLayer> pages = dsLayerService.findLayerPageInfo(dsLayerParam);
        return pages;
    }


    @ApiOperation(value = "新增或修改图层",notes = "新增或修改图层")
    @ApiImplicitParam(name = "layerMap",value = "图层Map",required = true,dataType = "Map")
    @PostMapping("/saveOrUpdate")
    public ResultMessage saveOrUpdate(@RequestBody Map<String, Object> layerMap){
        ResultMessage result = ResultMessage.fail("图层发布失败");
        if(layerMap != null && layerMap.size() > 0){
            DsLayer dsLayer = new DsLayer();

            if(layerMap.get("creator") != null){
                dsLayer.setCreator((Integer)layerMap.get("creator"));//创建人编号
                dsLayer.setModifier((Integer)layerMap.get("creator"));//修改人id
            }
            if(layerMap.get("creatorName") != null){
                dsLayer.setCreatorName(layerMap.get("creatorName") + "");//创建人
                dsLayer.setModifierName(layerMap.get("creatorName") + "");//修改人
            }

            if(layerMap.get("publishStatus") != null) {
                dsLayer.setPublishStatus((Integer)layerMap.get("publishStatus"));
            }else{
                dsLayer.setPublishStatus(PublishStatusEnum.PUBLISH_STATUS_N.getValue());//默认是2202
            }
            dsLayer.setName(layerMap.get("name") + "");//设置name:  如JiaHeDC:ly_CHN-HU_2017_SummerCorn_GF1_PIDDIST
            if(layerMap.get("regionId") != null){
                dsLayer.setRegionId((Long) layerMap.get("regionId"));
            }

            if(layerMap.get("dataTime") != null){
                dsLayer.setDataTime(LocalDateTime.parse(layerMap.get("dataTime").toString()));
            }

            dsLayer.setType(LayerTypeEnum.LAYER_TYPE_TIF.getValue());//图层类型：2001 shp 矢量，2002 tif 栅格

            dsLayer.setUrl(PropertyUtil.getPropertiesForConfig("GEOSERVER_URL",PropertiesConstant.GEOSERVER_CONFIG));// 设置url

            if(layerMap.get("ds") != null){
                dsLayer.setDs((Integer) layerMap.get("ds") );//数据集
            }

            if(layerMap.get("filePath") != null){
                dsLayer.setFilePath(layerMap.get("filePath").toString()); //存储路径
            }

            if(layerMap.get("reprocessId") != null) { //再加工数据ID
                dsLayer.setReprocessId((Integer) layerMap.get("reprocessId"));
            }

            if(layerMap.get("resultimageId") != null){ //数据集影像ID
                dsLayer.setResultimageId((Integer)layerMap.get("resultimageId"));
            }

            if(layerMap.get("resolution") != null){ //分辨率
                dsLayer.setResolution((Integer)layerMap.get("resolution"));
            }
            if(layerMap.get("cropId") != null){ //作物
                dsLayer.setCropId((Integer)layerMap.get("cropId"));
            }

            result = dsLayerService.saveOrUpdateLayer(dsLayer);
        }

        return result;

    }


    /**
     * 图层样式修改
     * @param request
     * @param dsLayerParam
     * @return
     * @version <1> 2018-06-20 xzg： Created.
     */
    @ApiOperation(value = "图层样式修改",notes = "图层样式修改")
    @ApiImplicitParam(name = "dsLayerParam",value = "图层明细实体",required = true,dataType = "DsLayerParam")
    @PostMapping("/modifyStyle")
    public ResultMessage modifyStyle(HttpServletRequest request, @RequestBody DsLayerParam dsLayerParam){

        Map<String,Object> userInfo = getUserInfo();

        if(userInfo != null){
            dsLayerParam.setPublisherName(userInfo.get("accountName")+"");
            dsLayerParam.setPublisher((Integer)userInfo.get("accountId"));
        }
        return dsLayerService.modifyLayerStyle(dsLayerParam);
    }


    /**
     * 图层明细发布或撤销
     * @param request
     * @param dsLayerParam
     * @return
     * @version <1> 2018-05-16 wl： Created.
     */
    @ApiOperation(value = "图层明细发布或撤销",notes = "图层或撤销分布明细")
    @ApiImplicitParam(name = "dsLayerParam",value = "图层明细实体",required = true,dataType = "DsLayerParam")
    @PostMapping("/publish")
    public ResultMessage publish(HttpServletRequest request, @RequestBody DsLayerParam dsLayerParam){

        Map<String,Object> userInfo = getUserInfo();

        if(userInfo != null){
            dsLayerParam.setPublisherName(userInfo.get("accountName")+"");
            dsLayerParam.setPublisher((Integer)userInfo.get("accountId"));
        }
        return dsLayerService.publish(dsLayerParam);
    }

    @ApiOperation(value = "根据图层编号查找图层",notes = "根据图层编号查找图层")
    @ApiImplicitParam(name = "layerId",value = "图层编号",required = true,dataType = "Integer")
    @PostMapping("/findLayerById")
    public ResultMessage findLayerById(@RequestParam  Integer layerId){
        return dsLayerService.findDsLayerByLayerId(layerId);
    }


    /**
     * 图层删除
     * 1、从geoserver 中删除图层
     * 2、删除图层表记录
     * 3、修改再加工数据表 数据状态为未发布
     * @param layerId
     * @return
     */
    @ApiOperation(value = "根据图层编号删除图层",notes = "根据图层编号删除图层")
    @ApiImplicitParam(name = "layerId",value = "图层编号",required = true,dataType = "Integer")
    @PostMapping("/delete")
    public ResultMessage delete(@RequestParam  Integer layerId){
        return dsLayerService.deleteDsLayerByLayerId(layerId);
    }

    /**
     * Description: 报告生成图层明细列表查询
     * @param reportCreateParam
     * @return
     * @version <1> 2018/5/22 16:49 zhangshen: Created.
     */
//    @ApiOperation(value = "报告生成图层明细列表", notes = "报告生成图层明细列表")
//    @ApiImplicitParam(name = "reportCreateParam",value = "报告生成参数",required = false, dataType = "ReportCreateParam")
//    @PostMapping("/findReportCreateLayer")
//    public PageInfo<DsLayer> findReportCreateLayer(ReportCreateParam reportCreateParam){
//        PageInfo<DsLayer> pages = dsLayerService.findReportCreateLayer(reportCreateParam);
//        return pages;
//    }


    /**
     *  @description: 叠加栅格影像
     *  1. 直接加载给定URL的图层信息
     *  2. 加载对URL过滤后的图层信息,如filter= "code like 'USA%' and level='2'"
     *  @param mapImageParam 图层影像参数对象
     *  @return  png : 返回图片流
     *  @version<1> 2018-01-16 cxj: Created.
     */
    @ApiOperation(value="图层影像查询",notes="通过参数查询图层影像")
    @ApiImplicitParam(name = "mapImageParam",value = "图层影像参数对象",required = true, dataType = "MapImageParam")
    @RequestMapping(value="/wms", method= RequestMethod.GET)
    public @ResponseBody void queryGeoPng(MapImageParam mapImageParam, HttpServletRequest req, HttpServletResponse res){
        if(org.apache.commons.lang3.StringUtils.isNotBlank(mapImageParam.getLayers()) && org.apache.commons.lang3.StringUtils.isNotBlank(mapImageParam.getFORMAT())) {
            String geoserver_Url = PropertyUtil.getPropertiesForConfig("GEOSERVER_URL", PropertiesConstant.GEOSERVER_CONFIG);

            StringBuffer fullPath = new StringBuffer(geoserver_Url + "/wms");
            fullPath.append("?SERVICE=" + mapImageParam.getSERVICE());
            fullPath.append("&VERSION=" + mapImageParam.getVERSION());
            fullPath.append("&REQUEST=" + mapImageParam.getREQUEST());
            fullPath.append("&FORMAT=" + mapImageParam.getFORMAT());
            fullPath.append("&TRANSPARENT=" + mapImageParam.getTRANSPARENT());
            fullPath.append("&layers=" + mapImageParam.getLayers());
            fullPath.append("&STYLES=" + mapImageParam.getSTYLES());
            fullPath.append("&WIDTH=" + mapImageParam.getWIDTH());
            fullPath.append("&HEIGHT=" + mapImageParam.getHEIGHT());
            fullPath.append("&BBOX=" + mapImageParam.getBBOX());
            fullPath.append("&crs=" + mapImageParam.getCRS());


            if (StringUtils.isNotBlank(mapImageParam.getCQL_FILTER())){
                fullPath.append("&cql_filter=" + mapImageParam.getCQL_FILTER());
            }

            System.out.println(fullPath.toString());

            dsLayerService.findGeoPng(req, res, fullPath.toString());
        }else{
            logger.error("LayerController queryGeoPng method，request parameter error");
        }
    }

    /**
     *  @description: 查询WMS图层数据(将WMS图层过滤，构建成新的Vector Layer.)
     *  @param mapDataParam 图层数据参数对象
     *  @return  json : 返回图层数据
     *  @version<1> 2018-01-16 cxj: Created.
     */
    @ApiOperation(value="图层数据查询",notes="通过参数查询图层数据")
    @ApiImplicitParam(name = "mapDataParam",value = "图层数据参数对象",required = true, dataType = "MapDataParam")
    @RequestMapping(value="/ows", method= RequestMethod.GET)
    public  Object queryGeoJson(MapDataParam mapDataParam, HttpServletRequest req, HttpServletResponse res) {
        if(org.apache.commons.lang3.StringUtils.isNotBlank(mapDataParam.getOutputFormat()) && org.apache.commons.lang3.StringUtils.isNotBlank(mapDataParam.getTypeName())){
            String geoserver_Url = PropertyUtil.getPropertiesForConfig("GEOSERVER_URL", PropertiesConstant.GEOSERVER_CONFIG);

            StringBuffer fullPath = new StringBuffer(geoserver_Url+"/ows");
            fullPath.append("?typeName=" + mapDataParam.getTypeName());
            fullPath.append("&service=" + mapDataParam.getService());
            fullPath.append("&request=" + mapDataParam.getRequest());
            fullPath.append("&outputFormat=" + mapDataParam.getOutputFormat());
            fullPath.append("&CQL_FILTER=" + mapDataParam.getCQL_FILTER().replace(" ", "%20"));

            return dsLayerService.findGeoJson( fullPath.toString());
        }else{
            logger.error("LayerController queryGeoJson method，request parameter error");
            return null;
        }
    }



    /**
     * 根据区域、作物、时间查询图层
     * 在当前区域下查询图层，如果没有查询父级区域
     * @param
     * cropId:作物ID
     * accuracyId：分辨率
     * dsId:数据集
     * regionId：区域ID
     * dataTime：图层时间
     * @version <1> 2018-05-22 cxw: Created.
     */
    @ApiOperation(value = "根据条件查询图层名字")
    @GetMapping("/findLayer")
    public ResultMessage layer(Integer cropId,Integer dsId,Integer accuracyId,Long regionId,String dataTime ){
        return dsLayerService.findLayer(cropId,dsId,accuracyId, regionId, dataTime );
    }

    /**
     * 根据生产任务ID集合获取图层名称
     * @version <1> 2019-02-18 lijie: Created.
     */
    @ApiOperation(value = "根据条件查询图层名字")
    @GetMapping("/findLayerByProductIds")
    public ResultMessage findLayerByProductIds(@RequestParam Integer [] productIds){
        return dsLayerService.findLayerByProductIds(productIds);
    }

    /**
     * 撤回或删除图层
     * @version <1> 2019-02-18 lijie: Created.
     */
    @ApiOperation(value = "撤回图层")
    @GetMapping("/backTiff")
    public ResultMessage backTiff(@RequestParam Integer [] productIds,@RequestParam Integer type){
        return dsLayerService.backTif(productIds,type);
    }

}
