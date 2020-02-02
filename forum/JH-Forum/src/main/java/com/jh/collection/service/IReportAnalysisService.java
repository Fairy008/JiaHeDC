package com.jh.collection.service;

import com.github.pagehelper.PageInfo;
import com.jh.collection.entity.TaskItemFeild;
import com.jh.collection.entity.vo.CollectionFeildValue;
import com.jh.vo.ResultMessage;

/**
 * 采集详情分析
 * @version <1> 2019-01-25 xy: Created.
 */
public interface IReportAnalysisService {

    public ResultMessage queryDistributionHistory(Integer taskItemId);

//    public ResultMessage queryDistributionHistory()

}
