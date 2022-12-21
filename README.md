
# Library API  📚
The project's main objective was to create an API to allow the easy management of any a small library business via HTTP requests,
was created using Spring framework, JPA and MySQL, and Offers simple features as creating new users based on specific roles, uploading and
validating book data to the database, and making book reservations based on simple bussines logic rules.

## Tools  🔨
- [Spring Framework](https://spring.io/)
- [JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [MySQL](https://www.mysql.com/)

## Main dependencies  📖
- Lombok
- Open API
- Swagger
- Spring-Boot Validation

## Functionality and usage ⚙️

In order to run correctly the project an Applications.properties file must be created in java/main/resources folder specifying:
  - JDBC driver connector
  - Database user
  - Database password
  - Database Port

It also allows the user to define its own exceptions and exception handlers in the restControllerAdvice file inside the error folder, and throws
a new exceptions everytime a field on any of the DTO classes has a pattern missmatch that may produce an internal server error.

### Full API documentation [here](http://localhost:8080/swagger-ui/index.html)<sub><sup> _(Once the server is running on the default settings)_ </sup></sub> ❇️ 
- Or just access to the swagger UI using your personal configuration 
