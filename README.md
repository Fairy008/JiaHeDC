2019-07-16：
1. 产品结构按各系统进行文件夹与数据库的分离：
base:       所有系统的基础服务
cloud:      私有云工程
collector:  app采集器工程，加入农险定验标、定损的功能
forum:      遥感社区工程
show:       珈和show工程


2、spring-cloud微服务架构：
    基础服务与组件：
    JH-Biz    : 公用组件， 常用调用接口/Mybatis增删改查接口
    JH-Common : 公用组件， 常用的工具类
    JH-Server : 服务注册中心              8000
    JH-Gateway: 智能路由                  8001
    JH-Sys    : 系统管理微服务             8002

    #数据集微服务：
    JH-DS-Ttn : 降雨地温、天气服务          8003
    JH-DS-Dgy : 分布、长势、估产数据集服务   8004
    
    #地图与数据集发布
    JH-Map      : 地图微服务               8010
    JH-Publish  : 数据集发布服务           8007
    
    #业务系统微服务
    JH-Cloud    : 私有云微服务            8006
    JH-Show     : 报告、简报展示服务       8008
    JH-Forum    : 珈和遥感社区服务        8011
    
    

  
  
  
  
  
  
    
     
    
