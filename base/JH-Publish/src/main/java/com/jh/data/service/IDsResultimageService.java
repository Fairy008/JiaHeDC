package com.jh.data.service;

import com.github.pagehelper.PageInfo;
import com.jh.data.entity.DsResultimage;
import com.jh.data.model.ImportParam2;
import com.jh.vo.ResultMessage;

/**
 * Description:
 *
 * @version <1> 2018-07-02  lcw : Created.
 */
public interface IDsResultimageService {
    PageInfo<ImportParam2> findImportDataFromCeph(ImportParam2 importParam);

    /**
     * 对数据集数据进行入库
     * @param resultimage
     * @return
     */
    ResultMessage saveLoader(DsResultimage resultimage);


    /**
     * 分页查询
     * @param dsResultimage
     * @return
     * @version <1> 2018-07-03  lcw : Created.
     */
    PageInfo<DsResultimage> findByPage(DsResultimage dsResultimage);


    /**
     * 根据ID查询数据集影像数据
     * @param rgId
     * @return
     * @version <1> 2018-07-03  lcw : Created.
     */
    ResultMessage findResultimageById(Integer rgId);

    /**
     * 激活数据生产任务
     * @param resultimage
     * @return
     * @version <1> 2018-07-03  lcw : Created.
     *
     */
    ResultMessage updateResultimageByIds(DsResultimage resultimage);
    /**
     * 更新生产任务
     * @param resultimage
     * @return
     * @version <1> 2018-07-03  lcw : Created.
     *
     */
    public ResultMessage updateResultimage(DsResultimage resultimage);

    /**
     * 根据ID查询数据集影像
     * @param rgId
     * @return
     */
    public ResultMessage queryResultimage(Integer rgId);

    /**
     * 批量发布图层
     * @param resultimage
     * @return
     * @version <1> 2018-07-04  lcw : Created.
     *
     */
    ResultMessage publishTif(DsResultimage resultimage);

    ResultMessage checkNull(String path);

    ResultMessage findImportDataDetail(ImportParam2 importParam, String suffixs);

    /**
     * 批量发布图层和影像
     * @param resultimage
     * @return
     * @version <1> 2019-02-18  lijie : Created.
     *
     */
    ResultMessage publishTifAndData(DsResultimage resultimage);
    /**
     * 撤回和批量撤回
     * @param resultimage
     * @return
     * @version <1> 2019-02-18  lijie : Created.
     *
     */
    ResultMessage backTifAndData(DsResultimage resultimage);

    /**
     * 删除和批量删除数据
     * @param resultimage
     * @return
     * @version <1> 2019-02-18  lijie : Created.
     *
     */
    ResultMessage deleteTifAndData(DsResultimage resultimage);

    /**
     * 数据处理中的数据集入库
     * @param resultimage
     * @return
     */
    ResultMessage saveHandleDataLoader(DsResultimage resultimage);
}
