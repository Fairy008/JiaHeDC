package com.jh.manage.download.service;

import com.jh.base.service.IBaseService;
import com.jh.manage.download.entity.DataDownloadDetail;
import com.jh.manage.download.model.DownloadDetailParam;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 * Created by XZG on 2018/3/7.
 */
public interface IDataDownloadDetailService extends IBaseService<DownloadDetailParam,DataDownloadDetail,Integer> {


    /**
     * 批量添加下载文件记录
     * @param detailList
     * @version <I> 2018-03-07 XZG: Created
     */
    public void saveBatch(List<DataDownloadDetail> detailList);

    /**
     * 根据任务查询所有的下载文件
     * @param downloadId
     * @return
     * @version <I> 2018-03-08 XZG: Created
     */
    public ResultMessage findDetailList(Integer downloadId);

}
