package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	/*
	 * declaring instance variable value as null for later assigning of object and
	 * safer use at later period
	 */
	public String path;
	public FileInputStream fileIs = null;
	public FileOutputStream fileOs = null;
	private XSSFWorkbook workBook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	/*
	 * We are declaring constructor for the inputStream as it would save the data
	 * for temporary use(saved temporarily until it stops)
	 */

	public ExcelUtils(String path) {
		this.path = path;
		try {
			fileIs = new FileInputStream(path);
			workBook = new XSSFWorkbook(fileIs);
			sheet = workBook.getSheetAt(0);
			fileIs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method to get cell data using DataFormatter
	public String getCellData(String sheetName, int rowNum, int colNum) {

		sheet = workBook.getSheet(sheetName);

		DataFormatter formatter = new DataFormatter();
		String data;
		try {
			data = formatter.formatCellValue(sheet.getRow(rowNum).getCell(colNum));
		} catch (Exception e) {
			data = "";
		}

		return data;
	}

	// Method to return entire data (excluding header) as Object[][] for
	// dataProvider
	public Object[][] testData(String sheetName) {
		sheet = workBook.getSheet(sheetName);

		int lastRowNum = sheet.getLastRowNum();
		int lastCellNum = sheet.getRow(0).getLastCellNum();

		Object[][] data = new Object[lastRowNum][lastCellNum];

		for (int i = 0; i < lastRowNum; i++) {
			for (int j = 0; j < lastCellNum; j++) {
				data[i][j] = getCellData(sheetName, i + 1, j);
			}
		}
		return data;
	}

	// Get row count
	public int getRowCount(String sheetName) {
		sheet = workBook.getSheet(sheetName);
		int rowCount = (sheet == null) ? 0 : sheet.getLastRowNum(); // ? means return, : means else
																	// this is a shorter code for if condition
		return rowCount;
	}

	// Get column count in a specific row
	public int getColumnCount(String sheetName, int rowNum) {
		sheet = workBook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		int colCount = (row == null) ? 0 : row.getLastCellNum();
		return colCount;
	}

}
