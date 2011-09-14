package com.neusoft.core.chain.general;

import org.apache.log4j.Logger;

import java.util.List;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.exception.BaseException;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {遍历节点} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class Result2BackModelHandleFilter extends FilterBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Result2BackModelHandleFilter.class);

	private static final long serialVersionUID = -7602343997875348499L;

	public Result2BackModelHandleFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		if(clear){
			context.getResultMap().clear();
		} // end_if
		if (CollectionUtils.isEmpty(keyList)) {
			logger.warn("please init keyList For Iterator");
		} else {
			for (String key : keyList) {
				context.getResultMap().put(key, context.getValueByAll(key));
			}
		}
		return false;
	}

	private List<String> keyList;

	private boolean clear = false;

	public void setKeyList(List<String> keyList) {
		this.keyList = keyList;
	}

	public void setClear(boolean clear) {
		this.clear = clear;
	}

}
