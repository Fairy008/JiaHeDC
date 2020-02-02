package com.jh.data.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.enums.PublishStatusEnum;
import com.jh.briefingNew.service.impl.BriefingReportServiceImpl;
import com.jh.constant.PropertiesConstant;
import com.jh.data.entity.DsResultimage;
import com.jh.data.enums.LayerTypeEnum;
import com.jh.data.enums.ProduceStatusEnum;
import com.jh.data.enums.YNEnum;
import com.jh.data.mapping.IDsResultimageMapper;
import com.jh.data.model.ImportParam2;
import com.jh.data.service.DataSatServiceFactory;
import com.jh.data.service.IDsResultimageService;
import com.jh.enums.PublishStatus_Enum;
import com.jh.feign.IDsLayerService;
import com.jh.report.enums.DataSetEnum;
import com.jh.util.DateUtil;
import com.jh.util.PropertyUtil;
import com.jh.util.cache.IdTransformUtils;
import com.jh.util.ceph.CephUtils;
import com.jh.util.ceph.LinkUtil;
import com.jh.util.geoserver.GeoServerUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.io.Files.isDirectory;
import static com.jh.data.enums.YNEnum.YNEnum_NO;
import static com.jh.report.enums.DataSetEnum.getDataSetEnum;
import static com.jh.util.ceph.CephUtils.getCephUrl;

/**
 * Description:
 *
 * @version <1> 2018-07-02  lcw : Created.
 */
@Service
@Transactional
public class DsResultimageServiceImpl implements IDsResultimageService {

    @Autowired
    private IDsResultimageMapper resultimageDao;


    @Autowired
    private IDsLayerService layerService;

    private static Logger logger = Logger.getLogger(BriefingReportServiceImpl.class);

    /**
     * 从ceph服务器中获取未转化为入库任务的待导入数据列表
     * 1.指定目录：从config.properties中获取
     * 2.过滤文件名包含“import_"前缀的文件或文件夹
     *
     * @return
     * @version<1> 2018-03-07 lcw : Created.
     */
    @Override
    public PageInfo<ImportParam2> findImportDataFromCeph(ImportParam2 importParam) {

        List<ImportParam2> dir = list(importParam.getPath(), importParam.getIgnorePrefix());

        return setPage(importParam, dir);

    }

