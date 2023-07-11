package com.practice.employee.dto;

import java.io.Serializable;

public class EmployeeDepartmentDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4035994833251413382L;

	private DepartmentDto departmentDto;
	private EmployeeDto employeeDto;
	public DepartmentDto getDepartmentDto() {
		return departmentDto;
	}
	public void setDepartmentDto(DepartmentDto departmentDto) {
		this.departmentDto = departmentDto;
	}
	public EmployeeDto getEmployeeDto() {
		return employeeDto;
	}
	public void setEmployeeDto(EmployeeDto employeeDto) {
		this.employeeDto = employeeDto;
	}
}