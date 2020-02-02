package com.jh.forum.bbs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.jh.biz.feign.IDictService;
import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.base.service.impl.BaseServiceImpl;
import com.jh.forum.bbs.Enum.ArticleStatusEnum;
import com.jh.forum.bbs.Enum.DownloadDataTypeEnum;
import com.jh.forum.bbs.constant.ForumConstant;
import com.jh.forum.bbs.entity.ForumDownloadData;
import com.jh.forum.bbs.mapping.IForumDownloadDataMapper;
import com.jh.forum.bbs.service.IForumDownloadDataService;
import com.jh.forum.bbs.service.IForumShoppingCarService;
import com.jh.forum.bbs.util.CommonUtils;
import com.jh.forum.bbs.vo.DownloadDataVo;
import com.jh.forum.bbs.vo.ShoppingCarVo;
import com.jh.util.cache.IdTransformUtils;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description:  可下载数据实现类
 * @version<1> 2019-07-01 lcw :Created.
 */
@Service
@Transactional
public class ForumDownloadDataServiceImpl extends BaseServiceImpl<DownloadDataVo, ForumDownloadData,Integer> implements IForumDownloadDataService {

    @Autowired
    private IForumDownloadDataMapper downloadDataMapper;
    @Autowired
    private IDictService dictService;
    @Autowired
    private IForumShoppingCarService forumShoppingCarService;

    @Override
    protected IBaseMapper<DownloadDataVo, ForumDownloadData, Integer> getDao() {
        return downloadDataMapper;
    }

    /**
     * 分页查询可下载数据
     * @param downloadDataVo
     * @return
     */
    @Override
    public PageInfo<DownloadDataVo> findByPage(DownloadDataVo downloadDataVo) {
        if(downloadDataVo.getTimeFlag() != null ){
            if(downloadDataVo.getTimeFlag() != 5){
                downloadDataVo.setStartDate(CommonUtils.getDate(downloadDataVo.getTimeFlag()));
                downloadDataVo.setEndDate(LocalDate.now());
            }
        }
        List<DownloadDataVo> list = new ArrayList<DownloadDataVo>();

        if(null!=downloadDataVo.getAccuracyBegin()||null!=downloadDataVo.getAccuracyEnd()){
            ResultMessage resultMessage=dictService.queryDictChildrenByParentId(4000);
            if(resultMessage.isFlag()){
                List<Map<String,Object>> accuracies = (List<Map<String,Object>>)resultMessage.getData();
                List<Object> values= Lists.newArrayList();
                accuracies.forEach(map -> {
                    if(map.get("dataValue").equals("TRMM-0.25°")){
                        return;
                    }
                    Integer dataValue=Integer.parseInt(map.get("dataValue").toString());
                    if(dataValue.equals(downloadDataVo.getAccuracyBegin())&&dataValue==1000){
                        values.add(map.get("dictId"));
                        return;
                    }
                    if(null==downloadDataVo.getAccuracyEnd()){
                        return;
                    }
                    if(dataValue>=downloadDataVo.getAccuracyBegin()&&dataValue<downloadDataVo.getAccuracyEnd() ){
                        values.add(map.get("dictId"));
                    }
                });
                downloadDataVo.setFileList(values);
            }

            //表示没有该精度的数据，则直接返回空
            if(downloadDataVo.getFileList().size() == 0){
                return new PageInfo<DownloadDataVo>(list);
            }

        }


        PageHelper.startPage(downloadDataVo.getPage(), downloadDataVo.getRows());

         list = downloadDataMapper.getListByCombination(downloadDataVo);

        IdTransformUtils.idTransForList(list);
        list = setImageUrl(list);

        return new PageInfo<DownloadDataVo>(list);
    }

    @Override
    public ResultMessage findById(Integer dataId) {

        DownloadDataVo downloadData = downloadDataMapper.findById(dataId);
        IdTransformUtils.idTransForObj(downloadData);

        //图片路径需要截取前面的文件夹
        int lastIndex = downloadData.getDataPath().lastIndexOf("\\");


        List<Map<String,Object>> list = CephUtils.listFiles(CephUtils.getAbsolutePath(downloadData.getDataPath().substring(0,lastIndex)), "");
        downloadData.setImageUrl(CephUtils.getShowPath1(downloadData.getDataPath()));
        List<Object> tempList = new ArrayList<Object>();

        if(list != null && list.size() > 0){
            for(Map<String,Object> voMap : list){
                //tempList.add(downloadData.getDataPath() + File.separator + voMap.get("fileName"));
                tempList.add(voMap.get("fileName"));
            }
        }
        downloadData.setFileList(tempList);

        return ResultMessage.success(downloadData);
    }

