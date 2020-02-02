package com.jh.manage.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.logs.entity.Logs;
import com.jh.logs.service.ILogsService;
import com.jh.manage.alarm.Enum.AlarmBusinessTypeEnum;
import com.jh.manage.alarm.Enum.DataAlamStatusEnum;
import com.jh.manage.alarm.entity.DataAlarm;
import com.jh.manage.alarm.service.IDataAlarmService;
import com.jh.manage.order.Enum.*;
import com.jh.manage.order.Enum.PersonTypeEnum;
import com.jh.manage.order.entity.DataOrder;
import com.jh.manage.order.entity.DataOrderDetail;
import com.jh.manage.order.mapping.IDataOrderMapper;
import com.jh.manage.order.model.DataOrderPath;
import com.jh.manage.order.model.OrderParam;
import com.jh.manage.order.service.IDataOrderDetailService;
import com.jh.manage.order.service.IDataOrderPathService;
import com.jh.manage.order.service.IDataOrderService;
import com.jh.manage.order.utils.AppPush;
import com.jh.manage.storage.entity.DataStorage;
import com.jh.manage.storage.model.StorageParam;
import com.jh.manage.storage.service.IDataStorageService;
import com.jh.util.DateUtil;
import com.jh.util.DownloadUtil;
import com.jh.util.ceph.CephUtils;
import com.jh.util.ceph.LinkUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

/**
 *数据订单服务：
 * 1.创建订单、订单明细
 *	数据订单：通过数据检索界面检索数据，生成数据订单
 *	需求订单：通过表单填写
 * 2.订单(订单明细)查询
 * 3.订单审核
 * 4.订单分发
 * @version <1> 2018-01-29 Hayden:Created.
 */
@Service
@Transactional
public class DataOrderServiceImpl extends BaseServiceImpl<OrderParam, DataOrder, Integer> implements IDataOrderService{


    @Autowired
    private IDataOrderMapper dataOrderMapper;

    @Autowired
    private IDataOrderDetailService dataOrderDetailService;

    @Autowired
    private IDataOrderPathService dataOrderPathService;

    @Autowired
    private IDataStorageService dataStorageService;

//    @Autowired
//    private IPermPersonService permPersonService;

    @Autowired
    private ILogsService iLogsService;

    @Autowired
    private IDataAlarmService iDataAlarmService;

