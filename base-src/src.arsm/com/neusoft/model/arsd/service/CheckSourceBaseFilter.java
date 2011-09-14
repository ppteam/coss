package com.neusoft.model.arsd.service;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.arsd.dao.pojo.SourceInfo;
import com.neusoft.model.arsd.exception.DictionaryException;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {针对字典明细的增删改操作} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 5, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class CheckSourceBaseFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CheckSourceBaseFilter.class);

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
		SourceInfo sourceInfo = context.getObject(key_instance, SourceInfo.class);
		List<String> srcIds = (List<String>) context.getResultMap().get("sourceIds");
		if (StringUtils.hasLength(sourceInfo.getId())) { // update
			if (!CollectionUtils.isEmpty(srcIds)) {
				for (String id : srcIds) {
					if (!id.equals(sourceInfo.getId())) {
						logger.error("[entry:" + sourceInfo.getSrcName() + "] has the same record in db");
						throw new DictionaryException("0000", "", null, new String[] { sourceInfo.getSrcName(),
								"SourceInfo" });
					}
				}
			}
		} else { // insert
			if (!CollectionUtils.isEmpty(srcIds)) {
				logger.error("[entry:" + sourceInfo.getSrcName() + "] has the same record in db");
				throw new DictionaryException("0000", "", null, new String[] { sourceInfo.getSrcName(), "SourceInfo" });
			}
		}
		return false;
	}

	private String key_instance = "instance";

	public void setKey_instance(String key_instance) {
		this.key_instance = key_instance;
	}

}
