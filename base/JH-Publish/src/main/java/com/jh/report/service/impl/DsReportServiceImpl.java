package com.jh.report.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.jh.base.enums.PublishStatusEnum;
import com.jh.briefing.service.ICropsGrowthPeriodService;
import com.jh.constant.CommonDefineEnum;
import com.jh.data.service.*;
import com.jh.enums.DataStatusEnum;
import com.jh.enums.DelStatusEnum;
import com.jh.feign.IDsLayerService;
import com.jh.feign.IRegionFeignService;
import com.jh.report.constant.ReportConstant;
import com.jh.report.enums.DataSetEnum;
import com.jh.report.enums.ReportEnum;
import com.jh.report.enums.YearCropEnum;
import com.jh.report.model.*;
import com.jh.util.*;
import com.jh.util.ceph.CephUtils;
import com.jh.data.entity.DsArea;
import com.jh.data.mapping.IDsAreaMapper;
import com.jh.data.mapping.IDsYieldMapper;
import com.jh.data.model.*;
import com.jh.report.entity.DsReport;
import com.jh.report.mapping.IDsReportMapper;
import com.jh.report.service.IDsReportService;
import com.jh.report.utils.PdfUtils;
import com.jh.vo.ResultMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Description:
 * 1.报告 实现类
 *
 * @version <1> 2018-05-13 11:32 zhangshen: Created.
 */
