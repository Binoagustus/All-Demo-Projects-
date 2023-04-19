package com.example.bidirectional.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bidirectional.Response;
import com.example.bidirectional.VO.JoinVO;
import com.example.bidirectional.model.Course;
import com.example.bidirectional.model.Department;
import com.example.bidirectional.model.Employee;
import com.example.bidirectional.model.Student;
import com.example.bidirectional.service.BiDirectionalService;

@RestController
@RequestMapping("/mapping")
public class BiDirectionalController {

	@Autowired
	BiDirectionalService service;
	
	@PostMapping("/addCourse")
	public ResponseEntity<Response> addCourses(@RequestBody Course course) {
		service.addCourse(course);
		Response response = new Response("course added");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/addStudent")
	public ResponseEntity<Response> addStudent(@RequestBody Student student) {
		Student studentResponse = service.addStudent(student);
		Response response = new Response("student added", studentResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/delete")
	public ResponseEntity<Response> deleteCourseForAStudent(@RequestParam int studentId, @RequestParam int courseId) {
		service.deleteCourseByStudentId(studentId, courseId);
		Response response= new Response("course deleted for a student");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/addEmployee")
	public ResponseEntity<Response> addEmployee(@RequestBody Employee employee) {
		Employee employeeResponse = service.addEmployee(employee);
		Response response = new Response("employee added", employeeResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/viewAllEmployees")
	public ResponseEntity<Response> viewEmployees() {
		List<Employee> employees = service.viewAllEmployees();
		Response response = new Response("List of Employees ", employees);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/addDepartment")
	public ResponseEntity<Response> addEmployee(@RequestBody Department department) {
		Department departmentResponse = service.addDepartmentWithEmployee(department);
		Response response = new Response("department with employee added", departmentResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/viewAllDepartments")
	public ResponseEntity<Response> viewDepartmentsWithEmployee() {
		List<Department> departments = service.viewAllDepartmentWithEmployee();
		Response response = new Response("List of departments ", departments);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getLeftJoin")
	public ResponseEntity<Response> getLeftJoin() {
		List<JoinVO> getJoinInfo = service.getLeftJoinInfo();
		Response response = new Response("List of Join Info", getJoinInfo);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getRightJoin")
	public ResponseEntity<Response> getRightJoin() {
		List<JoinVO> getRightJoinInfo = service.getRightJoinInfo();
		Response response = new Response("List of Right Join Info", getRightJoinInfo);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getInnerJoin")
	public ResponseEntity<Response> getInnerJoin() {
		List<JoinVO> getInnerJoinInfo = service.getInnerJoinInfo();
		Response response = new Response("List of Inner Join Info", getInnerJoinInfo);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getCrossJoin")
	public ResponseEntity<Response> getCrossJoin() {
		List<JoinVO> getCrossJoin = service.getCrossJoinInfo();
		Response response = new Response("List of Cross Join Info", getCrossJoin);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