    /**
     * 数据集入库
     * 1.检索所有文件，并入库
     * 3.入库操作完成后，将link创建至原始目录对应的_import文件夹下
     * 4.更新数据
     *
     * @param resultimage
     * @return
     */
    @Override
    public ResultMessage saveLoader(DsResultimage resultimage) {

        ResultMessage result = ResultMessage.fail();
        //String suffix = PropertyUtil.getPropertiesForConfig("DATA_LAYER_SUFFIX"); //图层后缀
        String suffix = getCephUrl("DATA_LAYER_SUFFIX");

        String datasetImport = CephUtils.getCephDatasetImport();
//        String basePath = CephUtils.getAbsolutePath(datasetImport);  //数据集导入的基础文件夹
        String _importPath = datasetImport + File.separator + CephUtils.getCephCollectionImportPrefix();

        String storagePath = resultimage.getStoragePath();
        //将文件入库后，反向创建link至_import下对应的目录
        String linkPath = storagePath.replace(datasetImport, _importPath); //link文件路径
        CephUtils.mkdirs(CephUtils.getAbsolutePath(linkPath));

        //目标路径
        String destPath = CephUtils.makeDatasetDirectory(resultimage.getDsCode(), resultimage.getRegionCode(), resultimage.getCropCode(), resultimage.getDataTimeStr().substring(0, 4));

        List<Map<String, Object>> list = CephUtils.listAllFiles(CephUtils.getAbsolutePath(storagePath), "");
        List<String> linkList = new ArrayList<String>();
        List<DsResultimage> imageList = new ArrayList<DsResultimage>();
        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {

                DsResultimage resultimageVo = new DsResultimage();

                BeanUtils.copyProperties(resultimage, resultimageVo);
                String fileName = (String) map.get("fileName");
                String _tif = fileName.substring(fileName.lastIndexOf("."));
                String filePath = (String) map.get("filePath");
                String fileNa = (String) map.get("fileNa");

                boolean bool = CephUtils.moveFile(filePath, CephUtils.getAbsolutePath(destPath), fileNa); //移动文件

                if (bool) {
                    CephUtils.mkdirs(CephUtils.getAbsolutePath((linkPath + File.separator + fileName)).replace(fileNa, ""));
                    String link = LinkUtil.makeLink(destPath + File.separator + fileNa, linkPath + File.separator + fileName); //创建link
                    //删除文件夹
                    File removeFile = new File(filePath.replace(fileNa, ""));
                    if (removeFile.exists()) {
                        boolean b = removeFile.delete();
                        System.out.println(b);
                    }
                    linkList.add(link);
                    if (suffix.contains(_tif)) {//保存数据
                        resultimageVo.setStoragePath(destPath + File.separator + fileNa);
                        resultimageVo.setRgStatus(ProduceStatusEnum.NOACTIVE.getValue());
                        resultimageVo.setPublishStatus(PublishStatusEnum.DATA_PUB_STA_WFB.getValue()); //待发布

                        imageList.add(resultimageVo);
                    }
                } else {
                    return result = ResultMessage.fail("数据入库失败");
                }
            }

            //创建link
            boolean bool = LinkUtil.getInstance().exec(linkList);
            if (bool) {
                if (imageList != null && imageList.size() > 0) {
                    for (DsResultimage dsResultimage : imageList) {
                        resultimageDao.save(dsResultimage);
                    }
                }
                //删除文件夹
                File removeFile = new File(CephUtils.getAbsolutePath(storagePath));
                CephUtils.deleteDirAndFiles(removeFile);//删除指定路径下的文件及文件夹

                result = ResultMessage.success("数据入库成功");
            }
        }


