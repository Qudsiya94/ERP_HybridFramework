package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;



public class ExcelFileUtil {
	 Workbook wb;
	
	public ExcelFileUtil(String ExcelPath) throws Throwable
	{
		 FileInputStream fi=new FileInputStream(ExcelPath);
		 wb=WorkbookFactory.create(fi);
	}
	
	public int rowCount(String SheetName)
	{
		 return wb.getSheet(SheetName).getLastRowNum();
	} 
	public String getCellData(String SheetName,int row,int column)
	{
		String data="";
		if(wb.getSheet(SheetName).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
		{
			int celldata=(int) wb.getSheet(SheetName).getRow(row).getCell(column).getNumericCellValue();
			data=String.valueOf(celldata);
		}
		else
		{
			data=wb.getSheet(SheetName).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
	}
	public void setcellData(String SheetName,int row,int column,String status,String writeExcel) throws Throwable
	{
	
		Sheet ws=wb.getSheet(SheetName);
		Row rowNum=ws.getRow(row);
		Cell cell=rowNum.createCell(column);
		cell.setCellValue(status);
		if(status.equalsIgnoreCase("pass"))
		{
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		FileOutputStream fo=new FileOutputStream(writeExcel);
		wb.write(fo);
			
		}
	/*
	public static void main(String[] args) throws Throwable {
		
		ExcelFileUtil xl= new ExcelFileUtil("D:/Results.xlsx");
		
		int rc = xl.rowCount("EmpData");
		System.out.println(rc);
		for (int i=1;i<=rc;i++)
		{
			String fname=xl.getCellData("EmpData",i,0);
			String mname=xl.getCellData("EmpData",i,1);
			String lname=xl.getCellData("EmpData", i, 2);
			String eid=xl.getCellData("EmpData",i, 3);
			System.out.println(fname+"    "+mname+"    "+lname+"     "+eid);
			xl.setcellData("EmpData",i,4,"pass","D:/Results.xlsx");
			
		    }
		
	}
	
	*/
		
	}
	


