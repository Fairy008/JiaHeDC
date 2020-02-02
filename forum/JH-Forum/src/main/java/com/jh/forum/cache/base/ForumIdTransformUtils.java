package com.jh.forum.cache.base;

import com.jh.enums.CacheTypeEnum;
import com.jh.forum.cache.service.ICacheService;
import com.jh.forum.cache.vo.CommentCacheVO;
import com.jh.forum.cache.vo.ForumCacheVO;
import com.jh.util.CacheUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:统计占比解析类：
 * 1.遍历所有使用了@IdToName注解的属性
 * 4.查找到注解上配置的ID属性，反射赋值
 *
 * @version <1> 2018-10-16 14:54 lijie: Created.
 */
@Component
public class ForumIdTransformUtils {

    private static Logger logger = LoggerFactory.getLogger(ForumIdTransformUtils.class);

    private static ICacheService cacheService;

    @Autowired
    private ICacheService iCacheService;

    @PostConstruct
    public void init() {
        cacheService = iCacheService;
    }

    public static void forumIdTransForObj(Object cobj){
        try {
            Class clazz = cobj.getClass();
            //获取所有属性值
            Field[] fields = getAllFields(clazz);
            for (Field field : fields) {
                //找出有需要转化数据的值
                ForumIdTransform r = field.getAnnotation(ForumIdTransform.class);
                if (r != null) {
                    //获取缓存类型
                    String cacheType = (String)r.type();
                    //获取转化类型
                    String transType=(String)r.transType();
                    //获取id属性值
                    Field field_prop = getFieldByName(clazz,r.propName());
                    field_prop.setAccessible(true);
                    Object obj=field_prop.get(cobj);
                    if(obj ==null){
                        //logger.info("id值为空：{}");
                        continue;
                    }
                    String idStr =field_prop.get(cobj).toString();
                    if(StringUtils.isBlank(idStr)){
                        //logger.info("id值为空：{}",idStr);
                        continue;
                    }
                    //根据ID转化
                    Integer name = 0;
                    if (cacheType.equals(CacheTypeEnum.CACHE_TYPE_FOLLOW_FIRSTCOMMENT.getType())) {
                        ResultMessage result = cacheService.queryFollowCommentNumByArticleId(Integer.parseInt(idStr));
                        if (result.isFlag()) {
                            ForumCacheVO forumCacheVO = (ForumCacheVO)result.getData();
                            if (transType.equals(CacheUtil.CACHE_FORUM_TRANS_LIKE)) {
                                name = forumCacheVO.getFollowNum();
                            } else if (transType.equals(CacheUtil.CACHE_FORUM_TRANS_COMMENT)) {
                                name = forumCacheVO.getCommentNum();
                            }
                        }
                    } else if (cacheType.equals(CacheTypeEnum.CACHE_TYPE_SECONDCOMMENT.getType())){
                        ResultMessage result = cacheService.querySecondCommentNumByCommentId(Integer.parseInt(idStr));
                        if (result.isFlag()) {
                            CommentCacheVO commentCacheVO = (CommentCacheVO)result.getData();
                            name = commentCacheVO.getCommentNum();
                        }
                    }

                    field.setAccessible(true);
                    field.set(cobj, name);
                }
            }

        }catch(Exception e){
            logger.error("通过反射根据ID设置名称或编码失败："+e.getMessage());
        }
    }

    public static void idTransForList(List list){
        for(Object obj:list){
            forumIdTransForObj(obj);
        }
    }

    public static void main(String [] args)throws Exception{
        ForumCacheVO p = new ForumCacheVO();
        p.setArticleId(1);
        forumIdTransForObj(p);
        System.out.println(p.getFollowNum() + "------" + p.getCommentNum());
    }
    /**
     * 获取所有属性值，包括私有属性和继承属性
     * @param clazz
     * @return
     */
    public static Field[] getAllFields(Class clazz){
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null){
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    /**
     * 根据属性名称获取属性对象，包括私有属性和继承属性
     * @param clazz
     * @return
     */
    public static Field getFieldByName(Class clazz,String fieldName){
        if(StringUtils.isBlank(fieldName)){
            return null;
        }
        Field[] fields=getAllFields(clazz);
        for(Field f:fields){
            if(fieldName.equals(f.getName())){
                return f;
            }
        }
        return null;
    }

}


