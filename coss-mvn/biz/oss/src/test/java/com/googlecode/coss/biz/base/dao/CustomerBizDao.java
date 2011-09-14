package com.googlecode.coss.biz.base.dao;

import com.googlecode.coss.biz.base.po.CustomerBiz;
import com.googlecode.coss.common.core.orm.mybatis.SqlMapDao;

public interface CustomerBizDao extends SqlMapDao<CustomerBiz, java.lang.Long> {

    public void saveOrUpdate(CustomerBiz customerBiz);

}
