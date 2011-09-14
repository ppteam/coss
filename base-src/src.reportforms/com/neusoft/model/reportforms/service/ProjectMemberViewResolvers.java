package com.neusoft.model.reportforms.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.model.reportforms.dao.pojo.ProjectMember;

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
public class ProjectMemberViewResolvers extends AbstractXlsViewResolvers {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ProjectMemberViewResolvers.class);

	public ProjectMemberViewResolvers() {
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
		List<ProjectMember> proMembers = (List<ProjectMember>) context.getValueByAll("proMemberList");
		try {
			initCellStyle(workbook.getSheetAt(0));
			fillDetail(workbook.getSheetAt(0), proMembers);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // end_fun

	/**
	 * 
	 * @param sheetAt
	 * @param proMembers
	 */
	private void fillDetail(HSSFSheet sheet, List<ProjectMember> proMembers) {
		if (logger.isDebugEnabled()) {
			logger.debug("fillDetail(HSSFSheet, List<ProjectMember>) - start");
		}

		if (CollectionUtils.isNotEmpty(proMembers)) {
			Map<String, List<ProjectMember>> map = new HashMap<String, List<ProjectMember>>();
			for (ProjectMember item : proMembers) {
				if (map.containsKey(item.getProjectId())) {
					map.get(item.getProjectId()).add(item);
				} else {
					List<ProjectMember> tpList = new ArrayList<ProjectMember>();
					tpList.add(item);
					map.put(item.getProjectId(), tpList);
				}
			} // end_for

			int maxRow = 5;
			int cell_index = 1;
			int row_index = 1;
			int arg_index = 0;
			int[] arg_int = new int[map.size()];
			Map<String, Integer> userSet = new HashMap<String, Integer>();
			Set<Entry<String, List<ProjectMember>>> entrys = map.entrySet();
			for (Entry<String, List<ProjectMember>> item : entrys) {
				List<ProjectMember> list = item.getValue();
				arg_int[arg_index] = list.size();
				arg_index++;
				for (ProjectMember p : list) {
					if (userSet.containsKey(p.getUserId())) {
						userSet.put(p.getUserId(), userSet.get(p.getUserId()) + 1);
					} else {
						userSet.put(p.getUserId(), 1);
					}
					if (row_index == 1) {
						setText(getCell(sheet, row_index, cell_index), p.getProjectName());
						getCell(sheet, row_index, cell_index).setCellStyle(tbar_style);
						row_index++;
						setText(getCell(sheet, row_index, cell_index), p.getUserName());
						formatCell(getCell(sheet, row_index, cell_index), p);
						row_index++;
					} else {
						setText(getCell(sheet, row_index, cell_index), p.getUserName());
						formatCell(getCell(sheet, row_index, cell_index), p);
						row_index++;
					}
				} // end_for
				maxRow = maxRow >= row_index ? maxRow : row_index;
				cell_index++;
				row_index = 1;
			} // end_fun

			setText(getCell(sheet, 1, arg_index + 1), "合计");
			getCell(sheet, 1, arg_index + 1).setCellStyle(tbar_style);

			// fill bbar
			formatRow(sheet, maxRow, 0, arg_index + 1, tbar_style);
			setText(getCell(sheet, maxRow, 0), "在项目人数");
			for (int i = 0; i < arg_index; i++) {
				setNumber(getCell(sheet, maxRow, i + 1), arg_int[i]);
			} // end_fun
			maxRow++;
			int repeatNum = 0;
			Set<Entry<String, Integer>> entries = userSet.entrySet();
			for (Entry<String, Integer> entry : entries) {
				if (entry.getValue() > 1) {
					repeatNum++;
				}
			}
			formatRow(sheet, maxRow, 0, 1, body_stype);
			setText(getCell(sheet, maxRow, 0), "重复人数：");
			setNumber(getCell(sheet, maxRow, 1), repeatNum);
			maxRow++;
			formatRow(sheet, maxRow, 0, 1, body_stype);
			setText(getCell(sheet, maxRow, 0), "管理人员：");
			maxRow++;
			formatRow(sheet, maxRow, 0, 1, body_stype);
			setText(getCell(sheet, maxRow, 0), "QA：");
		} // end_if

		if (logger.isDebugEnabled()) {
			logger.debug("fillDetail(HSSFSheet, List<ProjectMember>) - end");
		}
	} // end_fun

	private void formatCell(HSSFCell cell, ProjectMember member) {
		if ("c401a1050fe64b29ba60997e2a576d52".equals(member.getRoleType())) {
			cell.setCellStyle(psm_stype);
		} else if ("517a4e05b5d44b5096e9b3bd04bec654".equals(member.getRoleType())) {
			cell.setCellStyle(emp_stype);
		} else if ("70eed5ac1d7048d68f3648df7640b6a1".equals(member.getRoleType())) {
			cell.setCellStyle(qa_stype);
		} else {
			cell.setCellStyle(body_stype);
		}
	} // end_fun

	/**
	 * {初始化电子表格填写需要的cell 格式}
	 * 
	 * @param workbook
	 */
	private void initCellStyle(HSSFSheet sheet) {
		tbar_style = getCell(sheet, 1, 0).getCellStyle();
		tbar_style.setWrapText(true);
		body_stype = getCell(sheet, 9, 0).getCellStyle();
		psm_stype = getCell(sheet, 2, 0).getCellStyle();
		emp_stype = getCell(sheet, 3, 0).getCellStyle();
		qa_stype = getCell(sheet, 4, 0).getCellStyle();
	}

	private HSSFCellStyle tbar_style = null;

	private HSSFCellStyle body_stype = null;

	private HSSFCellStyle psm_stype = null;

	private HSSFCellStyle emp_stype = null;

	private HSSFCellStyle qa_stype = null;
} // end_class
