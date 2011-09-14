package com.neusoft.model.arsd.service;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.arsd.dao.pojo.DictRecord;
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
public class CheckRecordBaseFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CheckRecordBaseFilter.class);

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
		DictRecord record = context.getObject("instance", DictRecord.class);
		Assert.notNull(record);
		Assert.notNull(record.getEntryName());
		Assert.notNull(record.getEntrySire());
		List<String> catalogIds = (List<String>) context.getResultMap().get("recordIds");
		if (StringUtils.hasLength(record.getId())) { // update
			if (!CollectionUtils.isEmpty(catalogIds)) {
				for (String id : catalogIds) {
					if (!id.equals(record.getId())) {
						logger.error("[entry:" + record.getEntryName()
								+ "] has the same record in db");
						throw new DictionaryException("0000", "", null, new String[] {
								record.getEntryName(), "DictRecord" });
					}
				}
			}
		} else { // insert
			if (!CollectionUtils.isEmpty(catalogIds)) {
				logger.error("[entry:" + record.getEntryName() + "] has the same record in db");
				throw new DictionaryException("0000", "", null, new String[] {
						record.getEntryName(), "DictRecord" });
			}
		}

		return false;
	}

}
