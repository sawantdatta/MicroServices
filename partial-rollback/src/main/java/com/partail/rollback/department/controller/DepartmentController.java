package com.partail.rollback.department.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.partail.rollback.department.entity.Department;
import com.partail.rollback.department.exception.ResourceNotFoundException;
import com.partail.rollback.department.service.DepartmentService;

@RestController
@RequestMapping("/api/v2")
@CrossOrigin("*")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;

	@GetMapping("/departments/getAllDepartments")
	public List<Department> getAllDepartments() {
		return departmentService.getAllDepartments();
	}

	@GetMapping("/departments/getDepartmentById/{deptId}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable(value = "deptId") Integer departmentId)
			throws ResourceNotFoundException {
		
		return departmentService.getDepartmentById(departmentId);
	}

	@PostMapping("/departments/saveDepartment")
	public Department createDepartment(@Valid @RequestBody Department department) {
		return departmentService.save(department);
	}

	@PutMapping("/departments/{deptId}")
	public ResponseEntity<Department> updateDepartment(@PathVariable(value = "deptId") Integer departmentId,
			@Valid @RequestBody Department department) throws ResourceNotFoundException {
		return departmentService.updateDepartment(departmentId, department);
	}

	@DeleteMapping("/departments")
	public Map<String, Boolean> deleteEmployee(@RequestParam(value = "deptId") Integer departmentId)
			throws ResourceNotFoundException {
		return departmentService.deleteEmployee(departmentId);
	}
	
	@DeleteMapping("/departments/deleteDepartment/{deptId}")
	public Map<String, Boolean> deleteEmployeeFromPathVariable(@PathVariable(value = "deptId") Integer departmentId)
			throws ResourceNotFoundException {
		return departmentService.deleteEmployee(departmentId);
	}
	

	@PutMapping("/departments/saveDepartmentByPut")
	public Department createDepartmentByPut(@Valid @RequestBody Department department) {
		return departmentService.save(department);
	}

	@PatchMapping("/departments/saveDepartmentByPatch/{deptId}")
	public Department createDepartmentByPatch(@PathVariable(value = "deptId") Integer departmentId,@Valid @RequestBody Department department) throws ResourceNotFoundException {
		return departmentService.createDepartmentByPatch(departmentId,department);
	}
}
