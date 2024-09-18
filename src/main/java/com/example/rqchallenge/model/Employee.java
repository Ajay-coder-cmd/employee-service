package com.example.rqchallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Details about the employee")
public class Employee {

	@Schema(description = "Unique identifier of the employee", example = "1")
	@JsonProperty("id")
	private int id;

	@Schema(description = "Name of the employee", example = "John Doe")
	@JsonProperty("employee_name")
	private String employeeName;

	@Schema(description = "Salary of the employee", example = "75000")
	@JsonProperty("employee_salary")
	private int employeeSalary;

	@Schema(description = "Age of the employee", example = "30")
	@JsonProperty("employee_age")
	private int employeeAge;

	@Schema(description = "Profile image URL of the employee", example = "http://example.com/profile.jpg")
	@JsonProperty("profile_image")
	private String profileImage;
}
