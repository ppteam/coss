package com.neusoft.model.reportforms.service;

import org.apache.log4j.Logger;

import java.util.Calendar;
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
 * <b>Application describing:</b> 项目周报报表<br>
 * <b>Copyright:</b>Copyright &copy; 2011 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2011-2-14<br>
 * 
 * @author xiaoshuaiping e-mail:xiaoshp@neusoft.com
 * @version $Revision$
 */

public class ProjWeekViewResolvers extends AbstractXlsViewResolvers {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ProjWeekViewResolvers.class);

	public ProjWeekViewResolvers() {
	}

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
	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IContext context = (IContext) map.get("IContext");
		List<HashMap<String, Object>> proheadList = (List<HashMap<String, Object>>) context.getResultMap().get(
				"prohead_list");
		List<HashMap<String, Object>> prohourList = (List<HashMap<String, Object>>) context.getResultMap().get(
				"prohour_list");
		List<HashMap<String, Object>> procoutList = (List<HashMap<String, Object>>) context.getResultMap().get(
				"procout_list");
		List<HashMap<String, Object>> proprobList = (List<HashMap<String, Object>>) context.getResultMap().get(
				"proprob_list");
		HSSFSheet sheet = workbook.getSheetAt(0);
		int startRowNum = 3;
		int startCellNum = 1;
		if (!CollectionUtils.isEmpty(proheadList)) {
			HSSFRow row = null;
			HSSFCell cell = null;
			// 由开始日期取得第几周
			HashMap<String, Object> headMap = proheadList.get(0);
			Date startDate = DateUtil.strToDate((String) headMap.get("startDate"), "yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Calendar c1 = Calendar.getInstance();
			c.setTime(startDate);
			c1.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
			c.add(Calendar.DATE, c1.get(Calendar.DAY_OF_WEEK) - 7);
			row = sheet.getRow(startRowNum - 2);
			cell = row.getCell(startCellNum);
			// 报表标题填写
			cell.setCellValue(c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月第"
					+ c.get(Calendar.DAY_OF_WEEK_IN_MONTH) + "周项目周报");
			/*
			 * 项目信息填写
			 */
			row = sheet.getRow(startRowNum);
			// 文档编号
			cell = row.getCell(startCellNum);
			cell.setCellValue(cell.getRichStringCellValue().getString() + headMap.get("reportNO"));
			// 填写人
			cell = row.getCell(startCellNum + 4);
			cell.setCellValue(cell.getRichStringCellValue().getString() + headMap.get("userName"));
			// 记录日期
			cell = row.getCell(startCellNum + 7);
			cell.setCellValue(cell.getRichStringCellValue().getString()
					+ DateUtil.fromatDate((Date) headMap.get("recordDate"), "yyyy-MM-dd"));
			// 项目信息
			row = sheet.getRow(startRowNum + 1);
			cell = row.getCell(startCellNum + 1);
			cell.setCellValue((String) headMap.get("projectID"));
			// 项目名称
			cell = row.getCell(startCellNum + 4);
			cell.setCellValue((String) headMap.get("projectName"));
			// 项目经理
			cell = row.getCell(startCellNum + 8);
			cell.setCellValue((String) headMap.get("psmName"));
			// 项目进展综述
			row = sheet.getRow(startRowNum + 3);
			cell = row.getCell(startCellNum);
			cell.setCellValue((String) headMap.get("projectDesc"));

			int normalTime = 0;
			int overTime = 0;
			int normalTimeCount = 0;
			int overTimeCount = 0;
			/*
			 * 项目人员工时填写
			 */
			int rowNum = startRowNum + 6;
			HSSFRow hRow = sheet.getRow(rowNum);
			HSSFRow titleRow = sheet.getRow(rowNum - 1);
			// 单元格居中样式
			HSSFCellStyle centerStyle = hRow.getCell(startCellNum).getCellStyle();
			// 单元格居左样式
			HSSFCellStyle leftStyle = hRow.getCell(startCellNum + 3).getCellStyle();
			// 工时单元格样式
			HSSFCellStyle houStyle = hRow.getCell(startCellNum + 6).getCellStyle();
			// 项目进展综述单元格样式
			HSSFCellStyle titleStyle = titleRow.getCell(startCellNum).getCellStyle();
			// 列标题单元格样式
			HSSFCellStyle tcenStyle = sheet.getRow(startRowNum + 4).getCell(startCellNum).getCellStyle();
			// 项目进展综述单元格合并
			sheet.addMergedRegion(new CellRangeAddress(startRowNum + 2, startRowNum + 2, startCellNum, startCellNum + 8));
			/*
			 * 员工工时数据填写
			 */
			if (!CollectionUtils.isEmpty(prohourList)) {
				for (int hi = 0; hi < prohourList.size(); hi++) {
					row = sheet.createRow(rowNum);
					cell = row.createCell(startCellNum);
					// 工时中的序号
					cell = row.createCell(startCellNum);
					cell.setCellValue(hi + 1);
					cell.setCellStyle(centerStyle);
					// 员工姓名
					cell = row.createCell(startCellNum + 1);
					cell.setCellValue((String) prohourList.get(hi).get("userName"));
					cell.setCellStyle(centerStyle);

					// 部门名称
					cell = row.createCell(startCellNum + 2);
					cell.setCellValue((String) prohourList.get(hi).get("branchName"));
					cell.setCellStyle(centerStyle);
					// 本周主要工作
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, startCellNum + 3, startCellNum + 5));
					cell = row.createCell(startCellNum + 3);
					cell.setCellStyle(leftStyle);
					cell.setCellValue((String) prohourList.get(hi).get("weeklySummary"));
					cell = row.createCell(startCellNum + 4);
					cell.setCellStyle(leftStyle);
					cell = row.createCell(startCellNum + 5);
					cell.setCellStyle(leftStyle);
					// 正常工时
					if (prohourList.get(hi).get("normalTime") != null) {
						normalTime = Integer.parseInt(prohourList.get(hi).get("normalTime").toString().trim()) / 10;
					}
					normalTimeCount = normalTimeCount + normalTime;
					cell = row.createCell(startCellNum + 6);
					cell.setCellValue(normalTime / 10.0);
					cell.setCellStyle(houStyle);
					// 加班工时
					if (prohourList.get(hi).get("overTime") != null) {
						overTime = Integer.parseInt(prohourList.get(hi).get("overTime").toString().trim()) / 10;
					}
					overTimeCount = overTimeCount + overTime;
					cell = row.createCell(startCellNum + 7);
					cell.setCellValue(overTime / 10.0);
					cell.setCellStyle(houStyle);
					// 总工时
					cell = row.createCell(startCellNum + 8);
					cell.setCellValue(normalTime / 10.0 + overTime / 10.0);
					cell.setCellStyle(houStyle);
					if (hi < prohourList.size() - 1) {
						rowNum += 1;
					}
				}
			}
			/*
			 * 项目工时合计填写
			 */
			if (!CollectionUtils.isEmpty(procoutList)) {
				if (procoutList.get(0).get("normalTime") != null) {
					normalTime = Integer.parseInt(procoutList.get(0).get("normalTime").toString().trim()) / 10;
				}
				if (procoutList.get(0).get("overTime") != null) {
					overTime = Integer.parseInt(procoutList.get(0).get("overTime").toString().trim()) / 10;
				}
			}
			rowNum = rowNum + 1;
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + 1, startCellNum, startCellNum + 1));
			row = sheet.createRow(rowNum);
			// 项目合计工作量标题输出
			cell = row.createCell(startCellNum);
			cell.setCellValue("项目合计工作量(h)");
			cell.setCellStyle(titleStyle);
			cell = row.createCell(startCellNum + 1);
			cell.setCellStyle(titleStyle);
			// 正常标题
			cell = row.createCell(startCellNum + 2);
			cell.setCellValue("正常");
			cell.setCellStyle(titleStyle);
			// 加班标题
			cell = row.createCell(startCellNum + 3);
			cell.setCellValue("加班");
			cell.setCellStyle(titleStyle);
			// 总计标题
			cell = row.createCell(startCellNum + 4);
			cell.setCellValue("总计");
			cell.setCellStyle(titleStyle);
			// 本周工作量(h)标题
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + 1, startCellNum + 5, startCellNum + 5));
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + 1, startCellNum + 6, startCellNum + 6));
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + 1, startCellNum + 7, startCellNum + 7));
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + 1, startCellNum + 8, startCellNum + 8));
			cell = row.createCell(startCellNum + 5);
			cell.setCellValue("本周工作量(h)");
			cell.setCellStyle(titleStyle);
			// 本周工常工作量合计
			cell = row.createCell(startCellNum + 6);
			cell.setCellValue(normalTimeCount / 10.0);
			cell.setCellStyle(houStyle);
			// 本周加班工作量合计
			cell = row.createCell(startCellNum + 7);
			cell.setCellValue(overTimeCount / 10.0);
			cell.setCellStyle(houStyle);
			// 本周所有工作量合计
			cell = row.createCell(startCellNum + 8);
			cell.setCellValue((overTimeCount + normalTimeCount) / 10.0);
			cell.setCellStyle(houStyle);
			//
			rowNum = rowNum + 1;
			row = sheet.createRow(rowNum);
			cell = row.createCell(startCellNum);
			cell.setCellStyle(titleStyle);
			cell = row.createCell(startCellNum + 1);
			cell.setCellStyle(titleStyle);
			// 项目合计正常工作量
			cell = row.createCell(startCellNum + 2);
			cell.setCellValue(normalTime / 10.0);
			cell.setCellStyle(houStyle);
			// 项目合计加班工作量
			cell = row.createCell(startCellNum + 3);
			cell.setCellValue(overTime / 10.0);
			cell.setCellStyle(houStyle);
			// 项目合计所有工作量
			cell = row.createCell(startCellNum + 4);
			cell.setCellValue((overTime + normalTime) / 10.0);
			cell.setCellStyle(houStyle);
			cell = row.createCell(startCellNum + 5);
			cell.setCellStyle(centerStyle);
			cell = row.createCell(startCellNum + 6);
			cell.setCellStyle(centerStyle);
			cell = row.createCell(startCellNum + 7);
			cell.setCellStyle(centerStyle);
			cell = row.createCell(startCellNum + 8);
			cell.setCellStyle(centerStyle);
			/*
			 * 主要问题数据填写
			 */
			rowNum = rowNum + 1;
			row = sheet.createRow(rowNum);
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, startCellNum, startCellNum + 8));
			for (int ti = startCellNum; ti <= startCellNum + 8; ti++) {
				cell = row.createCell(ti);
				cell.setCellStyle(tcenStyle);
			}
			row.getCell(startCellNum).setCellValue("主要问题");
			rowNum = rowNum + 1;
			// 问题描述单元格标题合并
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, startCellNum + 2, startCellNum + 5));
			// 解决措施单元格标题合并
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, startCellNum + 6, startCellNum + 7));
			row = sheet.createRow(rowNum);
			cell = row.createCell(startCellNum);
			cell.setCellValue("日期填写");
			cell.setCellStyle(titleStyle);
			cell = row.createCell(startCellNum + 1);
			cell.setCellValue("提出人");
			cell.setCellStyle(titleStyle);
			cell = row.createCell(startCellNum + 2);
			cell.setCellValue("问题描述");
			cell.setCellStyle(titleStyle);
			cell = row.createCell(startCellNum + 3);
			cell.setCellStyle(titleStyle);
			cell = row.createCell(startCellNum + 4);
			cell.setCellStyle(titleStyle);
			cell = row.createCell(startCellNum + 5);
			cell.setCellStyle(titleStyle);
			cell = row.createCell(startCellNum + 6);
			cell.setCellValue("解决措施");
			cell.setCellStyle(titleStyle);
			cell = row.createCell(startCellNum + 7);
			cell.setCellStyle(titleStyle);
			cell = row.createCell(startCellNum + 8);
			cell.setCellValue("问题状态");
			cell.setCellStyle(titleStyle);
			if (!CollectionUtils.isEmpty(proprobList)) {
				if (logger.isDebugEnabled()) {
					logger.debug("fill into problems is begin total [" + proprobList.size() + "] And  Row [" + rowNum
							+ "]......");
				}
				for (int pi = 0; pi < proprobList.size(); pi++) {
					rowNum += 1;
					row = sheet.createRow(rowNum);
					// 日期填写
					cell = row.createCell(startCellNum);
					cell.setCellValue((String) proprobList.get(pi).get("discoverDate"));
					cell.setCellStyle(centerStyle);
					// 提出人
					cell = row.createCell(startCellNum + 1);
					cell.setCellValue((String) proprobList.get(pi).get("userName"));
					cell.setCellStyle(centerStyle);
					// 问题描述
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, startCellNum + 2, startCellNum + 5));
					cell = row.createCell(startCellNum + 2);
					cell.setCellStyle(leftStyle);
					cell.setCellValue((String) proprobList.get(pi).get("problemDesc"));
					cell = row.createCell(startCellNum + 3);
					cell.setCellStyle(leftStyle);
					cell = row.createCell(startCellNum + 4);
					cell.setCellStyle(leftStyle);
					cell = row.createCell(startCellNum + 5);
					cell.setCellStyle(leftStyle);
					// 解决措施
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, startCellNum + 6, startCellNum + 7));
					cell = row.createCell(startCellNum + 6);
					cell.setCellStyle(leftStyle);
					cell.setCellValue((String) proprobList.get(pi).get("resolveWay"));
					cell = row.createCell(startCellNum + 7);
					cell.setCellStyle(leftStyle);
					// 问题状态
					cell = row.createCell(startCellNum + 8);
					cell.setCellValue((String) proprobList.get(pi).get("problemStats"));
					cell.setCellStyle(centerStyle);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("fill into problems is end and row [" + rowNum + "]......");
				}
			} else {
				// 如果没有数据填写，则创建空白行
				rowNum = rowNum + 1;
				row = sheet.createRow(rowNum);
				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, startCellNum + 2, startCellNum + 5));
				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, startCellNum + 6, startCellNum + 7));
				for (int ti = startCellNum; ti <= startCellNum + 8; ti++) {
					cell = row.createCell(ti);
					cell.setCellStyle(centerStyle);
				}
			}
			// 项目负责人总结评价 标题ROW
			rowNum = rowNum + 1;
			row = sheet.createRow(rowNum);
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, startCellNum, startCellNum + 8));
			for (int ti = startCellNum; ti <= startCellNum + 8; ti++) {
				cell = row.createCell(ti);
				cell.setCellStyle(tcenStyle);
			}
			row.getCell(startCellNum).setCellValue("项目负责人总结评价");
			// 项目负责人总结评价数据填写
			rowNum = rowNum + 1;
			row = sheet.createRow(rowNum);
			row.setHeight(sheet.getRow(startRowNum + 3).getHeight());
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, startCellNum, startCellNum + 8));
			for (int ti = startCellNum; ti <= startCellNum + 8; ti++) {
				cell = row.createCell(ti);
				cell.setCellStyle(leftStyle);
			}
			row.getCell(startCellNum).setCellValue((String) headMap.get("leaderApp"));
		}
	}

} // end_class
