package com.example.excel.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.excel.exception.StudentException;
import com.example.excel.model.Student;
import com.example.excel.repository.StudentRepository;
import com.example.excel.utility.ImportFromExcelUtility;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepo;

	public void addStudent(Student student) {
		if (studentRepo.findById(student.getStudentId()).isPresent()) {
			throw new StudentException("Student with this student ID is already present");
		} else
			studentRepo.save(student);
	}

	public List<Student> getListOfStudents() {
		return studentRepo.findAll();
	}

	public void uploadExcelFile(MultipartFile file) {
		if (ImportFromExcelUtility.hasExcelFormat(file)) {
			try {
				List<Student> students = ImportFromExcelUtility.excelToDb(file.getInputStream());
				studentRepo.saveAll(students);
			} catch (IOException e) {
				throw new StudentException("Fail to store excel data" + e.getMessage());
			}
		} else
			throw new StudentException("Please upload an Excel File");
	}
}
