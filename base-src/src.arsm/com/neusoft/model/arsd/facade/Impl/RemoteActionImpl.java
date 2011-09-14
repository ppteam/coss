package com.neusoft.model.arsd.facade.Impl;

import org.apache.log4j.Logger;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.web.AbstractAjaxAction;
import com.neusoft.core.web.CallBackModel;
import com.neusoft.model.arsd.dao.pojo.AuthorityInfo;
import com.neusoft.model.arsd.dao.pojo.DictCatalog;
import com.neusoft.model.arsd.dao.pojo.DictRecord;
import com.neusoft.model.arsd.dao.pojo.RoleInfo;
import com.neusoft.model.arsd.dao.pojo.SourceInfo;
import com.neusoft.model.arsd.facade.Iface.IRemoteAction;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {类描述} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 4, 2011<br>
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
	 * com.neusoft.model.arsd.facade.Iface.IDictionaryAction#saveOrUpdateCatalog
	 * (com.neusoft.model.arsd.dao.pojo.DictCatalog)
	 */
	public CallBackModel saveOrUpdateCatalog(DictCatalog catalog) {
		// init param
		IContext context = creatContext();
		try {
			context.put("instance", catalog);
			executeChain(context, "saveOrupdateCatalog_chain");
		} catch (Exception e) {

		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.arsd.facade.Iface.IDictionaryAction#saveOrUpdateRecord
	 * (com.neusoft.model.arsd.dao.pojo.DictRecord)
	 */
	public CallBackModel saveOrUpdateRecord(DictRecord record) {
		IContext context = creatContext();
		try {
			context.put("instance", record);
			executeChain(context, "saveOrupdateRecord_chain");
		} catch (Exception e) {
			logger.error(e);
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.arsd.facade.Iface.IRemoteAction#saveOrUpdatSource(com
	 * .neusoft.model.arsd.dao.pojo.SourceInfo)
	 */
	public CallBackModel saveOrUpdatSource(SourceInfo sourceInfo) {
		IContext context = creatContext();
		try {
			context.put("instance", sourceInfo);
			executeChain(context, "saveOrupdateSource_chain");
		} catch (Exception e) {
			logger.error(e);
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.arsd.facade.Iface.IRemoteAction#saveOrUpdateAuthor(
	 * com.neusoft.model.arsd.dao.pojo.AuthorityInfo)
	 */
	public CallBackModel saveOrUpdateAuthor(AuthorityInfo author) {
		IContext context = creatContext();
		try {
			context.put("instance", author);
			if ("remove-myself".equals(author.getAuthorSign())) {
				executeChain(context, "remove_Author_chain");
			} else {
				executeChain(context, "saveOrupdateAuthor_chain");
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.arsd.facade.Iface.IRemoteAction#saveOrUpdateRole(com
	 * .neusoft.model.arsd.dao.pojo.RoleInfo)
	 */
	public CallBackModel saveOrUpdateRole(RoleInfo roleInfo) {
		IContext context = creatContext();
		try {
			context.put("instance", roleInfo);
			executeChain(context, "saveOrupdateRole_chain");
		} catch (Exception e) {
			logger.error(e);
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.arsd.facade.Iface.IRemoteAction#authorizationAction
	 * (java.lang.String, java.lang.String[])
	 */
	public CallBackModel authorizationAction(String roleId, String[] authorIds) {
		IContext context = creatContext();
		try {
			context.put("roleId", roleId);
			context.put("authorIds", authorIds);
			executeChain(context, "role_map_author_chain");
		} catch (Exception e) {
			logger.error(e);
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.arsd.facade.Iface.IRemoteAction#removeSource(com.neusoft
	 * .model.arsd.dao.pojo.SourceInfo)
	 */
	public CallBackModel removeSource(SourceInfo sourceInfo) {
		IContext context = creatContext();
		try {
			context.put("instance", sourceInfo);
			executeChain(context, "remove_Source_chain");
		} catch (Exception e) {
			logger.error(e);
		}
		return fillByContext(context);
	}

}
