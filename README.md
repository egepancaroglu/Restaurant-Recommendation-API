# Restaurant Recommendation API

restaurant-service, user-review-service and recommendation service are Spring Boot application that focused on user, restaurant operations and user based restaurant recommendation. <br/>

## Architecture

![architecture](https://github.com/egepancaroglu/N11-Bootcamp-Final-Case/assets/76057764/3a615858-d236-4962-a8a9-62b742db8c0a)


## Descriptions

- to determine user or restaurant fields via frontend or swagger.
- creating restaurants and own reviews.
- user-location based restaurant recommendation service.

## Requirements

For building and running the application: you need:

- **JDK 17** **JDK 21** 
- **IDE** (IntelliJ Idea)
- **[Get Docker](https://docs.docker.com/get-docker/)** *(optional)*

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. You can use anyone.

- If you dont have RabbitMQ, Solr, PostgreSQL, MongoDB or zipkin on your locally. You must build the docker-compose.yml for creating containers. 'docker-compose up --build' (Recommended, Docker required).

- You can execute the main methods sequencly by main methods classes from your IDE.
  - 1. You must start service-registry for all services registering him.
  - 2. You must start config-server for all services can find own config files.
  - 3. You must start user-review-service for user operations.
  - 4. You must start restaurant-service for restaurant operations.
  - 5. You must start recommendation-service for user-location based recommendations.
  - 6. You must start logger-service for storing error logs for services.

- 'mvn spring-boot:run' (Maven required)

## Usage and Endpoints

All endpoints can find on via swagger :

  - [user-review-service ](http://localhost:8080/swagger-ui/index.html#)
  - [restaurant-service ](http://localhost:8081/swagger-ui/index.html#)
  - [recommendation-service ](http://localhost:8083/swagger-ui/index.html#)



## Contributing

- Thanks to [N11 ](https://www.linkedin.com/company/n11/?originalSubdomain=tr) & [Patika ](https://www.patika.dev/home) family for mentoring and precious helping in the development process :)



