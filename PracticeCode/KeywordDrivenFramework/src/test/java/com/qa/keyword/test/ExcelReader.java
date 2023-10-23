package com.qa.keyword.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public XSSFWorkbook wb;
	public XSSFSheet xs;
	public FileInputStream fis;
	public List<List<String>> l;

	public Map<String, List<String>> dataFetcher(String Path) {

		try {
			fis = new FileInputStream(Path);
			wb = new XSSFWorkbook(fis);
			xs = wb.getSheet("Test Steps");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, List<String>> m = new HashMap<String, List<String>>();

		this.steps(xs);

		for (int i = 0; i < xs.getRow(0).getLastCellNum(); i++) {

			m.put(xs.getRow(0).getCell(i).getStringCellValue(), l.get(i));
		}

		return m;

	}

	public void steps(XSSFSheet sheet) {
		l = new ArrayList<List<String>>();
		for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {

			l.add(this.keywords(sheet, j));
		}
	}

	public List<String> keywords(XSSFSheet sheet, int j) {
		List<String> l1 = new ArrayList<String>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			l1.add(sheet.getRow(i).getCell(j).getStringCellValue());
		}
		return l1;
	}

}
