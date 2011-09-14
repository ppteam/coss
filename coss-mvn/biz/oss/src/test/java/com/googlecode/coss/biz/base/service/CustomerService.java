package com.googlecode.coss.biz.base.service;

import com.googlecode.coss.biz.base.dao.CustomerDao;

public interface CustomerService {

    /** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写 */
    public void setCustomerDao(CustomerDao dao);

}
