package com.neusoft.model.reportforms.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;

import com.neusoft.model.report.dao.pojo.DailyReport;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Aug 16, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class OverworkHandler {

	private Date startDate;
	private Date endDate;
	private List<DailyReport> reportList;

	public OverworkHandler(Date startDate, Date endDate, List<DailyReport> reportList) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.reportList = reportList;
	}

	public Map<String, String[]> getOverworkData() {
		Map<String, String[]> excelData = new HashMap<String, String[]>();
		// 循环所有记录，把加班数据放在对应地方
		// 数组存放格式：0.projecName,1.userName,所有加班数据,end.加班汇总
		for (int i = 0, length = reportList.size(); i < length; i++) {
			DailyReport dailyReport = reportList.get(i);
			String key = dailyReport.getProjectID() + dailyReport.getUserID();
			Date reportDate = dailyReport.getReportDate();
			int betweenDays = getDaysBetween(startDate, reportDate);
			// 数据列数，endDate-startDate+1+1(项目名)+1(人名)+1(总计)
			int rows = getDaysBetween(startDate, endDate) + 4;
			// 如果之前已经存在某项目下某人的加班记录
			if (excelData.containsKey(key)) {
				String[] overworkData = excelData.get(key);
				overworkData[betweenDays + 2] = String.valueOf(dailyReport.getWorkHours());
				overworkData[rows - 1] = String.valueOf(Double.parseDouble(overworkData[rows - 1])
						+ new Double(dailyReport.getWorkHours()));
			} else {
				String[] overworkData = new String[rows];
				overworkData[betweenDays + 2] = String.valueOf(dailyReport.getWorkHours());
				overworkData[rows - 1] = String.valueOf(dailyReport.getWorkHours());
				overworkData[0] = dailyReport.getProjectName();
				overworkData[1] = dailyReport.getUserName();
				excelData.put(key, overworkData);
			}
		}
		return excelData;
	}

	public String[] getOverworkTitle() {
		SimpleDateFormat sf = new SimpleDateFormat("M月d日");
		int length = getDaysBetween(startDate, endDate);
		String[] excelData = new String[length + 4];
		excelData[0] = "项目组";
		excelData[1] = "名称";
		excelData[length + 3] = "总计";
		Date thisDate = new Date(startDate.getTime());
		for (int i = 0; i <= length; i++) {
			excelData[i + 2] = sf.format(thisDate);
			thisDate = DateUtils.addDays(thisDate, 1);
		}
		return excelData;
	}

	public void calculateTotalOverwork(Map<String, double[]> excelData) {
		// 循环结束，汇总加班信息
		Set<String> keys = excelData.keySet();
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			double[] overwork = (double[]) excelData.get(iterator.next());
			for (int i = 0, length = overwork.length - 1; i < length; i++) {
				overwork[length] = overwork[length] + overwork[i];
			}
		}
	}

	public int getDaysBetween(Date beginDate, Date endDate) {
		Calendar d1 = Calendar.getInstance();
		d1.setTimeInMillis(beginDate.getTime());
		Calendar d2 = Calendar.getInstance();
		d2.setTimeInMillis(endDate.getTime());
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);// 得到当年的实际天数
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}
}
