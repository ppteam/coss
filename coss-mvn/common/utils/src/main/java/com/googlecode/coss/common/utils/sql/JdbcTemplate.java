package com.googlecode.coss.common.utils.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.googlecode.coss.common.utils.lang.StringUtils;

/**
 * <p>
 * This class can make commons JDBC programming easier
 * </p>
 * <p>
 * A DataSoure is inside JdbcTemplate
 * </p>
 */
public class JdbcTemplate {

    private static boolean      showSql      = true;

    private static final Logger logger       = Logger.getLogger("sqlLog");

    // face data source, public
    private DataSource          dataSource;

    // 0 for default, no apply setQueryTimeout method
    private int                 queryTimeout = 0;

    /**
     * Get DatSource of this instance
     * 
     * @return
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Get DatSource for this instance
     * 
     * @param dataSource
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // face connection for static method use, private
    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (this.dataSource != null) {
            return this.dataSource.getConnection();
        } else {
            return this.connection;
        }
    }

    /**
     * Get timeout setting, 0 for default
     * 
     * @return
     */
    public int getQueryTimeout() {
        return queryTimeout;
    }

    /**
     * set query timeout setting
     * 
     * @param queryTimeout
     */
    public void setQueryTimeout(int queryTimeout) {
        this.queryTimeout = queryTimeout;
    }

    /**
     * Construct JdbcTemplate instance for default. Before you use this instance
     * you must call #setDataSource(DataSource dataSource) method first.
     */
    public JdbcTemplate() {
    }

    /**
     * Construct JdbcTemplate instance buy appointing DataSource.
     * 
     * @param dataSource
     */
    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // for static method use
    private JdbcTemplate(Connection connection) {
        this.connection = connection;
    }

    // for static method use
    private JdbcTemplate(Connection connection, int timeout) {
        this.connection = connection;
    }

