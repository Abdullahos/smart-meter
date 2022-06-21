package com.root.meter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@Profile(value = "test")

@SpringBootTest
class MeterApplicationTests {

	@Test
	void contextLoads() {
	}

}