    //创建LINK
    LinkUtil linkUtil = LinkUtil.getInstance();

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DataOrderServiceImpl.class);

    /**
     * 订单分发分页查询
     * @param orderParam
     * @return PageInfo
     * @version <1> 2018-03-07 cxw： Created.
     */
    @Override
    public PageInfo<DataOrder> findOrderHandleByPage(OrderParam orderParam) {
        PageHelper.startPage(orderParam.getPage(), orderParam.getRows());
        List<DataOrder> list = dataOrderMapper.findOrderHandleByPage(orderParam);
        return new PageInfo<DataOrder>(list);
    }

    /**
     * 订单审核分页查询
     * @param orderParam
     * @return PageInfo
     * @version <1> 2018-03-07 cxw： Created.
     */
    @Override
    public PageInfo<DataOrder> findOrderAuditByPage(OrderParam orderParam) {
        PageHelper.startPage(orderParam.getPage(), orderParam.getRows());
        List<DataOrder> list = dataOrderMapper.findOrderAuditByPage(orderParam);
        return new PageInfo<DataOrder>(list);
    }

    /**
     * 订单列表分页查询
     * @param orderParam
     * @return PageInfo
     * @version <1> 2018-03-07 cxw： Created.
     */
    @Override
    public PageInfo<DataOrder> findOrderListByPage(OrderParam orderParam) {
        PageHelper.startPage(orderParam.getPage(), orderParam.getRows());
        List<DataOrder> list = dataOrderMapper.findOrderListByPage(orderParam);
       // List<DataOrder> dataOrders = new ArrayList<DataOrder>();
        for(int i = 0;i<list.size();i++){
            DataOrder dOrder = new DataOrder();
            dOrder = list.get(i);
            if(dOrder.getAuditStatus().intValue()== (OrderAuditStatusEnum.DATA_AUDIT_STATUS_YFF.getId().intValue())&&dOrder.getDataPath()!=null&&!"".equals(dOrder.getDataPath()))
            {
                String reworkFilePath= CephUtils.getShowPath(dOrder.getDataPath());  //订单link可访问路径
                dOrder.setDataPath(reworkFilePath);
            }
            else {
                dOrder.setDataPath("");
            }
            //dataOrders.add(dOrder);
        }
        return new PageInfo<DataOrder>(list);
    }


    /**
     *订单详情查询
     * @param orderId 订单ID
     * @return ResultMessage
     * @version <1> 2018-03-07 cxw： Created.
     */
    @Override
    public ResultMessage findOrderById( Integer orderId){

        ResultMessage result = ResultMessage.success();
        if(orderId!=null){
            DataOrder dOrder = dataOrderMapper.findOrderById(orderId);
            if(dOrder.getAuditStatus().intValue()==(OrderAuditStatusEnum.DATA_AUDIT_STATUS_YFF.getId().intValue())&&dOrder.getDataPath()!=null&&!"".equals(dOrder.getDataPath()))
            {
                String reworkFilePath= CephUtils.getShowPath(dOrder.getDataPath());  //订单link可访问路径
                dOrder.setDataPath(reworkFilePath);
            }
            else {
                dOrder.setDataPath("");
            }
            result.setData(dOrder);
            return result;
        }
        else{
            result = ResultMessage.fail();
            result.setMsg("订单ID不能为空");
            return result;
        }
    }

    /**
     *订单分发详情查询
     * @param orderId 订单ID
     * @return ResultMessage
     * @version <1> 2018-03-23 cxw： Created.
     */
    @Override
    public ResultMessage findOrderDistributeDetailById( Integer orderId){

        ResultMessage result = ResultMessage.success();
        if(orderId!=null){
            DataOrder dOrder = dataOrderMapper.findOrderById(orderId);
            if(dOrder.getDataPath()!=null&&!"".equals(dOrder.getDataPath()))
            {
                String reworkFilePath= CephUtils.getShowPath(dOrder.getDataPath());  //订单link可访问路径
                dOrder.setDataPath(reworkFilePath);
            }
            result.setData(dOrder);
            return result;
        }
        else{
            result = ResultMessage.fail();
            result.setMsg("订单ID不能为空");
            return result;
        }
    }

    @Override
    protected IBaseMapper<OrderParam, DataOrder, Integer> getDao() {
        return dataOrderMapper;
    }

    /**
     * 分发订单
     *  1.手动将数据复制到已创建的对应订单目录
     *  2.同意分发后用户可获取相应数据
     *  3.设置订单目录有效期，过期后订单目录自动消失（定时任务扫描过期订单目录删除）
     * @param  orderParam:订单参数对象
     * @return ResultMessage :
     * @version <1> 2018-3-08 cxw:Created.
     */
    @Override
    public ResultMessage updateDistributeOrder(OrderParam orderParam) {
        ResultMessage result = orderParam.checkHandleParam();
        DataOrder dataOrder = dataOrderMapper.findOrderById(orderParam.getOrderId());

        //订单编号
        String orderCode = dataOrder.getOrderCode();
        orderParam.setOrderCode(orderCode);
        if(null != orderParam) {//操作人即为分发人
            dataOrder.setHandlerPersonName(orderParam.getOperatorName());
            dataOrder.setHandlerPerson(orderParam.getOperator());
        }
        if(result.isFlag()){
            dataOrder.setAuditStatus(orderParam.getAuditStatus());
            dataOrder.setHandleSuggestion(orderParam.getHandleSuggestion());
            if(StringUtils.isNotBlank(orderParam.getDataAccessTime())){
                dataOrder.setDataAccessTime(DateUtil.strtoLocalDataTime(orderParam.getDataAccessTime()));
            }
            dataOrder.setHandleTime(DateUtil.getCurrentTime().toLocalDateTime());
            //dataOrder.setRemark(orderParam.getRemark());
            //同意分发
            if(orderParam.getAuditStatus().intValue()==(OrderAuditStatusEnum.DATA_AUDIT_STATUS_YFF.getId().intValue())){
//                ResultMessage res= findFileDetailByOrderId(orderParam.getOrderId());
                String reworkFilePath= CephUtils.getAbsolutePath(dataOrder.getDataPath());  //订单link可访问路径
                ResultMessage res = getFileList(dataOrder.getDataPath());

                //判断分发文件是否存在
                if(res.isFlag()){
                    //分发订单
                    int orderId = dataOrderMapper.updateOrder(dataOrder);
                    //保存明细

                    if(orderId>0){
                        result.setMsg("订单(" + dataOrder.getOrderCode() + ")" + OrderStatusEnum.ORDER_HANDLE_SUCCESS.getMsg());
                        List<Map<String,Object>> list = (List<Map<String,Object>>)res.getData();
                        if(list != null && list.size() > 0 ){
                            for(Map<String,Object> voMap : list){
                                DataOrderDetail orderDetail = new DataOrderDetail();
                                orderDetail.setOrderId(dataOrder.getOrderId());
                                orderDetail.setFileName(voMap.get("fileName").toString());
                                orderDetail.setFileSize(voMap.get("fileSize").toString());
                                dataOrderDetailService.saveDataOrderDetail(orderDetail);
                            }
                        }
                    }
                    else{
                        result=ResultMessage.fail();
                        result.setMsg("订单(" + dataOrder.getOrderCode() + ")" + OrderStatusEnum.ORDER_HANDLE_FAIL.getMsg());
                    }
                }
                else{
                    result=ResultMessage.fail();
                    result.setMsg(res.getMsg());
                }
            }else{//拒绝分发
                //分发订单
                int orderId = dataOrderMapper.updateOrder(dataOrder);
                if(orderId>0){
                    result.setMsg("订单(" + dataOrder.getOrderCode() + ")" + OrderStatusEnum.ORDER_NO_HANDLE_SUCCESS.getMsg());
//                }else{
//                    result=ResultMessage.fail();
//                    result.setMsg(OrderStatusEnum.ORDER_NO_HANDLE_FAIL.getMsg());
                }
            }
        }
        else{
            result=ResultMessage.fail();
           // result.setMsg(result.getMsg());
            result.setMsg("订单(" + orderCode + ")" + OrderStatusEnum.ORDER_HANDLE_FAIL.getMsg());
        }


//        insertLog(orderParam, result);
        insertAlarm(dataOrder, result,"需求订单(" + orderParam.getOrderCode() + ")分发");
        return result;
    }





    /**
     *删除订单
     * @param orderId 订单Id
     * @return ResultMessage
     * @version <1> 2018-03-13 cxw： Created.
     */
    @Override
    public ResultMessage delOrderById(Integer orderId) {
        ResultMessage result = ResultMessage.success();
        if(orderId!=null){
            //删除主订单
            int num = dataOrderMapper.delOrderById(orderId);
            if(num>0){
                /*//删除订单详情
                result = dataOrderDetailService.delOrderDetailByOrderId(orderId);*/
                result.setMsg("订单删除成功");
            }
            else {
                result = ResultMessage.fail();
                result.setMsg("订单删除失败");
            }
        }
        else {
            result = ResultMessage.fail();
            result.setMsg("订单删除失败");
        }

        return result;
    }

    /**
     *修改数据订单
     * @param orderParam 订单对象
     * @return ResultMessage
     * @version <1> 2018-03-13 cxw： Created.
     */
//    @Override
//    public ResultMessage updateOrder(OrderParam orderParam) {
//        ResultMessage result = ResultMessage.success();
//        DataOrder dataOrder = new DataOrder();
//        if (null != orderParam) {
//            dataOrder.setModifierName(orderParam.getModifierName());
//            dataOrder.setModifier(orderParam.getModifier());
//        }
//            //修改订单信息
//            int num = dataOrderMapper.updateOrder(dataOrder);
//            if(num>0){
//                result.setMsg("订单修改成功");
//            }
//            else {
//                result = ResultMessage.fail();
//                result.setMsg("订单修改失败");
//            }
//        return result;
//    }

    /**
     * 下载订单数据(判断下载数据是否过期)
     * @param orderParam 订单对象
     * @return ResultMessage
     * @version <1> 2018-03-16 cxw： Created.
     * @version<2> 2018-04-12 lcw : 移除request对象，修改日志统一调用方法</2>
     */
    @Override
    public ResultMessage downLoadOrderDataById(OrderParam orderParam) {
        ResultMessage result = ResultMessage.success();
        //查询订单分发路径
        DataOrder order = dataOrderMapper.findOrderById(orderParam.getOrderId());
        //验证数据是否已过期，过期则不能下载
        LocalDateTime dataAccessTime = order.getDataAccessTime();
        LocalDateTime nowTime = DateUtil.getCurrentTime().toLocalDateTime();
        //日期去掉时分秒
        Date dataAccessDate = DateUtil.strToDate(DateUtil.localDateTimeToDate(dataAccessTime));
        //日期去掉时分秒
        Date nowDate = DateUtil.strToDate(DateUtil.localDateTimeToDate(nowTime));
        if(nowDate.getTime()<=dataAccessDate.getTime())
        {
           /* ResultMessage resultMessage = dataOrderPathService.findDataOrderPathList(orderId);
            if(resultMessage.isFlag())
            {
                List<DataOrderPath> dops = (List<DataOrderPath>)resultMessage.getData();
                Vector<String> fileList = new Vector<>();
                for(int i=0;i<dops.size();i++){
                    fileList.add(dops.get(i).getDataPath());
                }
                DownloadUtil.getInstance().downloadByOrder(fileList, order.getOrderCode());
                result.setMsg("订单数据下载到'"+DownloadUtil.DOWNLOAD_PATH+"'成功");
            }
            else{
                result.setMsg(OrderStatusEnum.ORDER_DOWNLOAD_FAIL.getMsg());
            }*/
       }
        else
        {
            result=ResultMessage.fail();
            result.setMsg(OrderStatusEnum.ORDER_DOWNLOAD_OVERTIME.getMsg());
        }

        return result;
    }

    /**
     *订单列表文件详情查询（拼接下载路径专用）
     * @param orderId 订单ID
     * @return ResultMessage
     * @version <1> 2018-04-09 cxw： Created.
     */
