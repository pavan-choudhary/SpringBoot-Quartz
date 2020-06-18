package com.infy.scheduler;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchedulerServiceApplication{

	public static void main(String[] args) {
        BasicConfigurator.configure(); 
		SpringApplication.run(SchedulerServiceApplication.class, args);
	}
}
