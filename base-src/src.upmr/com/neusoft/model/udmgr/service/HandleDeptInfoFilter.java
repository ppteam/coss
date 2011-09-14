package com.neusoft.model.udmgr.service;

import org.apache.log4j.Logger;

import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.model.udmgr.dao.pojo.Department;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {部门树增、删、该 操作} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class HandleDeptInfoFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HandleDeptInfoFilter.class);

	private static final long serialVersionUID = -1661357785545171667L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		int action = context.getInteger(actionKey);
		Department department = context.getObject(instanceKey, Department.class);
		Assert.notNull(department, "[key:" + instanceKey + "] mapping value from context can't be null");
		switch (action) {
		case 0: // 修改本节点信息
			int val_0 = deptDao.update("updateByWeb", department);
			context.put("update", val_0);
			break;
		case -1: // 禁用该节点以及全部子节点
			Department fathor_0 = deptDao.get(department.getFatherId());
			StringBuffer value = new StringBuffer(fathor_0.getNodeXpath()).append("-").append(department.getDeptId())
					.append("%");
			int val_1 = deptDao.update("disabled_nodes_Bypath", value.toString());
			context.put("update", val_1);
			long cnt = deptDao.queryForCount("check_has_child", fathor_0.getDeptId());
			if (logger.isDebugEnabled()) {
				logger.debug("check_has_child  res is  [" + cnt + "]");
			}
			if (cnt == 0) {
				fathor_0.setLeafNode(0);
				deptDao.updateById(fathor_0);
			}
			break;
		case 1:// 新增字节点
			Department fathor = deptDao.get(department.getFatherId());
			department.setDeptId(CommonUtils.getUUIDSeq());
			department.setNodeXpath(fathor.getNodeXpath() + '-' + department.getDeptId());
			department.setNodeDeep(fathor.getNodeDeep() + 1);
			deptDao.insert(department);
			if (fathor.getLeafNode() == 0) {
				fathor.setLeafNode(1);
				deptDao.updateById(fathor);
			}
			context.put("insert", department.getId());
			break;
		default:
			logger.error("err param for action[0,1,-1] but it's value is [" + action + "]");
			break;
		}
		return false;
	} // end_fun

	private String actionKey = "action";
	private String instanceKey = "instance";

	public void setActionKey(String actionKey) {
		this.actionKey = actionKey;
	}

	public void setInstanceKey(String instanceKey) {
		this.instanceKey = instanceKey;
	}

}
