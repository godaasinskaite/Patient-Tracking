# Patient Tracking Application

### 🌟 Overview

This is a back-end application developed using Java 11 and Spring Boot. It provides a robust API for managing Patient, Attendance, Assessment and Progress operations.

### 🚀 Features

 - RESTful API endpoints for creating, updating, finding or deleting Patient, finding, marking scheduling Attendances, assessing and updating Patient Assessments, tracking Progress.
 - API documentation via Swagger UI.

### 🛠️ Technologies Used
 - Java 11
 - Spring Boot
 - H2 Database (for development/testing)
 - PostgreSQL (for production)
 - Spring Flyway
 - Swagger UI
 - Hibernate
 - Spring Data JPA
 - Controller Advice (for exception handling)
 - Input Validation
 - JUnit
 - Mockito
 - Lombok

### 🧰 Setup and Installation

##### Prerequisites

 - JDK 11
 - Your Preferred IDE (e.g., IntelliJ IDEA, Eclipse)
 - Apache Maven (if using command line)

##### Steps to run

 - Run command in Terminal, Command Prompt or PowerShell "git clone https://github.com/godaasinskaite/Patient-Tracking.git"
 - Open project with preferred IDE
 - Choose the appropriate branch: use the dev branch for full functionality
 - Then, run the main method in PatientTrackingApplication.class located at src/main/java/com/app/patient_tracker

 - Use http://localhost:1453/swagger-ui/ to test the API endpoints.

##### If you are using command line to run the project
 - Clone the repository "git clone https://github.com/godaasinskaite/Patient-Tracking.git"
 - Navigate to project direction "cd /path/to/your/project"
 - Choose appropriate branch:
   - "git checkout dev"  # for full functionality
 - Run the application with Maven "mvn clean spring-boot:run"
