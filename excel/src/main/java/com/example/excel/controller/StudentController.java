package com.example.excel.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.excel.Response;
import com.example.excel.model.Student;
import com.example.excel.service.StudentService;
import com.example.excel.utility.ExportToExcelUtility;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	StudentService studentService;

	@PostMapping("/addStudent")
	public ResponseEntity<Response> addStudent(@RequestBody Student student) {
		studentService.addStudent(student);
		Response response = new Response("student added succesfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/getAllStudents")
	public List<Student> getListOfStudents() {
		return studentService.getListOfStudents();
	}

	@GetMapping("/export-to-excel")
	public void exportIntoExcelFile(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=student" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<Student> listOfStudents = studentService.getListOfStudents();
		ExportToExcelUtility generator = new ExportToExcelUtility(listOfStudents);
		generator.generateExcelFile(response);
	}
	
	@PostMapping("/import-from-excel")
	public ResponseEntity<Response> uploadExcelFile(@RequestParam MultipartFile file) {
		studentService.uploadExcelFile(file);
		Response response = new Response("file uploaded succesfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
