package com.googlecode.coss.biz.base.po;

import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import com.googlecode.coss.common.core.orm.mybatis.BaseDataObject;

/* Persistent objects */
public class CusContact extends BaseDataObject implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;

	
	private java.lang.Long cusContactId;
	@NotNull 
	private java.lang.Long cusId;
	@NotNull 
	private java.lang.Long contactId;

	public void setCusContactId(java.lang.Long value) {
		this.cusContactId = value;
	}
	
	public java.lang.Long getCusContactId() {
		return this.cusContactId;
	}
	public void setCusId(java.lang.Long value) {
		this.cusId = value;
	}
	
	public java.lang.Long getCusId() {
		return this.cusId;
	}
	public void setContactId(java.lang.Long value) {
		this.contactId = value;
	}
	
	public java.lang.Long getContactId() {
		return this.contactId;
	}

}

