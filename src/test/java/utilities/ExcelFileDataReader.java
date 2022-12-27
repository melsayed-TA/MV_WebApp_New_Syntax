package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.shaft.tools.io.ReportManager;


public class ExcelFileDataReader {
	private FileInputStream fi;
	private FileOutputStream fo;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private XSSFRow row;
	private XSSFCell cell;
	private CellStyle style;   
	private String path;
	private String sheetName;
	
	//Constructor
	public ExcelFileDataReader(String path, String sheetName)
	{
		this.path = path;
		this.sheetName = sheetName;
	}
		
	//Actions
	public int getRowCount() throws IOException 
	{
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		int rowcount=sheet.getLastRowNum();
		workbook.close();
		fi.close();
		return rowcount;		
	}
	

	public int getCellCount(int rownum) throws IOException		//ColCount
	{
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		row=sheet.getRow(rownum);
		int cellcount=row.getLastCellNum();
		workbook.close();
		fi.close();
		return cellcount;
	}
	
	
	public String getCellData(int rownum,int colnum) throws IOException
	{
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		DataFormatter formatter = new DataFormatter();
		String data;
		try{
		data = formatter.formatCellValue(cell); //Returns the formatted value of a cell as a String regardless of the cell type.
		}
		catch(Exception e)
		{
			data="";
		}
		workbook.close();
		fi.close();
		return data;
	}
	
	public void setCellData(int rownum, int colnum, String data) throws IOException
	{
		File xlfile=new File(path);
		if(!xlfile.exists())    // If file not exists then create new file
		{
		workbook=new XSSFWorkbook();
		fo=new FileOutputStream(path);
		workbook.write(fo);
		}
				
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
			
		if(workbook.getSheetIndex(sheetName)==-1) // If sheet not exists then create new Sheet
			workbook.createSheet(sheetName);
		
		sheet=workbook.getSheet(sheetName);
					
		if(sheet.getRow(rownum)==null)   // If row not exists then create new Row
				sheet.createRow(rownum);
		row=sheet.getRow(rownum);
		
		cell=row.createCell(colnum);
		cell.setCellValue(data);
		fo=new FileOutputStream(path);
		workbook.write(fo);		
		workbook.close();
		fi.close();
		fo.close();
	}
	
	
	public void fillGreenColor(int rownum, int colnum) throws IOException
	{
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		style=workbook.createCellStyle();
		
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
				
		cell.setCellStyle(style);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
	}
	
	
	public void fillRedColor(int rownum, int colnum) throws IOException
	{
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		style=workbook.createCellStyle();
		
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
		
		cell.setCellStyle(style);		
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
	}

	//Get all ExcelFile Data (as a 2D array - Used in TDD "multiple data in different rows for same test") - (Stores Excel Data in a 2D array and returns this 2D array --> to be used in the data provider TestNG annotation, since the TestNG annotation only accepts 2D Arrays)
	public String[][] getExcelData() throws IOException {
		
		int rowsCount = getRowCount();
		int colCount  = getCellCount(1);
		
		String [][] excelData = new String[rowsCount][colCount];
		
		for (int i = 1; i <= rowsCount; i++) {
			for (int j = 0; j < colCount; j++) {
				excelData[i-1][j] = getCellData(i, j);
			}
		}
		
		return excelData;	
	}
	
	//Get Excel Data as Map (i.o. Dictionary) by Test case id (Test case id is the key)
	public /*static*/ Map<String,String> getTestDataAsMapByTestcaseId(String testCaseId) throws Exception
	{
		Map<String,String> TestDataInMap=new TreeMap<String,String>();		
		String query=null;
		query=String.format("SELECT * FROM %s WHERE Run='Yes' and TestCaseId='%s'", sheetName, testCaseId);
		Fillo fillo=new Fillo();
		Connection conn=null;
		Recordset recordset=null;
		try
		{
			conn=fillo.getConnection(path);
			recordset=conn.executeQuery(query);
			//recordset=((com.codoid.products.fillo.Connection) conn).executeQuery(query);
			while(recordset.next())
			{
				for(String field:recordset.getFieldNames())
				{
					TestDataInMap.put(field, recordset.getField(field));
				}
			}
		}
		catch(FilloException e)
		{
			e.printStackTrace();
			throw new Exception("Test data file could not be found.");			
		}
		conn.close();
		
		return TestDataInMap;		
	}
	
