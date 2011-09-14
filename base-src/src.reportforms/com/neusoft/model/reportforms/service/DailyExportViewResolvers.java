package com.neusoft.model.reportforms.service;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.util.DateUtil;
import com.neusoft.model.report.dao.pojo.DailyReport;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b>{项目日报- 统计} <br>
 * <b>Copyright:</b>Copyright &copy; 2011 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2011-1-24<br>
 * 
 * @author xiaoshuaiping Email:xiaoshp@nesusoft.com
 * @version $Revision$
 */
public class DailyExportViewResolvers extends AbstractXlsViewResolvers {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DailyExportViewResolvers.class);

	public DailyExportViewResolvers() {
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
		IContext context = (IContext) map.get("IContext");
		List<DailyReport> reportList = (List<DailyReport>) context.getValueByAll("reportList");
		// 项目人员对照表
		List<DailyReport> pmmList = (List<DailyReport>) context.getValueByAll("pmmList");
		try {
			initCellStyle(workbook.getSheetAt(9));
			// '0d0cc048adf440edb55d8e53d756439c' 正常
			// '3692b9cd731a452ea9e9c55efa5c9618' 请假
			// 'dba8abe0a65945a49ebfc587c947d920' 加班
			fillDailyDetail(workbook, reportList, pmmList);
			fillLeaveDetail(workbook, reportList);
			fillOverWorkerDetail(workbook, reportList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // end_fun

	/**
	 * {填写请假明细}
	 * 
	 * @param workbook
	 * @param leaveList
	 */
	private void fillLeaveDetail(HSSFWorkbook workbook, List<DailyReport> leaveList) {
		if (CollectionUtils.isNotEmpty(leaveList)) {
			HSSFSheet sheet = workbook.getSheetAt(7);
			int row_index = 4;
			for (DailyReport item : leaveList) {
				if ("3692b9cd731a452ea9e9c55efa5c9618".equals(item.getWorkType())) {
					formatRow(sheet, row_index, 1, 7, left_stype);
					setText(getCell(sheet, row_index, 1), DateUtil.fromatDate(item.getReportDate(), null));
					setText(getCell(sheet, row_index, 3), DateUtil.fromatDate(item.getReportDate(), null));
					setNumber(getCell(sheet, row_index, 5), item.getWorkHours() / 800D);
					getCell(sheet, row_index, 5).setCellStyle(jiaqi_stype);
					setText(getCell(sheet, row_index, 6), item.getUserName());
					setText(getCell(sheet, row_index, 7), item.getWorkActivity());
					row_index++;
				}
			} // end_if
		} // end_if
	} // end_if

	/**
	 * {填充加班明细}
	 * 
	 * @param workbook
	 * @param reportList
	 */
	private void fillOverWorkerDetail(HSSFWorkbook workbook, List<DailyReport> overWorkers) {
		HSSFSheet wksheet = workbook.getSheetAt(8);
		int row_index = 7;
		int rowNum = 1;

		if (CollectionUtils.isNotEmpty(overWorkers)) {
			for (DailyReport item : overWorkers) {
				if ("dba8abe0a65945a49ebfc587c947d920".equals(item.getWorkType())) {
					formatRow(wksheet, row_index, 0, 0, center_stype);
					formatRow(wksheet, row_index, 1, 6, jiaban_body);
					formatRow(wksheet, row_index, 7, 11, jiaban_shenp);
					setText(getCell(wksheet, row_index, 0), String.valueOf(rowNum));
					setText(getCell(wksheet, row_index, 1), item.getProjectName());
					setText(getCell(wksheet, row_index, 2), item.getUserName());
					setText(getCell(wksheet, row_index, 3), DateUtil.fromatDate(item.getReportDate(), null));
					setText(getCell(wksheet, row_index, 4),
							builderTime(item.getReportDate(), item.getReportType(), item.getWorkHours(),
									item.getCkRuleId()));
					setText(getCell(wksheet, row_index, 5), item.getWorkContent());
					setText(getCell(wksheet, row_index, 6), item.getResultsShow());
					setNumber(getCell(wksheet, row_index, 10), formatNum(item.getWorkHours()));
					row_index++;
					rowNum++;
				}
			} // end_if
		} // end_if
			// builder tatail
		formatRow(wksheet, row_index, 0, 11, top_shenp);
		formatRow(wksheet, row_index + 1, 0, 11, black_shenp);
		formatRow(wksheet, row_index + 2, 0, 11, btn_shenp);
		wksheet.addMergedRegion(new CellRangeAddress(row_index, row_index, 0, 3));
		setText(getCell(wksheet, row_index, 0), "加班申请客户负责人审批意见");
		setText(getCell(wksheet, row_index + 2, 8), "签字/日期：");
		row_index += 3;
		formatRow(wksheet, row_index, 0, 11, top_shenp);
		formatRow(wksheet, row_index + 1, 0, 11, black_shenp);
		formatRow(wksheet, row_index + 2, 0, 11, btn_shenp);
		wksheet.addMergedRegion(new CellRangeAddress(row_index, row_index, 0, 3));
		setText(getCell(wksheet, row_index, 0), "加班申请PM/PSM审批意见(项目负责人）");
		setText(getCell(wksheet, row_index + 2, 8), "签字/日期：");
		row_index += 3;
		formatRow(wksheet, row_index, 0, 11, top_shenp);
		formatRow(wksheet, row_index + 1, 0, 11, black_shenp);
		formatRow(wksheet, row_index + 2, 0, 11, btn_shenp);
		wksheet.addMergedRegion(new CellRangeAddress(row_index, row_index, 0, 3));
		setText(getCell(wksheet, row_index, 0), "加班申请区域负责人审批意见");
		setText(getCell(wksheet, row_index + 2, 8), "签字/日期：");
		row_index += 4;
		formatRow(wksheet, row_index, 0, 11, black_shenp);
		formatRow(wksheet, row_index + 1, 0, 11, black_shenp);
		formatRow(wksheet, row_index + 2, 0, 11, black_shenp);
		wksheet.addMergedRegion(new CellRangeAddress(row_index, row_index + 2, 0, 11));
		setText(getCell(wksheet, row_index, 0), "说明：1、加班审批必须事前申请,包括客户负责审批签字意见；\r\n"
				+ "             2、员工在法定休假日和周末加班的需填写本表，经审批通过后予以执行；\r\n"
				+ "             3、员工应当如实填写申请表各项内容，若有虚假情况或未经批准私自加班的，视为无效；\r\n"
				+ "             4、员工应在加班后三个工作日内，将“加班考勤表”上报给考勤管理员，考勤管理员核对加班申请与实际加班时间，逾期视为无效。");
	} // end_if

	/**
	 * {计算加班时间}
	 * 
	 * @param date
	 * @param type
	 * @param workHour
	 * @return
	 */
	private String builderTime(Date date, int type, int workHour, String ckRuleId) {
		float end = 0.0F;
		String sts_time = "";
		if (ckRuleId == null || "00000000000000000000000000000000".equals(ckRuleId)) {
			if (type == 1) {
				end = configuration.getFloat("zhuji.start.np.over") * 100 + workHour;
				sts_time = configuration.getString("zhuji.start.np.overtime");
			} else if (type == 0) {
				end = configuration.getFloat("wangyin.start.np.over") * 100 + workHour;
				sts_time = configuration.getString("wangyin.start.np.overtime");
			}
		} else {
			if (type == 1) {
				end = configuration.getFloat("zhuji.start.over") * 100 + workHour;
				sts_time = configuration.getString("zhuji.start.overtime");
			} else if (type == 0) {
				end = configuration.getFloat("wangyin.start.over") * 100 + workHour;
				sts_time = configuration.getString("wangyin.start.overtime");
			}
		}
		Date endDate = DateUtils.addMinutes(date, (int) end * 60 / 100);
		return sts_time + "-" + DateUtil.fromatDate(endDate, "HH:mm");
	} // end_if

	/**
	 * {对象填充Cell}
	 * 
	 * @param sheet
	 * @param item
	 * @param rowNum
	 */
	private void fillDailyValue(HSSFSheet sheet, DailyReport item, int rowNum, boolean isBody) {
		setText(getCell(sheet, rowNum, 0), item.getUserName());
		setText(getCell(sheet, rowNum, 1), "");
		setText(getCell(sheet, rowNum, 2), item.getProjectName());
		setText(getCell(sheet, rowNum, 3), item.getWorkContent());
		setText(getCell(sheet, rowNum, 4), item.getResultsShow());
		setText(getCell(sheet, rowNum, 5), item.getWorkSchde() + "%");
		getCell(sheet, rowNum, 5).setCellStyle(center_stype);
		setText(getCell(sheet, rowNum, 6), item.getDelayAffect());
		setText(getCell(sheet, rowNum, 7), item.getDelaySolve());
		setText(getCell(sheet, rowNum, 8), item.getWorkActivity());
		setNumber(getCell(sheet, rowNum, 9), formatNum(item.getWorkHours()));
		getCell(sheet, rowNum, 9).setCellStyle(center_stype);
	} // end_fun

	/**
	 * {填充合计工作量}
	 * 
	 * @param sheet
	 * @param rowNum
	 * @param total
	 */
	private void fillTotal(HSSFSheet sheet, int rowNum, int total) {
		formatRow(sheet, rowNum, START_COL, END_COL, fbar_stype);
		setText(getCell(sheet, rowNum, 0), "合计");
		setNumber(getCell(sheet, rowNum, 9), formatNum(total));
	}

	/**
	 * {日报明细编写}
	 * 
	 * @param workbook
	 *            电子表格句柄
	 * @param dailyReports
	 *            日报明细
	 * @param pmmList
	 *            项目人员对照表
	 */
	private void fillDailyDetail(HSSFWorkbook workbook, List<DailyReport> dailyReports,
			List<DailyReport> pmmList) {
		if (CollectionUtils.isNotEmpty(dailyReports)) {
			HSSFSheet wksheet = null;
			int wksheet_index = -1;
			int row_index = 5;
			int total = 0;
			List<DailyReport> temPmm = new ArrayList<DailyReport>();
			for (DailyReport item : dailyReports) {
				if ("0d0cc048adf440edb55d8e53d756439c".equals(item.getWorkType())
						|| "dba8abe0a65945a49ebfc587c947d920".equals(item.getWorkType())) {
					if (wksheet == null || wksheet_index != item.getDayOfWeek()) {
						if (wksheet_index != -1) {
							if (CollectionUtils.isNotEmpty(temPmm)) {
								// 填充当日 员工没有针对项目发生工作量的情况
								for (DailyReport pm : temPmm) {
									formatRow(wksheet, row_index, START_COL, END_COL, zore_type);
									fillDailyValue(wksheet, pm, row_index, false);
									row_index++;
								}
							} // end_if
							fillTotal(wksheet, row_index, total);
						} // end_if
						wksheet_index = item.getDayOfWeek();
						row_index = 5;
						total = 0;
						wksheet = workbook.getSheetAt(wksheet_index);
						// reset pmmList
						temPmm.clear();
						temPmm.addAll(pmmList);
						if (logger.isDebugEnabled()) {
							logger.debug("current sheet index is [" + wksheet_index + "]");
						}
					} // end_if
					total += item.getWorkHours();
					formatRow(wksheet, row_index, START_COL, END_COL, left_stype);
					fillDailyValue(wksheet, item, row_index, false);
					row_index++;
				} // end_if

				// 剔除掉已经填写日报的数据
				if (CollectionUtils.isNotEmpty(temPmm)) {
					DailyReport _p = null;
					for (DailyReport pm : temPmm) {
						if (pm.getRepComment().equals(item.getRepComment())) {
							_p = pm;
							break;
						}
					} // end_for
					if (_p != null) {
						temPmm.remove(_p);
					}
				} // end_if
			} // end_for
			if (wksheet != null) {
				// 填充当日 员工没有针对项目发生工作量的情况
				if (CollectionUtils.isNotEmpty(temPmm)) {
					for (DailyReport pm : temPmm) {
						formatRow(wksheet, row_index, START_COL, END_COL, zore_type);
						fillDailyValue(wksheet, pm, row_index, false);
						row_index++;
					}
				} // end_if
				fillTotal(wksheet, row_index, total);
			}
		}
	} // end_fun

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
	} // end_fun

	/**
	 * {初始化电子表格填写需要的cell 格式}
	 * 
	 * @param workbook
	 */
	private void initCellStyle(HSSFSheet sheet) {
		// zheng wen
		left_stype = getCell(sheet, 1, 1).getCellStyle();
		left_stype.setWrapText(true);

		// center_stype
		center_stype = getCell(sheet, 1, 3).getCellStyle();
		center_stype.setWrapText(true);

		// zheng wen bar
		fbar_stype = getCell(sheet, 3, 1).getCellStyle();
		fbar_stype.setWrapText(true);

		// 请假工期
		jiaqi_stype = getCell(sheet, 5, 1).getCellStyle();
		jiaqi_stype.setWrapText(true);

		// 加班正文
		jiaban_body = getCell(sheet, 8, 1).getCellStyle();
		jiaban_body.setWrapText(true);

		// 加班审批
		jiaban_shenp = getCell(sheet, 11, 1).getCellStyle();
		jiaban_shenp.setWrapText(true);

		black_shenp = getCell(sheet, 15, 1).getCellStyle();
		black_shenp.setWrapText(true);

		top_shenp = getCell(sheet, 13, 1).getCellStyle();
		top_shenp.setWrapText(true);

		btn_shenp = getCell(sheet, 17, 1).getCellStyle();
		btn_shenp.setWrapText(true);

		zore_type = getCell(sheet, 20, 1).getCellStyle();
		zore_type.setWrapText(true);
	}

	private HSSFCellStyle left_stype = null;

	private HSSFCellStyle center_stype = null;

	private HSSFCellStyle fbar_stype = null;

	private HSSFCellStyle jiaqi_stype = null;

	private HSSFCellStyle jiaban_body = null;

	private HSSFCellStyle jiaban_shenp = null;

	private HSSFCellStyle black_shenp = null;

	private HSSFCellStyle top_shenp = null;

	private HSSFCellStyle btn_shenp = null;

	private HSSFCellStyle zore_type = null;

	private static int START_COL = 0;

	private static int END_COL = 9;

} // end_class
