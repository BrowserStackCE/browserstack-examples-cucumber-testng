package browserstack.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataHelper {
	static String TestDataPath = System.getProperty("user.dir") + "\\ExcelFiles\\TestData.xlsx";
	public static HashMap<String, HashMap<String, String>> hm1 = new HashMap<>();
	static HashMap<String, String> currentHash=null;
	static String s3;

	public static void ReadTestData() throws IOException {

		FileInputStream file = new FileInputStream(TestDataPath);

		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		Row HeaderRow = sheet.getRow(0);
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			Row currentRow = sheet.getRow(i);
			HashMap<String, String> currentHash = new HashMap<String, String>();
			for (int j = 0; j < currentRow.getPhysicalNumberOfCells(); j++) {

				Cell currentCell1 = currentRow.getCell(0);
				s3 = currentCell1.getStringCellValue();
				System.out.println(s3);

				Cell currentCell = currentRow.getCell(j);
				currentHash.put(HeaderRow.getCell(j).getStringCellValue(), currentCell.getStringCellValue());

			}

		}

		
		hm1.put(s3, currentHash);
	}
}