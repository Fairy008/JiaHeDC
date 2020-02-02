package com.jh.collection.service.impl;

import com.jh.collection.entity.vo.CollectionAnalysisVo;
import com.jh.collection.entity.vo.CollectionFieldModelVo;
import com.jh.collection.entity.vo.CollectionStatisticsVo;
import com.jh.collection.mapping.TaskItemFeildMapper;
import com.jh.collection.service.IReportAnalysisService;
import com.jh.vo.ResultMessage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 采集分析service
 * @version <1> 2019-01-25: Created.
 */
@Service
public class ReportAnalysisServiceImpl implements IReportAnalysisService {

    @Resource
    private TaskItemFeildMapper taskItemFeildMapper;

    @Override
    public ResultMessage queryDistributionHistory(Integer taskItemId) {
        if(taskItemId == null){
            return ResultMessage.fail();
        }
        List<CollectionAnalysisVo> list = taskItemFeildMapper.queryDistributionHistory(taskItemId);
        if(CollectionUtils.isEmpty(list)){
            return ResultMessage.fail();
        }
        CollectionStatisticsVo collectionStatisticsVo = new CollectionStatisticsVo();
        List<String> collectionDateList = new ArrayList<String>();//年份
        List<Double> areaList = new ArrayList<Double>();//面积
        List<CollectionFieldModelVo> currentYearCollectionData = new ArrayList<CollectionFieldModelVo>();
        Double maxArea = 0d;//最大面积
        //增加测试数据
        /*areaList.add(25.23);
        areaList.add(45.23);
        collectionDateList.add("2016");
        collectionDateList.add("2017");*/
        for(CollectionAnalysisVo analysisVo : list){
            List<CollectionFieldModelVo> feildList = taskItemFeildMapper.findByItemId(analysisVo.getTaskItemId());
            currentYearCollectionData = feildList;
            collectionDateList.add(analysisVo.getYearStr());
            for(CollectionFieldModelVo modelVo : feildList){
                if(StringUtils.equals(modelVo.getFieldNameEn(),"area")){
                    Double area = new BigDecimal(modelVo.getCollectionValue()).setScale(2).doubleValue();
                    areaList.add(area);
                    if(area > maxArea){
                        maxArea = area;
                    }
                    continue;
                }
            }
        }
        collectionStatisticsVo.setAreaList(areaList);
        collectionStatisticsVo.setCollectionDateList(collectionDateList);
        collectionStatisticsVo.setCurrentYearCollectionData(currentYearCollectionData);
        collectionStatisticsVo.setMaxArea(maxArea);
        return ResultMessage.success(collectionStatisticsVo);
    }
}
