package com.jh.manage.storage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.constant.CommonDefineEnum;
import com.jh.constant.PropertiesConstant;
import com.jh.feign.IDsLayerService;
import com.jh.manage.loader.Enum.LoaderEnum;
import com.jh.manage.storage.Enum.LayerTypeEnum;
import com.jh.manage.storage.Enum.ReprocessPubStaEnum;
import com.jh.manage.storage.entity.DataReprocess;
import com.jh.manage.storage.mapping.IDataReprocessMapper;
import com.jh.manage.storage.model.ImportReprocessParam;
import com.jh.manage.storage.service.IDataReprocessService;
import com.jh.util.PropertyUtil;
import com.jh.util.ceph.CephUtils;
import com.jh.util.ceph.LinkUtil;
import com.jh.util.geoserver.GeoServerUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * 1.再加工数据实现类
 *
 * @version <1> 2018-04-27 9:17 zhangshen: Created.
 */
@Service
@Transactional
public class DataReprocessServiceImpl implements IDataReprocessService {

    @Autowired
    private IDataReprocessMapper dataReprocessMapper;

    @Autowired
    private IDsLayerService layerService;

    //创建LINK
    LinkUtil linkUtil = LinkUtil.getInstance();

    /**
     * 导入再加工数据
     * 1.移动文件夹到import_文件夹下,然后将文件夹名称修改成 import_文件名_时间戳
     * 2.将 import_文件名_时间戳文件夹 下的文件 移动到指定目录
     * 3.生成link
     * 4.往数据库写入信息
     * @param importReprocessParam
     * @return
     * @version <1> 2018-04-25 zhangshen:Created.
     */
    @Override
    public ResultMessage importReprocess(ImportReprocessParam importReprocessParam) {
        ResultMessage result = ResultMessage.fail();
        if(importReprocessParam != null && StringUtils.isNotBlank(importReprocessParam.getFileName())){
            //1.移动文件夹到import_文件夹下,然后将文件夹名称修改成 import_文件名_时间戳
            //操作Linux，将文件（夹）添加前缀
            String importFileName = CephUtils.setPrefix(CephUtils.getAbsolutePath("") , importReprocessParam.getFileName() , CephUtils.getCephCollectionImportPrefix());
            if(importFileName != null){
                if(importReprocessParam.getFileType() == 1){ //文件夹

                    String fileName = importReprocessParam.getFileName();
                    String fileNameStr = fileName.substring(fileName.replace("\\", "/").lastIndexOf("/")+1);
                    String makeDir = CephUtils.makeReprocessDirectory(fileNameStr);//  mnt\data\resultdata\reprocess\2018\120\test
                    String oldPath = CephUtils.getAbsolutePath("") + importFileName;//原文件夹路径，如：\\192.168.1.210\mnt\module\collection\reprocess\import_\import_test_1526970574417
                    String newPath = CephUtils.getAbsolutePath("") + makeDir;//指定文件夹路径，如：\\192.168.1.210\mnt\data\resultdata\reprocess\2018\120\test
                    List<File> listFile = CephUtils.listAllFilesForDG(oldPath, null);//
                    List<DataReprocess> list = new ArrayList<DataReprocess>();
                    for(File f: listFile){
                        //4.分装数据
                        DataReprocess dataReprocess = new DataReprocess();
                        dataReprocess.setCreator(importReprocessParam.getDataReprocess().getCreator());
                        dataReprocess.setCreatorName(importReprocessParam.getDataReprocess().getCreatorName());
                        dataReprocess.setModifier(importReprocessParam.getDataReprocess().getModifier());
                        dataReprocess.setModifierName(importReprocessParam.getDataReprocess().getModifierName());
                        if(StringUtils.isNotBlank(importReprocessParam.getDataReprocess().getProductTime())){
                            dataReprocess.setDataTime(LocalDateTime.parse(importReprocessParam.getDataReprocess().getProductTime() + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        }

                        dataReprocess.setPublishStatus(ReprocessPubStaEnum.DATA_REPROCESS_PUB_STA_WFB.getId());//2701未发布
                        dataReprocess.setDataSet(importReprocessParam.getDataReprocess().getDataSet());//数据集
                        dataReprocess.setSatellite(importReprocessParam.getDataReprocess().getSatellite());//卫星
                        dataReprocess.setSensor(importReprocessParam.getDataReprocess().getSensor());//传感器
                        dataReprocess.setWords(importReprocessParam.getDataReprocess().getWords());//关键字
                        dataReprocess.setRegionId(importReprocessParam.getDataReprocess().getRegionId()); //区域
                        String fileSize = CephUtils.compileSizeUnit(CephUtils.getFileSize(f));
                        dataReprocess.setFileSize(fileSize);
                        //文件路径
                        String filePath = makeDir + File.separator + f.getName();// 文件路径\文件名
                        dataReprocess.setPath(filePath);

                        File newFile = new File(newPath + File.separator + f.getName());//新建文件
                        if(newFile.exists()){ //删除目标文件夹的文件
                            newFile.delete();
                        }
                        boolean bool = f.renameTo(newFile);
                        if (bool){
                            //3.生成link
                            linkUtil.makeDataLinkForFile(f, makeDir, importFileName);

                            list.add(dataReprocess);
                        }
                    }
                    if(null != list && list.size() > 0){
                        dataReprocessMapper.insertDataReprocess(list);
                    }
                }
                result = ResultMessage.success();
            }else{
                result = ResultMessage.fail(LoaderEnum.DATA_LOADER_PREFIX_ERROR.getValue(), LoaderEnum.DATA_LOADER_PREFIX_ERROR.getMessage());
            }
        }else{
            result = ResultMessage.fail(LoaderEnum.DATA_LOADER_STORAGE_PATH_NULL.getValue(), LoaderEnum.DATA_LOADER_STORAGE_PATH_NULL.getMessage());
        }
        return result;
    }

    /**
     * 查询再加工数据列表
     * @param importReprocessParam
     * @return
     * @version <1> 2018-04-27 zhangshen:Created.
     */
    @Override
    public PageInfo<DataReprocess> findDateReprocessByPage(ImportReprocessParam importReprocessParam){
        PageHelper.startPage(importReprocessParam.getPage(), importReprocessParam.getRows());
        List<DataReprocess> list = dataReprocessMapper.findByPage(importReprocessParam);
        return new PageInfo<DataReprocess>(list);
    }

    /**
     * Description: 根据reprocessId,查询再加工数据
     * @param reprocessId
     * @return
     * @version <1> 2018/5/24 16:01 zhangshen: Created.
     */
    @Override
    public DataReprocess findDateReprocessById(Integer reprocessId){
        return dataReprocessMapper.findDateReprocessById(reprocessId);
    }

    /**
     * 批量发布再加工数据到图层
     * 1.循环 未发布的tiff或tif 再加工数据
     * 2.调用GeoServerUtil工具类,发布每条再加工数据
     * 3.发布成功,修改再加工数据明细, 将未发布修改成发布成功; 往图层信息表中 插入一条图层信息, 状态为2202
     *   发布失败,修改再加工数据明细, 将未发布修改成发布失败
     * @param importReprocessParam
     * @return 返回记录操作结果
     * @version <1> 2018-06-06 lxy: Created.
     */
    @Override
    public ResultMessage publishReprocessData(ImportReprocessParam importReprocessParam) {
        Integer[] reprocessIds = importReprocessParam.getReprocessIds();
        String style = importReprocessParam.getStyle();

        //登录人不为null
        if( reprocessIds.length > 0 && StringUtils.isNotBlank(style)){
            List<ResultMessage> list = new ArrayList<ResultMessage>();
            for(Integer id : reprocessIds){
                List<Integer> rId = new ArrayList<Integer>();//报告id
                rId.add(id);
                DataReprocess dataReprocess = dataReprocessMapper.findDateReprocessById(id);
                GeoServerUtil geoServerUtil = new GeoServerUtil();
                String filePath = CephUtils.getAbsolutePath(dataReprocess.getPath());
                File file = new File(filePath);

                System.out.println("文件路径为：" + filePath);

                ResultMessage geoResult = geoServerUtil.publishTiff(file, style);//发布tiff图层
                list.add(geoResult);
                if(geoResult.isFlag()){//发布成功
                    dataReprocess.setModifierName(importReprocessParam.getCreatorName());//修改人
                    dataReprocess.setModifier(importReprocessParam.getCreator());//修改人id
                    dataReprocess.setPublishStatus(ReprocessPubStaEnum.DATA_REPROCESS_PUB_STA_FBCG.getId());//发布状态 发布成功
                    dataReprocessMapper.batchPublishDataReprocess(rId, dataReprocess);//更新再加工数据

//                    //通过tiffName来判断图层表是否存在该图层，如果存在，则更新，否则新增
                    Map<String,Object> layerParam = getDsLayer(geoResult, dataReprocess);
//
                    ResultMessage result =  layerService.saveOrUpdate(layerParam);

                    System.out.println(result);

                }else{//发布失败
                    dataReprocess.setModifierName(importReprocessParam.getCreatorName());//修改人
                    dataReprocess.setModifier(importReprocessParam.getCreator());//修改人id
                    dataReprocess.setPublishStatus(ReprocessPubStaEnum.DATA_REPROCESS_PUB_STA_FBSB.getId());//发布状态 发布失败
                    dataReprocessMapper.batchPublishDataReprocess(rId, dataReprocess);//更新再加工数据
                }
            }

            return new ResultMessage(true, CommonDefineEnum.SUCCESS.getValue(), CommonDefineEnum.SUCCESS.getMesasge(), list);
        }else{
            return new ResultMessage(false, CommonDefineEnum.FAIL.getValue() , CommonDefineEnum.FAIL.getMesasge(),null);
        }
    }


    /**
     * 封装图层表bean对象
     * @param r
     * @param dataReprocess
     * @return
     * @version<1>2018-07-01 lcw :Created.
     */
    public Map<String,Object> getDsLayer( ResultMessage r, DataReprocess dataReprocess){

        Map<String,Object> voMap = new HashMap<String,Object>();
        voMap.put("creator",dataReprocess.getCreator());
        voMap.put("creatorName",dataReprocess.getCreatorName() );

        voMap.put("modifier",dataReprocess.getCreator());
        voMap.put("modifierName",dataReprocess.getCreatorName() );


        voMap.put("name", r.getData().toString());//设置name:  如JiaHeDC:ly_CHN-HU_2017_SummerCorn_GF1_PIDDIST
        voMap.put("regionId", dataReprocess.getRegionId());
        voMap.put("dataTime", dataReprocess.getDataTime().toString());

        voMap.put("type", LayerTypeEnum.LAYER_TYPE_TIF.getValue());//图层类型：2001 shp 矢量，2002 tif 栅格
        voMap.put("reprocessId", dataReprocess.getReprocessId());
        voMap.put("url", PropertyUtil.getPropertiesForConfig("GEOSERVER_URL", PropertiesConstant.GEOSERVER_CONFIG));// 设置url
        voMap.put("ds", dataReprocess.getDataSet());//数据集
        voMap.put("filePath", dataReprocess.getPath());

        return voMap;

    }

    /**
     * Description: 获取geoserver样式列表
     * @param
     * @return ResultMessage
     * @version <1> 2018/6/15 10:26 zhangshen: Created.
     */
    @Override
    public ResultMessage getGeoStyleList() {
        String workspace = PropertyUtil.getPropertiesForConfig("WORKSPACE_DATACENTER", PropertiesConstant.GEOSERVER_CONFIG);
        GeoServerUtil geoServerUtil = new GeoServerUtil();
        return geoServerUtil.getStylesInWorkspace(workspace);
    }

    @Override
    public ResultMessage updateReprocessDataStatus(List<Integer> rIds, DataReprocess dataReprocess) {

        if (rIds == null || rIds.isEmpty() || dataReprocess == null){
            return ResultMessage.fail();
        }

        dataReprocessMapper.batchPublishDataReprocess(rIds,dataReprocess);
        return ResultMessage.success();
    }
}
