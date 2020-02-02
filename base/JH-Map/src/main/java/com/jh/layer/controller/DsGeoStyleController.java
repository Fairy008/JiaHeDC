package com.jh.layer.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.BaseController;
import com.jh.layer.entity.DsGeoStyle;
import com.jh.layer.model.DsGeoStyleParam;
import com.jh.layer.service.IDsGeoStyleService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Description:
 * 1.样式管理Controller
 *
 * @version <1> 2018-06-19 15:25 zhangshen: Created.
 */
@RestController
@RequestMapping("/dsGeoStyle")
public class DsGeoStyleController extends BaseController {

    @Autowired
    private IDsGeoStyleService dsGeoStyleService;

    /**
     * Description: 查询样式列表
     * @param dsGeoStyleParam
     * @param request
     * @return PageInfo<DsGeoStyle> 样式PageInfo
     * @version <1> 2018/6/19 15:34 zhangshen: Created.
     */
    @ApiOperation(value = "查询样式列表", notes = "查询样式列表")
    @ApiImplicitParam(name = "dsGeoStyleParam",value = "样式列表查询参数",required = false, dataType = "dsGeoStyleParam")
    @PostMapping("findDsGeoStylePageInfo")
    public PageInfo<DsGeoStyle> findDsGeoStylePageInfo(DsGeoStyleParam dsGeoStyleParam, HttpServletRequest request){
        PageInfo<DsGeoStyle> pages = dsGeoStyleService.findDsGeoStylePageInfo(dsGeoStyleParam);
        return pages;
    }

    /**
     * Description: 查询geoserver工作区列表, 将配置文件中config.properties配置的geoserver工作区 放在list第一个
     * @param
     * @return List<DsGeoStyle>
     * @version <1> 2018/6/20 9:24 zhangshen: Created.
     */
    @ApiOperation(value = "查询geoserver工作区列表", notes = "查询geoserver工作区列表")
    @PostMapping("findGeoWorkspaceList")
    public ResultMessage findGeoWorkspaceList(){
        ResultMessage result = dsGeoStyleService.findGeoWorkspaceList();
        return result;
    }


    /**
     * Description: 根据id查询样式信息
     * @param styleId
     * @return ResultMessage 样式信息
     * @version <1> 2018/6/20 11:13 zhangshen: Created.
     */
    @ApiOperation(value = "根据id查询样式信息", notes = "根据id查询样式信息")
    @ApiImplicitParam(name = "styleId",value = "样式id",required = true, dataType = "Integer")
    @RequestMapping("findDsGeoStyleById")
    public ResultMessage findDsGeoStyleById(@RequestParam Integer styleId){
        ResultMessage result = dsGeoStyleService.findDsGeoStyleById(styleId);
        return result;
    }

    /**
     * Description: 新增 或 修改样式, 有styleId为修改, 无styleId为新增
     * @param request 
     * @return ResultMessage
     * @version <1> 2018/6/20 13:31 zhangshen: Created.
     */
    @ApiOperation(value="保存样式信息",notes="从请求对象中获取数据")
    @PostMapping("/saveStyleInfo")
    public @ResponseBody ResultMessage saveStyleInfo(HttpServletRequest request){
//        PermAccount permAccount = getCurrentPermAccount(request);


        Map<String, Object> userInfo = getUserInfo();

        DsGeoStyle dsGeoStyle = new DsGeoStyle();
        if(userInfo != null) {
            String styleId = request.getParameter("styleId");//获取styleId
            if(StringUtils.isBlank(styleId)){//新增
                dsGeoStyle.setCreator((Integer)userInfo.get("accountId"));
                dsGeoStyle.setCreatorName(userInfo.get("accountName") + "");
                dsGeoStyle.setCreateTime(LocalDateTime.now());
            }
            dsGeoStyle.setModifier((Integer)userInfo.get("accountId"));
            dsGeoStyle.setModifierName(userInfo.get("accountName") + "");
            dsGeoStyle.setModifyTime(LocalDateTime.now());
        }
        return dsGeoStyleService.saveStyleInfo(request, dsGeoStyle);
    }

    /**
     * Description: 根据样式ids,批量删除样式
     * @param styleIds
     * @return
     * @version <1> 2018/6/20 16:40 zhangshen: Created.
     */
    @ApiOperation("批量删除样式")
    @ApiImplicitParam(name = "styleIds",value = "样式ids",required = true,dataType = "String")
    @PostMapping("batchDeleteStyle")
    public ResultMessage batchDeleteStyle(@RequestBody String styleIds, HttpServletRequest request){

        Map<String, Object> userInfo = getUserInfo();
        DsGeoStyle geoStyle = new DsGeoStyle();
        if(userInfo != null){
            geoStyle.setCreator((Integer)userInfo.get("accountId"));
            geoStyle.setCreatorName(userInfo.get("accountName") + "");

        }




        return dsGeoStyleService.batchDeleteStyle(styleIds, geoStyle);
    }

    /**
     * Description: 获取geoserver样式列表
     * @param
     * @return ResultMessage
     * @version <1> 2018/6/15 10:26 zhangshen: Created.
     */
    @ApiOperation(value = "获取geoserver样式列表", notes = "获取geoserver样式列表")
    @PostMapping("/getGeoStyleList")
    public ResultMessage getGeoStyleList(){
        return dsGeoStyleService.getGeoStyleList();
    }

    /**
     * Description: 根据styleName查询样式信息
     * @param styleName
     * @return ResultMessage 样式信息
     * @version <1> 2018/7/31 17:13 zhangshen: Created.
     */
    @ApiOperation(value = "根据styleName查询样式信息", notes = "根据styleName查询样式信息")
    @ApiImplicitParam(name = "styleName",value = "样式名称",required = true, dataType = "String")
    @RequestMapping("findDsGeoStyleByName")
    public ResultMessage findDsGeoStyleByName(@RequestParam String styleName){
        ResultMessage result = dsGeoStyleService.findDsGeoStyleByName(styleName);
        return result;
    }
}
