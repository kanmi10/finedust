package com.project.dust;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@Slf4j
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class DustApplication {

	public static void main(String[] args) {
		SpringApplication.run(DustApplication.class, args);
	}


}
