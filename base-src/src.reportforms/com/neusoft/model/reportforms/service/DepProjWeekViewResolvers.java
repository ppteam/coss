package com.neusoft.model.reportforms.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.util.DateUtil;
import com.neusoft.model.pwreport.dao.pojo.PweeklyMemberplan;
import com.neusoft.model.pwreport.dao.pojo.PweeklyProblems;
import com.neusoft.model.pwreport.dao.pojo.PweeklyReport;
import com.neusoft.model.report.dao.pojo.DailyReport;
import com.neusoft.model.udmgr.dao.pojo.ProjectStats;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b> 项目周报报表<br>
 * <b>Copyright:</b>Copyright &copy; 2011 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2011-2-14<br>
 * 
 * @author xiaoshuaiping e-mail:xiaoshp@neusoft.com
 * @version $Revision$
 */

public class DepProjWeekViewResolvers extends AbstractXlsViewResolvers {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DepProjWeekViewResolvers.class);

	/**
	 * {项目周报报表模板填写数据}
	 * 
	 * @param map
	 * @param workbook
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author: xiaoshuaiping
	 */
	protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		IContext context = (IContext) map.get("IContext");
		initCellStyle(workbook.getSheetAt(0));
		fillSheetOne(workbook.getSheetAt(0), context);
		fillSheetTwo(workbook.getSheetAt(1), context);
	}

	/**
	 * {填写 Sheet one 数据信息}
	 * 
	 * @param workbook
	 * @param context
	 */
	private void fillSheetOne(HSSFSheet sheet, IContext context) {
		if (logger.isDebugEnabled()) {
			logger.debug("fillSheetOne(HSSFSheet, IContext) - start");
		}
		PweeklyReport report = (PweeklyReport) context.getValueByAll("PweeklyReport");
		// title fill in
		StringBuffer title_buf = new StringBuffer("项目周报 (")
				.append(DateUtil.fromatDate(report.getStartDate(), "yyyy/MM/dd")).append(" - ")
				.append(DateUtil.fromatDate(report.getEndDate(), "yyyy/MM/dd")).append(")");
		sheet.getRow(1).getCell(1).setCellValue(title_buf.toString());
		// table title info
		getCell(sheet, 2, 1).setCellValue("文档编号：" + report.getReportNo());
		getCell(sheet, 2, 7).setCellValue(report.getUserId());
		getCell(sheet, 2, 11).setCellValue(DateUtil.fromatDate(report.getRecordDate(), null));
		// table
		getCell(sheet, 3, 2).setCellValue(report.getProjectName());
		getCell(sheet, 3, 11).setCellValue(report.getPsmerId());
		getCell(sheet, 3, 13).setCellValue(report.getPsmName());
		//
		getCell(sheet, 5, 3).setCellValue(report.getProjectDesc());
		getCell(sheet, 5, 9).setCellValue(report.getResultsShow());
		getCell(sheet, 5, 2).setCellValue(report.getEfficiencyExecute());
		getCell(sheet, 6, 2).setCellValue(report.getHumanResource());
		getCell(sheet, 7, 2).setCellValue(report.getRequirementAlter());
		getCell(sheet, 8, 2).setCellValue(report.getClientRelation());
		// 里程碑 数据填写
		@SuppressWarnings("unchecked")
		List<ProjectStats> projectStats = (List<ProjectStats>) context.getValueByAll("ProjectStats");
		int seq_Stats = 0;
		if (CollectionUtils.isNotEmpty(projectStats)) {
			for (ProjectStats data : projectStats) {
				if (seq_Stats == 0) {
					// 项目启动时间
					getCell(sheet, 3, 8).setCellValue(handleDate(data.getPlanStart()));
				} // end_if
				setText(getCell(sheet, 11 + seq_Stats, 2), handleDate(data.getPlanStart()));
				setText(getCell(sheet, 11 + seq_Stats, 4), handleDate(data.getPlanEnd()));
				setText(getCell(sheet, 11 + seq_Stats, 6), handleDate(data.getRealityStart()));
				setText(getCell(sheet, 11 + seq_Stats, 8), handleDate(data.getRealityEnd()));
				setText(getCell(sheet, 11 + seq_Stats, 10), data.getMilestomeStats());
				setText(getCell(sheet, 11 + seq_Stats, 11), data.getPlanVersion());
				seq_Stats++;
			}
		}
		// 计划跟踪
		getCell(sheet, 22, 1).setCellValue(report.getCweekPlan() == null ? "无" : report.getCweekPlan());
		getCell(sheet, 22, 5).setCellValue(report.getCompletePlan() == null ? "无" : report.getCompletePlan());
		getCell(sheet, 22, 10).setCellValue(report.getNweekPlan() == null ? "无" : report.getNweekPlan());
		// 主要问题
		@SuppressWarnings("unchecked")
		List<PweeklyProblems> problems = (List<PweeklyProblems>) context.getValueByAll("Problems");
		int startRowNum = 29;
		if (CollectionUtils.isNotEmpty(problems)) {
			for (PweeklyProblems data : problems) {
				formatRow(sheet, startRowNum, 1, 13, left_stype);
				sheet.addMergedRegion(new CellRangeAddress(startRowNum, startRowNum, 3, 6));
				sheet.addMergedRegion(new CellRangeAddress(startRowNum, startRowNum, 7, 10));
				sheet.addMergedRegion(new CellRangeAddress(startRowNum, startRowNum, 11, 12));
				setText(getCell(sheet, startRowNum, 1), DateUtil.fromatDate(data.getDiscoverDate(), null));
				setText(getCell(sheet, startRowNum, 2), data.getUserName());
				setText(getCell(sheet, startRowNum, 3), data.getProblemDesc());
				setText(getCell(sheet, startRowNum, 7), data.getResolveWay());
				setText(getCell(sheet, startRowNum, 11), DateUtil.fromatDate(data.getSolveDate(), null));
				setText(getCell(sheet, startRowNum, 13), data.getResposibler());
				startRowNum++;
			}
		} else {
			formatRow(sheet, startRowNum, 1, 13, center_stype);
			sheet.addMergedRegion(new CellRangeAddress(startRowNum, startRowNum, 1, 13));
			sheet.getRow(startRowNum).createCell(1).setCellValue("暂无问题描述");
			startRowNum += 1;
		}

		// 成员工作总结
		formatRow(sheet, startRowNum, 1, 13, l_title_stype);
		sheet.addMergedRegion(new CellRangeAddress(startRowNum, startRowNum, 1, 13));
		setText(getCell(sheet, startRowNum, 1), "项目成员工作总结");

		startRowNum += 1;
		formatRow(sheet, startRowNum, 1, 13, n_title_stype);
		sheet.addMergedRegion(new CellRangeAddress(startRowNum, startRowNum, 3, 8));
		sheet.addMergedRegion(new CellRangeAddress(startRowNum, startRowNum, 9, 13));
		setText(getCell(sheet, startRowNum, 1), "姓名");
		setText(getCell(sheet, startRowNum, 2), "部门");
		setText(getCell(sheet, startRowNum, 3), "本周工作");
		setText(getCell(sheet, startRowNum, 9), "下周计划");

		@SuppressWarnings("unchecked")
		List<PweeklyMemberplan> memberplans = (List<PweeklyMemberplan>) context.getValueByAll("Memberplans");
		startRowNum += 1;
		if (CollectionUtils.isNotEmpty(memberplans)) {
			for (PweeklyMemberplan pl : memberplans) {
				formatRow(sheet, startRowNum, 1, 13, left_stype);
				sheet.addMergedRegion(new CellRangeAddress(startRowNum, startRowNum, 3, 8));
				sheet.addMergedRegion(new CellRangeAddress(startRowNum, startRowNum, 9, 13));
				setText(getCell(sheet, startRowNum, 1), pl.getUserName());
				getCell(sheet, startRowNum, 1).setCellStyle(center_stype);
				setText(getCell(sheet, startRowNum, 2), pl.getBranchName());
				getCell(sheet, startRowNum, 2).setCellStyle(center_stype);
				setText(getCell(sheet, startRowNum, 3), pl.getWeeklySummary());
				setText(getCell(sheet, startRowNum, 9), pl.getWeeklyPlan());
				startRowNum += 1;
			}
		} else {
			formatRow(sheet, startRowNum, 1, 13, center_stype);
			sheet.addMergedRegion(new CellRangeAddress(startRowNum, startRowNum, 1, 13));
			setText(getCell(sheet, startRowNum, 1), "无成员计划");
			startRowNum += 1;
		}

		formatRow(sheet, startRowNum, 1, 13, l_title_stype);
		sheet.addMergedRegion(new CellRangeAddress(startRowNum, startRowNum, 1, 13));
		setText(getCell(sheet, startRowNum, 1), "项目负责人总体评价");
		startRowNum += 1;

		for (int i = 0; i < 5; i++) {
			formatRow(sheet, startRowNum + i, 1, 13, left_stype);
		}
		sheet.addMergedRegion(new CellRangeAddress(startRowNum, startRowNum + 4, 1, 13));
		setText(getCell(sheet, startRowNum, 1), report.getLeaderAppraise());
		if (logger.isDebugEnabled()) {
			logger.debug("fillSheetOne(HSSFSheet, IContext) - end");
		}
	}

	/**
	 * {格式化字符串}
	 * 
	 * @param days
	 * @return
	 */
	private double formatNum(int days) {
		if (days == 0) {
			return 0.00D;
		} else {
			double res = days / 100.00D;
			return res;
		}
	}

	/**
	 * {填写Sheet2 数据信息}
	 * 
	 * @param workbook
	 * @param context
	 */
	private void fillSheetTwo(HSSFSheet sheet, IContext context) {
		if (logger.isDebugEnabled()) {
			logger.debug("fillSheetTwo(HSSFSheet, IContext) - start");
		}
		PweeklyReport report = (PweeklyReport) context.getValueByAll("PweeklyReport");
		@SuppressWarnings("unchecked")
		List<DailyReport> dailyReports = (List<DailyReport>) context.getValueByAll("DailyReports");
		setText(getCell(sheet, 1, 1), "[" + report.getProjectName() + "]工作量明细");
		if (CollectionUtils.isNotEmpty(dailyReports)) {
			if (logger.isDebugEnabled()) {
				logger.debug("fillSheetTwo(HSSFSheet, IContext) - dailyReports size is ["
						+ dailyReports.size() + "]");
			}
			int seq = 0;
			int row = 0;
			for (DailyReport date : dailyReports) {
				row = 6 + seq;
				formatRow(sheet, row, 1, 13, left_stype);
				setText(getCell(sheet, row, 1), String.valueOf(seq + 1));
				getCell(sheet, row, 1).setCellStyle(right_stype);
				setText(getCell(sheet, row, 2), report.getProjectName());
				setText(getCell(sheet, row, 3), DateUtil.fromatDate(date.getReportDate(), null));
				getCell(sheet, row, 3).setCellStyle(center_stype);
				setText(getCell(sheet, row, 4), DateUtil.fromatDate(date.getReportDate(), "E"));
				setText(getCell(sheet, row, 5), date.getUserID());
				setNumber(getCell(sheet, row, 6), formatNum(date.getWorkHours()));
				getCell(sheet, row, 6).setCellStyle(right_stype);
				setText(getCell(sheet, row, 7), date.getWorkType());
				getCell(sheet, row, 7).setCellStyle(center_stype);
				setText(getCell(sheet, row, 8), date.getWorkActivity());
				getCell(sheet, row, 8).setCellStyle(center_stype);
				setText(getCell(sheet, row, 9), date.getWorkSubActivity());
				getCell(sheet, row, 9).setCellStyle(center_stype);
				setText(getCell(sheet, row, 10), date.getWorkContent());
				setText(getCell(sheet, row, 11), date.getWorkStats());
				setText(getCell(sheet, row, 12), date.getResultsShow());
				setText(getCell(sheet, row, 13), date.getRepComment());
				seq++;
			} // end_for
		} // end_if

		if (logger.isDebugEnabled()) {
			logger.debug("fillSheetTwo(HSSFSheet, IContext) - end");
		}
	} // end_fun

	/**
	 * {初始化电子表格填写需要的cell 格式}
	 * 
	 * @param workbook
	 */
	private void initCellStyle(HSSFSheet sheet) {
		left_stype = sheet.getRow(3).getCell(13).getCellStyle();
		left_stype.setBorderBottom(CellStyle.BORDER_THIN);
		left_stype.setBorderLeft(CellStyle.BORDER_THIN);
		left_stype.setBorderTop(CellStyle.BORDER_THIN);
		left_stype.setBorderRight(CellStyle.BORDER_THIN);
		left_stype.setWrapText(true);

		center_stype = sheet.getRow(3).getCell(9).getCellStyle();
		center_stype.setBorderBottom(CellStyle.BORDER_THIN);
		center_stype.setBorderLeft(CellStyle.BORDER_THIN);
		center_stype.setBorderTop(CellStyle.BORDER_THIN);
		center_stype.setBorderRight(CellStyle.BORDER_THIN);
		center_stype.setWrapText(true);

		n_title_stype = sheet.getRow(28).getCell(1).getCellStyle();
		l_title_stype = sheet.getRow(9).getCell(1).getCellStyle();

		right_stype = sheet.getRow(3).getCell(2).getCellStyle();
		right_stype.setBorderBottom(CellStyle.BORDER_THIN);
		right_stype.setBorderLeft(CellStyle.BORDER_THIN);
		right_stype.setBorderTop(CellStyle.BORDER_THIN);
		right_stype.setBorderRight(CellStyle.BORDER_THIN);
	}

	private HSSFCellStyle center_stype = null;

	private HSSFCellStyle left_stype = null;

	private HSSFCellStyle right_stype = null;

	private HSSFCellStyle l_title_stype = null;

	private HSSFCellStyle n_title_stype = null;
} // end_class
