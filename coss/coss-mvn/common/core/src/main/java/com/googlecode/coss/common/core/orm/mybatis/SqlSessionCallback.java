package com.googlecode.coss.common.core.orm.mybatis;

import org.apache.ibatis.session.SqlSession;

public interface SqlSessionCallback {
	public Object doInSqlSession(SqlSession session);
}
