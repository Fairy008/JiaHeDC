package com.jh.collection.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.collection.entity.CollectionTemplate;
import com.jh.collection.entity.query.CollectionTemplateQuery;
import com.jh.collection.entity.vo.CollectionTaskVo;
import com.jh.collection.mapping.CollectionTemplateMapper;
import com.jh.collection.service.ICollectionTemplateService;
import com.jh.collection.utils.BeanToXmlUtils;
import com.jh.util.JsonUtils;
import com.jh.util.MD5Util;
import com.jh.vo.ResultMessage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 采集任务接口
 * @version <1> 2018-12-04: Created.
 */
@Service
public class CollectionTemplateServiceImpl implements ICollectionTemplateService {

    private static final Logger LOG = LoggerFactory.getLogger(CollectionTemplateServiceImpl.class);

    @Autowired
    private CollectionTemplateMapper collectionTemplateMapper;

    @Override
    public PageInfo<CollectionTemplate> findByPage(CollectionTemplateQuery collectionTemplateQuery) {
        LOG.info(">>>query collectionTemplate params:{}", JsonUtils.objectToJson(collectionTemplateQuery));
        PageHelper.startPage(collectionTemplateQuery.getPage(), collectionTemplateQuery.getRows());
        List<CollectionTemplate> list = collectionTemplateMapper.findByPage(collectionTemplateQuery);
        return new PageInfo<CollectionTemplate>(list);
    }

    @Transactional(readOnly = false)
    @Override
    public ResultMessage addTemplate(CollectionTemplate collectionTemplate) {
        LOG.info(">>>add collectionTemplate params:{}", JsonUtils.objectToJson(collectionTemplate));
        if(CollectionUtils.isEmpty(collectionTemplate.getCollectionFieldModelVoList())
                && StringUtils.isEmpty(collectionTemplate.getTemplateContent())){
            return ResultMessage.fail("未填写采集任务字段信息");
        }
        //判断模板名是否重复
        CollectionTemplateQuery collectionTemplateQuery = new CollectionTemplateQuery();
        collectionTemplateQuery.setTemplateName(collectionTemplate.getTemplateName());
        collectionTemplateQuery.setPhone(collectionTemplate.getPhone());
        collectionTemplateQuery.setQueryFlag(1);//判断是否重复查询标志
        List<CollectionTemplate> list = collectionTemplateMapper.findByPage(collectionTemplateQuery);
        if(CollectionUtils.isNotEmpty(list) && list.size()>0){
            return ResultMessage.fail("模板名不能重复");
        }
        collectionTemplate.setDelFlag(1);
        collectionTemplate.setCreateTime(new Date());
        StringBuffer buf = new StringBuffer(collectionTemplate.toString());
        String taskXml = collectionTemplate.getTemplateContent();
        if(StringUtils.isEmpty(taskXml)){
            CollectionTaskVo collectionTaskVo = new CollectionTaskVo(collectionTemplate.getCollectionFieldModelVoList());
            taskXml = BeanToXmlUtils.beanToXml(collectionTaskVo,CollectionTaskVo.class);
        }
        buf.append(taskXml);
        String digestTaskXml = MD5Util.digest(buf.toString());
        CollectionTemplate dbCollectionTask = collectionTemplateMapper.findUniqueByProperty(digestTaskXml);
        if(dbCollectionTask != null){
            return ResultMessage.fail("不能重复创建相同的模板");
        }
        collectionTemplate.setTemplateContent(taskXml);
        collectionTemplate.setEncrypt(digestTaskXml);
        collectionTemplateMapper.insert(collectionTemplate);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage updateTemplate(CollectionTemplate collectionTemplate) {
        LOG.info(">>>update collectionTemplate params:{}", JsonUtils.objectToJson(collectionTemplate));
        if(CollectionUtils.isEmpty(collectionTemplate.getCollectionFieldModelVoList())){
            return ResultMessage.fail("未填写采集任务字段信息");
        }
        CollectionTemplate dbCollectionTemplate = this.getById(collectionTemplate.getId());
        if(dbCollectionTemplate == null){
            return ResultMessage.fail("未找到该记录");
        }
        StringBuffer buf = new StringBuffer(collectionTemplate.toString());
        CollectionTaskVo collectionTaskVo = new CollectionTaskVo(collectionTemplate.getCollectionFieldModelVoList());
        String taskXml = BeanToXmlUtils.beanToXml(collectionTaskVo,CollectionTaskVo.class);
        String digestTaskXml = MD5Util.digest(buf.toString());

        collectionTemplate.setTemplateContent(taskXml);
        collectionTemplate.setEncrypt(digestTaskXml);
        collectionTemplate.setUpdateTime(new Date());
        collectionTemplateMapper.updateByPrimaryKeySelective(collectionTemplate);
        return ResultMessage.success();
    }

    @Override
    public ResultMessage deleteTemplate(CollectionTemplate collectionTemplate) {
        LOG.info(">>>delete collectionTemplate id:{}", collectionTemplate.getId());
        CollectionTemplate dbCollectionTemplate = this.getById(collectionTemplate.getId());
        if(dbCollectionTemplate == null){
            return ResultMessage.fail("未找到该记录");
        }
        collectionTemplate.setDelFlag(0);
        collectionTemplateMapper.updateByPrimaryKeySelective(collectionTemplate);
        return ResultMessage.success();
    }

    @Override
    public CollectionTemplate getById(Integer id) {
        LOG.info(">>>query collectionTemplate id:{}", id);
        CollectionTemplate collectionTemplate = collectionTemplateMapper.selectByPrimaryKey(id);
        if(collectionTemplate !=null && StringUtils.isNotEmpty(collectionTemplate.getTemplateContent())){
            CollectionTaskVo collectionTaskVo = BeanToXmlUtils.xml2Bean(CollectionTaskVo.class,collectionTemplate.getTemplateContent());
            collectionTemplate.setCollectionFieldModelVoList(collectionTaskVo.getCollectionFieldModelVoList());
        }
        return collectionTemplate;
    }
}
