package com.example.bidirectional.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Student {

	@Id
	private int StudentId;
	private String studentName;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "liked_courses", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> likedCourses;

	public int getStudentId() {
		return StudentId;
	}

	public void setStudentId(int studentId) {
		StudentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public List<Course> getLikedCourses() {
		return likedCourses;
	}

	public void setLikedCourses(List<Course> likedCourses) {
		this.likedCourses = likedCourses;
	}
}
