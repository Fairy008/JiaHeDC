package com.jh.manage.storage.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.manage.storage.entity.DataReprocess;
import com.jh.manage.storage.model.ImportReprocessParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 * 1.再加工数据Mapper
 *
 * @version <1> 2018-04-25 17:37 zhangshen: Created.
 */
@Mapper
public interface IDataReprocessMapper extends IBaseMapper<ImportReprocessParam, DataReprocess, Integer> {

    /**
     * 插入再加工数据导入明细
     * @param list
     */
    void insertDataReprocess(List<DataReprocess> list);

    /**
     * Description: 根据reprocessId,查询再加工数据
     * @param reprocessId
     * @return
     * @version <1> 2018/5/24 16:01 zhangshen: Created.
     */
    DataReprocess findDateReprocessById(Integer reprocessId);

    /**
     * Description: 根据processIds集合,查询再加工数据
     * @param reprocessIds 处理编号
     * @return
     * @version <1> 2018-06-06 lxy: Created.
     */
    List<DataReprocess> findDateReprocessByIds(Integer[] reprocessIds);



    /**
     * 批量发布再加工数据到图层
     * @param reprocessParam 修改的参数
     * @return 返回操作记录数
     * @version <1> 2018-06-06 lxy: Created.
     */
    int updateReprocessDataPublishStatus(ImportReprocessParam reprocessParam);

    /**
     * 批量发布再加工数据到图层
     * @param rIds
     * @param dataReprocess
     * @return 返回操作记录数
     * @version <1> 2018-06-06 lxy: Created.
     */
    void batchPublishDataReprocess(@Param("rIds") List<Integer> rIds, @Param("dataReprocess") DataReprocess dataReprocess);

}
