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
* Docker

## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+
* PostgreSQL (or you can use Docker to containerizing the image)


To build and run the project, you can simply open this project in your favorite IDE and run it, or follow these steps if you building and running this project using text editor :

* Clone the repository: `git clone https://github.com/fazriridwan19/ecommerce-spring.git`
* If you have different configuration of PostgreSQL you can update `application.yml` at `src/main/resources/`
* Navigate to the project directory: `cd ecommerce-spring`
* Build the project: `mvn clean install`
* Run the project: `mvn spring-boot:run`

To run this project using docker, you can follow these steps :
* pull the image `docker pull fazriridwan/spring-ecommerce`
* create `docker-compose.yml` file, you can use `docker-compose.yml` file that available in this repo
* go to terminal, set current directory based on your `docker-compose.yml` file directory
* run this command : `docker-compose up`

 
> The application will be available at http://localhost:8080.

> If running on docker , the application will be available at http://localhost:9090.

> The ERD is available at https://drive.google.com/file/d/1WmUAZUdTR72OcZj7tjB_U-GUdnudxcTK/view?usp=sharing

> API documentation available at https://app.swaggerhub.com/apis/fazriridwan19/e-commerce_api/1.0.0
