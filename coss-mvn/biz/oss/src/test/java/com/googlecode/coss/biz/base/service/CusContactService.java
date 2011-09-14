package com.googlecode.coss.biz.base.service;

import com.googlecode.coss.biz.base.po.CusContact;
import com.googlecode.coss.biz.base.dao.CusContactDao;

public interface CusContactService {

	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setCusContactDao(CusContactDao dao);

	
}
