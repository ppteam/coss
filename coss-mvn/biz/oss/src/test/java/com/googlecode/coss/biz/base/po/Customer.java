package com.googlecode.coss.biz.base.po;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.googlecode.coss.common.core.orm.mybatis.BaseDataObject;

/* Persistent objects */
public class Customer extends BaseDataObject implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    private java.lang.Long    cusId;
    @NotNull
    private java.lang.Long    cusNo;
    @Length(max = 64)
    private java.lang.String  cusEngName;
    @NotBlank
    @Length(max = 64)
    private java.lang.String  cusChnName;

    private java.lang.Integer area;
    @Length(max = 128)
    private java.lang.String  address;

    private java.lang.Integer zipcode;
    @Length(max = 12)
    private java.lang.String  contactTel;
    @Length(max = 12)
    private java.lang.String  backupTel1;
    @Length(max = 12)
    private java.lang.String  backupTel2;
    @Length(max = 12)
    private java.lang.String  fax;
    @Length(max = 1024)
    private java.lang.String  cusEngIntro;
    @Length(max = 1024)
    private java.lang.String  cusChnIntro;
    @Length(max = 256)
    private java.lang.String  website;
    @Length(max = 1024)
    private java.lang.String  remark;
    @NotBlank
    @Length(max = 1)
    private java.lang.String  status;
    @NotNull
    private java.lang.Long    createBy;
    @NotNull
    private java.util.Date    createTime;
    @NotNull
    private java.lang.Long    updateBy;
    @NotNull
    private java.util.Date    updateTime;

    public void setCusId(java.lang.Long value) {
        this.cusId = value;
    }

    public java.lang.Long getCusId() {
        return this.cusId;
    }

    public void setCusNo(java.lang.Long value) {
        this.cusNo = value;
    }

    public java.lang.Long getCusNo() {
        return this.cusNo;
    }

    public void setCusEngName(java.lang.String value) {
        this.cusEngName = value;
    }

    public java.lang.String getCusEngName() {
        return this.cusEngName;
    }

    public void setCusChnName(java.lang.String value) {
        this.cusChnName = value;
    }

    public java.lang.String getCusChnName() {
        return this.cusChnName;
    }

    public void setArea(java.lang.Integer value) {
        this.area = value;
    }

    public java.lang.Integer getArea() {
        return this.area;
    }

    public void setAddress(java.lang.String value) {
        this.address = value;
    }

    public java.lang.String getAddress() {
        return this.address;
    }

    public void setZipcode(java.lang.Integer value) {
        this.zipcode = value;
    }

    public java.lang.Integer getZipcode() {
        return this.zipcode;
    }

    public void setContactTel(java.lang.String value) {
        this.contactTel = value;
    }

    public java.lang.String getContactTel() {
        return this.contactTel;
    }

    public void setBackupTel1(java.lang.String value) {
        this.backupTel1 = value;
    }

    public java.lang.String getBackupTel1() {
        return this.backupTel1;
    }

    public void setBackupTel2(java.lang.String value) {
        this.backupTel2 = value;
    }

    public java.lang.String getBackupTel2() {
        return this.backupTel2;
    }

    public void setFax(java.lang.String value) {
        this.fax = value;
    }

    public java.lang.String getFax() {
        return this.fax;
    }

    public void setCusEngIntro(java.lang.String value) {
        this.cusEngIntro = value;
    }

    public java.lang.String getCusEngIntro() {
        return this.cusEngIntro;
    }

    public void setCusChnIntro(java.lang.String value) {
        this.cusChnIntro = value;
    }

    public java.lang.String getCusChnIntro() {
        return this.cusChnIntro;
    }

    public void setWebsite(java.lang.String value) {
        this.website = value;
    }

    public java.lang.String getWebsite() {
        return this.website;
    }

    public void setRemark(java.lang.String value) {
        this.remark = value;
    }

    public java.lang.String getRemark() {
        return this.remark;
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
