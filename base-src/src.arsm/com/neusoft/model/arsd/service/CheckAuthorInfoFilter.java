package com.neusoft.model.arsd.service;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.arsd.dao.pojo.AuthorityInfo;
import com.neusoft.model.arsd.exception.DictionaryException;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {验证当前对象是否与数据库值有冲突} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 5, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class CheckAuthorInfoFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CheckAuthorInfoFilter.class);

	private static final long serialVersionUID = -1661357785545171002L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	@SuppressWarnings("unchecked")
	public boolean execute(IContext context) throws BaseException {
		AuthorityInfo authority = context.getObject("instance", AuthorityInfo.class);
		List<String> srcIds = (List<String>) context.getResultMap().get("authorIds");
		if (StringUtils.hasLength(authority.getId())) { // update
			if (!CollectionUtils.isEmpty(srcIds)) {
				for (String id : srcIds) {
					if (!id.equals(authority.getId())) {
						logger.error("[entry:" + authority.getAuthorName() + "] or["
								+ authority.getAuthorSign() + "] has the same record in db");
						throw new DictionaryException("0000", "", null, new String[] {
								authority.getAuthorName() + " or " + authority.getAuthorSign(),
								"AuthorityInfo" });
					}
				}
			}
		} else { // insert
			if (!CollectionUtils.isEmpty(srcIds)) {
				logger.error("[entry:" + authority.getAuthorName() + "] or["
						+ authority.getAuthorSign() + "] has the same record in db");
				throw new DictionaryException("0000", "", null, new String[] {
						authority.getAuthorName() + " or " + authority.getAuthorSign(),
						"AuthorityInfo" });
			}
		}
		return false;
	}

}