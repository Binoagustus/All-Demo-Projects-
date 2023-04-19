package com.example.bidirectional.VO;

public class JoinVO {

	private String deptName;
	private String empName;
	private String empMail;
	private String address;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpMail() {
		return empMail;
	}

	public void setEmpMail(String empMail) {
		this.empMail = empMail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public JoinVO(String deptName, String empName, String empMail, String address) {
		super();
		this.deptName = deptName;
		this.empName = empName;
		this.empMail = empMail;
		this.address = address;
	}
}
