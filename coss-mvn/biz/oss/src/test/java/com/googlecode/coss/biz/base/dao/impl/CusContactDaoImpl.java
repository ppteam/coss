package com.googlecode.coss.biz.base.dao.impl;

import com.googlecode.coss.biz.base.po.CusContact;
import com.googlecode.coss.biz.base.dao.CusContactDao;
import com.googlecode.coss.common.core.orm.mybatis.BaseSqlMapDao;
import org.springframework.stereotype.Repository;

@Repository("cusContactDao")
public class CusContactDaoImpl extends BaseSqlMapDao<CusContact,java.lang.Long> implements CusContactDao{
	
	@Override
	public String getSqlMapNamesapce() {
		return "com.googlecode.coss.biz.base.model.CusContact";
	}
	
	public void saveOrUpdate(CusContact cusContact) {
		if(cusContact.getCusContactId() == null) 
			save(cusContact);
		else 
			update(cusContact);
	}
	
	
}
