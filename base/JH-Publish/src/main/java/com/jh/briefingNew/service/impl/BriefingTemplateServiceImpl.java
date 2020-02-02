package com.jh.briefingNew.service.impl;

import com.jh.briefingNew.entity.BriefingTemplate;
import com.jh.briefingNew.mapping.IBriefingTemplateMapper;
import com.jh.briefingNew.service.IBriefingTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description:简报模板
 *
 * @version <1> 2018-07-20 wl: Created.
 */
@Service
public class BriefingTemplateServiceImpl implements IBriefingTemplateService {

    @Autowired
    private IBriefingTemplateMapper briefingTemplateMapper;

    @Override
    public BriefingTemplate findBriefingTemplateByType(Integer templateType) {
       // return briefingTemplateMapper.findBriefingTemplateByType(templateType);
        return null;
    }
}
