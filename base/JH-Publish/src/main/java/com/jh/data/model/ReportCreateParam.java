package com.jh.data.model;


import com.jh.entity.PageEntity;
import com.jh.vo.ResultMessage;

/**
 * Description:
 * 1.报告生成参数
 *
 * @version <1> 2018-05-11 11:16 zhangshen: Created.
 */
public class ReportCreateParam extends PageEntity {

    private Long regionId;//区域id

    private String regionCode;//区域代码

    private Integer cropId;//作物id

    private String cropCode;//作物代码

    private Integer resolution;//分辨率

    private String dataTime;//数据时间

    private String startTime;//数据时间 开始时间

    private String endTime;//数据时间 结束时间

    private Integer dataSet;//数据集

    private String dataSetList;//数据集List

    private Integer creator;//创建人

    private String creatorName;// 创建人名称

    private String keyword;//关键字

    private String reportTempType;//报告模板的类型

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public String getCropCode() {
        return cropCode;
    }

    public void setCropCode(String cropCode) {
        this.cropCode = cropCode;
    }

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getDataSet() {
        return dataSet;
    }

    public void setDataSet(Integer dataSet) {
        this.dataSet = dataSet;
    }

    public String getDataSetList() {
        return dataSetList;
    }

    public void setDataSetList(String dataSetList) {
        this.dataSetList = dataSetList;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getReportTempType() { return reportTempType; }

    public void setReportTempType(String reportTempType) { this.reportTempType = reportTempType; }

    /**
     * Description: 检查创建报告所需的参数是否为空
     * @param
     * @return 
     * @version <1> 2018/5/14 9:42 zhangshen: Created.
     */
    public ResultMessage checkDistributionParam(){
        ResultMessage result = ResultMessage.success();

        if(this.endTime == null){
            result = ResultMessage.fail();
            result.setMsg("查询数据结束时间不能为空.");
        }

        if(this.startTime == null){
            result = ResultMessage.fail();
            result.setMsg("查询数据开始时间不能为空.");
        }

        if(this.resolution == null){
            result = ResultMessage.fail();
            result.setMsg("分辨率不能为空.");
        }

        if(this.dataSet==null){
            result = ResultMessage.fail();
            result.setMsg("数据集不能为空.");
        }

        if(this.dataSet==1801 || this.dataSet==1802 || this.dataSet==1803) {
            if (this.cropId == null) {
                result = ResultMessage.fail();
                result.setMsg("作物不能为空.");
            }
        }

        if(this.regionId==null){
            result = ResultMessage.fail();
            result.setMsg("区域不能为空.");
        }

        if(this.keyword==null){
            result = ResultMessage.fail();
            result.setMsg("关键字不能为空.");
        }

        if (this.reportTempType == null){
            result = ResultMessage.fail();
            result.setMsg("报告模板类型不能为空");
        }

        return result;
    }
}
