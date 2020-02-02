package com.jh.data.mapping;

import com.jh.data.entity.DsResultimage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description:
 *
 * @version <1> 2018-07-02  lcw : Created.
 */
@Mapper
public interface IDsResultimageMapper {

    void save(DsResultimage resultimage);

    List<DsResultimage> findByPage(DsResultimage dsResultimage);

    DsResultimage findById(Integer rgId);

    void updateResultimageByIds(DsResultimage dsResultimage);

    void update(DsResultimage dsResultimage);

    /**
     * 根据发布信息更新数据集影像
     * @param resultimageVo
     */
    void updateResultimageWithPublish(DsResultimage resultimageVo);
}
