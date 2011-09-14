package com.neusoft.model.reportforms.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.model.reportforms.dao.pojo.DutyCollect;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b>{考勤统计} <br>
 * <b>Copyright:</b>Copyright &copy; 2011 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2011-1-24<br>
 * 
 * @author xiaoshuaiping Email:xiaoshp@nesusoft.com
 * @version $Revision$
 */
public class DutyCollectViewResolvers extends AbstractXlsViewResolvers {

	public DutyCollectViewResolvers() {
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
	protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IContext context = (IContext) map.get("IContext");
		List<DutyCollect> list = (List<DutyCollect>) context.getValueByAll("dutyList");
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFSheet format = workbook.getSheetAt(1);
		try {
			initCellStyle(format);
			fillDetail(sheet, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * {格式化字符串}
	 * 
	 * @param days
	 * @return
	 */
	private String formatNum(int days, DecimalFormat format) {
		if (days == 0) {
			return "";
		} else {
			double res = days / 800.00D;
			return format.format(res);
		}
	}

	/**
	 * {对象填充Cell}
	 * 
	 * @param sheet
	 * @param item
	 * @param rowNum
	 */
	private void fillCellValue(HSSFSheet sheet, DutyCollect item, int rowNum, boolean isBody) {
		DecimalFormat format = new DecimalFormat("#.##");
		setText(getCell(sheet, rowNum, 0), item.getProjectName());
		setText(getCell(sheet, rowNum, 1), item.getUserName());
		setText(getCell(sheet, rowNum, 2), formatNum(item.getSickDays(), format));
		setText(getCell(sheet, rowNum, 3), formatNum(item.getThindDays(), format));
		setText(getCell(sheet, rowNum, 4), formatNum(item.getLaterDay(), format));
		setText(getCell(sheet, rowNum, 5), formatNum(item.getLeaveDay(), format));
		setText(getCell(sheet, rowNum, 6), formatNum(item.getMarryDays(), format));
		setText(getCell(sheet, rowNum, 7), formatNum(item.getDeadDays(), format));
		setText(getCell(sheet, rowNum, 8), formatNum(item.getLaborDays(), format));
		setText(getCell(sheet, rowNum, 9), formatNum(item.getHappyDays(), format));
		setText(getCell(sheet, rowNum, 10), formatNum(item.getHomeDays(), format));
		setText(getCell(sheet, rowNum, 11), formatNum(item.getLearnDays(), format));
		setText(getCell(sheet, rowNum, 12), formatNum(item.getDutyTotal(), format));
		setText(getCell(sheet, rowNum, 13), formatNum(item.getChangeDays(), format));
		setText(getCell(sheet, rowNum, 14), formatNum(item.getAllTotal(), format));
		setText(getCell(sheet, rowNum, 15), item.getComments());
		if (isBody) {
			for (int i = 2; i < 15; i++) {
				getCell(sheet, rowNum, i).setCellStyle(center_stype);
			} // end_for
			setText(getCell(sheet, rowNum, 16), item.getGroupBy());
		} else{
			setText(getCell(sheet, rowNum, 16), "");
		}
	} // end_fun

	/**
	 * {填充考勤明细数据}
	 * 
	 * @param sheet
	 * @param list
	 */
	private void fillDetail(HSSFSheet sheet, List<DutyCollect> list) {
		setText(getCell(sheet, 1, 0), "公司名称：东软");
		getCell(sheet, 1, 0).setCellStyle(btbord_stype);
		int row_index = 3;
		if (CollectionUtils.isNotEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				DutyCollect item = list.get(i);
				if (i < list.size() - 1) {
					formatRow(sheet, row_index, START_COL, END_COL, left_stype);
					fillCellValue(sheet, item, row_index, true);
					row_index++;
				} else {
					formatRow(sheet, row_index, START_COL, END_COL, sheet.getRow(2).getCell(12).getCellStyle());
					fillCellValue(sheet, item, row_index, false);
					row_index++;
				}

			} // end_for
		}

		row_index += 1;
		setText(getCell(sheet, row_index, 1), "负责人:");
		getCell(sheet, row_index, 1).setCellStyle(nobord_stype);
		formatRow(sheet, row_index, 2, 5, btbord_stype);
		sheet.addMergedRegion(new CellRangeAddress(row_index, row_index, 2, 5));

		setText(getCell(sheet, row_index, 10), "考勤负责人:");
		getCell(sheet, row_index, 10).setCellStyle(nobord_stype);
		formatRow(sheet, row_index, 11, 14, btbord_stype);
		sheet.addMergedRegion(new CellRangeAddress(row_index, row_index, 11, 14));

		row_index += 2;
		formatRow(sheet, row_index, START_COL, END_COL, nobord_stype);
		sheet.addMergedRegion(new CellRangeAddress(row_index, row_index, START_COL, END_COL));
		setText(getCell(sheet, row_index, 0), "注：1.此表为开发公司员工考勤表做为每月核算工作量的依据，冗余人员的考勤只做参考。");
		row_index += 1;
		formatRow(sheet, row_index, START_COL, END_COL, nobord_stype);
		sheet.addMergedRegion(new CellRangeAddress(row_index, row_index, START_COL, END_COL));
		setText(getCell(sheet, row_index, 0), "    2.请假调休流程参照“核心项目组考勤管理办法.XLS”执行。");
		row_index += 1;
		formatRow(sheet, row_index, START_COL, END_COL, nobord_stype);
		sheet.addMergedRegion(new CellRangeAddress(row_index, row_index, START_COL, END_COL));
		setText(getCell(sheet, row_index, 0), "    3.表中请填写请假的天数，请假日期请在备注中填写，半天按0.5人日计算。");
		row_index += 1;
		formatRow(sheet, row_index, START_COL, END_COL, nobord_stype);
		sheet.addMergedRegion(new CellRangeAddress(row_index, row_index, START_COL, END_COL));
		setText(getCell(sheet, row_index, 0), "    4.每周二前将前一周的考勤表报送行方项目组。");
	} // end_fun

	/**
	 * {初始化电子表格填写需要的cell 格式}
	 * 
	 * @param workbook
	 */
	private void initCellStyle(HSSFSheet sheet) {
		left_stype = getCell(sheet, 0, 1).getCellStyle();
		left_stype.setBorderBottom(CellStyle.BORDER_THIN);
		left_stype.setBorderLeft(CellStyle.BORDER_THIN);
		left_stype.setBorderTop(CellStyle.BORDER_THIN);
		left_stype.setBorderRight(CellStyle.BORDER_THIN);
		left_stype.setWrapText(true);
		left_stype.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		
		center_stype = getCell(sheet, 0, 3).getCellStyle();
		center_stype.setBorderBottom(CellStyle.BORDER_THIN);
		center_stype.setBorderLeft(CellStyle.BORDER_THIN);
		center_stype.setBorderTop(CellStyle.BORDER_THIN);
		center_stype.setBorderRight(CellStyle.BORDER_THIN);
		center_stype.setWrapText(true);
		center_stype.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		
		right_stype = getCell(sheet, 0, 5).getCellStyle();
		right_stype.setBorderBottom(CellStyle.BORDER_THIN);
		right_stype.setBorderLeft(CellStyle.BORDER_THIN);
		right_stype.setBorderTop(CellStyle.BORDER_THIN);
		right_stype.setBorderRight(CellStyle.BORDER_THIN);
		right_stype.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		
		nobord_stype = getCell(sheet, 0, 7).getCellStyle();
		nobord_stype.setBorderBottom(CellStyle.BORDER_NONE);
		nobord_stype.setBorderLeft(CellStyle.BORDER_NONE);
		nobord_stype.setBorderTop(CellStyle.BORDER_NONE);
		nobord_stype.setBorderRight(CellStyle.BORDER_NONE);
		
		btbord_stype = getCell(sheet, 0, 9).getCellStyle();
		btbord_stype.setBorderBottom(CellStyle.BORDER_THIN);
		btbord_stype.setBorderLeft(CellStyle.BORDER_NONE);
		btbord_stype.setBorderTop(CellStyle.BORDER_NONE);
		btbord_stype.setBorderRight(CellStyle.BORDER_NONE);
	}

	private HSSFCellStyle center_stype = null;

	private HSSFCellStyle left_stype = null;

	private HSSFCellStyle right_stype = null;
	
	private HSSFCellStyle nobord_stype = null;
	
	private HSSFCellStyle btbord_stype = null;

	private static int START_COL = 0;
	private static int END_COL = 16;

} // end_class
