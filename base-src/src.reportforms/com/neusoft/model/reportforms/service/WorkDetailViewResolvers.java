package com.neusoft.model.reportforms.service;

import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import com.neusoft.model.reportforms.dao.pojo.WorkDetail;

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
public class WorkDetailViewResolvers extends AbstractXlsViewResolvers {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WorkDetailViewResolvers.class);

	public WorkDetailViewResolvers() {
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
		int month = context.getInteger("month");
		int year = context.getInteger("year");
		List<Map<String, String>> proMembers = (List<Map<String, String>>) context
				.getValueByAll("proMemberIds");
		List<WorkDetail> detailList = (List<WorkDetail>) context.getValueByAll("workhoursList");
		List<WorkDetail> totalList = (List<WorkDetail>) context.getValueByAll("totalHoursList");
		try {
			initCellStyle(workbook.getSheetAt(1));
			Map<String, Integer> struts = inintCellStruts(workbook.getSheetAt(0), proMembers, year, month);
			fillDetail(workbook.getSheetAt(0), detailList, struts);
			fillTotal(workbook.getSheetAt(0), totalList, struts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // end_fun

	/**
	 * 
	 * @param sheetAt
	 * @param detailList
	 * @param struts
	 */
	private void fillTotal(HSSFSheet sheet, List<WorkDetail> totalList, Map<String, Integer> struts) {
		if (CollectionUtils.isNotEmpty(totalList)) {
			int mormal = 0;
			int overT = 0;
			int hoursRule = 8;
			for (WorkDetail item : totalList) {
				hoursRule = item.getHoursRule();
				setNumber(getCell(sheet, 3 + item.getDayNum(), struts.get("zongji")),
						item.getJobHours() / 100D);
				mormal += item.getJobHours();
				setNumber(getCell(sheet, 3 + item.getDayNum(), struts.get("zongji") + 1),
						item.getOverHours() / 100D);
				overT += item.getOverHours();
				setNumber(getCell(sheet, 3 + item.getDayNum(), struts.get("zongji") + 2), item.total() / 100D);

				setNumber(getCell(sheet, 3 + item.getDayNum(), struts.get("tongji")), item.getJobHours()
						/ (item.getHoursRule() * 100D));
				setNumber(getCell(sheet, 3 + item.getDayNum(), struts.get("tongji") + 1), item.getOverHours()
						/ (item.getHoursRule() * 100D));
				setNumber(getCell(sheet, 3 + item.getDayNum(), struts.get("tongji") + 2), item.total()
						/ (item.getHoursRule() * 100D));
			} // end_for
			setNumber(getCell(sheet, struts.get("bbar"), struts.get("zongji")), mormal / 100D);
			setNumber(getCell(sheet, struts.get("bbar"), struts.get("zongji") + 1), overT / 100D);
			setNumber(getCell(sheet, struts.get("bbar"), struts.get("zongji") + 2), (mormal + overT) / 100D);

			setNumber(getCell(sheet, struts.get("bbar"), struts.get("tongji")), mormal / (hoursRule * 100D));
			setNumber(getCell(sheet, struts.get("bbar"), struts.get("tongji") + 1), overT
					/ (hoursRule * 100D));
			setNumber(getCell(sheet, struts.get("bbar"), struts.get("tongji") + 2), (mormal + overT)
					/ (hoursRule * 100D));
		} // end_if
	}

	/**
	 * {填写加班明细}
	 * 
	 * @param sheetAt
	 * @param detailList
	 * @param struts
	 */
	private void fillDetail(HSSFSheet sheet, List<WorkDetail> detailList, Map<String, Integer> struts) {
		if (CollectionUtils.isNotEmpty(detailList)) {
			Map<String, Integer> totalMap = new HashMap<String, Integer>();
			int cell_index = -1;
			for (WorkDetail item : detailList) {
				cell_index = struts.get(item.getUserId());
				setNumber(getCell(sheet, 3 + item.getDayNum(), cell_index), item.getJobHours() / 100D);
				mergerValue(totalMap, cell_index, item.getJobHours());
				setNumber(getCell(sheet, 3 + item.getDayNum(), cell_index + 1), item.getOverHours() / 100D);
				mergerValue(totalMap, cell_index + 1, item.getOverHours());
				setNumber(getCell(sheet, 3 + item.getDayNum(), cell_index + 2), item.total() / 100D);
				mergerValue(totalMap, cell_index + 2, item.total());
				if (item.getLevHours() != 0) {
					setComment(sheet, 3 + item.getDayNum(), cell_index, "注释:", item.getBegType() + ": "
							+ item.getLevHours() / 100F + " 小时");
				}
			} // end_for
				// file bbar
			Set<Entry<String, Integer>> entry = totalMap.entrySet();
			for (Entry<String, Integer> item : entry) {
				int col_index = Integer.valueOf(item.getKey().substring(5));
				setNumber(getCell(sheet, struts.get("bbar"), col_index), item.getValue() / 100D);
			}
		} // end_if
	} // end_if

	/**
	 * 
	 * @param map
	 * @param value
	 */
	private void mergerValue(Map<String, Integer> map, int cellIndex, Integer value) {
		String key = "cell_" + cellIndex;
		if (map.containsKey(key)) {
			map.put(key, map.get(key) + value);
		} else {
			map.put(key, value);
		}
	} // end_if

	/**
	 * {初始化报表大致结构}
	 * 
	 * @param sheetAt
	 *            HSSFSheet
	 * @param proMembers
	 *            String
	 */
	private Map<String, Integer> inintCellStruts(HSSFSheet sheet, List<Map<String, String>> proMembers,
			int year, int month) {
		if (logger.isDebugEnabled()) {
			logger.debug("inintCellStruts(HSSFSheet, List<Map<String,String>>, int, int) - start");
		}

		setText(getCell(sheet, 1, 0), "报表日期：" + year + "-" + month);
		Map<String, Integer> map = new HashMap<String, Integer>();
		int cell_index = 2;
		if (CollectionUtils.isNotEmpty(proMembers)) {
			for (Map<String, String> item : proMembers) {
				formatRow(sheet, 2, cell_index, cell_index + 2, center_tital_stype);
				formatRow(sheet, 3, cell_index, cell_index + 2, center_tital_stype);
				sheet.addMergedRegion(new CellRangeAddress(2, 2, cell_index, cell_index + 2));
				setText(getCell(sheet, 2, cell_index), item.get("USER_NAME"));
				setText(getCell(sheet, 3, cell_index), "正常");
				setText(getCell(sheet, 3, cell_index + 1), "加班");
				setText(getCell(sheet, 3, cell_index + 2), "合计");
				getCell(sheet, 3, cell_index + 2).setCellStyle(heji_title_type);
				map.put(item.get("USER_ID"), cell_index);
				cell_index += 3;
			} // end_for
		} // end_if
		formatRow(sheet, 2, cell_index, cell_index + 2, zongji_title_type);
		formatRow(sheet, 3, cell_index, cell_index + 2, zongji_title_type);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, cell_index, cell_index + 2));
		setText(getCell(sheet, 2, cell_index), "总计");
		setText(getCell(sheet, 3, cell_index), "正常");
		setText(getCell(sheet, 3, cell_index + 1), "加班");
		setText(getCell(sheet, 3, cell_index + 2), "合计");
		getCell(sheet, 3, cell_index + 2).setCellStyle(heji_title_type);
		map.put("zongji", cell_index);
		cell_index += 3;
		formatRow(sheet, 2, cell_index, cell_index + 2, toji_title_type);
		formatRow(sheet, 3, cell_index, cell_index + 2, toji_title_type);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, cell_index, cell_index + 2));
		setText(getCell(sheet, 2, cell_index), "总计(人日)");
		setText(getCell(sheet, 3, cell_index), "正常");
		setText(getCell(sheet, 3, cell_index + 1), "加班");
		setText(getCell(sheet, 3, cell_index + 2), "合计");
		getCell(sheet, 3, cell_index + 2).setCellStyle(heji_title_type);
		map.put("tongji", cell_index);

		// detail_list
		Date _day = new Date();
		_day = DateUtils.setYears(_day, year);
		_day = DateUtils.setMonths(_day, month - 1);
		_day = DateUtils.setDays(_day, 1);
		String mm = DateUtil.fromatDate(_day, "MM");
		int _row_index = 4;
		do {
			int dayOfweek = DateUtil.dayOfWeek(_day);
			if (dayOfweek == 6 || dayOfweek == 7) {
				formatRow(sheet, _row_index, 0, cell_index + 2, week_type);
			} else {
				formatRow(sheet, _row_index, 0, cell_index + 2, righrt_tital_stype);
			}
			setText(getCell(sheet, _row_index, 0), DateUtil.fromatDate(_day, "MM/dd/yyyy"));
			setText(getCell(sheet, _row_index, 1), getWeek(dayOfweek));
			for (int i = 2; i < cell_index + 3; i++) {
				if (i == cell_index - 3 || i == cell_index - 2) {
					getCell(sheet, _row_index, i).setCellStyle(zongji_value_type);
				}
				if (i == cell_index || i == cell_index + 1) {
					getCell(sheet, _row_index, i).setCellStyle(toji_value_type);
				}
				if ((i - 1) % 3 == 0) {
					getCell(sheet, _row_index, i).setCellStyle(heji_value_type);
				}
			}
			_day = DateUtils.addDays(_day, 1);
			_row_index++;
		} while (mm.equals(DateUtil.fromatDate(_day, "MM")));

		// bbar
		map.put("bbar", _row_index);
		formatRow(sheet, _row_index, 0, cell_index + 2, bbar_value_type);
		setText(getCell(sheet, _row_index, 0), "合计");
		for (int i = 2; i <= cell_index + 2; i++) {
			setNumber(getCell(sheet, _row_index, i), 0);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("inintCellStruts(HSSFSheet, List<Map<String,String>>, int, int) - end");
		}
		return map;
	} // end_if

	/**
	 * {获取当期日期所在的周}
	 * 
	 * @param date
	 * @return
	 */
	private String getWeek(int dayOfweek) {
		String res = "";
		switch (dayOfweek) {
		case 1:
			res = "星期一";
			break;
		case 2:
			res = "星期二";
			break;
		case 3:
			res = "星期三";
			break;
		case 4:
			res = "星期四";
			break;
		case 5:
			res = "星期五";
			break;
		case 6:
			res = "星期六";
			break;
		case 7:
			res = "星期日";
			break;
		default:
			res = String.valueOf(dayOfweek);
			break;
		}
		return res;
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

		heji_title_type = getCell(sheet, 7, 1).getCellStyle();
		heji_value_type = getCell(sheet, 8, 1).getCellStyle();

		zongji_title_type = getCell(sheet, 11, 1).getCellStyle();
		zongji_value_type = getCell(sheet, 12, 1).getCellStyle();

		toji_title_type = getCell(sheet, 15, 1).getCellStyle();
		toji_value_type = getCell(sheet, 16, 1).getCellStyle();

		righrt_tital_stype = getCell(sheet, 17, 1).getCellStyle();

		week_type = getCell(sheet, 20, 1).getCellStyle();

		bbar_value_type = getCell(sheet, 12, 1).getCellStyle();
	}

	private HSSFCellStyle center_tital_stype = null;

	private HSSFCellStyle righrt_tital_stype = null;

	private HSSFCellStyle heji_title_type = null;

	private HSSFCellStyle heji_value_type = null;

	private HSSFCellStyle bbar_value_type = null;

	private HSSFCellStyle zongji_title_type = null;

	private HSSFCellStyle zongji_value_type = null;

	private HSSFCellStyle toji_title_type = null;

	private HSSFCellStyle toji_value_type = null;

	private HSSFCellStyle week_type = null;
} // end_class
