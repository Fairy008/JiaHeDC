package com.jh.data.service;

import com.jh.base.service.IBaseDataSetService;
import com.jh.report.enums.DataSetEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Description: 获取真实数据集service工厂类
 * @version <1> 2018-9-19 lijie: Created.
 * @version <1> 2018-10-12 lijie: update.
 */
@Component
public class DataSatServiceFactory {

    public static Logger logger= LoggerFactory.getLogger(DataSatServiceFactory.class);

    private static IBaseDataSetService baseIndexService;

    /**分布服务类*/
    @Autowired
    private IDsAreaService dsAreaService;
    /**长势服务类*/
    @Autowired
    private IDsGrowthService dsGrowthService;
    /**估产服务类*/
    @Autowired
    private IDsYieldService dsYieldService;


    /**分布服务类*/
    private static IDsAreaService areaService;
    /**长势服务类*/
    private static IDsGrowthService growthService;
    /**估产服务类*/
    private static IDsYieldService yieldService;

    @PostConstruct
    public void init() {
        areaService = dsAreaService;
        growthService = dsGrowthService;
        yieldService = dsYieldService;
    }

    /**
     *  根据指数类型查询真实的指数服务类
     * @param type 指数类型
     * @return
     */
    public static IBaseDataSetService getIndexService(Integer type){
        logger.info("根据指数类型查询真实的数据集服务类>>>开始");
        logger.info("指数类型>>>type={}",type);
        DataSetEnum dsType= DataSetEnum.getDataSetEnum(type);
        switch (dsType){
            case DATA_SET_DISTRIBUTION:
                baseIndexService=areaService;
                break;
            case DATA_SET_GROWTH:
                baseIndexService=growthService;
                break;
            case DATA_SET_YIELD:
                baseIndexService=yieldService;
                break;
            default:break;
        }
        logger.info("指数服务类>>>service={}",baseIndexService.getClass().getSimpleName());
        logger.info("根据指数类型查询真实的数据集服务类>>>结束");
        return baseIndexService;
    }


}
