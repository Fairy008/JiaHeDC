package com.jh.manage.download.mapping;

import com.jh.base.repository.IBaseMapper;
import com.jh.manage.download.entity.DataDownload;
import com.jh.manage.download.model.DownloadParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface IDataDownloadMapper  extends IBaseMapper<DownloadParam, DataDownload, Integer> {

    /**
     * 根据条件查询下载任务
     * @param downloadParam 查询参数
     * @return
     * @version <I> 2018-03-01 XZG: Created
     */
    List<DataDownload> findByParam(DownloadParam downloadParam);

    /**
     * 分业查询下载任务
     * @param downloadParam
     * @return
     * @version <I> 2018-03-01 XZG: Created
     */
    List<DownloadParam> queryByPage(DownloadParam downloadParam);

    /**
     * 查询任务详细信息
     * @param downloadId
     * @return
     */
    DownloadParam findDownloadById(Integer downloadId);

    /**
     * 编辑下载任务信息
     * @param downloadParam
     * @version<1> 2018-05-31 wl : Created.
     */
    void updateDownload(DownloadParam downloadParam);

    /**
     * Description: 获取大屏显示所需的数据(下载数据)
     * @param
     * @return
     * @version <1> 2018/8/16 13:57 zhangshen: Created.
     */
    List<Map<String, Object>> getEchartsDownloadData(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    /**
     * Description: 获取下载历史总数量
     * @param
     * @return
     * @version <1> 2018/8/16 20:41 zhangshen: Created.
     */
    Integer queryDownloadEchartsTotal();
}