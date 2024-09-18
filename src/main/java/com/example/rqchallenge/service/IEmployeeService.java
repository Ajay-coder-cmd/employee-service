package com.example.rqchallenge.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeRequest;

@Service
public interface IEmployeeService {
	List<Employee> getAllEmployees();

	List<Employee> getEmployeesByNameSearch(String name);

	Employee getEmployeeById(int id);

	int getHighestSalaryOfEmployees();

	List<Employee> getTop10HighestEarningEmployees();

	Employee createEmployee(EmployeeRequest request);

	String deleteEmployee(int id);
}
