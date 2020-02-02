package com.jh.cltApp.mapping;

import com.jh.cltApp.entity.CltTemplate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICltTemplateMapper {

    List<CltTemplate> findCltTemplateList(CltTemplate cltTemplate);

    int deleteByPrimaryKey(Integer templateId);

    int insert(CltTemplate record);

    int insertSelective(CltTemplate record);

    CltTemplate selectByPrimaryKey(Integer templateId);

    int updateByPrimaryKeySelective(CltTemplate record);

    int updateByPrimaryKey(CltTemplate record);

    int validTemplateName (CltTemplate record);

    List<CltTemplate> findCltTemplatePage(CltTemplate cltTemplate);
}