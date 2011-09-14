package com.googlecode.coss.biz.base.dao;

import com.googlecode.coss.common.core.orm.mybatis.SqlMapDao;
import com.googlecode.coss.biz.base.po.CustomerBiz;

public interface CustomerBizDao extends SqlMapDao<CustomerBiz,java.lang.Long>{

	public void saveOrUpdate(CustomerBiz customerBiz);
	
	
}
