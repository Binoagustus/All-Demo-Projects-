package com.example.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.excel.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{

}
