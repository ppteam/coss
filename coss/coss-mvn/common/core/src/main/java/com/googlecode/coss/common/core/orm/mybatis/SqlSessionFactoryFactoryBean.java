package com.googlecode.coss.common.core.orm.mybatis;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.util.Assert;

public class SqlSessionFactoryFactoryBean implements FactoryBean, InitializingBean {
	protected static final Logger logger = LoggerFactory.getLogger(SqlSessionFactoryFactoryBean.class);
	private Resource configLocation;
	private Resource[] mapperLocations;
	private DataSource dataSource;
	private boolean useTransactionAwareDataSource = true;

	SqlSessionFactory sqlSessionFactory;

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(configLocation, "configLocation must be not null");

		sqlSessionFactory = createSqlSessionFactory();
	}

	private SqlSessionFactory createSqlSessionFactory() throws IOException {
		Reader reader = new InputStreamReader(getConfigLocation().getInputStream());
		try {
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			Configuration conf = sqlSessionFactory.getConfiguration();
			if (dataSource != null) {
				DataSource dataSourceToUse = this.dataSource;
				if (this.useTransactionAwareDataSource && !(this.dataSource instanceof TransactionAwareDataSourceProxy)) {
					dataSourceToUse = new TransactionAwareDataSourceProxy(this.dataSource);
				}

				conf.setEnvironment(new Environment("development", new ManagedTransactionFactory(), dataSourceToUse));
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(conf);
			}

			if (mapperLocations != null) {
				Map<String, XNode> sqlFragments = new HashMap<String, XNode>();
				for (Resource r : mapperLocations) {
					logger.info("Loading Mybatis mapper xml from file[" + r.getFile().getAbsolutePath() + "]");

					Reader mapperReader = new InputStreamReader(r.getInputStream());

					try {
						XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(mapperReader, conf, r.getFile()
								.getAbsolutePath(), sqlFragments);
						//XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(r.getInputStream(), conf, r.getFile().getAbsolutePath(), sqlFragments);
						mapperBuilder.parse();
					} finally {
						mapperReader.close();
					}
				}
			}
			return sqlSessionFactory;
		} finally {
			reader.close();
		}
	}

	public Object getObject() throws Exception {
		return sqlSessionFactory;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Class getObjectType() {
		return SqlSessionFactory.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public Resource getConfigLocation() {
		return configLocation;
	}

	public void setConfigLocation(Resource configurationFile) {
		this.configLocation = configurationFile;
	}

	public void setMapperLocations(Resource[] mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

}