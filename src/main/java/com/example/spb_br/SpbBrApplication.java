package com.example.spb_br;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class SpbBrApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpbBrApplication.class, args);
	}

}
