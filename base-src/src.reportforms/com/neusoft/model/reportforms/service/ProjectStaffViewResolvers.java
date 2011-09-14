package com.neusoft.model.reportforms.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * <b>Application describing:</b> 项目-人员工作量统计表<br>
 * <b>Copyright:</b>Copyright &copy; 2011 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2011-2-11<br>
 * 
 * @author xiaoshuaiping E-mail:xiaoshp@neusoft.com
 * @version $Revision$
 */
public class ProjectStaffViewResolvers extends AbstractXlsViewResolvers {

	public ProjectStaffViewResolvers() {
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
				"projectStaff_list");
		HSSFSheet sheet = workbook.getSheetAt(0);
		// 报表表头信息处理
		Date startDate = DateUtil.strToDate(request.getParameter("startDate"), "yyyy-MM-dd");
		Date endDate = DateUtil.strToDate(request.getParameter("endDate"), "yyyy-MM-dd");
		String startDateStr = DateUtil.fromatDate(startDate, "yyyyMMdd");
		String endDateStr = DateUtil.fromatDate(endDate, "yyyyMMdd");
		// 报表表头信息填写
		HSSFRow termRow = sheet.getRow(1);
		HSSFCell termCell = termRow.getCell(1);
		termCell.setCellValue("统计周期：" + startDateStr + "－" + endDateStr);
		int startRownum = 3;
		int cellnum = 1;
		int nameCellnum = cellnum + 3;
		// int maxRownum=0;
		int maxCellnum = 0;

		if (!CollectionUtils.isEmpty(resultList)) {

			HashMap<String, Integer> isMap = new HashMap<String, Integer>();
			HSSFRow titleRow = sheet.getRow(startRownum - 1);
			// 报表CELL样式
			HSSFCellStyle titleStyle = titleRow.getCell(startRownum).getCellStyle();
			HSSFCellStyle centStyle = sheet.getRow(startRownum).getCell(cellnum).getCellStyle();
			HSSFCellStyle leftStyle = sheet.getRow(startRownum).getCell(cellnum + 1).getCellStyle();
			HSSFCellStyle hourStyle = sheet.getRow(startRownum).getCell(nameCellnum).getCellStyle();
			String projectID = null;
			String userName = null;
			// 确定行、列位置
			int newRownum = startRownum;
			int newCellnum = nameCellnum;
			for (int i = 0; i < resultList.size(); i++) {
				try {
					projectID = (String) resultList.get(i).get("projectID");
					userName = (String) resultList.get(i).get("userName");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!isMap.containsKey(projectID)) {
					isMap.put(projectID, newRownum++);
				}
				if (!isMap.containsKey(userName)) {
					isMap.put(userName, newCellnum++);
				}

			}
			// maxRownum=newRownum;
			maxCellnum = newCellnum;
			// 创建所有的行和列
			HSSFRow row = null;
			HSSFCell cell = null;
			for (int i = startRownum; i < newRownum; i++) {
				row = sheet.createRow(i);
				for (int j = cellnum; j < newCellnum; j++) {
					cell = row.createCell(j);
					cell.setCellStyle(centStyle);
				}

			}
			// 填充报表数据
			String projectName = null;
			String projectStats = null;
			int jobHours = 0;
			for (int i = 0; i < resultList.size(); i++) {
				try {
					projectID = (String) resultList.get(i).get("projectID");
					projectName = (String) resultList.get(i).get("projectName");
					projectStats = (String) resultList.get(i).get("projectStats");
					userName = (String) resultList.get(i).get("userName");
					if (resultList.get(i).get("jobHours") == null) {
						jobHours = 0;
					} else {
						jobHours = Integer.parseInt(resultList.get(i).get("jobHours").toString().trim()) / 10;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				newRownum = isMap.get(projectID);
				row = sheet.getRow(newRownum);
				// 项目编号
				cell = row.getCell(cellnum);
				cell.setCellValue(projectID);
				cell.setCellStyle(centStyle);
				// 项目名称
				cell = row.getCell(cellnum + 1);
				cell.setCellValue(projectName);
				cell.setCellStyle(leftStyle);
				// 项目状态
				cell = row.getCell(cellnum + 2);
				cell.setCellValue(projectStats);
				cell.setCellStyle(centStyle);

				newCellnum = isMap.get(userName);
				// 创建工时列标题
				cell = titleRow.createCell(newCellnum);
				cell.setCellValue(userName);
				cell.setCellStyle(titleStyle);
				// 输出工时
				cell = row.createCell(newCellnum);
				cell.setCellValue(jobHours / 10.0);
				cell.setCellStyle(hourStyle);
			}
			// 　 报表表头单元合并
			sheet.addMergedRegion(new CellRangeAddress(startRownum - 3, startRownum - 3, cellnum, maxCellnum - 1));
			sheet.addMergedRegion(new CellRangeAddress(startRownum - 2, startRownum - 2, cellnum, maxCellnum - 1));
		}
	}

}
