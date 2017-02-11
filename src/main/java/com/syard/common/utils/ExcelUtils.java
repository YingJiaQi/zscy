package com.syard.common.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	/**
	 * 对外提供读取excel 的方法
	 * */
	public static List<List<String>> readExcel(File file) throws IOException {
		String fileName = file.getName();
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1);
		if ("xls".equals(extension)) {
			return read2003Excel(file);
			} else if ("xlsx".equals(extension)) {
			return read2007Excel(file);
		} else {
			throw new IOException("请选择execl文件进行导入");
		}
	}

	/**
	 * 读取 office 2003 excel
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static List<List<String>> read2003Excel(File file) throws IOException {
		List<List<String>> list = new ArrayList<List<String>>();
		List<List<String>> lists=new ArrayList<List<String>>();
		HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = hwb.getSheetAt(0);
		String value = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		for (int i = sheet.getFirstRowNum(); i <= sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			List<String> linked = new ArrayList<String>();
			for (int j = sheet.getRow(1).getFirstCellNum();j <= sheet.getRow(1).getLastCellNum()-1; j++) {
				cell = row.getCell(j);
				if (cell == null) {
					value=null;
					linked.add(value);
				}else{
					DecimalFormat df = new DecimalFormat("0");// 格式化 number String
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
					DecimalFormat nf = new DecimalFormat("0");// 格式化数字
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						if ("@".equals(cell.getCellStyle().getDataFormatString())) {
							value = df.format(cell.getNumericCellValue());
						} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
							value = nf.format(cell.getNumericCellValue());
						} else {
							value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
						}
						break;				
					case XSSFCell.CELL_TYPE_BLANK:
						value = null;
						break;
					default:
						value = cell.toString();
					}
					linked.add(value);
				}
			}
			linked.add((i + 1)+"");
			list.add(linked);			
		}
		hwb.close();
		for (List<String> str : list) {
			int count=0;
			for (int i = 0; i < str.size()-1; i++) {
				if(str.get(i)!=null && !str.get(i).equals("")){
					count++;
				}
			}
			if(count!=0){
				lists.add(str);				
			}
		}
		return lists;
	}

	/**
	 * 读取Office 2007 excel
	 * */
	private static List<List<String>> read2007Excel(File file)
			throws IOException {
		List<List<String>> list = new ArrayList<List<String>>();
		List<List<String>> lists = new ArrayList<List<String>>();
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		String value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		for (int i = sheet.getFirstRowNum(); i <= sheet
				.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			List<String> linked = new ArrayList<String>();
			for (int j = sheet.getRow(1).getFirstCellNum(); j <= sheet
					.getRow(1).getLastCellNum() - 1; j++) {
				cell = row.getCell(j);
				if (cell == null) {
					value = null;
					linked.add(value);
				} else {
					DecimalFormat df = new DecimalFormat("0");// 格式化 number
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
					DecimalFormat nf = new DecimalFormat("0");// 格式化数字
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						if ("@".equals(cell.getCellStyle().getDataFormatString())) {
							value = df.format(cell.getNumericCellValue());
						} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
							value = nf.format(cell.getNumericCellValue());
						} else {
							value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
						}
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						value = null;
						break;
					default:
						value = cell.toString();
					}
					linked.add(value);
				}
			}
			//linked.add((i + 1) + "");
			list.add(linked);
		}
		xwb.close();
		for (List<String> str : list) {
			int count=0;
			for (int i = 0; i < str.size()-1; i++) {
				if(str.get(i)!=null && !str.get(i).equals("")){
					count++;
				}
			}
			if(count!=0){
				lists.add(str);				
			}
		}
		return lists;
	}
}