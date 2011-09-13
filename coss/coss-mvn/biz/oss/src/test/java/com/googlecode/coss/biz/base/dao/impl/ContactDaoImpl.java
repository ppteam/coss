package com.googlecode.coss.biz.base.dao.impl;

import com.googlecode.coss.biz.base.po.Contact;
import com.googlecode.coss.biz.base.dao.ContactDao;
import com.googlecode.coss.common.core.orm.mybatis.BaseSqlMapDao;
import org.springframework.stereotype.Repository;

@Repository("contactDao")
public class ContactDaoImpl extends BaseSqlMapDao<Contact,java.lang.Long> implements ContactDao{
	
	@Override
	public String getSqlMapNamesapce() {
		return "com.googlecode.coss.biz.base.model.Contact";
	}
	
	public void saveOrUpdate(Contact contact) {
		if(contact.getContactId() == null) 
			save(contact);
		else 
			update(contact);
	}
	
	
}
