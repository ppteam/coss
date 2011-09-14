package com.neusoft.model.udmgr.service;

import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.dao.Iface.IEntityDao;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.model.udmgr.dao.pojo.Department;
import com.neusoft.model.udmgr.dao.pojo.ProjectInfo;
import com.neusoft.model.udmgr.dao.pojo.ProjectStats;
import com.neusoft.model.udmgr.dao.pojo.PtransferLogs;
import com.neusoft.model.udmgr.dao.pojo.TeamTransLogs;
import com.neusoft.model.udmgr.dao.pojo.TodoList;
import com.neusoft.model.udmgr.dao.pojo.UserBaseInfo;

/**
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
public abstract class AbstractAdapterFilter extends FilterBase {

	private static final long serialVersionUID = -1661357785545171667L;

	protected IEntityDao<ProjectInfo, String> projectDao;

	protected IEntityDao<UserBaseInfo, String> userBaseDao;

	protected IEntityDao<TeamTransLogs, String> teamTranLogDao;

	protected IEntityDao<PtransferLogs, String> proMemberDao;

	protected IEntityDao<ProjectStats, String> proStatsDao;

	protected IEntityDao<Department, String> deptDao;

	protected IEntityDao<TodoList, String> todoListDao;

	public AbstractAdapterFilter() {
	}

	public void setProjectDao(IEntityDao<ProjectInfo, String> projectDao) {
		this.projectDao = projectDao;
	}

	public void setUserBaseDao(IEntityDao<UserBaseInfo, String> userBaseDao) {
		this.userBaseDao = userBaseDao;
	}

	public void setTeamTranLogDao(IEntityDao<TeamTransLogs, String> teamTranLogDao) {
		this.teamTranLogDao = teamTranLogDao;
	}

	public void setProMemberDao(IEntityDao<PtransferLogs, String> proMemberDao) {
		this.proMemberDao = proMemberDao;
	}

	public void setProStatsDao(IEntityDao<ProjectStats, String> proStatsDao) {
		this.proStatsDao = proStatsDao;
	}

	public void setDeptDao(IEntityDao<Department, String> deptDao) {
		this.deptDao = deptDao;
	}

	public void setTodoListDao(IEntityDao<TodoList, String> todoListDao) {
		this.todoListDao = todoListDao;
	}

	/**
	 * {数据转换}
	 * 
	 * @param baseInfo
	 * @return
	 */
	protected TeamTransLogs initTransLogs(UserBaseInfo baseInfo) {
		TeamTransLogs transLogs = new TeamTransLogs();
		transLogs.setTransferId(CommonUtils.getUUIDSeq());
		transLogs.setDeptId(baseInfo.getDeptId());
		transLogs.setUserId(baseInfo.getId());
		return transLogs;
	}
}
