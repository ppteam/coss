package com.neusoft.model.reportforms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.neusoft.core.chain.Iface.IContext;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b> 通讯录报表<br>
 * <b>Copyright:</b>Copyright &copy; 2011 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2011-1-24<br>
 * 
 * @author xiaoshuaiping Email:xiaoshp@nesusoft.com
 * @version $Revision$
 */
public class AddrBookViewResolvers extends AbstractXlsViewResolvers {

	public AddrBookViewResolvers() {

	}

	String columName = "deptName:userName:extensionNo:userMobileno:projectName:workingPlace:IPAdderss";

	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IContext context = (IContext) map.get("IContext");
		List<HashMap<String, Object>> resultList = (List<HashMap<String, Object>>) context.getResultMap().get(
				"addrBook_list");
		HSSFSheet sheet = workbook.getSheetAt(0);
		if (!CollectionUtils.isEmpty(resultList)) {
			HSSFRow row = null;
			HSSFCell cell = null;
			// 开始行数
			int startRowNum = 3;
			// 开始列数
			int startCellNum = 1;
			int cellNum = 0;
			HSSFRow oldRow = sheet.getRow(startRowNum);
			String[] colums = columName.split(":");
			String values = null;
			try {
				for (int i = 0; i < resultList.size(); i++) {
					row = sheet.createRow(startRowNum + i);
					// 填写序号
					cell = row.createCell(startCellNum);
					cell.setCellValue(i + 1);
					cell.setCellStyle(oldRow.getCell(startCellNum).getCellStyle());
					for (int j = 0; j < colums.length; j++) {
						values = (String) resultList.get(i).get(colums[j]);
						// 确定输出列的位置
						cellNum = startCellNum + 1 + j;
						cell = row.createCell(cellNum);
						cell.setCellValue(values);
						cell.setCellStyle(oldRow.getCell(cellNum).getCellStyle());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
