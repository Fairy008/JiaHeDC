package com.jh.show.report.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.show.report.enums.ReportEnum;
import com.jh.show.report.mapping.IBriefReportMapper;
import com.jh.show.report.service.IBriefReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @version <1> 2018-06-24 wl: Created.
 */
@Service
public class BriefReportServiceImpl implements IBriefReportService {

    @Autowired
    private IBriefReportMapper briefReportMapper;

    @Override
    public PageInfo<Map<String, Object>> findBriefReportByPage(Map<String, Object> map) {
        map.put("audisState", ReportEnum.PUBLISHED.getValue());
        PageHelper.startPage(Integer.parseInt(map.get("page").toString()),Integer.parseInt( map.get("rows").toString()));
        List<Map<String, Object>> list = briefReportMapper.findBriefReportByPage(map);
        return new PageInfo<Map<String, Object>>(list);
    }

    @Override
    public Long getNewBriefReport(Long regionId) {
        Map<String, Object> map=new HashMap<String,Object>();
        map.put("audisState", ReportEnum.PUBLISHED.getValue());
        map.put("regionId",regionId);
        List<Map<String, Object>> list = briefReportMapper.findBriefReportByPage(map);
        Integer reportId=null;
        if(list!=null&&list.size()>0){
            reportId=(Integer)list.get(0).get("report_id");
        }
        return new Long(reportId);
    }


    @Override
    public PageInfo<Map<String, Object>> findBriefPage(Map<String, Object> map) {
        map.put("audisState", ReportEnum.PUBLISHED.getValue());
        List<Map<String, Object>> list = briefReportMapper.findBriefPage(map);
        return new PageInfo<Map<String,Object>>(list);
    }
}
