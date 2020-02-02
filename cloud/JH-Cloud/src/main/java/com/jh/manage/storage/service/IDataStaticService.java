package com.jh.manage.storage.service;

import com.github.pagehelper.PageInfo;
import com.jh.manage.storage.entity.DataStatic;
import com.jh.manage.storage.model.ImportStaticParam;
import com.jh.vo.ResultMessage;

/**
 * Description:
 * 1.矢量数据接口
 *
 * @version <1> 2018-04-27 9:42 zhangshen: Created.
 */
public interface IDataStaticService {

    /**
     * 导入矢量数据
     * 1.修改文件夹名称
     * 2.往数据库写入信息
     * 3.复制文件到指定目录
     * @param importStaticParam
     * @return
     * @version <1> 2018-04-26 zhangshen:Created.
     */
    ResultMessage importStatic(ImportStaticParam importStaticParam);

    /**
     * 查询矢量数据
     * @param importStaticParam
     * @return
     * @version <1> 2018-04-28 zhangshen:Created.
     */
    PageInfo<DataStatic> findDateStaticByPage(ImportStaticParam importStaticParam);

    /**
     * Description: 根据staticId,查询矢量数据
     * @param staticId 
     * @return
     * @version <1> 2018/5/24 16:25 zhangshen: Created.
     */
    DataStatic findDateStaticById(Integer staticId);
}
