# Employee Service

## Overview

This application provides a RESTful API for managing employee records. It allows users to perform various operations, including creating, retrieving, searching, and deleting employee data.

## Base URL

The application interacts with an external API hosted at `https://dummy.restapiexample.com`. 
However, due to the API being unresponsive at the most of times, we have implemented a fallback mechanism 
that utilizes a mock service (`MockEmployeeServiceImpl`). 
This service provides dummy employee data to ensure the application remains functional even when the external API is unavailable.

## Features

- Retrieve all employees
- Search employees by name
- Get employee details by ID
- Retrieve the highest salary among employees
- Fetch the top 10 highest-earning employees
- Create new employee records
- Delete employee records by ID

## Exception Handling

Exception handling has been implemented thoroughly throughout the application to manage errors gracefully and provide meaningful feedback to users. Custom exceptions are used to handle specific scenarios, ensuring that users are informed of any issues encountered.

## Swagger Documentation

Swagger documentation has been integrated into the application, allowing for easy exploration of the available API endpoints. You can access the 
Swagger UI at `/swagger-ui/index.html` once the application is running.

## Lombok

Lombok is used in this project to reduce boilerplate code, such as getters, setters, and constructors. Ensure you have Lombok installed and configured in your IDE for proper functioning.

### IntelliJ IDEA

1. Go to `File > Settings > Plugins`.
2. Search for "Lombok" and install it.
3. Enable annotation processing: `File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors` and check "Enable annotation processing."

### Eclipse

1. Go to `Help > Eclipse Marketplace`.
2. Search for "Lombok" and install it.
3. Follow the installation instructions to integrate it with Eclipse.

## Running the Project

### In IntelliJ IDEA

1. Open the project in IntelliJ.
2. Ensure that you have the necessary Gradle plugin installed.
3. Click on the "Run" button or right-click the `Application` class and select `Run`.
4. The application will start, and you can access it via the configured port.

### In Eclipse

1. Open the project in Eclipse.
2. Ensure that you have the Gradle plugin installed (e.g., Buildship).
3. Right-click on the project and select `Run As > Gradle Build`.
4. The application will start, and you can access it via the configured port.

## Conclusion

This Employee Management Application demonstrates effective API design, exception handling, and the use of Swagger for documentation. The fallback mechanism ensures resilience against external API failures.
