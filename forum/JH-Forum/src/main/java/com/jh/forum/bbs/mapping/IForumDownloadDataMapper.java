package com.jh.forum.bbs.mapping;

import com.jh.forum.base.repository.IBaseMapper;
import com.jh.forum.bbs.entity.ForumDownloadData;
import com.jh.forum.bbs.vo.DownloadDataVo;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface IForumDownloadDataMapper extends IBaseMapper<DownloadDataVo, ForumDownloadData, Integer>{

    List<DownloadDataVo> getListByCombination(DownloadDataVo downloadDataVo);

    DownloadDataVo findById(Integer dataId);

    List<DownloadDataVo> findHotDataList(DownloadDataVo downloadDataVo);

    void updateDownloadData(DownloadDataVo downloadDataVo);

    List<DownloadDataVo> findAgriculturalData(DownloadDataVo downloadDataVo);

    List<DownloadDataVo> getSimilarData(Integer classify);

    List<DownloadDataVo> confirmationOfOrder(List<Integer> list);

    BigDecimal totalPriceForOrder(List<Integer> list);

    List<DownloadDataVo> downloadExample(DownloadDataVo downloadDataVo);

    List<DownloadDataVo> freeDownloadData(DownloadDataVo downloadDataVo);

    void downloadStatistc(Integer dataId);

    void collectStatistc(Integer dataId);

    void cancelCollectStatistic(Integer dataId);

    /**
     * 功能描述:更新下载量和收藏量
     * @Param:
     * @Return: []
     * @version<1>  2019/9/29  wangli :Created
     */
    void updateNum();

    /*
     * 功能描述:批量设置计算价格  价格都为1
     * @Param:
     * @Return: []
     * @version<1>  2019/10/22  wangli :Created
     */
    void calculateForOne(List<Integer> list);

    /*
     * 功能描述:批量设置计算价格  价格都为10
     * @Param:
     * @Return:
     * @version<1>  2019/10/22  wangli :Created
     */
    void calculateForTen(List<Integer> list);
}