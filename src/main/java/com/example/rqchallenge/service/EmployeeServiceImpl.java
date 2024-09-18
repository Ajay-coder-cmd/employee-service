package com.example.rqchallenge.service;

import com.example.rqchallenge.exception.EmployeeException;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeRequest;
import com.example.rqchallenge.util.EmployeeRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
public class EmployeeServiceImpl implements IEmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	private final EmployeeRestClient employeeRestClient;
	private final MockEmployeeServiceImpl mockEmployeeService;

	@Autowired
	public EmployeeServiceImpl(EmployeeRestClient employeeRestClient, MockEmployeeServiceImpl mockEmployeeService) {
		this.employeeRestClient = employeeRestClient;
		this.mockEmployeeService = mockEmployeeService;
		logger.info("EmployeeServiceImpl initialized");
	}

	@Override
	public List<Employee> getAllEmployees() {
		logger.info("Fetching all employees");
		try {
			return employeeRestClient.getAllEmployees();
		} catch (Exception e) {
			logger.error("Failed to fetch all employees from external API. Falling back to dummy API.", e);
			return mockEmployeeService.getAllEmployees();
		}
	}

	@Override
	public List<Employee> getEmployeesByNameSearch(String name) {
		logger.info("Searching employees by name: {}", name);
		List<Employee> allEmployees = getAllEmployees();
		List<Employee> employeesByName = allEmployees.stream()
				.filter(employee -> employee.getEmployeeName().toLowerCase().contains(name.toLowerCase()))
				.collect(Collectors.toList());
		if (employeesByName.isEmpty()) {
			logger.error("No employees found matching the search: {}", name);
			throw new EmployeeException("No employees found matching the search: " + name,
					HttpStatus.NOT_FOUND.value());
		}
		return employeesByName;
	}

	@Override
	public Employee getEmployeeById(int id) {
		logger.info("Fetching employee by id: {}", id);
		try {
			return employeeRestClient.getEmployeeById(id);
		} catch (Exception e) {
			logger.error("Failed to fetch employee with id {} from external API. Falling back to dummy API.", id, e);
			return mockEmployeeService.getEmployeeById(id);
		}
	}

	@Override
	public int getHighestSalaryOfEmployees() {
		logger.info("Calculating highest salary of employees");
		List<Employee> employees = getAllEmployees();
		return employees.stream().mapToInt(Employee::getEmployeeSalary).max().orElseThrow(
				() -> new EmployeeException("No employee salary data found", HttpStatus.NOT_FOUND.value()));
	}

	@Override
	public List<Employee> getTop10HighestEarningEmployees() {
		logger.info("Fetching top 10 highest earning employees");
		List<Employee> employees = getAllEmployees();
		return employees.stream().sorted((e1, e2) -> e2.getEmployeeSalary() - e1.getEmployeeSalary()).limit(10)
				.collect(Collectors.toList());
	}

	@Override
	public Employee createEmployee(EmployeeRequest request) {
		logger.info("Creating new employee");
		try {
			return employeeRestClient.createEmployee(request);
		} catch (Exception e) {
			logger.error("Failed to create employee using external API.", e);
			return mockEmployeeService.createEmployee(request);
		}
	}

	@Override
	public String deleteEmployee(int id) {
		logger.info("Deleting employee with id: {}", id);
		try {
			Employee employee = getEmployeeById(id);
			String employeeName = employee.getEmployeeName();
			employeeRestClient.deleteEmployee(id);
			return employeeName;
		} catch (Exception e) {
			logger.error("Failed to delete employee with id {} from external API. Falling back to dummy API.", id, e);
			return mockEmployeeService.deleteEmployee(id);
		}
	}
}