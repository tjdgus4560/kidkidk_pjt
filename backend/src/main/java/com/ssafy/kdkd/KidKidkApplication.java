package com.ssafy.kdkd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KidKidkApplication {

	public static void main(String[] args) {
		SpringApplication.run(KidKidkApplication.class, args);
	}

}
