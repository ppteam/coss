package com.googlecode.coss.biz.base.dao;

import com.googlecode.coss.biz.base.po.Customer;
import com.googlecode.coss.common.core.orm.mybatis.SqlMapDao;

public interface CustomerDao extends SqlMapDao<Customer, java.lang.Long> {

    public void saveOrUpdate(Customer customer);

}
