package com.googlecode.coss.biz.base.service;

import com.googlecode.coss.biz.base.po.CustomerBiz;
import com.googlecode.coss.biz.base.dao.CustomerBizDao;

public interface CustomerBizService {

	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setCustomerBizDao(CustomerBizDao dao);

	
}
