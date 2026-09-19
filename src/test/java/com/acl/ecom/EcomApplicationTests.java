package com.acl.ecom;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EcomApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testVolontairementIncorrect() {
		org.junit.jupiter.api.Assertions.assertEquals(2, 1 + 1);
	}

}
