package com.neusoft.model.reportforms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.report.dao.pojo.DailyReport;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b>{项目工作量明细- 统计} <br>
 * <b>Copyright:</b>Copyright &copy; 2011 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2011-1-24<br>
 * 
 * @author xiaoshuaiping Email:xiaoshp@nesusoft.com
 * @version $Revision$
 */
public class WuCanDetailViewResolvers extends AbstractXlsViewResolvers {

	public WuCanDetailViewResolvers() {
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
	protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		initCellStyle(workbook.getSheetAt(1));
		IContext context = (IContext) map.get("IContext");
		try {
			fillTitle(workbook.getSheetAt(0), context);
			fillDetial(workbook.getSheetAt(0), context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // end_fun

	/**
	 * {填写头文件}
	 * 
	 * @param sheetAt
	 * @param totalHours
	 */
	private void fillTitle(HSSFSheet sheet, IContext context) throws BaseException {
		setText(getCell(sheet, 1, 1),
				"员工误餐补助统计表(" + context.getInteger("year") + "-" + context.getInteger("month") + "月)");
		setNumber(getCell(sheet, 3, 8), context.getInteger("totalHours"));
	} // end_fun

	/**
	 * {填写误餐详情}
	 * 
	 * @param sheetAt
	 * @param totalHours
	 */
	@SuppressWarnings("unchecked")
	private void fillDetial(HSSFSheet sheet, IContext context) throws BaseException {
		int totalHours = context.getInteger("totalHours");
		int row_index = 5;
		List<DailyReport> dailyReports = (List<DailyReport>) context.getValueByAll("sumHoursDetail");
		if (CollectionUtils.isNotEmpty(dailyReports)) {
			int addHours = 0;
			int totalMoney = 0;
			for (DailyReport item : dailyReports) {
				formatRow(sheet, row_index, 1, 10, body_value_type);
				setNumber(getCell(sheet, row_index, 1), row_index - 4);
				setText(getCell(sheet, row_index, 3), item.getUserName());
				setText(getCell(sheet, row_index, 4), item.getWorkSubActivity());
				setText(getCell(sheet, row_index, 5), item.getWorkActivity());
				setNumber(getCell(sheet, row_index, 6), item.getWorkHours() / 100);
				addHours = (item.getWorkHours() / 100 - totalHours > 0) ? item.getWorkHours() / 100
						- totalHours : 0;
				setNumber(getCell(sheet, row_index, 7), addHours);
				setNumber(getCell(sheet, row_index, 8), addHours / 3);
				setNumber(getCell(sheet, row_index, 9), 12);
				setNumber(getCell(sheet, row_index, 10), addHours / 3 * 12);
				totalMoney += addHours / 3 * 12;
				row_index++;
			}
			formatRow(sheet, row_index, 1, 10, body_value_type);
			sheet.addMergedRegion(new CellRangeAddress(row_index, row_index, 1, 9));
			setText(getCell(sheet, row_index, 1), "合计");
			setNumber(getCell(sheet, row_index, 10), totalMoney);
		} // end_if
	} // end_fun

	/**
	 * {初始化电子表格填写需要的cell 格式}
	 * 
	 * @param workbook
	 */
	private void initCellStyle(HSSFSheet sheet) {
		// zheng wen
		center_tital_stype = getCell(sheet, 1, 1).getCellStyle();
		center_tital_stype.setWrapText(true);

		project_name_stype = getCell(sheet, 3, 1).getCellStyle();
		project_name_stype.setWrapText(true);

		total_hours_type = getCell(sheet, 5, 1).getCellStyle();
		total_hours_type.setWrapText(true);

		body_value_type = getCell(sheet, 9, 1).getCellStyle();
		body_value_type.setWrapText(true);
	}

	private HSSFCellStyle center_tital_stype = null;

	private HSSFCellStyle project_name_stype = null;

	private HSSFCellStyle total_hours_type = null;

	private HSSFCellStyle body_value_type = null;

} // end_class