        return result;
    }


    /**
     * 数据集影像数据分页查询
     *
     * @param dsResultimage
     * @return
     * @version<1> 2018-07-03 lcw :Created.
     */
    @Override
    public PageInfo<DsResultimage> findByPage(DsResultimage dsResultimage) {
        PageHelper.startPage(dsResultimage.getPage(), dsResultimage.getRows());
        List<DsResultimage> list = resultimageDao.findByPage(dsResultimage);
        IdTransformUtils.idTransForList(list);
        return new PageInfo<DsResultimage>(list);
    }

    /**
     * 根据ID查询数据集影像
     *
     * @param rgId
     * @return
     */
    @Override
    public ResultMessage findResultimageById(Integer rgId) {
        DsResultimage dsResultimage = resultimageDao.findById(rgId);
        IdTransformUtils.idTransForObj(dsResultimage);
        return ResultMessage.success(dsResultimage);
    }

    /**
     * 更新数据集影像
     *
     * @param resultimage
     * @return
     */
    @Override
    public ResultMessage updateResultimage(DsResultimage resultimage) {
        resultimageDao.update(resultimage);
        return ResultMessage.success("任务更新成功");
    }

    /**
     * 根据ID查询数据集影像
     *
     * @param rgId
     * @return
     */
    @Override
    public ResultMessage queryResultimage(Integer rgId) {
        DsResultimage resultimage = resultimageDao.findById(rgId);
        return ResultMessage.success(resultimage);
    }

    /**
     * 批量激活数据生产任务
     *
     * @param resultimage
     * @return
     */
    @Override
    public ResultMessage updateResultimageByIds(DsResultimage resultimage) {
        if (resultimage.getRgIds() == null) {
            return ResultMessage.fail("请勾选数据集影像数据");
        }

        resultimage.setRgStatus(ProduceStatusEnum.TOBEEXECUTED.getValue());
        resultimageDao.updateResultimageByIds(resultimage);

        return ResultMessage.success("数据生产任务激活成功");
    }

    /**
     * 发布图层
     * 1.根据ID查询对应的数据集影像数据，找到图层路径
     * 2.判断图层是否符合要求（.tif,.tiff）
     * 3.发布图层并更新图层表
     * 4.更新当前表的发布状态
     * 5.返回执行结果
     *
     * @param resultimage
     * @return
     */
    @Override
    public ResultMessage publishTif(DsResultimage resultimage) {
        ResultMessage result = ResultMessage.fail("图层发布失败");
        List<ResultMessage> list = new ArrayList<ResultMessage>();
        if (resultimage.getRgIds() != null && resultimage.getRgIds().length > 0) {
            String suffix = CephUtils.getLayerSuffix();
            GeoServerUtil geoUtil = new GeoServerUtil();

            for (Integer rgId : resultimage.getRgIds()) {
                DsResultimage resultimageVo = resultimageDao.findById(rgId);
                IdTransformUtils.idTransForObj(resultimageVo);
                //校验是否需要发布图层
                DataSetEnum denum=DataSetEnum.getDataSetEnum(resultimageVo.getDsType());
                if(denum.getIsPublish()==0){//无需发布图层
                    list.add(ResultMessage.success("发布成功",rgId));
                    //更新数据集影像表的状态
                    resultimageVo.setPublishStatus(PublishStatusEnum.DATA_PUB_STA_FBCG.getValue());//发布状态 发布成功
                    //更新状态
                    resultimageDao.updateResultimageWithPublish(resultimageVo);
                    continue;
                }
                if (resultimageVo != null && StringUtils.isNotBlank(resultimageVo.getStoragePath())) {
                    String _tif = resultimageVo.getStoragePath().substring(resultimageVo.getStoragePath().lastIndexOf("."));
                    if (suffix.contains(_tif)) { //可以发布
                        String fileName = resultimageVo.getRegionCode() + "_" + resultimageVo.getDataTime().getYear() + "_" + resultimageVo.getDataTime().getMonthValue() + "_" + resultimageVo.getDataTime().getDayOfMonth() + "_" + resultimageVo.getCropCode() + "_" + resultimageVo.getResolutionCode() + "_" + resultimageVo.getDsType();

                        String absPath = CephUtils.getAbsolutePath(resultimageVo.getStoragePath());

//                        String nativeCRS = geoUtil.getUTMZone(resultimageVo.getLon());


                        System.out.println("文件路径： " + absPath);

                        ResultMessage geoResult = geoUtil.publishTiff("store_" + fileName, "ly_" + fileName, new File(absPath), resultimage.getTifStyle());

                        resultimageVo.setModifier(resultimage.getModifier());
                        resultimageVo.setModifierName(resultimage.getModifierName());
                        resultimageVo.setPublisher(resultimage.getModifier());
                        resultimageVo.setPublisherName(resultimage.getModifierName());
                        resultimageVo.setPublishStatus(PublishStatusEnum.PUBLISH_STATUS_Y.getValue());
                        if (geoResult.isFlag()) { //发布成功
//
                            Map<String, Object> voMap = getDsLayer(resultimageVo, (String) geoResult.getData());

//                            ResultMessage layerResult =  restTemplate.postForObject(UrlConstant.LAYER_ADD_URL,voMap, ResultMessage.class);

                            ResultMessage layerResult = layerService.saveOrUpdate(voMap);

                            if (!layerResult.isFlag()) {
                                if (StringUtils.isNotBlank(resultimageVo.getImageTitle())) {
                                    layerResult.setMsg("影像名称为[" + resultimageVo.getImageTitle() + "]的图层发布失败");
                                } else {
                                    layerResult.setMsg("影像图层发布失败");

                                }
                                list.add(layerResult);
                                continue;
                            }


                            //更新数据集影像表的状态
                            resultimageVo.setPublishStatus(PublishStatusEnum.DATA_PUB_STA_FBCG.getValue());//发布状态 发布成功

                            //更新状态
                            resultimageDao.updateResultimageWithPublish(resultimageVo);

                            result = ResultMessage.success();
                            if (StringUtils.isNotBlank(resultimageVo.getImageTitle())) {
                                result.setMsg("影像名称为[" + resultimageVo.getImageTitle() + "]的图层发布成功");
                            } else {
                                result.setMsg("影像图层发布成功");

                            }
                            //保存成功的rgID
                            result.setData(rgId);
                            list.add(result);

                        } else { //发布失败
                            if (StringUtils.isNotBlank(resultimageVo.getImageTitle())) {
                                result = ResultMessage.fail("影像名称为[" + resultimageVo.getImageTitle() + "]的图层发布失败");
                            } else {
                                result = ResultMessage.fail("影像图层发布失败");

                            }

                            resultimageVo.setPublishStatus(PublishStatusEnum.DATA_PUB_STA_FBSB.getValue());//发布状态 发布失败
                            //更新状态
                            resultimageDao.updateResultimageWithPublish(resultimageVo);

                            list.add(result);
                        }
                    } else {

                        result = ResultMessage.fail("影像名称[" + resultimageVo.getImageTitle() + "]的文件不支持发布操作");
                        list.add(result);
                    }
                }
            }
            result = ResultMessage.success(list);

        } else {
            return ResultMessage.fail("需要发布的数据不能为空");
        }

        return result;
    }


    @Override
    public ResultMessage checkNull(String path) {
        if (StringUtils.isBlank(path)) {
            return ResultMessage.fail("待导入路径下无文件，无法入库");
        }

        List<File> list = CephUtils.listAllFilesForDG(CephUtils.getAbsolutePath(path), null);
        if (list != null && list.size() > 0) {
            return ResultMessage.success();
        } else {
            return ResultMessage.fail("待导入路径下无文件，无法入库");
        }

    }

    @Override
    public ResultMessage findImportDataDetail(ImportParam2 importParam, String suffixs) {

        List<Map<String, Object>> list = CephUtils.listAllFiles(CephUtils.getAbsolutePath(importParam.getPath()), suffixs);
        return ResultMessage.success(list);
    }


    /**
     * 封装图层表bean对象
     *
     * @param layerName
     * @param resultimage
     * @return
     * @version<1>2018-07-01 lcw :Created.
     */
    public Map<String, Object> getDsLayer(DsResultimage resultimage, String layerName) {

        Map<String, Object> voMap = new HashMap<String, Object>();
        voMap.put("creator", resultimage.getCreator());
        voMap.put("creatorName", resultimage.getCreatorName());

        voMap.put("modifier", resultimage.getCreator());
        voMap.put("modifierName", resultimage.getCreatorName());


        voMap.put("name", layerName);//设置name:  如JiaHeDC:ly_CHN-HU_2017_SummerCorn_GF1_PIDDIST
        voMap.put("regionId", resultimage.getRegionId());
        voMap.put("dataTime", resultimage.getDataTime().toString());

        voMap.put("type", LayerTypeEnum.LAYER_TYPE_TIF.getValue());//图层类型：2001 shp 矢量，2002 tif 栅格
        voMap.put("resultimageId", resultimage.getRgId()); //成果数据ID
        voMap.put("resolution", resultimage.getResolution()); //分辨率
        voMap.put("url", PropertyUtil.getPropertiesForConfig("GEOSERVER_URL", PropertiesConstant.GEOSERVER_CONFIG));// 设置url
        voMap.put("ds", resultimage.getDsType());//数据集
        voMap.put("filePath", resultimage.getStoragePath()); //文件路径
        voMap.put("cropId", resultimage.getCropId()); //作物
        voMap.put("publishStatus", resultimage.getPublishStatus()); //发布状态

        return voMap;

    }


