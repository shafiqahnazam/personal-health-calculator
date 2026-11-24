# Personal Health & Fitness Calculator

A Jakarta EE web application that provides personal health and fitness calculations through a SOAP web service.

## Features

- Body Mass Index (BMI) calculation
- Basal Metabolic Rate (BMR) calculation
- Body fat percentage estimation
- Lean body mass (LBM) calculation
- Total daily energy expenditure (TDEE) calculation
- Malaysian IC number validation and gender detection
- Profile values persisted in the HTTP session
- Input validation and calculation result display

## Technologies

- Java 17
- Jakarta EE 9.1
- Jakarta SOAP Web Services
- JSP and Jakarta Servlets
- HTML, CSS, and JavaScript
- Apache Ant
- Apache NetBeans
- GlassFish Server

## Project Structure

```text
PersonalHealthCalculator/
├── src/java/com/health/
│   ├── client/       # Servlet, SOAP client, and input validation
│   ├── module/       # Health calculation implementations
│   └── service/      # SOAP health web service
├── web/
│   ├── css/          # Application styles
│   ├── WEB-INF/      # Web application configuration
│   ├── index.jsp     # Main calculator page
│   └── script.js     # Form formatting and gender logic
├── dist/             # Built WAR archive
├── build.xml         # Apache Ant build file
└── nbproject/        # Apache NetBeans project configuration
```

## Prerequisites

- JDK 17
- Apache NetBeans 20 or a compatible IDE
- GlassFish Server 6.1 or compatible Jakarta EE 9.1 server
- Apache Ant

## Setup and Deployment

1. Open the project folder in Apache NetBeans.
2. Configure GlassFish Server in NetBeans if it is not already registered.
3. Set the project server to GlassFish and Java 17.
4. Build and deploy the project with **Run** or **Clean and Build**.
5. Open the application at:

   ```text
   http://localhost:8080/PersonalHealthCalculator/
   ```

The generated deployment archive is available at:

```text
dist/PersonalHealthCalculator.war
```

## Usage

Enter a name, IC number, gender, weight, and height. The IC number is accepted in 12-digit or `YYMMDD-XX-XXXX` format and is used to detect gender and calculate age.

Optional calculations require their related fields:

- **BMI:** weight and height
- **BMR:** weight, height, gender, and age
- **Body fat:** waist, neck, height, and hip for female calculations
- **LBM:** weight and body fat percentage
- **TDEE:** weight, height, age, gender, and weekly exercise minutes
