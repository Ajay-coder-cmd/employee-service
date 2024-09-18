package com.example.rqchallenge.util;

import com.example.rqchallenge.exception.EmployeeException;
import com.example.rqchallenge.model.ApiResponse;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class EmployeeRestClient {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeRestClient.class);

	@Value("${api.base.url}")
	private String baseUrl;

	private final RestTemplate restTemplate;

	@Autowired
	public EmployeeRestClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public List<Employee> getAllEmployees() {
		String url = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/employees").toUriString();
		logger.info("Fetching all employees from URL: {}", url);
		try {
			ResponseEntity<ApiResponse<List<Employee>>> response = restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<ApiResponse<List<Employee>>>() {
					});
			if (response.getStatusCode() == HttpStatus.OK && response.getBody().getStatus().equals("success")) {
				logger.info("Successfully fetched all employees. Status: {}, Data size: {}",
						response.getBody().getStatus(), response.getBody().getData().size());
				return response.getBody().getData();
			} else {
				logger.error("Error while fetching employees. Status code: {}, Status: {}", response.getStatusCode(),
						response.getBody().getStatus());
				throw new EmployeeException("Error while fetching employees", HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} catch (HttpClientErrorException e) {
			logger.error("HttpClientErrorException while fetching employees: {}", e.getMessage());
			throw new EmployeeException("Error while fetching employees: " + e.getMessage(), e.getStatusCode().value(),
					e);
		}
	}

	public Employee getEmployeeById(int id) {
		String url = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/employee").pathSegment(String.valueOf(id))
				.toUriString();
		logger.info("Fetching employee with ID: {}", id);
		try {
			ResponseEntity<ApiResponse<Employee>> response = restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<ApiResponse<Employee>>() {
					});
			if (response.getStatusCode() == HttpStatus.OK && response.getBody().getStatus().equals("success")) {
				logger.info("Successfully fetched employee with ID: {}", id);
				return response.getBody().getData();
			} else {
				logger.error("Employee not found with ID: {}. Status code: {}, Status: {}", id,
						response.getStatusCode(), response.getBody().getStatus());
				throw new EmployeeException("Employee not found with id: " + id, HttpStatus.NOT_FOUND.value());
			}
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				logger.error("Employee not found with ID: {}", id);
				throw new EmployeeException("Employee not found with id: " + id, e.getStatusCode().value());
			} else {
				logger.error("Error while fetching employee with ID: {}: {}", id, e.getMessage());
				throw new EmployeeException("Error while fetching employee: " + e.getMessage(),
						e.getStatusCode().value(), e);
			}
		}
	}

	public Employee createEmployee(EmployeeRequest request) {
		String url = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/create").toUriString();
		logger.info("Creating employee with request data: {}", request);
		try {
			ResponseEntity<ApiResponse<Employee>> response = restTemplate.exchange(url, HttpMethod.POST, null,
					new ParameterizedTypeReference<ApiResponse<Employee>>() {
					});
			if (response.getStatusCode() == HttpStatus.CREATED && response.getBody().getStatus().equals("success")) {
				logger.info("Successfully created employee.");
				return response.getBody().getData();
			} else {
				logger.error("Error while creating employee. Status code: {}, Status: {}", response.getStatusCode(),
						response.getBody().getStatus());
				throw new EmployeeException("Error while creating employee", HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} catch (HttpClientErrorException e) {
			logger.error("HttpClientErrorException while creating employee: {}", e.getMessage());
			throw new EmployeeException("Error while creating employee: " + e.getMessage(), e.getStatusCode().value(),
					e);
		}
	}

	public void deleteEmployee(int id) {
		String url = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/delete").pathSegment(String.valueOf(id))
				.toUriString();
		logger.info("Deleting employee with ID: {}", id);
		try {
			restTemplate.delete(url);
			logger.info("Successfully deleted employee with ID: {}", id);
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				logger.error("Employee not found with ID: {}", id);
				throw new EmployeeException("Employee not found with id: " + id, e.getStatusCode().value());
			} else {
				logger.error("Error while deleting employee with ID: {}: {}", id, e.getMessage());
				throw new EmployeeException("Error while deleting employee: " + e.getMessage(),
						e.getStatusCode().value(), e);
			}
		}
	}
}
