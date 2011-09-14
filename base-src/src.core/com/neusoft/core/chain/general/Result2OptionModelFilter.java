package com.neusoft.core.chain.general;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.web.ComboxModel;
import com.springsource.util.common.CollectionUtils;
import com.springsource.util.common.StringUtils;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {初始化默认的基础数据} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 14, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class Result2OptionModelFilter extends FilterBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Result2OptionModelFilter.class);

	private static final long serialVersionUID = -3585915843994282347L;

	public Result2OptionModelFilter() {
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
		List<ComboxModel> oriList = new ArrayList<ComboxModel>();
		List<ComboxModel> resList = new ArrayList<ComboxModel>();
		if (!CollectionUtils.isEmpty(keys)) {
			for (String key : keys) {
				if (context.getResultMap().get(key) != null) {
					oriList.addAll((List<ComboxModel>) context.getResultMap().get(key));
				}
			}
		}
		if (!CollectionUtils.isEmpty(oriList)) {
			Map<String, ComboxModel> _map = new HashMap<String, ComboxModel>();
			for (ComboxModel model : oriList) {
				if (!_map.containsKey(model.getStorName())) {
					_map.put(model.getStorName(), new ComboxModel(model.getStorName()));
				}
				_map.get(model.getStorName()).getItems().add(model);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("package ComboxModel size is [" + _map.size() + "]");
			}

			Set<Entry<String, ComboxModel>> entrys = _map.entrySet();
			for (Entry<String, ComboxModel> entry : entrys) {
				resList.add(entry.getValue());
			}
		}
		// 填充必须输出项
		if (StringUtils.hasLength(fixedKeys)) {
			String[] fixed = fixedKeys.split(split);
			for (String val : fixed) {
				boolean matched = false;
				for (ComboxModel md : resList) {
					if (val.equals(md.getStorName())) {
						matched = true;
						break;
					}
				}
				if (!matched) {
					if (logger.isDebugEnabled()) {
						logger.debug("[ComboxModel<val> is init for null value]");
					}
					resList.add(new ComboxModel(val));
				}
			} // end_for
		}
		if (CollectionUtils.isEmpty(resList)) {
			ComboxModel def = new ComboxModel("undifined_data");
			resList.add(def);
		}
		context.getResultMap().put("optionData", resList);
		return false;
	}

	private List<String> keys;

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

	private String fixedKeys;

	private String split = "[|]";

	public void setFixedKeys(String fixedKeys) {
		this.fixedKeys = fixedKeys;
	}

	public void setSplit(String split) {
		this.split = split;
	}

}
