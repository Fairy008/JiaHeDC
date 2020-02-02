package com.jh.manage.archive.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.manage.archive.entity.DataArchive;
import com.jh.manage.archive.model.ArchiveParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IDataArchiveMapper extends IBaseMapper<ArchiveParam, DataArchive, Integer> {
    /**
     * @description: 查询数据归档列表
     * @param archiveParam 数据归档查询条件
     * @return
     * @version<1> 2018-03-21 wl: Created.
     */
    List<DataArchive> findAll(ArchiveParam archiveParam);
    /**
     * @description: 根据用户id查询详细信息
     * @param archiveId 数据归档主键
     * @return
     * @version<1> 2018-03-21 wl: Created.
     */
    DataArchive findById(Integer archiveId);

    /**
     * @description: 保存数据归档信息
     * @param dataArchive 数据归档信息
     * @return
     * @version<1> 2018-03-26 wl: Created.
     */
    Integer saveArchive(DataArchive dataArchive);

    /**
     * 更新数据归档信息
     * @param archiveParam
     * @version<1> 2018-03-27 wl : Created.
     */
    void updateByPrimaryKey(ArchiveParam archiveParam);

    /**
     * 更新数据归档信息
     * @param dataArchive
     * @version<1> 2018-03-27 wl : Created.
     */
    void updateByPrimaryKeySelective(DataArchive dataArchive);

    /**
     *统计不同卫星数据的归档数量
     * @param  archiveParam 订单对象
     * @version <1> 2018-04-18 wl： Created.
     */
    List<Map<String, Object>> queryArchiveSateNum(ArchiveParam archiveParam);

    /**
     *统计归档卫星数据排行前5
     * @param  archiveParam 订单对象
     * @version <1> 2018-06-13 wl： Created.
     */
    List<Map<String, Object>>queryDataStorageSateNumTop5(ArchiveParam archiveParam);

    /**
     *统计已经归档的数据总和
     * @version <1> 2018-06-13 wl： Created.
     */
    List<Map<String, Object>> queryDataArchiveSum();

    List<Map<String,Object>> queryTotalArchiveEcharts(ArchiveParam archiveParam);
}