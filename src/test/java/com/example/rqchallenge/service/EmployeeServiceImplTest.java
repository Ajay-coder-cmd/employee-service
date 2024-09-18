package com.example.rqchallenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.rqchallenge.exception.EmployeeException;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeRequest;
import com.example.rqchallenge.util.EmployeeRestClient;

public class EmployeeServiceImplTest {

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@Mock
	private EmployeeRestClient employeeRestClient;

	@Mock
	private MockEmployeeServiceImpl mockEmployeeService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getAllEmployeesReturnsCorrectList() {
		when(employeeRestClient.getAllEmployees()).thenReturn(Arrays.asList(new Employee(), new Employee()));
		List<Employee> employees = employeeService.getAllEmployees();
		assertEquals(2, employees.size());
	}

	@Test
	public void getAllEmployeesReturnsCorrectListWhenRestClientFails() {
		when(employeeRestClient.getAllEmployees()).thenThrow(new RuntimeException());
		when(mockEmployeeService.getAllEmployees()).thenReturn(Arrays.asList(new Employee(), new Employee()));
		List<Employee> employees = employeeService.getAllEmployees();
		assertEquals(2, employees.size());
	}

	@Test
	public void getEmployeesByNameSearchReturnsCorrectList() {
		Employee employee = new Employee();
		employee.setEmployeeName("John Doe");
		when(employeeRestClient.getAllEmployees()).thenReturn(Arrays.asList(employee));
		List<Employee> employees = employeeService.getEmployeesByNameSearch("John");
		assertEquals(1, employees.size());
		assertEquals("John Doe", employees.get(0).getEmployeeName());
	}

	@Test
	public void getEmployeesByNameSearchThrowsExceptionWhenNoEmployeesFound() {
		when(employeeRestClient.getAllEmployees()).thenReturn(Collections.emptyList());
		assertThrows(EmployeeException.class, () -> employeeService.getEmployeesByNameSearch("John"));
	}

	@Test
	public void getEmployeeByIdReturnsCorrectEmployee() {
		Employee employee = new Employee();
		employee.setEmployeeName("John Doe");
		when(employeeRestClient.getEmployeeById(1)).thenReturn(employee);
		Employee result = employeeService.getEmployeeById(1);
		assertEquals("John Doe", result.getEmployeeName());
	}

	@Test
	public void getEmployeeByIdReturnsCorrectEmployeeWhenRestClientFails() {
		Employee employee = new Employee();
		employee.setEmployeeName("John Doe");
		when(employeeRestClient.getEmployeeById(1)).thenThrow(new RuntimeException());
		when(mockEmployeeService.getEmployeeById(1)).thenReturn(employee);
		Employee result = employeeService.getEmployeeById(1);
		assertEquals("John Doe", result.getEmployeeName());
	}

	@Test
	public void createEmployeeReturnsCorrectEmployee() {
		EmployeeRequest request = new EmployeeRequest("John Doe", 100000, 30, "");
		Employee employee = new Employee();
		employee.setEmployeeName("John Doe");
		when(employeeRestClient.createEmployee(request)).thenReturn(employee);
		Employee result = employeeService.createEmployee(request);
		assertEquals("John Doe", result.getEmployeeName());
	}

	@Test
	public void deleteEmployeeReturnsCorrectName() {
		Employee employee = new Employee();
		employee.setEmployeeName("John Doe");
		when(employeeRestClient.getEmployeeById(1)).thenReturn(employee);
		String result = employeeService.deleteEmployee(1);
		assertEquals("John Doe", result);
	}

	@Test
	public void deleteEmployeeReturnsCorrectNameWhenRestClientFails() {
		when(employeeRestClient.getEmployeeById(1)).thenThrow(new RuntimeException());
		when(mockEmployeeService.deleteEmployee(1)).thenReturn("John Doe");
		String result = employeeService.deleteEmployee(1);
		assertEquals("John Doe", result);
	}
}