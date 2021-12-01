package com.writerHub.practice;

import com.writerHub.practice.service.WriterHubUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WriterhubAuthenticationApplication {

	@Bean
	public PasswordEncoder passwordEncoder(){
		return  new BCryptPasswordEncoder();
	}
	public static void main(String[] args) {
		SpringApplication.run(WriterhubAuthenticationApplication.class, args);
	}

}
