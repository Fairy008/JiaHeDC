package com.jh.forum;

import com.github.pagehelper.PageInfo;
import com.jh.biz.feign.ICollectionTaskService;
import com.jh.forum.bbs.service.IForumArticleService;
import com.jh.forum.bbs.vo.ArticleVO;
import com.jh.util.JsonUtils;
import com.jh.vo.ResultMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @Description:
 * @version<1> 2019-03-08 xiayong :Created.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectionTaskServiceTest {

    @Autowired
    private ICollectionTaskService collectionTaskService;


    /**
     * 保存采集任务
     */
    @Test
    public void testSaveCollectionTask(){
        System.out.println("保存采集任务");
        String phone = "15361674006";
        String taskName = "测试任务";
        String workplace = "武汉珈和科技有限公司";
        Integer taskType =1;
        String templateContent = "测试测试";
        String remark = "测试测试";
        Date startDate = new Date();
        Date endDate = new Date();
        ResultMessage result = collectionTaskService.saveCollectionTask(phone,taskName,workplace,taskType,templateContent,remark,startDate,endDate);
        System.out.println(JsonUtils.objectToJson(result));
    }


    /**
     * 更新采集任务
     */
    @Test
    public void testUpdateCollectionTask(){
        System.out.println("更新采集任务");
        Integer id = 197;
        String taskName = "我去我去";
        String workplace = "武汉珈和科技有限公司";
        Integer taskType =1;
        Date endDate = new Date();
        ResultMessage result = collectionTaskService.updateCollectionTask(id,taskName,workplace,taskType,endDate);
        System.out.println(JsonUtils.objectToJson(result));
    }

    /**
     * 删除采集任务
     */
    @Test
    public void testDelCollectionTask(){
        System.out.println("删除采集任务");
        Integer id = 197;
        ResultMessage result = collectionTaskService.deleteCollectionTask(id);
        System.out.println(JsonUtils.objectToJson(result));
    }

    /**
     * 查询采集任务（分页查询）
     */
    @Test
    public void testGetCollectionTask(){
        System.out.println("测试最新报告查询");
        String phone = "15361674006";
        ResultMessage result = collectionTaskService.findFrontByUser(phone);
        System.out.println(JsonUtils.objectToJson(result));
    }

    /**
     * 查询用户采集模板
     */
    @Test
    public void testFindApiTemplateByPage(){
        System.out.println("测试用户模板查询");
        String phone = "15361674006";
        ResultMessage result = collectionTaskService.findApiTemplateByPage(phone);
        System.out.println(JsonUtils.objectToJson(result));
    }
}

