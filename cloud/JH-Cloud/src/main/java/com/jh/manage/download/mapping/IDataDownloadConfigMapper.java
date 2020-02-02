package com.jh.manage.download.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.manage.download.entity.DataDownloadConfig;
import com.jh.manage.download.model.DownloadConfigParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IDataDownloadConfigMapper extends IBaseMapper<DownloadConfigParam, DataDownloadConfig, Integer> {

    /**
     * 下载配置分页查询
     *
     * @param downloadConfigParam 下载参数配置参数
     * @return
     * @version <1> 2018-02-05 djh： Created.
     */
    List<DownloadConfigParam> queryByPage(DownloadConfigParam downloadConfigParam);

    /**
     * 保存下载配置对象
     *
     * @param dataDownloadConfig 下载配置对象
     * @return
     * @version <1> 2018-02-06 djh： Created.
     */
    int insertSelective(DataDownloadConfig dataDownloadConfig);

    /**
     * 根据主键更新对应的下载配置记录
     *
     * @param dataDownloadConfig 下载配置对象
     * @return
     * @version <1> 2018-02-06 djh： Created.
     */
    int updateByPrimaryKeySelective(DataDownloadConfig dataDownloadConfig);
}