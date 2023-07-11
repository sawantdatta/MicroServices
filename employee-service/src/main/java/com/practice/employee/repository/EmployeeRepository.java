package com.practice.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

}
