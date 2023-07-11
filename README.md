# MicroServices

**1.Eureka Server
2.Department Service
3.Employee Service
4.Discovery Client
5.Config Server
6.Config Client
7.Api Gateway**


**1.Eureka Server:**
Steps to execute Application :-
  i) Checkout/ downalod code 
  ii) Import project in IDE
  iii) Maven clean and install project
  iv) Run the Spring Boot Application 
  v) Open browesr and type http://localhost:8761, this will open Eureka Server

**2.Employee Service:** 
Steps to execute Application : -
  1) Checkout/ downalod code 
  2) Import project in IDE
  3) Maven clean and install project
  4) Download and install pgAdmin4
  5) Create database as microservices in pgAdmin4
  6) Open application.properties file from application and modify spring.jpa.hibenrate.ddl-auto=create
  7) Start Spring Boot Application
  8) Now referesh eurka sever link http://localhost:8761 will see employee-service get registered
  9) Now Stop Spring Boot Application, add dummy data into the table from pgAdmin4
  10) Modify spring.jpa.hibenrate.ddl-auto=update
  11) Start Spring Boot Application
  12) Open postman and apply Get method url as http://127.0.0.1:8081/api/v1/employees/getAllEmployees this will load all the employees from table.

**3.Department Service:-**
Steps to execute Application : -
  1) Checkout/ downalod code 
  2) Import project in IDE
  3) Maven clean and install project
  4) Download and install pgAdmin4
  5) Create database as microservices in pgAdmin4
  6) Open application.properties file from application and modify spring.jpa.hibenrate.ddl-auto=create
  7) Start Spring Boot Application
  8) Now referesh eurka sever link http://localhost:8761 will see departemnt-service get registered
  9) Now Stop Spring Boot Application, add dummy data into the table from pgAdmin4
  10) Modify spring.jpa.hibenrate.ddl-auto=update
  11) Start Spring Boot Application
  12) Open postman and apply Get method url as http://127.0.0.1:8082/api/v2/departments/getAllDepartments this will load all the employees from table.

**Communication between two microservices:**

** a) WebClient:-**

  1.Now add some dummy data into the employee table as 
    id:11, email_id:a, first_name:a, last_name:a,department_id:11 ( make sure that this depertment id not present in the department table)
  2.Now restart employee service
   Open postman and apply Get method url as http://127.0.0.1:8081/api/v1/employees/getEmployeeDepartment/11
   Will see below result :
   {
    "departmentDto": {
        "id": 1,
        "departmentName": "ABC"
    },
    "employeeDto": {
        "id": 11,
        "firstName": "K",
        "lastName": "K",
        "emailId": "K",
        "departmentId": 11
    }
}
Here CircuitBreaker fallbackMethod method get invoke with default department service data which is shown in the departmentDto.


**b) ApiClient:-**
To use ApiClient some correction is required here
 -comment out WebClient code from Main Class of Employee Service and also EmployeeController file from Autowired and getEmployeeDepartmentById method.
 -un comment out code from the getEmployeeDepartmentById method for the ApiClient
 -Now restart Employee Service and Open postman and apply Get method url as 
  http://127.0.0.1:8081/api/v1/employees/getEmployeeDepartment/11 will see above result.

**c) RestTemplate:**

To use RestTemplate some correction is required here
 -comment out WebClient code from Main Class of Employee Service and also EmployeeController file from Autowired ( ApiClient also ) and getEmployeeDepartmentById method.
 -un comment out code from the getEmployeeDepartmentById method for the RestTemplate and make DepartmentDto mapping changes accordingly
 -Now restart Employee Service and Open postman and apply Get method url as 
  http://127.0.0.1:8081/api/v1/employees/getEmployeeDepartment/11 will see above result
