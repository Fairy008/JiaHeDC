package com.jh.briefing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.briefing.entity.TemplateModule;
import com.jh.briefing.mapping.ITemplateModuleMapper;
import com.jh.briefing.model.TemplateModuleParam;
import com.jh.briefing.service.ITemplateModuleService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  模板模块服务接口类
 * Created by lxy on 2018/4/13.
 */
@Service
@Transactional
public class TemplateModuleService extends BaseServiceImpl<TemplateModuleParam,TemplateModule,Integer>
        implements ITemplateModuleService {

    @Autowired
    private ITemplateModuleMapper templateModuleMapper;

    @Override
    protected IBaseMapper<TemplateModuleParam, TemplateModule, Integer> getDao() {
        return templateModuleMapper;
    }

    /**
     * 模板模块信息分页查询
     * @param templateModuleParam 模板模块参数
     * @return 返回模板模块
     * @version <1> 2018-04-22 lxy： Created.
     */
    public PageInfo<TemplateModuleParam> queryTemplateModuleByPage(TemplateModuleParam templateModuleParam){
        PageHelper.startPage(templateModuleParam.getPage(), templateModuleParam.getRows());
        List<TemplateModuleParam> list = templateModuleMapper.queryTemplateModuleByPage(templateModuleParam);
        return new PageInfo<TemplateModuleParam>(list);
    }


    /**
     * 根据templateId主键删除模块数据
     * @param templateId 主键
     * @return 返回删除的记录数
     * @version <1> 2018-04-22 lxy created
     */
    public ResultMessage deleteByTemplateId(Integer templateId){
        int n = templateModuleMapper.deleteByTemplateId(templateId);
        if(n>0){
            return ResultMessage.success();
        }else{
            return ResultMessage.fail();
        }
    }

    /**
     * 新增模块数据
     * @param templateModule TemplateModule实体类
     * @return 返回保存的记录数
     * @version <1> 2018-04-22 lxy created
     */
    public ResultMessage saveTemplateModule(TemplateModule templateModule){
        int n = templateModuleMapper.saveTemplateModule(templateModule);
        if(n>0){
            return ResultMessage.success();
        }else{
            return ResultMessage.fail();
        }
    }

    /**
     * 根据主键查询对应的数据
     * @param templateId 主键
     * @return 返回TemplateModule对象
     * @version <1> 2018-04-22 lxy created
     */
    public ResultMessage findTemplateModuleByTemplateId(Integer templateId){
        TemplateModule templateModule = templateModuleMapper.findTemplateModuleByTemplateId(templateId);
        return ResultMessage.success(templateModule);
    }

    /**
     * 修改TemplateModule数据
     * @param templateModule TemplateModule实体类
     * @return 返回修改记录数
     * @version <1> 2018-04-22 lxy created
     */
    public ResultMessage updateTemplateModule(TemplateModule templateModule){
        int n = templateModuleMapper.updateTemplateModule(templateModule);
        if(n>0){
            return ResultMessage.success();
        }else{
            return ResultMessage.fail();
        }
    }


    /**
     * 查询所有的模板数据
     * @return ResultMessage
     * @version <1> 2018-04-22 lxy created
     */
    public ResultMessage queryAllTemplateModules(){
        List<TemplateModuleParam> templateModules = templateModuleMapper.queryAllTemplateModules();
        if(templateModules != null && templateModules.size()>0){
            return ResultMessage.success(templateModules);
        }else{
            return ResultMessage.fail();
        }
    }

    /**
     * 根据查询条件去查询对应的模板
     * @param templateModuleParam 查询参数
     * @return 返回对应的模板
     * @version <1> 2018-06-13 lxy created
     */
    @Override
    public ResultMessage queryTemplateModuleByParam(TemplateModuleParam templateModuleParam) {
        List<TemplateModuleParam> templateModules = templateModuleMapper.queryTemplateModuleByParam(templateModuleParam);
        if(templateModules != null && templateModules.size()>0){
            return ResultMessage.success(templateModules);
        }else{
            return ResultMessage.fail();
        }
    }
}
