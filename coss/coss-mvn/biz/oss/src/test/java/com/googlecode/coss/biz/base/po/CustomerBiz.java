package com.googlecode.coss.biz.base.po;

import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import com.googlecode.coss.common.core.orm.mybatis.BaseDataObject;

/* Persistent objects */
public class CustomerBiz extends BaseDataObject implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;

	
	private java.lang.Long bizId;
	@NotNull 
	private java.lang.Long cusId;
	@NotNull 
	private java.lang.Long consultant;
	@NotNull 
	private java.lang.Long cs;
	@Length(max=1)
	private java.lang.String cooperation;
	@Length(max=1)
	private java.lang.String cusLevel;
	@Length(max=1)
	private java.lang.String gmcLevel;
	@Length(max=256)
	private java.lang.String receiptAddress;
	@Length(max=256)
	private java.lang.String backupAddress1;
	@Length(max=256)
	private java.lang.String backupAddress2;
	
	private java.lang.Integer shippingArea;
	@NotBlank @Length(max=1)
	private java.lang.String status;
	@NotNull 
	private java.lang.Long createBy;
	@NotNull 
	private java.util.Date createTime;
	@NotNull 
	private java.lang.Long updateBy;
	@NotNull 
	private java.util.Date updateTime;

	public void setBizId(java.lang.Long value) {
		this.bizId = value;
	}
	
	public java.lang.Long getBizId() {
		return this.bizId;
	}
	public void setCusId(java.lang.Long value) {
		this.cusId = value;
	}
	
	public java.lang.Long getCusId() {
		return this.cusId;
	}
	public void setConsultant(java.lang.Long value) {
		this.consultant = value;
	}
	
	public java.lang.Long getConsultant() {
		return this.consultant;
	}
	public void setCs(java.lang.Long value) {
		this.cs = value;
	}
	
	public java.lang.Long getCs() {
		return this.cs;
	}
	public void setCooperation(java.lang.String value) {
		this.cooperation = value;
	}
	
	public java.lang.String getCooperation() {
		return this.cooperation;
	}
	public void setCusLevel(java.lang.String value) {
		this.cusLevel = value;
	}
	
	public java.lang.String getCusLevel() {
		return this.cusLevel;
	}
	public void setGmcLevel(java.lang.String value) {
		this.gmcLevel = value;
	}
	
	public java.lang.String getGmcLevel() {
		return this.gmcLevel;
	}
	public void setReceiptAddress(java.lang.String value) {
		this.receiptAddress = value;
	}
	
	public java.lang.String getReceiptAddress() {
		return this.receiptAddress;
	}
	public void setBackupAddress1(java.lang.String value) {
		this.backupAddress1 = value;
	}
	
	public java.lang.String getBackupAddress1() {
		return this.backupAddress1;
	}
	public void setBackupAddress2(java.lang.String value) {
		this.backupAddress2 = value;
	}
	
	public java.lang.String getBackupAddress2() {
		return this.backupAddress2;
	}
	public void setShippingArea(java.lang.Integer value) {
		this.shippingArea = value;
	}
	
	public java.lang.Integer getShippingArea() {
		return this.shippingArea;
	}
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	public java.lang.String getStatus() {
		return this.status;
	}
	public void setCreateBy(java.lang.Long value) {
		this.createBy = value;
	}
	
	public java.lang.Long getCreateBy() {
		return this.createBy;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdateBy(java.lang.Long value) {
		this.updateBy = value;
	}
	
	public java.lang.Long getUpdateBy() {
		return this.updateBy;
	}
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

}

