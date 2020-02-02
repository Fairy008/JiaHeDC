package com.jh.forum.bbs.controller;

import com.github.pagehelper.PageInfo;
import com.jh.forum.base.controller.BaseController;
import com.jh.forum.bbs.entity.ForumDownloadData;
import com.jh.forum.bbs.service.IForumDownloadDataService;
import com.jh.forum.bbs.vo.DownloadDataVo;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @Description:可下载数据控制器
 *
 * @version<1> 2019-07-01 lcw :Created.
 */
@RestController
@RequestMapping("/downloadData")
public class ForumDownloadDataController extends BaseController {



    @Autowired
    private IForumDownloadDataService downloadDataService;

    @PostMapping("/findCmsPage")
    public PageInfo<DownloadDataVo> findCmsPage(DownloadDataVo downloadDataVo){
        PageInfo<DownloadDataVo> pageInfo = downloadDataService.findByPage(downloadDataVo);
        return pageInfo;
    }

   /*
    * 功能描述:查询所有数据下载接口
    * @Param:
    * @Return:
    * @version<1>  2019/8/26  wangli :Created
    */
    @PostMapping("/findAllByPage")
    public PageInfo<DownloadDataVo> findAllByPage(DownloadDataVo downloadDataVo){
        if(downloadDataVo.getTagType() !=null &&  downloadDataVo.getTagType()!=0){
            downloadDataVo.setKeyWords("");
        }
        PageInfo<DownloadDataVo> pageInfo = downloadDataService.findAllByPage(downloadDataVo);
        return pageInfo;
    }

    /*
     * 功能描述: 查询数据下载信息接口
     * @Param:
     * @Return:
     * @version<1>  2019/8/26  wangli :Created
     */

    @PostMapping("/findAgriculturalData")
    public ResultMessage findAgriculturalData(DownloadDataVo downloadDataVo){
        if(downloadDataVo.getTagType() !=null &&  downloadDataVo.getTagType()!=0){
            downloadDataVo.setKeyWords("");
        }
        return downloadDataService.findAgriculturalData(downloadDataVo);
    }


    /**
     * 新增专家
     * @param downloadDataVo
     * @return ResultMessage
     * @version <1> 2019/3/7 mason:Created.
     */
    @ApiOperation(value = "新增下载数据",notes = "新增下载数据")
    @ApiImplicitParam(name = "downloadDataVo",value = "下载数据",required = true,dataType = "DownloadDataVo")
    @PostMapping("/saveDownloadData")
    public ResultMessage saveDownloadData(@RequestBody DownloadDataVo downloadDataVo){
        downloadDataVo.setCreator(getCurrentAccountId());
        downloadDataVo.setCreatorName(getCurrentNickName());
        downloadDataVo.setCreateTime(LocalDateTime.now());
        downloadDataVo.setModifier(getCurrentAccountId());
        downloadDataVo.setModifierName(getCurrentNickName());
        return downloadDataService.saveDownLoadData(downloadDataVo);
    }

    @ApiOperation(value = "后台查询下载数据",notes = "后台查询下载数据")
    @ApiImplicitParam(name = "dataId",value = "数据id",required = true, paramType="query", dataType = "Integer")
    @GetMapping("/findDownloadData")
    public ResultMessage findOrderCms(@RequestParam Integer dataId){
        return downloadDataService.getDataById(dataId);
    }

    @ApiOperation(value = "后台删除下载数据",notes = "后台删除下载数据")
    @ApiImplicitParam(name = "dataId",value = "数据id",required = true, paramType="query", dataType = "Integer")
    @PostMapping("/delDownloadData")
    public ResultMessage delDownloadData(@RequestParam Integer dataId){
        DownloadDataVo downloadDataVo =  new DownloadDataVo();
        downloadDataVo.setDataId(dataId);
        downloadDataVo.setDelFlag("0");
        return downloadDataService.updateData(downloadDataVo);
    }


    /**
     * 审核
     * @param downloadDataVo
     * @return
     */
    @PostMapping("/audit")
    public ResultMessage audit(@RequestBody DownloadDataVo downloadDataVo){
        downloadDataVo.setModifier(getCurrentAccountId());
        downloadDataVo.setModifierName(getCurrentNickName());
        downloadDataVo.setModifyTime(LocalDateTime.now());
        ResultMessage  resultMessage = downloadDataService.audit(downloadDataVo);
        return resultMessage;
    }

    @ApiOperation(value = "确认订单",notes = "确认订单")
    @ApiImplicitParam(name = "downloadDataVo",value = "数据id列表",required = true, paramType="query", dataType = "DownloadDataVo")
    @PostMapping("/confirmationOfOrder")
    public ResultMessage confirmationOfOrder(@RequestBody DownloadDataVo downloadDataVo){
        return downloadDataService.confirmationOfOrder(downloadDataVo);
    }


    @ApiOperation(value = "下载样例",notes = "下载样例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataId",value = "数据ID",required = true, dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "登录ID", required = true, dataType = "Integer", paramType = "query")
    })
    @GetMapping("/downloadExample")
    public ResultMessage downloadExample(@RequestParam int dataId,@RequestParam int userId){
        DownloadDataVo downloadDataVo =  new DownloadDataVo();
        downloadDataVo.setDataId(dataId);
        downloadDataVo.setCreator(userId);
        return downloadDataService.downloadExample(downloadDataVo);
    }



    @ApiOperation(value = "免费下载样例数据",notes = "免费下载样例数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataId",value = "数据ID",required = true, dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "登录ID", required = true, dataType = "Integer", paramType = "query")
    })
    @GetMapping("/freeDownloadData")
    public ResultMessage freeDownloadData(@RequestParam int dataId,@RequestParam int userId){
        DownloadDataVo downloadDataVo =  new DownloadDataVo();
        downloadDataVo.setDataId(dataId);
        downloadDataVo.setCreator(userId);
        //下载表单计数加1
        return downloadDataService.freeDownloadData(downloadDataVo);
    }

    //文件下载相关代码
    @GetMapping(value = "/downloadImage")
    public ResultMessage downloadImage(@RequestParam int dataId,@RequestParam int userId) {
        //taskId
        ResultMessage resultMessage = downloadDataService.getDataById(dataId);
        ForumDownloadData downloadData = (ForumDownloadData)resultMessage.getData();
        //拼接完整目录地址
        String fileUrl = CephUtils.getAbsolutePath(downloadData.getDataPath());

        return ResultMessage.success(fileUrl);
    }

    /*
     * 功能描述:自动计算并设置 降雨  地温  气温  干旱指数 病虫害监测
     * @Param:
     * @Return:
     * @version<1>  2019/10/22  wangli :Created
     */
    @ApiOperation(value = "自动计算并设置降雨/地温/气温/干旱指数/病虫害监测价格",notes = "自动计算并设置降雨/地温/气温/干旱指数/病虫害监测价格")
    @PostMapping("/calculate")
    public ResultMessage calculate(){
        ResultMessage resultMessage = downloadDataService.calculate();
        return resultMessage;
    }



}
