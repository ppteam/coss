package com.neusoft.core.chain.general;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.exception.BaseException;
import com.springsource.util.common.StringUtils;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {类描述} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Aug 4, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class MergerCollectionFilter extends FilterBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8902535690986258031L;

	public MergerCollectionFilter() {
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
		if (StringUtils.hasLength(keys)) {
			String[] items = keys.split("[|]");
			if ("list".equals(type)) {
				List<Object> mergerList = new ArrayList<Object>();
				List<Object> tempList = null;
				for (String item : items) {
					tempList = (List<Object>) context.getValueByAll(item);
					if (CollectionUtils.isNotEmpty(tempList)) {
						mergerList.addAll(tempList);
					}
				} // end_for
				context.getResultMap().put(resKey, mergerList);
			}
		}
		return false;
	}

	private String keys;
	private String resKey;
	private String type;

	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

}
