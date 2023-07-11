package com.practice.employee.apiclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.practice.employee.dto.DepartmentDto;

@FeignClient(value = "DEPARTMENT-SERVICE", url = "http://localhost:8082")
public interface APIClient {

	@GetMapping("api/v1/departments/getDepartmentById/{id}")
	DepartmentDto getDepartmentById(@PathVariable("id") Integer departmentId);
}
