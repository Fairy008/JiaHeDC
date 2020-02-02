package com.jh.report.enums;

/**
 * Description: 数据集枚举
 * 1. 生成报告业务中, 根据 数据集、区域、作物、精度 查询数据时间中用到
 *
 * @version <1> 2018-07-18 17:24 zhangshen: Created.
 */
public enum DataSetEnum {

    DATA_SET_TRMM(1805,"降雨",1),
    DATA_SET_T(1804,"地温",1),
    DATA_SET_YIELD(1802,"估产",0),
    DATA_SET_DISTRIBUTION(1801,"分布",1),
    DATA_SET_GROWTH(1803,"长势",1),
    DATA_SET_DROUGHT(1806,"干旱",1);

    private Integer id;
    private String name;
    //是否发布Tiff图层
    private Integer isPublish;

    DataSetEnum(Integer id, String name,Integer isPublish) {
        this.id = id;
        this.name = name;
        this.isPublish=isPublish;
    }

    /**
     * switch方法使用
     */
    public static DataSetEnum getDataSetEnum(Integer id){
        if(id==null){
            return null;
        }
        DataSetEnum [] enums=DataSetEnum.values();
        for(DataSetEnum taskEnum:enums){
            if(id.equals(taskEnum.getId())){
                return taskEnum;
            }
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Integer isPublish) {
        this.isPublish = isPublish;
    }
}
