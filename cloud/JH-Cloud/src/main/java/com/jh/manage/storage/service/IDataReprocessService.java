package com.jh.manage.storage.service;

import com.github.pagehelper.PageInfo;
import com.jh.manage.storage.entity.DataReprocess;
import com.jh.manage.storage.model.ImportReprocessParam;
import com.jh.vo.ResultMessage;

import java.util.List;

/**
 * Description:
 * 1.再加工数据接口
 *
 * @version <1> 2018-04-27 9:15 zhangshen: Created.
 */
public interface IDataReprocessService {

    /**
     * 导入再加工数据
     * 1.修改文件夹名称
     * 2.往数据库写入信息
     * 3.复制文件到指定目录
     * @param importReprocessParam
     * @return
     * @version <1> 2018-04-25 zhangshen:Created.
     */
    ResultMessage importReprocess(ImportReprocessParam importReprocessParam);

    /**
     * 查询再加工数据列表
     * @param importReprocessParam
     * @return
     * @version <1> 2018-04-27 zhangshen:Created.
     */
    PageInfo<DataReprocess> findDateReprocessByPage(ImportReprocessParam importReprocessParam);

    /**
     * Description: 根据reprocessId,查询再加工数据
     * @param reprocessId
     * @return
     * @version <1> 2018/5/24 16:01 zhangshen: Created.
     */
    DataReprocess findDateReprocessById(Integer reprocessId);

    /**
     * 批量发布再加工数据到图层
     * @param reprocessIds
     * @param style 样式名称
     * @return 返回记录操作结果
     * @version <1> 2018-06-06 lxy: Created.
     */
    ResultMessage publishReprocessData(ImportReprocessParam importReprocessParam);

    /**
     * Description: 获取geoserver样式列表
     * @param
     * @return ResultMessage
     * @version <1> 2018/6/15 10:26 zhangshen: Created.
     */
    ResultMessage getGeoStyleList();

    /**
     * 修改再加工数据状态
     * @param rIds
     * @param dataReprocess
     * @return
     * version <1> 2018/6/20 10:26 xzg: Created.
     */
    ResultMessage updateReprocessDataStatus(List<Integer> rIds, DataReprocess dataReprocess);

}
