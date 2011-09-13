package com.googlecode.coss.biz.base.dao.impl;

import com.googlecode.coss.biz.base.po.CustomerBiz;
import com.googlecode.coss.biz.base.dao.CustomerBizDao;
import com.googlecode.coss.common.core.orm.mybatis.BaseSqlMapDao;
import org.springframework.stereotype.Repository;

@Repository("customerBizDao")
public class CustomerBizDaoImpl extends BaseSqlMapDao<CustomerBiz,java.lang.Long> implements CustomerBizDao{
	
	@Override
	public String getSqlMapNamesapce() {
		return "com.googlecode.coss.biz.base.model.CustomerBiz";
	}
	
	public void saveOrUpdate(CustomerBiz customerBiz) {
		if(customerBiz.getBizId() == null) 
			save(customerBiz);
		else 
			update(customerBiz);
	}
	
	
}
