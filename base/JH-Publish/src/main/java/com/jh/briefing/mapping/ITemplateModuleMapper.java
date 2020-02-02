package com.jh.briefing.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.briefing.entity.TemplateModule;
import com.jh.briefing.model.TemplateModuleParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模板模块Mapper接口
 */
@Mapper
public interface ITemplateModuleMapper extends IBaseMapper<TemplateModuleParam,TemplateModule,Integer> {
    /**
     * 根据templateId主键删除模块数据
     * @param templateId 主键
     * @return 返回删除的记录数
     * @version <1> 2018-04-22 lxy created
     */
    int deleteByTemplateId(@Param("templateId") Integer templateId);

    /**
     * 新增模块数据
     * @param record TemplateModule实体类
     * @return 返回保存的记录数
     * @version <1> 2018-04-22 lxy created
     */
    int saveTemplateModule(TemplateModule record);

    /**
     * 根据主键查询对应的数据
     * @param templateId 主键
     * @return 返回TemplateModule对象
     * @version <1> 2018-04-22 lxy created
     */
    TemplateModule findTemplateModuleByTemplateId(@Param("templateId") Integer templateId);

    /**
     * 修改TemplateModule数据
     * @param record TemplateModule实体类
     * @return 返回修改记录数
     * @version <1> 2018-04-22 lxy created
     */
    int updateTemplateModule(TemplateModule record);

    /**
     * 分页查询
     * @param templateModuleParam 查询参数
     * @return 返回List<TemplateModuleParam>
     * @version <1> 2018-04-22 lxy created
     */
    List<TemplateModuleParam> queryTemplateModuleByPage(TemplateModuleParam templateModuleParam);

    /**
     * 查询所有的模板数据
     * @return List<TemplateModuleParam>
     */
    List<TemplateModuleParam> queryAllTemplateModules();

    /**
     * 根据查询条件去查询对应的模板
     * @param templateModuleParam 查询参数
     * @return 返回对应的模板
     */
    List<TemplateModuleParam> queryTemplateModuleByParam(TemplateModuleParam templateModuleParam);

}
