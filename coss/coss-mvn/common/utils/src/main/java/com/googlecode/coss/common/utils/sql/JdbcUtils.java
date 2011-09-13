package com.googlecode.coss.common.utils.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * A collection of JDBC helper methods.
 * 
 */
public class JdbcUtils {

	/**
	 * Close a <code>Connection</code>, avoid closing if null.
	 * 
	 * @param conn
	 *            Connection to close.
	 * @throws SQLException
	 *             if a database access error occurs
	 */
	public static void close(Connection conn) throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}

	/**
	 * Close a <code>ResultSet</code>, avoid closing if null.
	 * 
	 * @param rs
	 *            ResultSet to close.
	 * @throws SQLException
	 *             if a database access error occurs
	 */
	public static void close(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
	}

	/**
	 * Close a <code>Statement</code>, avoid closing if null.
	 * 
	 * @param stmt
	 *            Statement to close.
	 * @throws SQLException
	 *             if a database access error occurs
	 */
	public static void close(Statement stmt) throws SQLException {
		if (stmt != null) {
			stmt.close();
		}
	}

	/**
	 * Close a <code>Connection</code>, avoid closing if null and hide any
	 * SQLExceptions that occur.
	 * 
	 * @param conn
	 *            Connection to close.
	 */
	public static void closeQuietly(Connection conn) {
		try {
			close(conn);
		} catch (SQLException e) {
			// quiet
		}
	}

	/**
	 * Close a <code>Connection</code>, <code>Statement</code> and
	 * <code>ResultSet</code>. Avoid closing if null and hide any SQLExceptions
	 * that occur.
	 * 
	 * @param conn
	 *            Connection to close.
	 * @param stmt
	 *            Statement to close.
	 * @param rs
	 *            ResultSet to close.
	 */
	public static void closeQuietly(Connection conn, Statement stmt, ResultSet rs) {

		try {
			closeQuietly(rs);
		} finally {
			try {
				closeQuietly(stmt);
			} finally {
				closeQuietly(conn);
			}
		}

	}

	/**
	 * Close a <code>ResultSet</code>, avoid closing if null and hide any
	 * SQLExceptions that occur.
	 * 
	 * @param rs
	 *            ResultSet to close.
	 */
	public static void closeQuietly(ResultSet rs) {
		try {
			close(rs);
		} catch (SQLException e) {
			// quiet
		}
	}

	/**
	 * Close a <code>Statement</code>, avoid closing if null and hide any
	 * SQLExceptions that occur.
	 * 
	 * @param stmt
	 *            Statement to close.
	 */
	public static void closeQuietly(Statement stmt) {
		try {
			close(stmt);
		} catch (SQLException e) {
			// quiet
		}
	}

	/**
	 * Commits a <code>Connection</code> then closes it, avoid closing if null.
	 * 
	 * @param conn
	 *            Connection to close.
	 * @throws SQLException
	 *             if a database access error occurs
	 */
	public static void commitAndClose(Connection conn) throws SQLException {
		if (conn != null) {
			try {
				conn.commit();
			} finally {
				conn.close();
			}
		}
	}

	/**
	 * Commits a <code>Connection</code> then closes it, avoid closing if null
	 * and hide any SQLExceptions that occur.
	 * 
	 * @param conn
	 *            Connection to close.
	 */
	public static void commitAndCloseQuietly(Connection conn) {
		try {
			commitAndClose(conn);
		} catch (SQLException e) {
			// quiet
		}
	}

	/**
	 * Rollback any changes made on the given connection.
	 * 
	 * @param conn
	 *            Connection to rollback. A null value is legal.
	 * @throws SQLException
	 *             if a database access error occurs
	 */
	public static void rollback(Connection conn) throws SQLException {
		if (conn != null) {
			conn.rollback();
		}
	}

	/**
	 * Performs a rollback on the <code>Connection</code> then closes it, avoid
	 * closing if null.
	 * 
	 * @param conn
	 *            Connection to rollback. A null value is legal.
	 * @throws SQLException
	 *             if a database access error occurs
	 */
	public static void rollbackAndClose(Connection conn) throws SQLException {
		if (conn != null) {
			try {
				conn.rollback();
			} finally {
				conn.close();
			}
		}
	}

	/**
	 * Performs a rollback on the <code>Connection</code> then closes it, avoid
	 * closing if null and hide any SQLExceptions that occur.
	 * 
	 * @param conn
	 *            Connection to rollback. A null value is legal.
	 */
	public static void rollbackAndCloseQuietly(Connection conn) {
		try {
			rollbackAndClose(conn);
		} catch (SQLException e) {
			// quiet
		}
	}

	/**
	 * Check whether the given SQL type is numeric.
	 * 
	 * @param sqlType
	 *            the SQL type to be checked
	 * @return whether the type is numeric
	 */
	public static boolean isNumeric(int sqlType) {
		return Types.BIT == sqlType || Types.BIGINT == sqlType || Types.DECIMAL == sqlType || Types.DOUBLE == sqlType
				|| Types.FLOAT == sqlType || Types.INTEGER == sqlType || Types.NUMERIC == sqlType
				|| Types.REAL == sqlType || Types.SMALLINT == sqlType || Types.TINYINT == sqlType;
	}

	/**
	 * Set PreparedStatement parameters value
	 * 
	 * @param ps
	 * @param args
	 * @throws SQLException
	 */
	public static void setPreparedStatementValue(PreparedStatement ps, Object[] args) throws SQLException {
		for (int i = 0; i < args.length; i++) {
			ps.setObject(i + 1, args[i]);
		}
	}

	/**
	 * Set PreparedStatement muti parameters value
	 * 
	 * @param ps
	 *            PreparedStatement
	 * @param args
	 *            arguments for PreparedStatement
	 * @param sqlTypes
	 * @throws SQLException
	 */
	public static void setPreparedStatementValue(PreparedStatement ps, Object[] args, int[] sqlTypes)
			throws SQLException {
		if (args == null || sqlTypes == null || (args.length != sqlTypes.length)) {
			throw new java.lang.IllegalArgumentException(
					"parameter wrong, the length of args should equal with the length of sqlTypes");
		}
		for (int i = 0; i < args.length; i++) {
			setPreparedStatementValue(ps, i + 1, args[i], sqlTypes[i]);
		}
	}

	/**
	 * Set PreparedStatement one parameter value
	 * 
	 * @param ps
	 * @param parameterIndex
	 * @param value
	 * @param sqlType
	 * @throws SQLException
	 */
	public static void setPreparedStatementValue(PreparedStatement ps, int parameterIndex, Object value, int sqlType)
			throws SQLException {
		if (sqlType == Types.VARCHAR || sqlType == Types.LONGVARCHAR || sqlType == Types.CLOB) {
			ps.setString(parameterIndex, value.toString());
		} else if (sqlType == Types.INTEGER) {
			ps.setInt(parameterIndex, (Integer) value);
		} else if (sqlType == Types.DATE) {// DATE
			if (value instanceof String) {
				ps.setDate(parameterIndex, Date.valueOf(value.toString()));
			} else {
				ps.setDate(parameterIndex, (Date) value);
			}
		} else if (sqlType == Types.TIME) {// TIME
			if (value instanceof String) {
				ps.setTime(parameterIndex, Time.valueOf(value.toString()));
			} else {
				ps.setTime(parameterIndex, (Time) value);
			}
		} else if (sqlType == Types.TIMESTAMP) {// TIMESTAMP
			if (value instanceof String) {
				ps.setTimestamp(parameterIndex, Timestamp.valueOf(value.toString()));
			} else {
				ps.setTimestamp(parameterIndex, (Timestamp) value);
			}
		} else {
			ps.setObject(parameterIndex, value);
		}
	}

	/**
	 * 数据库连接
	 */
	private Connection conn;

	/**
	 * @param conn 数据库连接
	 */
	private JdbcUtils(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 插入数据
	 * @param sql
	 * @param generatedKeysConverter 主键映射
	 * @param params
	 * @return 主键
	 * @throws DataAccessException
	 */
	protected <T> T insert(String sql, ResultConverter<T> generatedKeysConverter, Object... params)
			throws DataAccessException {
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			setParameters(pstmt, params);
			executeUpdate(pstmt);
			ResultSet rs = pstmt.getGeneratedKeys();
			nextResult(rs);
			return convertResult(rs, generatedKeysConverter);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}

	/**
	 * 更新数据
	 * @param sql
	 * @param params
	 * @return 影响行数
	 * @throws DataAccessException
	 */
	protected int update(String sql, Object... params) throws DataAccessException {
		return executeUpdate(getPreparedStatement(sql, params));
	}

	/**
	 * 查询单个结果
	 * @param <T>
	 * @param sql
	 * @param converter
	 * @param params
	 * @return
	 */
	protected <T> T queryForObject(String sql, ResultConverter<T> converter, Object... params) {
		ResultSet rs = executeQuery(sql, params);
		if (nextResult(rs)) {
			return convertResult(rs, converter);
		} else {
			return null;
		}
	}

	/**
	 * 查询结果列表
	 * @param <T>
	 * @param sql
	 * @param converter
	 * @param params
	 * @return
	 */
	protected <T> List<T> queryForList(String sql, ResultConverter<T> converter, Object... params) {
		ResultSet rs = executeQuery(sql, params);
		List<T> list = new ArrayList<T>();
		while (nextResult(rs)) {
			list.add(convertResult(rs, converter));
		}
		return list;
	}

	/**
	 * @param sql SQL语句
	 * @return 预编译声明
	 */
	private PreparedStatement getPreparedStatement(String sql, Object... params) throws DataAccessException {
		PreparedStatement pstmt = getPreparedStatement(sql);
		setParameters(pstmt, params);
		return pstmt;
	}

	/**
	 * @param sql SQL语句
	 * @return 预编译声明
	 */
	private PreparedStatement getPreparedStatement(String sql) throws DataAccessException {
		try {
			return conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}

	/**
	 * 为预编译声明传入参数
	 * @param pstmt 预编译声明
	 * @param params 参数
	 * @throws DataAccessException
	 */
	private void setParameters(PreparedStatement pstmt, Object... params) throws DataAccessException {
		try {
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}

	/**
	 * 执行更新操作
	 * @param pstmt
	 * @return 影响行数
	 * @throws DataAccessException
	 */
	private int executeUpdate(PreparedStatement pstmt) throws DataAccessException {
		try {
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}

	/**
	 * 执行查询操作
	 * @param pstmt 预编译声明
	 * @return 结果集
	 * @throws DataAccessException
	 */
	private ResultSet executeQuery(PreparedStatement pstmt) throws DataAccessException {
		try {
			return pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}

	/**
	 * 执行查询操作
	 * @param sql SQL语句
	 * @param params 参数
	 * @return 结果集
	 * @throws DataAccessException
	 */
	private ResultSet executeQuery(String sql, Object... params) throws DataAccessException {
		return executeQuery(getPreparedStatement(sql, params));
	}

	/**
	 * 移动到下一行记录
	 * @param rs 结果集
	 * @return 是否有下一行记录
	 * @throws DataAccessException
	 */
	private boolean nextResult(ResultSet rs) throws DataAccessException {
		try {
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}

	/**
	 * 映射
	 * @param rs 结果集
	 * @return 映射结果
	 * @throws DataAccessException
	 */
	private <T> T convertResult(ResultSet rs, ResultConverter<T> converter) throws DataAccessException {
		try {
			return converter.convert(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}
}
