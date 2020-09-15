package com.qa.clearpc.utility;

import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.qa.clearpc.BaseClass.BaseClass;

public class Xls_Reader extends BaseClass
{
	public static Workbook wb; 
	Sheet sheet;
	public Xls_Reader()
	{
		try {
			File src = new File(Constants.TESTDATA_PATH);
			FileInputStream fis = new FileInputStream(src);
			wb = WorkbookFactory.create(fis);
		} catch (Exception e) {
			System.out.println("unable to load excel file-->" +e.getMessage());
			e.printStackTrace();
		}
	}
	
	//method to fetch string data from excel sheet
	public String getStringData(String sheetname, int row, int cell)
	{
		String cellData=wb.getSheet(sheetname).getRow(row).getCell(cell).getStringCellValue();
		return cellData;
	}
	
	//method to fetch numeric data from excel sheet
	public int getNumericData(String sheetname, int row, int cell)
	{
		double cellData = wb.getSheet(sheetname).getRow(row).getCell(cell).getNumericCellValue();
		int numData = (int)cellData;
		return numData; 	
	}
	
	//dataprovider
	public Object[][] GetData(String Sheetname)
	{
		sheet=wb.getSheet(Sheetname);
		Object[][] data=new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for(int i=0 ; i<sheet.getLastRowNum() ; i++)
		{
			for(int j=0 ; j<sheet.getRow(0).getLastCellNum() ; j++)
			{
				data[i][j]=sheet.getRow(i+1).getCell(j).toString();
			}
		}
		return data;
	}

	
}
