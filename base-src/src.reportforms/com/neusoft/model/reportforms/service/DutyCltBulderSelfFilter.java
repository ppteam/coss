package com.neusoft.model.reportforms.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.reportforms.dao.pojo.DutyCollect;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {考勤自我统计} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Aug 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class DutyCltBulderSelfFilter extends FilterBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4172683825309753955L;

	public DutyCltBulderSelfFilter() {
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
		List<DutyCollect> list = (List<DutyCollect>) context.getValueByAll(pamKey);
		if (CollectionUtils.isNotEmpty(list)) {
			DutyCollect total = DutyCollect.getTotalInstance();
			for (DutyCollect item : list) {
				item.builderSelf(true);
				total.setSickDays(item.getSickDays());
				total.setThindDays(item.getThindDays());
				total.setLaborDays(item.getLaborDays());
				total.setHappyDays(item.getHappyDays());
				total.setHomeDays(item.getHomeDays());
				total.setLearnDays(item.getLearnDays());
				total.setLeaveDay(item.getLeaveDay());
				total.setChangeDays(item.getChangeDays());
				total.setDeadDays(item.getDeadDays());
				total.setMarryDays(item.getMarryDays());
			} // end_if
			total.builderSelf(false);
			list.add(total);
		} // end_if
		return false;
	} // end_fun

	private String pamKey;

	public void setPamKey(String pamKey) {
		this.pamKey = pamKey;
	}

}
