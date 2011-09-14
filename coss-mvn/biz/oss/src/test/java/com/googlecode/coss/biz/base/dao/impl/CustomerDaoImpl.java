package com.googlecode.coss.biz.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.googlecode.coss.biz.base.dao.CustomerDao;
import com.googlecode.coss.biz.base.po.Customer;
import com.googlecode.coss.common.core.orm.mybatis.BaseSqlMapDao;

@Repository("customerDao")
public class CustomerDaoImpl extends BaseSqlMapDao<Customer, java.lang.Long> implements CustomerDao {

    @Override
    public String getSqlMapNamesapce() {
        return "com.googlecode.coss.biz.base.model.Customer";
    }

    public void saveOrUpdate(Customer customer) {
        if (customer.getCusId() == null)
            save(customer);
        else
            update(customer);
    }

}
