package com.jh.forum.cms.controller;

import com.github.pagehelper.PageInfo;
import com.jh.biz.feign.IPersonService;
import com.jh.cltApp.entity.CltTaskInfo;
import com.jh.cltApp.entity.CltTaskItem;
import com.jh.cltApp.entity.CltTaskUser;
import com.jh.cltApp.entity.CltTemplate;
import com.jh.cltApp.service.ICltTaskInfoService;
import com.jh.cltApp.service.ICltTaskItemService;
import com.jh.cltApp.service.ICltTaskUserService;
import com.jh.cltApp.service.ICltTemplateService;
import com.jh.cltApp.vo.*;
import com.jh.collection.entity.TaskItemFeild;
import com.jh.collection.entity.vo.CollectionTaskItemVo;
import com.jh.collection.entity.vo.CollectionTaskVo;
import com.jh.collection.utils.BeanToXmlUtils;
import com.jh.collection.utils.CommonExcelUtil;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.Enum.FollowTypeEnum;
import com.jh.forum.bbs.entity.ForumFollow;
import com.jh.forum.bbs.service.IForumFollowService;
import com.jh.util.DateUtil;
import com.jh.util.JsonUtils;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.apache.coyote.http11.Constants.a;

/**
 * 采集器任务后台管理controller
 * @version <1> 2019/4/15 16:37 lijie:Created.
 */
@Api(value = "采集器任务后台管理",description = "采集器任务后台管理")
@RestController
@RequestMapping("/cltTaskInfoManage")
public class CltTaskInfoManageController extends BaseController {

    @Autowired
    private ICltTaskInfoService cltTaskInfoService;
    @Autowired
    private IPersonService personService;
    @Autowired
    private ICltTaskUserService cltTaskUserService;
    @Autowired
    private IForumFollowService forumFollowService;
    @Autowired
    private ICltTaskItemService cltTaskItemService;
    @Autowired
    private ICltTemplateService cltTemplateService;

    /**
     * 查询任务列表
     * @param
     * @return
     * @version <1> 2019/4/9 11:31 lijie:Created.
     */
    @ApiOperation(value = "查询任务列表",notes = "查询任务列表")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/findPage")
    public PageInfo<CltTaskInfo> findPage(CltTaskInfo cltTaskInfo){
        cltTaskInfo.setCreator(getQueryCreator());
        return cltTaskInfoService.findCltTaskInfoPageInfoCms(cltTaskInfo);
    }

    /**
     * 新建任务
     * @param 
     * @return 
     * @version <1> 2019/4/10 16:29 lijie:Created.
     */
    @ApiOperation(value = "新建任务",notes = "新建任务")
    @ApiImplicitParam(name = "cltTaskInfoParamsVO",value = "新建任务实体",required = true,dataType = "CltTaskInfoParamsVO")
    @PostMapping("/createTaskInfo")
    public ResultMessage createTaskInfo(@RequestBody CltTaskInfoParamsVO cltTaskInfoParamsVO){
        cltTaskInfoParamsVO.setCreator(getCurrentAccountId());
        cltTaskInfoParamsVO.setCreatorName(getCurrentNickName());
        cltTaskInfoParamsVO.setModifier(getCurrentAccountId());
        cltTaskInfoParamsVO.setModifierName(getCurrentNickName());
        Integer[] arr = cltTaskInfoParamsVO.getUserArr();
        if(arr == null){
            cltTaskInfoParamsVO.setUserArr(new Integer []{getCurrentAccountId()});
        }
        return cltTaskInfoService.createTaskInfo(cltTaskInfoParamsVO);
    }

    /**
     * 修改任务
     * @param
     * @return
     * @version <1> 2019/4/10 16:29 lijie:Created.
     */
    @ApiOperation(value = "修改任务",notes = "修改任务")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/updateTaskInfoByTaskId")
    public ResultMessage updateTaskInfoByTaskId(@RequestBody CltTaskInfoParamsVO cltTaskInfoParamsVO){
        cltTaskInfoParamsVO.setModifier(getCurrentAccountId());
        cltTaskInfoParamsVO.setModifierName(getCurrentNickName());
        Integer[] arr = cltTaskInfoParamsVO.getUserArr();
        if(arr == null){
            cltTaskInfoParamsVO.setUserArr(new Integer []{getCurrentAccountId()});
        }
        return cltTaskInfoService.updateTaskInfoByTaskId(cltTaskInfoParamsVO);
    }

