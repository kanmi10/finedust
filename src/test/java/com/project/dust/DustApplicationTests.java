package com.project.dust;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootTest
class DustApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void dataTime() {
		LocalDateTime requestTime = LocalDateTime.now();
		LocalDateTime dateTime = LocalDateTime.of(2024, 10, 23, 9, 0);

		Duration between = Duration.between(dateTime, requestTime);
		System.out.println("between.toMinutes() = " + between.toMinutes());



	}

}
