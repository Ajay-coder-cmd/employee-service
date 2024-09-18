package com.example.rqchallenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.example.rqchallenge.exception.EmployeeException;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeRequest;

public class MockEmployeeServiceImplTest {

	@InjectMocks
	private MockEmployeeServiceImpl mockEmployeeService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getAllEmployeesReturnsCorrectList() {
		List<Employee> employees = mockEmployeeService.getAllEmployees();
		assertEquals(24, employees.size());
	}

	@Test
	public void getEmployeesByNameSearchReturnsCorrectList() {
		List<Employee> employees = mockEmployeeService.getEmployeesByNameSearch("Tiger");
		assertEquals(1, employees.size());
		assertEquals("Tiger Nixon", employees.get(0).getEmployeeName());
	}

	@Test
	public void getEmployeeByIdReturnsCorrectEmployee() {
		Employee employee = mockEmployeeService.getEmployeeById(1);
		assertEquals("Tiger Nixon", employee.getEmployeeName());
	}

	@Test
	public void getEmployeeByIdThrowsExceptionForInvalidId() {
		assertThrows(EmployeeException.class, () -> mockEmployeeService.getEmployeeById(100));
	}

	@Test
	public void getHighestSalaryOfEmployeesReturnsCorrectValue() {
		int highestSalary = mockEmployeeService.getHighestSalaryOfEmployees();
		assertEquals(725000, highestSalary);
	}

	@Test
	public void getTop10HighestEarningEmployeesReturnsCorrectList() {
		List<Employee> employees = mockEmployeeService.getTop10HighestEarningEmployees();
		assertEquals(10, employees.size());
		assertEquals(725000, employees.get(0).getEmployeeSalary());
	}

	@Test
	public void createEmployeeAddsEmployeeToList() {
		EmployeeRequest employeeRequest = new EmployeeRequest("New Employee", 100000, 30, "");
		Employee newEmployee = mockEmployeeService.createEmployee(employeeRequest);
		assertEquals("New Employee", newEmployee.getEmployeeName());
		assertEquals(25, mockEmployeeService.getAllEmployees().size());
	}

	@Test
	public void deleteEmployeeRemovesEmployeeFromList() {
		String deletedEmployeeName = mockEmployeeService.deleteEmployee(1);
		assertEquals("Tiger Nixon", deletedEmployeeName);
		assertEquals(23, mockEmployeeService.getAllEmployees().size());
	}

	@Test
	public void deleteEmployeeReturnsNullForInvalidId() {
		assertThrows(EmployeeException.class, () -> mockEmployeeService.deleteEmployee(100));
	}
}
