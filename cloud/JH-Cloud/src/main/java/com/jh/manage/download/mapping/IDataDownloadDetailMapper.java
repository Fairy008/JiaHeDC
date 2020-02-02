package com.jh.manage.download.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.manage.download.entity.DataDownloadDetail;
import com.jh.manage.download.model.DownloadDetailParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IDataDownloadDetailMapper extends IBaseMapper<DownloadDetailParam, DataDownloadDetail, Integer> {

    /**
     *批量添加下载文件记录
     * @param detailList
     * @version <1> 2017-03-07 xzg: Created.
     */
    void batchInsert(List<DataDownloadDetail> detailList);

    /**
     * 根据任务查询下载的文件详情
     * @param downloadId 任务主键
     * @return
     * @version <1> 2017-03-08 xzg: Created.
     */
    List<DataDownloadDetail> findDetailByDownloadId(Integer downloadId);

}