package com.partail.rollback.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.partail.rollback.EmployeeDepartmetRequest;
import com.partail.rollback.department.entity.Department;
import com.partail.rollback.employee.dto.DepartmentDto;
import com.partail.rollback.employee.dto.EmployeeDepartmentDto;
import com.partail.rollback.employee.dto.EmployeeDto;
import com.partail.rollback.employee.entity.Employee;
import com.partail.rollback.employee.exception.ResourceNotFoundException;
import com.partail.rollback.employee.repository.EmployeeRepository;
import com.partail.rollback.employee.service.EmployeeService;


@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	/*
	 * @Autowired private RestTemplate restTemplate;
	 */

	/*
	 * @Autowired private WebClient webClient;
	 */

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;


	@GetMapping("/employees/getAllEmployees")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}
	

	@GetMapping("/employees/getEmployeeById/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Integer employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}

	@GetMapping("/employees/getEmployeeDepartment/{id}")
	public ResponseEntity<EmployeeDepartmentDto> getEmployeeDepartmentById(
			@PathVariable(value = "id") Integer employeeId) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		/*
		 * Integer id = 23; ResponseEntity<DepartmentDto> departmentDto = restTemplate
		 * .getForEntity(
		 * "http://localhost:8082/api/v1/departments/getDepartmentById/{id}" // + //
		 * employee.getDepartmentId(), , DepartmentDto.class, id);
		 */
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

	@PostMapping("/employees/saveEmployee")
	public Employee createEmployee(@Valid @RequestBody Employee employee) {
		return employeeService.createEmployee(employee);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Integer employeeId,
			@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employee.setEmail_id(employeeDetails.getEmail_id());
		employee.setLast_name(employeeDetails.getLast_name());
		employee.setFirst_name(employeeDetails.getFirst_name());
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping("/employees")
	public Map<String, Boolean> deleteEmployee(@RequestParam(value = "id") Integer employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	public ResponseEntity<EmployeeDepartmentDto> getDefaultMethodDepartment(Integer employeeId, Exception exception)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setId(1);
		departmentDto.setDepartmentName("ABC");

		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setId(employee.getId());
		employeeDto.setFirstName(employee.getFirst_name());
		employeeDto.setLastName(employee.getLast_name());
		employeeDto.setEmailId(employee.getEmail_id());
		employeeDto.setDepartmentId(employee.getDepartment_id());

		EmployeeDepartmentDto employeeDepartmentDto = new EmployeeDepartmentDto();
		employeeDepartmentDto.setDepartmentDto(departmentDto);
		employeeDepartmentDto.setEmployeeDto(employeeDto);
		return ResponseEntity.ok().body(employeeDepartmentDto);
	}
	 @PostMapping("/employees/saveEmployeeAndDepartment")
	 public void createEmployeeWithPartialRollback(@RequestBody EmployeeDepartmetRequest employeeDepartmetRequest)
	 {
					 /*
					  *  {
			    "employee": {
			        "first_name": "A",
			        "last_name": "A",
			        "email_id": "A",
			        "department_id": 1
			    },
			    "department": {
			        "dept_id": 1,
			        "dept_name": "Invalid",
			        "salary": 1000
			    }
			} 
		  */
		 Employee employee=employeeDepartmetRequest.getEmployee();
		 Department department=employeeDepartmetRequest.getDepartment();
		 employeeService.createEmployeeWithPartialRollback(employee, department);
	 }
	 
	 @PostMapping("/employees/saveEmployeeAndDepartmentFullRollBack")
	 public void createEmployeeWithFullRollback(@RequestBody EmployeeDepartmetRequest employeeDepartmetRequest)
	 {
		 Employee employee=employeeDepartmetRequest.getEmployee();
		 Department department=employeeDepartmetRequest.getDepartment();
		 employeeService.createEmployeeWithFullRollback(employee, department);
	 }
}