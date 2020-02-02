package com.jh.layer.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.constant.PropertiesConstant;
import com.jh.layer.entity.DsGeoStyle;
import com.jh.layer.mapping.IDsGeoStyleMapper;
import com.jh.layer.model.DsGeoStyleParam;
import com.jh.layer.service.IDsGeoStyleService;
import com.jh.util.PropertyUtil;
import com.jh.util.geoserver.GeoServerUtil;
import com.jh.vo.ResultMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * 1.样式管理实现类
 *
 * @version <1> 2018-06-19 15:36 zhangshen: Created.
 */
@Service
@Transactional
public class DsGeoStyleServiceImpl implements IDsGeoStyleService{

    @Autowired
    private IDsGeoStyleMapper dsGeoStyleMapper;

    /**
     * Description: 样式查询列表
     * @param dsGeoStyleParam
     * @return
     * @version <1> 2018/6/19 15:37 zhangshen: Created.
     */
    @Override
    public PageInfo<DsGeoStyle> findDsGeoStylePageInfo(DsGeoStyleParam dsGeoStyleParam) {
        PageHelper.startPage(dsGeoStyleParam.getPage(), dsGeoStyleParam.getRows());
        List<DsGeoStyle> list = dsGeoStyleMapper.findDsGeoStylePageInfo(dsGeoStyleParam);
        return new PageInfo<DsGeoStyle>(list);
    }

    /**
     * Description: 查询geoserver工作区列表, 将配置文件中config.properties配置的geoserver工作区 放在list第一个
     * @param
     * @return ResultMessage
     * @version <1> 2018/6/20 9:24 zhangshen: Created.
     */
    @Override
    public ResultMessage findGeoWorkspaceList() {
        //获取config.properties配置的geoserver默认工作区
        //String defWorkspace = PropertyUtil.getPropertiesForConfig("WORKSPACE_DATACENTER");
        String defWorkspace = PropertyUtil.getPropertiesForConfig("WORKSPACE_DATACENTER", PropertiesConstant.GEOSERVER_CONFIG);
        //获取geoserver工作区列表
        GeoServerUtil geoServerUtil = new GeoServerUtil();
        ResultMessage result = geoServerUtil.getAllWorkspace();
        List<String> workspaces = new ArrayList<String>();
        if(result.isFlag()){
            List<String> list = (List<String>)result.getData();
            if(list != null && list.size() > 0){
                for(int i = 0 ; i < list.size(); i++){
                    String workspace = list.get(i);
                    workspaces.add(i , workspace);
                    //调换位置，其他不变
                    if(StringUtils.isNotBlank(defWorkspace) && defWorkspace.equals(workspace)){
                        workspaces.add(i, workspaces.get(0));
                        workspaces.add(0, workspace);
                    }
                }
//                if(StringUtils.isNotBlank(defWorkspace) && list.contains(defWorkspace)){
//                    list.add(0, list.remove(list.indexOf(defWorkspace)));//将配置的默认工作区放第一个
//                }
            }
        }
        return ResultMessage.success(workspaces);
    }

    /**
     * Description: 根据id查询样式信息
     * @param styleId
     * @return ResultMessage 样式信息
     * @version <1> 2018/6/20 11:13 zhangshen: Created.
     */
    @Override
    public ResultMessage findDsGeoStyleById(Integer styleId) {
        DsGeoStyleParam dsGeoStyleParam = new DsGeoStyleParam();
        dsGeoStyleParam.setStyleId(styleId);
        List<DsGeoStyle> list = dsGeoStyleMapper.findDsGeoStylePageInfo(dsGeoStyleParam);
        return ResultMessage.success(list);
    }

