package com.jh.produce.process.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.produce.process.entity.HandleConfig;
import com.jh.produce.process.mapping.IHandleConfigMapper;
import com.jh.produce.process.model.HandleConfigParam;
import com.jh.produce.process.service.IHandleConfigService;
import com.jh.vo.ResultMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * description: 算法配置管理
 *   对数据处理算法的配置信息的管理
 * @version <1> 2018-01-26 lcw： Created.
 */
@Service
@Transactional
public class HandleConfigServiceImpl extends BaseServiceImpl<HandleConfigParam, HandleConfig, Integer> implements IHandleConfigService {
    private Logger logger = Logger.getLogger(HandleConfigServiceImpl.class);

    @Autowired
    private IHandleConfigMapper handleConfigMapper;

    @Override
    protected IBaseMapper<HandleConfigParam, HandleConfig, Integer> getDao() {
        return handleConfigMapper;
    }


    @Override
    public PageInfo<HandleConfigParam> findByPage(HandleConfigParam handleConfigParam) {
        PageHelper.startPage(handleConfigParam.getPage(), handleConfigParam.getRows());
        List<HandleConfigParam> list = handleConfigMapper.queryByPage(handleConfigParam);
        return new PageInfo<HandleConfigParam>(list);
    }

    public ResultMessage queryAll(HandleConfigParam handleConfigParam) {
        try {
            List<HandleConfigParam> list = handleConfigMapper.queryByPage(handleConfigParam);
            return ResultMessage.success(list);
        }catch (Exception e){
            logger.error("IHandleConfigService queryAll method :" + e.getMessage());
            return ResultMessage.fail();
        }
    }

    public List<HandleConfig> findHandleConfigByIdList(List<Integer> handleConfigList) {
        return handleConfigMapper.findHandleConfigByIdList(handleConfigList);
    }

    @Override
    public List<HandleConfig> findHandleConfigListBySatId(Integer satId) {
        return handleConfigMapper.findHandleConfigListBySatId(satId);
    }

    @Override
    public List<HandleConfig> findHandlesBySatId(Integer satId){
        return handleConfigMapper.findHandlesBySatId(satId);
    }


    /**
     * 保存卫星关联数据
     * @param handleConfigs
     * @return
     */
    @Override
    public Boolean saveRelateHandleSat(List<HandleConfigParam>  handleConfigs) {
        try{
            handleConfigMapper.saveRelateHandleSat(handleConfigs);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据handleMetaId查询指定的卫星
     * @param handleMetaId
     * @return
     * @version <1> 2018/4/3 lxy :created
     */
    @Override
    public List<Map<String, Object>> findRelateHandleSatByHandleMetaId(Integer handleMetaId) {
        return handleConfigMapper.findRelateHandleSatByHandleMetaId(handleMetaId);
    }

    /**
     * 按照handleMetaId 删除指定的卫星
     * @param handleMetaId
     * @version <1> 2018/4/3 lxy :created
     */
    public void deleteRelateHandleSatByHandleMetaId(Integer handleMetaId){
        handleConfigMapper.deleteRelateHandleSatByHandleMetaId(handleMetaId);
    }

}