//
//    /**
//     * 封装图层表bean对象
//     * @return
//     * @version<1>2018-07-01 lcw :Created.
//     */
//    public DsLayer getDsLayer(DsResultimage resultimage, String layerName ){
//        DsLayer dsLayer = new DsLayer();
//        dsLayer.setPublishStatus(PublishStatus_Enum.PUBLISH_STATUS_N.getValue());//默认是2202
//        dsLayer.setName(layerName);//设置name:  如JiaHeDC:ly_CHN-HU_2017_SummerCorn_GF1_PIDDIST
//        dsLayer.setRegionId(resultimage.getRegionId());
//        dsLayer.setDataTime(resultimage.getDataTime());
//        dsLayer.setType(LayerTypeEnum.LAYER_TYPE_TIF.getValue());//图层类型：2001 shp 矢量，2002 tif 栅格
//        dsLayer.setResultimageId(resultimage.getRgId());
//        dsLayer.setUrl(PropertyUtil.getPropertiesForConfig("GEOSERVER_URL"));// 设置url
//        dsLayer.setDs(resultimage.getDsType());//数据集
//        dsLayer.setResolution(resultimage.getResolution());
//        dsLayer.setResultimageId(resultimage.getRgId());
//        dsLayer.setFilePath(resultimage.getStoragePath()); //文件
//        dsLayer.setCropId(resultimage.getCropId()); //作物
//
//        //创建人
//        dsLayer.setCreator(resultimage.getModifier());
//        dsLayer.setCreatorName(resultimage.getModifierName());
//
//        return dsLayer;
//    }


    /**
     * 遍历指定文件夹下的文件和文件夹
     *
     * @param path         ： 访问路径
     * @param ignorePrefix : 过滤以ignorePrefix为前缀的文件
     * @return
     * @version<1> 2018-03-07 lcw : Created.
     */
    private List<ImportParam2> list(String path, String ignorePrefix) {

        List<ImportParam2> list = new ArrayList<ImportParam2>();
        File file = new File(CephUtils.getAbsolutePath(path));
        logger.error(CephUtils.getAbsolutePath(path));
        file.list(new FilenameFilter() {
            String isVliadFile ="0";
            @Override
            public boolean accept(File dir, String name) {
                if (name.startsWith(ignorePrefix)) {
                    return false;
                }

                ImportParam2 importParam = new ImportParam2();
                importParam.setFileName(name);
//                importParam.setFileType(bool ? 1 : 2);
//                importParam.setFileTypeName(bool ? "文件夹" : "文件");
                importParam.setFileName(path + File.separator + name);
//                importParam.setPath(dir.getAbsolutePath()+File.separator+name);
                //遍历文件，必须包含TIF或者csv,才显示文件夹
                File dirFile=new File(dir.getAbsolutePath() + File.separator + name);
                boolean bool = dirFile.isDirectory();
                if(bool){
                    File [] files=dirFile.listFiles();
                    for(File childrenfile :files){
                        if(childrenfile.isFile()){
                            String filename=childrenfile.getName();
                            String suffix=CephUtils.getCephUrl("SUPPORT_FILE_SUFFIX");
                            if(suffix.contains(filename.substring(filename.lastIndexOf(".")))){
                                isVliadFile="1";
                            }
                        }
                    }
                }
                if (bool) {
                    importParam.setFileType(1);
                    importParam.setFileTypeName("文件夹");
                    importParam.setIsValidFile(isVliadFile);//目录下文件是否有效
                    list.add(importParam);
                }
                return true;
            }
        });
        return list;
    }


    /**
     * 设置分页信息
     *
     * @param dir
     * @version<1> 2018-03-07 lcw : Created.
     */
    private PageInfo<ImportParam2> setPage(ImportParam2 importParam, List<ImportParam2> dir) {
        PageInfo<ImportParam2> pageInfo = new PageInfo<ImportParam2>();
        if (dir != null && dir.size() > 0 && importParam != null) {
            if (importParam.getRows() == 0) {
                importParam.setRows(10);
            }
            int pages = dir.size() / importParam.getRows() + ((dir.size() % importParam.getRows()) == 0 ? 0 : 1);

            //开始条
            int startRow = (importParam.getPage() - 1) * importParam.getRows() + 1;

            int endRow = importParam.getPage() * importParam.getRows();

            List<ImportParam2> list = new ArrayList<ImportParam2>();
            for (int i = 0; i < dir.size(); i++) {
                if (startRow <= i + 1 && i + 1 <= endRow) {
                    dir.get(i).setStorageType(importParam.getStorageType());
                    list.add(dir.get(i));
                }
            }
            pageInfo.setList(list);
            pageInfo.setStartRow(startRow);
            pageInfo.setEndRow(endRow);
            pageInfo.setTotal(dir.size());
            pageInfo.setPages(pages);
        }
        return pageInfo;
    }

    @Override
    public ResultMessage publishTifAndData(DsResultimage resultimage) {
        ResultMessage result = this.publishTif(resultimage);
        List<ResultMessage> list = new ArrayList<ResultMessage>();
        if (result.isFlag()) {
            list = (List<ResultMessage>) result.getData();
            for (ResultMessage r : list) {
                if (r.isFlag()) {
                    //1.图层发布成功则发布数据
                    Integer rgId = (Integer) r.getData();
                    DsResultimage resultimageVo = resultimageDao.findById(rgId);
                    DsResultimage ri = new DsResultimage();
                    ri.setRgId(rgId);
                    ri.setPublishStatus(PublishStatusEnum.PUBLISH_STATUS_Y.getValue());
                    ri.setPublisher(resultimage.getModifier());
                    ri.setPublisherName(resultimage.getModifierName());
                    ri.setPublishTime(LocalDateTime.now());
                    DataSatServiceFactory.getIndexService(resultimageVo.getDsType()).updatePublishStatusByTaskId(ri);
                }
            }
        }
        return ResultMessage.success(list);
    }

    @Override
    public ResultMessage backTifAndData(DsResultimage ri) {
        logger.info("撤回图层开始》》》》》》》");
        //1.删除已发布图层
        layerService.backTiff(ri.getRgIds(),0);
        for(Integer rgId:ri.getRgIds()){
            DsResultimage resultimageVo = resultimageDao.findById(rgId);
            //2.更新各数据集数据状态为未发布
            ri.setDsType(resultimageVo.getDsType());
            ri.setPublishStatus(PublishStatusEnum.PUBLISH_STATUS_N.getValue());
            ri.setRgId(rgId);
            DataSatServiceFactory.getIndexService(ri.getDsType()).updatePublishStatusByTaskId(ri);
            //3.更新任务状态为待发布
            ri.setPublishStatus(PublishStatusEnum.DATA_PUB_STA_WFB.getValue());
            updateResultimage(ri);
        }
        logger.info("撤回图层结束》》》》》》》");
        return ResultMessage.success();
    }


    @Override
    public ResultMessage deleteTifAndData(DsResultimage ri) {
        logger.info("删除图层开始》》》》》》》");
        //1.删除已发布图层
        layerService.backTiff(ri.getRgIds(),1);
        for(Integer rgId:ri.getRgIds()) {
            DsResultimage resultimageVo = resultimageDao.findById(rgId);
            ri.setRgId(rgId);
            ri.setDsType(resultimageVo.getDsType());
            //2.删除各数据集数据状态
            DataSatServiceFactory.getIndexService(ri.getDsType()).deleteDataSetByTaskId(ri.getRgId());
            //3.更新任务状态为删除
            ri.setDelFlag(YNEnum.YNEnum_NO.getValue());
            ri.setDataStatus(YNEnum.YNEnum_NO.getValue());
            ri.setPublishStatus(PublishStatusEnum.DATA_PUB_STA_WFB.getValue());
            updateResultimage(ri);
        }
        logger.info("删除图层结束》》》》》》》");
        return ResultMessage.success();
    }

    /**
     * 数据处理中的数据集入库
     * 1.检索路径下所有文件
     * 2.文件进行分类，看需要创建几条数据集影像
     * 3.将文件拷贝到/resultdata/dataset/对应目录下
     * 4.创建数据集影像数据，插入到数据库表中
     * @param resultimage
     * @return
     * @version<1> 2018-02-19 zhangshen :Created.
     */
    @Override
    public ResultMessage saveHandleDataLoader(DsResultimage resultimage) {
        ResultMessage result = ResultMessage.fail();

        try {
            List<String> fileNameList = new ArrayList<String>();//指定路径下的所有文件名称
            if (StringUtils.isNotEmpty(resultimage.getStoragePath())) {
                List<Map<String, Object>> handleFileLinks = CephUtils.listAllFiles(CephUtils.getAbsolutePath(resultimage.getStoragePath()), null);//获取指定路径下的所有文件
                for (Map<String, Object> map : handleFileLinks) {
                    String fileNa = map.get("fileNa").toString();
                    if (! fileNameList.contains(fileNa.substring(0, fileNa.lastIndexOf(".")))) {
                        fileNameList.add(fileNa.substring(0, fileNa.lastIndexOf(".")));
                    }
                }

                Integer dsType = resultimage.getDsType();
                for (String fileName : fileNameList) {
                    String dataTimeStr = "";
                    if ((DataSetEnum.DATA_SET_DISTRIBUTION.getId()).equals(dsType)) {//fileName.contains("distr")
                        dataTimeStr = fileName.split("_")[1].split("-")[1];
                    } else if ((DataSetEnum.DATA_SET_GROWTH.getId()).equals(dsType)) {//fileName.contains("growth")
                        dataTimeStr = fileName.substring(0,8);
                    }
                    //目标路径 data\resultdata\dataset\distribution\CHN\soybean\2018
                    String destPath = CephUtils.makeDatasetDirectory(resultimage.getDsCode(), resultimage.getRegionCode(), resultimage.getCropCode(), dataTimeStr);
                    for (Map<String, Object> map : handleFileLinks) {
                        String filePath = map.get("filePath").toString();
                        String fileNa = map.get("fileNa").toString();
                        if (filePath.contains(fileName)) {
                            //System.out.println("将文件：" + filePath + "-----------------拷贝到：" + CephUtils.getAbsolutePath(destPath) + File.separator +map.get("fileNa").toString());
                            File srcFile = new File(filePath);
                            File destFile = new File(CephUtils.getAbsolutePath(destPath) + File.separator + map.get("fileNa").toString());
                            try {
                                FileUtils.copyFile(srcFile, destFile);
                            } catch (IOException e) {
                                logger.error(e.toString());
                                return ResultMessage.fail("数据入库失败");
                            }

                            String suffix = getCephUrl("DATA_LAYER_SUFFIX");
                            if (suffix.contains(fileNa.substring(fileNa.lastIndexOf(".")))) {
                                DsResultimage resultimageVo = new DsResultimage();
                                BeanUtils.copyProperties(resultimage, resultimageVo);
                                resultimageVo.setImageTitle(resultimage.getRegionName() + dataTimeStr + resultimage.getCropName() + resultimage.getDsName());
                                resultimageVo.setStoragePath(destPath + File.separator + fileNa);
                                resultimageVo.setDataTimeStr(DateUtil.dateToString(DateUtil.StringToDateYMD(dataTimeStr, "yyyyMMdd"), "yyyy-MM-dd"));
                                resultimageVo.setRgStatus(ProduceStatusEnum.NOACTIVE.getValue());
                                resultimageVo.setPublishStatus(PublishStatusEnum.DATA_PUB_STA_WFB.getValue()); //待发布

                                resultimageDao.save(resultimageVo);
                            }
                        }

                    }
                }
                result = ResultMessage.success("数据入库成功");
            }else{
                result = ResultMessage.fail("数据入库失败");
            }
        } catch (Exception e) {
            logger.error(e.toString());
            return ResultMessage.fail("数据入库失败");
        }

        return result;
    }
}
