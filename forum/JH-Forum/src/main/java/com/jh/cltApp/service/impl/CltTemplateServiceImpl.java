package com.jh.cltApp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.cltApp.entity.CltTemplate;
import com.jh.cltApp.mapping.ICltTemplateMapper;
import com.jh.cltApp.service.ICltTemplateService;
import com.jh.util.cache.IdTransformUtils;
import com.jh.vo.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务模板实现类
 * @version <1> 2019/4/9 10:02 zhangshen:Created.
 */
@Service
@Transactional
public class CltTemplateServiceImpl implements ICltTemplateService {

    @Autowired
    private ICltTemplateMapper cltTemplateMapper;

    private static Logger log = LoggerFactory.getLogger(CltTemplateServiceImpl.class);

    @Override
    public PageInfo<CltTemplate> findCltTemplatePageInfo(CltTemplate cltTemplate) {
        PageHelper.startPage(cltTemplate.getPage(), cltTemplate.getRows());
        List<CltTemplate> cltTemplateList = cltTemplateMapper.findCltTemplateList(cltTemplate);
        IdTransformUtils.idTransForList(cltTemplateList);
        return new PageInfo<CltTemplate>(cltTemplateList);
    }

    @Override
    public PageInfo<CltTemplate> findByPage(CltTemplate cltTemplate) {
        PageHelper.startPage(cltTemplate.getPage(), cltTemplate.getRows());
        List<CltTemplate> cltTemplateList = cltTemplateMapper.findCltTemplatePage(cltTemplate);
        IdTransformUtils.idTransForList(cltTemplateList);
        return new PageInfo<CltTemplate>(cltTemplateList);
    }

    @Override
    public ResultMessage findCltTemplateList(CltTemplate cltTemplate) {
        List<CltTemplate> cltTemplateList = cltTemplateMapper.findCltTemplateList(cltTemplate);
        IdTransformUtils.idTransForList(cltTemplateList);
        return ResultMessage.success(cltTemplateList);
    }

    @Override
    public ResultMessage createCltTemplate(CltTemplate cltTemplate) {
        cltTemplateMapper.insertSelective(cltTemplate);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage updateCltTemplate(CltTemplate cltTemplate) {
        cltTemplateMapper.updateByPrimaryKeySelective(cltTemplate);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage getById(Integer templateId) {
        CltTemplate template=cltTemplateMapper.selectByPrimaryKey(templateId);
        IdTransformUtils.idTransForObj(template);
        return ResultMessage.success(template);
    }

    @Override
    public ResultMessage deleteCltTemplate(Integer templateId) {
        cltTemplateMapper.deleteByPrimaryKey(templateId);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage validTemplateName(CltTemplate cltTemplate) {
        int flag = cltTemplateMapper.validTemplateName(cltTemplate);
        return ResultMessage.success(flag);
    }

}
