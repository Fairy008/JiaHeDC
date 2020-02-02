package com.jh.show.agric.service.impl;

import com.jh.enums.DataStatusEnum;
import com.jh.enums.DelStatusEnum;
import com.jh.enums.PublishStatus_Enum;
import com.jh.show.agric.mapping.IAreaMapper;
import com.jh.show.agric.service.IAreaService;
import com.jh.util.AccountTokenUtil;
import com.jh.vo.ResultMessage;
import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description:区域服务
 * 1.根据用户ID查询区域
 * @version <1> 2018-08-10 cxw: Created.
 */
@Service
public class AreaService implements IAreaService {

    @Autowired
    private IAreaMapper areaMapper;

    /**
     * 根据用户ID查询区域
     * @param token 用户标识
     * @return ResultMessage
     * @version <1> 2018-08-10 cxw: Created.
     */
    @Override
    public ResultMessage findAreaByUserId(String token) {
        if(!StringUtils.isNotBlank(token))
        {
            return ResultMessage.fail("请先确认用户是否登录");
        }
        Map<String, Object> map  = AccountTokenUtil.getUserInfoFromRedis(token);
        if(map!=null){
            Integer accountId = (Integer)map.get("accountId");
            List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
            list = areaMapper.findAreaByUserId(accountId);
            return ResultMessage.success(list);
        }
        else{
            return ResultMessage.fail("请先确认用户是否登录");
        }

    }

    /**
     * 查询报告时间轴 仅查询ds_area 表中的有效数据
     * @param
     * regionId 区域ID
     * resolution 精度
     * @return ResultMessage :
     * @version <1> 2018-08-13 cxw:Created.
     */
    @Override
    public ResultMessage findTimeAxisForWx(Long regionId,Integer resolution) {
        Map<String,Object> map = new HashMap();
        map.put("publishStatus", PublishStatus_Enum.PUBLISHED.getCode());//已发布
        map.put("resolution",resolution);//只查询高分卫星数据
        map.put("delFlag", DataStatusEnum.Valid.getCode());
        map.put("dataStatus", DelStatusEnum.Normal.getCode());
        map.put("regionId",regionId);
        List<Map<String, Object>> list =  new ArrayList<Map<String,Object>>();
        list =  areaMapper.findTimeAxisForWx(map);
        //数据转化
        List<Map<String,Object>> rlist=dataConver(list);
        return ResultMessage.success(rlist);
    }
    private List<Map<String,Object>> dataConver(List<Map<String,Object>> list){
        List<Map<String,Object>>rlist=new ArrayList<Map<String,Object>>();
        for(Map<String,Object> map:list){
            List<Map<String,Object>>corplist=new ArrayList<Map<String,Object>>();
            String cropIds=(String)map.get("cropid");
            String cropNames=(String)map.get("cropname");
            if(StringUtils.isNotBlank(cropIds)){
                String [] cropIdArr=cropIds.split(",");
                String [] cropNameArr=cropNames.split(",",cropIdArr.length);
                for(int i=0;i<cropIdArr.length;i++){
                    Map<String,Object> r=new HashMap<String,Object>();
                    r.put("cropId",cropIdArr[i]);
                    r.put("cropName",cropNameArr[i]);
                    corplist.add(r);
                }
                map.put("corps",corplist);
                map.remove("cropid");
                map.remove("cropname");
                rlist.add(map);
            }
        }
        return rlist;
    }
}
