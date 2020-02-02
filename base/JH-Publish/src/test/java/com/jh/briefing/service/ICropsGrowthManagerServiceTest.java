package com.jh.briefing.service;

import com.github.pagehelper.PageInfo;
import com.jh.briefing.entity.CropsGrowthManager;
import com.jh.briefing.model.CropsGrowthManagerParam;
import com.jh.briefing.model.DiseaseControlManagerParam;
import com.jh.briefing.model.GrowthRelativeTempManagerParam;
import com.jh.data.model.DsAreaParam;
import com.jh.data.model.ProductQueryParam;
import com.jh.data.service.IDsAreaService;
import com.jh.data.service.IDsGrowthService;
import com.jh.data.service.IDsYieldService;
import com.jh.vo.ResultMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.coyote.http11.Constants.a;

/**
 * 作物生育期配置管理测试类
 * Created by lj on 2018/8/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ICropsGrowthManagerServiceTest {

    @Autowired
    private ICropsGrowthManagerService cropsGrowthManagerService;
    @Autowired
    private IDiseaseControlManagerService diseaseControlManagerService;
    @Autowired
    private IGrowthRelativeTempManagerService growthRelativeTempManagerService;
    @Autowired
    private IDsAreaService  dsAreaService;
    @Autowired
    private IDsGrowthService dsGrowthService;
    @Autowired
    private IDsYieldService dsYieldService;


    @Test
    public void findCropsGrowthManagerByPage() throws Exception {
        CropsGrowthManagerParam params=new CropsGrowthManagerParam();
        PageInfo<CropsGrowthManagerParam> page= cropsGrowthManagerService.findCropsGrowthManagerByPage(params);
        System.out.print(page);
    }

    @Test
    public void addGrowthManager() throws Exception {
        CropsGrowthManager m=new CropsGrowthManager();
        m.setGrowthName("始花期a");
        m.setCreateTime(LocalDateTime.now());
        m.setCreator(141);
        m.setCropsId(511);
        m.setCropsName("油菜");
        m.setRemark("单元测试数据");
        ResultMessage rm=cropsGrowthManagerService.addGrowthManager(m);
        System.out.println(rm);
    }

    @Test
    public void addDiseaseManager() throws Exception {
        List<DiseaseControlManagerParam>list=new ArrayList<DiseaseControlManagerParam>();
        DiseaseControlManagerParam m=new DiseaseControlManagerParam();
        m.setCropsId(511);
        m.setGrowthId(6);
        m.setDiseaseName("小麦赤霉病a");
        m.setDiseaseMeasure("抽穗扬花期是防治关键时期。可选用氰烯菌酯、戊唑醇、甲基硫菌灵、井冈·腊芽菌等喷雾防治；重发田隔5~7天再喷药1~2次。");
        m.setDiseaseRemark("测试备注");
        m.setDiseaseType(1);
        list.add(m);
        ResultMessage rm=diseaseControlManagerService.addBatchDiseaseControl(list);
        System.out.println(rm);
    }

    @Test
    public void addGrowthTemp() throws Exception {
        List<GrowthRelativeTempManagerParam>list=new ArrayList<GrowthRelativeTempManagerParam>();
        GrowthRelativeTempManagerParam m=new GrowthRelativeTempManagerParam();
        m.setGrowthId(6);
        m.setGrowthName("始花期a");
        m.setIfavg((short)1);
        m.setInstruction("适宜冬小麦抽穗开花");
        m.setTempRangeStart(10);
        m.setTempRangeEnd(20);
        list.add(m);
        ResultMessage rm=growthRelativeTempManagerService.saveBatchGrowthRelativeTempManager(list);
        System.out.println(rm);
    }

    @Test
    public void deleteCropsGrowthManager() throws Exception {
        ResultMessage rm=cropsGrowthManagerService.deleteCropsGrowthManager(6);
        System.out.println(rm);
    }

    @Test
    public void deleteDiseaseManager() throws Exception {
        int flag=diseaseControlManagerService.deleteBatchDiseaseControl(3);
        System.out.println(flag);
    }

    @Test
    public void deleteRelativeTempManager() throws Exception {
        ResultMessage rm=growthRelativeTempManagerService.deleteBatchGrowthConditionsByGrowthId(3);
        System.out.println(rm);
    }

    @Test
    public void findGrowthDataByGrowthId() throws Exception {
        ResultMessage rm= cropsGrowthManagerService.findGrowthDataByGrowthId(1);
        System.out.print(rm);
    }

    @Test
    public void updateGrowthManager() throws Exception {
        CropsGrowthManager m=new CropsGrowthManager();
        m.setId(2);
        m.setGrowthName("始花期1");
        m.setCreator(1411);
        m.setCropsId(5111);
        m.setCropsName("油菜1");
        m.setRemark("单元测试数据1");
        m.setModifier(1);
        ResultMessage rm=cropsGrowthManagerService.updateGrowthManager(m);
        System.out.println(rm);
    }

    @Test
    public void findAreaDataByproductId() throws Exception {
        ProductQueryParam p=new ProductQueryParam();
        p.setProductId(629);
        PageInfo rm= dsAreaService.findDataSatListByTaskId(p);
        System.out.print(rm);
    }

    @Test
    public void findGrowthDataByproductId() throws Exception {
        ProductQueryParam p=new ProductQueryParam();
        p.setProductId(635);
        PageInfo rm= dsGrowthService.findDataSatListByTaskId(p);
        System.out.print(rm);
    }

    @Test
    public void findYieldDataByproductId() throws Exception {
        ProductQueryParam p=new ProductQueryParam();
        p.setProductId(714);
        PageInfo rm= dsYieldService.findDataSatListByTaskId(p);
        System.out.print(rm);
    }

}