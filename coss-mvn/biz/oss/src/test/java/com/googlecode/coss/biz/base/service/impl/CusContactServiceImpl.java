package com.googlecode.coss.biz.base.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.coss.biz.base.dao.CusContactDao;
import com.googlecode.coss.biz.base.po.CusContact;
import com.googlecode.coss.biz.base.service.CusContactService;
import com.googlecode.coss.common.core.service.BaseService;

@Service("cusContactService")
@Transactional
public class CusContactServiceImpl extends BaseService<CusContact, java.lang.Long> implements
        CusContactService {

    private CusContactDao cusContactDao;

    @Resource
    public void setCusContactDao(CusContactDao dao) {
        this.cusContactDao = dao;
    }

    public CusContactDao getSqlMapDao() {
        return this.cusContactDao;
    }

}
