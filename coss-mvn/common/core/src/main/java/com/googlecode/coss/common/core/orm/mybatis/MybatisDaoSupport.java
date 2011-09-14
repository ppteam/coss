package com.googlecode.coss.common.core.orm.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.dao.support.DaoSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Dao类 主要接收传入的sessionFactory 取得模板
 */
public class MybatisDaoSupport extends DaoSupport {
    private SqlSessionFactory sessionFactory;
    private MybatisTemplate   template;

    public void createTemplate() {
        this.template = new MybatisTemplate(sessionFactory);
    }

    public MybatisTemplate getTemplate() {
        // System.out.println(template);
        return template;
    }

    public void setTemplate(MybatisTemplate template) {
        this.template = template;
    }

    // 创建或得到session
    public SqlSession openSession() {
        SqlSession session = (SqlSession) TransactionSynchronizationManager
                .getResource(sessionFactory);
        if (session == null) {
            session = sessionFactory.openSession();
        }
        return session;
    }

    public SqlSessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.template = new MybatisTemplate(sessionFactory);
    }

    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {
        if (sessionFactory == null) {
            throw new IllegalArgumentException(
                    "Property 'sessionFactory' or 'template'  is required");
        }

    }

}
