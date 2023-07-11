# MicroServices

1.Eureka Server
2.Department Service
3.Employee Service
4.Discovery Client
5.Config Server
6.Config Client
7.Api Gateway


1.Eureka Server:
Steps to execute Application :-
  i) Checkout/ downalod code 
  ii) Import project in IDE
  iii) Maven clean and install project
  iv) Run the Spring Boot Application 
  v) Open browesr and type http://localhost:8761, this will open Eureka Server

  
2.Employee Service: 
Steps to execute Application : -
  1) Checkout/ downalod code 
  2) Import project in IDE
  3) Maven clean and install project
  4) Download and install pgAdmin4
  5) Create database as microservices in pgAdmin4
  6) Open application.properties file from application and modify spring.jpa.hibenrate.ddl-auto=create
  7) Start Spring Boot Application
  8) Now Stop Spring Boot Application, add dummy data into the table from pgAdmin4
  9) Modify spring.jpa.hibenrate.ddl-auto=update
  10) Start Spring Boot Application
  11) Open postman and apply Get method url as http://127.0.0.1:8081/api/v1/employees/getAllEmployees this will load all the employees from table.

3.Department Service:-
Steps to execute Application : -
  1) Checkout/ downalod code 
  2) Import project in IDE
  3) Maven clean and install project
  4) Download and install pgAdmin4
  5) Create database as microservices in pgAdmin4
  6) Open application.properties file from application and modify spring.jpa.hibenrate.ddl-auto=create
  7) Start Spring Boot Application
  8) Now Stop Spring Boot Application, add dummy data into the table from pgAdmin4
  9) Modify spring.jpa.hibenrate.ddl-auto=update
  10) Start Spring Boot Application
  11) Open postman and apply Get method url as http://127.0.0.1:8082/api/v2/departments/getAllDepartments this will load all the employees from table.

Communication between two microservices using WebClient:-

1.Now add some dummy data into the employee table as 
 EmpId:
 
    
  
