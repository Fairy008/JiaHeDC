package com.jh.forum.bbs.service.impl;

import com.jh.biz.feign.*;
import com.jh.enums.DsEnum;
import com.jh.forum.bbs.service.IDsService;
import com.jh.forum.bbs.vo.DsVo;
import com.jh.util.cache.IdTransformUtils;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 数据集查询
 * @version <1> 2019-03-13 cxw:Created.
 */
@Service
@Transactional
public class DsServiceImpl implements IDsService {


    @Autowired
    private IDistributionService distributionService;

    @Autowired
    private IYieldService yieldService;

    @Autowired
    private IGrowthService growthService;

    @Autowired
    private IDsTrmmService dsTrmmService;

    @Autowired
    private IDsTService dsTService;

    @Autowired
    private  IDsTempService dsTempService;

    /**
     * 根据区域查询所有数据集
     * regionId 区域ID
     * <1> 2019-03-13 cxw: Created.
     */
    public ResultMessage findDsListByRegionId(ResultMessage  cropResult, ResultMessage  dsResolutionResult,Long regionId) {
        ResultMessage resultMessage =  ResultMessage.fail();
        List<Map<String,Object>> dsrList = (List<Map<String,Object>>) dsResolutionResult.getData();
        List<Map<String,Object>> cropList = (List<Map<String,Object>>) cropResult.getData();
        List<DsVo> dsVoList = new ArrayList<DsVo>();
        for(int i = 0;i<dsrList.size();i++)
        {
            Integer dataSetId = Integer.valueOf(dsrList.get(i).get("dataSetId").toString());
            Integer resolutionId = Integer.valueOf(dsrList.get(i).get("resolutionId").toString());
            if(DsEnum.distribution.getId().equals(dataSetId)|| DsEnum.yield.getId().equals(dataSetId)|| DsEnum.growth.getId().equals(dataSetId))
            {
                for(int j = 0;j<cropList.size();j++)
                {
                    DsVo dsVo = new DsVo();
                    Integer cropId = Integer.valueOf(cropList.get(j).get("dictId").toString());
                    dsVo.setDataSetId(dataSetId);
                    dsVo.setResolutionId(resolutionId);
                    dsVo.setRegionId(regionId);
                    dsVo.setCropId(cropId);
                    dsVoList.add(dsVo);
                }
            }
            else{
                DsVo dsVo = new DsVo();
                dsVo.setDataSetId(dataSetId);
                dsVo.setResolutionId(resolutionId);
                dsVo.setRegionId(regionId);
                dsVoList.add(dsVo);
            }
        }
        IdTransformUtils.idTransForList(dsVoList);
        resultMessage =  ResultMessage.success();
        resultMessage.setData(dsVoList);
        return resultMessage;
    }