    /**
     * Issue a single SQL execute, typically a DDL statement.
     * 
     * @param sql
     * @throws SQLException
     */
    public void execute(final String sql) {
        if (showSql) {
            logger.info(getFinalSql(sql));
        }
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            applyQueryTimeOut(stmt);
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeQuietly(stmt);
            JdbcUtils.closeQuietly(conn);
        }
    }

    /**
     * Issue a single SQL update operation (such as an insert, update or delete
     * statement).
     * 
     * @param sql
     * @return
     * @throws SQLException
     */
    public int update(final String sql) {
        return update(sql, null, null);
    }

    /**
     * Issue a single SQL update operation (such as an insert, update or delete
     * statement) via a prepared statement, binding the given arguments.
     * 
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public int update(final String sql, Object[] args) {
        return update(sql, args, null);
    }

    /**
     * Issue a single SQL update operation (such as an insert, update or delete
     * statement) via a prepared statement, binding the given arguments.
     * 
     * @param sql
     * @param args
     * @param argTypes
     * @return
     * @throws SQLException
     */
    public int update(final String sql, Object[] args, int[] argTypes) {
        validateSqlParameter(sql);
        if (showSql) {
            logger.info(getFinalSql(sql, args, argTypes));
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            applyQueryTimeOut(pstmt);
            if (args != null) {
                if (argTypes != null) {
                    JdbcUtils.setPreparedStatementValue(pstmt, args, argTypes);
                } else {
                    JdbcUtils.setPreparedStatementValue(pstmt, args);
                }
            }
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeQuietly(pstmt);
            JdbcUtils.closeQuietly(conn);
        }
    }

    /**
     * Issue multiple SQL updates on a single JDBC Statement using batching.
     * Will fall back to separate updates on a single Statement if the JDBC
     * driver does not support batch updates.
     * 
     * @param sqls
     * @return
     * @throws SQLException
     */
    public int[] batchUpdate(final String[] sqls) {
        validateSqlParameter(sqls);
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            applyQueryTimeOut(stmt);
            stmt = conn.createStatement();
            for (String sql : sqls) {
                if (showSql) {
                    logger.info(getFinalSql(sql));
                }
                stmt.addBatch(sql);
            }
            int[] result = stmt.executeBatch();
            conn.commit();
            return result;
        } catch (SQLException e) {
            try {
                JdbcUtils.rollback(conn);
            } catch (Exception unused) {
            }
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeQuietly(stmt);
            JdbcUtils.closeQuietly(conn);
        }
    }

    /**
     * Execute SQL query and return first column as an object
     * 
     * @param sql
     * @return
     * @throws SQLException
     */
    public Object queryForObject(final String sql) {
        return queryForObject(sql, null, null);
    }

    /**
     * Execute SQL query and return first column as an object
     * 
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public Object queryForObject(final String sql, Object[] args) {
        return queryForObject(sql, args, null);
    }

    /**
     * Execute SQL query and return first column as an object
     * 
     * @param sql
     * @param args
     * @param sqlTypes
     * @return
     * @throws SQLException
     */
    public Object queryForObject(final String sql, Object[] args, int[] sqlTypes) {
        if (showSql) {
            logger.info(getFinalSql(sql, args, sqlTypes));
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            applyQueryTimeOut(pstmt);
            if (args != null) {
                if (sqlTypes != null) {
                    JdbcUtils.setPreparedStatementValue(pstmt, args, sqlTypes);
                } else {
                    JdbcUtils.setPreparedStatementValue(pstmt, args);
                }
            }
            rs = pstmt.executeQuery();
            if (rs == null) {
                return null;
            }
            if (rs.next()) {
                return rs.getObject(1);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeQuietly(conn, pstmt, rs);
        }
    }

    /**
     * Execute SQL query and return first column as an String
     * 
     * @param sql
     * @return
     * @throws SQLException
     */
    public String queryForString(final String sql) {
        return queryForString(sql, null, null);
    }

    /**
     * Execute SQL query and return first column as an String
     * 
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public String queryForString(final String sql, Object[] args) {
        return queryForString(sql, args, null);
    }

    /**
     * Execute SQL query and return first column as an String
     * 
     * @param sql
     * @param args
     * @param sqlTypes
     * @return
     * @throws SQLException
     */
    public String queryForString(final String sql, Object[] args, int[] sqlTypes) {
        if (showSql) {
            logger.info(getFinalSql(sql, args, sqlTypes));
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            applyQueryTimeOut(pstmt);
            if (args != null) {
                if (sqlTypes == null) {
                    JdbcUtils.setPreparedStatementValue(pstmt, args);
                } else {
                    JdbcUtils.setPreparedStatementValue(pstmt, args, sqlTypes);
                }
            }
            rs = pstmt.executeQuery();
            if (rs == null) {
                return null;
            }
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeQuietly(conn, pstmt, rs);
        }
    }

    /**
     * Execute SQL query and return first column as integer
     * 
     * @param sql
     * @return
     * @throws SQLException
     */
    public int queryForInt(final String sql) {
        return queryForInt(sql, null, null);
    }

    /**
     * Execute SQL query and return first column as integer
     * 
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public int queryForInt(final String sql, Object[] args) {
        return queryForInt(sql, args, null);
    }

    /**
     * Execute SQL query and return first column as integer
     * 
     * @param sql
     * @param args
     * @param sqlTypes
     * @return
     * @throws SQLException
     */
    public int queryForInt(final String sql, Object[] args, int[] sqlTypes) {
        if (showSql) {
            logger.info(getFinalSql(sql, args, sqlTypes));
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            applyQueryTimeOut(pstmt);
            if (args != null) {
                if (sqlTypes == null) {
                    JdbcUtils.setPreparedStatementValue(pstmt, args);
                } else {
                    JdbcUtils.setPreparedStatementValue(pstmt, args, sqlTypes);
                }
            }
            rs = pstmt.executeQuery();
            if (rs == null) {
                return 0;
            }
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeQuietly(conn, pstmt, rs);
        }
    }

    /**
     * Execute SQL query and return first low as RowSet
     * 
     * @param sql
     * @return
     * @throws SQLException
     */
    public Row queryForRow(final String sql) {
        return queryForRow(sql, null, null);
    }

    /**
     * Execute SQL query and return first low as RowSet
     * 
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public Row queryForRow(final String sql, Object[] args) {
        return queryForRow(sql, args, null);
    }

    /**
     * Execute SQL query and return first low as RowSet
     * 
     * @param sql
     * @param args
     * @param sqlTypes
     * @return
     * @throws SQLException
     */
    public Row queryForRow(final String sql, Object[] args, int[] sqlTypes) {
        if (showSql) {
            logger.info(getFinalSql(sql, args, sqlTypes));
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            if (args != null) {
                if (sqlTypes != null) {
                    JdbcUtils.setPreparedStatementValue(pstmt, args, sqlTypes);
                } else {
                    JdbcUtils.setPreparedStatementValue(pstmt, args);
                }
            }
            applyQueryTimeOut(pstmt);
            rs = pstmt.executeQuery();
            if (rs == null) {
                return null;
            }
            if (rs.next()) {
                return Row.valueOneOf(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeQuietly(conn, pstmt, rs);
        }
    }

    /**
     * Execute SQL query and return RowSet List
     * 
     * @param sql
     * @return
     * @throws SQLException
     */
    public List<Row> queryForRowList(final String sql) {
        return queryForRowList(sql, null, null);
    }

    /**
     * Execute SQL query and return RowSet List
     * 
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public List<Row> queryForRowList(final String sql, Object[] args) {
        return queryForRowList(sql, args, null);
    }

    /**
     * Execute SQL query and return RowSet List
     * 
     * @param sql
     * @param args
     * @param sqlTypes
     * @return
     * @throws SQLException
     */
    public List<Row> queryForRowList(final String sql, Object[] args, int[] sqlTypes) {
        if (showSql) {
            logger.info(getFinalSql(sql, args, sqlTypes));
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            if (args != null) {
                if (sqlTypes == null) {
                    JdbcUtils.setPreparedStatementValue(pstmt, args);
                } else {
                    JdbcUtils.setPreparedStatementValue(pstmt, args, sqlTypes);
                }
            }
            applyQueryTimeOut(pstmt);
            rs = pstmt.executeQuery();
            if (rs == null) {
                return null;
            }
            return Row.valueOf(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeQuietly(conn, pstmt, rs);
        }
    }

    /**
     * Query given SQL to create a prepared statement from SQL and a list of
     * arguments to bind to the query, mapping each row to a Java object via a
     * RowMapper.
     * 
     * @param sql
     * @param rowMapper
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List query(final String sql, RowMapper rowMapper) {
        return query(sql, null, null, rowMapper);
    }

    /**
     * Query given SQL to create a prepared statement from SQL and a list of
     * arguments to bind to the query, mapping each row to a Java object via a
     * RowMapper.
     * 
     * @param sql
     * @param args
     * @param rowMapper
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List query(final String sql, Object[] args, RowMapper rowMapper) {
        return query(sql, args, null, rowMapper);
    }

    /**
     * Query given SQL to create a prepared statement from SQL and a list of
     * arguments to bind to the query, mapping each row to a Java object via a
     * RowMapper.
     * 
     * @param sql
     * @param args
     * @param sqlTypes
     * @param rowMapper
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List query(final String sql, Object[] args, int[] sqlTypes, RowMapper rowMapper) {
        if (showSql) {
            logger.info(getFinalSql(sql, args, sqlTypes));
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            if (args != null) {
                if (sqlTypes == null) {
                    JdbcUtils.setPreparedStatementValue(pstmt, args);
                } else {
                    JdbcUtils.setPreparedStatementValue(pstmt, args, sqlTypes);
                }
            }
            applyQueryTimeOut(pstmt);
            rs = pstmt.executeQuery();
            if (rs == null) {
                return null;
            } else {
                List resultList = new ArrayList();
                while (rs.next()) {
                    resultList.add(rowMapper.mapRow(rs, rs.getRow()));
                }
                return resultList;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeQuietly(conn, pstmt, rs);
        }
    }

    private void validateSqlParameter(String sql) {
        if (StringUtils.isBlank(sql)) {
            throw new java.lang.IllegalArgumentException("SQL must not be blank");
        }
    }

    private void validateSqlParameter(String[] sqls) {
        if (sqls == null || sqls.length < 1) {
            throw new java.lang.IllegalArgumentException("SQL Array must not be blank");
        }
    }

    private void applyQueryTimeOut(Statement stmt) throws SQLException {
        if (this.queryTimeout != 0) {
            stmt.setQueryTimeout(queryTimeout);
        }
    }

    // /////////////////////////////////////////////reflection
    // method//////////////////////////////////////////////////////////

    /**
     * <p>
     * Query the first record, reflection to a JavaBean
     * </p>
     * 
     * @param type Type Class
     * @param sql
     * @param args
     * @param sqlTypes
     * @return
     * @throws Exception
     */
    public <T> T queryObjectUseReflection(Class<T> type, final String sql, Object[] args,
                                          int[] sqlTypes) {
        if (showSql) {
            logger.info(getFinalSql(sql, args, sqlTypes));
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            applyQueryTimeOut(pstmt);
            if (args != null) {
                if (sqlTypes != null) {
                    JdbcUtils.setPreparedStatementValue(pstmt, args, sqlTypes);
                } else {
                    JdbcUtils.setPreparedStatementValue(pstmt, args);
                }
            }
            rs = pstmt.executeQuery();
            return ResultSetUtils.getObjectUseReflection(type, rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeQuietly(conn, pstmt, rs);
        }
    }

    /**
     * <p>
     * Query the first record, reflection to a JavaBean
     * </p>
     * 
     * @param type
     * @param sql
     * @param args
     * @return
     * @throws Exception
     */
    public <T> T queryObjectUseReflection(Class<T> type, final String sql, Object[] args) {
        return queryObjectUseReflection(type, sql, args, null);
    }

    /**
     * <p>
     * Query the first record, reflection to a JavaBean
     * </p>
     * 
     * @param type
     * @param sql
     * @return
     * @throws Exception
     */
    public <T> T queryObjectUseReflection(Class<T> type, final String sql) {
        return queryObjectUseReflection(type, sql, null, null);
    }

    /**
     * <p>
     * Query all records, reflection to a JavaBean List
     * </p>
     * 
     * @param type
     * @param sql
     * @param args
     * @param sqlTypes
     * @return
     * @throws Exception
     */
    public <T> List<T> queryObjectListUseReflection(Class<T> type, final String sql, Object[] args,
                                                    int[] sqlTypes) {
        if (showSql) {
            logger.info(getFinalSql(sql, args, sqlTypes));
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            applyQueryTimeOut(pstmt);
            if (args != null) {
                if (sqlTypes != null) {
                    JdbcUtils.setPreparedStatementValue(pstmt, args, sqlTypes);
                } else {
                    JdbcUtils.setPreparedStatementValue(pstmt, args);
                }
            }
            rs = pstmt.executeQuery();
            return ResultSetUtils.getObjectListUseReflection(type, rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeQuietly(conn, pstmt, rs);
        }
    }

    /**
     * <p>
     * Query all records, reflection to a JavaBean List
     * </p>
     * 
     * @param type
     * @param sql
     * @param args
     * @return
     * @throws Exception
     */
    public <T> List<T> queryObjectListUseReflection(Class<T> type, final String sql, Object[] args) {
        return queryObjectListUseReflection(type, sql, args, null);
    }

    /**
     * <p>
     * Query all records, reflection to a JavaBean List
     * </p>
     * 
     * @param type
     * @param sql
     * @return
     * @throws Exception
     */
    public <T> List<T> queryObjectListUseReflection(Class<T> type, final String sql) {
        return queryObjectListUseReflection(type, sql, null, null);
    }

    // ///////////////////////////////////////////////static
    // method////////////////////////////////////////////////////////////////////

    /**
     * Issue a single SQL execute, typically a DDL statement.
     * 
     * @param conn
     * @param sql
     * @param timeout
     * @throws SQLException
     */
    public static void execute(Connection conn, final String sql, int timeout) {
        new JdbcTemplate(conn, timeout).execute(sql);
    }

    /**
     * Issue a single SQL execute, typically a DDL statement.
     * 
     * @param conn
     * @param sql
     * @throws SQLException
     */
    public static void execute(Connection conn, final String sql) {
        new JdbcTemplate(conn).execute(sql);
    }

    /**
     * Issue a single SQL update operation (such as an insert, update or delete
     * statement).
     * 
     * @param conn
     * @param sql
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static int update(Connection conn, final String sql, int timeout) {
        return new JdbcTemplate(conn, timeout).update(sql);
    }

    /**
     * Issue a single SQL update operation (such as an insert, update or delete
     * statement).
     * 
     * @param conn
     * @param sql
     * @return
     * @throws SQLException
     */
    public static int update(Connection conn, final String sql) {
        return new JdbcTemplate(conn).update(sql);
    }

    /**
     * Issue a single SQL update operation (such as an insert, update or delete
     * statement) via a prepared statement, binding the given arguments.
     * 
     * @param conn
     * @param sql
     * @param args
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static int update(Connection conn, final String sql, Object[] args, int timeout) {
        return new JdbcTemplate(conn, timeout).update(sql, args);
    }

    /**
     * Issue a single SQL update operation (such as an insert, update or delete
     * statement) via a prepared statement, binding the given arguments.
     * 
     * @param conn
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public static int update(Connection conn, final String sql, Object[] args) {
        return new JdbcTemplate(conn).update(sql, args);
    }

    /**
     * Issue a single SQL update operation (such as an insert, update or delete
     * statement) via a prepared statement, binding the given arguments.
     * 
     * @param conn
     * @param sql
     * @param args
     * @param argTypes
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static int update(Connection conn, final String sql, Object[] args, int[] argTypes,
                             int timeout) {
        return new JdbcTemplate(conn, timeout).update(sql, args, argTypes);
    }

    /**
     * Issue a single SQL update operation (such as an insert, update or delete
     * statement) via a prepared statement, binding the given arguments.
     * 
     * @param conn
     * @param sql
     * @param args
     * @param argTypes
     * @return
     * @throws SQLException
     */
    public static int update(Connection conn, final String sql, Object[] args, int[] argTypes) {
        return new JdbcTemplate(conn).update(sql, args, argTypes);
    }

    /**
     * Issue multiple SQL updates on a single JDBC Statement using batching.
     * Will fall back to separate updates on a single Statement if the JDBC
     * driver does not support batch updates.
     * 
     * @param conn
     * @param sqls
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static int[] batchUpdate(Connection conn, final String[] sqls, int timeout) {
        return new JdbcTemplate(conn, timeout).batchUpdate(sqls);
    }

    /**
     * Issue multiple SQL updates on a single JDBC Statement using batching.
     * Will fall back to separate updates on a single Statement if the JDBC
     * driver does not support batch updates.
     * 
     * @param conn
     * @param sqls
     * @return
     * @throws SQLException
     */
    public static int[] batchUpdate(Connection conn, final String[] sqls) {
        return new JdbcTemplate(conn).batchUpdate(sqls);
    }

    /**
     * Execute SQL query and return first column as an object
     * 
     * @param conn
     * @param sql
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static Object queryForObject(Connection conn, final String sql, int timeout) {
        return new JdbcTemplate(conn, timeout).queryForObject(sql);
    }

    /**
     * Execute SQL query and return first column as an object
     * 
     * @param conn
     * @param sql
     * @return
     * @throws SQLException
     */
    public static Object queryForObject(Connection conn, final String sql) {
        return new JdbcTemplate(conn).queryForObject(sql);
    }

    /**
     * Execute SQL query and return first column as an object
     * 
     * @param conn
     * @param sql
     * @param args
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static Object queryForObject(Connection conn, final String sql, Object[] args,
                                        int timeout) {
        return new JdbcTemplate(conn, timeout).queryForObject(sql, args);
    }

    /**
     * Execute SQL query and return first column as an object
     * 
     * @param conn
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public static Object queryForObject(Connection conn, final String sql, Object[] args) {
        return new JdbcTemplate(conn).queryForObject(sql, args);
    }

    /**
     * Execute SQL query and return first column as an object
     * 
     * @param conn
     * @param sql
     * @param args
     * @param sqlTypes
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static Object queryForObject(Connection conn, final String sql, Object[] args,
                                        int[] sqlTypes, int timeout) {
        return new JdbcTemplate(conn, timeout).queryForObject(sql, args, sqlTypes);
    }

    /**
     * Execute SQL query and return first column as an object
     * 
     * @param conn
     * @param sql
     * @param args
     * @param sqlTypes
     * @return
     * @throws SQLException
     */
    public static Object queryForObject(Connection conn, final String sql, Object[] args,
                                        int[] sqlTypes) {
        return new JdbcTemplate(conn).queryForObject(sql, args, sqlTypes);
    }

    /**
     * Execute SQL query and return first column as an String
     * 
     * @param conn
     * @param sql
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static String queryForString(Connection conn, final String sql, int timeout) {
        return new JdbcTemplate(conn, timeout).queryForString(sql);
    }

    /**
     * Execute SQL query and return first column as an String
     * 
     * @param conn
     * @param sql
     * @return
     * @throws SQLException
     */
    public static String queryForString(Connection conn, final String sql) {
        return new JdbcTemplate(conn).queryForString(sql);
    }

    /**
     * Execute SQL query and return first column as an String
     * 
     * @param conn
     * @param sql
     * @param args
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static String queryForString(Connection conn, final String sql, Object[] args,
                                        int timeout) {
        return new JdbcTemplate(conn, timeout).queryForString(sql, args);
    }

    /**
     * Execute SQL query and return first column as an String
     * 
     * @param conn
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public static String queryForString(Connection conn, final String sql, Object[] args) {
        return new JdbcTemplate(conn).queryForString(sql, args);
    }

    /**
     * Execute SQL query and return first column as an String
     * 
     * @param conn
     * @param sql
     * @param args
     * @param sqlTypes
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static String queryForString(Connection conn, final String sql, Object[] args,
                                        int[] sqlTypes, int timeout) {
        return new JdbcTemplate(conn, timeout).queryForString(sql, args, sqlTypes);
    }

    /**
     * Execute SQL query and return first column as an String
     * 
     * @param conn
     * @param sql
     * @param args
     * @param sqlTypes
     * @return
     * @throws SQLException
     */
    public static String queryForString(Connection conn, final String sql, Object[] args,
                                        int[] sqlTypes) {
        return new JdbcTemplate(conn).queryForString(sql, args, sqlTypes);
    }

    /**
     * Execute SQL query and return first column as integer
     * 
     * @param conn
     * @param sql
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static int queryForInt(Connection conn, final String sql, int timeout) {
        return new JdbcTemplate(conn, timeout).queryForInt(sql);
    }

    /**
     * Execute SQL query and return first column as integer
     * 
     * @param conn
     * @param sql
     * @return
     * @throws SQLException
     */
    public static int queryForInt(Connection conn, final String sql) {
        return new JdbcTemplate(conn).queryForInt(sql);
    }

    /**
     * Execute SQL query and return first column as integer
     * 
     * @param conn
     * @param sql
     * @param args
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static int queryForInt(Connection conn, final String sql, Object[] args, int timeout) {
        return new JdbcTemplate(conn, timeout).queryForInt(sql, args);
    }

    /**
     * Execute SQL query and return first column as integer
     * 
     * @param conn
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public static int queryForInt(Connection conn, final String sql, Object[] args) {
        return new JdbcTemplate(conn).queryForInt(sql, args);
    }

    /**
     * Execute SQL query and return first column as integer
     * 
     * @param conn
     * @param sql
     * @param args
     * @param sqlTypes
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static int queryForInt(Connection conn, final String sql, Object[] args, int[] sqlTypes,
                                  int timeout) {
        return new JdbcTemplate(conn, timeout).queryForInt(sql, args, sqlTypes);
    }

    /**
     * Execute SQL query and return first low as RowSet
     * 
     * @param conn
     * @param sql
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static Row queryForRow(Connection conn, final String sql, int timeout) {
        return new JdbcTemplate(conn, timeout).queryForRow(sql);
    }

    /**
     * Execute SQL query and return first low as RowSet
     * 
     * @param conn
     * @param sql
     * @return
     * @throws SQLException
     */
    public static Row queryForRow(Connection conn, final String sql) {
        return new JdbcTemplate(conn).queryForRow(sql);
    }

    /**
     * Execute SQL query and return first low as RowSet
     * 
     * @param conn
     * @param sql
     * @param args
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static Row queryForRow(Connection conn, final String sql, Object[] args, int timeout) {
        return new JdbcTemplate(conn, timeout).queryForRow(sql, args);
    }

    /**
     * Execute SQL query and return first low as RowSet
     * 
     * @param conn
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public static Row queryForRow(Connection conn, final String sql, Object[] args) {
        return new JdbcTemplate(conn).queryForRow(sql, args);
    }

    /**
     * Execute SQL query and return first low as RowSet
     * 
     * @param conn
     * @param sql
     * @param args
     * @param sqlTypes
     * @param timeout
     * @return
     * @throws SQLException
     */
    public Row queryForRow(Connection conn, final String sql, Object[] args, int[] sqlTypes,
                           int timeout) {
        return new JdbcTemplate(conn, timeout).queryForRow(sql, args, sqlTypes);
    }

    /**
     * Execute SQL query and return first low as RowSet
     * 
     * @param conn
     * @param sql
     * @param args
     * @param sqlTypes
     * @return
     * @throws SQLException
     */
    public Row queryForRow(Connection conn, final String sql, Object[] args, int[] sqlTypes) {
        return new JdbcTemplate(conn).queryForRow(sql, args, sqlTypes);
    }

    /**
     * Execute SQL query and return RowSet List
     * 
     * @param conn
     * @param sql
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static List<Row> queryForRowList(Connection conn, final String sql, int timeout) {
        return new JdbcTemplate(conn, timeout).queryForRowList(sql);
    }

    /**
     * Execute SQL query and return RowSet List
     * 
     * @param conn
     * @param sql
     * @return
     * @throws SQLException
     */
    public static List<Row> queryForRowList(Connection conn, final String sql) {
        return new JdbcTemplate(conn).queryForRowList(sql);
    }

    /**
     * Execute SQL query and return RowSet List
     * 
     * @param conn
     * @param sql
     * @param args
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static List<Row> queryForRowList(Connection conn, final String sql, Object[] args,
                                            int timeout) {
        return new JdbcTemplate(conn, timeout).queryForRowList(sql, args);
    }

    /**
     * Execute SQL query and return RowSet List
     * 
     * @param conn
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public static List<Row> queryForRowList(Connection conn, final String sql, Object[] args) {
        return new JdbcTemplate(conn).queryForRowList(sql, args);
    }

    /**
     * Execute SQL query and return RowSet List
     * 
     * @param conn
     * @param sql
     * @param args
     * @param sqlTypes
     * @param timeout
     * @return
     * @throws SQLException
     */
    public static List<Row> queryForRowList(Connection conn, final String sql, Object[] args,
                                            int[] sqlTypes, int timeout) {
        return new JdbcTemplate(conn, timeout).queryForRowList(sql, args, sqlTypes);
    }

    /**
     * Execute SQL query and return RowSet List
     * 
     * @param conn
     * @param sql
     * @param args
     * @param sqlTypes
     * @return
     * @throws SQLException
     */
    public static List<Row> queryForRowList(Connection conn, final String sql, Object[] args,
                                            int[] sqlTypes) {
        return new JdbcTemplate(conn).queryForRowList(sql, args, sqlTypes);
    }

    /**
     * Query given SQL to create a prepared statement from SQL and a list of
     * arguments to bind to the query, mapping each row to a Java object via a
     * RowMapper.
     * 
     * @param conn
     * @param sql
     * @param rowMapper
     * @param timeout
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public static List query(Connection conn, final String sql, RowMapper rowMapper, int timeout) {
        return new JdbcTemplate(conn, timeout).query(sql, rowMapper);
    }

    /**
     * Query given SQL to create a prepared statement from SQL and a list of
     * arguments to bind to the query, mapping each row to a Java object via a
     * RowMapper.
     * 
     * @param conn
     * @param sql
     * @param rowMapper
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public static List query(Connection conn, final String sql, RowMapper rowMapper) {
        return new JdbcTemplate(conn).query(sql, rowMapper);
    }

    /**
     * Query given SQL to create a prepared statement from SQL and a list of
     * arguments to bind to the query, mapping each row to a Java object via a
     * RowMapper.
     * 
     * @param conn
     * @param sql
     * @param args
     * @param rowMapper
     * @param timeout
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public static List query(Connection conn, final String sql, Object[] args, RowMapper rowMapper,
                             int timeout) {
        return new JdbcTemplate(conn, timeout).query(sql, args, rowMapper);
    }

    /**
     * Query given SQL to create a prepared statement from SQL and a list of
     * arguments to bind to the query, mapping each row to a Java object via a
     * RowMapper.
     * 
     * @param conn
     * @param sql
     * @param args
     * @param rowMapper
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public static List query(Connection conn, final String sql, Object[] args, RowMapper rowMapper) {
        return new JdbcTemplate(conn).query(sql, args, rowMapper);
    }

    /**
     * Query given SQL to create a prepared statement from SQL and a list of
     * arguments to bind to the query, mapping each row to a Java object via a
     * RowMapper.
     * 
     * @param conn
     * @param sql
     * @param args
     * @param sqlTypes
     * @param rowMapper
     * @param timeout
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public static List query(Connection conn, final String sql, Object[] args, int[] sqlTypes,
                             RowMapper rowMapper, int timeout) {
        return new JdbcTemplate(conn, timeout).query(sql, args, sqlTypes, rowMapper);
    }

    /**
     * Query given SQL to create a prepared statement from SQL and a list of
     * arguments to bind to the query, mapping each row to a Java object via a
     * RowMapper.
     * 
     * @param conn
     * @param sql
     * @param args
     * @param sqlTypes
     * @param rowMapper
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public static List query(Connection conn, final String sql, Object[] args, int[] sqlTypes,
                             RowMapper rowMapper) {
        return new JdbcTemplate(conn).query(sql, args, sqlTypes, rowMapper);
    }

    /**
     * <p>
     * Query the first records, reflection to a JavaBean
     * </p>
     * 
     * @param type
     * @param conn
     * @param sql
     * @param args
     * @param sqlTypes
     * @return
     * @throws Exception
     */
    public static Object queryObjectUseReflection(Class<?> type, Connection conn, final String sql,
                                                  Object[] args, int[] sqlTypes) throws Exception {
        return new JdbcTemplate(conn).queryObjectUseReflection(type, sql, args, sqlTypes);
    }

    /**
     * <p>
     * Query the first records, reflection to a JavaBean
     * </p>
     * 
     * @param type
     * @param conn
     * @param sql
     * @param args
     * @return
     * @throws Exception
     */
    public static Object queryObjectUseReflection(Class<?> type, Connection conn, final String sql,
                                                  Object[] args) throws Exception {
        return new JdbcTemplate(conn).queryObjectUseReflection(type, sql, args);
    }

    /**
     * <p>
     * Query the first records, reflection to a JavaBean
     * </p>
     * 
     * @param type
     * @param conn
     * @param sql
     * @return
     * @throws Exception
     */
    public static Object queryObjectUseReflection(Class<?> type, Connection conn, final String sql)
            throws Exception {
        return new JdbcTemplate(conn).queryObjectUseReflection(type, sql);
    }

    /**
     * <p>
     * Query all records, reflection to a JavaBean List
     * </p>
     * 
     * @param type
     * @param conn
     * @param sql
     * @param args
     * @param sqlTypes
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static List queryObjectListUseReflection(Class<?> type, Connection conn,
                                                    final String sql, Object[] args, int[] sqlTypes)
            throws Exception {
        return new JdbcTemplate(conn).queryObjectListUseReflection(type, sql, args, sqlTypes);
    }

    /**
     * <p>
     * Query all records, reflection to a JavaBean List
     * </p>
     * 
     * @param type
     * @param conn
     * @param sql
     * @param args
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static List queryObjectListUseReflection(Class<?> type, Connection conn,
                                                    final String sql, Object[] args)
            throws Exception {
        return new JdbcTemplate(conn).queryObjectListUseReflection(type, sql, args);
    }

    /**
     * <p>
     * Query all records, reflection to a JavaBean List
     * </p>
     * 
     * @param type
     * @param conn
     * @param sql
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static List queryObjectListUseReflection(Class<?> type, Connection conn, final String sql)
            throws Exception {
        return new JdbcTemplate(conn).queryObjectListUseReflection(type, sql);
    }

    public static boolean isShowSql() {
        return showSql;
    }

    public static void setShowSql(boolean showSql) {
        JdbcTemplate.showSql = showSql;
    }

    public static String getFinalSql(String sql, Object[] args, int[] sqlTypes) {
        if (sqlTypes == null || sqlTypes.length < 1) {
            return getFinalSql(sql, args);
        }
        StringBuilder sb = new StringBuilder();
        char[] sqlArray = sql.toCharArray();
        int i = 0;
        for (char c : sqlArray) {
            if (c == '?') {
                Object o = args[i];
                int type = sqlTypes[i];
                if (JdbcUtils.isNumeric(type)) {
                    sb.append(o);
                } else {
                    sb.append('\'');
                    sb.append(o);
                    sb.append('\'');
                }
                i++;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getFinalSql(String sql, Object[] args) {
        if (args == null || args.length < 1) {
            return getFinalSql(sql);
        }
        StringBuilder sb = new StringBuilder();
        char[] sqlArray = sql.toCharArray();
        int i = 0;
        for (char c : sqlArray) {
            if (c == '?') {
                Object o = args[i];
                if (o instanceof Number) {
                    sb.append(o);
                } else {
                    sb.append('\'');
                    sb.append(o);
                    sb.append('\'');
                }
                i++;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getFinalSql(String sql) {
        return sql;
    }
}
