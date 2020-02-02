package com.jh.layer.controller.nolog;

import com.jh.base.BaseController;
import com.jh.constant.PropertiesConstant;
import com.jh.layer.model.MapDataParam;
import com.jh.layer.model.MapImageParam;
import com.jh.layer.service.IDsLayerService;
import com.jh.util.PropertyUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 * 1.图层数据信息Controller
 *
 * @version <1> 2018-05-16 14:52 zhangshen: Created.
 */
@RestController
@RequestMapping("/nolog/layer")
public class NologDsLayerController extends BaseController {

    @Autowired
    private IDsLayerService dsLayerService;
    private static Logger logger = Logger.getLogger(NologDsLayerController.class);

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
        if(StringUtils.isNotBlank(mapImageParam.getLayers()) && StringUtils.isNotBlank(mapImageParam.getFORMAT())) {
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
        if(StringUtils.isNotBlank(mapDataParam.getOutputFormat()) && StringUtils.isNotBlank(mapDataParam.getTypeName())){
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

}