    /**
     * Description: 保存样式信息
     * @param request
     * @param dsGeoStyle
     * @return ResultMessage
     * @version <1> 2018/6/20 13:34 zhangshen: Created.
     */
    @Override
    public ResultMessage saveStyleInfo(HttpServletRequest request, DsGeoStyle dsGeoStyle) {
        ResultMessage result = new ResultMessage();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multfile = multipartRequest.getFile("styleFile");

        //将multfile装换成file , 但这种办法会出现一个问题，会在项目目录下自动生成一个临时的file文件，后面需要删除
        //暂时没找到好的方法,后面补充
        File f = null;
        try{
            InputStream ins= multfile.getInputStream();
            f = new File(multfile.getOriginalFilename());
            OutputStream os = new FileOutputStream(f);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        String styleId = request.getParameter("styleId");//获取styleId
        String workspace = request.getParameter("workspace");//获取工作区
        String styleName = request.getParameter("styleName");//获取样式名称
        String styleNameCn = request.getParameter("styleNameCn");//获取样式中文名

        dsGeoStyle.setStyleName(styleName);
        dsGeoStyle.setStyleNameCn(styleNameCn);
        dsGeoStyle.setWorkspace(workspace);

        GeoServerUtil geoServerUtil = new GeoServerUtil();
        if(StringUtils.isNotBlank(styleId)){//编辑样式
//            result = geoServerUtil.updateGeoStyleInfo("edit", workspace, f, styleName);
            result = geoServerUtil.updateStyle(workspace, styleName, f);

            if(result.isFlag()){
                dsGeoStyle.setStyleId(Integer.parseInt(styleId));
                dsGeoStyleMapper.updateByPrimaryKeySelective(dsGeoStyle);
            }
        }else{//新增样式
//            result = geoServerUtil.updateGeoStyleInfo("insert", workspace, f, styleName);
            result = geoServerUtil.publishStyle(workspace, styleName, f);
            if(result.isFlag()){
                dsGeoStyleMapper.insertSelective(dsGeoStyle);
            }
        }

        File del = new File(f.toURI());
        del.delete();//删除临时文件
        return result;
    }

    /**
     * Description: 根据样式id集删除样式
     * @param styleIds 样式id集
     * @param geoStyle
     * @return
     * @version <1> 2018/6/20 16:40 zhangshen: Created.
     */
    @Override
    public ResultMessage batchDeleteStyle(String styleIds, DsGeoStyle geoStyle) {
        ResultMessage result = new ResultMessage();
        //数据转换
        JSONObject json = JSONObject.fromObject(styleIds);
        JSONArray jsonArray = JSONArray.fromObject(json.getString("styleIds"));
        Object[] ids = jsonArray.toArray();
        //登录人不为null
        if(geoStyle != null && ids.length > 0){
            GeoServerUtil geoServerUtil = new GeoServerUtil();
            for(Object id : ids){
                Integer styleId = Integer.parseInt(id.toString());
                DsGeoStyle dgs = dsGeoStyleMapper.selectByPrimaryKey(styleId);//根据id查询样式信息
                if(dgs != null){
                    result = geoServerUtil.removeStyleInWorkspace(dgs.getWorkspace(), dgs.getStyleName());
                    if(result.isFlag()){
                        //组装数据
                        DsGeoStyle dsGeoStyle = new DsGeoStyle();
                        dsGeoStyle.setStyleId(styleId);
                        dsGeoStyle.setDelFlag("0");//删除
                        dsGeoStyle.setModifier(geoStyle.getCreator());
                        dsGeoStyle.setModifierName(geoStyle.getCreatorName());
                        dsGeoStyle.setModifyTime(LocalDateTime.now());
                        dsGeoStyleMapper.updateByPrimaryKeySelective(dsGeoStyle);
                    }
                }
            }
        }
        return result;
    }


    /**
     * Description: 获取geoserver样式列表
     * @param
     * @return ResultMessage
     * @version <1> 2018/6/15 10:26 zhangshen: Created.
     */
    @Override
    public ResultMessage getGeoStyleList() {

        //查询所有geoserver列表
        List<DsGeoStyle> list = dsGeoStyleMapper.findDsGeoStylePageInfo(new DsGeoStyleParam());

        return ResultMessage.success(list);

//        GeoServerUtil geoServerUtil = new GeoServerUtil();
//        return geoServerUtil.getStylesInWorkspace();
    }

    /**
     * Description: 根据styleName查询样式信息
     * @param styleName
     * @return ResultMessage 样式信息
     * @version <1> 2018/7/31 17:13 zhangshen: Created.
     */
    @Override
    public ResultMessage findDsGeoStyleByName(String styleName) {
        DsGeoStyleParam dsGeoStyleParam = new DsGeoStyleParam();
        dsGeoStyleParam.setStyleName(styleName);
        List<DsGeoStyle> list = dsGeoStyleMapper.findDsGeoStylePageInfo(dsGeoStyleParam);
        return ResultMessage.success(list);
    }
}
