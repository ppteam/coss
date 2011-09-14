package com.neusoft.model.eweekreport.service;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.core.web.LoginUser;
import com.neusoft.model.eweekreport.dao.pojo.EWeekReport;
import com.neusoft.model.eweekreport.exception.EWeekReportException;
import com.neusoft.model.udmgr.dao.pojo.UserBaseInfo;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {新增、修改、删除周报信息} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Apr 19, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class SaveOrUpdateEWeekReport extends AbstractEWeekReport {

	private static final long serialVersionUID = 3651210578081468795L;

	private static final Logger logger = Logger.getLogger(SaveOrUpdateEWeekReport.class);

	public SaveOrUpdateEWeekReport() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		EWeekReport instance = context.getObject(keyInstance, EWeekReport.class);
		instance.setRecordorID(context.get(LoginUser.KEY_USERID).toString());
		Integer count = (Integer) context.getValueByAll(keyCount);
		if (StringUtils.hasLength(instance.getId())) {
			if (count > 1) {
				throw new EWeekReportException("0002", BaseException.EXP_TYPE_EXCPT, "data error", null, null);
			}
			int rows = eWeekReportDao.updateById(instance);
			if (logger.isDebugEnabled()) {
				logger.debug("update eWeekReport and res [" + rows + "]");
			}
			context.putResults(getDefResultKey(), rows);
		} else {
			if (count != 0) {
				throw new EWeekReportException("0002", BaseException.EXP_TYPE_EXCPT, "data error", null, null);
			}
			instance.setWreportID(CommonUtils.getUUIDSeq());
			instance.setDeptID(context.getObject(LoginUser.KEY_USER_BAEN, UserBaseInfo.class).getDeptId());
			String wreportID = eWeekReportDao.insert(instance);
			if (logger.isDebugEnabled()) {
				logger.debug("insert eWeekReport and res [" + wreportID + "]");
			}
			context.putResults(getDefResultKey(), wreportID);
		}
		return false;
	}

	private String keyCount;

	private String keyInstance;

	public void setKeyCount(String keyCount) {
		this.keyCount = keyCount;
	}

	public void setKeyInstance(String keyInstance) {
		this.keyInstance = keyInstance;
	}

}
