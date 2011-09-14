package com.neusoft.model.reportforms.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.DateUtil;

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
public class CheckDetailTotalViewResolvers extends AbstractXlsViewResolvers {

	public CheckDetailTotalViewResolvers() {
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
		Date start = context.getObject("startDate", Date.class);
		Date end = context.getObject("endDate", Date.class);
		setText(getCell(sheet, 2, 8), DateUtil.fromatDate(start, null));
		setText(getCell(sheet, 2, 10), DateUtil.fromatDate(end, null));
	} // end_fun

	/**
	 * {填写误餐详情}
	 * 
	 * @param sheetAt
	 * @param totalHours
	 */
	@SuppressWarnings("unchecked")
	private void fillDetial(HSSFSheet sheet, IContext context) throws BaseException {
		int row_index = 4;
		List<Map<String, Object>> maps = (List<Map<String, Object>>) context.getValueByAll("json_stats_data");
		if (CollectionUtils.isNotEmpty(maps)) {
			for (Map<String, Object> item : maps) {
				formatRow(sheet, row_index, 2, 10, body_value_type);
				setNumber(getCell(sheet, row_index, 2), row_index - 3);
				setText(getCell(sheet, row_index, 3), (String) item.get("userName"));
				setText(getCell(sheet, row_index, 4), (String) item.get("deptName"));
				setNumber(getCell(sheet, row_index, 5), ((BigDecimal) item.get("bgGod")).doubleValue());
				if (((BigDecimal) item.get("bgExp")).doubleValue() > 0D) {
					getCell(sheet, row_index, 6).setCellStyle(err_value_type);
				}
				setNumber(getCell(sheet, row_index, 6), ((BigDecimal) item.get("bgExp")).doubleValue());
				if (((BigDecimal) item.get("bgErr")).doubleValue() > 0D) {
					getCell(sheet, row_index, 7).setCellStyle(err_value_type);
				}
				setNumber(getCell(sheet, row_index, 7), ((BigDecimal) item.get("bgErr")).doubleValue());
				setNumber(getCell(sheet, row_index, 8), ((BigDecimal) item.get("edGod")).doubleValue());
				if (((BigDecimal) item.get("edExp")).doubleValue() > 0D) {
					getCell(sheet, row_index, 9).setCellStyle(err_value_type);
				}
				setNumber(getCell(sheet, row_index, 9), ((BigDecimal) item.get("edExp")).doubleValue());
				if (((BigDecimal) item.get("edErr")).doubleValue() > 0D) {
					getCell(sheet, row_index, 10).setCellStyle(err_value_type);
				}
				setNumber(getCell(sheet, row_index, 10), ((BigDecimal) item.get("edErr")).doubleValue());
				row_index++;
			}

		} // end_if
	} // end_fun

	/**
	 * {初始化电子表格填写需要的cell 格式}
	 * 
	 * @param workbook
	 */
	private void initCellStyle(HSSFSheet sheet) {
		body_value_type = getCell(sheet, 4, 1).getCellStyle();
		body_value_type.setWrapText(true);

		err_value_type = getCell(sheet, 7, 1).getCellStyle();
		err_value_type.setWrapText(true);
	}

	private HSSFCellStyle body_value_type = null;

	private HSSFCellStyle err_value_type = null;

} // end_class
