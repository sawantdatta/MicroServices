package com.partail.rollback.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.partail.rollback.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

	//public Employee findByEmailId(String emailId);
}
