package com.SpringAutomation.TestCaseRegression;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestCaseRegressionApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestCaseRegressionApplication.class, args);
		System.out.println("Classpath:");
		System.out.println(System.getProperty("java.class.path"));
	}

}
