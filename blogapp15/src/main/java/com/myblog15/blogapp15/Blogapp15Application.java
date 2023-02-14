package com.myblog15.blogapp15;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication// What is @SpringBootApplication? 1) This is the starting point of execution. 2)This is the class where you do configuration like some beans to be created using @Bean Annotation and you can do it here.
public class Blogapp15Application {
//   @Autowired  // Instead of writing @Bean we can write @Autowired.
//	private ModelMapper modelMapper;
	@Bean //When you applied @Bean annotation on a method it will return an object, which is basically maintained by spring boot  When you run this project spring-boot will understand, Wherever I am using ModelMapper. It has create its object  @Bean  will create an object  and this object address will get Injected  this reference variable (mapper) Ex-   private ModelMapper mapper;
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(Blogapp15Application.class, args);
	}

}
