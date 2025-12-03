package com.example.college_management;

import org.springframework.boot.SpringApplication;

public class TestCollegeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.from(CollegeManagementApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
