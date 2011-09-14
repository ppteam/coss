package com.googlecode.coss.biz.base.dao;

import com.googlecode.coss.biz.base.po.CusContact;
import com.googlecode.coss.common.core.orm.mybatis.SqlMapDao;

public interface CusContactDao extends SqlMapDao<CusContact, java.lang.Long> {

    public void saveOrUpdate(CusContact cusContact);

}