    @Override
    public ResultMessage findHotDataList() {

        //近两个月生产的数据随机

        LocalDate dataTime = LocalDate.now().minusMonths(2);
        System.out.println(dataTime);

        DownloadDataVo downloadDataVo  = new DownloadDataVo();
        downloadDataVo.setDataTime(dataTime);

        List<DownloadDataVo> list = downloadDataMapper.findHotDataList(downloadDataVo);

        if(list != null && list.size() > ForumConstant.CHANGE_DOWNLOAD_DATA_COUNT){
            list =  CommonUtils.getSubStringByRadom(list,ForumConstant.CHANGE_DOWNLOAD_DATA_COUNT);
        }
        return ResultMessage.success(list);
    }

    @Override
    public ResultMessage findAgriculturalData(DownloadDataVo downloadDataVo) {
        downloadDataVo.setCheckNum(ForumConstant.FORUM_SHOW_DATA_NUM);
        if(downloadDataVo.getTimeFlag()!=null && downloadDataVo.getTimeFlag()!=0  ){
            LocalDate date = LocalDate.now();
            downloadDataVo.setEndDate(date);
            switch (downloadDataVo.getTimeFlag()){
                case 1:
                    date = date.minusMonths(1);
                    downloadDataVo.setStartDate(date);
                    break;
                case 3:
                    date = date.minusMonths(3);
                    downloadDataVo.setStartDate(date);
                    break;
                case 6:
                    date = date.minusMonths(6);
                    downloadDataVo.setStartDate(date);
                    break;
                case 12:
                    date = date.minusMonths(12);
                    downloadDataVo.setStartDate(date);
                    break;
            }

        }
        List<DownloadDataVo> list = downloadDataMapper.findAgriculturalData(downloadDataVo);
        list = setImageUrlForNewPath(list);
        return ResultMessage.success(list);
    }

    @Override
    public PageInfo<DownloadDataVo> findAllByPage(DownloadDataVo downloadDataVo) {
        PageHelper.startPage(downloadDataVo.getPage(), downloadDataVo.getRows());
        if(downloadDataVo.getTimeFlag()!=null && downloadDataVo.getTimeFlag()!=0){
            LocalDate date = LocalDate.now();
            downloadDataVo.setEndDate(date);
            switch (downloadDataVo.getTimeFlag()){
                case 1:
                    date = date.minusMonths(1);
                    downloadDataVo.setStartDate(date);
                    break;
                case 3:
                    date = date.minusMonths(3);
                    downloadDataVo.setStartDate(date);
                    break;
                case 6:
                    date = date.minusMonths(6);
                    downloadDataVo.setStartDate(date);
                    break;
                case 12:
                    date = date.minusMonths(12);
                    downloadDataVo.setStartDate(date);
                    break;
            }

        }
        List<DownloadDataVo> list = downloadDataMapper.findAgriculturalData(downloadDataVo);
        list = setImageUrlForNewPath(list);
        return new PageInfo<DownloadDataVo>(list);
    }


    /**
     * 将查询到的文章list，设置摘要图片
     * @param
     * @return
     * @version <1> 2019/3/25 mason:Created.
     */
    public List<DownloadDataVo> setImageUrl(List<DownloadDataVo> list){
        if(CollectionUtils.isNotEmpty(list)){
            for(DownloadDataVo articleVO : list){

                articleVO.setDataPath(CephUtils.getShowPath1(articleVO.getDataPath()));

                if(StringUtils.isNotBlank(articleVO.getDataContent())){
                    int startPos = articleVO.getDataContent().indexOf("<img");
                    if(startPos > -1){
                        String _tmpContent = articleVO.getDataContent().substring(startPos);
                        String imgurl = _tmpContent.substring(_tmpContent.indexOf("src=")+5,_tmpContent.indexOf("/>"));

                        String[] imgArr = imgurl.split("\"");
                        articleVO.setImageUrl(imgArr[0]);
                    }
                }
            }
        }
        return list;
    }

    /*
     * 功能描述:将查询到的图片路径中的/ 替换成\
     * @Param:
     * @Return:
     * @version<1>  2019/9/5  wangli :Created
     */
    public List<DownloadDataVo> setImageUrlForNewPath(List<DownloadDataVo> list){
        if(CollectionUtils.isNotEmpty(list)){
            for(DownloadDataVo downloadDataVo : list){
                if(downloadDataVo.getDataPath()!=null){
                    downloadDataVo.setDataPath(CephUtils.getShowPath1(downloadDataVo.getDataPath()));
                }
            }
        }
        return list;
    }

    @Transactional(readOnly = false)
    @Override
    public ResultMessage saveDownLoadData(DownloadDataVo downloadDataVo) {
        if (downloadDataVo.getDataId() == null) {
            downloadDataVo.setDataStatus("1");
            downloadDataVo.setDelFlag("1");
            downloadDataVo.setDataFlag(1);
            downloadDataVo.setCreateTime(LocalDateTime.now());
            downloadDataMapper.save(downloadDataVo);
            DownloadDataVo dataVo = new DownloadDataVo();
            dataVo.setDataId(downloadDataVo.getDataId());
            String url=CephUtils.getCephUrl("FORUM_DOWNLOAD_DATA_STORAGE")
                    .replace("\\",File.separator)+File.separator + downloadDataVo.getDataId().toString();

            CephUtils.mkdirs(CephUtils.getAbsolutePath(url));

            dataVo.setDataPath(url);
            downloadDataMapper.updateDownloadData(dataVo);
            return ResultMessage.success();
        }
        downloadDataVo.setModifyTime(LocalDateTime.now());
        return updateData(downloadDataVo);
    }

