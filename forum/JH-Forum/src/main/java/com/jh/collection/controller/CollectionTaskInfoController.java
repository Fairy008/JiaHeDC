package com.jh.collection.controller;

import com.github.pagehelper.PageInfo;
import com.jh.collection.entity.CollectionTaskInfo;
import com.jh.collection.entity.TaskItemFeild;
import com.jh.collection.entity.query.CollectionTaskInfoQuery;
import com.jh.collection.entity.vo.CollectionTaskItemVo;
import com.jh.collection.entity.vo.CollectionTaskVo;
import com.jh.collection.feign.IUploadService;
import com.jh.collection.service.ICollectionTaskInfoService;
import com.jh.collection.service.ICollectionTaskItemService;
import com.jh.collection.utils.BeanToXmlUtils;
import com.jh.collection.utils.CommonExcelUtil;
import com.jh.forum.base.controller.BaseController;
import com.jh.util.AccountTokenUtil;
import com.jh.util.JsonUtils;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import static com.jh.constant.CommonDefineEnum.REQUEST_FAIL;

/**
 * 用户采集任务（大分类）controller
 *
 * @version <1> 2018-12-04 xy： Created.
 */
@Api(value = "采集任务（大分类）", description = "用户采集任务（大分类）服务")
@RestController
@RequestMapping("/collection/taskInfo/")
public class CollectionTaskInfoController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(CollectionTaskInfoController.class);

    @Resource(name = "collectionTaskInfoServiceImpl")
    private ICollectionTaskInfoService collectionTaskInfoService;

    @Resource(name="collectionTaskItemServiceImpl")
    private ICollectionTaskItemService collectionTaskItemService;

    @Autowired
    private IUploadService uploadService;

    @ModelAttribute
    public CollectionTaskInfo get(@RequestParam(required = false) Integer id) {
        CollectionTaskInfo entity = null;
        if (id != null) {
            entity = collectionTaskInfoService.getById(id);
        }
        if (entity == null) {
            entity = new CollectionTaskInfo();
        }
        return entity;
    }

    /**
     * 采集任务模板分页查询
     *
     * @param collectionTaskInfoQuery 请求查询参数
     * @return PageInfo
     * @version <1> 2018-11-19 xy： Created.
     */
    @ApiOperation(value = "采集任务（大分类）分页查询", notes = "采集任务（大分类）分页查询")
    @ApiImplicitParam(name = "collectionTaskInfoQuery", value = "采集任务（大分类）参数", required = false, dataType = "CollectionTaskInfoQuery")
    @PostMapping("findByPage")
    public PageInfo<CollectionTaskInfo> findByPage(CollectionTaskInfoQuery collectionTaskInfoQuery) {
        return collectionTaskInfoService.findByPage(collectionTaskInfoQuery);
    }

    @ApiOperation(value = "采集Api任务（大分类）分页查询", notes = "采集任务（大分类）分页查询")
    @ApiImplicitParam(name = "collectionTaskInfoQuery", value = "采集任务（大分类）参数", required = false, dataType = "CollectionTaskInfoQuery")
    @PostMapping("findApiByPage")
    public ResultMessage findApiByPage(@RequestBody CollectionTaskInfoQuery collectionTaskInfoQuery) {
        PageInfo<CollectionTaskInfo> pageInfo = null;
        try {
            pageInfo = collectionTaskInfoService.findByPage(collectionTaskInfoQuery);
        } catch (Exception e) {
            LOG.error(">>>>系统异常e:{}", e.getMessage());
            return ResultMessage.fail(REQUEST_FAIL.getMesasge());
        }
        return ResultMessage.success(pageInfo);
    }

    @PostMapping("findByList")
    public PageInfo<CollectionTaskInfo> findByList(@RequestBody CollectionTaskInfoQuery collectionTaskInfoQuery) {
        return collectionTaskInfoService.findByPage(collectionTaskInfoQuery);
    }

    /**
     * 新增采集任务模板
     *
     * @param collectionTaskInfo 新增采集任务模板
     * @return
     * @version <1> 2018-12-04 xy： Created.
     */
    @ApiOperation(value = "新增采集任务（大分类）", notes = "新增采集任务（大分类）")
    @ApiImplicitParam(name = "collectionTaskInfo", value = "新增采集任务（大分类）", required = false, dataType = "CollectionTaskInfo")
    @PostMapping(value="add")
    public ResultMessage add(@RequestBody CollectionTaskInfo collectionTaskInfo) {
        collectionTaskInfo.setPhone(this.getCurrentAccount());
        return collectionTaskInfoService.addTaskInfo(collectionTaskInfo);
    }

    /**
     * 根据ID获取采集任务模板
     *
     * @param collectionTaskInfo 采集任务模板对象
     * @return
     * @version <1> 2018-11-19 xy： Created.
     */
    @ApiOperation(value = "根据ID获取采集任务对象", notes = "根据ID获取采集任务对象")
    @ApiImplicitParam(name = "collectionTaskInfo", value = "采集任务对象", required = true, dataType = "CollectionTaskInfo")
    @PostMapping("getById")
    public ResultMessage getById(CollectionTaskInfo collectionTaskInfo) {
        return ResultMessage.success(collectionTaskInfo);
    }

    /**
     * 更新采集任务模板
     *
     * @param collectionTaskInfo 采集任务模板对象
     * @return
     * @version <1> 2018-11-19 xy： Created.
     * @version <2> 2018-11-19 xy： update:重写方法
     */
    @ApiOperation(value = "更新采集任务信息", notes = "更新采集任务信息")
    @ApiImplicitParam(name = "CollectionTaskInfo", value = "更新任务信息", required = false, dataType = "CollectionTaskInfo")
    @PostMapping("update")
    public ResultMessage update(@RequestBody CollectionTaskInfo collectionTaskInfo) {
        return collectionTaskInfoService.updateTaskInfo(collectionTaskInfo);
    }

    /**
     * 删除采集任务
     *
     * @param collectionTaskInfo 删除采集任务
     * @return
     * @version <1> 2018-11-19 xy： Created
     * @version <2> 2018-11-19 xy： update:重写方法
     */
    @ApiOperation(value = "删除采集任务", notes = "删除采集任务")
    @PostMapping("delete")
    public ResultMessage delete(CollectionTaskInfo collectionTaskInfo) {
        return collectionTaskInfoService.deleteTaskInfo(collectionTaskInfo);
    }

    @ApiOperation(value = "Api删除采集任务", notes = "Api删除采集任务")
    @PostMapping("apiDelete")
    public ResultMessage apiDelete(@RequestBody CollectionTaskInfo collectionTaskInfo) {
        return collectionTaskInfoService.deleteTaskInfo(collectionTaskInfo);
    }

    /**
     * 导出excel
     *
     * @return
     */
    @GetMapping("export")
    public ResultMessage export(CollectionTaskInfo collectionTaskInfo, HttpServletResponse response) throws Exception {
        try {
            if (collectionTaskInfo == null || collectionTaskInfo.getId() == null) {
                return ResultMessage.fail("未找到此任务");
            }
            if (StringUtils.isEmpty(collectionTaskInfo.getTemplateContent())) {
                return ResultMessage.fail("此任务未关联模板");
            }
            CollectionTaskVo collectionTaskVo = BeanToXmlUtils.xml2Bean(CollectionTaskVo.class, collectionTaskInfo.getTemplateContent());
            if (collectionTaskVo == null || CollectionUtils.isEmpty(collectionTaskVo.getCollectionFieldModelVoList())) {
                return ResultMessage.fail("此任务未关联模板");
            }
            Map<Integer, List<TaskItemFeild>> taskItemFeildMap = collectionTaskInfoService.getTaskItemFeildMap(collectionTaskInfo.getId());
            if (taskItemFeildMap.size() == 0) {
                return ResultMessage.fail("未创建采集任务");
            }
            String sheetName = collectionTaskInfo.getTaskName();
            Integer mapKey = 0;
            //遍历采集数据集合
            //创建一个二维数组显示表格内容
            String[][] data = new String[taskItemFeildMap.size()][];
            int index = 0;
            for (Map.Entry<Integer, List<TaskItemFeild>> m : taskItemFeildMap.entrySet()) {
//                System.out.println("key:" + m.getKey() + " value:" + m.getValue());
                mapKey = m.getKey();
                List<TaskItemFeild> taskItemFeildList = m.getValue();
                String[] item = new String[taskItemFeildList.size()];
                for (int i = 0; i < taskItemFeildList.size(); i++) {
                    item[i] = taskItemFeildList.get(i).getCollectionValue();
                }
                data[index] = item;
                index++;
            }
            //获取导出表格标题
            List<TaskItemFeild> titleFeildList = taskItemFeildMap.get(mapKey);
            String[] title = new String[titleFeildList.size()];
            for (int j = 0; j < titleFeildList.size(); j++) {
                title[j] = titleFeildList.get(j).getFeildNameCh();
            }
            String fileName = collectionTaskInfo.getTaskName() + ".xls";//任务名作为excel文件名
            HSSFWorkbook wb = CommonExcelUtil.getHSSFWorkbook(sheetName, title, data, null);
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();

        } catch (Exception e) {
            return ResultMessage.fail("导出数据失败：" + e);
        }
        return ResultMessage.success();
    }

    @ResponseBody
    @RequestMapping("downloadZip")
    public void download(CollectionTaskInfo collectionTaskInfo,HttpServletResponse response) throws IOException {
        /*HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=media.zip");
        //下载tar图片资源文件
        String mediasId = collectionTaskInfoService.getAllMedisId(collectionTaskInfo.getId());
        byte[] data = uploadService.downloadZip(mediasId);
        return  new ResponseEntity<Object>(data, headers, HttpStatus.OK);*/

        //方法二
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=downloadZip.zip");
        //下载tar图片资源文件
        String mediasId = collectionTaskInfoService.getAllMedisId(collectionTaskInfo.getId());
        LOG.info(">>>download param mediasId:{},phone:{}",mediasId,collectionTaskInfo.getPhone());
        byte[] data = uploadService.downloadZip(mediasId,collectionTaskInfo.getPhone());
        if(data == null || data.length == 0){
            response.setCharacterEncoding("UTF-8");
            OutputStream outputSream = response.getOutputStream();
            outputSream.write(JsonUtils.objectToJson(ResultMessage.fail("no media")).getBytes());
            outputSream.flush();
        }else {
            response.setCharacterEncoding("UTF-8");
            OutputStream outputSream = response.getOutputStream();
            outputSream.write(data);
            outputSream.flush();
        }
    }

    /**
     * 大任务下子任务分文件夹打包
     * @param collectionTaskInfo
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("downloadMedia")
    public void downloadMedia(CollectionTaskInfo collectionTaskInfo,HttpServletResponse response) throws IOException {
        /*HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=media.zip");
        //下载tar图片资源文件
        String mediasId = collectionTaskInfoService.getAllMedisId(collectionTaskInfo.getId());
        byte[] data = uploadService.downloadZip(mediasId);
        return  new ResponseEntity<Object>(data, headers, HttpStatus.OK);*/

        //方法二
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=downloadZip.zip");
        //下载tar图片资源文件
