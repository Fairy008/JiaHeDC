package com.jh.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * Description:
 * 1.
 *
 * @version <1> 2018-05-10 15:33 zhangshen: Created.
 */
public class DatasetReturn {
    private Long regionId;

    private String regionName;

    private Float value;

    private Float lastValue;

    private Float percent;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;


    private String monthAndDay;  //月日作为x轴

    private Integer year;

    private String cropName;

    public void setRegionId(Long regionId){this.regionId = regionId;}
    public Long getRegionId(){return this.regionId;}

    public void setRegionName(String regionName){this.regionName = regionName;}
    public String getRegionName() { return this.regionName;}

    public void setValue(Float value){this.value = value;}
    public Float getValue(){return this.value;}

    public void setLastValue(Float lastValue){this.lastValue = lastValue;}
    public Float getLastValue(){return this.lastValue;}

    public void setPercent(Float percent){this.percent = percent;}
    public Float getPercent(){return this.percent;}

    public void setDate(LocalDate date){this.date = date;}
    public LocalDate getDate(){return this.date;}

    public void setYear(Integer year){this.year = year;}
    public Integer getYear(){return this.year;}

    public void setCropName(String cropName){this.cropName = cropName;}
    public String getCropName(){return this.cropName;}

    public String getMonthAndDay() {
        return monthAndDay;
    }

    public void setMonthAndDay(String monthAndDay) {
        this.monthAndDay = monthAndDay;
    }

    /**
     * 在列表中找区域相同的对象
     * @version <1> 2017-11-21 Hayden:Created.
     */
    public DatasetReturn findEqualRegion(List<DatasetReturn> dataList){
        for(DatasetReturn ds : dataList){
            if(this.getRegionId().equals(ds.getRegionId())){
                return ds;
            }
        }
        return null;
    }

    /**
     * 在列表中根据区域、时间找相同的对象
     * @version <1> 2017-11-30 XuZhenguo:Created.
     */
    public DatasetReturn isEqualRegion(List<DatasetReturn> dataList){
        for(DatasetReturn ds : dataList){
            if(this.getRegionId().equals(ds.getRegionId()) && this.getDate().plusMonths(-1).equals(ds.getDate()) ){
                return ds;
            }
        }
        return null;
    }
}
