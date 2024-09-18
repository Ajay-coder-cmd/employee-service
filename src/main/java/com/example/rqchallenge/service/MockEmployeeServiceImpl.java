package com.example.rqchallenge.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.rqchallenge.exception.EmployeeException;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeRequest;

@Service
public class MockEmployeeServiceImpl implements IEmployeeService {

	private List<Employee> employees;

	public MockEmployeeServiceImpl() {
		employees = new ArrayList<>();
		employees.add(new Employee(1, "Tiger Nixon", 320800, 61, ""));
		employees.add(new Employee(2, "Garrett Winters", 170750, 63, ""));
		employees.add(new Employee(3, "Ashton Cox", 86000, 66, ""));
		employees.add(new Employee(4, "Cedric Kelly", 433060, 22, ""));
		employees.add(new Employee(5, "Airi Satou", 162700, 33, ""));
		employees.add(new Employee(6, "Brielle Williamson", 372000, 61, ""));
		employees.add(new Employee(7, "Herrod Chandler", 137500, 59, ""));
		employees.add(new Employee(8, "Rhona Davidson", 327900, 55, ""));
		employees.add(new Employee(9, "Colleen Hurst", 205500, 39, ""));
		employees.add(new Employee(10, "Sonya Frost", 103600, 23, ""));
		employees.add(new Employee(11, "Jena Gaines", 90560, 30, ""));
		employees.add(new Employee(12, "Quinn Flynn", 342000, 22, ""));
		employees.add(new Employee(13, "Charde Marshall", 470600, 36, ""));
		employees.add(new Employee(14, "Haley Kennedy", 313500, 43, ""));
		employees.add(new Employee(15, "Tatyana Fitzpatrick", 385750, 19, ""));
		employees.add(new Employee(16, "Michael Silva", 198500, 66, ""));
		employees.add(new Employee(17, "Paul Byrd", 725000, 64, ""));
		employees.add(new Employee(18, "Gloria Little", 237500, 59, ""));
		employees.add(new Employee(19, "Bradley Greer", 132000, 41, ""));
		employees.add(new Employee(20, "Dai Rios", 217500, 35, ""));
		employees.add(new Employee(21, "Jenette Caldwell", 345000, 30, ""));
		employees.add(new Employee(22, "Yuri Berry", 675000, 40, ""));
		employees.add(new Employee(23, "Caesar Vance", 106450, 21, ""));
		employees.add(new Employee(24, "Doris Wilder", 85600, 23, ""));
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employees;
	}

	@Override
	public List<Employee> getEmployeesByNameSearch(String name) {
		return employees.stream()
				.filter(employee -> employee.getEmployeeName().toLowerCase().contains(name.toLowerCase()))
				.collect(Collectors.toList());
	}

	@Override
	public Employee getEmployeeById(int id) {
		return employees.stream().filter(employee -> employee.getId() == id).findFirst().orElseThrow(
				() -> new EmployeeException("Employee not found with id: " + id, HttpStatus.NOT_FOUND.value()));
	}

	@Override
	public int getHighestSalaryOfEmployees() {
		List<Employee> employees = getAllEmployees();
		return employees.stream().mapToInt(employee -> employee.getEmployeeSalary()).max().orElseThrow(
				() -> new EmployeeException("No employee salary data found", HttpStatus.NOT_FOUND.value()));
	}

	@Override
	public List<Employee> getTop10HighestEarningEmployees() {
		return employees.stream().sorted((e1, e2) -> e2.getEmployeeSalary() - e1.getEmployeeSalary()).limit(10)
				.collect(Collectors.toList());
	}

	@Override
	public Employee createEmployee(EmployeeRequest employeeRequest) {
		Employee newEmployee = new Employee(employees.size() + 1, employeeRequest.getName(),
				employeeRequest.getSalary(), employeeRequest.getAge(), employeeRequest.getProfileImage());
		employees.add(newEmployee);
		return newEmployee;
	}

	@Override
	public String deleteEmployee(int id) {
		Employee employee = getEmployeeById(id);
		if (employee != null) {
			employees.remove(employee);
			return employee.getEmployeeName();
		}
		return null;
	}
}