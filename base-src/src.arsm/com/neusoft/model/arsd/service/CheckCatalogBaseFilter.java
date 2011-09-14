package com.neusoft.model.arsd.service;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.arsd.dao.pojo.DictCatalog;
import com.neusoft.model.arsd.exception.DictionaryException;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {检测当前操作目录的逻辑判断} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 5, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class CheckCatalogBaseFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CheckCatalogBaseFilter.class);

	private static final long serialVersionUID = -1661357785545171667L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	@SuppressWarnings("unchecked")
	public boolean execute(IContext context) throws BaseException {
		DictCatalog instance = context.getObject("instance", DictCatalog.class);
		List<String> rows = (List<String>) context.getResultMap().get("catalogIds");
		if (StringUtils.hasLength(instance.getId())) { // edit
			if (!CollectionUtils.isEmpty(rows)) {
				for (String id : rows) {
					if (!id.equals(instance.getId())) {
						logger.error("[entry:" + instance.getEntryName()
								+ "] has the same record in db");
						throw new DictionaryException("0000", "", null, new String[] {
								instance.getEntryName(), "Catalog" });
					}
				}
			}
		} else { // add
			if (!CollectionUtils.isEmpty(rows)) {
				logger.error("[entry:" + instance.getEntryName() + "] has the same record in db");
				throw new DictionaryException("0000", "", null, new String[] {
						instance.getEntryName(), "Catalog" });
			}
		}
		return false;
	}

}
