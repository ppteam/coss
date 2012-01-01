package com.googlecode.coss.common.core.orm.mybatis;

import java.io.IOException;
import java.io.Reader;

import javax.sql.DataSource;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

/**
 * session工厂类
 */
@SuppressWarnings("rawtypes")
public class MybatisSessionFactory implements FactoryBean, InitializingBean {
    private String            configLocation;

    private DataSource        dataSource;

    private SqlSessionFactory sqlSessionFactory;

    private boolean           useTransactionAwareDataSource = true;

    private String            environmentId                 = "development";

    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    public void setUseTransactionAwareDataSource(boolean useTransactionAwareDataSource) {
        this.useTransactionAwareDataSource = useTransactionAwareDataSource;
    }

    public Object getObject() throws Exception {
        return this.sqlSessionFactory;
    }

    public Class getObjectType() {
        return (this.sqlSessionFactory != null ? this.sqlSessionFactory.getClass()
                : SqlSessionFactory.class);
    }

    public boolean isSingleton() {
        return true;
    }

    public void afterPropertiesSet() throws Exception {
        this.sqlSessionFactory = this.buildSqlSessionFactory(configLocation);
    }

    protected SqlSessionFactory buildSqlSessionFactory(String configLocation) throws IOException {
        if (configLocation == null) {
            throw new IllegalArgumentException("configLocation entry is required");
        }
        DataSource dataSourceToUse = this.dataSource;
        if (this.useTransactionAwareDataSource
                && !(this.dataSource instanceof TransactionAwareDataSourceProxy)) {
            dataSourceToUse = new TransactionAwareDataSourceProxy(this.dataSource);
        }

        Environment environment = new Environment(environmentId, new ManagedTransactionFactory(),
                dataSourceToUse);

        Reader reader = Resources.getResourceAsReader(configLocation);
        XMLConfigBuilder parser = new XMLConfigBuilder(reader, null, null);
        Configuration config = parser.parse();
        config.setEnvironment(environment);

        return new DefaultSqlSessionFactory(config);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }
}