    /**
     * 删除任务
     * @param
     * @return
     * @version <1> 2019/4/10 16:29 lijie:Created.
     */
    @ApiOperation(value = "根据任务id删除任务",notes = "根据任务id删除任务")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/deleteTaskInfoByTaskId")
    public ResultMessage deleteTaskInfoByTaskId(@RequestBody CltTaskInfo cltTaskInfo){
        return cltTaskInfoService.deleteTaskInfoByTaskId(cltTaskInfo.getTaskId());
    }

    /**
     * 批量删除任务
     * @param
     * @return
     * @version <1> 2019/4/10 16:29 lijie:Created.
     */
    @ApiOperation(value = "批量删除任务",notes = "批量删除删除任务")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/deleteTaskInfoByTaskIds")
    public ResultMessage deleteTaskInfoByTaskIds(@RequestBody CltTaskInfo cltTaskInfo){
        return cltTaskInfoService.deleteTaskInfoByTaskIds(cltTaskInfo.getTaskIds());
    }

    /**
     * 修改任务状态
     * @param
     * @return
     * @version <1> 2019/4/10 16:29 lijie:Created.
     */
    @ApiOperation(value = "修改任务状态",notes = "修改任务状态")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/updateTaskStatusByTaskId")
    public ResultMessage updateTaskStatusByTaskId(@RequestBody CltTaskInfo cltTaskInfo){
        cltTaskInfo.setModifier(getCurrentAccountId());
        cltTaskInfo.setModifierName(getCurrentNickName());
        return cltTaskInfoService.updateTaskStatusByTaskId(cltTaskInfo);
    }

    /**
     * 我分享的任务列表
     * @param 
     * @return 
     * @version <1> 2019/4/10 16:48 lijie:Created.
     */
    @ApiOperation(value = "我分享的任务列表",notes = "我分享的任务列表")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/findShareCltTaskInfoPageInfo")
    public PageInfo<CltTaskInfo> findShareCltTaskInfoPageInfo(@RequestBody CltTaskInfo cltTaskInfo){
        cltTaskInfo.setCreator(getCurrentAccountId());
        cltTaskInfo.setIsPublish(getCurrentAccountId());
        return cltTaskInfoService.findShareCltTaskInfoPageInfo(cltTaskInfo);
    }

    /**
     * 我参与的任务列表
     * @param
     * @return 
     * @version <1> 2019/4/10 16:49 lijie:Created.
     */
    @ApiOperation(value = "我参与的任务列表",notes = "我参与的任务列表")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/findJoinCltTaskInfoPageInfo")
    public PageInfo<CltTaskInfo> findJoinCltTaskInfoPageInfo(@RequestBody CltTaskInfo cltTaskInfo){
        cltTaskInfo.setCreator(getCurrentAccountId());
        return  cltTaskInfoService.findJoinCltTaskInfoPageInfo(cltTaskInfo);
    }

    /**
     * 根据taskId查找任务详情
     * @param
     * @return 
     * @version <1> 2019/4/15 16:32 lijie:Created.
     */
    @ApiOperation(value = "根据taskId查找任务详情",notes = "根据taskId查找任务详情")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/getCltTaskInfoByTaskId")
    public ResultMessage getCltTaskInfoByTaskId(@RequestBody CltTaskInfo cltTaskInfo) {
        return cltTaskInfoService.getCltTaskInfoByTaskId(cltTaskInfo);
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
        return cltTaskInfoService.updateIndexShow(request);
    }

