package com.neusoft.model.eweekreport.service;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.general.SqlMapClientSupportFilter;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.web.ComboxModel;
import com.neusoft.core.web.LoginUser;
import com.springsource.util.common.StringUtils;

/**
 * {初始化个人周报列表 查询查询参数 节点}
 * 
 * @author lsj
 * 
 */
public class InitQueryCondtionFilter extends SqlMapClientSupportFilter {

	private static final long serialVersionUID = 5242492865723135596L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	@SuppressWarnings("unchecked")
	public boolean execute(IContext context) throws BaseException {
		String userId = context.getString("userId");
		String projectId = context.getString("projectId");
		List<String> dateSet = (List<String>) context
				.get(LoginUser.KEY_DATASET);// 是否有数据集权限
		List<String> rolesType = (List<String>) context
				.get(LoginUser.KEY_ROLES);// 和项目有关的角色
		List<String> list = new ArrayList<String>();
		if (StringUtils.hasLength(userId)) {
			list.add(userId);
			context.put("userIDT", list);
		} else {
			if (StringUtils.hasLength(projectId)) {
				List<ComboxModel> res = sqlMapClientTemplate.queryForList(
						"PtransferLogs.query_userlist_by_projectId", projectId);
				if (dateSet.size() == 1) {// 没有数据集权限
					if (rolesType
							.contains(/* 项目组长 */"c401a1050fe64b29ba60997e2a576d52")) {
						if (res.size() > 0) {
							for (ComboxModel com : res) {
								list.add(com.getRegValue());
							}
						} else {
							list.add("nobody");
						}
					} else if (rolesType
							.contains(/* 员工 */"517a4e05b5d44b5096e9b3bd04bec654")) {
						list.add(context.getString(LoginUser.KEY_USERID));
					}
				} else {// 有数据集权限
					if (res.size() > 0) {
						for (ComboxModel com : res) {
							list.add(com.getRegValue());
						}
					} else {
						list.add("nobody");
					}
				}
				context.put("userIDT", list);
			} else {
				list.add(context.getString(LoginUser.KEY_USERID));
				context.put("userIDT", list);
			}
		}
		return false;
	}
}
