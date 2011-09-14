package com.neusoft.model.udmgr.facade.Iface;

import com.neusoft.core.web.CallBackModel;
import com.neusoft.model.udmgr.dao.pojo.Department;
import com.neusoft.model.udmgr.dao.pojo.ProjectInfo;
import com.neusoft.model.udmgr.dao.pojo.ProjectStats;
import com.neusoft.model.udmgr.dao.pojo.PtransferLogs;
import com.neusoft.model.udmgr.dao.pojo.TodoList;
import com.neusoft.model.udmgr.dao.pojo.UserAddition;
import com.neusoft.model.udmgr.dao.pojo.UserBaseInfo;

public interface IRemoteAction {

	/**
	 * {用户进出场管理}
	 * 
	 * @param userBaseInfo
	 * @return
	 */
	CallBackModel saveOrUpdateUser(UserBaseInfo userBaseInfo);

	/**
	 * {代办事项实现}
	 * 
	 * @param userBaseInfo
	 * @return
	 */
	CallBackModel saveOrUpdateTodo(TodoList todos);

	/**
	 * {用户离厂操作}
	 * 
	 * @param userId
	 * @return
	 */
	CallBackModel editTeamStats(String userId, int stats);

	/**
	 * {新增、修改用户基本信息}
	 * 
	 * @param userBaseInfo
	 * @param userAddition
	 * @return
	 */
	CallBackModel editUserBaseInfo(UserBaseInfo userBaseInfo, UserAddition userAddition);

	/**
	 * 用户授予角色操作
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleIds
	 *            授予的角色列表
	 * @return CallBackModel
	 */
	CallBackModel editUserMapRole(String userId, String[] roleIds);

	/**
	 * {数据集授权操作}
	 * 
	 * @param userId
	 * @param depts
	 * @return
	 */
	CallBackModel editAccDataSet(String userId, String[] depts);

	/**
	 * {新增、修改 项目基本信息}
	 * 
	 * @param projectInfo
	 * @return
	 */
	CallBackModel editProjectInfo(ProjectInfo projectInfo);

	/**
	 * {项目组成员管理}
	 * 
	 * @param ptransferLogs
	 * @return
	 */
	CallBackModel editProjectMember(PtransferLogs ptransferLogs);

	/**
	 * {项目里程碑}
	 * 
	 * @param ptransferLogs
	 * @return
	 */
	CallBackModel editProjectMilestone(ProjectStats ptransferLogs);

	/**
	 * {重置密码}
	 * 
	 * @param userId
	 * @param oldPsd
	 * @param newPsd
	 * @return
	 */
	CallBackModel resetPassWord(String oldPsd, String newPsd, String userId);

	/**
	 * {新增、修改、禁用部门操作}
	 * 
	 * @param instace
	 * @param action
	 *            0/1/-1 修改/新增/禁用
	 * @return
	 */
	CallBackModel insertOrUpdateDept(Department instace, int action);

	/**
	 * 同步当前服务器时间
	 * 
	 * @param format
	 *            格式化字符串
	 * @return
	 */
	String sycDateTime(String format);
}