    @Override
    public ResultMessage audit(DownloadDataVo downloadDataVo) {

        ResultMessage result = ResultMessage.success();
        if(downloadDataVo.getDataIds() == null || (downloadDataVo.getDataIds() !=null  && downloadDataVo.getDataIds().size() == 0)){

            result.setFlag(false);
            result.setMsg("可下载数据ID不能为空");
            return result;
        }

        if(downloadDataVo.getPushStatus() == null){
            result.setFlag(false);
            result.setMsg("状态不能为空");
            return result;
        }

        //如果只有单条数据审批的时候 需要判断该条数据的价格是否为空
        if(downloadDataVo.getDataIds().size()==1){
            DownloadDataVo downloadData = downloadDataMapper.findById(downloadDataVo.getDataIds().get(0));
            if(downloadData.getPrice()==null){
                result.setFlag(false);
                result.setMsg("请先设置价格");
                return result;
            }
        }
        downloadDataMapper.updateDownloadData(downloadDataVo);
        return result;
    }

    @Override
    public ResultMessage updateData(DownloadDataVo downloadDataVo) {
        downloadDataVo.setPushStatus(ArticleStatusEnum.ARTICLE_STATUS_PENDING.getId());
        downloadDataMapper.updateDownloadData(downloadDataVo);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage getDataById(Integer dataId) {
        ForumDownloadData downloadData = downloadDataMapper.getById(dataId);
        IdTransformUtils.idTransForBean(downloadData);
        downloadData.setDataPath(CephUtils.getShowPath1(downloadData.getDataPath()));
        return ResultMessage.success(downloadData);
    }

    @Override
    public ResultMessage getSimilarData(Integer classify) {
        List<DownloadDataVo> list = downloadDataMapper.getSimilarData(classify);
        list = setImageUrlForNewPath(list);
        return ResultMessage.success(list);
    }

    @Override
    public ResultMessage confirmationOfOrder(DownloadDataVo downloadDataVo) {
        List<DownloadDataVo> list = downloadDataMapper.confirmationOfOrder(downloadDataVo.getIdList());
        list = setImageUrlForNewPath(list);
        //统计订单价格
        BigDecimal totalPrice = downloadDataMapper.totalPriceForOrder(downloadDataVo.getIdList());
        Map<String,Object> map = new HashMap<>();
        if(list.size()>0){
            map.put("totalPrice",totalPrice);
            map.put("goodsList",list);
        }
        //从购物车里面清空已经购买的商品
        ShoppingCarVo shoppingCarVo = new ShoppingCarVo();
        shoppingCarVo.setIdList(downloadDataVo.getIdList());
        shoppingCarVo.setUserId(downloadDataVo.getUserId());
        forumShoppingCarService.deleteShoppingCarRecord(shoppingCarVo);
        return ResultMessage.success(map);
    }

    @Override
    public ResultMessage downloadExample(DownloadDataVo downloadDataVo) {
        List<DownloadDataVo> list = downloadDataMapper.downloadExample(downloadDataVo);
        return ResultMessage.success(list);
    }

    @Override
    public ResultMessage freeDownloadData(DownloadDataVo downloadDataVo) {
        List<DownloadDataVo> list = downloadDataMapper.freeDownloadData(downloadDataVo);
        return ResultMessage.success(list);
    }

    @Override
    public ResultMessage downloadStatistc(Integer dataId) {
        downloadDataMapper.downloadStatistc(dataId);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage collectStatistc(Integer dataId) {
        downloadDataMapper.collectStatistc(dataId);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage cancelCollectStatistic(Integer dataId) {
        downloadDataMapper.cancelCollectStatistic(dataId);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage calculate() {
        List<Integer> oneList = new ArrayList<>();
            //降雨
            oneList.add(DownloadDataTypeEnum.classify_trmm.getId());
            //地温
            oneList.add(DownloadDataTypeEnum.classify_t.getId());
            //气温
            oneList.add(DownloadDataTypeEnum.classify_weather.getId());
            //干旱指数
            oneList.add(DownloadDataTypeEnum.classify_drought_index.getId());
                //设置价格为1的数据价格
                downloadDataMapper.calculateForOne(oneList);

        List<Integer> tenList = new ArrayList<>();
            //病虫害监测
            tenList.add(DownloadDataTypeEnum.classify_diseases.getId());
                //设置价格为10的数据价格
                downloadDataMapper.calculateForTen(tenList);
        return ResultMessage.success();
    }

}
