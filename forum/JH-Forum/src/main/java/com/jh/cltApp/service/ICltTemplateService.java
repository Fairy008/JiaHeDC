package com.jh.cltApp.service;

import com.github.pagehelper.PageInfo;
import com.jh.cltApp.entity.CltTemplate;
import com.jh.vo.ResultMessage;

/**
 * 模板接口
 * @version <1> 2019/4/8 19:54 zhangshen:Created.
 */
public interface ICltTemplateService {
    
    /**
     * 查询我的所有模板(带分页)
     * @param
     * @return 
     * @version <1> 2019/4/8 18:51 zhangshen:Created.
     */
    PageInfo<CltTemplate> findCltTemplatePageInfo(CltTemplate cltTemplate);

    /**
     * 查询模板后台(带分页)
     * @param
     * @return
     * @version <1> 2019/4/8 18:51 zhangshen:Created.
     */
    PageInfo<CltTemplate> findByPage(CltTemplate cltTemplate);

    /**
     * 查询我的所有模板(不带分页)
     * @param
     * @return 
     * @version <1> 2019/4/8 18:47 zhangshen:Created.
     */
    ResultMessage findCltTemplateList(CltTemplate cltTemplate);

    /**
     * 新增模板
     * @param 
     * @return 
     * @version <1> 2019/4/8 18:57 zhangshen:Created.
     */
    ResultMessage createCltTemplate(CltTemplate cltTemplate);

    /**
     * 没有使用的模板支持删除
     * @param 
     * @return 
     * @version <1> 2019/4/8 18:58 zhangshen:Created.
     */
    ResultMessage deleteCltTemplate(Integer templateId);

    /**
     * 验证模板名称是否重复
     * @param
     * @return
     * @version <1> 2019/4/8 18:58 lijie:Created.
     */
    ResultMessage validTemplateName(CltTemplate cltTemplate);

    /**
     * 编辑模板
     * @param
     * @return
     * @version <1> 2019/4/8 18:57 zhangshen:Created.
     */
    ResultMessage updateCltTemplate(CltTemplate cltTemplate);

    /**
     * 根据ID获取模板信息
     * @param
     * @return
     * @version <1> 2019/4/8 18:57 zhangshen:Created.
     */
    ResultMessage getById(Integer templateId);

}
