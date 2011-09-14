package com.googlecode.coss.common.core.orm.mybatis;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.jdbc.support.JdbcAccessor;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class MybatisTemplate extends JdbcAccessor {
    private SqlSessionFactory sessionFactory;

    public MybatisTemplate() {
    }

    public MybatisTemplate(SqlSessionFactory factory) {
        this.sessionFactory = factory;
    }

    public SqlSession openSession() {
        SqlSession session = (SqlSession) TransactionSynchronizationManager
                .getResource(sessionFactory);
        if (session == null) {
            session = sessionFactory.openSession();
        }
        return session;
    }

    public void setSessionFactory(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /** */
    public List executeFind(SqlSessionCallback callback) {
        return (List) execute(callback);
    }

    /** */
    public Object execute(SqlSessionCallback callback) {
        SqlSession session = openSession();
        Object obj = callback.doInSqlSession(session);
        if (session != null)
            session.close();
        return obj;
    }

    /** 删除,根据语句 */
    public int delete(final String statement) {
        return delete(statement, null);
    }

    /** 删除，根据语句，带条件 */
    public int delete(final String statement, final Object param) {
        return (Integer) execute(new SqlSessionCallback() {
            public Object doInSqlSession(SqlSession session) {
                if (param != null)
                    return session.delete(statement, param);
                else
                    return session.delete(statement);
            }
        });
    }

    /** 修改带条件 */
    public int update(final String statement, final Object param) {
        return (Integer) execute(new SqlSessionCallback() {
            public Object doInSqlSession(SqlSession session) {
                if (param != null)
                    return session.update(statement, param);
                else
                    return session.update(statement);
            }
        });
    }

    /** 修改不带条件 */
    public int update(final String statement) {
        return update(statement, null);
    }

    /** 增加带条件 */
    public int insert(final String statement, final Object param) {
        return (Integer) execute(new SqlSessionCallback() {
            public Object doInSqlSession(SqlSession session) {
                if (param != null)
                    return session.insert(statement, param);
                else
                    return session.insert(statement);
            }
        });
    }

    /** 增加不带条件 */
    public int insert(final String statement) {
        return insert(statement, null);
    }

    /** 查询分页 */
    public List selectList(final String statement, final Object param, final RowBounds bounds) {
        return executeFind(new SqlSessionCallback() {
            public Object doInSqlSession(SqlSession session) {
                if (statement == null)
                    throw new IllegalArgumentException("Sql 'statement' require");
                if (bounds != null && param != null)
                    return session.selectList(statement, param, bounds);
                else if (bounds != null)
                    return session.selectList(statement, null, bounds);
                else if (param != null) {
                    return session.selectList(statement, param);
                } else
                    return session.selectList(statement);
            }
        });
    }

    /** 条件分页 */
    public List selectList(final String statement, final Object param, int skip, int limit) {
        return selectList(statement, param, new RowBounds(skip, limit));
    }

    /** 查询不分页 */
    public List selectList(final String statement, final Object param) {
        return selectList(statement, param, null);
    }

    /** 查询不带条件 */
    public List selectList(final String statement) {
        return selectList(statement, null);
    }

    /** 条件查带处理查询结果,带分页 */
    public void select(final String statement, final Object param, final RowBounds bounds,
                       final ResultHandler handler) {
        if (statement == null || handler == null)
            throw new IllegalArgumentException("'statement' and 'handler' require");
        execute(new SqlSessionCallback() {
            public Object doInSqlSession(SqlSession session) {
                if (handler != null && bounds != null && param != null)
                    session.select(statement, param, bounds, handler);
                else if (bounds == null)
                    session.select(statement, param, handler);
                return null;
            }
        });
    }

    /** 自己处理查询结果 ，不带分页 */
    public void select(final String statement, final Object param, final ResultHandler handler) {
        select(statement, param, null, handler);
    }

    /** 查唯一结果 */
    public Object selectOne(final String statement) {
        return selectOne(statement, null);
    }

    /** 查唯一结果带条件 */
    public Object selectOne(final String statement, final Object param) {
        if (statement == null)
            throw new IllegalArgumentException("Sql 'statement' require");
        return execute(new SqlSessionCallback() {
            public Object doInSqlSession(SqlSession session) {
                if (param != null)
                    return session.selectOne(statement, param);
                else
                    return session.selectOne(statement);

            }
        });
    }

}
