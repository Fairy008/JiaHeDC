package com.jh.briefingNew.mapping;

import com.jh.briefingNew.entity.BriefingTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * description:
 *
 * @version <1> 2018-07-20 wl: Created.
 */
@Mapper
public interface IBriefingTemplateMapper {
    /**
     * 根据主键查询对应的数据
     * @param templateType 主键
     * @return 返回BriefingTemplate对象
     * @version <1> 2018-07-23 wl created
     */
    BriefingTemplate findBriefingTemplateByType(@Param("templateType") Integer templateType);
}
