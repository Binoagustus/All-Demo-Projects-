package com.example.excel.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.example.excel.exception.StudentException;
import com.example.excel.model.Student;

public class ImportFromExcelUtility {

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "StudentId", "StudentName", "StudentDept" };
	static String SHEET = "Student";

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<Student> excelToDb(InputStream inputStream) {
		try {
			Workbook workbook = new XSSFWorkbook(inputStream);

			System.out.println(workbook);
			Sheet sheet = workbook.getSheet(SHEET);
			System.out.println(sheet);

			if (sheet == null) {
				throw new StudentException("no values from inputstream");
			}

			Iterator<Row> rows = sheet.iterator();

			List<Student> students = new ArrayList<Student>();

			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				Student student = new Student();

				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();

					switch (cellIdx) {
					case 0:
						student.setStudentId((int) currentCell.getNumericCellValue());
						break;

					case 1:
						student.setStudentName(currentCell.getStringCellValue());
						break;

					case 2:
						student.setStudentDept(currentCell.getStringCellValue());
						break;

					default:
						break;
					}

					cellIdx++;
				}

				students.add(student);
			}

			workbook.close();

			return students;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}

	}
}
