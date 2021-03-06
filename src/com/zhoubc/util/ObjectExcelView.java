package com.zhoubc.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 * 导入到EXCEL
 * 类名称：ObjectExcelView.java 
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
public class ObjectExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();
		String filename = Tools.date2Str(date, "yyyyMMddHHmmss");
		HSSFSheet sheet;
		HSSFCell cell;
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
		sheet = workbook.createSheet("sheet1");

		List<String> titles = (List<String>) model.get("titles");
		int len = titles.size();
		HSSFCellStyle headerStyle = workbook.createCellStyle(); // 标题样式
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont headerFont = workbook.createFont(); // 标题字体
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short) 11);
		headerStyle.setFont(headerFont);
		short width = 20, height = 25 * 20;
		sheet.setDefaultColumnWidth(width);
		for (int i = 0; i < len; i++) { // 设置标题
			String title = titles.get(i);
			cell = getCell(sheet, 0, i);
			cell.setCellStyle(headerStyle);
			setText(cell, title);
		}
		sheet.getRow(0).setHeight(height);

		HSSFCellStyle contentStyle = workbook.createCellStyle(); // 内容样式
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		List<PageData> varList = (List<PageData>) model.get("varList");
		int varCount = varList.size();
		for (int i = 0; i < varCount; i++) {
			PageData vpd = varList.get(i);
			for (int j = 0; j < len; j++) {
				String varstr = vpd.getString("var" + (j + 1)) != null ? vpd.getString("var" + (j + 1)) : "";
				cell = getCell(sheet, i + 1, j);
				cell.setCellStyle(contentStyle);
				setText(cell, varstr);
			}

		}
		
		
		List<BigDecimal> sum=(List<BigDecimal>) model.get("sum");
		if(sum!=null&&sum.size()>0) {
			for (int i= 0; i <sum.size(); i++) {
				if(i==0) {
			        cell = getCell(sheet, varList.size()+ 1,0);
			        cell.setCellStyle(contentStyle);
			        setText(cell,"合计");
			        CellRangeAddress cra =new CellRangeAddress(varList.size()+ 1, varList.size()+ 1, 0,i+Integer.parseInt(sum.get(0).toString())-1); // 起始行, 终止行, 起始列, 终止列  
			        sheet.addMergedRegion(cra);
				}else {
					BigDecimal j=sum.get(i);
					cell = getCell(sheet, varList.size()+ 1,i+Integer.parseInt(sum.get(0).toString())-1);
					cell.setCellStyle(contentStyle);
					setText(cell,j.toString());
				}
				
			}
		}

	}

}
