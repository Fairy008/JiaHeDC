package com.jh.manage.archive.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.manage.archive.entity.DataArchiveDetail;
import com.jh.manage.archive.model.ArchiveParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IDataArchiveDetailMapper extends IBaseMapper<ArchiveParam , DataArchiveDetail, Integer> {

    /**
     * 删除数据归档信息
     * @param archiveId
     * @version<1> 2018-03-27 wl : Created.
     */
    void deleteDetail(Integer archiveId);

    /**
     * @description: 根据archiveId查询storageId
     * @param archiveId 数据归档主键id
     * @return
     * @version <1> 2018-03-27 wl： Created.
     */
    List<DataArchiveDetail> selectByArchiveId(Integer archiveId);


    /**
     * 批量添加数据归档信息
     * @param archiveDetails
     * @version<1> 2018-05-30 wl : Created.
     */
    void saveDetails(List<DataArchiveDetail> archiveDetails);



}