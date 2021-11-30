package com.writerHub.practice;

import com.writerHub.practice.service.AuthorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WriterhubAuthenticationApplication {

	@Bean
	public AuthorService getAuthorService(){
		return new AuthorService();
	}
	public static void main(String[] args) {
		SpringApplication.run(WriterhubAuthenticationApplication.class, args);
	}

}
