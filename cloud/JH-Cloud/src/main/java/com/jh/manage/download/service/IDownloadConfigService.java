package com.jh.manage.download.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseService;
import com.jh.manage.download.entity.DataDownloadConfig;
import com.jh.manage.download.model.DownloadConfigParam;
import com.jh.vo.ResultMessage;

/**
 * description: 数据分类接口
 *  包括卫星、传感器或成像类型、产品级别等信息的管理
 * @version <1> 2018-01-26 lcw： Created.
 * @version <2> 2018-02-05 djh： 添加下载配置业务.
 */
public interface IDownloadConfigService extends IBaseService<DownloadConfigParam, DataDownloadConfig, Integer> {

    /**
     *  下载配置分页查询
     *
     *  @param downloadConfigParam 下载参数配置参数
     *  @return
     *  @version <1> 2018-02-05 djh： Created.
     */
    PageInfo<DownloadConfigParam> findByPage(DownloadConfigParam downloadConfigParam);

    /**
     *  保存下载配置对象
     *
     *  @param dataDownloadConfig 下载配置对象
     *  @return
     *  @version <1> 2018-02-06 djh： Created.
     */
    ResultMessage saveDataDownloadConfig(DataDownloadConfig dataDownloadConfig);

    /**
     * 删除指定id的下载配置记录
     *
     * @param dataDownloadConfig 下载配置对象
     * @return
     * @version <1> 2018-02-06 djh： Created.
     */
    ResultMessage deleteDataDownloadConfigById(DataDownloadConfig dataDownloadConfig);

    /**
     *  查询指定id对应的下载配置信息
     *
     *  @param downloadConfigParam 下载参数配置参数
     *  @return
     *  @version <1> 2018-02-06 djh： Created.
     */
    ResultMessage queryDataDownloadConfigById(DownloadConfigParam downloadConfigParam);

    /**
     * 根据数据分类查询下载配置记录
     * @param downloadConfigParam
     * @return
     * @version <1> 2018-03-02 xzg： Created.
     */
    ResultMessage findConfigByDataType(DownloadConfigParam downloadConfigParam);

}