@SuppressWarnings("unchecked")
@Service
public class DsReportServiceImpl implements IDsReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DsReportServiceImpl.class);

    @Autowired
    private IDsAreaMapper dsAreaMapper;//分布

    @Autowired
    private IDsYieldMapper dsYieldMapper;//估产

    @Autowired
    private IDsReportMapper dsReportMapper;//报告

    @Autowired
    private IDsAreaService dsAreaService;

    @Autowired
    private IDsYieldService dsYieldService;

    @Autowired
    private IDsGrowthService dsGrowthService;

    @Autowired
    private IDsTService dsTService;

    @Autowired
    private IDsTrmmService dsTrmmService;

    @Autowired
    private IDsTtnService dsTtnService;

    @Autowired
    private IRegionFeignService regionFeignService;

    @Autowired
    private ICropsGrowthPeriodService cropsGrowthPeriodService;//用于查询物候期

    @Autowired
    private IDsLayerService dsLayerService;//用于查询图层tif路径

    /**
     * Description: 根据条件查询报告是否已经生成
     * @param reportCreateParam
     * @return
     * @version <1> 2018/7/19 13:40 zhangshen: Created.
     */
    @Override
    public ResultMessage checkReportIsExist(ReportCreateParam reportCreateParam) {
        List<DsReport> dsReportList = dsReportMapper.checkReportIsExist(reportCreateParam);
        return ResultMessage.success(dsReportList);
    }

    /**
     * Description: 创建报告
     * @param reportCreateParam
     * @return
     * @version <1> 2018/5/13 12:12 zhangshen: Created.
     */
    @Override
    public ResultMessage createReportFun(ReportCreateParam reportCreateParam){
        if(null != reportCreateParam && reportCreateParam.getDataSet() != null){
            Integer dataSet = reportCreateParam.getDataSet();//数据集参数
            if ((DataSetEnum.DATA_SET_DISTRIBUTION.getId()).equals(dataSet)) {//分布
                return distributionCreateReport(reportCreateParam);
            }else if ((DataSetEnum.DATA_SET_YIELD.getId()).equals(dataSet)) {//估产
                return ResultMessage.fail("暂时无法生成估产报告");
            }else if ((DataSetEnum.DATA_SET_GROWTH.getId()).equals(dataSet)) {//长势
                return ResultMessage.fail("暂时无法生成长势报告");
            }else if ((DataSetEnum.DATA_SET_T.getId()).equals(dataSet)) {//地温
                return ResultMessage.fail("暂时无法生成地温报告");
            }else if ((DataSetEnum.DATA_SET_TRMM.getId()).equals(dataSet)) {//降雨
                return ResultMessage.fail("暂时无法生成降雨报告");
            }else if ((DataSetEnum.DATA_SET_DROUGHT.getId()).equals(dataSet)) {//干旱
                return ResultMessage.fail("暂时无法生成干旱报告");
            }
        }else{
            return ResultMessage.fail("数据集参数为空");
        }

        return ResultMessage.fail();
    }

    /**
     * Description: 生成分布报告
     * @param reportCreateParam
     * @return ResultMessage
     * @version <1> 2018/7/19 11:25 zhangshen: Created.
     */
    private ResultMessage distributionCreateReport(ReportCreateParam reportCreateParam){

        // 根据区域、作物、精度、数据时间 查询分布数据(当前)
        List<DsArea> dsAreaList = dsAreaService.findDsAreaByCondition(reportCreateParam, null);

        // 根据区域、作物、精度、数据时间 查询分布数据(同期)
        List<DsArea> dsAreaListLast = dsAreaService.findDsAreaByCondition(reportCreateParam, Integer.parseInt(PropertyUtil.getPropertiesForConfig("calculation_days")));
        dsAreaListLast = getDsAreaListLast(dsAreaListLast, reportCreateParam);

        if (CollectionUtil.isEmpty(dsAreaList)) {
            ResultMessage.fail("查询当前分布数据为空");
        }

        //根据区域id查询下级区域信息
        ResultMessage regionResult = regionFeignService.queryRegionListByParentId(reportCreateParam.getRegionId());
        List<Object> list = new ArrayList<Object>();
        if (regionResult.isFlag()) {
            list = (List<Object>)regionResult.getData();
        }

        //查询全国所有的直管
        ResultMessage municipalityResult = regionFeignService.queryMunicipalityArea();
        List<Object> municipalityList = new ArrayList<Object>();
        if (municipalityResult.isFlag()){
            municipalityList = (List<Object>)municipalityResult.getData();
        }

        boolean isMunicipality = false;
        for (int i=0;i<municipalityList.size();i++){
            Long area = (Long) municipalityList.get(i);
            if (reportCreateParam.getRegionId() == area) isMunicipality = true;
        }

        ResultMessage result = new ResultMessage();
        //创建报告
        if (list.size() > 0 && isMunicipality==false && reportCreateParam.getReportTempType().indexOf("City") >0) {//如果有下级区域并且不是直管县市
            result = disCityCreateReport(dsAreaList, dsAreaListLast, reportCreateParam);//创建有下级区域报告
        } else {
            result = disCountyCreateReport(dsAreaList, dsAreaListLast, reportCreateParam);//创建无下级区域报告
        }

        return result;
    }

    /**
     * Description: 创建有下级区域报告
     * @param listNow
     * @param listLast
     * @param reportCreateParam
     * @return
     * @version <1> @param dsAreaList 9:17 zhangshen: Created.
     */
    public ResultMessage disCityCreateReport(List<DsArea> listNow, List<DsArea> listLast, ReportCreateParam reportCreateParam) {
        ResultMessage result = null;
        try {
            //1.模板所需的数据
            DistributionCityData dis = getCityDisData(listNow, listLast);//数据
            dis.setCropType(getCropType(reportCreateParam));//设置物候期

            //2.模板所需的图片数据
            ImageParam imageParam = getCityPieImagesInfo(dis);//图片参数

            String pathPieChart = PdfUtils.createReportPieChart(dis, imageParam);//饼状图片
            LOGGER.info("饼状图片pathPieChart:" + pathPieChart);

            String pathTwoLineChart = PdfUtils.createTwoLineChart(dis, imageParam);
            LOGGER.info("两条折线图pathTwoLineChart:" + pathTwoLineChart);

            String rasterFilePath = getRasterFilePath(reportCreateParam.getCropId(), DataSetEnum.DATA_SET_DISTRIBUTION.getId(), reportCreateParam.getResolution(), reportCreateParam.getRegionId(), reportCreateParam.getDataTime());
            LOGGER.info("rasterFilePath:" + rasterFilePath);
            String generateThematicPath = null;
            if (StringUtils.isNotBlank(rasterFilePath)) {
                String title = dis.getRegionName() + DateUtil.getYearByDate(dis.getSatelliteImageDate()) + "年" + dis.getCropName() + ReportConstant.REPORT_AREA_THEMATIC;
                generateThematicPath = getGenerateThematicPath(reportCreateParam.getRegionId(), rasterFilePath, title, dis.getCropName());
                LOGGER.info("有下级区专题图generateThematicPath:" + generateThematicPath);
            }

            Map<String, String> imgMap = new HashMap<String, String>();//图片key(模板中通过key去值)， 图片绝对路径
            imgMap.put("image1", generateThematicPath);//专题图
            imgMap.put("image2", pathPieChart);//饼状图片
            imgMap.put("image3", pathTwoLineChart);//折线图
            dis.setImagesMap(getCountyImageMap(imgMap));//图片

            //3.生成有下级区域报告
            String tempName = reportCreateParam.getReportTempType() + ".ftl";
            String regionCodeAndDatePath = File.separator + getRegionCodeFull(reportCreateParam.getRegionCode()) + reportCreateParam.getDataTime().substring(0,4) + File.separator;
            String fileName = dis.getCropName() + "-" + dis.getRegionName() + "-" + ReportConstant.REPORT_TYPE_AREA + "-" + reportCreateParam.getDataTime();
            String filePath = getReportStoragePath(regionCodeAndDatePath, fileName);
            String storageFullPathPdf = filePath + "." + ReportConstant.REPORT_FILE_FORMAT_PDF;//pdf文件绝对路径
            String storageFullPathWord = filePath + "." + ReportConstant.REPORT_FILE_FORMAT_DOC;//doc文件绝对路径

            File filePdf = PdfUtils.generatePdf(tempName , dis, storageFullPathPdf, storageFullPathWord);//生成word, 再转pdf

            result = insertReportInfo(filePdf, reportCreateParam, regionCodeAndDatePath, fileName);//向ds_report表插入报告信息

        } catch (Exception e) {
            LOGGER.error("创建有下级区域报告 异常", e);
            new RuntimeException(e);
            return result;
        }

        return result;
    }


    /**
     * 创建无下级区域报告
     * @param dsAreaList
     * @param dsAreaListLast
     * @param reportCreateParam
     * @return
     */
    public ResultMessage disCountyCreateReport(List<DsArea> dsAreaList, List<DsArea> dsAreaListLast, ReportCreateParam reportCreateParam) {
        ResultMessage result = null;
        try {
            //1.模板所需的数据
            DistributionCountyData dis = getCountyDisData(dsAreaList, dsAreaListLast);//数据
            dis.setCropType(getCropType(reportCreateParam));//设置物候期

            //2.模板所需的图片数据
            ImageParam imageParam = getCountyImagesInfo(dsAreaList, dsAreaListLast);

            String path2 = PdfUtils.createBarAndLineChart(imageParam);//生成柱状折线图
            LOGGER.info("生成柱状折线图:" + path2);

            String rasterFilePath = getRasterFilePath(reportCreateParam.getCropId(), DataSetEnum.DATA_SET_DISTRIBUTION.getId(), reportCreateParam.getResolution(), reportCreateParam.getRegionId(), reportCreateParam.getDataTime());
            LOGGER.info("专题图:" + rasterFilePath);
            String generateThematicPath = null;
            if (StringUtils.isNotBlank(rasterFilePath)) {
                String title = dis.getRegionName() + DateUtil.getYearByDate(dis.getSatelliteImageDate()) + "年" + dis.getCropName() + ReportConstant.REPORT_AREA_THEMATIC;
                generateThematicPath = getGenerateThematicPath(reportCreateParam.getRegionId(), rasterFilePath, title, dis.getCropName());
                LOGGER.info("专题图:" + generateThematicPath);
            }

            Map<String, String> imgMap = new HashMap<String, String>();//图片key(模板中通过key去值)， 图片绝对路径
            imgMap.put("image1", generateThematicPath);//专题图
            imgMap.put("image2", path2);//柱状折线图

            dis.setImagesMap(getCountyImageMap(imgMap));//图片

            //3.生成无下级区域报告
            String tempPath = reportCreateParam.getReportTempType()+ ".ftl";
            String regionCodeAndDatePath = File.separator + getRegionCodeFull(reportCreateParam.getRegionCode()) + reportCreateParam.getDataTime().substring(0,4) + File.separator;
            String fileName =dis.getCropName() + "-" + dis.getRegionName() + "-" + ReportConstant.REPORT_TYPE_AREA + "-" + reportCreateParam.getDataTime();
            String filePath = getReportStoragePath(regionCodeAndDatePath, fileName);
            String storageFullPathPdf = filePath + "." + ReportConstant.REPORT_FILE_FORMAT_PDF;//pdf文件绝对路径
            String storageFullPathWord = filePath + "." + ReportConstant.REPORT_FILE_FORMAT_DOC;//doc文件绝对路径

            File filePdf = PdfUtils.generatePdf(tempPath , dis, storageFullPathPdf, storageFullPathWord);//生成word, 再转pdf
            result = insertReportInfo(filePdf, reportCreateParam, regionCodeAndDatePath, fileName);//向ds_report表插入报告信息

        } catch (Exception e) {
            LOGGER.error("创建无下级区域报告 异常", e);
            new RuntimeException(e);
            return result;
        }

        return result;
    }

    /**
     * Description: 获取报告的存储路径
     * @param regionCodeAndDatePath
     * @param fileName
     * @return
     * @version <1> 2018/7/25 9:35 zhangshen: Created.
     */
    private String getReportStoragePath(String regionCodeAndDatePath, String fileName) {
        String reportStorage = CephUtils.getAbsolutePath("") + CephUtils.getCephUrl("REPORT_STORAGE").replace("\\", File.separator);
        String storagePath = reportStorage + regionCodeAndDatePath;
        File file = new File(storagePath);//pdf路径 和 word路径
        if (!file.exists()) {
            file.mkdirs();
        }
        return storagePath + fileName;//文件名不带后缀
    }

    /**
     * Description: 封装无下级区域模板数据
     * @param dsAreaList
     * @param dsAreaListLast
     * @return
     * @version <1> 2018/7/21 12:51 zhangshen: Created.
     */
    private DistributionCountyData getCountyDisData(List<DsArea> dsAreaList, List<DsArea> dsAreaListLast) {
        DistributionCountyData dis = new DistributionCountyData();
        try {
            DsArea dsAreaNow = dsAreaList.get(0);

            dis.setRegionName(dsAreaNow.getRegionName());//区域
            dis.setCropName(dsAreaNow.getCropName());//作物
            dis.setSatelliteImageDate(DateUtil.LocalDateToUdate(dsAreaNow.getDataTime()));//数据时间
            dis.setResolutionCN(dsAreaNow.getResolutionValue());//高分一号卫星
            //dis.setCropType("灌浆乳熟期");
            dis.setEndYearArea((double)Math.round(dsAreaNow.getArea()));
            dis.setIsOverYearCrop(1);
            //判断是否是跨年作物，如果是跨年作物则IsOverYearCrop为1，不是跨年作物则为0
            for (YearCropEnum cropEnum:YearCropEnum.values()){
                if (cropEnum.getName().equals(dsAreaNow.getCropName())) dis.setIsOverYearCrop(0);
            }

            if (CollectionUtil.isNotEmpty(dsAreaListLast)) {
                DsArea dsAreaLast = dsAreaListLast.get(0);
                dis.setSatelliteImageDateLast(DateUtil.LocalDateToUdate(dsAreaLast.getDataTime()));
                dis.setBeginYearArea((double)Math.round(dsAreaLast.getArea()));
            } else {
                dis.setSatelliteImageDateLast(null);
                dis.setBeginYearArea(0D);
            }

            double area = dis.getEndYearArea() - dis.getBeginYearArea();
            dis.setArea(Math.abs(area));
            dis.setTrendFlag(area >= 0 ? 1 : 0);
            String areaPercentage = ArithUtil.round(ArithUtil.div(Math.abs(area), dis.getBeginYearArea()) * 100, 2) + "%";//保留两位小数
            dis.setAreaPercentage(areaPercentage);
        } catch (Exception e) {
            LOGGER.error("封装无下级区域模板数据 异常", e);
            new RuntimeException(e);
        }

        return dis;
    }

    /**
     * Description: 封装有下级区域模板数据
     * @param listNow
     * @param listLast
     * @return
     * @version <1> 2018/7/23 14:36 zhangshen: Created.
     */
    private DistributionCityData getCityDisData(List<DsArea> listNow, List<DsArea> listLast) {
        //封装有下级区域分布模板集数据
        DistributionCityData dis = new DistributionCityData();
        try {
            //当前父级赋值
            DsArea dsAreaNow = listNow.get(0);
            dis.setRegionName(dsAreaNow.getRegionName());//区域
            dis.setCropName(dsAreaNow.getCropName());//作物
            dis.setSatelliteImageDate(DateUtil.LocalDateToUdate(dsAreaNow.getDataTime()));//数据时间
            dis.setResolutionCN(dsAreaNow.getResolutionValue());//高分一号卫星
            //dis.setCropType("灌浆乳熟期");
            dis.setEndYearArea(ArithUtil.round(ArithUtil.div(dsAreaNow.getArea(), 10000), 2));//万亩
            dis.setIsOverYearCrop(1);
            //判断是否是跨年作物，如果是跨年作物则IsOverYearCrop为1，不是跨年作物则为0
            for (YearCropEnum cropEnum:YearCropEnum.values()){
                if (cropEnum.getName().equals(dsAreaNow.getCropName())) dis.setIsOverYearCrop(0);
            }
            //当前子级赋值
            //根据当前dsAreaNow查询子级信息
            List<DsArea> dsAreaListNow = dsAreaService.getDsAreaListByParent(dsAreaNow);
            dsAreaListNow = getSortByDsAreaList(dsAreaListNow);//降序排列

            dis.setRegionNameListStr(getRegionListStr(dsAreaListNow, 5));//主要分布在襄阳、荆州、荆门、天门、黄冈、孝感、十堰等地区  <=5个


            //同期子级信息
            List<DsArea> dsAreaListLast = new ArrayList<DsArea>();
            //同期父级赋值
            if (CollectionUtil.isNotEmpty(listLast)) {
                DsArea dsAreaLast = listLast.get(0);
                dis.setBeginYearArea(ArithUtil.round(ArithUtil.div(dsAreaLast.getArea(), 10000), 2));//万亩
                dis.setSatelliteImageDateLast(DateUtil.LocalDateToUdate(dsAreaLast.getDataTime()));
                //根据同期dsAreaLast查询子级信息， 并降序排序
                dsAreaListLast = getSortByDsAreaList(dsAreaService.getDsAreaListByParent(dsAreaLast));//降序排列

            } else {
                dis.setBeginYearArea(0D);
                dis.setSatelliteImageDateLast(null);
            }


            //获取下级分布地区集合List<DistributionBaseData>
            List<DistributionBaseData> lowerDisListAll = getLowerDisBaseData(dsAreaListNow, dsAreaListLast, dsAreaListNow.size());//下级所有
            dis.setDistributionList(lowerDisListAll);

            List<DistributionBaseData> reduceDisListTwo = getLowerDisBaseDataByFlag(lowerDisListAll, 2, 0);//减少的分布地区集合 2个
            dis.setReduceDisList(reduceDisListTwo);

            List<DistributionBaseData> increaseDisListTwo = getLowerDisBaseDataByFlag(lowerDisListAll, 2, 1);//增加的分布地区集合 2个
            dis.setIncreaseDisList(increaseDisListTwo);


            List<DistributionBaseData> increaseDisListAll = getLowerDisBaseDataByFlag(lowerDisListAll, 1);//增加的分布地区所有集合
            DistributionIncreaseData disIncrease = getIncreaseDisList(increaseDisListAll, dis, 3);
            dis.setDistributionIncreaseData(disIncrease);


            //当前父级 和 同期父级 比较
            double area = ArithUtil.sub(dis.getEndYearArea(), dis.getBeginYearArea());
            dis.setArea(Math.abs(area));
            dis.setTrendFlag(area >= 0 ? 1 : 0);
            String areaPercentage = ArithUtil.round(ArithUtil.div(Math.abs(area), dis.getBeginYearArea()) * 100, 2) + "%";//String.format("%.2f", Math.abs(area) / dis.getEndYearArea() * 100) + "%";//保留两位小数
            dis.setAreaPercentage(areaPercentage);

        } catch (Exception e) {
            LOGGER.error("封装有下级区域模板数据 异常", e);
            new RuntimeException(e);
        }

        return dis;
    }

    /**
     * Description: 将图片路径转换成BASE64字符串
     * @param imgMap
     * @return
     * @version <1> 2018/7/21 13:46 zhangshen: Created.
     */
    private Map<String, DistributionImage> getCountyImageMap(Map<String, String> imgMap) {
        Map<String, DistributionImage> map = new HashMap<String, DistributionImage>();
        for (Map.Entry<String, String> entry : imgMap.entrySet()) {
            DistributionImage distributionImage = new DistributionImage();
            String value = entry.getValue();
            if(StringUtils.isNotBlank(value)){//路径不为空
                String code = PdfUtils.getImageString(value);
                if(StringUtils.isNotBlank(code)){//生成的图片码不为空
                    distributionImage.setImageCode(code);
                    map.put(entry.getKey(), distributionImage);
                    //将图片装换成BASE64字符串后，删除图片
                    if (!"image1".equals(entry.getKey())) {//删除非专题图图片
                        File file = new File(value);
                        if (file.exists()) {
                            file.delete();
                        }
                    } else {//是专题图
                        File file = new File(value);
                        String strParentDirectory = file.getParent();//文件的上级目录
                        File removeFile = new File(strParentDirectory);
                        CephUtils.deleteDirAndFiles(removeFile);//删除指定路径下的文件及文件夹
                    }
                }
            }
        }
        return map;
    }

    /**
     * Description: 封装生成无下级区域柱状折线图片参数
     * @param dsAreaList
     * @param dsAreaListLast
     * @return
     * @version <1> 2018/7/20 15:43 zhangshen: Created.
     */
    private ImageParam getCountyImagesInfo(List<DsArea> dsAreaList, List<DsArea> dsAreaListLast) {
        ImageParam imageParam = new ImageParam();
        DsArea dsAreaNow = dsAreaList.get(0);
        imageParam.setRegionName(dsAreaNow.getRegionName());
        imageParam.setCropName(dsAreaNow.getCropName());
        imageParam.setYear(DateUtil.getYearByDate(DateUtil.LocalDateToUdate(dsAreaNow.getDataTime())));
        imageParam.setStoragePth(getTempImagePath());//设置图片存储路径
        imageParam.setUnit(ReportConstant.REPORT_AREA_UNIT_ONE);
        imageParam.setDescription(ReportConstant.REPORT_AREA_DESCRIPTION);

        List<ImageData> imageDataList = new LinkedList<ImageData>();

        if (CollectionUtil.isNotEmpty(dsAreaListLast)) {
            DsArea dsAreaLast = dsAreaListLast.get(0);
            ImageData imageDataLast = new ImageData();//同期
            imageDataLast.setYear(DateUtil.getYearByDate(DateUtil.LocalDateToUdate(dsAreaLast.getDataTime())));
            imageDataLast.setArea((float)((double)dsAreaLast.getArea()));
            imageDataList.add(imageDataLast);
        }

        ImageData imageDataNow = new ImageData();//当前
        imageDataNow.setYear(DateUtil.getYearByDate(DateUtil.LocalDateToUdate(dsAreaNow.getDataTime())));
        imageDataNow.setArea((float)((double)dsAreaNow.getArea()));
        imageDataList.add(imageDataNow);

        imageParam.setImageDataList(imageDataList);

        return imageParam;
    }

    /**
     * Description: 生成饼状图所需的参数
     * @param distributionCityData
     * @return
     * @version <1> 2018/7/23 16:03 zhangshen: Created.
     */
    private ImageParam getCityPieImagesInfo(DistributionCityData distributionCityData) {
        ImageParam imageParam = new ImageParam();
        imageParam.setRegionName(distributionCityData.getRegionName());
        imageParam.setUnit(ReportConstant.REPORT_AREA_UNIT_THOUSAND);
        imageParam.setStoragePth(getTempImagePath());//设置图片存储路径
        imageParam.setCropName(distributionCityData.getCropName());

        return imageParam;
    }

    /**
     * Description: 获取临时存放报告图片的路径
     * @param
     * @return 绝对路径
     * @version <1> 2018/7/24 16:31 zhangshen: Created.
     */
    private String getTempImagePath() {
        String reportTemporary = CephUtils.getAbsolutePath("") + CephUtils.getCephUrl("REPORT_TEMPORARY").replace("\\", File.separator);
        File file = new File(reportTemporary);//图片临时路径
        if (!file.exists()) {
            file.mkdirs();
        }
        return reportTemporary;
    }


    /**
     * Description: 排序，获取最接近dataTime的DsArea
     * @param dsAreaListLast
     * @param reportCreateParam
     * @return
     * @version <1> 2018/7/19 20:38 zhangshen: Created.
     */
    private List<DsArea> getDsAreaListLast(List<DsArea> dsAreaListLast, ReportCreateParam reportCreateParam) {
        String dataTime = reportCreateParam.getDataTime();
        if (dataTime != null) {
            try {
                Date dataTimeLast = DateUtil.subYear(DateUtil.StringToDateYMD(dataTime, "yyyy-MM-dd"), 1);
                System.out.println("同期：" + DateUtil.dateToString(dataTimeLast, "yyyy-MM-dd"));

                for (int i=0; i<dsAreaListLast.size()-1; i++) {//外层循环控制排序趟数
                    for(int j=0; j<dsAreaListLast.size()-1-i; j++){//内层循环控制每一趟排序多少次
                        int day1 = Math.abs(DateUtil.getTwoDateBetweenDay(DateUtil.LocalDateToUdate(dsAreaListLast.get(j).getDataTime()), dataTimeLast));//相隔天数
                        int day2 = Math.abs(DateUtil.getTwoDateBetweenDay(DateUtil.LocalDateToUdate(dsAreaListLast.get(j+1).getDataTime()), dataTimeLast));//相隔天数
                        if(day1 > day2){
                            dsAreaListLast = CollectionUtil.swap1(dsAreaListLast, j,j+1);//List元素交换位置
                        }
                    }
                }
            } catch (Exception e) {}
        }
        return dsAreaListLast;
    }

    /**
     * Description: 子级根据面积从大到小排序
     * @param dsAreaList
     * @return
     * @version <1> 2018/7/23 10:45 zhangshen: Created.
     */
    private List<DsArea> getSortByDsAreaList(List<DsArea> dsAreaList) {
        if (CollectionUtil.isNotEmpty(dsAreaList)) {//不为空进行排序
            Collections.sort(dsAreaList, new Comparator<DsArea>(){
                @Override
                public int compare(DsArea dsArea1, DsArea dsArea2) {
                    //按照面积进行降序排列
                    if(dsArea1.getArea() < dsArea2.getArea()){
                        return 1;
                    }
                    if(dsArea1.getArea() == dsArea2.getArea()){
                        return 0;
                    }
                    return -1;
                }
            });
        }
        for (DsArea dsArea : dsAreaList) {
            dsArea.setArea(ArithUtil.div(dsArea.getArea(), 10000));
            System.out.println(dsArea.getRegionName() + ":" + dsArea.getArea());
        }
        return dsAreaList;
    }

    /**
     * Description: 获取 主要分布在襄阳、荆州、荆门、天门、黄冈、孝感、十堰等地区  <=5个 字符串
     * @param dsAreaList
     * @param num
     * @return String
     * @version <1> 2018/7/23 11:17 zhangshen: Created.
     */
    private String getRegionListStr(List<DsArea> dsAreaList, int num) {
        String regionListStr = "";
        if (CollectionUtil.isNotEmpty(dsAreaList)) {
            List<String> regionNameList = new ArrayList<String>();
            for (DsArea dsArea : dsAreaList) {
                regionNameList.add(dsArea.getRegionName());
                if (num == regionNameList.size()) {
                    break;
                }
            }
            if (CollectionUtil.isNotEmpty(regionNameList)) {
                regionListStr = StringUtils.join(regionNameList.toArray(), "、");
            }
        }
        return regionListStr;
    }

    /**
     * Description: 获取下级分布地区DistributionBaseData集合
     * @param dsAreaListNow
     * @param dsAreaListLast
     * @param num
     * @return 返回前num个列表
     * @version <1> 2018/7/23 12:08 zhangshen: Created.
     */
    private List<DistributionBaseData> getLowerDisBaseData(List<DsArea> dsAreaListNow, List<DsArea> dsAreaListLast, int num) {
        List<DistributionBaseData> disLsit = new ArrayList<DistributionBaseData>();
        for (DsArea dsAreaNow : dsAreaListNow) {
            if (CollectionUtil.isNotEmpty(dsAreaListLast)) {//同期不为空
                for (DsArea dsAreaLast : dsAreaListLast) {
                    if (dsAreaNow.getRegionId().equals(dsAreaLast.getRegionId())) {//当前 和 同期的区域id相同, 进行比较
                        DistributionBaseData dis = new DistributionBaseData();

                        dis.setRegionName(dsAreaNow.getRegionName());
                        dis.setEndYearArea(ArithUtil.round(dsAreaNow.getArea(), 2));//万亩

                        double beginYearArea = dsAreaLast.getArea() == null ? 0D : ArithUtil.round(dsAreaLast.getArea(), 2);
                        dis.setBeginYearArea(beginYearArea);

                        double area = ArithUtil.sub(dis.getEndYearArea(), dis.getBeginYearArea());//面积差
                        dis.setArea(Math.abs(area));
                        dis.setTrendFlag(area >= 0 ? 1 : 0);
                        String areaPercentage = ArithUtil.round(ArithUtil.div(Math.abs(area), dis.getBeginYearArea()) * 100, 2) + "%";//String.format("%.2f", Math.abs(area) / dis.getEndYearArea() * 100) + "%";//保留两位小数
                        dis.setAreaPercentage(areaPercentage);

                        disLsit.add(dis);
                        break;//跳出内层循环
                    }
                }
            } else {
                DistributionBaseData dis = new DistributionBaseData();

                dis.setRegionName(dsAreaNow.getRegionName());
                dis.setEndYearArea(ArithUtil.round(dsAreaNow.getArea(), 2));

                double beginYearArea = 0D;
                dis.setBeginYearArea(beginYearArea);

                double area = ArithUtil.sub(dis.getEndYearArea(), dis.getBeginYearArea());//面积差
                dis.setArea(Math.abs(area));
                dis.setTrendFlag(area >= 0 ? 1 : 0);
                String areaPercentage = ArithUtil.round(ArithUtil.div(Math.abs(area), dis.getBeginYearArea()) * 100, 2) + "%";//保留两位小数
                dis.setAreaPercentage(areaPercentage);

                disLsit.add(dis);
            }


            if (num == disLsit.size()) {
                break;//跳出外层循环
            }
        }

        for (DistributionBaseData data : disLsit) {
            System.out.println((data.getTrendFlag() == 1 ? "增加----" : "减少----") + data.getRegionName() + "----当前:" + data.getEndYearArea()+ "----同期:" + data.getBeginYearArea());
        }

        return disLsit;
    }

    /**
     * Description: 获取增加或减少的集合
     * @param list
     * @param num
     * @param flag 1增加 0减少
     * @return
     * @version <1> 2018/7/23 12:49 zhangshen: Created.
     */
    private List<DistributionBaseData> getLowerDisBaseDataByFlag(List<DistributionBaseData> list, int num, int flag) {
        List<DistributionBaseData> disList = new ArrayList<DistributionBaseData>();
        for (DistributionBaseData dis : list) {
            if (dis.getTrendFlag() == flag) {//1增加 0减少
                disList.add(dis);
            }

            if (num == disList.size()) {
                break;
            }
        }
        for (DistributionBaseData dis: disList) {
            System.out.println((flag == 1 ? "增加----" : "减少----") + dis.getRegionName() + ":" + dis.getEndYearArea());
        }
        return disList;
    }

    /**
     * Description: 获取增加或减少的所有集合
     * @param list
     * @param flag 1增加 0减少
     * @return
     * @version <1> 2018/7/23 12:49 zhangshen: Created.
     */
    private List<DistributionBaseData> getLowerDisBaseDataByFlag(List<DistributionBaseData> list, int flag) {
        List<DistributionBaseData> disList = new ArrayList<DistributionBaseData>();
        for (DistributionBaseData dis : list) {
            if (dis.getTrendFlag() == flag) {//1增加 0减少
                disList.add(dis);
            }
        }
        for (DistributionBaseData dis: disList) {
            System.out.println((flag == 1 ? "增加----" : "减少----") + dis.getRegionName() + ":" + dis.getEndYearArea());
        }
        return disList;
    }

    /**
     * Description: 获取DistributionIncreaseData
     * @param list
     * @param num
     * @return
     * @version <1> 2018/7/23 13:25 zhangshen: Created.
     */
    private DistributionIncreaseData getIncreaseDisList(List<DistributionBaseData> list, DistributionCityData disCity, int num) {
        DistributionIncreaseData dis = new DistributionIncreaseData();

        String regionListStr = "";
        if (CollectionUtil.isNotEmpty(list)) {
            List<String> regionNameList = new ArrayList<String>();
            for (DistributionBaseData distributionBaseData : list) {
                regionNameList.add(distributionBaseData.getRegionName());
                if (num == regionNameList.size()) {
                    break;
                }
            }
            if (CollectionUtil.isNotEmpty(regionNameList)) {
                regionListStr = StringUtils.join(regionNameList.toArray(), "、");
            }
        }
        dis.setRegionNames(regionListStr);//襄阳市、荆州市、荆门市

        double endArea = 0D;//增加区域的总面积
        for (DistributionBaseData distributionBaseData : list) {
            endArea = ArithUtil.add(endArea, distributionBaseData.getEndYearArea());
        }
        System.out.println("增加区域的总面积:" + endArea + ",总面积:" + disCity.getEndYearArea());

        if (disCity.getEndYearArea() != null && disCity.getEndYearArea() != 0D) {
            double percentage = ArithUtil.round(ArithUtil.div(endArea, disCity.getEndYearArea()) * 100, 2);//Double.parseDouble(String.format("%.2f", endArea / disCity.getEndYearArea() * 100));
            String areaPercentage = percentage + "%";//保留两位小数
            String otherPercentage = ArithUtil.sub(100, percentage) + "%";//其他占比
            dis.setAreaPercentage(areaPercentage);
            dis.setOtherAreaPercentage(otherPercentage);//其他占比
        }
        return dis;
    }

    /**
     * Description: 生成pdf后，向ds_report表插入数据
     * @param file
     * @param reportCreateParam
     * @param regionCodeAndDatePath 区域code全路径加年份
     * @param fileName 文件名称(不带后缀)
     * @return
     * @version <1> 2018/7/25 10:24 zhangshen: Created.
     */
    public ResultMessage insertReportInfo(File file, ReportCreateParam reportCreateParam, String regionCodeAndDatePath, String fileName) {
        FileInputStream fis  = null;
        try {
            //1.检查报告是否存在，存在就删除
            List<DsReport> dsReportList = dsReportMapper.checkReportIsExist(reportCreateParam);
            if (dsReportList.size() > 0) {
                List<Integer> rIds = new ArrayList<Integer>();//报告id集合
                for (DsReport ds : dsReportList) {
                    rIds.add(ds.getReportId());
                }
                //组装数据
                DsReport dsReport = new DsReport();
                dsReport.setDelFlag("0");//删除
                dsReportMapper.batchDeleteReport(rIds, dsReport);
            }

            //2.组装数据插入
            fis = new FileInputStream(file);
            int size = fis.available();
            String fileSize = CephUtils.compileSizeUnit(size);//文件大小
            String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);//文件后缀

            DsReport report = new DsReport();
            report.setRegionId(reportCreateParam.getRegionId());
            report.setReportName(fileName);
            report.setFilePath(CephUtils.getCephUrl("REPORT_STORAGE").replace("\\", File.separator) + regionCodeAndDatePath + fileName + "." + ReportConstant.REPORT_FILE_FORMAT_PDF);
            report.setCycle(1l);
            report.setCropId(reportCreateParam.getCropId());
            report.setDataType(1);//数据源类型,如mod0911,mod09ga,gf1等 目前没有,暂时填1
            report.setDsId(reportCreateParam.getDataSet());
            report.setApprovalStatus(9101);//报告类型;自动生成9101,手动导入9102
            report.setCreateTime(LocalDateTime.now());
            report.setCreator(reportCreateParam.getCreator());
            report.setCreatorName(reportCreateParam.getCreatorName());
            //report.setDataStatus("1");
            //report.setDelFlag("1");
            report.setResolution(reportCreateParam.getResolution());//精度
            report.setFileSize(fileSize);
            report.setFileSuffix(suffix);
            report.setPublishStatus(2202);//待发布
            report.setKeyword(reportCreateParam.getKeyword());//关键字
            report.setReportTime(LocalDateTime.parse(reportCreateParam.getDataTime() + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            saveReportInfo(report);//保存报告信息
        } catch (Exception e) {
            LOGGER.error("生成pdf后，向ds_report表插入数据 异常", e);
            new RuntimeException(e);
            return ResultMessage.fail("生成报告失败");
        } finally {
            if(null!=fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ResultMessage.success();
    }

    /**
     * Description: 获取物候期
     * @param reportCreateParam
     * @return
     * @version <1> 2018/7/25 15:43 zhangshen: Created.
     */
    public String getCropType(ReportCreateParam reportCreateParam) {
        Long regionId = reportCreateParam.getRegionId();
        Integer cropsId = reportCreateParam.getCropId();
        String dateStr = reportCreateParam.getDataTime();
        return cropsGrowthPeriodService.getCropsGrowthName(regionId, cropsId, dateStr);
    }

    /**
     * Description:
     * @param cropId 作物id
     * @param dataSet 数据集
     * @param resolution 分辨率
     * @param regionId 区域id
     * @param layerDate 图层时间
     * @return
     * @version <1> 2018/7/26 13:37 zhangshen: Created.
     */
    public String getRasterFilePath (Integer cropId, Integer dataSet, Integer resolution, Long regionId, String layerDate) {
        String filePath = null;
        try {
            ResultMessage result = dsLayerService.layer(cropId, dataSet, resolution, regionId, layerDate);
            if (result.isFlag()) {
                Map<String, Object> map = (Map<String, Object>) result.getData();
                //CephUtils.getAbsolutePath("")
                filePath = CephUtils.getCephOnService() + CephUtils.getCephRoot() + map.get("filePath");
                filePath = filePath.replace("\\", File.separator);
            }
        } catch (Exception e){
            LOGGER.error("查询生成的专题图文件路径 异常", e);
            throw new RuntimeException(e);
        }
        return filePath;
    }

    /**
     * Description: 根据指定的区域ID及农作物分布影像，生成专题图
     * @param regionId 区域ID
     * @param rasterFilePath 分布影像路径
     * @param title 专题图标题
     * @param cropName 农作物名称
     * @return 专题图路径
     * @version <1> 2018/7/26 13:13 zhangshen: Created.
     */
    public String getGenerateThematicPath(Long regionId, String rasterFilePath, String title, String cropName) {
        OutputStream out = null;
        BufferedReader in = null;
        try {
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("regionId", regionId);
            params.put("rasterFilePath", rasterFilePath);//    //192.168.1.223/data/mnt/data/resultdata/dataset/distribution/CHN-HU/EarlyRice/2017/CHN-HU_EarlyRice_2017_GF1_16m.tif
            params.put("title", title);//湖北省宜昌市2018年早稻分布专题图
            params.put("cropName", cropName);//早稻

            URL url = new URL(PropertyUtil.getPropertiesForConfig("generateThematicService"));//http://192.168.1.222:7777/generateThematic
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
            String resultData = "";
            while ((line = in.readLine()) != null){
                resultData += line;
            }

            System.out.println(resultData);

            JSONObject jsonObject = JSONObject.fromObject(resultData);
            boolean flag = Boolean.parseBoolean(jsonObject.getString("flag"));
            if (flag) {
                return (jsonObject.getString("result").replace(CephUtils.getCephOnService(), CephUtils.getServer())).replace("\\", File.separator);
            }
            //System.out.println(flag + "--------" + result);
        } catch (Exception e) {
            LOGGER.error("生成专题图 异常", e);
            new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Description: 保存报告信息
     * @param dsReport
     * @return
     * @version <1> 2018/5/14 14:50 zhangshen: Created.
     */
    public void saveReportInfo(DsReport dsReport) {
        dsReportMapper.saveReportInfo(dsReport);
    }

    /**
     * Description: 查询报告信息
     * @param dsReportParam
     * @return
     * @version <1> 2018/5/15 14:59 zhangshen: Created.
     */
    @Override
    public PageInfo<DsReport> findDsReportByPage(DsReportParam dsReportParam){
        PageHelper.startPage(dsReportParam.getPage(), dsReportParam.getRows());
        List<DsReport> list = dsReportMapper.findDsReportByPage(dsReportParam);
        return new PageInfo<DsReport>(list);
    }

    /**
     * Description: 预览pdf文件报告
     * @param filePath 文件路径
     * @param response
     * @return
     * @version <1> 2018/5/16 15:26 zhangshen: Created.
     */
    @Override
    public void previewReportPdf(String filePath , HttpServletResponse response){
        DownloadUtil.getInstance().previewReportPdf(filePath, response);
    }

    /**
     * Description: 根据reportId查询DsReport
     * @param reportId
     * @return
     * @version <1> 2018/5/18 10:07 zhangshen: Created.
     */
    @Override
    public DsReport findDsReportById(Integer reportId){
        return dsReportMapper.findDsReportById(reportId);
    }

    /**
     * Description: 根据报告ids,批量发布报告
     * @param reportIds
     * @return
     * @version <1> 2018/5/18 15:34 zhangshen: Created.
     */
    @Override
    public ResultMessage batchPublishReport(String reportIds,DsReport dsReport){
        //数据转换
        JSONObject json = JSONObject.fromObject(reportIds);
        JSONArray jsonArray = JSONArray.fromObject(json.getString("reportIds"));
        Object[] ids = jsonArray.toArray();
        //登录人不为null
        if(ids.length > 0){
            List<Integer> rIds = new ArrayList<Integer>();//报告id集合
            for(Object id : ids){
                rIds.add(Integer.parseInt(id.toString()));
            }
//            dsReport.setPublisherName(permAccount.getNickName());//发布人
            dsReport.setPublishTime(LocalDateTime.now());//发布时间
            dsReport.setPublishStatus(PublishStatusEnum.PUBLISH_STATUS_Y.getValue());//发布状态

            dsReportMapper.batchPublishReport(rIds, dsReport);
            return new ResultMessage(true, CommonDefineEnum.SUCCESS.getValue(),null , CommonDefineEnum.SUCCESS.getMesasge());
        }else{
            return new ResultMessage(false, CommonDefineEnum.FAIL.getValue(),null , CommonDefineEnum.FAIL.getMesasge());
        }
    }

    /**
     * Description: 编辑报告
     * @param request
     * @return
     * @version <1> 2018-05-21 zhangshen : Created.
     */
    @Override
    public ResultMessage updateReportById(HttpServletRequest request, DsReport report){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("reportFile");
        String reportId = request.getParameter("reportId");
        String reportType = request.getParameter("reportType");//报告类型;自动生成9101,手动导入9102
        String reportTime = request.getParameter("reportTime");//报告时间
        String regionId = request.getParameter("regionId");//区域
        String regionCode = request.getParameter("regionCode");//区域code
        String regionName = request.getParameter("regionName");//区域中文名
        String cropId = request.getParameter("cropId");//作物
        String cropName = request.getParameter("cropName");//作物name
        String dataSet = request.getParameter("dataSet");//数据集
        String resolution = request.getParameter("resolution");//分辨率
        String keyword = request.getParameter("keyword");//关键字

        String reportStorage = CephUtils.getAbsolutePath("") + CephUtils.getCephUrl("REPORT_STORAGE").replace("\\", File.separator);//\\192.168.1.210\mnt\data\report
        //String s = File.separator + reportTime + File.separator + cropId + File.separator;//    \2018-05-14\501\

        //  /区域code/年份/报告名称.pdf
        String s = File.separator + getRegionCodeFull(report.getRegionCode()) + reportTime.substring(0,4) + File.separator;

        File f2 = new File(reportStorage + s);//    \\192.168.1.210\mnt\data\report\区域code\年份\
        if (!f2.exists()) {
            f2.mkdirs();
        }
        String reportMonitorName = "";
        if ((DataSetEnum.DATA_SET_DISTRIBUTION.getId()).equals(Integer.parseInt(dataSet))) {//分布
            reportMonitorName = ReportConstant.REPORT_TYPE_AREA;
        }else if ((DataSetEnum.DATA_SET_YIELD.getId()).equals(Integer.parseInt(dataSet))) {//估产
            reportMonitorName = "产量估算";
        }else if ((DataSetEnum.DATA_SET_GROWTH.getId()).equals(Integer.parseInt(dataSet))) {//长势
            reportMonitorName = "长势";
        }else if ((DataSetEnum.DATA_SET_T.getId()).equals(Integer.parseInt(dataSet))) {//地温
            reportMonitorName = "地温";
        }else if ((DataSetEnum.DATA_SET_TRMM.getId()).equals(Integer.parseInt(dataSet))) {//降雨
            reportMonitorName = "降雨";
        }else if ((DataSetEnum.DATA_SET_DROUGHT.getId()).equals(Integer.parseInt(dataSet))) {//干旱
            reportMonitorName = "干旱";
        }
//        String fName = ReportConstant.REPORT_TITLE_BEGIN_NAME + (StringUtils.isNotBlank(cropName) ? "-" + cropName : "") + "-" + regionCode + regionName + "-" + reportMonitorName + "-" + reportTime;
        String fName =  (StringUtils.isNotBlank(cropName) ?cropName : "") + "-"  + regionName + "-" + reportMonitorName + "-" + reportTime;

        String suff = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);//文件后缀

        String storagePath = "";//全路径+文件名
        if(ReportConstant.REPORT_FILE_FORMAT_PDF.equals(suff)){//如果上传的是pdf文件
            storagePath = reportStorage + s + fName + "." + ReportConstant.REPORT_FILE_FORMAT_PDF;//pdf全路径+文件名

            File f = new File(reportStorage + s + fName + "." + ReportConstant.REPORT_FILE_FORMAT_DOC);//如果上传的是pdf, 那么就删除对应的doc文件
            if(f.exists()){
                f.delete();
            }
        }else if(ReportConstant.REPORT_FILE_FORMAT_DOC.equals(suff)){
            storagePath = reportStorage + s + fName + "." + ReportConstant.REPORT_FILE_FORMAT_DOC;//doc全路径+文件名
        }

        File copyFile = new File(storagePath);//文件
        if(copyFile.exists()){//文件存在
            copyFile.delete();//删除
        }
        try {
            file.transferTo(copyFile);//复制文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (copyFile.exists()) {
            String pPath = CephUtils.getCephUrl("REPORT_STORAGE").replace("\\", File.separator) + s;//路径
            String pathPdf = pPath + fName + "." + ReportConstant.REPORT_FILE_FORMAT_PDF;//路径加文件名;
            File fPdf = null;
            if(ReportConstant.REPORT_FILE_FORMAT_PDF.equals(suff)){//如果上传的是pdf文件
                fPdf = copyFile;
            }else if(ReportConstant.REPORT_FILE_FORMAT_DOC.equals(suff)){
                PdfUtils pdfUtils = new PdfUtils();
                File f3 = new File(CephUtils.getAbsolutePath("") + pPath);//    \\192.168.1.210\mnt\data\report\regionCode\2018\
                if (!f3.exists()) {
                    f3.mkdirs();
                }
                //pdf全路径+文件名
                String reportPdfFullPath = CephUtils.getAbsolutePath("") + pathPdf;
                fPdf = pdfUtils.wordToPdf(reportPdfFullPath, storagePath);
            }

            FileInputStream fis  = null;
            try{
                fis = new FileInputStream(fPdf);
                int size = fis.available();
                String fileSize = CephUtils.compileSizeUnit(size);//文件大小
                String suffix = fPdf.getName().substring(fPdf.getName().lastIndexOf(".") + 1);//文件后缀
                if(StringUtils.isNotBlank(reportId)){//编辑

                    //1.检查报告是否存在，存在就删除
                    ReportCreateParam reportCreateParam = new ReportCreateParam();
                    reportCreateParam.setRegionId(Long.parseLong(regionId));
                    reportCreateParam.setDataSet(Integer.parseInt(dataSet));
                    reportCreateParam.setDataTime(reportTime);
                    reportCreateParam.setResolution(Integer.parseInt(resolution));
                    if((DataSetEnum.DATA_SET_DISTRIBUTION.getId()).equals(Integer.parseInt(dataSet)) || (DataSetEnum.DATA_SET_YIELD.getId()).equals(Integer.parseInt(dataSet)) || (DataSetEnum.DATA_SET_GROWTH.getId()).equals(Integer.parseInt(dataSet))){
                        reportCreateParam.setCropId(Integer.parseInt(cropId));
                    }
                    List<DsReport> dsReportList = dsReportMapper.checkReportIsExist(reportCreateParam);
                    if (dsReportList.size() > 0) {
                        List<Integer> rIds = new ArrayList<Integer>();//报告id集合
                        for (DsReport ds : dsReportList) {
                            if (!reportId.equals(ds.getReportId()+"")) {
                                rIds.add(ds.getReportId());
                            }
                        }
                        DsReport dsReport = new DsReport();
                        dsReport.setDelFlag("0");//删除
                        dsReportMapper.batchDeleteReport(rIds, dsReport);
                    }

                    report.setReportId(Integer.parseInt(reportId));
                    report.setModifyTime(LocalDateTime.now());
                    report.setReportName(fName);
                    report.setFilePath(pathPdf);
                    report.setApprovalStatus(Integer.parseInt(reportType));//报告类型;自动生成9101,手动导入9102
                    report.setReportTime(LocalDateTime.parse(reportTime + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    report.setFileSize(fileSize);
                    report.setFileSuffix(suffix);
                    report.setKeyword(keyword);
                    dsReportMapper.updateReportInfo(report);//更新报告信息
                }else{//上传
                    //1.检查报告是否存在，存在就删除
                    ReportCreateParam reportCreateParam = new ReportCreateParam();
                    reportCreateParam.setRegionId(Long.parseLong(regionId));
                    reportCreateParam.setDataSet(Integer.parseInt(dataSet));
                    reportCreateParam.setDataTime(reportTime);
                    reportCreateParam.setResolution(Integer.parseInt(resolution));
                    if((DataSetEnum.DATA_SET_DISTRIBUTION.getId()).equals(Integer.parseInt(dataSet)) || (DataSetEnum.DATA_SET_YIELD.getId()).equals(Integer.parseInt(dataSet)) || (DataSetEnum.DATA_SET_GROWTH.getId()).equals(Integer.parseInt(dataSet))){
                        reportCreateParam.setCropId(Integer.parseInt(cropId));
                    }
                    List<DsReport> dsReportList = dsReportMapper.checkReportIsExist(reportCreateParam);
                    if (dsReportList.size() > 0) {
                        List<Integer> rIds = new ArrayList<Integer>();//报告id集合
                        for (DsReport ds : dsReportList) {
                            rIds.add(ds.getReportId());
                            System.out.println(ds.getReportId());
                        }
                        DsReport dsReport = new DsReport();
                        dsReport.setDelFlag("0");//删除
                        dsReportMapper.batchDeleteReport(rIds, dsReport);
                    }

                    report.setRegionId(Long.parseLong(regionId));
                    report.setReportName(fName);
                    report.setFilePath(pathPdf);
                    report.setCycle(1l);
                    if((DataSetEnum.DATA_SET_DISTRIBUTION.getId()).equals(Integer.parseInt(dataSet)) || (DataSetEnum.DATA_SET_YIELD.getId()).equals(Integer.parseInt(dataSet)) || (DataSetEnum.DATA_SET_GROWTH.getId()).equals(Integer.parseInt(dataSet))){
                        report.setCropId(Integer.parseInt(cropId));
                    }
                    report.setDataType(1);
                    report.setDsId(Integer.parseInt(dataSet));
                    report.setApprovalStatus(Integer.parseInt(reportType));//报告类型;自动生成9101,手动导入9102
                    report.setCreateTime(LocalDateTime.now());
                    //report.setCreator(reportCreateParam.getCreator());
                    //report.setCreatorName(reportCreateParam.getCreatorName());
                    //report.setDataStatus("1");
                    //report.setDelFlag("1");
                    //report.setPublishTime(LocalDateTime.now());
                    report.setFileSize(fileSize);
                    report.setFileSuffix(suffix);
                    report.setPublishStatus(2202);//待发布
                    report.setKeyword(keyword);
                    report.setResolution(Integer.parseInt(resolution));
                    report.setReportTime(LocalDateTime.parse(reportTime + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    saveReportInfo(report);//保存报告信息
                }
            }catch(Exception e){
                e.printStackTrace();
                return new ResultMessage(false, CommonDefineEnum.FAIL.getValue(),null , CommonDefineEnum.FAIL.getMesasge());
            }finally{
                if(null!=fis){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return new ResultMessage(true, CommonDefineEnum.SUCCESS.getValue(),null , CommonDefineEnum.SUCCESS.getMesasge());
    }

    /**
     * Description: 根据报告ids,批量撤回报告
     * @param reportIds
     * @return
     * @version <1> 2018/5/24 11:34 zhangshen: Created.
     */
    @Override
    public ResultMessage batchCancelReport(String reportIds){
        //数据转换
        JSONObject json = JSONObject.fromObject(reportIds);
        JSONArray jsonArray = JSONArray.fromObject(json.getString("reportIds"));
        Object[] ids = jsonArray.toArray();
        //登录人不为null
        if(ids.length > 0){
            List<Integer> rIds = new ArrayList<Integer>();//报告id集合
            for(Object id : ids){
                rIds.add(Integer.parseInt(id.toString()));
            }
            //组装数据
            DsReport dsReport = new DsReport();
            dsReport.setPublisherName(null);//发布人
            dsReport.setPublishTime(null);//发布时间
            dsReport.setPublishStatus(2202);//发布状态

            dsReportMapper.batchCancelReport(rIds, dsReport);
            return new ResultMessage(true, CommonDefineEnum.SUCCESS.getValue(),null , CommonDefineEnum.SUCCESS.getMesasge());
        }else{
            return new ResultMessage(false, CommonDefineEnum.FAIL.getValue(),null , CommonDefineEnum.FAIL.getMesasge());
        }
    }

    /**
     * Description: 根据报告ids,批量删除报告
     * @param reportIds
     * @return
     * @version <1> 2018/5/24 11:34 zhangshen: Created.
     */
    @Override
    public ResultMessage batchDeleteReport(String reportIds){
        //数据转换
        JSONObject json = JSONObject.fromObject(reportIds);
        JSONArray jsonArray = JSONArray.fromObject(json.getString("reportIds"));
        Object[] ids = jsonArray.toArray();
        //登录人不为null
        if( ids.length > 0){
            List<Integer> rIds = new ArrayList<Integer>();//报告id集合
            for(Object id : ids){
                rIds.add(Integer.parseInt(id.toString()));
            }
            //组装数据
            DsReport dsReport = new DsReport();
            dsReport.setDelFlag("0");//删除

            dsReportMapper.batchDeleteReport(rIds, dsReport);
            return new ResultMessage(true, CommonDefineEnum.SUCCESS.getValue() , CommonDefineEnum.SUCCESS.getMesasge(),null);
        }else{
            return new ResultMessage(false, CommonDefineEnum.FAIL.getValue() , CommonDefineEnum.FAIL.getMesasge(),null);
        }
    }

    /**
     * Description: 通过regionCode 查询地区code全路径
     * @param regionCode 区域code CHN-HU-JIN-ZHO
     * @return 区域全路径 如:CHN\CHN-HU\CHN-HU-JIN\CHN-HU-JIN-ZHO\
     * @version <1> 2018/6/5 15:44 zhangshen: Created.
     */
    private static String getRegionCodeFull(String regionCode){
        String regionCodeFull = "";
        if (StringUtils.isNotBlank(regionCode)) {
            String[] strArray = regionCode.split("-");
            for (int i=1; i<=strArray.length; i++) {
                if (i != strArray.length) {
                    regionCodeFull += regionCode.substring(0, StringUtils.ordinalIndexOf(regionCode, "-", i)) + File.separator;
                } else {
                    regionCodeFull += regionCode + File.separator;
                }
            }
        }
        return regionCodeFull;
    }


    /**
     * Description: 根据路径查看文件word文件是否存在
     * @param path 路径
     * @param suffix 后缀
     * @return
     * @version <1> 2018/6/6 14:02 zhangshen: Created.
     */
    @Override
    public boolean findWordIsExist(String path, String suffix) {
        path = path.substring(0, path.lastIndexOf(".")) + suffix;
        String filePath= CephUtils.getAbsolutePath(path);
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * Description: 根据条件查询数据时间
     * @param reportCreateParam
     * @return
     * @version <1> 2018/7/18 17:10 zhangshen: Created.
     */
    @Override
    public ResultMessage queryDateTimeList(ReportCreateParam reportCreateParam) {
        ResultMessage resultMessage = new ResultMessage();
        if (reportCreateParam != null && reportCreateParam.getDataSet() != null) {
            Integer dataSet = reportCreateParam.getDataSet();
            List<?> dsReportList = new ArrayList<>();
            if ((DataSetEnum.DATA_SET_DISTRIBUTION.getId()).equals(dataSet)) {//分布
                dsReportList = dsAreaService.queryDateTimeList(reportCreateParam);
            }else if ((DataSetEnum.DATA_SET_YIELD.getId()).equals(dataSet)) {//估产
                dsReportList = dsYieldService.queryDateTimeList(reportCreateParam);
            }else if ((DataSetEnum.DATA_SET_GROWTH.getId()).equals(dataSet)) {//长势
                dsReportList = dsGrowthService.queryDateTimeList(reportCreateParam);
            }else if ((DataSetEnum.DATA_SET_T.getId()).equals(dataSet)) {//地温
                dsReportList = dsTService.queryDateTimeList(reportCreateParam);
            }else if ((DataSetEnum.DATA_SET_TRMM.getId()).equals(dataSet)) {//降雨
                dsReportList = dsTrmmService.queryDateTimeList(reportCreateParam);
            }else if ((DataSetEnum.DATA_SET_DROUGHT.getId()).equals(dataSet)) {//干旱
                dsReportList = dsTtnService.queryDateTimeList(reportCreateParam);
            }
            if (dsReportList.size() > 0) {
                resultMessage.setFlag(true);
                resultMessage.setData(dsReportList);
            }
        }
        return resultMessage;
    }

    public static void main(String[] org) {
        /*OutputStream out = null;
        BufferedReader in = null;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("regionId", 3103000148L);//3102000029
            params.put("rasterFilePath", "//192.168.1.223/data/mnt/data/resultdata/dataset/distribution/CHN-HU/EarlyRice/2017/CHN-HU_EarlyRice_2017_GF1_16m.tif");//    //192.168.1.223/data/mnt/data/resultdata/dataset/distribution/CHN-HU/EarlyRice/2017/CHN-HU_EarlyRice_2017_GF1_16m.tif
            params.put("title", "2018年早稻分布专题图");//湖北省宜昌市2018年早稻分布专题图
            params.put("cropName", "水稻");//早稻

            URL url = new URL("http://192.168.1.222:7777/generateThematic");//http://192.168.1.222:7777/generateThematic
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
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

            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            String line;
            String resultData = "";
            while ((line = in.readLine()) != null) {
                resultData += line;
            }

            System.out.println(resultData);

            JSONObject jsonObject = JSONObject.fromObject(resultData);
            boolean flag = Boolean.parseBoolean(jsonObject.getString("flag"));

        } catch (Exception e) {
            e.printStackTrace();
        }*/


        //System.out.println(getRegionCodeFull("CHN-HU-JIN-ZHO"));
        /*DsReportServiceImpl d = new DsReportServiceImpl();
        List<DsArea> dsAreaListLast = new ArrayList<>();
        DsArea d1 = new DsArea();
        d1.setDataTime(DateUtil.UDateToLocalDate(DateUtil.StringToDateYMD("2018-05-05", "yyyy-MM-dd")));
        DsArea d2 = new DsArea();
        d2.setDataTime(DateUtil.UDateToLocalDate(DateUtil.StringToDateYMD("2018-05-09", "yyyy-MM-dd")));
        DsArea d3 = new DsArea();
        d3.setDataTime(DateUtil.UDateToLocalDate(DateUtil.StringToDateYMD("2018-05-12", "yyyy-MM-dd")));
        DsArea d4 = new DsArea();
        d4.setDataTime(DateUtil.UDateToLocalDate(DateUtil.StringToDateYMD("2018-05-04", "yyyy-MM-dd")));
        DsArea d5 = new DsArea();
        d5.setDataTime(DateUtil.UDateToLocalDate(DateUtil.StringToDateYMD("2018-04-03", "yyyy-MM-dd")));
        dsAreaListLast.add(d1);
        dsAreaListLast.add(d2);
        dsAreaListLast.add(d3);
        dsAreaListLast.add(d4);
        dsAreaListLast.add(d5);
        ReportCreateParam reportCreateParam = new ReportCreateParam();
        reportCreateParam.setDataTime("2019-05-06");
        List<DsArea> ds = d.getDsAreaListLast(dsAreaListLast, reportCreateParam);
        for(DsArea dd: ds){
            System.out.println(DateUtil.localDateToYMD(dd.getDataTime()));
        }*/

        /*List<DsArea> a = new ArrayList<>();
        a.add(new DsArea());
        System.out.println(CollectionUtil.isEnpty(a));*/
    }




    @Override
    public PageInfo<Map<String, Object>> findReportPage(int pageNum, int pageSize, Long regionId, Integer dataSetId, Integer accuracyId, Integer cropId, String startDate, String endDate,String token) {

        try{
            //token 、区域id 、 数据集id 不能为空
            if (StringUtils.isEmpty(token) || regionId == null || dataSetId == null){
                return new PageInfo<>(null);
            }

            //设置查询条件
            PageHelper.startPage(pageNum,pageSize);
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("publishStatus", PublishStatusEnum.PUBLISH_STATUS_Y.getValue());
            params.put("dataStatus", DataStatusEnum.Valid.getCode());
            params.put("delFlag", DelStatusEnum.Normal.getCode());
            params.put("regionId",regionId);
            params.put("dataSetId",dataSetId);

            //设置查询报告的数据权限
            List<Map<String,Object>> reportProductList = new ArrayList<Map<String,Object>>();
            params.put("reportProductList",reportProductList);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date paramStartDate = null;
            Date paramEndDate = null;
            if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
                paramStartDate = sdf.parse(startDate);
                paramEndDate = sdf.parse(endDate);
            }


            //判断是否是超级管理员   ，超级管理员不需要条件补全
            boolean isSuperAdmin = AccountTokenUtil.isSuperAdmin(token);
            if (!isSuperAdmin){
                List<Map<String,Object>> productList = AccountTokenUtil.getUserProductsFromRedis(token);
                if (productList == null || productList.isEmpty()){
                    //用户没有数据权限  ，返回空
                    return new PageInfo<>(null);
                }

                for (Map<String,Object> product : productList){
                    long productRegionId = Long.parseLong(product.get("regionId").toString());
                    int productDataSetId = Integer.parseInt(product.get("datasetId").toString());

                    if (productRegionId == regionId.longValue() && productDataSetId == dataSetId.intValue()){
                        int productAccuracyId = Integer.parseInt(product.get("accuracyId").toString());
                        int productCropId = Integer.parseInt(product.get("cropId").toString());
                        Date productStartDate = sdf.parse(product.get("startDate").toString());
                        Date productEndDate = sdf.parse(product.get("endDate").toString());

                        if (accuracyId != null && accuracyId.intValue() != productAccuracyId){
                            continue;
                        }

                        if (cropId != null && cropId.intValue() != productCropId){
                            continue;
                        }

                        if (paramStartDate != null && paramEndDate != null && (paramEndDate.before(productStartDate) || paramStartDate.after(productEndDate))){
                            //查询时间与数据权限时间不相交
                            continue;
                        }

                        //符合查询条件，补全条件
                        Map<String,Object> reportProduct = new HashMap<String,Object>();
                        reportProduct.put("accuracyId",productAccuracyId);
                        reportProduct.put("cropId",productCropId);
                        if (paramStartDate == null || paramEndDate == null){
                            //查询时间为空，用数据权限时间进行补全
                            reportProduct.put("startDate",productStartDate);
                            reportProduct.put("endDate",productEndDate);
                        } else {
                            //查询时间不为空，查询时间与数据权限时间分几种情况： 外包、左交、右交、内包
                            reportProduct.put("startDate",paramStartDate.before(productStartDate) ? productStartDate : paramStartDate);
                            reportProduct.put("endDate",paramEndDate.after(productEndDate) ? productEndDate : paramEndDate);
                        }
                        if (reportProduct != null && !reportProduct.isEmpty()){
                            reportProductList.add(reportProduct);
                        }
                    }
                }
            } else {
                //超级管理员
                Map<String,Object> reportProduct = new HashMap<String,Object>();
                if (accuracyId != null){
                    reportProduct.put("accuracyId",accuracyId);
                }

                if (cropId != null){
                    reportProduct.put("cropId",cropId);
                }

                if (paramStartDate != null && paramEndDate != null){
                    reportProduct.put("startDate",paramStartDate);
                    reportProduct.put("endDate",paramEndDate);
                }
                if (reportProduct != null && !reportProduct.isEmpty()){
                    reportProductList.add(reportProduct);
                }
            }

            List<Map<String,Object>> reportList = dsReportMapper.findReportPageByParam(params);
            return new PageInfo<>(reportList);
        } catch (Exception e){
            return new PageInfo<>(null);
        }
    }


    @Override
    public PageInfo<Map<String, Object>> findDsReportPage(int pageNum, int pageSize, Long regionId, Integer dataSetId, Integer accuracyId, Integer cropId, String startDate,String endDate) {

            //设置查询条件
            try{
                //token 、区域id 、 数据集id 不能为空
                if ( regionId == null || !StringUtils.isNotBlank(startDate)|| !StringUtils.isNotBlank(endDate)){
                    return new PageInfo<>(null);
                }
                PageHelper.startPage(pageNum,pageSize);
                //设置查询条件
                Map<String,Object> params = new HashMap<String,Object>();
                params.put("publishStatus", PublishStatusEnum.PUBLISH_STATUS_Y.getValue());
                params.put("dataStatus", DataStatusEnum.Valid.getCode());
                params.put("delFlag", DelStatusEnum.Normal.getCode());
                params.put("regionId",regionId);
                params.put("cropId",cropId);
                params.put("dataSetId",dataSetId);
                params.put("accuracyId",accuracyId);
                //设置查询报告的数据权限
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date paramStartDate = null;
                Date paramEndDate = null;
                if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
                    paramStartDate = sdf.parse(startDate);
                    paramEndDate = sdf.parse(endDate);
                }
                params.put("startDate",paramStartDate);
                params.put("endDate",paramEndDate);

                List<Map<String,Object>> reportList = dsReportMapper.findReportPage(params);
                return new PageInfo<>(reportList);
            } catch (Exception e){
                System.out.print(e.toString());
                return new PageInfo<>(null);
            }
    }

    /**
     * 查询所有审批通过的报告
     * @param regionId  区域ID
     * @param dataSetId 数据集ID
     * @param accuracyId 分辨率ID
     * @param cropId   作物ID
     * @param dataTime  报告时间（年）
     * @return
     * @version <1> 2018-08-10 cxw : Created.
     */
    @Override
    public ResultMessage findAllReport(Long regionId, Integer dataSetId, Integer accuracyId, Integer cropId, String dataTime) {
        ResultMessage resultMessage = new ResultMessage();
        try{
            //token 、区域id 、 数据集id 不能为空
            if ( regionId == null || dataSetId == null || accuracyId ==null){
                return ResultMessage.fail("查询失败");
            }

            //设置查询条件
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("publishStatus", PublishStatusEnum.PUBLISH_STATUS_Y.getValue());
            params.put("dataStatus", DataStatusEnum.Valid.getCode());
            params.put("delFlag", DelStatusEnum.Normal.getCode());
            params.put("regionId",regionId);
            params.put("cropId",cropId);
            params.put("dataSetId",dataSetId);
            params.put("accuracyId",accuracyId);
            //设置查询报告的数据权限
            String startDate = null;
            String endDate = null;
            if(StringUtils.isNotBlank(dataTime)){
                startDate = dataTime+"-01-01";
                endDate = dataTime+"-12-31";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date paramStartDate = null;
            Date paramEndDate = null;
            if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
                paramStartDate = sdf.parse(startDate);
                paramEndDate = sdf.parse(endDate);
            }
            params.put("startDate",paramStartDate);
            params.put("endDate",paramEndDate);

            List<Map<String,Object>> reportList = dsReportMapper.findAllReport(params);
              return ResultMessage.success(reportList);
        } catch (Exception e){
            System.out.print(e.toString());
            return ResultMessage.fail("查询失败");
        }
    }

    @Override
    public PageInfo<Map<String, Object>> findReportShowByPage(Map<String, Object> map) {
        map.put("publishStatus", map.get("publishStatus"));//已发布
        PageHelper.startPage(Integer.parseInt(map.get("page").toString()),Integer.parseInt( map.get("rows").toString()));
        List<Map<String, Object>> list = dsReportMapper.findReportShowByPage(map);
        return new PageInfo<Map<String, Object>>(list);
    }

    @Override
    public ResultMessage findReportShowById(Integer reportId) {
        ResultMessage resultMessage = new ResultMessage();
        Map<String,Object>map= dsReportMapper.findReportShowById(reportId);
        return  ResultMessage.success(map);
    }

    @Override
    public ResultMessage findAllReportTempType() {
        List<Object> list = new ArrayList<Object>();

        for (ReportEnum reportEnum:ReportEnum.values()){
            JSONObject object = new JSONObject();
            object.put("key",reportEnum.getKey());
            object.put("value",reportEnum.getValue());
            list.add(object);
        }
        return ResultMessage.success(list);
    }
}
