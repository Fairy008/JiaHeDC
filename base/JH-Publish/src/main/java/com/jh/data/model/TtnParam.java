package com.jh.data.model;

import com.jh.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@ApiModel(value = "Ttn", description = "地温、降雨条件查询对象")
public class TtnParam extends PageEntity {
	@ApiModelProperty(value="区域ID")
	private Long regionId;

	@ApiModelProperty(value="查询开始日期")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate startDate;

	@ApiModelProperty(value="查询结束日期")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate endDate;

	@ApiModelProperty(value="导出标记")
	private int exportFlag;

	public void setRegionId(Long regionId){ this.regionId = regionId;}
	public Long getRegionId(){return this.regionId;}

	public void setStartDate(LocalDate startDate){this.startDate = startDate;}
	public LocalDate getStartDate(){return this.startDate;}

	public void setEndDate(LocalDate endDate){this.endDate = endDate;}
	public LocalDate getEndDate(){return this.endDate;}

	public String toString(){
		return regionId.toString() +"_"+ startDate.toString() +"_"+ endDate.toString();
	}

	public int getExportFlag() {
		return exportFlag;
	}

	public void setExportFlag(int exportFlag) {
		this.exportFlag = exportFlag;
	}

	public int valid(){
		int flagCode = 0;
		if(regionId==null){
			flagCode = 1;
			return flagCode;
		}

		if(startDate==null){
			flagCode = 2;
			return flagCode;
		}

		if(endDate==null){
			flagCode = 3;
			return flagCode;
		}

		if(startDate.isAfter(endDate)){
			flagCode = 4;
			return flagCode;
		}

		return flagCode;

	}


}