package com.jh.show.agric.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Table(name = "init_dict")
public class DictEntity {
	@Id
	@Column(name = "dict_id")
	// @GeneratedValue(generator = "JDBC")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dictId;

	@Column(name = "data_name")
	private String dataName;

	@Column(name = "data_code")
	private String dataCode;

	@Column(name = "data_value")
	private String dataValue;

	@Column(name = "order_no")
	private Integer orderNo;

	public Integer getDictId(){return this.dictId;}
	public void setDictId(Integer dictId){this.dictId = dictId;}

	public String getDataName(){return this.dataName;}
	public void setDataName(String dataName){this.dataName = dataName;}

	public String getDataCode(){return this.dataCode;}
	public void setDataCode(String dataCode){this.dataCode = dataCode;}

	public String getDataValue(){return this.dataValue;}
	public void setDataValue(String dataValue){this.dataValue = dataValue;}

	public Integer getOrderNo(){return this.orderNo;}
	public void setOrderNo(Integer orderNo){this.orderNo = orderNo;}
}