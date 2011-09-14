package com.neusoft.model.udmgr.service;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.model.udmgr.dao.pojo.ProjectInfo;
import com.neusoft.model.udmgr.dao.pojo.ProjectStats;
import com.neusoft.model.udmgr.exception.HandleProjectException;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {新增用户操作} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class HandleProjectInfoFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HandleProjectInfoFilter.class);

	private static final long serialVersionUID = -1661098785545171667L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		ProjectInfo instance = context.getObject(paramKey, ProjectInfo.class);
		Integer pro_count = (Integer) context.getValueByAll(keyCount);
		if (StringUtils.hasLength(instance.getId())) {
			if (pro_count > 1) {
				throw new HandleProjectException("0001", BaseException.EXP_TYPE_EXCPT, "unequel index in db", null,
						null);
			} // end_if
			int row = projectDao.updateById(instance);
			context.getResultMap().put("update_rows", row);
			if (logger.isDebugEnabled()) {
				logger.debug("update ProjectInfo [" + instance.getId() + "] is success ...");
			}
		} else {
			if (pro_count > 0) {
				throw new HandleProjectException("0001", BaseException.EXP_TYPE_EXCPT, "unequel index in db", null,
						null);
			}
			instance.setProjectId(CommonUtils.getUUIDSeq());
			String id = projectDao.insert(instance);
			context.getResultMap().put("instance_id", id);
			initProjectStats(instance);
			if (logger.isDebugEnabled()) {
				logger.debug("insert ProjectInfo [" + instance.getId() + "] is success ...");
			}
		}
		return false;
	} // end_fun

	/**
	 * {初始化项目里程碑数据信息}
	 * 
	 * @param instance
	 */
	private void initProjectStats(ProjectInfo instance) throws BaseException {
		List<String> statsIds = projectDao.queryByParams("DictRecord.query_recordIds_by_sire",
				"3fhxjg94aa1d49c7aadk53e2ref1r434");
		if (!CollectionUtils.isEmpty(statsIds)) {
			for (String id : statsIds) {
				ProjectStats stats = new ProjectStats();
				stats.setStatsId(CommonUtils.getUUIDSeq());
				stats.setMilestoneName(id);
				stats.setProjectId(instance.getProjectId());
				proStatsDao.insert(stats);
			} // end_for
		} // end_fun
	} // end_fun

	private String paramKey = "ProjectInfo";

	private String keyCount;

	public void setKeyCount(String keyCount) {
		this.keyCount = keyCount;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

}
