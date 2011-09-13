package com.googlecode.coss.biz.base.dao;

import com.googlecode.coss.common.core.orm.mybatis.SqlMapDao;
import com.googlecode.coss.biz.base.po.Customer;

public interface CustomerDao extends SqlMapDao<Customer,java.lang.Long>{

	public void saveOrUpdate(Customer customer);
	
	
}
