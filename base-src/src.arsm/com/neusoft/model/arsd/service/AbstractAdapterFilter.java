package com.neusoft.model.arsd.service;

import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.common.Iface.IRefreshSecMetadata;
import com.neusoft.core.dao.Iface.IEntityDao;
import com.neusoft.model.arsd.dao.pojo.AuthorityInfo;
import com.neusoft.model.arsd.dao.pojo.DictCatalog;
import com.neusoft.model.arsd.dao.pojo.DictRecord;
import com.neusoft.model.arsd.dao.pojo.RoleInfo;
import com.neusoft.model.arsd.dao.pojo.SourceInfo;

/**
 * 
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

	protected IEntityDao<DictCatalog, String> catalogDao;

	protected IEntityDao<DictRecord, String> recordDao;

	protected IEntityDao<SourceInfo, String> sourceInfoDao;

	protected IEntityDao<AuthorityInfo, String> authorityDao;

	protected IEntityDao<RoleInfo, String> roleDao;

	protected IRefreshSecMetadata refreshSecMetadata;

	public void setRefreshSecMetadata(IRefreshSecMetadata refreshSecMetadata) {
		this.refreshSecMetadata = refreshSecMetadata;
	}

	public void setRecordDao(IEntityDao<DictRecord, String> recordDao) {
		this.recordDao = recordDao;
	}

	public void setCatalogDao(IEntityDao<DictCatalog, String> catalogDao) {
		this.catalogDao = catalogDao;
	}

	public void setSourceInfoDao(IEntityDao<SourceInfo, String> sourceInfoDao) {
		this.sourceInfoDao = sourceInfoDao;
	}

	public void setAuthorityDao(IEntityDao<AuthorityInfo, String> authorityDao) {
		this.authorityDao = authorityDao;
	}

	public void setRoleDao(IEntityDao<RoleInfo, String> roleDao) {
		this.roleDao = roleDao;
	}

}
