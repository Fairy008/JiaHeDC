package com.jh.briefing.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseService;
import com.jh.briefing.entity.TemplateModule;
import com.jh.briefing.model.TemplateModuleParam;
import com.jh.vo.ResultMessage;

/**
 *  模板模块服务接口类
 * Created by lxy on 2018/4/13.
 */
public interface ITemplateModuleService extends IBaseService<TemplateModuleParam,TemplateModule,Integer> {
    /**
     * 模板模块信息分页查询
     * @param templateModuleParam 模板模块参数
     * @return 返回模板模块
     * @version <1> 2018-04-22 lxy： Created.
     */
    PageInfo<TemplateModuleParam> queryTemplateModuleByPage(TemplateModuleParam templateModuleParam);


    /**
     * 根据templateId主键删除模块数据
     * @param templateId 主键
     * @return 返回删除的记录数
     * @version <1> 2018-04-22 lxy created
     */
    ResultMessage deleteByTemplateId(Integer templateId);

    /**
     * 新增模块数据
     * @param templateModule TemplateModule实体类
     * @return 返回保存的记录数
     * @version <1> 2018-04-22 lxy created
     */
    ResultMessage saveTemplateModule(TemplateModule templateModule);

    /**
     * 根据主键查询对应的数据
     * @param templateId 主键
     * @return 返回TemplateModule对象
     * @version <1> 2018-04-22 lxy created
     */
    ResultMessage findTemplateModuleByTemplateId(Integer templateId);

    /**
     * 修改TemplateModule数据
     * @param templateModule TemplateModule实体类
     * @return 返回修改记录数
     * @version <1> 2018-04-22 lxy created
     */
    ResultMessage updateTemplateModule(TemplateModule templateModule);


    /**
     * 查询所有的模板数据
     * @return ResultMessage
     * @version <1> 2018-04-22 lxy created
     */
    ResultMessage queryAllTemplateModules();

    /**
     * 根据查询条件去查询对应的模板
     * @param templateModuleParam 查询参数
     * @return 返回对应的模板
     * @version <1> 2018-06-13 lxy created
     */
    ResultMessage queryTemplateModuleByParam(TemplateModuleParam templateModuleParam);

}