//        String mediasId = collectionTaskInfoService.getAllMedisId(collectionTaskInfo.getId());
        LOG.info(">>>download param :{},taskInfoId:{}",collectionTaskInfo.getId(),collectionTaskInfo.getPhone());
        response.setCharacterEncoding("UTF-8");
        OutputStream outputSream = response.getOutputStream();
        List<CollectionTaskItemVo> taskItemList= collectionTaskItemService.getAllItemListByInfoId(collectionTaskInfo.getId());
        if(CollectionUtils.isEmpty(taskItemList)){
            outputSream.write(JsonUtils.objectToJson(ResultMessage.fail("no media")).getBytes());
            outputSream.flush();
        }else {
            byte[] data = uploadService.downloadMedia(JsonUtils.objectToJson(taskItemList), collectionTaskInfo.getPhone());
            if (data == null || data.length == 0) {
                outputSream.write(JsonUtils.objectToJson(ResultMessage.fail("no media")).getBytes());
                outputSream.flush();
            } else {
                outputSream.write(data);
                outputSream.flush();
            }
        }
    }

    /**
     * 查看资源文件
     * @param mediaId
     */
    @ResponseBody
    @RequestMapping(value="/{mediaId}",method= RequestMethod.GET)
    public void downloadMeidaById(@PathVariable("mediaId")Integer mediaId, HttpServletResponse response) throws IOException {
        byte[] data = uploadService.downMediaById(mediaId);
        response.setCharacterEncoding("UTF-8");
        OutputStream outputSream = response.getOutputStream();
        outputSream.write(data);
        outputSream.flush();
    }

    /**
     * 设置响应头 防止乱码
     *
     * @param response
     * @param fileName
     */
    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 注册后默认个用户分配个任务
     * @param phone
     * @return
     */
    @PostMapping("registerToAllocation")
    public ResultMessage registerToAllocation(@RequestParam("phone") String phone) {
        return collectionTaskInfoService.registerToAllocation(phone);
    }

    /**
     * 采集任务查询
     * @param regionId 区域ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return ResultMessage
     * @version <1> 2019-03-18 cxw： Created.
     */
    @ApiOperation(value = "采集任务查询", notes = "采集任务查询")
    @GetMapping("queryCollectDataList")
    public ResultMessage queryCollectDataList(Long regionId, String startDate, String endDate) {
        String phone = getCurrentAccount();
        return collectionTaskInfoService.findCollectDataList(phone,regionId, startDate, endDate);
    }

    @ApiOperation(value = "采集任务查询", notes = "采集任务查询")
    @GetMapping("queryCollectDatas")
    public ResultMessage queryCollectDatas(Long regionId, String startDate, String endDate) {
        return collectionTaskInfoService.findCollectDataList(null,regionId, startDate, endDate);
    }



    /**
     * 上传首页轮播图
     *
     * @param request
     * @return
     * @version <1> 2019-03-18 lijie： Created
     */
    @ApiOperation(value = "上传首页轮播图", notes = "上传首页轮播图")
    @PostMapping("updateIndexShow")
    public ResultMessage updateIndexShow(HttpServletRequest request) {
        return collectionTaskInfoService.updateIndexShow(request);
    }
}
