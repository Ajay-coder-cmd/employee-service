package com.example.rqchallenge.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeRequest;
import com.example.rqchallenge.service.IEmployeeService;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IEmployeeService employeeService;

	private Employee employee;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		employee = new Employee(1, "John Doe", 75000, 30, "http://example.com/profile.jpg");
	}

	@Test
	public void testGetAllEmployees() throws Exception {
		List<Employee> employees = Arrays.asList(employee);

		when(employeeService.getAllEmployees()).thenReturn(employees);

		mockMvc.perform(get("/employees").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(employee.getId()))
				.andExpect(jsonPath("$[0].employee_name").value(employee.getEmployeeName()))
				.andExpect(jsonPath("$[0].employee_salary").value(employee.getEmployeeSalary()))
				.andExpect(jsonPath("$[0].employee_age").value(employee.getEmployeeAge()))
				.andExpect(jsonPath("$[0].profile_image").value(employee.getProfileImage()));
	}

	@Test
	public void testGetEmployeeById() throws Exception {
		when(employeeService.getEmployeeById(1)).thenReturn(employee);

		mockMvc.perform(get("/employees/{id}", 1).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(employee.getId()))
				.andExpect(jsonPath("$.employee_name").value(employee.getEmployeeName()))
				.andExpect(jsonPath("$.employee_salary").value(employee.getEmployeeSalary()))
				.andExpect(jsonPath("$.employee_age").value(employee.getEmployeeAge()))
				.andExpect(jsonPath("$.profile_image").value(employee.getProfileImage()));
	}

	@Test
	public void testGetEmployeesByNameSearch() throws Exception {
		List<Employee> employees = Arrays.asList(employee);

		when(employeeService.getEmployeesByNameSearch("John")).thenReturn(employees);

		mockMvc.perform(get("/employees/search/{searchString}", "John").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(employee.getId()))
				.andExpect(jsonPath("$[0].employee_name").value(employee.getEmployeeName()))
				.andExpect(jsonPath("$[0].employee_salary").value(employee.getEmployeeSalary()))
				.andExpect(jsonPath("$[0].employee_age").value(employee.getEmployeeAge()))
				.andExpect(jsonPath("$[0].profile_image").value(employee.getProfileImage()));
	}

	@Test
	public void testCreateEmployee() throws Exception {
		EmployeeRequest employeeRequest = new EmployeeRequest("John Doe", 30, 75000, "http://example.com/profile.jpg");
		Employee employee = new Employee(1, "John Doe", 75000, 30, "http://example.com/profile.jpg");

		when(employeeService.createEmployee(any(EmployeeRequest.class))).thenReturn(employee);

		mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(
				"{\"name\": \"John Doe\", \"salary\": 75000, \"age\": 30, \"profileImage\": \"http://example.com/profile.jpg\"}"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(employee.getId()))
				.andExpect(jsonPath("$.employee_name").value(employee.getEmployeeName()))
				.andExpect(jsonPath("$.employee_salary").value(employee.getEmployeeSalary()))
				.andExpect(jsonPath("$.employee_age").value(employee.getEmployeeAge()))
				.andExpect(jsonPath("$.profile_image").value(employee.getProfileImage()));
	}

	@Test
	public void testDeleteEmployeeById() throws Exception {
		when(employeeService.deleteEmployee(1)).thenReturn("Employee deleted");

		mockMvc.perform(delete("/employees/{id}", 1).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string("Employee deleted"));
	}

	@Test
	public void testGetHighestSalaryOfEmployees() throws Exception {
		when(employeeService.getHighestSalaryOfEmployees()).thenReturn(75000);

		mockMvc.perform(get("/employees/highestSalary").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").value(75000));
	}

	@Test
	public void testGetTop10HighestEarningEmployees() throws Exception {
		List<Employee> employees = Arrays.asList(employee);

		when(employeeService.getTop10HighestEarningEmployees()).thenReturn(employees);

		mockMvc.perform(get("/employees/topTenHighestEarning").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(employee.getId()))
				.andExpect(jsonPath("$[0].employee_name").value(employee.getEmployeeName()))
				.andExpect(jsonPath("$[0].employee_salary").value(employee.getEmployeeSalary()))
				.andExpect(jsonPath("$[0].employee_age").value(employee.getEmployeeAge()))
				.andExpect(jsonPath("$[0].profile_image").value(employee.getProfileImage()));
	}
}
