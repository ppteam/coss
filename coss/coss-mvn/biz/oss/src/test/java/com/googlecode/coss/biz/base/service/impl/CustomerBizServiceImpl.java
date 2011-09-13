package com.googlecode.coss.biz.base.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.coss.common.core.service.BaseService;
import com.googlecode.coss.biz.base.po.CustomerBiz;
import com.googlecode.coss.biz.base.dao.CustomerBizDao;
import com.googlecode.coss.biz.base.service.CustomerBizService;

@Service("customerBizService")
@Transactional
public class CustomerBizServiceImpl extends BaseService<CustomerBiz,java.lang.Long> implements CustomerBizService{

	private CustomerBizDao customerBizDao;
	
	@Resource
	public void setCustomerBizDao(CustomerBizDao dao) {
		this.customerBizDao = dao;
	}
	
	public CustomerBizDao getSqlMapDao() {
		return this.customerBizDao;
	}
	
	
}
