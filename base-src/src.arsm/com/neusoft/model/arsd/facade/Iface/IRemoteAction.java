package com.neusoft.model.arsd.facade.Iface;

import com.neusoft.core.web.CallBackModel;
import com.neusoft.model.arsd.dao.pojo.AuthorityInfo;
import com.neusoft.model.arsd.dao.pojo.DictCatalog;
import com.neusoft.model.arsd.dao.pojo.DictRecord;
import com.neusoft.model.arsd.dao.pojo.RoleInfo;
import com.neusoft.model.arsd.dao.pojo.SourceInfo;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {字典管理ajax 接口定义} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 4, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public interface IRemoteAction {

	/**
	 * {新增、修改字典目录}
	 * 
	 * @param catalog
	 *            id:null 新增/ 否则修改
	 * @return CallBackModel
	 */
	CallBackModel saveOrUpdateCatalog(DictCatalog catalog);

	/**
	 * {新增、修改字典目录明细}
	 * 
	 * @param catalog
	 *            id:null 新增/ 否则修改
	 * @return CallBackModel
	 */
	CallBackModel saveOrUpdateRecord(DictRecord record);

	/**
	 * {新增、修改权限}
	 * 
	 * @param author
	 * @return
	 */
	CallBackModel saveOrUpdateAuthor(AuthorityInfo author);

	/**
	 * {新增、修改角色}
	 * 
	 * @param roleInfo
	 * @return
	 */
	CallBackModel saveOrUpdateRole(RoleInfo roleInfo);

	/**
	 * {新增、修改资源信息}
	 * 
	 * @param sourceInfo
	 * @return CallBackModel
	 */
	CallBackModel saveOrUpdatSource(SourceInfo sourceInfo);

	/**
	 * {删除 资源信息}
	 * 
	 * @param sourceInfo
	 * @return CallBackModel
	 */
	CallBackModel removeSource(SourceInfo sourceInfo);

	/**
	 * {针对角色进行授权操作}
	 * 
	 * @param roleId
	 * @param authorIds
	 * @return
	 */
	CallBackModel authorizationAction(String roleId, String[] authorIds);
}
