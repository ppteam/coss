package com.neusoft.model.pwreport.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.pwreport.dao.pojo.PweeklyMemberplan;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {合并项目成员计划} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 5, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class MergerMemberPlansFilter extends FilterBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MergerMemberPlansFilter.class);

	private static final long serialVersionUID = -1661357785545171964L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	@SuppressWarnings("unchecked")
	public boolean execute(IContext context) throws BaseException {
		List<PweeklyMemberplan> srcList = (List<PweeklyMemberplan>) context.getValueByAll(keySrc);
		List<PweeklyMemberplan> tagList = (List<PweeklyMemberplan>) context.getValueByAll(keyTag);
		if (!CollectionUtils.isEmpty(srcList) && !CollectionUtils.isEmpty(tagList)) {
			if (logger.isDebugEnabled()) {
				logger.debug("srcList size [" + srcList.size() + "] and tagList size [" + tagList.size() + "]");
			}

			for (PweeklyMemberplan src : srcList) {
				for (PweeklyMemberplan tag : tagList) {
					if (src.getUserId().equals(tag.getUserId())) {
						src.setNormalTime(tag.getNormalTime());
						src.setOverTime(tag.getOverTime());
					}
				}
			}
		} // end_if
		return false;
	} // end_fun

	// 源
	private String keySrc;
	// 目标
	private String keyTag;

	public String getKeySrc() {
		return keySrc;
	}

	public void setKeySrc(String keySrc) {
		this.keySrc = keySrc;
	}

	public String getKeyTag() {
		return keyTag;
	}

	public void setKeyTag(String keyTag) {
		this.keyTag = keyTag;
	}

}
