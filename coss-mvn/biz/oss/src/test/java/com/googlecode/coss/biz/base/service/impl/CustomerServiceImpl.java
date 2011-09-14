package com.googlecode.coss.biz.base.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.coss.common.core.service.BaseService;
import com.googlecode.coss.biz.base.po.Customer;
import com.googlecode.coss.biz.base.dao.CustomerDao;
import com.googlecode.coss.biz.base.service.CustomerService;

@Service("customerService")
@Transactional
public class CustomerServiceImpl extends BaseService<Customer,java.lang.Long> implements CustomerService{

	private CustomerDao customerDao;
	
	@Resource
	public void setCustomerDao(CustomerDao dao) {
		this.customerDao = dao;
	}
	
	public CustomerDao getSqlMapDao() {
		return this.customerDao;
	}
	
	
}
