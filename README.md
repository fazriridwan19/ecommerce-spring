# E-Commerce Backend Service with JWT Authentication
This project is the implementation of RESTFull API using Spring Boot 3.0 and JSON Web Tokens (JWT) as security. It includes the following features:

## Features
* User registration and login with JWT authentication
* Role-based authorization with Spring Security for accessing resources
* CRUD operation for Product & Cart resource
* Filter product by category
* Add product to cart
* List of product on the shopping cart
* Deleting list of product on the shopping cart
* Checkout and make payment transaction for product

## Technologies
* Spring Boot 3.0
* Spring Security
* Spring Data JPA
* JSON Web Tokens (JWT)
* PostgreSQL (Store as Docker Image)
* Maven

## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+
* PostgreSQL (or you can use Docker to containerizing the image)


To build and run the project, follow these steps:

* Clone the repository: `git clone https://github.com/fazriridwan19/ecommerce-spring.git`
* If you have different configuration of PostgreSQL you can update `application.yml` at `src/main/resources/`
* Navigate to the project directory: cd spring-boot-security-jwt
* Build the project: mvn clean install
* Run the project: mvn spring-boot:run

> The application will be available at http://localhost:8080.

> The ERD is available at https://drive.google.com/file/d/1WmUAZUdTR72OcZj7tjB_U-GUdnudxcTK/view?usp=sharing