package com.googlecode.coss.common.core.helper;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.coss.common.utils.lang.StringUtils;

public class JaxbHelper {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(JaxbHelper.class);

	/**
	 * Object 生成 XML String
	 * 
	 * @param obj
	 * @return String
	 * @param packagesName
	 * @throws JAXBException
	 */
	public static String generateXml(Object obj, String... packagesName) {
		JAXBContext context;
		StringWriter sw;
		String xmlStr = null;
		try {
			if (packagesName != null && packagesName.length != 0) {
				context = JAXBContext.newInstance(StringUtils.join(packagesName, ":"));
			} else {
				context = JAXBContext.newInstance(obj.getClass());
			}
			sw = new StringWriter();
			Marshaller m = context.createMarshaller();
			// m.marshal(catalogElement,new FileOutputStream(xmlDocument));
			m.marshal(obj, sw);
			m.setProperty("jaxb.encoding", "utf-8");// 设置编码
			m.setProperty("jaxb.formatted.output", true);// 设置xml是否格式化
			xmlStr = sw.getBuffer().toString();
		} catch (Exception e) {
			handleException(e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(xmlStr);
		}
		return xmlStr;
	}

	/**
	 * 解析 XML String 为 Object
	 * 
	 * @param xmlStr
	 * @return Object
	 * @throws JAXBException
	 */
	public static Object parseXml(String xmlStr, Class<?> clazz) {
		JAXBContext jc;
		Object obj = null;
		try {
			jc = JAXBContext.newInstance(clazz);
			Unmarshaller u = jc.createUnmarshaller();
			Reader sr = new StringReader(xmlStr);
			obj = u.unmarshal(sr);
		} catch (Exception e) {
			handleException(e);
		}
		return obj;
	}

	/**
	 * Object 生成 XML String
	 * 
	 * @param obj
	 * @return String
	 * @param packagesName
	 * @throws JAXBException
	 */
	public static String generateXml(Object obj) {
		JAXBContext context;
		StringWriter sw;
		String xmlStr = null;
		try {
			context = JAXBContext.newInstance(obj.getClass());
			sw = new StringWriter();
			Marshaller m = context.createMarshaller();
			// m.marshal(catalogElement,new FileOutputStream(xmlDocument));
			m.marshal(obj, sw);
			m.setProperty("jaxb.encoding", "utf-8");// 设置编码
			m.setProperty("jaxb.formatted.output", true);// 设置xml是否格式化
			xmlStr = sw.getBuffer().toString();
		} catch (Exception e) {
			handleException(e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(xmlStr);
		}
		return xmlStr;
	}

	/**
	 * 解析 XML String 为 Object
	 * 
	 * @param xmlStr
	 * @param packagesName
	 * @throws JAXBException
	 */
	public static Object parseXml(String xmlStr, String... packagesName) {
		JAXBContext jc;
		Object obj = null;
		try {
			if (packagesName != null && packagesName.length != 0) {
				jc = JAXBContext.newInstance(StringUtils.join(packagesName, ":"));
			} else {
				jc = JAXBContext.newInstance();
			}
			Unmarshaller u = jc.createUnmarshaller();
			Reader sr = new StringReader(xmlStr);
			obj = u.unmarshal(sr);
		} catch (Exception e) {
			handleException(e);
		}
		return obj;
	}

	public static void handleException(Throwable ex) {
		ex.printStackTrace();
		throw new RuntimeException(ex.getMessage());
	}
}