//    @Override
//    public ResultMessage findOrderFilePathDetailById( Integer orderId){
//
//        ResultMessage result = ResultMessage.success();
//        if(orderId!=null){
//            DataOrder dOrder =  dataOrderMapper.findOrderById(orderId);
//            if(dOrder.getDataPath()!=null&&!"".equals(dOrder.getDataPath()))
//            {
//                String reworkFilePath = PropertyUtil.getPropertiesForConfig("DOWNLOAD_URL") + File.separator + dOrder.getDataPath();
//                dOrder.setDataPath(reworkFilePath);
//            }
//            result.setData(dOrder.getDataPath());
//            return result;
//        }
//        else{
//            result = ResultMessage.fail();
//            result.setMsg("订单ID不能为空");
//            return result;
//        }
//    }

    /**
     * 订单分发时查询文件详情：根据订单主键查询订单关联文件详情
     * @param orderId 订单ID
     * @return ResultMessage
     * @version <1> 2018-03-22 cxw： Created.
     */
    @Override
    public ResultMessage findFileDetailByOrderId(Integer orderId) {
        ResultMessage result = ResultMessage.success();
//        ResultMessage res = findOrderDistributeDetailById(orderId);

//        DataOrder dOrder = dataOrderMapper.findOrderById(orderId);
       // ResultMessage resPath = findOrderFilePathDetailById(orderId);
//        if(res.isFlag()){
        String path ="";
        DataOrder order = dataOrderMapper.findOrderById(orderId);
        //if(order.getAuditStatus().intValue()==OrderAuditStatusEnum.DATA_AUDIT_STATUS_DFF.getId().intValue()||order.getAuditStatus().intValue()==OrderAuditStatusEnum.DATA_AUDIT_STATUS_YFF.getId().intValue()){
        path = order.getDataPath();
        if(path!=null&&!path.equals("")){
            result = getFileList(path);
        }
        else{
            result=ResultMessage.fail();
            result.setMsg("无分发数据");
        }
//        }
//        else{
//            result = ResultMessage.fail();
//            result.setMsg(res.getMsg());
//        }
        return result;
    }

    /**
     * 订单分发时，查询该分发步骤中存在的文件
     * @param path
     * @return
     * @version <1> 2018-04-16 lcw : Created.
     */
    private  ResultMessage getFileList(String path) {
        ResultMessage result = ResultMessage.success();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

//        String downPath = path.replace(CephUtils.getServer()+CephUtils.getCephRoot(),"");
        String absPath = CephUtils.getAbsolutePath(path);
        list = CephUtils.listFilesForOrder(absPath,path);
        if(list.size()==0){
            result=ResultMessage.fail();
            result.setMsg("无分发数据");
        }
        else{
            result.setData(list);
        }
        return result;

    }

    /**
     * 判断已分发数据是否过期
     * @param dataAccessTime 截止时间
     * @return
     * @version<1> 2018-04-09 cxw :Created.
     */
    @Override
    public ResultMessage findOverdueOrder(String dataAccessTime) {
        ResultMessage result = ResultMessage.success();
        List<DataOrder> list = dataOrderMapper.findOverdueOrder(dataAccessTime);
        if(list.size()>0){
	        result.setData(list);
        } else{
            result =  ResultMessage.fail();
            result.setMsg("无过期订单数据");
        }
        return result;
    }

    /**
     * 构造订单编号
     * @param orderId
     * @param orderType
     * @return
     * @version<1> 2018-03-20 lcw :Created.
     */
    private String getOrderCode(Integer orderId , Integer orderType){

        if(orderId == null || orderType == null ){
            return null;
        }
        StringBuffer code = new StringBuffer();
        if(OrderTypeEnum.DATA_ORDER_TYPE_SJDD.getId().equals(orderType)){
            code.append(OrderTypeEnum.DATA_ORDER_TYPE_SJDD.getKey());
        }else if(OrderTypeEnum.DATA_ORDER_TYPE_XQDD.getId().equals(orderType)){
            code.append(OrderTypeEnum.DATA_ORDER_TYPE_XQDD.getKey());
        }
        String _orderId = orderId.toString();
        int length = _orderId.length(); //长度
        for(int i = 0 ; i + length < 9;i++){
            code.append("0");
        }
        code.append(_orderId);
        return code.toString();
    }

    /**
     * 下载订单数据
     * @param orderParam 订单对象
     * @return ResultMessage
     * @version <1> 2018-03-16 cxw： Created.
     */
    @Override
    public void downLoadOrderData(OrderParam orderParam,Integer orderDetailId, HttpServletResponse response) {
        ResultMessage result = ResultMessage.success();
        //查询订单分发路径
        DataOrder order = dataOrderMapper.findOrderById(orderParam.getOrderId());
//        dataOrder.setOrderCode(order.getOrderCode());
        orderParam.setOrderCode(order.getOrderCode());
        //验证数据是否已过期，过期则不能下载
        LocalDateTime dataAccessTime = order.getDataAccessTime();
        LocalDateTime nowTime = DateUtil.getCurrentTime().toLocalDateTime();
        //日期去掉时分秒
        Date dataAccessDate = DateUtil.strToDate(DateUtil.localDateTimeToDate(dataAccessTime));
        //日期去掉时分秒
        Date nowDate = DateUtil.strToDate(DateUtil.localDateTimeToDate(nowTime));
        if(nowDate.getTime()<=dataAccessDate.getTime()) {

            ResultMessage detailResult = dataOrderDetailService.findOrderDetailById(orderDetailId);
            if(detailResult.isFlag()){
                DataOrderDetail detail = (DataOrderDetail)detailResult.getData();

                DownloadUtil.getInstance().downloadFile(order.getDataPath() + File.separator + detail.getFileName(),response);
            }

        }
    }


    @Override
    public ResultMessage queryOrderCount(OrderParam orderParam) {
        //判断timeSlot是否为空
        if(orderParam.getTimeSlot()!=null && orderParam.getTimeSlot()!=""){
            LocalDate localDate=LocalDate.now();
            switch (orderParam.getTimeSlot()){
                case "day":
                    orderParam.setBeginTime(localDate.toString());
                    break;
                case "threeDay":
                    orderParam.setBeginTime(localDate.plusDays(-2).toString());
                    break;
                case "week"://例如今天周五  查询上周五到今天的数据
                    orderParam.setBeginTime(localDate.plusWeeks(-1).toString());
                    break;
                case "month"://5月31日  查询的是4月30日到  5月31日的数据
                    orderParam.setBeginTime(localDate.plusMonths(-1).toString());
                    break;
            }
            orderParam.setEndTime(localDate.toString());
        }
        return ResultMessage.success(dataOrderMapper.queryOrderCount(orderParam));
    }

    @Override
    public ResultMessage queryOrderDetailCount(OrderParam orderParam) {
        //判断timeSlot是否为空
        if(orderParam.getTimeSlot()!=null && orderParam.getTimeSlot()!=""){
            LocalDate localDate=LocalDate.now();
            switch (orderParam.getTimeSlot()){
                case "day":
                    orderParam.setBeginTime(localDate.toString());
                    break;
                case "threeDay":
                    orderParam.setBeginTime(localDate.plusDays(-2).toString());
                    break;
                case "week"://例如今天周五  查询上周五到今天的数据
                    orderParam.setBeginTime(localDate.plusWeeks(-1).toString());
                    break;
                case "month"://5月31日  查询的是4月30日到  5月31日的数据
                    orderParam.setBeginTime(localDate.plusMonths(-1).toString());
                    break;
            }
            orderParam.setEndTime(localDate.toString());
        }
        return ResultMessage.success(dataOrderMapper.queryOrderDetailCount(orderParam));
    }

    @Override
    public ResultMessage queryDataOrderBySatellite(OrderParam orderParam) {
        //判断timeSlot是否为空
        if(orderParam.getTimeSlot()!=null && orderParam.getTimeSlot()!=""){
            LocalDate localDate=LocalDate.now();
            switch (orderParam.getTimeSlot()){
                case "day":
                    orderParam.setBeginTime(localDate.toString());
                    break;
                case "threeDay":
                    orderParam.setBeginTime(localDate.plusDays(-2).toString());
                    break;
                case "week"://例如今天周五  查询上周五到今天的数据
                    orderParam.setBeginTime(localDate.plusWeeks(-1).toString());
                    break;
                case "month"://5月31日  查询的是4月30日到  5月31日的数据
                    orderParam.setBeginTime(localDate.plusMonths(-1).toString());
                    break;
            }
            orderParam.setEndTime(localDate.toString());
        }
        return ResultMessage.success(dataOrderMapper.queryDataOrderBySatellite(orderParam));
    }

    @Override
    public ResultMessage queryDataOrderSum(OrderParam orderParam) {
        List<Map<String, Object>> total=dataOrderMapper.queryDataOrderBySatellite(orderParam);
        List<Map<String, Object>> topFive=dataOrderMapper.queryDataOrderByTopFive(orderParam);
        LocalDate localDate=LocalDate.now();
        orderParam.setBeginTime(localDate.toString());
        orderParam.setEndTime(localDate.toString());
        List<Map<String, Object>> today=dataOrderMapper.queryDataOrderBySatellite(orderParam);
        Map map=new HashMap();
        map.put("total",total);
        map.put("today",today);
        map.put("topFive",topFive);
        return ResultMessage.success(map);
    }

    /**
     * 下载数据订单申请文件
     * @param url 文件路径
     * @return ResultMessage
     * @version <1> 2018-06-26 cxw： Created.
     */
    @Override
    public void downLoadOrderWordById(String url,HttpServletResponse response) {
        ResultMessage   result=ResultMessage.fail();
        result.setMsg(OrderStatusEnum.ORDER_DOWNLOAD_OVERTIME.getMsg());

        if(StringUtils.isNotBlank(url))
        {
            DownloadUtil.getInstance().downloadFile(url.replaceAll("\\\\","/"),response);
            result = ResultMessage.success();
            result.setMsg("订单数据下载成功");
        }
        else{
            result.setMsg(OrderStatusEnum.ORDER_DOWNLOAD_FAIL.getMsg());
        }

    }

    @Override
    public ResultMessage queryOrderStatistics(OrderParam orderParam) {
        List<Map<String,Object>> list = dataOrderMapper.queryOrderStatistics(orderParam);

        return ResultMessage.success(list);
    }


    /**
     * 组装orderparam
     * @param dataOrder
     * @return
     */
    private OrderParam getOrderParam(DataOrder dataOrder){
        OrderParam orderParam = new OrderParam();
        orderParam.setOperator(dataOrder.getCreator());
        orderParam.setOperatorName(dataOrder.getCreatorName());
        orderParam.setOrderCode(dataOrder.getOrderCode());
        return orderParam;
    }


    /**
     * 添加日志
     * @param orderParam
     * @param result
     * @version<1> 2018-04-12 lcw :Created.</1>
     */
    private void insertLog(OrderParam orderParam, ResultMessage result){
        //添加日志信息
        Logs logs = new Logs();
        logs.setOperator(orderParam.getOperator());
        logs.setOperatorName(orderParam.getOperatorName());
        logs.setOpContent(result.getMsg()+"("+orderParam.getOrderCode()+")");
        iLogsService.addLog(logs);
    }

    /**
     * 插入告警消息
     * @param dataOrder
     * @param result
     * @version<1> 2018-04-12 lcw :Created.</1>
     */
    private void insertAlarm(DataOrder dataOrder, ResultMessage result, String businessName){
        DataAlarm dataAlarm = new DataAlarm();
        //添加告警信息
        dataAlarm.setAlarmTime(dataOrder.getAuditTime());
        dataAlarm.setBusinessId(dataOrder.getOrderId());
        dataAlarm.setBusinessType(AlarmBusinessTypeEnum.BUSINESS_TYPE_ORDER.getId());
        dataAlarm.setCreator(dataOrder.getCreator()); //审核之后推送给订单创建人
        dataAlarm.setCreatorName(dataOrder.getCreatorName());
        dataAlarm.setCreateTime(dataOrder.getAuditTime());
        dataAlarm.setReason(result.getMsg());
        if(result.isFlag()){
            dataAlarm.setBusStatus(DataAlamStatusEnum.DATA_ALAM_SUCCESS.getValue());
        }else{
            dataAlarm.setBusStatus(DataAlamStatusEnum.DATA_ALAM_FAIL.getValue());
        }
        dataAlarm.setBusinessName(businessName);

        iDataAlarmService.saveAlarm(dataAlarm);
    }


    /*******************************************************************************************************************************
     * *****************************************************************************************************************************
     * ***************************************************订单管理功能重构* 2018-07-23 lcw ********************************************
     * *****************************************************************************************************************************
     * *****************************************************************************************************************************
     */



    //查看订单





    /**
     *订单审核
     *  1.数据订单审核： 数据订单审核通过后，直接分发数据
     *  2.需求订单审核：需求订单审核通过后，推送给数据处理人员做数据分发处理
     * @param  orderParam 订单对象
     * @return ResultMessage
     * @version <1> 2018-03-14 cxw： Created.
     * @version<2> 2018-04-12 lcw : 重写该方法
     */
    @Override
    public ResultMessage updateAuditOrder(OrderParam orderParam) {
        ResultMessage result = orderParam.checkAuditParam();

        //订单信息
        DataOrder dataOrder = dataOrderMapper.findOrderById(orderParam.getOrderId());
        //订单编号
        String orderCode = dataOrder.getOrderCode();
        orderParam.setOrderCode(orderCode);

        String businessName = "";
        if(dataOrder.getOrderType().intValue() == OrderTypeEnum.DATA_ORDER_TYPE_SJDD.getId().intValue()){ //数据订单
            businessName = "数据订单("+orderCode+")审核与分发";
        }

        if(dataOrder.getOrderType().intValue() == OrderTypeEnum.DATA_ORDER_TYPE_XQDD.getId().intValue()){ //需求订单
            businessName = "需求订单("+orderCode+")审核";
        }


        //开始审核订单
        if(result.isFlag()){  //校验参数成功
            dataOrder.setOrderAuditor(orderParam.getOperator()); //审核人
            dataOrder.setOrderAuditorName(orderParam.getOrderAuditorName());  //审核人
            dataOrder.setAuditStatus(orderParam.getAuditStatus());  //更新审核状态
            dataOrder.setAuditTime(DateUtil.getCurrentTime().toLocalDateTime() );
            dataOrder.setAuditSuggestion(orderParam.getAuditSuggestion());
//            dataOrder.setOrderId(orderParam.getOrderId());
            if(StringUtils.isNotBlank(orderParam.getDataAccessTime())){
                dataOrder.setDataAccessTime(DateUtil.strtoLocalDataTime(orderParam.getDataAccessTime()));
            }

            //订单审核通过时
            if(orderParam.getAuditStatus().intValue()==OrderAuditStatusEnum.DATA_AUDIT_STATUS_DFF.getId().intValue()||orderParam.getAuditStatus().intValue()==OrderAuditStatusEnum.DATA_AUDIT_STATUS_YFF.getId().intValue())
            {

                String mobile = dataOrder.getMobile();

                String rootPath = "";
                if(OrderAttributeEnum.DATA_ORDER_ATTRIBUTE_INNER.getId().intValue() == dataOrder.getOrderAttribute().intValue()){ //内部订单
                    rootPath = CephUtils.makeInnerOrderDirectory(orderCode, mobile);
                }

                if(OrderAttributeEnum.DATA_ORDER_ATTRIBUTE_OUTER.getId().intValue() == dataOrder.getOrderAttribute().intValue()){ //外部订单
                    rootPath = CephUtils.makeOuterOrderDirectory(orderCode, mobile);
                }

                //订单任务存储相对路径
                dataOrder.setDataPath(rootPath);

                //如果是审核需求订单则直接创建目录，数据订单需要创建目录然后分发数据路径
                if(orderParam.getOrderType().intValue()==OrderTypeEnum.DATA_ORDER_TYPE_SJDD.getId().intValue()){

                    //数据订单的审核人和分发人为同一个人，处于同一个步骤
                    dataOrder.setHandlerPerson(orderParam.getOperator());
                    dataOrder.setHandlerPersonName(orderParam.getOperatorName());
                    dataOrder.setHandleTime(DateUtil.getCurrentTime().toLocalDateTime());
                    dataOrder.setAuditStatus(OrderAuditStatusEnum.DATA_AUDIT_STATUS_YFF.getId()); //若为数据订单，设置为已分发
                    List<String> list = new ArrayList<>();

                    //查询已选择的原始待审核分发数据
                    ResultMessage res = dataStorageService.findOrderDetailByOrderId(orderParam.getOrderId());
                    if(res.isFlag()){
                        List<DataStorage> dataStorages = (List<DataStorage>)res.getData();
                        List<DataOrderPath> dataOrderPaths = new ArrayList<DataOrderPath>();
                        for(int i=0;i<dataStorages.size();i++)
                        {
                            //link文件存储的相对路径，该字段存在数据库中
                            String dataPath = CephUtils.getOrderLink(rootPath,dataStorages.get(i).getFileName() );
                            String reworkFilePath=dataStorages.get(i).getStorageUrl();
                            //创建可在linux系统的可执行link命令
                            String link= LinkUtil.makeLink(reworkFilePath, dataPath);
                            list.add(link);

                            //订单路径对象
                            DataOrderPath dop = new DataOrderPath();
                            dop.setOrderId(orderParam.getOrderId());
                            dop.setDataPath(dataPath);
                            dataOrderPaths.add(dop);
                        }

                        boolean bool = linkUtil.exec(list); //执行link命令
                        if(bool){
                            //审核数据订单
                            int orderId = dataOrderMapper.updateOrder(dataOrder);

                            //数剧订单审核通过后，分发只是更新状态即可
//                            //批量插入分发路径
//                            dataOrderPaths.removeAll(Collections.singleton(null));
//                            ResultMessage rm_path = dataOrderPathService.saveDataOrderPathList(dataOrderPaths);
                            if(orderId>0) {
                                result.setMsg("数据订单(" + orderCode + ")" + OrderStatusEnum.DATA_ORDER_AUDIT_SUCCESS.getMsg());
                            }else {
                                result = ResultMessage.fail();
                                result.setMsg("数据订单(" + orderCode + ")"  + OrderStatusEnum.DATA_ORDER_AUDIT_FAIL.getMsg());
                            }
                        }
                    } else {
                        result = ResultMessage.fail();
                        result.setMsg("数据订单(" + orderCode + ")" + OrderStatusEnum.DATA_ORDER_AUDIT_FAIL.getMsg());
                    }
                }
                else{
                    //审核需求订单
                    int orderId = dataOrderMapper.updateOrder(dataOrder);
                    if(orderId>0) {
                        result.setMsg("需求订单(" + orderCode +")" +OrderStatusEnum.DATA_ORDER_AUDIT_SUCCESS.getMsg());
                    }else {
                        result = ResultMessage.fail();
                        //result.setMsg("需求订单审核通过操作失败");
                        result.setMsg("需求订单(" + orderCode +")" + OrderStatusEnum.DATA_ORDER_AUDIT_FAIL.getMsg());
                    }
                }
            }else{
                //订单审核不通过时执行方法
                int orderId = dataOrderMapper.updateOrder(dataOrder);
                if(orderId>0) {
                    result.setMsg("订单("+  orderCode +")" + OrderStatusEnum.ORDER_NO_AUDIT_FAIL.getMsg());
                }
                else {
                    result = ResultMessage.fail();
                    result.setMsg("订单("+  orderCode +")" + OrderStatusEnum.DATA_ORDER_AUDIT_FAIL.getMsg());
                }
            }
        }
        else{
            result = ResultMessage.fail();
            // result.setMsg("订单ID或审核状态不能为空");
            result.setMsg("订单("+ orderCode + ")" + OrderStatusEnum.ORDER_NO_AUDIT_FAIL.getMsg());
//            result.setMsg("订单(" + orderCode + ")" + OrderStatusEnum.DATA_ORDER_AUDIT_FAIL.getMsg());
        }

        insertLog(orderParam, result); //日志消息
        insertAlarm(dataOrder, result, businessName); //告警消息
        return result;
    }




    /**
     *修改需求订单
     * 1.当需求订单未通过审核时可修改需求信息
     * 2.审核通过后不可进行修改
     * @param  orderParam 订单对象
     * @return ResultMessage
     * @version <1> 2018-03-14 cxw： Created.
     * @version<2>2018-07-23 lcw : 只有需求订单在审核前可以修改，数据订单创建完成后不可更改
     */
