package com.practice.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.employee.entity.Employee;

@Service
public class EmployeeService {

	public List<Employee> getAllEmployees() {
		return getAllEmp();
	}

	private static List<Employee> getAllEmp() {
		List<Employee> list = new ArrayList<>();

		Employee employee = new Employee();
		employee.setId(1);
		employee.setFirstName("A");
		employee.setLastName("A");
		employee.setEmailId("a@gmail.com");
		employee.setDepartmentId(1);
		list.add(employee);
		return list;
	}
}
