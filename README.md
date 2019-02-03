# Assignment Scalable Web

Spring-boot application providing an API for calculating the difference between two binary data bodies. 


### Prerequisites

What things you need to install the software and how to install them

* java 8


### Installing

Clone the source locally:

```
$ git clone https://github.com/indiepopart/diff-service.git
$ cd diff-service
$ ./mvnw clean install
```

## Running the application
The default port is 8080.

With maven spring-boot plugin:
```
$ ./mvnw spring-boot run
```

Running the executable jar:
```
$ cd target
$ java -jar diff-service-0.0.1-SNAPSHOT.jar
```


## API documentation

The API was documented using swagger. Check the swagger-ui endpoint at http://localhost:8080/swagger-ui.html


## Running tests

### Running unit tests

```
$ ./mvnw test
```
### Running integration tests


```
$ ./mvnw integration-test
```
The command will also run unit tests first.


## Authors

* **Jimena Garbarino** - *Initial work* - [indiepopart](https://github.com/indiepopart)



