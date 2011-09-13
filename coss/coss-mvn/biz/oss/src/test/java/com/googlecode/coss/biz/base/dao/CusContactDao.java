package com.googlecode.coss.biz.base.dao;

import com.googlecode.coss.common.core.orm.mybatis.SqlMapDao;
import com.googlecode.coss.biz.base.po.CusContact;

public interface CusContactDao extends SqlMapDao<CusContact,java.lang.Long>{

	public void saveOrUpdate(CusContact cusContact);
	
	
}
