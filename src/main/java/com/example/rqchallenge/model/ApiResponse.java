package com.example.rqchallenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
	private String status;
	private T data;
	private String message;
}
