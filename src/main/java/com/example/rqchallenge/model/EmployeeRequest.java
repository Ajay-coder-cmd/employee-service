package com.example.rqchallenge.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Details required to create or update an employee")
public class EmployeeRequest {

	@NotBlank(message = "Name is mandatory")
	@Schema(description = "Name of the employee", example = "Jane Doe")
	private String name;

	@Min(value = 18, message = "Age should not be less than 18")
	@Schema(description = "Age of the employee", example = "28")
	private int age;

	@Min(value = 0, message = "Salary should not be less than 0")
	@Schema(description = "Salary of the employee", example = "65000")
	private int salary;

	@Schema(description = "Profile image URL of the employee", example = "http://example.com/profile.jpg")
	private String profileImage;
}
