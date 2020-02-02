package com.jh.show.report.controller;

import com.jh.show.report.service.ILayerService;
import com.jh.util.PropertyUtil;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 图层数据查询接口
 * version<1> 2017-11-21 cxw: Created.
 */

@Api(value = "图层数据查询接口",description="图层数据查询接口")
@RestController
@RequestMapping("/layer")
public class LayerController {

    @Autowired
    private ILayerService layerService;

    private static Logger logger = Logger.getLogger(LayerController.class);
    /**
     *  叠加栅格影像
     * 1. 直接加载给定URL的图层信息
     * 2. 加载对URL过滤后的图层信息,如filter= "code like 'USA%' and level='2'"
     * @return  png : 返回地图
     * @version <1> 2017-11-21 cxw: Created.
     */
    @ApiOperation(value="查询区域查询Png图层数据",notes="查询区域查询Png图层数据" )
    @GetMapping("/wms")
    public @ResponseBody
    void queryGeoPng(HttpServletRequest req, HttpServletResponse res){
        //获取请求参数
        String version =  req.getParameter("VERSION");
        String service =  req.getParameter("SERVICE");
        String request =  req.getParameter("REQUEST");
        String format =  req.getParameter("FORMAT");
        String transparent =  req.getParameter("TRANSPARENT");
        String layers =  req.getParameter("layers");
//        String layers =  "JiaHeDC:ly_CHN-HU_2017_LateRice_MOD09A1_PIDDIST";
        String cql_filter =  req.getParameter("CQL_FILTER");
        try {
            if(cql_filter != null)
                cql_filter = URLEncoder.encode(cql_filter,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String crs =  req.getParameter("CRS");
        String styles =  req.getParameter("STYLES");
        String width =  req.getParameter("WIDTH");
        String height =  req.getParameter("HEIGHT");
        String bbox =  req.getParameter("BBOX");
        if(layers!=null&&!"".equals(layers)&&format!=null&&!"".equals(format)) {
            //geoserver http请求地址
            //String geoserver_Url = PropertyUtil.getPropertiesForConfig("GEOSERVER_URL");
            String geoserver_Url= CephUtils.getGeoserverUrl();
            String path = geoserver_Url + "/wms?SERVICE=" + service + "&VERSION=" + version + "&REQUEST=" + request + "&FORMAT=" + format + "&TRANSPARENT=" + transparent + "&layers=" + layers + "&STYLES=" + styles + "&WIDTH=" + width + "&HEIGHT=" + height + "&BBOX=" + bbox + "&crs=" + crs;
            if (StringUtils.isNotBlank(cql_filter)){
                path += "&cql_filter=" + cql_filter;
            }
            layerService.findGeoPng(req, res, path);
        }
        else{
            logger.error("请求参数错误");
            return ;
        }
    }


    /**
     * 查询WMS图层数据(将WMS图层过滤，构建成新的Vector Layer.)
     * @return  json : 返回图层数据
     * @version <1> 2017-11-21日 cxw: Created.
     */
    @ApiOperation(value="查询WMS图层数据",notes="查询WMS图层数据" )
    @GetMapping("/ows")
    public  Object queryGeoJson(HttpServletRequest req, HttpServletResponse res) {
        //获取geoserver http请求参数
        String typeName = req.getParameter("typeName");
        String service = req.getParameter("service");
        String request = req.getParameter("request");
        String outputFormat = req.getParameter("outputFormat");
        String cql_filter =  req.getParameter("CQL_FILTER");//.replace(" ", "%20");
        try {
            cql_filter = URLEncoder.encode(cql_filter,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(outputFormat!=null&&!"".equals(outputFormat)&&typeName!=null&&!"".equals(typeName)) {
            //geoserver http请求地址
            //String geoserver_Url = PropertyUtil.getPropertiesForConfig("GEOSERVER_URL");
            String geoserver_Url= CephUtils.getGeoserverUrl();
            String path = geoserver_Url+"/ows?typeName=" + typeName + "&service=" + service + "&request=" + request + "&CQL_FILTER=" + cql_filter + "&outputFormat=" + outputFormat;// + "&format_options=" + format_options;
            return layerService.findGeoJson(req, res, path);
        } else {
            logger.error("请求参数错误");
            return ResultMessage.fail("请求参数错误");
        }
    }


}