    /**
     * 根据区域查询所有数据集
     * regionId 区域ID
     * <1> 2019-03-13 cxw: Created.
     */
    public ResultMessage setDsList(ResultMessage  dsTributes,ResultMessage  dsYields,ResultMessage  dsGrowth,ResultMessage  dsTrmms,ResultMessage  dsTs,ResultMessage  dsTemps) {
        ResultMessage resultMessage =  ResultMessage.fail();
        List<Map<String,Object>> dsTributesDatas = (List<Map<String,Object>>) dsTributes.getData();
        List<Map<String,Object>> dsYieldDatas = (List<Map<String,Object>>) dsYields.getData();
        List<Map<String,Object>> dsGrowthDatas = (List<Map<String,Object>>) dsGrowth.getData();
        List<Map<String,Object>> dsTrmmDatas = (List<Map<String,Object>>) dsTrmms.getData();
        List<Map<String,Object>> dsTDatas = (List<Map<String,Object>>) dsTs.getData();
        List<Map<String,Object>> dsTempDatas = (List<Map<String,Object>>) dsTemps.getData();
        List<DsVo> dsVoList = new ArrayList<DsVo>();
        for(int i = 0;i<dsTributesDatas.size();i++){
            Integer resolutionId = Integer.valueOf(dsTributesDatas.get(i).get("resolution").toString());
            Integer cropId = Integer.valueOf(dsTributesDatas.get(i).get("cropId").toString());
            Long regionId = Long.valueOf(dsTributesDatas.get(i).get("regionId").toString());
            DsVo dsVo = new DsVo();
            dsVo.setDataSetId(DsEnum.distribution.getId());
            dsVo.setResolutionId(resolutionId);
            dsVo.setRegionId(regionId);
            dsVo.setCropId(cropId);
            dsVoList.add(dsVo);
        }
        for(int i = 0;i<dsYieldDatas.size();i++){
            Integer resolutionId = Integer.valueOf(dsYieldDatas.get(i).get("resolution").toString());
            Integer cropId = Integer.valueOf(dsYieldDatas.get(i).get("cropId").toString());
            Long regionId = Long.valueOf(dsYieldDatas.get(i).get("regionId").toString());
            DsVo dsVo = new DsVo();
            dsVo.setDataSetId(DsEnum.yield.getId());
            dsVo.setResolutionId(resolutionId);
            dsVo.setRegionId(regionId);
            dsVo.setCropId(cropId);
            dsVoList.add(dsVo);
        }
        for(int i = 0;i<dsGrowthDatas.size();i++){
            Integer resolutionId = Integer.valueOf(dsGrowthDatas.get(i).get("resolution").toString());
            Integer cropId = Integer.valueOf(dsGrowthDatas.get(i).get("cropId").toString());
            Long regionId = Long.valueOf(dsGrowthDatas.get(i).get("regionId").toString());
            DsVo dsVo = new DsVo();
            dsVo.setDataSetId(DsEnum.growth.getId());
            dsVo.setResolutionId(resolutionId);
            dsVo.setRegionId(regionId);
            dsVo.setCropId(cropId);
            dsVoList.add(dsVo);
        }
        for(int i = 0;i<dsTrmmDatas.size();i++){
            Integer resolutionId = Integer.valueOf(dsTrmmDatas.get(i).get("resolution").toString());
            Long regionId = Long.valueOf(dsTrmmDatas.get(i).get("regionId").toString());
            String dataMonth = dsTrmmDatas.get(i).get("dataMonth").toString();
            DsVo dsVo = new DsVo();
            dsVo.setDataSetId(DsEnum.trmm.getId());
            dsVo.setResolutionId(resolutionId);
            dsVo.setRegionId(regionId);
            dsVo.setDataMonth(dataMonth);

            dsVoList.add(dsVo);
        }
        for(int i = 0;i<dsTDatas.size();i++){
            Integer resolutionId = Integer.valueOf(dsTDatas.get(i).get("resolution").toString());
            Long regionId = Long.valueOf(dsTDatas.get(i).get("regionId").toString());
            String dataMonth = dsTDatas.get(i).get("dataMonth").toString();
            DsVo dsVo = new DsVo();
            dsVo.setDataSetId(DsEnum.t.getId());
            dsVo.setResolutionId(resolutionId);
            dsVo.setRegionId(regionId);
            dsVo.setDataMonth(dataMonth);
            dsVoList.add(dsVo);
        }
        for(int i = 0;i<dsTempDatas.size();i++){
            Long regionId = Long.valueOf(dsTempDatas.get(i).get("regionId").toString());
            String dataMonth = dsTempDatas.get(i).get("dataMonth").toString();
            DsVo dsVo = new DsVo();
            dsVo.setDataSetId(DsEnum.temperature.getId());
            dsVo.setRegionId(regionId);
            dsVo.setDataMonth(dataMonth);
            dsVoList.add(dsVo);
        }

        IdTransformUtils.idTransForList(dsVoList);
        resultMessage =  ResultMessage.success();
        resultMessage.setData(dsVoList);
        return resultMessage;
    }

    @Override
    public ResultMessage findDsList(DsVo dsVo) {
        ResultMessage  dsTributes = distributionService.queryAllDistribution(dsVo.getRegionId(),dsVo.getStartDate(),dsVo.getEndDate());
        ResultMessage  dsYields = yieldService.queryAllYield(dsVo.getRegionId(),dsVo.getStartDate(),dsVo.getEndDate());
        ResultMessage  dsGrowth = growthService.queryAllGrowth(dsVo.getRegionId(),dsVo.getStartDate(),dsVo.getEndDate());
        ResultMessage  dsTrmms = dsTrmmService.queryAllTrmm(dsVo.getRegionId(),dsVo.getStartDate(),dsVo.getEndDate());
        ResultMessage  dsTs = dsTService.queryAllT(dsVo.getRegionId(),dsVo.getStartDate(),dsVo.getEndDate());
        ResultMessage  dsTemps = dsTempService.queryAllTemp(dsVo.getRegionId(),dsVo.getStartDate(),dsVo.getEndDate());

        ResultMessage result = setDsList(dsTributes,dsYields,dsGrowth,dsTrmms,dsTs,dsTemps);
        return result;
    }
}
