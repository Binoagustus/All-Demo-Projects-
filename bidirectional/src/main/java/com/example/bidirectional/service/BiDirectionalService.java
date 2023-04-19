package com.example.bidirectional.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bidirectional.VO.JoinVO;
import com.example.bidirectional.exception.BiDirectionalException;
import com.example.bidirectional.model.Course;
import com.example.bidirectional.model.Department;
import com.example.bidirectional.model.Employee;
import com.example.bidirectional.model.Student;
import com.example.bidirectional.repository.CourseRepository;
import com.example.bidirectional.repository.DepartmentRepository;
import com.example.bidirectional.repository.EmployeeRepository;
import com.example.bidirectional.repository.StudentRepository;

@Service
public class BiDirectionalService {

	@Autowired
	CourseRepository courseRepo;
	
	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	EmployeeRepository empRepo;
	
	@Autowired
	DepartmentRepository deptRepo;
	
	public void addCourse(Course course) {
		if(courseRepo.findByCourseName(course.getCourseName()).isPresent()) {
			throw new BiDirectionalException("Course already present");
		} else
			courseRepo.save(course);
	}

	public Student addStudent(Student student) {
		return studentRepo.save(student);
	}

	public void deleteCourseByStudentId(int studentId, int courseId) {
		Optional<Student> student = studentRepo.findById(studentId);
		List<Course> courses = student.get().getLikedCourses();
		for(Course course : courses) {
			if(course.getCourseId() == courseId) {
				courses.remove(course);
			}
		}
		studentRepo.save(student.get());
	}

	public Employee addEmployee(Employee employee) {
		return empRepo.save(employee);
	}

	public List<Employee> viewAllEmployees() {
		return empRepo.findAll();
	}

	public Department addDepartmentWithEmployee(Department department) {
		return deptRepo.save(department);
	}

	public List<Department> viewAllDepartmentWithEmployee() {
		return deptRepo.findAll();
	}

	public List<JoinVO> getLeftJoinInfo() {
		return deptRepo.getJoinInformation();
	}

	public List<JoinVO> getRightJoinInfo() {
		return deptRepo.getRightJoinInformation();
	}	
	
	public List<JoinVO> getInnerJoinInfo() {
		return deptRepo.getInnerJoinInformation();
	}

	public List<JoinVO> getCrossJoinInfo() {
		return deptRepo.getCrossJoinInformation();
	}
}
