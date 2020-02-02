package com.jh.manage.loader.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.manage.loader.entity.DataLoader;
import com.jh.manage.loader.entity.DataLoaderDetail;
import com.jh.manage.loader.model.LoaderParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IDataLoaderMapper extends IBaseMapper<LoaderParam, DataLoader, Integer> {


    /**
     * 根据入库任务ID查询入库明细
     * @param dataLoaderId
     * @return
     * @version<1>  2018-03-01 lcw:Cteated.
     */
    List<DataLoaderDetail> findDataLoaderDetailById(Integer dataLoaderId);

    /**
     * 更新入库任务的入库状态
     * @param dataLoader
     * @version<1> 2018-03-04 lcw : Created.
     */
    void updateStatus(DataLoader dataLoader);

    /**
     * 保存入库任务的任务明细
     * @param details
     * @version<1> 2018-03-04 lcw : Created.
     */
    void saveOrderDetail(List<DataLoaderDetail> details);

    List<Map<String,Object>> queryLoaderSateSumEcharts(LoaderParam loaderParam);

    List<Map<String,Object>> queryLoaderSateSumTableEcharts(LoaderParam loaderParam);

    /**
     * Description: 获取近七天GF1~GF4入库数量
     * @param loaderParam
     * @return
     * @version <1> 2018/8/16 16:50 zhangshen: Created.
     */
    List<Map<String,Object>> queryLoaderEcharts(LoaderParam loaderParam);

    /**
     * Description: 获取入库历史总数量
     * @param
     * @return
     * @version <1> 2018/8/16 20:41 zhangshen: Created.
     */
    Integer queryLoaderEchartsTotal();
}