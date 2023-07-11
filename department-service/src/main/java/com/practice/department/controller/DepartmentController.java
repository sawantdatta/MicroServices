package com.practice.department.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.result.UpdateResult;
import com.practice.department.entity.Department;
import com.practice.department.exception.ResourceNotFoundException;
import com.practice.department.repo.DepartmentRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2")
public class DepartmentController {
	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@GetMapping("/departments/getAllDepartments")
	public List<Department> getAllDepartments() {
		/*
		 * Query query = new Query(Criteria.where("name").is("abc")); Update update =
		 * new Update(); update.set("quantity", "text"); UpdateResult result =
		 * mongoTemplate.updateFirst(query, update, Department.class); if (result ==
		 * null) System.out.println("No documents updated"); else
		 * System.out.println(result.getModifiedCount() + " document(s) updated..");
		 */
		return departmentRepository.findAll();
	}

	@GetMapping("/departments/getDepartmentById/{id}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable(value = "id") Integer departmentId)
			throws ResourceNotFoundException {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + departmentId));
		return ResponseEntity.ok().body(department);
	}

	@PostMapping("/departments/saveDepartment")
	public Department createDepartment(@Valid @RequestBody Department department) {
		return departmentRepository.save(department);
	}

	@PutMapping("/departments/{id}")
	public ResponseEntity<Department> updateDepartment(@PathVariable(value = "id") Integer departmentId,
			@Valid @RequestBody Department department) throws ResourceNotFoundException {
		Department department2 = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + departmentId));

		department2.setId(department.getId());
		department2.setDepartmentName(department.getDepartmentName());

		final Department updatedDepartment = departmentRepository.save(department2);
		return ResponseEntity.ok(updatedDepartment);
	}

	@DeleteMapping("/departments")
	public Map<String, Boolean> deleteEmployee(@RequestParam(value = "id") Integer departmentId)
			throws ResourceNotFoundException {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + departmentId));

		departmentRepository.delete(department);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
