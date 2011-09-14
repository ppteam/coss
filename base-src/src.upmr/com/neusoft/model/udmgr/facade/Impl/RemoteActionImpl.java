package com.neusoft.model.udmgr.facade.Impl;

import org.apache.log4j.Logger;

import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.util.DateUtil;
import com.neusoft.core.web.AbstractAjaxAction;
import com.neusoft.core.web.CallBackModel;
import com.neusoft.model.udmgr.dao.pojo.Department;
import com.neusoft.model.udmgr.dao.pojo.ProjectInfo;
import com.neusoft.model.udmgr.dao.pojo.ProjectStats;
import com.neusoft.model.udmgr.dao.pojo.PtransferLogs;
import com.neusoft.model.udmgr.dao.pojo.TodoList;
import com.neusoft.model.udmgr.dao.pojo.UserAddition;
import com.neusoft.model.udmgr.dao.pojo.UserBaseInfo;
import com.neusoft.model.udmgr.facade.Iface.IRemoteAction;
import com.springsource.util.common.StringUtils;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {用户信息管理} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class RemoteActionImpl extends AbstractAjaxAction implements IRemoteAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RemoteActionImpl.class);

	public RemoteActionImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.udmgr.facade.Iface.IRemoteAction#editUserBaseInfo(com
	 * .neusoft.model.udmgr.dao.pojo.UserBaseInfo,
	 * com.neusoft.model.udmgr.dao.pojo.UserAddition)
	 */
	public CallBackModel editUserBaseInfo(UserBaseInfo userBaseInfo, UserAddition userAddition) {
		IContext context = creatContext();
		try {
			Assert.notNull(userBaseInfo, "[p:userBaseInfo] is not null");
			context.put("instance", userBaseInfo);
			executeChain(context, "edit_user_BaseInfo_chain");
		} catch (Exception e) {
			logger.error(e);
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.udmgr.facade.Iface.IRemoteAction#editProjectInfo(com
	 * .neusoft.model.udmgr.dao.pojo.ProjectInfo)
	 */
	public CallBackModel editProjectInfo(ProjectInfo projectInfo) {
		IContext context = creatContext();
		try {
			Assert.notNull(projectInfo, "[p:projectInfo] is not null");
			context.put("ProjectInfo", projectInfo);
			executeChain(context, "save_update_ProjectInfo_chain");
		} catch (Exception e) {
			logger.error(e);
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.udmgr.facade.Iface.IRemoteAction#resetPassWord(java
	 * .lang.String, java.lang.String, java.lang.String)
	 */
	public CallBackModel resetPassWord(String oldPsd, String newPsd, String userId) {
		IContext context = creatContext();
		try {
			if (StringUtils.hasLength(userId)) {
				context.put("userId", userId);
			} else {
				context.put("userId", getLoginUser().getUserBase().getUserId());
			}
			context.put("oldPsd", oldPsd);
			context.put("newPsd", newPsd);
			executeChain(context, "change_passwd_chain");
		} catch (Exception e) {

		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.udmgr.facade.Iface.IRemoteAction#editProjectMember(
	 * com.neusoft.model.udmgr.dao.pojo.PtransferLogs)
	 */
	public CallBackModel editProjectMember(PtransferLogs ptransferLogs) {
		IContext context = creatContext();
		try {
			Assert.notNull(ptransferLogs, "[p:ptransferLogs] is not null");
			context.put("instance", ptransferLogs);
			executeChain(context, "edit_project_member_chain");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.udmgr.facade.Iface.IRemoteAction#editProjectMilestone
	 * (com.neusoft.model.udmgr.dao.pojo.ProjectStats)
	 */
	public CallBackModel editProjectMilestone(ProjectStats projectStats) {
		IContext context = creatContext();
		try {
			Assert.notNull(projectStats, "[p:ptransferLogs] is not null");
			context.put("instance", projectStats);
			executeChain(context, "edit_projectStats_chain");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.udmgr.facade.Iface.IRemoteAction#editUserMapRole(java
	 * .lang.String, java.lang.String[])
	 */
	public CallBackModel editUserMapRole(String userId, String[] roleIds) {
		IContext context = creatContext();
		try {
			Assert.hasLength(userId, "[userId] is not null");
			context.put("userId", userId);
			context.put("roleIds", roleIds);
			executeChain(context, "edit_user_map_role_chain");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.udmgr.facade.Iface.IRemoteAction#editAccDataSet(java
	 * .lang.String, java.lang.String[])
	 */
	public CallBackModel editAccDataSet(String userId, String[] deptIds) {
		IContext context = creatContext();
		try {
			Assert.hasLength(userId, "[userId] is not null");
			context.put("userId", userId);
			context.put("deptIds", deptIds);
			executeChain(context, "edit_access_data_power_chain");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.udmgr.facade.Iface.IRemoteAction#saveOrUpdateUser(com
	 * .neusoft.model.udmgr.dao.pojo.UserBaseInfo)
	 */
	public CallBackModel saveOrUpdateUser(UserBaseInfo userBaseInfo) {
		IContext context = creatContext();
		try {
			Assert.notNull(userBaseInfo, "[p:userBaseInfo] is not null");
			context.put("UserBaseInfo", userBaseInfo);
			executeChain(context, "insert_user_BaseInfo_chain");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.udmgr.facade.Iface.IRemoteAction#editTeamStats(java
	 * .lang.String, int)
	 */
	public CallBackModel editTeamStats(String userId, int stats) {
		IContext context = creatContext();
		try {
			Assert.hasLength(userId, "[p:userId] is not null");
			context.put("userId", userId);
			switch (stats) {
			case 0:
				executeChain(context, "user_leave_team_chain");
				break;
			case 1:
				executeChain(context, "user_rejoin_team_chain");
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.udmgr.facade.Iface.IRemoteAction#insertOrUpdateDept
	 * (com.neusoft.model.udmgr.dao.pojo.Department, int)
	 */
	public CallBackModel insertOrUpdateDept(Department instace, int action) {
		IContext context = creatContext();
		context.put("instance", instace);
		context.put("action", action);
		try {
			executeChain(context, "handle_dept_info_chain");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.udmgr.facade.Iface.IRemoteAction#saveOrUpdateTodo(com
	 * .neusoft.model.udmgr.dao.pojo.TodoList)
	 */
	public CallBackModel saveOrUpdateTodo(TodoList todos) {
		IContext context = creatContext();
		try {
			Assert.notNull(todos, "[p:todos] is not null");
			context.put("instance", todos);
			initContext(context);
			executeChain(context, "handle_todoList_chain");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.udmgr.facade.Iface.IRemoteAction#sycDateTime(java.lang
	 * .String)
	 */
	public String sycDateTime(String format) {
		return String.valueOf(DateUtil.getTimeInMillis());
	}

}
