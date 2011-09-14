package com.neusoft.model.reportforms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.util.DateUtil;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b>人员-月份工作量统计表 <br>
 * <b>Copyright:</b>Copyright &copy; 2011 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2011-2-11<br>
 * 
 * @author xiaoshuaiping E-mail:xiaoshp@neusoft.com
 * @version $Revision$
 */
public class StaffMonthViewResolvers extends AbstractXlsViewResolvers {

	public StaffMonthViewResolvers() {
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
	@SuppressWarnings({ "unchecked" })
	protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IContext context = (IContext) map.get("IContext");
		List<HashMap<String, Object>> resultList = (List<HashMap<String, Object>>) context.getResultMap().get(
				"staffMonth_list");
		int startRowNum = 4;
		int startCellNum = 1;
		HSSFSheet sheet = workbook.getSheetAt(0);
		// 报表表头信息处理
		Date startDate = DateUtil.strToDate(request.getParameter("startDate"), "yyyy-MM-dd");
		Date endDate = DateUtil.strToDate(request.getParameter("endDate"), "yyyy-MM-dd");
		String startDateStr = DateUtil.fromatDate(startDate, "yyyyMMdd");
		String endDateStr = DateUtil.fromatDate(endDate, "yyyyMMdd");
		// 输出报表表头信息
		HSSFRow termRow = sheet.getRow(startRowNum - 3);
		HSSFCell termCell = termRow.getCell(startCellNum);
		termCell.setCellValue("统计周期：" + startDateStr + "－" + endDateStr);

		if (!CollectionUtils.isEmpty(resultList)) {
			HashMap<String, Integer> isMap = new HashMap<String, Integer>();
			TreeMap<String, Integer> isRepMap = new TreeMap<String, Integer>();

			HSSFRow monthRow = sheet.getRow(startRowNum - 2);
			HSSFRow titelRow = sheet.getRow(startRowNum - 1);
			HSSFRow oldRow = sheet.getRow(startRowNum);
			HSSFRow row = null;
			HSSFCell cell = null;
			// CELL格式处理
			HSSFCellStyle tileCellStyle = titelRow.getCell(startCellNum).getCellStyle();
			HSSFCellStyle hoursCellStyle = oldRow.getCell(startCellNum + 4).getCellStyle();
			HSSFCellStyle infoCellStyle = oldRow.getCell(startCellNum + 1).getCellStyle();
			HSSFCellStyle startCellStyle = oldRow.getCell(startCellNum + 3).getCellStyle();
			HSSFCellStyle fuheCellStyle = oldRow.getCell(startCellNum + 6).getCellStyle();
			// 行位置确定
			String userName = null;
			String reportDate = null;
			int newRowNum = startRowNum;
			int newCellNum = startCellNum + 4;
			for (int i = 0; i < resultList.size(); i++) {
				try {
					userName = (String) resultList.get(i).get("userName");
					reportDate = (String) resultList.get(i).get("reportDate");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!isMap.containsKey(userName)) {
					isMap.put(userName, newRowNum++);
				}
				if (!isRepMap.containsKey(reportDate)) {
					isRepMap.put(reportDate, newCellNum);
				}
			}
			// 列位置确定
			List<String> list = new ArrayList<String>();
			list.addAll(isRepMap.keySet());
			isRepMap.clear();
			for (int i = 0; i < list.size(); i++) {
				reportDate = list.get(i);
				isRepMap.put(reportDate, newCellNum);
				cell = titelRow.createCell(newCellNum);
				cell.setCellValue("正常");
				cell.setCellStyle(tileCellStyle);

				cell = titelRow.createCell(newCellNum + 1);
				cell.setCellValue("加班");
				cell.setCellStyle(tileCellStyle);

				cell = titelRow.createCell(newCellNum + 2);
				cell.setCellValue("负荷");
				cell.setCellStyle(tileCellStyle);
				// 输出月份
				sheet.addMergedRegion(new CellRangeAddress(startRowNum - 2, startRowNum - 2, newCellNum, newCellNum + 2));
				cell = monthRow.createCell(newCellNum);
				cell.setCellValue(reportDate);
				cell.setCellStyle(tileCellStyle);

				cell = monthRow.createCell(newCellNum + 1);
				cell.setCellStyle(tileCellStyle);

				cell = monthRow.createCell(newCellNum + 2);
				cell.setCellStyle(tileCellStyle);
				newCellNum = newCellNum + 3;
			}

			// 创建所有的行及列
			for (int i = startRowNum; i < newRowNum; i++) {
				row = sheet.createRow(i);
				for (int j = startCellNum; j <= newCellNum - 1; j++) {
					cell = row.createCell(j);
					cell.setCellStyle(hoursCellStyle);
				}
			}

			// 填写报表数据
			String deptName = null;
			String companyName = null;
			String teamStats = null;
			int jobHours = 0;
			int overTime = 0;
			int dataCellNum = 0;
			double fuhe = 0.00000d;
			for (int i = 0; i < resultList.size(); i++) {
				try {
					deptName = (String) resultList.get(i).get("deptName");
					userName = (String) resultList.get(i).get("userName");
					companyName = (String) resultList.get(i).get("companyName");
					reportDate = (String) resultList.get(i).get("reportDate");
					teamStats = (String) resultList.get(i).get("teamStats");
					if (resultList.get(i).get("jobHours") == null) {
						jobHours = 0;
					} else {
						jobHours = Integer.parseInt(resultList.get(i).get("jobHours").toString().trim()) / 10;
					}
					if (resultList.get(i).get("overTime") == null) {
						overTime = 0;
					} else {
						overTime = Integer.parseInt(resultList.get(i).get("overTime").toString().trim()) / 10;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				row = sheet.getRow(isMap.get(userName));
				// 填写组别
				cell = row.getCell(startCellNum);
				cell.setCellValue(deptName);
				cell.setCellStyle(infoCellStyle);
				// 填写姓名
				cell = row.getCell(startCellNum + 1);
				cell.setCellValue(userName);
				cell.setCellStyle(infoCellStyle);
				// 填写公司名称
				cell = row.getCell(startCellNum + 2);
				cell.setCellValue(companyName);
				cell.setCellStyle(infoCellStyle);
				// 填写工作状态
				cell = row.getCell(startCellNum + 3);
				cell.setCellValue(teamStats);
				cell.setCellStyle(startCellStyle);
				// 填写工时
				dataCellNum = isRepMap.get(reportDate);
				cell = row.getCell(dataCellNum);
				cell.setCellValue(jobHours / 10.00);
				cell.setCellStyle(hoursCellStyle);
				// 加班工时
				cell = row.getCell(dataCellNum + 1);
				cell.setCellValue(overTime / 10.00);
				cell.setCellStyle(hoursCellStyle);
				// 计算工作负荷
				cell = row.getCell(dataCellNum + 2);
				if (jobHours == 0) {
					cell.setCellValue(0);
				} else {
					fuhe = new BigDecimal((overTime + jobHours) / (double) jobHours).setScale(4,
							BigDecimal.ROUND_HALF_UP).doubleValue();
					cell.setCellValue(fuhe);
				}
				cell.setCellStyle(fuheCellStyle);
			}
			// 　报表表头单元合并
			sheet.addMergedRegion(new CellRangeAddress(startRowNum - 4, startRowNum - 4, startCellNum, newCellNum - 1));
			sheet.addMergedRegion(new CellRangeAddress(startRowNum - 3, startRowNum - 3, startCellNum, newCellNum - 1));

		}
	}

}
