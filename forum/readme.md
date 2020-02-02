 
2.社区开发要求：
  1）社区需要调用的接口统一调用JH-Forum , 服务间的调用使用feign组件进行调用
  
  2）不存在业务表与字典表的关联查询，业务表中的ID字段如果需要转换，请调用缓存方法实现转换，缓存文档请参见**《SVN:/RDC/06 培训资料/缓存操作说明.docx》**
  
  3）采用独立数据库（forum）进行开发
  
  4）接口编写的时候，需要在test包中写对应的测试用例
  
  
  
3.部署说明：
   收藏与点赞表（forum_follow）:
      添加唯一性索引(2019-03-11)：
      ALTER TABLE "public"."forum_follow" 
        DROP CONSTRAINT "follow_unique_index",
        ADD CONSTRAINT "follow_unique_index" UNIQUE ("article_id", "follow_type", "creator");
      COMMENT ON CONSTRAINT "follow_unique_index" ON "public"."forum_follow" IS '一个人只能收藏、点赞一次';
  

开发说明：
1.首页： “开启数据定制”页面跳转未实现
2.首页：“热门数据”列表未实现
3.首页：轮播图需要替换实际图片
4.在线客服： 客户QQ必须在线，否则获取不到， 配置： url_config.js ===>  QQ_ONLINE.qq
