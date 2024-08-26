package com.example.spb_br;

import com.example.spb_br.controller.AccountController;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpbBrApplicationTests {

	@Autowired
	private AccountController accountController;

	@Test
	void contextLoads() {
		assertThat(accountController).isNotNull();
	}

}
