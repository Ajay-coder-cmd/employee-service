package com.example.rqchallenge.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeRequest;
import com.example.rqchallenge.service.IEmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Employee API", description = "Operations related to employees")
public class EmployeeController {
	private final IEmployeeService employeeService;
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	public EmployeeController(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/employees")
	@Operation(summary = "Get all employees", description = "Retrieve a list of all employees")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful retrieval of employees"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<Employee>> getAllEmployees() {
		logger.info("Fetching all employees");
		List<Employee> employees = employeeService.getAllEmployees();
		return ResponseEntity.ok(employees);
	}

	@GetMapping("/employees/search/{searchString}")
	@Operation(summary = "Search employees by name", description = "Retrieve employees matching the search string")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful retrieval of employees"),
			@ApiResponse(responseCode = "404", description = "No employees found for the search term"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<Employee>> getEmployeesByNameSearch(
			@PathVariable @Parameter(description = "Name to search for") String searchString) {
		logger.info("Fetching employees by name search: {}", searchString);
		List<Employee> employees = employeeService.getEmployeesByNameSearch(searchString);
		return ResponseEntity.ok(employees);
	}

	@GetMapping("/employees/{id}")
	@Operation(summary = "Get employee by ID", description = "Retrieve an employee by their ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful retrieval of employee"),
			@ApiResponse(responseCode = "404", description = "Employee not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Employee> getEmployeeById(
			@PathVariable @Parameter(description = "ID of the employee to fetch") int id) {
		logger.info("Fetching employee by id: {}", id);
		Employee employee = employeeService.getEmployeeById(id);
		return ResponseEntity.ok(employee);
	}

	@GetMapping("/employees/highestSalary")
	@Operation(summary = "Get the highest salary of employees", description = "Retrieve the highest salary among employees")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful retrieval of highest salary"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
		logger.info("Fetching highest salary of employees");
		int highestSalary = employeeService.getHighestSalaryOfEmployees();
		return ResponseEntity.ok(highestSalary);
	}

	@GetMapping("/employees/topTenHighestEarning")
	@Operation(summary = "Get top 10 highest earning employees", description = "Retrieve the top 10 highest earning employees")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful retrieval of top earning employees"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<Employee>> getTop10HighestEarningEmployees() {
		logger.info("Fetching top ten highest earning employee names");
		List<Employee> topEarningEmployees = employeeService.getTop10HighestEarningEmployees();
		return ResponseEntity.ok(topEarningEmployees);
	}

	@PostMapping("/employees")
	@Operation(summary = "Create a new employee", description = "Create a new employee record")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Employee successfully created"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Employee> createEmployee(
			@RequestBody @Parameter(description = "Employee details to be created") @Valid EmployeeRequest employeeInput) {
		logger.info("Creating employee: {}", employeeInput);
		Employee employee = employeeService.createEmployee(employeeInput);
		return ResponseEntity.ok(employee);
	}

	@DeleteMapping("/employees/{id}")
	@Operation(summary = "Delete employee by ID", description = "Delete an employee record by their ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Employee successfully deleted"),
			@ApiResponse(responseCode = "404", description = "Employee not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<String> deleteEmployeeById(
			@PathVariable @Parameter(description = "ID of the employee to delete") int id) {
		logger.info("Deleting employee by id: {}", id);
		String response = employeeService.deleteEmployee(id);
		return ResponseEntity.ok(response);
	}
}
