package com.googlecode.coss.biz.base.po;

import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import com.googlecode.coss.common.core.orm.mybatis.BaseDataObject;

/* Persistent objects */
public class Contact extends BaseDataObject implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;

	
	private java.lang.Long contactId;
	@NotBlank @Length(max=32)
	private java.lang.String contactName;
	@Length(max=1)
	private java.lang.String gender;
	@Length(max=32)
	private java.lang.String position;
	@NotBlank @Length(max=32)
	private java.lang.String mobile;
	@Length(max=32)
	private java.lang.String workPhone;
	@NotBlank @Email @Length(max=32)
	private java.lang.String email;
	@Length(max=32)
	private java.lang.String homePhone;
	@Length(max=16)
	private java.lang.String qq;
	@Length(max=64)
	private java.lang.String msn;
	@Length(max=128)
	private java.lang.String other;
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

	public void setContactId(java.lang.Long value) {
		this.contactId = value;
	}
	
	public java.lang.Long getContactId() {
		return this.contactId;
	}
	public void setContactName(java.lang.String value) {
		this.contactName = value;
	}
	
	public java.lang.String getContactName() {
		return this.contactName;
	}
	public void setGender(java.lang.String value) {
		this.gender = value;
	}
	
	public java.lang.String getGender() {
		return this.gender;
	}
	public void setPosition(java.lang.String value) {
		this.position = value;
	}
	
	public java.lang.String getPosition() {
		return this.position;
	}
	public void setMobile(java.lang.String value) {
		this.mobile = value;
	}
	
	public java.lang.String getMobile() {
		return this.mobile;
	}
	public void setWorkPhone(java.lang.String value) {
		this.workPhone = value;
	}
	
	public java.lang.String getWorkPhone() {
		return this.workPhone;
	}
	public void setEmail(java.lang.String value) {
		this.email = value;
	}
	
	public java.lang.String getEmail() {
		return this.email;
	}
	public void setHomePhone(java.lang.String value) {
		this.homePhone = value;
	}
	
	public java.lang.String getHomePhone() {
		return this.homePhone;
	}
	public void setQq(java.lang.String value) {
		this.qq = value;
	}
	
	public java.lang.String getQq() {
		return this.qq;
	}
	public void setMsn(java.lang.String value) {
		this.msn = value;
	}
	
	public java.lang.String getMsn() {
		return this.msn;
	}
	public void setOther(java.lang.String value) {
		this.other = value;
	}
	
	public java.lang.String getOther() {
		return this.other;
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

