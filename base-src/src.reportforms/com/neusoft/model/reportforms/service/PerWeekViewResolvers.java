package com.neusoft.model.reportforms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.util.DateUtil;
import com.neusoft.model.eweekreport.dao.pojo.EWeekReport;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b>个人周报 <br>
 * <b>Copyright:</b>Copyright &copy; 2011 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2011-1-24<br>
 * 
 * @author xiaoshuaiping Email:xiaoshp@nesusoft.com
 * @version $Revision$
 */
public class PerWeekViewResolvers extends AbstractXlsViewResolvers {

	public PerWeekViewResolvers() {
	}

	private String columName = "projectNo:projectName:reportDate:weeks:userName:workHours:workType:workActivity:workContent:workStats:resultsShow:repComment";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.view.document.AbstractExcelView#
	 * buildExcelDocument(java.util.Map,
	 * org.apache.poi.hssf.usermodel.HSSFWorkbook,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IContext context = (IContext) map.get("IContext");
		HSSFSheet sheet = workbook.getSheetAt(0);

		HSSFSheet format_Sheet = workbook.getSheetAt(1);
		HSSFCellStyle format_style = getCell(format_Sheet, 1, 1).getCellStyle();
		format_style.setWrapText(true);
		format_style.setBorderBottom(CellStyle.BORDER_THIN);
		format_style.setBorderLeft(CellStyle.BORDER_THIN);
		format_style.setBorderRight(CellStyle.BORDER_THIN);
		format_style.setBorderTop(CellStyle.BORDER_THIN);
		format_style.setFillForegroundColor(IndexedColors.WHITE.getIndex());

		try {
			EWeekReport weekReport = (EWeekReport) context.getResultMap().get("pw_tital");
			builderTitle(sheet, weekReport, request);

			List<Map<String, Object>> dataList = (List<Map<String, Object>>) context.getResultMap().get("pw_list");
			builderList(sheet, dataList, format_style);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * {组装报表共用部分}
	 * 
	 * @param workbook
	 * @param weekReport
	 * @throws Exception
	 */
	private void builderTitle(HSSFSheet sheet, EWeekReport weekReport, HttpServletRequest request) throws Exception {
		String startDate = DateUtil.fromatDate(weekReport.getStartDate(), "yyyy/MM/dd");
		String endDate = DateUtil.fromatDate(weekReport.getEndDate(), "yyyy/MM/dd");
		String title = "[" + weekReport.getUserName() + "]" + " 个人周报    " + startDate + " - " + endDate;
		// title
		getCell(sheet, 1, 1).setCellValue(title);
		// 本周工作计划填写
		getCell(sheet, 2, 1).setCellValue("本周工作总结：" + weekReport.getWorkSummary());
		// 下周工作内容/计划
		getCell(sheet, 2, 6).setCellValue("下周工作内容/计划：" + weekReport.getNweekPlan());
		// 问题/风险/困难/期望帮助
		getCell(sheet, 2, 11).setCellValue("问题/风险/困难/期望帮助：" + weekReport.getUnsolveProblem());
	} // end_fun

	/**
	 * {组装报表共用部分}
	 * 
	 * @param workbook
	 * @param weekReport
	 * @throws Exception
	 */
	private void builderList(HSSFSheet sheet, List<Map<String, Object>> dataList, HSSFCellStyle format_style)
			throws Exception {
		if (CollectionUtils.isNotEmpty(dataList)) {
			String[] colums = columName.split(":");
			int startRowNum = 5;
			int startCellNum = 1;
			HSSFCell cell = null;
			for (int i = 0; i < dataList.size(); i++) {
				// fill index
				cell = getCell(sheet, startRowNum + i, startCellNum);
				setText(cell, String.valueOf(i + 1));
				cell.setCellStyle(format_style);

				for (int j = 0; j < colums.length; j++) {
					cell = getCell(sheet, startRowNum + i, startCellNum + 1 + j);
					// 报表列表数据填写
					int workHours = 0;
					if (colums[j].equalsIgnoreCase("workHours")) {
						// 报表工时填写
						if (dataList.get(i).get("workHours") == null) {
							workHours = 0;
						} else {
							workHours = Integer.parseInt(dataList.get(i).get("workHours").toString().trim()) / 10;
						}
						cell.setCellValue(workHours / 10.0);
					} else {
						setText(cell, dataList.get(i).get(colums[j]) == null ? "" : dataList.get(i).get(colums[j])
								.toString());
					} // end_
					cell.setCellStyle(format_style);
				}
			} // end_for
		} // end_fun
	} // end_fun

	
} // end_class
