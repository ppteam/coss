package com.neusoft.model.udmgr.service;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.model.udmgr.dao.pojo.TeamTransLogs;
import com.neusoft.model.udmgr.dao.pojo.UserBaseInfo;
import com.neusoft.model.udmgr.exception.HandleUserException;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {解析上传文件 并且生成对应的数据} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Aug 25, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class CoverByts2UserFilter extends FilterBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CoverByts2UserFilter.class);

	private static final long serialVersionUID = -5399105771701404639L;

	public CoverByts2UserFilter() {
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
		byte[] bts = (byte[]) context.get(key);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bts);
		try {
			List<String> lines = IOUtils.readLines(byteArrayInputStream);
			if (logger.isDebugEnabled()) {
				logger.debug("this text has line size [" + lines.size() + "] ");
			}
			List<UserBaseInfo> usersList = new ArrayList<UserBaseInfo>();
			List<TeamTransLogs> logsList = new ArrayList<TeamTransLogs>();
			List<Map<String, String>> umrList = new ArrayList<Map<String, String>>();
			String[] depts = configuration.getString("test.depts.list").split("[|]");
			String[] partys = configuration.getString("test.party.list").split("[|]");
			String[] roles = configuration.getString("test.roles.list").split("[|]");
			for (String line : lines) {
				if (!line.startsWith("#")) {
					usersList.add(creatUser(line, depts, partys, roles, umrList, logsList));
				} // end_if
			} // end_for
			if (logger.isDebugEnabled()) {
				logger.debug("this batch has [" + usersList.size() + "] user will be import to system");
			}
			context.put("userList", usersList);
			context.put("logsList", logsList);
			context.put("userMapRole", umrList);
		} catch (IOException e) {
			throw new HandleUserException("0002", HandleUserException.EXP_TYPE_EXCPT, "IO Error", e, null);
		}
		return false;
	} // end_fun

	/**
	 * 
	 * @param line
	 * @return
	 * @throws HandleUserException
	 */
	private UserBaseInfo creatUser(String line, String[] depts, String[] partys, String[] roles,
			List<Map<String, String>> umrList, List<TeamTransLogs> logsList) throws HandleUserException {
		UserBaseInfo user = null;
		String[] items = line.trim().split("[|]");
		try {
			user = new UserBaseInfo();
			user.setUserId(CommonUtils.getUUIDSeq());
			user.setLoginId(items[0]);
			user.setPassword(DigestUtils.md5Hex(items[0]));
			user.setName(items[1]);
			user.setEnabled(1);
			user.setTeamStats(1);
			user.setCompanyName("东软(广州)");
			user.setIpAddress("255.255.255.255");
			user.setSexed(NumberUtils.createInteger(items[3]));
			user.setDeptId(depts[NumberUtils.createInteger(items[2])]);
			user.setPostType(partys[NumberUtils.createInteger(items[4])]);
			if (items.length == 7) {
				user.setWorkPlace(items[6]);
			} // end_if
			Map<String, String> map = new HashMap<String, String>();
			map.put("mappId", user.getId());
			map.put("userId", user.getId());
			map.put("roleId", roles[NumberUtils.createInteger(items[5])]);
			// logs
			TeamTransLogs logs = new TeamTransLogs();
			logs.setDeptId(user.getDeptId());
			logs.setUserId(user.getId());
			logs.setTransferId(user.getId());
			umrList.add(map);
		} catch (Exception e) {
			throw new HandleUserException("0002", HandleUserException.EXP_TYPE_EXCPT, "text error", e,
					new String[] { items[1] });
		}
		return user;
	} // end_fun

	public static void main(String[] args) {
		String asd = "example|模拟用户|1|1|0|0|NULL";
		System.out.println(asd.split("[|]").length);
	}

	private String key;

	// <property name="configuration" ref="configurationImpl" />
	private Configuration configuration;

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
