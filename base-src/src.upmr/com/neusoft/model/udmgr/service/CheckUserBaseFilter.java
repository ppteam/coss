package com.neusoft.model.udmgr.service;

import java.util.List;

import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.udmgr.dao.pojo.UserBaseInfo;
import com.neusoft.model.udmgr.exception.HandleUserException;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {校验当前用户信息的业务合法性} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class CheckUserBaseFilter extends AbstractAdapterFilter {

	private static final long serialVersionUID = -1661357785545171667L;

	public CheckUserBaseFilter() {
		super();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	@SuppressWarnings("unchecked")
	public boolean execute(IContext context) throws BaseException {
		UserBaseInfo instance = context.getObject("UserBaseInfo", UserBaseInfo.class);
		List<String> userIds = (List<String>) context.getResultMap().get("user_ids_list");
		if (StringUtils.hasLength(instance.getId())) { // 更新
			if (!CollectionUtils.isEmpty(userIds)) {
				for (String id : userIds) {
					if (!id.equals(instance.getId())) {
						throw new HandleUserException("0000", BaseException.EXP_TYPE_EXCPT, null,
								null, null);
					}
				}
			}
		} else {
			if (!CollectionUtils.isEmpty(userIds)) {
				throw new HandleUserException("0000", BaseException.EXP_TYPE_EXCPT, null, null,
						null);
			}
		}
		return false;
	}

}
