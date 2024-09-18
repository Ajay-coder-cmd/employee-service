package com.example.rqchallenge.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import com.example.rqchallenge.model.ApiResponse;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeRequest;

@SpringBootTest
@ActiveProfiles("test")
public class EmployeeRestClientTest {

	@MockBean
	private RestTemplate restTemplate;

	@Autowired
	private EmployeeRestClient employeeRestClient;

	@Test
	void testGetAllEmployees_Success() {

		List<Employee> employees = Collections.singletonList(new Employee(1, "John Doe", 50000, 30, "profile.jpg"));
		ApiResponse<List<Employee>> apiResponse = new ApiResponse<>("success", employees,
				"Employees fetched successfully");

		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), isNull(), any(ParameterizedTypeReference.class)))
				.thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));

		List<Employee> result = employeeRestClient.getAllEmployees();

		// Verify results
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("John Doe", result.get(0).getEmployeeName());
	}

	@Test
	void testGetEmployeeById_Success() {

		Employee employee = new Employee(1, "John Doe", 50000, 30, "profile.jpg");
		ApiResponse<Employee> apiResponse = new ApiResponse<>("success", employee, "Employee fetched successfully");

		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), isNull(), any(ParameterizedTypeReference.class)))
				.thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));

		Employee result = employeeRestClient.getEmployeeById(1);

		assertNotNull(result);
		assertEquals("John Doe", result.getEmployeeName());
	}

	@Test
	void testCreateEmployee_Success() {
		// Setup mock response
		EmployeeRequest request = new EmployeeRequest("John Doe", 50000, 30, "profile.jpg");
		Employee employee = new Employee(1, "John Doe", 50000, 30, "profile.jpg");
		ApiResponse<Employee> apiResponse = new ApiResponse<>("success", employee, "Employee created successfully");

		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(), any(ParameterizedTypeReference.class)))
				.thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.CREATED));

		Employee result = employeeRestClient.createEmployee(request);

		// Verify results
		assertNotNull(result);
		assertEquals("John Doe", result.getEmployeeName());
	}

	@Test
	void testDeleteEmployee_Success() {

		doNothing().when(restTemplate).delete(anyString());

		assertDoesNotThrow(() -> employeeRestClient.deleteEmployee(1));
	}

}