/*    @Override
    public ResultMessage updateNeedOrder(OrderParam orderParam) {
        ResultMessage result = ResultMessage.fail("订单修改失败");
        if(orderParam.checkEditParam().isFlag()){
            DataOrder dataOrder = dataOrderMapper.findOrderById(orderParam.getOrderId());
            if(dataOrder != null){
                dataOrder.setModifierName(orderParam.getOperatorName());
                dataOrder.setModifier(orderParam.getOperator());
                dataOrder.setOrderDescription(orderParam.getOrderDescription());
                dataOrder.setRemark(orderParam.getRemark());
                dataOrder.setModifyTime(DateUtil.getCurrentTime().toLocalDateTime());
                //修改需求订单
                int orderId = dataOrderMapper.updateOrder(dataOrder);
                if(orderId>0) {
                    result = ResultMessage.success();
                    result.setMsg("需求订单("+ orderParam.getOrderCode() +")"+ OrderStatusEnum.NEED_ORDER_EDIT_SUCCESS.getMsg());
                }
                else {
                    result.setMsg("需求订单("+ orderParam.getOrderCode() +")" +OrderStatusEnum.NEED_ORDER_EDIT_FAIL.getMsg());
                }
            }
        }else{
            result.setMsg("需求订单("+ orderParam.getOrderCode() +")" +OrderStatusEnum.NEED_ORDER_EDIT_FAIL.getMsg());
        }

        insertLog(orderParam, result);
        return result;
    }*/

    /**
     *修改需求订单
     * 1.当需求订单未通过审核时可修改需求信息
     * 2.审核通过后不可进行修改
     * @param  orderParam 订单对象
     * @param file 上传文件
     * @return ResultMessage
     * @version <1> 2018-03-14 cxw： Created.
     */
    @Override
    public ResultMessage updateNeedOrder(OrderParam orderParam,MultipartFile file) {
        ResultMessage result = ResultMessage.success("需求订单修改成功");
        DataOrder dataOrder = new DataOrder();
        if(orderParam.checkEditParam().isFlag()){
            if (null != orderParam) {
                dataOrder.setModifierName(orderParam.getOperatorName());
                dataOrder.setModifier(orderParam.getOperator());
            }
            dataOrder.setOrderCode(orderParam.getOrderCode());
            dataOrder.setOrderDescription(orderParam.getOrderDescription());
            dataOrder.setOrderId(orderParam.getOrderId());
            dataOrder.setRemark(orderParam.getRemark());
            dataOrder.setModifyTime(DateUtil.getCurrentTime().toLocalDateTime());
            if(file != null)
            {
                if(StringUtils.isNotBlank(file.getOriginalFilename())) {
                    result = saveAuditWord(orderParam, file); //上传申请文件
                    if (result.isFlag()) {
                        String path = (String) result.getData();
                        dataOrder.setWordPath(path);
                        dataOrder.setWordName(path.substring(path.lastIndexOf(File.separator) + 1, path.length()));
                        Integer updateNum = dataOrderMapper.updateOrder(dataOrder);
                        result.setMsg("需求订单(" + orderParam.getOrderCode() + ")" + OrderStatusEnum.NEED_ORDER_EDIT_SUCCESS.getMsg());
                    } else {
                        result = ResultMessage.fail();
                        result.setMsg("数据申请单上传失败");
                    }
                }
            }
            //修改需求订单
            int orderId = dataOrderMapper.updateOrder(dataOrder);
            if(orderId>0) {
                result.setMsg("需求订单("+ orderParam.getOrderCode() +")"+ OrderStatusEnum.NEED_ORDER_EDIT_SUCCESS.getMsg());
                //推送消息
                pushOrder(dataOrder.getOrderCode(), dataOrder.getModifierName());
            }
            else {
                result = ResultMessage.fail();
                result.setMsg("需求订单("+ orderParam.getOrderCode() +")" +OrderStatusEnum.NEED_ORDER_EDIT_FAIL.getMsg());
            }
        }
        else{
            result = ResultMessage.fail();
            result.setMsg("需求订单("+ orderParam.getOrderCode() +")" +OrderStatusEnum.NEED_ORDER_EDIT_FAIL.getMsg());
        }

        insertLog(getOrderParam(dataOrder), result);
        return result;
    }


    /**
     *创建数据订单
     * 1.创建订单目录
     * 2.用户直接在首页选择所需数据创建数据订单
     * 3.管理员审核分发通过后可获取数据
     * @param dataOrderDetails 订单参数：DataStorage元数据对象ID参数为必须获取参数，不能为空
     * @return ResultMessage
     * @version <1> 2018-03-12 cxw： Created.
     * @version <2> 2018-04-12 lcw :
     *   1.移除request对象（用户信息从controller中获取）
     *   2.提取日志方法（调用该类公共的insertLogs方法）
     *   3.移除告警消息保存操作，创建订单时不需要添加告警消息
     */
    @Override
    public ResultMessage createOrder(OrderParam orderParam, List<DataOrderDetail> dataOrderDetails,MultipartFile file) {

        orderParam.setBeginTime(LocalDate.now().toString());

        //判断订单是否超出阈值
        //外部用户 查询角色阈值
        if(PersonTypeEnum.PERSON_TYPE_OUTER.getId().intValue() == orderParam.getPersonType().intValue()){
            Integer orderFileNum = orderParam.getOrderFileNum();
            if(orderFileNum != null){
                //查询当日订单总数
                Integer orderFileByDay = dataOrderMapper.queryOrderFileNumByDay(orderParam);
                int orderFileSize = orderFileByDay + dataOrderDetails.size();

                if(orderFileNum < orderFileSize ){
                    int num = orderFileNum - orderFileByDay; //订单阈值-当日已提交文件景数
                    if(num > 0 && num < dataOrderDetails.size()){

                        return ResultMessage.fail("当日（订单文件阈值："+ orderFileNum + "）最多还能创建包含"+ (orderFileNum - orderFileByDay) +  "景影像的订单，订单创建失败");
                    }
                    return ResultMessage.fail("当日订单影像景数超出阈值(阈值为："+ orderFileNum +  ")，订单创建失败");

                }

            }

        }



        DataOrder dataOrder = new DataOrder();
        String orderTypeName = OrderTypeEnum.DATA_ORDER_TYPE_SJDD.getMsg();
        ResultMessage result = ResultMessage.success(orderTypeName+"订单创建成功");
        if(OrderTypeEnum.DATA_ORDER_TYPE_XQDD.getId().intValue() == orderParam.getOrderType())
        {
            orderTypeName = OrderTypeEnum.DATA_ORDER_TYPE_XQDD.getMsg();
            dataOrder.setOrderDescription(orderParam.getOrderDescription());
            result = ResultMessage.success(orderTypeName+"订单创建成功");
        }else {
            if (dataOrderDetails.size() > 0) {
                //判断元数据ID是否为空
                boolean bol = true;
                for (int i = 0; i < dataOrderDetails.size(); i++) {
                    if (dataOrderDetails.get(i).getStorageId() == null) {
                        bol = false;
                    }
                }
                if (!bol) {
                    result = ResultMessage.fail();
                    result.setMsg("元数据不能为空");
                    return result;
                }
            }
        }
        if(orderParam != null){
            dataOrder.setCreatorName(orderParam.getOperatorName());
            dataOrder.setCreator(orderParam.getOperator());
        }
//        if(file!=null) {
            dataOrder.setDelFlag("1");
            dataOrder.setDataStatus("1");
            dataOrder.setAuditStatus(OrderAuditStatusEnum.DATA_AUDIT_STATUS_DSH.getId());
            dataOrder.setOrderDescription(orderParam.getOrderDescription());
            dataOrder.setOrderType(orderParam.getOrderType());
            if (PersonTypeEnum.PERSON_TYPE_INNER.getId().equals(orderParam.getPersonType())) {
                dataOrder.setOrderAttribute(OrderAttributeEnum.DATA_ORDER_ATTRIBUTE_INNER.getId()); //内部订单
            } else if (PersonTypeEnum.PERSON_TYPE_OUTER.getId().equals(orderParam.getPersonType())) {
                dataOrder.setOrderAttribute(OrderAttributeEnum.DATA_ORDER_ATTRIBUTE_OUTER.getId()); //外部订单
            }
            //添加数据订单
            Integer orderId = dataOrderMapper.insertDataOrder(dataOrder);
            //创建订单编号，然后更新订单信息
            String orderCode = getOrderCode(dataOrder.getOrderId(), orderParam.getOrderType());
            dataOrder.setOrderCode(orderCode);
            orderParam.setOrderCode(orderCode);
            Integer updateNum = dataOrderMapper.updateOrder(dataOrder);
            //添加数据订单详情，如果数据订单添加成功便添加数据订单详情
            //判断订单类型，如果是数据订单，则要保存明细
            Integer orderType = dataOrder.getOrderType();  //订单类型
            if (OrderTypeEnum.DATA_ORDER_TYPE_SJDD.getId().intValue() == orderType) //数据订单
            {
                if (updateNum > 0) {
                    for (int i = 0; i < dataOrderDetails.size(); i++) {
                        DataOrderDetail dataOrderDetail = new DataOrderDetail();
                        dataOrderDetail.setOrderId(dataOrder.getOrderId());
                        dataOrderDetail.setStorageId(dataOrderDetails.get(i).getStorageId());
                        //根据源数据ID查询数据，保存路径
                        ResultMessage storageResult = dataStorageService.findStorageById(dataOrderDetail.getStorageId());
                        if (storageResult.isFlag()) {
                            DataStorage storage = (DataStorage) storageResult.getData();
                            dataOrderDetail.setFileName(storage.getFileName());
                            dataOrderDetail.setFileSize(storage.getDataSize());
                        } else {
                            return ResultMessage.fail("源数据不存在");
                        }
                        dataOrderDetailService.saveDataOrderDetail(dataOrderDetail);
                    }
                    result = ResultMessage.success();
                    result.setMsg(orderTypeName + "(" + orderCode + ")" + OrderStatusEnum.DATA_ORDER_CREATE_SUCCESS.getMsg());

                    pushOrder(dataOrder.getOrderCode() , dataOrder.getCreatorName());
                } else {
                    result = ResultMessage.fail();
                    result.setMsg(orderTypeName + "(" + orderCode + ")" + OrderStatusEnum.DATA_ORDER_CREATE_FAIL.getMsg());
                }
            }
        if(result.isFlag() && null != file){
            result = saveAuditWord(orderParam,file); //上传申请文件
            if(result.isFlag()) {
                String path = (String) result.getData();
                System.out.print(path);
                dataOrder.setWordPath(path);
                dataOrder.setWordName(path.substring(path.lastIndexOf(File.separator) + 1, path.length()));
                Integer updateNumber = dataOrderMapper.updateOrder(dataOrder);
                result.setMsg(orderTypeName+"("+ orderParam.getOrderCode() +")" + OrderStatusEnum.DATA_ORDER_CREATE_SUCCESS.getMsg());
            }
            else {
                result = ResultMessage.fail();
                result.setMsg("数据申请单上传失败");
            }
        }
        insertLog(getOrderParam(dataOrder), result);  //插入告警消息
        return result;
    }

    /**
     *保存数据申请单
     * @param  orderParam 订单对象
     * @param file 申请订单文件
     * @return ResultMessage
     * @version <1> 2018-06-26 cxw： Created.
     */
    public ResultMessage saveAuditWord(OrderParam orderParam,MultipartFile file) {
        String orderTypeName = OrderTypeEnum.DATA_ORDER_TYPE_SJDD.getMsg();
        ResultMessage result = ResultMessage.success(orderTypeName+"订单创建成功");
        if(OrderTypeEnum.DATA_ORDER_TYPE_XQDD.getId().equals(orderParam.getOrderType()))
        {
            orderTypeName = OrderTypeEnum.DATA_ORDER_TYPE_XQDD.getMsg();
            result = ResultMessage.success(orderTypeName+"订单创建成功");
        }
        String rootPath = "";
        if(PersonTypeEnum.PERSON_TYPE_INNER.getId().equals(orderParam.getPersonType())){
            rootPath = CephUtils.makeInnerOrderDirectory(orderParam.getOrderCode(), orderParam.getCreatorName()); //内部订单
        }else if(PersonTypeEnum.PERSON_TYPE_OUTER.getId().equals(orderParam.getPersonType())){
            rootPath = CephUtils.makeOuterOrderDirectory(orderParam.getOrderCode(), orderParam.getCreatorName()); //外部订单
        }
        String orderStorage = CephUtils.getAbsolutePath("") +rootPath;
       // String orderStorage = CephUtils.getCephRoot() +rootPath;
        //String s = File.separator +orderParam.getOperatorName() + File.separator + DateUtil.getTimestamptoYMD(DateUtil.getCurrentTime())+File.separator+orderParam.getOrderCode()+File.separator;
        logger.error(orderStorage);
        File f2 = new File(orderStorage);//
        if (!f2.exists()) {
            f2.mkdirs();
        }
        String reportMonitorName = "";
        String fName = file.getOriginalFilename() ;
        String suff = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);//文件后缀

        String storagePath = "";//全路径+文件名
        String filePath = rootPath.substring(0, rootPath.lastIndexOf(File.separator)) + File.separator + fName.substring(0, fName.lastIndexOf(".")) + "_" + orderParam.getOrderCode() + "." + suff; //数据库保存路径
        if("doc".equals(suff) || "docx".equals(suff) || "pdf".equals(suff) || "jpg".equals(suff) || "png".equals(suff)){
            storagePath = orderStorage.substring(0, orderStorage.lastIndexOf(File.separator)) + File.separator + fName.substring(0, fName.lastIndexOf(".")) + "_" + orderParam.getOrderCode() + "." + suff;//pdf全路径+文件名
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
                File f3 = new File(storagePath);//    \\192.168.1.210\mnt\data\report\regionCode\2018\
                if (!f3.exists()) {
                    f3.mkdirs();
                }

                FileInputStream fis  = null;
                try{
                    fis = new FileInputStream(copyFile);
                    int size = fis.available();
                    String fileSize = CephUtils.compileSizeUnit(size);//文件大小
                    String suffix = copyFile.getName().substring(copyFile.getName().lastIndexOf(".") + 1);//文件后缀
                }catch(Exception e){
                    e.printStackTrace();
                    result = ResultMessage.fail("数据申请单上传失败");
                    return result;
                }finally{
                    if(null!=fis){
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            result = ResultMessage.fail("数据申请单上传失败");
                            return result;
                        }
                        result.setData(filePath);
                        result.setMsg("创建订单成功");
                    }
                }
            }
        }
        else{
            result = ResultMessage.fail("数据申请单文件类型必须为doc、docx、pdf、jpg、png其中之一");
            return result;
        }
        return result;
    }




    /**
     * 查询待审核订单
     * @param orderParam
     * @return
     */
    @Override
    public ResultMessage queryNotAuditList(OrderParam orderParam) {
        List<Map<String,Object>> list = dataOrderMapper.queryNotAuditList(orderParam);

        return ResultMessage.success(list);
    }
    /**
     * 查询已审核订单
     * @param orderParam
     * @return
     */
    @Override
    public ResultMessage queryAuditList(OrderParam orderParam) {
        List<Map<String,Object>> list = dataOrderMapper.queryAuditList(orderParam);

        return ResultMessage.success(list);
    }

    @Override
    public ResultMessage queryOrderForApp(Integer orderId) {

        Map<String,Object> orderMap = dataOrderMapper.queryOrderForApp(orderId);

        ResultMessage detailResult = dataOrderDetailService.findOrderDetailByOrderId(orderId);
        if(detailResult.isFlag()){
            List<DataOrderDetail>  detailList = (List<DataOrderDetail>)detailResult.getData();
            List<String> fileList = new ArrayList<String>();
            if(detailList != null && detailList.size() > 0){
                for(DataOrderDetail orderDetail: detailList){
                    fileList.add(orderDetail.getFileName());
                }
            }
            orderMap.put("orderDetail", fileList);
        }


        return ResultMessage.success(orderMap);
    }


    /**
     * 分待审核和已审核状态统计订单数量
     * @param orderParam
     * @return
     */
    @Override
    public ResultMessage queryIfAuditStatistics(OrderParam orderParam) {

        List<Map<String,Object>> list = dataOrderMapper.queryIfAuditStatistics(orderParam);

        return ResultMessage.success(list);
    }


    @Override
    public ResultMessage queryIfAuditByPerson(OrderParam orderParam) {
        List<Map<String,Object>> list = dataOrderMapper.queryIfAuditByPerson(orderParam);
        return ResultMessage.success(list);
    }


    public void pushOrder(String orderCode , String createName){

//        AppPush.pushMsg("订单提醒", createName + "提交的订单（编号："+ orderCode +"）需要您审核");
    }


}
