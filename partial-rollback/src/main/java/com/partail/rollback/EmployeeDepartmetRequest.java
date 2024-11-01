package com.partail.rollback;

import com.partail.rollback.department.entity.Department;
import com.partail.rollback.employee.entity.Employee;

public class EmployeeDepartmetRequest {
	private Employee employee;
	private Department department;
	
	
	public EmployeeDepartmetRequest(Employee employee, Department department) {
		super();
		this.employee = employee;
		this.department = department;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
}