	//Set data in Excel
	public /*static*/ void UpdateTestResultsToExcel(String tcStatus,String testCaseId)
	{
		Connection conn=null;
		Fillo fillo =new Fillo();
		try{
			conn=fillo.getConnection(path);
			String query=String.format("UPDATE %s SET TestCaseStatus='%s' where TestCaseID='%s'", sheetName,tcStatus,testCaseId);
			conn.executeUpdate(query);
		} catch(FilloException e){
			e.printStackTrace();
		}		
	}
	
	
	//Get Excel data as list of hash maps
	public List<Map<String,String>> getTestDataAsListOfHashMaps() {
		List<Map<String,String>> testDataAllRows = null;		//List of hash maps
		Map<String,String> testData = null;		//only one hash map (one row)
		
		try {
			//FileInputStream fileInputStream = new FileInputStream(excelFilePath);
			FileInputStream fileInputStream = new FileInputStream(path);
			XSSFWorkbook workbook = null;
			try {
				workbook = new XSSFWorkbook(fileInputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			XSSFSheet sheet = workbook.getSheet(sheetName);
			int lastRowNumber = sheet.getLastRowNum();
			int lastColNumber = sheet.getRow(0).getLastCellNum();
			
			List<String> list = new ArrayList<String>();
			for (int i =0; i < lastColNumber; i++) {
				XSSFRow row = sheet.getRow(0);
				XSSFCell cell = row.getCell(i);
				String rowHeader = cell.getStringCellValue().trim();
				list.add(rowHeader);
			}
					
			testDataAllRows = new ArrayList<Map<String,String>>();
			
			for(int j=1; j <= lastRowNumber; j++) {
				XSSFRow row = sheet.getRow(j);
				testData = new TreeMap<String,String>(String.CASE_INSENSITIVE_ORDER);
				for(int k=0; k < lastColNumber; k++) {
					XSSFCell cell = row.getCell(k);
					String colValue = "";
					
					if (cell != null) {
						colValue = cell.getStringCellValue().trim();
					} 
					
					testData.put((String) list.get(k), colValue);											
			}
				testDataAllRows.add(testData);
		}
		

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	 
	return testDataAllRows;
	
	}
	
	
	//Get cell data by key (Key here is the testcaseId in excel file)
	public String getCellDataByTestcaseId(String testcaseID, String columnNameToGetItsValue) {
		//List<Map<String,String>> testDataAllRows = getTestDataAsListOfHashMaps(excelFilePath, excelSheetName);   //get list of all hash maps
		List<Map<String,String>> testDataAllRows = getTestDataAsListOfHashMaps();   //get list of all hash maps
		
		String colValue = null;
		
		for (Map<String, String> myMap : testDataAllRows) {		//for each hashmap in all hash maps
			String currentTestcaseId = myMap.get("TestCaseId").toString().trim();		//get testcaseId value from excel
		
			if (currentTestcaseId == testcaseID || currentTestcaseId.contains(testcaseID.toString().trim()) == true ) {	// True --> the required map is found!
				//ReportManager.log("***TESTDATA*** --> TestCaseId (Key): '" + testcaseID + "' was found successfully in the excel test data file");
				colValue = myMap.get(columnNameToGetItsValue);		
				ReportManager.log("***TESTDATA*** --> The value of the coloumn '" + columnNameToGetItsValue + "' corresponding to the TestCaseId '" + testcaseID + "' of the excel file: '" + path +"' is --> '" + colValue + "'");
				break;
			}		
		}
		
		if (colValue == "") {
			ReportManager.log("The coloumn value is empty or could not be read!");  //ToDo: Log a failure!
		}
		
		return colValue;
	}
	
	
	//Get cell data by key (Key name is defined by the user of the method)
	public String getCellDataByKey(String keyColumnName , String keyColumnValue, String columnNameToGetItsValue) {
		//List<Map<String,String>> testDataAllRows = getTestDataAsListOfHashMaps(excelFilePath, excelSheetName);   //get list of all hash maps
		List<Map<String,String>> testDataAllRows = getTestDataAsListOfHashMaps();   //get list of all hash maps
		
		String colValue = null;
		
		for (Map<String, String> myMap : testDataAllRows) {		//for each hashmap in all hash maps
			String currentKeyColumnValue = myMap.get(keyColumnName).toString().trim();		//get key column value from excel
		
			if (currentKeyColumnValue == keyColumnValue || currentKeyColumnValue.contains(keyColumnValue.toString().trim()) == true ) {	// True --> the required map is found!
				//ReportManager.log("***TESTDATA*** --> TestCaseId (Key): '" + testcaseID + "' was found successfully in the excel test data file");
				colValue = myMap.get(columnNameToGetItsValue);		
				ReportManager.log("***TESTDATA*** --> The value of the coloumn '" + columnNameToGetItsValue + "' corresponding to the key column '" + keyColumnName  + "' and key value '" + keyColumnValue + "' of the excel file: '" + path +"' is --> '" + colValue + "'");
				break;
			}		
		}
		
		if (colValue == "") {
			ReportManager.log("The coloumn value is empty or could not be read!");  //ToDo: Log a failure!			
		}
		
		return colValue;
	}
	
}