    /**
     * 查询任务参与人(不带分页)
     * @param
     * @return
     * @version <1> 2019/4/10 10:55 lijie:Created.
     */
    @ApiOperation(value = "查询任务参与人(不带分页)",notes = "查询任务参与人(不带分页)")
    @ApiImplicitParam(name = "cltTaskUser",value = "参与人实体",required = true,dataType = "CltTaskUser")
    @PostMapping("/findCltTaskUserList")
    public ResultMessage findCltTaskUserList(@RequestBody CltTaskUser cltTaskUser){
        cltTaskUser.setCreator(getCurrentAccountId());
        return cltTaskUserService.findCltTaskUserList(cltTaskUser);
    }

    /**
     * 查询我的关注列表(不带分页)
     * @param
     * @return
     * @version <1> 2019/4/19 10:36 zhangshen:Created.
     */
    @ApiOperation(value = "查询我的关注列表(不带分页)",notes = "查询我的关注列表(不带分页)")
    @ApiImplicitParam(name = "forumFollow",value = "关注实体",required = true,dataType = "ForumFollow")
    @PostMapping("/findMyFollowList")
    public ResultMessage findMyFollowList(@RequestBody ForumFollow forumFollow){
        if (forumFollow.getCreator() == null) {
            forumFollow.setCreator(getCurrentAccountId());
        }
        forumFollow.setFollowType(FollowTypeEnum.FOLLOW_TYPE_FOCUS.getId());
        return forumFollowService.findMyFollowList(forumFollow);
    }

    /**
     * 修改任务状态
     * @param
     * @return
     * @version <1> 2019/4/10 16:29 lijie:Created.
     */
    @ApiOperation(value = "批量修改任务状态",notes = "批量修改任务状态")
    @ApiImplicitParam(name = "cltTaskInfo",value = "任务实体",required = true,dataType = "CltTaskInfo")
    @PostMapping("/auditTaskList")
    public ResultMessage auditTaskList(@RequestBody CltTaskInfo cltTaskInfo){
        cltTaskInfo.setModifier(getCurrentAccountId());
        cltTaskInfo.setModifierName(getCurrentNickName());
        cltTaskInfo.setModifier(getCurrentAccountId());
        cltTaskInfo.setModifierName(getCurrentNickName());
        cltTaskInfo.setModifyTime(LocalDateTime.now());
        cltTaskInfo.setAuditTime(LocalDateTime.now());
        cltTaskInfo.setAuditor(getCurrentAccountId());
        cltTaskInfo.setPublishTime(LocalDateTime.now());
        return cltTaskInfoService.auditTaskList(cltTaskInfo);
    }

    /**
     * 查询首页轮播图数
     *
     * @param request
     * @return
     * @version <1> 2019-03-18 lijie： Created
     */
    @ApiOperation(value = "上传首页轮播图", notes = "上传首页轮播图")
    @PostMapping("getIndexShowNum")
    public ResultMessage getIndexShowNum() {
        CltTaskInfo cltTaskInfo = new CltTaskInfo();
        cltTaskInfo.setIndexShow(1);
        PageInfo<CltTaskInfo> info = cltTaskInfoService.findCltTaskInfoPageInfoCms(cltTaskInfo);
        return ResultMessage.success(info.getTotal());
    }

    private Integer getQueryCreator() {
        Integer creator = getCurrentAccountId();
        ResultMessage result = personService.isExistRole(creator, "FORUM_ADMIN");
        Integer flag = (Integer) result.getData();
        if (flag > 0) creator = null;//是管理员则不加创建人条件
        return creator ;
    }

