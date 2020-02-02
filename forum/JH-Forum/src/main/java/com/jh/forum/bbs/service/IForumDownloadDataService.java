package com.jh.forum.bbs.service;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.service.IBaseService;
import com.jh.forum.bbs.entity.ForumDownloadData;
import com.jh.forum.bbs.vo.DownloadDataVo;
import com.jh.vo.ResultMessage;


/**
 * @Description:
 * @version<1> 2019-07-01 lcw :Created.
 */
public interface IForumDownloadDataService extends IBaseService<DownloadDataVo, ForumDownloadData,Integer> {

    PageInfo<DownloadDataVo> findByPage(DownloadDataVo downloadDataVo);

    ResultMessage findById(Integer dataId);

    ResultMessage findHotDataList();

    /*
     * 功能描述: 查询数据下载
     * @Param:
     * @Return:
     * @version<1>  2019/8/26  wangli :Created
     */
    ResultMessage findAgriculturalData(DownloadDataVo downloadDataVo);

    PageInfo<DownloadDataVo> findAllByPage(DownloadDataVo downloadDataVo);

    /**
     * 保存存储数据
     * @param downloadDataVo
     * @return
     */
    ResultMessage saveDownLoadData(DownloadDataVo downloadDataVo);

    ResultMessage audit(DownloadDataVo downloadDataVo);

    ResultMessage updateData(DownloadDataVo downloadDataVo);

    ResultMessage getDataById(Integer dataId);

    ResultMessage getSimilarData(Integer classify);

    ResultMessage confirmationOfOrder(DownloadDataVo downloadDataVo);

    ResultMessage downloadExample(DownloadDataVo downloadDataVo);

    ResultMessage freeDownloadData(DownloadDataVo downloadDataVo);

    ResultMessage downloadStatistc(Integer dataId);

    ResultMessage collectStatistc(Integer dataId);

    ResultMessage cancelCollectStatistic(Integer dataId);

    ResultMessage calculate();


}
