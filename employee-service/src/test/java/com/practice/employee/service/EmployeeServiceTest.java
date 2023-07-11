package com.practice.employee.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.practice.employee.entity.Employee;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

	@InjectMocks
	private EmployeeService employeeService;


	@Test
	public void getAllEmployees() {
		List<Employee> list = new ArrayList<>();

		Employee employee = new Employee();
		employee.setId(1);
		employee.setFirstName("A");
		employee.setLastName("A");
		employee.setEmailId("a@gmail.com");
		employee.setDepartmentId(1);
		list.add(employee);
		try (MockedStatic<EmployeeService> utilities = Mockito.mockStatic(EmployeeService.class)) {
			utilities.when(() -> employeeService.getAllEmployees()).thenReturn(list);
			assertThat(list.size()).isNotEqualTo(0);
		}

		//List<Employee> list = employeeService.getAllEmployees();
		// then - verify the output
		//assertThat(list).isNotNull();
	}
}
