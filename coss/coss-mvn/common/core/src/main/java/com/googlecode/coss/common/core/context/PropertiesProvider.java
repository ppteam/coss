package com.googlecode.coss.common.core.context;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

/**
 * 提供 Properties读取
 */
public class PropertiesProvider implements BeanFactoryPostProcessor {

	private static final Logger logger = LoggerFactory.getLogger(PropertiesProvider.class);

	private static PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();

	public Properties properties = new Properties();

	private String encoding = "ISO-8859-1";

	private Resource locations[];

	public Properties getProperties() {
		return properties;
	}

	public void setLocations(Resource[] locations) {
		this.locations = locations;
	}

	/**
	 * 载入多个properties文件, 相同的属性最后载入的文件将会覆盖之前的载入.
	 * 
	 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
	 */
	public Properties loadProperties() throws IOException {

		for (Resource resource : locations) {
			logger.debug("Loading properties file from classpath:" + resource.getFilename());
			InputStream is = null;
			try {
				is = resource.getInputStream();
				propertiesPersister.load(properties, new InputStreamReader(is, encoding));
			} catch (IOException ex) {
				logger.info("Could not load properties from classpath:" + resource.getFilename() + ": "
						+ ex.getMessage());
			} finally {
				if (is != null) {
					is.close();
				}
			}
		}
		return properties;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory configurablelistablebeanfactory)
			throws BeansException {
		try {
			loadProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
