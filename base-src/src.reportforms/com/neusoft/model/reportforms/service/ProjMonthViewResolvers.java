package com.neusoft.model.reportforms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.util.DateUtil;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b> 项目-月份工作量统计表<br>
 * <b>Copyright:</b>Copyright &copy; 2011 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2011-2-14<br>
 * 
 * @author xiaoshuaiping e-mail:xiaoshp@neusoft.com
 * @version $Revision$
 */

public class ProjMonthViewResolvers extends AbstractXlsViewResolvers {

	public ProjMonthViewResolvers() {
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
		List<HashMap<String, Object>> resultList = (List<HashMap<String, Object>>) context.getResultMap().get(
				"projMonth_list");
		HSSFSheet sheet = workbook.getSheetAt(0);
		int startRowNum = 4;
		int StartCellNum = 1;
		// 报表表头信息处理
		Date startDate = DateUtil.strToDate(request.getParameter("startDate"), "yyyy-MM-dd");
		Date endDate = DateUtil.strToDate(request.getParameter("endDate"), "yyyy-MM-dd");
		String startDateStr = DateUtil.fromatDate(startDate, "yyyyMMdd");
		String endDateStr = DateUtil.fromatDate(endDate, "yyyyMMdd");
		// 报表表头信息填写
		HSSFRow termRow = sheet.getRow(startRowNum - 3);
		HSSFCell termCell = termRow.getCell(StartCellNum);
		termCell.setCellValue("统计周期：" + startDateStr + "－" + endDateStr);

		if (!CollectionUtils.isEmpty(resultList)) {
			HashMap<String, Integer> isMap = new HashMap<String, Integer>();
			TreeMap<String, Integer> isRepMap = new TreeMap<String, Integer>();
			HSSFRow titleRow = sheet.getRow(startRowNum - 2);
			HSSFRow titleRow2 = sheet.getRow(startRowNum - 1);
			HSSFRow oldRor = sheet.getRow(startRowNum);
			HSSFRow row = null;
			HSSFCell cell = null;
			// CELL格式处理
			HSSFCellStyle titleStyle = titleRow2.getCell(StartCellNum).getCellStyle();
			HSSFCellStyle centStyle = oldRor.getCell(StartCellNum).getCellStyle();
			HSSFCellStyle leftStyle = oldRor.getCell(StartCellNum + 1).getCellStyle();
			HSSFCellStyle staStyle = oldRor.getCell(StartCellNum + 2).getCellStyle();
			HSSFCellStyle jobStyle = oldRor.getCell(StartCellNum + 4).getCellStyle();
			// 行位置确认
			String projectID = null;
			String reportDate = null;
			int newRowNum = startRowNum;
			int newCellNum = StartCellNum + 3;
			for (int i = 0; i < resultList.size(); i++) {
				try {
					projectID = (String) resultList.get(i).get("projectID");
					reportDate = (String) resultList.get(i).get("reportDate");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!isMap.containsKey(projectID)) {
					isMap.put(projectID, newRowNum++);
				}
				if (!isRepMap.containsKey(reportDate)) {
					isRepMap.put(reportDate, newCellNum);
				}
			}
			// 列位置确认，列中的月份需要按年份、月份的顺序输出，所以使用TreeMap重新做排序。
			List<String> list = new ArrayList<String>();
			list.addAll(isRepMap.keySet());
			isRepMap.clear();
			for (int i = 0; i < list.size(); i++) {
				isRepMap.put(list.get(i), newCellNum);
				cell = titleRow2.createCell(newCellNum);
				cell.setCellValue("参与人员");
				cell.setCellStyle(titleStyle);

				cell = titleRow2.createCell(newCellNum + 1);
				cell.setCellValue("工时总计(H)");
				cell.setCellStyle(titleStyle);
				// 月份单元格合并
				sheet.addMergedRegion(new CellRangeAddress(startRowNum - 2, startRowNum - 2, newCellNum, newCellNum + 1));
				cell = titleRow.createCell(newCellNum);
				cell.setCellValue(list.get(i));
				cell.setCellStyle(titleStyle);

				cell = titleRow.createCell(newCellNum + 1);
				cell.setCellStyle(titleStyle);
				newCellNum = newCellNum + 2;
			}

			// 创建所有的行及列
			for (int i = startRowNum; i < newRowNum; i++) {
				row = sheet.createRow(i);
				for (int j = StartCellNum; j <= newCellNum - 1; j++) {
					cell = row.createCell(j);
					cell.setCellStyle(centStyle);
				}
			}
			// 报表数据填写
			String projectName = null;
			String entryName = null;
			String userName = null;
			int workHours = 0;
			int jobNum = 0;
			for (int i = 0; i < resultList.size(); i++) {
				try {
					projectID = (String) resultList.get(i).get("projectID");
					projectName = (String) resultList.get(i).get("projectName");
					entryName = (String) resultList.get(i).get("entryName");
					userName = (String) resultList.get(i).get("userName");
					userName = userName.replace(',', '、');
					reportDate = (String) resultList.get(i).get("reportDate");
					if (resultList.get(i).get("workHours") == null) {
						workHours = 0;
					} else {
						workHours = Integer.parseInt(resultList.get(i).get("workHours").toString().trim()) / 10;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 项目编号
				row = sheet.getRow(isMap.get(projectID));
				cell = row.createCell(StartCellNum);
				cell.setCellValue(projectID);
				cell.setCellStyle(centStyle);
				// 项目名称
				cell = row.createCell(StartCellNum + 1);
				cell.setCellValue(projectName);
				cell.setCellStyle(leftStyle);
				// 项目状态
				cell = row.createCell(StartCellNum + 2);
				cell.setCellValue(entryName);
				cell.setCellStyle(staStyle);

				jobNum = isRepMap.get(reportDate);
				// 项目中人员名字
				cell = row.getCell(jobNum);
				cell.setCellValue(userName);
				cell.setCellStyle(leftStyle);
				// 工时
				cell = row.getCell(jobNum + 1);
				cell.setCellValue(workHours / 10.0);
				cell.setCellStyle(jobStyle);

			}
			// 表头单元格合并
			sheet.addMergedRegion(new CellRangeAddress(startRowNum - 4, startRowNum - 4, StartCellNum, newCellNum - 1));
			sheet.addMergedRegion(new CellRangeAddress(startRowNum - 3, startRowNum - 3, StartCellNum, newCellNum - 1));
		}
	}

}
