package com.neusoft.model.reportforms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.DateUtil;
import com.neusoft.model.report.dao.pojo.DailyReport;
import com.neusoft.model.reportforms.dao.pojo.DutyCollect;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {处理当前逻辑} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Aug 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class AssemblingDutyCltFilter extends FilterBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4172683825309753938L;

	public AssemblingDutyCltFilter() {
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
		List<DailyReport> reports = (List<DailyReport>) context.getValueByAll(pamKey);
		List<DutyCollect> srcList = new ArrayList<DutyCollect>();
		if (CollectionUtils.isNotEmpty(reports)) {
			for (DailyReport dailyReport : reports) {
				mergerDutyClt(srcList, dailyReport);
			} // end_if
		} // end_if
		context.getResultMap().put(resKey, srcList);
		return false;
	} // end_fun

	private void mergerDutyClt(List<DutyCollect> srcList, DailyReport dailyReport) {
		boolean isMatched = false;
		if (CollectionUtils.isEmpty(srcList)) {
			srcList.add(builder(dailyReport));
		} else {
			for (DutyCollect item : srcList) {
				if (item.getUserId().equals(dailyReport.getUserID()) && item.getWkofYear() == dailyReport.getWkOfYear()) {
					routeDays(item, dailyReport);
					isMatched = true;
					break;
				}
			}
			if (!isMatched) {
				srcList.add(builder(dailyReport));
			}
		}
	}

	/**
	 * 日报 转换 为 制定格式的考勤建模
	 * 
	 * @param report
	 * @return
	 */
	private DutyCollect builder(DailyReport report) {
		DutyCollect res = DutyCollect.getInstance();
		res.setUserId(report.getUserID());
		res.setUserName(report.getUserName());
		res.setProjectId(report.getProjectID());
		res.setProjectName(report.getProjectName());
		res.setWkofYear(report.getWkOfYear());
		res.setWeekStart(report.getWeekStart());
		res.setWeekEnd(report.getWeekEnd());
		routeDays(res, report);
		return res;
	} // end_fun

	private void builderComt(DutyCollect target, DailyReport report, String jiaqi) {
		target.setCmtBuffer(jiaqi + DateUtil.fromatDate(report.getReportDate(), "MM-dd"));
	}

	/**
	 * {按照分类 分别绘制事件}
	 * 
	 * @param target
	 * @param report
	 */
	private void routeDays(DutyCollect target, DailyReport report) {
		if ("45670000000000000000000000000001".equals(report.getBegType())) {
			target.setSickDays(report.getWorkHours());
			builderComt(target, report, "病假:");
		} else if ("45670000000000000000000000000002".equals(report.getBegType())) {
			target.setThindDays(report.getWorkHours());
			builderComt(target, report, "事假:");
		} else if ("45670000000000000000000000000003".equals(report.getBegType())) {
			target.setMarryDays(report.getWorkHours());
			builderComt(target, report, "婚假:");
		} else if ("45670000000000000000000000000004".equals(report.getBegType())) {
			target.setDeadDays(report.getWorkHours());
			builderComt(target, report, "丧假:");
		} else if ("45670000000000000000000000000005".equals(report.getBegType())) {
			target.setLaborDays(report.getWorkHours());
			builderComt(target, report, "产假:");
		} else if ("45670000000000000000000000000011".equals(report.getBegType())) {
			target.setChangeDays(report.getWorkHours());
			builderComt(target, report, "调休:");
		} else if ("45670000000000000000000000000006".equals(report.getBegType())) {
			target.setHappyDays(report.getWorkHours());
			builderComt(target, report, "年假:");
		} else if ("45670000000000000000000000000007".equals(report.getBegType())) {
			target.setHomeDays(report.getWorkHours());
			builderComt(target, report, "探亲假:");
		} else if ("45670000000000000000000000000010".equals(report.getBegType())) {
			target.setLearnDays(report.getWorkHours());
			builderComt(target, report, "培训:");
		}
	}

	private String pamKey;

	private String resKey;

	public void setPamKey(String pamKey) {
		this.pamKey = pamKey;
	}

	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

}
