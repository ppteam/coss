package com.neusoft.model.reportforms.service;

import org.apache.log4j.Logger;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.model.report.dao.pojo.DailyReport;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b>{加班明细统计报表} <br>
 * <b>Copyright:</b>Copyright &copy; 2011 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2011-1-24<br>
 * 
 * @author xiaoshuaiping Email:xiaoshp@nesusoft.com
 * @version $Revision$
 */
public class OverWorkViewResolvers extends AbstractXlsViewResolvers {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OverWorkViewResolvers.class);

	public OverWorkViewResolvers() {
	}

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
	protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("buildExcelDocument(Map<String,Object>, HSSFWorkbook, HttpServletRequest, HttpServletResponse) - start");
		}

		IContext context = (IContext) map.get("IContext");
		List<DailyReport> reportList = (List<DailyReport>) context.getValueByAll("overworkList");
		try {

			// 计算一共需要多少列，建一个Map<String,数组>来存放数据
			Date startDate = (Date) context.get("startDate");
			Date endDate = (Date) context.get("endDate");
			OverworkHandler handler = new OverworkHandler(startDate, endDate, reportList);
			// 打印标题
			String[] title = handler.getOverworkTitle();
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFSheet templetSheet = workbook.getSheetAt(1);
			HSSFCellStyle titleStyle = getCell(templetSheet, 0, 0).getCellStyle();
			for (int i = 0; i < title.length; i++) {
				getCell(sheet, 0, i).setCellValue(title[i]);
				getCell(sheet, 0, i).setCellStyle(titleStyle);
			}
			// 打印内容
			Map<String, String[]> excelData = handler.getOverworkData();
			Set<String> keys = excelData.keySet();
			int line = 1;
			HSSFCellStyle contentStyle = getCell(templetSheet, 1, 0).getCellStyle();
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				Object key = (Object) iterator.next();
				String[] row = excelData.get(key);
				for (int i = 0; i < row.length; i++) {
					HSSFCell cell = getCell(sheet, line, i);
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(row[i]);
					getCell(sheet, line, i).setCellStyle(contentStyle);
				}
				line++;
			}
			setFoot(workbook, line);
		} catch (Exception e) {
			logger.error(
					"buildExcelDocument(Map<String,Object>, HSSFWorkbook, HttpServletRequest, HttpServletResponse)",
					e);

			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buildExcelDocument(Map<String,Object>, HSSFWorkbook, HttpServletRequest, HttpServletResponse) - end");
		}
	} // end_fun

	private void setFoot(HSSFWorkbook workbook, int rowIndex) {
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFSheet templetSheet = workbook.getSheetAt(1);
		HSSFCellStyle footStyle = getCell(templetSheet, 2, 0).getCellStyle();
		setText(getCell(sheet, rowIndex, 1), "负责人:");
		getCell(sheet, rowIndex, 1).setCellStyle(footStyle);
		formatRow(sheet, rowIndex, 2, 5, footStyle);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 2, 5));

		formatRow(sheet, rowIndex, 9, 15, footStyle);
		setText(getCell(sheet, rowIndex, 9), "考勤负责人:");
		getCell(sheet, rowIndex, 10).setCellStyle(footStyle);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 9, 15));
		int startCol = 0;
		int endCol = 10;
		rowIndex += 2;
		formatRow(sheet, rowIndex, startCol, endCol, footStyle);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, startCol, endCol));
		setText(getCell(sheet, rowIndex, 0), "注：1.此表为开发公司员工考勤表做为每月核算工作量的依据，冗余人员的考勤只做参考。");
		rowIndex += 1;
		formatRow(sheet, rowIndex, startCol, endCol, footStyle);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, startCol, endCol));
		setText(getCell(sheet, rowIndex, 0), "    2.加班申请流程参照“核心项目组考勤管理办法.XLS”执行。");
		rowIndex += 1;
		formatRow(sheet, rowIndex, startCol, endCol, footStyle);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, startCol, endCol));
		setText(getCell(sheet, rowIndex, 0), "    3.表中请填写加班的工时，单位“小时”，统计周期：起于周一，止于周日。");
		rowIndex += 1;
		formatRow(sheet, rowIndex, startCol, endCol, footStyle);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, startCol, endCol));
		setText(getCell(sheet, rowIndex, 0), "    4.每周二前将前一周的加班工时统计表报送行方项目组。");
	}

} // end_class
