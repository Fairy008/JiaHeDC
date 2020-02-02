package com.jh.manage.download.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.manage.download.entity.DataDownloadConfig;
import com.jh.manage.download.mapping.IDataDownloadConfigMapper;
import com.jh.manage.download.model.DownloadConfigParam;
import com.jh.manage.download.service.IDownloadConfigService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * description: 数据分类实现类
 *  包括卫星、传感器或成像类型、产品级别等信息的管理
 * @version <1> 2018-01-26 lcw： Created.
 * @version <2> 2018-02-05 djh： 添加下载配置业务.
 * @version <3> 2018-02-06 djh： 添加下载配置业务.
 */
@Service
@Transactional
public class DownloadConfigServiceImpl extends BaseServiceImpl<DownloadConfigParam, DataDownloadConfig, Integer> implements IDownloadConfigService {

    @Autowired
    private IDataDownloadConfigMapper downloadConfigMapper;

    @Override
    protected IBaseMapper<DownloadConfigParam, DataDownloadConfig, Integer> getDao() {
        return downloadConfigMapper;
    }

    @Override
    public PageInfo<DownloadConfigParam> findByPage(DownloadConfigParam downloadConfigParam) {
        PageHelper.startPage(downloadConfigParam.getPage(), downloadConfigParam.getRows());
        List<DownloadConfigParam> list = downloadConfigMapper.queryByPage(downloadConfigParam);
        return new PageInfo<DownloadConfigParam>(list);
    }

    @Override
    public ResultMessage saveDataDownloadConfig(DataDownloadConfig dataDownloadConfig) {
        try {
            downloadConfigMapper.insertSelective(dataDownloadConfig);
            return ResultMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail();
        }
    }

    @Override
    public ResultMessage deleteDataDownloadConfigById(DataDownloadConfig dataDownloadConfig) {
        try {
            downloadConfigMapper.updateByPrimaryKeySelective(dataDownloadConfig);
            return ResultMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail();
        }
    }

    @Override
    public ResultMessage queryDataDownloadConfigById(DownloadConfigParam downloadConfigParam) {
        try {
            List<DownloadConfigParam> list = downloadConfigMapper.queryByPage(downloadConfigParam);
            if (list == null || list.size() == 0) {
                return ResultMessage.fail();
            }
            return ResultMessage.success(list.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail();
        }
    }

    @Override
    public ResultMessage findConfigByDataType(DownloadConfigParam downloadConfigParam) {
        List<DownloadConfigParam> list = downloadConfigMapper.queryByPage(downloadConfigParam);
        return ResultMessage.success(list);
    }

}
