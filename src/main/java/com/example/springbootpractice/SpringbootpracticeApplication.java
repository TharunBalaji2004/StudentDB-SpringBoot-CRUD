package com.example.springbootpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootpracticeApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootpracticeApplication.class, args);
	}
}

/*
* Controller (consists of REST APIs and makes HTTP Requests and hits the API path)- AddMethods(path)
* Service - contains the implementation of AddMethod
* Repository - saves the data fetched by AddMethods
* Model - Create a Model Class for storing data with properties to DB (Eg: StudentModelClass)
++
Flow: Controller -> Service -> Repository (stores data as Model datatype)
* */
