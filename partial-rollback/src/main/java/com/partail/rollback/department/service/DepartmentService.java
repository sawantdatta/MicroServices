package com.partail.rollback.department.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.partail.rollback.department.entity.Department;
import com.partail.rollback.department.exception.ResourceNotFoundException;
import com.partail.rollback.department.repo.DepartmentRepository;

import antlr.StringUtils;


@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	
		public List<Department> getAllDepartments() {			
			return departmentRepository.findAll();
		}
		
		public ResponseEntity<Department> getDepartmentById(Integer departmentId)
				throws ResourceNotFoundException {
			Department department = departmentRepository.findById(departmentId)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + departmentId));
			return ResponseEntity.ok().body(department);
		}

		public Department createDepartment(@RequestBody Department department) {
			return departmentRepository.save(department);
		}

		public ResponseEntity<Department> updateDepartment(Integer departmentId,
				@RequestBody Department department) throws ResourceNotFoundException {
			Department department2 = departmentRepository.findById(departmentId)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + departmentId));

			department2.setDept_id(department.getDept_id());
			department2.setDept_name(department.getDept_name());

			final Department updatedDepartment = departmentRepository.save(department2);
			return ResponseEntity.ok(updatedDepartment);
		}

		public Map<String, Boolean> deleteEmployee(Integer departmentId)
				throws ResourceNotFoundException {
			Department department = departmentRepository.findById(departmentId)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + departmentId));

			departmentRepository.delete(department);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return response;
		}

		public Department save(@javax.validation.Valid Department department) {
			return departmentRepository.save(department);
		}

		 @Transactional(propagation = Propagation.REQUIRES_NEW)
		 public void createOrUpdateDepartmentWithNewTransaction(Department department) {
		        // If the department has an ID, we consider it as an update
		        /*if (department.getId() != null) {
		            // Optional: You can check if the department exists before updating
		            return departmentRepository.findById(department.getId())
		                    .map(existingDepartment -> {
		                        existingDepartment.setDepartmentName(department.getDepartmentName());
		                        return departmentRepository.save(existingDepartment);
		                    })
		                    .orElseGet(() -> departmentRepository.save(department)); // Create if not found
		        } else {
		            // Create a new department if no ID is provided
		            return departmentRepository.save(department);
		        }*/
		        // Save or update the department
		        // If any exception occurs, this transaction will be rolled back independently
		        departmentRepository.save(department);

		        // Simulate a failure for testing purposes
		        if(department.getDept_name().equals("Invalid")) {
		            throw new RuntimeException("Invalid department name");
		    }
		 }
		 @Transactional(propagation = Propagation.REQUIRES_NEW)
		 public void createOrUpdateDepartment(Department department) {
		        // If the department has an ID, we consider it as an update
		        /*if (department.getId() != null) {
		            // Optional: You can check if the department exists before updating
		            return departmentRepository.findById(department.getId())
		                    .map(existingDepartment -> {
		                        existingDepartment.setDepartmentName(department.getDepartmentName());
		                        return departmentRepository.save(existingDepartment);
		                    })
		                    .orElseGet(() -> departmentRepository.save(department)); // Create if not found
		        } else {
		            // Create a new department if no ID is provided
		            return departmentRepository.save(department);
		        }*/
		        // Save or update the department
		        // If any exception occurs, this transaction will be rolled back independently
		        departmentRepository.save(department);

		        // Simulate a failure for testing purposes
		        if(department.getDept_name().equals("Invalid")) {
		            throw new RuntimeException("Invalid department name");
		    }
		 }
		 public Department createDepartmentByPatch(Integer departmentId,Department department)
					throws ResourceNotFoundException {
				Department department1 = departmentRepository.findById(departmentId)
						.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + departmentId));
						if(!Objects.isNull(department.getDept_name()))
						{
					       department1.setDept_name(department.getDept_name());
						}
						if(!Objects.isNull(department.getSalary()))
						{
					       department1.setSalary(department.getSalary());
						}
				return departmentRepository.save(department1);
				
			}

}

