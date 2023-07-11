package com.practice.department.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.department.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
