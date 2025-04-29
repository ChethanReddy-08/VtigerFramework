package com.comcast.crm.generic.fileutility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility 
{
	public String getDataFromExcelFile(String sheetName, int rowNum, int CelNum) throws EncryptedDocumentException, IOException
	{
		FileInputStream fis = new FileInputStream("./testdata/testScriptData.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		String data = wb.getSheet(sheetName).getRow(rowNum).getCell(CelNum).getStringCellValue();
		wb.close();
		return data;
	}
	
	public int getRowCount(String SheetName) throws EncryptedDocumentException, IOException
	{
		FileInputStream fis = new FileInputStream("./testdata/testScriptData.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		int rowcount = wb.getSheet(SheetName).getLastRowNum();
		wb.close();
		return rowcount;
	}
	
	public void setDataintoExcel(String sheetName, int rowNum, int CelNum, String data) throws EncryptedDocumentException, IOException
	{
		FileInputStream fis = new FileInputStream("./testdata/testScriptData.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		wb.getSheet(sheetName).getRow(rowNum).createCell(CelNum);
		
		FileOutputStream fos = new FileOutputStream("./testdata/testScriptData.xlsx");
		wb.write(fos);
		wb.close();
		
		
	}

}