    /**
     * 导出excel
     *
     * @return
     */
    @GetMapping("export")
    public ResultMessage export(CltTaskInfo cltTaskInfo, HttpServletResponse response) throws Exception {
        try {
            if (cltTaskInfo == null || cltTaskInfo.getTaskId() == null) {
                return ResultMessage.fail("未找到此任务");
            }
            ResultMessage result = cltTaskInfoService.getCltTaskInfoByTaskId(cltTaskInfo);
            if(!result.isFlag()){
                return ResultMessage.fail(result.getMsg());
            }
            CltTaskInfoVO cltTaskInfoVo = (CltTaskInfoVO)result.getData();
            CltTaskItem item =new CltTaskItem();
            item.setTaskId(cltTaskInfo.getTaskId() );
            ResultMessage itemResult = cltTaskItemService.findAllTaskItemList(item);
            if(!itemResult.isFlag()){
                return ResultMessage.fail(itemResult.getMsg());
            }
            List<CltTaskItemVO> cltTaskItemList = (List<CltTaskItemVO>)itemResult.getData();
            String sheetName = cltTaskInfoVo.getTaskName();
            //遍历采集数据集合
            //创建一个二维数组显示表格内容
            String[][] data = new String[cltTaskItemList.size()][];
            //必填标题
            String[] requriedTitle = {"任务名称","调研区域","调研作物","调查地址","调查时间","坐标信息"};
            ResultMessage tempResult =cltTemplateService.getById(cltTaskInfoVo.getTemplateId());
            if(!tempResult.isFlag()){
                return ResultMessage.fail(tempResult.getMsg());
            }
            CltTemplate tem = (CltTemplate)tempResult.getData();
            TemplateVO tmv = tem.getTemplateVO();
            List<FieldModelVO>  list = null;
            if(tmv != null){
                list = tmv.getFieldModelVOList();
            }
            String [] title =new String [list.size()+requriedTitle.length];
            for(int i = 0;i<requriedTitle.length;i++){
                title[i]=requriedTitle[i];
            }
            for(int i = 0;i<list.size();i++){
                title[i+requriedTitle.length]=list.get(i).getFieldNameCh();
            }
            int index = 0;
            for (CltTaskItemVO vo : cltTaskItemList) {
                CltTaskItem itemParam =new CltTaskItem();
                itemParam.setItemId(vo.getItemId());
                ResultMessage itemData = cltTaskItemService.findTaskItemByItemId(itemParam);
                if(itemData == null){
                    return ResultMessage.fail(itemData.getMsg());
                }
                CltTaskItemVO ivo =(CltTaskItemVO) itemData.getData();
                TemplateVO tv =ivo.getTemplateVO();
                if(tv ==null){
                    continue;
                }
                List<FieldModelVO> taskItemFeildList =tv.getFieldModelVOList();
                String[] itemInfo = new String[taskItemFeildList.size()+6];
                itemInfo[0] = sheetName;
                itemInfo[1] = cltTaskInfoVo.getRegionName();
                itemInfo[2] = cltTaskInfoVo.getCropName();
                itemInfo[3] = vo.getSurveyAddress();
                itemInfo[4] = DateUtil.dateToString(DateUtil.localDateTimeToDate(vo.getSurveyTime()),"yyyy-MM -dd HH:mm:ss");
                itemInfo[5] = vo.getPosition();
                for (int i = 0; i < taskItemFeildList.size(); i++) {
                    itemInfo[i+6] = taskItemFeildList.get(i).getValue();
                }
                data[index] = itemInfo;
                index++;
            }

            String fileName = sheetName + ".xls";//任务名作为excel文件名
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
     * 大任务下子任务分文件夹打包
     * @param collectionTaskInfo
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("downloadMedia")
    public ResultMessage downloadMedia(CltTaskInfo cltTaskInfo,HttpServletResponse response,HttpServletRequest request) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=downloadZip.zip");
        response.setCharacterEncoding("UTF-8");
        OutputStream outputSream = response.getOutputStream();
        CltTaskItem item =new CltTaskItem();
        item.setTaskId(cltTaskInfo.getTaskId() );
        ResultMessage itemResult = cltTaskItemService.findAllTaskItemList(item);
        if(!itemResult.isFlag()){
            return ResultMessage.fail(itemResult.getMsg());
        }
        List<CltTaskItemVO> cltTaskItemList = (List<CltTaskItemVO>)itemResult.getData();
        if(CollectionUtils.isEmpty(cltTaskItemList)){
            outputSream.write(JsonUtils.objectToJson(ResultMessage.fail("no media")).getBytes());
            outputSream.flush();
        }else {
            byte[] data = cltTaskInfoService.downloadMediaByTaskInfoId(request,cltTaskItemList);
            if (data == null || data.length == 0) {
                outputSream.write(JsonUtils.objectToJson(ResultMessage.fail("no media")).getBytes());
                outputSream.flush();
            } else {
                outputSream.write(data);
                outputSream.flush();
            }
        }
        return null;
    }

}
