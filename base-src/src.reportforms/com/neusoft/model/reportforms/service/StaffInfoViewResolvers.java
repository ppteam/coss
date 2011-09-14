package com.neusoft.model.reportforms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.neusoft.core.chain.Iface.IContext;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b> 人员基本信息报表<br>
 * <b>Copyright:</b>Copyright &copy; 2011 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2011-2-18<br>
 * 
 * @author xiaoshuaiping E-mail:xiaoshp@neusoft.com
 * @version $Revision$
 */
public class StaffInfoViewResolvers extends AbstractXlsViewResolvers {

	String columName = "userName:teamStats:deptName:extensionNO:userMobileno:graduateSchool:educationBG:specialtyKnow:workDate:"
			+ "joinDate:joinWork:joinTrain:spectiality:birthday:userIdentity:userSexed:maritalStatus:degree:qualifications:"
			+ "skillSpeciality:manageEMP:certificate:politicsStatus";

	public StaffInfoViewResolvers() {
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
				"staffInfo_list");
		if (!CollectionUtils.isEmpty(resultList)) {
			int startRowNum = 3;
			int satrCellNum = 1;
			int cellNum = 0;
			String[] colums = columName.split(":");
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow oldRow = sheet.getRow(startRowNum);
			HSSFRow newRow = null;
			HSSFCell cell = null;
			String values = null;
			for (int i = 0; i < resultList.size(); i++) {
				// 创建新的一行
				newRow = sheet.createRow(i + startRowNum);
				cell = newRow.createCell(satrCellNum);
				// 输出序号
				cell.setCellValue(i + 1);
				cell.setCellStyle(oldRow.getCell(satrCellNum).getCellStyle());
				for (int j = 0; j < colums.length; j++) {
					try {
						values = (String) resultList.get(i).get(colums[j]);
						// 确定输出列的位置
						cellNum = satrCellNum + 1 + j;
						// 创建新的列
						cell = newRow.createCell(cellNum);
						cell.setCellValue(values);
						cell.setCellStyle(oldRow.getCell(cellNum).getCellStyle());
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		}
	}

}
