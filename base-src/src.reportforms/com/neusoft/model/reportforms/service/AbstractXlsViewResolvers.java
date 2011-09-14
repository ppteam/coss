package com.neusoft.model.reportforms.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.Configuration;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.neusoft.core.util.DateUtil;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {抽象xls类} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 24, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public abstract class AbstractXlsViewResolvers extends AbstractExcelView implements ResourceLoaderAware {

	private static final long serialVersionUID = -2663357783545171664L;

	public AbstractXlsViewResolvers() {
		setUrl("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.view.document.AbstractExcelView#
	 * getTemplateSource(java.lang.String,
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected HSSFWorkbook getTemplateSource(String url, HttpServletRequest request) throws Exception {
		Resource inputFile = resourceLoader.getResource(xlsPath);
		if (logger.isDebugEnabled()) {
			logger.debug("Loading Excel workbook from " + inputFile);
		}
		POIFSFileSystem fs = new POIFSFileSystem(inputFile.getInputStream());
		return new HSSFWorkbook(fs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.view.AbstractView#prepareResponse(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
		String[] args = xlsPath.split("[/]");
		if (StringUtils.hasLength(request.getParameter("title"))) {
			String title = request.getParameter("title");
			response.setHeader("Content-Disposition", "attachment; filename=" + title + ".xls");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=" + args[args.length - 1]);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ResourceLoaderAware#setResourceLoader(org
	 * .springframework.core.io.ResourceLoader)
	 */
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	private ResourceLoader resourceLoader;

	protected Configuration configuration;

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	// 电子表格对应的路径
	private String xlsPath;

	public void setXlsPath(String xlsPath) {
		this.xlsPath = xlsPath;
	}

	/**
	 * {初始化表格Cell}
	 * 
	 * @param sheet
	 * @param rowNum
	 * @param startCol
	 * @param endCol
	 * @param style
	 */
	protected void formatRow(HSSFSheet sheet, int rowNum, int startCol, int endCol, HSSFCellStyle style) {
		for (int i = startCol; i <= endCol; i++) {
			getCell(sheet, rowNum, i).setCellStyle(style);
		}
	}

	/**
	 * {项目问题 导致特别处理日期}
	 * 
	 * @param date
	 * @return
	 */
	protected String handleDate(Date date) {
		String date_str = "";
		if (date != null) {
			String format = DateUtil.fromatDate(date, null);
			date_str = "1970-01-01".equals(format) ? "" : format;
		}
		return date_str;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.view.document.AbstractExcelView#getCell
	 * (org.apache.poi.hssf.usermodel.HSSFSheet, int, int)
	 */
	@Override
	protected HSSFCell getCell(HSSFSheet sheet, int row, int col) {
		HSSFRow sheetRow = sheet.getRow(row);
		if (sheetRow == null) {
			sheetRow = sheet.createRow(row);
		}
		HSSFCell cell = sheetRow.getCell(col);
		if (cell == null) {
			cell = sheetRow.createCell(col);
		}
		return cell;
	}

	/**
	 * {为制定的单元格添加批注}
	 * 
	 * @param sheet
	 * @param row
	 * @param col
	 * @param author
	 * @param text
	 */
	protected void setComment(HSSFSheet sheet, int row, int col, String author, String text) {
		CreationHelper factory = sheet.getWorkbook().getCreationHelper();
		ClientAnchor anchor = factory.createClientAnchor();
		anchor.setCol1(col);
		anchor.setCol2(col + 3);
		anchor.setRow1(row);
		anchor.setRow2(row + 3);
		Drawing drawing = sheet.createDrawingPatriarch();
		Comment comment = drawing.createCellComment(anchor);
		RichTextString str = factory.createRichTextString(text);
		comment.setString(str);
		comment.setAuthor(author);
		getCell(sheet, row, col).setCellComment(comment);
	} // end_fun

	/**
	 * 
	 * @param cell
	 * @param text
	 */
	protected void setNumber(HSSFCell cell, double number) {
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(number);
	} // end
} // end_clss
