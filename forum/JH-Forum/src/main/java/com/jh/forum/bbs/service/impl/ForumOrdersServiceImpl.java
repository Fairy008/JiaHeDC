package com.jh.forum.bbs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.forum.bbs.Enum.OrderStatusEnum;
import com.jh.forum.bbs.entity.ForumOrderDetail;
import com.jh.forum.bbs.entity.ForumOrders;
import com.jh.forum.bbs.mapping.IForumDownloadDataMapper;
import com.jh.forum.bbs.mapping.IForumOrderDetailMapper;
import com.jh.forum.bbs.mapping.IForumOrdersMapper;
import com.jh.forum.bbs.service.IForumDownloadDataService;
import com.jh.forum.bbs.service.IForumOrdersService;
import com.jh.forum.bbs.vo.ArticleVO;
import com.jh.forum.bbs.vo.DownloadDataVo;
import com.jh.forum.bbs.vo.OrdersVo;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class ForumOrdersServiceImpl implements IForumOrdersService {


    @Autowired
    private IForumDownloadDataService forumDownloadDataService;

    @Autowired
    private IForumOrdersMapper forumOrdersMapper;

    @Autowired
    private IForumOrderDetailMapper forumOrderDetailMapper;

    @Autowired
    private IForumDownloadDataMapper downloadDataMapper;

    @Override
    public ResultMessage createOrder(OrdersVo ordersVo) {
        List<Integer> idList = ordersVo.getIdList();
        DownloadDataVo downloadDataVo = new DownloadDataVo();
        downloadDataVo.setIdList(idList);
       //先获取订单的idlist   根据list  查询出每个数据的
        List<DownloadDataVo> list = downloadDataMapper.confirmationOfOrder(downloadDataVo.getIdList());
        BigDecimal totalPrice = new BigDecimal(0);
        int goodsNum = 0;
        String orderNo = randomNum();
        if(list.size()>0){
            goodsNum = list.size();
            for(int i = 0 ;i < goodsNum ;i++){
                //classify_name region_name accuracy_name unit_price num  price
                DownloadDataVo downloadDataVoDetail = list.get(i);
                ForumOrderDetail orderDetail = new ForumOrderDetail();
                orderDetail.setAccuracyName(downloadDataVoDetail.getAccuracyName());
                orderDetail.setClassifyName(downloadDataVoDetail.getClassifyName());
                orderDetail.setRegionName(downloadDataVoDetail.getRegionName());
                orderDetail.setUnitPrice(downloadDataVoDetail.getPrice());
                orderDetail.setNum(1);
                orderDetail.setPrice(downloadDataVoDetail.getPrice());
                orderDetail.setDataId(downloadDataVoDetail.getDataId());
                orderDetail.setCreator(ordersVo.getCreator());
                orderDetail.setOrderNo(orderNo);
                orderDetail.setCropName(downloadDataVoDetail.getCropName());
                orderDetail.setDataTime(downloadDataVoDetail.getDataTime());
                orderDetail.setDataName(downloadDataVoDetail.getDataTitle());
                orderDetail.setDataPath(downloadDataVoDetail.getDataPath());
                orderDetail.setDataPackagePath(downloadDataVoDetail.getDataPackagePath());
                forumOrderDetailMapper.insertSelective(orderDetail);
                totalPrice = totalPrice.add(orderDetail.getPrice());
            }
        }

        //order_no（随机生成12位）  accuracy_range 精度 data_classify 类型
        ordersVo.setGoodsNum(goodsNum);
        ordersVo.setTotalPrice(totalPrice);
        ordersVo.setOrderNo(orderNo);
        ordersVo.setOrderStatus(OrderStatusEnum.Order_status_noPay.getId());
        ordersVo.setCreator(ordersVo.getCreator());
        ordersVo.setSubject("数据订单"+orderNo);
        //随机生成订单标题
        int id = forumOrdersMapper.insertSelective(ordersVo);
        //查询 订单标题
        ForumOrders forumOrders = forumOrdersMapper.selectByPrimaryKey(ordersVo.getId());
        return ResultMessage.success(forumOrders);
    }

    @Override
    public ResultMessage findByType(OrdersVo ordersVo) {
        List<OrdersVo> payList = new ArrayList<>();
        List<OrdersVo> nopayList = new ArrayList<>();
        // 首先查询用户的所有订单   根据订单id查询所有商品详情
        List<OrdersVo> list = forumOrdersMapper.queryByType(ordersVo);
        //根据order_no查询所有的商品详情
        for(int i=0 ;i<list.size();i++){
            OrdersVo ordersVoDetail = list.get(i);
            String orderNo = ordersVoDetail.getOrderNo();
            if(ordersVoDetail.getPayStatus().equals(OrderStatusEnum.Order_status_pay.getId())){//查询已支付的订单
                List<ForumOrderDetail> listDetail = forumOrderDetailMapper.findDetailsByOrderNo(orderNo);
                listDetail = setImageUrlForNewPath(listDetail);
                if(listDetail.size()>0){
                    ordersVoDetail.setDetailsList(listDetail);
                    payList.add(ordersVoDetail);
                }
            }else{//查询待支付的订单
                List<ForumOrderDetail> listDetail = forumOrderDetailMapper.findDetailsByOrderNo(orderNo);
                listDetail = setImageUrlForNewPath(listDetail);
                if(listDetail.size()>0){
                    ordersVoDetail.setDetailsList(listDetail);
                    nopayList.add(ordersVoDetail);
                }
            }

        }
        Map<String,Object> map = new HashMap<>();
        map.put("payList",payList);
        map.put("nopayList",nopayList);
        return ResultMessage.success(map);
    }

    @Override
    public ResultMessage deleteOrder(String tradeNo) {
        forumOrdersMapper.deleteByOrderNo(tradeNo);
        return  ResultMessage.success();
    }

    @Override
    public OrdersVo findOrderById(String tradeNo) {
        return forumOrdersMapper.findOrderById(tradeNo);
    }

    /*
     * 功能描述:查询所有订单
     * @Param:
     * @Return: [ordersVo]
     * @version<1>  2019/10/8  wangli :Created
     */
    @Override
    public PageInfo<OrdersVo> findAllOrder(OrdersVo ordersVo) {
        PageHelper.startPage(ordersVo.getPage(), ordersVo.getRows());
        List<OrdersVo> list = forumOrdersMapper.findAllOrder(ordersVo);
        return new PageInfo<OrdersVo>(list);
    }



    /*
     * 功能描述: 生成随机数
     * @Param:
     * @Return: []
     * @version<1>  2019/9/1  wangli :Created
     */
    public String randomNum (){
        String numStr = "" ;
        String trandStr = String.valueOf((Math.random() * 9 + 1) * 10000);
        String dataStr = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
        numStr = trandStr.toString().substring(0, 5);
        numStr = numStr + dataStr ;
        return numStr;
    }

    public List<ForumOrderDetail> setImageUrlForNewPath(List<ForumOrderDetail> list){
        if(CollectionUtils.isNotEmpty(list)){
            for(ForumOrderDetail orderDetail : list){
                if(orderDetail.getDataPath() != null){
                    orderDetail.setDataPath(CephUtils.getShowPath1(orderDetail.getDataPath()));
                }
                if(orderDetail.getDataPackagePath()!=null){
                    orderDetail.setDataPackagePath(CephUtils.getShowPath1(orderDetail.getDataPackagePath()));
                }
            }
        }
        return list;
    }
}
