package com.practice.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
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
import org.springframework.web.reactive.function.client.WebClient;

import com.practice.employee.apiclient.APIClient;
import com.practice.employee.dto.DepartmentDto;
import com.practice.employee.dto.EmployeeDepartmentDto;
import com.practice.employee.dto.EmployeeDto;
import com.practice.employee.entity.Employee;
import com.practice.employee.exception.ResourceNotFoundException;
import com.practice.employee.repository.EmployeeRepository;
import com.practice.employee.service.EmployeeService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	/*
	 * @Autowired private RestTemplate restTemplate;
	 */

	private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private WebClient webClient;

	@Autowired
	private APIClient apiClient;

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EmployeeService employeeService;


	/*
	 * @Autowired public WebClient webClient;
	 */

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
	@CircuitBreaker(name = "${spring.application.name}",fallbackMethod ="getDefaultMethodDepartment")
	//@Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultMethodDepartment")
	public ResponseEntity<EmployeeDepartmentDto> getEmployeeDepartmentById(
			@PathVariable(value = "id") Integer employeeId) throws ResourceNotFoundException {

		LOGGER.info("inside getEmployeeDepartmentById() method");
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		DepartmentDto departmentDto = webClient.get()
				.uri("http://localhost:8082/api/v2/departments/getDepartmentById/" + employee.getDepartmentId())
				.retrieve().bodyToMono(DepartmentDto.class).block();

		/*
		 * ResponseEntity<DepartmentDto> departmentDto = restTemplate.getForEntity(
		 * "http://localhost:8082/api/v1/departments/getDepartmentById/" +
		 * employee.getDepartmentId(), DepartmentDto.class);
		 */

		// DepartmentDto departmentDto =
		// apiClient.getDepartmentById(employee.getDepartmentId());

		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setId(employee.getId());
		employeeDto.setFirstName(employee.getFirstName());
		employeeDto.setLastName(employee.getLastName());
		employeeDto.setEmailId(employee.getEmailId());
		employeeDto.setDepartmentId(departmentDto.getId());

		EmployeeDepartmentDto employeeDepartmentDto = new EmployeeDepartmentDto();
		employeeDepartmentDto.setDepartmentDto(departmentDto);
		employeeDepartmentDto.setEmployeeDto(employeeDto);
		return ResponseEntity.ok().body(employeeDepartmentDto);
	}

	@PostMapping("/employees/saveEmployee")
	public Employee createEmployee(@Valid @RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Integer employeeId,
			@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employee.setEmailId(employeeDetails.getEmailId());
		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
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
		employeeDto.setFirstName(employee.getFirstName());
		employeeDto.setLastName(employee.getLastName());
		employeeDto.setEmailId(employee.getEmailId());
		employeeDto.setDepartmentId(employee.getDepartmentId());

		EmployeeDepartmentDto employeeDepartmentDto = new EmployeeDepartmentDto();
		employeeDepartmentDto.setDepartmentDto(departmentDto);
		employeeDepartmentDto.setEmployeeDto(employeeDto);
		return ResponseEntity.ok().body(employeeDepartmentDto);
	}
}
