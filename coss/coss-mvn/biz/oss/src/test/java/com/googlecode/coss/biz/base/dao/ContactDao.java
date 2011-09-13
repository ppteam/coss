package com.googlecode.coss.biz.base.dao;

import com.googlecode.coss.common.core.orm.mybatis.SqlMapDao;
import com.googlecode.coss.biz.base.po.Contact;

public interface ContactDao extends SqlMapDao<Contact,java.lang.Long>{

	public void saveOrUpdate(Contact contact);
	
	
}
