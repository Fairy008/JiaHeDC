package com.jh.briefingNew.service;

import com.jh.briefingNew.entity.BriefingTemplate;

/**
 * description:
 *
 * @version <1> 2018-07-20 wl: Created.
 */
public interface IBriefingTemplateService {
    /**
     * 根据主键查询对应的数据
     * @param templateType 主键
     * @return 返回BriefingTemplate对象
     * @version <1> 2018-07-20 wl created
     */
    BriefingTemplate findBriefingTemplateByType(Integer templateType);
}
