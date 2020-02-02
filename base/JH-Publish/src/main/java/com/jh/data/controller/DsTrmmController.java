package com.jh.data.controller;

import com.github.pagehelper.PageInfo;
import com.jh.base.controller.BaseController;
import com.jh.data.entity.DsTrmm;
import com.jh.data.model.DsTrmmParam;
import com.jh.data.model.TemTrmmTotalDataReturn;
import com.jh.data.model.TtnParam;
import com.jh.data.service.IDsTrmmService;
import com.jh.vo.ResultMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * description:降雨数据管理发布
 *
 * @version <1> 2018-06-08 wl: Created.
 */
@RestController
@RequestMapping("/dsTrmm")
public class DsTrmmController extends BaseController {

    @Autowired
    private IDsTrmmService dsTrmmService;

    /**
     * 降雨明细查询
     * @param request
     * @param dsTrmmParam
     * @return
     * @version <1> 2018-06-08 wl： Created.
     */
    @ApiOperation(value = "获取降雨明细列表", notes = "获取降雨明细列表")
    @ApiImplicitParam(name = "dsTrmmParam",value = "降雨明细查询参数",required = false, dataType = "DsTrmmParam")
    @PostMapping("/findByPage")
    public PageInfo<DsTrmm> findByPage(DsTrmmParam dsTrmmParam, HttpServletRequest request){
        PageInfo<DsTrmm> pages = dsTrmmService.findByPage(dsTrmmParam);
        return pages;
    }

    /**
     * 降雨明细修改
     * @param request
     * @param dsTrmmParam
     * @return
     * @version <1> 2018-06-08 wl： Created.
     */
    @ApiOperation(value = "降雨明细修改",notes = "修改降雨明细")
    @ApiImplicitParam(name = "dsTrmmParam",value = "降雨明细实体",required = true,dataType = "DsTrmmParam")
    @PostMapping("/edit")
    public ResultMessage edit(HttpServletRequest request, @RequestBody DsTrmmParam dsTrmmParam){
            dsTrmmParam.setModifierName(getCurrentNickName());
            dsTrmmParam.setModifier(getCurrentAccountId());
        return dsTrmmService.updateDsTrmm(dsTrmmParam);
    }


    /**
     * @description: 根据ID查询降雨详情
     * @param request
     * @param id
     * @return
     * @version <1> 2018-06-08 wl： Created.
     */
    @ApiOperation(value = "降雨明细查询",notes = "按降雨明细id查询")
    @ApiImplicitParam(name = "id",value = "降雨明细主键",required = true,paramType = "query", dataType = "Integer")
    @PostMapping("/findById")
    public ResultMessage findById(HttpServletRequest request, @RequestParam Integer id){
        return dsTrmmService.findById(id);
    }

    /**
     *删除数据集明细
     * @param id 订单Id
     * @return ResultMessage
     * @version <1> 2018-06-08 wl： Created.
     */
    @ApiOperation(value = "删除数据集明细",notes = "删除数据集明细")
    @ApiImplicitParam(name = "id",value = "订单Id",required = true,dataType = "Integer")
    @PostMapping("delete")
    public ResultMessage delete(@RequestParam Integer id){
        DsTrmmParam dsTrmmParam=new DsTrmmParam();
        dsTrmmParam.setId(id);
        dsTrmmParam.setDelFlag("0");
        return dsTrmmService.updateDsTrmm(dsTrmmParam);
    }

    /**
     * 降雨明细发布或撤销
     * @param request
     * @param dsTrmmParam
     * @return
     * @version <1> 2018-06-08 wl： Created.
     */
    @ApiOperation(value = "降雨明细发布或撤销",notes = "发布或撤销降雨明细")
    @ApiImplicitParam(name = "dsAreaParam",value = "降雨明细实体",required = true,dataType = "DsAreaParam")
    @PostMapping("/publish")
    public ResultMessage publish(HttpServletRequest request, @RequestBody DsTrmmParam dsTrmmParam){

        dsTrmmParam.setPublisher(getCurrentAccountId());
        dsTrmmParam.setPublisherName(getCurrentNickName());

        return dsTrmmService.publish(dsTrmmParam);
    }


    /**
     * 查询指定时间类上中下旬的雨量和地温的均值以及十年的均值
     * @param param
     * @return
     * @version <1> 2018-3-21 lxy:Created
     */
    @ApiOperation(value="查询指定时间内上中下旬的雨量总量以及十年的均值",notes="根据区域、日期段查询上中下旬的雨量总量以及十年的均值" )
    @PostMapping("/queryTrmmForTenDaysAndHistory")
    public PageInfo<TemTrmmTotalDataReturn>  queryTrmmForTenDaysAndHistory(TtnParam param){
        ResultMessage resultMessage = new ResultMessage();

        if(param != null){
            PageInfo<TemTrmmTotalDataReturn> result=dsTrmmService.findTrmmForTenDaysAndHistory(param);
            return result;
        }
        else{
            return new PageInfo<TemTrmmTotalDataReturn>(null);
        }

    }

    /**
     * 查询指定时间类上中下旬的雨量和地温的均值以及十年的均值
     * @param param
     * @return
     * @version <1> 2018-3-21 lxy:Created
     */
    @ApiOperation(value="查询指定时间内上中下旬的雨量总量以及十年的均值",notes="根据区域、日期段查询上中下旬的雨量总量以及十年的均值" )
    @PostMapping("/queryTrmmListForTenDaysAndHistory")
    public ResultMessage queryTrmmListForTenDaysAndHistory(@RequestBody TtnParam param){
        ResultMessage resultMessage = ResultMessage.success();
        if(param != null){
            List<TemTrmmTotalDataReturn> listRetun =  dsTrmmService.findTrmmListForTenDaysAndHistory(param);
            if(listRetun.size() == 0){
                resultMessage = ResultMessage.fail();
                resultMessage.setData(null);
            }else{
                resultMessage.setData(listRetun);
            }
        }
        else{
            resultMessage = ResultMessage.fail();
            resultMessage.setData(null);
        }
        return resultMessage;
    }

    /**
     * 查询指定时间类上中下旬的雨量的均值以及十年的均值
     * @param regionId 区域
     * @param regionName 区域名
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     * @version <1> 2018-06-29 cxw:Created
     */
    @ApiOperation(value = "导出查询数据",notes = "")
    @GetMapping( "/exportTrmmData")
    public ResultMessage exportTrmmData(HttpServletRequest request, HttpServletResponse response, Long regionId, String regionName, String startDate, String endDate) throws IOException, ParseException {
        return dsTrmmService.exportTrmmData(request,response,regionId,regionName,startDate,endDate);
    }

    /**
     * 查询指定时间类上中下旬的雨量的均值以及十年的均值
     * @param regionId 区域
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     * @version <1> 2018-06-29 cxw:Created
     */
    @ApiOperation(value = "导出查询数据判断",notes = "")
    @GetMapping(value = "/isExistTrmmData")
    public ResultMessage isExistTrmmData( Long regionId, String startDate,String endDate) throws IOException {
        return dsTrmmService.isExistTrmmData(regionId,startDate,endDate);
    }

}
