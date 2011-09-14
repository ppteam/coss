package com.googlecode.coss.biz.base.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.coss.common.core.service.BaseService;
import com.googlecode.coss.biz.base.po.Contact;
import com.googlecode.coss.biz.base.dao.ContactDao;
import com.googlecode.coss.biz.base.service.ContactService;

@Service("contactService")
@Transactional
public class ContactServiceImpl extends BaseService<Contact,java.lang.Long> implements ContactService{

	private ContactDao contactDao;
	
	@Resource
	public void setContactDao(ContactDao dao) {
		this.contactDao = dao;
	}
	
	public ContactDao getSqlMapDao() {
		return this.contactDao;
	}
	
	
}
