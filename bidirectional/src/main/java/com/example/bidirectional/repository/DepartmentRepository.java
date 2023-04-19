package com.example.bidirectional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bidirectional.VO.JoinVO;
import com.example.bidirectional.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>{

	@Query("SELECT new com.example.bidirectional.VO.JoinVO(d.deptName, e.empName, e.empMail, e.address)" + 
			"FROM Department d LEFT JOIN d.employees e")
	List<JoinVO> getJoinInformation();
	
	@Query("SELECT new com.example.bidirectional.VO.JoinVO(d.deptName, e.empName, e.empMail, e.address)" + 
			"FROM Department d RIGHT JOIN d.employees e")
	List<JoinVO> getRightJoinInformation();
	
	@Query("SELECT new com.example.bidirectional.VO.JoinVO(d.deptName, e.empName, e.empMail, e.address)" + 
			"FROM Department d INNER JOIN d.employees e")
	List<JoinVO> getInnerJoinInformation();
	
	@Query("SELECT new com.example.bidirectional.VO.JoinVO(d.deptName, e.empName, e.empMail, e.address)" + 
			"FROM Department d CROSS JOIN Employee e")
	List<JoinVO> getCrossJoinInformation();
	
}
