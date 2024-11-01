package com.partail.rollback.employee.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.partail.rollback.department.entity.Department;
import com.partail.rollback.department.service.DepartmentService;
import com.partail.rollback.employee.dto.EmployeeDepartmentDto;
import com.partail.rollback.employee.dto.EmployeeDto;
import com.partail.rollback.employee.entity.Employee;
import com.partail.rollback.employee.exception.ResourceNotFoundException;
import com.partail.rollback.employee.repository.EmployeeRepository;


@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	DepartmentService departmentService;

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
	
	public ResponseEntity<Employee> getEmployeeById(Integer employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}

	public ResponseEntity<EmployeeDepartmentDto> getEmployeeDepartmentById(Integer employeeId) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setId(employee.getId());
		employeeDto.setFirstName(employee.getFirst_name());
		employeeDto.setLastName(employee.getLast_name());
		employeeDto.setEmailId(employee.getEmail_id());
		//employeeDto.setDepartmentId(departmentDto.getBody().getId());

		EmployeeDepartmentDto employeeDepartmentDto = new EmployeeDepartmentDto();
		//employeeDepartmentDto.setDepartmentDto(departmentDto.getBody());
		employeeDepartmentDto.setEmployeeDto(employeeDto);
		return ResponseEntity.ok().body(employeeDepartmentDto);
	}

	public Employee createEmployee(@javax.validation.Valid @RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Integer employeeId,
			@javax.validation.Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		employee.setEmail_id(employeeDetails.getEmail_id());
		employee.setFirst_name(employeeDetails.getFirst_name());
		employee.setLast_name(employeeDetails.getLast_name());
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	public Map<String, Boolean> deleteEmployee(@RequestParam(value = "id") Integer employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@org.springframework.transaction.annotation.Transactional
    public void createEmployeeWithPartialRollback(Employee employee, Department department) {
        // Create employee (this should always succeed if the employee data is correct)
        employeeRepository.save(employee);

        try {
            // Update department - we want this to be in its own transaction so that 
            // failure here doesn't affect employee creation.
            departmentService.createOrUpdateDepartmentWithNewTransaction(department);
        } catch (Exception e) {
            // Log or handle the department failure, but don't rethrow it to avoid rolling back the entire transaction
            System.out.println("Department update failed: " + e.getMessage());
        }
	}
	@org.springframework.transaction.annotation.Transactional
    public void createEmployeeWithFullRollback(Employee employee, Department department) {
    try {
            employeeRepository.save(employee);
            // Update department - we want this to be in its own transaction so that 
            // failure here doesn't affect employee creation.
            departmentService.createOrUpdateDepartment(department);
        } catch (Exception e) {
            // Log or handle the department failure, but don't rethrow it to avoid rolling back the entire transaction
            System.out.println("Department update fail" + e.getMessage());
            employeeRepository.delete(employee);
        }
	}
}